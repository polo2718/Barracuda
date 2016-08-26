/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.statistics;

/**
 * <p> This package calculates the statistical moments. Kurtosis and Skewness are
 * calculated using the bias-corrected formulas by default. 
 * See changeBiasFormula(boolean flag) for changing the formula to the non-bias corrected one
 * </p>
 * @author<p>Diego Garibay-Pulido 2016</p>
 */
public class StatisticalMoment{
    protected double moments[];
    boolean biasCorrect=true;
    
     /**
      * Creates a Statistical Moment object that computes the statistical moments
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
    public double getCentralMoment(int n) throws IllegalArgumentException{
        if(n<5 & n>=0)
            return moments[n];
        else
            return Double.NaN;
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
    
    private static double[] accumulateMean(double x,double momentsTemp[]){
        double n=momentsTemp[0];
        double n1=n+1;
        double d1=(momentsTemp[1]-x)/n1;
        momentsTemp[1]-=d1;
        momentsTemp[0]=n1;
        return momentsTemp;
    }
    
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
    
    public void computeMoments(double x[]){
        for(int i=0;i<x.length;i++)
            accumulate(x[i]);
    }
    
    public double mean(){
        return moments[1];
    }
    
    public static double mean(double x[]){
        double momentsTemp[]={0,0};
        for(int i=0;i<x.length;i++){
            momentsTemp=accumulateMean(x[i],momentsTemp);
        }
        return momentsTemp[1];
    }
    
    public double variance() throws ArithmeticException{
        if(moments[0]<2)
            return Double.NaN;
        double temp=moments[2]*moments[0];
        return temp/(moments[0]-1);
    }
    
    public static double variance(double x[]){
        double momentsTemp[]={0,0,0};
        if(x.length>2){
            for(int i=0;i<x.length;i++){
                momentsTemp=accumulateVariance(x[i],momentsTemp);
            }
            double temp=momentsTemp[2]*momentsTemp[0];
            return temp/(momentsTemp[0]-1);
        }else{
            throw new IllegalArgumentException("Check input array. At least 3 values are necessary to compute variance");
        }
    }
    
    public double std(){
        return Math.sqrt(variance());
    }
    
    public static double std(double x[]){
        return Math.sqrt(variance(x));
    }
    
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
    
    public static double skewness(double x[]){
        double momentsTemp[]={0,0,0,0};
        if(x.length>3){
            for(int i=0;i<x.length;i++){
                momentsTemp=accumulateSkewness(x[i],momentsTemp);
            }
            double n=momentsTemp[0];
            double v=variance(x);
            double s=std(x);
            return momentsTemp[3]*n*n/(s*v*(n-1)*(n-2));
        }else{
            throw new IllegalArgumentException("Check input array. At least 4 values are necessary to compute skewness");
        }
    }
    
    public static double skewness(double x[],boolean flag){
        double momentsTemp[]={0,0,0,0};
        if(x.length>3){
            for(int i=0;i<x.length;i++){
                momentsTemp=accumulateSkewness(x[i],momentsTemp);
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
    
    public static double kurtosis(double x[]){
        double momentsTemp[]={0,0,0,0,0};
        if(x.length>4){
            for(int i=0;i<x.length;i++){
                momentsTemp=accumulateKurtosis(x[i],momentsTemp);
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
    
    public static double kurtosis(double x[],boolean flag){
        double momentsTemp[]={0,0,0,0,0};
        if(x.length>4){
            for(int i=0;i<x.length;i++){
                momentsTemp=accumulateKurtosis(x[i],momentsTemp);
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
}
