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
	public Mat img = new Mat();
	public Mat img1 = new Mat();
	public String pathName;

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
	public static final String appAboutText = "RCC\n";
	
	/** main window panels */
	private JPanel appDatePanel = new JPanel();
	private JPanel appImagePanel = new JPanel();
	private JPanel appResultPanel = new JPanel();
	
	/** data window panels */
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
	private JFileChooser appFileSave = new JFileChooser();
	
	public static JLabel appImageLabel = new JLabel();
	public static JLabel appResultLabel = new JLabel();
	
	
	/** default constructors (all views set) */
	public AppView(){
		/** set main view */
		this.setTitle("RCC (red cells counter)");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1500,600);
		this.setResizable(true);
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
		
		GridLayout appPanelsLayout = new GridLayout(1,3);
		this.setLayout(appPanelsLayout);
		this.add(appDatePanel);
		this.add(appImagePanel);
		this.add(appResultPanel);
		
		// dodane przeze mnie zeby wyswietlal sie obrazek 
		appImagePanel.setLayout(new FlowLayout());
		appImagePanel.add(appImageLabel);
		appResultPanel.setLayout(new FlowLayout());
		appResultPanel.add(appResultLabel);
				
		
		GridLayout appDatePanelLayout = new GridLayout(3,1);
		appDatePanel.setLayout(appDatePanelLayout);
		appDatePanel.add(appPatientPanel);
		appDatePanel.add(appClinicPanel);
		appDatePanel.add(appActionPanel);
		
		/** set patient view panel */
		appPatientPanel.setLayout(new BorderLayout());
		
		JPanel appPatientDataPanel = new JPanel();
		appPatientDataPanel.setLayout(new GridLayout(0,2,2,20));
		
		appPatientDataPanel.add(appLabelName);
		appPatientDataPanel.add(appTextFieldName);
		appPatientDataPanel.add(appLabelSurname);
		appPatientDataPanel.add(appTextFieldSurname);
		appPatientDataPanel.add(appLabelID);
		appPatientDataPanel.add(appTextFieldID);
		
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
		appActionPanel.setLayout(new GridLayout(2,2,2,20));
		appActionPanel.add(appButtonActionLoadImage);
		appActionPanel.add(appButtonActionProcess);
		appActionPanel.add(appButtonActionSaveResult);
		
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
             ImageIcon icon = new ImageIcon(pathName);
             appImageLabel.setIcon(icon);  
		}
			img = Highgui.imread(pathName);// jest okej
			//Highgui.imwrite("D:\\KOALA_1_orgin.jpg", img); // jest okej
			//Highgui.imwrite("D:\\KOALA_1.jpg", img); // dupa
			
			// Cialo funkcji Process z ImageProcessing
		   Mat dst = new Mat();
		   Mat dst1 = new Mat();
		   Mat dst2 = new Mat();

		   Imgproc.threshold(img, dst, 12, 1, Imgproc.THRESH_BINARY);
		   Highgui.imwrite("D:\\KOALA_1_binary.jpg", dst);
		   int dilate_size = 1;
		   Mat element  = Imgproc.getStructuringElement(2, new Size(3 * dilate_size + 1, 3 * dilate_size + 1));
		   Imgproc.Canny(dst, dst1, 10, 60);
		   Highgui.imwrite("D:\\KOALA_1_canny.jpg", dst1);
		   Imgproc.dilate(dst1, dst2, element);
		   Highgui.imwrite("D:\\KOALA_1_dilate.jpg", dst2);
		   List<MatOfPoint> contours = new ArrayList<MatOfPoint>(); 
		   Imgproc.findContours(dst2, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		   int retval = contours.size();  // ilosc erytrocytow
		   Highgui.imwrite("D:\\KOALA_1.jpg", dst);
				
		return img;
	}
	

	public void analyseImage()
	{
		//img1 = ImageProcessing.Process(img);//gubi obrazek
		BufferedImage image = new BufferedImage(img1.cols(), img1.rows(), BufferedImage.TYPE_3BYTE_BGR);
		img1.get(0,0,((DataBufferByte)image.getRaster().getDataBuffer()).getData()); 
		AppView.appResultLabel.setIcon((Icon)image);
			// teoretyczne wyswietlenie w Jlabel
		
		//wgraj obrazek do Jlabel i wrzuc liczbe erytrocytow
		// zwroc liczbe erytrocytow i obrazek zeby zapisac
		//return img1;
	}
	
	public void saveImage()
	{
		appFileSave.setFileFilter(new FileNameExtensionFilter("JPG Images", "jpg"));
		appFileSave.setDialogTitle("Save an image");
		int returnValue = appFileSave.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			String file_save = appFileChooser.getCurrentDirectory().getAbsolutePath();
			Highgui.imwrite(file_save, img1);  
		}
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
