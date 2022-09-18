package jpa.entitymodels;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@NamedQueries({
	@NamedQuery(name = "get_student_by_email", query = "FROM Student s WHERE s.sEmail=:email"),
	@NamedQuery(name = "get_all_students", query = "FROM Student"),
	@NamedQuery(name = "get_student_courses_by_email", query = ("FROM Student s JOIN FETCH s.sCourses c WHERE s.sEmail = :email"))
	
})

/*
 * POJO class for student, mapped by student table in the DB
 */
@Entity
@Table(name ="student")
public class Student {
	@Id
	@Column(name = "email", nullable = false, length = 50)
	private String sEmail;
	@Column(name = "name", nullable = false, length = 50)
	private String sName;
	@Column(name = "password", nullable = false, length = 50)
	private String sPassword;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "studnet_course",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
        )
	private List<Course> sCourses;

	public Student() {}
	
	public Student(String sEmail, String sName, String sPassword, List<Course> sCourses) {
		super();
		this.sEmail = sEmail;
		this.sName = sName;
		this.sPassword = sPassword;
		this.sCourses = sCourses;
	}
	
	/*
	 * getters and setters
	 */
	public String getStudentEmail() {
		return sEmail;
	}

	public void setStudentEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsName() {
		return sName;
	}

	public void setStudentName(String sName) {
		this.sName = sName;
	}

	public String getStudentPassword() {
		return sPassword;
	}

	public void setStudentPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	public List<Course> getStudentCourses() {
		return sCourses;
	}

	public void setStudentCourses(List<Course> sCourses) {
		this.sCourses = sCourses;
	}


	
	@Override
	public int hashCode() {
		return Objects.hash(sCourses, sEmail, sName, sPassword);
	}
	
	/*
	 * Overide equals(), ignored course list comparison for the sake of simplicity
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Student other = (Student) obj;
		//Objects.equals(sCourses, other.sCourses)
		return Objects.equals(sEmail, other.sEmail)
				&& Objects.equals(sName, other.sName) && Objects.equals(sPassword, other.sPassword);
	}

	@Override
	public String toString() {
		return "Student [sEmail=" + sEmail + ", sName=" + sName + ", sPassword=" + sPassword + ", sCourses=" + sCourses
				+ "]";
	}

}
