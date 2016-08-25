/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.statistics;

/**
 *  
 * @author
 * <p>
 * Diego Garibay-Pulido 2016</p>
 */
public class StatisticalMoment{
    protected double moments[];
    
     /**
      * Creates a Statistical Moment object that Statistical Moments
      */   
    
    public StatisticalMoment() {
            moments=new double[5];
            reset();
    }
    
    /**
     * Gets the central statistical moment
     * @param n
     * @return 
     */
    public double getMoment(int n) {
        return moments[n];
    }
    
    public void setOrder(int n) throws IllegalArgumentException{
       if(n<0){
            throw new IllegalArgumentException("Invalid moment order");   
        }else{
            moments=new double[n];
            reset();
        }
    }
    
    public int getOrder(){
        return moments.length;
    }
    
    public void reset(){
        for(int i=0;i<moments.length;i++){
            moments[i]=0;
        }
    }
    
    public void accumulate(double x){
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
    
    public void computeMoments(double x[]){
        for(int i=0;i<x.length;i++)
            accumulate(x[i]);
    }
    
    public double mean(){
        return moments[1];
    }
    
    public double mean(double x[]){
        computeMoments(x);
        return mean();
    }
    
    public double variance() throws ArithmeticException{
        if(moments[0]<2)
            return Double.NaN;
        double temp=moments[2]*moments[0];
        return temp/(moments[0]-1);
    }
    
    public double variance(double x[]){
        computeMoments(x);
        return variance();
    }
    
    public double std(){
        return Math.sqrt(variance());
    }
    
    public double std(double x[]){
        return Math.sqrt(variance(x));
    }
    
    public double skewness() throws ArithmeticException{
        if(moments[0]<3)
            return Double.NaN;
        double v=variance();
        double s=std();
        return moments[3]*moments[0]*moments[0]/(s*v*(moments[0]-1)*(moments[0]-2));
        
    }
    
    public double skewness(double x[]){
        computeMoments(x);
        return skewness();
    }
    
    public double kurtosis() throws ArithmeticException{
        if(moments[0]<4)
             return Double.NaN;
        double n=moments[0];
        double k=(n-2)*(n-3);
        double n1=n-1;
        double v=variance();
        
        return (moments[4]*n*n*(n+1)/(v*v*n1)-n1*n1*3)/k+3;
    }
    
    public double kurtosis(double x[]){
        computeMoments(x);
        return kurtosis();
    }
}
