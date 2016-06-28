package com.bis.sourcelead.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SubjectsDao;
import com.bis.sourcelead.entity.Student;
import com.bis.sourcelead.entity.Subjects;

@Repository
public class SubjectsDaoImpl  implements SubjectsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(SubjectsDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void getSubjects() {
		logger.debug("In Subjects get method");
		Subjects subjects = null;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			subjects = (Subjects) session.load(Subjects.class,1);
			System.out.println("--Child Record--");
			printData(subjects);
			
			/*To Do
			At child side when we take FetchType is EAGER in @OneToMany when child record is loading parent record is also loaded automatically in single query. 
			When we take FetchType as LAZY only child record is loaded and parent record is loaded later on demand when we call parent class*/
			
			Student student = subjects.getStudent();
			System.out.println("--Parent Record--");
			printStudentData(student);
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Subjects get method end");
		
	}
	
	private void printData(Subjects subjects) {
		  System.out.println("Subject Name="+subjects.getSubjectName()+", marks="+subjects.getMarks());
    }
	
	private void printStudentData(Student student) {
        System.out.println("Student Name="+student.getStudentName()+", standard="+student.getStandard());
    }

}
