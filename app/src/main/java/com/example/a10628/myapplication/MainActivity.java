package com.example.a10628.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    File sdCardDir = Environment.getExternalStorageDirectory();

    //页面点击函数业务逻辑
    public void onClick(android.view.View view){
        if(checkFile())
        {
            showDialog();
            return;
        }
        newDirectory(sdCardDir.toString(), "TestDir");//首先先建一个TestDir文件夹
        Date date = new Date();//这里目的是将当前时间转换为字符串，然后为后面建立文件夹
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = format.format(date);
        moveFileFolder(sdCardDir.toString()+"/ThinkTime", now);//把原先的文件夹里面的所有
        //文件移动到新的文件夹中去

    }

    //新建一个文件夹的函数
    public void newDirectory(String _path, String dirName){
        File file = new File(_path + "/" + dirName);
        try{
            if(!file.exists()){
                file.mkdir();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //移动某个文件夹下的所有文件到指定文件夹下
    public void moveFileFolder(String oldPath, String newPath){
        newDirectory(sdCardDir.toString() + "/" + "TestDir", newPath);//先新建一个文件夹，
        //待会把文件移动到这个新建的文件夹
        try{
            File baseFile = new File(oldPath);//这里可以填写文件路径，而不只是具体的某个文件
            File[] fileList = baseFile.listFiles();//把文件下的所有文件都放到list列表中去
            for (int i = 0; i < fileList.length; i++){
                //下面一条是移动函数代码
                if( fileList[i].renameTo(new File(sdCardDir.toString() + "/TestDir" + "/" + newPath + "/" + fileList[i].getName()))){
                    System.out.println("move successful.");
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        showDialogSuccess();//移动文件之后，提出成功提示！
    }

    //点击button键在界面弹出提示
    public void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("ThinkTime文件夹下为空！");
        builder.setPositiveButton("我知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }

    //点击button键在界面弹出提示
    public void showDialogSuccess(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("ThinkTime文件夹下文件已经移到指定位置！");
        builder.setPositiveButton("我知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }

    //检查指定文件夹下是否有文件
    public boolean checkFile(){
        String filePath = sdCardDir.toString() + "/ThinkTime";
        File baseFile = new File(filePath);//这里可以填写文件路径，而不只是具体的某个文件
        File[] fileList = baseFile.listFiles();//把文件下的所有文件都放到list列表中去
        if(fileList.length == 0)
            return true;
        else
            return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setDimAmount(0f);
        verifyStoragePermissions(this);
        Intent i = new Intent(MainActivity.this, MyService.class);
        startService(i);
        setContentView(R.layout.activity_main);
    }

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

    }

}
