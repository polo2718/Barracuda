/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.imaging.spatialfiltering;
import data.niftilibrary.niftijio.FourDimensionalArray;
import data.niftilibrary.niftijio.*;
import domain.imaging.spatialfiltering.operations.NonLinearFilterOperation;
import domain.imaging.spatialfiltering.operations.UnpairedtTest;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class NiftiNonLinearSpatialFilter {
    
    private NonLinearSpatialFilter filter;
    public NiftiNonLinearSpatialFilter(NonLinearFilterOperation operation){
        filter = new NonLinearSpatialFilter(operation);
    }
    
    /**
     * For non-linear filters; Implements a non-linear operation that involves two arrays in the neighborhood w.
     * @param array1 The first array
     * @param array2 The second array
     * @param w The neighborhood kernel
     * @param dims The dimensions in which to perform the operations
     * @return A FourDimensionalArray the same size as the input array with the result of the operation
     */
    public  FourDimensionalArray doubleFilter(FourDimensionalArray array1, FourDimensionalArray array2, Kernel w, int[] dims) throws IllegalArgumentException{
        if(array1.sizeX()==array2.sizeX() &
           array1.sizeY()==array2.sizeY() &
           array1.sizeZ()==array2.sizeZ() &
           array1.dimension()==array2.dimension())
        {
            double [][] tempArray;
            FourDimensionalArray result=new FourDimensionalArray(array1.sizeX(),array1.sizeY(),array1.sizeZ(),array1.dimension());
            for(int i=0;i<dims.length;i++){
                for(int j=0;j<array1.sizeZ();j++){
                    tempArray=filter.doubleFilter(array1.getSlice(dims[i], j), array2.getSlice(dims[i], j), w);
                    result.setSlice(tempArray,dims[i],j);
                }
            }
            return result;
        }else{
            throw new IllegalArgumentException("All Arrays must be the same size");
        }
    }
    /**
     * Performs a doubleFilter operation in the positions where the mask is not zero
     * @param array1 First Array
     * @param array2 Second Array
     * @param w Kernel w
     * @param mask Mask for performing operations
     * @param dims Vector with the dimensions (4th dimensions) in which to perform the filtering
     * @return The result of the operation
     */
    public FourDimensionalArray doubleFilter(FourDimensionalArray array1, FourDimensionalArray array2, Kernel w, FourDimensionalArray mask, int[] dims) throws IllegalArgumentException{
        if(array1.sizeX()==array2.sizeX() &
           array1.sizeY()==array2.sizeY() &
           array1.sizeZ()==array2.sizeZ() &
           array1.dimension()==array2.dimension())
        {
            if(array1.sizeX()==mask.sizeX() &
                array1.sizeY()==mask.sizeY() &
                array1.sizeZ()==mask.sizeZ() &
                array1.dimension()==mask.dimension())
             {
                double [][] tempArray;
                FourDimensionalArray result=new FourDimensionalArray(array1.sizeX(),array1.sizeY(),array1.sizeZ(),array1.dimension());
                for(int i=0;i<dims.length;i++){
                    for(int j=0;j<array1.sizeZ();j++){
                        tempArray=filter.doubleFilter(array1.getSlice(dims[i], j), array2.getSlice(dims[i], j), w,mask.getSlice(dims[i], j));
                        result.setSlice(tempArray,dims[i],j);
                    }
                }
                return result;
             }else{
                 throw new IllegalArgumentException("All Arrays must be the same size");
             }
        }else{
            throw new IllegalArgumentException("All Arrays must be the same size");
        }
    }
    
   
    
}


