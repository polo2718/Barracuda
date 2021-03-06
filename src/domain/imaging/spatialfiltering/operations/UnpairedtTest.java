/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering.operations;

import domain.mathUtils.numericalMethods.statistics.*;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class UnpairedtTest implements NonLinearFilterOperation{
    private static  StudentDistribution distribution=new StudentDistribution();
    public static final boolean LEFT_TAIL=true;
    public static final boolean RIGHT_TAIL=false;
    private boolean tail;
    public UnpairedtTest(){
    }
    public UnpairedtTest(boolean flag){
        this.tail=flag;
    }
    
    @Override
    public double operate(double[][] array) {
        throw new UnsupportedOperationException(
                "Two arrays are required for a two sample t-test."); 
    }
    /**
     * Performs a two-sample unpaired t-test between two arrays and returns the probability 
     * value of the resulting t-statistic
     * @param array1 array1
     * @param array2 array2
     * @return returns the left side p-value for the result of the t-test
     */
    @Override
    public double operate(double[][] array1, double[][] array2) {
        double p_value;
        if(array1.length==array2.length & array1[0].length==array2[0].length){//Check dimensions
            StatisticalMoment moment1=new StatisticalMoment();//Create statistical moment object
            StatisticalMoment moment2=new StatisticalMoment();//Create statistical moment object
            for(int i=0;i<array1.length;i++){
                for(int j=0;j<array1[0].length;j++){
                    moment1.accumulate(array1[i][j]);
                    moment2.accumulate(array2[i][j]);
                }
            }
            int n1= (int) moment1.getCentralMoment(0);
            int n2= (int) moment2.getCentralMoment(0);
            int dof=n1+n2-2;
            if(dof<1){
                p_value=Double.NaN; //If the test cannot be performed return NaN
            }else{
                double mx=moment1.mean()-moment2.mean();
                
                double s1= moment1.variance();
                double s2= moment2.variance();
                double sx= ((n1-1)*s1+(n2-1)*s2)/((double)dof);//Variance
                if(sx==0){ 
                    if(mx==0){
                        // When Variance is zero the distribution aproximates a delta
                        // Since s=0 and M=0 then the null hypothesis is always true
                        // therefore the p value is always 1
                        p_value=1;
                    }else{
                        if(mx>0){
                            p_value=1;
                        }else{
                            p_value=0;
                        }
                        // If Variance is zero and mean is different than zero
                        // there is a uniform difference between two arrays. Thus
                        // making the null hypothesis always false (Pb = 0)
                    }
                }else{
                    double t=mx/Math.sqrt(sx*(1.0/n1+1.0/n2));//
                    distribution.setDof(dof);
                    p_value=distribution.cumulativeValue(t);
                }
                if(!tail){ //If tail equals right tail
                        p_value=1-p_value;
                }
            }
        }else{
            throw new IllegalArgumentException("Array dimension mismatch");
        }
        return p_value;
    }
    
    public void setTail(boolean flag){
        tail=flag;
    }
}
