package testCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.NewRegistrationPage;
import testBase.BaseTest;

@Listeners(utils.ExtentReportUtility.class)
public class TC001AccountRegistrationTest extends BaseTest {

    @Test(groups = {"regression", "master"})
    public void verifyAccountRegistration(){
        logger.info("Starting test *** TC_001_AccountRegistrationTest ***");
        logger.debug("Debug test");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked my account button");
            homePage.clickRegister();
            logger.info("Clicked register button");

            logger.info("Entering user details for new registration: ");
            NewRegistrationPage newRegistrationPage = new NewRegistrationPage(driver);
            newRegistrationPage.setFirstName(generateRandomString(5).toUpperCase());
            newRegistrationPage.setLastName(generateRandomString(6).toUpperCase());
            newRegistrationPage.setEmail(generateRandomString(6) + "@gmail.com");

            newRegistrationPage.setTelephone(generatedRandomNumeric(10));

            String password = generateRandomAlphaNumeric(8);
            newRegistrationPage.setPassword(password);
            newRegistrationPage.setConfirmPassword(password);

            newRegistrationPage.acceptPrivacyPolicy();
            newRegistrationPage.clickContinue();
            logger.info("Submitted user details for new registration");

            logger.info("Validating success message");
            Assert.assertEquals("Your Account Has Been Created!", newRegistrationPage.getAccCreatedMessage());
            logger.info("Test passed");

        }catch (Exception ex){
            logger.error("Test failed: "+ ex.getMessage());
            Assert.fail("Test failed: "+ ex.getMessage());
        }
        finally {
            logger.info("Test completed *** TC_001_AccountRegistrationTest ***");
        }

    }

}
