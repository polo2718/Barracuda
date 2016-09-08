/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering;

/**
 * Performs a "comparison" spatial filter where the resulting array is the product of performing an operation on the input arrays 
 * within the neighborhood defined by a Kernel
 * @author Diego Garibay-Pulido 2016
 */
public interface DoubleSpatialFilter {
    /**
 * For use with non-linear spatial filters, performs an operation with 2 arrays on the neighborhood w
 * Performs the operation for each element in the array
 * @param array1 Array 1
 * @param array2 Array 2 (Must be the same size as Array 1)
 * @param w Kernel that defines the neighborhood
 * @return 2D array as a result of the filtering
 */
    public abstract double[][] doubleFilter(double[][] array1, double[][] array2, Kernel w);
    /**
     * Performs doubleFilter with a mask: Values where mask equals zero are assigned NaN values
     * @param array1 First array
     * @param array2 Second Array
     * @param w Kernel that defines the neighborhood
     * @param mask Mask: Values equal to zero are assigned NaN
     * @return Result of the filtering
     */
    public abstract double[][] doubleFilter(double[][] array1, double[][] array2, Kernel w,double[][] mask);
}
