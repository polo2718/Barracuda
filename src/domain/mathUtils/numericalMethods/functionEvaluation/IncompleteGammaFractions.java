/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;
import domain.mathUtils.numericalMethods.GenericMathDefinitions;
//import domain.mathUtils.numericalMethods.functionEvaluation.GammaFunction;
import domain.mathUtils.numericalMethods.functionEvaluation.interfaces.MultiVariableFunction;
import domain.mathUtils.numericalMethods.iterations.ContinuedFractions;

/**
 * This class defines the Incomplete Gamma Function gamma(x,alpha) through the evaluation of
 * an infinite continued fraction. The calculation of such fraction is performed through an iterative
 * process.
 * <p> the continued fraction converges when x>alpha+1
 * <p>The Incomplete Gamma Series is a MultiVariable Function gamma(x, alpha)
 * <p>This is a protected class and can only be used within the package
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see ContinuedFractions
 * @see IterativeProcess
 * @see MultiVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
class IncompleteGammaFractions extends ContinuedFractions implements MultiVariableFunction {

    private double x;
    private double alpha;
    private double sum; //auxiliary variable
    //private static GammaFunction gammaFunction;    
    /**
     * Generic constructor
     */
    public IncompleteGammaFractions() {
        //gammaFunction= new GammaFunction();
    }
    
    
    /**
     * Computes the value of nth a_n and b_n coefficients.
     * The computed values are stored in {@link ContinuedFractions#fractionCoeff}
     * @param n nth iteration
     */
    @Override
    protected void compute_N_fractionCoeff(int n) {
        sum+=2;
        double a_n=n*(alpha-n);
        super.fractionCoeff[0]=a_n;
        super.fractionCoeff[1]=sum;
    }
    /**
     * Computes the value of the first b_n term
     * @return value of the first b_n term
     * @see ContinuedFractions
     */
    @Override
    protected double initialValue() {
        sum=argument;
        return sum;
    }

     /**
     * Returns the value of the incompleteGamma function gamma(x,alpha) when computed with a continued fraction approximation
     * The continued fraction converges when x>alpha+1
     * @param variables 1D array containing the two independent variables x and alpha
     * <p> variables[0]=x
     * <p> variables[1]=alpha
     * @return value of the incompleteGamma function evaluated at gamma(x,alpha)
     * @throws IllegalArgumentException an exception is thrown when the input array containing the 2 independent variables has the wrong dimensionality
     */
    @Override
    public double value(double[] variables) throws IllegalArgumentException{
        if(variables.length!=2)
            throw new IllegalArgumentException("Check input array. Wrong number of independent variables were provided."
            + " IncompleteGamma function is defined for only two independent variables"
            + " x and alpha");
        //update instance variables with the provided values
        x=variables[0];
        alpha= variables[1];
        //set the continued fractions argument
        super.setArgument(x-alpha+1);
        //initialize auxiliar variables
        double fraction;
        //double leadingTerm=Math.exp(-x+alpha*Math.log(x)-gammaFunction.logValue(alpha));

        /*************ITERATIVE PROCESS************/
        //Set Precision
        super.setDesiredPrecision(GenericMathDefinitions.defaultNumericalPrecision());
        super.evaluate();
        return super.getResult();
    }

}
