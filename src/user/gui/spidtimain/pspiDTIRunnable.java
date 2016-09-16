/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.spidtimain;

import java.awt.Cursor;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class pspiDTIRunnable implements Runnable {
    private BarracudaPspiDTIUI b;
    private final BarracudaLoader a;
    
    public pspiDTIRunnable(BarracudaLoader a){
        this.a=a;
    }
    
    @Override
    public void run() {
        
        a.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        b= new BarracudaPspiDTIUI();
        b.setVisible(true);
        a.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        
        b.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                    b.dispose();
                    b=null;
                    Thread.currentThread().interrupt(); 
                    a.setVisible(true);
                }
            });
        a.setVisible(false);
    }
    
}
