/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering.operations;

import domain.mathUtils.numericalMethods.statistics.StatisticalMoment;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class MeanOperation implements NonLinearFilterOperation {

    public MeanOperation(){
        
    }
    @Override
    public double operate(double[][] array) {
        StatisticalMoment moment= new StatisticalMoment();
        for (double[] array1 : array) {
            for (int j = 0; j<array[0].length; j++) {
                moment.accumulate(array1[j]);
            }
        }
        return moment.mean();
    }

    @Override
    public double operate(double[][] array1, double[][] array2) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
}
