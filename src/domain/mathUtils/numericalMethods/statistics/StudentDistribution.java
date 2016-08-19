/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.statistics;
import domain.mathUtils.numericalMethods.functionEvaluation.BetaFunction;
import domain.mathUtils.numericalMethods.functionEvaluation.IncompleteBetaFunction;

/**
 * This class defines the Student's distribution and provides general methods for using it in diverse statistical analysis
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public final class StudentDistribution extends ProbabilityDensityFunction {
    
    private static final BetaFunction beta=new BetaFunction();
    private static final IncompleteBetaFunction incompleteBeta= new IncompleteBetaFunction();
    
    /**
     * Degrees of freedom
     */
    private int dof;
    private double n;
    
    /**
     * General constructor for the StudentDistribution
     * @param dof degrees of freedom
     * @throws IllegalArgumentException when the supplied dof is less than one
     */
    public StudentDistribution(int dof) throws IllegalArgumentException{
        if (dof<1)
            throw new IllegalArgumentException("A Student's distribution cannot be defined for"
                    + " DOF<1\n Supplied DOF = " +dof);
        this.dof=dof;
        this.n=dof;
    }
    
    @Override
    public double value(double x) {
        double result;
        //parameters for beta Function
        double [] parameters=new double [2];
        parameters [0]= n/2.0;
        parameters [1]= 0.5;
        result=-0.5*(n+1)*Math.log(1+x*x/n)-beta.logValue(parameters);
        result=Math.exp(result)/Math.sqrt(n);
        return result;
    }

    @Override
    public double cumulativeDensityValue(double t) {
        double result;
        //Parameters for the incomplete beta function
        double [] parameters= new double [3];
        double t_2=t*t;
       
        if(t<=0){
            parameters[0]=n/(t_2+n);
            parameters[1]=n*0.5;
            parameters[2]=0.5;
            result=0.5*incompleteBeta.regularizedValue(parameters);
        }
        else{
            parameters[0]=t_2/(t_2+n);
            parameters[1]=0.5;
            parameters[2]=0.5*n;
            result=0.5*(incompleteBeta.regularizedValue(parameters)+1);
        }
        return result;
        //Parameters for the incomplete Beta Function
        /*
        double [] parameters= new double [3];
        parameters[0]=n/(n+t*t);
        parameters[1]= 0.5*n;
        parameters[2]= 0.5;
       
        double result= incompleteBeta.value(parameters);
        if(t>=0){
            result+=1;
            result=result/2;
        }
        else{
            result=(1-result)/2;
        }
     
        return result;
        */
    }

    @Override
    public double average() {
        return 0;
    }

    @Override
    public double variance() {
        return dof>2 ?n/(n-2) : Double.NaN;
    }

    @Override
    public double skewness() {
        return 0;
    }

    @Override
    public double kurtosis() {
        return n>4 ? 6/(n-4) : Double.NaN;
    }
    
    /**
     * Returns the Degrees of freedom of the current distribution
     * @return degrees of freedom
     */
    public int getDof(){
        return dof;
    }
    
    /**
     * Set the degrees of freedom of the T distribution
     * @param dof degrees of freedom
     * @throws IllegalArgumentException when the supplied dof is less than one
     */
    public void setDof(int dof) throws IllegalArgumentException{
        if (dof<1)
            throw new IllegalArgumentException("A Student's distribution cannot be defined for"
                    + " DOF<1\n Supplied DOF = " +dof);
        this.dof=dof;
        this.n=dof;
    }
    
}
