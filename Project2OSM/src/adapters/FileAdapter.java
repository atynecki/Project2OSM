package adapters;

/**
 * @class FileAdapter
 * @brief class representing adapter to communication with file object 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileAdapter {
	
	protected File file;
	protected BufferedReader bufferedReader;
	protected BufferedWriter bufferedWriter;
	protected String cellSeparator;
	protected String lineSeparator;
	
	/** default constructors */
	public FileAdapter(){
		file = null;
		this.cellSeparator = null;
		this.lineSeparator = null;
	}
	
	/** parameterized constructors */
	public FileAdapter(String filePath, String cellSeparator, String lineSeparator){
		file = new File(filePath);
		this.cellSeparator = cellSeparator;
		this.lineSeparator = lineSeparator;
	}
	
	/**
	 * @fn createDirectory()
	 * @brief create directory according to path
	 * @param path
	 * @return true if create directory correctly
	 */
	public static boolean createDirectory(String path){
		boolean result = false;
		try{
			result = (new File(path)).mkdirs();
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @fn createFileAndDirs()
	 * @brief create directory and file
	 * @throws IOException
	 */
	protected void createFileAndDirs() throws IOException{
		if(!file.exists()){
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
	}
	
	/**
	 * @fn openOrCreateToWrite()
	 * @brief open (if exist) or create file to write 
	 */
	public void openOrCreateToWrite() {
		try {
			createFileAndDirs();
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @fn write()
	 * @brief write String to file
	 * @param String object
	 */
	public void write(String str) {
		if (file.canWrite()) {
			try {
				bufferedWriter.write(str);
				bufferedWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @fn writeLine()
	 * @brief write line to file
	 * @param String object
	 */
	public void writeLine(String line){
		if(lineSeparator == null){
			line += System.getProperty("line.separator");
			write(line);
		} else {
			line += lineSeparator;
			write(line);
		}
	}

	/**
	 * @fn close()
	 * @brief close file
	 */
	public void close(){
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}