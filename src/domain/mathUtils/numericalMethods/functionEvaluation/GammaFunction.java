/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;
import domain.mathUtils.numericalMethods.interfaces.OneVariableFunction;


/**
 * Provides methods regarding the gamma function or (Euler's) Integral
 * @see OneVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public final class GammaFunction implements OneVariableFunction{
    
//constant declaration
    private final static double sqrt2Pi=Math.sqrt(2*Math.PI);
    private final static double[] coefficients=
    {
        1.000000000190015,
        76.18009172947146,
        -86.50532032941677,
        24.01409824083091,
        -1.231739572450155,
        0.1208650973866179e-2,
        -0.5395239384953e-5
    }; //coefficients for the first Lanczos formula
    
    /**
     * Constructor
     */
    public GammaFunction(){
    }
    
    /**
     * Computes the value of the gamma function with 10e-6 precision
     * @param x
     * @return Value of the Gamma function evaluated at x
     */
    @Override
    public double value(double x){
        //test if the value is a positive integer if it is then return the value of the factorial function
        if(isPositiveInteger(x)){
            return (double)factorial((long)x-1);
        }
        //test if the value is a negative integer
        else if(isNegativeInteger(x)){
            return Double.NaN;
        }
        //if x is between 0 (exclusive) and 1 (inclusive) then use recursion formula gamma(x+1)=x*gamma(x)
        //or gamma(x)=gamma(x+1)/x
        else if(x<=1 && x>0){
            return value(x+1)/x;
        }
        //if x is negative use reflection formula
        else if(x<0){
           return Math.PI/(value(1-x)*Math.sin(Math.PI*x));
        }
        //If x is positive use first Lanczo's series approximation
        else if (x>1){
            return Math.exp(leadingTerm(x))*series(x)*sqrt2Pi/x;
        }
        else return Double.NaN;  
    }
    
    /**
     * Returns the factorial of a given number
     * The method uses a recursion formula to compute the factorial
     * @param x long 
     * @return long containing the value of the factorial of x 
     */
    public long factorial (long x){
        return x<2 ? 1 :x*factorial(x-1);
    }
    
    /**
     * Returns the value for the natural logarithm of the gamma function evaluated at x
     * @param x
     * @return ln(gamma(x))
     */
    public double logValue(double x){
       return Math.log(value(x));
    }
    
    /**
     * This function tests if the value x is an Integer
     * @param x - value to test
     * @return true if the tested value is a Negative Integer
     */
    private boolean isInteger(double x){
        if(Double.isNaN(x)||!Double.isFinite(x))
            return false;
        //a number is an integer if x%1 is equal to zero
        return x%1==0; 
    }
    
    /**
     * This function tests if the value x is a Negative Integer
     * @param x - value to test
     * @return true if the tested value is a Negative Integer
     */
    private boolean isNegativeInteger(double x){
        return isInteger(x) && x<0; 
    }
    
    /**
     * This function tests if the value x is a Positive Integer
     * @param x - value to test
     * @return true if the tested value is a Positive Integer
     */
    private boolean isPositiveInteger(double x){
        return isInteger(x) && x>0; 
    }
    
    /**
     * This function returns the series term of the Lanczo's Formula
     * @param x
     * @return result of the series term on the Lanczo's formula
     */
    private double series(double x){
        double result=coefficients[0];
        double temp;
        for(int i=1; i<=6; i++){
            temp=coefficients[i]/(x+i);
            result+=temp;
        }
        return result;
    }
    
    /**
     * This function return the leading term of the Lanczo's Formula
     * @param x
     * @return 
     */
    private double leadingTerm(double x){
        double temp=x+5.5;
        return Math.log(temp)*(x+0.5)-temp;
    }
    

    
}
