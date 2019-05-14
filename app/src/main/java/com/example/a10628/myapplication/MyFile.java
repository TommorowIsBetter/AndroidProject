package com.example.a10628.myapplication;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 10628 on 2018/3/16.
 */
// 文件操作类
public class MyFile {
    static File sdCardDir = Environment.getExternalStorageDirectory();
    static File outDirectory = new File(sdCardDir,"ThinkTime");
    // 保存记录
    public static void SaveRecord(Page page,String filename){

        if(!outDirectory.exists()){
            boolean b = outDirectory.mkdir();
            Log.d("dir created",String.valueOf(b));
        }

        File outfile = new File(outDirectory,filename+".json");
        if(!outfile.exists()) {
            try {
                boolean bool = outfile.createNewFile();
                Log.d("file created",String.valueOf(bool));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        String str = gson.toJson(page);

        try {
            FileOutputStream fout = new FileOutputStream(outfile);
            byte[] bs = str.getBytes();
            fout.write(bs);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
