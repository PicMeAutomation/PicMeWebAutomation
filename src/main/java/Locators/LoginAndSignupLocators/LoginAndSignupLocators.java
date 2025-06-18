package Locators.LoginAndSignupLocators;

import org.openqa.selenium.By;

public class LoginAndSignupLocators {
	
	public By verifyApplicationURL = By.xpath("//p[contains(text(), 'Guest Invitation')]");
	public By GetStartedBtnOnHOmePage = By.xpath("//h2[contains(text(), 'Use PicMe For')]//preceding::a[contains(text(), 'Get Started')]");
	public By ContinueBtnOnLoginPage = By.xpath("//button[@class='kiteui-stack clickable optColChild transition padded t-picme-false--5552196-auth-card-important-btn']");
	public By emailField = By.xpath("//input[@placeholder='Email or Phone']");
	public By passwordField = By.xpath("//input[@placeholder='Password']");
	public By ContinueBtnSignupPage = By.xpath("//p[@class='hCenter vCenter t-picme-false--5552196-auth-card-important-btn']");
	public By verificationCodeField = By.xpath("//input[@placeholder='Verification Code']");
	public By emailContent = By.xpath("//div[@id='mail']");
	public By ContinueBtnAfterVerifactionCode = By.xpath("//p[@class='hCenter vCenter t-picme-false--5552196-auth-card-important-btn']");
	public By AllowBtn = By.xpath("//p[contains(text(), 'Allow')]");
	public By profileBtn = By.xpath("//p[@class='hCenter vCenter t-picme-false--5552196-bar-important-button-hed-h3']");

}
