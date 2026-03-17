package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class NewRegistrationPage extends BasePage {

    public NewRegistrationPage (WebDriver driver){
        super(driver);
    }

    @FindBy(id = "input-firstname")
    WebElement txtInputFirstName;

    @FindBy(id = "input-lastname")
    WebElement txtInputLastName;

    @FindBy(id = "input-email")
    WebElement txtInputEmail;

    @FindBy(id = "input-telephone")
    WebElement txtInputTelephone;

    @FindBy(id = "input-password")
    WebElement txtInputPassword;

    @FindBy(id = "input-confirm")
    WebElement txtInputConfirmPassword;

    @FindBy(xpath = "//label[normalize-space()='No']//input[@value='0']")
    WebElement radioBtnUnSubscribe;

    @FindBy(xpath = "//input[@type='checkbox']")
    WebElement checkBoxPrivacyPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement buttonContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement accCreatedSuccessMsg;

    public void setFirstName(String firstName) {
        txtInputFirstName.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        txtInputLastName.sendKeys(lastName);
    }

    public void setEmail(String email) {
        txtInputEmail.sendKeys(email);
    }

    public void setTelephone(String telephone) {
        txtInputTelephone.sendKeys(telephone);
    }

    public void setPassword(String password) {
        txtInputPassword.sendKeys(password);
    }

    public void setConfirmPassword(String confirmPassword) {
        txtInputConfirmPassword.sendKeys(confirmPassword);
    }

    public void clickUnsubscribe(){
        radioBtnUnSubscribe.click();
    }

    public void acceptPrivacyPolicy(){
        checkBoxPrivacyPolicy.click();
    }

    public void clickContinue(){
        buttonContinue.click();
    }

    public String getAccCreatedMessage(){
        try {
            return accCreatedSuccessMsg.getText();
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
