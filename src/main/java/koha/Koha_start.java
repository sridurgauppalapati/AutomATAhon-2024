package koha;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import configuration.Keywords;
import configuration.ReadPropertiesFile;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

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
			 try {
				 actions.click(klocator.Cataloging);
				 actions.click(klocator.New_Record);
				 actions.click(klocator.Server_Search);
				 actions.switchToWindow("Z39.50/SRU search results › Koha");
				 actions.setValue(klocator.ISBN, "0205311326");
				 actions.click(klocator.LibraryOfCongress_checkbox);
				 actions.click(klocator.Book_submit);
				 actions.switchToWindow("Z39.50/SRU search results › Koha");
				 actions.click(klocator.carat_dropdown);
				 actions.click(klocator.Import);
				 // Switch back to the default content
//				 actions.switchToDefaultWindow();
				 actions.switchToWindow("Add MARC record › Cataloging › Koha");
				
			        // Save and view record
			        actions.click(klocator.Save_Dropdown);
			        actions.click(klocator.SaveAndViewRecord);

			        // Handle mandatory fields if error occurs
			        if (actions.isElementVisible(klocator.ErrorModal)) {
			            List<WebElement> missingFields = actions.getMissingFields(klocator.MissingFieldsList);
			            for (WebElement field : missingFields) {
			                String fieldName = field.getText();
			                logger.logInfo("Handling missing field: " + fieldName);
			                WebElement inputField = actions.findElementByText(fieldName);

			                if (inputField.getTagName().equals("select")) {
			                    actions.selectByVisibleText(inputField, "Books");
			                } else if (inputField.getTagName().equals("input")) {
			                    if (inputField.getAttribute("type").equals("checkbox") || inputField.getAttribute("type").equals("radio")) {
			                        if (!inputField.isSelected()) {
			                            inputField.click();
			                        }
			                    } else {
			                        inputField.sendKeys("Sample Value");
			                    }
			                }

			            }
			            actions.click(klocator.SaveAndViewRecord);
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

}
