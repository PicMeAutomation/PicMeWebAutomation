package Utilities.SuiteBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.seleniumhq.jetty9.server.Authentication.Failure;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.browserstack.local.Local;
import com.mysql.jdbc.Driver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import Utilities.Factory.DriverFactory;
import Utilities.Factory.ReportFactory;
import Utilities.ReportUtilities.ReportUtilities;
import io.percy.selenium.Percy;

public class SuiteBase {
	public WebDriver driver;
	public JavascriptExecutor js;
	public WebDriverWait wait;
	public ExtentReports extent;
	public ExtentTest logger;
	//public Logger log;
	public String methodName = "";
	public Percy percy;


	protected static ReportUtilities ru = new ReportUtilities();

	/*
	 * @BeforeTest(alwaysRun = true) public synchronized void Report() { if
	 * (ReportFactory.reportFlag == 0) { ReportFactory.reportFlag++;
	 * ru.setTheBuildPath(); } log = Logger.getRootLogger();
	 * log.setLevel(Level.OFF); String test = getClass().getSimpleName();
	 * System.out.println("********************Test Case :\"" + test +
	 * "\": Execution started.********************"); extent =
	 * ReportFactory.getExtentReport(); logger = ReportFactory.getTest(test); }
	 */
	public Logger log = LoggerFactory.getLogger(getClass());

	@BeforeTest(alwaysRun = true)
	public synchronized void Report() {
		java.util.logging.Logger.getLogger("org.openqa.selenium.remote.http.WebSocket").setLevel(java.util.logging.Level.SEVERE);

	    if (ReportFactory.reportFlag == 0) {
	        ReportFactory.reportFlag++;
	        ru.setTheBuildPath();
	    }

	    String test = getClass().getSimpleName();
	    System.out.println("******************** Test Case: " + test + "  Execution Started 🚀 ********************");
	    java.util.logging.Logger.getLogger("org.openqa.selenium.devtools").setLevel(java.util.logging.Level.SEVERE);


	    extent = ReportFactory.getExtentReport();
	    logger = ReportFactory.getTest(test);
	}


	@BeforeClass(alwaysRun = true)
	@Parameters("browserName")
	public void CreateWebDriver(String browser) {
	    System.out.println("✅ @BeforeClass started for browser: " + browser);
	    try {
	        driver = DriverFactory.getInstance().getDriver(browser, browser);
	        if (driver == null) {
	            System.out.println("❌ WebDriver is null!");
	            throw new RuntimeException("WebDriver is null for browser: " + browser);
	        }
	        //System.out.println("➡ Deleting cookies");
	        driver.manage().deleteAllCookies();

	        System.out.println("➡ Maximizing window");
	        driver.manage().window().maximize();

	        //System.out.println("✅ Driver setup complete");
	        logger.log(LogStatus.INFO, "INFO: Browser Open.");

	    } catch (Exception e) {
	        System.out.println("❌ Exception in @BeforeClass: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("BeforeClass failed", e);
	    }
	}



	
	@AfterClass(alwaysRun = true)
	public void CloseBrowserStackLocal() throws InterruptedException, IOException {
        String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win") == false) {
			Local bsLocal = new Local();
			System.out.println(bsLocal.isRunning());
			String port = "45691";
			String command = "lsof -t -i :" + port;
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			String pid = null;
			// Read the output, which should be the PID
			if ((line = reader.readLine()) != null) {
				pid = line.trim(); // Get the PID
			}
			process.waitFor();
			String commandKill = "kill -9 " + pid; // Command to kill the process
			Process processKill = Runtime.getRuntime().exec(commandKill);
			processKill.waitFor(); // Wait for the process to finish
			System.out.println("<----------process finished------------>");
		}
	}
	
	@AfterTest(alwaysRun = true)
	public void closeWebDriver() {
	    System.out.println("✅ @AfterTest: closing browser...");
	    try {
	        WebDriver activeDriver = DriverFactory.getInstance().getAnyDriver();
	        if (activeDriver != null) {
	            DriverFactory.getInstance().removeDriver();
	           // System.out.println("🔻 Quitting WebDriver...");
	        } else {
	            //System.out.println("⚠️ No WebDriver to quit.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Reporting cleanup
	    String test = getClass().getSimpleName();
	    ReportFactory.closeTest(test);
	    ReportFactory.closeReport();

	    if (logger != null) {
	        logger.log(LogStatus.INFO, "INFO: Browser Closed.");
	    }

	    System.out.println("******************** Test Case :\"" + test + "\": Execution Stop. ********************");
	}


	  
	@BeforeMethod
	public void nameBefore(Method method) {
		methodName = method.getName();
	}
	 
	@AfterMethod
	public void verifyMethod(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenShot_path = ru.captureScreen(driver, ru.getReportPath(), methodName);
			String image = logger.addScreenCapture(screenShot_path);
			logger.log(LogStatus.FAIL, "Verification " + methodName + " fail.", image);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, "Verification of " + methodName + " performed successfully.");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Verification of " + methodName + " Skip.");
		}
	}
}
