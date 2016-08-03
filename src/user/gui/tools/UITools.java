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

/**
 *
 * @author Diego Garibay-Pulido
 */
public abstract class UITools {
    
    public static void imageToLabel(BufferedImage img,javax.swing.JLabel label){
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
        //Determine which is bigger
        double s=(double)H;
        if(W>H){
            s=(double)W;
        }
        //int temp=(int) s;
        //if(maxN>temp){
        s=(double)(s/maxN);
        n[0]=(int)(n[0]*s);
        n[1]=(int)(n[1]*s);
         
        int finalw = img.getWidth();
        int finalh = img.getHeight();

        finalh = (int)(finalh * s);                
        finalw = (int)(finalw * s);

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, finalw, finalh, null);
        g2.dispose();
        
        img=resizedImg;
        imageIcon = new ImageIcon(img);
        label.setIcon(imageIcon);
        
    }
        
}

