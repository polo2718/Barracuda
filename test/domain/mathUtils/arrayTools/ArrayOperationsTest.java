/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.arrayTools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author "Leopoldo Cendejas-Zaragoza, Illinois Institute of Technology & RUSH
 * University, 2016"
 */
public class ArrayOperationsTest {
    
    public ArrayOperationsTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findMaximum method, of class ArrayOperations.
     */
    @Test
    public void testFindMaximum() {
        //test if the the method is actually finding the maximum
        System.out.println("findMaximum");
        double[] x = {5,4,3,2,1,4,5,100,2};
        double expResult = 100;
        double result = ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);
        
        //test what happens if the method is called with an empty input array
        x=new double[0];
        expResult=Double.NaN;
        result= ArrayOperations.findMaximum(x);
        assertEquals(result,expResult,0);
        
        //method should return the maximum value even if there are NaN in the array
        x=new double[] {Double.NaN, 2, 100, 0};
        expResult=100;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);
        
        //method should return positive infinity if the array contains positive infinity
        x=new double[] {3,Double.POSITIVE_INFINITY,2,Double.NaN,5};
        expResult=Double.POSITIVE_INFINITY;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);
        
        //method should return number if negative infinity is on the array
        x=new double[] {3,Double.NEGATIVE_INFINITY,2,Double.NaN,5};
        expResult=5;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);
    }
    
}
