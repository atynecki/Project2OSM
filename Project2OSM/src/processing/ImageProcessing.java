package processing;
import mvc.AppView;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class ImageProcessing 
{
	public ImageProcessing(){}
		
	static public Mat Process(Mat img)
	{   
		   Mat dst = new Mat();
		   Imgproc.threshold(img, dst, 12, 1, Imgproc.THRESH_OTSU);
		   int dilate_size = 1;
		   Mat element  = Imgproc.getStructuringElement(2, new Size(3 * dilate_size + 1, 3 * dilate_size + 1));
		   Imgproc.Canny(img, dst, 10, 60);
		   Imgproc.dilate(dst, dst, element);
		   List<MatOfPoint> contours = new ArrayList<MatOfPoint>(); 
		   Imgproc.findContours(dst, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		   int retval = contours.size();  // ilosc erytrocytow
		   		    
	    return dst;			
	}
	
	static public void Show(BufferedImage image)
	{
		
	}
	

	
	
}
