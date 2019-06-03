package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {
	
	public static ArrayList<CSVRecord> getLines (String file, boolean removeHeader) {
	
		ArrayList<CSVRecord> lines= new ArrayList<CSVRecord>();
		File objectFile= new File(file);
		
		try {
			
			CSVParser parser= new CSVParser(new FileReader(objectFile), CSVFormat.DEFAULT.withHeader());
			
			for(CSVRecord line: parser) {
				lines.add(line);				
			}
		}catch(FileNotFoundException e) {
			System.out.println("There is no file. ");
			System.exit(0);
		}catch(IOException e) {
			System.out.println(e);
			System.exit(0);
		}
		
		return lines;
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
