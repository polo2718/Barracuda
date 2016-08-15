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
public class IncompleteBetaFunctionTest {
    private IncompleteBetaFunction incompleteBeta;
    private double expResult;
    private double result;
    
    public IncompleteBetaFunctionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        incompleteBeta= new IncompleteBetaFunction();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of value method, of class IncompleteBetaFunction.
     */
    @Test
    public void testValue() throws Exception {
        System.out.println("***IncompleteBetaFunctionTest 1*****");
        double[] variables = new double [3];
        double x=0.2;
        double alpha1=0.8;
        double alpha2=0.3;
        variables[0]=x;
        variables[1]=alpha1;
        variables[2]=alpha2;
        expResult =0.100821588838197 ;
        result=incompleteBeta.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        System.out.println("***IncompleteBetaFunctionTest 2*****");
        x=0.5;
        alpha1=0.8;
        alpha2=30;
        variables[0]=x;
        variables[1]=alpha1;
        variables[2]=alpha2;
        expResult =0.999999999538707 ;
        result=incompleteBeta.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        System.out.println("***IncompleteBetaFunctionTest 2*****");
        x=0.1;
        alpha1=1;
        alpha2=1;
        variables[0]=x;
        variables[1]=alpha1;
        variables[2]=alpha2;
        expResult = 0.1;
        result=incompleteBeta.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
    }
    
}
