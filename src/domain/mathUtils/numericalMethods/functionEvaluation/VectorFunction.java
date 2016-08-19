/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;

/**
 * This interface defines a vector function. A vector function is a function whose domain is a set of real numbers (parameters) and
 * whose range is a set of vectors
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public interface VectorFunction {
    /**
     * This method returns the resulting vector components after evaluating the vector function in a set of parameters
     * @param parameters independent variables
     * @return 2d array containing vector components
     */
    public double[] value(double[] parameters);
}
