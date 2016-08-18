/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.tools;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/**
 *
 * @author
 * <p>
 * Diego Garibay-Pulido 2016</p>
 */
public abstract class IconGetter {
    public  Image getProjectIcon(String filename){
        Image img;
        filename="/images/"+filename;
        URL imageurl = getClass().getResource(filename);
        img = Toolkit.getDefaultToolkit().getImage(imageurl);
        return img;
    }
}
