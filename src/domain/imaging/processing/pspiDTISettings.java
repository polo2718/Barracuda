/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.processing;

import data.niftilibrary.niftijio.DrawableNiftiVolume;

/**
 *
 * @author Diego Garibay-Pulido 2017 RUSH University Medical Center
 */
public class pspiDTISettings {
    private  String workingDirectory;
    private String outputDirectory;
    private  String patientInitials;
    private  double alpha;
    private  double[] thresholds;
    private boolean correction = false;
    private boolean intensityShift = false;
    private String outputFilename;

    public pspiDTISettings(String workingDirectory,String outputDirectory, String patientInitials, double alpha, double [] thresholds, boolean correction, boolean intensityShift){
        this.workingDirectory=workingDirectory;
        this.outputDirectory=outputDirectory;
        this.patientInitials=patientInitials;
        this.alpha=alpha;
        this.thresholds=thresholds;
        this.correction=correction;
        this.intensityShift=intensityShift;
        outputFilename= outputDirectory+"\\"+patientInitials+Double.toString(alpha);
    }
    
    public pspiDTISettings(String workingDirectory, String outputDirectory, String patientInitials){
        this.workingDirectory=workingDirectory;
        this.outputDirectory=outputDirectory;
        this.patientInitials=patientInitials;
        alpha=0.01;
        thresholds=null;
        outputFilename= outputDirectory+"\\"+patientInitials+Double.toString(alpha);
    }
    
    //Getters & Setters
    public void setWorkingDirectory(String workingDirectory){
        this.workingDirectory=workingDirectory;
    }
    
    public double getAlpha(){
        return alpha;
    }
    
    public String getOutputFilename(){
        return outputFilename;
    }
    
}
