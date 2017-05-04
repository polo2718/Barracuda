/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.tools;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.datatransfer.*;
import java.io.*;

/**
 *<p> This class contains useful functions for use in the java swing UI </p>
 * @author Diego Garibay-Pulido 2016
 */
public abstract class UITools {
    public static final String COLORMAP_GREY="grayscale";
    public static final String COLORMAP_HOT="hot";
    public static final String COLORMAP_HOT_INVERTED="hot_invert";
    public static final String COLORMAP_WINTER="winter";
    public static final String COLORMAP_WINTER_INVERTED="winter_invert";
    public static final String COLORMAP_RAINBOW="rainbow";
    /**
     * <p>Receives a buffered image and resizes it to adjust it to the JLabel in 
     * which the image will be placed and shows it on the JLabel</p>
     * @param img The buffered image
     * @param label The label in which the image will be shown
     * @return The scale of the scaled image
     */
    public static double imageToLabel(BufferedImage img,JLabel label){
        double s;
        if(img!=null){
            ImageIcon imageIcon;
            //Get image dimensions
            int n[]=new int[2];
            n[0]=img.getWidth();
            n[1]=img.getHeight();
            //Determine which is bigger
            int maxN;
            if(n[0]>n[1]){maxN=n[0];}
            else{maxN=n[1];}
            
            //Get label dimensions
            int H=label.getHeight();
            int W=label.getWidth();
            //Determine which is smaller
            s=(double)H;
            if(W<H){s=(double)W;}
            
            //Scale the image dimensions to the label
            s=(double)(s/maxN);
            n[0]=(int)(n[0]*s);
            n[1]=(int)(n[1]*s);

            BufferedImage resizedImg = new BufferedImage(n[0], n[1], BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(img, 0, 0, n[0], n[1], null);
            g2.dispose();

            imageIcon = new ImageIcon(resizedImg);
            label.setIcon(imageIcon);
        }
        else{
            s=Double.NaN;
        }
        return s;
    }
    public static double imageToLabel(Image img,JLabel label){
        double s;
        if(img!=null){
        ImageIcon imageIcon;
        //Get image dimensions
        int n[]=new int[2];
        n[0]=img.getWidth(label);
        n[1]=img.getHeight(label);
        //Determine which is bigger
        int maxN=n[0];
        for(int i=1;i<2;i++){
            if(n[i]>maxN){
            maxN=n[i];
            }
        }
        //Get label dimensions
        int H=label.getHeight();
        int W=label.getWidth();
        //Determine which is smaller
        s=(double)H;
        if(W<H){
            s=(double)W;
        }
        
        s=(double)(s/maxN);
        n[0]=(int)(n[0]*s);
        n[1]=(int)(n[1]*s);

        BufferedImage resizedImg = new BufferedImage(n[0], n[1], BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, n[0], n[1], null);
        g2.dispose();
        
        img=resizedImg;
        imageIcon = new ImageIcon(img);
        label.setIcon(imageIcon);
        }
        else{
            s=Double.NaN;
        }
        return s;
    }
    /**
     * <p>Converts a double value to a corresponding RGB value given a dynamic range:
     * Inverted colormaps will make values more than max transparent while regular colormaps
     * will assign transparent colors to values smaller than the min
     * @param val value to be converted to RGB
     * @param max Dynamic range max value
     * @param min Dynamic range min value
     * @param colormap String that indicates the color maps, see class fields
     * @return A Color Object indicating the color corresponding to the value
     */
    public static Color doubleToRGB(double val,double max,double min,String colormap){
        return doubleToRGB(val,max,min,colormap,255);
    } 
    /**
     * Converts a double value to a corresponding RGB with colormap and transparency value
     * @param val value to be converted
     * @param max scale maximum
     * @param min scale minimum
     * @param colormap hot,hot_invert,winter,winter_invert,rainbow,grayscale.
     * @param alpha transparency value
     * @return The corresponding Color
     */
    public static Color doubleToRGB(double val,double max,double min,String colormap,int alpha){
        int temp,mod;
        Color rgb;
        double mul;
        double range=max-min;
        switch(colormap){
            case"hot":
                mul=765/range;
                val=val-min;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=new Color(0,0,0,0);
                }
                else if((val*mul)>765){ //More than max display is white

                    rgb=new Color(255,255,255,alpha);
                }
                else if(val<=0){ //Less than min is not shown
                    rgb=new Color(0,0,0,0);
                }else{
                    val=val*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //Only red
                            rgb=new Color(mod,0,0,alpha);
                            break;
                        case 1: //Red and green
                            rgb=new Color(255,mod,0,alpha);
                            break;
                        case 2: //Red green and blue
                            rgb=new Color(255,255,mod,alpha);
                            break;
                        default:
                            rgb=new Color(0,0,0,0);
                            break;
                    }
                }
                break;
            case"hot_invert":
                mul=765/range;
                val=val-min;
                if(Double.isNaN(val)){ // NaN is zero
                   rgb=new Color(0,0,0,0);
                }
                else if((val*mul)>765){ //More than max display is transparent
                    rgb=new Color(0,0,0,0);
                }
                else if(val<0){ //Less than min is white
                    rgb=new Color(255,255,255,alpha);
                }else{
                    val=val*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //
                            rgb=new Color(255,255,255-mod,alpha);
                            break;
                        case 1: //
                            rgb=new Color(255,255-mod,0,alpha);
                            break;
                        case 2: //
                            rgb=new Color(255-mod,0,0,alpha);
                            break;
                        default:
                            rgb=new Color(0,0,0,0);
                            break;
                    }
                }
                break;
            case"winter":
                mul=765/range;
                val=val-min;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=new Color(0,0,0,0);
                }
                else if((val*mul)>765){ //More than max display is white
                    rgb=new Color(255,255,255,alpha);
                }
                else if(val<=0){ //Less than min is black
                    rgb=new Color(0,0,0,0);
                }else{
                    val=val*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //Only blue
                            rgb=new Color(0,0,mod,alpha);
                            break;
                        case 1: //Blue and green
                            rgb=new Color(0,mod,255,alpha);
                            break;
                        case 2: //Blue Green and red
                            rgb=new Color(mod,255,255,alpha);
                            break;
                        default:
                            rgb=new Color(0,0,0,0);
                            break;
                    }
                }
                break;
            case"winter_invert":
                mul=765/range;
                val=val-min;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=new Color(0,0,0,0);
                }
                else if((val*mul)>765){ //More than max display is transparent
                    rgb=new Color(0,0,0,0);
                }
                else if(val<0){ //Less than min is white
                    rgb=new Color(255,255,255,alpha);
                }else{
                    val=val*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //
                            rgb=new Color(255-mod,255,255,alpha);
                            break;
                        case 1: //
                            rgb=new Color(0,255-mod,255,alpha);
                            break;
                        case 2: //
                            rgb=new Color(0,0,255-mod,alpha);
                            break;
                        default:
                            rgb=new Color(0,0,0,0);
                            break;
                    }
                }
                break;
            case"rainbow":
                mul=1020/range;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=new Color(0,0,0,0);
                }
                else if(((val-min)*mul)>1020){ //More than max display is red
                    rgb=new Color(255,0,0,alpha);
                }
                else if((val-min)*mul<=0){ //Less than min is blue
                    rgb=new Color(0,0,255,alpha);
                }else{
                    val=(val-min)*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //Blue to Yellow
                            rgb=new Color(0,mod,255,alpha);
                            break;
                        case 1: //Yellow to Green
                            rgb=new Color(0,255,255-mod,alpha);
                            break;
                        case 2: //Green to orange
                            rgb=new Color(mod,255,0,alpha);
                            break;
                        case 3:
                            rgb=new Color(255,255-mod,0,alpha);
                            break;
                        default:
                            rgb=new Color(0,0,0,0);
                            break;
                    }
                }
                break;
            default://Default is grayscale
                mul=255.0/range;
                if(Double.isNaN(val)){ // NaN is transparent
                    rgb=new Color(0,0,0,0);
                }
                else if(val<min){ //Less than min is transparent
                    rgb=new Color(0,0,0,0);
                }
                else if(val==min){//Min equals black
                    rgb=new Color(0,0,0,alpha);
                }
                else if(val>=max){ //More than max is white
                    rgb=new Color(255,255,255,alpha);
                }
                else if(((val-min)*mul)>255){ //More than 255 is white
                     rgb=new Color(255,255,255,alpha);
                }
                else{
                    temp=(int)((val-min)*mul);
                    rgb=new Color(temp,temp,temp,alpha);
                }
                break;
        }
        return rgb;
    }  
    
    
    
    
}
