/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation.exceptions;

/**
 * Thrown when trying to evaluate beta function for the wrong number of independent variables 
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */
public class BetaFunctionException extends Exception {
    /**
     * Constructor
     */
    public BetaFunctionException(){
        super("The input array has wrong number of variables. The input array needs to have"
                + "two input independent variables");
    }
    /**
     * Constructor
     * @param message for the exception 
     */
    public BetaFunctionException(String message){
        super(message);
    }
    
}
