package automationExceptions;

import utils.UtilFunctions;

public class ConnectionInterruptedException extends InterruptedException {

    public ConnectionInterruptedException(String s) {
        super(s);
        UtilFunctions.log("Exception occurred. Returning true.");
    }
}
