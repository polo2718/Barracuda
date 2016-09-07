/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering.operations;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public interface NonLinearFilterOperation {
    
    public abstract double operate(double[][] array);
    public abstract double operate(double[][] array1,double [][] array2);
}
