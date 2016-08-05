/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;

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
    
    private BetaFunction betaFunction; 
    
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
        System.out.println("Beta Value Test");
        double [] x={12,3};
        double expResult=.00091575091575091575;
        double result=betaFunction.value(x);
        assertEquals(expResult, result, 10e-6);
        System.out.println("Test passed!!\n");
        
        System.out.println("Beta Value Test 2");
        x= new double [] {1.5, 30};
        expResult=.0053271380392595118723;
        result=betaFunction.value(x);
        assertEquals(expResult, result, 10e-6);
        System.out.println("Test passed!!\n");
        
    }

    /**
     * Test of logValue method, of class BetaFunction.
     */
    @Test
    public void testLogValue() throws Exception {
        System.out.println("Beta logValue Test");
    }
    
}
