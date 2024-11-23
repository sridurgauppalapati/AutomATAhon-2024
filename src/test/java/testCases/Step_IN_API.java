package testCases;




import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Base64;

import org.testng.annotations.Test;


public class Step_IN_API {
              private static final String API_URL = "http://34.148.101.249:8080/api/v1/";

              // Replace with your Koha API URL
    private static final String USERNAME = "Melinda.Bednar";
    private static final String PASSWORD = "ul6lng7qVi";

    @Test
    public static void main() {
        try {
            // Base64 encode the username and password
            String credentials = USERNAME + ":" + PASSWORD;
            String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            // Step 1: Add Patron of type "Staff"
            String patronId = addPatron(base64Credentials);

//            if (patronId != null) {
//                // Step 2: Create password for the patron
//                boolean passwordCreated = createPassword(patronId, "new_password123", base64Credentials);
//                if (passwordCreated) {
//                    System.out.println("Password created successfully for patron with ID " + patronId);
//                } else {
//                    System.out.println("Failed to create password for patron.");
//                }
//            } else {
//                System.out.println("Failed to add patron.");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Step 1: Add Patron of type "Staff"
    public static String addPatron(String base64Credentials) {
        String patronData = "{\"first_name\": \"John\", \"last_name\": \"Doe\", \"email\": \"johndoe@example.com\", \"type\": \"Staff\", \"library_id\": \"your_library_id\"}";

        Response response = given()
            .baseUri(API_URL)
            .header("Authorization", "Basic " + base64Credentials) // Basic Auth header
            .contentType("application/json")
            .body(patronData)
            .post();

        // Debug output
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

//        if (response.statusCode() == 201) {
//            // Patron added successfully
//            return response.jsonPath().getString("id"); // Assuming response contains the patron's ID
//        } else {
//            System.out.println("Error adding patron: " + response.jsonPath().getString("error"));
            return null;
//        }
    }

    // Step 2: Create Password for Patron
    public static boolean createPassword(String patronId, String password, String base64Credentials) {
        String passwordData = "{\"password\": \"" + password + "\"}";

        Response response = given()
            .baseUri(API_URL)
            .header("Authorization", "Basic " + base64Credentials) // Basic Auth header
            .contentType("application/json")
            .body(passwordData)
            .post("/staff/patrons/" + patronId + "/password");

        // Debug output
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
//
        return response.statusCode() == 200;
    }
}

