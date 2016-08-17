/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;

import domain.mathUtils.numericalMethods.functionEvaluation.interfaces.MultiVariableFunction;

/**
 * Provides methods regarding the beta Function
 * @see GammaFunction
 * @see MultiVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class BetaFunction implements MultiVariableFunction {
    
    private static final GammaFunction gammaFunction= new GammaFunction(); //Gamma function instance
    
    /**
     * Constructor
     */
    public BetaFunction(){
    }
    
    /**
     * Returns the value of the beta function evaluated at x,y
     * @param variables 1D array containing two independent variables x and y
     * <p>variables[0]=double containing x;</p>
     * <p>variables[1]=double containing y;</p>
     * @return beta(x,y)
     * @throws IllegalArgumentException an exception is thrown when the input array containing the 2 independent variables has the wrong dimensionality
     */
    @Override
    public double value(double[] variables) throws IllegalArgumentException{
        if (variables.length==2){
            double x=variables[0];
            double y=variables[1];
            return Math.exp(gammaFunction.logValue(x)+gammaFunction.logValue(y)-
                    gammaFunction.logValue(x+y));
        }else 
            throw new IllegalArgumentException("Check input array. Wrong number of independent variables were provided. Beta "
                    + "function is defined for only two independent variables");
    }
    /**
     * Computes the value of the natural logarithm if the beta function evaluated at x, y
     * @param variables 1D array containing two independent variables x and y
     * <p>variables[1]=double containing x;</p>
     * <p>variables[2]=double containing y;</p>
     * @return ln(beta(x,y))
     * @throws IllegalArgumentException an exception is thrown when the input array containing the 2 independent variables has the wrong dimensionality
     */
    public double logValue(double [] variables) throws IllegalArgumentException{
        if(variables.length==2){
            double x=variables[0];
            double y=variables[1];
            return gammaFunction.logValue(x)+gammaFunction.logValue(y)-
                gammaFunction.logValue(x+y);
        }else
            throw new IllegalArgumentException("Check input array. Wrong number of independent variables were provided. Beta "
                    + "function is defined for only two independent variables");
    }
}
