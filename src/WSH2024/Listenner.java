package WSH2024;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Listenner implements ActionListener, MouseListener, ChangeListener {
    int slidernumber;
    JSlider slide;
    Graphics g1;
    BufferedImage img=null;
    int w,h;
    String btype;
    public void actionPerformed(ActionEvent e){
        String text=e.getActionCommand();
        if (text.equals("加载图片")) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & PNG Images", "jpg", "png");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getPath();
                System.out.println("You chose to open this file: " + path);
                File file = new File(path);
                try {
                    img = ImageIO.read(file);// 加载图片
                    w = img.getWidth();
                    h = img.getHeight();// 加载宽高
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else {
            btype=text;
        }
    }
    public void mouseClicked(MouseEvent e){
        System.out.println("点击");
    }
    public void mousePressed(MouseEvent e){
        System.out.println("按住");
        int x=e.getX();
        int y=e.getY();
        if (btype.equals("原图")){
            g1.drawImage(img,x,y,null);
        } else if (btype.equals("灰度")) {
            for ( int i=0;i<w;i++){
                for (int j=0;j<h;j++){
                    int rgb=img.getRGB(i,j);
                    Color color1=new Color(rgb);
                    int red= color1.getRed();
                    int green=color1.getGreen();
                    int blue=color1.getBlue();
                    int gray=(red+green+blue)/3;
                    Color color2=new Color(gray,gray,gray);
                    g1.setColor(color2);
                    g1.fillRect(x+i,y+j,1,1);
                }
            }
        } else if (btype.equals("二值化")) {
            drawBlackWhite(x,y);
        } else if (btype.equals("马赛克")) {
            drawmasaike(x,y);
        } else if (btype.equals("反片")) {
            drawopsite(x,y);
        } else if (btype.equals("油画")) {
            drawoil(x,y);
        } else if (btype.equals("轮廓画")) {
            drawsome(x,y);
        } else if (btype.equals("红变绿")) {
            drawredtogreen(x,y);
        } else if (btype.equals("蓝变红")) {
            drawbluetored(x,y);
        }
    }
    public void mouseReleased(MouseEvent e){
        System.out.println("释放");
    }
    public void mouseEntered(MouseEvent e){
        System.out.println("进入");
    }
    public void mouseExited(MouseEvent e){
        System.out.println("退出");
    }
    //《——————————封装分界线————————————》
    //二值化
    public void drawBlackWhite(int x,int y){
        for ( int i=0;i<w;i++){
            for (int j=0;j<h;j++){
                int rgb=img.getRGB(i,j);
                Color color1=new Color(rgb);
                int red= color1.getRed();
                int green=color1.getGreen();
                int blue=color1.getBlue();
                int gray=(red+green+blue)/3;
                if (gray<slidernumber){
                    g1.setColor(Color.black);
                }else {
                    g1.setColor(Color.white);
                }
                g1.fillRect(x+i,y+j,1,1);
            }}}
    //马赛克
    public void drawmasaike(int x,int y){
        for ( int i=0;i<w;i+=10){
            for (int j=0;j<h;j+=10){
                int rgb=img.getRGB(i,j);
                Color color1=new Color(rgb);
                g1.setColor(color1);
                g1.fillRect(x+i,y+j,10,10);
            }
        }
    }
    //反片
    public void drawopsite(int x,int y){
        for ( int i=0;i<w;i+=1){
            for (int j=0;j<h;j+=1){
                int rgb=img.getRGB(i,j);
                Color color1=new Color(rgb);
                int red= color1.getRed();
                int green=color1.getGreen();
                int blue=color1.getBlue();
                Color color2=new Color(255-red,255-green,255-blue);
                g1.setColor(color2);
                g1.fillRect(x+i,y+j,1,1);
            }
        }
    }
    //油画
    public void drawoil(int x,int y){
        Random random=new Random();
        for ( int i=0;i<w;i+=3){
            for (int j=0;j<h;j+=3){
                int rgb=img.getRGB(i,j);
                Color color1=new Color(rgb);
                int red= color1.getRed();
                int green=color1.getGreen();
                int blue=color1.getBlue();
                int width= random.nextInt(5)+5;
                int height=random.nextInt(5)+7;
                g1.setColor(color1);
                g1.fillRect(x+i,y+j,width,height);
            }
        }
    }
    //轮廓画
    public void drawsome(int x,int y){
        for ( int i=0;i<w-2;i++){
            for (int j=0;j<h-2;j++){
                int rgb=img.getRGB(i,j);
                Color color1=new Color(rgb);
                int red= color1.getRed();
                int green=color1.getGreen();
                int blue=color1.getBlue();
                int gray=(red+green+blue)/3;
                int rgb1=img.getRGB(i+2,j+2);
                Color color2=new Color(rgb1);
                int red1= color2.getRed();
                int green1=color2.getGreen();
                int blue1=color2.getBlue();
                int gray1=(red1+green1+blue1)/3;
                if(Math.abs(gray-gray1)<10){
                    g1.setColor(Color.white);
                }else {g1.setColor(Color.black);}
                g1.fillRect(x+i,y+j,1,1);
            }
        }

    }
    //红变绿
    public void drawredtogreen(int x,int y){
        for (int i=0;i<w;i++){
            for (int j=0;j<h;j++){
                int rgb= img.getRGB(i,j);
                Color color=new Color(rgb);
                int red= color.getRed();
                int green= color.getGreen();
                int blue= color.getBlue();
                Color color1=new Color(green,red,blue);
                g1.setColor(color1);
                g1.fillRect(x+i,y+j,1,1);
            }
        }
    }
    //蓝变红
    public void drawbluetored(int x,int y){
        for (int i=0;i<w;i++){
            for (int j=0;j<h;j++){
                int rgb=img.getRGB(i,j);
                Color color=new Color(rgb);
                int red= color.getRed();
                int blue= color.getBlue();
                int green= color.getGreen();
                Color color1=new Color(blue,green,red);
                g1.setColor(color1);
                g1.fillRect(x+i,y+j,1,1);
            }
        }
    }
    //滑杆
    public void stateChanged(ChangeEvent e){
        slidernumber=slide.getValue();
    }




























}
