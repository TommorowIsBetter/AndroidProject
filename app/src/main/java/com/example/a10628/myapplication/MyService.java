package com.example.a10628.myapplication;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.LinkedList;


/**
 * Created by 10628 on 2018/2/28.
 */

public class MyService extends AccessibilityService {
    long time_counter = System.currentTimeMillis();
    static LinkedList<Event> eventsList = new LinkedList<>();
    static LinkedList<Node> nodeInfosList = new LinkedList<>();
    boolean hasRecorded = false;
    Page curpage;
    // 事件处理逻辑
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 获取事件类型
        int eventType = event.getEventType();
        switch (eventType) {
            // 如果页面文本变化则存储:窗口的内容发生变化，或者更具体的子树根布局变化事件
            case(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED):{
                Record();
                hasRecorded = false;
                break;
            }
            default: {
                /*当不满足case的时候才会执行default，一旦执行上面的case语句就会在最后执行break，所以
                此时这里的default也就不会执行。*/
                if(!hasRecorded){
                    // 当hasRecorded为false的时候执行catchPage()函数,设置page的packageName和nodes这两个属性
                    catchPage();
                    hasRecorded = true;
                }
                // 设置event name和source(即发起控件)
                Event event1 = new Event();
                // 设置event name
                event1.setEventname(AccessibilityEvent.eventTypeToString(eventType));
                Node source = new Node(event.getSource());
                if (source != null)
                    // 设置event 的source属性
                    event1.setSource(source);
                else
                    event1.setSource(null);
                // 把事件添加到eventList里面去
                eventsList.add(event1);
                break;
            }
        }

    }

    // 设置page的packageName和nodes这两个属性
    public void catchPage(){
        curpage = new Page();
        // 设置page的packageName
        curpage.setPackagename(getPackageName());
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if(root == null){
            Log.i("page","null");
        }else {
            // 通过递归的方式获取所有的结点的信息
            recycle(root);
        }
        // 设置page的nodes
        curpage.setNodes(nodeInfosList);
    }

    // 记录页面信息
    public void Record(){

        if(eventsList.size() == 0 || System.currentTimeMillis() - time_counter <= 1000){
            nodeInfosList.clear();
            eventsList.clear();
            time_counter = System.currentTimeMillis();
            return;
        }
        curpage.setHovertime(System.currentTimeMillis() - time_counter);
        curpage.setEvents(eventsList);
        String now = String.valueOf(System.currentTimeMillis());
        MyFile.SaveRecord(curpage,now);
        nodeInfosList.clear();
        eventsList.clear();
        time_counter = System.currentTimeMillis();
        Log.d(this.getPackageName(),MyFile.outDirectory.getAbsolutePath());

    }

    // 遍历节点信息，然后进行保存到nodeInfoList中
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            // 这里的Node(info)已经通过初始化函数初始化过了，可以直接添加到nodeInfosList中去了
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
