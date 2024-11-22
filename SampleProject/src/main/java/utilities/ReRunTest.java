package utilities;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import configuration.BrowserConfig;

public class ReRunTest extends BrowserConfig{

	private static FileWriter filewriter;
	private static List<String> failedTestCase = new ArrayList<String>();
	public Reporting logger = new Reporting();

	public String createFailedXmlFile() {
		String rerunXmlFile = null;
		String[] failedTestCases = new String[failedTestCase.size()];
		failedTestCases = failedTestCase.toArray(failedTestCases);
		if(failedTestCases.length>0) {
			rerunXmlFile = System.getProperty("user.dir") + "\\src\\test\\resources\\failed.xml";
			try {
				File file = new File(rerunXmlFile);
				filewriter = new FileWriter(file);

				filewriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				filewriter.write("<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\n");
				filewriter.write("<suite name=\"TestNgMavenExampleSuite\" parallel=\"false\">\n");
				filewriter.write("<test name=\"TestNgMavenTest\">\n");
				filewriter.write("<classes>\n");
				filewriter.write("<class name=\"com.epsilon.testCases.ExecutableTest\">\n");
				filewriter.write("<methods>\n");
				writeIncludedMethods(failedTestCases);
				filewriter.write("</methods>\n");
				filewriter.write("</class>\n");
				filewriter.write("</classes>\n");
				filewriter.write("</test>\n");
				filewriter.write("</suite>\n");
				filewriter.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return rerunXmlFile;
	}

	public void writeIncludedMethods(String[] failedTestcases) {
		try {
			for(int i=0; i<failedTestcases.length; i++) {
				filewriter.write("<include name=\""+failedTestcases[i]+"\"></include>\n");
			}
		}catch (Exception e) {

		}
	}

	public void captureFailedTestCase(int failedFlag, String methodName) {
		try {
			if(failedFlag>0) {
				failedTestCase.add(methodName);
			}
		}catch(Exception e) {
			logger.logFail("Failed to capture Failed testcase due to exception "+e.getMessage());
		}
	}

	public void printFailedTestCases() {
		String[] failedTestCases = new String[failedTestCase.size()];
		failedTestCases = failedTestCase.toArray(failedTestCases);
	}
}