package protego.com.protego;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by chanijindal on 23/12/14.
 */
public class Iptables {

    public static boolean hasRoot= false;
    public static AppsInfo applications[]=null;
    public static final String SHAREDPREF_NAME = "Preferences";
    public static final String SHAREDPREF_INTERFACE="Interfaces";
    public static final String SHAREDPREF_INTERFACE_WIFI="Wi-Fi";
    public static final String SHAREDPREF_INTERFACE_2G="2G/3G";
    public static final String SHAREDPREF_ALLOWEDUIDS="Allowed UIDs";
    public static final String SHAREDPREF_MODE="Blockmode";
    public static final String SHAREDPREF_MODE_WHITELIST="whitelist";
    public static final String SHAREDPREF_MODE_BLACKLIST="blacklist";





    public static void showAlertBox(String message,Context context)
    {
        if(context!=null)
        {
           AlertDialog.Builder alert= new AlertDialog.Builder(context);
           alert.setMessage(message);
           alert.show();
        }
    }


    public static boolean hasRoot(Context context) {
        if (hasRoot)
            return true;

        try {


            if (runAsRootUser("", null, 1000) == 0) {
                showAlertBox("Root Access granted",context);
                hasRoot = true;
                return true;
            }
        } catch (Exception e)
        {
        }

        showAlertBox("Cannot acquire the root access .Either phone is not rooted or root permission not granted",context);
        return false;





    }

    public static int runAsRootUser(String script,StringBuilder result,long timeout)
    {
        ScriptRunner runner =new ScriptRunner(script,result);
        runner.start();

            try {
                if(timeout>0)
                runner.join(timeout);
                else
                  runner.join();
                if(runner.isAlive())
                    runner.interrupt();
                   // runner.destroy();
                    runner.join(50);

            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return runner.exitValue;
            }
        return runner.exitValue;
    }



}
