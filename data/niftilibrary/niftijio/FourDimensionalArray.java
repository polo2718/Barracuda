package data.niftilibrary.niftijio;

/* Four-dimensional array implementation that avoids using java's multi-dimensional arrays.
        * <p/>
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
        int nx = array.length;
        int ny = array[0].length;
        int nz = array[0][0].length;
        int dim = array[0][0][0].length;

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
    
    public double[][][] get3DArray(){
         double data4D[][][] = new double [nx][ny][nz];
         int l=0;
        for (int i = 0; i < nx; i++)
        {
            for (int j = 0; j < ny; j++)
            {
                for (int k = 0; k < nz; k++)
                {
                    data4D[i][j][k]=get(i,j,k,l) ;
                }
            }
        }
     return data4D;   
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
  
    
    public int sizeX() {return nx;}
    public int sizeY() {return ny;}
    public int sizeZ() {return nz;}
    public int dimension() {return dim;}
}
