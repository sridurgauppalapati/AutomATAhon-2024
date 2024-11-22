package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import configuration.BrowserConfig;

public class Reporting extends BrowserConfig {

	private static FileWriter writer;
	private static File HtmlDirectory;
	protected static String extendReportPath;
	static Timestamp ts = new Timestamp(System.currentTimeMillis());
	private static String Reporttimestamp = "_" + ts.getTime();
	public static Logger l4jlogger = Logger.getLogger("AgilityLoyalty");

	public Reporting() {
		PropertyConfigurator.configure("Log4j.properties");
	}

	// Extent
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public static ExtentTest test;

	public void beforeTestReporting() {
		CreateHtmlReportFile();
		writeBasicTemplate();
	}

	public void beforeTestExtentReport() {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			System.getenv("USERNAME");
			String d = System.getProperty("os.name");
			String username = System.getProperty("user.name");
			String Reportpath = System.getProperty("user.dir") + "\\Reports";
			File fileReport = new File(Reportpath);
			if (!fileReport.exists())
				fileReport.mkdir();
			extendReportPath = System.getProperty("user.dir") + "\\Reports\\Reports" + Reporttimestamp;
			File file = new File(extendReportPath);
			if (!file.exists()) {
				file.mkdir();
			}
			File file1 = new File(extendReportPath + "\\ExtentReport");
			if (!file1.exists()) {
				file1.mkdir();
			}
			htmlReporter = new ExtentHtmlReporter(
					extendReportPath + "\\ExtentReport\\ExtentReport" + Reporttimestamp + ".html");
			htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/Reporting.xml"));
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("OS", d);
			extent.setSystemInfo("Host Name", inetAddress.getHostName());
			extent.setSystemInfo("IP Address", inetAddress.getHostAddress());
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User Name", username);
		} catch (Exception e) {
			l4jlogger.error("Failed to add extent report due to exception " + e.getMessage());
		}
	}

	public void logBatchResult(String DBColumnName, String DBValue, String InputColumnName, String InputValue,
			String POrF) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			String result;
			String dbCol = "<td><p>" + DBColumnName + "</p></td>";
			String dbValue = "<td><p>" + DBValue + "</p></td>";
			String inputCol = "<td><p>" + InputColumnName + "</p></td>";
			String inputValue = "<td><p>" + InputValue + "</p></td>";
			if (POrF.contains("Pass"))
				result = "<td bgcolor=#00cc00>" + POrF + "</a></td>";
			else if (POrF.contains("Fail"))
				result = "<td bgcolor=#ff0000>" + POrF + "</a></td>";
			else
				result = "<td></td>";
			String logs = "<tr>" + dbCol + dbValue + inputCol + inputValue + result + "</tr>";
			writer.write(logs);
			// Extent
			// test.log(Status.FAIL, stepName,
			// MediaEntityBuilder.createScreenCaptureFromPath(userDirector1 +
			// ImageFileName).build());
			// Assert.assertTrue(false);
			// log4j
			// l4jlogger.info(stepName);
		} catch (IOException e) {
			l4jlogger.error("Failed due log in report due to exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * public void addExePropertiesIntoReporting() { try {
	 * writer.write("<div><div id=\"EnvironmentDetails\">"); writer.write("OS : ");
	 * writer.write("HostName : "); writer.write("IP Address : ");
	 * writer.write("Environment : "); writer.write("User Name : ");
	 * writer.write("</div>"); writer.write("<div id=\"ExecutionSummary\">");
	 * 
	 * writer.write("</div></div>"); writer.write(""); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 */

	public void afterMethodExtentReport(ITestResult result) {
		/*
		 * if (result.getStatus() == ITestResult.FAILURE) { test.log(Status.FAIL,
		 * MarkupHelper.createLabel( result.getMethod().getMethodName() +
		 * " Test case FAILED due to below issues:", ExtentColor.RED));
		 * 
		 * } else if (result.getStatus() == ITestResult.SUCCESS) { test.log(Status.PASS,
		 * MarkupHelper.createLabel(result.getName() + " Test Case PASSED",
		 * ExtentColor.GREEN));
		 * 
		 * } else if (result.getStatus() == ITestResult.SKIP) { test.log(Status.SKIP,
		 * MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED",
		 * ExtentColor.ORANGE)); test.skip(result.getThrowable()); } else {
		 * test.error(MarkupHelper.createLabel(result.getName() + " Test Case ERROR",
		 * ExtentColor.PURPLE)); }
		 */
		try {
			writer.flush();
			extent.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void afterTestExtentReport() {
		extent.flush();
	}

	public void methodLevelReporting(Method methodName) {
		try {
			System.out.println("Method name : " + methodName.getName());
			// writeMethodLevelTemplate(methodName.getName());
			writeBatchTemplate(methodName.getName());
		} catch (Exception e) {
			l4jlogger.error("Failed while reporting due to exception " + e.getMessage());
		}
	}

	public void beforeMethodExtentReport(Method methodName) {
		test = extent.createTest(methodName.getName(), "Sample execution");
	}

	public void closeReporting() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			l4jlogger.error("Failed to close the writer due to exception " + e.getMessage());
		}
	}

	public void CreateHtmlReportFile() {
		try {
			String name = "ExecutionReport";
			//Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			HtmlDirectory = new File(String.valueOf("./test-output/HtmlReport"));
			if (!HtmlDirectory.exists())
				HtmlDirectory.mkdir();
			createHtmlFile(name + new DataGeneration().generateRandomNumber("12"));
			//System.out.println(name + new DataGeneration().generateRandomNumber("12"));
		} catch (Exception e) {
			l4jlogger.error("Failed to create a HTML report due to exception " + e.getMessage());
		}
	}

	public void createHtmlFile(String fileName) {
		try {
			File file = new File(System.getProperty("user.dir") + "/test-output/HtmlReport/" + fileName + ".html");
			writer = new FileWriter(file);
		} catch (Exception e) {
			l4jlogger.error("Failed to create testcase file due to exception " + e.getMessage());
		}
	}

	public void writeBasicTemplate() {
		try {
			writer.write("<html><head><style>\n");
			writer.write(styleCssTemplate());
			writer.write("</style></head>\n");
			writer.write("<body><table width=100%>");
			writer.write("<tr><center><img src=\""+System.getProperty("user.dir")+"\\src\\test\\resources\\logo.png\" alt=\"Logo Not Found\"></center></tr>\r\n");
			writer.write("<tr class=tc0><td><b>Step Name</b></td><td><b>Description</b></td><td><b>Result</b></td><td><b>Screenshot</b></td></tr></tr>\r\n");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			l4jlogger.error(e.getMessage());
		}
	}

	public String styleCssTemplate() {
		String str = "body {background-color: #ffffff;  border: 2px solid grey; border-radius: 5px}";
		str = str + "background-color: #ffffff;  border: 2px solid grey; border-radius: 5px}";
		str += "table {background-color: #d9d9d9; text-align: center;}";
		str += "th { background-color: #19b9e5;text-align: center;  color: #000000; font-family: Candara, Calibri;font-size: 20px;}";
		str += "th#Scenario { background-color: #cceeff;text-align: center;  color: #000000; font-family: Candara, Calibri;font-size: 20px;}";
		str += "tr { background-color: #E6E6E6; color: #000000;  font-family:Calibri; font-size: 15px;}";
		str += "p {background-color: #E6E6E6 ; color: #000000 ;}";
		str += "h1 { align=center; text-align: center;color: #000000; font-family:  Candara,Calibri;font-size: 28px; }";
		str += "h2 { align=center; text-align: center;background-color: #004994; color: #000000; font-family:  Candara,Calibri;font-size: 28px; }";
		str += "h3 {text-align=right;font-family: Candara, Calibri; font-size: 20px; }";
		str += "h4 {text-align=right;font-family: Candara, Calibri; font-size: 18px; }";
		str += "div#EnvironmentDetails{ border: 2px solid black; padding: 10px; width : 30%; float:left;}";
		str += "div#ExecutionSummary{ border: 2px solid black; padding: 10px; width : 30%; float:right;}";
		str += "td { text-align: center; height:40px;}";
		str += "tr.tc0 { text-align: center; background-color: #de1b6a; height:40px !important;}";
		return str;
	}

	public String writeScriptForCollapse() {
		String script = "<script>var coll = document.getElementsByClassName(\"collapsible\");var i;";
		script += "for (i = 0; i < coll.length; i++) {";
		script += "coll[i].addEventListener(\"click\", function() {";
		script += "this.classList.toggle(\"active\");";
		script += "var content = this.nextElementSibling;";
		script += "if (content.style.display === \"block\") {";
		script += "content.style.display = \"none\";} else {";
		script += "content.style.display = \"block\";}});}</script>";
		return script;
	}

	/*
	 * public void writeMethodLevelTemplate(String MethodName) { try {
	 * writer.write("<tr><th colspan=100 width=10%>" + MethodName +
	 * "</th></tr>\r\n"); writer.write(
	 * "<tr class=tc0><td width=15%><b>Time(s)</b></td><td><b>Step</b></td><td width=10%><b>Result</b></td></tr></tr>\r\n"
	 * ); } catch (IOException e) { System.out.println("Failed due to " +
	 * e.getMessage()); // TODO Auto-generated catch block
	 * l4jlogger.error(e.getMessage()); } }
	 */
	public void writeBatchTemplate(String MethodName) {
		try {
			writer.write("<tr><th colspan=100 width=10%>" + MethodName + "</th></tr>\r\n");
			writer.flush();
			} catch (IOException e) {
			System.out.println("Failed due to " + e.getMessage());
			// TODO Auto-generated catch block
			l4jlogger.error(e.getMessage());
		}
	}

	public void writeMethodName(String MethodName) {
		logInfo("<font style=\"font-weight:bold\" color=\"Meganta\" size=\"3\">" + MethodName + "</font>");
	}

	public void logInfo(String stepName) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			// String userDirector1 = "..\\screenshots\\";
			/*
			 * // String userDirector = System.getProperty("user.dir") + //
			 * "/test-output/screenshots/"; String ImageFileName = takeScreenshot(); endTime
			 * = System.currentTimeMillis(); NumberFormat formatter = new
			 * DecimalFormat("#0.0"); String timeInSecs = formatter.format((endTime -
			 * startTime) / 1000d); startTime = endTime; String time = "<td><p>" +
			 * timeInSecs + "</p></td>"; String step = "<td><p><a href=" + userDirector1 +
			 * ImageFileName + ">" + stepName + "</p></td>"; String result =
			 * "<td bgcolor=#ff0000>" + "Fail" + "</a></td>"; String logs = "<tr>" + time +
			 * step + result + "</tr>"; writer.write(logs);
			 */
			// Extent
			test.log(Status.INFO, stepName);
			// Assert.assertTrue(false);
			// log4j
			// l4jlogger.info(stepName);
		} catch (Exception e) {
			l4jlogger.error("Failed due log in report due to exception " + e.getMessage());
			e.printStackTrace();
		}
	}


	
//	public void logFail(String stepName, String Screenshot) {
//		System.setProperty("org.uncommons.reportng.escape-output", "false");
//		try {
//			String userDirector1 = "..\\screenshots\\";
//			// Time calculation
//			endTime = System.currentTimeMillis();
//			NumberFormat formatter = new DecimalFormat("#0.0");
//			String timeInSecs = formatter.format((endTime - startTime) / 1000d);
//			startTime = endTime;
//			String time = "<td><p>" + timeInSecs + "</p></td>";
//			String step = "<td><p>" + stepName + "</p></td>";
//			if (Screenshot.contains("Y")) {
//				String ImageFileName1 = takeScreenshot();
//				step = "<td><p><a href=" + userDirector1 + ImageFileName1 + ">" + stepName + "</p></td>";
//				String screensht =  "<td><p>" + ImageFileName1 + "</p></td>";
//			}	
//			String result = "<td bgcolor=#FF0000>" + "Fail" + "</a></td>";
//			String logs = "<tr>" + time + step + result + "</tr>";
//			writer.write(logs);
//			// Assert.assertTrue(true);
//			// log4j
//			l4jlogger.error(stepName);
//		} catch (Exception e1) {
//			l4jlogger.error("Failed to log fail in report" + e1.getMessage());
//		}
//	}   

//	public void logPass(String stepName, String Screenshot) {
//		System.setProperty("org.uncommons.reportng.escape-output", "false");
//		try {
//			String userDirector1 = "..\\screenshots\\";
//			// Time calculation
//			endTime = System.currentTimeMillis();
//			NumberFormat formatter = new DecimalFormat("#0.0");
//			String timeInSecs = formatter.format((endTime - startTime) / 1000d);
//			startTime = endTime;
//			String time = "<td><p>" + timeInSecs + "</p></td>";
//			String step = "<td><p>" + stepName + "</p></td>";
//			if (Screenshot.contains("Y")) {
//				String ImageFileName1 = takeScreenshot();
//				step = "<td><p><a href=" + userDirector1 + ImageFileName1 + ">" + stepName + "</p></td>";
//			}
//			String result = "<td bgcolor=#00cc00>" + "Pass" + "</a></td>";
//			String logs = "<tr>" + time + step + result + "</tr>";
//			writer.write(logs);
//			// Extent
//			if (Screenshot.contains("Y")) {
//				String ImageFileName = takeScreenshot();
//				test.log(Status.PASS, stepName,
//						MediaEntityBuilder.createScreenCaptureFromPath(userDirector1 + ImageFileName).build());
//			} else
//				test.log(Status.PASS, stepName);
//			// Assert.assertTrue(true);
//			// log4j
//			l4jlogger.info(stepName);
//		} catch (IOException e1) {
//			l4jlogger.error("Failed to log in report" + e1.getMessage());
//		}
//	}
	
	public void logFail(String stepName) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			String userDirector1 = "..\\screenshots\\";
			// String userDirector = System.getProperty("user.dir") +
			// "/test-output/screenshots/";
			String ImageFileName = takeScreenshot();
			endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.0");
			String timeInSecs = formatter.format((endTime - startTime) / 1000d);
			startTime = endTime;
			//String time = "<td><p>" + timeInSecs + "</p></td>";
			String step = "<td><p>" + stepName + "</p></td>";
			String step1 = "<td><p>" + "" + "</p></td>";
			//String step2 = "<td><p>" + "" + "</p></td>";
			String result = "<td bgcolor=#ff0000>" + "Fail" + "</a></td>";
			String scrshot = "<td><a href=" + userDirector1 + ImageFileName + "><img src=" + userDirector1 + ImageFileName + " height=\"80\"></img></a></td>";
			String logs = "<tr>" + step + step1 + result + scrshot + "</tr>";
			writer.write(logs);
			// Assert.assertTrue(false);
			// log4j
			l4jlogger.info(stepName);
		} catch (IOException e) {
			l4jlogger.error("Failed due log in report due to exception " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void logPass(String stepName1,String stepName2) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			String userDirector1 = "..\\screenshots\\";
			// Time calculation
			endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.0");
			String timeInSecs = formatter.format((endTime - startTime) / 1000d);
			startTime = endTime;
			String ImageFileName = takeScreenshot();
			//String time = "<td><p>" + timeInSecs + "</p></td>";
			String step1 = "<td><p>" + stepName1 + "</p></td>";
			String step2 = "<td><p>" + stepName2 + "</p></td>";
			//String step3 = "<td><p>" + stepName3 + "</p></td>";
			/*
			 * if (Screenshot.contains("Y")) { String ImageFileName1 = takeScreenshot();
			 * step = "<td><p><a href=" + userDirector1 + ImageFileName1 + ">" + stepName +
			 * "</p></td>"; }
			 */
			String result = "<td bgcolor=#00cc00>" + "Pass" + "</a></td>";
			String scrshot = "<td><a href=" + userDirector1 + ImageFileName + "><img src=" + userDirector1 + ImageFileName + " height=\"80\"></img></a></td>";
			//String step = "<td><p>" + "Here We Are" + "</p></td>";
			String logs = "<tr>" + step1 +step2 + result + scrshot +"</tr>";
			writer.write(logs);
			l4jlogger.info(stepName1);
			writer.flush();
		} catch (IOException e1) {
			l4jlogger.error("Failed to log in report" + e1.getMessage());
		}
	}

	public void logError(String stepName) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			String userDirector1 = "..\\screenshots\\";
			String ImageFileName = takeScreenshot();
			// Time calculation
			endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.0");
			String timeInSecs = formatter.format((endTime - startTime) / 1000d);
			startTime = endTime;
			String time = "<td><p>" + timeInSecs + "</p></td>";
			String step = "<td><p><a href=" + userDirector1 + ImageFileName + ">" + stepName + "</p></td>";
			String result = "<td bgcolor=#00cc00>" + "Pass" + "</a></td>";
			String logs = "<tr>" + time + step + result + "</tr>";
			writer.write(logs);
			// Extent
			test.log(Status.ERROR, stepName,
					MediaEntityBuilder.createScreenCaptureFromPath(userDirector1 + ImageFileName).build());
			// Assert.assertTrue(true);
			// log4j
			l4jlogger.error(stepName);
		} catch (IOException e1) {
			l4jlogger.error("Failed to log in report" + e1.getMessage());
		}
	}

	public void logSkip(String stepName) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			String userDirector1 = "..\\screenshots\\";
			String ImageFileName = takeScreenshot();
			// Time calculation
			endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.0");
			String timeInSecs = formatter.format((endTime - startTime) / 1000d);
			startTime = endTime;
			String time = "<td><p>" + timeInSecs + "</p></td>";
			String step = "<td><p><a href=" + userDirector1 + ImageFileName + ">" + stepName + "</p></td>";
			String result = "<td bgcolor=#00cc00>" + "Pass" + "</a></td>";
			String logs = "<tr>" + time + step + result + "</tr>";
			writer.write(logs);
			// Extent
			test.log(Status.SKIP, stepName,
					MediaEntityBuilder.createScreenCaptureFromPath(userDirector1 + ImageFileName).build());
			// Assert.assertTrue(true);
			// log4j
			l4jlogger.info(stepName);
		} catch (IOException e1) {
			l4jlogger.error("Failed to log in report" + e1.getMessage());
		}
	}

	public void logWarning(String stepName) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			String userDirector1 = "..\\screenshots\\";
			String ImageFileName = takeScreenshot();
			// Time calculation
			endTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.0");
			String timeInSecs = formatter.format((endTime - startTime) / 1000d);
			startTime = endTime;
			String time = "<td><p>" + timeInSecs + "</p></td>";
			String step = "<td><p><a href=" + userDirector1 + ImageFileName + ">" + stepName + "</p></td>";
			String result = "<td bgcolor=#00cc00>" + "Pass" + "</a></td>";
			String logs = "<tr>" + time + step + result + "</tr>";
			writer.write(logs);
			// Extent
			test.log(Status.WARNING, stepName,
					MediaEntityBuilder.createScreenCaptureFromPath(userDirector1 + ImageFileName).build());
			// Assert.assertTrue(true);
			// log4j
			l4jlogger.warn(stepName);
		} catch (IOException e1) {
			l4jlogger.error("Failed to log in report" + e1.getMessage());
		}
	}

	/*
	 * public void logPass(String stepName) { try { test.log(Status.PASS, stepName,
	 * MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build()); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 * 
	 * public void logFail(String stepName) { try { test.log(Status.FAIL, stepName,
	 * MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build()); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	public String takeScreenshot() {
		String ImageFileName = null;
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			ImageFileName = "Screenshot_" + timestamp.getTime() + ".jpeg";
			File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			File directory = new File(String.valueOf("./test-output/screenshots"));
			File directory1 = new File(String.valueOf("./Reports/Reports" + Reporttimestamp + "/screenshots"));
			if (!directory1.exists())
				directory1.mkdir();
			if (!directory.exists())
				directory.mkdir();
			String userDirector = System.getProperty("user.dir") + "/test-output/screenshots/";
			FileUtils.copyFile(scrFile, new File(userDirector + ImageFileName));
			FileUtils.copyFile(scrFile, new File(extendReportPath + "/screenshots/" + ImageFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			l4jlogger.error("Failed to take screen shot due to exception " + e.getMessage());
			e.printStackTrace();
		}
		return ImageFileName;
	}
}
