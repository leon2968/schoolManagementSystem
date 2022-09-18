/**
 *
 */
package com.zheng.sba.school_management.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jpa.dao.HibernateUtil;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.StudentService;

/**
 * @author Z
 *
 */
class StudentServiceTest {

	private static StudentService sc = null;
	private static Student s1, s2;
	private static Course c1, c2;

	/**
	 * Initialize StudentService, then setup testing environment for all tests
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeAll() throws Exception {
		sc = new StudentService();
		c1 = new Course(998, "TestJava", "Ms Lewis");
		c2 = new Course(999, "TestJavaScript", "Raheem");
		List<Course> cl1 = new ArrayList<>( Arrays.asList(c1, c2));
		List<Course> cl2 = new ArrayList<>( Arrays.asList(c1));
		s1 = new Student("Test1@xxx.com", "TestStudent1", "Testpass1", cl1);
		s2 = new Student("Test2@xxx.com", "TestStudent2", "Testpass2", cl2);
		Session session = HibernateUtil.getSession();
		Transaction t = session.beginTransaction();
		session.persist(s1);
		session.persist(s2);
		t.commit();
		session.close();


	}

	/**
	 * Not used for now
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUpBeforeEach() throws Exception {

	}


	/**
	 * Rollback the changes in DB then close sessionFactory
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterAll() throws Exception {

		//System.out.flush();
		Session session = HibernateUtil.getSession();
		Transaction t = session.beginTransaction();
		session.delete(s1);
		session.delete(s2);
		session.delete(c1);
		session.delete(c2);
		t.commit();
		HibernateUtil.closeSessionFactory();
	}


	@Test
	public void getStudentByEmailTest() {
		Student actualS = sc.getStudentByEmail("Test1@xxx.com");
		Assertions.assertEquals(s1,actualS);
	}

	/*
	 * Test validateStudent() using correct and incorrect passwords
	 */
	@ParameterizedTest
	@CsvSource({"Test1@xxx.com, Testpass1, true",
				"Test1@xxx.com, TestpassXXX, false",
				"Test2@xxx.com, Testpass2, true",
				"Test1@xxx.com, Testpass2, false",})
	public void validateStudentTest(String email, String pass, boolean veracity) {
		boolean isValidated = sc.validateStudent(email, pass);
		//case when password matches
		if(veracity)
			Assertions.assertTrue(isValidated);
		//case when password is wrong
		else
			Assertions.assertFalse(isValidated);
	}

	/*
	 * Test registerStudentToCourse()
	 * when given a new course , the student's course list will be updated to contain that course
	 * when given a old course , the student's course list will not change
	 */
	@Test
	//@ValueSource(ints = {998,999}) //course Id(999) for c2
	public void registerStudentToCourseTest() {
		sc.registerStudentToCourse("Test2@xxx.com", 999);
		List<Course> cl = sc.getStudentCourses("Test2@xxx.com");
//		System.out.print("\n\n\n************************************************************************\n");
//		System.out.println(cl);
//		System.out.println(c2);
//		System.out.print("************************************************************************\\n\\n\\n");
		Assertions.assertTrue(cl.contains(c2));
	}
	
//	@Test //assert no exception is thrown, the method needs to throw an exception in order for this to work
//	public void initialStudentDataTest() {
//		Assertions.assertAll(() -> sc.initializeStudentData());
//	}
}
