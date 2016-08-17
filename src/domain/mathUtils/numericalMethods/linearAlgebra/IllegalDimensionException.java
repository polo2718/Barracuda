/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.linearAlgebra;

/**
 * This exception should be thrown when a calculation with illegal dimensions is attempted.
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class IllegalDimensionException extends Exception{

    public IllegalDimensionException() {
    }

    public IllegalDimensionException(String message) {
        super(message);
    }
    
}
