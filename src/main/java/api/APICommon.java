package api;


import static com.jayway.restassured.RestAssured.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBodyData;

import configuration.Keywords;
import utilities.DataGeneration;
import utilities.Excel;
import utilities.Reporting;


public class APICommon {

	public static DataGeneration dataGenerate = new DataGeneration();
	public Reporting logger = new Reporting();
	private String API_User ="";
	private String API_Password = "";
	private String API_Url = "";
	private Keywords actions = new Keywords();
	private String API_LocalHost = "";
	private String API_Port = "";

	public String getAccessToken() {
		String accessToken = null;
		try {
			RestAssured.proxy(API_LocalHost, Integer.parseInt(API_Port));
			String body = "grant_type=password&username=" + API_User + "&password=" + API_Password
					+ "&response_type=token";
			Response response = RestAssured.given().header("Content-Type", "application/x-www-form-urlencoded")
					.header("Accept-Language", "en-US").header("Authorization", "Basic Token")
					.body(body).when().post(new URL("end point uri"));
			JsonPath jsonPathEvaluator = response.jsonPath();
			accessToken = jsonPathEvaluator.get("AccessToken");
			System.out.println("Access Token received from Response " + accessToken);
		} catch (Exception e) {
			System.out.println(e);
		}
		return accessToken;
	}

	public Headers getHeaders() {
		Map<String, String> apiHeaders = new HashMap<String, String>() ;
		//String accessToken = getAccessToken();
		Headers header = new Headers();
		String ContentType = "application/json";
		//String Accept = "application/json";
		//String ProgramCode = "Program-Code";
		try {
			apiHeaders.put("Content-Type", ContentType);
			//apiHeaders.put("Accept", Accept);
			//apiHeaders.put("Program-Code", ProgramCode);

			List<Header> headerList = new ArrayList<Header>();
			for(String key : apiHeaders.keySet()) {
		}
			//headerList.add(new Header("Authorization","OAuth "+accessToken));
			header = new Headers(headerList);
		}
		catch (Exception e){
			logger.logFail("Could get headers due to exception "+e.getMessage());
		}
		return header;
	}

	public String readJsonFile(String FilePath) {
		String jsonRead = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(FilePath));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			jsonRead = sb.toString();
			br.close();
		}catch(Exception e) {
			logger.logFail("Failed to read Json from given file due to exception "+e.getMessage());
		}
		return jsonRead;
	}

	public String refactorWithRandomizedValues(String json) {
		try {
			Matcher m = Pattern.compile("\\{\\{(.+?)\\}\\}").matcher(json); //"\\{{(.*?)\\}}"
			while(m.find()) {
				String givenVariable = (m.group(1));
				String randomValue = dataGenerate.randomDataGenerator(givenVariable, actions.getMethodName());
				json=json.replace("{{"+givenVariable+"}}", randomValue);  
			}
		}catch(Exception e) {
			logger.logFail("Failed to form Json with Randomized values due to exception "+e.getMessage());
		}
		return json;
	}

	public String API_Post(File file, String Url) {
		//Headers apiheader =  getHeaders();
		Response response=null;
		String sURL = refactorWithRandomizedValues(Url);
		//String jsonBodyGiven = refactorWithRandomizedValues(jsonBody).trim();
		try {			
			response = (Response)given().multiPart("file",file)
			           .expect().when()
			           .post(sURL);	
			String getStatusCode = String.valueOf(response.getStatusCode());
			//verifyStatusCode(statusCode, getStatusCode, response.asString());
			//dataGenerate.writeResponse(Key, response.asString());
			//writeResponseToData(response, "Post");
		} catch (Exception e) {
			logger.logFail("Failed to Post due to exception " + e.getMessage());
		}
		return response.asString();
	}
	
	public String API_Get(String Url) {
		Headers apiheader =  getHeaders();
		Response response = null;
		String sURL = refactorWithRandomizedValues(Url);
		//String jsonBodyGiven = refactorWithRandomizedValues(jsonBody).trim();
		try {
			 response = (Response) given().headers(apiheader).when().get(new URL(sURL));
			String getStatusCode = String.valueOf(response.getStatusCode());
			//verifyStatusCode(statusCode, getStatusCode, response.asString());
			//dataGenerate.writeResponse(Key, response.asString());
			dataGenerate.writeApiData("getCall", "VideoName", response.asString());
		} catch (Exception e) {
			logger.logFail("Failed to Post due to exception " + e.getMessage());
		}
		return response.asString(
				);
	}
	
	public String API_GetResponse(String Url) {
		Headers apiheader =  getHeaders();
		Response response = null;
		String sURL = refactorWithRandomizedValues(Url);
		//String jsonBodyGiven = refactorWithRandomizedValues(jsonBody).trim();
		try {
			 response = (Response) given().headers(apiheader).when().get(new URL(sURL));
			String getStatusCode = String.valueOf(response.getStatusCode());
			//verifyStatusCode(statusCode, getStatusCode, response.asString());
			//dataGenerate.writeResponse(Key, response.asString());
			dataGenerate.writeApiData("getCall", "VideoName", response.asString());
		} catch (Exception e) {
			logger.logFail("Failed to Post due to exception " + e.getMessage());
		}
		return response.asString();
	}

	public void verifyStatusCode(String ExpectedStatusCode, String ActualStatusCode, String responseString) {
		try {
			if((ExpectedStatusCode.equals("N")||ExpectedStatusCode.equals(null))&&(ActualStatusCode.equals("200")||ActualStatusCode.equals("201"))) {
				logger.logPass("Status Code : "+ActualStatusCode +" and Obtained response : <br>"+ responseString, "N");
			}else if(ExpectedStatusCode.equals(ActualStatusCode)) {
				logger.logPass("Status Code : "+ActualStatusCode +" and Obtained response : <br>"+ responseString, "N");
			}else if(ExpectedStatusCode.equals("N")||ExpectedStatusCode.equals("Y")){
				//logger.logFail("Expected Status : 200/201 Actual Status : "+ActualStatusCode +" <br> Obtained response : "+ responseString, "N");
			}
		}catch(Exception e) {
			logger.logFail("Failed to verify Status Code due tp exception "+e.getMessage());
		}
	}

	/*public void API_Put(String dataKey, String statusCode) {
		String jsonBody = "";
		// Data key 
		String Key = dataKey;
		if(dataKey.contains(".")) {
			Key = dataKey.split("\\.")[1];
		}
		logger.logInfo("API Post"+ " "+Key+ " "+"Method");
		Headers apiheader =  getHeaders();
		String sURL = refactorWithRandomizedValues(Excel.getApiHeaderData(dataKey,"URL"));
		String jsonBodyGiven = Excel.getApiHeaderData(dataKey, "JsonBody").trim();
		try {
			if(jsonBodyGiven!=null&&(!jsonBodyGiven.equals(""))) {
				jsonBody = refactorWithRandomizedValues(jsonBodyGiven);
			}else {
				logger.logFail("Empty value in Json Body. Please verify");
			}
			logger.logPass("Created Json Body is : '"+jsonBody+"'", "N");
			Response response = (Response) given().headers(apiheader).body(jsonBody).when().put(new URL(API_Url+"/"+sURL));
			String getStatusCode = String.valueOf(response.getStatusCode());
			verifyStatusCode(statusCode, getStatusCode, response.asString());
			//dataGenerate.writeResponse(Key, response.asString());
			writeResponseToData(response, Key);
		} catch (Exception e) {
			logger.logFail("Failed to Put due to exception " + e.getMessage());
		}
	}

	public void API_Get(String dataKey, String statusCode) {
		logger.logInfo("API Get Method for "+dataKey);
		// Data key 
		try {
			String Key = dataKey;
			if(dataKey.contains(".")) {
				Key = dataKey.split("\\.")[1];
			}
			Headers apiheader = getHeaders();
			String sURL = refactorWithRandomizedValues(Excel.getApiHeaderData(dataKey,"URL"));

			Response response = (Response) given().headers(apiheader).when().get(new URL(API_Url+"/"+sURL));
			String getStatusCode = String.valueOf(response.getStatusCode());
			verifyStatusCode(statusCode, getStatusCode, response.asString());
			//dataGenerate.writeResponse(Key, response.asString());
			writeResponseToData(response, Key);
		} catch (Exception e) {
			logger.logFail("Failed to Get " + e.getMessage());
		}
	}
	
	public void API_Delete(String dataKey, String statusCode) {
		logger.logInfo("API Get Method for "+dataKey);
		// Data key 
		try {
			String Key = dataKey;
			if(dataKey.contains(".")) {
				Key = dataKey.split("\\.")[1];
			}
			Headers apiheader = getHeaders();
			String sURL = refactorWithRandomizedValues(Excel.getApiHeaderData(dataKey,"URL"));
			Response response = (Response) given().headers(apiheader).when().delete(new URL(API_Url+"/"+sURL));
			String getStatusCode = String.valueOf(response.getStatusCode());
			verifyStatusCode(statusCode, getStatusCode, response.asString());
			//dataGenerate.writeResponse(Key, response.asString());
			writeResponseToData(response, Key);
		} catch (Exception e) {
			logger.logFail("Failed to Delete " + e.getMessage());
		}
	}

	public void API_GetAndVerify(String dataKey, String statusCode) {
		logger.logInfo("API Get and Verify Method for "+dataKey);
		String Key = dataKey;
		if(dataKey.contains(".")) {
			Key = dataKey.split("\\.")[1];
		}
		Headers apiheader = getHeaders();
		String sURL = refactorWithRandomizedValues(Excel.getApiHeaderData(dataKey,"URL"));
		try {
			Response response = (Response) given().headers(apiheader).when().get(new URL(API_Url+"/"+sURL));
			//dataGenerate.writeResponse(Key, response.asString());
			String getStatusCode = String.valueOf(response.getStatusCode());
			verifyStatusCode(statusCode, getStatusCode, response.asString());
			writeResponseToData(response, Key);
			verifyAttributes(dataKey,actions.getMethodName());
		} catch (Exception e) {
			logger.logFail("Failed to Get and Verify " + e.getMessage(),"N");
		}
	}

	public void API_Verify(String validation, String extraParam) {
		try {
			if(validation.contains("=")) {
				String[] validationAttributes = validation.split("=");
				String Actual = dataGenerate.randomDataGenerator(validationAttributes[1].trim(),actions.getMethodName());
				String Expected = dataGenerate.randomDataGenerator(validationAttributes[0].trim(),actions.getMethodName());
				if(Actual.equalsIgnoreCase("{{notpresent}}")) {
					if(Expected.equalsIgnoreCase("{{notpresent}}")) {
						logger.logPass(validationAttributes[0] +" is not present in the response","N");
					}else {
						logger.logFail(validationAttributes[0]+" is available in the response","N");
					}
				}else if(Expected.equalsIgnoreCase(Actual)){
					logger.logPass("Expected:" + Expected+" Actual:" + Actual,"N");
				}else {
					logger.logFail("Expected:" + Expected+" Actual:" + Actual,"N");
				}
			}else if(validation.contains("%")) {
				String[] validationAttributes = validation.split("%");
				String Actual = dataGenerate.randomDataGenerator(validationAttributes[1].trim(),actions.getMethodName());
				String Expected = dataGenerate.randomDataGenerator(validationAttributes[0].trim(),actions.getMethodName());
				if(Expected.contains(Actual)){
					logger.logPass("'"+Actual+"' is found in the expected","N");
				}else {
					logger.logFail("Failed to find '"+Actual+"' in expected","N");
				}
			}
		} catch (Exception e) {
			logger.logFail("API_Verify failed due to exception : " + e.getMessage(),"N");;
		}
	}
*/
	public Map<Object,Object> convertSimpleJsonToMap(String jsonStr) {
		String[] temp = new String[2];
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		String splitString1 = jsonStr.substring(1);
		String splitString2 = splitString1.replaceAll("[}]$", "");
		String[] splitString = splitString2.split(", ");
		try {
			for(String str : splitString) {
				if(str.contains("=")) {
					temp = str.split("\\="); 
					if(temp.length>1) {
						resultMap.put(temp[0], temp[1]);
					}
					temp = null;
				}
			}
		} catch (Exception e) {
			logger.logFail("Failed to convert json String to map due to exception "+e.getMessage());
		}
		return resultMap;
	}

	public void writeResponseToData(Response response, String methodName) {
		try {
			Map<Object,Object> responseMap = new HashMap<Object, Object>();
			JsonPath jsonPathEvaluator = response.jsonPath();
			Object jsonObject = jsonPathEvaluator.get().getClass();
			if(jsonObject.toString().equals("class java.util.ArrayList"))
			{
				ArrayList<Object> jsonList= jsonPathEvaluator.get();
				for(Object elist:jsonList) {
					responseMap = convertSimpleJsonToMap(elist.toString());;
					writeLoop(response, responseMap, methodName);
				}
			}else {
				responseMap = jsonPathEvaluator.get();
				writeLoop(response, responseMap, methodName);
			}
		}catch(Exception e) {
			logger.logFail("Failed to write response to data properties due to exception "+e.getMessage());
		}
	}

	public void writeLoop(Response response,Map<Object,Object> responseMap,String methodName) {
		try {
			for(Object key : responseMap.keySet()) {
				String responseKey = key.toString();
				String responseValue = responseMap.get(key).toString();
				if((responseValue.contains("{")||responseValue.contains("["))&&!responseValue.equals("[]")) {
					int childObjects = 0, tempFlag=0;
					Map<Object, Object> childNodes = new HashMap<Object, Object>();
					childNodes = response.jsonPath().getMap(responseKey+"["+childObjects+"]");
					childObjects +=1;
					while(tempFlag==0) {
						if(childNodes==null)
							childNodes = response.jsonPath().getMap(responseKey);
						for(Object childKey : childNodes.keySet()) {
							String child_responseKey = childKey.toString();
							String child_responseValue = childNodes.get(childKey).toString();

							if((child_responseValue.contains("{")||child_responseValue.contains("["))&&!child_responseValue.equals("[]")) {
								Map<Object, Object> subchildNodes = new HashMap<Object, Object>();
								subchildNodes = convertSimpleJsonToMap(child_responseValue);
								for(Object childKey1 : subchildNodes.keySet()) {
									String child_responseKey1 = childKey1.toString();
									String child_responseValue1 = subchildNodes.get(childKey1).toString();
									dataGenerate.writeApiData(methodName, child_responseKey1, child_responseValue1);
									dataGenerate.writeApiData(methodName, child_responseKey+child_responseKey1, child_responseValue1);
								}
							}else {
								dataGenerate.writeApiData(methodName, responseKey+child_responseKey, child_responseValue);
							}
						}
						try {
							childNodes = response.jsonPath().getMap(responseKey+"["+childObjects+"]");
							childObjects +=1;
							if(childNodes==null)
								tempFlag+=1;
						}catch(Exception ex) {
							tempFlag+=1;
						}
					}
				}else {
					dataGenerate.writeApiData(methodName, responseKey, responseValue);
				}
			}
		}catch(Exception e) {
			logger.logFail("Failed to write loop due to exception "+e.getMessage());
		}
	}

	public Map<Object, Object> getAllChildNodesToMap(Response response, String responseKey) {
		int childObjects = 0, tempFlag=0;
		Map<Object, Object> childNodes = new HashMap<Object, Object>();
		Map<Object, Object> tempMap = new HashMap<Object, Object>();
		do {
			try {
				tempMap = response.jsonPath().getMap(responseKey+"["+childObjects+"]");
				childObjects +=1;
				childNodes.putAll(tempMap);
			}catch(Exception ex) {
				tempFlag+=1;
			}
		}while(tempFlag==0);
		return childNodes;
	}

	public void VerifyValue(String dataKey, String SS) {
		try {

		}catch(Exception e) {
			logger.logFail("Failed to verify value due to exception "+e.getMessage());
		}
	}
}

	/*
	 * public void verifyAttributes(String dataKey, String methodName) { XSSFSheet
	 * ExcelWSheet; String SheetName = ""; Object[] header = null; Object[]
	 * dataForHeader = null; String Key = dataKey; if (dataKey.contains(".")) {
	 * SheetName = dataKey.split("\\.")[0]; Key = dataKey.split("\\.")[1];
	 * ExcelWSheet = Excel.getDataWorkBook().getSheet(SheetName); try {
	 * 
	 * for (CellRangeAddress range : ExcelWSheet.getMergedRegions()) { int
	 * firstRowValue = range.getFirstRow(); int firstColumn =
	 * range.getFirstColumn(); int lastRowValue = range.getLastRow(); if
	 * (firstColumn == 0 && Key.equalsIgnoreCase(Excel.getCellData(ExcelWSheet,
	 * firstRowValue, 0))) { int columnNumber = ExcelWSheet.getRow(firstRowValue +
	 * 2).getLastCellNum(); header =new Object[columnNumber]; dataForHeader =new
	 * Object[columnNumber]; { for(int j = firstColumn ,k=0; j < columnNumber; j++)
	 * { String attribute = null; String attributevalue = null; try {
	 * attribute=Excel.getCellData(ExcelWSheet, firstRowValue + 2, j).trim(); if
	 * (attribute != null && !attribute.equals("")) { attributevalue =
	 * Excel.getCellData(ExcelWSheet, firstRowValue + 3,j).trim();
	 * 
	 * header[k]=dataGenerate.randomDataGenerator("get("+Key+"."+attribute+")",
	 * methodName);
	 * dataForHeader[k]=dataGenerate.randomDataGenerator(attributevalue,
	 * methodName); k++; } } catch (Exception e) { logger.logFail(
	 * attribute+" Data is not present in data properties ", "N"); } }break; } } }
	 * Object [] headerList=actions.removeNullValues(header);
	 * Arrays.sort(headerList); Object []
	 * dataForHeaderList=actions.removeNullValues(dataForHeader);
	 * Arrays.sort(dataForHeaderList); if(Arrays.equals(headerList,
	 * dataForHeaderList)){
	 * logger.logPass("Verification passed Expected : "+Arrays.toString(headerList)
	 * +" Actual: "+Arrays.toString(dataForHeaderList), "N"); } else{
	 * logger.logFail("Verification failed Expected : "+Arrays.toString(headerList)
	 * +" Actual : "+Arrays.toString(dataForHeaderList), "N"); } }catch (Exception
	 * e) {
	 * logger.logFail("Failed to Verify attributes due to error "+e.getMessage()); }
	 * 
	 * } }
	 */