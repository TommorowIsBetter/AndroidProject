package com.example.a10628.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    File sdCardDir = Environment.getExternalStorageDirectory();

    // 页面点击函数业务逻辑
    public void onClick(android.view.View view){
        if(checkFile())
        {
            showDialog();
            return;
        }
        // 首先先建一个TestDir文件夹
        newDirectory(sdCardDir.toString(), "TestDir");
        // 首先先建一个TestDirBackup文件夹
        newDirectory(sdCardDir.toString(), "TestDirBackup");
        Date date = new Date();
        // 这里目的是将当前时间转换为字符串，然后为后面建立文件夹
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = format.format(date);
        // 把原先的文件夹里面的所有文件移动到新的文件夹中去,并且把所有json文件合并为一个json文件
        moveFileFolder(sdCardDir.toString()+"/ThinkTime", now);
        // 把/ThinkTime文件夹下的所有文件移到一个备份文件夹中去。让ThinkTime文件夹准备录制下次的内容，
        // 本来想通过删除/thinkTime文件夹下的内容但是没有删除成功，只能通过移除操作代替
        moveFileToBackup(sdCardDir.toString()+"/ThinkTime", now);
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


    //移动某个文件夹下的所有文件到指定文件夹下，并且合并为一个json文件
    public void moveFileFolder(String oldPath, String name){
        try{
           MergeJsons(oldPath, sdCardDir.toString() + "/TestDir/" + name, name);
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
        builder.setMessage("ThinkTime文件夹下文件合并成功！");
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
        try{                              //如果文件不存在，就新建它，否则会出现空指针错误，系统宕掉
            if(!baseFile.exists()){
                baseFile.mkdir();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        File[] fileList = baseFile.listFiles();//把文件下的所有文件都放到list列表中去
        if(fileList.length == 0)
            return true;
        else
            return false;
    }


    /**
     *
     * @param filepath 待处理的文件夹
     * @param aimPath 目的存入的指定文件夹
     * @param name 重新给生成的json文件进行命名
     * @throws IOException
     * @Description 把一个文件夹下的所有json文件变成一个json文件，然后存储在指定的文件夹中。
     */
    public static void MergeJsons(String filepath, String aimPath, String name) throws IOException{
        File baseFile = new File(filepath); //File类型可以是文件也可以是文件夹
        File[] fileList = baseFile.listFiles(); //将该目录下的所有文件放置在一个File类型的数组中
        List<ThinkTimeBean> thinkTimeBeanList = new ArrayList<ThinkTimeBean>();//建立一个结合用于存放待会获取json的对象
        if (fileList == null){    //空指针进行处理
            System.out.println("filepath doesn't exist!");
        }else {
            for (int i = 0; i < fileList.length; i++) {
                String str = FileUtils.readFileToString(fileList[i], "utf-8");//把json文件转换为字符串
                try{
                    ThinkTimeBean thinkTimeBean = JSON.parseObject(str, ThinkTimeBean.class);//把字符串转换为实体类
                    thinkTimeBeanList.add(thinkTimeBean);//json实体类加入到集合中
                }catch(Exception e){
                    System.out.println("the format of file is wrong!");
                }
            }
        }
        File fileDirectory = new File(aimPath);
        if (!fileDirectory.exists()){
            fileDirectory.mkdirs();
        }
        File file = new File(aimPath + "/" + name +".json");
        if (!file.exists()){
            file.createNewFile();
        }
        String mergeJson = JSON.toJSONString(thinkTimeBeanList,true);//把json实体类集合转换为字符串
        FileUtils.writeStringToFile(file, mergeJson, "utf-8");//把字符串写入到文件中
    }


    //点击button键在界面弹出提示
    public void showDialogBackupSuccess(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("ThinkTime已经备份到指定位置！");
        builder.setPositiveButton("我知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }


    //移动某个文件夹下的所有文件到备份文件夹
    public void moveFileToBackup(String oldPath, String newPath){
        newDirectory(sdCardDir.toString() + "/" + "TestDirBackup", newPath);//先新建一个文件夹，
        //待会把文件移动到这个新建的文件夹
        try{
            File baseFile = new File(oldPath);//这里可以填写文件路径，而不只是具体的某个文件
            File[] fileList = baseFile.listFiles();//把文件下的所有文件都放到list列表中去
            for (int i = 0; i < fileList.length; i++){
                //下面一条是移动函数代码
                if( fileList[i].renameTo(new File(sdCardDir.toString() + "/TestDirBackup" + "/" + newPath + "/" + fileList[i].getName()))){
                    System.out.println("move successful.");
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        showDialogBackupSuccess();//备份文件之后，提出成功提示！
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
