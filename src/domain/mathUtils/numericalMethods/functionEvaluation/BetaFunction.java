/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;

import domain.mathUtils.numericalMethods.functionEvaluation.exceptions.BetaFunctionException;
import domain.mathUtils.numericalMethods.interfaces.MultiVariableFunction;

/**
 * Provides methods regarding the beta Function
 * @see GammaFunction
 * @see MultiVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class BetaFunction implements MultiVariableFunction {
    
    private GammaFunction gammaFunction;
    /**
     * Constructor
     */
    public BetaFunction(){
        gammaFunction= new GammaFunction();
    }
    
    /**
     * Computes the value for the Beta function
     * @param variables 1D array containing two independent variables x and y
     * Example:
     * variables[1]=double x;
     * variables[2]= double y;
     * @return
     * @throws Exception 
     */
    @Override
    public double value(double[] variables) throws BetaFunctionException{
        if (variables.length==2){
            double x=variables[0];
            double y=variables[1];
            return Math.exp(gammaFunction.logValue(x)+gammaFunction.logValue(y)-
                    gammaFunction.logValue(x+y));
        }else 
            throw new BetaFunctionException();
    }
    /**
     * @param variables 1D array containing two independent variables x and y
     * Example:
     * variables[1]=double x;
     * variables[2]=double y;
     * @return 
     * @throws Exception 
     */
    public double logValue(double [] variables) throws BetaFunctionException{
        if(variables.length==2){
            double x=variables[0];
            double y=variables[1];
            return gammaFunction.logValue(x)+gammaFunction.logValue(y)-
                gammaFunction.logValue(x+y);
        }else
            throw new BetaFunctionException();
    }
    
}
