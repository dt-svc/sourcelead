package com.bis.sourcelead.mockservices;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.stub;
import javax.rmi.CORBA.Stub;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.bis.sourcelead.dao.CitiesDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.impl.CitiesServiceImpl;

import com.bis.sourcelead.service.impl.ProjectsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProjecttestMockServiceTest {

	
	private static final Long DummayValue = 3L;

	/*@Mock
	CitiesDao citiesDao;*/
	
	@Mock
	ProjectsDao projectsDao;
	
	@InjectMocks
	ProjectsService projectsService = new ProjectsServiceImpl();
	
	@Before
	  public void setUp(){
		MockitoAnnotations.initMocks(this);
	}


	
	@Test
	public void getProjectById() throws Exception {
		String[]  s1={"sa","sad","sada"};
		String[]  s2={"sa","sad","sada"};
		
		
		Projects projects1 = new Projects();
		projects1.setClientName(s1);
		projects1.setId(8L);
		
		Projects projects = new Projects();
		projects.setClientName(s2);
		projects.setId(9L);
		
		
		when(projectsDao.getProjectById(anyLong())).thenReturn(projects1);
		assertEquals(projects1.getId(), projectsDao.getProjectById(anyLong()));
		
	}
	
	
	
	//@Test
	public void getProjectByOrgLocationsId() throws Exception {
		Projects projects1 = new Projects();
		projects1.setId(9L);
		
		Projects projects = new Projects();
		projects.setId(19L);
		
		Projects projects2 = new Projects();
		projects.setId(29L);
		
		Projects projects3 = new Projects();
		projects.setId(39L);
		
		Projects projects4 = new Projects();
		projects.setId(49L);
		
		
		
		List<Projects> list = Arrays.asList(projects1,projects,projects3,projects4);
		
		when(projectsDao.getProjectByOrgLocationsId(anyLong())).thenReturn(list);
		
		assertEquals(list.get(0), projects1);
		//stub(projectsDao.getProjectById(DummayValue)).toReturn(projects);
	}
	
	/*@Test
	public void getProjectById() throws Exception {
		
		ProjectsServiceImpl projectsServiceImpl = new ProjectsServiceImpl();
		projectsServiceImpl.setProjectsDao(projectsDao);
		Projects projects = new Projects();
		when(projectsDao.getProjectById(3L)).thenReturn(projects);
		Projects projects1 = projectsServiceImpl.getProjectById(3L);
		assertEquals(projects1, projects);

	}
	
	//@Test
	public void saveProject() throws Exception {
		
		ProjectsServiceImpl projectsServiceImpl = new ProjectsServiceImpl();
		projectsServiceImpl.setProjectsDao(projectsDao);
		Projects projects = new Projects();
		
		projects.setProjectName("fdgsdfg");
		when(projectsDao.saveProject(projects)).thenReturn(projects);
		Projects projects1 = projectsServiceImpl.saveProject(projects);
		assertEquals(projects1, projects);

	}*/
	
	/*@Test
	public void saveCity() throws Exception {
		
		CitiesServiceImpl citiesServiceImpl = new CitiesServiceImpl();
		citiesServiceImpl.setCitiesDao(citiesDao);
		Cities city = new Cities();
		city.setCountryCode("UStetr");
		city.setCityName("KMR");
		city.setStateCode("TG");
		
		when(citiesDao.addCity(city)).thenReturn(true);
		Boolean result = citiesServiceImpl.addCity(city);
		assertTrue(result);
		System.out.println("Successfully Added!!!!");

	}
	
	@Test(expected=Exception.class)
	public void saveCityExp() throws Exception  {
		
		CitiesServiceImpl citiesServiceImpl = new CitiesServiceImpl();
		citiesServiceImpl.setCitiesDao(citiesDao);
		Cities city = new Cities();
		city.setCountryCode("ertertretytryertyeryertyrtyrtyrtyreyryertyeryertyertyerty");
		city.setCityName("KMRrt");
		city.setStateCode("TGrtr");
		
		when(citiesDao.addCity(city)).thenThrow(Exception.class);
		Boolean result = citiesServiceImpl.addCity(city);
		assertTrue(result);
		System.out.println("Successfully Added!!!!");

	}*/
	
	
	
	
	
}
