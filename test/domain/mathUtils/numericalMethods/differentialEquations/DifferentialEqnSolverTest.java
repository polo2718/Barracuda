/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.differentialEquations;

import domain.mathUtils.numericalMethods.functionEvaluation.MultiVariableFunction;
import domain.mathUtils.numericalMethods.functionEvaluation.OneVariableFunction;
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
public class DifferentialEqnSolverTest {
    MultiVariableFunction f;
    OneVariableFunction exactyFunction;
    DifferentialEqnSolver eqnSolver;
    /**
     * Constructor
     */
    public DifferentialEqnSolverTest() {
        //define function f
        f=(double[] variables) -> {
            double value;
            double t=variables[0];
            double y=variables[1];
            return value=y-t*t+1;
        };
        //define function exacty
        exactyFunction= (double x) -> {
          double value;
          return value=(x+1)*(x+1)-0.5*Math.exp(x);
        };
    }
   
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before //performs test initialization before each test
    public void setUp() {
        eqnSolver=new DifferentialEqnSolver(0, 2, 11, 0.5, f);
    }
    
    @After //performs test cleanup after each test
    public void tearDown() {
        eqnSolver=null;
    }

    /**
     * Test of setStepSize method, of class DifferentialEqnSolver.
     */
    @Test
    public void testSetStepSize() {
        eqnSolver=new DifferentialEqnSolver(0, 2, 5, 0.5, f);
        System.out.println("*****setStepSize Test*****");
        double h = 0.2;
        eqnSolver.setStepSize(h);
        double expResult=0.2;
        double result=eqnSolver.getStepSize();
        assertEquals(expResult,result,0.0);
        
        //test if the number of samples was updated correctly
        int expN=11;
        int n=eqnSolver.get_n();
        assertEquals(expN,n);
        System.out.println("Test passed!");
    }

    /**
     * Test of set_n method, of class DifferentialEqnSolver.
     */
    @Test
    public void testSet_n() {
        System.out.println("*******set_n Test**********");
        eqnSolver=new DifferentialEqnSolver(0, 2, 5, 0.5, f);
        eqnSolver.set_n(11);
        int expResult = 11;
        int result=eqnSolver.get_n();
        
        assertEquals(expResult, result);
        //test if the step size was updated correctly
        double exp_h=2.0/(11-1);
        double h=eqnSolver.getStepSize();
        assertEquals(exp_h,h,0.0);
        System.out.println("Test passed!");
    }

    /**
     * Test of set_t0 method, of class DifferentialEqnSolver.
     */
    @Test
    public void testSet_t0() {
        System.out.println("*****set_t0 Test*****");
        double t0 = 3;
        
    }

    /**
     * Test of set_tend method, of class DifferentialEqnSolver.
     */
    @Test
    public void testSet_tend() {
        System.out.println("set_tend");
        double tend = 0.0;
        DifferentialEqnSolver instance = null;
        instance.set_tend(tend);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of solveEuler method, of class DifferentialEqnSolver.
     */
    @Test
    public void testSolveEuler() {
        System.out.println("******solveEuler Test****");
        eqnSolver.solveEuler();
        System.out.println(eqnSolver.toString());
    }
    
}
