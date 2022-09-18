package com.zheng.sba.school_management.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

/*
 * tests for Student class
 */
public class StudentTest {

	/*
	 * test getStudentEmail()
	 */
	@Test
	public void getStudentEmailTest() {
		// given
		Student s = new Student();
		String expectedEmail = "abc@xxx.com";
		s.setStudentEmail(expectedEmail);
		// when
		String actualEmail = s.getStudentEmail();
		// then
		Assertions.assertEquals(expectedEmail, actualEmail);
	}

	/*
	 * test getStudentPassword()
	 */
	@Test
	public void getStudentPasswordTest() {
		// given
		Student s = new Student();
		String expectedPwd = "abc@xxx.com";
		s.setStudentPassword(expectedPwd);
		// when
		String actualPwd = s.getStudentPassword();
		// then
		Assertions.assertEquals(expectedPwd, actualPwd);
	}

	/*
	 * test getStudentCourses()
	 */
	@Test
	public void getStudentCoursesTest() {
		// given
		Student s = new Student();
		List<Course> expectedCL = new ArrayList<>();
		Course course1 = new Course(998, "TestJava", "Ms Lewis");
		Course course2 = new Course(999, "TestJavaScript", "Raheem");
		expectedCL.add(course1);
		expectedCL.add(course2);
		s.setStudentCourses(expectedCL);
		// when
		List<Course> actualCL = new ArrayList<>(s.getStudentCourses());
		// then
		//make sure two lists are not referencing same memory
		Assertions.assertNotSame(expectedCL, actualCL);
		//compare two lists hold exactly same data
		Assertions.assertIterableEquals(expectedCL, actualCL);
	}

}
