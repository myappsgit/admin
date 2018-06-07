package stepDefinitions.REST;

import cucumber.api.DataTable;
import cucumber.api.java.en.When;
import gherkin.deps.net.iharder.Base64;
import stepDefinitions.GlobalStepdefs;
import utils.UtilFunctions;
import api.api_DataRepository.CreateUser;
import api.support.TabBar;

import java.util.List;

import api.APICommon;

public class RestGlobalStepdefs extends TabBar {
	
    public String className = getClass().getSimpleName();
    public static String globalURLType = "";
    
    @When("^API: I perform \"(.*?)\" action on \"(.*?)\" api with the following payload$")
    public void performAPIActions(String httpMethod, String context, DataTable dataTable) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        
        context = context.replace(" ", "");
        globalURLType = tabSet.get(context);

        APICommon create = new APICommon();
        create.CreateUser(httpMethod, globalURLType, dataTable);
        
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }
    
    @When("^API: I fetch the session id with username \"(.*?)\" and password \"(.*?)\"$")
    public void fetchSessionId(String username, String password) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        
        APICommon sessionId = new APICommon();
        sessionId.getSessionId(username, password);
        
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }
    
    @When("^API: I add \"(.*?)\" task \"(.*?)\" from the location \"(.*?)\"$")
    public void addTask(String taskType, String taskName, String location) throws Throwable {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Start");
        
        APICommon createTask = new APICommon();
        if(taskType.equals("Video")){
        	createTask.CreateVideoTask(taskName, location);
        } else if(taskType.equals("App")){
        	//add method to handle App tasks
        } else{
        	//add method to handle Poll tasks
        }
        
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Complete");
    }
}

