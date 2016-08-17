/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.linearAlgebra;

/**
 * This class defines a matrix object and provides useful methods for performing vector
 * operations.
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class Matrix {
    /**
     * Matrix components
     */
    protected double [][] components;
    
    /**
     * Constructor
     * @param components 2D array containing matrix components
     */
    public Matrix (double[][] components){
        this.components=components;
    }
    /**
     * Returns the number of rows in the matrix
     * @return number of rows in the matrix
     */
    public int rows(){
        return components.length;
    }
    
    /**
     * Returns the number of columns in a matrix
     * @return number of columns in a matrix
     */
    public int columns(){
        return components[0].length;
    }
    
    /**
     * Performs the product between a matrix (A) and a vector(v) the result is a vector(u). u=Av
     * @param v Vector by which the matrix is going to be multiplied
     * @return Vector object (u) containing the result of the product
     * @throws IllegalDimensionException To perform a product between a matrix A and a vector v, The number of columns in A should be the 
     * same as the number of columns in 
     * @see Vector
     */
    public Vector product(Vector v) throws IllegalDimensionException{
        int r=this.rows();
        int c=this.columns();
        if(v.dimension()!=c)
            throw new IllegalDimensionException("Product error " + r + "x" +c
                    +" Matrix cannot be multiplied with a vector of dimensions "
                    +v.dimension());
        return secureProduct(v);
    }
    
    /**
     * Performs the product between a matrix (A) and a vector(v) the result is a vector(u). u=Av 
     * This method performs the product without checking for dimensionality problems,
     * @param v Vector by which the Matrix is going to be multiplied
     * @return Vector object (u) containing the result of the product
     * @see Vector
     * @author Diego Garibay, Leopoldo Cendejas
     */
    protected Vector secureProduct(Vector v){
        //initialize the array containing the output vector components
        double [] vectorComp= new double [this.rows()];
        //Perform multiplication
        for(int i=0; i<this.rows();i++){
            for(int j=0; j<this.columns();j++){
                vectorComp[i]+=this.components[i][j]*v.components[j];
            }
        }
        return new Vector(vectorComp);
    }
    
    /**
     * Performs the product between a matrix A and a matrix B. The result is another matrix C=AB
     * @param b Matrix b
     * @return resulting matrix C
     * @throws IllegalDimensionException The inner dimensions between the matrices should be the same to be able to perform a matrix multiplication
     */
    public Matrix product(Matrix b) throws IllegalDimensionException{
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
