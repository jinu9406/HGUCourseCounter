package edu.handong.analysis.datamodel;

import edu.handong.analysis.utils.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.csv.CSVRecord;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterTaken;
	
	
	
	public Course(CSVRecord data) {
		
		this.studentId= data.get(0).trim();
		this.yearMonthGraduated= data.get(1).trim();
		this.firstMajor= data.get(2).trim();
		this.secondMajor= data.get(3).trim();		
		this.courseCode= data.get(4).trim();
		this.courseName= data.get(5).trim();
		this.courseCredit= data.get(6).trim();
		this.yearTaken= Integer.parseInt(data.get(7).trim());
		this.semesterTaken= Integer.parseInt(data.get(8).trim());
			
	}	
	
	public String getStudentId() {
		return this.studentId;
	}
	
	public String getYearAndSemester() {
		String yearAndSemester= Integer.toString(yearTaken)+ "-"+ Integer.toString(semesterTaken);
		
		return yearAndSemester;
	}
	
	public String getCourseCode() {
		
		return this.courseCode;
		
	}
	
	public String getCourseName() {
		return this.courseName;
	}

	

}
