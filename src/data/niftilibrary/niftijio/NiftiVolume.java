package data.niftilibrary.niftijio;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.awt.image.*;
import domain.mathUtils.arrayTools.ArrayOperations;
import domain.mathUtils.linearAlgebra.LinearAlgebra;
import user.gui.tools.UITools;


public class NiftiVolume
{
    public NiftiHeader header;
    public FourDimensionalArray data;
    public double scale[];
    public char[] orient;
    private String orientation;
    private double max;
    private double min;

    public NiftiVolume(int nx, int ny, int nz, int dim)
    {
        this.header = new NiftiHeader(nx, ny, nz, dim);
        this.data = new FourDimensionalArray(nx,ny,nz,dim);
    }

    public NiftiVolume(NiftiHeader hdr)
    {
        this.header = hdr;

        int nx = hdr.dim[1];
        int ny = hdr.dim[2];
        int nz = hdr.dim[3];
        int dim = hdr.dim[4];

        if (hdr.dim[0] == 2)
            nz = 1;
        if (dim == 0)
            dim = 1;

        this.data = new FourDimensionalArray(nx,ny,nz,dim);
    }

    public NiftiVolume(double[][][][] data)
    {
        this.data = new FourDimensionalArray(data);
    }

    public static NiftiVolume read(String filename) throws IOException {
        NiftiHeader hdr = NiftiHeader.read(filename);
        
        InputStream is = new FileInputStream(filename);
        if (hdr.filename.endsWith(".gz"))
            is = new GZIPInputStream(is);
        try {
            return read(new BufferedInputStream(is), hdr); 
        } finally {
            is.close();
        }
        
    }

    /** Read the NIFTI volume from a NIFTI input stream.
     * 
     * @param is an input stream pointing to the beginning of the NIFTI file, uncompressed.
     * @return a NIFTI volume
     * @throws IOException if read fails
     */
    public static NiftiVolume read(InputStream is) throws IOException {
        return read(is, null);
    }

    /** Read the NIFTI volume from a NIFTI input stream.
     * 
     * @param is an input stream pointing to the beginning of the NIFTI file, uncompressed. The operation will close the stream.
     * @param filename the name of the original file, can be null
     * @return a NIFTI volume
     * @throws IOException if read fails
     */
    public static NiftiVolume read(InputStream is, String filename) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        try {
            bis.mark(2048);
            NiftiHeader hdr = NiftiHeader.read(bis, filename);
            bis.reset();
            return read(bis, hdr);
        } finally {
            bis.close();
        }
    }

    public static NiftiVolume read(BufferedInputStream is, NiftiHeader hdr) throws IOException {
        // skip header
        is.skip((long) hdr.vox_offset);

        int nx = hdr.dim[1];
        int ny = hdr.dim[2];
        int nz = hdr.dim[3];
        int dim = hdr.dim[4];

        if (hdr.dim[0] == 2)
            nz = 1;
        if (dim == 0)
            dim = 1;

        NiftiVolume out = new NiftiVolume(hdr);
        DataInput di = hdr.little_endian ? new LEDataInputStream(is) : new DataInputStream(is);

        for (int d = 0; d < dim; d++)
        {
            for (int k = 0; k < nz; k++)
                for (int j = 0; j < ny; j++)
                    for (int i = 0; i < nx; i++)
                    {
                        double v;

                        switch (hdr.datatype)
                        {
                        case NiftiHeader.NIFTI_TYPE_INT8:
                        case NiftiHeader.NIFTI_TYPE_UINT8:
                            v = di.readByte();

                            if ((hdr.datatype == NiftiHeader.NIFTI_TYPE_UINT8) && v < 0)
                                v = v + 256d;
                            if (hdr.scl_slope != 0)
                                v = v * hdr.scl_slope + hdr.scl_inter;
                            break;
                        case NiftiHeader.NIFTI_TYPE_INT16:
                        case NiftiHeader.NIFTI_TYPE_UINT16:
                            v = (double) (di.readShort());

                            if ((hdr.datatype == NiftiHeader.NIFTI_TYPE_UINT16) && (v < 0))
                                v = Math.abs(v) + (double) (1 << 15);
                            if (hdr.scl_slope != 0)
                                v = v * hdr.scl_slope + hdr.scl_inter;
                            break;
                        case NiftiHeader.NIFTI_TYPE_INT32:
                        case NiftiHeader.NIFTI_TYPE_UINT32:
                            v = (double) (di.readInt());
                            if ((hdr.datatype == NiftiHeader.NIFTI_TYPE_UINT32) && (v < 0))
                                v = Math.abs(v) + (double) (1 << 31);
                            if (hdr.scl_slope != 0)
                                v = v * hdr.scl_slope + hdr.scl_inter;
                            break;
                        case NiftiHeader.NIFTI_TYPE_INT64:
                        case NiftiHeader.NIFTI_TYPE_UINT64:
                            v = (double) (di.readLong());
                            if ((hdr.datatype == NiftiHeader.NIFTI_TYPE_UINT64) && (v < 0))
                                v = Math.abs(v) + (double) (1 << 63);
                            if (hdr.scl_slope != 0)
                                v = v * hdr.scl_slope + hdr.scl_inter;
                            break;
                        case NiftiHeader.NIFTI_TYPE_FLOAT32:
                            v = (double) (di.readFloat());
                            if (hdr.scl_slope != 0)
                                v = v * hdr.scl_slope + hdr.scl_inter;
                            break;
                        case NiftiHeader.NIFTI_TYPE_FLOAT64:
                            v = (double) (di.readDouble());
                            if (hdr.scl_slope != 0)
                                v = v * hdr.scl_slope + hdr.scl_inter;
                            break;
                        case NiftiHeader.DT_NONE:
                        case NiftiHeader.DT_BINARY:
                        case NiftiHeader.NIFTI_TYPE_COMPLEX64:
                        case NiftiHeader.NIFTI_TYPE_FLOAT128:
                        case NiftiHeader.NIFTI_TYPE_RGB24:
                        case NiftiHeader.NIFTI_TYPE_COMPLEX128:
                        case NiftiHeader.NIFTI_TYPE_COMPLEX256:
                        case NiftiHeader.DT_ALL:
                        default:
                            throw new IOException("Sorry, cannot yet read nifti-1 datatype " + NiftiHeader.decodeDatatype(hdr.datatype));
                        }

                        out.data.set(i,j,k,d,v);
                    }
        }

        return out;
    }

    public void write(String filename) throws IOException
    {
        NiftiHeader hdr = this.header;
        hdr.filename = filename;

        int dim = hdr.dim[4] > 0 ? hdr.dim[4] : 1;
        int nz = hdr.dim[3] > 0 ? hdr.dim[3] : 1;
        int ny = hdr.dim[2];
        int nx = hdr.dim[1];

        OutputStream os = new BufferedOutputStream(new FileOutputStream(hdr.filename));
        if (hdr.filename.endsWith(".gz"))
            os = new BufferedOutputStream(new GZIPOutputStream(os));

        DataOutput dout = (hdr.little_endian) ? new LEDataOutputStream(os) : new DataOutputStream(os);

        byte[] hbytes = hdr.encodeHeader();
        dout.write(hbytes);

        int nextra = (int) hdr.vox_offset - hbytes.length;
        byte[] extra = new byte[nextra];
        dout.write(extra);

        for (int d = 0; d < dim; d++)
            for (int k = 0; k < nz; k++)
                for (int j = 0; j < ny; j++)
                    for (int i = 0; i < nx; i++)
                    {
                        double v = this.data.get(i,j,k,d);

                        switch (hdr.datatype)
                        {
                        case NiftiHeader.NIFTI_TYPE_INT8:
                        case NiftiHeader.NIFTI_TYPE_UINT8:
                            if (hdr.scl_slope == 0)
                                dout.writeByte((int) v);
                            else
                                dout.writeByte((int) ((v - hdr.scl_inter) / hdr.scl_slope));
                            break;
                        case NiftiHeader.NIFTI_TYPE_INT16:
                        case NiftiHeader.NIFTI_TYPE_UINT16:
                            if (hdr.scl_slope == 0)
                                dout.writeShort((short) (v));
                            else
                                dout.writeShort((short) ((v - hdr.scl_inter) / hdr.scl_slope));
                            break;
                        case NiftiHeader.NIFTI_TYPE_INT32:
                        case NiftiHeader.NIFTI_TYPE_UINT32:
                            if (hdr.scl_slope == 0)
                                dout.writeInt((int) (v));
                            else
                                dout.writeInt((int) ((v - hdr.scl_inter) / hdr.scl_slope));
                            break;
                        case NiftiHeader.NIFTI_TYPE_INT64:
                        case NiftiHeader.NIFTI_TYPE_UINT64:
                            if (hdr.scl_slope == 0)
                                dout.writeLong((long) Math.rint(v));
                            else
                                dout.writeLong((long) Math.rint((v - hdr.scl_inter) / hdr.scl_slope));
                            break;
                        case NiftiHeader.NIFTI_TYPE_FLOAT32:
                            if (hdr.scl_slope == 0)
                                dout.writeFloat((float) (v));
                            else
                                dout.writeFloat((float) ((v - hdr.scl_inter) / hdr.scl_slope));
                            break;
                        case NiftiHeader.NIFTI_TYPE_FLOAT64:
                            if (hdr.scl_slope == 0)
                                dout.writeDouble(v);
                            else
                                dout.writeDouble((v - hdr.scl_inter) / hdr.scl_slope);
                            break;
                        case NiftiHeader.DT_NONE:
                        case NiftiHeader.DT_BINARY:
                        case NiftiHeader.NIFTI_TYPE_COMPLEX64:
                        case NiftiHeader.NIFTI_TYPE_FLOAT128:
                        case NiftiHeader.NIFTI_TYPE_RGB24:
                        case NiftiHeader.NIFTI_TYPE_COMPLEX128:
                        case NiftiHeader.NIFTI_TYPE_COMPLEX256:
                        case NiftiHeader.DT_ALL:
                        default:
                            throw new IOException("Sorry, cannot yet write nifti-1 datatype " + NiftiHeader.decodeDatatype(hdr.datatype));

                        }
                    }

        if (hdr.little_endian)
            ((LEDataOutputStream) dout).close();
        else
            ((DataOutputStream) dout).close();

        return;
    }
    
    /**
     * <p>This function returns an image containing a slice for a given nifti
     * volume data. </p>
     * @author Diego Garibay-Pulido 2016
     * @param sliceNum The slice number
     * @param plane The plane (coronal, saggital or axial)
     * @param dimension The dimension
     * @return <p>b -A gray-scale BufferedImage with the slice adjusted to its 
     * dimensions </p>
     */ 
    public BufferedImage drawNiftiSlice(int sliceNum, String plane, int dimension,String colormap){
	// Initialize variables
        double temp;
        int rgb,z_idx,x_idx,y_idx;
        BufferedImage b;
        // get image dimensions
        int nx  = header.dim[1];
        int ny  = header.dim[2];
        int nz  = header.dim[3];
        int dim = header.dim[4];

        if(orientation.equals("LPI")|orientation.equals("RPI")|
        orientation.equals("LPS")|orientation.equals("RPS")|
        orientation.equals("LAI")|orientation.equals("RAI")|
        orientation.equals("LAS")|orientation.equals("RAS")){
        //Checks dimension bounds
        if (dim>=dimension){
                switch (plane) {
                    case "saggital":
                        b=new BufferedImage(ny,nz,BufferedImage.TYPE_INT_ARGB);
                        if(orient[1]=='A'){y_idx=ny-1;}
                        else if(orient[1]=='P'){y_idx=0;}
                        else{
                            System.out.println("Invalid orientation: Use dcm2nii to change orientation");
                            b=null;
                            break;
                        }
                        for(int i=0;i<ny;i++){
                            if(orient[2]=='I'){z_idx=nz-1;}
                            else if(orient[2]=='S'){z_idx=0;}
                            else{
                                System.out.println("Invalid orientation: Use dcm2nii to change orientation");
                                b=null;
                                break;
                            }
                            for(int j=0;j<nz;j++){
                                temp = data.get(sliceNum,i,j,dimension);
                                rgb=UITools.doubleToRGB(temp, max, min,colormap);
                                if(orient[2]=='I'){
                                    if(orient[1]=='P'){
                                        b.setRGB(i,z_idx,rgb);
                                        z_idx=z_idx-1;
                                    }else{
                                        b.setRGB(y_idx,z_idx,rgb);
                                        z_idx=z_idx-1;   
                                    }
                                }else{
                                    if(orient[1]=='P'){
                                        b.setRGB(i,j,rgb);
                                    }else{
                                        b.setRGB(y_idx,j,rgb);   
                                    }
                                }
                            }
                            if(orient[1]!='P'){
                            y_idx=y_idx-1;
                            }else{y_idx=0;}
                        }
                        b = getNiftiScaledImage(b,scale[1],scale[2]);
                        break;
                        
                    case "coronal":
                        b=new BufferedImage(nx,nz,BufferedImage.TYPE_INT_ARGB);
                        if(orient[0]=='R'){
                            x_idx=nx-1;
                        }else if(orient[0]=='L'){x_idx=0;}
                         else{
                            System.out.println("Invalid orientation: Use dcm2nii to change orientation");
                            b=null;
                            break;
                        }
                        for(int i=0;i<nx;i++){
                            if(orient[2]=='I'){
                                z_idx=nz-1; 
                            }else if(orient[2]=='S'){z_idx=0;}
                            else{
                                System.out.println("Invalid orientation: Use dcm2nii to change orientation");
                                b=null;
                                break;
                            }
                            for(int j=0;j<nz;j++){
                                temp = data.get(i,sliceNum,j,dimension);
                                rgb=UITools.doubleToRGB(temp, max, min,colormap);
                                if(orient[2]=='I'){
                                    if(orient[0]=='L'){
                                        b.setRGB(i,z_idx,rgb);
                                        z_idx=z_idx-1;
                                    }else{
                                        b.setRGB(x_idx,z_idx,rgb);
                                        z_idx=z_idx-1;
                                    }
                                }else{
                                    if(orient[0]=='L'){
                                        b.setRGB(i,j,rgb);
                                    }else{
                                        b.setRGB(x_idx,j,rgb);  
                                    }
                                }
                            }
                            if(orient[0]!='L'){
                            x_idx=x_idx-1; 
                            }else{x_idx=0;}
                        }
                        b = getNiftiScaledImage(b,scale[0],scale[2]);
                        break;
                        
                    default:
                        //Axial is the default plane to graph on
                        b=new BufferedImage(nx,ny,BufferedImage.TYPE_INT_ARGB);
                        if(orient[0]=='R'){
                            x_idx=nx-1;
                        }else if(orient[0]=='L'){x_idx=0;}
                        else{
                            System.out.println("Invalid orientation: Use dcm2nii to change orientation");
                            b=null;
                            break;
                        }
                        for(int i=0;i<nx;i++){
                            if(orient[1]=='P'){
                                y_idx=ny-1;
                            }else if(orient[1]=='A'){y_idx=0;}
                            else{
                                System.out.println("Invalid orientation: Use dcm2nii to change orientation");
                                b=null;
                                break;
                            }
                            for(int j=0;j<ny;j++){
                                temp =data.get(i,j,sliceNum,dimension);
                                rgb=UITools.doubleToRGB(temp, max, min,colormap);
                                if(orient[1]=='P'){
                                    if(orient[0]=='L'){
                                        b.setRGB(i,y_idx,rgb);
                                        y_idx=y_idx-1;
                                    }else{
                                        b.setRGB(x_idx,y_idx,rgb);
                                        y_idx=y_idx-1; 
                                    }
                                }else{
                                    if(orient[0]=='L'){
                                        b.setRGB(i,j,rgb);
                                    }else{
                                        b.setRGB(x_idx,j,rgb); 
                                    }
                                }
                            }
                            if(orient[0]!='L'){
                            x_idx=x_idx-1;
                            }else{x_idx=0;}
                        }
                        b = getNiftiScaledImage(b,scale[0],scale[1]);
                        break;
                }
            }
        else {
            System.out.println("Invalid dimension");
            b=null;
        }
        }
        else{
            System.out.println("Invalid orientation: Use dcm2nii to change orientation");
            b=null;
        }
        //ImageIO.write(b,"jpg",new File ("C:\\Users\\Synapticom\\Desktop\\Diego\\RUSH\\Matlab\\pspiDTI\\Test_data\\DoubleArray.jpg"));
        return b;
    }
    
    /**
     * <p>Executes the interpolation for a buffered image, the output lenght of each
     * dimension is multiplied by the factors</p>
     * @param src BufferedImage with the data
     * @param factor_w Width factor
     * @param factor_h Height factor
     * @return Buffered image with the scaled image
     */
    private BufferedImage getNiftiScaledImage(BufferedImage src, double factor_w, double factor_h){
        int finalw = src.getWidth();
        int finalh = src.getHeight();
        
        finalh = (int)(finalh * factor_h);                
        finalw = (int)(finalw * factor_w);

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();
        return resizedImg;
    }
    
    /**
     * <p>Reads the nifti header and returns a multiplier for scaling the image to 
     * its actual dimensions</p>
     * @author Diego Garibay-Pulido 2016
     */
    public void  getNiftiScale(){
        //Read pixel dimensions
        float pd[]=new float[3];
        pd[0] = header.pixdim[1];
        pd[1] = header.pixdim[2];
        pd[2] = header.pixdim[3];

        //Figure out min dimension
        float mindim=pd[0];
        for(int r=1;r<3;r++){
            if(mindim>pd[r]){
                mindim=pd[r];
            }
        }

        //New dimensions 
        //[x y z]=[i j k]*[pixdimx/mindim pixdimy/mindim pixdimz/mindim]
        int n[]=new int[3];
        n[0] = (int) (header.dim[1]*pd[0]/mindim);
        n[1] = (int) (header.dim[2]*pd[1]/mindim);
        n[2] = (int) (header.dim[3]*pd[2]/mindim);

        //Figure out actual multiplier for each axis
        double s[]= new double[3];
        s[0]=(double) n[0]/header.dim[1];
        s[1]=(double) n[1]/header.dim[2];
        s[2]=(double) n[2]/header.dim[3];
        this.scale= s;
         //get image orientation
        orientation = header.orientation();
        orient = orientation.toCharArray();
 
       }
    
    public double[] computeXYZ(double[][] R,int xVal,int yVal,int zVal){
        double ijk[]= new double[3];
        ijk[0]=(double)xVal;
        ijk[1]=(double)yVal;
        ijk[2]=(double)zVal*header.qfac;
        
        ijk=LinearAlgebra.matrixTimesVector(R, ijk);
        double xyz[]=new double[3];
        for(int i=0;i<3;i++){
            xyz[i]=ijk[i]+header.qoffset[i];
        }
        
        return xyz;
    }
    
    public void setMax3D(int dimension){
        max=ArrayOperations.findMaximum(data.get3DArray(dimension));
    }
    public void setMin3D(int dimension){
        min=ArrayOperations.findMinimum(data.get3DArray(dimension));
    }
    
    public void setMax(double m){
        max=m;
    }
    
    public void setMin(double m){
        min=m;
    }
    
    public double getMax(){
        return max;
    }
    
    public double getMin(){
        return min;
    }
}
