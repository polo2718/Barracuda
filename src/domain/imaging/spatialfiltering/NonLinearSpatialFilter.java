/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering;

import domain.imaging.spatialfiltering.operations.*;
/**
 * Class that performs a non-linear spatial filtering by correlating the kernel 
 * with an array and performing the operation defined when instantiating the object
 * @author Diego Garibay-Pulido 2016
 */
public class NonLinearSpatialFilter implements SpatialFilter, DoubleSpatialFilter{
    private final NonLinearFilterOperation operation;
    /**
     * The constructor defines the type of operation to be performed on the neighborhood.
     * @param operation See NonLinearFilterOperation for details
     */
    public NonLinearSpatialFilter(NonLinearFilterOperation operation){
        this.operation=operation;
    }
    
    @Override
    public double[][] filter(double [][] array,Kernel w){
        Kernel x=new Kernel(w.getElements()); //Create temporary copy of kernel
        double [][] paddedArray=x.padNaNArray(array);//Pad array with NaN
        double[][] resultingArray=new double[array.length][array[0].length];
        double[][] tempArray=new double[x.getM()][x.getN()];
        int a=x.getA();
        int b=x.getB();
        if(x.getParity()){//ODD Kernel
            for(int i=a;i<paddedArray.length-a;i++){
                for(int j=b;j<paddedArray[0].length-b;j++){
                    for(int s=-1*a;s<=a;s++){
                        for(int t=-1*b;t<=b;t++){
                            tempArray[s+a][t+b]=paddedArray[i+s][j+t];   
                        }
                    }
                    resultingArray[i-a][j-b]=operation.operate(tempArray);
                }
            }
        }
        else{ //Even Kernel: 
            int m=x.getM()-1;
            int n=x.getN()-1;
            for(int i=a;i<paddedArray.length-a;i++){
                for(int j=b;j<paddedArray[0].length-b;j++){
                    for(int s=-1*m;s<=0;s++){
                        for(int t=-1*n;t<=0;t++){
                            tempArray[s+m][t+n]=paddedArray[i+s][j+t];   
                        }
                    }
                    resultingArray[i-a][j-b]=operation.operate(tempArray);
                }
            }
        }
        return resultingArray;
    }
    @Override
    public double[][] filter(double [][] array,Kernel w,double [][] mask){
        Kernel x=new Kernel(w.getElements()); //Create temporary copy of kernel
        double [][] paddedArray=x.padNaNArray(array);//Pad array with NaN
        double[][] resultingArray=new double[array.length][array[0].length];
        double[][] tempArray=new double[x.getM()][x.getN()];
        int a=x.getA();
        int b=x.getB();
        if(x.getParity()){//ODD Kernel
            for(int i=a;i<paddedArray.length-a;i++){
                for(int j=b;j<paddedArray[0].length-b;j++){
                    if(mask[i-a][j-b]!=0){
                        for(int s=-1*a;s<=a;s++){
                            for(int t=-1*b;t<=b;t++){
                                tempArray[s+a][t+b]=paddedArray[i+s][j+t];    
                            }
                        }
                        resultingArray[i-a][j-b]=operation.operate(tempArray);
                    }else{
                        resultingArray[i-a][j-b]=Double.NaN;
                    }
                }
            }
        }
        else{ //Even Kernel: 
            int m=x.getM()-1;
            int n=x.getN()-1;
            for(int i=a;i<paddedArray.length-a;i++){
                for(int j=b;j<paddedArray[0].length-b;j++){
                    if(mask[i-a][j-b]!=0){
                        for(int s=-1*m;s<=0;s++){
                            for(int t=-1*n;t<=0;t++){
                                tempArray[s+m][t+n]=paddedArray[i+s][j+t];  
                            }
                        }
                        resultingArray[i-a][j-b]=operation.operate(tempArray);
                    }else{
                        resultingArray[i-a][j-b]=Double.NaN;
                    }
                }
            }
        }
        return resultingArray;
    }
    @Override
    public double[][] doubleFilter(double[][] array1, double[][] array2, Kernel w) {
        double[][] resultingArray;
        if(array1.length==array2.length & array1[0].length==array2[0].length){
            double [][] paddedArray1=w.padNaNArray(array1);
            double [][] paddedArray2=w.padNaNArray(array2);//Pad both arrays
            double[][] tempArray1=new double[w.getM()][w.getN()];
            double[][] tempArray2=new double[w.getM()][w.getN()];//Get Kernel dimensions
            resultingArray=new double[array1.length][array1[0].length];
            
            int a=w.getA();
            int b=w.getB();
            if(w.getParity()){//ODD Kernel
                for(int i=a;i<paddedArray1.length-a;i++){
                    for(int j=b;j<paddedArray1[0].length-b;j++){
                        for(int s=-1*a;s<=a;s++){
                            for(int t=-1*b;t<=b;t++){
                                tempArray1[s+a][t+b]=paddedArray1[i+s][j+t];
                                tempArray2[s+a][t+b]=paddedArray2[i+s][j+t];
                            }
                        }
                        resultingArray[i-a][j-b]=operation.operate(tempArray1,tempArray2);
                    }
                }
            }
            else{ //Even Kernel: 
                int m=w.getM()-1;
                int n=w.getN()-1;
                for(int i=a;i<paddedArray1.length-a;i++){
                    for(int j=b;j<paddedArray1[0].length-b;j++){
                        for(int s=-1*m;s<=0;s++){
                            for(int t=-1*n;t<=0;t++){
                                tempArray1[s+m][t+n]=paddedArray1[i+s][j+t];
                                tempArray2[s+m][t+n]=paddedArray2[i+s][j+t];
                            }
                        }
                        resultingArray[i-a][j-b]=operation.operate(tempArray1,tempArray2);
                    }
                }
            }
        }else{
            resultingArray=null;
            
        }
        return resultingArray;
    }
    @Override
    public double[][] doubleFilter(double[][] array1, double[][] array2, Kernel w,double [][] mask)  {
        double[][] resultingArray;
        if(array1.length==array2.length & array1[0].length==array2[0].length){
            double [][] paddedArray1=w.padNaNArray(array1);
            double [][] paddedArray2=w.padNaNArray(array2);//Pad both arrays
            double[][] tempArray1=new double[w.getM()][w.getN()];
            double[][] tempArray2=new double[w.getM()][w.getN()];//Get Kernel dimensions
            resultingArray=new double[array1.length][array1[0].length];
            
            int a=w.getA();
            int b=w.getB();
            if(w.getParity()){//ODD Kernel
                for(int i=a;i<paddedArray1.length-a;i++){
                    for(int j=b;j<paddedArray1[0].length-b;j++){
                        if(mask[i-a][j-b]!=0){
                            for(int s=-1*a;s<=a;s++){
                                for(int t=-1*b;t<=b;t++){
                                    tempArray1[s+a][t+b]=paddedArray1[i+s][j+t];
                                    tempArray2[s+a][t+b]=paddedArray2[i+s][j+t];
                                }
                            }
                            resultingArray[i-a][j-b]=operation.operate(tempArray1,tempArray2);
                        }else{
                            resultingArray[i-a][j-b]=Double.NaN;
                        }
                        
                    }
                }
            }
            else{ //Even Kernel: 
                int m=w.getM()-1;
                int n=w.getN()-1;
                for(int i=a;i<paddedArray1.length-a;i++){
                    for(int j=b;j<paddedArray1[0].length-b;j++){
                        if(mask[i-a][j-b]!=0){
                            for(int s=-1*m;s<=0;s++){
                                for(int t=-1*n;t<=0;t++){
                                    tempArray1[s+m][t+n]=paddedArray1[i+s][j+t];
                                    tempArray2[s+m][t+n]=paddedArray2[i+s][j+t];
                                }
                            }
                            resultingArray[i-a][j-b]=operation.operate(tempArray1,tempArray2);
                        }else{
                            resultingArray[i-a][j-b]=Double.NaN;
                        }
                    }
                }
            }
            
        }else{
            resultingArray=null;            
        }
        return resultingArray;
    }
}
