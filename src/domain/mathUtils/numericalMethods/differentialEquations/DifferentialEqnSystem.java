/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.differentialEquations;

import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;
import domain.mathUtils.numericalMethods.functionEvaluation.OneVariableFunction;

/**
 *This class provides methods to solve systems of differential equations of the form du_k/dt=f_k(t,u1,u2,...,u_k) for k=1,2,3,..., m where m is
 * the number of dependent variables.<p> 
 * This class extends the DifferentialEqnSolver class.
 * @author Leopoldo Cendejas-Zaragoza (2017) Illinois Institute of Technology
 * @see DifferentialEqnSolver
 */
public class DifferentialEqnSystem extends DifferentialEqnSolver {
    
    private MultiVariableFunction[] f;
    private double[][] y; //Array containing the values of the approximate solutions
    private double[] u0; //Initial values
    
    /**
     * Constructor
     * @param t0 initial time
     * @param tend end time
     * @param n number of points in the solution
     * @param u0 array containing the initial values u1(t0), u2(t0), u3(t0)
     * @param f array containing the forcing functions f(t,u1,u2,...,un)
     * @throws IllegalArgumentException when 
     */
    public DifferentialEqnSystem(double t0, double tend, int n, double[] u0, MultiVariableFunction[] f)
            throws IllegalArgumentException{
        super(t0,tend,n,u0[0],f[0]);
        int u0_length=u0.length;
        int f_length=f.length;
        if (u0_length!=f_length){
            String s;
            s="The system is not well posed:\n"
                    + "The number of initial conditions u0="+u0_length+" is not the same as the number of functions supplied in f="
                    +f_length;
            throw new IllegalArgumentException(s);
        }
        this.u0=u0;
        this.f=f;
    }
    
    /**
     * Constructor
     * @param t0 initial time
     * @param tend end time
     * @param h step size
     * @param u0 array containing the initial values u1(t0), u2(t0), u3(t0)
     * @param f array containing the forcing functions f(t,u1,u2,...,un)
     */
    public DifferentialEqnSystem(double t0, double tend, double h, double[] u0, MultiVariableFunction[] f){
        super(t0,tend,h,u0[0],f[0]);
        int u0_length=u0.length;
        int f_length=f.length;
        if (u0_length!=f_length){
            String s;
            s="The system is not well posed:\n"
                    + "The number of initial conditions u0="+u0_length+" is not the same as the number of functions supplied in f="
                    +f_length;
            throw new IllegalArgumentException(s);
        }
        this.u0=u0;
        this.f=f;
    }
    
    /**
     * Sets the forcing functions
     * @param f array of MultiVariable functions containing the values of the multivariable function.
     */
    public void set_f(MultiVariableFunction[] f){
        this.f=f;
    }
}
