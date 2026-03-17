package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;

    public Logger logger;

    protected Properties properties;

    @BeforeClass(groups = {"sanity", "regression", "master", "dataDriven"})
    @Parameters({"browser","os"})
    public void setup(String browser, String osName) throws IOException {
        loadProperties();

        if(properties.getProperty("execution_env").equalsIgnoreCase("remote")){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            switch (browser.toLowerCase()){
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("Invalid browser");
                    return;
            }

            switch (osName.toLowerCase()){
                case "windows":
                    capabilities.setPlatform(Platform.WINDOWS);
                    break;
                case "mac":
                    capabilities.setPlatform(Platform.MAC);
                    break;
                case "linux":
                    capabilities.setPlatform(Platform.LINUX);
                    break;
                default:
                    System.out.println("Invalid os");
                    return;
            }

            URL url = new URL("http://localhost:4444/wd/hub");
            driver = new RemoteWebDriver(url, capabilities);

        }
        else {
            driver = DriverFactory.getDriver(browser.toLowerCase());
//            switch (browser.toLowerCase()) {
//                case "chrome":
//                    driver = new ChromeDriver();
//                    break;
//                case "edge":
//                    driver = new EdgeDriver();
//                    break;
//                case "firefox":
//                    driver = new FirefoxDriver();
//                    break;
//                default:
//                    System.out.println("Invalid browser");
//                    return;
//            }
        }

        logger = LogManager.getLogger(this.getClass());

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(properties.getProperty("appURL"));
        driver.manage().window().maximize();
    }

    @AfterClass(groups = {"sanity", "regression", "master", "dataDriven"})
    public void tearDown(){
        if(properties.getProperty("execution_env").equalsIgnoreCase("local")){
            DriverFactory.removeDriver();
        }
        else {
            driver.quit();
        }
    }

    public String captureScreen(String testName) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") +
                "//screenshots//" + testName + "_" + timeStamp + ".png";
        File targetFile = new File(screenshotPath);
//        srcFile.renameTo(targetFile);
        FileUtils.copyFile(srcFile, targetFile);
        return screenshotPath;
    }
    private void loadProperties() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(".//src/test/resources//config.properties");
        properties = new Properties();
        properties.load(fileInputStream);
    }

    public String generateRandomString(int length){
        String randomString = RandomStringUtils.secure().nextAlphabetic(length);
        return randomString;
    }

    public String generatedRandomNumeric(int length){
        String randomNumber = RandomStringUtils.secure().nextNumeric(length);
        return randomNumber;
    }

    public String generateRandomAlphaNumeric(int length){
        String randomAlphanumeric = RandomStringUtils.secure().nextAlphanumeric(length);
        return randomAlphanumeric;
    }
}
