/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.iterative;
import domain.mathUtils.numericalMethods.GenericMathDefinitions;
import domain.mathUtils.numericalMethods.functionEvaluation.GammaFunction;
import domain.mathUtils.numericalMethods.interfaces.MultiVariableFunction;
import domain.mathUtils.numericalMethods.iterations.InfiniteSeries;
import domain.mathUtils.numericalMethods.iterations.IterativeProcess;

/**
 *
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see InfiniteSeries
 * @see IterativeProcess
 */
public class IncompleteGammaSeries extends InfiniteSeries implements MultiVariableFunction{
    
    private double alpha;
    private static GammaFunction gammaFunction;

    /**
     * Generic constructor for the Incomplete GammaSeries.
     */
    public IncompleteGammaSeries() {
        alpha=Double.NaN;
        gammaFunction=new GammaFunction();
    }
    
    /**
     * Returns the value of the incompleteGamma function gamma(x,alpha) when computed with a series approximation
     * The series converges when x<alpha+1
     * @param variables 1D array containing the two independent variables x and alpha
     * <p> variables[0]=x
     * <p> variables[1]=alpha
     * @return value of the incompleteGamma function evaluated at gamma(x,alpha)
     * @throws IllegalArgumentException 
     */
    @Override
    public double value(double[] variables) throws IllegalArgumentException {
        if(variables.length!=2)
            throw new IllegalArgumentException("Check input array. Wrong number of independent variables were provided."
                    + " IncompleteGamma function is defined for only two independent variables"
                    + " x and alpha");
        //Extract first independent variable (x)
        double x= variables[0];
        //Extract second independent variable
        this.alpha=variables[1]; 
        //Initialize auxiliar variables
        double seriesResult;
        double leadingTerm=Math.exp(-x+alpha*Math.log(x)-gammaFunction.logValue(x));
        //++++++++Iterative process++++++++++//
        //Perform iterative process for evaluation
        //Set the argument x for the series
        super.argument=x;
        //Set the desired precision of the result
        super.setDesiredPrecision(GenericMathDefinitions.defaultNumericalPrecision());
        //Evaluate the series using the iterative method
        super.evaluate();
        seriesResult=super.getResult();
        return leadingTerm*seriesResult;
    }
    @Override
    protected void compute_N_Term(int n) {
        super.lastTerm=initialValue();
        for(int i=1;i<=n;i++){
            super.lastTerm*=argument/(alpha+i+1);
        }
    }
    
    /**
     * Computes the value of the first term of the series
     * @return 
     */
    @Override
    protected double initialValue() {
        return 1.0/alpha;
    }
}
