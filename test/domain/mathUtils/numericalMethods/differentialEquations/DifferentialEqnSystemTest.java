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
  
    
}
