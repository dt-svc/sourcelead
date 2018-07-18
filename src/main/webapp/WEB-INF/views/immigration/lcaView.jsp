<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

 <section class="content-wraper content-wraperNew">
	        <div class="success_pop" id="selector" >
	       <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
			</div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>LCA View Details</h2>
      </div>
	   <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="/app/immigration/viewLCAList">LCA List</a> &raquo; LCA Summary</div>
		<input type="hidden" id = "lcaUuid" value = "${lcaUuid}">
    </section>
    
	<section class="immigraTabsWrap">
	
  	<div class="immHeadWrap">
  	 
  	 <div class="immigraDetailsOut">
	 <div class="immigraDetailsWrap">
  	 <div class="immigraDetailsCta">
  	        <h2>LCA Details</h2>
		  	<ul>
				<li class="clearfix"><span>LCA No</span><p>: ${lcaDTO.lcaNo}</p></li>
				<li class="clearfix"><span>LCA Title</span><p>: ${lcaDTO.lcaTitle}</p></li>
				<li class="clearfix"><span>LCA Status</span><p>: ${lcaDTO.lcaStatus}</p></li>
				<li class="clearfix"><span>Status</span><p>: ${lcaDTO.status}</p></li>
				<li class="clearfix"><span>Start Date</span><p>: ${lcaDTO.startDate}</p></li>
				<li class="clearfix"><span>End Date</span><p>: ${lcaDTO.endDate}</p></li>
				<li class="clearfix"><span>No.of Positions</span><p>: ${lcaDTO.noOfPositions}</p></li>
				
			</ul>	
     </div>
  </div>  
    <div class="immigraDetailsWrap"> 
   <div class="immigraDetailsCta">
                <h2>Visa Classifications</h2>
			  	<ul>
				<li class="clearfix"><span>New Employment</span><p>: ${lcaDTO.newEmploymentCount}</p></li>
				<li class="clearfix"><span>Concurrent Employment</span><p>: ${lcaDTO.concurrentEmploymentCount}</p></li>
				<li class="clearfix"><span>Extension</span><p>: ${lcaDTO.extentionCount}</p></li>
				<li class="clearfix"><span>Change in Employer</span><p>: ${lcaDTO.changeInEmployerCount}</p></li>
				<li class="clearfix"><span>Change in Previous<br> Approved Employment</span><p>: ${lcaDTO.changeInPreApproEmplmtCount}</p></li>
				<li class="clearfix"><span>Amendment</span><p>: ${lcaDTO.amendmentCount}</p></li>
				
			</ul>	
   </div>
   </div>
   <div class="clearfix"></div>
       <div class="immigraDetailsWrap"> 
   <div class="immigraDetailsCta">
           <h2>SOC Details</h2>
		  	<ul>
				<li class="clearfix"><span>Job Title</span><p>: ${lcaDTO.jobTitle}</p></li>
				<li class="clearfix"><span>SOC Code</span><p>: ${lcaDTO.socCode}</p></li>
				<li class="clearfix"><span>SOC <br> Occupation Title</span><p>: ${lcaDTO.socOccupationTitle}</p></li>
				<li class="clearfix"><span>Offered Currency</span><p>: ${lcaDTO.offeredWageCurrency}</p></li>
				<li class="clearfix"><span>Offered Wage</span><p>: ${lcaDTO.offeredWage}</p></li>
				<li class="clearfix"><span>Offered Wage Type</span><p>: ${lcaDTO.offeredWageRateType}</p></li>
			</ul>	
   </div>
   </div>
       <div class="immigraDetailsWrap"> 
   <div class="immigraDetailsCta">
                <h2>Prevailing Wage</h2>
			  	<ul>
				<li class="clearfix"><span>Wage Level</span><p>: ${lcaDTO.wagesLevel}</p></li>
				<li class="clearfix"><span>Prevailing  Currency</span><p>: ${lcaDTO.privilingWageCurrency}</p></li>
				<li class="clearfix"><span>Prevailing  Wage</span><p>: ${lcaDTO.prevailingWage}</p></li>
				<li class="clearfix"><span>Prevailing  Wage Type</span><p>: ${lcaDTO.privilingWageRateType}</p></li>
			</ul>	
   </div>
   </div>
    <div class="clearfix"></div>
   <!-- LCA Place Of Employment Locations -->
    <c:forEach var="loc" items="${locList}" begin="0" end="2">
	   <div class="immigraDetailsWrap"> 
	   <div class="immigraDetailsCta">
	           <h2>LCA Place Of Employment Or Location : <c:set var="secCount" value="${secCount+1}" scope="page"/><c:out value="${secCount}" /></h2>
	           
			  	<ul>
					<li class="clearfix"><span>Location</span><p>: ${loc.location}</p></li>
					<li class="clearfix"><span>Address Line 1</span><p>: ${loc.addressLine1}</p></li>
					<li class="clearfix"><span>Address Line 2</span><p>: ${loc.addressLine2}</p></li>
					<li class="clearfix"><span>City</span><p>: ${loc.city}</p></li>
					<li class="clearfix"><span>State</span><p>: ${loc.state}</p></li>
					<li class="clearfix"><span>Country</span><p>: ${loc.country}</p></li>
					<li class="clearfix"><span>ZipCode</span><p>: ${loc.zipCode}</p></li>
				</ul>	
				
	   </div>
	   </div>
   </c:forEach>
   
   </div>
    </div>
    
	
</section> 

<jsp:include page="../masterFooter.jsp" />
 
