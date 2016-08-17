/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;
import domain.mathUtils.numericalMethods.GenericMathDefinitions;
import domain.mathUtils.numericalMethods.functionEvaluation.interfaces.MultiVariableFunction;
import domain.mathUtils.numericalMethods.iterations.ContinuedFractions;
import domain.mathUtils.numericalMethods.iterations.IterativeProcess;

/**
 * This class defines the Incomplete Beta Function continued fraction expansion F(x,alpha1,alpha2)
 * The calculation of such fraction is performed through an iterative process.
 * <p> the continued fraction converges when x>(alpha1+1)/(alpha1+alpha2+2)
 * <p>The Incomplete Gamma Series is a MultiVariable Function gamma(x, alpha)
 * <p>This is a protected class and can only be used within the package
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see ContinuedFractions
 * @see IterativeProcess
 * @see MultiVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
class IncompleteBetaFractions extends ContinuedFractions implements MultiVariableFunction {

    private double alpha1;
    private double alpha2;

    /**
     * Generic constructor
     */
    public IncompleteBetaFractions() {
    }
    
    
    /**
    * Computes the value of nth a_n and b_n coefficients. 
    * In this case the b_n coefficient does not need an update
    * The computed values are stored in {@link ContinuedFractions#fractionCoeff} 
    * fractionCoeff[0] contains the value of the current a_n coefficient
    * @param n nth iteration
     */
    @Override
    protected void compute_N_fractionCoeff(int n) {
        //Check if an integer is even or odd using bit arithmetic (only for positive)
	int m = n / 2;
	int m2 = 2 * m;
	super.fractionCoeff[0] = m2 == n
                    ? super.argument * m * ( alpha2 - m)
                        / ( (alpha1 + m2) * (alpha1 + m2 - 1))
                    : -super.argument * ( alpha1 + m) * (alpha1 + alpha2 + m)
                        / ( (alpha1 + m2) * (alpha1 + m2 + 1));
    }
    
    /**
     * Computes the value of the first b_n term
     * @return value of the first b_n term
     * @see ContinuedFractions
     */
    @Override
    protected double initialValue() {
        return 1;
    }

    /**
     * Returns the value of the incompleteBeta function fraction F(x,alpha1, alpha2) when computed with a continued fraction approximation
     * The continued fraction converges when x>(alpha1+1)/(alpha1+alpha2+2)
     * @param variables 1D array containing the three independent variables x, alpha1 and alpha2
     * <p> variables[0]=x
     * <p> variables[1]=alpha1
     * <p> variables[2]=alpha2
     * @return value of the incompleteBeta function fraction evaluated at F(x,alpha1, alpha2)
     * @throws IllegalArgumentException an exception is thrown when the input array containing the 2 independent variables has the wrong dimensionality
     */
    @Override
    public double value(double[] variables) throws IllegalArgumentException {
       if(variables.length!=3){
           throw new IllegalArgumentException("Check input array. Wrong number of independent variables were provided."
            + " IncompleteGamma function is defined for only three independent variables"
            + " x, alpha1 and alpha2");
       }
       super.argument=variables[0];
       this.alpha1=variables[1];
       this.alpha2=variables[2];
       
      /*******ITERATIVE PROCESS************/
       //set precision
       super.setDesiredPrecision(GenericMathDefinitions.defaultNumericalPrecision());
       super.setMaximumIterations(100);
       super.evaluate();
       return super.getResult();
    }

}
