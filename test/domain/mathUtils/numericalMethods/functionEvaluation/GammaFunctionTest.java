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
public class GammaFunctionTest {
    private GammaFunction gamma;
    private static double tol=1E-10;
    
    public GammaFunctionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        gamma=new GammaFunction();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of value method, of class GammaFunction.
     */
    @Test
    public void testValue() {
        // test that the Gamma function is giving NaN for negative integers
        double expResult=Double.NaN;
        double result=gamma.value(-243);     
        System.out.println("gamma(-243)");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, 0);
        System.out.println("Test passed!!\n");
        
        //test the returned value for the Gamma function gamma(4)=6
        expResult=6;
        result=gamma.value(4);
        System.out.println("gamma(4)");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-10);
        System.out.println("Test passed!!\n");
        
        //test a positive value which is less than 1 and greater than 0 gamma(0.55)
        expResult=1.61612426873;
        result=gamma.value(0.55);
        System.out.println("gamma(0.55)");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-10);
        System.out.println("Test passed!!\n");
        
        //test a non integer negative value gamma(-7.5)
        expResult=2.2384932886E-4;
        result=gamma.value(-7.5);
        System.out.println("gamma(-7.5)");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-10);
        System.out.println("Test passed!!\n");
        
        //test a large non integer negative value gamma(-46.7)
        expResult=-4.7766440088560100E-59;
        result=gamma.value(-46.7);
        System.out.println("gamma(-46.7)");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, tol);
        System.out.println("Test passed!!\n");
        
        //test positive value for the gama function gamma(5.4)
        expResult=44.5988481451;
        result=gamma.value(5.4);
        System.out.println("gamma(5.4)");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-10);
        System.out.println("Test passed!!\n");
        
        //test another positive integer value for the gama function gamma(10)
        expResult=362880;
        result=gamma.value(10);
        System.out.println("gamma 10");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-6);
        System.out.println("Test passed!!\n");
        
        //test large positive integer
        expResult=8.841761993739701e30;
        System.out.println("gamma 30");
        System.out.println("Expected result="+expResult);     
        result=gamma.value(30);
        System.out.println("Result="+result);
        assertEquals(expResult, result, 10e-10);
        System.out.println("Test passed!!\n");
        
        //test positive value>12 for the gama function gamma(12.1)
        expResult=5.09832278441104E7;
        result=gamma.value(12.1);
        System.out.println("gamma 12.1");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1E-4);
        System.out.println("Test passed!!\n");
        
        
        //test another positive value for the gama function gamma(3/2)
        expResult=Math.sqrt(Math.PI)/2;
        result=gamma.value(3.0/2.0);
        System.out.println("gamma(3/2)");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-6);
        System.out.println("Test passed!!\n");   
    }
    /**
     * Test for the logValue method
     */
    @Test
    public void logValueTest(){
        //Compute the log value of the Gamma function for a large value
        double expResult=2361.2830331159307;
        double result=gamma.logValue(460.5);
        System.out.println("\nln gamma(460.5)");
        System.out.println("Expected result="+expResult);     
        System.out.println("Result="+result);
        System.out.println("Test passed!!\n"); 
        assertEquals(expResult, result, tol);
    }
    
}
