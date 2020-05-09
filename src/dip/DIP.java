/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dip;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author 41819
 */
public class DIP {
    public int[] Resize(Image img){
        //height<530,width<680 [width,height]
        int height=530;
        int width=680;
        int [] size=new int[2];
        if((img.getHeight()>height&&img.getWidth()<width)||(img.getHeight()/height>=img.getWidth()/width)){
            size[1]=height;
            double w=img.getWidth()*height/img.getHeight();
            size[0]=(int)w;
        }
        else if(img.getHeight()<height&&img.getWidth()>width||(img.getHeight()/height<img.getWidth()/width)){
            size[0]=width;
            double h=img.getHeight()*width/img.getWidth();
            size[1]=(int)h;
        }
        return size;
    }
    
    public WritableImage RGB2Gray(WritableImage img){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        
        WritableImage gray_img=new WritableImage(width,height);
        
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterGray = gray_img.getPixelWriter();
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                Color color=pixelReader.getColor(i,j);
                color = color.grayscale();
                pixelWriterGray.setColor(i, j, color);
            }
        return gray_img;
    }
    
    public WritableImage RGB2BGR(WritableImage img){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        
        WritableImage img1=new WritableImage(width,height);
        
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterGray = img1.getPixelWriter();
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                Color color=pixelReader.getColor(i,j);
                pixelWriterGray.setColor(i, j, Color.color(color.getBlue(), color.getGreen(), color.getRed()));
            }
        return img1;
    }
    
    public WritableImage RGB2GBR(WritableImage img){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        
        WritableImage img1=new WritableImage(width,height);
        
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterGray = img1.getPixelWriter();
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                Color color=pixelReader.getColor(i,j);
                pixelWriterGray.setColor(i, j, Color.color(color.getGreen(), color.getBlue(), color.getRed()));
            }
        return img1;
    }
    
    public WritableImage RGB2RBG(WritableImage img){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        
        WritableImage img1=new WritableImage(width,height);
        
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterGray = img1.getPixelWriter();
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                Color color=pixelReader.getColor(i,j);
                pixelWriterGray.setColor(i, j, Color.color(color.getRed(), color.getBlue(), color.getGreen()));
            }
        return img1;
    }
    
    public WritableImage RGB2BRG(WritableImage img){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        
        WritableImage img1=new WritableImage(width,height);
        
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterGray = img1.getPixelWriter();
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                Color color=pixelReader.getColor(i,j);
                pixelWriterGray.setColor(i, j, Color.color(color.getBlue(), color.getRed(), color.getGreen()));
            }
        return img1;
    }
    
    public WritableImage RGB2GRB(WritableImage img){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        
        WritableImage img1=new WritableImage(width,height);
        
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterGray = img1.getPixelWriter();
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                Color color=pixelReader.getColor(i,j);
                pixelWriterGray.setColor(i, j, Color.color(color.getGreen(), color.getRed(), color.getBlue()));
            }
        return img1;
    }
    
    private double[] RGB2HSV_pixel(double r,double g,double b){
        double[] color=new double[3];
        double Cmax=(r>g?r:g)>b?(r>g?r:g):b;
        double Cmin=(r<g?r:g)<b?(r<g?r:g):b;
        double delta=Cmax-Cmin;
        double H=0,S=0,V=0;
        V=Cmax;
        if(Cmax==0)
            S=0;
        else
            S=delta/Cmax;
        if(delta==0)
            H=0;
        else if(Cmax==r&&g>=b)
            H=60*((g-b)/delta);
        else if(Cmax==r&&g<b)
            H=60*(((g-b)/delta)+6);
        else if(Cmax==g)
            H=60*((b-r)/delta+2);
        else if(Cmax==b)
            H=60*((r-g)/delta+4);
        color[0]=H;
        color[1]=S;
        color[2]=V;
        
        return color;
    }
    
    public WritableImage RGB2HSV(WritableImage img){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        double r=0;
        double g=0;
        double b=0;
        double[] c=RGB2HSV_pixel(r,g,b);
        WritableImage hsv_img=new WritableImage(width,height);
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterHSV = hsv_img.getPixelWriter();
        //ColorAdjust colorAdjust = new ColorAdjust(); 
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                Color color=pixelReader.getColor(i,j);
                r=color.getRed();
                g=color.getGreen();
                b=color.getBlue();
                c=RGB2HSV_pixel(r,g,b);
                pixelWriterHSV.setColor(i, j, Color.rgb((int)(c[0]*255/360),(int)(c[1]*255),(int)(c[2]*255)));
            }
        return hsv_img;
    }
    
    public WritableImage RGB2RGB(Image img){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        
        WritableImage gray_img=new WritableImage(width,height);
        
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriter = gray_img.getPixelWriter();
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                Color color=pixelReader.getColor(i,j);
                pixelWriter.setColor(i, j, color);
            }
        return gray_img;
    }
    
    /*
    *mode=0:RGB
    *mode=1:Gray
    *mode=2:
    */
    public void Img_View(WritableImage img,ImageView imageView){
       // ImageView imageView=new ImageView();
            imageView.setImage(img);//height<530,width<440
            int[] img_size=Resize(img);
            imageView.setFitHeight(img_size[1]);
            imageView.setFitWidth(img_size[0]);
    }
    
    private float[][] Reverse(float[][] kernel){
        int width=kernel[0].length;
        int height=kernel.length;
        for(int i=0;i<height/2;i++)
            for(int j=0;j<width;j++){
                float t=kernel[i][j];
                kernel[i][j]=kernel[height-i-1][width-j-1];
                kernel[height-i-1][width-j-1]=t;
            }
        for(int j=0;j<width/2;j++){
            float t=kernel[height/2][j];
            kernel[height/2][j]=kernel[height/2][width-j-1];
            kernel[height/2][width-j-1]=t;
        }
        return kernel;
    }
    
    private Color Set_color(float r,float g,float b,float gray,int mode){
        Color color=Color.color(0,0,0);
        r=(r<=1?r:1);
        r=(r>=0?r:0);
        g=(g<=1?g:1);
        g=(g>=0?g:0);
        b=(b<=1?b:1);
        b=(b>=0?b:0);
        gray=(gray<=1?gray:1);
        gray=(gray>=0?gray:0);
        if(mode==0){
            color=Color.color(r, g, b);
        }
        else if(mode==1)
            color=Color.gray(gray);
        return color;
    }
    
    public WritableImage convolution(WritableImage img,float[][] kernel,int mode){
        kernel=Reverse(kernel);
        int width=kernel[0].length;
        int height=kernel.length;
        float r=0,g=0,b=0,gray=0;
        WritableImage img1=new WritableImage((int)img.getWidth(),(int)img.getHeight());
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriter = img1.getPixelWriter();
        for(int i=0;i<(int)img.getWidth();i++)
            for(int j=0;j<(int)img.getHeight();j++){
                if(i<(int)(width/2)){//left
                    if(j<(int)(height/2)){//left-top
                        for(int x=(int)(width/2)-i;x<width;x++)
                            for(int y=(int)(height/2)-j;y<height;y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                            }
                            pixelWriter.setColor(i, j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    } 
                    else if(j>img.getHeight()-height/2-1){//left-buttom
                        for(int x=(int)(width/2)-i;x<width;x++)
                            for(int y=0;y<(int)height/2-j+img.getHeight();y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                }
                            }
                            pixelWriter.setColor(i, j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    }
                    else{
                        for(int x=(int)(width/2)-i;x<width;x++)
                            for(int y=0;y<height;y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                }
                            }
                            pixelWriter.setColor(i, j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    }
                }    
                    
                else if(i>img.getWidth()-width/2-1){//right
                    if(j<(int)(height/2)){//right-top
                        for(int x=0;x<(int)width/2-i+img.getWidth();x++)
                            for(int y=(int)(height/2)-j;y<height;y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                }
                            }
                            pixelWriter.setColor(i, j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    } 
                    else if(j>img.getHeight()-height/2-1){//right-buttom
                        for(int x=0;x<(int)width/2-i+img.getWidth();x++)
                            for(int y=0;y<(int)height/2-j+img.getHeight();y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                }
                            }
                            pixelWriter.setColor(i, j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    }
                    else{
                        for(int x=0;x<(int)width/2-i+img.getWidth();x++)
                            for(int y=0;y<height;y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                }
                            }
                            pixelWriter.setColor(i, j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    }
                }
                
                else{
                    if(j<(int)(height/2)){//top
                        for(int x=0;x<width;x++)
                            for(int y=(int)(height/2)-j;y<height;y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                }
                            }
                            pixelWriter.setColor(i, j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    }
                    else if(j>img.getHeight()-height/2-1){
                        for(int x=0;x<width;x++)
                            for(int y=0;y<(int)height/2-j+img.getHeight();y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                }
                            }
                            pixelWriter.setColor(i, j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    }
                    else{
                        for(int x=0;x<width;x++)
                            for(int y=0;y<height;y++){
                                if(mode==0){
                                    r+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray+=kernel[x][y]*pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                }
                            }
                            pixelWriter.setColor(i,j,Set_color(r,g,b,gray,mode));
                            r=g=b=gray=0;
                    } 
                }
            }    
        return img1;
    }
    
    private Color Get_median_color(double[] r,double[] g,double[] b,double[] gray,int index,int mode){
        Color color=Color.rgb(0,0,0);
        if(mode==0){
            Arrays.sort(r);
            Arrays.sort(g);
            Arrays.sort(b);
            color=Color.color(r[index], g[index], b[index]);
        }
        else if(mode==1){
            Arrays.sort(gray);
            color=Color.gray(gray[index]);
        }
        return color;
    }
    
    public WritableImage Median(WritableImage img,int size,int mode){
        int width=size;
        int height=size;
        double[] r=new double[width*height],g=new double[width*height],b=new double[width*height],gray=new double[width*height];
        int index=0;
        WritableImage img1=new WritableImage((int)img.getWidth(),(int)img.getHeight());
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriter = img1.getPixelWriter();
        for(int i=0;i<(int)img.getWidth();i++)
            for(int j=0;j<(int)img.getHeight();j++){
                if(i<(int)(width/2)){//left
                    if(j<(int)(height/2)){//left-top
                        for(int x=(int)(width/2)-i;x<width;x++)
                            for(int y=(int)(height/2)-j;y<height;y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    } 
                    else if(j>img.getHeight()-height/2-1){//left-buttom
                        for(int x=(int)(width/2)-i;x<width;x++)
                            for(int y=0;y<(int)height/2-j+img.getHeight();y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    }
                    else{
                        for(int x=(int)(width/2)-i;x<width;x++)
                            for(int y=0;y<height;y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    }
                }    
                    
                else if(i>img.getWidth()-width/2-1){//right
                    if(j<(int)(height/2)){//right-top
                        for(int x=0;x<(int)width/2-i+img.getWidth();x++)
                            for(int y=(int)(height/2)-j;y<height;y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    } 
                    else if(j>img.getHeight()-height/2-1){//right-buttom
                        for(int x=0;x<(int)width/2-i+img.getWidth();x++)
                            for(int y=0;y<(int)height/2-j+img.getHeight();y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    }
                    else{
                        for(int x=0;x<(int)width/2-i+img.getWidth();x++)
                            for(int y=0;y<height;y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    }
                }
                
                else{
                    if(j<(int)(height/2)){//top
                        for(int x=0;x<width;x++)
                            for(int y=(int)(height/2)-j;y<height;y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    }
                    else if(j>img.getHeight()-height/2-1){
                        for(int x=0;x<width;x++)
                            for(int y=0;y<(int)height/2-j+img.getHeight();y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    }
                    else{
                        for(int x=0;x<width;x++)
                            for(int y=0;y<height;y++){
                                if(mode==0){
                                    r[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    g[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getGreen();
                                    b[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getBlue();
                                }
                                else if(mode==1){
                                    gray[index]=pixelReader.getColor(i-width/2+x,j-height/2+y).getRed();
                                    
                                }
                                index++;
                            }
                        pixelWriter.setColor(i,j,Get_median_color(r,g,b,gray,(int)width*height/2,mode));
                        r=new double[width*height];
                        g=new double[width*height];
                        b=new double[width*height];
                        gray=new double[width*height];
                        index=0;
                    } 
                }
            }    
        return img1;
    }
    
    public WritableImage image_process(WritableImage img,int option){
        WritableImage img1=new WritableImage((int)img.getWidth(),(int)img.getHeight());
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterGray = img1.getPixelWriter();
        
        for(int i=0;i<(int)img.getWidth();i++)
            for(int j=0;j<(int)img.getHeight();j++){
                Color color=pixelReader.getColor(i,j);
                switch(option){
                    case 0: color=color.invert();
                            pixelWriterGray.setColor(i, j, color);
                            break;
                    case 1: color=color.brighter();
                            pixelWriterGray.setColor(i, j, color);
                            break;
                    case 2: color=color.darker();
                            pixelWriterGray.setColor(i, j, color);
                            break;
                    case 3: color=color.saturate();
                            pixelWriterGray.setColor(i, j, color);
                            break;
                    case 4: color=color.desaturate();
                            pixelWriterGray.setColor(i, j, color);
                            break;
                }
                
            }
        
        
        return img1;
    }
    
    
    public WritableImage Binary (WritableImage img,double threshold){
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        
        WritableImage img1=new WritableImage(width,height);
        
        PixelReader pixelReader = img.getPixelReader();
        PixelWriter pixelWriterGray = img1.getPixelWriter();
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++){
                double color=pixelReader.getColor(i,j).getRed();
                color=color>threshold?1:0;
                pixelWriterGray.setColor(i, j, Color.gray(color));
            }
        return img1;
        
    }
    
}
