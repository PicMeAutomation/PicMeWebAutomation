package Utilities.Factory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.browserstack.local.Local;

import Utilities.ExcelConfig.ReadExcel;
//import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	Object[][] arr;
	
	
	private String browserName="";
	private static DriverFactory instance = new DriverFactory();
	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private WebDriver globalDriverRef = null; // add this field

	public WebDriver getDriver(String browser, String executionEnv) throws Exception {
	    this.browserName = browser;
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    System.out.println("🛠️ getDriver called with browser: " + browser);

	    WebDriver wd = driver.get();
	    if (wd == null) {
	        System.out.println("🧱 Initializing WebDriver for: " + browserName);

	        if (executionEnv.equalsIgnoreCase("browserstack")) {
	            System.out.println("🌩️ Using BrowserStack Remote WebDriver...");
	            wd = getRemoteDriver(browserName);
	        } else {
	            switch (browserName) {
	                case "Chrome":
	                    System.out.println("🚀 Launching ChromeDriver locally...");
	                    wd = getChromeDriver();
	                    break;
	                default:
	                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
	            }
	        }
	        driver.set(wd);
	        globalDriverRef = wd;
	        capabilities.setCapability("enablePopups", "false");
			capabilities.setCapability("acceptInsecureCerts", "true");
	    }
	    return wd;
	}

	private WebDriver getRemoteDriver(String browserName) throws MalformedURLException {
	    DesiredCapabilities caps = new DesiredCapabilities();
	    String URL="https://hub-cloud.browserstack.com/wd/hub";
	    caps.setCapability("browserName", browserName);
	    caps.setCapability("browserVersion", "latest");

	    caps.setCapability("os", "Windows");
	    caps.setCapability("osVersion", "10");

	    caps.setCapability("browserstack.user", "qatest_zbowfs");
	    caps.setCapability("browserstack.key", "ADweeYzR9K1PGP79rkrs");

	    caps.setCapability("project", "PicMeAutomation");
	    caps.setCapability("build", "Local-vs-BStack");
	    caps.setCapability("name", "Test on " + browserName);

	    return new RemoteWebDriver(caps);
	}



			 public void setDriver(WebDriver driverInstance) {
			        driver.set(driverInstance);
			    }

			 public void removeDriver() {
				    WebDriver currentDriver = driver.get();

				    if (currentDriver != null) {
				        System.out.println("🔻 Quitting ThreadLocal WebDriver...");
				        currentDriver.quit();
				        driver.remove();
				        globalDriverRef = null; // optional safety
				    } else if (globalDriverRef != null) {
				        System.out.println("🔻 Quitting Global WebDriver...");
				        globalDriverRef.quit();
				        globalDriverRef = null;
				    } else {
				        System.out.println("⚠️ No WebDriver found to quit.");
				    }

				    System.out.println("🧹 Driver cleanup complete.");
				}

			 public WebDriver getAnyDriver() {
				    WebDriver local = driver.get();
				    return (local != null) ? local : globalDriverRef;
				}

			 public WebDriver getDriver() {
				    return driver.get(); // internal use only
				}

				public boolean hasDriver() {
				    return driver.get() != null;
				}

			private DriverFactory() {
				//Do-nothing..Do not allow to initialize this class from outside
			}

			public static DriverFactory getInstance() {
				return instance;
			}


			@SuppressWarnings("deprecation")
			private WebDriver getChromeDriver() {
			    System.out.println("⚙️ Setting up ChromeDriver via WebDriverManager...");
			   // System.err.println("🚨 ChromeDriver is being initialized at:");
			    WebDriverManager.chromedriver().setup();
			    ChromeOptions options = new ChromeOptions();
			    WebDriver driver = new ChromeDriver(options);
			    driver.manage().window().maximize();
			    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			    System.out.println("✅ ChromeDriver launched successfully");
			    return driver;
			}
			
			

///////////////////////BrowserStack Configuration/////////////////////////////

			public static String USERNAME="qatest_zbowfs"; // qatest_zbowfs
			public static String ACCESSKEY="ADweeYzR9K1PGP79rkrs"; //ADweeYzR9K1PGP79rkrs

			private WebDriver getChromeDriverForWeb(final String platform, int version, String browser) throws Exception {
					String URL="https://"+USERNAME+":"+ACCESSKEY+"@hub.browserstack.com/wd/hub";
					DesiredCapabilities capabilities = new DesiredCapabilities();
					Local bsLocal = new Local();

					Platform platformName = null;
					switch (platform.toLowerCase())
					{
					case "windows":
						platformName = Platform.WINDOWS;
						break;
					case "xp":
						platformName = Platform.XP;
						break;
					case "linux":
						platformName = Platform.LINUX;
						break;
					case "mac":
						platformName = Platform.MAC;
						break;
					default:
						platformName = Platform.WINDOWS;
						break;
					}
					ChromeOptions options = new ChromeOptions();
					
					
					capabilities.setPlatform(platformName);					
					capabilities.setBrowserName(browser);
					capabilities.setVersion(String.valueOf(version));
					capabilities.setCapability("enablePopups", "false");
					capabilities.setCapability("acceptInsecureCerts", "true");
					Map<String, Object> prefs = new LinkedHashMap<>();
					prefs.put("credentials_enable_service", Boolean.valueOf(false));
					prefs.put("profile.password_manager_enabled", Boolean.valueOf(false));
					prefs.put("autofill.credit_card_enabled", Boolean.valueOf(false));
					
					options.setExperimentalOption("prefs", prefs);
					
					HashMap<String, String> bsLocalArgs = new HashMap<String,String>();
			
					bsLocalArgs.put("key", ACCESSKEY);
					bsLocalArgs.put("forcelocal", "true");
					bsLocal.start(bsLocalArgs);
					
					capabilities.setCapability("browserstack.networkLogs", true);
					capabilities.setCapability("browserstack.local", true);	
					capabilities.setCapability(ChromeOptions.CAPABILITY, options);
					
					URL browserurl= new URL(URL);
					WebDriver wd= new RemoteWebDriver(browserurl, capabilities);
					return wd;
			}
			static {
			    // Suppress Selenium DevTools warnings
			    java.util.logging.Logger.getLogger("org.openqa.selenium.devtools").setLevel(java.util.logging.Level.SEVERE);
			    java.util.logging.Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(java.util.logging.Level.SEVERE);

			    // Suppress Chromium-specific warnings
			    java.util.logging.Logger.getLogger("org.openqa.selenium.chromium").setLevel(java.util.logging.Level.SEVERE);
			    java.util.logging.Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(java.util.logging.Level.SEVERE);

			    // Suppress WebSocket handler and Netty noise
			    java.util.logging.Logger.getLogger("org.openqa.selenium.remote.http.WebSocket").setLevel(java.util.logging.Level.SEVERE);
			    java.util.logging.Logger.getLogger("org.asynchttpclient.netty.handler.WebSocketHandler").setLevel(java.util.logging.Level.SEVERE);
			    java.util.logging.Logger.getLogger("io.netty.channel.DefaultChannelPipeline").setLevel(java.util.logging.Level.SEVERE);
			    java.util.logging.Logger.getLogger("io.netty.util.concurrent.DefaultPromise").setLevel(java.util.logging.Level.SEVERE);
			    java.util.logging.Logger.getLogger("io.netty").setLevel(java.util.logging.Level.SEVERE); // <-- NEW: suppress any other Netty logging

			    // Catch-all for any selenium logging
			    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);
			    
			 // WebDriverManager
			    java.util.logging.Logger.getLogger("io.github.bonigarcia.wdm").setLevel(java.util.logging.Level.SEVERE);
			    java.util.logging.Logger.getLogger("org.asynchttpclient.netty.handler.WebSocketHandler").setLevel(java.util.logging.Level.SEVERE);

			}
			
}