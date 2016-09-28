/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.gui.spidtimain;
import data.niftilibrary.niftijio.*;
import domain.mathUtils.numericalMethods.linearAlgebra.Matrix;
import domain.mathUtils.numericalMethods.linearAlgebra.Vector;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import user.gui.tools.*;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;


/**
 *  <p>This is the main class for the pspiDTI UI it includes the menus and
 * a NIFTI file viewer.</p>  
 * 
 * @author Diego Garibay-Pulido 2016
 */
public class BarracudaViewUI extends javax.swing.JFrame {

    /**
     * Creates new form BarracudaViewUI
     */
    private DrawableNiftiVolume niiVol; //NIFTI main volume
    private DrawableNiftiVolume overlayVol; //NIFTI Overlay volume
    //double[][] R = new double[3][3]; //Rotation matrix for NIFTI #1
    private Matrix rotationMtrx; //Rotation matrix for NIFTI #1
    private double coronalScale; //Display scale on the coronalLabel
    private double saggitalScale;//Display scale on the saggitalLabel
    private double axialScale; //Display scale on the axialLabel
    private Point prevMouse;//Previous Mouse position for the contrast adjustment
    private Point zoomStartPoint;//Zoom area initial point
    private Point zoomEndPoint;//Zoom area end point
    private Point panPoint;
    private String colorScale;//Display colorscale (Main volume)
    private String colorScaleOverlay="hot";//Overlay colorscale
    private Color xHairColor=Color.GREEN;
    private boolean viewState=true;//Boolean flag to indicate radiological or neurological view
    private JFileChooser fc = new JFileChooser();
    private BarracudaViewMosaicFrame mosaicWindow;
    
    /**
     * Creates a viewer object
     */
    public BarracudaViewUI() {
        initComponents();
        //Set up file chooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "NIFTI (*.nii,*.gz)", "nii", "gz");
        fc.setFileFilter(filter);
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
    }
    /**
     * Creates an object with two volumes, the main viewing volume and the overlay volume
     * @param niiVol The nifti main volume
     * @param overlayVol The nifti overlay volume
     */
    public BarracudaViewUI(DrawableNiftiVolume niiVol,DrawableNiftiVolume overlayVol) {
        initComponents();
        //Set up file chooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "NIFTI (*.nii,*.gz)", "nii", "gz");
        fc.setFileFilter(filter);
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        if(niiVol!=null){
            if(overlayVol!=null){
                if(overlayVol.orient[0]=='L'){overlayVol.orient[0]='R';}
                else if(overlayVol.orient[0]=='R'){overlayVol.orient[0]='L';}
                if(overlayVol.header.dim[4]<=0){overlayVol.header.dim[4]=1;}
                this.overlayVol=overlayVol;
            }
            // Added code so default view would be neurological
            if(niiVol.orient[0]=='L'){niiVol.orient[0]='R';}
            else if(niiVol.orient[0]=='R'){niiVol.orient[0]='L';}
            
            //Set spinner models
            if(niiVol.header.dim[4]<=0){niiVol.header.dim[4]=1;}
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0,niiVol.header.dim[4]-1,1);
            volSpinner.setModel(model);
            model = new SpinnerNumberModel(0,0,niiVol.header.dim[1]-1,1);
            xSpinner.setModel(model);
            model = new SpinnerNumberModel(0,0,niiVol.header.dim[2]-1,1);
            ySpinner.setModel(model);
            model = new SpinnerNumberModel(0,0,niiVol.header.dim[3]-1,1);
            zSpinner.setModel(model);
            //Set slider values
            saggitalSlider.setMaximum(niiVol.header.dim[1]-1);
            saggitalSlider.setValue((int)(niiVol.header.dim[1]/2));
            coronalSlider.setMaximum(niiVol.header.dim[2]-1);
            axialSlider.setMaximum(niiVol.header.dim[3]-1);
            coronalSlider.setValue((int)(niiVol.header.dim[2]/2));
            axialSlider.setValue((int)(niiVol.header.dim[3]/2));
            
            rotationMtrx=new Matrix(niiVol.header.mat33());
            
            this.niiVol=niiVol;
            //Set Color Bar
            setColorBar();
            drawLabelsXHair();
            setXYZLabels();
        }
        
    }
    /**
     * Opens a nifti file and displays it in a new viewer instance
     * @param filename NIFTI file full path. Must be a valid filename
     */
    public BarracudaViewUI(String filename) {
        initComponents();
        //Set up file chooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "NIFTI (*.nii,*.gz)", "nii", "gz");
        fc.setFileFilter(filter);
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        niiVol=null;
        colorScale="greyscale";
        try{niiVol=new DrawableNiftiVolume(NiftiVolume.read(filename));
        }catch(Exception e){}
        if(niiVol!=null){
            // Added code so default view would be neurological
            if(niiVol.orient[0]=='L'){niiVol.orient[0]='R';}
            else if(niiVol.orient[0]=='R'){niiVol.orient[0]='L';}
            
            //Set spinner models
            if(niiVol.header.dim[4]<=0){niiVol.header.dim[4]=1;}
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0,niiVol.header.dim[4]-1,1);
            volSpinner.setModel(model);
            model = new SpinnerNumberModel(0,0,niiVol.header.dim[1]-1,1);
            xSpinner.setModel(model);
            model = new SpinnerNumberModel(0,0,niiVol.header.dim[2]-1,1);
            ySpinner.setModel(model);
            model = new SpinnerNumberModel(0,0,niiVol.header.dim[3]-1,1);
            zSpinner.setModel(model);
            //Set slider values
            saggitalSlider.setMaximum(niiVol.header.dim[1]-1);
            saggitalSlider.setValue((int)(niiVol.header.dim[1]/2));
            coronalSlider.setMaximum(niiVol.header.dim[2]-1);
            axialSlider.setMaximum(niiVol.header.dim[3]-1);
            coronalSlider.setValue((int)(niiVol.header.dim[2]/2));
            axialSlider.setValue((int)(niiVol.header.dim[3]/2));
            
            rotationMtrx=new Matrix(niiVol.header.mat33());
            //Set Color Bar
            setColorBar();
            drawLabelsXHair();
            setXYZLabels();
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewGroup = new javax.swing.ButtonGroup();
        volumeSelectorGroup = new javax.swing.ButtonGroup();
        viewActionsGroup = new javax.swing.ButtonGroup();
        colorScaleGroup = new javax.swing.ButtonGroup();
        errorDialog = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        coronalPanel = new javax.swing.JPanel();
        coronalLabel1 = new javax.swing.JLabel();
        coronalLabel = new javax.swing.JLabel();
        coronalLabel3 = new javax.swing.JLabel();
        coronalLabel2 = new javax.swing.JLabel();
        coronalLabel4 = new javax.swing.JLabel();
        coronalSlider = new javax.swing.JSlider();
        axialPanel = new javax.swing.JPanel();
        axialLabel1 = new javax.swing.JLabel();
        axialLabel = new javax.swing.JLabel();
        axialLabel2 = new javax.swing.JLabel();
        axialLabel3 = new javax.swing.JLabel();
        axialSlider = new javax.swing.JSlider();
        axialLabel4 = new javax.swing.JLabel();
        saggitalPanel = new javax.swing.JPanel();
        saggitalLabel = new javax.swing.JLabel();
        saggitalLabel3 = new javax.swing.JLabel();
        saggitalLabel2 = new javax.swing.JLabel();
        saggitalLabel4 = new javax.swing.JLabel();
        saggitalLabel5 = new javax.swing.JLabel();
        saggitalSlider = new javax.swing.JSlider();
        infoPanel = new java.awt.Panel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        volSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        xSpinner = new javax.swing.JSpinner();
        ySpinner = new javax.swing.JSpinner();
        zSpinner = new javax.swing.JSpinner();
        zPosLabel = new javax.swing.JLabel();
        yPosLabel = new javax.swing.JLabel();
        xPosLabel = new javax.swing.JLabel();
        neuroView = new javax.swing.JRadioButton();
        radioView = new javax.swing.JRadioButton();
        colorBar = new javax.swing.JLabel();
        cbm = new javax.swing.JLabel();
        cbm1 = new javax.swing.JLabel();
        colorBar12 = new javax.swing.JLabel();
        colorBar34 = new javax.swing.JLabel();
        colorBar14 = new javax.swing.JLabel();
        colorBarMax = new javax.swing.JTextField();
        colorBarMin = new javax.swing.JTextField();
        xhairButton = new javax.swing.JToggleButton();
        valueLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openVolumeMenu = new javax.swing.JMenuItem();
        openOverlayMenu = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        colormapMenu = new javax.swing.JMenu();
        grayScale = new javax.swing.JCheckBoxMenuItem();
        hotScale = new javax.swing.JCheckBoxMenuItem();
        hotInvertScale = new javax.swing.JCheckBoxMenuItem();
        winterScale = new javax.swing.JCheckBoxMenuItem();
        winterInvertScale = new javax.swing.JCheckBoxMenuItem();
        rainbowScale = new javax.swing.JCheckBoxMenuItem();
        resetColorScale = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        crosshairMenu = new javax.swing.JRadioButtonMenuItem();
        zoomMenu = new javax.swing.JRadioButtonMenuItem();
        panMenu = new javax.swing.JRadioButtonMenuItem();
        restoreZoomMenu = new javax.swing.JMenuItem();
        mosaicViewMenu = new javax.swing.JMenuItem();
        volumeMenu = new javax.swing.JMenu();
        volumeSelect = new javax.swing.JRadioButtonMenuItem();
        overlaySelect = new javax.swing.JRadioButtonMenuItem();

        viewGroup.add(radioView);
        viewGroup.add(neuroView);
        neuroView.setSelected(true);

        volumeSelectorGroup.add(volumeSelect);
        volumeSelectorGroup.add(overlaySelect);

        viewActionsGroup.add(crosshairMenu);
        viewActionsGroup.add(zoomMenu);
        viewActionsGroup.add(panMenu);

        colorScaleGroup.add(grayScale);
        colorScaleGroup.add(hotScale);
        colorScaleGroup.add(hotInvertScale);
        colorScaleGroup.add(winterScale);
        colorScaleGroup.add(winterInvertScale);
        colorScaleGroup.add(rainbowScale);

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
        setTitle("Barracuda View");
        setIconImage(IconGetter.getProjectIcon("brain128.png"));
        setMinimumSize(new java.awt.Dimension(600, 650));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        coronalPanel.setBackground(new java.awt.Color(0, 0, 0));
        coronalPanel.setMinimumSize(new java.awt.Dimension(15, 15));
        coronalPanel.setName(""); // NOI18N
        coronalPanel.setPreferredSize(new java.awt.Dimension(295, 329));

        coronalLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        coronalLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coronalLabel1.setText("<html> <font size=4 color=#1aff1a><strong>R</strong><font>");
        coronalLabel1.setToolTipText("");

        coronalLabel.setBackground(new java.awt.Color(0, 0, 0));
        coronalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coronalLabel.setToolTipText("");
        coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        coronalLabel.setMinimumSize(new java.awt.Dimension(10, 10));
        coronalLabel.setName(""); // NOI18N
        coronalLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                coronalLabelMouseDragged(evt);
            }
        });
        coronalLabel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                coronalLabelMouseWheelMoved(evt);
            }
        });
        coronalLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                coronalLabelMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                coronalLabelMouseReleased(evt);
            }
        });

        coronalLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        coronalLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coronalLabel3.setText("<html> <font size=4 color=#1aff1a><strong>L</strong><font>");
        coronalLabel3.setToolTipText("");

        coronalLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        coronalLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coronalLabel2.setText("<html>\n<font size=4 color=#1aff1a><strong>S</strong><font>");
        coronalLabel2.setToolTipText("");

        coronalLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        coronalLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coronalLabel4.setText("<html> <font size=4 color=#1aff1a><strong>I</strong><font>");
        coronalLabel4.setToolTipText("");

        coronalSlider.setBackground(new java.awt.Color(0, 0, 0));
        coronalSlider.setMinorTickSpacing(1);
        coronalSlider.setSnapToTicks(true);
        coronalSlider.setToolTipText("");
        coronalSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        coronalSlider.setExtent(1);
        coronalSlider.setFocusCycleRoot(true);
        coronalSlider.setMinimumSize(new java.awt.Dimension(10, 5));
        coronalSlider.setModel(getSliderModel());
        coronalSlider.setName(""); // NOI18N
        coronalSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                coronalSliderStateChanged(evt);
            }
        });

        javax.swing.GroupLayout coronalPanelLayout = new javax.swing.GroupLayout(coronalPanel);
        coronalPanel.setLayout(coronalPanelLayout);
        coronalPanelLayout.setHorizontalGroup(
            coronalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coronalPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(coronalLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(coronalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(coronalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(coronalLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coronalSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(coronalLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(coronalLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        coronalPanelLayout.setVerticalGroup(
            coronalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coronalPanelLayout.createSequentialGroup()
                .addComponent(coronalSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(coronalLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(coronalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(coronalLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coronalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(coronalLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(coronalLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        axialPanel.setBackground(new java.awt.Color(0, 0, 0));
        axialPanel.setMinimumSize(new java.awt.Dimension(15, 15));
        axialPanel.setPreferredSize(new java.awt.Dimension(579, 560));
        axialPanel.setRequestFocusEnabled(false);

        axialLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        axialLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        axialLabel1.setText("<html> <font size=4 color=#1aff1a><strong>R</strong><font>");

        axialLabel.setBackground(new java.awt.Color(0, 0, 0));
        axialLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        axialLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        axialLabel.setMinimumSize(new java.awt.Dimension(10, 10));
        axialLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                axialLabelMouseDragged(evt);
            }
        });
        axialLabel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                axialLabelMouseWheelMoved(evt);
            }
        });
        axialLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                axialLabelMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                axialLabelMouseReleased(evt);
            }
        });

        axialLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        axialLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        axialLabel2.setText("<html> <font size=4 color=#1aff1a><strong>A</strong><font>");
        axialLabel2.setToolTipText("");

        axialLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        axialLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        axialLabel3.setText("<html> <font size=4 color=#1aff1a><strong>L</strong><font>");

        axialSlider.setBackground(new java.awt.Color(0, 0, 0));
        axialSlider.setMinorTickSpacing(1);
        axialSlider.setSnapToTicks(true);
        axialSlider.setMinimumSize(new java.awt.Dimension(10, 5));
        axialSlider.setModel(getSliderModel());
        axialSlider.setName(""); // NOI18N
        axialSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                axialSliderStateChanged(evt);
            }
        });

        axialLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        axialLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        axialLabel4.setText("<html> <font size=4 color=#1aff1a><strong>P</strong><font>");
        axialLabel4.setToolTipText("");

        javax.swing.GroupLayout axialPanelLayout = new javax.swing.GroupLayout(axialPanel);
        axialPanel.setLayout(axialPanelLayout);
        axialPanelLayout.setHorizontalGroup(
            axialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(axialPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(axialLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(axialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(axialSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(axialLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(axialPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(axialLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(axialLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(axialLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        axialPanelLayout.setVerticalGroup(
            axialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(axialPanelLayout.createSequentialGroup()
                .addComponent(axialLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(axialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(axialLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(axialLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(axialLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(axialLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(axialSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        saggitalPanel.setBackground(new java.awt.Color(0, 0, 0));
        saggitalPanel.setMinimumSize(new java.awt.Dimension(15, 15));
        saggitalPanel.setPreferredSize(new java.awt.Dimension(569, 552));

        saggitalLabel.setBackground(new java.awt.Color(0, 0, 0));
        saggitalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        saggitalLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saggitalLabel.setMinimumSize(new java.awt.Dimension(10, 10));
        saggitalLabel.setName(""); // NOI18N
        saggitalLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                saggitalLabelMouseDragged(evt);
            }
        });
        saggitalLabel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                saggitalLabelMouseWheelMoved(evt);
            }
        });
        saggitalLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saggitalLabelMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                saggitalLabelMouseReleased(evt);
            }
        });

        saggitalLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        saggitalLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        saggitalLabel3.setText("<html> <font size=4 color=#1aff1a><strong>A</strong><font>");

        saggitalLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        saggitalLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        saggitalLabel2.setText("<html> <font size=4 color=#1aff1a><strong>P</strong><font>");
        saggitalLabel2.setToolTipText("");

        saggitalLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        saggitalLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        saggitalLabel4.setText("<html> <font size=4 color=#1aff1a><strong>S</strong><font>");
        saggitalLabel4.setToolTipText("");

        saggitalLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        saggitalLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        saggitalLabel5.setText("<html> <font size=4 color=#1aff1a><strong>I</strong><font>");
        saggitalLabel5.setToolTipText("");

        saggitalSlider.setBackground(new java.awt.Color(0, 0, 0));
        saggitalSlider.setMinorTickSpacing(1);
        saggitalSlider.setSnapToTicks(true);
        saggitalSlider.setToolTipText("");
        saggitalSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        saggitalSlider.setMinimumSize(new java.awt.Dimension(10, 5));
        saggitalSlider.setModel( getSliderModel());
        saggitalSlider.setName(""); // NOI18N
        saggitalSlider.setPreferredSize(new java.awt.Dimension(256, 26));
        saggitalSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                saggitalSliderStateChanged(evt);
            }
        });

        javax.swing.GroupLayout saggitalPanelLayout = new javax.swing.GroupLayout(saggitalPanel);
        saggitalPanel.setLayout(saggitalPanelLayout);
        saggitalPanelLayout.setHorizontalGroup(
            saggitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saggitalPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(saggitalLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(saggitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(saggitalSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(saggitalLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saggitalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saggitalLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(saggitalLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        saggitalPanelLayout.setVerticalGroup(
            saggitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, saggitalPanelLayout.createSequentialGroup()
                .addComponent(saggitalSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(saggitalLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(saggitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(saggitalLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saggitalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(saggitalLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(saggitalLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );

        jLabel1.setText("X:");
        jLabel1.setPreferredSize(new java.awt.Dimension(2, 2));

        jLabel2.setText("Y:");
        jLabel2.setPreferredSize(new java.awt.Dimension(2, 2));

        jLabel3.setText("Z:");
        jLabel3.setPreferredSize(new java.awt.Dimension(2, 2));

        volSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        volSpinner.setPreferredSize(new java.awt.Dimension(50, 22));
        volSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volSpinnerStateChanged(evt);
            }
        });

        jLabel4.setText("Volume:");

        xSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        xSpinner.setMinimumSize(new java.awt.Dimension(50, 22));
        xSpinner.setName(""); // NOI18N
        xSpinner.setPreferredSize(new java.awt.Dimension(50, 22));
        xSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xSpinnerStateChanged(evt);
            }
        });

        ySpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        ySpinner.setPreferredSize(new java.awt.Dimension(50, 22));
        ySpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ySpinnerStateChanged(evt);
            }
        });

        zSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        zSpinner.setPreferredSize(new java.awt.Dimension(50, 22));
        zSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zSpinnerStateChanged(evt);
            }
        });

        yPosLabel.setText(" ");

        xPosLabel.setText(" ");
        xPosLabel.setToolTipText("");

        neuroView.setSelected(true);
        neuroView.setText("Neurological View");
        neuroView.setSelected(true);
        neuroView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neuroViewActionPerformed(evt);
            }
        });

        radioView.setText("Radiological View");
        radioView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioViewActionPerformed(evt);
            }
        });

        colorBar.setToolTipText("");
        colorBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbm.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cbm.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cbm.setText("_");
        cbm.setToolTipText("");
        cbm.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        cbm.setAlignmentY(0.0F);
        cbm.setIconTextGap(0);
        cbm.setMaximumSize(new java.awt.Dimension(7, 15));
        cbm.setMinimumSize(new java.awt.Dimension(7, 15));
        cbm.setName(""); // NOI18N
        cbm.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        cbm1.setText("_");
        cbm1.setToolTipText("");
        cbm1.setMaximumSize(new java.awt.Dimension(7, 15));
        cbm1.setMinimumSize(new java.awt.Dimension(7, 15));
        cbm1.setName(""); // NOI18N

        colorBar12.setText("_       ");
        colorBar12.setToolTipText("");
        colorBar12.setMaximumSize(new java.awt.Dimension(7, 15));
        colorBar12.setMinimumSize(new java.awt.Dimension(7, 15));
        colorBar12.setName(""); // NOI18N

        colorBar34.setText("_       ");
        colorBar34.setToolTipText("");
        colorBar34.setMaximumSize(new java.awt.Dimension(7, 15));
        colorBar34.setMinimumSize(new java.awt.Dimension(7, 15));
        colorBar34.setName(""); // NOI18N

        colorBar14.setText("_       ");
        colorBar14.setToolTipText("");
        colorBar14.setMaximumSize(new java.awt.Dimension(7, 15));
        colorBar14.setMinimumSize(new java.awt.Dimension(7, 15));
        colorBar14.setName(""); // NOI18N

        colorBarMax.setText("Max");
        colorBarMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorBarMaxActionPerformed(evt);
            }
        });

        colorBarMin.setText("Min");
        colorBarMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorBarMinActionPerformed(evt);
            }
        });

        xhairButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/crosshair.png"))); // NOI18N
        xhairButton.setSelected(true);
        xhairButton.setToolTipText("");
        xhairButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xhairButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(xhairButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(volSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(infoPanelLayout.createSequentialGroup()
                                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(zSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(xSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(yPosLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                            .addComponent(zPosLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(xPosLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addComponent(neuroView)
                            .addComponent(radioView, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(colorBar12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(colorBar14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(colorBar34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(infoPanelLayout.createSequentialGroup()
                                        .addComponent(cbm1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)
                                        .addComponent(colorBarMin, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, infoPanelLayout.createSequentialGroup()
                                .addComponent(colorBar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(cbm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(colorBarMax, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xPosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yPosLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(zPosLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(zSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(volSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(neuroView)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioView))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(colorBarMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, infoPanelLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(cbm, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(colorBar34, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(colorBar12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(colorBar14, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(cbm1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(colorBarMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))))
                .addGap(26, 26, 26)
                .addComponent(xhairButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(colorBar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        valueLabel.setText("0.00");

        fileMenu.setText("File");

        openVolumeMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openVolumeMenu.setIcon(IconGetter.getProjectImageIcon("open_icon.png")
        );
        openVolumeMenu.setText("Open Nifti");
        openVolumeMenu.setToolTipText("");
        openVolumeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openVolumeMenuActionPerformed(evt);
            }
        });
        fileMenu.add(openVolumeMenu);

        openOverlayMenu.setIcon(IconGetter.getProjectImageIcon("overlay_icon.png")
        );
        openOverlayMenu.setText("Overlay Volume");
        openOverlayMenu.setToolTipText("");
        openOverlayMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openOverlayMenuActionPerformed(evt);
            }
        });
        fileMenu.add(openOverlayMenu);

        jMenuBar1.add(fileMenu);

        viewMenu.setText("View");

        colormapMenu.setIcon(IconGetter.getProjectImageIcon("colorbar_icon.png"));
        colormapMenu.setText("Color scale");
        colormapMenu.setToolTipText("");

        grayScale.setSelected(true);
        grayScale.setText("Grayscale");
        grayScale.setToolTipText("");
        grayScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grayScaleActionPerformed(evt);
            }
        });
        colormapMenu.add(grayScale);

        hotScale.setText("Hot");
        hotScale.setToolTipText("");
        hotScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotScaleActionPerformed(evt);
            }
        });
        colormapMenu.add(hotScale);

        hotInvertScale.setText("Hot (Inverted)");
        hotInvertScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotInvertScaleActionPerformed(evt);
            }
        });
        colormapMenu.add(hotInvertScale);

        winterScale.setText("Winter");
        winterScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                winterScaleActionPerformed(evt);
            }
        });
        colormapMenu.add(winterScale);

        winterInvertScale.setText("Winter (Inverted)");
        winterInvertScale.setToolTipText("");
        winterInvertScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                winterInvertScaleActionPerformed(evt);
            }
        });
        colormapMenu.add(winterInvertScale);

        rainbowScale.setText("Rainbow");
        rainbowScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rainbowScaleActionPerformed(evt);
            }
        });
        colormapMenu.add(rainbowScale);

        resetColorScale.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        resetColorScale.setText("Reset Colorscale");
        resetColorScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetColorScaleActionPerformed(evt);
            }
        });
        colormapMenu.add(resetColorScale);

        viewMenu.add(colormapMenu);
        viewMenu.add(jSeparator1);

        crosshairMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        crosshairMenu.setSelected(true);
        crosshairMenu.setText("Crosshair");
        crosshairMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crosshairMenuActionPerformed(evt);
            }
        });
        viewMenu.add(crosshairMenu);

        zoomMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.ALT_MASK));
        zoomMenu.setText("Zoom");
        zoomMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomMenuActionPerformed(evt);
            }
        });
        viewMenu.add(zoomMenu);

        panMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK));
        panMenu.setText("Pan");
        panMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panMenuActionPerformed(evt);
            }
        });
        viewMenu.add(panMenu);

        restoreZoomMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MINUS, java.awt.event.InputEvent.ALT_MASK));
        restoreZoomMenu.setText("Restore zoom");
        restoreZoomMenu.setActionCommand("Reset Zoom");
        restoreZoomMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restoreZoomMenuActionPerformed(evt);
            }
        });
        viewMenu.add(restoreZoomMenu);

        mosaicViewMenu.setText("Mosaic View");
        mosaicViewMenu.setToolTipText("");
        mosaicViewMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mosaicViewMenuActionPerformed(evt);
            }
        });
        viewMenu.add(mosaicViewMenu);

        jMenuBar1.add(viewMenu);

        volumeMenu.setText("Volume");
        neuroView.setSelected(true);

        volumeSelect.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK));
        volumeSelect.setSelected(true);
        volumeSelect.setText("Volume");
        volumeSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volumeSelectActionPerformed(evt);
            }
        });
        volumeMenu.add(volumeSelect);

        overlaySelect.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK));
        overlaySelect.setText("Overlay");
        overlaySelect.setToolTipText("");
        overlaySelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                overlaySelectActionPerformed(evt);
            }
        });
        volumeMenu.add(overlaySelect);

        jMenuBar1.add(volumeMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(valueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(axialPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(coronalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saggitalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 282, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(coronalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(saggitalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(axialPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valueLabel)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**********Slider action**********/
    private void coronalSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_coronalSliderStateChanged
        drawNiftiSlice(coronalSlider,ySpinner);
    }//GEN-LAST:event_coronalSliderStateChanged
    private void saggitalSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_saggitalSliderStateChanged
        drawNiftiSlice(saggitalSlider,xSpinner);
    }//GEN-LAST:event_saggitalSliderStateChanged
    private void axialSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_axialSliderStateChanged
         drawNiftiSlice(axialSlider,zSpinner);
    }//GEN-LAST:event_axialSliderStateChanged
    /*****Resize window*******/
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
            resizeGraphs();
    }//GEN-LAST:event_formComponentResized
    /****Open main volume****/
    private void openVolumeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openVolumeMenuActionPerformed
        
        int returnVal = fc.showOpenDialog(BarracudaViewUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String filename = file.getAbsolutePath();
        //Try to read nifti file    
            try{
                //Set neurological view as default
                resetGrayScale();
                niiVol=null;
                overlayVol=null;
                System.gc();
                neuroView.setSelected(true);
                viewState=true;
                //Set Orientation Labels
                coronalLabel1.setText("<html> <font size=4 color=#1aff1a><strong>R</strong><font>");
                coronalLabel3.setText("<html> <font size=4 color=#1aff1a><strong>L</strong><font>");
                axialLabel1.setText("<html> <font size=4 color=#1aff1a><strong>R</strong><font>");
                axialLabel3.setText("<html> <font size=4 color=#1aff1a><strong>L</strong><font>");
                //Read Nifti file and initialize drawable copy
                niiVol=new DrawableNiftiVolume(NiftiVolume.read(filename));
                if(niiVol!=null){
                    volumeSelect.setSelected(true);
                    // Added code so default view would be neurological
                    if(niiVol.orient[0]=='L'){niiVol.orient[0]='R';}
                    else if(niiVol.orient[0]=='R'){niiVol.orient[0]='L';}
                    else{
                        errorLabel.setText("Invalid orientation");
                        errorDialog.setLocationRelativeTo(null);
                        errorDialog.setVisible(true);
                    }
                    //Set Color Bar
                    setColorBar();
                    //Set spinner models
                    if(niiVol.header.dim[4]<=0){niiVol.header.dim[4]=1;}
                    SpinnerNumberModel model = new SpinnerNumberModel(0, 0,niiVol.header.dim[4]-1,1);
                    volSpinner.setModel(model);
                    model = new SpinnerNumberModel(0,0,niiVol.header.dim[1]-1,1);
                    xSpinner.setModel(model);
                    model = new SpinnerNumberModel(0,0,niiVol.header.dim[2]-1,1);
                    ySpinner.setModel(model);
                    model = new SpinnerNumberModel(0,0,niiVol.header.dim[3]-1,1);
                    zSpinner.setModel(model);
                    //Set slider values
                    saggitalSlider.setMaximum(niiVol.header.dim[1]-1);
                    saggitalSlider.setValue((int)(niiVol.header.dim[1]/2));
                    coronalSlider.setMaximum(niiVol.header.dim[2]-1);
                    axialSlider.setMaximum(niiVol.header.dim[3]-1);
                    coronalSlider.setValue((int)(niiVol.header.dim[2]/2));
                    axialSlider.setValue((int)(niiVol.header.dim[3]/2));
                    //Set crosshair display
                    crosshairMenu.setSelected(true);
                    drawLabelsXHair();
                    //Get rotation matrix
                    //R=niiVol.header.mat33();
                    rotationMtrx=new Matrix(niiVol.header.mat33());
                    //Set unit labels
                    setXYZLabels();
                }
            }
            catch (IOException e){
                errorLabel.setText("Error: Cannot open file");
                errorDialog.setLocationRelativeTo(null);
                errorDialog.setVisible(true);
            }
        } else {
            returnVal=0;
        }
    }//GEN-LAST:event_openVolumeMenuActionPerformed
    /******Spinner Behavior******/
    private void volSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volSpinnerStateChanged
        if(niiVol!=null){
            // Get volume maximum
            niiVol.setMax3D((int)volSpinner.getValue());
            niiVol.setMin3D((int)volSpinner.getValue());
            if(overlayVol!=null){
                // Get volume maximum
                overlayVol.setMax3D((int)volSpinner.getValue());
                overlayVol.setMin3D((int)volSpinner.getValue());
            }
            setXYZLabels();
        }
        drawNifti();
    }//GEN-LAST:event_volSpinnerStateChanged
    private void ySpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ySpinnerStateChanged
        coronalSlider.setValue((int)ySpinner.getValue());
    }//GEN-LAST:event_ySpinnerStateChanged
    private void xSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xSpinnerStateChanged
            saggitalSlider.setValue((int)xSpinner.getValue());
    }//GEN-LAST:event_xSpinnerStateChanged
    private void zSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zSpinnerStateChanged
        axialSlider.setValue((int)zSpinner.getValue());
    }//GEN-LAST:event_zSpinnerStateChanged
    /********Mouse Actions***************/
    //Coronal mouse actions
    private void coronalLabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coronalLabelMouseDragged
        
        if(niiVol!=null){
            if(SwingUtilities.isLeftMouseButton(evt)){
                if(crosshairMenu.isSelected()){
                    coronalMouseXHair();
                }else if(zoomMenu.isSelected()){
                    if(zoomStartPoint==null){
                        zoomStartPoint=coronalLabel.getMousePosition(false);
                    }else{
                        zoomEndPoint=coronalLabel.getMousePosition(false);
                        drawCoronalZoomBox();
                    }
                }else if(panMenu.isSelected()){
                    coronalPan();
                }
            }else if(SwingUtilities.isRightMouseButton(evt)){
                if(crosshairMenu.isSelected()){
                    try{
                        mouseAdjustMax(coronalLabel);
                        updateMaxColorbar();
                    }catch(Exception e){}
                    }
            }else if(SwingUtilities.isMiddleMouseButton(evt)){
                coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
                coronalPan();
                if(crosshairMenu.isSelected()){
                        coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                    }else if(zoomMenu.isSelected()){
                        coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                    }
            }  
        }
    }//GEN-LAST:event_coronalLabelMouseDragged
    private void coronalLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coronalLabelMouseClicked
        if(niiVol!=null){
            if(SwingUtilities.isLeftMouseButton(evt)){
                if(crosshairMenu.isSelected()){
                    coronalMouseXHair();
                }
                else if(panMenu.isSelected()){
                    coronalPan();
                }
            }else if(SwingUtilities.isRightMouseButton(evt)){
                prevMouse=coronalLabel.getMousePosition(false);
            }
            else if(SwingUtilities.isMiddleMouseButton(evt)){
                coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
                panPoint=coronalLabel.getMousePosition(false);
                if(crosshairMenu.isSelected()){
                        coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                }else if(zoomMenu.isSelected()){
                        coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            }
        }
    }//GEN-LAST:event_coronalLabelMouseClicked
    private void coronalLabelMouseReleased(java.awt.event.MouseEvent evt) {                                           
	if(niiVol!=null){
            if(zoomMenu.isSelected()){
                if(SwingUtilities.isLeftMouseButton(evt)){
                    if(zoomStartPoint!=null){
                        zoomEndPoint=coronalLabel.getMousePosition(false);
                        setCoronalZoom();
                        zoomStartPoint=null;
                        zoomEndPoint=null; 
                    }
                }else if(SwingUtilities.isRightMouseButton(evt)){
                    niiVol.clearDrawRange();
                    zoomStartPoint=null;
                    zoomEndPoint=null;
                    if(overlayVol==null){
                        drawAllSlices();
                    }else{
                        overlayVol.clearDrawRange();
                        drawAllSlicesOverlay();
                    }
                    
                }
            }
        }
    }                                             
    //Saggital mouse actions
    private void saggitalLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saggitalLabelMouseClicked
        if(niiVol!=null){
            if(SwingUtilities.isLeftMouseButton(evt)){
                if(crosshairMenu.isSelected()){
                    saggitalMouseXHair();
                }   
                else if(panMenu.isSelected()){
                    saggitalPan();
                }
            }else if(SwingUtilities.isRightMouseButton(evt)){
                prevMouse=saggitalLabel.getMousePosition(false);
            }
            else if(SwingUtilities.isMiddleMouseButton(evt)){
                saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
                panPoint=saggitalLabel.getMousePosition(false);
                if(crosshairMenu.isSelected()){
                        saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                }else if(zoomMenu.isSelected()){
                        saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            }
        }
    }//GEN-LAST:event_saggitalLabelMouseClicked
    private void saggitalLabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saggitalLabelMouseDragged
        if(niiVol!=null){
            if(SwingUtilities.isLeftMouseButton(evt)){
                if(crosshairMenu.isSelected()){
                    saggitalMouseXHair();
                }else if(zoomMenu.isSelected()){
                    if(zoomStartPoint==null){
                        zoomStartPoint=saggitalLabel.getMousePosition(false);
                    }else{
                        zoomEndPoint=saggitalLabel.getMousePosition(false);
                        drawSaggitalZoomBox();
                    }
                }else if(panMenu.isSelected()){
                    saggitalPan();
                }
            }else if(SwingUtilities.isRightMouseButton(evt)){
                if(crosshairMenu.isSelected()){
                    try{
                        mouseAdjustMax(saggitalLabel);
                        updateMaxColorbar();
                    }catch(Exception e){}
                    }
            }else if(SwingUtilities.isMiddleMouseButton(evt)){
                saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
                saggitalPan();
                if(crosshairMenu.isSelected()){
                    saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                }else if(zoomMenu.isSelected()){
                    saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            }  
        }
    }//GEN-LAST:event_saggitalLabelMouseDragged
    private void saggitalLabelMouseReleased(java.awt.event.MouseEvent evt) {                                             
	   if(niiVol!=null){
				if(zoomMenu.isSelected()){
					if(SwingUtilities.isLeftMouseButton(evt)){
						if(zoomStartPoint!=null){
							zoomEndPoint=saggitalLabel.getMousePosition(false);
							setSaggitalZoom();
							zoomStartPoint=null;
							zoomEndPoint=null; 
						}
					}else if(SwingUtilities.isRightMouseButton(evt)){
						niiVol.clearDrawRange();
						zoomStartPoint=null;
						zoomEndPoint=null;
						if(overlayVol==null){
                                                    drawAllSlices();
                                                }else{
                                                    overlayVol.clearDrawRange();
                                                    drawAllSlicesOverlay();
                                                }
					}
				}
			}
	}                                           
    //Axial mouse actions
    private void axialLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_axialLabelMouseClicked
        if(niiVol!=null){
            if(SwingUtilities.isLeftMouseButton(evt)){
            if(crosshairMenu.isSelected()){
                    axialMouseXHair();
                }
            }else if(SwingUtilities.isRightMouseButton(evt)){
                prevMouse=axialLabel.getMousePosition(false);
            }
            else if(panMenu.isSelected()){
                    axialPan();
            }
            else if(SwingUtilities.isMiddleMouseButton(evt)){
                axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
                panPoint=axialLabel.getMousePosition(false);
                if(crosshairMenu.isSelected()){
                        axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                }else if(zoomMenu.isSelected()){
                        axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            }
        }
    }//GEN-LAST:event_axialLabelMouseClicked
    private void axialLabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_axialLabelMouseDragged
         if(niiVol!=null){
            if(SwingUtilities.isLeftMouseButton(evt)){
                if(crosshairMenu.isSelected()){
                    axialMouseXHair();
                }else if(zoomMenu.isSelected()){
                    if(zoomStartPoint==null){
                        zoomStartPoint=axialLabel.getMousePosition(false);
                    }else{
                        zoomEndPoint=axialLabel.getMousePosition(false);
                        drawAxialZoomBox();
                    }
                }
                else if(panMenu.isSelected()){
                    axialPan();
                }
            }else if(SwingUtilities.isRightMouseButton(evt)){
                if(crosshairMenu.isSelected()){
                    try{
                        mouseAdjustMax(axialLabel);
                        updateMaxColorbar();
                    }catch(Exception e){}
                    }
            }else if(SwingUtilities.isMiddleMouseButton(evt)){
                axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
                axialPan();
                if(crosshairMenu.isSelected()){
                    axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                }else if(zoomMenu.isSelected()){
                    axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            }  
        }
    }//GEN-LAST:event_axialLabelMouseDragged
    private void axialLabelMouseReleased(java.awt.event.MouseEvent evt) {                                         
		if(niiVol!=null){
            if(zoomMenu.isSelected()){
                if(SwingUtilities.isLeftMouseButton(evt)){
                    if(zoomStartPoint!=null){
                        zoomEndPoint=axialLabel.getMousePosition(false);
                        setAxialZoom();
                        zoomStartPoint=null;
                        zoomEndPoint=null; 
                    }
                }else if(SwingUtilities.isRightMouseButton(evt)){
                    niiVol.clearDrawRange();
                    zoomStartPoint=null;
                    zoomEndPoint=null;
                    if(overlayVol==null){
                        drawAllSlices();
                    }else{
                        overlayVol.clearDrawRange();
                        drawAllSlicesOverlay();
                    }
                }
            }
        }
    }                                           
    /****ColorBar menu actions*****/	
    private void grayScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grayScaleActionPerformed
        if(niiVol!=null & volumeSelect.isSelected()){
            colorScale="grayscale";
            setColorBar();
            drawNifti();
        }
        if(overlayVol!=null & !volumeSelect.isSelected()){
            colorScaleOverlay="grayscale";
            setColorBar();
            drawNifti();
        } 
    }//GEN-LAST:event_grayScaleActionPerformed
    private void hotScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotScaleActionPerformed
        if(niiVol!=null & volumeSelect.isSelected()){
            colorScale="hot";
            setColorBar();
            drawNifti();
        }
        if(overlayVol!=null & !volumeSelect.isSelected()){
            colorScaleOverlay="hot";
            setColorBar();
            drawNifti();
        } 
    }//GEN-LAST:event_hotScaleActionPerformed
    private void hotInvertScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotInvertScaleActionPerformed
        if(niiVol!=null){
            if(niiVol!=null & volumeSelect.isSelected()){
            colorScale="hot_invert";
            setColorBar();
            drawNifti();
        }
        if(overlayVol!=null & !volumeSelect.isSelected()){
            colorScaleOverlay="hot_invert";
            setColorBar();
            drawNifti();
        } 
        }
    }//GEN-LAST:event_hotInvertScaleActionPerformed
    private void winterScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_winterScaleActionPerformed
        if(niiVol!=null){
            if(niiVol!=null & volumeSelect.isSelected()){
            colorScale="winter";
            setColorBar();
            drawNifti();
        }
        if(overlayVol!=null & !volumeSelect.isSelected()){
            colorScaleOverlay="winter";
            setColorBar();
            drawNifti();
        } 
        }
    }//GEN-LAST:event_winterScaleActionPerformed
    private void winterInvertScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_winterInvertScaleActionPerformed
        if(niiVol!=null){
            if(niiVol!=null & volumeSelect.isSelected()){
            colorScale="winter_invert";
            setColorBar();
            drawNifti();
        }
        if(overlayVol!=null & !volumeSelect.isSelected()){
            colorScaleOverlay="winter_invert";
            setColorBar();
            drawNifti();
        } 
        }
    }//GEN-LAST:event_winterInvertScaleActionPerformed
    private void rainbowScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rainbowScaleActionPerformed
        if(niiVol!=null & volumeSelect.isSelected()){
            colorScale="rainbow";
            setColorBar();
            drawNifti();
        }
        if(overlayVol!=null & !volumeSelect.isSelected()){
            colorScaleOverlay="rainbow";
            setColorBar();
            drawNifti();
        } 
    }//GEN-LAST:event_rainbowScaleActionPerformed
    private void resetColorScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetColorScaleActionPerformed
        if(niiVol!=null){
            niiVol.setMax3D((int)volSpinner.getValue());
            if(overlayVol!=null){
                overlayVol.setMax3D((int)volSpinner.getValue());
            }
            drawNifti();
            updateMaxColorbar();
        }
    }//GEN-LAST:event_resetColorScaleActionPerformed
    private void colorBarMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorBarMinActionPerformed
       if(niiVol!=null){
            String minString=colorBarMin.getText();
           try{
                double min=Double.parseDouble(minString);
                if(min>niiVol.getMax()){
                    errorLabel.setText("Min > max : not valid");
                    errorDialog.setLocationRelativeTo(null);
                    errorDialog.setVisible(true);
                    if(volumeSelect.isSelected()){
                        minString=String.format("%.3f",niiVol.getMin());
                    }else{
                        minString=String.format("%.3f",overlayVol.getMin());
                    }
                    colorBarMax.setText(minString);
                }
                else{
                    if(volumeSelect.isSelected()){
                        niiVol.setMin(min);
                    }else{
                        overlayVol.setMin(min);
                    }
                    
                    drawNifti();
                }

           }catch(Exception e){
               errorLabel.setText("Must be a valid number :" +e.getMessage());
               errorDialog.setLocationRelativeTo(null);
               errorDialog.setVisible(true);
               if(volumeSelect.isSelected()){
                    minString=String.format("%.3f",niiVol.getMin());
               }else{
                    minString=String.format("%.3f",overlayVol.getMin());
               }
               colorBarMax.setText(minString);
           }
       }
    }//GEN-LAST:event_colorBarMinActionPerformed
    private void colorBarMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorBarMaxActionPerformed
        if(niiVol!=null){
            String maxString=colorBarMax.getText();
           try{
                double max=Double.parseDouble(maxString);
                if(niiVol.getMin()>max){
                    errorLabel.setText("Max < Min : not valid");
                    errorDialog.setLocationRelativeTo(null);
                    errorDialog.setVisible(true);
                    if(volumeSelect.isSelected()){
                        maxString=String.format("%.3f",niiVol.getMax());
                    }else{
                        maxString=String.format("%.3f",overlayVol.getMax());
                    }
                    colorBarMax.setText(maxString);
                }
                else{
                    if(volumeSelect.isSelected()){
                        niiVol.setMax(max);
                    }else{
                        overlayVol.setMax(max);
                    }
                    
                    drawNifti();
                }

           }catch(Exception e){
               errorLabel.setText("Must be a valid number :" +e.getMessage());
               errorDialog.setLocationRelativeTo(null);
               errorDialog.setVisible(true);
               if(volumeSelect.isSelected()){
                    maxString=String.format("%.3f",niiVol.getMax());
               }else{
                    maxString=String.format("%.3f",overlayVol.getMax());
               }
               colorBarMax.setText(maxString);
           }
       }
    }//GEN-LAST:event_colorBarMaxActionPerformed
    /*****Radiological or neurological view******/
    private void neuroViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neuroViewActionPerformed
        boolean bool =neuroView.isSelected();
        if(bool!=viewState){
        if(niiVol!=null){
            if(niiVol.orient[0]=='L'){
                niiVol.orient[0]='R';
            }else{
                niiVol.orient[0]='L';
            }
            if(overlayVol!=null){
                if(overlayVol.orient[0]=='L'){
                    overlayVol.orient[0]='R';
                }else{
                    overlayVol.orient[0]='L';
                }
            }
            coronalLabel1.setText("<html> <font size=4 color=#1aff1a><strong>R</strong><font>");
            coronalLabel3.setText("<html> <font size=4 color=#1aff1a><strong>L</strong><font>");
            axialLabel1.setText("<html> <font size=4 color=#1aff1a><strong>R</strong><font>");
            axialLabel3.setText("<html> <font size=4 color=#1aff1a><strong>L</strong><font>");
            drawNifti();
            viewState=true;
        }
        }
    }//GEN-LAST:event_neuroViewActionPerformed
    private void radioViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioViewActionPerformed
        boolean bool=!radioView.isSelected();
        if(bool!=viewState){
        if(niiVol!=null){
            if(niiVol.orient[0]=='L'){
                niiVol.orient[0]='R';
            }else{
                niiVol.orient[0]='L';
            }
            if(overlayVol!=null){
                if(overlayVol.orient[0]=='L'){
                    overlayVol.orient[0]='R';
                }else{
                    overlayVol.orient[0]='L';
                }
            }
            coronalLabel1.setText("<html> <font size=4 color=#1aff1a><strong>L</strong><font>");
            coronalLabel3.setText("<html> <font size=4 color=#1aff1a><strong>R</strong><font>");
            axialLabel1.setText("<html> <font size=4 color=#1aff1a><strong>L</strong><font>");
            axialLabel3.setText("<html> <font size=4 color=#1aff1a><strong>R</strong><font>");
            drawNifti();
            viewState=false;
        }
        }
    }//GEN-LAST:event_radioViewActionPerformed
    /*****Other Menu actions*****/
    private void restoreZoomMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreZoomMenuActionPerformed
       if(niiVol!=null){
            niiVol.clearDrawRange();
            if(overlayVol!=null){
                overlayVol.clearDrawRange();
                drawAllSlicesOverlay();
            }
            else{
                drawAllSlices();  
            }
            zoomStartPoint=null;
            zoomEndPoint=null;
       }
    }//GEN-LAST:event_restoreZoomMenuActionPerformed
    private void crosshairMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crosshairMenuActionPerformed
        coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        if(niiVol!=null)
        drawLabelsXHair();
        
    }//GEN-LAST:event_crosshairMenuActionPerformed
    private void zoomMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomMenuActionPerformed
        coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        if(niiVol!=null){
           if(overlayVol!=null){
                drawAllSlicesOverlay();
           }else{
                drawAllSlices();
           }
       }
    }//GEN-LAST:event_zoomMenuActionPerformed

    private void openOverlayMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openOverlayMenuActionPerformed
        if(niiVol!=null){    
            int returnVal = fc.showOpenDialog(BarracudaViewUI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String filename = file.getAbsolutePath();
            //Try to read nifti file    
                try{
                    overlayVol=null;
                    System.gc();
                    overlayVol=new DrawableNiftiVolume(NiftiVolume.read(filename));
                    if(overlayVol!=null){
                        niiVol.clearDrawRange();
                        overlayVol.clearDrawRange();
                        if(overlayVol.header.dim[4]<=0){overlayVol.header.dim[4]=1;}
                        if(overlayVol.header.dim[1]==niiVol.header.dim[1] &
                           overlayVol.header.dim[2]==niiVol.header.dim[2] &
                           overlayVol.header.dim[3]==niiVol.header.dim[3] &
                           overlayVol.header.dim[4]==niiVol.header.dim[4] &
                           overlayVol.header.orientation().equals(niiVol.header.orientation())){
                            
                            if(neuroView.isSelected()){
                                // Added code so default view would be neurological
                                if(overlayVol.orient[0]=='L'){overlayVol.orient[0]='R';}
                                else if(overlayVol.orient[0]=='R'){overlayVol.orient[0]='L';}
                                else{
                                    errorLabel.setText("Error");
                                    errorDialog.setLocationRelativeTo(null);
                                    errorDialog.setVisible(true);
                                }
                                if(zoomMenu.isSelected()){
                                    drawNifti();
                                }else{
                                    drawLabelsXHair();
                                }
                                
                            }
                        overlaySelect.setSelected(true);   
                        setColorBar();
                        }else{
                            if(!overlayVol.header.orientation().equals(niiVol.header.orientation())){
                                errorLabel.setText("Volumes are not in the same space!");
                            }else{
                                errorLabel.setText("Dimensions are not the same!");
                            }
                            errorDialog.setLocationRelativeTo(null);
                            errorDialog.setVisible(true);
                            overlayVol=null;
                        }
                    }
                }
                catch (IOException e){
                    errorLabel.setText("Error: "+ e.getMessage());
                    errorDialog.setVisible(true);
                    errorDialog.setLocationRelativeTo(null);
                }
            } else {
                returnVal=0;
            }
        }else{
            
        }
    }//GEN-LAST:event_openOverlayMenuActionPerformed

    private void overlaySelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overlaySelectActionPerformed
        if(overlayVol==null){
            volumeSelect.setSelected(true);
        }else{
            setColorBar();
            setXYZLabels();
        }
    }//GEN-LAST:event_overlaySelectActionPerformed

    private void volumeSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volumeSelectActionPerformed
        setColorBar();
        setXYZLabels();
    }//GEN-LAST:event_volumeSelectActionPerformed

    private void panMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_panMenuActionPerformed
        coronalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        saggitalLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        axialLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        if(niiVol!=null){
           if(overlayVol!=null){
                drawAllSlicesOverlay();
           }else{
                drawAllSlices();
           }
       }
    }//GEN-LAST:event_panMenuActionPerformed

    private void mosaicViewMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mosaicViewMenuActionPerformed
        if(niiVol!=null){
            if(overlayVol!=null){
                mosaicWindow=new BarracudaViewMosaicFrame(niiVol,overlayVol,viewState,colorScale,colorScaleOverlay);
                        
            }else{
                mosaicWindow=new BarracudaViewMosaicFrame(niiVol,null,viewState,colorScale, null);
            }
           mosaicWindow.setVisible(true);
                        mosaicWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                            public void windowClosing(java.awt.event.WindowEvent evt) {
                                mosaicWindow.dispose();
                                mosaicWindow=null;
                                System.gc();
                            }
                        });
        }else{
            errorLabel.setText("Error: Add a volume first");
            errorDialog.setLocationRelativeTo(null);
            errorDialog.setVisible(true);
        }
        
    }//GEN-LAST:event_mosaicViewMenuActionPerformed

    private void coronalLabelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_coronalLabelMouseWheelMoved
       int notches = evt.getWheelRotation();
       if(notches<0){
           try{
               coronalSlider.setValue(coronalSlider.getValue()+1);
           }catch(Exception e){
               
           }
       }else{
          try{
               coronalSlider.setValue(coronalSlider.getValue()-1);
           }catch(Exception e){
               
           } 
       }
    }//GEN-LAST:event_coronalLabelMouseWheelMoved

    private void saggitalLabelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_saggitalLabelMouseWheelMoved
        int notches = evt.getWheelRotation();
       if(notches<0){
           try{
               saggitalSlider.setValue(saggitalSlider.getValue()+1);
           }catch(Exception e){
               
           }
       }else{
          try{
               saggitalSlider.setValue(saggitalSlider.getValue()-1);
           }catch(Exception e){
               
           } 
       }
    }//GEN-LAST:event_saggitalLabelMouseWheelMoved

    private void axialLabelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_axialLabelMouseWheelMoved
        int notches = evt.getWheelRotation();
       if(notches<0){
           try{
               axialSlider.setValue(axialSlider.getValue()+1);
           }catch(Exception e){
               
           }
       }else{
          try{
               axialSlider.setValue(axialSlider.getValue()-1);
           }catch(Exception e){
               
           } 
       }
    }//GEN-LAST:event_axialLabelMouseWheelMoved

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        niiVol=null;
        overlayVol=null;
        fc=null;
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
       niiVol=null;
       overlayVol=null;
       fc=null;
    }//GEN-LAST:event_formWindowClosed

    private void xhairButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xhairButtonActionPerformed
        if(xhairButton.isSelected()){
            if(crosshairMenu.isSelected()){
                drawLabelsXHair();
            }
        }else{
            drawAllSlices();
        }
    }//GEN-LAST:event_xhairButtonActionPerformed
                                    
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BarracudaViewUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BarracudaViewUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BarracudaViewUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BarracudaViewUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BarracudaViewUI().setVisible(true);
            }
        });
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel axialLabel;
    private javax.swing.JLabel axialLabel1;
    private javax.swing.JLabel axialLabel2;
    private javax.swing.JLabel axialLabel3;
    private javax.swing.JLabel axialLabel4;
    private javax.swing.JPanel axialPanel;
    private javax.swing.JSlider axialSlider;
    private javax.swing.JLabel cbm;
    private javax.swing.JLabel cbm1;
    private javax.swing.JLabel colorBar;
    private javax.swing.JLabel colorBar12;
    private javax.swing.JLabel colorBar14;
    private javax.swing.JLabel colorBar34;
    private javax.swing.JTextField colorBarMax;
    private javax.swing.JTextField colorBarMin;
    private javax.swing.ButtonGroup colorScaleGroup;
    private javax.swing.JMenu colormapMenu;
    private javax.swing.JLabel coronalLabel;
    private javax.swing.JLabel coronalLabel1;
    private javax.swing.JLabel coronalLabel2;
    private javax.swing.JLabel coronalLabel3;
    private javax.swing.JLabel coronalLabel4;
    private javax.swing.JPanel coronalPanel;
    private javax.swing.JSlider coronalSlider;
    private javax.swing.JRadioButtonMenuItem crosshairMenu;
    private javax.swing.JDialog errorDialog;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JCheckBoxMenuItem grayScale;
    private javax.swing.JCheckBoxMenuItem hotInvertScale;
    private javax.swing.JCheckBoxMenuItem hotScale;
    private java.awt.Panel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem mosaicViewMenu;
    private javax.swing.JRadioButton neuroView;
    private javax.swing.JMenuItem openOverlayMenu;
    private javax.swing.JMenuItem openVolumeMenu;
    private javax.swing.JRadioButtonMenuItem overlaySelect;
    private javax.swing.JRadioButtonMenuItem panMenu;
    private javax.swing.JRadioButton radioView;
    private javax.swing.JCheckBoxMenuItem rainbowScale;
    private javax.swing.JMenuItem resetColorScale;
    private javax.swing.JMenuItem restoreZoomMenu;
    private javax.swing.JLabel saggitalLabel;
    private javax.swing.JLabel saggitalLabel2;
    private javax.swing.JLabel saggitalLabel3;
    private javax.swing.JLabel saggitalLabel4;
    private javax.swing.JLabel saggitalLabel5;
    private javax.swing.JPanel saggitalPanel;
    private javax.swing.JSlider saggitalSlider;
    private javax.swing.JLabel valueLabel;
    private javax.swing.ButtonGroup viewActionsGroup;
    private javax.swing.ButtonGroup viewGroup;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JSpinner volSpinner;
    private javax.swing.JMenu volumeMenu;
    private javax.swing.JRadioButtonMenuItem volumeSelect;
    private javax.swing.ButtonGroup volumeSelectorGroup;
    private javax.swing.JCheckBoxMenuItem winterInvertScale;
    private javax.swing.JCheckBoxMenuItem winterScale;
    private javax.swing.JLabel xPosLabel;
    private javax.swing.JSpinner xSpinner;
    private javax.swing.JToggleButton xhairButton;
    private javax.swing.JLabel yPosLabel;
    private javax.swing.JSpinner ySpinner;
    private javax.swing.JLabel zPosLabel;
    private javax.swing.JSpinner zSpinner;
    private javax.swing.JRadioButtonMenuItem zoomMenu;
    // End of variables declaration//GEN-END:variables

    /************Display plane in corresponding label************/
    //Generic
    private void drawLabelsXHair(){
        drawCoronalXHair();
        drawSaggitalXHair();
        drawAxialXHair();
    }//Images & Crosshairs
    private void drawAllSlices(){
        int yVal=coronalSlider.getValue();
        int xVal=saggitalSlider.getValue();
        int zVal=axialSlider.getValue();
        BufferedImage img;
        img = niiVol.drawNiftiSlice(yVal, "coronal",(int)volSpinner.getValue(),colorScale);       
        coronalScale=UITools.imageToLabel(img,coronalLabel);
        img = niiVol.drawNiftiSlice(xVal, "saggital",(int)volSpinner.getValue(),colorScale);       
        saggitalScale=UITools.imageToLabel(img,saggitalLabel);
        img = niiVol.drawNiftiSlice(zVal, "axial",(int)volSpinner.getValue(),colorScale);       
        axialScale=UITools.imageToLabel(img,axialLabel);
    }//Images w/o Crosshairs
    private void drawNiftiSlice(javax.swing.JSlider slider,javax.swing.JSpinner spinner){
        if(overlayVol==null){
            if(niiVol!=null){
                 if(crosshairMenu.isSelected()){
                     drawLabelsXHair();
                 }else{
                     drawAllSlices();
                 }
                spinner.setValue(slider.getValue());
                setXYZLabels();
            }
        }else{
                if(crosshairMenu.isSelected()){
                     drawLabelsXHair();
                 }else{
                     drawAllSlicesOverlay();
                 }
                spinner.setValue(slider.getValue());
                setXYZLabels();
        }
    }
    private void drawNifti(){
        if(overlayVol==null){
            if(niiVol!=null){
                 if(crosshairMenu.isSelected()){
                     drawLabelsXHair();
                 }else{
                     drawAllSlices();
                 }
                setXYZLabels();
            }
        }else{
            if(crosshairMenu.isSelected()){
                     drawLabelsXHair();
                 }else{
                     drawAllSlicesOverlay();
                 }
                setXYZLabels();
        }
    }
    private void drawAllSlicesOverlay(){
        drawCoronalOverlay();
        drawAxialOverlay();
        drawSaggitalOverlay();
    }
    //Coronal
    private void drawCoronalXHair(){
            int Val=coronalSlider.getValue();
            int xVal=saggitalSlider.getValue()-niiVol.getDrawRange(1,0);
            int yVal=axialSlider.getValue()-niiVol.getDrawRange(1,1);
            if(Val<niiVol.header.dim[2] & xVal<niiVol.header.dim[1] & yVal<niiVol.header.dim[3]){
                BufferedImage img;
                
                if(overlayVol==null){
                img = niiVol.drawNiftiSlice(Val, "coronal",(int)volSpinner.getValue(),colorScale);
                }else{
                    BufferedImage ovImg=overlayVol.drawNiftiSlice(coronalSlider.getValue(), "coronal",(int)volSpinner.getValue(),colorScaleOverlay);
                    BufferedImage niiImg=niiVol.drawNiftiSlice(coronalSlider.getValue(), "coronal",(int)volSpinner.getValue(),colorScale);
                    int w=niiImg.getWidth();
                    int h=niiImg.getHeight();
                    img = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g=img.createGraphics();
                    Color c=new Color(0,0,0,0);
                    g.drawImage(niiImg,0,0,c,null);
                    g.drawImage(ovImg,0,0,c,null);
                }
                if(img!=null){         
                    coronalScale=UITools.imageToLabel(img,coronalLabel);
                    int actualnx=(int) Math.ceil((xVal*niiVol.scale[0])*coronalScale);
                    int actualny=(int) Math.ceil((yVal*niiVol.scale[2])*coronalScale);
                    img=(BufferedImage)((ImageIcon)coronalLabel.getIcon()).getImage();
                    if(xhairButton.isSelected()){
                        Graphics g =img.getGraphics();
                        g.setColor(Color.GREEN);
                        if(niiVol.orient[0]=='L'){
                            g.drawLine(actualnx, 0, actualnx, img.getHeight());
                        }else{
                            g.drawLine(img.getWidth()-actualnx, 0, img.getWidth()-actualnx, img.getHeight());
                        }
                        if(niiVol.orient[2]=='I'){
                            g.drawLine(0,img.getHeight()-actualny, img.getWidth(), img.getHeight()-actualny);
                        }else{
                            g.drawLine(0,actualny, img.getWidth(),actualny);
                        }
                    }
                }
            }
    }
    private void coronalMouseXHair(){
        Point point = coronalLabel.getMousePosition(false);
        if(point!=null){
            int x,y;
            int iconWidth=((BufferedImage)((ImageIcon)coronalLabel.getIcon()).getImage()).getWidth()/2;
            int iconHeight=((BufferedImage)((ImageIcon)coronalLabel.getIcon()).getImage()).getHeight()/2;
            int labelWidth=coronalLabel.getWidth()/2;
            int w=labelWidth*2;
            int labelHeight=coronalLabel.getHeight()/2;
            int h=labelHeight*2;
            int xOffset=niiVol.getDrawRange(1,0);
            int yOffset=niiVol.getDrawRange(1,1);
            if(niiVol.orient[0]=='L'){
                x=((int)(((point.getX())-(labelWidth-iconWidth))/(coronalScale*niiVol.scale[0])))+xOffset;
            }else{
                x=((int)(((w-point.getX())-(labelWidth-iconWidth))/(coronalScale*niiVol.scale[0])))+xOffset;
            }
            if(niiVol.orient[2]=='I'){
                y=((int)(((h-point.getY())-(labelHeight-iconHeight))/(coronalScale*niiVol.scale[2])))+yOffset;
            }else{
                y=(int)(((point.getY())-(labelHeight-iconHeight))/(coronalScale*niiVol.scale[2]))+yOffset;
            }
            saggitalSlider.setValue(x);
            axialSlider.setValue(y);
        }
    }
    private void drawCoronalOverlay(){
        BufferedImage ovImg=overlayVol.drawNiftiSlice(coronalSlider.getValue(), "coronal",(int)volSpinner.getValue(),colorScaleOverlay);
        BufferedImage niiImg=niiVol.drawNiftiSlice(coronalSlider.getValue(), "coronal",(int)volSpinner.getValue(),colorScale);
        int w=niiImg.getWidth();
        int h=niiImg.getHeight();
        BufferedImage combined = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=combined.createGraphics();
        Color c=new Color(0,0,0,0);
        g.drawImage(niiImg,0,0,c,null);
        g.drawImage(ovImg,0,0,c,null);
        coronalScale=UITools.imageToLabel(combined,coronalLabel);
    }
    private void coronalPan(){
        try{
                Point newPoint=coronalLabel.getMousePosition(false);
                int drawRange[][]=niiVol.getDrawRange();;
                if(panPoint!=null){
                    if(newPoint.getX()>panPoint.getX()){
                        if(niiVol.orient[0]=='L'){
                            if(drawRange[1][2]<niiVol.header.dim[1]-1){
                                drawRange[1][0]+=2;
                                drawRange[1][2]+=2;
                            }
                        }else{
                            if(drawRange[1][0]>1){
                                drawRange[1][0]-=2;
                                drawRange[1][2]-=2;
                            }
                        }
                    }else if(newPoint.getX()<panPoint.getX()){
                        if(niiVol.orient[0]=='L'){
                            if(drawRange[1][0]>1){
                                drawRange[1][0]-=2;
                                drawRange[1][2]-=2;
                            }
                        }else{
                            if(drawRange[1][2]<niiVol.header.dim[1]-1){
                                drawRange[1][0]+=2;
                                drawRange[1][2]+=2;
                            }
                        }
                    }
                    if(newPoint.getY()>panPoint.getY()){
                        if(niiVol.orient[2]=='S'){
                            if(drawRange[1][3]<niiVol.header.dim[3]-1){
                                drawRange[1][1]+=2;
                                drawRange[1][3]+=2;
                            }
                        }else{
                            if(drawRange[1][1]>1){
                                drawRange[1][1]-=2;
                                drawRange[1][3]-=2;
                            }
                        }
                    }else if(newPoint.getY()<panPoint.getY()){
                        if(niiVol.orient[2]=='S'){
                            if(drawRange[1][1]>1){
                                drawRange[1][1]-=2;
                                drawRange[1][3]-=2;
                            }
                        }else{
                            if(drawRange[1][3]<niiVol.header.dim[3]-1){
                                drawRange[1][1]+=2;
                                drawRange[1][3]+=2;
                            }
                        }
                    }
                    niiVol.setDrawRange(drawRange);
                    if(overlayVol!=null){
                        overlayVol.setDrawRange(drawRange);
                    }
                    drawNiftiSlice(coronalSlider,ySpinner);
                    panPoint=newPoint;
                }else{
                    panPoint=newPoint;
                }
                }catch(Exception e){}
    }
    //Saggital
    private void drawSaggitalXHair(){
        int Val=saggitalSlider.getValue();
        int xVal=coronalSlider.getValue()-niiVol.getDrawRange(0,0);
        int yVal=axialSlider.getValue()-niiVol.getDrawRange(0,1);
        if(xVal<niiVol.header.dim[2] & Val<niiVol.header.dim[1]& yVal<niiVol.header.dim[3]){
        BufferedImage img;
        if(overlayVol==null){
            img = niiVol.drawNiftiSlice(Val, "saggital",(int)volSpinner.getValue(),colorScale);
        }else{
            BufferedImage ovImg=overlayVol.drawNiftiSlice(Val, "saggital",(int)volSpinner.getValue(),colorScaleOverlay);
            BufferedImage niiImg=niiVol.drawNiftiSlice(Val, "saggital",(int)volSpinner.getValue(),colorScale);
            int w=niiImg.getWidth();
            int h=niiImg.getHeight();
            img = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g=img.createGraphics();
            Color c=new Color(0,0,0,0);
            g.drawImage(niiImg,0,0,c,null);
            g.drawImage(ovImg,0,0,c,null);
        }
        if(img!=null){
            saggitalScale= UITools.imageToLabel(img,saggitalLabel);
            int actualnx=(int) Math.round((xVal*niiVol.scale[1])*saggitalScale);
            int actualny=(int) Math.round((yVal*niiVol.scale[2])*saggitalScale);
            img=(BufferedImage)((ImageIcon)saggitalLabel.getIcon()).getImage();
            if(xhairButton.isSelected()){
                Graphics g =img.getGraphics();
                g.setColor(Color.GREEN);
                if(niiVol.orient[1]=='P'){
                    g.drawLine(actualnx, 0, actualnx, img.getHeight());
                }else{
                    g.drawLine(img.getWidth()-actualnx, 0, img.getWidth()-actualnx, img.getHeight());
                }
                if(niiVol.orient[2]=='I'){
                    g.drawLine(0,img.getHeight()-actualny, img.getWidth(), img.getHeight()-actualny);
                }else{
                     g.drawLine(0,actualny, img.getWidth(),actualny);
                }
            }
        }
        }
    }
    private void saggitalMouseXHair(){
        Point point = saggitalLabel.getMousePosition(false);
        if(point!=null){
            int x,y;
            int iconWidth=((BufferedImage)((ImageIcon)saggitalLabel.getIcon()).getImage()).getWidth()/2;
            int iconHeight=((BufferedImage)((ImageIcon)saggitalLabel.getIcon()).getImage()).getHeight()/2;
            int labelWidth=saggitalLabel.getWidth()/2;
            int w=labelWidth*2;
            int labelHeight=saggitalLabel.getHeight()/2;
            int h=labelHeight*2;
            int xOffset=niiVol.getDrawRange(0,0);
            int yOffset=niiVol.getDrawRange(0,1);
            if(niiVol.orient[1]=='P'){
                x=(int)(((point.getX())-(labelWidth-iconWidth))/(saggitalScale*niiVol.scale[1]))+xOffset;
            }else{
                x=(int)(((w-point.getX())-(labelWidth-iconWidth))/(saggitalScale*niiVol.scale[1]))+xOffset;
            }
            if(niiVol.orient[2]=='I'){
                y=(int)(((h-point.getY())-(labelHeight-iconHeight))/(saggitalScale*niiVol.scale[2]))+yOffset;
            }else{
                y=(int)(((point.getY())-(labelHeight-iconHeight))/(saggitalScale*niiVol.scale[2]))+yOffset;
            }
            coronalSlider.setValue(x);
            axialSlider.setValue(y);
        }
    }
    private void drawSaggitalOverlay(){
        BufferedImage ovImg=overlayVol.drawNiftiSlice(saggitalSlider.getValue(), "saggital",(int)volSpinner.getValue(),colorScaleOverlay);
        BufferedImage niiImg=niiVol.drawNiftiSlice(saggitalSlider.getValue(), "saggital",(int)volSpinner.getValue(),colorScale);
        int w=niiImg.getWidth();
        int h=niiImg.getHeight();
        BufferedImage combined = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=combined.createGraphics();
        Color c=new Color(0,0,0,0);
        g.drawImage(niiImg,0,0,c,null);
        g.drawImage(ovImg,0,0,c,null);
        saggitalScale=UITools.imageToLabel(combined,saggitalLabel);
    }
    private void saggitalPan(){
        try{
                Point newPoint=saggitalLabel.getMousePosition(false);
                int drawRange[][]=niiVol.getDrawRange();;
                if(panPoint!=null){
                    if(newPoint.getX()>panPoint.getX()){
                        if(niiVol.orient[1]=='P'){
                            if(drawRange[0][2]<niiVol.header.dim[2]-1){
                                drawRange[0][0]+=2;
                                drawRange[0][2]+=2;
                            }
                        }else{
                            if(drawRange[0][0]>1){
                                drawRange[0][0]-=2;
                                drawRange[0][2]-=2;
                            }
                        }
                    }else if(newPoint.getX()<panPoint.getX()){
                        if(niiVol.orient[1]=='P'){
                            if(drawRange[0][0]>1){
                                drawRange[0][0]-=2;
                                drawRange[0][2]-=2;
                            }
                        }else{
                            if(drawRange[0][2]<niiVol.header.dim[2]-1){
                                drawRange[0][0]+=2;
                                drawRange[0][2]+=2;
                            }
                        }
                    }
                    if(newPoint.getY()>panPoint.getY()){
                        if(niiVol.orient[2]=='S'){
                            if(drawRange[0][3]<niiVol.header.dim[3]-1){
                                drawRange[0][1]+=2;
                                drawRange[0][3]+=2;
                            }
                        }else{
                            if(drawRange[0][1]>1){
                                drawRange[0][1]-=2;
                                drawRange[0][3]-=2;
                            }
                        }
                    }else if(newPoint.getY()<panPoint.getY()){
                        if(niiVol.orient[2]=='S'){
                            if(drawRange[0][1]>1){
                                drawRange[0][1]-=2;
                                drawRange[0][3]-=2;
                            }
                        }else{
                            if(drawRange[0][3]<niiVol.header.dim[3]-1){
                                drawRange[0][1]+=2;
                                drawRange[0][3]+=2;
                            }
                        }
                    }
                    niiVol.setDrawRange(drawRange);
                    if(overlayVol!=null){
                        overlayVol.setDrawRange(drawRange);
                    }
                    drawNiftiSlice(saggitalSlider,xSpinner);
                    panPoint=newPoint;
                }else{
                    panPoint=newPoint;
                }
                }catch(Exception e){}
    }
    //Axial
    private void drawAxialXHair(){
        
        int Val=axialSlider.getValue();
        int xVal=saggitalSlider.getValue()-niiVol.getDrawRange(2,0);
        int yVal=coronalSlider.getValue()-niiVol.getDrawRange(2,1);
        if(yVal<niiVol.header.dim[2] & xVal<niiVol.header.dim[1] & Val<niiVol.header.dim[3]){
        BufferedImage img;
        if(overlayVol==null){
            img = niiVol.drawNiftiSlice(Val, "axial",(int)volSpinner.getValue(),colorScale);
        }else{
            BufferedImage ovImg=overlayVol.drawNiftiSlice(Val, "axial",(int)volSpinner.getValue(),colorScaleOverlay);
            BufferedImage niiImg=niiVol.drawNiftiSlice(Val, "axial",(int)volSpinner.getValue(),colorScale);
            int w=niiImg.getWidth();
            int h=niiImg.getHeight();
            img = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g=img.createGraphics();
            Color c=new Color(0,0,0,0);
            g.drawImage(niiImg,0,0,c,null);
            g.drawImage(ovImg,0,0,c,null);
        }
        if(img!=null){
            axialScale=UITools.imageToLabel(img,axialLabel);
            //x saggital, y coronal
            
            int actualnx=(int) Math.round((xVal*niiVol.scale[0])*axialScale);
            int actualny=(int) Math.round((yVal*niiVol.scale[1])*axialScale);
            img=(BufferedImage)((ImageIcon)axialLabel.getIcon()).getImage();
            if(xhairButton.isSelected()){
                Graphics g =img.getGraphics();
                g.setColor(Color.GREEN);
                if(niiVol.orient[0]=='L'){
                    g.drawLine(actualnx, 0, actualnx, img.getHeight());
                }else{
                    g.drawLine(img.getWidth()-actualnx, 0, img.getWidth()-actualnx, img.getHeight());
                }
                if(niiVol.orient[1]=='A'){
                    g.drawLine( 0,actualny,img.getWidth(),actualny);
                }else{
                    g.drawLine( 0,img.getHeight()-actualny, img.getWidth(),img.getHeight()-actualny);
                }
            }
        }
        }
    }
    private void axialMouseXHair(){
        Point point = axialLabel.getMousePosition(false);
        if(point!=null){
            int x,y;
            int iconWidth=((BufferedImage)((ImageIcon)axialLabel.getIcon()).getImage()).getWidth()/2;
            int iconHeight=((BufferedImage)((ImageIcon)axialLabel.getIcon()).getImage()).getHeight()/2;
            int labelWidth=axialLabel.getWidth()/2;
            int w=labelWidth*2;
            int labelHeight=axialLabel.getHeight()/2;
            int h=labelHeight*2;
            int xOffset=niiVol.getDrawRange(2,0);
            int yOffset=niiVol.getDrawRange(2,1);
            if(niiVol.orient[0]=='L'){
                x=(int)(((point.getX())-(labelWidth-iconWidth))/(axialScale*niiVol.scale[0]))+xOffset;
            }else{
                x=(int)(((w-point.getX())-(labelWidth-iconWidth))/(axialScale*niiVol.scale[0]))+xOffset;
            }
            if(niiVol.orient[1]=='P'){
                y=(int)(((h-point.getY())-(labelHeight-iconHeight))/(axialScale*niiVol.scale[1]))+yOffset;
            }else{
                y=(int)(((point.getY())-(labelHeight-iconHeight))/(axialScale*niiVol.scale[1]))+yOffset;
            }
            saggitalSlider.setValue(x);
            coronalSlider.setValue(y);
        }
    }
    private void drawAxialOverlay(){
        BufferedImage ovImg=overlayVol.drawNiftiSlice(axialSlider.getValue(), "axial",(int)volSpinner.getValue(),colorScaleOverlay);
        BufferedImage niiImg=niiVol.drawNiftiSlice(axialSlider.getValue(), "axial",(int)volSpinner.getValue(),colorScale);
        int w=niiImg.getWidth();
        int h=niiImg.getHeight();
        BufferedImage combined = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=combined.createGraphics();
        Color c=new Color(0,0,0,0);
        g.drawImage(niiImg,0,0,c,null);
        g.drawImage(ovImg,0,0,c,null);
        axialScale=UITools.imageToLabel(combined,axialLabel);
    }
    private void axialPan(){
        try{
                Point newPoint=axialLabel.getMousePosition(false);
                int drawRange[][]=niiVol.getDrawRange();;
                if(panPoint!=null){
                    if(newPoint.getX()>panPoint.getX()){
                        if(niiVol.orient[0]=='L'){
                            if(drawRange[2][2]<niiVol.header.dim[1]-1){
                                drawRange[2][0]+=2;
                                drawRange[2][2]+=2;
                            }
                        }else{
                            if(drawRange[2][0]>1){
                                drawRange[2][0]-=2;
                                drawRange[2][2]-=2;
                            }
                        }
                    }else if(newPoint.getX()<panPoint.getX()){
                        if(niiVol.orient[0]=='L'){
                            if(drawRange[2][0]>1){
                                drawRange[2][0]-=2;
                                drawRange[2][2]-=2;
                            }
                        }else{
                            if(drawRange[2][2]<niiVol.header.dim[1]-1){
                                drawRange[2][0]+=2;
                                drawRange[2][2]+=2;
                            }
                        }
                    }
                    if(newPoint.getY()>panPoint.getY()){
                        if(niiVol.orient[1]=='A'){
                            if(drawRange[2][3]<niiVol.header.dim[2]-1){
                                drawRange[2][1]+=2;
                                drawRange[2][3]+=2;
                            }
                        }else{
                            if(drawRange[2][1]>1){
                                drawRange[2][1]-=2;
                                drawRange[2][3]-=2;
                            }
                        }
                    }else if(newPoint.getY()<panPoint.getY()){
                        if(niiVol.orient[1]=='A'){
                            if(drawRange[2][1]>1){
                                drawRange[2][1]-=2;
                                drawRange[2][3]-=2;
                            }
                        }else{
                            if(drawRange[2][3]<niiVol.header.dim[2]-1){
                                drawRange[2][1]+=2;
                                drawRange[2][3]+=2;
                            }
                        }
                    }
                    niiVol.setDrawRange(drawRange);
                    if(overlayVol!=null){
                        overlayVol.setDrawRange(drawRange);
                    }
                    drawNiftiSlice(axialSlider,zSpinner);
                    panPoint=newPoint;
                }else{
                    panPoint=newPoint;
                }
                }catch(Exception e){}
    }
    /********Display functions***********/
    private void resizeGraphs(){
        if(overlayVol==null){
            if(niiVol!=null){
                if(crosshairMenu.isSelected()){
                     drawLabelsXHair();
                }else{
                     drawAllSlices();
                }
            }
        }else{
            if(crosshairMenu.isSelected()){
                drawLabelsXHair();
            }else{
                drawAllSlicesOverlay();
            }
        }
    }//When graphs are resized
    private void mouseAdjustMax(javax.swing.JLabel label){
        Point point=label.getMousePosition();
        if(prevMouse!=null){
            //If new point>prev point decrease max
            if(prevMouse.getY()<point.getY()){
                if(volumeSelect.isSelected()){
                    niiVol.setMax(niiVol.getMax()*0.98);
                }else{
                    overlayVol.setMax(overlayVol.getMax()*0.98);
                }
                drawLabelsXHair();
            }
            //If new point<prev point increase max
            else if(prevMouse.getY()>point.getY()){
                if(volumeSelect.isSelected()){
                    niiVol.setMax(niiVol.getMax()*1.02);
                }else{
                    overlayVol.setMax(overlayVol.getMax()*1.02);
                }
                drawLabelsXHair();
            }
        }
        prevMouse=point;
    }//Contrast Adjustment
    private void setXYZLabels(){
        //Get slider values
        int XVal=(int)saggitalSlider.getValue();
        int YVal=(int)coronalSlider.getValue();
        int ZVal=(int)axialSlider.getValue();
        DrawableNiftiVolume vol;
        String str;
        if(volumeSelect.isSelected()){
            vol=niiVol;
            str="Volume: ";
        }else{
            vol=overlayVol;
            str="Overlay: ";
        }
        if(YVal<vol.header.dim[2] & XVal<vol.header.dim[1] & ZVal<vol.header.dim[3]){
            //Get nifti units
            String units=vol.header.getUnits();
            //Coompute position coordinates using qform and sform matrices 
            Vector coordinates=vol.computeXYZ(rotationMtrx, XVal, YVal, ZVal);
            //double xyz[]=vol.computeXYZ(R,XVal,YVal,ZVal);
            //Format strings
            double x=coordinates.getComponent(0);
            double y=coordinates.getComponent(1);
            double z=coordinates.getComponent(2);
            String numString = String.format ("%.2f ",x);
            xPosLabel.setText(numString+units);
            numString = String.format ("%.2f ",y);
            yPosLabel.setText(numString+units);
            numString = String.format ("%.2f ",z);
            zPosLabel.setText(numString+units);
            
            double num=vol.data.get(XVal, YVal, ZVal, (int)volSpinner.getValue());
            numString=String.format("%.8f ", num);
            
            valueLabel.setText(str+numString);
        }
    }//X,Y,Z units
    
    /******Color Bar functions******/
    private void setColorBar(){
        Image img;
        URL imageurl;
        String str;
        if(volumeSelect.isSelected()){
            str=colorScale;
        }else{
            str=colorScaleOverlay;
        }
        switch(str){
            case"grayscale": imageurl= getClass().getResource("/images/grayMap.png");
                grayScale.setSelected(true);
                break;
            case"hot":imageurl= getClass().getResource("/images/hotMap.png");
                hotScale.setSelected(true);
                break;
            case"hot_invert":imageurl= getClass().getResource("/images/hotInverseMap.png");
                hotInvertScale.setSelected(true);
                break;
            case"winter":imageurl= getClass().getResource("/images/winterMap.png");
                winterScale.setSelected(true);
                break;
            case"winter_invert":imageurl= getClass().getResource("/images/winterInverseMap.png");
                winterInvertScale.setSelected(true);
                break;
            case"rainbow":imageurl= getClass().getResource("/images/rainbowMap.png");
                rainbowScale.setSelected(true);
                break;
            default:imageurl= getClass().getResource("/images/grayMap.png");
                grayScale.setSelected(true);
                break;
        }
 
        img =  Toolkit.getDefaultToolkit().getImage(imageurl);
        updateMaxColorbar();
        ImageIcon imageIcon = new ImageIcon(img);
        colorBar.setIcon(imageIcon);
    }//Sets colorbar image
    private void updateMaxColorbar(){
        double max,min;
        DrawableNiftiVolume vol;
        if(volumeSelect.isSelected()){
            vol=niiVol;
        }else{
            vol=overlayVol;
        }
        max=vol.getMax();
        min=vol.getMin();
        String numString = String.format ("%.2f",max);
        colorBarMax.setText(numString);
        numString=String.format("_%.2f",(3*(max-min)/4));
        colorBar34.setText(numString);
        numString=String.format("_%.2f",((max-min)/2));
        colorBar12.setText(numString);
        numString=String.format("_%.2f",((max-min)/4));
        colorBar14.setText(numString);
        numString = String.format ("%.2f",min);
        colorBarMin.setText(numString);
    }//Adjusts colorbar values
    private void resetGrayScale(){
        //Set the colorscale
        grayScale.setSelected(true);
        if(volumeSelect.isSelected()){
            colorScale="grayscale";
        }else{
             colorScaleOverlay="grayscale";
        }
    }//Resets the colorbar to gray

    /**********Zoom Functions***********/    
    private void drawCoronalZoomBox(){
        if(zoomStartPoint!=null & zoomEndPoint!=null){
            //Get label image
            BufferedImage img;
            if(overlayVol==null){
                img = niiVol.drawNiftiSlice(coronalSlider.getValue(), "coronal",(int)volSpinner.getValue(),colorScale);
                coronalScale=UITools.imageToLabel(img, coronalLabel);
            }else{
                drawCoronalOverlay();
            }
            img=(BufferedImage)((ImageIcon)coronalLabel.getIcon()).getImage();
            //Get mouse coordinates
            int xy[]=getZoomArea(coronalLabel,img);
            //Draw rectangle
            Graphics g =img.getGraphics();
            g.setColor(xHairColor);
            g.drawRect(xy[0],xy[2],xy[1]-xy[0],xy[3]-xy[2]);
        }
    }//Draws the box
    private void setCoronalZoom(){
        //Get label image & Draw to erase previous zoom box
        BufferedImage img;
        if(overlayVol==null){
            img = niiVol.drawNiftiSlice(coronalSlider.getValue(), "coronal",(int)volSpinner.getValue(),colorScale);
            coronalScale=UITools.imageToLabel(img, coronalLabel);
        }else{
            drawCoronalOverlay();
        }
        img=(BufferedImage)((ImageIcon)coronalLabel.getIcon()).getImage();
        
        if(zoomStartPoint!=null & zoomEndPoint!=null){
            //Get zoomBox coordinates
            int xy[]=getZoomArea(coronalLabel,img);
            int w=img.getWidth();
            int h=img.getHeight();
            //Translate image coordinates to nifti data array coordinates
            double x00,x11,y00,y11;
            double r=coronalScale*niiVol.scale[0];
            if(niiVol.orient[0]=='L'){//Take orientation into account
                    x00=((double)xy[1]/r)+((double)niiVol.getDrawRange(1,0));
                    x11=((double)xy[0]/r)+((double)niiVol.getDrawRange(1,0));
            }else{
                    x00=((double)(w-xy[0])/r)+((double)niiVol.getDrawRange(1,0));
                    x11=((double)(w-xy[1])/r)+((double)niiVol.getDrawRange(1,0));
            }
            r=coronalScale*niiVol.scale[2];
            if(niiVol.orient[2]=='I'){
                    y00=((double)(h-xy[2])/r)+((double)niiVol.getDrawRange(1,1));
                    y11=((double)(h-xy[3])/r)+((double)niiVol.getDrawRange(1,1));
            }else{
                    y00=((double)xy[3]/r)+((double)niiVol.getDrawRange(1,1));
                    y11=((double)xy[2]/r)+((double)niiVol.getDrawRange(1,1));
            }
            if(x00!=x11 &y00!=y11){//Check for zero width or height
                Graphics g =img.getGraphics();
                g.setColor(xHairColor);
                g.drawRect(xy[0],xy[2],xy[1]-xy[0],xy[3]-xy[2]);
                //Set Draw Range & Redraw image
                niiVol.setDrawRange(1,0,(int)Math.round(x11));
                niiVol.setDrawRange(1,1,(int)Math.round(y11));
                niiVol.setDrawRange(1,2,(int)Math.round(x00));
                niiVol.setDrawRange(1,3,(int)Math.round(y00));
                if(overlayVol!=null){
                    overlayVol.setDrawRange(1,0,(int)Math.round(x11));
                    overlayVol.setDrawRange(1,1,(int)Math.round(y11));
                    overlayVol.setDrawRange(1,2,(int)Math.round(x00));
                    overlayVol.setDrawRange(1,3,(int)Math.round(y00));
                    drawCoronalOverlay();
                }else{
                    img = niiVol.drawNiftiSlice(coronalSlider.getValue(), "coronal",(int)volSpinner.getValue(),colorScale);
                    coronalScale=UITools.imageToLabel(img, coronalLabel);
                }
            }
        }
    }//Zoom-in action
    
    private void drawSaggitalZoomBox(){
        if(zoomStartPoint!=null & zoomEndPoint!=null){
            BufferedImage img;
            if(overlayVol==null){
            img = niiVol.drawNiftiSlice(saggitalSlider.getValue(), "saggital",(int)volSpinner.getValue(),colorScale);
                saggitalScale=UITools.imageToLabel(img, saggitalLabel);
            }else{
                drawSaggitalOverlay();
            }
            img=(BufferedImage)((ImageIcon)saggitalLabel.getIcon()).getImage();

            int xy[]=getZoomArea(saggitalLabel,img);
            Graphics g =img.getGraphics();
            g.setColor(xHairColor);
            g.drawRect(xy[0],xy[2],xy[1]-xy[0],xy[3]-xy[2]);
        }
    }
    private void setSaggitalZoom(){
        BufferedImage img;
        if(overlayVol==null){
            img = niiVol.drawNiftiSlice(saggitalSlider.getValue(), "saggital",(int)volSpinner.getValue(),colorScale);
            saggitalScale=UITools.imageToLabel(img, saggitalLabel);
        }else{
            drawSaggitalOverlay();
        }
        img=(BufferedImage)((ImageIcon)saggitalLabel.getIcon()).getImage();

        if(zoomStartPoint!=null & zoomEndPoint!=null){
            //Get zoomBox coordinates
            int xy[]=getZoomArea(saggitalLabel,img);
            int w=img.getWidth();
            int h=img.getHeight();
            
            double x00,x11,y00,y11;
            double r=saggitalScale*niiVol.scale[1];
            if(niiVol.orient[1]=='P'){
                    x00=((double)xy[1]/r)+((double)niiVol.getDrawRange(0,0));
                    x11=((double)xy[0]/r)+((double)niiVol.getDrawRange(0,0));
            }else{
                    x00=((double)(w-xy[0])/r)+((double)niiVol.getDrawRange(0,0));
                    x11=((double)(w-xy[1])/r)+((double)niiVol.getDrawRange(0,0));
            }
            r=saggitalScale*niiVol.scale[2];
            if(niiVol.orient[2]=='I'){
                    y00=((double)(h-xy[2])/r)+((double)niiVol.getDrawRange(0,1));
                    y11=((double)(h-xy[3])/r)+((double)niiVol.getDrawRange(0,1));
            }else{
                    y00=((double)xy[3]/r)+((double)niiVol.getDrawRange(0,1));
                    y11=((double)xy[2]/r)+((double)niiVol.getDrawRange(0,1));
            }
            if(x00!=x11 &y00!=y11){
                Graphics g =img.getGraphics();
                g.setColor(xHairColor);
                g.drawRect(xy[0],xy[2],xy[1]-xy[0],xy[3]-xy[2]);
                niiVol.setDrawRange(0,0,(int)Math.round(x11));
                niiVol.setDrawRange(0,1,(int)Math.round(y11));
                niiVol.setDrawRange(0,2,(int)Math.round(x00));
                niiVol.setDrawRange(0,3,(int)Math.round(y00));
                if(overlayVol!=null){
                    overlayVol.setDrawRange(0,0,(int)Math.round(x11));
                    overlayVol.setDrawRange(0,1,(int)Math.round(y11));
                    overlayVol.setDrawRange(0,2,(int)Math.round(x00));
                    overlayVol.setDrawRange(0,3,(int)Math.round(y00));
                    drawSaggitalOverlay();
                }else{
                    img = niiVol.drawNiftiSlice(saggitalSlider.getValue(), "saggital",(int)volSpinner.getValue(),colorScale);
                    saggitalScale=UITools.imageToLabel(img, saggitalLabel);
                }
            }
        }
    }
    
    private void drawAxialZoomBox(){
        if(zoomStartPoint!=null & zoomEndPoint!=null){
            BufferedImage img;
            if(overlayVol==null){
                img = niiVol.drawNiftiSlice(axialSlider.getValue(), "axial",(int)volSpinner.getValue(),colorScale);
                axialScale=UITools.imageToLabel(img, axialLabel);
            }else{
                drawAxialOverlay();
            }
            img=(BufferedImage)((ImageIcon)axialLabel.getIcon()).getImage();
        
            int xy[]=getZoomArea(axialLabel,img);
            Graphics g =img.getGraphics();
            g.setColor(xHairColor);
            g.drawRect(xy[0],xy[2],xy[1]-xy[0],xy[3]-xy[2]);        
        }
    }
    private void setAxialZoom(){
        BufferedImage img;
        if(overlayVol==null){
            img = niiVol.drawNiftiSlice(axialSlider.getValue(), "axial",(int)volSpinner.getValue(),colorScale);
            axialScale=UITools.imageToLabel(img, axialLabel);
        }else{
            drawAxialOverlay();
        }
        img=(BufferedImage)((ImageIcon)axialLabel.getIcon()).getImage();
        
        if(zoomStartPoint!=null & zoomEndPoint!=null){
            //Get zoomBox coordinates
            int xy[]=getZoomArea(axialLabel,img);
            int w=img.getWidth();
            int h=img.getHeight();
            
            double x00,x11,y00,y11;
            double r=axialScale*niiVol.scale[0];
            if(niiVol.orient[0]=='L'){
                    x00=((double)xy[1]/r)+((double)niiVol.getDrawRange(2,0));
                    x11=((double)xy[0]/r)+((double)niiVol.getDrawRange(2,0));
            }else{
                    x00=((double)(w-xy[0])/r)+((double)niiVol.getDrawRange(2,0));
                    x11=((double)(w-xy[1])/r)+((double)niiVol.getDrawRange(2,0));
            }
            r=axialScale*niiVol.scale[1];
            if(niiVol.orient[1]=='P'){
                    y00=((double)(h-xy[2])/r)+((double)niiVol.getDrawRange(2,1));
                    y11=((double)(h-xy[3])/r)+((double)niiVol.getDrawRange(2,1));
            }else{
                    y00=((double)xy[3]/r)+((double)niiVol.getDrawRange(2,1));
                    y11=((double)xy[2]/r)+((double)niiVol.getDrawRange(2,1));
            }
            if(x00!=x11 &y00!=y11){
                Graphics g =img.getGraphics();
                g.setColor(xHairColor);
                g.drawRect(xy[0],xy[2],xy[1]-xy[0],xy[3]-xy[2]);
                niiVol.setDrawRange(2,0,(int)Math.round(x11));
                niiVol.setDrawRange(2,1,(int)Math.round(y11));
                niiVol.setDrawRange(2,2,(int)Math.round(x00));
                niiVol.setDrawRange(2,3,(int)Math.round(y00));
                if(overlayVol!=null){
                    overlayVol.setDrawRange(2,0,(int)Math.round(x11));
                    overlayVol.setDrawRange(2,1,(int)Math.round(y11));
                    overlayVol.setDrawRange(2,2,(int)Math.round(x00));
                    overlayVol.setDrawRange(2,3,(int)Math.round(y00));
                    drawAxialOverlay();
                }else{
                    img = niiVol.drawNiftiSlice(axialSlider.getValue(), "axial",(int)volSpinner.getValue(),colorScale);
                    axialScale=UITools.imageToLabel(img, axialLabel);
                }
            }
        }
    }
    //Translates Mouse position to image coordinates
    private int[] getZoomArea(javax.swing.JLabel label,BufferedImage img){
            int labelWidth=label.getWidth()/2;
            int labelHeight=label.getHeight()/2;
            int iconHeight=img.getHeight()/2;
            int iconWidth=img.getWidth()/2;
            int x0=(int)(zoomStartPoint.getX()-(labelWidth-iconWidth));
            int x1=(int)(zoomEndPoint.getX()-(labelWidth-iconWidth));
            int y0=(int)(zoomStartPoint.getY()-(labelHeight-iconHeight));
            int y1=(int)(zoomEndPoint.getY()-(labelHeight-iconHeight));
            int temp;
            if(x0>x1){
                temp=x0;
                x0=x1;
                x1=temp;
            }
            if(y0>y1){
                temp=y0;
                y0=y1;
                y1=temp;    
            }
            if(x0<0){x0=0;}
            if(x1>(2*iconWidth)){x1=2*iconWidth;}
            if(y0<0){y0=0;}
            if(y1>(2*iconHeight)){y1=2*iconHeight;}
            
            int coord[]={x0,x1,y0,y1};
            return coord;
    }
    
    private DefaultBoundedRangeModel getSliderModel(){
        DefaultBoundedRangeModel model=new DefaultBoundedRangeModel(50,1,0,100);
        return model;
    }
}
