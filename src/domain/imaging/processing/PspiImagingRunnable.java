/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.processing;

import data.niftilibrary.niftijio.DrawableNiftiVolume;
import data.niftilibrary.niftijio.NiftiVolume;
import java.awt.Cursor;
import user.gui.spidtimain.BarracudaPspiDTIUI;

/**
 * This class enables the pspiDTI algorithm to work in parallel to the UI.
 * @author Diego Garibay-Pulido 2016
 */
public class PspiImagingRunnable implements Runnable{
    private DrawableNiftiVolume ictalFA, baselineFA, ictalTR, baselineTR, binaryMask;
    private String workingDirectory;
    private String patientInitials;
    private double alpha;
    private javax.swing.JTextPane console;
    private BarracudaPspiDTIUI a;
    double [] thresholds;
    boolean correction = false;
    boolean intensityShift = false;
    /**
     * Constructor
     * @param ictalFA Post-ictal FA NIFTI volume
     * @param baselineFA Baseline FA NIFTI volume
     * @param ictalTR Post-ictal Trace NIFTI volume
     * @param baselineTR Baseline Trace NIFTI volume
     * @param binaryMask Binary mask NIFTI volume (optional)
     * @param workingDirectory Working directory
     * @param patientInitials Patient initials
     * @param alpha Confidence level
     * @param console Output console
     * @param a UI handler
     * @param thresholds Array containing the FA and the Trace minimum change thresholds
     */
    public PspiImagingRunnable(DrawableNiftiVolume ictalFA, DrawableNiftiVolume baselineFA,
                   DrawableNiftiVolume ictalTR, DrawableNiftiVolume baselineTR,
                   DrawableNiftiVolume binaryMask, String workingDirectory,
                   String patientInitials,double alpha,javax.swing.JTextPane console,
                   BarracudaPspiDTIUI a,double thresholds[]){
        this.alpha=alpha;
        this.patientInitials=patientInitials;
        this.binaryMask=binaryMask;
        this.workingDirectory=workingDirectory;
        this.baselineFA=baselineFA;
        this.ictalFA=ictalFA;
        this.baselineTR=baselineTR;
        this.ictalTR=ictalTR;
        this.console=console;
        this.a=a;
        this.thresholds=thresholds;
    }
    
    public PspiImagingRunnable(DrawableNiftiVolume ictalFA, DrawableNiftiVolume baselineFA,
                   DrawableNiftiVolume ictalTR, DrawableNiftiVolume baselineTR,
                   DrawableNiftiVolume binaryMask, String workingDirectory,
                   String patientInitials,double alpha,javax.swing.JTextPane console,
                   BarracudaPspiDTIUI a,double thresholds[], boolean correction){
        this.alpha=alpha;
        this.patientInitials=patientInitials;
        this.binaryMask=binaryMask;
        this.workingDirectory=workingDirectory;
        this.baselineFA=baselineFA;
        this.ictalFA=ictalFA;
        this.baselineTR=baselineTR;
        this.ictalTR=ictalTR;
        this.console=console;
        this.a=a;
        this.thresholds=thresholds;
        this.correction = correction;
    }
    
    public PspiImagingRunnable(DrawableNiftiVolume ictalFA, DrawableNiftiVolume baselineFA,
                   DrawableNiftiVolume ictalTR, DrawableNiftiVolume baselineTR,
                   DrawableNiftiVolume binaryMask, String workingDirectory,
                   String patientInitials,double alpha,javax.swing.JTextPane console,
                   BarracudaPspiDTIUI a,double thresholds[], boolean correction,boolean intensityShift){
        this.alpha=alpha;
        this.patientInitials=patientInitials;
        this.binaryMask=binaryMask;
        this.workingDirectory=workingDirectory;
        
        NiftiVolume temp = new NiftiVolume(baselineFA.data.get4DArray());
        temp.header=baselineFA.header;
        this.baselineFA= new DrawableNiftiVolume(temp);
        
        temp = new NiftiVolume(ictalFA.data.get4DArray());
        temp.header=ictalFA.header;
        this.ictalFA= new DrawableNiftiVolume(temp);

        temp = new NiftiVolume(baselineTR.data.get4DArray());
        temp.header=baselineTR.header;
        this.baselineTR = new DrawableNiftiVolume(temp);
        
        temp = new NiftiVolume(ictalTR.data.get4DArray());
        temp.header=ictalTR.header;
        this.ictalTR = new DrawableNiftiVolume(temp);
        
        this.console=console;
        this.a=a;
        this.thresholds=thresholds;
        this.correction = correction;
        this.intensityShift = intensityShift;
    }
    
    @Override
    public void run() {
        a.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        pspiDTI p;
        
        p= new pspiDTI(ictalFA,baselineFA,
                    ictalTR,baselineTR,
                    binaryMask, workingDirectory,
                    patientInitials,alpha,console,thresholds,correction,intensityShift);
        p.calculate();
        p=null;
        a.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
