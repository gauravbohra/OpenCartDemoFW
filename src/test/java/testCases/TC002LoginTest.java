package testCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;

@Listeners(utils.ExtentReportUtility.class)
public class TC002LoginTest extends BaseTest {

    @Test(groups = {"sanity", "master"})
    public void verifyLogin(){

        logger.info("Starting test *** TC002LoginTest ***");
        try {

            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked my account button");
            homePage.clickLogin();
            logger.info("Clicked login button");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(properties.getProperty("userEmail"));
            loginPage.setPassword(properties.getProperty("password"));
            logger.info("Entered valid user email and password");
            loginPage.clickLogin();
            logger.info("Clicked login button");

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            Assert.assertTrue(myAccountPage.isHeadingDisplayed(), "Login failed");
            logger.info("login successful");
        }catch (Exception e){
            Assert.fail("Failed login test");
        }

        logger.info("Finished test *** TC002LoginTest ***");
    }
}
