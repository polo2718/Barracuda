/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.linearAlgebra;

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
public class MatrixTest {
    
    private Matrix matrixA;
    
    public MatrixTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        double[][] components = {{3,2},{-1,1},{6,4}};
        matrixA= new Matrix(components);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of rows method, of class Matrix.
     */
    @Test
    public void testRows() {
        int expResult=3;
        int result=matrixA.rows();
        assertEquals(expResult, result);
    }

    /**
     * Test of columns method, of class Matrix.
     */
    @Test
    public void testColumns() {
        int expResult=2;
        int result=matrixA.columns();
        assertEquals(expResult, result);
    }

    /**
     * Test of product method, of class Matrix.
     */
    @Test
    public void testProduct_Vector() throws Exception {
        System.out.println("\nMatrix times vector Test");
        double [] vComponents={3,-1};
        double [] expComponents={7,-4,14};
        Vector vector= new Vector(vComponents);
        Vector expResult=new Vector(expComponents);
        Vector result=matrixA.product(vector);
        assertEquals(expResult, result);
        System.out.println("Test passed!!!");
    }


    /**
     * Test of product method, of class Matrix.
     */
    @Test
    public void testProduct_Matrix() throws Exception {
    }
    
}
