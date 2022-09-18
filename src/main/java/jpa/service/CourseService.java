package jpa.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jpa.dao.CourseDAO;
import jpa.dao.HibernateUtil;
import jpa.entitymodels.Course;

public class CourseService implements CourseDAO{

	private Course course;
	private List<Course> courseList;

	private Session session;
	private Transaction t;

	/*
	 * internal method to initial session and transaction
	 */
	private void startSessionTransaction() {
		session = HibernateUtil.getSession();
		t = session.beginTransaction();
	}

	@Override
	public List<Course> getAllCourses() {
		startSessionTransaction();

		@SuppressWarnings("unchecked")
		TypedQuery<Course> query = session.getNamedQuery("get_all_courses");
		courseList = query.getResultList();

		t.commit();
		session.close();
		return courseList;
	}

	public Course getCourseById(int id) {
		startSessionTransaction();

		@SuppressWarnings("unchecked")
		TypedQuery<Course> query = session.getNamedQuery("get_course_by_id");
		query.setParameter("cId",id);
		course = query.getSingleResult();

		t.commit();
		session.close();
		return course;
	}

	/*
	 * feed sample data into Course table
	 */
	public void initializeCourseData() {
		try (FileReader fr = new FileReader(".\\src\\main\\resources\\Course-1.sql"); BufferedReader br = new BufferedReader(fr)) {
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
