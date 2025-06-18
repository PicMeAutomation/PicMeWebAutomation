package Actions.CommonActions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Set;

//import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
//import Data.ApplicationCredentials.ShipSticksLogInCredentials;
import Utilities.ReportUtilities.ReportUtilities;
import Utilities.SuiteBase.SuiteBase;
//import browserstack.shaded.ch.qos.logback.core.util.Duration;
//import io.percy.selenium.Percy;

public class CommonActions extends SuiteBase{

	private WebDriver driver;
	private WebDriverWait wait;
	private ReportUtilities ru ;
	private ExtentTest logger;
	private String methodName;
	private JavascriptExecutor js;
	public CommonActions(String methodName, WebDriver wd, ExtentTest logger){
		
		this.driver = wd;
		this.wait = new WebDriverWait(wd, Duration.ofSeconds(30));
		this.ru = new ReportUtilities();
		this.logger = logger;
		this.methodName = methodName;
		//this.js = (JavascriptExecutor) wd;
	}
	
	public boolean verifyClickedLink(String title) {
		String UILoginPageTitle= getPageTitle();
		if(UILoginPageTitle.equals(title)) {
			return true;
		}
		return false;
	}

	public boolean isElementPresent(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} 
		catch (TimeoutException e) {
			return false;
		} 
	}
	public void waitForElementToDisappear(By locator, int timeoutInSeconds) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public void waitForElementToAppear(By locator, int timeoutInSeconds) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}


	public void SelectFromDropDown(By by, String DropdownText) {
		WebElement elemnt = driver.findElement(by);
		Select select = new Select(elemnt);
		select.selectByVisibleText(DropdownText);
	}
	
	public void driverClose(){
		String parent = driver.getWindowHandle();
		System.out.println("PARENT IS:"+ parent);
		Set<String> allwindows = driver.getWindowHandles();
		System.out.println("All window is:"+allwindows );
		for (String child : allwindows) {
			waitFor(2000);
			if (!parent.equalsIgnoreCase(child)) {
				System.out.println("child IS:"+ child);
				waitFor(1000);
				//driver.manage().deleteAllCookies();
				driver.close();
				System.out.println("Window closed:"+parent);
				driver.switchTo().window(child);
			}
		}
	}
	public boolean isElementPresent(WebElement loc) {
		try {
			loc.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} catch (Exception e) {
			return false;
		} finally {

		}
	}

	public void ClickMethod(By loc, String name) {
		if(isElementPresent(loc)) {
			wait.until(ExpectedConditions.elementToBeClickable(loc));
			driver.findElement(loc).click();
			logger.log(LogStatus.INFO,"INFO: Clicked on '"+name+"' ");
			System.out.println("INFO: Clicked on "+name);
		}else {
			assertFailWithOutException("FAIL: The '"+name+"' does  ot displayed on the home page.");
		}
	}
	
	/**
	 * Wait for page to load
	 */
	public void iWaitForPageToLoad() {
	    try {
	    	wait.until((ExpectedCondition<Boolean>) driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete")); 
	    } catch (Exception e) {
	        System.out.println("Wait for page failed with the following exc: " + e.toString());
	    }
	}
	public void waitUntilElementVisible() {
		WebDriver wd = null;
		new WebDriverWait(wd, Duration.ofSeconds(30));
	}

	public void clickOnElement(By loc, String name) {
		if(isElementPresent(loc)) {
			wait.until(ExpectedConditions.elementToBeClickable(loc));
			driver.findElement(loc).click();
			logger.log(LogStatus.INFO,"INFO: Clicked on '"+name+"' ");
			System.out.println("INFO: Clicked on "+name);
		}else {
			assertFailWithOutException("FAIL: The '"+name+"' does not displayed on the home page.");
		}
	}
	

	public void ClickMethod(WebElement loc, String name) {
		if(isElementPresent(loc)) {
			wait.until(ExpectedConditions.elementToBeClickable(loc));
			loc.click();
			logger.log(LogStatus.INFO,"INFO: Clicked on '"+name+"' ");
			System.out.println("INFO: Clicked on "+name);
		}else {
			assertFailWithOutException("FAIL: The '"+name+"' does not displayed on the home page.");
		}
	}

	public void switchFrame(String id) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(id));
	}

	private boolean verifyAvailabilityOfURL(String url){
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.connect();
			return true;
		} catch (final MalformedURLException e) {
			throw new IllegalStateException("Exception: Bad Email URL: " + url, e);
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isElementPresentWithWait(By by, int sec) {
		 WebDriver wd = null;
		WebDriverWait wait1 =new WebDriverWait(wd, Duration.ofSeconds(30));
		try {
			wait1.until(ExpectedConditions.visibilityOfElementLocated(by));
			wait1.until(ExpectedConditions.elementToBeClickable(by));
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} catch (TimeoutException e) {
			return false;
		} finally {
		}
	}

	public boolean isElementPresentWithWait(WebElement loc, int sec) {
		WebDriver wd = null;
		WebDriverWait wait1 =new WebDriverWait(wd, Duration.ofSeconds(30));
		try {
			wait1.until(ExpectedConditions.visibilityOf(loc));
			wait1.until(ExpectedConditions.elementToBeClickable(loc));
			loc.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} catch (TimeoutException e) {
			return false;
		} finally {
		}
	}

	public String getTextFromLocationWithWait(By loc, int a) {
		if (isElementPresentWithWait(loc, a)) {
			return driver.findElement(loc).getText();
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
			return null;
		}
	}

	public void verifyAssertNotEqualIntegers(int actual, int expected, String passMessage, String failMessage) {
		try {
			Assert.assertNotEquals(actual, expected, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage);
		} catch (Exception e) {
			assertFailWithException(failMessage, e);
		}
	}

	public void verifyAssertNotEqualString(String actual, String expected, String passMessage, String failMessage) {
		try {
			Assert.assertNotEquals(actual, expected, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage);
		} catch (Exception e) {
			assertFailWithException(failMessage, e);
		}
	}

	public void scrollDownToWebElement(WebElement loc)
	{
		js.executeScript("arguments[0].scrollIntoView(true);",loc);
	}

	public void selectDropdownValue(By loc, int number) {
		wait.until(ExpectedConditions.elementToBeClickable(loc));
		Select index = new Select(driver.findElement(loc));
		index.selectByIndex(number);
	}

	public void selectDropdownFirstValue(By loc) {
		wait.until(ExpectedConditions.elementToBeClickable(loc));
		Select index = new Select(driver.findElement(loc));
		index.selectByIndex(1);
	}

	public void uploadImage(String imagePath) throws AWTException{
		File FilePath = new File(imagePath);
		StringSelection stringSelection = new StringSelection(null);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		stringSelection = new StringSelection(FilePath.getAbsolutePath());
		clipboard.setContents(stringSelection, null);
		waitFor(3000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public boolean hitURL(String actualURL, String methodName, WebDriver driver, ExtentTest logger) {
		CommonActions ca = new CommonActions(methodName,driver,logger);
		try {
			if(verifyAvailabilityOfURL(actualURL)){
				driver.navigate().to(actualURL);
			    //percy.snapshot("First");
				logger.log(LogStatus.INFO, "INFO: Application is up and running, Login Page Opened.");
				System.out.println("INFO: Application is up and running, Home Page Opened.");
				System.out.println("--------------");
				return true;
			} else {
				ca.assertFailWithOutException("FAIL: Ap	plication URl \"" + actualURL + "\" does not hit and application page does not open.");
				return false;
			}
		} catch (Exception e) {
			ca.assertFailWithException("FAIL: Application URl \"" + actualURL + "\" does not hit and application page does not open.", e);
			return false;
		}
	}

	public void enterTabButton(By loc){
		WebElement webElement = driver.findElement(loc);//You can use xpath, ID or name whatever you like
		webElement.sendKeys(Keys.TAB);
	}

	public void enterEnterButton(By loc){
		WebElement webElement = driver.findElement(loc);//You can use xpath, ID or name whatever you like
		webElement.sendKeys(Keys.ENTER);
	}

	public void assertFailWithException(String failMessage, Exception e) {
		String screenShot_path = ru.captureScreen(driver, ru.getReportPath(), methodName);
		String image = logger.addScreenCapture(screenShot_path);
		logger.log(LogStatus.FAIL, failMessage, image);
		e.printStackTrace();
		Assert.fail(failMessage);
	}

	public void selectAttachment(String filepath){
		try{

			StringSelection ss = new StringSelection(filepath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}catch(AWTException e){
			e.printStackTrace();
		}
	}

	public void openNewWindow(){
		try{
			Robot r = new Robot();                          
			r.keyPress(KeyEvent.VK_CONTROL); 
			r.keyPress(KeyEvent.VK_T); 
			waitFor(2000);
			r.keyRelease(KeyEvent.VK_CONTROL); 
			r.keyRelease(KeyEvent.VK_T);    
			//To switch to the new tab
			String parent = driver.getWindowHandle();
			//System.out.println("PARENT 1 IS:"+ parent);
			Set<String> allwindows = driver.getWindowHandles();
			//System.out.println("allwindow1 IS:"+ allwindows);
			for (String child : allwindows) {
				if (!parent.equalsIgnoreCase(child)) {
					waitFor(1000);
					//System.out.println("Child 1 is:"+child);
					driver.switchTo().window(child);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void refreshPage(){
		driver.navigate().refresh();
	}

	public void closeNewlyOpenedWindow(){
		String parent = driver.getWindowHandle();
		Set<String> allwindows = driver.getWindowHandles();
		for (String child : allwindows) {
			waitFor(2000);
			if (!parent.equalsIgnoreCase(child)) {
				driver.close();
				System.out.println("Window closed:"+parent);
				driver.switchTo().window(child);
			}
		}
	}

	public void assertFailWithExceptionWithList(String failMessage, Exception e, List<String> actual, List<String> expected) {
		String screenShot_path = ru.captureScreen(driver, ru.getReportPath(), methodName);
		String image = logger.addScreenCapture(screenShot_path);
		logger.log(LogStatus.FAIL, failMessage, image);
		e.printStackTrace();
		System.out.println("Expected list :-");
		printListString(expected);
		System.out.println("Actual List :-");
		printListString(actual);
		Assert.fail(failMessage);
	}

	public void assertFailWithOutException(String failMessage) {
		String screenShot_path = ru.captureScreen(driver, ru.getReportPath(), methodName);
		String image = logger.addScreenCapture(screenShot_path);
		logger.log(LogStatus.FAIL, failMessage, image);
		Assert.fail(failMessage);
	}

	public void assertFailWithOutExceptionWithOutImage(String failMessage) {
		logger.log(LogStatus.FAIL, failMessage);
		Assert.fail(failMessage);
	}

	public void printListString(List<String> list) {
		for (String each : list) {
			System.out.println(each);
		}
	}

	public boolean isElementPresentWithWait(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		wait.until(ExpectedConditions.elementToBeClickable(by));
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} finally {
		}
	}

	public boolean isElementDisplayed(By loc) {
		if (driver.findElement(loc).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementDisplayed(WebElement loc) {
		if (loc.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementPresentBySize(By loc){
		if(driver.findElements(loc).size() != 0)
		{
			return true;
		}else{
			return false;	
		}	
	}

	public void waitFor(int i) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void waitForMax(int i) {
		try {
			Thread.sleep(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitSleep(int i) {
		try {
		//	Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void navigateToPreviousPage() {
		try {
			driver.navigate().back();
		} catch (Exception e) {

		}
	}
	public void clickOnElementThroughAction(By loc) {
		if (isElementPresent(loc)) {
			wait.until(ExpectedConditions.elementToBeClickable(loc));
			WebElement el = driver.findElement(loc);
			Actions builder = new Actions(driver);
			builder.moveToElement( el ).click( el );
			builder.perform();
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}	

	public void clickOnElementWithJSExecutor(By loc ) {
		if (isElementPresent(loc)) {
			wait.until(ExpectedConditions.elementToBeClickable(loc));
			JavascriptExecutor je=(JavascriptExecutor)driver;
			WebElement element = driver.findElement(loc);
			je.executeScript("arguments[0].click();", element);			
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}	

	public void clickOnElementWithJSExecutor(WebElement loc) {
		if (isElementPresent(loc)) {
			wait.until(ExpectedConditions.elementToBeClickable(loc));
			JavascriptExecutor je=(JavascriptExecutor)driver;
			WebElement element =loc;
			je.executeScript("arguments[0].click();", element);			
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}	

	public void clearTextBox(By loc) {
		if (isElementPresent(loc)) {
			driver.findElement(loc).clear();
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}

	public void clearTextBox(WebElement loc) {
		if (isElementPresent(loc)) {
			loc.clear();
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}

	public void enterTextInTextBox(By loc, String text) {
		if (isElementPresent(loc)) {
			driver.findElement(loc).click();
			clearTextBox(loc);
			driver.findElement(loc).sendKeys(text);
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}

	public void enterTextInTextBox(WebElement loc, String text) {
		if (isElementPresent(loc)) {
			loc.click();
			clearTextBox(loc);
			loc.sendKeys(text);
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}

	public void enterTextInTextBoxUsingStringBuilder(By loc, StringBuilder stringBuilder) {
		if (isElementPresent(loc)) {
			driver.findElement(loc).click();
			driver.findElement(loc).sendKeys(stringBuilder);
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}

	public void enterTextInTextBoxWithOutClicking(By loc, String text) {
		if (isElementPresent(loc)) {
			clearTextBox(loc);
			driver.findElement(loc).sendKeys(text);
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}

	public void enterDataInTextBox(By loc, String text) {
		if (isElementPresent(loc)) {
			driver.findElement(loc).sendKeys(text);
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
		}
	}

	public String getTextFromLocation(By loc) {
		if (isElementPresent(loc)) {
			return driver.findElement(loc).getText();
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
			return null;
		}
	}

	public String getTextFromLocation(WebElement loc) {
		if (isElementPresent(loc)) {
			return loc.getText();
		} else {
			assertFailWithOutException("FAIL: Location does not found..LOC-" + loc);
			return null;
		}
	}

	public String getTextFromLocationUsingAttribute(By loc, String attribute) {
		if (isElementPresent(loc)) {
			return driver.findElement(loc).getAttribute(attribute);
		} else {
			Assert.fail("FAIL: Location does not found..LOC-" + loc);
			return null;
		}
	}

	public String getTextFromLocationUsingAttribute(WebElement loc, String attribute) {
		if (isElementPresent(loc)) {
			return loc.getAttribute(attribute);
		} else {
			Assert.fail("FAIL: Location does not found..LOC-" + loc);
			return null;
		}
	}

	public String getPageTitle() {
		return driver.getTitle();
	}
	/*
	 * final JavascriptExecutor jse = (JavascriptExecutor) driver; JSONObject
	 * executorObject = new JSONObject(); JSONObject argumentsObject = new
	 * JSONObject();
	 */
	  
	 

	public void verifyAssertTrue(boolean condition, String passMessage, String failMessage) {
		try {
			Assert.assertTrue(condition, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage); 
		} catch (Exception e) {
			assertFailWithException(failMessage, e);
		}
	}
	
	/*
	 * public void FailedStatusOnBrowserStack() { final JavascriptExecutor js =
	 * (JavascriptExecutor) driver; JSONObject executorObject = new JSONObject();
	 * JSONObject argumentsObject = new JSONObject(); argumentsObject.put("status",
	 * "<passed/failed>"); executorObject.put("action", "setSessionStatus");
	 * executorObject.put("arguments", argumentsObject);
	 * js.executeScript(String.format("browserstack_executor: %s", executorObject));
	 * js.
	 * executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\"}}"
	 * ); driver.close(); // driver.quit(); }
	 */
	
	/*
	 * public void PassedStatusOnBrowserStack() { final JavascriptExecutor js =
	 * (JavascriptExecutor) driver; JSONObject executorObject = new JSONObject();
	 * JSONObject argumentsObject = new JSONObject(); argumentsObject.put("status",
	 * "<passed/failed>"); executorObject.put("action", "setSessionStatus");
	 * executorObject.put("arguments", argumentsObject);
	 * js.executeScript(String.format("browserstack_executor: %s", executorObject));
	 * js.
	 * executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}"
	 * ); }
	 */

	public void verifyAssertFalse(boolean condition, String passMessage, String failMessage) {
		try {
			Assert.assertFalse(condition, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage);
		} catch (Exception e) {
			assertFailWithException(failMessage, e);
		}
	}

	public void verifyAssertEqualString(String actual, String expected, String passMessage, String failMessage) {
		try {
			Assert.assertEquals(actual, expected, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage);
		} catch (Exception e) {
			assertFailWithException(failMessage, e);
		}
	}

	public void verifyAssertEqualString1(String actual, WebElement expected, String passMessage, String failMessage) {
		try {
			Assert.assertEquals(actual, expected, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage);
		} catch (Exception e) {
			assertFailWithException(failMessage, e);
		}
	}

	public void verifyAssertEqualIntegers(int actual, int expected, String passMessage, String failMessage) {
		try {
			Assert.assertEquals(actual, expected, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage);
		} catch (Exception e) {
			assertFailWithException(failMessage, e);
		}
	}

	public void verifyAssertEqualListString(List<String> actual, List<String> expected, String passMessage, String failMessage) {
		try {
			Assert.assertEquals(actual, expected, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage);
		} catch (Exception e) {
			System.out.println("Expected list :-");
			printListString(expected);
			System.out.println("Actual List :-");
			printListString(actual);
			assertFailWithExceptionWithList(failMessage, e, actual, expected);
		}
	}

	public void verifyAssertEquals(Object actual, Object expected, String passMessage, String failMessage) {
		try {
			Assert.assertEquals(actual, expected, failMessage);
			System.out.println(passMessage);
			logger.log(LogStatus.PASS, passMessage);
		} catch (Exception e) {
			assertFailWithException(failMessage, e);
		}
	}

	public boolean compareStringWithEquals(String actual, String expected) {
		if (actual.equals(expected)) {
			return true;
		} else {
			System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	public boolean compareStringWithEqualsWithoutMsg(String actual, String expected) {
		if (actual.equals(expected)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean compareIntegerWithInteger(int actual, int expected) {
		if (actual == expected) {
			return true;
		} else {
			System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	public boolean compareIntegerWithIntegerWithOutFailMsg(int actual, int expected) {
		if (actual == expected) {
			return true;
		} else {
			//System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	public boolean compareIntegerWithDouble(double actual, double expected) {
		if (actual == expected) {
			return true;
		} else {
			System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	public boolean compareListStringWithEquals(List<String> actual, List<String> expected) {
		if (actual.equals(expected)) {
			return true;
		} else {
			System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	public boolean compareListOfListStringWithEquals(List<List<String>> actual, List<List<String>> expected) {
		if (actual.equals(expected)) {
			return true;
		} else {
			//System.out.println("Expected Object Size is - " + expected.size() + "\n" + "Actual Object Size is - "+actual.size());
			System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	public boolean compareListOfListStringContainsAll(List<List<String>> actual, List<List<String>> expected) {
		if (actual.containsAll(expected)) {
			return true;
		} else {
			System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public boolean compareListStringWithContains(List<String> actual, List<String> expected) {
		if (actual.contains(expected)) {
			return true;
		} else {
			System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public boolean compareListStringWithContainsWithOutFailMsg(List<String> actual, List<String> expected) {
		if (actual.contains(expected)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean compareListStringWithContainsAll(List<String> actual, List<String> expected) {
		if (actual.containsAll(expected)) {
			return true;
		} else {
			System.out.println("Expected is - " + expected + "\n" + "Actual is - " + actual);
			return false;
		}
	}

	public boolean isCheckBoxSelected(By loc) {
		if (isElementPresent(loc)) {
			if (driver.findElement(loc).isSelected()) {
				return true;
			} else {
				return false;
			}
		} else {
			assertFailWithOutException("FAIL: Locator does not displayed. Loc is - " + loc);
			return false;
		}
	}

	public boolean isCheckBoxSelected(WebElement loc) {
		if (isElementPresent(loc)) {
			if (loc.isSelected()) {
				return true;
			} else {
				return false;
			}
		} else {
			assertFailWithOutException("FAIL: Locator does not displayed. Loc is - " + loc);
			return false;
		}
	}

	public boolean isFieldEnabled(By loc) {
		if (isElementPresent(loc)) {
			if (driver.findElement(loc).isEnabled()) {
				return true;
			} else {
				return false;
			}
		} else {
			assertFailWithOutException("FAIL: Locator does not displayed. Loc is - " + loc);
			return false;
		}
	}

	public boolean isFieldEnabled(WebElement loc) {
		if (isElementPresent(loc)) {
			if (loc.isEnabled()) {
				return true;
			} else {
				return false;
			}
		} else {
			assertFailWithOutException("FAIL: Locator does not displayed. Loc is - " + loc);
			return false;
		}
	}

	public void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			waitFor(5000);
			alert.accept();
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText().trim();
			System.out.println("Alert data: " + alertText);
			alert.dismiss();
		} catch (NoAlertPresentException ex) {
		}
	}

	public boolean isRadioButtonSelected(By loc) {
		if (isElementPresent(loc)) {
			if (driver.findElement(loc).isSelected()) {
				return true;
			} else {
				return false;
			}
		} else {
			assertFailWithOutException("FAIL: Locator does not displayed. Loc is -" + loc);
			return false;
		}
	}

	public boolean isRadioButtonSelected(WebElement loc) {
		if (isElementPresent(loc)) {
			if (loc.isSelected()) {
				return true;
			} else {
				return false;
			}
		} else {
			assertFailWithOutException("FAIL: Locator does not displayed. Loc is -" + loc);
			return false;
		}
	}

	public String windowSwitchTochildReturnPageTitle(){
		String pageTitle=null;
		String parent = driver.getWindowHandle();
		Set<String> allwindows = driver.getWindowHandles();
		for (String child : allwindows) {
			if (!child.equalsIgnoreCase(parent)) {
				driver.switchTo().window(child);
				pageTitle=driver.getTitle();
				driver.close();
			}
			driver.switchTo().window(parent);
		}
		return pageTitle;	
	}

	public void windowSwitchTochild(){
		String parent = driver.getWindowHandle();
		Set<String> allwindows = driver.getWindowHandles();
		waitFor(1000);
		for (String child : allwindows) {
			if (!parent.equalsIgnoreCase(child)) {
				driver.switchTo().window(child);
			}
		}
		allwindows.removeAll(allwindows);
	}

	public int getCount(By loc){
		waitFor(1000);
		List<WebElement> locatedPath= driver.findElements(loc);
		int count=0;
		for(@SuppressWarnings("unused") WebElement i: locatedPath){
			count++;
		}
		return count;
	}

	public int getCount1(WebElement loc){
		waitFor(1000);
		List<WebElement> locatedPath=driver.findElements((By) loc);
		int count=0;
		for(@SuppressWarnings("unused") WebElement i: locatedPath){
			count++;
		}
		return count;
	}

	public void moveToElement(By loc){
		WebElement element = driver.findElement(loc);
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();
	}
	public void moveToElementWithClick(By loc){
		WebElement element = driver.findElement(loc);
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();
		actions.click();
	}

	public void moveToElement(WebElement loc){
		Actions actions = new Actions(driver);
		actions.moveToElement(loc);
		actions.perform();
	}

	public void MoveToPreviousPage()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.history.go(-1)");
	}

	public boolean compareStringWithEqualIgnoreCase(String actual, String expected) {
		if (actual.equalsIgnoreCase(expected)) {
			return true;
		} else {
			System.out.println("Expected is -" + expected + "\n" + "Actual is -" + actual);
			return false;
		}
	}

	public void assertFailWithOutException(String failMessage, String methodName, WebDriver driver, ExtentTest logger) {
		String screenShot_path = ru.captureScreen(driver, ru.getReportPath(), methodName);
		String image = logger.addScreenCapture(screenShot_path);
		logger.log(LogStatus.FAIL, failMessage, image);
		//jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\"}}");
		Assert.fail(failMessage);
	}

	public void scrollUP() {
		js.executeScript("scroll(0, -500);");
	}

	public void scrollDown() {
		js.executeScript("scroll(0, 250);");
	}

	public void scrollByLine() {
		js.executeScript("window.scrollByLines(2)");
	}

	public boolean addedTagName(By loc){
		Boolean  a1 = driver.findElements(loc).size() > 0;
		if(a1==true){
			return true;
		}else{
			return false;
		}
	}
}
