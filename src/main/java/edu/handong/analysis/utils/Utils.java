package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Utils {
	
	public static ArrayList<String> getLines (String file, boolean removeHeader) {
	
		ArrayList<String> line= new ArrayList<String>();
		File objectFile= new File(file);
		
		try {
			FileReader fileReader= new FileReader(objectFile);
			BufferedReader bufferedReader= new BufferedReader(fileReader);
			String data= null;
			
			while((data= bufferedReader.readLine())!= null) {
				line.add(data);
			}if(removeHeader= true) {
				line.remove(0);
			}
			
			bufferedReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("There is no file. ");
			System.exit(0);
		}catch(IOException e) {
			System.out.println(e);
			System.exit(0);
		}
		
		return line;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
		PrintWriter outputStream= null;
		
		try {
			File file= new File(targetFileName);
			
			if((file.exists())!= true) {
				file.getParentFile().mkdirs();
			}
			
			outputStream= new PrintWriter(file);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		for(String data: lines) {
			outputStream.println(data);
		}
		
		outputStream.close();
	}
}
