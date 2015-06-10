package processing;
import mvc.AppView;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
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
	public ImageProcessing()
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
		 
		
	// przetwarzanie obrazka
	static public Mat Process(Mat img)
	{      Mat dst = new Mat();
		   Imgproc.threshold(img, dst, 12, 1, Imgproc.THRESH_OTSU);
		   int dilate_size = 2;
		   Mat element  = Imgproc.getStructuringElement(1, new Size(3 * dilate_size + 1, 3 * dilate_size + 1));
		   Imgproc.Canny(img, dst, 10, 60);
		   Imgproc.dilate(dst, dst, element);
		return dst;
	}
	
	// zliczanie erytrocytow z obrazka
	public static int countEryth(Mat dst)
	{
		   List<MatOfPoint> contours = new ArrayList<MatOfPoint>(); 
		   Imgproc.findContours(dst, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		   int eryth = contours.size();
		return eryth;
	}
	
	// konwersja z macierzy na BufferedImage
	public static BufferedImage matToBufferedImage(Mat mat) 
	{
		BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_BYTE_GRAY);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    mat.get(0, 0, data);
	    return image;
	}
	
	//skalowanie obrazka do JLabel
	public static BufferedImage ResizedImage(BufferedImage img, int width, int height) 
	{
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(img, 0, 0, width, height, null);
		g2.dispose();
		return resizedImage;
	}
	
	
	
	

	
	
}
