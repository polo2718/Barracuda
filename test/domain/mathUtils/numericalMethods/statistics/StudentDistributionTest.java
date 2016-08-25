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
        System.out.println("\n Student dstribution value test");
        int dof=15;
        studentD.setDof(dof);
        System.out.println("DOF= " +dof);
        double x=6;
        double expResult=0.000021970797512769418;
        double result=studentD.value(x);
        System.out.println("Exp Result=" +expResult);
        System.out.println("Result=" +result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed!!");
        
        dof=1000;
        studentD.setDof(dof);
        x=-31;
        result=studentD.value(x);
        expResult=1.643485442151604E-147;
        assertEquals(expResult, result, 10e-8);
        
        dof=3;
        studentD.setDof(dof);
        x=3;
        result=studentD.value(x);
        expResult=0.02297203730924134;
        assertEquals(expResult, result, 10e-8);       
        
    }

    /**
     * Test of cumulativeValue method, of class StudentDistribution.
     */
    @Test
    public void testCumulativeDistributionValue() {
        System.out.println("\nCumulative Density Student distribution Test 1");
        //15 degrees of freedom
        int dof=15;
        studentD.setDof(dof);
        System.out.println("DOF= " + dof);
        double expResult=0.0250212523871212;
        double t=-2.131;
        System.out.println("Probability of having a random variable <=" +t);
        double result=studentD.cumulativeValue(t);
        System.out.println("Exp Result="+expResult);
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-9);
        System.out.println("Test passed");
        
        System.out.println("\nCumulative Density Student distribution Test 2");
        //15 degrees of freedom
        dof=15;
        studentD.setDof(dof);
        System.out.println("DOF= " + dof);
        expResult=0.974978747612879;
        t=2.131;
        System.out.println("Probability of having a random variable <=" +t);
        result=studentD.cumulativeValue(t);
        System.out.println("Exp Result="+expResult);
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed");
        
        System.out.println("\nCumulative Density Student distribution Test 3");
        //15 degrees of freedom
        dof=80;
        studentD.setDof(dof);
        System.out.println("DOF= " + dof);
        expResult=0.899995937394251;
        t=1.2922;
        System.out.println("Probability of having a random variable <=" +t);
        result=studentD.cumulativeValue(t);
        System.out.println("Exp Result="+expResult);
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed"); 
        
        System.out.println("\nCumulative Density Student distribution Test 4");
        //15 degrees of freedom
        dof=80;
        studentD.setDof(dof);
        System.out.println("DOF= " + dof);
        expResult=1-0.899995937394251;
        t=-1.2922;
        System.out.println("Probability of having a random variable <=" +t);
        result=studentD.cumulativeValue(t);
        System.out.println("Exp Result="+expResult);
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed");  
        
        System.out.println("\nCumulative Density Student distribution Test 5");
        //15 degrees of freedom
        dof=1;
        studentD.setDof(dof);
        System.out.println("DOF= " + dof);
        expResult=0.900009618274457;
        t=3.078;
        System.out.println("Probability of having a random variable <=" +t);
        result=studentD.cumulativeValue(t);
        System.out.println("Exp Result="+expResult);
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-8);
        System.out.println("Test passed");
        
        System.out.println("\nCumulative Density Student distribution Test 5");
        //15 degrees of freedom
        dof=1;
        studentD.setDof(dof);
        System.out.println("DOF= " + dof);
        expResult=1-0.900009618274457;
        t=-3.078;
        System.out.println("Probability of having a random variable <=" +t);
        result=studentD.cumulativeValue(t);
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
    
    @Test
    public void testInverseCumulativeValue(){
        System.out.println("\nInverse Cumulative distribution Test 1");
        int dof=15;
        studentD.setDof(dof);
        System.out.println("DOF= " + dof);
        double expResult=-2.13099999539;
        double prob=0.0250212523871212;
        //double prob=0.0250212523871212;
        System.out.println("Cumulative probability value=" +prob);
        double result=studentD.inverseCumulativeValue(prob);
        System.out.println("Exp Result="+expResult);
        System.out.println("Result="+result);
        assertEquals(expResult, result, 1e-9);
        System.out.println("Test passed");    
    }
}
