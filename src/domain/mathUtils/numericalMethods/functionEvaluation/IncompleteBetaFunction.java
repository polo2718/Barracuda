/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.functionEvaluation;
import domain.mathUtils.numericalMethods.interfaces.MultiVariableFunction;

/**
 * Provides the method to compute the Incomplete Beta Function beta(x,alpha1, alpha2)
 * <P> This function is approximated through a continued fraction expansion.
 * The incomplete Gamma function is a Multivariable function.
 * @see IncompleteBetaFractions
 * @see MultiVariableFunction
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 */

public class IncompleteBetaFunction implements MultiVariableFunction{
    
    private double alpha1;
    private double alpha2;
    private double x;
    private static final IncompleteBetaFractions fraction=new IncompleteBetaFractions();
    private static final BetaFunction betaFunction=new BetaFunction();
    
    /**
     * Returns the value of the incompleteBeta function beta(x,alpha1, alpha2) when computed with a continued fraction approximation
     * @param variables 1D array containing the three independent variables x, alpha1 and alpha2
     * <p> variables[0]=x
     * <p> variables[1]=alpha1
     * <p> variables[2]=alpha2
     * @return value of the incompleteBeta function fraction evaluated at F(x,alpha1, alpha2)
     * @throws IllegalArgumentException an exception is thrown when the input array containing the 2 independent variables has the wrong dimensionality
     * The continued fraction converges when x is greater than(alpha1+1)/(alpha1+alpha2+2).

     */
    @Override
    public double value(double[] variables) throws Exception {
        /*
        The continued fraction converges when x>(alpha1+1)/(alpha1+alpha2+2)
        When the fraction does not converge, the incomplete Beta function should be calculated as
        beta(x, alpha1, alpha2)= 1-beta(1-x,alpha2, alpha1)
        two special values are also considered:
        * beta() when x=0 should be evaluated to zero
        * beta() when x=1 should be evaluated to 1
        */
        if(variables.length!=3){
              throw new IllegalArgumentException("Check input array. Wrong number of independent variables were provided."
               + " IncompleteGamma function is defined for only three independent variables"
               + " x, alpha1 and alpha2");
          }
        x=variables[0];
        alpha1=variables[1];
        alpha2=variables[2];
        //special value
        if(x==0)
           return 0;
        //special value
        if(x==1)
           return 1;
        double a[]={alpha1, alpha2};//auxiliar variable for computing beta function
        double logBeta=betaFunction.logValue(a);
        double factor=Math.exp(alpha1*Math.log(x)+alpha2*Math.log(1-x)
                   -logBeta);
        if( x*(alpha1+alpha2+2) < (alpha1+1) ){
            return factor/(fraction.value(variables)*alpha1);
        }
        else{
           variables[0]=1-x;
           variables[1]=alpha2;
           variables[2]=alpha1;
           return 1-factor/(fraction.value(variables)*alpha2);
        }
    }
}
