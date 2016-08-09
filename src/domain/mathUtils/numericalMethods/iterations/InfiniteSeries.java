/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.iterations;

/**
 * This class provides a framework to compute an infinite series. 
 * <p>Computation of a series is performed by adding sequentially term by term.</p>
 * <p>When the series term has a lower value than the desired precision, then the computation should finish.
 * Thus the series computation can follow a general iterative process
 * <p>Each subclass should be able to retrieve specific elements of the series by implementing the abstract methods.
 * <p>A series is a function. Therefore the subclasses of this framework should implement the oneVariable function interface
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see IterativeProcess
 * Adapted from Didier H. Besset (2002) Object-Oriented Implementation of Numerical Methods. Morgan Kauffman Publishers
 */
public abstract class InfiniteSeries extends IterativeProcess {
    
    /**
     * Approximation for the sum
     */
    private double result;
    
    /**
     * Argument for the series
     */
    private double argument;
    
    /**
     * Value of the last evaluated term in the series
     */
    private double lastTerm;
    
    /**
     * Computes the nth term of the series. The computed values is stored in
     * {@link InfiniteSeries#lastTerm}
     * @param n - nth
     */
    protected abstract void compute_N_Term (int n);
    
    /**
     * Computes the value of the first term of the series
     * @return value of the first term in the series 
     */
    protected abstract double initialValue();
    
    /**
     * Initialize the iterative process by computing the first term of the series 
     * {@link InfiniteSeries#initialValue()}
     */
    
    @Override
    public void initializeIterations(){
        this.result=initialValue();
    }
    
    /**
     * Evaluates the current iteration by adding to the result the next term of the series
     * This method updates the {@link InfiniteSeries#result} attribute with the new computed result
     * @return precision relative to the result after computing the iteration
     */
    @Override
    public double evaluateIteration(){
        compute_N_Term(super.getIterations());
        this.result+=lastTerm;
        return super.relativePrecision(Math.abs(lastTerm), Math.abs(result));
    }
    
    /**
     * Sets the infinite series argument
     * @param argument 
     */
    public void setArgument(double argument){
        this.argument=argument;
    }
}
