package pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import configuration.BrowserConfig;
import configuration.Keywords;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import utilities.DataGeneration;
import utilities.Mob_Locators;
import utilities.Reporting;
import utilities.UI_Locators;

public class CommonMethods {

	public Keywords actions = new Keywords();
	public DataGeneration dataGenerate = new DataGeneration();
	public Reporting logger = new Reporting();
	public BrowserConfig config = new BrowserConfig();
	public UI_Locators locator = new UI_Locators();
	public static HashMap<String, String> imdbmap = new HashMap();

	public void Launch(String browserName, String Url) {
		try {
			if (config.webDriver == null)
				config.Launch(browserName, Url);
			else
				actions.getURL(Url);
			//logger.logPass("Launched", "N");
		} catch (Exception e) {
			System.out.println("Failed to Launch due to exception " + e.getMessage());
		}
	}

	public String generateRandomNumber() {
		Random rn = new Random();
		long range = 1000000000L + (long) (rn.nextDouble() * 999999999L);
		String randNumb = String.valueOf(range);
		return randNumb;
	}

	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		return (dtf.format(now));
	}

	public String[] csv_FileRead(String Fileloc) {
		BufferedReader br = null;
		String line = " ";
		String[] data = null;
		try {
			br = new BufferedReader(new FileReader(Fileloc));
			while ((line = br.readLine()) != null) {
				data = line.split("\\|");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return data;

	}

	public void SelectVideoFromList(String Locator,String videoName) {
		try {
			WebElement findVideo = null;
			int flag = 0;
			do{
				try {
					findVideo = actions.getWebElement("//a[@id='video-title'][@title='"+videoName+"']");
				}catch(Exception e) {

				}
				actions.scroll(0, 100);
				Thread.sleep(300);
				flag++;
				if(flag>250)
					break;
				//actions.waitForPageToLoad(30);
			}while(findVideo== null);
			if(findVideo==null) {
				logger.logFail("Video Not Found in list");
			}
			actions.jsScrollToElement(findVideo);
			logger.logPass("Screenshot Of Video", "Located video");
			actions.click(findVideo);
			actions.waitForPageToLoad(10);
		}catch(Exception e) {
			logger.logFail("Failed to select video from list due to exception "+e.getMessage());
		}
	}
	
	public void SelectVideoFromList_Mob(String Locator,String videoName) {
		System.out.println(videoName);
		try {
			WebElement findVideo = null;
			int flag = 0;
			do{
				try {
					findVideo = actions.getWebElement(Locator.replace("##", videoName));
				}catch(Exception e) {

				}
				//Robot robot = new Robot();
				//robot.keyPress(KeyEvent.VK_DOWN);

				//actions.scroll(0, 100);
				actions.jsScrollToElement(new Mob_Locators().ShowMore);
				Thread.sleep(100);
				try {
					findVideo = actions.getWebElement(Locator.replace("##", videoName));
				}catch(Exception e) {

				}
				flag++;
				if(actions.getWebElement(new Mob_Locators().ShowMore).isDisplayed()) {
					actions.jsScrollToElement(new Mob_Locators().ShowMore);
					actions.click(new Mob_Locators().ShowMore);
				}
				if(flag>500)
					break;
				//actions.waitForPageToLoad(30);
			}while(findVideo== null);
			if(findVideo==null) {
				logger.logFail("Video Not Found in list");
			}
			actions.jsScrollToElement(findVideo);
			logger.logPass("Screenshot Of Video", "Located video");
			try {
			actions.click(findVideo);
			}catch(Exception e) {
				actions.scroll(0, 100);
				actions.click(findVideo);
			}
			actions.waitForPageToLoad(10);
		}catch(Exception e) {
			logger.logFail("Failed to select video from list due to exception "+e.getMessage());
		}
	}

	public String[] GetUpNextVideo(List<WebElement> videolist) {
		String[] upNextList = new String[videolist.size()];
		for(int j=0; j<videolist.size();j++)
		{
			upNextList[j]=videolist.get(j).getText();
			System.out.println(upNextList[j]);
		}
		return upNextList;
	}

	public File WriteToJsonFile(String jsonValue,String Mode) {
		File file = new File(System.getProperty("user.dir") + "//src//main//resources//jsonBody"+Mode+".json");
		try {
			Writer writer;
			writer = new FileWriter(file);
			writer.write(jsonValue);
			writer.close();
		} catch (IOException e) {
			logger.logFail("Failed to write to json file due to exception "+e.getMessage());
		}
		return file;
	}

	/*
	 * public void get_DirectorandCompare(String moviename, String Wikipedialink) {
	 * try { Launch("Chrome", Wikipedialink); String Wiki_Directorname =
	 * actions.getWebElement(locator.directed_By).getText(); String Imdb_link =
	 * actions.getWebElement(locator.wiki_imdblink).getAttribute("href");
	 * Launch("Chrome", Imdb_link);
	 * actions.waitExplicit(locator.imdblink_directed_By, 30); String
	 * Imdb_Directorname =
	 * actions.getWebElement(locator.imdblink_directed_By).getText();
	 * System.out.println(moviename+"Dir1:" + Wiki_Directorname + "Dir2:" +
	 * Imdb_Directorname); if(Wiki_Directorname.equals(Imdb_Directorname)) {
	 * System.out.println(moviename+"Pass"); //logger.logPass(moviename,
	 * Wiki_Directorname, Imdb_link, Imdb_Directorname); }
	 * //Assert.assertEquals(Wiki_Directorname, Imdb_Directorname); } catch
	 * (Exception e) { System.out.println(e.getMessage()); } }
	 */
}
