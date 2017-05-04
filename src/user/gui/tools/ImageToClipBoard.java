/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.tools;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Diego Garibay-Pulido 2017 RUSH University Medical Center
 */
public class ImageToClipBoard implements ClipboardOwner {
    
    public void copyToClipboard(BufferedImage i){
        try{
            TransferableImage trans = new TransferableImage( i );
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents( trans, this );
           
        } 
        catch(Exception e){
            e.printStackTrace();
        }
            
    }
    @Override
    public void lostOwnership( Clipboard clip, Transferable trans ) {
        
    }
    private class TransferableImage implements Transferable {

        Image i;

        public TransferableImage( Image i ) {
            this.i = i;
        }

        public Object getTransferData( DataFlavor flavor )
        throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                return i;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
    }
}
