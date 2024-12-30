package koha;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.*;
//import org.json.simple.JSONObject;
import org.json.JSONObject;
import configuration.ExcelUtils;
import configuration.ReadPropertiesFile;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import io.restassured.module.jsv.JsonSchemaValidator;

public class PatronCreation {
	static Koha_start koha_start = new Koha_start();
	public static void PatronCreationOnGenerationMode() throws IOException{
		
        Workbook workbook = ExcelUtils.getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header

            Cell modeCell = row.getCell(0);
            String creationMode = modeCell.getStringCellValue();

            String libraryName = "FirstLib";
            String cardNumber = KohaRandonValueGenerator.generateCardNumber(libraryName);
            System.out.println(cardNumber);
            String firstName = KohaRandonValueGenerator.generateRandomName();
            System.out.println(firstName);
            String lastName = KohaRandonValueGenerator.generateRandomName();
            System.out.println(lastName);
            String email = KohaRandonValueGenerator.generateRandomEmail(firstName);
            System.out.println(email);

            switch (creationMode) {
//                case "OPAC UI":
//                    createPatronOpacUI(cardNumber, firstName, lastName, email);
//                    break;
//                case "Librarian UI":
//                    createPatronLibrarianUI(cardNumber, firstName, lastName, email);
//                    break;
                case "API":
                    createPatronAPI(cardNumber, firstName, lastName, email);
                    break;
            }

            // Update Excel file with the generated data
            row.createCell(1).setCellValue(cardNumber);
            row.createCell(2).setCellValue(firstName);
            row.createCell(3).setCellValue(lastName);
            row.createCell(4).setCellValue(email);
            row.createCell(5).setCellValue("Created");
        }

        ExcelUtils.saveWorkbook(workbook);
    }

    private static void createPatronOpacUI(String cardNumber, String firstName, String lastName, String email) throws IOException {
    	
    	koha_start.createPatronWithOPACUI(cardNumber, firstName, lastName, email);
    }

    private static void createPatronLibrarianUI(String cardNumber, String firstName, String lastName, String email) throws IOException {
        // Implement Selenium automation for Librarian UI
    	
    	koha_start.CreatePatronWithLibrarianUI(cardNumber, firstName, lastName, email);
    }

    private static void createPatronAPI(String cardNumber, String firstName, String lastName, String email) throws IOException {
    	ReadPropertiesFile propertyFile = new ReadPropertiesFile();
        // Implement API request with RestAssured
    	
		        // Rest-Assured API request to create a patron
		
			         // Base URI for Koha API
			         RestAssured.baseURI = propertyFile.readProperties("datafile","apiBaseUrl");
			         String username = propertyFile.readProperties("datafile","username");
			         String password = propertyFile.readProperties("datafile","password");

			         JSONObject jsonPayload = new JSONObject();
			         jsonPayload.put("cardnumber", cardNumber);
			         jsonPayload.put("firstname", firstName);
			         jsonPayload.put("surname", lastName);
			         jsonPayload.put("email", email);
			         jsonPayload.put("category_id", "123");
			         jsonPayload.put("library_id", "123");
			         

			         // Save the JSON payload to a new file
			         String payloadFilePath = "Utils/JsonFiles/APIPatronPayload.json";
			         try (FileWriter fileWriter = new FileWriter(payloadFilePath)) {
			             fileWriter.write(jsonPayload.toString(4)); // Pretty print with 4 spaces
			             System.out.println("Payload file successfully created/overwritten: " + payloadFilePath);
			         } catch (IOException e) {
			             System.err.println("Error writing payload file: " + e.getMessage());
			             throw e; // Rethrow to handle it in the calling code
			         }
			         
			         String requestBody = new String(Files.readAllBytes(Paths.get(payloadFilePath)));

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

			         // Validate status code
			         assert response.getStatusCode() >= 200 && response.getStatusCode() < 300;

			         // Validate response time
			         assert response.getTime() < 2000;

			         // Validate schema (add schema validation code here)
			         // Validate response status code
			         if (response.getStatusCode() == 200) {
			             // Perform schema validation
			             File schemaFile = new File("Utils/JsonFiles/patronPayload.json");
			             response.then()
			                     .assertThat()
			                     .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
			             System.out.println("Schema validation passed.");
			         } else {
			             System.out.println("Request failed with status code: " + response.getStatusCode());
			         }    
    }
}

