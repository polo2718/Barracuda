/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;

import domain.mathUtils.numericalMethods.interfaces.OneVariableFunction;
import java.util.Arrays;

/**
 *
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class PolynomialFunction implements OneVariableFunction {
    /**
     * 1D Array containing the coefficients which define a Polynomial Function.
     * Examples:
     * The Polynomial 2x^2+4x+5 has the following array of coefficients {5,4,2}
     * The Polynomial x^3+2x+3 has the following array of coefficients {3,2,0,1}
     */
    private double[] coefficients;
    
    /**
     *Value specifying the degree of the polynomial. 
     */
    private int degree;
    
    /**
     * Constructor method
     * @param coefficients - 1D array containing the coefficients of the polynomial  
     */
    public PolynomialFunction(double coefficients[]){
        this.coefficients=coefficients;
        degree=coefficients.length-1;
    }
    /**
     * Compute the value of a polynomial function
     * The value is computed using Horner's method
     * @param x -  value of the independent variable
     * @return 
     */
    @Override
    public double value(double x){
        int n= coefficients.length; //number of elements in the coefficient array
        double result=coefficients[--n];
        for(int i=n-1;i>=0;i--)
            result=result*x+coefficients[i];
        return result;
    }
    /**
     * Returns the degree of the current polynomial
     * @return degree of the current polynomial 
     */
    public int degree(){
        return degree;
    }
    
    /**
     * Get the coefficients of a given polynomial
     * @return 1D array containing the polynomial coefficients
     */
    public double[] getCoefficients(){
        return this.coefficients;
    }
    
    /**
     * Compute the derivative of the polynomial function
     * @return PolynomialFunction corresponding to the derivative of the Polynomial
     */
    public PolynomialFunction derivative(){
        //if the polynomial has degree==0 then the derivative is equal to zero
        double [] deriv_coeff={0};
        if(degree==0){
            return new PolynomialFunction(deriv_coeff);
        }
        //The length of coefficients array should be reduced by one in the derivative. 
        //This means that the number of elements in the coeffient array of the derivate should be
        //equal to the degree of the actual polynomial.
        //The degree of the derivative will be then reduced by one
        int n=degree;
        deriv_coeff= new double[n]; 
        for(int i=0; i<n;i++){
            deriv_coeff[i]=coefficients[i+1]*(i+1);
        }
        return new PolynomialFunction(deriv_coeff);
    }
  /*
    **********************Override equals***************************************
    */  
    @Override
    public boolean equals(Object other){
        if (other == null) 
            return false;
        if (other == this) 
            return true;
        if (!(other instanceof PolynomialFunction))
            return false;
        if (this.degree!=((PolynomialFunction)other).degree)
            return false;

        double [] otherCoeff=((PolynomialFunction)other).coefficients;
        if (this.coefficients.length!=otherCoeff.length)
            return false;
        for(int i=0; i<this.coefficients.length; i++){
            if(this.coefficients[i]==otherCoeff[i]);
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Arrays.hashCode(this.coefficients);
        hash = 41 * hash + this.degree;
        return hash;
    }
}
