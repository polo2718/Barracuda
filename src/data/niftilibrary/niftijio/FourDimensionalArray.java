package data.niftilibrary.niftijio;

import domain.mathUtils.arrayTools.ArrayOperations;

/**
        * <p>Four-dimensional array implementation that avoids using java's multi-dimensional arrays.
        * </p>
        * For very large images, java's multi-dimensional arrays cause too much overhead and eventually
        * result in either heap or garbage collection issues. This implementation uses one single large array
        * while providing a 4D access interface.
        *
        * Method names should be self-explanatory.
        * @author Ghazi Bouabene, University of Basel, Switzerland
        *
        *
 */
public class FourDimensionalArray {

    private double[] data;
    private int nx, ny, nz, dim;

    public FourDimensionalArray(int nx, int ny, int nz, int dim) {
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
        this.dim = dim;
        this.data = new double[nx * ny * nz * dim];
    }

    public FourDimensionalArray(double[][][][] array) {
        this.nx = array.length;
        this.ny = array[0].length;
        this.nz = array[0][0].length;
        this.dim = array[0][0][0].length;

        this.data = new double[nx * ny * nz * dim];

        for (int d = 0; d < dim; d++)
            for (int k = 0; k < nz; k++)
                for (int j = 0; j < ny; j++)
                    for (int i = 0; i < nx; i++) {
                        set(i,j,k,d, array[i][j][k][d]);
                    }
    }

    public double get(int x, int y, int z, int d) {
        int idx = d * (nx * ny * nz) + z * (nx * ny) + y * nx + x;
        return data[idx];
    }

    public void set(int x, int y, int z, int d, double val) {
        int idx = d * (nx * ny * nz) + z * (nx * ny) + y * nx + x;
        data[idx] = val;
    }
    /** 
     * Additional access to retrieve a 4D data structure from the NiftiVolume
     * @author Diego Garibay-Pulido
     * @return 4D array with nifti image data
     */
    public double[][][][] get4DArray(){
         double data4D[][][][] = new double [nx][ny][nz][dim];
        for (int i = 0; i < nx; i++)
        {
            for (int j = 0; j < ny; j++)
            {
                for (int k = 0; k < nz; k++)
                {
                    for (int l = 0; l < dim; l++) {
                       data4D[i][j][k][l]=get(i,j,k,l) ;
                    }
                }
            }
        }
     return data4D;   
    }
    
    /**
     * Overwrite the image information in the nifti volume object
     * @author Diego Garibay-Pulido
     * @param array A 4D array containing the image information
     */
    public void set4DArray(double [][][][] array){
        if((nx == array.length) & ( ny == array[0].length)& (nz == array[0][0].length) &  (dim == array[0][0][0].length))
        {
            for (int d = 0; d < dim; d++)
                for (int k = 0; k < nz; k++)
                    for (int j = 0; j < ny; j++)
                        for (int i = 0; i < nx; i++) {
                            set(i,j,k,d, array[i][j][k][d]);
                        }
        }
        else{
            System.err.println("Error: Array must be the same size as the original image");
        }
    }
    
    public double[][][] get3DArray(int dimension){
         double data3D[][][] = new double [nx][ny][nz];
        for (int i = 0; i < nx; i++)
        {
            for (int j = 0; j < ny; j++)
            {
                for (int k = 0; k < nz; k++)
                {
                    data3D[i][j][k]=get(i,j,k,dimension) ;
                }
            }
        }
     return data3D;   
    }
    
    public void set3DArray(double [][][] array){
        if((nx == array.length) & ( ny == array[0].length)& (nz == array[0][0].length))
        {   
            int d=1;
                for (int k = 0; k < nz; k++)
                    for (int j = 0; j < ny; j++)
                        for (int i = 0; i < nx; i++) {
                            set(i,j,k,d, array[i][j][k]);
                        }
        }
        else{
            System.err.println("Error: Array must be the same size as the original image");
        }
    }
    
    /**
     * Gets a two-dimensional slice in the axial plane, for processing purposes
     * @param dimension Fourth dimension number
     * @param sliceNum slice number
     * @return The 2D axial slice
     */
    public double[][] getSlice(int dimension, int sliceNum){
        int idx;
        double [][] slice = new double[nx][ny];
        for (int i = 0; i < nx; i++){
            for (int j = 0; j < ny; j++){
                idx=dimension*(nx*ny*nz)+sliceNum*(nx*ny)+j*nx+i;
                slice[i][j]= data[idx];
            }
        }
        return slice;
    }
    /**
     * Sets the data on a two-dimensional slice in the axial plane, for processing purposes
     * @param slice A 2D array of the same dimensions as the slice
     * @param dimension The dimension
     * @param sliceNum The slice number
     */
    public void setSlice(double [][] slice, int dimension, int sliceNum){
        if(slice.length==nx & slice[0].length==ny){
        int idx;
            for (int i = 0; i < nx; i++){
                for (int j = 0; j < ny; j++){
                    idx=dimension*(nx*ny*nz)+sliceNum*(nx*ny)+j*nx+i;
                    data[idx]=slice[i][j];
                }
            }
        }
    }
    
    public double get2DArrayMax(int dimension,String plane, int sliceNum){
        double max;
        int idx;
        switch(plane){
            case"coronal":
                max=0.0;
                for (int i = 0; i < nx; i++)
                {
                    for (int j = 0; j < nz; j++)
                    {
                        idx=dimension*(nx*ny*nz)+j*(nx*ny)+sliceNum*nx+i;
                        if(max<data[idx]){
                            max=data[idx];
                        }
                    }
                }
                break;
            case"saggital":
                max=0.0;
                for (int i = 0; i < ny; i++)
                {
                    for (int j = 0; j < nz; j++)
                    {
                        idx=dimension*(nx*ny*nz)+j*(nx*ny)+i*nx+sliceNum;
                        if(max<data[idx]){
                            max=data[idx];
                        }
                    }
                }
                break;
            default: //Default is axial
                max=0.0;
                for (int i = 0; i < nx; i++)
                {
                    for (int j = 0; j < ny; j++)
                    {
                        idx=dimension*(nx*ny*nz)+sliceNum*(nx*ny)+j*nx+i;
                        if(max<data[idx]){
                            max=data[idx];
                        }
                    }
                }
                break;
        }
        return max;              
    }
  
    public int nonZeroCount(){
        return ArrayOperations.nonZeroCount(data);
    }
    
    public int numericCount(){
        return ArrayOperations.lengthActualVal(data);
    }
    public int sizeX() {return nx;}
    public int sizeY() {return ny;}
    public int sizeZ() {return nz;}
    public int dimension() {return dim;}
}
