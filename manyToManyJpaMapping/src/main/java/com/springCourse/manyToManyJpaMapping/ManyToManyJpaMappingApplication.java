package com.springCourse.manyToManyJpaMapping;

import com.springCourse.manyToManyJpaMapping.dao.AppDAO;
import com.springCourse.manyToManyJpaMapping.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ManyToManyJpaMappingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManyToManyJpaMappingApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AppDAO appDAO) {
        return runner -> {
            createCourseAndStudents(appDAO);
        };
    }

    private void createCourseAndStudents(AppDAO appDAO) {
//		Create a course
        Course tempCourse = new Course("How to lock in");
//		Create the students
//		Add Students to the course

        tempCourse.addStudent(new Student("Martio", "Rifato", "martior@gmail.com"));
        tempCourse.addStudent(new Student("Dilia", "Sinheta", "dilias@gmail.com"));
        tempCourse.addStudent(new Student("Trech", "Polter", "trechp@gmail.com"));
//		Save the course and associated students

        appDAO.saveCourse(tempCourse);

    }

    private void deleteCourseAndReviews(AppDAO appDAO) {
        int theId = 10;
        System.out.println("Deleting course " + theId);
//        Will delete course and associated reviews
        appDAO.deleteCourseById(theId);
    }

    private void retrieveCourseAndReviews(AppDAO appDAO) {

        // Get course and reviews
        int theId = 10;
        Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);

        // print the course

        System.out.println(tempCourse);

        // print the reviews

        System.out.println(tempCourse.getReviews());
    }

    private void createCourseAndReviews(AppDAO appDAO) {
//        Create a course
        Course tempCourse = new Course("Pacman - How to score one million points");
//        Add some reviews
        tempCourse.addReview(new Review("Great course"));
        tempCourse.addReview(new Review("After all, good course"));
        tempCourse.addReview(new Review("A really bad one"));
//        Save the course
        appDAO.saveCourse(tempCourse);
        System.out.println();
    }

    private void deleteCourse(AppDAO appDAO) {
        int theId = 11;
//        Find the course
        appDAO.deleteCourseById(theId);
    }

    private void updateCourse(AppDAO appDAO) {
        int theId = 10;
        // find the course
        Course tempCourse = appDAO.findCourseById(theId);
        // Update the course
        System.out.println("Updating course Id: " + theId);
        tempCourse.setTitle("Enjoy the simple thing");

        appDAO.update(tempCourse);
    }


    private void updateInstructor(AppDAO appDAO) {
        int theId = 1;
//        Find the instructor
        System.out.println("Finding instructor id: " + theId);
        Instructor tempInstructor = appDAO.findInstructorById(theId);
//        Update the instructor
        System.out.println("Update instructor id: " + theId);
        tempInstructor.setLastName("Tester");
        appDAO.update(tempInstructor);

    }

    private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
        int theId = 3;
//        Find instructor
        System.out.println("Finding instructor: " + theId);
        Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);
//
        System.out.println("tempInstructor: " + tempInstructor);
        System.out.println("Associated courses " + tempInstructor.getCourses());
    }

    private void findCoursesForInstructor(AppDAO appDAO) {
        int theId = 1;
//        Find instructor
        System.out.println("Finding constructor id: " + theId);
        Instructor tempInstructor = appDAO.findInstructorById(theId);
        System.out.println("tempInstructor: " + tempInstructor);

//        Find courses for instructor
        System.out.println("Finding courses for instructor id: " + theId);
        List<Course> courses = appDAO.findCoursesByInstructorId(theId);
//        Associate the courses
        tempInstructor.setCourses(courses);
        System.out.println("The associated courses: " + tempInstructor.getCourses());
    }

    private void findInstructorWithCourses(AppDAO appDAO) {
        int theId = 1;
        System.out.println("Finding instructor id: " + theId);

        Instructor tempInstructor = appDAO.findInstructorById(theId);

        System.out.println("tempInstructor: " + tempInstructor);
        System.out.println("the associated courses: " + tempInstructor.getCourses());
    }

    private void createInstructorWithCourses(AppDAO appDAO) {
//        Create the Instructor
        Instructor tempInstructor = new Instructor("Troch", "Pinetas", "susan.public@luv2code.com");
//        Create the Instructor Detail
        InstructorDetail tempInstructorDetail = new InstructorDetail("http://youtube.com", "Guitar");
//        Associate the objects
        tempInstructor.setInstructorDetail(tempInstructorDetail);

//        Create some courses
        Course tempCourse1 = new Course("All Piano 2- The ultimate guide");
        Course tempCourse2 = new Course("The Ball Masterclass 2");
//        Add courses to instructor
        tempInstructor.add(tempCourse1);
        tempInstructor.add(tempCourse2);
//        Save the instructor
//        This will ALSO save the courses
//        Because of CascadeType.PERSIST
        System.out.println("Saving instructor: " + tempInstructor);
        System.out.println("The courses: " + tempInstructor.getCourses());
        appDAO.save(tempInstructor);
        System.out.println("Done");
    }

    private void deleteInstructorDetail(AppDAO appDAO) {
        int theId = 5;
        appDAO.deleteInstructorDetailById(theId);
    }

    private void findInstructorDetail(AppDAO appDAO) {
        int theId = 1;

        System.out.println("Finding instructor detail id: " + theId);
        InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);
        System.out.println("tempInstructorDetail: " + tempInstructorDetail);
        System.out.println("the associated instructor: " + tempInstructorDetail.getInstructor());
    }

    private void deleteInstructor(AppDAO appDAO) {
        int theId = 5;
        appDAO.deleteInstructorById(theId);
    }

    private void findInstructor(AppDAO appDAO) {
        int theId = 2;
        System.out.println("Finding instructor id: " + theId);

        Instructor tempInstructor = appDAO.findInstructorById(theId);
        System.out.println("TempInstructor: " + tempInstructor);
        System.out.println("The associate Instructor Detail only: " + tempInstructor.getInstructorDetail());

    }

    private void createInstructor(AppDAO appDAO) {
//		Create the instructor
        Instructor tempInstructor = new Instructor("Arath", "Mitrao", "arath@luv2code.com");

//		Create the instructor email
        InstructorDetail tempInstructorDetail = new InstructorDetail("Mitrao.com", "VideoGames!");

//		Associate the object
        tempInstructor.setInstructorDetail(tempInstructorDetail);

//		Save the instructor - this will also save the details object because of cascadeType.ALL
        System.out.println("Saving instructor: " + tempInstructor);
        appDAO.save(tempInstructor);
    }
}
