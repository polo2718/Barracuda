/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.iterative;
import domain.mathUtils.numericalMethods.interfaces.MultiVariableFunction;
import domain.mathUtils.numericalMethods.iterations.ContinuedFractions;

/**
 * This class defines the Incomplete Gamma Function gamma(x,alpha) through the evaluation of
 * an infinite continued fraction. The calculation of such fraction is performed through an iterative
 * process.
 * <p>The Incomplete Gamma Series is a MultiVariable Function gamma(x, alpha)
 * <p>This is a protected class and can only be used within the package
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see ContinuedFractions
 * @see IterativeProcess
 * @see MultiVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
class IncompleteGammaFractions extends ContinuedFractions implements MultiVariableFunction {

    /**
     * Computes the value of nth a_n and b_n coefficients.
     * The computed values are stored in {@link ContinuedFractions#fractionCoeff}
     * @param n nth iteration
     */
    @Override
    protected void compute_N_fractionCoeff(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Computes the value of the first b_n term
     * @return value of the first term in the series
     * @see ContinuedFractions
     */
    @Override
    protected double initialValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double value(double[] variables) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
