/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.linearAlgebra;

import java.util.Arrays;

/**
 * This class defines a vector object and provides useful methods for performing vector
 * operations.
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class Vector {
    /**
     * Vector components
     */
    protected double components[];
    /**
     * Constructor
     * @param components 1D double array containing the components for the vector
     * @throws NegativeArraySizeException 
     */
    public Vector(double components[]) throws NegativeArraySizeException{
        if(components.length<=0){
            throw new NegativeArraySizeException("Vector components cannot be empty");
        }
        this.components=components;
    }
    /**
     * Return the vector components
     * @return 1D array containing the value of the vector components
     */
    public double[] getComponents(){
        return components;
    }
    
    /**
     * This method returns the value of the nth vector component
     * @param n nth component
     * @return The value of the nth component
     * @throws IllegalArgumentException 
     */
    public double getComponent(int n) throws IllegalArgumentException{
        if(n<0 || n>components.length)
            throw new IllegalArgumentException("The specified component does not exist "
                    + "in the component array");
        return components[n];
    }
    /**
     * Returns the vector dimensionality
     * @return vector dimension (number of components)
     */
    public int dimension(){
        return components.length;
    }
    
    /**
     * Performs the scalar product (u dot v) between this vector (u) and the provided vector (v).
     * @param v Vector with which the scalar product should be performed
     * @return scalar product
     * @throws IllegalDimensionException To perform an scalar product, u and v should have the same dimensionality 
     */
    public double scalarProduct(Vector v) throws IllegalDimensionException{
        if(components.length!=v.dimension())
            throw new IllegalDimensionException("Dot product between vectors of diferrent dimensions "+
                    components.length+" and " + v.dimension());
        return secureScalarProduct(v);
    }
    /**
     * Performs the scalar product (u dot v) between this vector (u) and the provided vector (v) without checking for dimensionality problems
     * @param v Vector with which the scalar product should be performed
     * @return scalar product
     */
    protected double secureScalarProduct(Vector v){
        double result=0;
        for(int i=0; i<=this.dimension();i++)
            result+=this.components[i]*v.components[i];
        return result;
    }
    
    /**
     * Tests if a vector object is equivalent to this vector 
     * @param other Vector
     * @return true if the vectors are equal
     */
    @Override
    public boolean equals(Object other){
        if (other == null) 
            return false;
        if (other == this) 
            return true;
        if (!(other instanceof Vector))
            return false;
        int n= this.dimension();
        int o=((Vector)other).dimension();
        if( o!= n)
            return false;
        double [] otherComp=((Vector)other).components;
        for(int i=0; i<n;i++){
            if (otherComp[i]!=this.components[i])
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Arrays.hashCode(this.components);
        return hash;
    }
            
}
