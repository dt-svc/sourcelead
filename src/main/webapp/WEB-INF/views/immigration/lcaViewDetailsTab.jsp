<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  	 
  	 <div class="immigraDetailsOut">
	 <div class="immigraDetailsWrap">
  	 <div class="immigraDetailsCta">
  	        <h2>LCA Details &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h2>
		  	<ul>
				<li class="clearfix"><span>LCA No</span><p>: ${lcaDTO.lcaNo}</p></li>
				<li class="clearfix"><span>LCA Title</span><p>: ${lcaDTO.lcaTitle}</p></li>
				<li class="clearfix"><span>LCA Status</span><p>: ${lcaDTO.lcaStatus}</p></li>
				
				<c:if test="${lcaDTO.status == '1'}">
				<li class="clearfix"><span>Status</span><p>: Active</p></li>
				</c:if>
				<c:if test="${lcaDTO.status == '0'}">
				<li class="clearfix"><span>Status</span><p>: In-active</p></li>
				</c:if>
				
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
   <!-- LCA Place Of Employment Locations  View-->
    <c:forEach var="loc" items="${locList}">
	   <div class="immigraDetailsWrap"> 
	   <div class="immigraDetailsCta">
	           <h2>LCA Place Of Employment (OR) Location : <c:set var="secCount" value="${secCount+1}" scope="page"/><c:out value="${secCount}"/></h2>
	           
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
   <!-- LCA Documents View -->
   <c:forEach var="doc" items="${docList}">
	   <div class="immigraDetailsWrap"> 
	   <div class="immigraDetailsCta">
	           <h2>LCA Document : <c:set var="docCount" value="${docCount+1}" scope="page"/><c:out value="${docCount}" /></h2>
	           
			  	<ul>
					<li class="clearfix"><span>Document Title</span><p>: ${doc.documentTitle}</p></li>
					<li class="clearfix"><span>File Name</span><p>: ${doc.fileName} <span class="pull-right"><a class="fa fa-eye" onClick="viewFile('${doc.fileUrl}','${doc.fileName}','${doc.fileContentType}');"></a> &nbsp;<a download class="glyphicon glyphicon-download-alt" target="_blank" href="${doc.fileUrl}" download title="Download"></a></span></p> </li>
					<li class="clearfix"><span>Comment</span><p>: ${doc.description}</p></li>
				</ul>	
				
	   </div>
	   </div>
   </c:forEach>
  
   </div>
