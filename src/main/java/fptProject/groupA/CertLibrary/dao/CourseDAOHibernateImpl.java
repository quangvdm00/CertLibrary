package fptProject.groupA.CertLibrary.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fptProject.groupA.CertLibrary.persistence.Course;
import fptProject.groupA.CertLibrary.persistence.CourseDto;
import fptProject.groupA.CertLibrary.persistence.CourseHomePageDto;

@Repository
public class CourseDAOHibernateImpl implements CourseDao {
	
	private static final String GET_COURSE_DTO = "SELECT cd.course_id AS id, c.course_tittle AS name, cd.image AS image, \r\n"
			+ "c.platform AS platform, c.category AS category, cd.course_length AS courseLength, GROUP_CONCAT(cs.skill_name SEPARATOR ';') skills\r\n"
			+ "FROM course_detail AS cd\r\n"
			+ "JOIN course AS c\r\n"
			+ "ON c.course_id = cd.course_id\r\n"
			+ "JOIN course_skills AS cs\r\n"
			+ "ON cd.course_id = cs.course_id\r\n"
			+ "GROUP BY cd.course_id;";
	
	private static final String GET_COURSES_HOME_PAGE_DTO = 
			"SELECT e.employee_id AS id, e.full_name AS fullName, c.course_tittle AS tittle, \r\n"
			+ "c.platform AS platform, c.category AS category, cd.course_length AS courseLength \r\n"
			+ "FROM employee AS e\r\n"
			+ "JOIN course_employee AS ce\r\n"
			+ "ON e.employee_id = ce.employee_id\r\n"
			+ "JOIN course AS c\r\n"
			+ "ON ce.course_id = c.course_id\r\n"
			+ "JOIN course_detail AS cd\r\n"
			+ "ON c.course_id = cd.course_id;";
	
	private EntityManager entityManager;
	
	// constructor injection
	@Autowired
	public CourseDAOHibernateImpl (EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	// Get all the courses
	@Override
	public List<Course> getAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createNamedQuery(Course.GET_ALL, Course.class).getResultList();
	}

//	Tổng số khóa học
	@Override
	public Integer numberOfCourses() {
		Session currentSession = entityManager.unwrap(Session.class);
		String sql = "SELECT count(c.course_id) FROM course AS c JOIN course_employee";
		return currentSession.createQuery(sql, Integer.class).getFirstResult();
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<CourseDto> getCoursesDto() {
		Session openSession = entityManager.unwrap(Session.class).getSessionFactory().openSession();
		NativeQuery<?> query = openSession.createNativeQuery(GET_COURSE_DTO);
		query.addScalar(CourseDto.ID, StandardBasicTypes.INTEGER)
								.addScalar(CourseDto.NAME, StandardBasicTypes.STRING)
								.addScalar(CourseDto.IMAGE, StandardBasicTypes.STRING)
								.addScalar(CourseDto.PLATFORM, StandardBasicTypes.STRING)
								.addScalar(CourseDto.CATEGORY, StandardBasicTypes.STRING)
								.addScalar(CourseDto.COURSE_LENGTH, StandardBasicTypes.DOUBLE)
								.addScalar(CourseDto.SKILLS, StandardBasicTypes.STRING)
								.setResultTransformer(Transformers.aliasToBean(CourseDto.class));
		return (List<CourseDto>) query.getResultList();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CourseHomePageDto> getCoursesHomePageDto() {
		Session openSession = entityManager.unwrap(Session.class).getSessionFactory().openSession();
		NativeQuery<?> query = openSession.createNativeQuery(GET_COURSES_HOME_PAGE_DTO);
		query.addScalar(CourseHomePageDto.ID, StandardBasicTypes.INTEGER)
						.addScalar(CourseHomePageDto.FULL_NAME, StandardBasicTypes.STRING)
						.addScalar(CourseHomePageDto.TITTLE, StandardBasicTypes.STRING)
						.addScalar(CourseHomePageDto.PLATFORM, StandardBasicTypes.STRING)
						.addScalar(CourseHomePageDto.CATEGORY, StandardBasicTypes.STRING)
						.addScalar(CourseHomePageDto.COURSE_LENGTH, StandardBasicTypes.INTEGER)
						.setResultTransformer(Transformers.aliasToBean(CourseHomePageDto.class));
		return (List<CourseHomePageDto>) query.getResultList();
	}

}
