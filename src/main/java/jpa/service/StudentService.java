package jpa.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.print.DocFlavor.URL;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jpa.dao.HibernateUtil;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

/*
 * StudentService provides DAO implementation for student class
 */
public class StudentService implements StudentDAO {

	private List<Student> studentList;
	private Student student;
	private List<Course> studentCourseList;

	private CourseService cs;
	private Session session;
	private Transaction t;

	/*
	 * internal method to initial session and transaction
	 */
	private final void startSessionTransaction() {
		session = HibernateUtil.getSession();
		t = session.beginTransaction();

	}

	/*
	 * return all student list
	 */
	@Override
	public List<Student> getAllStudents() {
		startSessionTransaction();

		@SuppressWarnings("unchecked")
		TypedQuery<Student> query = session.getNamedQuery("get_all_students");
		studentList = query.getResultList();

		t.commit();
		session.close();

		return studentList;
	}

	/*
	 * retrieve Student using NamedQuery in the Student class
	 */
	@Override
	public Student getStudentByEmail(String sEmail) {
		startSessionTransaction();

		@SuppressWarnings("unchecked")
		TypedQuery<Student> query = session.getNamedQuery("get_student_by_email");
		query.setParameter("email", sEmail);
		student = query.getSingleResult();
		student.toString(); // to avoid org.hibernate.lazyinitializationexception
		t.commit();
		session.close();

		return student;
	}

	@Override
	public boolean validateStudent(String sEmail, String sPassword) {

		// check if entered password matches the password stored in the DB
		try {
			if (sPassword.equals(getStudentByEmail(sEmail).getStudentPassword()))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/*
	 * first retrieve Student's registered courses using getStudentCourses() then
	 * register the course if not registered already
	 */
	@Override
	public void registerStudentToCourse(String sEmail, int cId) {

		studentCourseList = getStudentCourses(sEmail);
		cs = new CourseService();
		Course newCourse = cs.getCourseById(cId);
		// if the course is not registered, add it to the studentCourseList and update
		// DB
		if (studentCourseList == null || !studentCourseList.contains(newCourse)) {
			student.getStudentCourses().add(newCourse);
			startSessionTransaction();
			session.update(student);
			t.commit();
			session.close();
			System.out.println("Course is successfully registered!");
		} else { // course is already registered for the student
			System.out.println("You are already registered in that course!");
		}
	}

	/*
	 * retrieve Student registered courses using NamedQuery in the Student class
	 */
	@Override
	public List<Course> getStudentCourses(String sEmail) {
		try {
			startSessionTransaction();
			@SuppressWarnings("unchecked")
			TypedQuery<Student> query = session.getNamedQuery("get_student_courses_by_email");
			//if use "get_student_by_email", using toString() on student after querying to avoid org.hibernate.lazyinitializationexception
			query.setParameter("email", sEmail);
			student = query.getSingleResult();
			studentCourseList = student.getStudentCourses();
			// System.out.println("Course list : " + studentCourseList);
			t.commit();
			session.close();

			return studentCourseList;
		} catch (Exception e) {
			session.close();
			return null;

		}
	}
	
	/*
	 * feed sample data into Student table
	 */
	public void initializeStudentData(){
		try (FileReader fr = new FileReader(".\\src\\main\\resources\\Student-1.sql"); BufferedReader br = new BufferedReader(fr)) {
			String line = br.readLine();
			startSessionTransaction();
			while ((line = br.readLine()) != null) {
				Query query = session.createNativeQuery(line);
				query.executeUpdate();
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
