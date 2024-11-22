package configuration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Properties;

import utilities.Reporting;

public class ReadPropertiesFile {
	private Reporting logger = new Reporting();
	//public static LinkedHashMap<String, String> readMap= new LinkedHashMap<String, String>();

	public LinkedHashMap<String, String> getRunPropertiesFile() {
		Properties prop = new Properties();
		InputStream input = null;
		LinkedHashMap<String, String> readMap= new LinkedHashMap<String, String>();
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "/Run.properties");
			// load a properties file
			prop.load(input);
			// get the property value and print it out

			Object[] keySet = prop.keySet().toArray();

			for (int i = 0; i < keySet.length; i++) {
				readMap.put((String) keySet[i], prop.getProperty((String) keySet[i]));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return readMap;
	}

	public LinkedHashMap<String, String> getDataPropertiesFile() {
		Properties prop = new Properties();
		InputStream input = null;
		LinkedHashMap<String, String> readMap= new LinkedHashMap<String, String>();
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\dataFile.properties");
			// load a properties file
			prop.load(input);
			// get the property value and print it out

			Object[] keySet = prop.keySet().toArray();

			for (int i = 0; i < keySet.length; i++) {
				readMap.put((String) keySet[i], prop.getProperty((String) keySet[i]));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return readMap;
	}

	public LinkedHashMap<String, String> getPropertiesFile(String fileName) {
		Properties prop = new Properties();
		InputStream input = null;
		LinkedHashMap<String, String> readMap= new LinkedHashMap<String, String>();
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "\\Utils\\Properties\\"+fileName+".properties");
			// load a properties file
			prop.load(input);
			// get the property value and print it out

			Object[] keySet = prop.keySet().toArray();

			for (int i = 0; i < keySet.length; i++) {
				readMap.put((String) keySet[i], prop.getProperty((String) keySet[i]));
			}

		} catch (Exception e) {
			logger.logFail("File "+fileName+" not found due to exception "+e.getMessage());
		}
		return readMap;
	}

	public String readRunProperties(String skey){
		LinkedHashMap<String, String> readMap= getRunPropertiesFile();
		String keyValue =readMap.get(skey);
		return keyValue;
	}

	public String readDataProperties(String skey){
		String keyValue;
		try {
			LinkedHashMap<String, String> readMap= getDataPropertiesFile();
			keyValue =readMap.get(skey);
			if(keyValue==null) {
				keyValue = "{{notpresent}}";
			}
		}catch(Exception e) {
			keyValue = "{{notpresent}}";
		}
		return keyValue;

	}

	public String readProperties(String fileName,String skey){
		String keyValue;
		try {
			LinkedHashMap<String, String> readMap= getPropertiesFile(fileName);
			keyValue =readMap.get(skey);
		}catch(Exception e) {
			keyValue = "{{notpresent}}";
		}
		return keyValue;
	}
}
