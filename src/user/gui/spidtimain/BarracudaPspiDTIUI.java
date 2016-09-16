/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.spidtimain;

import data.niftilibrary.niftijio.DrawableNiftiVolume;
import data.niftilibrary.niftijio.FourDimensionalArray;
import data.niftilibrary.niftijio.NiftiVolume;
import domain.imaging.spatialfiltering.Kernel;
import domain.imaging.spatialfiltering.NiftiNonLinearSpatialFilter;
import domain.imaging.spatialfiltering.operations.UnpairedtTest;
import java.awt.Cursor;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import user.gui.tools.IconGetter;

/**
 *
 * @author Diego Garibay-Pulido 2016
 */
public class BarracudaPspiDTIUI extends javax.swing.JFrame {
private JFileChooser fc;
private DrawableNiftiVolume ictalFA, baselineFA, ictalTR, baselineTR, binaryMask;
    /**
     * Creates new form BarracudaPspiDTIUI
     */
    public BarracudaPspiDTIUI() {
        initComponents();
        fc= new JFileChooser();
        // Initiate file chooser settings
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "NIFTI (*.nii,*.gz)", "nii", "gz");
        fc.setFileFilter(filter);
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        this.setLocationRelativeTo(null);
        tabManager.setEnabledAt(1, false); // Analysis Tab, only enabled when at least all the files are input
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        errorDialog = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        tabManager = new javax.swing.JTabbedPane();
        fileTab = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        openIctalFileButton = new javax.swing.JButton();
        openBaselineFileButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        maskTextPane = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ictalTextPane = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        baselineTextPane = new javax.swing.JTextPane();
        openMaskFileButton = new javax.swing.JButton();
        analysisTab = new javax.swing.JPanel();
        tTestButton = new javax.swing.JButton();

        errorDialog.setTitle("Error!!!");
        errorDialog.setIconImage(IconGetter.getProjectIcon("error_icon.png"));
        errorDialog.setModal(true);
        errorDialog.setSize(new java.awt.Dimension(280, 120));

        jLabel5.setText("<html> <strong><font size=4 color=\"red\">Error!!!!</font></strong>");

        errorLabel.setText("Error Message");

        javax.swing.GroupLayout errorDialogLayout = new javax.swing.GroupLayout(errorDialog.getContentPane());
        errorDialog.getContentPane().setLayout(errorDialogLayout);
        errorDialogLayout.setHorizontalGroup(
            errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(errorDialogLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
            .addGroup(errorDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        errorDialogLayout.setVerticalGroup(
            errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(errorDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabManager.setVerifyInputWhenFocusTarget(false);

        jLabel2.setText("Baseline FA File:");

        openIctalFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open_icon.png"))); // NOI18N
        openIctalFileButton.setToolTipText("");
        openIctalFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openIctalFileButtonActionPerformed(evt);
            }
        });

        openBaselineFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open_icon.png"))); // NOI18N
        openBaselineFileButton.setToolTipText("");
        openBaselineFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBaselineFileButtonActionPerformed(evt);
            }
        });

        maskTextPane.setEditable(false);
        jScrollPane3.setViewportView(maskTextPane);

        jLabel3.setText("WM mask File:");
        jLabel3.setToolTipText("");

        jLabel1.setText("Post-ictal FA File:");

        ictalTextPane.setEditable(false);
        jScrollPane1.setViewportView(ictalTextPane);

        baselineTextPane.setEditable(false);
        jScrollPane2.setViewportView(baselineTextPane);

        openMaskFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open_icon.png"))); // NOI18N
        openMaskFileButton.setToolTipText("");
        openMaskFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMaskFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fileTabLayout = new javax.swing.GroupLayout(fileTab);
        fileTab.setLayout(fileTabLayout);
        fileTabLayout.setHorizontalGroup(
            fileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(openIctalFileButton)
                    .addComponent(openBaselineFileButton)
                    .addComponent(openMaskFileButton))
                .addContainerGap(342, Short.MAX_VALUE))
        );
        fileTabLayout.setVerticalGroup(
            fileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openIctalFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2)
                    .addComponent(openBaselineFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(fileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openMaskFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(148, 148, 148))
        );

        tabManager.addTab("File input", fileTab);

        tTestButton.setText("Perform T-Test");
        tTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tTestButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout analysisTabLayout = new javax.swing.GroupLayout(analysisTab);
        analysisTab.setLayout(analysisTabLayout);
        analysisTabLayout.setHorizontalGroup(
            analysisTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analysisTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tTestButton)
                .addContainerGap(611, Short.MAX_VALUE))
        );
        analysisTabLayout.setVerticalGroup(
            analysisTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analysisTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tTestButton)
                .addContainerGap(267, Short.MAX_VALUE))
        );

        tabManager.addTab("Analysis", analysisTab);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabManager)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openIctalFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openIctalFileButtonActionPerformed
        int returnVal= fc.showDialog(this,"Open ictal FA file...");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String filename = file.getAbsolutePath();
            try{
                ictalFA= new DrawableNiftiVolume(NiftiVolume.read(filename));
                ictalTextPane.setText(filename);
            }catch(Exception e){

            }
        }
        else {
            returnVal=0;
        }
        if(ictalFA!=null & baselineFA!=null){
            tabManager.setEnabledAt(1, true);
        }
    }//GEN-LAST:event_openIctalFileButtonActionPerformed

    private void openBaselineFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBaselineFileButtonActionPerformed
        int returnVal= fc.showDialog(this,"Open Baseline FA file...");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String filename = file.getAbsolutePath();
            try{
                baselineFA= new DrawableNiftiVolume(NiftiVolume.read(filename));
                baselineTextPane.setText(filename);
            }catch(Exception e){
                
            }
        }
        else {
            returnVal=0;
        }
        if(ictalFA!=null & baselineFA!=null){
            tabManager.setEnabledAt(1, true);
        }
    }//GEN-LAST:event_openBaselineFileButtonActionPerformed

    private void openMaskFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMaskFileButtonActionPerformed
        int returnVal= fc.showDialog(this,"Open WM mask file...");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String filename = file.getAbsolutePath();
            try{
                binaryMask= new DrawableNiftiVolume(NiftiVolume.read(filename));
                maskTextPane.setText(filename);
            }catch(Exception e){

            }
        }
        else {
            returnVal=0;
        }
        
    }//GEN-LAST:event_openMaskFileButtonActionPerformed

    private void tTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tTestButtonActionPerformed
        
        if(ictalFA!=null & baselineFA!=null){
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            UnpairedtTest operation = new UnpairedtTest();
            NiftiNonLinearSpatialFilter spatialFilter = new NiftiNonLinearSpatialFilter(operation);
            int[] dims ={0};
            Kernel w = new Kernel(1,3);
            FourDimensionalArray result;
            if(binaryMask!=null){
                result= spatialFilter.doubleFilter(ictalFA.data, baselineFA.data, w, binaryMask.data, dims);
            }else{
                result= spatialFilter.doubleFilter(ictalFA.data, baselineFA.data, w, dims);
            }
            NiftiVolume resultingVol= new NiftiVolume();
            resultingVol.header=ictalFA.header;
            resultingVol.data=result;
            try{
                resultingVol.write("C:\\Users\\Synapticom\\Desktop\\p_vals.nii.gz");
            }
            catch(Exception e){
                System.out.println("Not able to write");
            }
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_tTestButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel analysisTab;
    private javax.swing.JTextPane baselineTextPane;
    private javax.swing.JDialog errorDialog;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JPanel fileTab;
    private javax.swing.JTextPane ictalTextPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane maskTextPane;
    private javax.swing.JButton openBaselineFileButton;
    private javax.swing.JButton openIctalFileButton;
    private javax.swing.JButton openMaskFileButton;
    private javax.swing.JButton tTestButton;
    private javax.swing.JTabbedPane tabManager;
    // End of variables declaration//GEN-END:variables
    
    
    
}
