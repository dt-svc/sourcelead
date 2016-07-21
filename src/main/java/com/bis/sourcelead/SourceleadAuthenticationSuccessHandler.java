package com.bis.sourcelead;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Custom AuthenticationSuccessHandler
 * 
 * @author: Naresh Bolisetty
 * @date: 25-May-2016
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.bis.sourcelead")
public class SourceleadAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.sendRedirect("/sourcelead/dashboard");
	}
}