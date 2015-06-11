package mvc;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import processing.ImageProcessing;
import data.*;


public class AppView extends JFrame{
	private static final long serialVersionUID = 1L;
	public static Mat img = new Mat();
	public static Mat img1 = new Mat();
	public String pathName;
	public int numberEryth;
		
	/** menu view components */
	private JMenuBar appMenuBar = new JMenuBar();
	private JMenu appMenu = new JMenu("Menu");
	private JMenuItem appMenuClose = new JMenuItem("Close");
	private JMenuItem appMenuAbout = new JMenuItem("About");
	
	/** about frame */
	private JFrame appAboutFrame = new JFrame("About");
	private StyleContext appAboutStyleContext = new StyleContext();
	private DefaultStyledDocument appAboutStyleDoc = new DefaultStyledDocument(appAboutStyleContext);
	private JTextPane appAboutTextPane = new JTextPane(appAboutStyleDoc);
	//TODO dokoñczyæ about
	public static final String appAboutText = "RCC\n\n  This application has been prepared for Software Medical Systems (OSM) "
			+ "at Warsaw University of Technology, The Faculty of Electronics and Information Technology.\n\n "
			+ "Authors : Artur Tynecki\n Anna Zawistowska\n 15L semester\n\n"
			+ "Application can be used for counting erythrocytes from microscope images. The prefered image input format is JPEG. "
			+ "All information about patient, clinic, number of erythrocytes and image (output format JPEG) is saved in 'Outcome' "
			+ "folder named by patient name and surname.";
		
	/** main window panels */
	private JPanel appLeftPanel = new JPanel();
	private JPanel appRightPanel = new JPanel();
	
	/** left window panels */
	private JPanel appPatientPanel = new JPanel();
	private JPanel appClinicPanel = new JPanel();
	private JPanel appActionPanel = new JPanel();
	
	/** patient panel components */
	/* labels */
	private JLabel appLabelName = new JLabel("Name:");
	private JLabel appLabelSurname = new JLabel("Surname:");
	private JLabel appLabelID = new JLabel("ID:");
	/* text fields */
	private JTextField appTextFieldName = new JTextField(15);
	private JTextField appTextFieldSurname = new JTextField(15);
	private JTextField appTextFieldID = new JTextField(15);
	/* action button */
	private JButton appButtonPatientClear = new JButton("Clear");
	
	/** clinic panel components */
	/* labels */
	private JLabel appClinicName = new JLabel("Name");
	private JLabel appClinicAddress = new JLabel("Address");
	private JLabel appClinicOrderNumber = new JLabel("Order number:");
	/* text fields */
	private JTextField appClinicFieldName = new JTextField(15);
	private JTextField appClinicFieldAddress = new JTextField(30);
	private JTextField appClinicFiledOrderNumber = new JTextField(12);
	/* action button */
	private JButton appButtonClinicClear = new JButton("Clear");
	
	/** action panel components */
	/* action button */
	private JButton appButtonActionLoadImage = new JButton("Image load");
	private JButton appButtonActionProcess = new JButton("Analyze");
	private JButton appButtonActionSaveResult = new JButton("Save result");
	
	private JFileChooser appFileChooser = new JFileChooser();
	
	/** right window panel */
	private JPanel appImagePanel = new JPanel();
	private JPanel appResultPanel = new JPanel();
	private JPanel appCounterPanel = new JPanel();
	
	public static JLabel appImageLabel = new JLabel();
	public static JLabel appResultLabel = new JLabel();
	public static JTextField appCounterField = new JTextField(4);
	
	/** default constructors (all views set) */
	public AppView(){
		/** set main view */
		this.setTitle("RCC (red cells counter)");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1500,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		/** set menu view */
		appMenuBar.add(appMenu);
		appMenu.add(appMenuAbout);
		appMenu.add(appMenuClose);
		setJMenuBar(appMenuBar);
		
		/** set main view panels */
		appPatientPanel.setBorder(BorderFactory.createTitledBorder("Patient"));
		appClinicPanel.setBorder(BorderFactory.createTitledBorder("Clinic"));
		appActionPanel.setBorder(BorderFactory.createTitledBorder("Control"));
		
		appImagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
		appResultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
		appCounterPanel.setBorder(BorderFactory.createTitledBorder("Number of erythrocytes"));
		
		GridLayout appPanelsLayout = new GridLayout(1,2);
		this.setLayout(appPanelsLayout);
		this.add(appLeftPanel);
		this.add(appRightPanel);
		
		// dodane przeze mnie zeby wyswietlal sie obrazek 
		appImagePanel.setLayout(new FlowLayout());
		appImagePanel.add(appImageLabel);
		appResultPanel.setLayout(new FlowLayout());
		appResultPanel.add(appResultLabel);
				
		
		GridLayout appDatePanelLayout = new GridLayout(3,1);
		//FlowLayout appDatePanelLayout = new FlowLayout(FlowLayout.CENTER);
		appLeftPanel.setLayout(appDatePanelLayout);
		appLeftPanel.add(appPatientPanel);
		appLeftPanel.add(appClinicPanel);
		appLeftPanel.add(appActionPanel);
		
		appRightPanel.setLayout(new BorderLayout());
		appRightPanel.add(appCounterPanel, BorderLayout.PAGE_END);
		JPanel appImagesPanel = new JPanel();
		appImagesPanel.setLayout(new GridLayout(2,1));
		appImagesPanel.add(appImagePanel);
		appImagesPanel.add(appResultPanel);
		appRightPanel.add(appImagesPanel, BorderLayout.CENTER);
		appCounterPanel.add(appCounterField);
		
		
		JSplitPane MainPanel = new JSplitPane();
		setMinimumSize(new Dimension(900, 700)); 
	    getContentPane().setLayout(new GridBagLayout());
		MainPanel.setDividerLocation(400);
		MainPanel.setEnabled(false);
		appLeftPanel.setMinimumSize(new Dimension(50, 200)); 
	    MainPanel.setLeftComponent(appLeftPanel); 
	    appRightPanel.setMinimumSize(new Dimension(200, 200)); 
	    MainPanel.setRightComponent(appRightPanel); 
	    GridBagConstraints gridBagConstraints = new GridBagConstraints(); 
	    gridBagConstraints.fill = GridBagConstraints.BOTH; 
	    gridBagConstraints.weightx = 1.0; 
	    gridBagConstraints.weighty = 1.0; 
	    getContentPane().add(MainPanel, gridBagConstraints); 
        Dimension screenSize = getToolkit().getScreenSize(); 
	    setBounds((screenSize.width-410)/2, (screenSize.height-329)/2, 410, 329); 
		
		/** set patient view panel */
	    appPatientPanel.setLayout(new BorderLayout());
	    JPanel appPatientDataPanel = new JPanel();
	    
		appPatientDataPanel.setLayout(new GridBagLayout());
		GridBagConstraints d = new GridBagConstraints();
		d.insets = new Insets(5, 10, 5, 5);
		d.gridx = 0;
		d.gridy = 0;
		d.ipady = 15;
		appPatientDataPanel.add(appLabelName, d);
		d.gridx = 1;
		d.gridy = 0;
		d.ipady = 15;
		appPatientDataPanel.add(appTextFieldName, d);
		d.gridx = 0;
		d.gridy = 1;
		d.ipady = 15;
		appPatientDataPanel.add(appLabelSurname, d);
		d.gridx = 1;
		d.gridy = 1;
		d.ipady = 15;
		appPatientDataPanel.add(appTextFieldSurname, d);
		d.gridx = 0;
		d.gridy = 2;
		d.ipady = 15;
		appPatientDataPanel.add(appLabelID, d);
		d.gridx = 1;
		d.gridy = 2;
		d.ipady = 15;
		appPatientDataPanel.add(appTextFieldID, d);
		
		appPatientPanel.add(appPatientDataPanel, BorderLayout.CENTER);
		
		JPanel appActionBar1 = new JPanel();
		appActionBar1.setLayout(new GridLayout(1,3));
		appActionBar1.add(Box.createGlue());
		appActionBar1.add(appButtonPatientClear);
		appActionBar1.add(Box.createGlue());
		appPatientPanel.add(appActionBar1, BorderLayout.PAGE_END);
		
		/** set clinic view panel */
		appClinicPanel.setLayout(new BorderLayout());
		
		JPanel appClinicDataPanel = new JPanel();
		appClinicDataPanel.setLayout(new BoxLayout(appClinicDataPanel, BoxLayout.Y_AXIS));
		appClinicDataPanel.add(appClinicName);
		appClinicDataPanel.add(appClinicFieldName);
		appClinicDataPanel.add(appClinicAddress);
		appClinicDataPanel.add(appClinicFieldAddress);
		appClinicDataPanel.add(appClinicOrderNumber);
		appClinicDataPanel.add(appClinicFiledOrderNumber);
		appClinicFiledOrderNumber.setEditable(false);
		appClinicDataPanel.add(Box.createVerticalStrut(10));
		appClinicPanel.add(appClinicDataPanel, BorderLayout.CENTER);
		
		JPanel appActionBar2 = new JPanel();
		appActionBar2.setLayout(new GridLayout(1,3));
		appActionBar2.add(Box.createGlue());
		appActionBar2.add(appButtonClinicClear);
		appActionBar2.add(Box.createGlue());
		appClinicPanel.add(appActionBar2, BorderLayout.PAGE_END);
		
		/** set action view panel */
		appActionPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;
		appActionPanel.add(appButtonActionLoadImage, c);
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;
		appActionPanel.add(appButtonActionProcess,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 20;
		appActionPanel.add(appButtonActionSaveResult,c);
		
	}
	
	/** getters */
	public JButton getAppButtonPatientClear() {
		return appButtonPatientClear;
	}

	public JButton getAppButtonLaboratoryClear() {
		return appButtonClinicClear;
	}
	
	/** ABOUT FRAME */
	/**
	 * @fn setAboutFrame()
	 * @brief create about frame and display
	 */
	public JFrame setAboutFrame() {
		appAboutFrame.setSize(300,300);
		appAboutFrame.setResizable(true);
		appAboutFrame.setLocationRelativeTo(null);
		appAboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		final Style heading2Style = appAboutStyleContext.addStyle("Heading2", null);
	    heading2Style.addAttribute(StyleConstants.Foreground, Color.BLACK);
	    heading2Style.addAttribute(StyleConstants.FontSize, new Integer(16));
	    heading2Style.addAttribute(StyleConstants.FontFamily, "serif");
	    heading2Style.addAttribute(StyleConstants.Bold, new Boolean(true));
	    heading2Style.addAttribute(StyleConstants.Alignment, Alignment.CENTER);
	    
		try{
			appAboutStyleDoc.insertString(0, appAboutText, null);
			appAboutStyleDoc.setParagraphAttributes(0, 1, heading2Style, false);
		} catch (Exception e) {
			System.out.println("Exception when constructing document: " + e);
		}
		
		appAboutFrame.getContentPane().add(new JScrollPane(appAboutTextPane));
		appAboutFrame.setVisible(true);
		
		return appAboutFrame;
	}
	
/** PATIENT VIEW */

	/**
	 * @fn readPatientView()
	 * @brief read data from patient view
	 * @return patient object
	 * @throws AppException
	 */
	public Patient readPatientView() throws AppException {
		Patient newPatient = new Patient();
		if(Utils.isText(appTextFieldName.getText()))
			newPatient.setName_(appTextFieldName.getText());
		else {
			throw new AppException("Incorrect name");
		}
		if(Utils.isText(appTextFieldSurname.getText()))
			newPatient.setLast_name_(appTextFieldSurname.getText());
		else {
			throw new AppException("Incorrect surname");
		}
		String id_num = appTextFieldID.getText();
		if(Utils.isNumber(id_num)){
			if(id_num.length() < 11)
				throw new AppException("Too short ID number");
			else if(id_num.length() > 11)
				throw new AppException("Too long ID number");
			else
				newPatient.setID_num_(id_num);
		}
		else {
			throw new AppException("Incorrect ID number");
		}
		
		return newPatient;
	}
	
	/**
	 * @fn cleanPatientView()
	 * @brief clean patient view
	 */
	public void cleanPatientView() {
		appTextFieldName.setText("");
		appTextFieldSurname.setText("");
		appTextFieldID.setText("");
	}
	
/** CLINIC VIEW */
	/**
	 * @fn readPatientView()
	 * @brief read data from clinic view
	 * @return clinic object
	 */
	public Clinic readClinicView(){
		Clinic newClinic = new Clinic();
		
		newClinic.setName_(appClinicFieldName.getText());
		newClinic.setAddress_(appClinicFieldAddress.getText());
		
		return newClinic;
	}
	
	public void setOrderNumberView(String num){
		appClinicFiledOrderNumber.setText(num);
	}
	
	public void clearOrderNumberView(String num){
		appClinicFiledOrderNumber.setText("");
	}
	
	/**
	 * @fn cleanClinicView()
	 * @brief clean clinic view
	 */
	public void cleanClinicView() {
		appClinicFieldAddress.setText("");
		appClinicFieldName.setText("");
	}
/** ACTION VIEW */
	
	public Mat loadImage() {
		appFileChooser.setFileFilter(new FileNameExtensionFilter("JPG Images", "jpg", "jpeg"));
		appFileChooser.setDialogTitle("Choose an image");
		int returnValue = appFileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			 pathName = appFileChooser.getSelectedFile().getPath();
			 img = Highgui.imread(pathName, Highgui.CV_LOAD_IMAGE_GRAYSCALE);	
			 BufferedImage image = ImageProcessing.matToBufferedImage(img);
			 BufferedImage image1 = ImageProcessing.ResizedImage(image, appImagePanel.getWidth(), appImagePanel.getHeight());
			 ImageIcon image2 = new ImageIcon(image1);
             appImageLabel.setIcon(image2);  
		}
			 	
		return img;
	}
	
	public void analyseImage()
	{
		img1 = ImageProcessing.Process(img);
		BufferedImage image = ImageProcessing.matToBufferedImage(img1);
		BufferedImage image1 = ImageProcessing.ResizedImage(image, appResultPanel.getWidth(), appResultPanel.getHeight());
		ImageIcon image2 = new ImageIcon(image1);
		numberEryth = ImageProcessing.countEryth(img1); //liczba erytrocytow
		String numberEryth1 = Integer.toString(numberEryth);
		appCounterField.setText(numberEryth1);
		AppView.appResultLabel.setIcon(image2);
	}
	
	public static Mat getImage() 
	{
		return img1;
	}
	
			
	/**
	 * @fn setController()
	 * @brief add listener for all active components
	 * @param AppControler class contains listeners 
	 */
	public void setController(AppController c) {
		appMenuAbout.addActionListener(c);
		appMenuClose.addActionListener(c);
		appButtonPatientClear.addActionListener(c);
		appButtonClinicClear.addActionListener(c);
		appButtonActionLoadImage.addActionListener(c);
		appButtonActionProcess.addActionListener(c);
		appButtonActionSaveResult.addActionListener(c);
	}

	
	
}
