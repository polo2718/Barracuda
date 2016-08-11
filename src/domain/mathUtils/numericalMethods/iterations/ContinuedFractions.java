/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.iterations;
import domain.mathUtils.numericalMethods.GenericMathDefinitions;
import domain.mathUtils.numericalMethods.interfaces.OneVariableFunction;

/**
 * This abstract class provides a framework to compute a function defined by a continued fraction.
 * <p> The computation of a continued fraction is performed by applying a recursive method known as the modified Lentz method.
 * (proposed by Thompson and Barnett)
 * <p> The computation follows a general iterative process 
 * <p> The continued fraction defines a function. Therefore the subclasses of this framework should implement the 
 * {@link OneVariableFunction} interface
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * Adapted from Didier H. Besset (2002) Object-Oriented Implementation of Numerical Methods. Morgan Kauffman Publishers
 */
public abstract class ContinuedFractions extends IterativeProcess {
    
    /**
     * Approximation for the continued fraction
     */
    private double result;
    
    /**
     * Continued fraction argument
     */
    protected double argument;
    
    /**
     * c_n element of the first auxiliary series proposed in the Lent'z method 
     */
    private double c_n;
    
    /**
     * d_n element of the second auxiliary series proposed in the Lent'z method
     */
    private double d_n;
    
    /**
     * a_n and b_n Coefficients for the continued fraction representation of the function f(x)=b_0+a_1/(b_1+(a_2/b_2+...)).
     * <p>fractionCoeff[0] contains the a_n coefficient
     * <p>fractionCoeff[1] contains the b_n coefficient
     */
    protected double [] fractionCoeff= new double[2];
    
    /**
     * Computes the value of nth a_n and b_n coefficients.
     * The computed values are stored in {@link ContinuedFractions#fractionCoeff}
     * @param n nth iteration
     */
    protected abstract void compute_N_fractionCoeff(int n);
    
    /**
     * Computes the value of the first b_n term
     * @return value of the first term in the series 
     */
    protected abstract double initialValue();
    
    @Override
    public void initializeIterations(){
        //compute the initial values for  b_n coefficients
        double b_n=limitedSmallValue(initialValue());
        c_n=b_n;
        d_n=0; 
        result=c_n;
    }
    
    /**
     * Evaluates the current iteration by performing the modified Lentz method to compute the continued fraction value.
     * @return absolute precision after computing the iteration
     */
    @Override
    public double evaluateIteration(){
        compute_N_fractionCoeff(super.getIterations());
        double a_n=fractionCoeff[0];
        double b_n=fractionCoeff[1];
        double delta;
        //Compute value for the auxiliary series
        c_n=limitedSmallValue(a_n/c_n+b_n);
        d_n=1/limitedSmallValue(a_n*d_n+b_n);
        delta=c_n*d_n;
        result*=delta;
        return Math.abs(delta-1);
    }

    @Override
    public void finalizeIterations(){
    }
    
    /**
     * This method provides a protection against small values proposed by Thompson and Barnett to modify Lentz method to avoid extreme errors
     * when dividing or multiplying by a very small value.
     * <p>If any value of the elements of the auxiliary series is smaller than a floor value, then
     * that value is adjusted to the floor value {@link GenericMathDefinitions#smallNumber}
     * @param r value to limit
     * @return limited small value
     */
    private double limitedSmallValue(double r){
        return Math.abs(r)<GenericMathDefinitions.smallNumber()
                ?GenericMathDefinitions.smallNumber(): r;
    }
    /**
     * Sets the infinite series argument
     * @param argument series argument
     */
    public void setArgument(double argument){
        this.argument=argument;
    }
    
    /**
     * Returns the series estimate after performing the iterative process
     * @return series estimate after performing the iterative process 
     */
    public double getResult() {
        return result;
    }
}
