/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.mathUtils.arrayTools;

/**
 * <p>
 * This class contains methods relevant to mathematiical array processing.
 * The class is abstract. Calling a method from a client object does not require an instance variable of the class.
 * </p>
 * @author Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology
 */
public abstract class ArrayOperations {
    /**
     * Find the maximum number in a 1D array
     * @param x 1D array of doubles
     * @return maximum value in the array
     * @author Leopoldo Cendejas-Zaragoza
     */
    public static double findMaximum(double x[]){
        if (x.length!=0){
            double maximum=x[0];
            for(double number: x){
                if (number>maximum)
                    maximum=number;
            }
            return maximum;
        }
        else{
            System.out.println("Input array is empty");
            return Double.NaN;
        }
    }
}
