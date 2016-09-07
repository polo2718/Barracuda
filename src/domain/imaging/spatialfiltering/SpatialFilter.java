/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public interface SpatialFilter {
    public abstract double[][] filter(double[][] array, Kernel w);
    public abstract double[][] filter(double [][] array,Kernel w,double [][] mask);
    public abstract double[][] doubleFilter(double[][] array1, double[][] array2, Kernel w);
    public abstract double[][] doubleFilter(double[][] array1, double[][] array2, Kernel w,double[][] mask);
    
}
