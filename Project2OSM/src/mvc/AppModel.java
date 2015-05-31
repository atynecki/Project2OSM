package mvc;

/**
 * @class AppModel
 * @brief class representing all application date, application state and allows access to them.
 * Contains list of patients and controller class
 */

import java.time.LocalDateTime;
import java.util.Random;

import adapters.*;
import data.*;
import processing.*;

public class AppModel {
	private AppController controller;
	private FileAdapter appFile;
	private String csvCellSeparator = " ";
	private String csvLineSeparator = System.lineSeparator();
	private String[] resultFileLabel = {"[Data]", "[Name]", "[Surname]", "[ID number]", "[Clinic name]", "[Clinic address]",
			"[Order numer]"};
	private ImageAdapter appImage;
	private Patient patient;
	private Clinic clinic;
	private String orderNumber;
	private Random randomGenerator = new Random();
	private ImageProcessing processing;
	
	/** default constructors */
	public AppModel (){
		controller = null;
		appFile = new FileAdapter();
		appImage = new ImageAdapter();
		patient = new Patient();
		processing = new ImageProcessing();
	}
	
	/** getters and setters */
	public void setController(AppController c){
		this.controller = c;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public void savePatientData(){
		String dataDirPath = System.getProperty("user.dir") + "/" + "outcome" + "/" + patient.getName_() + " " + patient.getLast_name_() + "/";
		FileAdapter.createDirectory(dataDirPath);
		appFile = new FileAdapter(dataDirPath + "data.txt", csvCellSeparator, csvLineSeparator);
		appFile.openOrCreateToWrite();
		LocalDateTime examDate = LocalDateTime.now();
		appFile.writeLine(resultFileLabel[0] + " " + examDate.toString());
		appFile.writeLine(resultFileLabel[1] + " " + patient.getName_());
		appFile.writeLine(resultFileLabel[2] + " " + patient.getLast_name_());
		appFile.writeLine(resultFileLabel[3] + " " + patient.getID_num_());
		appFile.writeLine(resultFileLabel[4] + " " + clinic.getName_());
		appFile.writeLine(resultFileLabel[5] + " " + clinic.getAddress_());
		appFile.writeLine(resultFileLabel[6] + " " + orderNumber);
	}
	
	public void processImage(){
		
	}
	public void saveResultImage(){
		
	}
	
	public String generateOrderNumber(){
		LocalDateTime examDate = LocalDateTime.now();
		int uniq = randomGenerator.nextInt(1000) ;
		orderNumber = examDate.getDayOfMonth() + "/" + examDate.getMonthValue() + "/" + examDate.getYear() + "/" + uniq;
		return orderNumber;
	}
}
