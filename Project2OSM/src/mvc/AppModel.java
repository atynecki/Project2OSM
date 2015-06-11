package mvc;

/**
 * @class AppModel
 * @brief class representing all application date, application state and allows access to them.
 * Contains list of patients and controller class
 */

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import adapters.*;
import data.*;
import processing.*;

public class AppModel {
	private AppController controller;
	private FileAdapter appFile;
	private String csvCellSeparator = " ";
	private String csvLineSeparator = System.lineSeparator();
	private String[] resultFileLabel = {"[Data]", "[Name]", "[Surname]", "[ID number]", "[Clinic name]", "[Clinic address]",
			"[Order numer]" , "[Number erythrocytes]"};
	private Patient patient;
	private Clinic clinic;
	private String orderNumber;
	private Random randomGenerator = new Random();
	private ImageProcessing processing;
	public int numberEryth;
	
	/** default constructors */
	public AppModel (){
		controller = null;
		appFile = new FileAdapter();
		patient = new Patient();
		processing = new ImageProcessing();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	/** getters and setters */
	public void setController(AppController c){
		this.controller = c;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	public int getNumberEryth() {
		return numberEryth;
	}

	/**
	 * @fn loadImage()
	 * @brief load source image
	 * @param file path
	 * @return file object
	 */
	public BufferedImage loadImage(String path) {
		 AppView.img_src = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);	
		 BufferedImage image = ImageProcessing.matToBufferedImage(AppView.img_src);
		 
		 return image;
	}
	
	/**
	 * @fn processImage()
	 * @brief analyze image
	 * @return file object after analyze
	 */
	public BufferedImage processImage()
	{
		AppView.img_result = processing.process(AppView.img_src);
		BufferedImage image = ImageProcessing.matToBufferedImage(AppView.img_result);
		numberEryth = processing.countEryth(AppView.img_result);
		
		return image;
	}

	/**
	 * @fn saveData()
	 * @brief save analyze result image + patient and clinic data
	 */
	public void saveData(){
		String dataDirPath1 = System.getProperty("user.dir");
		String pathIMG  = dataDirPath1 + "\\" + "outcome" + "\\" + patient.getName_() + " " + patient.getLast_name_() + "\\" + "result.jpg";
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
		appFile.writeLine(resultFileLabel[7] + " " + numberEryth);
		appFile.close();
		Highgui.imwrite(pathIMG,AppView.img_result); 
	}
	
	/**
	 * @fn generateOrderNumber()
	 * @brief generate order number
	 * @return order number in String representation
	 */
	public String generateOrderNumber(){
		LocalDateTime examDate = LocalDateTime.now();
		int uniq = randomGenerator.nextInt(1000) ;
		orderNumber = examDate.getDayOfMonth() + "/" + examDate.getMonthValue() + "/" + examDate.getYear() + "/" + uniq;
		return orderNumber;
	}
}
