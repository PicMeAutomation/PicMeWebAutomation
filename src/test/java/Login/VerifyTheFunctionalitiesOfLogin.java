package Login;

import org.testng.annotations.Test;

import Actions.CommonActions.CommonActions;
import Actions.CommonNavigations.CommonNavigations;
import Actions.LoginAndSignupActions.LoginAndSignupActions;
import Data.ApplicationCredentials.LogInCredentials;
import Locators.LoginAndSignupLocators.LoginAndSignupLocators;
import Utilities.SuiteBase.SuiteBase;

public class VerifyTheFunctionalitiesOfLogin extends SuiteBase {

	CommonActions ca;
	LoginAndSignupActions loginActions;
	LoginAndSignupLocators LoginLoc;
	LogInCredentials LogInCred;
	CommonNavigations url;
	String pageTitle;

	@Test(priority = 1, groups = { "Smoke", "Regression", "Web" })
	public void VerifyTestPrerequisites() {
		try {
			System.out.println("🚀 Test method started");
			ca = new CommonActions(methodName, driver, logger);
			loginActions = new LoginAndSignupActions(pageTitle, driver, logger);
			LoginLoc = new LoginAndSignupLocators();
			LogInCred = new LogInCredentials();
			url = new CommonNavigations(methodName, driver, logger);
			url.openURLForLoginToApplication();
		} catch (Exception e) {
			System.out.println("❌ Exception in test: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	@Test(priority = 2, groups = { "Smoke", "Regression", "Web" })
	public void verifyLoginToApplicationWithValidCredentials() {
		loginActions.clickOnGetStartedBtnOnHomePage();
		ca.iWaitForPageToLoad();
		loginActions.EnterExistingEmailForLogin();
		ca.iWaitForPageToLoad();
		loginActions.EnterPasswordAndClickContinueBtn();
		ca.iWaitForPageToLoad();
		ca.verifyAssertTrue(loginActions.verifyUserIsLoggedIn(), 
				"PASS: User is logged in successfully.",
				"Fail: Something get wrong while creating account.");
		System.out.println("--------------");
	}
}
