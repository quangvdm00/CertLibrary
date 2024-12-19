package org.o7planning.sbhibernate.dao;

import java.util.List;

import org.o7planning.sbhibernate.presistence.Course;
import org.o7planning.sbhibernate.presistence.CourseDto;



public interface CourseDao {
	List<Course> getAll();
	Integer numberOfCourses();
	List<CourseDto> getCoursesDto();
	
	CourseDto addCourse(CourseDto courseDto);
}