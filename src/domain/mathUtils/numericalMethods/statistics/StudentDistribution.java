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
        setDof(dof);
    }
    
    /**
     * General constructor for defining a StudentDistribution with one DOF
     */
    public StudentDistribution(){
        setDof(1);
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
        //Parameters for the incomplete Beta Function
        double [] p= new double [3];
        double t2=t*t;
        double result;
        if(t>=0){
            p[0]=t2/(t2+n);
            p[1]=0.5;
            p[2]=0.5*n;
            result=0.5*(1+incompleteBeta.value(p));
        }
        else{
            p[0]=n/(n+t2);
            p[1]= 0.5*n;
            p[2]= 0.5;
            result=0.5*incompleteBeta.value(p);;
        }
        return result;
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
