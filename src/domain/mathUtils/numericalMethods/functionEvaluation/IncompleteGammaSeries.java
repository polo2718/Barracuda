/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;
import domain.mathUtils.numericalMethods.GenericMathDefinitions;
//import domain.mathUtils.numericalMethods.functionEvaluation.GammaFunction;
import domain.mathUtils.numericalMethods.iterations.InfiniteSeries;
import domain.mathUtils.numericalMethods.iterations.IterativeProcess;

/**
 * This class defines the Incomplete Gamma Function gamma(x,alpha) through the evaluation of
 * an infinite series expansion. The calculation of such series is performed through an iterative
 * process
 * <p> The Incomplete Gamma Series is a Multivariable Function gamma(x,alpha)
 * <p>This is a protected class and can only be used within the package
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see InfiniteSeries
 * @see IterativeProcess
 * @see MultiVariableFunction
 */
class IncompleteGammaSeries extends InfiniteSeries implements MultiVariableFunction{
    
    private double alpha;
    private double denominator; //auxiliar variable
    //private static GammaFunction gammaFunction;

    /**
     * Generic constructor for the Incomplete GammaSeries.
     */
    public IncompleteGammaSeries() {
        //gammaFunction=new GammaFunction();
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
        //Extract first independent variable (x) and set this value as the series argument
        double x= variables[0];
        super.setArgument(x);
        //Extract second independent variable
        this.alpha=variables[1]; 
        //Initialize auxiliar variables
        double seriesResult;
        //double leadingTerm=Math.exp(-x+alpha*Math.log(x)-gammaFunction.logValue(alpha));
        
        //++++++++Iterative process++++++++++//
        //Perform iterative process for evaluation
        //Set the desired precision of the result
        super.setDesiredPrecision(GenericMathDefinitions.defaultNumericalPrecision());
        super.setMaximumIterations(100);
        //Evaluate the series using the iterative method
        super.evaluate();
        seriesResult=super.getResult();
        //return leadingTerm*seriesResult;
        return seriesResult;
    }
    /**
     * 
     * @param n iteration number
     * 
     */
    @Override
    protected void compute_N_Term(int n) {
        //Since only the last term in the series is of interest, the denominator auxiliary variable
        //is used to avoid performing a loop to compute the last term
        //denominator=alpha+n
        /*
        super.lastTerm=initialValue();
        for(int i=1;i<=n;i++){
            super.lastTerm*=argument/(alpha+i);
        }
        */
        denominator+=1;
        super.lastTerm*=super.argument/denominator;
    }
    
    /**
     * Computes the value of the first term of the series
     * @return 
     */
    @Override
    protected double initialValue() {
        denominator=alpha; //this is an auxiliar variable that is used to avoid a loop calculation in the method compute_N_term
        super.lastTerm=1.0/alpha;
        return lastTerm;
    }
}
