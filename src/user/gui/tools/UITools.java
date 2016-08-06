/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.tools;

import java.awt.Graphics2D;
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

        BufferedImage resizedImg = new BufferedImage(n[0], n[1], BufferedImage.TYPE_INT_RGB);
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
    
 }

