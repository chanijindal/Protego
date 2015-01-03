package protego.com.protego;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

import static android.content.DialogInterface.*;
import static protego.com.protego.Iptables.*;


public class MainActivity extends ActionBarActivity {

    StringBuilder script =new StringBuilder();
    StringBuilder result =new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iptables.hasRoot(this);
       // script="tcpdump || exit";
      String rule1 ="iptables -I INPUT 1 -j LOG --log-prefix \"[IPT IN START] \" --log-level 4 --log-uid ||exit/n ";
       // String start="su -c \"fgrep '[IPT IN ' /proc/kmsg\"|| exit\n";
       // script.append("iptables -F || exit\n");
       // String rule2 ="iptables -L";
         script.append(rule1);
       // script.append(start);
      /* if( Iptables.runAsRootUser(script,result,1000)==0)
           Toast.makeText(this,"Packets captured",Toast.LENGTH_LONG).show();
        else
           Toast.makeText(this,"error capturing packets",Toast.LENGTH_LONG).show();
*/
        if(Iptables.runAsRootUser(script.toString(),result,1000)==0) {
            /*Log.d("Am I printing","Yes");
            if(result.toString().isEmpty())
            {
                Log.d("Error message","No result");
            }
            else
            Log.d("what the hell","is happening");

            //Log.e("Just checking", result.toString());
            */
           // Toast.makeText(this, "Hurray iptables is working", Toast.LENGTH_LONG).show();

           showAlertBox(result.toString(),this);

        }
        else
            Toast.makeText(this,"Oops I guess there's a problem",Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public static void showAlertBox(String message,Context context)
    {
        if(context!=null)
        {
            AlertDialog.Builder alert= new AlertDialog.Builder(context);
            alert.setMessage(message);
            alert.show();
        }
    }


   /* private void checkPreferences()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(SHAREDPREF_NAME,0);
        Editor editor = sharedPreferences.edit();
        boolean changed=false;
        if(sharedPreferences.getString(SHAREDPREF_INTERFACE,"").length()==0) {
            editor.putString(SHAREDPREF_INTERFACE, SHAREDPREF_INTERFACE_2G);
            changed=true;
        }
        if(sharedPreferences.getString(SHAREDPREF_MODE,"").length()==0) {
            editor.putString(SHAREDPREF_MODE, SHAREDPREF_MODE_WHITELIST);
            changed=true;
        }

        if(changed)
            editor.commit();

    }


    private void selectInterface()
    { new AlertDialog.Builder(this).setItems(new String[]{"2G/3G Network","Wi-fi","Both"}, new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int which) {
            final String interfac = (which==0 ? Iptables.SHAREDPREF_INTERFACE_2G : which==1 ? Iptables.SHAREDPREF_INTERFACE_WIFI : Iptables.SHAREDPREF_INTERFACE_2G+"|"+Iptables.SHAREDPREF_INTERFACE_WIFI);
            final Editor editor = getSharedPreferences(Iptables.SHAREDPREF_NAME, 0).edit();
            editor.putString(Iptables.SHAREDPREF_INTERFACE, interfac);
            editor.commit();
           // refreshHeader();
        }
    }).setTitle("Select interfaces:")
            .show();
    }

*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
