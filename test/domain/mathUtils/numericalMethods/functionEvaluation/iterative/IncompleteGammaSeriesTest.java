/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.iterative;

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
public class IncompleteGammaSeriesTest {
    
    private IncompleteGammaSeries incompleteGamma;
    private double expResult;
    private double result;
    private double alpha;
    private double x;
    
    public IncompleteGammaSeriesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        incompleteGamma= new IncompleteGammaSeries();
    }
    
    @After
    public void tearDown() {
        incompleteGamma=null;
    }

    /**
     * Test of value method, of class IncompleteGammaSeries.
     */
    @Test
    public void testValue() {
        System.out.println("***IncompleteGammaSeriesTest*****");
        double[] variables = new double[2];
        alpha=5;
        x=3;
        variables[0]=x;
        variables[1]=alpha;
        expResult = 2.21684106573;
        result = incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        System.out.println(incompleteGamma.toString());
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
    }


    
}
