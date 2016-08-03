/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;

import java.util.Arrays;
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
public class PolynomialFunctionTest {
    private PolynomialFunction polynomial;
    
    public PolynomialFunctionTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before //Perform initialization before each test
    public void setUp() {
        double [] x={1,2,0,1};
        polynomial=new PolynomialFunction(x);
    }
    
    @After //Perform initialization after each test
    public void tearDown() {
        polynomial=null;
    }

    /**
     * Test of value method, of class PolynomialFunction.
     */
    @Test
    public void testValue() {
        System.out.println("****testValue");
        //Evaluate function when x=3
        double x=3;
        double result=polynomial.value(x);
        double expResult=34;
        assertEquals(expResult, result, 0);
        
        //Evaluate function when x=2.8
        x=2.8;
        result=polynomial.value(x);
        expResult=28.552;
        assertEquals(expResult, result, 0);
        System.out.println("test passed!!!");
    }
    
    /**
     * Test of derivative method, of class PolynomialFunction 
     */
    @Test
    public void testDerivative(){
        System.out.println("****testDerivative");
        //see if the derivative function is properly calculated
        double [] expCoeff={2,0,3}; //actual exponents of the derivative function
        
        PolynomialFunction expResult=new PolynomialFunction(expCoeff); // expected output polynomial function
        System.out.println("Expected coefficients");
        System.out.println(Arrays.toString(expResult.getCoefficients()));

        PolynomialFunction result=polynomial.derivative(); //compute the derivative of the polynomial function
        System.out.println("Coefficients computed through derivative");
        System.out.println(Arrays.toString(result.getCoefficients()));
        
        System.out.println("Expected degree\n"+expResult.degree());
        System.out.println("Degree computed through derivative\n"+result.degree());
        
        assertEquals(expResult, result);
        System.out.println("test passed!!!");
    }
}
