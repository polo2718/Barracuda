/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.imaging.spatialfiltering.operations.*;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class NonLinearSpatialFilterTest {
    
    public NonLinearSpatialFilterTest() {
    }
    

    /**
     * Test of filter method, of class NonLinearSpatialFilter.
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
        
        double[][] expResult= {
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,1.0/9,1.0/9,1.0/9,0,0,0,0},
                       {0,0,0,0,1.0/9,1.0/9,1.0/9,0,0,0,0},
                       {0,0,0,0,1.0/9,1.0/9,1.0/9,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0},
                       {0,0,0,0,0,0,0,0,0,0,0}
        };
        
        double [][] h={{1,2,3},
                       {4,5,6},
                       {7,8,9}
        };
        Kernel w= new Kernel(h);
        MeanOperation operation=new MeanOperation();//Define the filter operation
        NonLinearSpatialFilter filter= new NonLinearSpatialFilter(operation);
        double[][] resultingArray=filter.filter(array1, w);
        for (int i = 0; i<resultingArray.length; i++) {
            for (int j = 0; j<resultingArray[0].length; j++) {
                assertEquals(expResult[i][j],resultingArray[i][j],10e-8);
                System.out.printf(" %.3f", resultingArray[i][j]);
            }
            System.out.println("");
        }
    }
    
}
