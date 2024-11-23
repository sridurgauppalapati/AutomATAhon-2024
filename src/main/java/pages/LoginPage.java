package pages;


import configuration.Keywords;
import utilities.Koha_locator;
import utilities.Locators;
import utilities.Reporting;

public class LoginPage {
	
	Locators locator = new Locators();
	Keywords actions = new Keywords();
	Reporting logger = new Reporting();
	Koha_locator klocator = new Koha_locator();
	
	
	public void Login(String userName, String password) {
		try {
			actions.setValue(locator.userName,userName);
			actions.setValue(locator.password,password);
			
			actions.click(locator.sumbit);
			actions.wait(10);
		}catch(Exception e) {
			logger.logFail("Failed to login due to exception "+e.getMessage());
		}
		
	}
	public void create_library() {
		try {
			actions.click(klocator.admin);
			actions.javaScriptClick(klocator.Library);
			actions.javaScriptClick(klocator.add_lib);
			actions.setValue(klocator.branchname,"TestVerse");
			actions.setValue(klocator.branchcode, "GTRHACK"+"TV" );
			actions.javaScriptClick(klocator.library_submit);

		}catch(Exception e) {
			e.printStackTrace();
			logger.logFail("Failed to login due to exception "+e.getMessage());
		}
		
	}
	
//	public void create_Patrons() {
//		try {
//			actions.click(klocator.Home);
//			actions.click(klocator.Patrons);
//			actions.selectDropdownByLi(klocator.New_Patrons,"Staff");
//			
////			actions.click(klocator.add_lib);
////			actions.setValue(klocator.branchname,"TestVerse");
////			actions.setValue(klocator.branchcode, "GTR_HACK_"+"TV" );
////			actions.click(klocator.library_submit);
////			actions.wait(10);
//		}catch(Exception e) {
//			logger.logFail("Failed to login due to exception "+e.getMessage());
//		}
//		
//	}
	

}
