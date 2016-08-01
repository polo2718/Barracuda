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
        System.out.println("findMaximum");
        double[] x = null;
        double expResult = 0.0;
        double result = ArrayOperations.findMaximum(x);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
