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
 * This class defines the methods that are necessary to find  the roots of a function using the bisection method. 
 * The bisection algorithm uses the Iterative Process framework to find the roots.
 * @author "Leopoldo Cendejas-Zaragoza, Illinois Institute of Technology & RUSH
 * University, 2016"
 * @see FunctionIteration
 * @see IterativeProcess
 */
public class BisectionFinder extends FunctionIteration{
    /**
     * current x positive value
     */
    private double xPos;
    /**
     * current x negative value
     */
    private double xNeg;
    /**
     * General constructor
     * @param f function 
     * @param xpos initial value where f(x) is positive
     * @param xneg initial value where f(x) is negative
     */
    public BisectionFinder(OneVariableFunction f, double xpos, double xneg) {
        setFunction(f);
        setXPos(xpos);
        setXNeg(xneg);
    }
    
    @Deprecated
    @Override
    public void initializeIterations() {
    }
    
    /**
     * Computes the current iteration according to the bisection method
     * @return precision relative to the result
     */
    @Override
    public double evaluateIteration() {
        //current result is equal to the midpoint between a point where the function is positive
        //and another point where the function is negative.
        super.result=0.5*(xPos+xNeg);
        if(super.f.value(result)>0){
            xPos=result;
        }
        else
            xNeg=result;
        return relativePrecision(Math.abs(xPos-xNeg));
    }
    
    @Deprecated
    @Override
    public void finalizeIterations() { 
    }
    
    /**
     * Set the xPos value (value where f(x) is positive)
     * @param xPos value where f(x) is positive: f(xPos) is greater than 0
     * @throws IllegalArgumentException when f(x) is negative instead of positive at the specified xPos value
     */
    public void setXPos(double xPos) throws IllegalArgumentException{
        if(f.value(xPos)<0)
            throw new IllegalArgumentException("The value of f(" + xPos+ ") is"
                    + "negative instead of positive");
        else{
            this.xPos=xPos;
        }
    }
    
    /**
     * Set the xneg value (value where f(x) is negative)
     * @param xNeg value where f(x) is negative: f(xNeg) is less than 0
     * @throws IllegalArgumentException when f(x) is positive instead of negative at the specified xNeg value
     */
    public void setXNeg(double xNeg) throws IllegalArgumentException{
        if(f.value(xNeg)>0)
            throw new IllegalArgumentException("The value of f(" + xNeg+ ") is"
                    + "positive instead of negative");
        else{
            this.xNeg=xNeg;
        }
    }
}
