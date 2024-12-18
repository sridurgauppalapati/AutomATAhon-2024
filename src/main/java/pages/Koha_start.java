package pages;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import configuration.Keywords;
import configuration.ReadPropertiesFile;



import io.restassured.RestAssured;
import io.restassured.response.Response;
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
			actions.wait(10);
		}catch(Exception e) {
			logger.logFail("Failed to login due to exception "+e.getMessage());
		}
		
	}
//	public void create_library() {
//		try {
//			actions.click(klocator.admin);
//			actions.javaScriptClick(klocator.Library);
//			actions.javaScriptClick(klocator.add_lib);
//			actions.setValue(klocator.branchname,"TestVerse");
//			actions.setValue(klocator.branchcode, "GTRHACK"+"TV" );
//			actions.javaScriptClick(klocator.library_submit);
//
//		}catch(Exception e) {
//			e.printStackTrace();
//			logger.logFail("Failed to login due to exception "+e.getMessage());
//		}
//		
//	}
	
	
		 public void createPatronWithAPI() throws IOException {
		        // Rest-Assured API request to create a patron
		        RestAssured.baseURI = propertyFile.readProperties("datafile", "apiBaseUrl");
		        		
		        String payload = new String(Files.readAllBytes(Paths.get("C:/automation/SampleProject/Utils/patronPayload.json")));


		        Response response = RestAssured.given()
		                .header("Authorization", " Bearer" +  propertyFile.readProperties("datafile", "apiKey"))
		                .header("Content-Type", "application/json")
		                .body(payload)
		                .post("/api/v1/patrons");

		        Assert.assertEquals(response.getStatusCode(), 201, "Failed to create patron");
		        patronId = response.jsonPath().getString("patron_id");
		        Assert.assertNotNull(patronId, "Patron ID should not be null");
		        System.out.println("Patron created successfully with ID: " + patronId);
		    }


}
