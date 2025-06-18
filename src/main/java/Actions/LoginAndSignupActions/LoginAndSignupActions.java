package Actions.LoginAndSignupActions;

	import java.time.Duration;
	import java.util.ArrayList;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;

	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;

	import com.relevantcodes.extentreports.ExtentTest;

	import Actions.CommonActions.CommonActions;
	import Data.LoginAndSignupDatas.LoginAndSignupPageDatas;
    import Locators.LoginAndSignupLocators.LoginAndSignupLocators;
	import io.appium.java_client.ExecutesDriverScript;

	public class LoginAndSignupActions {
		private WebDriver driver;
		private ExtentTest logger;
		private CommonActions ca;
		private LoginAndSignupLocators loginLoc;
		private LoginAndSignupPageDatas LoginSignupData;

		public LoginAndSignupActions(String methodName, WebDriver wd, ExtentTest logger) {
			this.logger = logger;
			this.ca = new CommonActions(methodName, wd, logger);
			this.loginLoc = new LoginAndSignupLocators();
			this.LoginSignupData = new LoginAndSignupPageDatas();
			this.driver = wd;
		}

		public boolean verifyURLIsHitProperly() {
			if (ca.isElementPresent(loginLoc.GetStartedBtnOnHOmePage)) {
				return true;
			}
			return false;
		}

		public void clickOnGetStartedBtnOnHomePage() {
			if (ca.isElementPresent(loginLoc.GetStartedBtnOnHOmePage)) {
				ca.clickOnElement(loginLoc.GetStartedBtnOnHOmePage, "GetStartedBtnOnHOmePage");
			}
		}
/////////////////////////////Login Suite///////////////////////////////////////////////////////////////
		
		public void EnterExistingEmailForLogin() {
			ca.enterTextInTextBox(loginLoc.emailField, LoginSignupData.TestEmail);
			clickOnContinueBtn();
		}
		public boolean verifyUserIsLoggedIn() {
			if(ca.isElementPresent(loginLoc.profileBtn)) {
				return true;
			}
			return false;
		}
		
/////////////////////////////CreateAccount Suite///////////////////////////////////////////////////////////////
		
		public void EnterEmailForCreateAccountAndClickContinueBtn() {
			ca.enterTextInTextBox(loginLoc.emailField, LoginSignupData.randonEmail);
			String enteredEmail = driver.findElement(loginLoc.emailField).getAttribute("value");
		    System.out.println("📝 Entered Email is: " + enteredEmail);
		    clickOnContinueBtn();
		}

		public void clickOnContinueBtn() {
			ca.clickOnElement(loginLoc.ContinueBtnOnLoginPage, "Continue Btn After Email is Entered");
		}

		public void EnterPasswordAndClickContinueBtn() {
			ca.enterTextInTextBox(loginLoc.passwordField, LoginSignupData.TestPassword);
			ca.clickOnElement(loginLoc.ContinueBtnSignupPage, "Continue Btn Afer Password is Entered");
		}
		public void EnterVarificationCodeAfterPassword() {
		    String inboxName = LoginSignupData.randonEmail;

		    // Open YOPmail in new tab
		    ca.openNewWindow();
		    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		    driver.switchTo().window(tabs.get(1));
		    driver.get("https://yopmail.com");
		    ca.iWaitForPageToLoad();

		    // Enter email and check inbox
		    WebElement emailInput = driver.findElement(By.xpath("//input[@id='login']")); // input box
			emailInput.sendKeys(inboxName);
		    WebElement checkInboxButton = driver.findElement(By.xpath("//i[@class='material-icons-outlined f36']"));
			checkInboxButton.click();

		    // Wait for iframe and switch
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("ifmail"));

		    // Extract 6-digit code
		    String fullText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))).getText();
		    Matcher matcher = Pattern.compile("\\b\\d{6}\\b").matcher(fullText);
		    if (!matcher.find()) throw new RuntimeException("❌ Verification code not found.");
		    String code = matcher.group();
		    System.out.println("✅ Verification Code: " + code);

		    // Return to main tab
		    driver.switchTo().defaultContent();
		    driver.close();
		    driver.switchTo().window(tabs.get(0));
		    ca.iWaitForPageToLoad();

		    // Enter code and continue
		    ca.enterTextInTextBox(loginLoc.verificationCodeField, code);
		    String entered = driver.findElement(loginLoc.verificationCodeField).getAttribute("value");
		    System.out.println("📝 Entered Code: " + entered);
		    ca.clickOnElement(loginLoc.ContinueBtnSignupPage, "Continue Button after valid verification code is entered");
		}

		public boolean verifyLoggedInpage() {
			if (ca.isElementPresent(loginLoc.AllowBtn)) {
				return true;
			}
			return false;
		}
		
		public void clickOnAllowPopUp() {
			ca.waitForElementToAppear(loginLoc.AllowBtn, 10);
			ca.clickOnElement(loginLoc.AllowBtn, "AllowBtn");
		}
		

}
