/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering.operations;

/**
 * Interface that is used when applying a non-linear filter to a two dimensional array
 * Implementations of this interface must be used when instantiating a new non-linear filter
 * @author Diego Garibay-Pulido 2016
 */
public interface NonLinearFilterOperation {
    /**
     * Performs a given operation on an array and returns the result of the operation
     * @param array 2D double array that is passed with each neighborhood
     * @return The value resulting from the operation
     */
    public abstract double operate(double[][] array);
    /**
     * Performs an operation involving two equal sized arrays and returns the value
     * @param array1 First array
     * @param array2 Second array
     * @return The result of the operation
     */
    public abstract double operate(double[][] array1,double [][] array2);
    
}
