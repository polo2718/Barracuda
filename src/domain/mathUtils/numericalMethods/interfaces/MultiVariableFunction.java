/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.interfaces;

/**
 *Interface definition for multivariable functions
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public interface MultiVariableFunction {
    /**
     * The method should return the value of the function evaluated at different values
     * @param variables - 1D array storing the values of the independent variables 
     * @return value - Value of the dependent variable 
     */
    public double value(double[] variables) throws Exception;
    
}
