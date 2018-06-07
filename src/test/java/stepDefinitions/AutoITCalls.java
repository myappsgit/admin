package stepDefinitions;


import cucumber.api.java.en.Given;
import utils.UtilFunctions;
import utils.UtilProperty;

public class AutoITCalls {
	public String className = getClass().getSimpleName();
	
    @Given("^I upload the \"(.*?)\" file$")
    public void fileUpload(String fileName) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");

        GlobalStepdefs globalStepdefs = new GlobalStepdefs();
        globalStepdefs.clickMiscElement("Upload", "", "");
        Runtime.getRuntime().exec(UtilProperty.sMainDir + "\\src\\test\\java\\autoItScripts\\"+fileName+".exe");
        
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }
}
