package com.bis.sourcelead.dao.impl;

import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.StudentDao;
import com.bis.sourcelead.entity.Courses;
import com.bis.sourcelead.entity.Student;
import com.bis.sourcelead.entity.Subjects;

@Repository
public class StudentDaoImpl implements StudentDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void getStudent() {
		logger.debug("In Student get method");
		Student student = null;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			student = (Student) session.load(Student.class,1);
			printData(student);
			Set<Subjects> subjectsSet = student.getSubjects();
            System.out.println("Subjects for student1: ");
            for(Subjects subjects : subjectsSet) {
            	printSubjectsData(subjects);
            	System.out.println("---Courses Data---");
            	Set<Courses> coursesList = subjects.getCourses();
            	for(Courses courses : coursesList){
            		printCoursesData(courses);
            	}
            }
            
            
            /*TO DO
            Here buy defalut we gave fetch mode as LAZY. To change fetch mode dynamically at method level we need to use Criteria and Restrictions.*/ 
            
            Criteria criteria = session.createCriteria(Student.class).add(Restrictions.eq("id", new Integer(2)));
            criteria.setFetchMode("subjects", FetchMode.SELECT);
            Student student2 = (Student) criteria.uniqueResult();
            printData(student2);
			Set<Subjects> subjectsSet2 = student2.getSubjects();
            System.out.println("Subjects for student  2: ");
            for(Subjects subjects2 : subjectsSet2) {
            	printSubjectsData(subjects2);
            	System.out.println("---Courses Data---");
            	Set<Courses> coursesList = subjects2.getCourses();
            	for(Courses courses : coursesList){
            		printCoursesData(courses);
            	}
            	
            	  /*TO DO
                Here buy defalut we gave fetch mode of Subjects is LAZY. To change fetch mode dynamically at method level we need to use Criteria and Restrictions.*/ 
                
            	
            	for(Courses courses : coursesList){
            		System.out.println("---Courses Data By loading on demand---");
	            	Criteria criteria2 = session.createCriteria(Courses.class).add(Restrictions.eq("id", new Integer(courses.getId())));
	                criteria2.setFetchMode("courses", FetchMode.SELECT);
	                Courses courses2 = (Courses) criteria2.uniqueResult();
	                printCoursesData(courses2);
            	}
            	
            	
            }
            
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Student get method end");
		
	}
	
	private void printData(Student student) {
        System.out.println("Student Name="+student.getStudentName()+", standard="+student.getStandard());
    }
	
	private void printSubjectsData(Subjects subjects) {
        System.out.println("Subject Name="+subjects.getSubjectName()+", marks="+subjects.getMarks());
    }
	
	private void printCoursesData(Courses courses) {
        System.out.println("Course Name="+courses.getCourseName()+", Duration="+courses.getDuration());
    }

}
