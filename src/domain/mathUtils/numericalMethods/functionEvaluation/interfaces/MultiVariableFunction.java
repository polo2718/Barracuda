/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.interfaces;

/**
 *Interface definition for multivariable functions
 * A multivariable function is a scalar function
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see ScalarFunction
 */
public interface MultiVariableFunction extends ScalarFunction{
    /**
     * The method should return the value of the function evaluated at different values
     * @param variables - 1D array storing the values of the independent variables 
     * @return value - Value of the dependent variable 
     * @throws java.lang.IllegalArgumentException if an illegal operation is performed
     */
    @Override
    public double value(double[] variables) throws IllegalArgumentException;
        
}
