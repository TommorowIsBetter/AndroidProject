package com.example.a10628.myapplication;

import java.util.List;

/**
 * Created by 10628 on 2018/3/16.
 */
// 页面类
public class Page {
    // 停留时间
    long hovertime;
    // 包名
    String packagename;
    // 节点表
    List<Node> nodes;
    // 文件名
    String filename;
    // 屏幕宽
    int width = 1080;
    // 屏幕高
    int height = 1920;
    // 事件列表
    List<Event> events;

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
