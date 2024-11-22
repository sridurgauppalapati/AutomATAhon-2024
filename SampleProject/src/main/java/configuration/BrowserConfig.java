package configuration;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;
import javax.swing.JOptionPane;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrowserConfig {

	public static WebDriver webDriver;
	public static long startTime;
	public static long endTime;
	public static int Failedflag = 0;
	static ReadPropertiesFile read = new ReadPropertiesFile();
	static String chromeDriverPath = System.getProperty("user.dir") + "\\Utils\\Drivers\\chromedriver.exe";
	static String chromeDriverMobPath = System.getProperty("user.dir")+"\\Utils\\Drivers\\chromedriver_124.exe";
public static String Mode = "UI";

	public int getFailedFlagCount() {
		return Failedflag;
	}

	public void setFailedFlagToZero() {
		Failedflag = 0;
	}

	public void Launch(String Mode, String URLKey) {
		try { 
			startTime = System.currentTimeMillis();
			Mode = Mode.toUpperCase();
			System.out.println(Mode);
			
			if(Mode.equalsIgnoreCase("UI")) {
				browserExecution("Chrome",URLKey);
			}
			else if(Mode.equalsIgnoreCase("MOBILE")){
				mobileExecution("Chrome",URLKey);
			}
		} catch (Exception e) {
			System.out.println("Failed to launch application dfue to exception " + e.getMessage());
		}

	}

	private void mobileExecution(String BrowserName, String URLKey) {
		try {	
			
			try {
				String[] command = {"cmd.exe", "/C", "Start", System.getProperty("user.dir")+"/openDevice.bat"};
				Process process =  Runtime.getRuntime().exec(command);           
			} catch (Exception ex) {
				System.out.println("Mobile already open");
			} 
			// Set DesiredCapabilities
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "15.0");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");

			// Set Browser and Chromedriver Capabilities
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserName);
			capabilities.setCapability("chromedriverExecutable", chromeDriverMobPath); // Replace with your path

			// Set Auto Accept Alerts Capability
			capabilities.setCapability("autoAcceptAlerts", true);

			// Create ChromeOptions and set W3C mode
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("w3c", false);

			// Merge ChromeOptions with DesiredCapabilities
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			// Initialize AndroidDriver
			webDriver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			new Keywords().getURL(URLKey);
		}catch(Exception e) {
			System.out.println("Failed to launch mobile "+e.getMessage());
		}
	}

	private void browserExecution(String BrowserName, String URLKey) {
		BrowserName = BrowserName.toUpperCase();
		try {
			switch (BrowserName) {
			case "CHROME": {
				try {
					webDriver = getChromeDriver();
					webDriver.manage().window().maximize();
					new Keywords().getURL(URLKey);
				} catch (Exception e) {
					System.out.println("Exception while launching a ChromeDriver " + e.getMessage());
				}
				break;
			}
			case "FIREFOX": {
				try {
					webDriver = getFireFoxDriver();
					webDriver.manage().window().maximize();
					new Keywords().getURL(URLKey);
				} catch (Exception e) {
					System.out.println("Exception while Launching a FFDriver " + e.getMessage());
				}
				break;
			}

			case "IE": {
				try {
					webDriver = getIEDriver();
					webDriver.manage().window().maximize();
					new Keywords().getURL(URLKey);
				} catch (Exception e) {
					System.out.println("Exception while Launching a IEDriver " + e.getMessage());
				}
				break;
			}
			case "EDGE": {
				try {
					webDriver = getEdgeDriver();
					webDriver.manage().window().maximize();
					new Keywords().getURL(URLKey);
				} catch (Exception e) {
					System.out.println("Exception while Launching a EdgeDriver " + e.getMessage());
				}
				break;
			}
			case "SAFARI": {
				try {
					webDriver = getSafari();
					webDriver.manage().window().maximize();
					new Keywords().getURL(URLKey);
				} catch (Exception e) {
					System.out.println("Exception while Launching a EdgeDriver " + e.getMessage());
				}
				break;
			}
			case "HTML": {
				try {
					webDriver = getHtmlUnitDriver();
					webDriver.manage().window().maximize();
					new Keywords().getURL(URLKey);
				} catch (Exception e) {
					System.out.println("Exception while Launching a EdgeDriver " + e.getMessage());
				}
				break;
			}
			}
		} catch (Exception e) {
			System.out.println("Failed to launch application due to exception " + e.getMessage());
		}
	}

	private WebDriver getSafari() {

		WebDriver driver = null;
		try {
			driver = new SafariDriver();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Safari Failed during driver initialization " + e.getMessage());
		}

		return driver;
	}

	private WebDriver getEdgeDriver() {

		WebDriver driver = null;
		try {
			String EdgeDriverPath = System.getProperty("user.dir") + "\\Utils\\Drivers\\MicrosoftWebDriver.exe";
			System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, EdgeDriverPath);
			driver = new EdgeDriver();
			System.out.println("launching Microsoft Edge browser");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Edge Failed during driver initialization " + e.getMessage());
		}

		return driver;
	}

	private WebDriver getFireFoxDriver() {
		WebDriver driver = null;
		String geckoDriverPath = null;
		try {
			geckoDriverPath = System.getProperty("user.dir") + "\\Utils\\Drivers\\geckodriver.exe";
			System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, geckoDriverPath);

			driver = new FirefoxDriver();

		} catch (Exception e) {
			System.out.println("FF Failed during driver initialization " + e.getMessage());
		}
		return driver;
	}

	private WebDriver getHtmlUnitDriver() {

		WebDriver driver = null;
		try {
			//driver = new HtmlUnitDriver();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("HtmlUnitDriver Failed during driver initialization " + e.getMessage());
		}

		return driver;
	}

	private WebDriver getChromeDriver() {

		WebDriver driver = null;
		try {
			//WebDriverManager.chromedriver().setup();
			System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chromeDriverPath);
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Chrome Failed during driver initialization " + e.getMessage());
		}

		return driver;
	}

	private WebDriver getIEDriver() {
		WebDriver driver = null;
		try {
			String ieDriverPath = System.getProperty("user.dir") + "\\Utils\\Drivers\\IEDriverServer.exe";
			System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, ieDriverPath);

			InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();

			internetExplorerOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			internetExplorerOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			internetExplorerOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			internetExplorerOptions.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			internetExplorerOptions.setCapability("requireWindowFocus", true);
			internetExplorerOptions.ignoreZoomSettings();

			driver = new InternetExplorerDriver(internetExplorerOptions);

			driver.manage().deleteAllCookies();
			//Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");


		}catch(Exception e) {
			System.out.println("Failed to launch IE driver due to exception "+e.getMessage());
		}

		return driver;
	}

	public static String browserNameMsgBox(String[] browsers) {
		int response = 0;
		try {
			response = JOptionPane.showOptionDialog(null, "Please provide the Browser Name...", "Browser Name incorrect!..",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, browsers, browsers[0]);
			/*

			new Thread(new Runnable() {
			      @Override
			      public void run() {
			        try {
			          Thread.sleep(5000);
			        } catch (InterruptedException e) {
			          e.printStackTrace();
			        }
			      }
			    }).start();  
			 */
		}catch(Exception e) {
			System.err.println("Failed to display Dilog box due to exception "+e.getMessage());
		}
		return  browsers[response];
	}
}
