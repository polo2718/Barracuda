/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.interfaces;

/**
 * Interface definition for one variable function 
 * A function of one variable is an special case of a Multivariable Function
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see MultiVariableFunction
 */
public interface OneVariableFunction extends MultiVariableFunction {
    /**
     * The method should return the value of the function for the specified variable
     * @param x - Value of the independent variable 
     * @return value - Value of the dependent variable 
     * @throws IllegalArgumentException Exception caused by an incorrect input argument
     */
     public double value(double x) throws IllegalArgumentException;     

    @Override
    public default double value(double[] variables) throws IllegalArgumentException {
        if(variables.length!=1)
            throw new IllegalArgumentException("Wrong number of parameters supplied: One variable function should have only one parameter");
        double x=variables[1];
        return value(x);
    }
}
