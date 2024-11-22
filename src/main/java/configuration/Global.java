package configuration;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import utilities.Reporting;
import utilities.UI_Locators;

public class Global {
	protected Keywords actions = new Keywords();
	protected Reporting logger = new Reporting();
	protected BrowserConfig config = new BrowserConfig();

	@BeforeTest
	public void beforeTest() {
		logger.beforeTestReporting();
		logger.beforeTestExtentReport();
	}

	@BeforeMethod
	public void beforeMethod(Method methodName) {
		logger.methodLevelReporting(methodName);
		//logger.beforeMethodExtentReport(methodName);
	//	actions.deleteDataPropFile();
	}

	@AfterMethod
	public void afterMethod(ITestResult result, Method methodName) {
		logger.afterMethodExtentReport(result);
	}

	@AfterTest
	public void afterTest() {
		logger.closeReporting();
		logger.afterTestExtentReport();
		actions.quitBrowser();
	}

}
