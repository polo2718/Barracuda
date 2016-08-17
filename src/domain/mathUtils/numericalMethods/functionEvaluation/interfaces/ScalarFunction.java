/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.interfaces;

/**
 * This interface defines the most general case of a scalar function
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public interface ScalarFunction {
    /**
    * The method should return the value of the function for the specified variables
    * The most general case of a scalar function is a Multivariable function
    * @param variables 1D array containing the independent variables
    * @return value - Value of the dependent variable 
    */
    public double value(double [] variables) throws IllegalArgumentException;
}
