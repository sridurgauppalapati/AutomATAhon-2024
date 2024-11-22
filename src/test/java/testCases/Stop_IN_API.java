package testCases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.APICommon;

public class Stop_IN_API {

	APICommon api = new APICommon();

//	{"team":"team-name","video":"search-video-name","upcoming-videos":["A","B","C"]}

	@Test(priority = 0)
	public void RegistrationSuccessful() throws IOException
	{ 
		//String jsonBody = "{\"name\": \"{{RandomName}}\", \"job\": \"Programmer\"}";
//		api.API_Get("http://54.169.34.162:5252/video");
		
		String res = api.API_Get("https://fake-json-api.mock.beeceptor.com/users");
		
		System.out.println(res);
		System.out.println("====");
		
        URL url = new URL("https://fake-json123.free.beeceptor.com");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.connect();

        //Getting the response code
//        int responsecode = conn.getResponseCode();
        
//        System.out.println(responsecode);
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        
        conn.setDoOutput(true); // Enable sending request body
        
     // Define the POST body
        String jsonBody = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
        
        HashMap data = new HashMap();
        
        data.put("title","foo");
        data.put("body", "bar");
        
        ObjectMapper objectMapper = new ObjectMapper();
        String map_json = objectMapper.writeValueAsString(data);
        

        // Write the body to the output stream
        try (OutputStream os = conn.getOutputStream()) {
        	
            byte[] input = map_json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Read the response code
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
		
//		api.API_Post(null, 'https://fake-json123.free.beeceptor.com');
		
		System.out.println(res);
		JSONArray allDataArray = new JSONArray();
		
		String[] sList = {"A","B","C"};
		
		String json = "{\"team\":\"team-name\","
				+ "\"video\":\"search-video-name\","
				+ "\"upcoming-videos\":[";
		   //if List not empty
		   if (!(sList.length ==0)) {
		       //Loop index size()
		       for(int index = 0; index < sList.length; index++) {
		           try {
		              json +="\""+ sList[index]+"\"";
		              if(index!=sList.length-1) {
		            	  json +=",";
		              }else {
		            	  json+="]}";
		              }
		           } catch (Exception e) {
		               e.printStackTrace();
		           }
		       }
		       System.out.println(json);
		   } else {
		       //Do something when sList is empty
		   }
	}
}
