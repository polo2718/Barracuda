/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.iterative;
import domain.mathUtils.numericalMethods.interfaces.MultiVariableFunction;

/**
 * Provides the method to compute the Incomplete Gamma Function gamma(x,alpha)
 * The incomplete Gamma function is a Multivariable function.
 * @see MultiVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class IncompleteGammaFunction implements MultiVariableFunction{
    
    private final IncompleteGammaFractions gammaFractions;
    private final IncompleteGammaSeries gammaSeries;
    
    public IncompleteGammaFunction(){
        gammaFractions= new IncompleteGammaFractions();
        gammaSeries= new IncompleteGammaSeries();
    }
    /**
     * Returns the value of the incompleteGamma function gamma(x,alpha) using a combination of the series and
     * continued fractions approximation.
     * <p>The series approximation converges when x<alpha+1
     * <p>The continued fraction converges when x>alpha+1
     * @param variables 1D array containing the two independent variables x and alpha
     * <p> variables[0]=x
     * <p> variables[1]=alpha
     * @return value of the incompleteGamma function evaluated at gamma(x,alpha)
     * @throws IllegalArgumentException 
     */
    @Override
    public double value(double[] variables) throws Exception {
        if(variables.length!=2)
            throw new IllegalArgumentException("Check input array. Wrong number of independent variables were provided."
                + " IncompleteGamma function is defined for only two independent variables"
                + " x and alpha");
        double x=variables[0];
        double alpha=variables[1];
        if(x<alpha+1){
            return gammaSeries.value(variables);
        }
        else return gammaFractions.value(variables);
    }
    
}
