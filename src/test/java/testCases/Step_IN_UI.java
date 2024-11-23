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
		login.create_library();
//		login.create_Patrons();
	}

//	@Test(priority = 1)
//	public void TC_UI_CreateLibrary() {
//		
//		
//	
//	}

//	@Test(priority = 2)
//	public void TC_UI_VideoLink() {
//		actions.getWebElement(ulocator.Video).click();
//		logger.logPass("Select", "Video Link is selected");
//		actions.waitImplicit(null, 3);
//	}
//
//
//	@Test(priority = 3)
//	public void TC_UI_GetVideoNameViaApi() {
//		String videoName = api.API_Get("http://54.169.34.162:5252/video");
//		logger.logPass("Get Video name via API", "VideoName : "+videoName);
//		common.SelectVideoFromList(ulocator.SelectVideo, videoName);
//	}
//
//	@Test(priority = 4)
//	public void TC_UI_SelectQuality() {
//		actions.mouseHover(ulocator.VideoSettings);
//		actions.waitImplicit(ulocator.VideoSettings, 5);
//		actions.click(ulocator.VideoSettings);
//		actions.click(ulocator.QualityMenu);
//		actions.waitImplicit(null, 1);
//		actions.select("360");			
//	}
//
//	@Test(priority = 5)
//	public void TC_UI_GetUpNextListAndPost() {
//			List<WebElement>videolist=actions.getWebElementList(ulocator.upnext);
//			String[] upNextList = common.GetUpNextVideo(videolist);
//			String jsonValue = actions.jsonCreate(upNextList);
//			File file = common.WriteToJsonFile(jsonValue,"UI");
//			String response = api.API_Post(file, "http://54.169.34.162:5252/upload");
//			logger.logPass("API uploaded response",response);
//			String getResponse = api.API_Get("http://54.169.34.162:5252/result/"+response);
//			logger.logPass("API Get response",getResponse);
//	}

}
