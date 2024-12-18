package testCases;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;

import java.time.Duration;

public class Step_IN_API {

    private WebDriver driver;
    private String baseUrl = "http://your-koha-instance";
    private String apiBaseUrl = "http://your-koha-api-instance";
    private String apiKey = "your-api-key";
    private String patronId;
    private String patronUsername = "john_smith";

  
       public void createPatronWithAPI() throws IOException {
        // Rest-Assured API request to create a patron
        RestAssured.baseURI = apiBaseUrl;

        String payload = new String(Files.readAllBytes(Paths.get("src/test/resources/patronPayload.json")));


        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/v1/patrons");

        Assert.assertEquals(response.getStatusCode(), 201, "Failed to create patron");
        patronId = response.jsonPath().getString("patron_id");
        Assert.assertNotNull(patronId, "Patron ID should not be null");
        System.out.println("Patron created successfully with ID: " + patronId);
    }

    @Test(priority = 2, dependsOnMethods = "createPatronWithAPI")
    public void validatePatronInUI() {
        // Open Staff Interface and search for the patron
        driver.get(baseUrl + "/staff");

        // Log in to staff interface
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin_password");
        driver.findElement(By.id("loginButton")).click();

        // Search for the patron
        WebElement searchBox = driver.findElement(By.id("search"));
        searchBox.sendKeys(patronUsername);
        searchBox.submit();

        // Validate the patron is visible in the table
        WebElement patronRow = driver.findElement(By.xpath("//table//tr[contains(., '" + patronUsername + "')]"));
        Assert.assertTrue(patronRow.isDisplayed(), "Patron should be visible in the search results");

        System.out.println("Patron validated successfully in the UI.");
    }

   
    public void assignPermissionInUI() {
        // Navigate to the patron details page
        driver.findElement(By.xpath("//table//tr[contains(., '" + patronUsername + "')]//a[text()='Details']")).click();

        // Set permission as superlibrarian
        driver.findElement(By.linkText("More")).click();
        driver.findElement(By.linkText("Set Permission")).click();

        WebElement permissionDropdown = driver.findElement(By.id("permissionsDropdown"));
        permissionDropdown.click();
        WebElement superLibrarianOption = driver.findElement(By.xpath("//option[@value='superlibrarian']"));
        superLibrarianOption.click();
        driver.findElement(By.id("savePermissionsButton")).click();

        // Validate the success message
        WebElement successMessage = driver.findElement(By.className("alert-success"));
        Assert.assertTrue(successMessage.isDisplayed(), "Permission should be assigned successfully");

        System.out.println("Permission assigned successfully.");
    }

}
