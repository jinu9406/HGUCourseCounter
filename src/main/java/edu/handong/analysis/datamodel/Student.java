package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	
	private String studentId;
	private ArrayList<Course>coursesTaken;
	private HashMap<String, Integer>semestersByYearAndSemester;

	public Student(String studentId) {
		this.studentId= studentId;
		this.coursesTaken= new ArrayList<Course>();
		this.semestersByYearAndSemester= new HashMap<String, Integer>();
	}
	
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
		
		
	}
	
	public HashMap<String, Integer> getSemestersByYearAndSemester(){
		
		
		if(semestersByYearAndSemester.containsKey(coursesTaken.get(0).getYearAndSemester())!= true) {
			semestersByYearAndSemester.put(coursesTaken.get(0).getYearAndSemester(), semestersByYearAndSemester.size()+ 1);
			
		}
		
		
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSemester(int semester) {
		
		int count= 0;
		String tmp= null;
		
		for(String string: semestersByYearAndSemester.keySet()) {
			
			if(semestersByYearAndSemester.get(string).equals(semester)) {
				tmp= string;
				break;
			}
			
		}
		for(Course course: coursesTaken) {
			
			if(course.getYearAndSemester().equals(tmp)) {
				count++;
			}
			
		}
		
		return count;
	}
	
	public String getStudentId() {
		return this.studentId;
	}
}
