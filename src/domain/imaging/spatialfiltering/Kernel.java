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
    private int m,n,a,b;
    private boolean parity;
    public static final boolean ODD_KERNEL=true;
    public static final boolean EVEN_KERNEL=false;
    
    public Kernel(double [][] elements) throws IllegalArgumentException{
        m=elements.length;
        n=elements[0].length;
        if(n==m){
            this.elements=elements;

            if((elements.length%2)==1 & (elements[0].length%2)==1){
                a=(m-1)/2;
                b=(n-1)/2;
                parity=ODD_KERNEL;
            }
            else{
                a=(m/2);
                b=(n/2);
                parity=EVEN_KERNEL;
            }
        } else{
            throw new IllegalArgumentException("Kernel must have equal dimensions");
        }
            
    }
    public Kernel(double val,int m) throws IllegalArgumentException{
        elements =new double[m][m];
        for(int i=0;i<m;i++){
            for(int j=0;j<m;j++){
                elements[i][j]=val;
            }
        }
        this.n=m;
        this.m=m;
        if((n%2)==1&(m%2)==1){
            a=(m-1)/2;
            b=(n-1)/2;
            parity=ODD_KERNEL;
        }
        else{
            a=m/2;
            b=n/2;
            parity=EVEN_KERNEL;
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
    
    public double[][] padNaNArray(double [][] array){
        double[][] resultingArray;
        if(elements!=null){
            int m=array.length;
            int n=array[0].length;
            resultingArray= new double[m+2*a][n+2*b];
            for(int i=0;i<resultingArray.length;i++){
                for(int j=0;j<resultingArray[0].length;j++){
                    if((i>=a & j>=b) & (i<(m+a) & j<(n+b)))
                        resultingArray[i][j]=array[i-a][j-b];
                    else
                        resultingArray[i][j]=Double.NaN;
                }
            }
        }else{
            resultingArray=null;
        }
        return resultingArray;
    }
    
    public double[][] getElements(){
        return elements;
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
        return m;
    }
    public int getN(){
        return n;
    }

    public int getA(){
        return a;
    }
    public int getB(){
        return b;
    }
    
    public double checkConsistency(){
        double sum=0;
        for (double[] element : elements) {
            for (int j = 0; j<elements[0].length; j++) {
                sum += element[j];
            }
        }
        if(sum!=1) //In order to keep intensity, kernel element sum must be equal to 1
            System.out.println("Kernel sum is not equal to 1. \n "
                    + "Global intensity changes are expected if"
                    + " the kernel is used on a linear spatial filter");
        return sum;
    }
    
    public boolean getParity(){
        return parity;
    }
    
    public void rotateKernel90() {
        int n=elements.length;
        int m=elements[0].length;
        double[][] ret = new double[n][m];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                ret[i][j] = elements[n-j-1][i];
            }
        }

        elements=ret;
    }
    
    public void rotateKernel180(){
        rotateKernel90();
        rotateKernel90();
    }

}