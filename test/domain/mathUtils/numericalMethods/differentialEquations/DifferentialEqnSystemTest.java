/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.differentialEquations;

import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leopoldo Cendejas-Zaragoza (2017) Illinois Institute of Technology
 */
public class DifferentialEqnSystemTest {
    MultiVariableFunction[] f;
    double[] u0;
    DifferentialEqnSystem systemSolver;
    public DifferentialEqnSystemTest() {
        // define forcing functions
        f=new MultiVariableFunction[2];
        f[0]=(double[] variables) -> {
            double u1=variables[1];
            double u2=variables[2];
            return 2*u1+4*u2;
        };
        f[1]=(double[] variables) -> {
            double u1=variables[1];
            double u2=variables[2];
            return -u1+6*u2;
        };
        //define initial conditions
        u0=new double[] {-1,6};
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        systemSolver=new DifferentialEqnSystem(0, 0.60, 0.1, u0, f);
    }
    
    @After
    public void tearDown() {
        systemSolver=null;
    }


    /**
     * Test of solveRK4 method, of class DifferentialEqnSystem.
     */
    @Test
    public void testSolveRK4() {
        System.out.println("******solveRK4 System Test****");
        systemSolver.solveRK4();
        System.out.println(systemSolver.toString());
        double[][] u=systemSolver.getSolutionArray();
        int n=systemSolver.get_n();
        double resultu1=u[n-1][0];
        double resultu2=u[n-1][1];
        double expResultu1=160.7563;
        double expResultu2=152.0025;
        
        assertEquals(expResultu1, resultu1, 1e-4);
        assertEquals(expResultu2, resultu2, 1e-4);
    }
    @Test
    public void testsolveABM(){
        
        System.out.println("**********solveABM System Test 1******");
        systemSolver=new DifferentialEqnSystem(0, 0.6, 0.05, u0, f);
        systemSolver.solveABM();
        System.out.println(systemSolver.toString());
        double[][] u=systemSolver.getSolutionArray();
        int n=systemSolver.get_n();
        
        // compare with the values of the exact solution and compare the error
        double resultu1=u[n-1][0];
        double resultu2=u[n-1][1];
        double expResultu1=160.9383752;
        double expResultu2=152.1198341;
        double expError1=0.02;
        double expError2=0.02;
        double error1=Math.abs((resultu1-expResultu1)/expResultu1);
        double error2=Math.abs((resultu2-expResultu2)/expResultu2);
        //error should be less than 0.02%
        assertEquals((error1<=expError1), true);
        assertEquals((error2<=expError2), true);
        
        System.out.println("**********solveABM System Test 2******");
        u0= new double[] {0.5};
        
        MultiVariableFunction f1= (double[] variables) -> {
            double value;
            double t=variables[0];
            double y=variables[1];
            return value=y-t*t+1;
        };
        f=new MultiVariableFunction[] {f1};
        systemSolver= new DifferentialEqnSystem(0, 2, 11, u0, f);
        
        systemSolver.solveABM();
        System.out.println(systemSolver.toString());
        u=systemSolver.getSolutionArray();
        n=systemSolver.get_n();
        
        resultu1=u[n-1][0];
        expResultu1=5.3053;
        assertEquals(expResultu1, resultu1, 1e-3);
    }
    
}
