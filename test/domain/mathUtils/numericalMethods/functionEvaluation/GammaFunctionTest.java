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
        assertEquals(expResult, result, 0);
        
    }
    
}
