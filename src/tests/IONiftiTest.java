/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import data.niftilibrary.niftijio.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Synapticom
 */
public class IONiftiTest {
    public static void main(String[] args) {
    try{
            NiftiVolume niiVol= NiftiVolume.read("C:\\Users\\Synapticom\\Desktop\\Diego\\RUSH\\Matlab\\pspiDTI\\Test_data\\basal_test.nii.gz");
            // Read Header dimensions
            int nx  = niiVol.header.dim[1];
            int ny  = niiVol.header.dim[2];
            int nz  = niiVol.header.dim[3];
            int dim = niiVol.header.dim[4];
            //Create buffer array
            double data[][][][]=niiVol.data.get4DArray();
            //Get image data
            for(int i=0;i<nx;i++)
                for(int j=0;j<ny;j++)
                    for(int k=0;k<nz;k++)
                        for(int l=0;l<dim;l++){
                            data[i][j][k][l]=200*niiVol.data.get(i,j,k,l);
                        }
            //Set image data
            niiVol.data.set4DArray(data);
            //Write data to disk
            niiVol.write("C:\\Users\\Synapticom\\Desktop\\Diego\\RUSH\\Matlab\\pspiDTI\\Test_data\\basal200.nii.gz");
            //Read orientation
            String orient = niiVol.header.orientation();
            System.out.println(orient);
        }
        catch (IOException e)
        {
            System.err.println("error: " + e.getMessage());
        }
    }
}
