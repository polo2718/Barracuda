/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering;

import static domain.imaging.spatialfiltering.LinearSpatialFilter.CONVOLUTION;
import static domain.imaging.spatialfiltering.LinearSpatialFilter.CORRELATION;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class LinearSpatialFilterTest {
    
    public LinearSpatialFilterTest() {
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
     * Test of filter method, of class LinearSpatialFilter.
     */
    @Test
    public void testFilter() {
        double[][] array1= {
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,1,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0}
        };
        double [][] h={{1,2,3},
                       {4,5,6},
                       {7,8,9}
        };
        
        Kernel w=new Kernel(h);
        System.out.println("Odd Convolution");
        LinearSpatialFilter filter= new LinearSpatialFilter(CONVOLUTION);
        double[][] resultingArray=filter.filter(array1, w);
        for (double[] resultingArray1 : resultingArray) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                System.out.printf(" %3.0f", resultingArray1[j]);
            }
            System.out.println("");
        }
        
        System.out.println("\nOdd Correlation");
        filter.changeFilterOperation(CORRELATION);
        resultingArray=filter.filter(array1, w);
        for (double[] resultingArray1 : resultingArray) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                System.out.printf(" %3.0f", resultingArray1[j]);
            }
            System.out.println("");
        }
        System.out.println("\n\n");
        double[][] r={{1,2},
        {3,4}
        };
        w=new Kernel(r);
        
        System.out.println("Even Convolution");
        filter.changeFilterOperation(CONVOLUTION);
        resultingArray=filter.filter(array1, w);
        for (double[] resultingArray1 : resultingArray) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                System.out.printf(" %3.0f", resultingArray1[j]);
            }
            System.out.println("");
        }
        
        System.out.println("\nEven Correlation");
        filter.changeFilterOperation(CORRELATION);
        resultingArray=filter.filter(array1, w);
        for (double[] resultingArray1 : resultingArray) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                System.out.printf(" %3.0f", resultingArray1[j]);
            }
            System.out.println("");
        }
    }

    
}
