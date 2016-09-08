/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering;

/**
 * Interface for spatial filters
 * @author Diego Garibay-Pulido 2016
 */
public interface SpatialFilter {
    /**
     * Filters a two dimensional array with the Kernel w
     * @param array 2D array
     * @param w Kernel w
     * @return A 2D array with the result of the filtering
     */
    public abstract double[][] filter(double[][] array, Kernel w);
    /**
     * Filters a two dimensional array with the Kernel w where the mask is not zero
     * @param array 2D array
     * @param w Kernel w
     * @param mask Mask, where mask equals zero a value of NaN is assigned
     * @return 2D array as a result of the filtering
     */
    public abstract double[][] filter(double [][] array,Kernel w,double [][] mask);
    
    
}
