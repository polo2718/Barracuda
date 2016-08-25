/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.statistics;


/**
 *
 * @author
 * <p>Diego Garibay-Pulido 2016</p>
 */
public interface Moment {
    /**
     * This function sets the Order of the Moment
     * @param n Order
     */
    public void setOrder(int n) throws IllegalArgumentException;
    /**
     * Get the order of the moment
     * @return 
     */
    public int getOrder();
    /**
     * Function that returns the value of the moment given a dataset x
     * @param x The data set
     * @return The value of the moment
     */
    public double getMoment(int n);
    
}
