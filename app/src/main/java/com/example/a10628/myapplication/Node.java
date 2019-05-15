package com.example.a10628.myapplication;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

//控件节点类
public class Node {
    // 包名
    String Packagename;
    // 类名
    String Classname;
    // 文本
    String Text;
    // 坐标
    Rect Bounds;


    public Node(AccessibilityNodeInfo node){
        /*通过获取到结点，然后对node进行初始化，给node的四个属性赋予值，但是这里的Bounds没有看出来
        怎么赋予的值*/
        Bounds = new Rect();
        // 防止空指针错误，加入这个判断后就可以多次录制不同的操作序列，而不需要重新启动APP
        if (node != null){
            node.getBoundsInScreen(Bounds);
            Classname = node.getClassName().toString();
            Packagename = node.getPackageName().toString();
            if(node.getText() != null)
                Text = node.getText().toString();
            // 后期添加部分
            else if(node.getContentDescription() != null)
                Text = node.getContentDescription().toString();
            else
                Text ="";
        }
    }

    public String getPackagename() {

        return Packagename;
    }

    public void setPackagename(String packagename) {

        Packagename = packagename;
    }

    public String getClassname() {

        return Classname;
    }

    public void setClassname(String classname) {

        Classname = classname;
    }

    public String getText() {

        return Text;
    }

    public void setText(String text) {

        Text = text;
    }

    public Rect getBounds() {

        return Bounds;
    }

    public void setBounds(Rect bounds) {

        Bounds = bounds;
    }
}
