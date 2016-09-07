/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.statistics;

/**
 * Limited version of the StatisticalMoment class, provides a faster implementation 
 * if only mean, variance and Standard deviation are required, does not support static methods or
 * skewness and kurtosis
 * @author Diego Garibay-Pulido 2016
 */
public class LimitedStatisticalMoment extends StatisticalMoment{
    public LimitedStatisticalMoment(){
        moments=new double[3];
        reset();
    }
    /**
     * Inserts a new data point into the statistical moment object
     * @param x The value to insert
     */
    @Override
    public void accumulate(double x){
        if(!Double.isNaN(x)){
            double n=moments[0];
            double n1=n+1;
            double d1=(moments[1]-x)/n1;
            double d2=d1*d1;
            double r1=(double)n/(double)n1; // -- n/n+1
            moments[2]+=(1+n)*d2;
            moments[2]*=r1;
            moments[1]-=d1;
            moments[0]=n1;
        }
    }
    
    @Override
    public double getCentralMoment(int n) throws IllegalArgumentException{
        if(n<3 & n>=0)
            return moments[n];
        else
            return Double.NaN;
    }
    
    @Override
    public double skewness() throws ArithmeticException{
        throw new UnsupportedOperationException("Use StatisticalMoment class instead");
    }
     @Override
     public double kurtosis() throws ArithmeticException{
        throw new UnsupportedOperationException("Use StatisticalMoment class instead");
    }
}
