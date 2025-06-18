package Locators.CollectionsLocators;

import org.openqa.selenium.By;

public class CollectionsLocators {
	
	public By PlusIconCollection = By.xpath("//p[contains(text(), 'Collections')]//following::div[@class='viewDraws icon t-picme-false--5552196-button'][1]");
	public By CollectionName = By.xpath("//input[@placeholder='Name']");
	public By CreateCollContinueBtn = By.xpath("//p[@class='hCenter vCenter t-picme-false--5552196-card-important-button']");
	public By UploadBtn = By.xpath("//div[@class='viewDraws icon hCenter vCenter t-picme-false--5552196-droptarget-button-important']");
	public By verifyUploadedPhoto = By.xpath("//img[@class='scaleType-Crop viewDraws t-picme-false--5552196-gridimage']");
}
