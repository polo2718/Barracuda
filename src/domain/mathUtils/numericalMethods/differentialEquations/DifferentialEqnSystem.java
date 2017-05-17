/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.differentialEquations;

import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;

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
    private double[][] u; //Array containing the values of the approximate solutions
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
        return u;
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
        int nVars=f.length+1;
        double [][] solution=new double[n][nVars];
        for(int i=0;i<n;i++){
            for(int j=0; j<nVars; j++){
                if(j==0){
                    //fill time vector
                    solution[i][j]=t[i];
                }
                else{
                    //fill solution
                    solution[i][j]=u[i][j-1];
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
     * the number of dependent variables. Using a 4th order Runge Kutta Method<p> 
     */
    @Override
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public void solveRK4(){
        int numVar=f.length; //number of dependent variables
        u=new double [n][numVar]; //initialize solution array
        t= new double[n]; // initialize time array
        int nVars=numVar+1; //auxiliar variable to store the number of independent variables of the forcing function 
        double[] vars=new double[nVars]; //auxiliar array for computing the function
        double[] k1=new double[numVar]; //coefficients for Runge Kutta Operation
        double[] k2=new double[numVar];
        double[] k3=new double[numVar];
        double[] k4=new double[numVar];
   
        //Populate solution and time arrays with the initial values
        t[0]=t0;
        System.arraycopy(u0, 0, u[0], 0, numVar);// place the initial values at the beginning of solution array
        
        //Perform RK4 solution
        for(int i=1; i<n;i++){
            //compute k1
            for(int j=0;j<numVar;j++){
                //fill auxiliar array vars to compute the function
                vars[0]=t[i-1];
                for(int k=1; k<nVars;k++){
                    vars[k]=u[i-1][k-1];
                }
                k1[j]=f[j].value(vars);
            }
            //compute k2
            for(int j=0;j<numVar;j++){
                //fill auxiliar array vars to compute the function
                vars[0]=t[i-1]+0.5*h;
                for(int k=1; k<nVars;k++){
                    vars[k]=u[i-1][k-1]+0.5*h*k1[k-1];
                }
                k2[j]=f[j].value(vars);
            }
            //compute k3
            for(int j=0;j<numVar;j++){
                //fill auxiliar array vars to compute the function
                //vars[0]=t[i-1]+0.5*h;
                for(int k=1; k<nVars;k++){
                    vars[k]=u[i-1][k-1]+0.5*h*k2[k-1];
                }
                k3[j]=f[j].value(vars);
            }
            //compute k4
            for(int j=0;j<numVar;j++){
                //fill auxiliar array vars to compute the function
                vars[0]=t[i-1]+h;
                for(int k=1; k<nVars;k++){
                    vars[k]=u[i-1][k-1]+h*k3[k-1];
                }
                k4[j]=f[j].value(vars);
            }
            //Compute next value
            t[i]=vars[0];
            for(int j=0;j<numVar;j++){
                u[i][j]=u[i-1][j]+h/6.0*(k1[j]+2*k2[j]+2*k3[j]+k4[j]);
            }
        }
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
                + "t\t\t";
       for(int i=0;i<nsol;i++){
        s+="u_"+Integer.toString(i+1)+"\t\t";
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
