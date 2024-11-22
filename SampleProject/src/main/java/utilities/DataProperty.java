package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class DataProperty{

	

	public void writeData(String dataKey, String dataValue) {
		try {
			File file = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\dataFile.properties");
			FileWriter writer = new FileWriter(file,true);
			writer.write("\n"+dataKey+" = "+dataValue);
			writer.close();
		}catch(Exception e) {
			//logger.logFail("Failed to create data into file due to exception "+e.getMessage());
			System.out.println("Failed to create data into file due to exception "+e.getMessage());
		}
	}

	/**
	 * It gets all the value from Properties file, The key starts with the provided Data Parameter
	 * @param ValueToGet 
	 * @return
	 */
	public Map<Object, Object> getToMap(String prefixToGet){
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		Properties prop = new Properties();
		try {
			InputStream input =  new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\dataFile.properties");
			prop.load(input);
			// get the property value and print it out
			Object[] keySet = prop.keySet().toArray();
			for (int i = 0; i < keySet.length; i++) {
				String keyName = keySet[i].toString();
				String valueName = prop.getProperty((String) keySet[i]);
				if(keyName.startsWith(prefixToGet)) {
					String[] removePrefix = keyName.split("\\.");
					if(removePrefix.length>1) {
						String removePrefixValue = removePrefix[1]; 
						if(!valueName.equals("")) {
							if(keyName.contains("date")&&valueName.contains("T")&&valueName.contains("Z")) {
								returnMap.put(removePrefixValue, valueName.split("T")[0]);
							}else {
								returnMap.put(removePrefixValue, valueName);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnMap;

	}

	public void cleanData() {
		try {
			File file = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\dataFile.properties");
			FileWriter writer = new FileWriter(file);
			writer.write("");
			writer.close();
		} catch (Exception e) {
			//logger.logFail("Failed to clean data in dataFile.properties due to exception "+e.getMessage());
			System.out.println("Failed to clean data in dataFile.properties due to exception "+e.getMessage());
		}
	}
}