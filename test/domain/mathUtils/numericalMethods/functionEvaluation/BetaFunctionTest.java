/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;

import domain.mathUtils.numericalMethods.functionEvaluation.functionExceptions.BetaFunctionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class BetaFunctionTest {
    private static double tol= 1E-10; //error tolerance
    private BetaFunction betaFunction; 
    private double expResult;
    private double result;
    
    public BetaFunctionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        betaFunction=new BetaFunction();
    }
    
    @After
    public void tearDown() {
        betaFunction=null;
    }

    /**
     * Test of value method, of class BetaFunction.
     */
    @Test
    public void testValue() throws Exception {
        System.out.println("Beta Value Test beta(12,3)");
        double [] x={12,3};
        double expResult=.00091575091575091575;
        double result=betaFunction.value(x);
        assertEquals(expResult, result, tol);
        System.out.println("Test passed!!\n");
        
        System.out.println("Beta Value Test 2 beta(1.5, 30)");
        x= new double [] {1.5, 30};
        expResult=.0053271380392595118723;
        result=betaFunction.value(x);
        assertEquals(expResult, result, tol);
        System.out.println("Test passed!!\n");
        
        //test beta function for VERY large computations
        System.out.println("Beta Value Test 3 beta(460.5,456.3)");
        x= new double [] {460.5, 456.3};
        expResult=1.73374e-277;
        result=betaFunction.value(x);
        System.out.println("Expected result="+ expResult);
        System.out.println("Result="+ result);
        assertEquals(expResult, result, tol);
        System.out.println("Test passed!!\n");
        
    }

    /**
     * Test of logValue method, of class BetaFunction.
     */
    @Test
    public void testLogValue() throws Exception{
        //test logarithm of the Beta function
        System.out.println("\nBeta logValue Test");
        System.out.println("beta(503,900)");
        expResult=-917.716702491;
        double [] x={503.2, 900};
        result=betaFunction.logValue(x);
        System.out.println("Expected result="+ expResult);
        System.out.println("result="+ result);
        assertEquals(expResult,result, tol);
        System.out.println("Test passed\n");
    }
    
}
