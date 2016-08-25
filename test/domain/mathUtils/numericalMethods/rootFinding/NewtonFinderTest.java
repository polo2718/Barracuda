/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.rootFinding;

import domain.mathUtils.numericalMethods.functionEvaluation.OneVariableFunction;
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
public class NewtonFinderTest {
    private NewtonFinder newton;
    private OneVariableFunction f;
    private OneVariableFunction df;
    public NewtonFinderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        f=new OneVariableFunction(){
            @Override
            public double value(double x) throws IllegalArgumentException {
                return Math.sin(x);
            }
        };
        
        df= new OneVariableFunction(){
            @Override
            public double value(double x) throws IllegalArgumentException {
                return Math.cos(x);
            }
        };
        newton= new NewtonFinder(f, df, 3.0);
    }
    
    @After
    public void tearDown() {
        newton=null;
    }

    /**
     * Test of evaluateIteration method, of class NewtonFinder.
     */
    @Test
    public void testEvaluateIteration() {
        System.out.println("\nNewton Algorithm Test");
        System.out.println("Find roots for sin(x) between 0 and 2pi");
        double expResult=Math.PI;
        newton.evaluate();
        double result=newton.getResult();
        System.out.println("Exp result = "+ Math.PI);
        System.out.println("Result = "+ result);
        System.out.println(newton.toString());
        assertEquals(expResult,result, 10e-8);
        System.out.println("Test Passed");
    }
    
}
