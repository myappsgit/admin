package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import utils.UtilFunctions;
import wrapper.SeleniumFunctions;

public class AdvertiserStepdefs {
	public String className = getClass().getSimpleName();
    @Given("^I select the following categories$")
    public void setCategory(DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        
        List<String> dataList = dataTable.asList(String.class);
        for(String data : dataList){   
            WebElement catElt = SeleniumFunctions.findElement(Hooks.getDriver(), By.xpath("//label[contains(@for, 'appCategory') and text()='"+data+"']"));
            catElt.click();
        }
            
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }
}
