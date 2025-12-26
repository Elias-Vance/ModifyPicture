package WSH2024;

import javax.swing.*;
import java.awt.*;

public class Show {
    Listenner li=new Listenner();
    public void show(){
        JFrame window=new JFrame("图片修改器4.0");
        window.setSize(650,800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        FlowLayout flow=new FlowLayout();
        window.setLayout(flow);
        window.setVisible(true);
        String[] arr={"加载图片","原图","灰度","二值化","马赛克","反片","油画","轮廓画","红变绿","蓝变红"};
        for (int i=0;i< arr.length;i++){
            String text=arr[i];
            JButton button=new JButton(text);
            button.addActionListener(li);
            window.add(button);
        }
        //画笔
        Graphics g2=window.getGraphics();
        li.g1=g2;
        //鼠标
        window.addMouseListener(li);
        //滑杆
        JSlider slider=new JSlider(0,255,128);
        li.slide=slider;
        JLabel la=new JLabel("二值化阈值:");
        slider.addChangeListener(li);
        window.add(la);
        window.add(slider);
    }


}
