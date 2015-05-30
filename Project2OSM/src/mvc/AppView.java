package mvc;

import java.util.*;
import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import data.*;

public class AppView extends JFrame{

	private static final long serialVersionUID = 1L;

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
	private JPanel appLabPanel = new JPanel();
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
	
	/** laboratory panel components */
	private JButton appButtonLaboratoryClear = new JButton("Clear");
	
	/** action panel components */
	/* action button */
	private JButton appButtonActionLoadImage = new JButton("Image load");
	private JButton appButtonActionProcess = new JButton("Analyze");
	private JButton appButtonActionSaveResult = new JButton("Save result");
	
	private JFileChooser appFileChooser = new JFileChooser();
	
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
		appLabPanel.setBorder(BorderFactory.createTitledBorder("Laboratory"));
		appActionPanel.setBorder(BorderFactory.createTitledBorder("Control"));
		
		appImagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
		appResultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
		
		GridLayout appPanelsLayout = new GridLayout(1,3);
		this.setLayout(appPanelsLayout);
		this.add(appDatePanel);
		this.add(appImagePanel);
		this.add(appResultPanel);

		GridLayout appDatePanelLayout = new GridLayout(3,1);
		appDatePanel.setLayout(appDatePanelLayout);
		appDatePanel.add(appPatientPanel);
		appDatePanel.add(appLabPanel);
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
		appActionBar1.setLayout(new GridLayout(1,1));
		appActionBar1.add(appButtonPatientClear);
		appPatientPanel.add(appActionBar1, BorderLayout.PAGE_END);
		
		/** set laboratory view panel */
		
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
		return appButtonLaboratoryClear;
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
	
/** LABORATORY VIEW */
	/**
	 * @fn cleanLaboratoryView()
	 * @brief clean laboratory view
	 */
	public void cleanLaboratoryView() {
		
	}
/** ACTION VIEW */
	
	public void loadImage() {
		int returnValue = appFileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File readFile  = appFileChooser.getSelectedFile();
          //TODO sprawdzenie czy jest to plik z obrazem i zrzutowanie na obraz
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
		appButtonActionLoadImage.addActionListener(c);
		appButtonActionProcess.addActionListener(c);
		appButtonActionSaveResult.addActionListener(c);
	}
	
}
