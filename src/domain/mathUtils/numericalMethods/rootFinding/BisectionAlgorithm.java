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
 *
 * @author "Leopoldo Cendejas-Zaragoza, Illinois Institute of Technology & RUSH
 * University, 2016"
 * @see FunctionIteration
 * @see IterativeProcess
 */
public class BisectionAlgorithm extends FunctionIteration{
    
    public BisectionAlgorithm(OneVariableFunction f, double xpos, double xneg) {
        setFunction(f);
    }

    @Override
    public void initializeIterations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double evaluateIteration() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void finalizeIterations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
