/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.linearAlgebra;

import domain.mathUtils.numericalMethods.linearAlgebra.LinearAlgebra;
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
public class LinearAlgebraTest {
    
    public LinearAlgebraTest() {
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
     * Test of matrixTimesVector method, of class LinearAlgebra.
     */
    @Test
    public void testMatrixTimesVector() {
        System.out.println("matrixTimesVector");
        double[][] mat = {{3,2},{-1,1},{6,4}};
        double[] vec = {3,-1};
        double[] expResult = {7,-4,14};
        double[] result = LinearAlgebra.matrixTimesVector(mat, vec);
        
        for(int i=0;i<result.length;i++){
           assertEquals(expResult[i], result[i],0);
        }
        
       
    }

}
