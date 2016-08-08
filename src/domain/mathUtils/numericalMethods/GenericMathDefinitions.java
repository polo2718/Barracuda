/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods;

/**
 * This class is used to define several default constants that are used in numerical methods regarding precision and floating point representation 
 * @author "Leopoldo Cendejas-Zaragoza, 2016, Illinois Institute of Technology"
 * Adapted from Didier H. Besset (2002) Object-Oriented Implementation of Numerical Methods. Morgan Kauffman Publishers
 * and Press et.al (1993) Numerical Recipes. Cambridge University Press
 */
public class GenericMathDefinitions {
    /**
     * Radix for machine number representation
     */
    private static double radix=0;
    
    /**
     *Largest positive number that when added 1 yields 1 due to floating point arithmetic error. 
     *@see GenericMathDefinitions#computeMachinePrecision() 
     */
    private static double machinePrecision=0;
    
    /**
     * Default numerical precision used for numerical calculation involving floating point arithmetic.
     * Defined as the square root of the machine precision.
     * @see GenericMathDefinitions#defaultNumericalPrecision()
     */
    private static double defaultNumericalPrecision=0;

    /**
     * Constructor
     */
    public GenericMathDefinitions() {
    }
    
    /**
     * Gets the default numerical precision for a numerical method, it is defined as the square root of the machine precision
     * @return default numerical precision
     * @see GenericMathDefinitions#getMachinePrecision() 
     * @see GenericMathDefinitions#computeMachinePrecision()
     */
    public static double defaultNumericalPrecision(){
        if(defaultNumericalPrecision==0){
            defaultNumericalPrecision=Math.sqrt(getMachinePrecision());
        }
        return defaultNumericalPrecision;
    }
    
    /**
     * Returns the Machine Precision number
     * @return machine precision
     * @see GenericMathDefinitions#computeMachinePrecision() 
     */
    public static double getMachinePrecision() {
        if(machinePrecision==0)
            computeMachinePrecision();
        return machinePrecision;
    }
    
    /**
     * This method computes the machine precision of the current system by performing a loop.
     * The attribute machinePrecision is updated after running this method.
     * @see GenericMathDefinitions#machinePrecision
     * @see GenericMathDefinitions#radix
     */
    public static void computeMachinePrecision(){
        double inverseRadix=1.0d/getRadix();
        machinePrecision=1.0d;
        double tmp=1.0d+machinePrecision;
        while(tmp-1.0d != 0.0d){
            machinePrecision*=inverseRadix;
            tmp= 1.0d+machinePrecision;
        }
    }
    
    /**
     * Returns the radix
     * @return radix for machine number representation
     */
    public static double getRadix() {
        if(radix==0)
            computeRadix();
        return radix;
    }
    
    /**
     * This method computes the radix of the machine number representation
     * The attribute radix is updated after running this method.
     * @see radix
     */
    public static void computeRadix(){
        double a=1.0d;
        double tmp1;
        double tmp2;
        do {
            a+=a;
            tmp1=a+1.0d;
            tmp2=tmp1-a;
        } while (tmp2-1.0d != 0.0d);
        double b=1.0d;
        while(radix==0){
            b+=b;
            tmp1=a+b;
            radix=(int)(tmp1-a);
        }
    }
}
