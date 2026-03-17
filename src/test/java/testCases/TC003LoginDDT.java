package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;
import utils.DataProviders;

public class TC003LoginDDT extends BaseTest {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = {"dataDriven", "master"})
    public void verify_Login_DDT(String userName, String password, String expRes){

        logger.info("Staring test ***** TC003LoginDDT ******");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked on my account button");
            homePage.clickLogin();
            logger.info("Clicked on login link");

            LoginPage loginPage = new LoginPage(driver);
            logger.info("Entering user login details");
            loginPage.setEmail(userName);
            loginPage.setPassword(password);

            loginPage.clickLogin();
            logger.info("Clicked on login button");

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean isUserLoggedIn = myAccountPage.isHeadingDisplayed() ;
            if(expRes.equalsIgnoreCase("Valid")){
                if(isUserLoggedIn){
                    myAccountPage.clickLogout();
                    Assert.assertTrue(true);
                }
                else {
                    Assert.fail();
                }
            }
            else {
                if(isUserLoggedIn){
                    myAccountPage.clickLogout();
                    Assert.fail();
                }
                else {
                    Assert.assertTrue(true);
                }

            }


        }catch (Exception e){
            Assert.fail("Login test failed");
        }

        logger.info("Test completed ***** TC003LoginDDT *****");
    }
}
