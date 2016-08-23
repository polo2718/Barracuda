/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.niftilibrary.niftijio;

import domain.mathUtils.arrayTools.ArrayOperations;
import domain.mathUtils.numericalMethods.linearAlgebra.LinearAlgebra;
import domain.mathUtils.numericalMethods.linearAlgebra.Matrix;
import domain.mathUtils.numericalMethods.linearAlgebra.Vector;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import user.gui.tools.UITools;

/**
 *<p> This class is a variation of NiftiVolume to be used when drawing methods 
 * in a UI are required</p>
 * @author <p>Diego Garibay-Pulido 2016</p>
 */
public class DrawableNiftiVolume extends NiftiVolume{
    
    public  double scale[];
    public  char[] orient;
    private  String orientation;
    private double max;
    private double min;
    private int[][] drawRange = new int[3][4];
    
    /**
     * <p>This constructor takes a NiftiVolume object to create the
     * DrawableNiftiVolume with the same header and data as the original
     * NiftiVolume</p>
     * @param nii The original NiftiVolume 
     */
    
    public DrawableNiftiVolume() {
    }
    
    public void initDrawableNiftiVolume(NiftiVolume nii){
        this.header=nii.header;
        this.data=nii.data;
        getNiftiScale();//Sets scale, orientation & orient
        clearDrawRange();//Sets to zero the DrawRange;
        
        // Get volume maximum if not already embedded in the header
        if(header.cal_max==0){setMax3D(0);}
        else{setMax(header.cal_max);}
        //Get volume minimum if not alread embedded in the header
        if(header.cal_min==0){setMin3D(0);}
        else{setMin(header.cal_min);}
    }
    
    /**
     * <p>This function returns an image containing a slice for a given nifti
     * volume data. The drawRange determines how much of the image will be
     * displayed and is used for zoom purposes. </p>
     * @author Diego Garibay-Pulido 2016
     * @param sliceNum The slice number
     * @param plane The plane (coronal, saggital or axial)
     * @param dimension The dimension
     * @param colormap The colormap to draw the image.
     * @return <p>b -A gray-scale BufferedImage with the slice adjusted to its 
     * dimensions </p>
     */ 
    public BufferedImage drawNiftiSlice(int sliceNum, String plane, int dimension,String colormap){
	// Initialize variables
        double temp;
        int z_idx,x_idx,y_idx;
        BufferedImage b;
        Color rgb;
        
        int dim = header.dim[4];
        int dx,dy;
        //Checks orientation
        if(orientation.equals("LPI")|orientation.equals("RPI")|
        orientation.equals("LPS")|orientation.equals("RPS")|
        orientation.equals("LAI")|orientation.equals("RAI")|
        orientation.equals("LAS")|orientation.equals("RAS")){
            //Checks dimension bounds
            if (dim>=dimension){
                    switch (plane) {
                        case "saggital":
                            //drawRange[0][:] contains the drawRange for saggital plane
                            dx=drawRange[0][2]-drawRange[0][0];
                            dy=drawRange[0][3]-drawRange[0][1];
                            b=new BufferedImage(dx,dy,BufferedImage.TYPE_INT_ARGB);
                            if(orient[1]=='A'){y_idx=dx-1;}
                            else{y_idx=0;}
                            for(int i=drawRange[0][0];i<drawRange[0][2];i++){
                                if(orient[2]=='I'){z_idx=dy-1;}
                                else{z_idx=0;}
                                for(int j=drawRange[0][1];j<drawRange[0][3];j++){
                                    temp = data.get(sliceNum,i,j,dimension);//Gets array value
                                    rgb=UITools.doubleToRGB(temp, max, min,colormap);//Turns double to ARGB
                                    if(orient[2]=='I'){
                                        if(orient[1]=='P'){
                                            b.setRGB(y_idx,z_idx,rgb.getRGB());
                                            z_idx=z_idx-1;
                                        }else{
                                            b.setRGB(y_idx,z_idx,rgb.getRGB());
                                            z_idx=z_idx-1;   
                                        }
                                    }else{
                                        if(orient[1]=='P'){
                                            b.setRGB(y_idx,z_idx,rgb.getRGB());
                                            z_idx=z_idx+1;
                                        }else{
                                            b.setRGB(y_idx,z_idx,rgb.getRGB()); 
                                            z_idx=z_idx+1;
                                        }
                                    }
                                }
                                if(orient[1]!='P'){
                                y_idx=y_idx-1;
                                }else{y_idx=y_idx+1;}
                            }
                            //Scales image to adjust for real header dimensions
                            b = getNiftiScaledImage(b,scale[1],scale[2]);
                            break;
                        case "coronal":
                            //drawRange[1][:] contains the drawRange for coronal plane
                            dx=drawRange[1][2]-drawRange[1][0];
                            dy=drawRange[1][3]-drawRange[1][1];
                            b=new BufferedImage(dx,dy,BufferedImage.TYPE_INT_ARGB);
                            if(orient[0]=='R'){
                                x_idx=dx-1;
                            }else{x_idx=0;}
                            for(int i=drawRange[1][0];i<drawRange[1][2];i++){
                                if(orient[2]=='I'){
                                    z_idx=dy-1; 
                                }else{z_idx=0;}
                                for(int j=drawRange[1][1];j<drawRange[1][3];j++){
                                    temp = data.get(i,sliceNum,j,dimension);
                                    rgb=UITools.doubleToRGB(temp, max, min,colormap);
                                    if(orient[2]=='I'){
                                        if(orient[0]=='L'){
                                            b.setRGB(x_idx,z_idx,rgb.getRGB());
                                            z_idx=z_idx-1;
                                        }else{
                                            b.setRGB(x_idx,z_idx,rgb.getRGB());
                                            z_idx=z_idx-1;
                                        }
                                    }else{
                                        if(orient[0]=='L'){
                                            b.setRGB(x_idx,z_idx,rgb.getRGB());
                                            z_idx=z_idx+1;
                                        }else{
                                            b.setRGB(x_idx,z_idx,rgb.getRGB()); 
                                            z_idx=z_idx+1;
                                        }
                                    }
                                }
                                if(orient[0]!='L'){
                                x_idx=x_idx-1; 
                                }else{x_idx=x_idx+1;}
                            }
                            b = getNiftiScaledImage(b,scale[0],scale[2]);
                            break;
                        default:
                            dx=drawRange[2][2]-drawRange[2][0];
                            dy=drawRange[2][3]-drawRange[2][1];
                            //Axial is the default plane to graph on
                            b=new BufferedImage(dx,dy,BufferedImage.TYPE_INT_ARGB);
                            if(orient[0]=='R'){
                                x_idx=dx-1;
                            }else{x_idx=0;}
                            for(int i=drawRange[2][0];i<drawRange[2][2];i++){
                                if(orient[1]=='P'){
                                    y_idx=dy-1;
                                }else{y_idx=0;}
                                for(int j=drawRange[2][1];j<drawRange[2][3];j++){
                                    temp =data.get(i,j,sliceNum,dimension);
                                    rgb=UITools.doubleToRGB(temp, max, min,colormap);
                                    if(orient[1]=='P'){
                                        if(orient[0]=='L'){
                                            b.setRGB(x_idx,y_idx,rgb.getRGB());
                                            y_idx=y_idx-1;
                                        }else{
                                            b.setRGB(x_idx,y_idx,rgb.getRGB());
                                            y_idx=y_idx-1; 
                                        }
                                    }else{
                                        if(orient[0]=='L'){
                                            b.setRGB(x_idx,y_idx,rgb.getRGB());
                                            y_idx=y_idx+1;
                                        }else{
                                            b.setRGB(x_idx,y_idx,rgb.getRGB());
                                            y_idx=y_idx+1;
                                        }
                                    }
                                }
                                if(orient[0]!='L'){
                                x_idx=x_idx-1;
                                }else{x_idx=x_idx+1;}
                            }
                            b = getNiftiScaledImage(b,scale[0],scale[1]);
                            break;
                    }
            }else {
                System.out.println("Invalid dimension");
                b=null;
            }
        }else{
            System.out.println("Invalid orientation: Use dcm2nii to change orientation");
            b=null;
        }
        return b;
    }
    
    /**
     * <p>Executes the interpolation for a buffered image, the output length of each
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

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        Color c=new Color(0,0,0,0);
        g2.drawImage(src, 0, 0, finalw, finalh,c, null);
        g2.dispose();
        return resizedImg;
    }
    
    /**
     * <p>Reads the nifti header and returns a multiplier for scaling the image to 
     * its actual dimensions</p>
     * @author Diego Garibay-Pulido 2016
     */
    public  void  getNiftiScale(){
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
        scale= s;
         //get image orientation
        orientation = header.orientation();
        orient = orientation.toCharArray();
 
       }
    
    @Deprecated
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
    /**
     * 
     * @param rotationMtrx Matrix object containing the rotation matrix
     * @param xVal
     * @param yVal
     * @param zVal
     * @return Vector containing the x, y, and z coordinates
     * @author Diego Garibay, Leopoldo Cendejas
     */
        public Vector computeXYZ(Matrix rotationMtrx,int xVal,int yVal,int zVal){
        double ijk[]= new double[3];
        ijk[0]=(double)xVal;
        ijk[1]=(double)yVal;
        ijk[2]=(double)zVal*header.qfac;
        Vector v=new Vector(ijk);
        //Perform the product of the rotation matrix with the vector
        try{
            v=rotationMtrx.product(v);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        ijk=v.getComponents();
        double xyz[]=new double[3];
        for(int i=0;i<3;i++){
            xyz[i]=ijk[i]+header.qoffset[i];
        }
        
        return new Vector(xyz);
    }
   
    
    public void setMax3D(int dimension){
        max=ArrayOperations.findMaximum(data.get3DArray(dimension));
    }
    public  void setMin3D(int dimension){
        min=ArrayOperations.findMinimum(data.get3DArray(dimension));
    }
    
    public  void setMax(double m){
        max=m;
    }
    public  void setMin(double m){
        min=m;
    }
    
    public double getMax(){
        return max;
    }
    public double getMin(){
        return min;
    }
    
    public int[][] getDrawRange(){
        return drawRange;
    }
    public void setDrawRange(int range[][]){
        drawRange=range;
    }
    public void setDrawRange(int x,int y, int val){
        drawRange[x][y]=val;
    }
    public int getDrawRange(int x,int y){
        return drawRange[x][y];
    }
    //Clear the drawRange
    public  void clearDrawRange(){
        drawRange=new int[3][4];
        //Saggital Range ny*nz
        drawRange[0][0]=0;// x0
        drawRange[0][1]=0;//y0
        drawRange[0][2]=header.dim[2];//x1 ny
        drawRange[0][3]=header.dim[3];//y1 nz
         //Coronal Range nx*nz
        drawRange[1][0]=0;// x0
        drawRange[1][1]=0;//y0
        drawRange[1][2]=header.dim[1];//x1 nx
        drawRange[1][3]=header.dim[3];//y1 nz
         //Axial Range nx*ny
        drawRange[2][0]=0;// x0
        drawRange[2][1]=0;//y0
        drawRange[2][2]=header.dim[1];//x1 nx
        drawRange[2][3]=header.dim[2];//y1 ny
    }
}
   
    
