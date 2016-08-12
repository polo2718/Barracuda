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
public class IncompleteGammaFractionsTest {
    
    private IncompleteGammaFractions incompleteGamma;
    private double expResult;
    private double result;
    
    public IncompleteGammaFractionsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        incompleteGamma= new IncompleteGammaFractions();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of value method, of class IncompleteGammaFractions.
     */
    @Test
    public void testValue() throws Exception {
        System.out.println("***IncompleteGammaFractionsTest 1*****");
        double[] variables = new double [2];
        double x=7.98;
        double alpha=3.234423;
        variables[0]=x;
        variables[1]=alpha;
        expResult =0.981281064115454 ;
        result=incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        System.out.println(incompleteGamma.toString());
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        System.out.println("***IncompleteGammaFractionsTest 2*****");
        alpha=525.32;
        x=680.178;
        variables[0]=x;
        variables[1]=alpha;
        expResult = 0.999999999721052;
        result = incompleteGamma.value(variables);
        System.out.println("expResult= "+expResult);
        System.out.println("result= "+ result);
        System.out.println(incompleteGamma.toString());
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!!");
        
        
    }
    
}
