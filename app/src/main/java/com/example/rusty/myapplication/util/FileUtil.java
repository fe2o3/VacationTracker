package com.example.rusty.myapplication.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rusty on 9/26/2015.
 */
public class FileUtil {
    private final Context context;

    public FileUtil(Context context) {
        this.context = context;

    }

    public void writeToFile(String filename, String data) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadFile(String filename) {
        byte[] data = new byte[1024];
        StringBuffer sb = new StringBuffer("");
        int offset = 0;
        int n;
        FileInputStream fis = null;
        try {
            try {
                fis = context.openFileInput(filename);
            } catch (FileNotFoundException e) {
                FileOutputStream fos = null;

                try {
                    fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
                    fos.write(null);
                    fos.flush();
                } catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                } finally {

                    fos.close();
                }

                fis = context.openFileInput(filename);
            }
            while ((n = fis.read(data, offset, 1024)) != -1) {
                sb.append(new String(data, 0, n));
            }
            fis.close();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return sb.toString();
    }

}
