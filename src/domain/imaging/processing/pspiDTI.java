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
import domain.mathUtils.numericalMethods.statistics.LimitedStatisticalMoment;
import java.awt.Cursor;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class pspiDTI {
    private DrawableNiftiVolume ictalFA, baselineFA, ictalTR, baselineTR, binaryMask;
    private String workingDirectory;
    private String patientInitials;
    private double alpha;
    private javax.swing.JTextPane console;
    
    public static final boolean LEFT_TAIL=true;
    public static final boolean RIGHT_TAIL=false;
    public static final boolean FA=true;
    public static final boolean TR=false;
    
    public pspiDTI(DrawableNiftiVolume ictalFA, DrawableNiftiVolume baselineFA,
                   DrawableNiftiVolume ictalTR, DrawableNiftiVolume baselineTR,
                   DrawableNiftiVolume binaryMask, String workingDirectory,
                   String patientInitials,double alpha,javax.swing.JTextPane console){
        this.alpha=alpha;
        this.patientInitials=patientInitials;
        this.binaryMask=binaryMask;
        this.workingDirectory=workingDirectory;
        this.baselineFA=baselineFA;
        this.ictalFA=ictalFA;
        this.baselineTR=baselineTR;
        this.ictalTR=ictalTR;
        this.console=console;
    }
    
    /**
     * Performs the t_test and returns the p_values
     * @param flag left-tailed or right-tailed
     * @param FA_TR flag to indicate FA or TR
     * @return  Raw p_values 
     */
    public FourDimensionalArray oneTailedT_Test(boolean flag,boolean FA_TR){
        int[] dims ={0};
        Kernel w = new Kernel(1,3);
        FourDimensionalArray result;
        UnpairedtTest operation = new UnpairedtTest();
        NiftiNonLinearSpatialFilter spatialFilter = new NiftiNonLinearSpatialFilter(operation);
        if(FA_TR){
            operation.setTail(UnpairedtTest.LEFT_TAIL);
            if(binaryMask!=null){
                result= spatialFilter.doubleFilter(ictalFA.data, baselineFA.data, w, binaryMask.data, dims);
            }else{
                result= spatialFilter.doubleFilter(ictalFA.data, baselineFA.data, w, dims);
            }
        }else{
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
    public FourDimensionalArray cleanPvalues(FourDimensionalArray pVals,boolean FA_TR){
        int x=pVals.sizeX();
        int y=pVals.sizeY();
        int z=pVals.sizeZ();
        double temp;
        FourDimensionalArray result= new FourDimensionalArray(x,y,z,1);
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                for(int k=0;k<z;k++){
                    temp=pVals.get(i,j,k,0);
                    if(temp>alpha){//If the p value is greater than alpha
                        result.set(i,j,k,0,Double.NaN);
                    }else{
                        result.set(i,j,k,0,temp);
                    }
                }
            }
        }
        try{
            NiftiVolume resultingVol=new NiftiVolume();
            if(FA_TR){
                resultingVol.header=ictalFA.header;
                resultingVol.data=result;
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_Pvals.nii.gz");
            }else{
                resultingVol.header=ictalTR.header;
                resultingVol.data=result;
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_Pvals.nii.gz");
            }
        }catch(Exception e){}
        return result;
    }
    
    /**
     * Performs the subtraction only in the values inside the mask
     * @param mask The mask in which to perform the subtraction (NaN flag)
     * @param FA_TR flag
     * @return The raw subtracted result, without any cleaning.
     */
    public void subtract(FourDimensionalArray mask,boolean FA_TR){
        int x=mask.sizeX();
        int y=mask.sizeY();
        int z=mask.sizeZ();
        
        FourDimensionalArray result= new FourDimensionalArray(x,y,z,1);
        double temp;
        LimitedStatisticalMoment moments = new LimitedStatisticalMoment();
        double threshold;
        NiftiVolume resultingVol = new NiftiVolume();
        //Calculate subtraction and accumulate values for mean and std calculations
        if(FA_TR){//FA
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    for(int k=0;k<z;k++){
                        if(Double.isNaN(mask.get(i,j,k,0))){//If mask is NaN
                            result.set(i,j,k,0,Double.NaN);
                        }else{
                            temp=ictalFA.data.get(i,j,k,0)-baselineFA.data.get(i,j,k,0);
                            moments.accumulate(temp);
                            result.set(i,j,k,0,temp);
                        }
                    }
                }
            }
            resultingVol.header=ictalFA.header;
            resultingVol.data=result;
            String str=console.getText()+"Writing FA decrease...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_decrease.nii.gz");
            }catch(Exception e){}
            //One standard deviation
            double mean=moments.mean();
            double std=moments.std();
            threshold=mean-std;
            resultingVol.data=cleanSubtract(result,threshold,FA,1);
            str=console.getText()+"Writing FA: 1std...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_1std_decrease.nii.gz");
            }catch(Exception e){}
            //Two standard deviations
            threshold=mean-2*std;
            resultingVol.data=cleanSubtract(result,threshold,FA,1);
            str=console.getText()+"Writing FA: 2std...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_2std_decrease.nii.gz");
            }catch(Exception e){}
            //Three standard deviations
            threshold=mean-3*std;
            resultingVol.data=cleanSubtract(result,threshold,FA,1);
            str=console.getText()+"Writing FA: 3std...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_3std_decrease.nii.gz");
            }catch(Exception e){}
            // 0.05 FA threshold
            resultingVol.data=cleanSubtract(result,-1,FA,0.05);
            str=console.getText()+"Writing FA: 0.05...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_0.05t_decrease.nii.gz");
            }catch(Exception e){}
            // 0.1 FA threshold
            resultingVol.data=cleanSubtract(result,-1,FA,0.1);
            str=console.getText()+"Writing FA: 0.1...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_0.10t_decrease.nii.gz");
            }catch(Exception e){}
            // 0.15 FA threshold
            resultingVol.data=cleanSubtract(result,-1,FA,0.15);
            str=console.getText()+"Writing FA: 0.15...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_0.15t_decrease.nii.gz");
            }catch(Exception e){}
            // 0.20 FA threshold
            resultingVol.data=cleanSubtract(result,-1,FA,0.2);
            str=console.getText()+"Writing FA: 0.2...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_FA_0.20t_decrease.nii.gz");
            }catch(Exception e){}
            
        }else{//TR
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    for(int k=0;k<z;k++){
                        if(Double.isNaN(mask.get(i,j,k,0))){//If mask is NaN
                            result.set(i,j,k,0,Double.NaN);
                        }else{
                            temp=ictalTR.data.get(i,j,k,0)-baselineTR.data.get(i,j,k,0);
                            moments.accumulate(temp);
                            result.set(i,j,k,0,temp);
                        }
                    }
                }
            }
            resultingVol.header=ictalTR.header;
            resultingVol.data=result;
            String str=console.getText()+"Writing Trace increase...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_increase.nii.gz");
            }catch(Exception e){}
            //One standard deviation
            double mean=moments.mean();
            double std=moments.std();
            threshold=mean+std;
            resultingVol.data=cleanSubtract(result,threshold,TR,1);
            str=console.getText()+"Writing Trace: 1std...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_1std_increase.nii.gz");
            }catch(Exception e){}
            //Two standard deviations
            threshold=mean+2*std;
            resultingVol.data=cleanSubtract(result,threshold,TR,1);
            str=console.getText()+"Writing Trace: 2std...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_2std_increase.nii.gz");
            }catch(Exception e){}
            //Three standard deviations
            threshold=mean+3*std;
            resultingVol.data=cleanSubtract(result,threshold,TR,1);
            str=console.getText()+"Writing Trace: 3std...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_3std_increase.nii.gz");
            }catch(Exception e){}
            
            // 0.05 TR threshold
            resultingVol.data=cleanSubtract(result,1,TR,0.05);
            str=console.getText()+"Writing Trace: 0.05...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_0.05t_increase.nii.gz");
            }catch(Exception e){}
            // 0.1 TR threshold
            resultingVol.data=cleanSubtract(result,1,TR,0.1);
            str=console.getText()+"Writing Trace: 0.1...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_0.10t_increase.nii.gz");
            }catch(Exception e){}
            // 0.15 TR threshold
            resultingVol.data=cleanSubtract(result,1,TR,0.15);
            str=console.getText()+"Writing Trace: 0.15...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_0.15t_increase.nii.gz");
            }catch(Exception e){}
            // 0.20 TR threshold
            resultingVol.data=cleanSubtract(result,1,TR,0.2);
            str=console.getText()+"Writing Trace: 0.2...\n";
            console.setText(str);
            try{
                resultingVol.write(workingDirectory+patientInitials+"_pspiDTI_output\\"+patientInitials+Double.toString(alpha)+"a_TR_0.20t_increase.nii.gz");
            }catch(Exception e){}
            
            
        }
    }
    
   public FourDimensionalArray cleanSubtract(FourDimensionalArray array, double threshold,boolean FA_TR,double change){
        int x=array.sizeX();
        int y=array.sizeY();
        int z=array.sizeZ();
        //Variable to hold the result
        double temp;
        FourDimensionalArray result= new FourDimensionalArray(x,y,z,1);
        if(FA_TR){
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    for(int k=0;k<z;k++){
                            temp=array.get(i,j,k,0);
                            if(temp<=threshold|Math.abs(temp)>=change){
                                result.set(i,j,k,0,temp);
                            }else{
                                result.set(i,j,k,0,Double.NaN);
                            }
                    }
                }
            }
        }
        else{
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    for(int k=0;k<z;k++){
                            temp=array.get(i,j,k,0);
                            if(temp>=threshold|Math.abs(temp)>=change){
                                result.set(i,j,k,0,temp);
                            }else{
                                result.set(i,j,k,0,Double.NaN);
                            }
                    }
                }
            }
        }
        return result;
    }
    
   public void calculate(){
        FourDimensionalArray temp1;
        FourDimensionalArray temp2;
        
        
        append("Performing FA t-test...\n");
        temp1=oneTailedT_Test(LEFT_TAIL,pspiDTI.FA);
        
        append("Cleaning FA p-values...\n");
        console.setText(str);
        temp2=cleanPvalues(temp1,pspiDTI.FA);
        
        str=console.getText()+"Performing FA subtraction...\n";
        console.setText(str);
        subtract(temp2,pspiDTI.FA);
        
        str=console.getText()+"Performing Trace t-test...\n";
        console.setText(str);
        temp1=oneTailedT_Test(RIGHT_TAIL,pspiDTI.TR);
        
        str=console.getText()+"Cleaning Trace p-values...\n";
        console.setText(str);
        temp2=cleanPvalues(temp1,pspiDTI.TR);
        
        str=console.getText()+"Performing Trace subtraction...\n";
        console.setText(str);
        subtract(temp2,pspiDTI.TR);
        
        str=console.getText()+"Done!!...\n";
        console.setText(str);
        
    }
   
   private void append(String str){
       str=console.getText()+str;
       console.setText(str);
       console.setCaretPosition(str.length());
   }
}
