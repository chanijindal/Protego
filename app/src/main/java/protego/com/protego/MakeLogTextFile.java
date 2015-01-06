package protego.com.protego;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by chanijindal on 06/01/15.
 */
public class MakeLogTextFile {


    public static boolean makeFile(String logData,String path,Context context) {
        File logFile = new File(path+"/log.txt");

            try {
                logFile.createNewFile();
                //FileOutputStream fileOutputStream = context.openFileOutput("log.txt", Context.MODE_APPEND);
                //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                //outputStreamWriter.write(logData);
                //outputStreamWriter.close();
                //fileOutputStream.close();
                FileWriter fileWriter = new FileWriter(logFile.getAbsoluteFile());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(logData);
                bufferedWriter.close();
                fileWriter.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;


        }



    }


}
