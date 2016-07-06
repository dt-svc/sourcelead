package com.bis.sourcelead;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.GenericFilterBean;

import com.bis.sourcelead.entity.TokenInfo;
import com.bis.sourcelead.service.AuthenticationService;

@Component
@EnableTransactionManagement
@ComponentScan(basePackages = "com.bis.sourcelead")
public final class CustomTokenAuthenticationFilter extends GenericFilterBean {
	private static final String REQUEST_ATTR_DO_NOT_CONTINUE = "MyAuthenticationFilter-doNotContinue";
	
	@Autowired
	AuthenticationService authenticationService;

	private  String logoutLink = "/logout";
	
	private static final String HEADER_TOKEN = "X-CustomToken";
	private static final String HEADER_USERNAME = "X-Username";
	private static final String HEADER_PASSWORD = "X-Password";
 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException
	{
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		boolean authenticated = checkToken(httpRequest, httpResponse);
		if (authenticated) {
			checkLogout(httpRequest);
		}
		checkLogin(httpRequest, httpResponse);
		if (canRequestProcessingContinue(httpRequest)) {
			chain.doFilter(request, response);
		}
	}
	private void checkLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		String username = httpRequest.getHeader(HEADER_USERNAME);
		String password = httpRequest.getHeader(HEADER_PASSWORD);
	   if (username != null && password != null) {
			checkUsernameAndPassword(username, password, httpResponse);
			doNotContinueWithRequestProcessing(httpRequest);
		}
	}
	private void checkUsernameAndPassword(String username, String password, HttpServletResponse httpResponse) throws IOException {
		TokenInfo tokenInfo = authenticationService.authenticate(username, password);
		if (tokenInfo != null) {
			httpResponse.setHeader(HEADER_TOKEN, tokenInfo.getToken());
			// TODO set other token information possible: IP, ...
		} else {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean checkToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		String token = httpRequest.getHeader(HEADER_TOKEN);
		if (token == null) {
			return false;
		}

		if (authenticationService.checkToken(token)) {
			System.out.println(" *** " + HEADER_TOKEN + " valid for: " +
				SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return true;
		} else {
			System.out.println(" *** Invalid " + HEADER_TOKEN + ' ' + token);
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			doNotContinueWithRequestProcessing(httpRequest);
		}
		return false;
	}

   private void checkLogout(HttpServletRequest httpRequest) {
		if (currentLink(httpRequest).equals(logoutLink)) {
			String token = httpRequest.getHeader(HEADER_TOKEN);
			// we go here only authenticated, token must not be null
			authenticationService.logout(token);
			doNotContinueWithRequestProcessing(httpRequest);
		}
	}

	private String currentLink(HttpServletRequest httpRequest) {
		if (httpRequest.getPathInfo() == null) {
			return httpRequest.getServletPath();
		}
		return httpRequest.getServletPath() + httpRequest.getPathInfo();
	}

	private void doNotContinueWithRequestProcessing(HttpServletRequest httpRequest) {
		httpRequest.setAttribute(REQUEST_ATTR_DO_NOT_CONTINUE, "");
	}

	private boolean canRequestProcessingContinue(HttpServletRequest httpRequest) {
		return httpRequest.getAttribute(REQUEST_ATTR_DO_NOT_CONTINUE) == null;
	}
 
}
