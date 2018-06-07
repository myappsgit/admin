package frames;

import java.util.HashMap;


/******************************************************************************
 Class Name: Global_Frames
 Contains hash map to map and store frame name and value for frames common
 across the application
 ******************************************************************************/

public class Global_Frames {

    //Hash Map to store frame values and names related to global frames
    public static HashMap<String, String> globalFramesMap = new HashMap<>();


    /**************************************************************************
     * name: setGlobalFramesMap()
     * functionality: Set frame name and value
     * return: void
     *************************************************************************/
    public static void setGlobalFramesMap(){
        //main parent frame
        globalFramesMap.put("PARENT_FRAME", "workspace");

        //parent frame - all frames in application are children of this
        //need to update the frame id as per the application
        globalFramesMap.put("FRAME_BASE", "temp");

        //parent frame for main iframe
        //need to update this frame id, if any iFrame present
        globalFramesMap.put("FRAME_GLOBAL_MAIN", globalFramesMap.get("FRAME_BASE") + "temp1");
    }

}
