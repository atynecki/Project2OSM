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
	public ImageProcessing(){System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
		
	static public void Process(Mat img)
	{   
		 			// cialo funkcji jest poki co w AppView pod image load
	}
	
	static public void Show(BufferedImage image)
	{
		
	}
	

	
	
}
