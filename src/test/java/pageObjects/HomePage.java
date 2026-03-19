package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage{

    WebDriverWait wait;
    public HomePage(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @FindBy(xpath = "//div[@id='top-links']//a[contains(@href,'account/account')]")
    WebElement linkMyAccount;

    @FindBy(xpath = "//div[@id='top-links']//a[contains(@href,'register')]")
    WebElement linkRegister;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
    WebElement linkLogin;


    public void clickMyAccount(){
        wait.until(ExpectedConditions.visibilityOf(linkMyAccount));
        linkMyAccount.click();
    }

    public void clickRegister(){
        linkRegister.click();
    }

    public void clickLogin(){
        linkLogin.click();
    }
}
