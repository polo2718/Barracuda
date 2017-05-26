package examples;
import domain.mathUtils.numericalMethods.differentialEquations.DifferentialEqnSystem;
import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;
//Example for solving systems of differential equations
public class DiffEqnSystem {
    public static void main(String[] args) {
        double h=0.1;   //step size
        double t0=0;    //initial time
        double tN=0.60; //final time
        double y1_0=-1; //initial condition for y1
        double y2_0=3;  //initial condition for y2
        double[] y_0={y1_0,y2_0}; //organize initial values in a column vector
        //Initialize forcing functions
        MultiVariableFunction f1,f2;
        f1=(double[] variables) -> {
            double t=variables[0];//not used in this example
            double y1=variables[1];
            double y2=variables[2];
            return 2*y1+4*y2; //forcing function
        };
        f2=(double[] variables) -> {
            double t=variables[0];//not used in this example
            double y1=variables[1];
            double y2=variables[2];
            return -y1-5*y2; //forcing function
        };
        //Organize forcing functions in column vector
        MultiVariableFunction[] f={f1,f2};
        //Initialize Differential equation Solver
        DifferentialEqnSystem eqnSystem;
        eqnSystem= new DifferentialEqnSystem(t0, tN, h, y_0, f);
        //Solve using RK4
        eqnSystem.solveRK4();
        System.out.println(eqnSystem.toString());
        //Solve using ABM
        eqnSystem.solveABM();
        System.out.println(eqnSystem.toString());
    }
}
