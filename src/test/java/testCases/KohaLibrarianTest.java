package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KohaLibrarianTest {

    private static final String BASE_URL = "<KOHA_API_BASE_URL>"; // Replace with actual Koha API base URL
    private static final String API_KEY = "<API_KEY>"; // Replace with your API key

    @DataProvider(name = "patronData")
    public Object[][] patronDataProvider() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("patronData.json"); // JSON file with patron data
        Map<String, Map<String, String>> data = objectMapper.readValue(file, Map.class);
        return data.entrySet().stream().map(entry -> new Object[]{entry.getKey(), entry.getValue()}).toArray(Object[][]::new);
    }

    @Test(dataProvider = "patronData")
    public void testCreateLibrarian(String patronId, Map<String, String> patronDetails) throws IOException {
        // Step 1: Add a patron of type "Staff"
        RequestSpecification request = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .body(patronDetails);

        Response response = request.post("/patrons");

        Assert.assertEquals(response.getStatusCode(), 201, "Failed to create patron.");

        // Extract the username from the response
        String username = response.jsonPath().getString("username");
        Assert.assertNotNull(username, "Username should not be null.");

        // Step 2: Create a password for the patron
        String patronIdCreated = response.jsonPath().getString("id");

        Map<String, String> passwordPayload = Map.of(
                "password", patronDetails.get("password"),
                "password_repeat", patronDetails.get("password")
        );

        Response passwordResponse = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .body(passwordPayload)
                .post("/patrons/" + patronIdCreated + "/password");

        Assert.assertEquals(passwordResponse.getStatusCode(), 200, "Failed to set password for the patron.");

        // Step 3: Update the file with the generated username
        patronDetails.put("username", username);
        updatePatronDataFile(patronId, patronDetails);
    }

    private void updatePatronDataFile(String patronId, Map<String, String> updatedDetails) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("patronData.json");

        // Read the current data from the file
        Map<String, Map<String, String>> data = objectMapper.readValue(file, Map.class);

        // Update the specific patron's details
        data.put(patronId, updatedDetails);

        // Write the updated data back to the file
        try (FileWriter writer = new FileWriter(file)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, data);
        }
    }
}
