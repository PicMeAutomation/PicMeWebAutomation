package Utilities.ReportUtilities;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import Data.UtilitiesData.ResourceData;
import Utilities.ActionUtilities.ResourcesUtilities;

public class ReportUtilities extends ResourcesUtilities {

    private final String configFilePath = "./config/ReportConfig.properties";  // ✅ New safe location

    public String captureScreen(WebDriver driver, String ImagePath, String imageName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        imageName = imageName + "-" + dateFormat.format(date) + ".jpg";
        TakesScreenshot ts = (TakesScreenshot) driver;
        File oScnshot = ts.getScreenshotAs(OutputType.FILE);
        String dest = ImagePath + imageName;
        File oDest = new File(dest);
        try {
            org.apache.commons.io.FileUtils.copyFile(oScnshot, oDest);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageName;
    }

	/*
	 * public String getReportPath() { String returnPath =
	 * getPropValueForProp("Report_File_Path",
	 * ResourceData.getReportConfigFilePath()); return returnPath; }
	 */
    public String getReportPath() {
        String returnPath = getPropValueForProp("Report_File_Path", ResourceData.getReportConfigFilePath());

        // Convert to absolute path
        return System.getProperty("user.dir") + File.separator + returnPath;
    }


    public void setReportPath(String path) {
        try {
            FileInputStream in = new FileInputStream(configFilePath);
            Properties props = new Properties();
            props.load(in);
            in.close();

            props.setProperty("Report_File_Path", path);

            FileOutputStream out = new FileOutputStream(configFilePath);
            props.store(out, null);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBuildRunCount() {
        String beforeCount = getPropValueForProp("Build_Run_Count", configFilePath);
        setBuildRunCount();  // auto-increment for next use
        return beforeCount;
    }

    public void setBuildRunCount() {
        String beforeCount = getPropValueForProp("Build_Run_Count", configFilePath);
        int count = Integer.parseInt(beforeCount);
        String newCount = String.valueOf(count + 1);
        setPropValueForProp("Build_Run_Count", newCount, configFilePath);
    }
    public void setTheBuildPath() {
        String runCount = getBuildRunCount();  // this also increments internally
        String reportPath = "Reports" + File.separator + "PicMeReport" + File.separator + "Build" + File.separator + "Run_" + runCount + File.separator;
        setPropValueForProp("Report_File_Path", reportPath, configFilePath);
    }

	/*
	 * public void setTheBuildPath() { String runCount = getBuildRunCount(); // this
	 * also increments internally String reportPath =
	 * "Reports\\PicMeReport\\Build\\Run_" + runCount + "\\";
	 * setPropValueForProp("Report_File_Path", reportPath, configFilePath); }
	 */
}
