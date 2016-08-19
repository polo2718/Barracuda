/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.statistics;

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
public class StudentDistributionTest {
    private StudentDistribution studentD;
    
    public StudentDistributionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        studentD= new StudentDistribution(1);
    }
    
    @After
    public void tearDown() {
        studentD=null;
    }

    /**
     * Test of value method, of class StudentDistribution.
     */
    @Test
    public void testValue() {
        
    }

    /**
     * Test of cumulativeDensityValue method, of class StudentDistribution.
     */
    @Test
    public void testCumulativeDensityValue() {
        System.out.println("\nCumulative Density Student distribution Test");
        //15 degrees of freedom
        int dof=15;
        studentD.setDof(dof);
        System.out.println("DOF= " + dof);
        double expResult=.025;
        double t=2.131;
        System.out.println("Probability of having a random variable <=" +t);
        double result=studentD.cumulativeDensityValue(t);
        System.out.println("Exp Result="+expResult);
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed");
        
    }

    /**
     * Test of average method, of class StudentDistribution.
     */
    @Test
    public void testAverage() {
        assertEquals(0,studentD.average(),0);
    }

    /**
     * Test of variance method, of class StudentDistribution.
     */
    @Test
    public void testVariance() {
        studentD.setDof(2);
        double expResult= Double.NaN;
        double result=studentD.variance();
        assertEquals(expResult, result, 0);
        
        studentD.setDof(6);
        expResult=3.0/2.0;
        result=studentD.variance();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of skewness method, of class StudentDistribution.
     */
    @Test
    public void testSkewness() {
        studentD.setDof(12);
        double expResult=0;
        double result=studentD.skewness();
        assertEquals(expResult, result,0);
    }

    /**
     * Test of kurtosis method, of class StudentDistribution.
     */
    @Test
    public void testKurtosis() {
        studentD.setDof(4);
        double expResult= Double.NaN;
        double result=studentD.kurtosis();
        assertEquals(expResult, result, 0);
        
        studentD.setDof(6);
        expResult=3.0;
        result=studentD.kurtosis();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of getDof method, of class StudentDistribution.
     */
    @Test
    public void testGetDof() {
        assertEquals(1, studentD.getDof());
    }
    
}
