package testCases;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.TestNG;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;

import api.APICommon;
import configuration.BrowserConfig;
import configuration.Global;
import configuration.Keywords;
import pages.CommonMethods;
import utilities.Mob_Locators;
import utilities.MyThread;
import utilities.UI_Locators;

public class Step_IN_Parallel extends Global {
	// public BrowserConfig config = new BrowserConfig();
	public CommonMethods common = new CommonMethods();
	public Keywords actions = new Keywords();
	public Mob_Locators mlocator = new Mob_Locators();
	public APICommon api = new APICommon();
	public Step_IN_UI ui = new Step_IN_UI();
	public Step_IN_Mob mob = new Step_IN_Mob();
	@Parameters("Mode")
	@Test
	public void ModeExecution(String Mode) {
		TestNG runner = new TestNG();
		if(Mode.equals("UI")) {
		try {
			ui.TC_UI_Launch_Youtube();
			ui.TC_UI_Search_StepInForum();
			ui.TC_UI_VideoLink();
			ui.TC_UI_GetVideoNameViaApi();
			ui.TC_UI_SelectQuality();
			ui.TC_UI_GetUpNextListAndPost();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		}
		else if(Mode.equals("Mobile")) {
			try {
				mob.TC_Mob_Launch_Youtube();
				mob.TC_Mob_Search_StepInForum();
				mob.TC_Mob_VideoLink();
				mob.TC_Mob_GetVideoNameViaApi();
				mob.TC_Mob_GetUpNextListAndPost();
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
