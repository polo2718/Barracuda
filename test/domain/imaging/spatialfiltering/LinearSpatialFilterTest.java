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
        
        double[][] expResultOddConvolution= {
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,1,2,3,0,0,0,0},
                       {0,0,0,0,4,5,6,0,0,0,0},
                       {0,0,0,0,7,8,9,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0}
        };
        
        double[][] expResultOddCorrelation= {
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,9,8,7,0,0,0,0},
                       {0,0,0,0,6,5,4,0,0,0,0},
                       {0,0,0,0,3,2,1,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0}
        };
        
        double[][] expResultEvenConvolution= {
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,1,2,0,0,0,0},
                       {0,0,0,0,0,3,4,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0}
        };
        
        double[][] expResultEvenCorrelation= {
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,4,3,0,0,0,0},
                       {0,0,0,0,0,2,1,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0}
        };
        
        double [][] h={{1,2,3},
                       {4,5,6},
                       {7,8,9}
        };
        
        double[][] r={{1,2},
                      {3,4}
        };
        
        Kernel w=new Kernel(h);
        System.out.println("Odd Convolution");
        LinearSpatialFilter filter= new LinearSpatialFilter(CONVOLUTION);
        double[][] resultingArray=filter.filter(array1, w);
        for (int i=0;i<resultingArray.length;i++) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                assertEquals(expResultOddConvolution[i][j],resultingArray[i][j],0);
                System.out.printf(" %3.0f", resultingArray[i][j]);
            }
            System.out.println("");
        }
        
        System.out.println("\nOdd Correlation");
        filter.changeFilterOperation(CORRELATION);
        resultingArray=filter.filter(array1, w);
        for (int i=0;i<resultingArray.length;i++) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                assertEquals(expResultOddCorrelation[i][j],resultingArray[i][j],0);
                System.out.printf(" %3.0f", resultingArray[i][j]);
            }
            System.out.println("");
        }
        
        w=new Kernel(r);
        
        System.out.println("\nEven Convolution");
        filter.changeFilterOperation(CONVOLUTION);
        resultingArray=filter.filter(array1, w);
        for (int i=0;i<resultingArray.length;i++) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                assertEquals(expResultEvenConvolution[i][j],resultingArray[i][j],0);
                System.out.printf(" %3.0f", resultingArray[i][j]);
            }
            System.out.println("");
        }

        System.out.println("\nEven Correlation");
        filter.changeFilterOperation(CORRELATION);
        resultingArray=filter.filter(array1, w);
        for (int i=0;i<resultingArray.length;i++) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                assertEquals(expResultEvenCorrelation[i][j],resultingArray[i][j],0);
                System.out.printf(" %3.0f", resultingArray[i][j]);
            }
            System.out.println("");
        }
    }

    
}
