/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.rootFinding;
import domain.mathUtils.numericalMethods.functionEvaluation.OneVariableFunction;
import domain.mathUtils.numericalMethods.iterations.FunctionIteration;
import domain.mathUtils.numericalMethods.iterations.IterativeProcess;

/**
 * This class defines the methods that are necessary to find the roots of a function using Newton's method
 * The Newton's method uses the Iterative Process Framework to find the roots.
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see FunctionIteration
 * @see IterativeProcess
 */
public class NewtonFinder extends FunctionIteration{
    /**
     * derivative of the function
     */
    private OneVariableFunction df;
    
    /**
     * General constructor 
     * @param f function
     * @param df derivative of the function
     * @param xi initial approximation to the root
     */
    public NewtonFinder(OneVariableFunction f, OneVariableFunction df, double xi) {
        super.setFunction(f);
        super.result=xi;
        this.df=df;
    }

    @Deprecated
    @Override
    public void initializeIterations() {
    }
    
    /**
     * Evaluates the current iteration according to Newton's method
     * @return precision relative to the result
     */
    @Override
    public double evaluateIteration() {
        double delta=f.value(result)/df.value(result);
        super.result-=delta;
        return relativePrecision(Math.abs(delta));
    }

    @Deprecated
    @Override
    public void finalizeIterations() {
    }
    
    /**
     * Set the derivative of the provided function
     * @param df One variable function 
     */
    public void setDerivative(OneVariableFunction df){
        this.df=df;
    }
    
}
