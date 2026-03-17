package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseTest;

import javax.mail.Authenticator;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportUtility implements ITestListener {
    private ExtentSparkReporter sparkReporter;
    private ExtentReports reports;
    private ExtentTest test;
    private String reportName;

    public void onStart(ITestContext iTestContext){
    /*    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
        Date date = new Date();
        String timeStamp = dateFormat.format(date); */

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "ExtentReport-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"//reports//"+ reportName);
        sparkReporter.config().setReportName("Automation Report");
        sparkReporter.config().setDocumentTitle("OpenCart Test");
        sparkReporter.config().setTheme(Theme.DARK);

        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Application","OpenCart");
        reports.setSystemInfo("Module", "Shopping");
        reports.setSystemInfo("User Name",System.getProperty("user.name"));
        reports.setSystemInfo("Environment", "QA");

        String browser = iTestContext.getCurrentXmlTest().getParameter("browser");
        reports.setSystemInfo("Browser", browser);
        String os = iTestContext.getCurrentXmlTest().getParameter("os");
        reports.setSystemInfo("OS", os);

        List<String> includedGroups = iTestContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty()){
            reports.setSystemInfo("groups", includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult iTestResult){
        test = reports.createTest(iTestResult.getTestClass().getName());
        test.assignCategory(iTestResult.getMethod().getGroups());
//        test.assignCategory(iTestResult.getTestClass().getName());
        test.log(Status.PASS, iTestResult.getName() + " test method passed");
    }

    public void onTestFailure(ITestResult iTestResult){
        test = reports.createTest(iTestResult.getTestClass().getName());
        test.assignCategory(iTestResult.getMethod().getGroups());
//        test.assignCategory(iTestResult.getTestClass().getName());
        test.log(Status.FAIL, iTestResult.getName() + " test method failed");
        test.log(Status.INFO, iTestResult.getThrowable());

        String screenshotPath;
        try {
            BaseTest testClass = (BaseTest) iTestResult.getMethod().getInstance();
            screenshotPath = testClass.captureScreen(iTestResult.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    public void onTestSkipped(ITestResult iTestResult){
        test = reports.createTest(iTestResult.getTestClass().getName());
        test.assignCategory(iTestResult.getMethod().getGroups());
//        test.assignCategory(iTestResult.getTestClass().getName());
        test.log(Status.SKIP, iTestResult.getName() + " test method failed");
        test.log(Status.INFO, iTestResult.getThrowable());
    }

    public void onFinish(ITestContext iTestContext){
        reports.flush();

        //Opening extent report after execution is finished
        String extentReportPath = System.getProperty("user.dir")+"//reports//"+ reportName;
        File file = new File(extentReportPath);
        try {
            if(file.exists()) {
                Desktop.getDesktop().browse(file.toURI());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Only enable when need to send report using Email
//        try {
//            URL url = new URL("file:///"+ System.getProperty("user.dir")+"//reports//"+reportName);
//
//            DataSourceUrlResolver dataSourceUrlResolver = new DataSourceUrlResolver(url);
//            ImageHtmlEmail email = new ImageHtmlEmail();
//            email.setDataSourceResolver(dataSourceUrlResolver);
//            email.setHostName("smtp.gmail.com");
//            email.setSmtpPort(465);
//            email.setSSLOnConnect(true);
//
//            String user = "krishn.auto100@gmail.com";
//            String password = "bpmjuwccyhqyrmig";
//            DefaultAuthenticator authenticator = new DefaultAuthenticator(user, password);
//            email.setAuthenticator(authenticator);
//            email.setFrom(user);
//            email.setSubject("Automation Report");
//            email.setMsg("PFA report attached");
//            email.addTo("gbohra1810@gmail.com");
//            email.attach(url, "ExtentReport.html", "Please check report");
//            email.send();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
