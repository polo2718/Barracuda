/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.iterations;

/**
 *This abstract class provides the general framework to perform any iterative process.
 * Any specific algorithm that performs an iterative process should be a subclass.
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * Adapted from Didier H. Besset (2002) Object-Oriented Implementation of Numerical Methods. Morgan Kauffman Publishers
 */
public abstract class IterativeProcess {
    /**
     * Number of iterations that have been performed by the algorithm (Initialize at zero)
     */
    private int iterations=0;
    
    /**
     * Number of Maximum Iterations allowed for the algorithm (Default is 50)
     */
    private int maximumIterations=50;
    
    /**
     * Precision that has been achieved by the particular algorithm.
     */
    private double precision;
    
    /**
     * Desired precision that should be attained after the algorithm execution.
     */
    private double desiredPrecision=0;
    
    /**
     * Performs the iterative method
     */
    public void evaluate(){
        initializeIteration();
        while(iterations++<maximumIterations){
            //perform next iteration
            precision=evaluateIteration();
            // end the method if the algorithm has already converged
            if(hasConverged())
                break;
        }
        finalizeIteration();
    }
    
    /**
     * Constructor
     */
    public IterativeProcess(){
    }
    
    /**
     * Defines necessary initial parameters/conditions to start a specific iterative process. 
     */
    public abstract void initializeIteration();
    
    /**
     * A subclass should compute the result of the current iteration according to each specific algorithm  
     * @return  relative precision of the result after performing the iteration
     */
    public abstract double evaluateIteration();
    
    /**
     * Releases the resources that were used by the iterative process. 
     */
    public abstract void finalizeIteration();
    
    /**
     * Test if the algorithm has converged or attained the desired precision 
     * @return true if the algorithm has converged or produced the desired precision.
     */
    public boolean hasConverged(){
        return precision<desiredPrecision;
    }

    /**
     * Get the current number of iterations that have been performed
     * @return number of performed iterations
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Get the number of Maximum Iterations that are allowed to be performed,
     * @return number of maximum iterations
     */
    public int getMaximumIterations() {
        return maximumIterations;
    }

    /**
     * Sets the number of Maximum Iterations that are allowed to be performed.
     * @param maximumIterations 
     */
    public void setMaximumIterations(int maximumIterations) {
        this.maximumIterations = maximumIterations;
    }

    /**
     * Gets the precision of the solution at the current iteration
     * @return current precision of the solution
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * Gets the value of the desired precision that should be attained by the iterative process.
     * @return  value of the desired precision
     */
    public double getDesiredPrecision() throws IllegalArgumentException {
        return desiredPrecision;
    }
    
    /**
     * Sets the desired precision that should be attained by the iterative process.
     * @param desiredPrecision 
     */
    public void setDesiredPrecision(double desiredPrecision) {
        this.desiredPrecision = desiredPrecision;
    }
    
}
