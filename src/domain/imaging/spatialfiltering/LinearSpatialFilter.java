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
public class LinearSpatialFilter implements SpatialFilter {
    @Override
    public double[][] filter(double[][] array,Kernel w){
        return new double[2][2];
    }
    
    
}
