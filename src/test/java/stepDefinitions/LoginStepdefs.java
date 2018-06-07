package stepDefinitions;

import wrapper.SeleniumFunctions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinitions.Hooks;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.UtilFunctions;
import utils.UtilProperty;
import static stepDefinitions.Hooks.driver;

/******************************************************************************
 Class Name: LoginStepdefs
 Contains step definitions related to login
 ******************************************************************************/

public class LoginStepdefs {

    public String className = getClass().getSimpleName();

    @Given("^I am logged into the application with user \"(.*?)\" using the default password$")
    public void applicationLogin(String userName) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        String defaultPassword = UtilProperty.userPwd;
        Hooks.openBrowser(userName, defaultPassword);

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Given("^I am logged into the application with the default user$")
    public void applicationLogin() throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        String defaultUserName = UtilProperty.userName;
        String defaultPassword = UtilProperty.userPwd;

        Hooks.openBrowser(defaultUserName, defaultPassword);

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Given("^I am logged into the application with user \"(.*?)\" and password \"(.*?)\"$")
    public void applicationLogin(String userName, String password) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        Hooks.openBrowser(userName, password);
        

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @When("^I click the logout button$")
    public void logOut()throws Throwable{
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName("Login");
        String logOutButton = UtilFunctions.getElementFromJSONFile(fileObj, "BUTTONS.Logout", "path");

        WebElement userPref = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//i[@class='fa fa-user-circle-o']"));
              userPref.click();
        //Set loggedInUser to empty string.  This will make the next scenario to close the browser and log back in
        Hooks.setLoggedInUser("");
        //This is not an actual xpath. Take xpath or any locator from application
        WebElement logOutElt = SeleniumFunctions.findElement(driver, By.id(logOutButton));
        if (logOutElt != null)
        	logOutElt.click();

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }
}


