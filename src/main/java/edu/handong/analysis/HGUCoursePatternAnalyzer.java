package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVRecord;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	private String inputpath;
	private String outputpath;
	private int analysis;
	private String coursecode;
	private int startyear;
	private int endyear;
	private boolean help;
	private String dataPath;
	private String resultPath;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		Options options= new Options();
		
		createOption(options);
		
		if(parseOption(options, args)) {
			if(help) {
				
				System.out.println("help");
				printHelp(options);
				System.exit(0);
				
			}else if(analysis== 1) {
				
				try {
					ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);
					
					students = loadStudentCourseRecords(lines);
					
					// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
					Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
					
					// Generate result lines to be saved.
					ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
					
					// Write a file (named like the value of resultPath) with linesTobeSaved.
					Utils.writeAFile(linesToBeSaved, resultPath);
				}catch(Exception e){
					printHelp(options);
					System.exit(0);
				}
			}else if(analysis== 2) {
				
				try {
					ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);
					
					students = loadStudentCourseRecords(lines);
					
					// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
					Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
					
					// Generate result lines to be saved.
					ArrayList<String> linesToBeSaved = countStudentsNumberOfSemester(sortedStudents);
					
					// Write a file (named like the value of resultPath) with linesTobeSaved.
					Utils.writeAFile(linesToBeSaved, resultPath);
				}catch(Exception e){
					printHelp(options);
					System.exit(0);
				}
				
			}
		}
		
	}
	
	private boolean parseOption(Options options, String[] args) {
		CommandLineParser parser= new DefaultParser();
		
		try {
			CommandLine cmd= parser.parse(options, args);
			
			inputpath= cmd.getOptionValue("i");
			outputpath= cmd.getOptionValue("o");
			analysis= Integer.parseInt(cmd.getOptionValue("a"));
			coursecode= cmd.getOptionValue("c");
			startyear= Integer.parseInt(cmd.getOptionValue("s"));
			endyear= Integer.parseInt(cmd.getOptionValue("e"));
			help= cmd.hasOption("h");
			
			 
		}catch(Exception e){
			printHelp(options);
			return false;
		}
		
		return true;
	}

	

	private void printHelp(Options options) {
		HelpFormatter formatter= new HelpFormatter();
		
		String header= "HGU Course Analyzer";
		String footer= "";
		
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
		
	}

	private void createOption(Options options) {
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());
		
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for \'-a 2\' option")
				.hasArg()
				.argName("course code")
				.required()
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a help page")
				.argName("Help")
				.build());
		
	}

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<CSVRecord> lines) {
		
		students= new HashMap<String, Student>();
		Student student= new Student(lines.get(0).get(0));
		
		for(CSVRecord data: lines) {
			Course course = new Course(data);
			String SstudentId = student.getStudentId();
			String CstudentId= course.getStudentId();
			
			if(SstudentId.equals(CstudentId)) {
				
				student.addCourse(course);
				student.getSemestersByYearAndSemester();
				students.put(SstudentId, student);
			}else {
				
				student= new Student(CstudentId);
				student.addCourse(course);
				
				
			}
		}
		
		return students; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		ArrayList<String> output= new ArrayList<String>();
		String L, R;
		
		output.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInSemester");
		
		for(Student list: sortedStudents.values()) {
			
			L= list.getStudentId();
			L += ", "+ Integer.toString(list.getSemestersByYearAndSemester().size());
			
			for(int i= 1; i<= list.getSemestersByYearAndSemester().size(); i++) {
				
				R= ", ";
				R+= Integer.toString(i);
				R+= ", ";
				R+= Integer.toString(list.getNumCourseInNthSemester(i));
				output.add(L+ R);
				
			}
			
		}
		
		return output; // do not forget to return a proper variable.
	}
	
private ArrayList<String> countStudentsNumberOfSemester(Map<String, Student> sortedStudents) {
		
		ArrayList<String> output= new ArrayList<String>();
		
		String courseName= null;
		String L;
		
		int allStudentNumber;
		int studentTakenNumber;
		
		output.add("Year, Semesterm, CourseCode, CourseName, TotalStudents, StudentsTaken, Rate");
		
		for(int i= startyear; i<= endyear; i++) {
			
				for(int j= 0; j<= 4; j++) {
					
					allStudentNumber= 0;
					studentTakenNumber= 0;
					
					String yearAndSemesterTmp= i+ "-"+ j;
					
					for(Student list: sortedStudents.values()) {
						
						if(list.haveYear(yearAndSemesterTmp)) {
							allStudentNumber++;
							
							if(list.haveCourse(yearAndSemesterTmp, coursecode)) {
								
								studentTakenNumber++;
								
							}
							
							if(courseName== null) {
								
								courseName= list.getCourseName(coursecode);
								
							}
							
						}
						
					}
					
					if(studentTakenNumber!= 0) {
						double percentage= Math.round(((double)studentTakenNumber/(double)allStudentNumber)*10)/10.0;
						
						L= yearAndSemesterTmp.split("-")[0]+ ", "+ yearAndSemesterTmp.split("-")[1]+ ", "+ coursecode+ ", "+ courseName+ ", "+ Integer.toString(allStudentNumber)+ ", "+ 
									Integer.toString(studentTakenNumber)+ ", "+
									Double.toString(percentage) + "%";
						
						output.add(L);
						
					}
				}
			
		}
		
		return output; // do not forget to return a proper variable.
	}
}
