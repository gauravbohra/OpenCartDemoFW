package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy (xpath = "//h2[normalize-space()='My Account']")
    WebElement headingMessage;

    @FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")
    WebElement buttonLogout;

    public boolean isHeadingDisplayed(){
        try {
            return headingMessage.isDisplayed();
        }catch (Exception e){
            return false;
        }
    }

    public void clickLogout(){
        buttonLogout.click();
    }

}
