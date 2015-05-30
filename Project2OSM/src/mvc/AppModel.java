package mvc;

/**
 * @class AppModel
 * @brief class representing all application date, application state and allows access to them.
 * Contains list of patients and controller class
 */

import java.time.LocalDateTime;

import adapters.*;
import data.*;
import processing.*;

public class AppModel {
	private AppController controller;
	private FileAdapter appFile;
	private String csvCellSeparator = " ";
	private String csvLineSeparator = System.lineSeparator();
	private String[] resultFileLabel = {"[Data]", "[Name]", "[Surname]", "[ID number]"};
	private ImageAdapter appImage;
	private Patient patient;
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
	}
	
	public void processImage(){
		
	}
	public void saveResultImage(){
		
	}
}
