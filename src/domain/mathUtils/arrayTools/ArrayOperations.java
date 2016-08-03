/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.arrayTools;

/**
 * <p>
 * This class contains methods relevant to mathematical array processing.
 * The class is abstract. Calling a method from a client object does not require an instance variable of the class.
 * </p>
 * @author Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology
 */
public abstract class ArrayOperations {
    /**
     * Find the maximum number in a 1D array
     * Supports arrays having  NaN values
     * @param x 1D array of doubles
     * @return maximum value in the array.
     * <p>
     * NaN if the input array is empty.
     * @author Leopoldo Cendejas-Zaragoza
     */
    public static double findMaximum(double x[]){
        if (x.length!=0){
            double maximum=x[0];
            for(double number: x){
                //test if the maximum number is NaN if it is NaN use the minimum value for a double as defined by IEEE for comparison 
                double temp=Double.isNaN(maximum)? -Double.MAX_VALUE:maximum;
                if(maximum==Double.POSITIVE_INFINITY)
                    return maximum;
                if (number>temp)
                    maximum=number;
            }
            return maximum;
        }
        else{
            System.out.println("Input array is empty");
            return Double.NaN;
        }
    }
   
    /**
     * Find the maximum number in a 2D array
     * Supports arrays having  NaN values
     * @param x 2D array of doubles
     * @return maximum value in the array.
     * <p>
     * NaN if the input array is empty.
     * @author Leopoldo Cendejas-Zaragoza
     */
    public static double findMaximum(double x[][]){
            if (x.length!=0){
            double maximum=x[0][0];
            for(double[] column: x){
                for(double number: column){
                    //test if the maximum number is NaN if it is NaN use the minimum value for a double as defined by IEEE for comparison 
                    double temp=Double.isNaN(maximum)? -Double.MAX_VALUE:maximum;
                    if(maximum==Double.POSITIVE_INFINITY)
                        return maximum;
                    if (number>temp)
                        maximum=number;
                }
            }
            return maximum;
        }
        else{
            System.out.println("Input array is empty");
            return Double.NaN;
        }
    }
    
    /**
     * Find the maximum number in a 3D array
     * Supports arrays having  NaN values
     * @param x 3D array of doubles
     * @return maximum value in the array.
     * <p>
     * NaN if the input array is empty.
     * @author Leopoldo Cendejas-Zaragoza
     */
    public static double findMaximum(double x[][][]){
            if (x.length!=0){
            double maximum=x[0][0][0];
            for(double[][] slice: x){
                for(double column[]: slice){
                    for(double number:column){
                        //test if the maximum number is NaN if it is NaN use the minimum value for a double as defined by IEEE for comparison 
                        double temp=Double.isNaN(maximum)? -Double.MAX_VALUE:maximum;
                        if(maximum==Double.POSITIVE_INFINITY)
                            return maximum;
                        if (number>temp)
                            maximum=number;
                    }
                }
            }
            return maximum;
        }
        else{
            System.out.println("Input array is empty");
            return Double.NaN;
        }
    }
  
    /**
     * Find the minimum number in a 1D array
     * Supports arrays having  NaN values
     * @param x 1D array of doubles
     * @return minimum value in the array.
     * <p>
     * NaN if the input array is empty.
     * @author Leopoldo Cendejas-Zaragoza
     */
    public static double findMinimum(double x[]){
        if (x.length!=0){
            double minimum=x[0];
            for(double number: x){
                //test if the maximum number is NaN if it is NaN use the maximum value for a double as defined by IEEE for comparison 
                double temp=Double.isNaN(minimum)? Double.MAX_VALUE:minimum;
                if(minimum==Double.NEGATIVE_INFINITY)
                    return minimum;
                if (number<temp)
                    minimum=number;
            }
            return minimum;
        }
        else{
            System.out.println("Input array is empty");
            return Double.NaN;
        }
    }
    
    /**
     * Find the minimum number in a 2D array
     * Supports arrays having  NaN values
     * @param x 2D array of doubles
     * @return minimum value in the array.
     * <p>
     * NaN if the input array is empty.
     * @author Leopoldo Cendejas-Zaragoza
     */
    public static double findMinimum(double x[][]){
            if (x.length!=0){
            double minimum=x[0][0];
            for(double[] column: x){
                for(double number: column){
                    //test if the maximum number is NaN if it is NaN use the minimum value for a double as defined by IEEE for comparison 
                    double temp=Double.isNaN(minimum)? Double.MAX_VALUE:minimum;
                    if(minimum==Double.NEGATIVE_INFINITY)
                        return minimum;
                    if (number<temp)
                        minimum=number;
                }
            }
            return minimum;
        }
        else{
            System.out.println("Input array is empty");
            return Double.NaN;
        }
    }
    
    /**
     * Find the minimum number in a 3D array
     * Supports arrays having  NaN values
     * @param x 3D array of doubles
     * @return minimum value in the array.
     * <p>
     * NaN if the input array is empty.
     * @author Leopoldo Cendejas-Zaragoza
     */
    public static double findMinimum(double x[][][]){
            if (x.length!=0){
            double minimum=x[0][0][0];
            for(double[][] slice: x){
                for(double column[]: slice){
                    for(double number:column){
                    //test if the maximum number is NaN if it is NaN use the minimum value for a double as defined by IEEE for comparison 
                        double temp=Double.isNaN(minimum)? Double.MAX_VALUE:minimum;
                        if(minimum==Double.NEGATIVE_INFINITY)
                            return minimum;
                        if (number<temp)
                            minimum=number;
                    }
                }
            }
            return minimum;
        }
        else{
            System.out.println("Input array is empty");
            return Double.NaN;
        }
    } 
    
}
