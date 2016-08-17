/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.iterations;
import domain.mathUtils.numericalMethods.functionEvaluation.interfaces.OneVariableFunction;

/**
 * This abstract class is a general framework to approximate a result by means of an iterative process based on a function definition.
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see IterativeProcess
 * Adapted from Didier H. Besset (2002) Object-Oriented Implementation of Numerical Methods. Morgan Kauffman Publishers 
 */
public abstract class FunctionIteration extends IterativeProcess {
    
    /**
     * Computed result
     */
    private double result=Double.NaN;
    
    /**
     * Function that is necessary to perform the iterative analysis
     */
    private OneVariableFunction f;
    
    /**
     * Constructor
     */
    public FunctionIteration(){
    }
    
    public void setFunction(OneVariableFunction function){
        f=function;
    }
    
    /**
     *Sets the initial estimated value 
     * @param x initial value 
     */
    public void setInitialValue(double x){
        result=x;
    }
    
    /**
     * Returns the current result
     * @return current result
     */
    public double getResult(){
        return result;
    }
    
    /**
     * Computes the precision relative to the current result
     * @param epsilon absolute precision
     * @return precision relative to the current result
     */
    public double relativePrecision(double epsilon){
        return super.relativePrecision(epsilon, Math.abs(result));
    }
}
