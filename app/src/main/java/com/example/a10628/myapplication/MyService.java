package com.example.a10628.myapplication;

import	android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import static android.content.ContentValues.TAG;


/**
 * Created by 10628 on 2018/2/28.
 */

public class MyService extends AccessibilityService {

    long time_counter = System.currentTimeMillis();

    static LinkedList<Event> eventsList = new LinkedList<>();

    static LinkedList<Node> nodeInfosList = new LinkedList<>();

    boolean hasRecorded = false;

    Page curpage = new Page();

    //事件处理逻辑
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();    //获取事件类型
        //Log.d("evtype",AccessibilityEvent.eventTypeToString(eventType));
        switch (eventType) {
            case(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED):{ //如果页面文本变化则存储
                Record();
                hasRecorded = false;
                break;
            }
            default: { //当不满足case的时候才会执行default，一旦执行上面的case语句就会在最后执行break，
                //所以此时这里的default也就不会执行。
                if(!hasRecorded){//当hasRecorded为false的时候执行catchPage()函数
                    catchPage();   //设置page的packageName和nodes这两个属性
                    hasRecorded = true;
                }
                Event event1 = new Event();//设置event name和source(即发起控件)
                event1.setEventname(AccessibilityEvent.eventTypeToString(eventType));//设置event name
                Node source = new Node(event.getSource());
                if (source != null)
                    event1.setSource(source);//设置event 的source属性
                else
                    event1.setSource(null);
                eventsList.add(event1);//把事件添加到eventList里面去
                break;
            }
        }

    }

    public void catchPage(){//设置page的packageName和nodes这两个属性
        curpage = new Page();
        curpage.setPackagename(getPackageName());//设置page的packageName
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if(root == null){
            Log.i("page","null");
        }else {
            recycle(root);//通过递归的方式获取所有的结点的信息
        }
        curpage.setNodes(nodeInfosList);//设置page的nodes
    }

    //记录页面信息
    public void Record(){

        if(eventsList.size() == 0 || System.currentTimeMillis() - time_counter <= 1000){
            nodeInfosList.clear();
            eventsList.clear();
            time_counter = System.currentTimeMillis();
            return;
        }
        curpage.setHovertime(System.currentTimeMillis() - time_counter);
        curpage.setEvents(eventsList);
           /* UUID uuid = UUID.randomUUID();
            //保存至文件
            MyFile.SaveRecord(curpage, uuid.toString());*/
        String now = String.valueOf(System.currentTimeMillis());
        MyFile.SaveRecord(curpage,now);


        nodeInfosList.clear();
        eventsList.clear();
        time_counter = System.currentTimeMillis();
        Log.d(this.getPackageName(),MyFile.outDirectory.getAbsolutePath());

    }

    //遍历节点信息，然后进行保存到nodeInfoList中
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            nodeInfosList.add(new Node(info));//这里的Node(info)已经通过初始化函数初始化过了，可以直接
            //添加到nodeInfosList中去了
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    recycle(info.getChild(i));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
