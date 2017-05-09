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
            double y=variables[0];
            double t=variables[1];
            return value=y-t*t+1;
        };
        //define function y
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
     * Test of set_f method, of class DifferentialEqnSolver.
     */
    @Test
    public void testSet_f() {
        System.out.println("set_f");
        MultiVariableFunction f = null;
        DifferentialEqnSolver instance = null;
        instance.set_f(f);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSolution method, of class DifferentialEqnSolver.
     */
    @Test
    public void testGetSolution() {
        System.out.println("getSolution");
        DifferentialEqnSolver instance = null;
        double[] expResult = null;
        double[] result = instance.getSolution();
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeArray method, of class DifferentialEqnSolver.
     */
    @Test
    public void testGetTimeArray() {
        System.out.println("getTimeArray");
        DifferentialEqnSolver instance = null;
        double[] expResult = null;
        double[] result = instance.getTimeArray();
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSolutionPair method, of class DifferentialEqnSolver.
     */
    @Test
    public void testGetSolutionPair() {
        System.out.println("getSolutionPair");
        DifferentialEqnSolver instance = null;
        double[][] expResult = null;
        double[][] result = instance.getSolutionPair();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExactSolutionFunction method, of class DifferentialEqnSolver.
     */
    @Test
    public void testGetExactSolutionFunction() {
        System.out.println("getExactSolutionFunction");
        DifferentialEqnSolver instance = null;
        OneVariableFunction expResult = null;
        OneVariableFunction result = instance.getExactSolutionFunction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of solveEuler method, of class DifferentialEqnSolver.
     */
    @Test
    public void testSolveEuler() {
        System.out.println("solveEuler");
        DifferentialEqnSolver instance = null;
        instance.solveEuler();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
