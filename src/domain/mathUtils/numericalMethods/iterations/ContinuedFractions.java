/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.iterations;
import domain.mathUtils.numericalMethods.interfaces.OneVariableFunction;

/**
 * This abstract class provides a framework to compute a function defined by a continued fraction.
 * <p> The computation of a continued fraction is performed by applying a recursive method known as the modified Lenz method.
 * (proposed by Thompson and Barnett)
 * <p> The computation follows a general iterative process 
 * <p> The continued fraction defines a function. Therefore the subclasses of this framework should implement the 
 * {@link OneVariableFunction} interface
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * Adapted from Didier H. Besset (2002) Object-Oriented Implementation of Numerical Methods. Morgan Kauffman Publishers
 */
public abstract class ContinuedFractions extends IterativeProcess {
    
    /**
     * Approximation for the continued fraction
     */
    private double result;
    
    /**
     * Continued fraction argument
     */
    private double argument;
    
    @Override
    public void initializeIterations(){
        
    }
    
    @Override
    public double evaluateIteration(){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void finalizeIterations(){
    }

    
}
