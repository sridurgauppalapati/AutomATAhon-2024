package testCases;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import api.APICommon;

public class ApiTest {

	APICommon api = new APICommon();

	@Test(priority = 0)
	public void RegistrationSuccessful()
	{ 
		//String jsonBody = "{\"name\": \"{{RandomName}}\", \"job\": \"Programmer\"}";
//		api.API_Get("http://54.169.34.162:5252/video");
		
			api.API_Get("https://fake-json-api.mock.beeceptor.com/users");
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
