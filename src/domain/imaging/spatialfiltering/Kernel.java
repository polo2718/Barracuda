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
public class Kernel {
    private double[][] elements; 
    private int a;
    private int b;
    
    public Kernel(double [][] elements) throws IllegalArgumentException{
        if((elements.length%2)==1 & (elements[0].length%2)==1){
            this.elements=elements;
            a=(elements.length-1)/2;
            b=(elements[0].length-1)/2;
        }
        else{
            this.elements=null;
            throw new IllegalArgumentException("Kernel is not yet defined for even dimensions");
        }
            
    }
    public Kernel(double val,int m,int n) throws IllegalArgumentException{
        if((n%2)==1&(m%2)==1){
            elements =new double[m][n];
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    elements[i][j]=val;
                }
            }
            a=(m-1)/2;
            b=(n-1)/2;
        }
        else{
            this.elements=null;
            throw new IllegalArgumentException("Kernel is not yet defined for even dimensions");
        }
    }
    
    
    /**
     * Method to zero pad an array depending on the kernel size
     * @param array A double 2D array
     * @return The zero-padded double 2D array
     */
    public double[][] padArray(double [][] array){
        double[][] resultingArray;
        if(elements!=null){
            int m=array.length;
            int n=array[0].length;
            resultingArray= new double[m+2*a][n+2*b];
            for(int i=a;i<(m+a);i++){
                for(int j=b;j<(n+b);j++){
                    resultingArray[i][j]=array[i-a][j-b];
                }
            }
        }else{
            resultingArray=null;
        }
        return resultingArray;
    }
    
    public double getElement(int x,int y){
        return elements[x][y];
    }
    
    public void setElement(double val, int m, int n) throws IllegalArgumentException{
        if(elements!=null)
            elements[m][n]=val;
        else
            throw new IllegalArgumentException("Define the elements first");
    }
    
    public int getM(){
        return 2*a+1;
    }
    public int getN(){
        return 2*b+1;
    }

    public int getA(){
        return a;
    }
    public int getB(){
        return b;
    }
    

}