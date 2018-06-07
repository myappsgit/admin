package stepDefinitions;

import static stepDefinitions.Hooks.driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constants.GlobalConstants;
import wrapper.SeleniumFunctions;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import support.Navigation;
import support.Page;
import utils.SetUpFile;
import utils.UtilFunctions;

public class GlobalStepdefs {

    public String className = getClass().getSimpleName();
    public static String curPageName = "";
    public String svgText;

    @Then("^I click the \"(.*?)\" (?:image|tab|element|icon)(?: in the \"(.*?)\" pane)?( if it exists)?$")
    public void clickMiscElement(String elementName, String paneName, String exists) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        elementName = elementName.replace(" ", "");
        if (exists == null) {
            UtilFunctions.log("Pagename: " + curPageName);
            Assert.assertTrue("Misc Element: " + elementName + " not present", Page.clickMiscElement(Hooks.getDriver(), curPageName, elementName, paneName, exists));
        } else {
            try {
                Page.clickMiscElement(Hooks.getDriver(), curPageName, elementName, paneName, exists);
            } catch (Exception e) {
                e.printStackTrace();

                UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " : Complete");
            }
        }
    }

    @Given("^I am on the \"(.*?)\" page$")
    public void selectPage(String pageName) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        pageName = pageName.replace(" ", "");
        curPageName = pageName;
        Navigation.setPage(pageName);
        boolean getValue = Navigation.navigationPageExists(Hooks.getDriver(), pageName);
        Assert.assertTrue("Page element: " + pageName + " not found", getValue);

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Given("^I select the \"(.*?)\" subpage$")
    public void selectSubPage(String subPage) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        subPage = subPage.replace(" ", "");
        boolean getValue = Navigation.selectNavigationPage(Hooks.getDriver(), curPageName, subPage);
        Assert.assertTrue("Sub page element: " + subPage + " not found", getValue);

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Given("^I launch the \"(.*?)\" web application$")
    public void launchApplication(String appName) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        SetUpFile setUp = new SetUpFile();
        setUp.launchApplication();

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @And("^I click the \"(.*?)\" button( if it exists)?$")

    public void clickButton(String buttonName, String exists) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        if (exists == null) {
            Assert.assertTrue("Button: " + buttonName + " does not exists", Page.clickButton(Hooks.getDriver(), curPageName, buttonName));
        } else {
            try {
                Page.clickButton(Hooks.getDriver(), curPageName, buttonName);
            } catch (Exception e) {
                e.printStackTrace();
                UtilFunctions.log("Button: " + buttonName + " does not exists. Exception: " + e.getMessage());
            }
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^(?:the|The)? following (field(?:s)?|dashboard options) should(?: be)? (enabled|disabled|display|not display)(?: in the \"(.*?)\" pane)?$")
    public void checkDataTableValuesInPane(String state, String type, String paneName, DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

//        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
//        for (Map data : dataList) {
//            String fieldType = (String) data.get("Type");
//            String fieldName = ((String) data.get("Name")).replace(" ", "");
        if (type.equals("fields") || type.equals("field")) {
            if (state.equals("not display")) {
                Assert.assertNotNull("Field is not present.", Page.checkDataTableInPane(Hooks.getDriver(), curPageName, state, dataTable));
            } else {
                Assert.assertEquals("", Page.checkDataTableInPane(Hooks.getDriver(), curPageName, state, dataTable));

            }
        } else if (type.equals("dashboard options")) {
            List<String> dataList = dataTable.asList(String.class);
            for (String pageName : dataList) {
                if (state.equals("not display")) {
                    UtilFunctions.log("SubPage name to be checked: " + pageName);
                    Assert.assertFalse("SubPage: " + pageName + " is not displayed.", Navigation.navigationPageExists(Hooks.getDriver(), pageName));
                } else {
                    UtilFunctions.log("SubPage name to be checked: " + pageName);
                    Assert.assertTrue("", Navigation.navigationPageExists(Hooks.getDriver(), pageName));
                }
            }
        }

//        }
    }

    @Then("^the following subpages should load$")
    public void checkSubPageLoad(DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        List<String> dataList = dataTable.asList(String.class);
        for (String subPageName : dataList) {
            UtilFunctions.log("SubPage name to be checked: " + subPageName);
            Assert.assertTrue("SubPage: " + subPageName + " does not exist.", Navigation.navigationSubPageExists(Hooks.getDriver(), subPageName));
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    //Enter Value in Text Box
    @When("^I enter \"(.*?)\" in the \"(.*?)\" field$")
    public void enterInTheField(String value, String fieldName) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        Assert.assertTrue("Text Box: " + fieldName + " Object Not found", Page.setTextBox(Hooks.getDriver(), curPageName, value, fieldName));

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    //step definition to set multiple field values
    @Given("^I set the following values$")
    public void setFieldValues(DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

//        if (paneName == null)
        Assert.assertEquals("", Page.setMultipleFieldValues(Hooks.getDriver(), dataTable, "", curPageName));
//        else {
//            Assert.assertEquals("", Page.setMultipleFieldValues(Hooks.getDriver(), dataTable, "", curPageName));
//        }
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

//    //step definition to set multiple field values
//    @Given("^I set the following values in the \"(.*?)\" pane$")
//    public void setFieldValues(String paneName, DataTable dataTable) throws Throwable {
//        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
//        }.getClass().getEnclosingMethod().getName() + " : Start");
//
//        if (paneName == null)
//            Assert.assertEquals("", Page.setMultipleFieldValues(Hooks.getDriver(), dataTable,""));
//        else {
//            Assert.assertEquals("", Page.setMultipleFieldValues(Hooks.getDriver(), dataTable, paneName));
//        }
//        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
//        }.getClass().getEnclosingMethod().getName() + " : Complete");
//    }

    //step definition to check if elements exists
    @Given("^the following elements should be displayed$")
    public void checkElementsInPage(DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        Assert.assertEquals("", Page.checkElementsExists(Hooks.getDriver(), dataTable, ""));

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Given("^the element \"(.*?)\" should be displayed$")
    public void checkIfElementIsDisplayed(String elt) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(curPageName);
        String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "MISC_ELEMENTS." + elt);
        String fieldPath = elementType[0];
        String fieldMethod = elementType[1];
        UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
        SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
        WebElement eleObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
        if (eleObj != null) {
            eleObj.isDisplayed();
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^the following text should appear in the \"(.*?)\" pane$")
    public void checkForMultipleTextEntries(String paneName, DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        List<String> textList = dataTable.asList(String.class);
        for (String text : textList) {
//        	try{
            Assert.assertTrue("Text: " + text + " not found", Page.textExists(Hooks.driver, text, false));
//        	}
//            catch(Exception e){
//            	System.out.println();
//            	UtilFunctions.log("Text:"+text+"not found");
//            }
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @When("^I click \"(.*?)\" in the confirmation box( if it exists)?$")
    public void selectAlert(String alertName, String exists) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        if (exists == null) {
            Assert.assertTrue("Alert: " + alertName + " not visible", Page.handleAlert(Hooks.getDriver(), alertName));
        } else {
            try {
                Page.handleAlert(Hooks.getDriver(), alertName);
            } catch (Exception e) {
                e.printStackTrace();
                UtilFunctions.log("Alert: " + alertName + " not visible Exception: " + e.getMessage());
            }
        }
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^There should( not)? be an alert(?: with the text \"(.*)\")?$")
    public void checkAlert(String condition, String alertText) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        boolean alert = SeleniumFunctions.checkAlertPresent(Hooks.getDriver());
        if (condition == null) {
            Assert.assertTrue("Alert is not displayed", alert);
            if (alertText != null) {
                Assert.assertTrue("Alert text: " + alertText + " is not displayed", SeleniumFunctions.getAlertText(Hooks.getDriver()).contains(alertText));
            }
        } else {
            Assert.assertFalse("Alert displayed", alert);
        }
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @And("^I wait \"(.*)\" second(?:s)?$")
    public void waitForSpecifiedTime(int seconds) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        int milliSec = (seconds * 1000);

        Thread.sleep(milliSec);

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @When("^I (un)?check the \"(.*?)\" checkbox(?: in the \"(.*?)\" pane)?( if it exists)?$")
    public void checkCheckBox(String checkType, String checkbox, String paneName, String exists) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        UtilFunctions.log("CheckBoxName: " + checkbox);
        if (checkType == null) {
            checkType = "check";
        } else {
            checkType = "uncheck";
        }
        if (exists == null) {
            Assert.assertTrue("CheckBox: " + checkbox + " didn't set properly.", Page.setCheckBox(Hooks.getDriver(), curPageName, checkbox, checkType));
//            Page.setCheckBox(Hooks.getDriver(), curPageName, checkbox, checkType);
        } else {
            try {
//                 Assert.assertTrue("CheckBox: " + checkbox + " didn't set properly.", Page.setCheckBox(Hooks.getDriver(), curPageName, checkbox, checkType));
                Page.setCheckBox(Hooks.getDriver(), curPageName, checkbox, checkType);
            } catch (Exception e) {
                e.printStackTrace();
                UtilFunctions.log("checkBox: " + checkbox + " didn't set properly. " + e.getMessage());
            }

            UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName() + " : Complete");
        }
    }

    @Then("^I click the \"(.*?)\" (?:image|tab|element) in the \"(.*?)\" row?( if it exists)?$")
    public void clickRowElement(String elementName, String rowText, String exists) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        boolean flag;
        elementName = elementName.replace(" ", "");
        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(curPageName);
        String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "MISC_ELEMENTS." + elementName);
        String path = elementType[0];
        path = "//td[text()='rowText']//following::" + path;
        String method = elementType[1];
        SeleniumFunctions.explicitWait(Hooks.getDriver(), GlobalConstants.TEN, path + ";" + method);
        WebElement eleObj = SeleniumFunctions.findElement(Hooks.getDriver(), SeleniumFunctions.setByValues(path + ";" + method));

        if (exists == null) {
            if (eleObj == null) {
                UtilFunctions.log("Element: '" + elementName + "' does not exist. Returning false.");
                flag = false;
            } else {
                eleObj.click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UtilFunctions.log("Element: '" + elementName + "' exist and clicked. Returning true.");
                flag = true;
            }
            Assert.assertTrue(flag);
        } else {
            try {
                eleObj.click();
            } catch (Exception e) {
                e.printStackTrace();

                UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " : Complete");
            }
        }
    }

    @When("^I click the \"(.*?)\" link( if it exists)?$")
    public void clickLink(String linkName, String exists) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        if (exists == null)
            Assert.assertTrue("Link: " + linkName + " not found", Page.clickLinkText(Hooks.getDriver(), linkName));
        else
            Page.clickLinkText(Hooks.getDriver(), linkName);

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^the message \"(.*?)\" should be displayed$")
    public void checkAlertText(String text) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebDriverWait wait = new WebDriverWait(driver, GlobalConstants.TWENTY);

        String appText = null;
        wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//div[@class='mszbox zoomIn'] | //div[@class='popup-body']/*"), text));

        WebElement alert = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='mszbox zoomIn'] | //div[@class='popup-body']/*"));
        appText = alert.getText();
        UtilFunctions.log("This is the app text: " + appText);

        appText = appText.trim().replaceAll("\\s+", " ");
        Assert.assertTrue((appText.contains(text)));

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^the \"(.*?)\" page should (load|not load)(?: within \"(.*?)\" seconds?)?$")
    public void checkPageLoad(String pageName, String action, String time) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        WebElement pageElement = null;
        curPageName = pageName;
        if (time == null) {
            //System.out.println("Do nothing!");
            pageElement = Page.findPage(Hooks.getDriver(), curPageName, pageName);
        } else {
            pageElement = Page.findPage(Hooks.getDriver(), curPageName, pageName, Integer.parseInt(time));
        }
        if (action.equals("load"))
            Assert.assertNotNull("Object for pane: " + pageName + " Not Found", pageElement);
        else
            Assert.assertNull("Object for pane: " + pageName + " Found", pageElement);

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^I wait \"(.*?)\" seconds until the \"(.*?)\" element is (not displayed|loaded)$")
    public void waitForLoad(String time, String elementName, String state) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        int waitTime;
        elementName = elementName.replace(" ", "");
        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(curPageName);
        String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "MISC_ELEMENTS." + elementName);
        String path = elementType[0];
        String method = elementType[1];
        SeleniumFunctions.explicitWait(Hooks.getDriver(), GlobalConstants.TEN, path + ";" + method);
        WebElement eleObj = SeleniumFunctions.findElement(Hooks.getDriver(), SeleniumFunctions.setByValues(path + ";" + method));
        if (time == null) {
            waitTime = GlobalConstants.FIFTEEN;
        } else
            waitTime = Integer.parseInt(time);

        if (state.equals("loaded")) {
            try {
                SeleniumFunctions.explicitWait(Hooks.getDriver(), waitTime, path + ";" + method);
            } catch (Exception e) {
                e.printStackTrace();
                UtilFunctions.log("Page not loded" + e.getMessage());
            }
        } else if (state.equals("not loaded")) {
            SeleniumFunctions.explicitWaitForInvisibility(Hooks.getDriver(), waitTime, path + ";" + method);
            if (!eleObj.isDisplayed()) {
                UtilFunctions.log("Element invisible");
            }
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @When("^I select \"(.*?)\" from the \"(.*?)\" dropdown( if it exists)?$")
    public void selectFromDropdown(String value, String dropDownName, String exists) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        if (exists == null) {
            Page.selectDropDownInPane(Hooks.getDriver(), curPageName, value, dropDownName);
        } else {
            //Catch errors when operating on a dropdown that may not exist
            try {
                Page.selectDropDownInPane(Hooks.getDriver(), curPageName, value, dropDownName);
            } catch (Exception e) {
                e.printStackTrace();
                if (exists == null) {
                    Assert.assertTrue(e.getMessage(), false);
                }
                UtilFunctions.log("Dropdown '" + dropDownName + "' does not exist. Exception: " + e.getMessage());
            }
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^I click on the \"(.*?)\" task \"(.*?)\"$")
    public void clickTask(String taskType, String taskTitle) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement elt = null;
        if (taskType.equals("Poll")) {
            elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='pollbox' and descendant::span[normalize-space(./text())='" + taskTitle + "']]"));
            elt.click();
        } else if (taskType.equals("Video")) {
            elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='videobox' and descendant::span[normalize-space(./text())='" + taskTitle + "']]"));
            elt.click();
        } else {
            elt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='appbox' and descendant::span[normalize-space(./text())='" + taskTitle + "']]"));
            elt.click();
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @When("^I select \"(.*?)\" from the \"(.*?)\" radio list$")
    public void selectRadioListButton(String value, String radioName) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        Thread.sleep(500);
        Assert.assertTrue("Radio Button: " + radioName + " not found", Page.setRadioValue(Hooks.getDriver(), curPageName, value, radioName));
        Thread.sleep(500);
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");

    }

    @When("^I switch to \"(.*?)\" window$")
    public void switchWindow(String type) throws Throwable {
        if (type.equals("new")) {
            SeleniumFunctions.switchToNewWindow(driver);
        } else {
            Hooks.getDriver().switchTo().window(GlobalConstants.parentWindow);
        }
    }

    @When("^I close the window$")
    public void closeNewWindow() throws Throwable {
        SeleniumFunctions.closeNewWindow(driver);
    }

    @When("^I enter value in the text field \"(.*?)\" from text file \"(.*?)\"$")
    public void ReadFile(String fieldName, String fileName) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        fieldName = fieldName.replace(" ", "");
        UtilFunctions.log("Text box field name: " + fieldName);

        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(curPageName);
        String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "TEXT_FIELDS." + fieldName);
        String textPath = elementType[0];
        String textMethod = elementType[1];
        WebElement textElt = SeleniumFunctions.findElement(Hooks.getDriver(), SeleniumFunctions.setByValues(textPath + ";" + textMethod));

        ArrayList<String> arr = new ArrayList<>();
        FileReader fr = new FileReader("F:\\"+fileName+".txt");
        BufferedReader br = new BufferedReader(fr);
        String x ="";
        while ((x = br.readLine()) != null) {
            arr.add(x);
        }
        svgText = arr.toString().replace("[","");
        svgText = svgText.replace("]","");
        System.out.println(svgText);
        br.close();

        try{
            textElt.click();
            textElt.clear();
            textElt.sendKeys(svgText);
        } catch (Exception e){
            e.printStackTrace();
            UtilFunctions.log("Text field"+ fieldName +"not set");
        }


        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @When("^I click the element with booking id \"(.*?)\" and booking status \"(.*?)\"$")
    public void selectBooking(String bookingId, String bookingStatus) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        try {
            WebElement bookingElt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//tr/descendant::td[text()='"+bookingId+"' and following-sibling::td[text()='"+bookingStatus+"']]"));
            bookingElt.click();
        } catch (Exception e) {
            e.printStackTrace();
            UtilFunctions.log("Booking Id: " + bookingId + " does not exists. Exception: " + e.getMessage());
        }
//        try {
//            WebElement bookingElt1 = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//div[@class='historyContainer' and descendant::*[text()='" + bookingId + "']]"));
//            bookingElt1.click();
//        }catch (Exception e){
//            e.printStackTrace();
//            UtilFunctions.log("Booking Id: " + bookingId + " does not exists. Exception: " + e.getMessage());
//        }
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }
}