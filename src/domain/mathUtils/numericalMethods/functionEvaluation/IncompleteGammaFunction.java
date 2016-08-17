/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;
import domain.mathUtils.numericalMethods.functionEvaluation.GammaFunction;
import domain.mathUtils.numericalMethods.functionEvaluation.interfaces.MultiVariableFunction;

/**
 * Provides the method to compute the Incomplete Gamma Function gamma(x,alpha)
 * <P> This function is approximated through an infinite series expansion and a continued fractions expansion.
 * The incomplete Gamma function is a Multivariable function.
 * @see IncompleteGammaFractions
 * @see IncompleteGammaSeries
 * @see MultiVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class IncompleteGammaFunction implements MultiVariableFunction{
    
    private static final IncompleteGammaFractions gammaFractions = new IncompleteGammaFractions();
    private static final IncompleteGammaSeries gammaSeries= new IncompleteGammaSeries();
    private static final GammaFunction gammaFunction= new GammaFunction();
    
    /**
     * Generic constructor
     */
    public IncompleteGammaFunction(){
    }
    /**
     * Returns the value of the incompleteGamma function gamma(x,alpha) using a combination of the series and
     * continued fractions approximation.
     * <p>The series approximation converges when x is less than alpha+1
     * <p>The continued fraction converges when x is greater than alpha+1
     * @param variables 1D array containing the two independent variables x and alpha
     * <p> variables[0]=x
     * <p> variables[1]=alpha
     * @return value of the incompleteGamma function evaluated at gamma(x,alpha)
     * @throws IllegalArgumentException when the input array has the wrong number of elements
     */
    @Override
    public double value(double[] variables) throws IllegalArgumentException{
        if(variables.length!=2)
            throw new IllegalArgumentException("Check input array. Wrong number of independent variables were provided."
                + " IncompleteGamma function is defined for only two independent variables"
                + " x and alpha");
        double x=variables[0];
        double alpha=variables[1];
        if(x==0)
            return 0;
        double leadingTerm=Math.exp(-x+alpha*Math.log(x)-gammaFunction.logValue(alpha));
        if(x-1<alpha){
            return leadingTerm*gammaSeries.value(variables);
        }
        else return 1-leadingTerm/gammaFractions.value(variables);
    }
    
}
