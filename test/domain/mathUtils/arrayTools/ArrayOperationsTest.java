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

    /**
     * Test of findMaximum method, of class ArrayOperations.
     */
    @Test
    public void testFindMaximum_2DArr() {
        //Test if the method is actually giving a maximum
        System.out.println("findMaximum 2D");
        double[][] x = {{10, 40, 3}, {4, 5, -54}, {7, 8, 9}};
        double expResult = 40;
        double result = ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0.0);
        
        //method should return the maximum value even if there are NaN in the array
        x=new double[][] {{Double.NaN, 2, 100}, {4, Double.NaN, -54}, {7, 8, 9,102}} ;
        expResult=102;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);
        
        //method should return positive infinity if the array contains positive infinity
        x=new double[][] {{Double.NaN, 2, 100}, {4, Double.NaN, -54}, 
            {Double.POSITIVE_INFINITY, 8, 9,102}} ;
        expResult=Double.POSITIVE_INFINITY;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);   
        
        //method should return the expected value even if a negative Infinity is present in the array
        x=new double[][] {{Double.NaN, 2, Double.NEGATIVE_INFINITY}, {4, Double.NaN, -54}, {7, 8, 9,102}} ;
        expResult=102;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);
        
        //method should return NaN if the input array is empty
        x=new double[0][0];
        expResult=Double.NaN;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result,0);
        
        //method should return the maximum value even if one dimension of the array is not initialized
        x=new double[][]{{Double.NaN, 2, 100}};
        expResult=100;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);
        
        x=new double[][]{{Double.NaN}, {2}, {100}};
        expResult=100;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0);
    }
    
    /**
     * Test of findMinimum method, of class ArrayOperations.
     */
    @Test
    public void testFindMinimum() {
        //test if the the method is actually finding the minimum
        System.out.println("findMinimum");
        double[] x = {-5,4,3,2,1,4,5,100,2};
        double expResult = -5;
        double result = ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);
        
        //test what happens if the method is called with an empty input array
        x=new double[0];
        expResult=Double.NaN;
        result= ArrayOperations.findMinimum(x);
        assertEquals(result,expResult,0);
        
        //method should return the minimum value even if there are NaNs in the array
        x=new double[] {Double.NaN, 2, 100, 0};
        expResult=0;
        result=ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);
        
        //method should return the minimum number if the array contains positive infinity
        x=new double[] {3,Double.POSITIVE_INFINITY,-2,Double.NaN,5};
        expResult=-2;
        result=ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);
        
        //method should negative infinity if the array contains a negative infinity value
        x=new double[] {3,Double.NEGATIVE_INFINITY,2,Double.NaN,5, Double.POSITIVE_INFINITY};
        expResult=Double.NEGATIVE_INFINITY;
        result=ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);
    }    
    /**
     * Test of findMinimum method, of class ArrayOperations.
     */
    @Test
    public void testFindMinimum_2DArr() {
        //Test if the method is actually giving a minimum
        System.out.println("findMinimum 2D");
        double[][] x = {{10, 40, 3}, {4, 5, -54}, {7, 8, 9}};
        double expResult = -54;
        double result = ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0.0);
        
        //method should return the minimum value even if there are NaNs in the array
        x=new double[][] {{Double.NaN, 2, 100}, {4, Double.NaN, -54}, {7, 8, 9,102}} ;
        expResult=-54;
        result=ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);
        
        //method should return the expected value if the array contains positive infinity
        x=new double[][] {{Double.NaN, 2, 100}, {4, Double.NaN, -54}, 
            {Double.POSITIVE_INFINITY, 8, 9,102}} ;
        expResult=-54;
        result=ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);   
        
        //method should return negative infinity if a negative Infinity is present in the array
        x=new double[][] {{Double.NaN, 2, Double.NEGATIVE_INFINITY}, {4, Double.NaN, -54}, {7, 8, 9,102}} ;
        expResult=Double.NEGATIVE_INFINITY;
        result=ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);
        
        //method should return NaN if the input array is empty
        x=new double[0][0];
        expResult=Double.NaN;
        result=ArrayOperations.findMaximum(x);
        assertEquals(expResult, result,0);
        
        //method should return the minimum value even if one dimension of the array is not initialized
        x=new double[][]{{Double.NaN, 2, 100}};
        expResult=2;
        result=ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);
        
        x=new double[][]{{Double.NaN}, {2}, {100}};
        expResult=2;
        result=ArrayOperations.findMinimum(x);
        assertEquals(expResult, result, 0);
    }

}
