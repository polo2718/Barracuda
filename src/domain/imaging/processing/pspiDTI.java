/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.processing;

import data.niftilibrary.niftijio.DrawableNiftiVolume;
import data.niftilibrary.niftijio.FourDimensionalArray;
import data.niftilibrary.niftijio.NiftiVolume;
import domain.imaging.spatialfiltering.Kernel;
import domain.imaging.spatialfiltering.NiftiNonLinearSpatialFilter;
import domain.imaging.spatialfiltering.operations.UnpairedtTest;
import domain.mathUtils.arrayTools.ArrayOperations;
import domain.mathUtils.numericalMethods.statistics.LimitedStatisticalMoment;


/**
 * Class that performs the pspiDTI algorithm
 * @author Diego Garibay-Pulido 2016
 */
public class pspiDTI {
    private final DrawableNiftiVolume ictalFA, baselineFA, ictalTR, baselineTR, binaryMask;
    private final String workingDirectory;
    private String outputDirectory;
    private final String patientInitials;
    private final double alpha;
    private final javax.swing.JTextPane console;
    private final double[] thresholds;
    private boolean correction = false;
    private boolean intensityShift = false;
    private double trueAlpha;
    private int kernelSize=5;
    
    public static final boolean LEFT_TAIL=true;
    public static final boolean RIGHT_TAIL=false;
    public static final boolean FA=true;
    public static final boolean TR=false;
    /**
     * Constructor, runs with Bonferroni correction
     * @param ictalFA Post-ictal FA NIFTI volume
     * @param baselineFA Baseline FA NIFTI volume
     * @param ictalTR Post-ictal Trace NIFTI volume
     * @param baselineTR Baseline Trace NIFTI volume
     * @param binaryMask Binary mask NIFTI volume (optional)
     * @param workingDirectory Working directory
     * @param patientInitials Patient initials
     * @param alpha Confidence level
     * @param console Output console
     * @param thresholds Array containing the FA and the Trace minimum change thresholds
     * @param correction Specifies whether a correction must be applied
     */
    public pspiDTI(DrawableNiftiVolume ictalFA, DrawableNiftiVolume baselineFA,
                   DrawableNiftiVolume ictalTR, DrawableNiftiVolume baselineTR,
                   DrawableNiftiVolume binaryMask, String workingDirectory,
                   String patientInitials,double alpha,javax.swing.JTextPane console,double [] thresholds,boolean correction){
        this.alpha=alpha;
        this.patientInitials=patientInitials;
        this.binaryMask=binaryMask;
        this.workingDirectory=workingDirectory;
        this.baselineFA=baselineFA;
        this.ictalFA=ictalFA;
        this.baselineTR=baselineTR;
        this.ictalTR=ictalTR;
        this.console=console;
        this.thresholds=thresholds;
        this.correction=correction;
        
    }
    /**
     * Constructor, Bonferroni correction and Global Intensity Shift
     * @param ictalFA Post-ictal FA NIFTI volume
     * @param baselineFA Baseline FA NIFTI volume
     * @param ictalTR Post-ictal Trace NIFTI volume
     * @param baselineTR Baseline Trace NIFTI volume
     * @param binaryMask Binary mask NIFTI volume (optional)
     * @param workingDirectory Working directory
     * @param patientInitials Patient initials
     * @param alpha Confidence level
     * @param console Output console
     * @param thresholds Array containing the FA and the Trace minimum change thresholds
     * @param correction Specifies whether a correction must be applied
     * @param intensityShift flag that indicates a global intensity Shift
     */
    public pspiDTI(DrawableNiftiVolume ictalFA, DrawableNiftiVolume baselineFA,
                   DrawableNiftiVolume ictalTR, DrawableNiftiVolume baselineTR,
                   DrawableNiftiVolume binaryMask, String workingDirectory,
                   String patientInitials,double alpha,javax.swing.JTextPane console,double [] thresholds,boolean correction,boolean intensityShift){
        this.alpha=alpha;
        this.patientInitials=patientInitials;
        this.binaryMask=binaryMask;
        this.workingDirectory=workingDirectory;
        this.baselineFA=baselineFA;
        this.ictalFA=ictalFA;
        this.baselineTR=baselineTR;
        this.ictalTR=ictalTR;
        this.console=console;
        this.thresholds=thresholds;
        this.correction=correction;
        this.intensityShift=intensityShift;
    }
    /**
     * Runs pspiDTI without Bonferroni and intensity Shift
     * @param ictalFA
     * @param baselineFA
     * @param ictalTR
     * @param baselineTR
     * @param binaryMask
     * @param workingDirectory
     * @param patientInitials
     * @param alpha
     * @param console
     * @param thresholds 
     */
    public pspiDTI(DrawableNiftiVolume ictalFA, DrawableNiftiVolume baselineFA,
                   DrawableNiftiVolume ictalTR, DrawableNiftiVolume baselineTR,
                   DrawableNiftiVolume binaryMask, String workingDirectory,
                   String patientInitials,double alpha,javax.swing.JTextPane console,double [] thresholds){
        this.alpha=alpha;
        this.patientInitials=patientInitials;
        this.binaryMask=binaryMask;
        this.workingDirectory=workingDirectory;
        this.baselineFA=baselineFA;
        this.ictalFA=ictalFA;
        this.baselineTR=baselineTR;
        this.ictalTR=ictalTR;
        this.console=console;
        this.thresholds=thresholds;
    }
    
   
    /**
     * Performs the t_test and returns the p_values
     * @param flag left-tailed or right-tailed
     * @param FA_TR flag to indicate FA or TR
     * @return  Raw p_values 
     */
    private FourDimensionalArray oneTailedT_Test(boolean flag,boolean FA_TR){
        int[] dims ={0};
        Kernel w = new Kernel(1,kernelSize); //Creates the kernel
        FourDimensionalArray result; //Array to hold the results
        UnpairedtTest operation = new UnpairedtTest(); //Creates a new unpaired t-test
        NiftiNonLinearSpatialFilter spatialFilter = new NiftiNonLinearSpatialFilter(operation); //Defines the filter operation
        if(FA_TR){ //For FA values, it performs a left tail t-test
            operation.setTail(UnpairedtTest.LEFT_TAIL);
            if(binaryMask!=null){
                result= spatialFilter.doubleFilter(ictalFA.data, baselineFA.data, w, binaryMask.data, dims);
            }else{
                result= spatialFilter.doubleFilter(ictalFA.data, baselineFA.data, w, dims);
            }
        }else{ //For Trace operations, it performs a right tail t-test
            operation.setTail(UnpairedtTest.RIGHT_TAIL);
            if(binaryMask!=null){
                result= spatialFilter.doubleFilter(ictalTR.data, baselineTR.data, w, binaryMask.data, dims);
            }else{
                result= spatialFilter.doubleFilter(ictalTR.data, baselineTR.data, w, dims);
            }
        }
        return result;
    }
    
    /**
     * Cleans the p_values according to the confidence level in the constructor
     * @param pVals the clean p values
     * @param FA_TR flag
     * @return <p>Outputs a file containing the p_values and returns a binary mask where p values less or equal than alpha </p>
     */
    private FourDimensionalArray cleanPvalues(FourDimensionalArray pVals,boolean FA_TR){
        int x=pVals.sizeX();
        int y=pVals.sizeY();
        int z=pVals.sizeZ();
        double temp;
        FourDimensionalArray result= new FourDimensionalArray(x,y,z,1);
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                for(int k=0;k<z;k++){
                    temp=pVals.get(i,j,k,0);
                    if(temp>trueAlpha){//If the p value is greater than the true alpha
                        result.set(i,j,k,0,Double.NaN);
                    }else{
                        result.set(i,j,k,0,temp);
                    }
                }
            }
        }
        NiftiVolume resultingVol=new NiftiVolume();
        String str,str2;
        if(FA_TR){
            resultingVol.header=ictalFA.header;
            str=outputDirectory+"\\"+patientInitials+Double.toString(alpha)+"a_FA_Pvals.nii.gz";
            str2="Writing FA p-values...";
        }else{
            resultingVol.header=ictalTR.header;
            str=outputDirectory+"\\"+patientInitials+Double.toString(alpha)+"a_TR_Pvals.nii.gz";
            str2="Writing TR p-values...";
        }
        resultingVol.header.intent_code=22;
        resultingVol.header.cal_max=(float)alpha;
        resultingVol.header.cal_min=0;
        resultingVol.data=result;
        append(str2);
        writeVolume(resultingVol,str);
        return result;
    }
    
    /**
     * Performs the subtraction only in the values inside the mask
     * @param mask The mask in which to perform the subtraction (NaN flag)
     * @param FA_TR flag
     */
    private void subtract(FourDimensionalArray mask,boolean FA_TR){
        int x=mask.sizeX();
        int y=mask.sizeY();
        int z=mask.sizeZ();
        String outputFilename= outputDirectory+"\\"+patientInitials+Double.toString(alpha);
        FourDimensionalArray result= new FourDimensionalArray(x,y,z,1);
        double temp,threshold;
        LimitedStatisticalMoment moments = new LimitedStatisticalMoment();
        NiftiVolume resultingVol = new NiftiVolume();
        NiftiVolume ictal,baseline;
        String [] str;
        //Defines volumes to subtract
        if(FA_TR){
            ictal=ictalFA;
            baseline=baselineFA;
            str=new String[]{"FA","decrease"};
        }else{
            ictal=ictalTR;
            baseline=baselineTR;
            str=new String[]{"TR","increase"};
        }
        //Performs subtraction
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                for(int k=0;k<z;k++){
                    if(Double.isNaN(mask.get(i,j,k,0))){//If mask is NaN
                        result.set(i,j,k,0,Double.NaN);
                    }else{
                        temp=ictal.data.get(i,j,k,0)-baseline.data.get(i,j,k,0);
                        moments.accumulate(temp);
                        result.set(i,j,k,0,temp);
                    }
                }
            }
        }
        resultingVol.header=ictal.header;
        resultingVol.header.intent_code=1001;
        resultingVol.header.intent_name=new StringBuffer(str[0]+" "+str[1]);
        resultingVol.data=result;
        double max,min;
        //Defines maximum and minimum
        if(FA_TR){
            min=ArrayOperations.findMinimum(resultingVol.data.get3DArray(0));
            max=0f;
        }else{
            max=ArrayOperations.findMaximum(resultingVol.data.get3DArray(0));
            min=0f;
        }
        resultingVol.header.cal_min=(float)min;
        resultingVol.header.cal_max=(float)max;
        append("Writing "+str[0]+" Files...");
        //Write raw increase/decrease volume
        writeVolume(resultingVol,outputFilename+"a_"+str[0]+"_"+str[1]+".nii.gz");
        //Standard deviation thresholds
        double mean=moments.mean();
        double std=moments.std();
        for(int i=1;i<4;i++){
            if(FA_TR)
                threshold=mean-((double)i*std); //FA looks at decreases, sign change
            else
                threshold=mean+((double)i*std); //TR looks at increases, sign change
            resultingVol.data=cleanSubtract(result,threshold,FA_TR,1);
            append("Writing "+str[0]+": "+Integer.toString(i)+"std...");
            writeVolume(resultingVol,outputFilename+"a_"+str[0]+"_"+Integer.toString(i)+"std_"+str[1]+".nii.gz");
        }
        if(Double.isNaN(thresholds[0])& FA_TR){//If no FA threshold was specified
            for(double thr=0.05;thr<0.25;thr=thr+0.05){
                resultingVol.data=cleanSubtract(result,-1,FA_TR,thr);  
                append("Writing "+str[0]+": "+String.format("%.2f", thr)+"...");
                writeVolume(resultingVol,outputFilename+"a_"+str[0]+"_"+String.format("%.2f", thr)+"t_"+str[1]+".nii.gz");
            }
        }
        else if(Double.isNaN(thresholds[1])& !FA_TR){ //If no TR threshold was specified
            for(double thr=0.05;thr<0.25;thr=thr+0.05){
                resultingVol.data=cleanSubtract(result,1,FA_TR,thr*max);  
                append("Writing "+str[0]+": "+String.format("%.2f", thr)+"...");
                writeVolume(resultingVol,outputFilename+"a_"+str[0]+"_"+String.format("%.2f", thr)+"t_"+str[1]+".nii.gz");
            }        
        }
        else if(!Double.isNaN(thresholds[0]) & FA_TR){ //If a FA threshold was specified
            resultingVol.data=cleanSubtract(result,-1,FA_TR,thresholds[0]);
            append("Writing "+str[0]+": "+Double.toString(thresholds[0])+"...");
            writeVolume(resultingVol,outputFilename+"a_"+str[0]+"_"+Double.toString(thresholds[0])+"t_"+str[1]+".nii.gz");
        } else if(!Double.isNaN(thresholds[1]) & !FA_TR){ //If a TR threshold was specified
            resultingVol.data=cleanSubtract(result,1,FA_TR,thresholds[1]*max);
            append("Writing "+str[0]+": "+Double.toString(thresholds[1])+"...");
            writeVolume(resultingVol,outputFilename+"a_"+str[0]+"_"+Double.toString(thresholds[1])+"t_"+str[1]+".nii.gz");
        }  
    }
    
    /**
     * Cleans the subtraction with a standard deviation threshold or with a minimum change in FA/Trace
     * @param array The previously calculated subtraction
     * @param threshold The standard deviation threshold (Pre-calculated)
     * @param FA_TR Flag to indicate either FA or Trace calculation
     * @param change Minimum change in FA/Trace to make the value stay
     * @return A 4D array object containing the result of the subtraction
     */
    private FourDimensionalArray cleanSubtract(FourDimensionalArray array, double threshold,boolean FA_TR,double change){
        int x=array.sizeX();
        int y=array.sizeY();
        int z=array.sizeZ();
        //Variable to hold the result
        double temp;
        FourDimensionalArray result= new FourDimensionalArray(x,y,z,1);
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                for(int k=0;k<z;k++){
                    temp=array.get(i,j,k,0);
                    if(FA_TR & (temp<=threshold|Math.abs(temp)>=change)){
                        result.set(i,j,k,0,temp);
                    }
                    else if(!FA_TR & (temp>=threshold|Math.abs(temp)>=change)){
                        result.set(i,j,k,0,temp);
                    }
                    else{
                        result.set(i,j,k,0,Double.NaN);
                    }
                }
            }
        }
        return result;
    }

    /**
     * This function calculates the pspiDTI algorithm and prints the output to a console
     */
    public void calculate(){
        //outputDirectory=workingDirectory+patientInitials+"_pspiDTI_output";
        outputDirectory=workingDirectory;
        int voxelCount;
        if(binaryMask!=null){
            voxelCount=binaryMask.data.nonZeroCount();
        }else if(ictalFA!=null){
            voxelCount=ictalFA.data.numericCount();
        }else{
            voxelCount=ictalTR.data.numericCount();
        }
        append("Voxels: "+voxelCount + "\n");
        if(correction){
            trueAlpha=alpha/voxelCount;
            append("Corrected Alpha = "+ trueAlpha + "\n");
        }else{
            trueAlpha=alpha;
            append("Alpha = "+trueAlpha + "\n");
        }
        if(intensityShift){
            append("Performing global intensity shift correction...");
            GlobalIntensityShift();
            append("Done!!!\n");
        }
        FourDimensionalArray temp1;
        FourDimensionalArray temp2;
        if(ictalFA!=null & baselineFA!=null){
            append("Performing FA t-test...\n");
            temp1=oneTailedT_Test(LEFT_TAIL,pspiDTI.FA);
            append("Cleaning FA p-values...\n");
            temp2=cleanPvalues(temp1,pspiDTI.FA);
            append("Performing FA subtraction...\n");
            subtract(temp2,pspiDTI.FA);
        }else{
            append("No FA files were provided...Skipping Calculations\n");
        }
        if(ictalTR!=null & baselineTR!=null){
            append("Performing Trace t-test...\n");
            temp1=oneTailedT_Test(RIGHT_TAIL,pspiDTI.TR);
            append("Cleaning Trace p-values...\n");
            temp2=cleanPvalues(temp1,pspiDTI.TR);
            append("Performing Trace subtraction...\n");
            subtract(temp2,pspiDTI.TR);
        }
        else{
            append("No Trace files were provided...Skipping Calculations\n");
        }
        append("Done!!!\n");
        
    }
   
    /**
     * Appends new text to the console
     * @param str The string to be output to the console
     */
   private void append(String str){
       str=console.getText()+str;
       console.setText(str);
       console.setCaretPosition(str.length());
   }
   
   private void GlobalIntensityShift(){
       double[][][] tempIctal;
       double[][][] tempBaseline;
       double[][][] tempMask;
       double ictalMean;
       double baselineMean;
       double scalingFactor;
       if(binaryMask!=null){
           tempMask=binaryMask.data.get3DArray(0);
           if(ictalFA!=null && baselineFA!=null){
                //Retrieve 3D arrays
                tempIctal=ictalFA.data.get3DArray(0);
                tempBaseline=baselineFA.data.get3DArray(0);
                //Compute mean
                ictalMean = computeIntracerebralMean(tempIctal,tempMask);
                baselineMean = computeIntracerebralMean(tempBaseline,tempMask);
                //Compute the scalingFactor
                scalingFactor= (baselineMean/ictalMean);
                append("Scaling factor: "+scalingFactor+"\n");
                ictalFA.data.scalarMult(scalingFactor);
            }
           if(ictalTR!=null && baselineTR!=null){
               //Retrieve 3D arrays
                tempIctal=ictalTR.data.get3DArray(0);
                tempBaseline=baselineTR.data.get3DArray(0);
                //Compute mean
                ictalMean = computeIntracerebralMean(tempIctal,tempMask);
                baselineMean = computeIntracerebralMean(tempBaseline,tempMask);
                //Compute the scalingFactor
                scalingFactor= (ictalMean/baselineMean);
                ictalTR.data.scalarMult(scalingFactor);
           }
       }
   }
   private double computeIntracerebralMean(double[][][] array, double[][][] mask){
       LimitedStatisticalMoment moment = new LimitedStatisticalMoment();
       for(int i=0;i<array.length;i++){
           for(int j=0;j<array[0].length;j++){
               for(int k=0;k<array[0][0].length;k++){
                   if(!Double.isNaN(mask[i][j][k])){
                       moment.accumulate(array[i][j][k]);
                   }
               }
           }
       }
       return moment.mean();
   }
   
   private void writeVolume(NiftiVolume volume,String filename){
       try{
            volume.write(filename);
            append("\n");
        }catch(Exception e){
            append("Error writing file\n");
        }
   }
   
   
}
