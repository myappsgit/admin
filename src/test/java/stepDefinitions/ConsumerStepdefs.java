package stepDefinitions;

import java.util.List;
import java.util.Map;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import constants.GlobalConstants;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import support.Page;
import utils.UtilFunctions;
import wrapper.SeleniumFunctions;

import static stepDefinitions.GlobalStepdefs.curPageName;
import static stepDefinitions.Hooks.driver;

/******************************************************************************
Class Name: ConsumerStepdefs
Contains step definitions related to Consumer
******************************************************************************/

public class ConsumerStepdefs {
    public String className = getClass().getSimpleName();
    private static String oldPoints;
    private static String newPoints;
    private static String taskPoints;

    @Given("^I set the following preferences$")
    public void setUserPreferences(DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        boolean flag = false;

        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
        for (Map data : dataList) {
            String preferenceName = (String) data.get("Name");
            String preferenceStatus = ((String) data.get("Status"));

            SeleniumFunctions.explicitWait(Hooks.getDriver(), GlobalConstants.FIFTEEN, "//div[@class='slider round' and ancestor::div[@class='col-5' and preceding-sibling::div[@class='col-7 titleContainer' and contains(normalize-space(.), '" + preferenceName + "')]]]" + ";xpath");

            WebElement prefElt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='slider round' and ancestor::div[@class='col-5' and preceding-sibling::div[@class='col-7 titleContainer' and contains(normalize-space(.), '" + preferenceName + "')]]]"));
            String bgColor = prefElt.getCssValue("background-color");

            System.out.println("This is: " + bgColor);
            if (bgColor.equals("rgba(33, 150, 243, 1)")) {
                bgColor = "#2196f3";
            } else if (bgColor.equals("rgba(236, 237, 239, 1)")) {
                bgColor = "#ecedef";
            }
            if (bgColor.equals("#2196f3") && preferenceStatus.equals("Off")) {
                prefElt.click();
                flag = true;
            } else if (bgColor.equals("#ecedef") && preferenceStatus.equals("On")) {
                prefElt.click();
                flag = true;
            } else {
                UtilFunctions.log("Preference already set");
                flag = true;
            }
        }

        Assert.assertTrue(flag);

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^I select the facility \"(.*?)\"$")
    public void selectFacility(String facilityName) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        WebElement recordElt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//*[text()='No record found']"));
        if (recordElt == null) {
            Thread.sleep(3000);
            WebElement facility = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//img[@class='imgMeetingRoom' and parent::div[@class='facilityThumbnail' and child::div[@class='facilityTitleContainer' and descendant::*[contains(normalize-space(.),'" + facilityName + "')]]]]"));
//            WebElement facility = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//img[@class='imgMeetingRoom' and parent::div[@class='facilityThumbnail' and following-sibling::div[@class='facilityDetailsContainer' and descendant::*[contains(normalize-space(.), 'JNR Co-working')]]]]"));
            SeleniumFunctions.explicitWait(Hooks.getDriver(), 10, "//img[@class='imgMeetingRoom' and parent::div[@class='facilityThumbnail' and following-sibling::div[(@class='secondChildDivision' or @class='facilityDetailsContainer' or @class='headingCo-Working') and descendant::*[contains(normalize-space(.),'" + facilityName + "')]]]]" + ";" + "xpath");
            facility.click();
        } else {
            System.out.println("No record found");
        }
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }


    @Then("^I fetch the consumer points (?:before|after)? completing a task$")
    public void fetchConsumerPoints(String state) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement pointsElt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//span[@class='consumerTextLightColor']"));
        if (state.equals("before")) {
            oldPoints = pointsElt.getText();
            oldPoints = oldPoints.trim().replaceAll("\\s+", "");
//        	return oldPoints;
        } else if (state.equals("after")) {
            newPoints = pointsElt.getText();
            newPoints = newPoints.trim().replaceAll("\\s+", "");
//        	return newPoints;
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
//		return null;
    }

    @Then("^the added points should be \"(.*?)\"$")
    public void checkPoints(String points) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        int i = Integer.valueOf(newPoints) - Integer.valueOf(oldPoints);
        Assert.assertTrue((Integer.valueOf(points) == i));

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^the points of \"(.*?)\" task \"(.*?)\" should be added successfully$")
    public void checkAddedPoints(String taskType, String taskTitle) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement elt = null;
        if (taskType.equals("Poll")) {
            elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//p[@class='points' and following::span[normalize-space(./text())='" + taskTitle + "' and ancestor::div[@class='pollbox']]]"));
            taskPoints = elt.getText();
        } else if (taskType.equals("Video")) {
            elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//p[@class='points' and following::span[normalize-space(./text())='" + taskTitle + "' and ancestor::div[@class='videobox']]]"));
            taskPoints = elt.getText();
        }

        int i = Integer.valueOf(newPoints) - Integer.valueOf(oldPoints);
        Assert.assertTrue((Integer.valueOf(taskPoints) == i));

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^I (like|dislike) the \"(.*?)\" task \"(.*?)\"$")
    public void likeOrDislikeTask(String state, String taskType, String taskTitle) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement elt = null;
        Actions actions = new Actions(Hooks.getDriver());

        if (taskType.equals("Poll")) {
            if (state.equals("like")) {
                elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("img[contains(@src, '../assets/icons/like.svg') and following::span[normalize-space(./text())='" + taskTitle + "' and ancestor::div[@class='pollbox']]]"));
                ((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elt);
                actions.moveToElement(elt);
                elt.click();
            } else {
                elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("img[contains(@src, '../assets/icons/unlike.svg') and following::span[normalize-space(./text())='" + taskTitle + "' and ancestor::div[@class='pollbox']]]"));
                ((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elt);
                actions.moveToElement(elt);
                elt.click();
            }
        } else if (taskType.equals("Video")) {
            if (state.equals("like")) {
                elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("img[contains(@src, '../assets/icons/like.svg') and following::span[normalize-space(./text())='" + taskTitle + "' and ancestor::div[@class='videobox']]]"));
                ((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elt);
                actions.moveToElement(elt);
                elt.click();
            } else {
                elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("img[contains(@src, '../assets/icons/unlike.svg') and following::span[normalize-space(./text())='" + taskTitle + "' and ancestor::div[@class='videobox']]]"));
                ((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elt);
                actions.moveToElement(elt);
                elt.click();
            }
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^the \"(.*?)\" \"(.*?)\" should be under \"(.*?)\"$")
    public void clickTask(String taskType, String taskTitle) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement elt = null;
        if (taskType.equals("Poll")) {
            elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='pollbox' and descendant::span[normalize-space(./text())='" + taskTitle + "' and ancestor::completed-video-info-component]]"));
            elt.click();
        } else if (taskType.equals("Video")) {
            elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='videobox' and descendant::span[normalize-space(./text())='" + taskTitle + "' and ancestor::completed-video-info-component]]"));
            elt.click();
        } else {
            elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='appbox' and descendant::span[normalize-space(./text())='" + taskTitle + "' and ancestor::completed-video-info-component]]"));
            elt.click();
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @When("^I click the booking id \"(.*?)\"$")
    public void clickwithBookingid(String bookingId) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

//        if(page.equals("upcoming")){
        try {
            WebElement bookingElt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//td[text()='" + bookingId + "']"));
            bookingElt.click();
        } catch (Exception e) {
            e.printStackTrace();
            UtilFunctions.log("Booking Id: " + bookingId + " does not exists. Exception: " + e.getMessage());
        }
//        } else{
//            try {
//                WebElement bookingElt1 = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//td[text()='" + bookingId + "']"));
//                bookingElt1.click();
//            }catch (Exception e){
//                e.printStackTrace();
//                UtilFunctions.log("Booking Id: " + bookingId + " does not exists. Exception: " + e.getMessage());
//            }
//        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }


}