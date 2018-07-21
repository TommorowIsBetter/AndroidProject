package com.example.a10628.myapplication;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by 10628 on 2018/3/16.
 */
//页面类
public class Page {
    long hovertime ;//停留时间

    String packagename;//包名

    List<Node> nodes;//节点表

    String filename;//文件名

    int width = 1080;//屏幕宽

    int height = 1920;//屏幕高

    List<Event> events;//事件列表

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getHovertime() {
        return hovertime;
    }

    public void setHovertime(long hovertime) {
        this.hovertime = hovertime;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
