package stepDefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.UtilFunctions;
import wrapper.SeleniumFunctions;

import java.io.IOException;


public class AdvisorStepdefs {
    public String className = getClass().getSimpleName();
    private Scenario scenario;

    @Before
    public void before(Scenario scenario) throws IOException {
        this.scenario = scenario;
    }

//    @Then("^I select the facility \"(.*?)\"$")
//    public void i_select_the_facility_something(String facility1) throws Throwable {
//        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
//        }.getClass().getEnclosingMethod().getName() + " : Start");
//
//        WebElement recordElt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//*[(contains(., 'No record found') or contains(., 'No data found'))]"));
//        WebElement nxtElt= SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//i[@title='Next Page']"));
//
////            try{
//        if (recordElt == null) {
//            try{
//                WebElement facility = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//img[@class='imgMeetingRoom' and parent::div[@class='facilityThumbnail' and following-sibling::div[@class='secondChildDivision' and descendant::*[text()='" + facility1 + "']]]]"));
//                facility.click();
//            }catch(Exception e){
//      //          nxtElt.click();
////             .retryOn(TimeoutException.class, 5, Duration.mins(1))
////                        .escalateOn(ConnectionClosedException.class);
////                  nxtElt.click();
//            }
//            Thread.sleep(2000);
//        } else {
//            scenario.write("No record found");
//            UtilFunctions.log("No record found");
//            System.out.println("No record found");
//        }
////            } catch(Exception e){
////                e.printStackTrace();
////            }
//        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
//        }.getClass().getEnclosingMethod().getName() + " : Complete");
//    }

    @And("^I view the user details of \"([^\"]*)\"$")
    public void i_view_the_user_details_of_something(String Username) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement userDetailsicon = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//i[@class='fa fa-eye iconDsegin' and parent::td/preceding-sibling::td[text()='" + Username + "']]"));
        userDetailsicon.click();

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");

    }
}