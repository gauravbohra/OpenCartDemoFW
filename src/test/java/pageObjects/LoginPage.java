package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "input-email")
    WebElement txtInputEmail;

    @FindBy(id = "input-password")
    WebElement txtInputPassword;

    @FindBy(xpath = "//input[@value='Login']")
    WebElement btnLogin;

    public void setEmail(String email) {
        txtInputEmail.sendKeys(email);
    }

    public void setPassword(String password) {
        txtInputPassword.sendKeys(password);
    }

    public void clickLogin(){
        btnLogin.click();
    }

}
