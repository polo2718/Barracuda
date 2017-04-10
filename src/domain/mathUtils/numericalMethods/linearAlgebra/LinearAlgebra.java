/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.mathUtils.numericalMethods.linearAlgebra;

/**
 *
 * @author
 * <p>
 * Diego Garibay-Pulido 2016</p>
 */
@Deprecated
public abstract class LinearAlgebra {
    @Deprecated
    public static double[] matrixTimesVector(double[][] mat,double[] vec){
        double result[]=new double[mat.length];
        if(mat[0].length==vec.length){
            for(int i=0;i<mat.length;i++){
                result[i]=0;
                for(int j=0;j<mat[0].length;j++){
                    result[i]=result[i]+vec[j]*mat[i][j];
                }
            }
            return result;
        }
        else{
            System.out.println("Incompatible dimensions");
            return null;
        }
    }
    
    
    
}
