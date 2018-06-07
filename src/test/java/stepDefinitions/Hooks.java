package stepDefinitions;

import constants.GlobalConstants;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import utils.SetUpFile;
import utils.UtilFunctions;
import utils.UtilProperty;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;


/******************************************************************************
 Class Name: Hooks
 Class invoked to execute set-up functions
 ******************************************************************************/

public class Hooks {

    //Driver object
    public static WebDriver driver = null;
    public String className = getClass().getSimpleName();
    private static Hooks hooks = new Hooks();
    public static String loggedInUser = "";

    @Before
    public void setUp(Scenario scenario) throws Exception {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("Scenario: " + scenario.getName() + " starting.");
        System.out.println(scenario.getName());
        System.out.println(scenario.getSourceTagNames().iterator().next());
        UtilFunctions.log("Scenario Tag Name: " + scenario.getSourceTagNames().iterator().next());
    }


    /**************************************************************************
     * name: openBrowser(String userName)
     * functionality: Function to invoke setup browser functionality
     * param: String userName - Name of user
     * return: void
     *************************************************************************/
    public static void openBrowser(String userName, String password) throws InterruptedException, IOException {
        UtilFunctions.log("Class: " + hooks.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("userName: " + userName);
//        if (loggedInUser.equals(userName)) {
//            UtilFunctions.log("User already logged in: " + userName);
//        } else {
        if (!(driver == null)) {
            closeBrowser();
        }
        loggedInUser = userName;
        setDriver(SetUpFile.getSetUpFileObj().setUpBrowser(driver, userName, password));
    }
//

    /**************************************************************************
     * name: closeBrowserAndTakeScreenShot(Scenario scenario)
     * functionality: Function to take screen shot and close browser
     * param: Scenario scenario - Scenario object
     * return: void
     *************************************************************************/
    @After
    public static void closeBrowserAndTakeScreenShot(Scenario scenario) throws IOException {
        try {
            UtilFunctions.log("Class: " + hooks.className + "; Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
//            if (driver.getWindowHandles().size() >= 1 && (!scenario.isFailed())) {
//                LoginStepdefs loginStepdefs = new LoginStepdefs();
//                try {
//                    loginStepdefs.logOut();
//                    driver.close();
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            }
            if (driver == null) {
                System.out.println("Do nothing");
            } else if (scenario.isFailed()) {
                UtilFunctions.log("Scenario: " + scenario.getName() + " - Failed");
                loggedInUser = "";
                takeScreenShot(scenario);
//                driver.close();
                driver.quit();
                driver = null;
                UtilFunctions.log("Browser closed");
                Runtime.getRuntime().exec("Taskkill /PID " + GlobalConstants.browserProcessId + " /F");
                UtilFunctions.log("Tried to kill process with PID: " + GlobalConstants.browserProcessId);
            }
//            driver.close();
            UtilFunctions.log("Scenario: " + scenario.getName() + " complete.");
        } catch (UnreachableBrowserException ubEx) {
            driver = null;
            loggedInUser = "";
            UtilFunctions.log("// Ignore. The browser was killed properly due to UnreachableBrowserException: " + ubEx.getMessage());
            UtilFunctions.log("Scenario: " + scenario.getName() + " complete.");
            UtilFunctions.log("Browser closed");
            Runtime.getRuntime().exec("Taskkill /PID " + GlobalConstants.browserProcessId + " /F");
            UtilFunctions.log("Tried to kill process with PID: " + GlobalConstants.browserProcessId);
        }
    }


    /**************************************************************************
     * name: getDriver()
     * functionality: Function to return driver object
     * return: returns driver object
     *************************************************************************/
    public static WebDriver getDriver() {
        UtilFunctions.log("Class: " + hooks.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("get driver object");
        return driver;
    }


    /**************************************************************************
     * name: setDriver(WebDriver setDriver)
     * functionality: Function to set WebDriver
     * param: WebDriver setDriver - WebDriver object
     * return: void
     *************************************************************************/
    public static void setDriver(WebDriver setDriver) {
        UtilFunctions.log("Class: " + hooks.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("get driver object");
        driver = setDriver;
    }


    /**************************************************************************
     * name: closeBrowser()
     * functionality: Function to close browser and set driver object to null
     * return: void
     *************************************************************************/
    public static void closeBrowser(WebDriver... drivers) throws IOException {
        UtilFunctions.log("Class: " + hooks.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        loggedInUser = "";
        //Skip logout if not logged in.  Window count should be greater than or equal 1 if already logged in.
//        if (driver.getWindowHandles().size() >= 1){
//            LoginStepdefs loginStepdefs = new LoginStepdefs();
//            try {
//                loginStepdefs.logOut();
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        }

        if (drivers.length > 0) {
//            drivers[0].close();
            drivers[0].quit();
            drivers = null;
        } else {
//            driver.close();
            driver.quit();
            driver = null;
        }
        UtilFunctions.log("Browser closed");
        Runtime.getRuntime().exec("Taskkill /PID " + GlobalConstants.browserProcessId + " /F");
        UtilFunctions.log("Tried to kill process with PID: " + GlobalConstants.browserProcessId);
    }


    /**************************************************************************
     * name: takeScreenShot(Scenario scenario)
     * functionality: Function to take screen shot
     * * param: Scenario scenario - Scenario object
     * return: void
     *************************************************************************/
    public static void takeScreenShot(Scenario scenario) {
        UtilFunctions.log("Class: " + hooks.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        try {
            byte[] screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenShot, "image/png");
        } catch (WebDriverException wde) {
            System.err.println(wde.getMessage());
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }

    /**************************************************************************
     * Helper method to set the loggedInUser variable
     *
     * @param userName the name of the user currently logged in to application
     * @return void
     *************************************************************************/
    public static void setLoggedInUser(String userName) {
        loggedInUser = userName;
    }
}
