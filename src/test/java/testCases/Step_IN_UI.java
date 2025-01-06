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
import koha.CommonMethods;
import koha.Koha_start;
import koha.PatronCreation;
import koha.Validations;
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
	public PatronCreation patronCreation = new PatronCreation();

	@Test(priority = 0)
	public void TC_KOHA() {
		common.Launch();
		logger.logPass("Launch", "successfully launched koha application login page");
		kohaStart.Login();
		logger.logPass("Login", "successfully login into koha application");
	}

	@Test(priority = 1)
	public void TC_UI_CreateLibrary() {
		kohaStart.create_library();
	}
	@Test(priority = 2)
	public void createPatron() throws IOException, InterruptedException {
		
		patronCreation.PatronCreationOnGenerationMode();
//		Validations.OPACPatronValidation();

	}
	@Test(priority = 3)
	public void createBook() throws IOException, InterruptedException {
		
		kohaStart.createBook();

	}
	
	@Test(priority = 4)
	public void addItems() throws Exception {
		
		kohaStart.addItemsToBook();

	}
	@Test(priority = 5)
	public void BookToCart() throws Exception {
		
		kohaStart.addBookToCart();

	}
	@Test(priority = 6)
	public void checkout() throws Exception {
		
		kohaStart.CheckoutBook();

	}
	@Test(priority = 7)
	public void checkIn() throws Exception {
		
		kohaStart.submitOrCheckInBook();

	}
	
	
}
