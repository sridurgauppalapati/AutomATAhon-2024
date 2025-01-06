package koha;


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import configuration.Keywords;
import configuration.ReadPropertiesFile;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import utilities.FileUtility;
import utilities.Koha_locator;
import utilities.Locators;
import utilities.Reporting;

public class Koha_start {
	
	Locators locator = new Locators();
	Keywords actions = new Keywords();
	Reporting logger = new Reporting();
	Koha_locator klocator = new Koha_locator();
	ReadPropertiesFile propertyFile = new ReadPropertiesFile();
	private String patronId;
	
	public void Login() {
		try {
			actions.setValue(locator.userName,propertyFile.readProperties("datafile","username"));
			actions.setValue(locator.password,propertyFile.readProperties("datafile","password"));
			
			actions.click(locator.sumbit);
			actions.wait(20);
		}catch(Exception e) {
			logger.logFail("Failed to login due to exception "+e.getMessage());
		}
		
	}
	
	public void create_library() {
		try {
			actions.click(klocator.admin);
			actions.javaScriptClick(klocator.Library);
			actions.javaScriptClick(klocator.add_lib);
			actions.setValue(klocator.branchname,"TestVerse");
			actions.setValue(klocator.branchcode, "GTRHACK"+"TV" );
			actions.javaScriptClick(klocator.library_submit);

		}catch(Exception e) {
			e.printStackTrace();
			logger.logFail("Failed to login due to exception "+e.getMessage());
		}
		
	}
		public void createPatronWithOPACUI(String cardNumber, String firstName, String surname, String email) throws IOException {
			try {
				actions.javaScriptClick(klocator.Patrons);
				actions.javaScriptClick(klocator.New_Patrons);
				actions.javaScriptClick(klocator.Patron_lib);
				actions.setValue(klocator.Patron_Surname,surname );
				actions.setValue(klocator.Patron_Firstname,firstName);
				actions.setValue(klocator.Patron_Email, email );
				actions.setValue(klocator.Patron_CardNumber,cardNumber );
				actions.javaScriptClick(klocator.Patron_Save);
	
			}catch(Exception e) {
				e.printStackTrace();
				logger.logFail("Failed to login due to exception "+e.getMessage());
			}
			
	}
		public void CreatePatronWithLibrarianUI(String cardNumber, String firstName, String surname, String email) throws IOException {
			try {
				actions.click(klocator.Patrons);
				actions.click(klocator.New_Patrons);
				actions.javaScriptClick(klocator.Patron_lib);
				actions.setValue(klocator.Patron_Surname,surname );
				actions.setValue(klocator.Patron_Firstname,firstName);
				actions.setValue(klocator.Patron_Email, email );
				actions.setValue(klocator.Patron_CardNumber,cardNumber );
				actions.javaScriptClick(klocator.Patron_Save);
	
			}catch(Exception e) {
				e.printStackTrace();
				logger.logFail("Failed to login due to exception "+e.getMessage());
			}
			
	}
		
		 public void createPatronWithAPI() throws IOException {
		        // Rest-Assured API request to create a patron
		
			         // Base URI for Koha API
			         RestAssured.baseURI = propertyFile.readProperties("datafile","apiBaseUrl");
			         String username = propertyFile.readProperties("datafile","username");
			         String password = propertyFile.readProperties("datafile","password");

			         String requestBody = new String(Files.readAllBytes(Paths.get("Utils/JsonFiles/patronPayload.json")));
			      

			         // Make POST request to create a patron

			         Response response = RestAssured.given()
			                 .auth().preemptive()
			                 .basic(username, password)  // Basic Authentication
			                 .header("Content-type", "application/json")
			                 .body(requestBody)
			                 .when()
			                 .post("/api/v1/patrons");
			         
			         // Print response for debugging
			         System.out.println("Response Code: " + response.getStatusCode());
			         System.out.println("Response Body: " + response.prettyPrint());

			         // Assert the response status code
			         if (response.getStatusCode() == 201) {
			             System.out.println("Patron created successfully!");
			         } else {
			             System.out.println("Failed to create patron. Error: " + response.getStatusCode());
			         }

			         // Extract the generated userid
			         String generatedUserId = response.jsonPath().getString("userid");
			         System.out.println("Generated UserID: " + generatedUserId);

			         // Save the generated userid to a file for further use
			         // (This part can be implemented as needed)
			     } 
		 
		 public void createBook() throws IOException {
			 Robot robot;
			    try {
			    	robot = new Robot();
			        // Navigate through Cataloging options
			        actions.click(klocator.Cataloging);
			        actions.click(klocator.New_Record);
			        actions.click(klocator.Server_Search);

			        // Switch to the server search results window
			        actions.switchToWindow("Z39.50/SRU search results › Koha");
			        actions.setValue(klocator.ISBN, "0205311326");
			        actions.ensureCheckboxChecked(klocator.LibraryOfCongress_checkbox);
			        actions.click(klocator.Book_submit);

			        // Switch back to the search results and import record
			        actions.switchToWindow("Z39.50/SRU search results › Koha");
			        actions.click(klocator.carat_dropdown);
			        actions.click(klocator.Import);

			        // Switch to the MARC record tab
			        actions.switchToWindow("Add MARC record › Cataloging › Koha");

			        // Save and view record
			        actions.click(klocator.Save_Dropdown);
			        actions.click(klocator.SaveAndViewRecord);

			        // Handle mandatory fields if error modal is visible
			        if (actions.isElementVisible(klocator.ErrorModal)) {
			            List<WebElement> missingFields = actions.getMissingFields(klocator.MissingFields);
			            System.out.println("Missing fields: " + missingFields);

			            // Example fix for mandatory field (e.g., Koha Item Type)
			            try {
			                actions.click(klocator.kohaItemTypeLink);
			                actions.click(klocator.kohaItemType);
			                actions.setValue(klocator.kohaItemTypeInput, "my");
			                robot.keyPress(KeyEvent.VK_ENTER);
			                actions.click(klocator.Save_Dropdown);
			                actions.click(klocator.SaveAndViewRecord);
			            } catch (Exception e) {
			                logger.logFail("Failed to handle Koha item type field due to exception: " + e.getMessage());
			            }

			            
			        }

			        // Validate the book title (optional validation step)
			        String capturedTitle = actions.getText(klocator.BookTitleField);
			        if ("Practical stress management : a comprehensive workbook for managing change and promoting health / John A. Romas, Manoj Sharma.".equals(capturedTitle)) {
			            logger.logPass("Book title validated successfully: " + capturedTitle, capturedTitle);
			        } else {
			            logger.logFail("Validation failed. Expected: Expected Book Title, Found: " + capturedTitle);
			        }

			    } catch (Exception e) {
			        e.printStackTrace();
			        logger.logFail("Failed to create book due to exception: " + e.getMessage());
			    }
			}
		 public void addItemsToBook() throws Exception {
			 Robot robot = new Robot();

			    try {
			        // Navigate to cataloging and search for the book
			        actions.click(klocator.Cataloging);
			        actions.setValue(klocator.Cat_search, "Practical stress management");
			        robot.keyPress(KeyEvent.VK_ENTER);
			        robot.keyRelease(KeyEvent.VK_ENTER);
			        actions.click(klocator.cat_searchResult);

			        // Navigate to edit and manage items
			        actions.click(klocator.bookDetail_edit);
			        actions.click(klocator.bookDetail_editItem);

			        // Capture home and current library values
			        String homeLibraryRaw = actions.getWebElement(klocator.Home_library).getText();
			        String currentLibraryRaw = actions.getWebElement(klocator.Current_library).getText();

			        String homeLibrary = homeLibraryRaw.replace("×", "").trim();
			        String currentLibrary = currentLibraryRaw.replace("×", "").trim();

			        System.out.println("Home Library: " + homeLibrary);
			        System.out.println("Current Library: " + currentLibrary);

			        // Set shelving location
//			        actions.selectDropdownByVisibleText(actions.getWebElement(klocator.Shelving_location), "Book cart");
			        actions.click(klocator.Shelving_location);
			        robot.keyPress(KeyEvent.VK_ENTER);
			        robot.keyRelease(KeyEvent.VK_ENTER);
			        // Generate a random date acquired within the past 3 months
//			        LocalDate randomDate = LocalDate.now().minusDays((int) (Math.random() * 90));
//		            String formattedDate = randomDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//		            actions.setHiddenDate(klocator.Date_acquired, klocator.Date_acquiredInput, formattedDate);
//
//			        actions.getWebElement(klocator.Date_acquiredInput).sendKeys(formattedDate);

			        // Generate and set barcode
			        String libraryName = currentLibrary;
			        String libraryInitials = libraryName.replaceAll("[^A-Z]", "");
			        String barcode = libraryInitials + "_BK_1";
			        actions.setValue(klocator.Barcode, barcode);

			        // Click "Add multiple copies of this item"
			        actions.click(klocator.multi_option);

			        // Generate a random number of copies and add them
			        int copiesToAdd = new Random().nextInt(11) + 5; // Random number between 5 and 15
			        actions.setValue(klocator.no_of_copies, String.valueOf(copiesToAdd));
			        actions.click(klocator.add_copies);

			        // Validate barcodes
			        List<WebElement> barcodeElements = actions.getWebElementList(klocator.item_table);
			        List<String> barcodes = new ArrayList<>();
			        for (WebElement barcodeElement : barcodeElements) {
			            barcodes.add(barcodeElement.getText());
			        }

			        for (int i = 0; i < barcodes.size(); i++) {
			        	System.out.println("barcodes:"+barcodes);
			            String expectedBarcode = libraryInitials + "_BK_" + (i + 1);
			            System.out.println("expectedBarcode:"+expectedBarcode);
			            if (!barcodes.get(i).equals(expectedBarcode)) {
			                throw new Exception("Barcode validation failed at index " + i);
			            }
			        }

			        // Write barcodes to a file
			        FileUtility.writeToFile("C:/Users/sriuppal2/OneDrive - Publicis Groupe/Desktop/Koha/path_to_output_file.txt", barcodes);

			        System.out.println("All barcodes validated and saved successfully!");

			    } catch (Exception e) {
			        System.err.println("Error occurred while adding items to the book: " + e.getMessage());
			        throw e;
			    }
		 }

		 public void addBookToCart() throws Exception {
			 Robot robot = new Robot(); // Instantiate Robot for keyboard operations

			    try {
			        // Navigate to cataloging and search for the book
			        actions.click(klocator.Cataloging);
			        actions.setValue(klocator.Cat_search, "Practical stress management");
			        robot.keyPress(KeyEvent.VK_ENTER);
			        robot.keyRelease(KeyEvent.VK_ENTER);

			        // Select the book from the search results
			        actions.click(klocator.cat_searchResult);

			        // Add the book to the cart
			        actions.click(klocator.book_AddToCart);
			        String cartButtonText = actions.getText(klocator.book_AddToCart);

			        // Validate the cart button text
			        if (!cartButtonText.contains("Remove from cart")) {
			            throw new Exception("Cart button text validation failed!");
			        }

			        // Validate the cart count
			        WebElement cartIcon = actions.getWebElement(klocator.cart_iconcount); // Locator for the cart icon
			        String cartCount = cartIcon.getText();
			        if (!cartCount.equals("1")) {
			            throw new Exception("Cart count validation failed!");
			        }

			        // Navigate to the cart and select the book for placing a hold
			        actions.click(klocator.cart);
			        actions.switchToWindow("Your cart › Catalog › Koha"); // Switch to the cart window
			        actions.click(klocator.cart_Checkbox); // Select the checkbox for the book
			        actions.click(klocator.placehold); // Click on 'Place Hold'

			        // Switch to the 'Place Hold' window
			        actions.switchToWindow(
			            "Place a hold on Practical stress management : a comprehensive workbook for managing change and promoting health / › Holds › Circulation › Koha"
			        );
			        actions.click(klocator.placeholdPatronSearch); // Search for the patron
			        actions.click(klocator.confirmhold); // Confirm the hold

			        // Validate the book title in the Holds tab
			        String holdBookTitle = actions.getWebElement(klocator.bookTitle).getText();
			        if (!holdBookTitle.equals(
			            "Practical stress management : a comprehensive workbook for managing change and promoting health /"
			        )) {
			            throw new Exception("Book title validation failed in Holds tab!");
			        }

			        System.out.println("All validations passed!");
			    } catch (Exception e) {
			        System.err.println("An error occurred: " + e.getMessage());
			        throw e; // Re-throw the exception for further handling
			    }
			}
		 public void CheckoutBook() throws Exception {
			 Robot robot = new Robot(); // Instantiate Robot for keyboard operations

			    try {
			    	actions.click(klocator.Patrons);
			    	actions.click(klocator.Patron_Search);
			    	actions.wait(1000);
			    	actions.click(klocator.Hold_History);
			    	List<WebElement> holdsHistoryTable = actions.getWebElementList(klocator.holdsHistoryTableRows);
			    	 // Validate that the book is visible in "Holds History" table
			        boolean bookFound = false;
			        for (WebElement row : holdsHistoryTable) {
			            if (row.getText().contains("Practical stress management")) { // Replace with dynamic book title if required
			                bookFound = true;
			                String status = actions.getWebElement(klocator.holdStatus).getText(); // Adjust locator for status
			                if (!status.equals("Pending")) {
			                    throw new Exception("Book status validation failed! Expected: Pending, Found: " + status);
			                }
			                break;
			            }
			        }
			        if (!bookFound) {
			            throw new Exception("Book not found in Holds History!");
			        }
			        actions.click(klocator.checkout);
			        actions.click(klocator.hold);
			        
			        
			        String holdBookTitle = actions.getText(klocator.holdsTabBookTitle);
			        String holdDate = actions.getText(klocator.holdsTabBookDate);
			        if (!holdBookTitle.equals("Practical stress management")) {
			            throw new Exception("Book title mismatch in Holds tab! Expected: Practical stress management, Found: " + holdBookTitle);
			        }
			        // Enter the barcode and check out the book
			        String randomBarcode = "FL_BK_1"; // Replace with dynamically captured barcode
			        actions.setValue(klocator.checkoutBarcodeInput, randomBarcode);
			        actions.click(klocator.checkout);
			        
			        // Validate that book details appear under "Checkouts" tab
			        actions.click(klocator.checkoutTab);
			        List<WebElement> checkoutsTableRows = actions.getWebElementList(klocator.checkoutsTableRows);
			        boolean checkoutBookFound = false;
			        for (WebElement row : checkoutsTableRows) {
			            if (row.getText().contains("Practical stress management") && row.getText().contains(randomBarcode)) {
			                checkoutBookFound = true;
			                break;
			            }
			        }
			        if (!checkoutBookFound) {
			            throw new Exception("Book details not found under 'Checkouts' tab!");
			        }
			        
			     // Validate that the number next to "Checkouts" tab is updated
			        String checkoutsCount = actions.getText(klocator.checkoutsTabCount); // Adjust locator
			        int checkoutsNumber = Integer.parseInt(checkoutsCount);
			        if (checkoutsNumber < 1) {
			            throw new Exception("Checkouts count validation failed! Expected: >= 1, Found: " + checkoutsNumber);
			        }

			        System.out.println("All validations passed successfully!");
			    } catch (Exception e) {
			        System.err.println("An error occurred: " + e.getMessage());
			        throw e; // Re-throw for further handling
			    }

			    	
			    }
		 public void AddCreditToKohaAPI() throws Exception {
			 try {
			 RestAssured.baseURI = propertyFile.readProperties("datafile","apiBaseUrl");
	         String username = propertyFile.readProperties("datafile","username");
	         String password = propertyFile.readProperties("datafile","password");

	        // Generate a random credit amount between 100 and 500
	            int creditAmount = new Random().nextInt(401) + 100;
	            patronId = "1";
	            // API Endpoint
	            String endpoint = "/patrons/" + patronId + "/account/credits";

	            // Create request body
	            Map<String, Object> requestBody = new HashMap<>();
	            requestBody.put("credit_type", "CREDIT"); // Type of credit
	            requestBody.put("amount", creditAmount); // Credit amount
	            requestBody.put("library_id", "123"); // Library ID
	            requestBody.put("description", "Adding credit to patron account"); // Description
	            requestBody.put("note", "Credit added via automation"); // Note
	        
	         Response response = RestAssured.given()
	                 .auth().preemptive()
	                 .basic(username, password)  // Basic Authentication
	                 .header("Content-type", "application/json")
	                 .body(requestBody)
	                 .when()
	                 .post(endpoint);
	        
	         
	      // Validate response
	            if (response.statusCode() == 200) {
	                // Extract the response body
	                Map<String, Object> responseBody = response.jsonPath().getMap("$");

	                // Validate the credit amount in the response matches the request
	                if ((int) responseBody.get("amount") == creditAmount) {
	                    System.out.println("Credit added successfully! Amount: " + creditAmount);
	                } else {
	                    throw new Exception("Credit amount mismatch! Sent: " + creditAmount + ", Received: " + responseBody.get("amount"));
	                }
	            } else {
	                throw new Exception("Failed to add credit! HTTP Status: " + response.statusCode() + ", Response: " + response.body().asString());
	            }
	        } catch (Exception e) {
	            System.err.println("Error while adding credits: " + e.getMessage());
	        }
	    }
		 public void submitOrCheckInBook() throws Exception {
			    try {
			    	String barcode = "FL_BK_1";
			        // Navigate to Staff UI Circulation > Check in
			        actions.click(klocator.circulationTab); // Click on Circulation tab
			        actions.click(klocator.checkInTab); // Click on Check in option

			        // Enter the barcode number used for checkout
			        actions.setValue(klocator.checkInBarcodeInput, barcode); // Set barcode value
			        actions.click(klocator.checkInButton); // Click on Check in button

			        // Validate the book is present in "Checked-in items" table
			        boolean isBookPresent = actions.waitForElementToBeVisible((actions.getWebElement(klocator.checkedInItemsTable)),1000);
			      
			        if (!isBookPresent) {
			            throw new Exception("Checked-in items table is not visible!");
			        }

			        // Fetch book details from the Checked-in items table
			        String checkedInBookTitle = actions.getText(klocator.checkedInBookTitle);
			       

			        // Validate the book title and status
			        if (!checkedInBookTitle.equals("Practical Stress Management")) {
			            throw new Exception("Book title does not match! Expected: 'Practical Stress Management', Found: '" + checkedInBookTitle + "'");
			        }
			 

			        System.out.println("Book successfully checked in. All validations passed!");
			    } catch (Exception e) {
			        System.err.println("Error during book check-in process: " + e.getMessage());
			        throw e;
			    }
			}
}


	  
		 	