package Data.ApplicationCredentials;

import browserstack.shaded.commons.lang3.RandomStringUtils;

public class LogInCredentials {
	
	public String URL = "https://picme.com/";
	public String URL1 = "https://dev.app.picme.com/";
	public String ProductionURL = "https://dev.app.picme.com/login-or-signup";
	public String LoginURL = "";
	public String RegisterURL = "";
	
	public String AdminUserName = "";
	public String AdminUserName1 = "";
	public String AdminUserPassword = "";
	
	public String validusername="";
	public String validpassword="";
	public String invaliduser="";
	public String invalidpass="";
	
	
	///////////////////Validations/////////////////////////////////
	public String BlankUserPass="";
	public String BlankUser="";
	public String BlankPass="";
	public String InvalidPassValidation="";
	public String InvalidPass="";
	public String InvalidUser="";
	public String InvalidUserMessage="";
	public String InvalidUserNameMessage = "";
	public String InvalidUsernameMesg="";
	
	public String GoogleAccountTitle ="";

	//////////// FOR RANDON USERNAME DATA///////////////
	public String randomName(){
		String aplhabet="0123456789";
		return RandomStringUtils.random(5, aplhabet);
	}
	
	
	

}
