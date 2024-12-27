package com.springCourse.manyToManyJpaMapping.dao;

import com.springCourse.manyToManyJpaMapping.entity.Course;
import com.springCourse.manyToManyJpaMapping.entity.Instructor;
import com.springCourse.manyToManyJpaMapping.entity.InstructorDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppDAO {

    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);

    void deleteInstructorById(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorDetailById(int theId);

    List<Course> findCoursesByInstructorId(int theId);

    Instructor findInstructorByIdJoinFetch(int theId);

    void update(Instructor tempInstructor);

    void update(Course tempCourse);

    Course findCourseById(int theId);

    void deleteCourseById(int theId);

    void saveCourse(Course theCourse);

    Course findCourseAndReviewsByCourseId(int theId);
}
