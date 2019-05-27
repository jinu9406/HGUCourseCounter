package edu.handong.analysis.datamodel;

import edu.handong.analysis.utils.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	
	
	
	public Course(String line) {
		
		this.studentId= line.split(",")[0].trim();
		this.yearMonthGraduated= line.split(",")[1].trim();
		this.firstMajor= line.split(",")[2].trim();
		this.secondMajor= line.split(",")[3].trim();
		this.courseCode= line.split(",")[4].trim();
		this.courseName= line.split(",")[5].trim();
		this.courseCredit= line.split(",")[6].trim();
		this.yearTaken= Integer.parseInt(line.split(",")[7].trim());
		this.semesterTaken= Integer.parseInt(line.split(",")[8].trim());
			
	}	
	
	public String getStudentId() {
		return this.studentId;
	}
	
	public String getYearAndSemester() {
		String yearAndSemester= Integer.toString(yearTaken)+ "-"+ Integer.toString(semesterTaken);
		
		return yearAndSemester;
	}

	

}
