/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.differentialEquations;

import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;
import domain.mathUtils.numericalMethods.functionEvaluation.OneVariableFunction;

/**
 *This class provides methods to solve systems of differential equations of the form du_k/dt=f_k(t,u1,u2,...,u_k) for k=1,2,3,..., m 
 * from for t0{@literal <=}t{@literal <=tend} and with the initial conditions u_1(t0)=alpha_1, u2(t0)=alpha_2,...,u_m(t0)=alpha_m, where m is
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
    /**
     * Returns an array containing all the specified forcing functions
     * @return 1d array containing the forcing functions
     */
    public MultiVariableFunction[] get_fk(){
        return f;
    }
    
    @Override
    public MultiVariableFunction get_f(){
        throw new UnsupportedOperationException("Use 'get_fk' method when solving systems of Diff eqns:");
    }
    
    /**
     * Returns a 2D array containing the approximate values of the solution.<p>
     * Each column corresponds to the values of each dependent variable, i.e. u_1, u_2, u_3, ... u_m; 
     * @return 2D array containing the approximate values for the solution.
     */
    public double[][] getSolutionArray(){
        return y;
    }
   
    @Override
    public double[] getSolution(){
        throw new UnsupportedOperationException("Use 'getSolutionArray' method when solving systems of Diff eqns");
    }
    
    /**
     * Returns the values of the independent variable and the solution as an ordered pair.
     * The values of the independent variable are organized in the first column.
     * The values of the dependent variables are organized in the following columns
     * @return 2d array containing the values of the independent variable and the approximate solutions to the dependent variable as an ordered pair. <p>
     * 
     */
    @Override
    public double [][] getSolutionPair(){
        int f_length=f.length;
        double [][] solution=new double[n][f_length];
        for(int i=0;i<n;i++){
            for(int j=0; j<f_length; j++){
                if(j==0){
                    //fill time vector
                    solution[i][j]=t[i];
                }
                else{
                    //fill solution
                    solution[i][j]=y[i][j-1];
                }
            }
        }
        return solution;
    }
    
    @Override
    public double[] getExactSolution(){
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public double[][] getExactSolutionPair(){
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public double[] getRelError(){
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public double[] getAbsError(){
        throw new UnsupportedOperationException("Not implemented");
    }
    
    /**
     * Solve the differential equation dy/dt=f(t,y) for t0 {@literal <=}t{@literal <=tend} and y(t0)=alpha using the RK4 (Runge Kutta fourth order) method
     * This function updates the 1D arrays 't' and 'y' which contain the values of the independent variable and dependent variable respectively
     */
    @Override
    public void solveEuler(){
        throw new UnsupportedOperationException("Not implemented yet");
    }
    /**
     * This methods computes the solution to the system of differential equations of the form du_k/dt=f_k(t,u1,u2,...,u_k) for k=1,2,3,..., m 
     * from for t0{@literal <=}t{@literal <=tend} and with the initial conditions u_1(t0)=alpha_1, u2(t0)=alpha_2,...,u_m(t0)=alpha_m, where m is 
     * the number of dependent variables.<p> 
     */
    @Override
    public void solveRK4(){
    }
    
    @Override
    public void solveABM(){
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String toString(){
       int nsol=f.length;//number of dependent variables
       double[][] solution=getSolutionPair();
       String s;
       s="******Differential Eqn solver output********\n"
                + "Step size 'h'="+h+"\n"
                + "t\t";
       for(int i=0;i<nsol;i++){
        s+="u_"+i+1+"\t";
       }
       s+="\n";
       for(int i=0;i<n;i++){ //rows
           for(int j=0;j<nsol+1;j++){ //columns
               s+=String.format("%11.7E", solution[i][j])+"\t";
           }
           s+="\n";
       }
       return s;
    }

    
}
