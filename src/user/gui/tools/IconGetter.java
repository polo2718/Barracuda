/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.tools;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author
 * <p>
 * Diego Garibay-Pulido 2016</p>
 */
public abstract class IconGetter {
    public  static Image getProjectIcon(String filename){
        Object o=new Object();
        Image img;
        filename="/images/"+filename;
        URL imageurl = o.getClass().getResource(filename);
        img = Toolkit.getDefaultToolkit().getImage(imageurl);
        return img;
    }
    public static ImageIcon getProjectImageIcon(String filename){
        Image img=getProjectIcon(filename);
        ImageIcon icon=new ImageIcon(img);
        return icon;
    }
}
