/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 *<p> This class contains useful functions for use in the java swing UI </p>
 * @author Diego Garibay-Pulido 2016
 */
public abstract class UITools {
    /**
     * <p>Receives a buffered image and resizes it to adjust it to the JLabel in 
     * which the image will be placed and shows it on the JLabel</p>
     * @param img The buffered image
     * @param label The label in which the image will be shown
     */
    public static double imageToLabel(BufferedImage img,JLabel label){
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
    
    public static int doubleToRGB(double val,double max,double min,String colormap){
        int rgb,temp,mod;
        double mul;
        double range=max-min;
        switch(colormap){
            case"hot":
                mul=765/range;
                val=val-min;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=0;
                }
                else if((val*mul)>765){ //More than max display is white

                    rgb = 0xFFFFFFFF;
                }
                else if(val<0){ //Less than min is black
                    rgb=0xFF000000;
                }else{
                    val=val*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //Only red
                            rgb=255<<24|mod<<16;
                            break;
                        case 1: //Red and green
                            rgb=255<<24|255<<16|mod<<8;
                            break;
                        case 2: //Red green and blue
                            rgb=255<<24|255<<16|255<<8|mod;
                            break;
                        default:
                            rgb=0;
                            break;
                    }
                }
                break;
            case"hot_invert":
                mul=765/range;
                val=val-min;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=0;
                }
                else if((val*mul)>765){ //More than max display is black
                    rgb = 0xFF000000;
                }
                else if(val<0){ //Less than min is black
                    rgb=0xFF000000;
                }else{
                    val=val*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //
                            rgb=255<<24|255<<16|255<<8|(255-mod);
                            break;
                        case 1: //
                            rgb=255<<24|255<<16|(255-mod)<<8;
                            break;
                        case 2: //
                            rgb=255<<24|(255-mod)<<16;
                            break;
                        default:
                            rgb=0;
                            break;
                    }
                }
                break;
            case"winter":
                mul=765/range;
                val=val-min;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=0;
                }
                else if((val*mul)>765){ //More than max display is white
                    rgb = 0xFFFFFFFF;
                }
                else if(val<0){ //Less than min is black
                    rgb=0xFF000000;
                }else{
                    val=val*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //Only blue
                            rgb=255<<24|mod;
                            break;
                        case 1: //Blue and green
                            rgb=255<<24|mod<<8|255;
                            break;
                        case 2: //Blue Green and red
                            rgb=255<<24|mod<<16|255<<8|255;
                            break;
                        default:
                            rgb=0;
                            break;
                    }
                }
                break;
            case"winter_invert":
                mul=765/range;
                val=val-min;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=0;
                }
                else if((val*mul)>765){ //More than max display is black
                    rgb = 0xFF000000;
                }
                else if(val<0){ //Less than min is black
                    rgb=0xFF000000;
                }else{
                    val=val*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //
                            rgb=255<<24|(255-mod)<<16|255<<8|255;
                            break;
                        case 1: //
                            rgb=255<<24|(255-mod)<<8|255;
                            break;
                        case 2: //
                            rgb=255<<24|(255-mod);
                            break;
                        default:
                            rgb=0;
                            break;
                    }
                }
                break;
            case"rainbow":
                mul=1020/range;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=0;
                }
                else if((val*mul)>1020){ //More than max display is red
                    rgb = 0xFFFF0000;
                }
                else if(val<0){ //Less than min is black
                    rgb=0xFF000000;
                }else{
                    val=(val-min)*mul; //Multiply by dynamic range
                    temp=(int) Math.floor(val/255);
                    mod=(int) val%255;
                    switch(temp){
                        case 0: //Blue to Yellow
                            rgb=255<<24|mod<<8|255;
                            break;
                        case 1: //Yellow to Green
                            rgb=255<<24|255<<8|(255-mod);
                            break;
                        case 2: //Green to orange
                            rgb=255<<24|mod<<16|255<<8;
                            break;
                        case 3:
                            rgb=255<<24|255<<16|(255-mod)<<8;
                            break;
                        default:
                            rgb=0;
                            break;
                    }
                }
                break;
            default://Default is grayscale
                mul=255.0/range;
                if(Double.isNaN(val)){ // NaN is zero
                    rgb=0;
                }
                else if((val*mul)>255){ //More than 255 is white
                     rgb = 0xFFFFFFFF;
                }
                else if(val<0){ //Less than min is black
                    rgb=0xFF000000;
                }
                else{
                    temp=(int)(val*mul);
                    rgb = 255<<24|temp<<16|temp<<8|temp;
                }
                break;
        }
        
        
        
        
        return rgb;
    }
    
}
