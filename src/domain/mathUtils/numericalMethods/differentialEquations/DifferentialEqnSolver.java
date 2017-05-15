/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.differentialEquations;
import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;
import domain.mathUtils.numericalMethods.functionEvaluation.OneVariableFunction;

/**
 *This class provides methods to solve initial value problems of the form dy/dt=f(t,y) for t0{@literal <=}t{@literal <=tend} and y(t0)=alpha through different numerical methods.
 */
public class DifferentialEqnSolver {

    private MultiVariableFunction f; // Function f(t,y)
    private double[] y; //Array containing the values of the approximate solution (dependent variable)
    private double y0; //Initial value
    protected double [] t; //Array containing the values of the independent variable
    private double[] exacty;// Array containing the values of the exact solution
    private OneVariableFunction exactyFunction; //One variable function containing the analytical expression of the exact solution
    private double [] absError; //Array containing the absolute error
    private double [] relError; //Array containing the relative error
    private double h; //Step size
    protected double t0; //Initial time 't0'
    protected double tend;// End time
    protected int n;// numer of samples
    
    private AMStepContainer ams; // auxiliary instance to perform an Adams-Bashfort-Moulton Step
    
   /**
    * Constructor
    * @param t0 initial time (double)
    * @param tend end time (double)
    * @param n number of points in the solution
    * @param y0 initial value y(t0)
    * @param f MultivariableFunction f(t,y) in dy/dt=f(t,y)
    * @throws IllegalArgumentException if t0{@literal >=}tend
    * @see MultiVariableFunction
    */
    public DifferentialEqnSolver(double t0, double tend, int n, double y0, MultiVariableFunction f) 
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
            this.y0 = y0;
            setStepSize(); // compute h
        }
    }
    
    /**
    * Constructor (Use when the exact solution is known)
    * @param t0 initial time (double)
    * @param tend end time (double)
    * @param n number of points in the solution
    * @param y0 initial value y(t0)
    * @param f MultivariableFunction f(t,y) in dy/dt=f(t,y)
    * @param exactyFunction One variable function specifying the values for the exact solution 
    * @throws IllegalArgumentException if t0{@literal >=}tend
    * @see MultiVariableFunction
    * @see OneVariableFunction
    */
    public DifferentialEqnSolver(double t0, double tend, int n, double y0, MultiVariableFunction f, OneVariableFunction exactyFunction) 
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
            this.y0=y0;
            this.exactyFunction=exactyFunction;
            setStepSize(); // compute h
        }
    }
    /**
    * Constructor
    * @param t0 initial time (double)
    * @param tend end time (double)
    * @param h step size
    * @param y0 initial value y(t0)
    * @param f MultivariableFunction f(t,y) in dy/dt=f(t,y)
    * @throws IllegalArgumentException if t0{@literal >=}tend
    * @see MultiVariableFunction
    */
    public DifferentialEqnSolver(double t0, double tend, double h, double y0, MultiVariableFunction f) 
            throws IllegalArgumentException{
        //Check if the interval is correct
        if (isCorrectInterval(t0, tend)){
            String s="The value of t0=" +t0 + " should be less than tend=" +tend;
            throw new IllegalArgumentException(s);
        }
        else if(h>Math.abs(tend-t0)){
            String s="The specified step size h="+h+" should be less or equal than abs(tend-t0)";
            }
        //Set the provided values as object attributes
        else{
            this.t0=t0;
            this.tend=tend;
            this.f=f;
            this.y0 = y0;
            this.n=(int) Math.round(Math.abs((tend-t0)/h))+1;
            setStepSize(); // compute n
        }
    }
    
    /**
    * Constructor (Use when the exact solution is known)
    * @param t0 initial time (double)
    * @param tend end time (double)
    * @param h step size
    * @param y0 initial value y(t0)
    * @param f MultivariableFunction f(t,y) in dy/dt=f(t,y)
    * @param exactyFunction One variable function specifying the values for the exact solution 
    * @throws IllegalArgumentException if t0{@literal >=}tend
    * @see MultiVariableFunction
    * @see OneVariableFunction
    */
    public DifferentialEqnSolver(double t0, double tend, double h, double y0, MultiVariableFunction f, OneVariableFunction exactyFunction) 
            throws IllegalArgumentException{
        //Check if the interval is correct
        if (isCorrectInterval(t0, tend)){
            String s="The value of t0=" +t0 + " should be less than tend=" +tend;
            throw new IllegalArgumentException(s);
        }
        else if(h>Math.abs(tend-t0)){
            String s="The specified step size h="+h+" should be less or equal than abs(tend-t0)";
            }
        //Set the provided values as object attributes
        else{
            this.t0=t0;
            this.tend=tend;
            this.f=f;
            this.y0=y0;
            this.exactyFunction=exactyFunction;
            this.n=(int) Math.round(Math.abs((tend-t0)/h))+1;
            setStepSize(); // compute n
        }
    }
    
    /**
     * Sets the Step size according to the existing values of initial time and ending time. This function updates the number of samples that are needed to obtain the specified step size as close as possible
     * @param h  desired step size
     */
    public void setStepSize(double h){
        this.n=(int) Math.round(Math.abs((tend-t0)/h))+1;
        setStepSize(); //update value of h
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
        setStepSize(); //update h
    }
    
    /**
     * Return the total number of samples n
     * @return n (total number of samples)
     */
    public int get_n(){
        return n;
    }

    /**
     * Sets the initial time and modifies the step size 'h' accordingly, maintaining the number of samples 'n'.
     * @param t0 initial time
     * @throws IllegalArgumentException when the specified t0 is not less than the value of tend
     */
    public void set_t0(double t0) throws IllegalArgumentException{
        if (t0<tend){
            this.t0=t0;
            setStepSize(); // compute h
        }
        else{
            throw new IllegalArgumentException("The value of t0=" +t0 + " should be less than tend=" +tend);
        }
    }
    
    /**
     * Sets the final time and modifies the value of the step size h accordingly, maintaining the number of samples 'n'
     * @param tend end time
     * @throws IllegalArgumentException when the specified tend is not greater or equal than t0
     */
    public void set_tend(double tend) throws IllegalArgumentException{
        if(tend>t0){
            this.tend=tend;
            setStepSize(); // compute h
        }
        else{
            throw new IllegalArgumentException("The value of tend=" +tend + " should be greater than t0=" +t0);
        } 
    }
    
    /**
     * Sets the function f(t,y)
     * @param f MultivariableFunction
     * @see MultiVariableFunction
     */
    public void set_f(MultiVariableFunction f){
        this.f=f;
    }
    
    /**
     * Returns the multivariable function f(t,y) in dy/dt=f(t,y)
     * @return f MultivariableFunction f(t,y) in dy/dt=f(t,y)
     * @see MultiVariableFunction
     */
    public MultiVariableFunction get_f(){
        return this.f;
    }
    
    /**
     * Returns the array containing the approximate solution values
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
        for(int i=0; i<n; i++){
            solution[i][1]=t[i];
            solution[i][2]=y[i];
        }
        return solution;
    }
    
    /**
     *Returns the array containing the exact solution values 
     * @return 
     */
    public double[] getExactSolution(){
        return exacty;
    }
    
    /**
     * Returns the values of the independent variable and the exact solution as an ordered pair.
     * The values of the independent variable are organized in the first column, while the values of the exact solution are organized in the second column
     * @return 
     */
    public double[][] getExactSolutionPair(){
        double [][] x= new double [n][2];
        for(int i=0; i<n; i++){
            x[i][1]=t[i];
            x[i][2]=exacty[i];
        }
        return x;
    }
    
    /**
     * Returns the exact solution function
     * @return exact solution function
     * @see OneVariableFunction
     */
    public OneVariableFunction getExactSolutionFunction(){
        return exactyFunction;
    }

    /**
     * Returns a 1D array containing the value of the relative error
     * @return 1D array containing the value of the relative error
     */
    public double[] getRelError(){
        return relError;
    }
    /**
     * Returns a 1D array containing the value of the absolute error
     * @return 1D array containing the values of the absolute error
     */
    public double[] getAbsError(){
        return absError;
    }
    /**
     * Solve the differential equation dy/dt=f(t,y) for t0{@literal <=}t{@literal <=tend} and y(t0)=alpha using the Euler's method
     * This function updates the 1D arrays 't' and 'y' which contain the values of the independent variable and dependent variable respectively
     */
    public void solveEuler() {
        //initialize solution arrays
        t=new double[n];
        y=new double[n];
        double t1=t0, y1=y0, t2, y2;
        t[0]=t0;
        y[0]=y0;
        double[] vars={t1,y1}; // auxiliar array to evaluate function f
        
        //Euler's method
        for(int i=1;i<n;i++){
            //Perform Euler's approximation
            t2=t1+h;
            y2=y1+h*f.value(vars);
            
            //Store in solution array
            y[i]=y2;
            t[i]=t2;
            
            //refresh variables
            t1=t2;
            y1=y2;
            vars[0]=t1;
            vars[1]=y1;
        }
        computeExacty();
    }
    
    /**
     * Solve the differential equation dy/dt=f(t,y) for t0 {@literal <=}t{@literal <=tend} and y(t0)=alpha using the RK4 (Runge Kutta fourth order) method
     * This function updates the 1D arrays 't' and 'y' which contain the values of the independent variable and dependent variable respectively
     */
    public void solveRK4(){
        //initialize solution arrays
        t= new double[n];
        y=new double[n];
        double t1=t0, y1=y0, k1, k2, k3 ,k4;
        double [] x;// auxiliar array to temporaly store evaluation points
        t[0]=t0; //store initial values in the solution
        y[0]=y0;
        
        for(int i=1; i<n;i++){
            //compute RK step
            x=RK4step(t1, y1);
            //store calculated points
            t[i]=x[0];
            y[i]=x[1];
            //update auxiliar variables
            t1=t[i];
            y1=y[i];
        }
       computeExacty();
    }
    
    /**
     * Performs the computation of a RK4 step
     * @param t1 current time
     * @param y1 current solution point
     * @return 1d array containing the values of the next time step 't2' and next solution evaluation 'y2'
     */
    private double[] RK4step(double t1, double y1){
        double[] vars={t1,y1};
        double [] x= new double[2];
        double k1, k2, k3, k4; //RK constants 
        k1=f.value(vars);
        vars[0]=t1+0.5*h;
        vars[1]=y1+0.5*h*k1;
        k2=f.value(vars);
        vars[1]=y1+0.5*h*k2;
        k3=f.value(vars);
        vars[0]=t1+h;
        vars[1]=y1+h*k3;
        k4=f.value(vars);
        
        x[0]=vars[0];
        x[1]=y1+h*(k1+2*k2+2*k3+k4)/6.0;
        return x;
    }
    /**
     * Solve the differential equation dy/dt=f(t,y) for t0 {@literal <=}t{@literal <=tend} and y(t0)=alpha using a predictor-corrector (Adams Bashfort Moulton method) <p>
     * The predictor uses a 3 step Adams-Bashfort explicit approximation O(h^3). <p>
     * The corrector uses a 3 step Adams-Moulton implicit approximation O(h^4). <p>
     * This function updates the 1D arrays 't' and 'y' which contain the values of the independent variable and dependent variable respectively
     */
    public void solveABM(){
        double[] x;
        double t1,y1;
        double p[]; //auxiliar array to store 'predicted' approximations
        t=new double[n];
        y=new double[n];
        t1=t0; y1=y0;
        t[0]=t0; y[0]=y0;
        //compute the first 4 points using the Runge Kutta method 
        for(int i=1;i<n && i<4;i++){
            //compute RK step
            x=RK4step(t1, y1);
            //store calculated points
            t[i]=x[0];
            y[i]=x[1];
            //update auxiliar variables
            t1=t[i];
            y1=y[i];
        }
        //Perform predictor-corrector method
        if(n>4){
            //prepare first ABM iteration
            ams=new AMStepContainer(); // create instance of container class
            ams.y4=y[3];
            ams.t4=t[3];
            for(int i=0;i<4;i++){
                ams.t[i]=t[i];
                ams.y[i]=y[i];
            }
            //perform ABM method
            for(int i=4; i<n; i++){
                //perform predictor-corrector step
                ams=AB3step(ams);
                //store calculated points
                y[i]=ams.y4;
                t[i]=ams.t4;
            }
        }
        computeExacty();
    }
    /**
     * Performs an Adams Bashfort Moulton Predictor corrector Step
     * @param ams instance of AMStepContainer class
     * @return AMSStepContainer with updated
     */
    private AMStepContainer AB3step(AMStepContainer ams){
        double p4;
        double[] p=ams.p;
        double[] ystep=ams.y;
        double[] tstep=ams.t;
        double vars[]= new double[2];
        
        //test if the first AMB iteration (when the approximation vector p is null
        if(ams.p==null){
            p=new double[4];
            //compute values of approximation vector
            for(int i=0;i<4;i++){
                vars[0]=tstep[i];
                vars[1]=ystep[i];
                p[i]=f.value(vars);
            }
        }
        //predict the next y (Adams Bashfort)
        ams.y4=ystep[3]+h/24.0*(55*p[3]-59*p[2]+37*p[1]-9*p[0]);
        //correct next y (Adams Moulton)
        ams.t4+=h;
        vars[0]=ams.t4;
        vars[1]=ams.y4;
        
        p4=f.value(vars);
        ams.y4=ystep[3]+h/24.0*(9*p4+19*p[3]-5*p[2]+p[1]);
        
        //update p, y, t
        for(int i=0; i<3; i++){
            p[i]=p[i+1];
            ystep[i]=ystep[i+1];
        }
        p[3]=p4;
        ystep[3]=ams.y4;
        ams.p=p;
        ams.y=ystep;
        return ams;
    }
    /**
     * Auxiliary class to perform Adams Moulton step
     * This class contains the next step values y4, and t4
     */
    protected class AMStepContainer{
        public double y4, t4;
        public double[] y= new double [4];
        public double[] t= new double [4];
        public double[] p= null;
    }
    
    @Override
    public String toString(){
        String s;
        if(exactyFunction==null){
            //show results without exact solution
            s="******Differential Eqn solver output********\n"
                + "Step size 'h'="+h+"\n"
                + "t\t\ty_approx\n";
            for(int i=0; i<n;i++){
                s+=String.format("%11.7E", t[i]);
                s+="\t";
                s+=String.format("%11.7E", y[i]);
                s+="\n";
            }
            s+="********************************************";
        }
        //show results with exact solution
        else{
            s="******Differential Eqn solver output********\n"
                + "Step size 'h'="+h+"\n"
                + "t\t\ty_approx\ty_exact\t\tAbs error\tRel error\n";
            for(int i=0; i<n;i++){
                s+=String.format("%11.7E", t[i]);
                s+="\t";
                s+=String.format("%11.7E", y[i]);
                s+="\t";
                s+=String.format("%11.7E", exacty[i]);
                s+="\t";
                s+=String.format("%11.7E", absError[i]);
                s+="\t";
                s+=String.format("%6.3f%%", relError[i]);
                s+="\n";
            }
            s+="********************************************";
        }
        return s;
    }
    
    /**
     * Sets the step Size according to the existing values of the initial, ending times, and number of samples
     */
    private void setStepSize(){
        this.h=Math.abs((tend-t0)/(n-1));
    }
        
    /**
     * Checks if the supplied values for the time interval are correct. That is, t0 {@literal <=} tend  
     * @param t0 initial time
     * @param tend ending time
     * @return 
     */
    private boolean isCorrectInterval(double t0, double tend){
        return t0>=tend;
    }
    
    /**
     * Computes the exact solution array 'exacty' from the exact solution. It also computes the absolute and relative errors
     */
    private void computeExacty(){
        if (exactyFunction!=null){
            exacty= new double[n];
            absError= new double[n];
            relError= new double[n];
            for(int i=0;i<n;i++){
                exacty[i]=exactyFunction.value(t[i]);
                relError[i]=Math.abs((y[i]-exacty[i])/exacty[i])*100;
                absError[i]=Math.abs((y[i]-exacty[i]));
            }
        }
    }
}
