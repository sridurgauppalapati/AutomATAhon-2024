package koha;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validations {
	private static final String EXCEL_FILE_PATH = "C:/Users/sriuppal2/OneDrive - Publicis Groupe/Desktop/Koha/Patrons.xlsx";
    private static final String SCREENSHOT_DIR = "C:/Users/sriuppal2/OneDrive - Publicis Groupe/Desktop/Koha/screenshots/";
    static WebDriver driver = new ChromeDriver();
    public static void OPACPatronValidation() throws IOException, InterruptedException {
        // Set up WebDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        

        // Open Excel file
        FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Iterate through Excel rows
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            // Get username and password from Excel
            String username = row.getCell(1).getStringCellValue(); // Column B: Username
            String password = row.getCell(2).getStringCellValue(); // Column C: Password

            // Open OPAC login page
            driver.get("http://localhost:8080/cgi-bin/koha/mainpage.pl"); // Replace with OPAC URL
            driver.manage().window().maximize();

            // Take pre-login screenshot
            captureScreenshot(driver, "OPAC_LOGIN_" + username);

            // Log in to OPAC
            WebElement usernameField = driver.findElement(By.id("username")); // Replace with actual locator
            WebElement passwordField = driver.findElement(By.id("password")); // Replace with actual locator
            WebElement loginButton = driver.findElement(By.id("loginButton")); // Replace with actual locator

            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            loginButton.click();

            // Wait for the page to load
            Thread.sleep(3000); // Replace with WebDriverWait if dynamic loading occurs

            // Verify login success
            boolean isLoggedIn = driver.findElements(By.id("logoutButton")).size() > 0; // Replace with actual locator
            if (isLoggedIn) {
                System.out.println("Login successful for user: " + username);

                // Take post-login screenshot
                captureScreenshot(driver, "OPAC_LOGGED_IN_" + username);

                // Navigate to Personal Details and validate
                driver.findElement(By.id("personalDetailsLink")).click(); // Replace with actual locator
                Thread.sleep(2000);

                // Validate details against Excel
                String expectedFirstName = row.getCell(3).getStringCellValue(); // Column D: First Name
                String expectedLastName = row.getCell(4).getStringCellValue();  // Column E: Last Name

                WebElement displayedFirstName = driver.findElement(By.id("firstname")); // Replace with actual locator
                WebElement displayedLastName = driver.findElement(By.id("lastname"));   // Replace with actual locator

                if (displayedFirstName.getText().equals(expectedFirstName) &&
                        displayedLastName.getText().equals(expectedLastName)) {
                    System.out.println("Personal details match for user: " + username);
                } else {
                    System.out.println("Mismatch in personal details for user: " + username);
                }
            } else {
                System.out.println("Login failed for user: " + username);
            }
        }

        // Close Excel and WebDriver
        workbook.close();
        fis.close();
        driver.quit();
    }

    // Helper method to capture screenshots
    private static void captureScreenshot(WebDriver driver, String fileNamePrefix) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File destination = new File(SCREENSHOT_DIR + fileNamePrefix + "_" + timestamp + ".png");
        screenshot.renameTo(destination);
    }
    
//    public void validateBookInOpac() {
//        try {
//            //Search for the book using the title from the createBook method
//            String bookTitle = "Practical stress management : a comprehensive workbook for managing change and promoting health / John A. Romas, Manoj Sharma.";
//            
//            opacDriver.findElement(By.id("searchBox")).sendKeys(bookTitle); // Replace with actual search field ID
//            opacDriver.findElement(By.id("searchButton")).click(); // Replace with actual search button ID
//            
//
//            // 3. Validate book details in OPAC UI
//            validateBookDetailsInOpac(bookTitle);
//
//            // 4. Switch to Staff Interface (Browser 1) and capture Contributor details
//            String expectedContributor = "John A. Romas, Manoj Sharma";  // Example contributor
//            validateContributorInStaffInterface(expectedContributor);
//
//            // 5. Capture screenshot in OPAC UI for the book
//            captureScreenshot(bookTitle);
//
//        } catch (Exception e) {
//            logger.logFail("Test case failed due to exception: " + e.getMessage());
//        }
//    }
//    static WebDriver opacDriver = actions.getWebDriver();
//    public void searchBookInOpac(String bookTitle) {
//         // Assuming Browser 2 is assigned
//    	
//    }
//
//    // 3. Validate Book Details in OPAC UI
//    public void validateBookDetailsInOpac(String expectedTitle) {
//        WebDriver opacDriver = actions.getWebDriver(); // Assuming Browser 2 is assigned
//        WebElement bookTitleElement = opacDriver.findElement(By.xpath("//div[@class='bookTitle']")); // Replace with actual XPath for title
//        String actualTitle = bookTitleElement.getText();
//
//        if (expectedTitle.equals(actualTitle)) {
//            logger.logPass("Book title validated successfully in OPAC: " + actualTitle);
//        } else {
//            logger.logFail("Validation failed. Expected: " + expectedTitle + ", Found: " + actualTitle);
//        }
//    }
//
//    // 4. Switch to Staff Interface and Capture Contributor(s) Details
//    public void validateContributorInStaffInterface(String expectedContributor) {
//        WebDriver staffDriver = actions.getWebDriver(); // Assuming Browser 1 is assigned
//        staffDriver.get("STAFF_INTERFACE_URL"); // Replace with actual Staff interface URL
//
//        // Assuming you're already logged into the Staff Interface, locate the Contributor(s) field
//        WebElement contributorElement = staffDriver.findElement(By.xpath("//div[@id='contributors']")); // Replace with actual XPath for contributor field
//        String actualContributor = contributorElement.getText();
//
//        if (expectedContributor.equals(actualContributor)) {
//            logger.logPass("Contributor(s) validated successfully: " + actualContributor);
//        } else {
//            logger.logFail("Validation failed. Expected: " + expectedContributor + ", Found: " + actualContributor);
//        }
//    }
//
//    // 5. Capture Screenshot of the Book in OPAC UI
//    public void captureScreenshot(String bookTitle) {
//        WebDriver opacDriver = actions.getWebDriver(); // Assuming Browser 2 is assigned
//
//        // Format the filename as 'book-name-opac-YYYYMMDD-HHMMSS.png'
//        String fileName = bookTitle.replaceAll(" ", "-").toLowerCase() + "-opac-" + getCurrentDateTime() + ".png";
//
//        // Capture screenshot
//        File screenshot = ((TakesScreenshot) opacDriver).getScreenshotAs(OutputType.FILE);
//        try {
//            File destination = new File("screenshots/" + fileName);
//            FileUtils.copyFile(screenshot, destination);
//            logger.logInfo("Screenshot saved as: " + fileName);
//        } catch (IOException e) {
//            logger.logFail("Failed to capture screenshot: " + e.getMessage());
//        }
//    }
//
//    // Helper method to get current date-time in the required format
//    private String getCurrentDateTime() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
//        return sdf.format(new Date());
//    }


}
