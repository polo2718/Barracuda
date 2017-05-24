package examples;
import domain.mathUtils.numericalMethods.differentialEquations.DifferentialEqnSolver;
import domain.mathUtils.numericalMethods.functionEvaluation.*;
//This class provides an example to solve initial value problems (IVP) using several approximation methods (Euler, RK4, Adams-Bashfort-Moulton)
public class DiffEqnExample {
    public static void main(String[] args) {
        //Start by setting up the IVP
        DifferentialEqnSolver eqnSolver; //Differential equation solver
        double h=0.2; //step size (duration) 
        double t0=0; //initial time
        double tN=2; //final time
        double y0=0.5; //initial condition
        //Define forcing function
        MultiVariableFunction f;
        //define forcing function f
        f=(double[] variables) -> {
            double value;
            double t=variables[0];
            double y=variables[1];
            return value=y-t*t+1;
        };
        //Define exact solution (if known)
        OneVariableFunction exactyFunction;
        exactyFunction= (double x) -> {
          double value;
          return value=(x+1)*(x+1)-0.5*Math.exp(x);
        };
        //initialize a differential equation solver
        eqnSolver=new DifferentialEqnSolver(t0, tN, h, y0, f, exactyFunction);
        //eqnSolver=new DifferentialEqnSolver(t0, tN, h, y0, f);//if exact solution is not known use this line instead
        eqnSolver.solveEuler();//Solve by Euler's method
        System.out.println(eqnSolver.toString());
        eqnSolver.solveRK4(); //Solve by RK4 Method
        System.out.println(eqnSolver.toString());
        eqnSolver.solveABM(); //Solve by ABM Method
        System.out.println(eqnSolver.toString());
    }
}
