package stepDefinitions;

import constants.DataTableVals;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.UtilFunctions;
import wrapper.SeleniumFunctions;

import java.util.List;

public class AdminStepDefs {
    public String className = getClass().getSimpleName();

    @Then("^the Payment Status should contain following in the Admin Dashboard pane$")
    public void checkPaymentStatus(DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement paymentElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='paymentStatusSectionHeaderContainer' and contains(.,'Payment')]]/descendant::*/text()"));
        String paymentTxt = paymentElt.getText();

        WebElement refundElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='paymentStatusSectionHeaderContainer' and contains(.,'Refund')]]/descendant::*/text()"));
        String refundTxt = refundElt.getText();

        WebElement chargesElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='paymentStatusSectionHeaderContainer' and contains(.,'Charges')]]/descendant::*/text()"));
        String chargesTxt = chargesElt.getText();

            List<DataTableVals> dataList = dataTable.asList(DataTableVals.class);
            for(DataTableVals data : dataList) {
                String payment = data.returnName();
                String refund = data.returnType();
                String charges = data.returnValue();

                Assert.assertTrue("Payment count does not match", payment.contains(paymentTxt));
                Assert.assertTrue("Refund count does not match", refund.contains(refundTxt));
                Assert.assertTrue("Charges count does not match", charges.contains(chargesTxt));
            }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^the User Status should contain following in the Admin Dashboard pane$")
    public void checkUserStatus(DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement activeElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='userStatusHeader-Active' and contains(.,'Active')]]/descendant::*/text()"));
        String activeTxt = activeElt.getText();

        WebElement disabledElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='userStatusHeader-Disabled' and contains(.,'Disabled')]]/descendant::*/text()"));
        String disabledTxt = disabledElt.getText();

        WebElement activationPendingElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='userStatusHeader-NotActive' and contains(.,'Activation Pending')]]/descendant::*/text()"));
        String activationPendingTxt = activationPendingElt.getText();

        List<DataTableVals> dataList = dataTable.asList(DataTableVals.class);
        for(DataTableVals data : dataList) {
            String active = data.returnName();
            String disabled = data.returnType();
            String activationPending = data.returnValue();

            Assert.assertTrue("Active count does not match", active.contains(activeTxt));
            Assert.assertTrue("Disabled count does not match", disabled.contains(disabledTxt));
            Assert.assertTrue("Activation Pending count does not match", activationPending.contains(activationPendingTxt));
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }

    @Then("^the Facility Status should contain following in the Admin Dashboard pane$")
    public void checkFacilityStatus(DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        WebElement activeElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='facilityStatusSectionHeaderContainer' and contains(.,'Active')]]/descendant::*/text()"));
        String activeTxt = activeElt.getText();

        WebElement certifiedElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='facilityStatusSectionHeaderContainer' and contains(.,'Certified')]]/descendant::*/text()"));
        String certifiedTxt = certifiedElt.getText();

        WebElement notActiveElt = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//div[@class='itemContainer' and preceding-sibling::div[@class='facilityStatusSectionHeaderContainer' and contains(.,'Not Active')]]/descendant::*/text()"));
        String notActiveTxt = notActiveElt.getText();

        List<DataTableVals> dataList = dataTable.asList(DataTableVals.class);
        for(DataTableVals data : dataList) {
            String active = data.returnName();
            String certified = data.returnType();
            String notActive = data.returnValue();

            Assert.assertTrue("Active count does not match", active.contains(activeTxt));
            Assert.assertTrue("Certified count does not match", certified.contains(certifiedTxt));
            Assert.assertTrue("Not Active count does not match", notActive.contains(notActiveTxt));
        }

        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }
}
