package testCases;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import api.APICommon;
import configuration.BrowserConfig;
import configuration.Global;
import configuration.Keywords;
import pages.CommonMethods;
import utilities.Mob_Locators;
import utilities.MyThread;
import utilities.UI_Locators;

public class Step_IN_Mob extends Global {
	// public BrowserConfig config = new BrowserConfig();
	public CommonMethods common = new CommonMethods();
	public Keywords actions = new Keywords();
	public Mob_Locators mlocator = new Mob_Locators();
	public APICommon api = new APICommon();

	
	@Test(priority = 0)
	public void TC_Mob_Launch_Youtube() {
		common.Launch("Mobile", "https://www.youtube.com/");
		logger.logPass("Launch", "Step In Forum Launched");
	}

	@Test(priority = 1)
	public void TC_Mob_Search_StepInForum() {
		actions.click(mlocator.searchBtn);
		actions.getWebElement(mlocator.search).sendKeys("step-inforum" + Keys.ENTER);
		logger.logPass("Enter", "Step In Forum Entered");
		actions.waitImplicit(mlocator.step_in_channel, 5);
		actions.getWebElement(mlocator.step_in_channel).click();
		actions.waitImplicit(mlocator.Video, 5);
	}

	@Test(priority = 2)
	public void TC_Mob_VideoLink() {
		actions.getWebElement(mlocator.Video).click();
		logger.logPass("Select", "Video Link is selected");
		actions.waitImplicit(null, 3);
	}


	@Test(priority = 3)
	public void TC_Mob_GetVideoNameViaApi() {
		String videoName = api.API_Get("http://54.169.34.162:5252/video");
		logger.logPass("Get Video name via API", "VideoName : "+videoName);
		common.SelectVideoFromList_Mob(mlocator.SelectVideo, videoName);
	}

	//@Test(priority = 4)
	public void TC_Mob_SelectQuality() {
		actions.mouseHover(mlocator.VideoSettings);
		actions.waitImplicit(mlocator.VideoSettings, 5);
		actions.click(mlocator.VideoSettings);
		actions.click(mlocator.QualityMenu);
		actions.waitImplicit(null, 1);
		actions.select("360");			
	}

	@Test(priority = 5)
	public void TC_Mob_GetUpNextListAndPost() {
			List<WebElement>videolist=actions.getWebElementList(mlocator.upnext);
			String[] upNextList = common.GetUpNextVideo(videolist);
			String jsonValue = actions.jsonCreate(upNextList);
			File file = common.WriteToJsonFile(jsonValue, "Mobile");
			String response = api.API_Post(file, "http://54.169.34.162:5252/upload");
			logger.logPass("API uploaded response",response);
			String getResponse = api.API_Get("http://54.169.34.162:5252/result/"+response);
			logger.logPass("API Get response",getResponse);
	}

	
	public void TC_Mob_Fetch_Yuotube() {

	common.Launch("Mobile", "https://www.youtube.com/");

		try {
			logger.logPass("Launch", "Step In Forum Launched");
			actions.click(mlocator.searchBtn);
			actions.getWebElement(mlocator.search).sendKeys("step-inforum" + Keys.ENTER);
			logger.logPass("Enter", "Step In Forum Entered");

			logger.logPass("Step In Forum Launched", "Step In Forum Launched");
			//System.out.println("Print serach");
			actions.waitImplicit(mlocator.step_in_channel, 5);
			actions.getWebElement(mlocator.step_in_channel).click();
			actions.waitImplicit(mlocator.Video, 5);
			actions.getWebElement(mlocator.Video).click();
			System.out.println("Print video1");
			actions.waitImplicit(null, 3);
			String videoName = api.API_Get("http://54.169.34.162:5252/video");
			logger.logPass("Get Video name via API", "VideoName : "+videoName);

			System.out.println("VideoName : "+videoName);
			WebElement findVideo = null;
			int flag = 0;
			do{
				try {
					findVideo = actions.getWebElement("//h4[@class='compact-media-item-headline'][text()='"+videoName+"']");
					}catch(Exception e) {
						
					}
				actions.scroll(0, 100);
				flag++;
				if(flag>221)
					break;
				//actions.waitForPageToLoad(30);
			}while(findVideo== null);
			logger.logPass("Screenshot Of Video", "Located video");
			if(findVideo==null) {
				logger.logFail("Video Not Found in list");
			}
			actions.jsScrollToElement(findVideo);
			actions.click(findVideo);
			actions.waitForPageToLoad(10);
			actions.mouseHover(mlocator.VideoSettings);
			actions.waitImplicit(mlocator.VideoSettings, 5);
			actions.click(mlocator.VideoSettings);
			actions.click(mlocator.QualityMenu);
			actions.waitImplicit(null, 1);
			//String listQuality = actions.getWebElement(mlocator.QualityList).getText();
			actions.select("360");			
			List<WebElement>videolist=actions.getWebElementList(mlocator.upnext);
			String[] upNextList = new String[videolist.size()];
			for(int j=0; j<videolist.size();j++)
	        {
				upNextList[j]=videolist.get(j).getAttribute("title");
	            System.out.println(upNextList[j]);
	        }
			String jsonValue = actions.jsonCreate(upNextList);
			File file = new File(System.getProperty("user.dir") + "//src//main//resources//jsonBody_mobile.json");
			Writer writer = new FileWriter(file);
			writer.write(jsonValue);
			writer.close();
			

			String response = api.API_Post(file, "http://54.169.34.162:5252/upload");
			logger.logPass("API uploaded response",response);
			
			String getResponse = api.API_Get("http://54.169.34.162:5252/result/"+response);
			logger.logPass("API Get response",getResponse);

			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
