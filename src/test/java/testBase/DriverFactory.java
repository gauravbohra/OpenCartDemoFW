package testBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private static void setDriver(String browser){
        WebDriver driver = null;
        switch (browser.toLowerCase()){
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless=new");
                firefoxOptions.addArguments("--disable-gpu");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                Assert.fail("Invalid browser type");
        }
        driverThreadLocal.set(driver);
    }


    public static WebDriver getDriver(String browserType){
        if(driverThreadLocal.get()==null){
            setDriver(browserType);
        }
        return driverThreadLocal.get();
    }

    public static void removeDriver(){
        if(driverThreadLocal.get()!=null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }


}
