/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.spidtimain;

/**
 * Runnable to launch barracuda viewer in new thread
 * @author Diego Garibay-Pulido 2016
 */
public class ViewerRunnable implements Runnable{
    private BarracudaViewUI b;
    final BarracudaLoader a;
    final String filename;
    
    public ViewerRunnable(BarracudaLoader a){
        this.a=a;
        filename=null;
    }
    
    public ViewerRunnable(BarracudaLoader a,String filename){
        this.a=a;
        this.filename=filename;
    }
    
    public void run(){
        a.setVisible(false);
        if(filename==null)
            b= new BarracudaViewUI();
        else
            b= new BarracudaViewUI(filename);
        
        b.setVisible(true);
        b.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                    b.dispose();
                    b=null;
                    Thread.currentThread().interrupt(); 
                    a.setVisible(true);
                }
            });
    }
    
    
}
