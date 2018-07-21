package com.example.a10628.myapplication;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by 10628 on 2018/3/16.
 */
//事件类
public class Event {

    String eventname;//事件名

    Node source;//发起控件

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public  Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }
}
