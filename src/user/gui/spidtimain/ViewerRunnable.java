/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.spidtimain;

import java.awt.Cursor;
import java.io.File;

/**
 * Runnable to launch barracuda viewer in new thread
 * @author Diego Garibay-Pulido 2016
 */
public class ViewerRunnable implements Runnable{
    private BarracudaViewUI b;
    private final BarracudaLoader a;
    private final String filename;
    private File file;
    
    public ViewerRunnable(BarracudaLoader a){
        this.a=a;
        filename=null;
    }
    
    public ViewerRunnable(BarracudaLoader a,String filename){
        this.a=a;
        this.filename=filename;
    }
    
    @Override
    public void run(){
        a.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        if(filename==null)
            b= new BarracudaViewUI();
        else
            b= new BarracudaViewUI(filename);
        
        b.setVisible(true);
        a.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        b.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                    file = b.getFile();
                    b.dispose();
                    b=null;
                    Thread.currentThread().interrupt(); 
                    a.setVisible(true);
                }
            });
        a.setVisible(false);
    }
    public File getFile(){
        return file;
    }
    
}
