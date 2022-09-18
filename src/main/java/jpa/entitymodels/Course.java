package jpa.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@NamedQueries({
	@NamedQuery(name = "get_course_by_id", query = "FROM Course c WHERE c.cId=:cId"),
	@NamedQuery(name = "get_all_courses", query = "FROM Course")
})

@Entity
@Table(name = "course")
public class Course {
	@Id
	@Column(name = "id", nullable = false)
	private int cId;
	@Column(name = "name", nullable = false, length = 50)
	private String cName;
	@Column(name = "instructor", nullable = false, length = 50)
	private String cInstructorName;

	public Course () {}

	public Course(int id, String name, String instructor) {
		this.cId = id;
		this.cName = name;
		this.cInstructorName = instructor;
	}
	
	/*
	 * getters and setters
	 */
	public int getId() {
		return cId;
	}

	public void setId(int id) {
		this.cId = id;
	}

	public String getName() {
		return cName;
	}

	public void setName(String name) {
		this.cName = name;
	}

	public String getInstructor() {
		return cInstructorName;
	}

	public void setInstructor(String instructor) {
		this.cInstructorName = instructor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Course other = (Course) obj;
		if (cId != other.cId)
			return false;
		if (cInstructorName == null) {
			if (other.cInstructorName != null)
				return false;
		} else if (!cInstructorName.equals(other.cInstructorName))
			return false;
		if (cName == null) {
			if (other.cName != null)
				return false;
		} else if (!cName.equals(other.cName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Course [id=" + cId + ", name=" + cName + ", instructor=" + cInstructorName + "]";
	}

}
