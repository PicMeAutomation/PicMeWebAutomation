package Collections;

import java.awt.AWTException;

import org.testng.annotations.Test;

import Actions.CollectionsActions.CollectionsActions;
import Actions.CommonActions.CommonActions;
import Actions.CommonNavigations.CommonNavigations;
import Actions.LoginAndSignupActions.LoginAndSignupActions;
import Data.ApplicationCredentials.LogInCredentials;
import Locators.CollectionsLocators.CollectionsLocators;
import Locators.LoginAndSignupLocators.LoginAndSignupLocators;
import Utilities.SuiteBase.SuiteBase;

public class VerifyTheFunctionalitiesOfCollections extends SuiteBase {

	CommonActions ca;
	LoginAndSignupActions loginActions;
	LoginAndSignupLocators LoginLoc;
	LogInCredentials LogInCred;
	CollectionsActions CollectionsAction;
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
			CollectionsAction = new CollectionsActions(pageTitle, driver, logger);
			url = new CommonNavigations(methodName, driver, logger);
			url.openURLForLoginToApplication();
		} catch (Exception e) {
			System.out.println("❌ Exception in test: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	@Test(priority = 2, groups = { "Smoke", "Regression", "Web" })
	public void verifyFunctionalityOfCreatingAccount() {
		loginActions.clickOnGetStartedBtnOnHomePage();
		ca.iWaitForPageToLoad();
		loginActions.EnterEmailForCreateAccountAndClickContinueBtn();
		ca.iWaitForPageToLoad();
		loginActions.EnterPasswordAndClickContinueBtn();
		ca.iWaitForPageToLoad();
		loginActions.EnterVarificationCodeAfterPassword();
		ca.iWaitForPageToLoad();
		ca.verifyAssertTrue(loginActions.verifyLoggedInpage(), 
				"PASS: User account is created successfully.",
				"Fail: Something get wrong while creating account.");
	}
	@Test(priority = 3, groups = { "Smoke", "Regression", "Web" })
	public void verifyFunctionalityOfCreateNewCollection() throws AWTException {
		loginActions.clickOnAllowPopUp();
		ca.iWaitForPageToLoad();
		loginActions.clickOnContinueBtn();
		ca.iWaitForPageToLoad();
		loginActions.EnterPasswordAndClickContinueBtn();
		ca.iWaitForPageToLoad();
		loginActions.clickOnAllowPopUp();
		CollectionsAction.clickOnPlusIconForCreateNewCollection();
		ca.iWaitForPageToLoad();
		CollectionsAction.CreateNewCollection();
		ca.iWaitForPageToLoad();
		CollectionsAction.uploadPhotosOnCreatedCollection();
		ca.verifyAssertTrue(CollectionsAction.verifyPhotoIsUploadedInCollection(), 
				"PASS: Collection is created and photo is uploaded successfully.",
				"Fail: Something get wrong while creating Collection or Uploading Photo.");
		System.out.println("--------------");
	}
	
	public void verifyFunctionalityOfShareCollection() {
		
	}

}
