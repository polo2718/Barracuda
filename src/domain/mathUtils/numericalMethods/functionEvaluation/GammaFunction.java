/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;
import domain.mathUtils.numericalMethods.interfaces.OneVariableFunction;


/**
 * Provides useful methods regarding the gamma function or (Euler's) Integral
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see OneVariableFunction
 */
public final class GammaFunction implements OneVariableFunction{
    
//constant declaration
    private final static double sqrt2Pi=Math.sqrt(2*Math.PI);
    private final static double[] coefficients=
    {
        1.000000000190015,
        76.1800917297146,
        -86.50532032941677,
        24.01409824083091,
        -1.231739572450155,
        .120865097866179e-2,
        -.395239384953e-5
    }; //coefficients for the Lanczos formula
    
    /**
     * Constructor
     */
    public GammaFunction(){
    }
    
    @Override
    public double value(double x){
        //test if the value is a negative integer
        if(isNegativeInteger(x)){
            return Double.NaN;
        }
        //if x is between 0 and 1 (inclusive) then use recursion formula gamma(x+1)=x*gamma(x)
        //or gamma(x)=gamma(x+1)/x
        if(x<=1 && x>0){
            return value((x+1/x));
        }
       
        //if x is negative use reflection formula
        if(x<0){
        
        }
        //If x is positive use Lanczo's series approximation
        if (x>0){
        
        }
        else return Double.NaN;
        
    }
   
    /**
     * This function tests if the value x is a Negative Integer
     * @param x - value to test
     * @return true if the tested value is a Negative Integer
     */
    private boolean isNegativeInteger(double x){
        if(Double.isNaN(x)||!Double.isFinite(x))
            return false;
        //a number is an integer if x%1 is equal to zero
        return x%1==0 && x<0; 
    }
    
}
