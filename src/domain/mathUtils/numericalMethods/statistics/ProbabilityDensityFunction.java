/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.statistics;
import domain.mathUtils.numericalMethods.GenericMathDefinitions;
import domain.mathUtils.numericalMethods.functionEvaluation.OneVariableFunction;
import domain.mathUtils.numericalMethods.rootFinding.NewtonFinder;

/**
 * This class defines the general methods for a probability density function of a random variable.
 *<p>A probability density function describes the likelihood  of finding the continuous random variable within an interval.
 *<p>A probability density function is a function of one variable.
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * @see OneVariableFunction
 */
public abstract class ProbabilityDensityFunction implements OneVariableFunction {
    
    /**
     * Returns the value of the Probability Density function evaluated at the random variable x.
     * <p> Computes the probability of finding the continuous random variable at the specified point
     * <p> A subclass of ProbabilityDensityFunction should override this method
     * @param x value of the random variable
     * @return Value of the probability density function
     */
    @Override
    public abstract double value(double x);
    
    /**
     * Returns the value of the cumulative distribution function of the Probability density function.
     * <p> This method computes the probability of finding the random variable between (-Inf,t]
     * That is: cumulativeValue(t)=Prob(x smaller or equal than t)
     * <p> It is equal to the integral from -Inf to t of the probability distribution function
     * <p> Also called repartition function or acceptance function.
     * <p> A subclass of ProbabilityDensityFunction should override this method.
     * @param t number
     * @return Probability of finding t between (-Inf,t]
     */
    public abstract double cumulativeValue(double t);
    
    /**
     * Returns the value of the cumulative distribution function of the Probability density function.
     * <p> This method computes the probability of finding the random variable between [a,b]
     * That is: cumulativeValue(a,b)=Prob(x greater or equal than a AND x smaller or equal than b)
     * <p> It is equal to the integral from a to b of the probability distribution function
     * <p> Also called repartition function or acceptance function.
     * @param a Lower bound 
     * @param b Upper bound
     * @return Prob(x greater or equal than a AND x smaller or equal than b)
     */
    public double cumulativeValue(double a, double b){
        return cumulativeValue(b)-cumulativeValue(a);
    }
    
    /**
     * Returns the value (t) for which the given probability of finding the random variable is in (-Inf, t] 
     * <p> This method is also known as the inverse cumulative distribution function
     * <p> The value is computed using Newton's method for finding roots
     * @param prob probability of finding the random variable in the interval (-Inf, t]
     * @return value t
     */
    public double inverseCumulativeValue(double prob){
        //define function
        OneVariableFunction f=new OneVariableFunction() {
            @Override
            public double value(double x) throws IllegalArgumentException {
                return ProbabilityDensityFunction.this.cumulativeValue(x)-prob;
            }
        };
        //Perform newton algorithm
        NewtonFinder newton= new NewtonFinder(f, this, average());
        newton.setDesiredPrecision(GenericMathDefinitions.defaultNumericalPrecision());
        newton.evaluate();
        return newton.getResult();
    }
    
    /**
     * Returns the average of the distribution
     * <p> A subclass of ProbabilityDensityFunction should override this method
     * @return Average of the distribution
     */
    public abstract double average();
    
    /**
     * Returns the variance of the distribution
     * <p> A subclass of ProbabilityDensityFunction should override this method
     * @return variance of the distribution
     */
    public abstract double variance();
    
   /**
    * Returns the standard deviation of the distribution 
    * <p> A subclass of ProbabilityDensityFunction should override this method
    * @return standard deviation
    */
    public double standardDeviation(){
        return Math.sqrt(variance());
    }
    
    /**
     * Returns the value of the skewness of the distribution
     * <p> A subclass of ProbabilityDensityFunction should override this method
     * @return returns the value of the skewness of the distribution
     */
    public abstract double skewness();
    
    /**
     * Returns the value of the kurtosis of the distribution
     * <p> A subclass of ProbabilityDensityFunction should override this method
     * @return kurtosis 
     */
    public abstract double kurtosis();
}
