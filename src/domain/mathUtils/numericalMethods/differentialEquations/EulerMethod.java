/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.differentialEquations;
import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;
import domain.mathUtils.numericalMethods.functionEvaluation.OneVariableFunction;

/**
 * This class uses the DiffEqnSolver to solve an IVP of the form dy/dt=f(y,t) for t0{@literal <=}t{@literal <=tend} and y(t0)=alpha through the Euler's numerical method. 
 * @author Leopoldo Cendejas-Zaragoza (2017) Illinois Institute of Technology
 * @see DifferentialEqnSolver
 */
public class EulerMethod extends DifferentialEqnSolver {
    
    /**
    * Constructor
    * @param t0 initial time (double)
    * @param tend end time (double)
    * @param n number of points in the solution
    * @param f MultivariableFunction f(y,t) in dy/dt=f(y,t)
    * @throws IllegalArgumentException if t0{@literal >=}tend
    * @see MultiVariableFunction
    */
    public EulerMethod(double t0, double tend, int n, MultiVariableFunction f) throws IllegalArgumentException {
        super(t0, tend, n, f);
    }
    
    /**
    * Constructor (Use when the exact solution is known)
    * @param t0 initial time (double)
    * @param tend end time (double)
    * @param n number of points in the solution
    * @param f MultivariableFunction f(y,t) in dy/dt=f(y,t)
    * @param exactyFunction One variable function specifying the values for the exact solution 
    * @throws IllegalArgumentException if t0{@literal >=}tend
    * @see MultiVariableFunction
    * @see OneVariableFunction
    */
    public EulerMethod(double t0, double tend, int n, MultiVariableFunction f, OneVariableFunction exactyFunction) throws IllegalArgumentException {
        super(t0, tend, n, f, exactyFunction);
    }
    
    /**
     * Solve the differential equation dy/dt=f(y,t) for t0{@literal <=}t{@literal <=tend} and y(t0)=alpha
     * This function updates the 1D arrays 't' and 'y' which contain the values of the independent variable and dependent variable respectively
     */
    @Override
    public void solve() {
        
    }
    
}
