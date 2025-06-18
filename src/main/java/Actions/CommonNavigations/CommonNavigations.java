package Actions.CommonNavigations;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import Actions.CommonActions.CommonActions;
import Actions.LoginAndSignupActions.LoginAndSignupActions;
import Data.ApplicationCredentials.LogInCredentials;
import Locators.LoginAndSignupLocators.LoginAndSignupLocators;

public class CommonNavigations {
	
	private CommonActions ca;
	private WebDriver driver;
	private ExtentTest logger;
	private String methodName;
	private LogInCredentials LogInCredential;
	private LoginAndSignupActions LoginAction;
	private LoginAndSignupLocators LoginLoc;

	public CommonNavigations(String methodName, WebDriver wd, ExtentTest logger) {
		this.driver = wd;
		this.logger = logger;
		this.methodName = methodName;
		this.ca = new CommonActions(methodName, wd, logger);
		this.LoginAction = new LoginAndSignupActions(methodName, wd, logger);
		this.LogInCredential = new LogInCredentials();
		this.LoginLoc = new LoginAndSignupLocators();
	}

	public void openURLForLoginToApplication() {
		ca.verifyAssertTrue(ca.hitURL(LogInCredential.URL, methodName, driver, logger),
				"PASS: Application URL hit properly.",
				"Fail: Application URl does not hit and application page does not open.");
	}
	
	public void openURLForRegisterToApplication() {
		ca.verifyAssertTrue(
				ca.hitURL(LogInCredential.RegisterURL, methodName, driver, logger),
				"PASS: Application Register URL hit properly.",
				"Fail: Application URl does not hit and application page does not open.");
	}
	
	

}
