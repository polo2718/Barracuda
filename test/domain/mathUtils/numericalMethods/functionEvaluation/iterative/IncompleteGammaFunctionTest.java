/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.iterative;

import domain.mathUtils.numericalMethods.functionEvaluation.IncompleteGammaFunction;
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
public class IncompleteGammaFunctionTest {
    
    private double expResult;
    private double result;
    private IncompleteGammaFunction incompleteGamma;
    public IncompleteGammaFunctionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        incompleteGamma= new IncompleteGammaFunction();
    }
    
    @After
    public void tearDown() {
        incompleteGamma=null;
    }

    /**
     * Test of value method, of class IncompleteGammaFunction.
     */
    @Test
    public void testValue() throws Exception {
        System.out.println("***IncompleteGammaFunctionTest 1*****");
        double[] variables = new double [2];
        double x=7.98;
        double alpha=3.234423;
        variables[0]=x;
        variables[1]=alpha;
        expResult =0.981281064115454 ;
        result=incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        System.out.println("***IncompleteGammaFunctionTest 2*****");
        alpha=525.32;
        x=680.178;
        variables[0]=x;
        variables[1]=alpha;
        expResult = 0.999999999721052;
        result = incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        System.out.println("***IncompleteGammaFunctioTest 3*****");
        alpha=5;
        x=3;
        variables[0]=x;
        variables[1]=alpha;
        expResult = 0.184736755476;
        result = incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        System.out.println("***IncompleteGammaSeriesTest 4*****");
        alpha=903.45;
        x=800.8575;
        variables[0]=x;
        variables[1]=alpha;
        expResult = 1.996767530907947e-04;
        result = incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        System.out.println("***IncompleteGammaSeriesTest 5*****");
        alpha=1;
        x=1;
        variables[0]=x;
        variables[1]=alpha;
        expResult = 0.632120558828558;
        result = incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        System.out.println("***IncompleteGammaSeriesTest 6*****");
        alpha=1;
        x=5;
        variables[0]=x;
        variables[1]=alpha;
        expResult = 0.993262053000915;
        result = incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
    }
    
}
