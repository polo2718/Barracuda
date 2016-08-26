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
 * @author
 * <p>
 * Diego Garibay-Pulido 2016</p>
 */
public class StatisticalMomentTest {
    
    public StatisticalMomentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of mean method, of class StatisticalMoment.
     */
    @Test
    public void testMean_0args() {
        System.out.println("Mean: Cyclic implementation");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        StatisticalMoment instance = new StatisticalMoment();
        for(int i=0;i<x.length;i++){
            instance.accumulate(x[i]);
        }
        double expResult = 6.3077;
        double result = instance.mean();
        assertEquals(expResult, result, 0.0001);
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of mean method, of class StatisticalMoment.
     */
    @Test
    public void testMean_doubleArr() {
        System.out.println("Mean: Static Method");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        double expResult = 6.3077;
        double result = StatisticalMoment.mean(x);
        assertEquals(expResult, result, 0.0001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of variance method, of class StatisticalMoment.
     */
    @Test
    public void testVariance_0args() {
        System.out.println("Variance: Cyclic implementation");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        StatisticalMoment instance = new StatisticalMoment();
        for(int i=0;i<x.length;i++){
            instance.accumulate(x[i]);
        }
        double expResult = 31.8974;
        double result = instance.variance();
        assertEquals(expResult, result, 0.0001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of variance method, of class StatisticalMoment.
     */
    @Test
    public void testVariance_doubleArr() {
        System.out.println("Variance: Static Method");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        double expResult = 31.8974;
        double result = StatisticalMoment.variance(x);
        assertEquals(expResult, result, 0.0001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of std method, of class StatisticalMoment.
     */
    @Test
    public void testStd_0args() {
        System.out.println("Std: Cyclic implementation");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        StatisticalMoment instance = new StatisticalMoment();
        for(int i=0;i<x.length;i++){
            instance.accumulate(x[i]);
        }
        double expResult = 5.6478;
        double result = instance.std();
        assertEquals(expResult, result, 0.0001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of std method, of class StatisticalMoment.
     */
    @Test
    public void testStd_doubleArr() {
        System.out.println("Std: Static Method");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        double expResult = 5.6478;
        double result = StatisticalMoment.std(x);
        assertEquals(expResult, result, 0.0001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of skewness method, of class StatisticalMoment.
     */
    @Test
    public void testSkewness_0args() {
        System.out.println("Skewness: Cyclic implementation");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        StatisticalMoment instance = new StatisticalMoment();
        for(int i=0;i<x.length;i++){
            instance.accumulate(x[i]);
        }
        double expResult = 1.701439989134833;
        double result = instance.skewness();
        assertEquals(expResult, result, 0.000000000000001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
        double moment2=Math.sqrt(instance.getCentralMoment(2));
        double moment3=instance.getCentralMoment(3);
        double r=moment3/(moment2*moment2*moment2);
        System.out.println("R="+r);
    }

    /**
     * Test of skewness method, of class StatisticalMoment.
     */
    @Test
    public void testSkewness_doubleArr() {
        System.out.println("Skewness: Static Method");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        double expResult = 1.701439989134833;
        double result = StatisticalMoment.skewness(x);
        assertEquals(expResult, result, 0.000000000000001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of kurtosis method, of class StatisticalMoment.
     */
    @Test
    public void testKurtosis_0args() {
        System.out.println("Kurtosis: Cyclic implementation");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        StatisticalMoment instance = new StatisticalMoment();
        for(int i=0;i<x.length;i++){
            instance.accumulate(x[i]);
        }
        double expResult = 5.267901313619023;
        double result = instance.kurtosis();
        assertEquals(expResult, result, 0.000000000000001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of kurtosis method, of class StatisticalMoment.
     */
    @Test
    public void testKurtosis_doubleArr() {
        System.out.println("Kurtosis: Static Method");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        double expResult = 5.267901313619023;
        double result = StatisticalMoment.kurtosis(x);
        assertEquals(expResult, result, 0.000000000000001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of skewness method, of class StatisticalMoment.
     */
    @Test
    public void testSkewness_doubleArr_boolean() {
        System.out.println("Skewness: Non-bias corrected");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        boolean flag = false;
        double expResult = 1.498466443486696;
        double result = StatisticalMoment.skewness(x, flag);
        assertEquals(expResult, result, 0.000000000000001);
        // TODO review the generated test code and remove the default call to fail.
       System.out.println ("Expected ="+expResult+" Result="+result);
    }

    /**
     * Test of kurtosis method, of class StatisticalMoment.
     */
    @Test
    public void testKurtosis_doubleArr_boolean() {
        System.out.println("Kurtosis: Non-bias corrected");
        double x[] = {2,6,9,1,4,2,6,4,5,3,16,20,4};
        boolean flag = false;
        double expResult = 4.056363955345788;
        double result = StatisticalMoment.kurtosis(x, flag);
        assertEquals(expResult, result, 0.000000000000001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println ("Expected ="+expResult+" Result="+result);
    }
    
}
