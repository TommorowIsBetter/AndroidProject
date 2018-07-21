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
        int eventType = event.getEventType();
        //Log.d("evtype",AccessibilityEvent.eventTypeToString(eventType));
        switch (eventType) {
            case(AccessibilityEvent.TYPE_VIEW_CLICKED):{
                catchPage();
                Record();
                break;
            }
            case(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED):{
                break;
            }
            case(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED):{
                break;
            }
            default: {
                Event event1 = new Event();
                event1.setEventname(AccessibilityEvent.eventTypeToString(eventType));
                Node source = new Node(event.getSource());
                if (source != null)
                    event1.setSource(source);
                else
                    event1.setSource(null);
                eventsList.add(event1);
                break;
            }
        }

    }

    public void catchPage(){

        try {
            Thread.currentThread().sleep(2000);//阻断2秒,以便抓取整个界面，否则抓取不全
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        curpage = new Page();
        curpage.setPackagename(getPackageName());
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if(root == null){
            Log.i("page","null");
        }else {
            recycle(root);
        }
        curpage.setNodes(nodeInfosList);
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

    //遍历节点信息
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            nodeInfosList.add(new Node(info));
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
