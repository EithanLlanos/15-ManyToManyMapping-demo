package com.springCourse.manyToManyJpaMapping.dao;

import com.springCourse.manyToManyJpaMapping.entity.Course;
import com.springCourse.manyToManyJpaMapping.entity.Instructor;
import com.springCourse.manyToManyJpaMapping.entity.InstructorDetail;
import com.springCourse.manyToManyJpaMapping.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {

    private final EntityManager entityManager;

    public AppDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
        // Retrieve the instructor
        Instructor tempInstructor = entityManager.find(Instructor.class, theId);

        // Get the courses
        List<Course> courses = tempInstructor.getCourses();

        // Break association of all courses for the instructor

        for (Course tempCourse : courses) {
            tempCourse.setInstructor(null);
        }

        // Delete the instructor
        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class, theId);
    }

//    // Method to delete Instructor detail and Instructor at the same time
//    public void deleteInstructorDetailById(int theId) {
//        // retrieve instructor detail
//        InstructorDetail tempInstructorDetail = entityManager.find(InstructorDetail.class, theId);
//        // delete instructor detail
//        entityManager.remove(tempInstructorDetail);
//    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {
//        Retrieve instructor detail
        InstructorDetail tempInstructorDetail = entityManager.find(InstructorDetail.class, theId);

//        Remove the associated object reference
//        Break bidirectional link
        tempInstructorDetail.getInstructor().setInstructorDetail(null);

//        Delete the Instructor Detail
        entityManager.remove(tempInstructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
//        Create query
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id=:data", Course.class
        );
        query.setParameter("data", theId);

//        Execute the query
        List<Course> courses = query.getResultList();
        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {
//        Create query

        TypedQuery<Instructor> query = entityManager.createQuery(
                "select i from Instructor i "
                        + "join fetch i.courses "
                        + "join fetch i.instructorDetail "
                        + "where i.id = :data", Instructor.class);
        query.setParameter("data", theId);

//        Execute query
        Instructor instructor = query.getSingleResult();
        return instructor;

    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        entityManager.merge(tempInstructor);
    }

    @Override
    @Transactional
    public void update(Course tempCourse) {
        entityManager.merge(tempCourse);
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class, theId);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {
//        Retrieve the course
        Course tempCourse = entityManager.find(Course.class, theId);
//        Delete the course
        entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void saveCourse(Course theCourse) {
        entityManager.persist(theCourse);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {
        // create query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                        + "join fetch c.reviews "
                        + "where c.id = :data", Course.class);
        query.setParameter("data", theId);
        // execute query

        Course theCourse = query.getSingleResult();
        return theCourse;

    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {
//        Create query
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c "
                + "join fetch c.students "
                + "where c.id = :data", Course.class);
        query.setParameter("data", theId);
//        Execute query
        Course course = query.getSingleResult();
        return course;
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int theId) {
//        Create query
        TypedQuery<Student> query = entityManager.createQuery(
                "select s from Student s "
                        + "join fetch s.courses "
                        + "where s.id = :data", Student.class);
        query.setParameter("data", theId);
//        Execute query
        Student student = query.getSingleResult();
        return student;
    }

    @Override
    @Transactional
    public void update(Student tempStudent) {
        entityManager.merge(tempStudent);
    }

}
