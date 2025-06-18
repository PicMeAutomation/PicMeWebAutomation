package Data.LoginAndSignupDatas;

import browserstack.shaded.commons.lang3.RandomStringUtils;

public class LoginAndSignupPageDatas {
	
	public String randomName(){
		String aplhabet="abcdefghijklmnopqrstuvwxyz";
		return RandomStringUtils.random(4, aplhabet);
	}
	
	public String HomePageTitle = "PicMe";
	public String LoginOrSignupPageTitle = "Login Or Sign Up";
	public final String randonEmail="picme+"+randomName()+"@yopmail.com";
	public  String TestEmail =  "picme@yopmail.com";
	public String TestPassword = "PicMe2025";
	
	public String URL = "https://picme.com/";
	public String URL1 = "https://dev.app.picme.com/in/zHIRZBz_NEag7fXCE_MnEQ";
	public String ProductionURL = "https://dev.app.picme.com/login-or-signup";
	public String LoginURL = "";
	public String RegisterURL = "";

}
