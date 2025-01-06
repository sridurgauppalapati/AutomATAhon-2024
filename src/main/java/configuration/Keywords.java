package configuration;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;
import java.util.Random;

import org.testng.Assert;
import org.json.simple.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.DataGeneration;
import utilities.DataProperty;
import utilities.Excel;
import utilities.Reporting;

public class Keywords extends BrowserConfig {

	public Reporting logger = new Reporting();
	public DataGeneration dataGenerate = new DataGeneration();
	public DataProperty dataProp = new DataProperty();

	public void quitBrowser() {
		if(webDriver!=null) {
			webDriver.close();
		}
	}

	/**
	 *This method is used to clear the text entered or displayed in the text fields.
	 *@sLocator - Locator of the element
	 */
	public void clear(String sLocator) {
		WebElement actualLocator = getWebElement(sLocator);
		actualLocator.clear();
	}

	/**
	 * 
	 *This method gives WebElement of input locator.
	 */
	public WebElement getWebElement(String sLocator) {
		System.out.println("Printing locator " + sLocator);
		String actualLocator = sLocator;
		WebElement element = null;
		try {
			if (actualLocator.contains("//") || actualLocator.startsWith("(")) {
	            element = webDriver.findElement(By.xpath(actualLocator));
			} else {
				try {
					element = webDriver.findElement(By.id(actualLocator));
				} catch (Exception e) {
					try {
						element = webDriver.findElement(By.name(actualLocator));
					} catch (Exception e1) {
						try {
							element = webDriver.findElement(By.cssSelector(actualLocator));
						} catch (Exception e2) {
							try {
								element = webDriver.findElement(By.className(actualLocator));
							} 
							catch (Exception e5) {
								
								try {
									element = webDriver.findElement(By.linkText(actualLocator));
								} catch (Exception e6) {
									//logger.logFail("Failed since the locator given was not found");
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			//logger.logFail("Error while finding the Element. Exception: " + e.getMessage());
		}
		return element;
	}
	/**
	 *This method gives List of WebElement of input locator.
	 */
	public List<WebElement> getWebElementList(String sLocator) {
		String actualLocator = sLocator;
		List<WebElement> element = null;
		try {
			if (actualLocator.startsWith("//")) {
				element = webDriver.findElements(By.xpath(actualLocator));
			} else {
				try {
					element = webDriver.findElements(By.cssSelector(actualLocator));
					if (element.size() != 0)
						return element;
					else
						element = webDriver.findElements(By.name(actualLocator));
					if (element.size() != 0)
						return element;
					else
						element = webDriver.findElements(By.id(actualLocator));
					if (element.size() != 0)
						return element;
					else
						element = webDriver.findElements(By.className(actualLocator));
					if (element.size() != 0)
						return element;
					else
						element = webDriver.findElements(By.tagName(actualLocator));
					if (element.size() != 0)
						return element;
				} catch (Exception e) {
					//logger.logFail("Error while finding the Element list due to exception " + e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.logFail("Error while finding the Element list due to exception " + e.getMessage());
		}
		return element;
	}
	/**
	 *This method gives List of WebElement for input locator (without excel's locator sheet).
	 */
	public List<WebElement> getWebElementListWithoutExcel(String actualLocator) {
		List<WebElement> element = null;
		try {
			if (actualLocator.startsWith("//")) {
				element = webDriver.findElements(By.xpath(actualLocator));
			} else {
				try {
					element = webDriver.findElements(By.cssSelector(actualLocator));
					if (element.size() != 0)
						return element;
					else
						element = webDriver.findElements(By.name(actualLocator));
					if (element.size() != 0)
						return element;
					else
						element = webDriver.findElements(By.id(actualLocator));
					if (element.size() != 0)
						return element;
					else
						element = webDriver.findElements(By.className(actualLocator));
					if (element.size() != 0)
						return element;
					else
						return null;
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			logger.logFail("Error while finding the Element list due to exception " + e.getMessage());
		}
		return element;
	}

	public void selectDropdownByVisibleText(WebElement dropdownElement, String visibleText) {
        try {
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText(visibleText);
            System.out.println("Selected option: " + visibleText);
        } catch (Exception e) {
            System.err.println("Failed to select the option: " + visibleText);
            e.printStackTrace();
        }
    }
	public static String generateRandomDate(int daysRange) {
        // Get the current date
        LocalDate today = LocalDate.now();

        // Generate a random number of days within the range
        Random random = new Random();
        int randomDays = random.nextInt(daysRange + 1); // Range: 0 to daysRange

        // Subtract the random number of days from today to get a random past date
        LocalDate randomDate = today.minusDays(randomDays);

        // Format the date in dd/MM/yyyy format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return randomDate.format(formatter);
    }

	public WebElement getWebElementWithoutExcel(String actualLocator) {
		WebElement element = null;
		try {
			if (actualLocator.startsWith("//")) {
				element = webDriver.findElement(By.xpath(actualLocator));
			} else {
				try {
					element = webDriver.findElement(By.id(actualLocator));
				} catch (Exception e) {
					try {
						element = webDriver.findElement(By.name(actualLocator));
					} catch (Exception e1) {
						try {
							element = webDriver.findElement(By.cssSelector(actualLocator));
						} catch (Exception e2) {
							try {
								element = webDriver.findElement(By.className(actualLocator));

							} catch (Exception e5) {
								//logger.logFail("Failed since the locator '"+actualLocator+"' given was not found");
							}
						}
					}
				}
			}

		} catch (Exception e) {
			////logger.logFail("Error while finding the Element. Exception: " + e.getMessage());
		}
		return element;
	}

	public void getURL(String url) {
		System.out.println(url);
		webDriver.get(url);
	}

	public void click(String sLocator) {
		WebElement actualLocator = getWebElement(sLocator);
		//jsScrollToElement(sLocator);
		actualLocator.click();
	}

	public void click(WebElement actualLocator) {
		actualLocator.click();
	}

	
	public String getText(String elementString) {
	    try {
	    	WebElement element = getWebElement(elementString);
	        // Ensure the element is displayed before attempting to get its text
	        if (element.isDisplayed()) {
	            String text = element.getText();
	            logger.logInfo("Successfully retrieved text: " + text);
	            return text;
	        } else {
	            logger.logInfo("Element is not displayed, unable to retrieve text.");
	            return null;
	        }
	    } catch (Exception e) {
	        logger.logFail("Failed to get text due to exception: " + e.getMessage());
	        return null;
	    }
	}

	public boolean waitForElementToBeVisible(WebElement locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, timeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(locator));
            return true;
        } catch (Exception e) {
            System.err.println("Element not visible within the timeout: " + e.getMessage());
            return false;
        }
    }
	public void waitForPageToLoad(int timeInSec) {
		try {
			int iterator = 0;
			boolean blnrtrn, blnajaxIsComplete = false;
			try {
				do {
					JavascriptExecutor js = (JavascriptExecutor) webDriver;
					blnrtrn = js.executeScript("return document.readyState").equals("complete");
					try {
						blnajaxIsComplete = (Boolean) js.executeScript("return jQuery.active == 0");
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
					if (blnrtrn && blnajaxIsComplete) {
						break;
					} else {
						iterator = iterator + 1;
						Thread.sleep(1000);
					}
				} while (iterator < timeInSec);

				if (!blnrtrn && blnajaxIsComplete) {
					System.out.println("Failed to load page in " + timeInSec + " seconds");
				}
				//waitForLoadingToDisappear();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.logFail("Failed to load page in " + timeInSec + " due to exception " + e.getMessage());
			}
		} catch (Exception e) {
			logger.logFail("Failed to load page in " + timeInSec + " due to exception " + e.getMessage());
		}
	}

//	public void waitForLoadingToDisappear() {
//		try {
//			new WebDriverWait(webDriver, 30).until(
//					ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(("Page_Loading"))));
//		} catch (Exception e) {
//			logger.logFail("Failed to wait for Loading to disappear from page due to exception " + e.getMessage());
//			e.printStackTrace();
//		}
//	}

	public boolean isElementPresent(String sLocator) {
		WebElement locator = getWebElement(sLocator);
		boolean elementPresent = false;
		if(locator!=null) {
			try {
				if (locator.isDisplayed()) {
					elementPresent = true;
				}
			} catch (Exception ex) {
				if (locator.isDisplayed())
					elementPresent = true;
			}
		}
		return elementPresent;
	}

	public boolean isElementPresent(WebElement element) {
		boolean elementPresent = false;
		if(element!=null) {
			try {
				if (element.isDisplayed()) {
					elementPresent = true;
				}
			} catch (Exception ex) {
				if (element.isDisplayed())
					elementPresent = true;
			}
		}
		return elementPresent;
	}

	public boolean isElementEnabled(String sLocator) {
		WebElement locator = getWebElement(sLocator);
		boolean elementPresent = false;
		if(locator!=null) {
			try {
				if (locator.isEnabled()) {
					elementPresent = true;
				}
			} catch (Exception ex) {
				if (locator.isEnabled())
					elementPresent = true;
			}
		}
		return elementPresent;
	}


	public void scrollToView(String sLocator) {
		try {
			WebElement locator = getWebElement(sLocator);
			if (locator != null) {
				Actions actions = new Actions(webDriver);
				actions.moveToElement(locator);
				actions.perform();
				Thread.sleep(500);

			}
		} catch (Exception e) {
			logger.logFail("Failed to scroll the page due to exception " + e.getMessage());
		}
	}

	public void mouseHover(String sLocator) {
		try {
			WebElement locator = getWebElement(sLocator);
			if (locator != null) {
				Actions actions = new Actions(webDriver);
				actions.moveToElement(locator);
				actions.perform();
				Thread.sleep(500);

			}
		} catch (Exception e) {
			logger.logFail("Failed to scroll the page due to exception " + e.getMessage());
		}
	}

	public void scrollToView(WebElement element) {

		try {
			if (element != null) {
				Actions actions = new Actions(webDriver);
				actions.moveToElement(element);
				actions.perform();
				Thread.sleep(500);
			}
		} catch (Exception e) {
			logger.logFail("Failed to scroll the page due to exception " + e.getMessage());
		}
	}



	public void jsScrollToElement(String sLocator) {

		try {
			WebElement locator = getWebElement(sLocator);
			if (locator != null) {
				((JavascriptExecutor) webDriver).executeScript("window.scroll(" + (locator.getLocation().getX() - 20)
						+ ", " + (locator.getLocation().getY() - 350) + ");");

			}
		} catch (Exception e) {
			logger.logFail("Failed to scroll the page due to exception " + e.getMessage());
		}
	}

	public void jsScrollToElement(String sLocator,int Xaxis, int Yaxis) {
		try {
			WebElement locator = getWebElement(sLocator);
			if (locator != null) {
				((JavascriptExecutor) webDriver).executeScript("window.scroll(" + (locator.getLocation().getX() - 20)
						+ ", " + (Yaxis - Xaxis) + ");");

			}
		} catch (Exception e) {
			logger.logFail("Failed to scroll the page due to exception " + e.getMessage());
		}
	}

	public void jsScrollToElement(WebElement element,int Xaxis, int Yaxis) {
		try {
			if (element != null) {
				((JavascriptExecutor) webDriver).executeScript("window.scroll(" + (element.getLocation().getX() - 20)
						+ ", " + (Yaxis - Xaxis) + ");");

			}
		} catch (Exception e) {
			logger.logFail("Failed to scroll the page due to exception " + e.getMessage());
		}
	}

	public void jsScrollToElement(WebElement element) {

		try {
			if (element != null) {
				((JavascriptExecutor) webDriver).executeScript("window.scroll(" + (element.getLocation().getX() - 20)
						+ ", " + (element.getLocation().getY() - 350) + ");");

			}
		} catch (Exception e) {
			logger.logFail("Failed to scroll the page due to exception " + e.getMessage());
		}
	}

	public void javaScriptClick(String sLocator) {

		try {
			WebElement element = getWebElement(sLocator);
			if (element != null) {
				((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
			} else {
				logger.logFail("Given element to JS click is null. Verify the Locator given");
			}
		} catch (Exception e) {
			logger.logFail("Failed to perform JS click due to exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void javaScriptClick(WebElement element) {
		try {
			if (element != null) {
				((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
			} else {
				logger.logFail("Given element to JS click is null. Verify the Locator given");
			}
		} catch (Exception e) {
			logger.logFail("Failed to perform JS click due to exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void scroll(int min, int max) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("window.scrollBy(" + min + "," + max + ")", "");
		} catch (Exception e) {
			//logger.logFail("Failed to scroll the page due to exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void scrollToBottomOfCreateCustPage() {
		scrollToView("CreateCustomer_BottomOfPage");
		getWebElement("CreateCustomer_footer").click();
		Actions action = new Actions(webDriver);
		action.sendKeys(Keys.DOWN).build().perform();
	}

	public String[] removeNullValues(String[] arrayString) {
		try {
			ArrayList<String> list = new ArrayList<String>();
			for (String s : arrayString) {
				if (s == null)
					s = null;
				else
					list.add(s);
			}
			arrayString = list.toArray(new String[list.size()]);
		} catch (Exception e) {
			System.out.println("Failed to remove null values from String[] due to exception " + e.getMessage());
		}
		return arrayString;
	}


	public void waitImplicit(String sLocator, int seconds) {
		try {
			if (sLocator == null) {
				Thread.sleep((seconds * 1000));
			} else {
				for (int i = 1; i < seconds; i++) {
					if (isElementPresent(sLocator)) {
						break;
					}
					Thread.sleep(1000);
				}
				if (!isElementPresent(sLocator)) {
					logger.logFail("Element not found within the given time");
				}
			}
		} catch (Exception e) {
			logger.logFail(
					"Locator " + sLocator + " is not found within " + seconds + " seconds. Exception:" + e.toString());
		}
	}


//	public boolean waitForElementPresent(String sLocator, int TimeInSeconds) {
//		try {
//			ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
//				
//				public Boolean apply(WebDriver webDriver) {
//					WebElement element = getWebElement(sLocator);
//					WebDriverWait wait = new WebDriverWait(webDriver, 30);
//					wait.until(ExpectedConditions.stalenessOf(element));
//					
//					if (element != null) {
//						return true;
//
//					} else {
//						return false;
//					}
//				}
//			};
//			Wait<WebDriver> wait = new WebDriverWait(webDriver, TimeInSeconds);
//			try {
//				wait.until(expectation);
//				return true;
//			} catch (Exception e) {
//				logger.logFail("Error while waiting for element. Exception: " + e.getMessage());
//			}
//		} catch (Exception e) {
//			logger.logFail("Error while waiting for element. Exception: " + e.getMessage());
//		}
//		return false;
//	}
	/**
	 * It will select the value from the drop down on the basis of there li tag if thats available.
	 */
	public void SetselectDropdownByLi(String sLocator, String Data) {
		try {
			WebElement Dd_locator = getWebElement(sLocator);
			String locatorAttributeRole = Dd_locator.getAttribute("role");
			if (locatorAttributeRole.contains("listbox")) {
				jsScrollToElement(Dd_locator);
				Thread.sleep(1000);
				List<WebElement> liList = Dd_locator.findElements(By.tagName("li"));
				System.out.println(liList);
				for (WebElement li : liList) {
					if (li.getText().equals(Data)) {
						// System.out.println(li.getText());
						li.click();
						logger.logPass("Successfully able to Select the Option :" + li.getText(), "Y");
						Thread.sleep(1000);
						waitForPageToLoad(60);
						break;
					}
				}

			}
		} catch (Exception e) {
			logger.logFail("Failed to select Dropdown due to exception " + e.getStackTrace());
		}
	}
	/**
	 * It will perform drag and drop operation.
	 * @String From - is the string which we need to drag
	 * @String To - is the string on which we need to drop the first element
	 */
	public void DragNDrop(String From, String To) {
		try {
			WebElement Frm = getWebElement(From);
			WebElement Too = getWebElement(To);
			// Using Action class for drag and drop.
			Actions act = new Actions(webDriver);
			// Dragged and dropped.
			act.dragAndDrop(Frm, Too).build().perform();
			logger.logPass("Successfully performing Drag N Drop", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			logger.logFail("Drag and Drop failed due to exception :" + e.getMessage());
		}
	}
	/**
	 * It will perform drag and drop operation.
	 * @WebElement From - is the element which we need to drag
	 * @WebElement To - is the element on which we need to drop the first element
	 */
	public void DragNDrop(WebElement From, WebElement To) {
		try {
			// Using Action class for drag and drop.
			Actions act = new Actions(webDriver);
			// Drag and drop.
			act.dragAndDrop(From, To).build().perform();
			logger.logPass("Successfully performing Drag N Drop", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			logger.logFail("Drag and Drop failed due to exception :" + e.getMessage());
		}

	}

	public void click(String sLocator, WebElement Ele) {

		try {
			if (sLocator != null && sLocator != "" && Ele == null) {
				List<WebElement> listEle = getWebElementList(sLocator);
				if (listEle.size() > 0) {
					for (WebElement ele : listEle) {
						try {
							if (ele.isEnabled() && ele.isDisplayed()) {
								ele.click();
								break;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

				}
			}
			if (Ele != null) {
				Ele.click();
			}

		} catch (Exception e) {
			logger.logFail("Failed to click on the Duplicate List of elements");
		}
	}


	public String getTextNode(WebElement e) {
		String text = e.getText().trim();
		List<WebElement> children = e.findElements(By.xpath("./*"));
		for (WebElement child : children) {
			text = text.replaceFirst(child.getText(), "").trim();
		}
		return text;
	}

	public String getMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	public void deleteDataPropFile() {
		try {
			dataProp.cleanData();
		} catch (Exception e) {
			System.out.println("No such file/directory exists");
		}
	}

	public void select(String sLocator, String VisibleText) {
		WebElement ele = getWebElement(sLocator);
		Select sel = new Select(ele);
		sel.selectByVisibleText(VisibleText);

	}
	public boolean isElementVisible(String elementLocator) {
	    try {
	       
	        WebElement element = webDriver.findElement(By.cssSelector(elementLocator));
	        boolean isVisible = element.isDisplayed();
//	        logger.logInfo("Element is visible: " + elementLocator);
	        return isVisible;
	    } catch (NoSuchElementException e) {
//	        logger.logInfo("Element not found: " + elementLocator);
	        return false;
	    } catch (Exception e) {
//	        logger.logFail("Failed to check visibility of element due to exception: " + e.getMessage());
	        return false;
	    }
	}
	 public void ensureCheckboxChecked(String checkbox) {
		 WebElement actualLocator = getWebElement(checkbox);
	        if (!actualLocator.isSelected()) {
	        	actualLocator.click();
	        	logger.logPass("Checkbox was unchecked, now it is checked.","blabla");
	        } 
	    }
	public void assertTrue(boolean condition, String assertStatement, String Screenshot) {
		if(condition) {
			logger.logPass("Assert Passed for '"+assertStatement+"'", Screenshot);
		}else {
			logger.logFail("Assert Failed for '"+assertStatement+"'");
			Assert.fail();
		}
	}

	public void assertFalse(boolean condition, String assertStatement, String Screenshot) {
		if(!condition) {
			logger.logPass("Assert Passed for '"+assertStatement+"'", Screenshot);
		}else {
			logger.logFail("Assert Failed for '"+assertStatement+"'");
			webDriver.quit();
			Assert.fail();
		}
	}

	public void switchToWindow(Object sValue){
		try{
			String title = sValue.toString();
			Set<String> availableWindows = webDriver.getWindowHandles();
			if (!availableWindows.isEmpty()) {
				for (String windowId : availableWindows) {
					if (webDriver.switchTo().window(windowId).getTitle().contains(title)) {
						logger.logPass("Window is switched to "+title , "N");
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.logFail("Failed to switch due to exception "+e.getMessage());
		}
	}

	public void switchToWindowHandle(String windowHandler){
		try{
			webDriver.switchTo().window(windowHandler);
		}catch(Exception e){
			logger.logFail("Failed to switch due to exception "+e.getMessage());
		}
	}
	
	public void switchToDefaultWindow() throws InterruptedException {
		String defaultWindowHandle = webDriver.getWindowHandle(); 
		switchToWindow(defaultWindowHandle);
		Thread.sleep(1000); 
//        try {
//            // Wait until only the default window is available
//            while (webDriver.getWindowHandles().size() > 1) {
//                Thread.sleep(1000); // Small delay to allow pop-up to close
//            }
//            // Switch back to the default window
//            switchToWindow(defaultWindowHandle);
//            webDriver.switchTo().window(defaultWindowHandle);
//        } catch (NoSuchWindowException e) {
//            System.out.println("Default window not found: " + e.getMessage());
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt(); // Restore interrupt status
//            System.out.println("Interrupted while waiting for pop-up to close: " + e.getMessage());
//        }
    }

	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}
	
	public List<WebElement> getMissingFields(String missingFieldsLocator) {
	    try {
	        return webDriver.findElements(By.cssSelector(missingFieldsLocator + " ul > li"));
	    } catch (Exception e) {
	        logger.logFail("Failed to get missing fields due to exception: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}

	

	public WebElement findElementByText(String text) {
	    try {
	        String sanitizedText = text.replace("\n", " ").trim(); // Replace line breaks
	        return webDriver.findElement(By.xpath("//*[contains(normalize-space(text()),'" + sanitizedText + "')]"));
	    } catch (NoSuchElementException e) {
	        logger.logFail("Element with text not found: " + text);
	        return null;
	    } catch (Exception e) {
	        logger.logFail("Failed to find element by text due to exception: " + e.getMessage());
	        return null;
	    }
	}


	public void setValue(String sLocator, String sValue) {
		try {
			WebElement locator = getWebElement(sLocator);
			locator.sendKeys(sValue);
		}catch(Exception e) {
			logger.logFail("Failed to set value due to exception "+e.getMessage());
		}
	}
	public void switchToFrame(String frameLocator) {
	    try {
	       
	        WebElement frameElement = webDriver.findElement(By.cssSelector(frameLocator));
	        webDriver.switchTo().frame(frameElement);
	        logger.logInfo("Switched to frame: " + frameLocator);
	    } catch (Exception e) {
	        logger.logFail("Failed to switch to frame due to exception: " + e.getMessage());
	    }
	}
	public void switchToDefaultContent() {
	    try {
	      
	    	webDriver.switchTo().defaultContent();
	        logger.logInfo("Switched back to default content.");
	    } catch (Exception e) {
	        logger.logFail("Failed to switch to default content due to exception: " + e.getMessage());
	    }
	}

	public String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	public void closeWindow() {
		webDriver.close();
	}

	public Object[] removeNullValues(Object[] arrayString) {
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			for (Object s : arrayString) {
				if (s == null)
					s = null;
				else
					list.add(s);
			}
			arrayString = list.toArray(new String[list.size()]);
		} catch (Exception e) {
			System.out.println("Failed to remove null values from String[] due to exception " + e.getMessage());
		}
		return arrayString;
	}
	
	public void selectMissingFieldLinks(String selection,int index) {
		try {
	        // Construct XPath for the specific <li> element based on the index
	        String liXpath = selection + "//ul[1]/li[" + index + "]//a";

	        // Find the <a> element using the constructed XPath
	        WebElement element = webDriver.findElement(By.xpath(liXpath));

	        // Click the found <a> element
	        click(element);

	        logger.logPass("Successfully clicked on the missing field link.","blabla");
	    } catch (Exception e) {
	        logger.logFail("Failed to get missing fields due to exception: " + e.getMessage());
	    }
	}
	public String extractFieldName(String missingField) {
	    // Updated regex to match text between "subfield" and "in tab"
	    String regex = "subfield\\s+\\w+\\s+(.*?)\\s+in\\s+tab\\s+\\d+";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(missingField);

	    if (matcher.find()) {
	        // Group 1 contains the desired text
	        String extractedText = matcher.group(1).trim();
	        System.out.println("Extracted Text: " + extractedText);
	        return extractedText;
	    } else {
	        System.out.println("No match found.");
	        return null;
	    }
	}

		

	public void select(String selection) {
		waitImplicit(null, 1);
		Robot robot;
		Map<String,String> keyMap = new HashMap<String,String>();
		keyMap.put("144", "1");
		keyMap.put("240", "2");
		keyMap.put("360", "3");
		keyMap.put("480", "4");
		keyMap.put("720", "5");
		keyMap.put("1080", "6");
		try {
			robot = new Robot();
			int count = Integer.parseInt(keyMap.get(selection));
			for(int i = 0; i <count; i++) {
				robot.keyPress(KeyEvent.VK_UP);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.logPass("Selecting", "Select "+selection+"p from Quality Option");
			robot.keyPress(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String jsonCreate(String[] list) {
		JSONArray allDataArray = new JSONArray();
		String[] sList = list;
		String json = "{\"team\":\"team-name\","
				+ "\"video\":\"search-video-name\","
				+ "\"upcoming-videos\":[";
		//if List not empty
		sList = removeNullValues(sList);
		if (!(sList.length ==0)) {
			//Loop index size()
			for(int index = 0; index < sList.length; index++) {
				try {
					json +="\""+ sList[index]+"\"";
					if(index!=sList.length-1) {
						json +=",";
					}else {
						json+="]}";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(json);
		} else {
			//Do something when sList is empty
		}
		return json;
	}
	public static void setHiddenDate(String labelXPath, String inputXPath, String desiredDate) throws Exception {
        try {
            // Click on the label or trigger to make the input visible
            WebElement dateLabel = webDriver.findElement(By.xpath(labelXPath));
            dateLabel.click();

            // Wait for the input field to appear in the DOM
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            WebElement dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(inputXPath)));

            // Use JavaScript to set the value in the hidden input field
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].setAttribute('value', arguments[1]);", dateInput, desiredDate);

            // Optionally trigger the input's onchange event
            js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dateInput);

            System.out.println("Date set successfully: " + desiredDate);

        } catch (Exception e) {
            throw new Exception("Failed to set the date. Reason: " + e.getMessage(), e);
        }
    }


	
}
