package jpa.mainrunner;

import static java.lang.System.out;

import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.jpa.HibernateEntityManagerFactory;

import jpa.dao.HibernateUtil;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

/**
 *
 */
public class SMSRunner {

	private Scanner sc;
	private StringBuilder sb;

	private CourseService courseService;
	private StudentService studentService;
	private Student currentStudent;

	public SMSRunner() {
		sc = new Scanner(System.in);
		sb = new StringBuilder();
		courseService = new CourseService();
		studentService = new StudentService();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SMSRunner sms = new SMSRunner();
		sms.run();
	}

	private void run() {
		// Login or quit
		try {
			switch (menu1()) {
			case 0:
			case 1:
				if (studentLogin()) {
					registerMenu();
				}
				break;
			case 2: 
			case 9:
				initializeDB();
			default:
				out.println("Goodbye!");

			}
		} catch (InputMismatchException e) {
			out.println("Invalid choice. Goodbye!");
		}
	}

	private int menu1() throws InputMismatchException {
		sb.append("******************************\n   School Management System  \n******************************\n");
		sb.append("\n1. Student Login\n2. Quit Application\n\n9. Reset Database (Choose this first if you are running the application for the first time. \nWARNING!!! : This will initialize database and reset all data!)\n\nPlease Enter Selection: \n");
		out.print(sb.toString());
		sb.delete(0, sb.length());

		return sc.nextInt();
	}

	/*
	 * print out courses in a given course list
	 */
	private void printCourses(List<Course> cl) {
		out.printf("%5s  %-30S  %-20s\n", "ID", "Course Name ", "Instructor Name");
		for (Course course : cl) {
			out.printf("%5s  %-30S  %-20s\n", course.getId(), course.getName(), course.getInstructor());
		}
	}

	private boolean studentLogin() {
		boolean retValue = false;
		out.print("Enter your email address: \n");
		String email = sc.next();
		out.print("Enter your password: \n");
		String password = sc.next();

		if (studentService.validateStudent(email, password)) {
			currentStudent = studentService.getStudentByEmail(email);
			out.println("******************************\n Welcome, " + currentStudent.getsName() + "!\n******************************");
			List<Course> sCourses = studentService.getStudentCourses(email);
			out.println("My Classes :");
			if (sCourses == null) {
				out.println("Currently no course has been registered yet.");
			} else {
				printCourses(sCourses);
			}
			retValue = true;
		} else {
			out.println("Wrong Credentials. GoodBye!");
		}
		return retValue;
	}

	private void registerMenu() {
		sb.append("\n1. Register a class\n2. Logout\nPlease Enter Selection: \n");
		out.print(sb.toString());
		sb.delete(0, sb.length());

		try {
			switch (sc.nextInt()) {
			case 1:
				List<Course> allCourses = courseService.getAllCourses();

				out.println("All Courses: ");
				printCourses(allCourses);
				out.println();
				out.print("Enter Course Number: ");
				int cId = sc.nextInt();
				// register the course
				studentService.registerStudentToCourse(currentStudent.getStudentEmail(), cId);

				// show updated list from database
				Student temp = studentService.getStudentByEmail(currentStudent.getStudentEmail());
				List<Course> sCourses = studentService.getStudentCourses(temp.getStudentEmail());

				out.println("My Classes :");
				printCourses(sCourses);
				//out.println("Do");
				registerMenu();
				break;
			case 2:
			default:
				out.println("Goodbye!");
				HibernateUtil.closeSessionFactory();
			}
		} catch (Exception e) {
			out.println("Invalid choice. Goodbye!");
			HibernateUtil.closeSessionFactory();

		}
	}
	
	private void initializeDB() {
		studentService.initializeStudentData();
		courseService.initializeCourseData();
		out.print("Database successfully initialized. Please run the application again.\n");
		HibernateUtil.closeSessionFactory();
	}
}
