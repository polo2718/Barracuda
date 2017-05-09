/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.differentialEquations;
import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;

/**
 *This class provides a framework to solve initial value problems of the form dy/dt=f(y,t) for t0{@literal <=}t{@literal <=tend} and y(t0)=alpha through different numerical methods.
 */
public abstract class DifferentialEqnSolver {
    /**
     * Function f(y,t)
     */
    private MultiVariableFunction f;
    
    /**
     * Array containing the values of the approximate solution (dependent variable)
     */
    private double[] y;
    
    /**
     * Array containing the values of the independent variable
     */
    private double [] t;
    
    /**
     * Step size
     */
    private double h;
    
    /**
     * Initial time 't0'
     */
    private double t0;
    
    /**
     * End time 'tend'
     */
    private double tend;
    
    /**
     * number of samples 
     */
    private int n;
    
   /**
    * Constructor
    * @param t0 initial time (double)
    * @param tend end time (double)
    * @param n number of points in the solution
    * @param f MultivariableFunction f(y,t) in dy/dt=f(y,t)
    * @throws IllegalArgumentException if t0{@literal >=}tend
    */
    public DifferentialEqnSolver(double t0,double tend, int n, MultiVariableFunction f) 
            throws IllegalArgumentException{
        //Check if the interval is correct
        if (isCorrectInterval(t0, tend)){
            String s="The value of t0=" +t0 + " should be less than tend=" +tend;
            throw new IllegalArgumentException(s);
        }
        //Set the provided values as object attributes
        else{
            this.t0=t0;
            this.tend=tend;
            this.n=n;
            this.f=f;
            setStepSize();
        }
    }
     
    /**
     * Sets the step Size according to the existing values of the initial, ending times, and number of samples
     */
    private void setStepSize(){
        this.h=Math.abs((tend-t0)/n);
    }
    
    /**
     * Sets the Step size according to the existing values of initial time and ending time. This function updates the number of samples that are needed to obtain the specified step size as close as possible
     * @param h  desired step size
     */
    public void setStepSize(double h){
        this.n=(int) Math.round(Math.abs((tend-t0)/h));
    }
    
 
    /**
     * Returns the value of the step size
     * @return step size
     */
    public double getStepSize(){
        return this.h;
    }
    
    
    /**
     * Sets the number of samples in the solution, and updates the step size 
     * @param n 
     */
    public void set_n(int n){
        set_t0(t0);
        set_tend(tend);
        this.n=n;
        //update h
        this.h=Math.abs((tend-t0)/n);
    }

    /**
     * Sets the initial time
     * @param t0 initial time
     */
    public void set_t0(double t0){
        this.t0=t0;
    }
    
    /**
     * Sets the final time
     * @param tend end time
     */
    public void set_tend(double tend){
        this.tend=tend;
    }
    

    
    /**
     * Sets the function f(y,t)
     * @param f MultivariableFunction
     * @see MultiVariableFunction
     */
    public void set_f(MultiVariableFunction f){
        this.f=f;
    }
    
    /**
     * Returns the multivariable function f(y,t) in dy/dt=f(y,t)
     * @return f MultivariableFunction f(y,t) in dy/dt=f(y,t)
     * @see MultiVariableFunction
     */
    public MultiVariableFunction get_f(){
        return this.f;
    }
    
    /**
     * Checks if the supplied values for the time interval are correct. That is, t0 {@literal <=} tend  
     * @param t0 initial time
     * @param tend ending time
     * @return 
     */
    private boolean isCorrectInterval(double t0, double tend){
        return t0<tend;
    }
    
    /**
     * Returns the approximate solution array
     * @return array containing the approximate solution points (dependent variable)
     */
    public double[] getSolution(){
        return y;
    }
    
    /**
     * Returns the array containing the values of the independent variable
     * @return array containing the values of the independent variable
     */
    public double [] getTimeArray(){
        return t;
    }
    
    /**
     * Returns the values of the independent variable and the solution as an ordered pair.
     * The values of the independent variable are organized in the first column, while the values of the dependent variable are organized in the second column
     * @return 2d array containing the values of the independent variable and the solution as an ordered pair  
     */
    public double [][] getSolutionPair(){
        double [][] solution= new double [n][2];
        for(int i=0; i<t.length; i++){
            solution[i][1]=t[i];
            solution[i][2]=y[i];
        }
        return solution;
    }
    /**
     * Solve the differential equation dy/dt=f(y,t) for t0{@literal <=}t{@literal <=tend} and y(t0)=alpha
     * This function updates the 1D arrays 't' and 'y' which contain the values of the independent variable and dependent variable respectively
     */
    public abstract void solve();
}
