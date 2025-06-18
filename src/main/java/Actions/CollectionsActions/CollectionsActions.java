package Actions.CollectionsActions;

import java.awt.AWTException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import Actions.CommonActions.CommonActions;
import Data.LoginAndSignupDatas.LoginAndSignupPageDatas;
import Locators.CollectionsLocators.CollectionsLocators;
import Locators.LoginAndSignupLocators.LoginAndSignupLocators;

public class CollectionsActions {
	private WebDriver driver;
	private ExtentTest logger;
	private CommonActions ca;
	private CollectionsLocators CollectionsLoc;
	private LoginAndSignupLocators loginLoc;
	private LoginAndSignupPageDatas LoginSignupData;

	public CollectionsActions(String methodName, WebDriver wd, ExtentTest logger) {
		this.logger = logger;
		this.ca = new CommonActions(methodName, wd, logger);
		this.loginLoc = new LoginAndSignupLocators();
		this.LoginSignupData = new LoginAndSignupPageDatas();
		this.CollectionsLoc = new CollectionsLocators();
		this.driver = wd;
	}
	public void clickOnPlusIconForCreateNewCollection() {
		ca.clickOnElement(CollectionsLoc.PlusIconCollection, "PlusIconCollection");
	}
	public void CreateNewCollection() {
		ca.enterTextInTextBox(CollectionsLoc.CollectionName, "Testing");
		ca.waitFor(2000);
		ca.clickOnElement(CollectionsLoc.CreateCollContinueBtn, "CreateCollContinueBtn");
	}
	public void uploadPhotosOnCreatedCollection() throws AWTException {
		ca.waitForElementToAppear(CollectionsLoc.UploadBtn, 10);
		if(ca.isElementPresent(CollectionsLoc.UploadBtn)) {
		ca.waitForMax(5000);
		ca.clickOnElement(CollectionsLoc.UploadBtn, "UploadBtn");
		ca.waitForMax(5000);
		ca.uploadImage("D:\\PicMeProject\\Photos\\Car1.jpg");}
	}
	public boolean verifyPhotoIsUploadedInCollection() {
		ca.waitForMax(5000);
		ca.waitForElementToAppear(CollectionsLoc.verifyUploadedPhoto, 20);
		if(ca.isElementPresent(CollectionsLoc.verifyUploadedPhoto)) {
			return true;
		}
		return false;
	}
}
