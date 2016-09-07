/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class LinearSpatialFilter implements SpatialFilter {
    public static final boolean CONVOLUTION = true;
    public static final boolean CORRELATION = false;
    private boolean flag;
    
    public LinearSpatialFilter(boolean flag){
        this.flag=flag;
    }
    
    @Override
    public double[][] filter(double[][] array,Kernel w){
        Kernel x=new Kernel(w.getElements());
        if(flag){//Convolution
            x.rotateKernel180();
        }
        double[][] paddedArray= x.padArray(array);
        double[][] resultingArray=new double[array.length][array[0].length];
        int a=x.getA();
        int b=x.getB();
        if(x.getParity()){//ODD Kernel
            for(int i=a;i<paddedArray.length-a;i++){
                for(int j=b;j<paddedArray[0].length-b;j++){
                    for(int s=-1*a;s<=a;s++){
                        for(int t=-1*b;t<=b;t++){
                            resultingArray[i-a][j-b]+=paddedArray[i+s][j+t]*x.getElement(s+a,t+b);    
                        }
                    }     
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
                            resultingArray[i-a][j-b]+=paddedArray[i+s][j+t]*x.getElement(s+m,t+n);    
                        }
                    }     
                }
            }
        }
        return resultingArray;
    }
     @Override
    public double[][] filter(double[][] array, Kernel w, double[][] mask) {
        Kernel x=new Kernel(w.getElements());
        if(flag){//Convolution
            x.rotateKernel180();
        }
        double[][] paddedArray= x.padArray(array);
        double[][] resultingArray=new double[array.length][array[0].length];
        int a=x.getA();
        int b=x.getB();
        if(x.getParity()){//ODD Kernel
            for(int i=a;i<paddedArray.length-a;i++){
                for(int j=b;j<paddedArray[0].length-b;j++){
                    if(mask[i-a][j-b]!=0){
                        for(int s=-1*a;s<=a;s++){
                            for(int t=-1*b;t<=b;t++){
                                resultingArray[i-a][j-b]+=paddedArray[i+s][j+t]*x.getElement(s+a,t+b);    
                            }
                        } 
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
                                resultingArray[i-a][j-b]+=paddedArray[i+s][j+t]*x.getElement(s+m,t+n);    
                            }
                        }
                    }else{
                        resultingArray[i-a][j-b]=Double.NaN;
                    }
                }
            }
        }
        return resultingArray;
    }
    
    public void changeFilterOperation(boolean flag){
        this.flag=flag;
    }

}
