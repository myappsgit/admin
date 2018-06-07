package api.support;

import java.util.HashMap;

/******************************************************************************
 Class Name: TabBar
 Contains functions related to navigation pages
 ******************************************************************************/

public class TabBar{

    public String className = getClass().getSimpleName();
    protected HashMap<String, String> tabSet = new HashMap<>();
    {
        tabSet.put("CreateUser", "usrDts/");
    }
}

