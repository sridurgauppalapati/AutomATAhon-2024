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
import pages.Koha_start;
import utilities.UI_Locators;
import utilities.Koha_locator;
import utilities.Locators;

public class Step_IN_UI extends Global {
	public CommonMethods common = new CommonMethods();
	public Keywords actions = new Keywords();
	public UI_Locators ulocator = new UI_Locators();
	public APICommon api = new APICommon();
	public Koha_start kohaStart = new Koha_start();
	public Koha_locator klocator = new Koha_locator();
	public Locators loc = new Locators();

	@Test(priority = 0)
	public void TC_KOHA() {
		common.Launch();
		logger.logPass("Launch", "successfully launched koha application login page");
		kohaStart.Login();
		logger.logPass("Login", "successfully login into koha application");
	}

	@Test(priority = 1)
	public void TC_UI_CreateLibrary() {
//		kohaStart.create_library();
	}
	@Test(priority = 2)
	public void createPatronWithAPI() {
//		kohaStart.create_library();
	}

}
