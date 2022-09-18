package jpa.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/*
 * Utility class to handle Hibernate sessionFactory creation/close and session creation.
 */
public class HibernateUtil {
	
	/*
	 * upon object initialization, it will try to create a SessionFactory
	 */
	private static SessionFactory sf = null;
	static {
		try {
			sf = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("SessionFactory creation failed.");
			System.out.println(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sf;
	}
	
	/*
	 * return a session if succesfully created
	 */
	public static Session getSession() {
		try {
			Session session = sf.openSession();
			return session;
		} catch (Exception e) {
			System.out.println("Session open failed.");
			System.out.println(e);
		}
		return null;
	}
	
	/*
	 * close SessionFactory 
	 */
	public static void closeSessionFactory() {
		try {
			sf.close();
		} catch (Exception e) {
			System.out.println("Unable to close sessionFactory.");
			System.out.println(e);
		}
	}

}
