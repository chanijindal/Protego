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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

import static android.content.DialogInterface.*;
import static protego.com.protego.Iptables.*;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{


    Button applyRulesButton,showRulesButton,showLogButton,chooseDirectoryButton ;
    TextView resultTextView;
    public static  StringBuilder script =new StringBuilder();
    public static StringBuilder result =new StringBuilder();
    public static StringBuilder logResult=new StringBuilder();
    String m_chosenDir = "";
    boolean m_newFolderEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyRulesButton= (Button) findViewById(R.id.applyRulesButton);
        applyRulesButton.setOnClickListener(this);
        showRulesButton= (Button) findViewById(R.id.showRulesButton);
        showRulesButton.setOnClickListener(this);
        showLogButton= (Button) findViewById(R.id.showLogButton);
        showLogButton.setOnClickListener(this);
        chooseDirectoryButton= (Button) findViewById(R.id.chooseDirectoryButton);
        chooseDirectoryButton.setOnClickListener(this);
        resultTextView= (TextView) findViewById(R.id.resultTextView);

        Iptables.hasRoot(this);


        if(Iptables.runAsRootUser(script.toString(),result,1000)==0) {

            Toast.makeText(this, "Hurray iptables is working", Toast.LENGTH_LONG).show();
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




    public static  void makeIptableRules()
    {
      script.append("iptables -I INPUT 1 -j LOG --log-prefix \"[IPT IN START] \" --log-level 4 --log-uid\n");

    }


    public static  void showIptableRules()
    {
        script.append("iptables -L\n");

    }


    public static void showLogOutput()
    {
        script.append("su -c \"fgrep '[IPT IN ' /proc/kmsg\"");
    }




    public  void showDirectoryDialog()
    {




        DirectoryChooserDialog directoryChooserDialog =
                new DirectoryChooserDialog(MainActivity.this,
                        new DirectoryChooserDialog.ChosenDirectoryListener()
                        {
                            @Override
                            public void onChosenDir(String chosenDir)
                            {
                                m_chosenDir = chosenDir;
                                Toast.makeText(
                                        MainActivity.this, "Chosen directory: " +
                                                chosenDir, Toast.LENGTH_LONG).show();
                            }
                        });
        // Toggle new folder button enabling
        directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
        // Load directory chooser dialog for initial 'm_chosenDir' directory.
        // The registered callback will be called upon final directory selection.
        directoryChooserDialog.chooseDirectory(m_chosenDir);
        m_newFolderEnabled = ! m_newFolderEnabled;
    }

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

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.applyRulesButton:
                makeIptableRules();
                resultTextView.setText("IPTABLE RULES APPLIED");
                runAsRootUser(script.toString(),result,1000);
                return;
            case R.id.showRulesButton:
                showIptableRules();
                runAsRootUser(script.toString(),result,1000);
                showAlertBox(result.toString(),this);
                return;
            case R.id.showLogButton:
               // showLogOutput();
                runAsRootUser("su -c \"fgrep '[IPT IN ' /proc/kmsg\"",logResult,1000);
                showAlertBox(logResult.toString(),this);
                if(MakeLogTextFile.makeFile(logResult.toString(),m_chosenDir,this)==true)
                resultTextView.setText("TEXT FILE FOR LOGS CREATED");
                else
                resultTextView.setText("TEXT FILE COULD NOT BE CREATED");
                return;

            case R.id.chooseDirectoryButton:
                showDirectoryDialog();
                return;

        }
    }
}
