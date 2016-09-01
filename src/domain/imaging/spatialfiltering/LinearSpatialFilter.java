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
        double[][] paddedArray= w.padArray(array);
        double[][] resultingArray=new double[array.length][array[0].length];
        
        int a=w.getA();
        int b=w.getB();
        if(flag){//Convolution
            for(int i=a;i<paddedArray.length-a;i++){
                for(int j=b;j<paddedArray[0].length-b;j++){
                    for(int s=-1*a;s<=a;s++){
                        for(int t=-1*b;t<=b;t++){
                            resultingArray[i-a][j-b]+=paddedArray[i-s][j-t]*w.getElement(s+a,t+b);
                        }
                    }
                    
                }
            }
        }
        else{//Correlation
             for(int i=a;i<paddedArray.length-a;i++){
                for(int j=b;j<paddedArray[0].length-b;j++){
                    for(int s=-1*a;s<=a;s++){
                        for(int t=-1*b;t<=b;t++){
                            resultingArray[i-a][j-b]+=paddedArray[i+s][j+t]*w.getElement(s+a,t+b);
                        }
                    }
                    
                }
            }
        }
        
        return resultingArray;
    }
    
    public void changeFilterOperation(boolean flag){
        this.flag=flag;
    }
    
    public static void main(String args[]){
        double[][] array1= {{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
                       {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},};
        double [][] h={{1,1,1},
                       {1,1,1},
                       {1,1,1}
        };
        Kernel w=new Kernel(h);
        System.out.println("Convolution \n");
        LinearSpatialFilter filter= new LinearSpatialFilter(CONVOLUTION);
        double[][] resultingArray=filter.filter(array1, w);
        for(int i=0;i<resultingArray.length;i++){
            for(int j=0;j<resultingArray[0].length;j++){
                System.out.printf(" %2.2f",resultingArray[i][j]/9.0);
            }
            System.out.println("");
        }
        System.out.println("Correlation \n");
        filter.changeFilterOperation(CORRELATION);
        resultingArray=filter.filter(array1, w);
        for(int i=0;i<resultingArray.length;i++){
            for(int j=0;j<resultingArray[0].length;j++){
                System.out.printf(" %2.2f",resultingArray[i][j]/9.0);
            }
            System.out.println("");
        }
    }
}
