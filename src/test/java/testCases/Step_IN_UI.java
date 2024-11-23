package testCases;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import api.APICommon;
import configuration.Global;
import configuration.Keywords;
import pages.CommonMethods;
import pages.LoginPage;
import utilities.UI_Locators;
import utilities.Koha_locator;
import utilities.Locators;

public class Step_IN_UI extends Global {
	// public BrowserConfig config = new BrowserConfig();
	public CommonMethods common = new CommonMethods();
	public Keywords actions = new Keywords();
	public UI_Locators ulocator = new UI_Locators();
	public APICommon api = new APICommon();
	public LoginPage login = new LoginPage();
	public Koha_locator klocator = new Koha_locator();
	public Locators loc = new Locators();

	@Test(priority = 0)
	public void TC_UI_Launch_IndianExpress() {
		common.Launch("UI", "http://34.148.101.249:8081/");
		logger.logPass("Launch", "Step In Forum Launched");
		login.Login("Melinda.Bednar", "ul6lng7qVi");
		
	}

	@Test(priority = 1)
	public void TC_UI_CreateLibrary() {
		login.create_library();
	login.create_Patrons();
		
	
	}

}
