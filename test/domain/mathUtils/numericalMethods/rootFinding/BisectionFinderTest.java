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
public class BisectionFinderTest {
    private OneVariableFunction sine= new OneVariableFunction(){

        @Override
        public double value(double x) throws IllegalArgumentException {
           return Math.sin(x);
        }
        
    };
    
    private BisectionFinder bisection;
    private double xPos;
    private double xNeg;
    
    public BisectionFinderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        xPos=Math.PI/2.0;
        xNeg=3*Math.PI/2.0;
        bisection= new BisectionFinder(sine, xPos, xNeg);
    }
    
    @After
    public void tearDown() {
        bisection=null;
    }


    /**
     * Test of setXPos method, of class BisectionAlgorithm.
     */
    @Test
    public void testSetXPos() {
        xPos= 3*Math.PI/2.0;
        xNeg= 3*Math.PI/2.0+Math.PI/4.0;
        try{
            bisection = new BisectionFinder(sine, xPos, xNeg);
        }
        catch(IllegalArgumentException e){
            boolean test=e.getMessage().equals("The value of f(" + xPos+ ") is"
                    + "negative instead of positive");
            assertTrue(test);
        }
    }

    /**
     * Test of setXNeg method, of class BisectionAlgorithm.
     */
    @Test
    public void testSetXNeg() {
        xPos=Math.PI/2.0;
        xNeg=Math.PI/4;
        try{
            bisection = new BisectionFinder(sine, xPos, xNeg);
        }
        catch(IllegalArgumentException e){
            boolean test=e.getMessage().equals("The value of f(" + xNeg+ ") is"
                    + "positive instead of negative");
            assertTrue(test);
        }
    }
    
    /**
     * Test the bisection algorithm in a continuous function
     */
    @Test
    public void bisectionAlgorithm(){
        System.out.println("\nBisection Algorithm Test");
        System.out.println("Find roots for sin(x) between 0 and 2pi");
        double expResult=Math.PI;
        bisection.evaluate();
        double result=bisection.getResult();
        System.out.println("Exp result = "+ Math.PI);
        System.out.println("Result = "+ result);
        System.out.println(bisection.toString());
        assertEquals(expResult,result, 10e-8);
        System.out.println("Test Passed");
    }
    
}
