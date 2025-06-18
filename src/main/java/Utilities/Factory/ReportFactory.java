package Utilities.Factory;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import Utilities.ReportUtilities.ReportUtilities;

public class ReportFactory {
	public static ExtentReports reporter;
    public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
    public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
    public static int reportFlag = 0;
    protected static ReportUtilities ru = new ReportUtilities();

    public synchronized static ExtentReports getExtentReport() {
        if (reporter == null) {
            reporter = new ExtentReports(ru.getReportPath() + "Report.html", false, DisplayOrder.NEWEST_FIRST);
        }
        return reporter;
    }

    public synchronized static ExtentTest getTest(String testName, String testDescription) {

        if (!nameToTestMap.containsKey(testName)) {
            Long threadID = Thread.currentThread().getId();
            ExtentTest test = reporter.startTest(testName, testDescription);
            nameToTestMap.put(testName, test);
            threadToExtentTestMap.put(threadID, testName);
        }
        return nameToTestMap.get(testName);
    }

    public synchronized static ExtentTest getTest(String testName) {
        return getTest(testName, "");
    }

    public synchronized static ExtentTest getTest() {
        Long threadID = Thread.currentThread().getId();

        if (threadToExtentTestMap.containsKey(threadID)) {
            String testName = threadToExtentTestMap.get(threadID);
            return nameToTestMap.get(testName);
        }
        //system log, this shouldnt happen but in this crazy times if it did happen log it.
        return null;
    }

    public synchronized static void closeTest(String testName) {
        if (!testName.isEmpty()) {
            ExtentTest test = getTest(testName);
            reporter.endTest(test);
        }
    }

    public synchronized static void closeTest(ExtentTest test) {
        if (test != null) {
            reporter.endTest(test);
        }
    }

    public synchronized static void closeTest() {
        ExtentTest test = getTest();
        closeTest(test);
    }

    public synchronized static void closeReport() {
        if (reporter != null) {
            reporter.flush();
            //reporter.close();
        }
    }
}
