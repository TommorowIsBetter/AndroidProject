package com.example.a10628.myapplication;

import android.graphics.Rect;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//控件节点类
public class Node {
    String Packagename;//包名
    String Classname;//类名;
    String Text;//文本
    Rect Bounds;//坐标


    public Node(AccessibilityNodeInfo node){ //通过获取到结点，然后对node进行初始化，给node的四个属性
        //赋予值，但是这里的Bounds没有看出来怎么赋予的值
        Bounds = new Rect();
        if (node != null){//防止空指针错误，加入这个判断后就可以多次录制不同的操作序列，而不需要重新启动APP。
            node.getBoundsInScreen(Bounds);
            Classname = node.getClassName().toString();
            Packagename = node.getPackageName().toString();
            if(node.getText() != null)
                Text = node.getText().toString();
            else if(node.getContentDescription() != null)    //后期添加部分
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
