package pages;

import configuration.Keywords;
import utilities.Locators;
import utilities.Reporting;

public class LoginPage {
	
	Locators locator = new Locators();
	Keywords actions = new Keywords();
	Reporting logger = new Reporting();
	
	public void Login(String userName, String password) {
		try {
			actions.setValue(locator.userName,userName);
			actions.setValue(locator.password,password);
		}catch(Exception e) {
			logger.logFail("Failed to login due to exception "+e.getMessage());
		}
	}

}
