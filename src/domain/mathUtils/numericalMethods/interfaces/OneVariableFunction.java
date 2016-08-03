/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.interfaces;

/**
 * Interface definition for one variable function 
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public interface OneVariableFunction {
    /**
     * The method should return the value of the function for the specified variable
     * @param x - Value of the independent variable 
     * @return value - Value of the dependent variable 
     */
    public double value(double x);
    
}
