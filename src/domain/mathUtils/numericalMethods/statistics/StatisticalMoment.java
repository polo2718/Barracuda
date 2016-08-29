/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.statistics;

/**
 * <p> This class calculates the statistical moments for a data array. Kurtosis and Skewness are
 * calculated using the bias-corrected formulas by default. 
 * See setBias() to change to change between bias corrected and not corrected formulas.
 * </p>
 * @author<p>Diego Garibay-Pulido 2016</p>
 * Adapted from Didier H. Besset (2002) Object-Oriented Implementation of Numerical Methods. Morgan Kauffman Publishers
 * and Press et.al (1993) Numerical Recipes. Cambridge University Press
 */
public class StatisticalMoment{
    public static final boolean BIAS_CORRECTED=true;
    public static final boolean UNCORRECTED=false;
    
    private double moments[];
    boolean biasCorrect=true;
    
     /**
      * <p>Creates a Statistical Moment object that computes the statistical moments.
      * The default value gives bias corrected moments. To change the bias, use setBias() method.</p>
      */   
    public StatisticalMoment() {
            moments=new double[5];
            reset();
    }
    
    /**
     * Gets the central statistical moment
     * @param n Order of moment
     * @return The Central Moment
     */
    public double getCentralMoment(int n) throws IllegalArgumentException{
        if(n<5 & n>=0)
            return moments[n];
        else
            return Double.NaN;
    }
    
    /**
     * Resets the statistical moment object to zero, must be done before performing new calculations
     */
    public void reset(){
        for(int i=0;i<moments.length;i++){
            moments[i]=0;
        }
    }
    
    /**
     * Inserts a new data point into the statistical moment object
     * @param x The value to insert
     */
    public void accumulate(double x){
        if(!Double.isNaN(x)){
            double n=moments[0];
            double n1=n+1;
            double n2=n*n;
            double d1=(moments[1]-x)/n1;
            double d2=d1*d1;
            double d3=d2*d1;
            double r1=(double)n/(double)n1; // -- n/n+1
            moments[4]+=(1+n*n2)*d2*d2 + 6*moments[2]*d2 + 4*moments[3]*d1;
            moments[4]*=r1;
            moments[3]+=(1-n2)*d3 + 3*moments[2]*d1;
            moments[3]*=r1;
            moments[2]+=(1+n)*d2;
            moments[2]*=r1;
            moments[1]-=d1;
            moments[0]=n1;
        }
    } 
    
    /**
     * Internal Static method that gets only the mean.
     * @param x The new value
     * @param momentsTemp the moments array
     * @return the modified moments array
     */
    private static double[] accumulateMean(double x,double momentsTemp[]){
        double n=momentsTemp[0];
        double n1=n+1;
        double d1=(momentsTemp[1]-x)/n1;
        momentsTemp[1]-=d1;
        momentsTemp[0]=n1;
        return momentsTemp;
    }
    /**
     * Internal Static method that gets only the variance
     * @param x value
     * @param momentsTemp moments array
     * @return modified moments array
     */
    private static double[] accumulateVariance(double x,double momentsTemp[]){
        double n=momentsTemp[0];
        double n1=n+1;
        double d1=(momentsTemp[1]-x)/n1;
        double d2=d1*d1;
        double r1=(double)n/(double)n1; // -- n/n+1
        momentsTemp[2]+=(1+n)*d2;
        momentsTemp[2]*=r1;
        momentsTemp[1]-=d1;
        momentsTemp[0]=n1;
        return momentsTemp;
    } 
    /**
     * Internal skewness method
     * @param x value
     * @param momentsTemp moments array
     * @return modified moments array
     */
    private static double[] accumulateSkewness(double x,double momentsTemp[]){
        double n=momentsTemp[0];
        double n1=n+1;
        double n2=n*n;
        double d1=(momentsTemp[1]-x)/n1;
        double d2=d1*d1;
        double d3=d2*d1;
        double r1=(double)n/(double)n1; // -- n/n+1
        momentsTemp[3]+=(1-n2)*d3 + 3*momentsTemp[2]*d1;
        momentsTemp[3]*=r1;
        momentsTemp[2]+=(1+n)*d2;
        momentsTemp[2]*=r1;
        momentsTemp[1]-=d1;
        momentsTemp[0]=n1;
        return momentsTemp;
    } 
    /**
     * Internal kurtosis method
     * @param x value
     * @param momentsTemp moments array
     * @return modified moments array
     */
    private static double[] accumulateKurtosis(double x,double momentsTemp[]){
        double n=momentsTemp[0];
        double n1=n+1;
        double n2=n*n;
        double d1=(momentsTemp[1]-x)/n1;
        double d2=d1*d1;
        double d3=d2*d1;
        double r1=(double)n/(double)n1; // -- n/n+1
        momentsTemp[4]+=(1+n*n2)*d2*d2 + 6*momentsTemp[2]*d2 + 4*momentsTemp[3]*d1;
        momentsTemp[4]*=r1;
        momentsTemp[3]+=(1-n2)*d3 + 3*momentsTemp[2]*d1;
        momentsTemp[3]*=r1;
        momentsTemp[2]+=(1+n)*d2;
        momentsTemp[2]*=r1;
        momentsTemp[1]-=d1;
        momentsTemp[0]=n1;
        return momentsTemp;
    } 
    
    /**
     * <p>Method that computes all the central moments from an array x.
     * Must be called before calculating any moment from an array</p>
     * @param x  The data array
     */
    public void computeMoments(double x[]){
        for(int i=0;i<x.length;i++){
            if (!Double.isNaN(x[i])) {
                accumulate(x[i]);
            }
        } 
    }
    public void computeMoments(double x[][]){
        for (double[] x1 : x) {
            for (int j = 0; j<x[0].length; j++) {
                if (!Double.isNaN(x1[j])) {
                    accumulate(x1[j]);
                }
            }
        }
    }
    
    /**
     * <p>Method that gets the arithmetic mean of the data set contained in this object.
     * Either accumulate or computeMoments must be called before this method is used
     * or the method will return 0</p>
     * @return arithmetic mean
     */
    public double mean(){
        return moments[1];
    }
    
    /**
     * Static method that gets the arithmetic mean from an array of doubles
     * @param x The data array
     * @return The arithmetic mean
     */
    public static double mean(double x[]){
        double momentsTemp[]={0,0};
        for(int i=0;i<x.length;i++){
            if (!Double.isNaN(x[i])) {
                momentsTemp=accumulateMean(x[i],momentsTemp);
            }
        }
        return momentsTemp[1];
    }
    public static double mean(double x[][]){
        double momentsTemp[]={0,0};
        for (double[] x1 : x) {
            for (int j = 0; j<x[0].length; j++) {
                if (!Double.isNaN(x1[j])) {
                    momentsTemp = accumulateMean(x1[j], momentsTemp);
                }
            }
        }
        return momentsTemp[1];
    }
    
    /**
     * <p>Method that gets the non-normalized variance of the data set contained in this object.
     * Either accumulate or computeMoments must be called before this method is used
     * or the method will return 0</p>
     * @return non-normalized variance
     */
    public double variance() throws ArithmeticException{
        if(moments[0]<2)
            return Double.NaN;
        double temp=moments[2]*moments[0];
        if(biasCorrect)
            return temp/(moments[0]-1);
        else
            return temp/moments[0];
    }
    
    /**
     * Static method to get the bias corrected sample variance from an array of values
     * @param x The array of values
     * @return The variance
     */
    public static double variance(double x[]){
        double momentsTemp[]={0,0,0};
            for(int i=0;i<x.length;i++){
                if(!Double.isNaN(x[i])){
                    momentsTemp=accumulateVariance(x[i],momentsTemp);
                }
            }
            double temp=momentsTemp[2]*momentsTemp[0];
            return temp/(momentsTemp[0]-1);
    }
    /**
     * Static method variation to give the non-bias corrected variance
     * @param x The array of values
     * @param flag Boolean to indicate bias correction
     * @return The variance
     */
    public static double variance(double x[], boolean flag){
        double momentsTemp[]={0,0,0};
            for(int i=0;i<x.length;i++){
                if(!Double.isNaN(x[i])){
                    momentsTemp=accumulateVariance(x[i],momentsTemp);
                }
            }
            double temp=momentsTemp[2]*momentsTemp[0];
            if(flag)
                return temp/(momentsTemp[0]-1);
            else 
                return temp/momentsTemp[0];
    }
    public static double variance(double x[][]){
        double momentsTemp[]={0,0,0};
            for (double[] x1 : x) {
                for (int j = 0; j<x[0].length; j++) {
                    if (!Double.isNaN(x1[j])) {
                        momentsTemp = accumulateVariance(x1[j], momentsTemp);
                    }
                }
            }
            double temp=momentsTemp[2]*momentsTemp[0];
            return temp/(momentsTemp[0]-1);
    }
    public static double variance(double x[][], boolean flag){
        double momentsTemp[]={0,0,0};
            for (double[] x1 : x) {
                for (int j = 0; j<x[0].length; j++) {
                    if (!Double.isNaN(x1[j])) {
                        momentsTemp = accumulateVariance(x1[j], momentsTemp);
                    }
                }
            }
            double temp=momentsTemp[2]*momentsTemp[0];
            if(flag)
                return temp/(momentsTemp[0]-1);
            else 
                return temp/momentsTemp[0];
    }
    
    /**
     * <p>Method to get the non-normalized st. deviation. computeMoments or accumulate
     * must be called before this method</p>
     * @return Non-normalized standard deviation
     */
    public double std(){
        return Math.sqrt(variance());
    }  
    /**
     * <p>Static method to get the st. deviation</p>
     * @param x Array of values
     * @return Standard deviation
     */
    public static double std(double x[]){
        return Math.sqrt(variance(x));
    }
    public static double std(double x[][]){
        return Math.sqrt(variance(x));
    }
    
    /**
     * Method that returns bias-corrected skewness.
     * @return Bias-corrected skewness
     * @throws ArithmeticException if data is not enough
     */
    public double skewness() throws ArithmeticException{
        if(moments[0]<3)
            return Double.NaN;
        if(biasCorrect){ //Corrected for bias
            double v=variance();
            double s=std();
            return moments[3]*moments[0]*moments[0]/(s*v*(moments[0]-1)*(moments[0]-2));
        }else{ //Not corrected for bias
            double moment2=Math.sqrt(moments[2]);
            return moments[3]/(moment2*moment2*moment2);
        }
    }
    /**
     * Static method to return Bias-corrected skewness from array
     * @param x Data Array
     * @return Bias-corrected skewness
     */
    public static double skewness(double x[]){
        double momentsTemp[]={0,0,0,0};
        if(x.length>3){
            for(int i=0;i<x.length;i++){
                if (!Double.isNaN(x[i])) {
                    momentsTemp=accumulateSkewness(x[i],momentsTemp);
                }
            }
            double n=momentsTemp[0];
            double v=variance(x);
            double s=std(x);
            return momentsTemp[3]*n*n/(s*v*(n-1)*(n-2));
        }else{
            throw new IllegalArgumentException("Check input array. At least 4 values are necessary to compute skewness");
        }
    }
    public static double skewness(double x[][]){
        double momentsTemp[]={0,0,0,0};
        if(x.length*x[0].length>3){
            for (double[] x1 : x) {
                for (int j = 0; j<x[0].length; j++) {
                    if (!Double.isNaN(x1[j])) {
                        momentsTemp = accumulateSkewness(x1[j], momentsTemp);
                    }
                }
            }
            double n=momentsTemp[0];
            double v=variance(x);
            double s=std(x);
            return momentsTemp[3]*n*n/(s*v*(n-1)*(n-2));
        }else{
            throw new IllegalArgumentException("Check input array. At least 4 values are necessary to compute skewness");
        }
    }
    /**
     * Method that returns either bias-corrected or non-corrected skewness
     * @param x Data Array
     * @param flag If flag is set to false, bias correction will NOT be performed
     * @return Skewness
     */
    public static double skewness(double x[],boolean flag){
        double momentsTemp[]={0,0,0,0};
        if(x.length>3){
            for(int i=0;i<x.length;i++){
                if (!Double.isNaN(x[i])) {
                    momentsTemp=accumulateSkewness(x[i],momentsTemp);
                }
            }
            if(flag){ //Corrected for bias
                double n=momentsTemp[0];
                double v=variance(x);
                double s=std(x);
                return momentsTemp[3]*n*n/(s*v*(n-1)*(n-2));
            }else{
                double moment2=Math.sqrt(momentsTemp[2]);
                return momentsTemp[3]/(moment2*moment2*moment2);
            }
        }else{
            throw new IllegalArgumentException("Check input array. At least 4 values are necessary to compute skewness");
        }
    }
    public static double skewness(double x[][],boolean flag){
        double momentsTemp[]={0,0,0,0};
        if(x.length*x[0].length>3){
            for (double[] x1 : x) {
                for (int j = 0; j<x[0].length; j++) {
                    if (!Double.isNaN(x1[j])) {
                        momentsTemp = accumulateSkewness(x1[j], momentsTemp);
                    }
                }
            }
            if(flag){ //Corrected for bias
                double n=momentsTemp[0];
                double v=variance(x);
                double s=std(x);
                return momentsTemp[3]*n*n/(s*v*(n-1)*(n-2));
            }else{
                double moment2=Math.sqrt(momentsTemp[2]);
                return momentsTemp[3]/(moment2*moment2*moment2);
            }
        }else{
            throw new IllegalArgumentException("Check input array. At least 4 values are necessary to compute skewness");
        }
    }
    /**
     * Method that returns bias-corrected kurtosis.
     * @return Bias-corrected kurtosis
     * @throws ArithmeticException if data is not enough
     */
    public double kurtosis() throws ArithmeticException{
        if(moments[0]<4)
             return Double.NaN;
        if(biasCorrect){
            double n=moments[0];
            double k=(n-2)*(n-3);
            double n1=n-1;
            double v=variance();
            return (moments[4]*n*n*(n+1)/(v*v*n1)-n1*n1*3)/k+3;
        }else{
            return moments[4]/(moments[2]*moments[2]);
        }
    }
    /**
     * Static method to return Bias-corrected kurtosis from array
     * @param x Data Array
     * @return Bias-corrected kurtosis
     */
    public static double kurtosis(double x[]){
        double momentsTemp[]={0,0,0,0,0};
        if(x.length>4){
            for(int i=0;i<x.length;i++){
                if (!Double.isNaN(x[i])) {
                    momentsTemp=accumulateKurtosis(x[i],momentsTemp);
                }
            }
            double n=momentsTemp[0];
            double k=(n-2)*(n-3);
            double n1=n-1;
            double v=variance(x);
            return (momentsTemp[4]*n*n*(n+1)/(v*v*n1)-n1*n1*3)/k+3;
        }else{
            throw new IllegalArgumentException("Check input array. At least 5 values are necessary to compute kurtosis");
        }
    }
    public static double kurtosis(double x[][]){
        double momentsTemp[]={0,0,0,0,0};
        if(x.length*x[0].length>4){
            for (double[] x1 : x) {
                for (int j = 0; j<x[0].length; j++) {
                    if (!Double.isNaN(x1[j])) {
                        momentsTemp = accumulateKurtosis(x1[j], momentsTemp);
                    }
                }
            }
            double n=momentsTemp[0];
            double k=(n-2)*(n-3);
            double n1=n-1;
            double v=variance(x);
            return (momentsTemp[4]*n*n*(n+1)/(v*v*n1)-n1*n1*3)/k+3;
        }else{
            throw new IllegalArgumentException("Check input array. At least 5 values are necessary to compute kurtosis");
        }
    }
    /**
     * Method that returns either bias-corrected or non-corrected kurtosis
     * @param x Data Array
     * @param flag If flag is set to false, bias correction will NOT be performed
     * @return Kurtosis
     */
    public static double kurtosis(double x[],boolean flag){
        double momentsTemp[]={0,0,0,0,0};
        if(x.length>4){
            for(int i=0;i<x.length;i++){
                if (!Double.isNaN(x[i])) {
                    momentsTemp=accumulateKurtosis(x[i],momentsTemp);
                }
            }
            if(flag){
                double n=momentsTemp[0];
                double k=(n-2)*(n-3);
                double n1=n-1;
                double v=variance(x);
                return (momentsTemp[4]*n*n*(n+1)/(v*v*n1)-n1*n1*3)/k+3;
            }
            else{
                 return momentsTemp[4]/(momentsTemp[2]*momentsTemp[2]);
            }
        }else{
            throw new IllegalArgumentException("Check input array. At least 5 values are necessary to compute kurtosis");
        }
    }
    public static double kurtosis(double x[][],boolean flag){
        double momentsTemp[]={0,0,0,0,0};
        if(x.length*x[0].length>4){
            for (double[] x1 : x) {
                for (int j = 0; j<x[0].length; j++) {
                    if (!Double.isNaN(x1[j])) {
                        momentsTemp = accumulateKurtosis(x1[j], momentsTemp);
                    }
                }
            }
            if(flag){
                double n=momentsTemp[0];
                double k=(n-2)*(n-3);
                double n1=n-1;
                double v=variance(x);
                return (momentsTemp[4]*n*n*(n+1)/(v*v*n1)-n1*n1*3)/k+3;
            }
            else{
                 return momentsTemp[4]/(momentsTemp[2]*momentsTemp[2]);
            }
        }else{
            throw new IllegalArgumentException("Check input array. At least 5 values are necessary to compute kurtosis");
        }
    }
    /**
     * Setter method that changes the bias behavior of the object
     * @param flag options StatisticalMoment.BIAS_CORRECTED or StatisticalMoment.UNCORRECTED
     */
    public void setBias(boolean flag){
        biasCorrect=flag;
    }
}
