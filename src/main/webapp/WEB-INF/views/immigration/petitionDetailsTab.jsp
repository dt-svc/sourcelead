<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
		  
		  <div class="immigraDetailsOut">
		  	<div class="immigraDetailsWrap">
			  <div class="immigraDetailsCta">
				  <h2>Petition detail</h2>
				  <ul>
					  <li class="clearfix"><span>Petition type</span><p>: ${immigration.petitionType}</p></li>
					   <li class="clearfix"><span>Process type</span><p>: ${immigration.processType}</p></li>
					  <li class="clearfix"><span>Open date</span><p>: ${immigration.openDate}</p></li>
					  <li class="clearfix"><span>Current USCIS Status</span><p>: ${immigration.currUscisStatus}</p></li>
					  <li class="clearfix"><span>Date of arrival</span><p>: ${immigration.arrivalDate}</p></li>
					  <li class="clearfix"><span>Place of entry</span><p>: ${immigration.entryPlace}</p></li>
					  <li class="clearfix"><span>Choose Template</span><p>: ${immigration.templateName}</p></li>
				  </ul>
			  </div>
			  <div class="immigraDetailsCta">
			  	<c:choose>
					<c:when test="${SourceLeadWeb_orgLocations.isLawFirm == 'Y'}">
						 <h2>Company Details</h2>
						  <ul>
							  <li class="clearfix"><span>Company Name</span><p>: ${immigration.attorneyName}</p></li>
							  <li class="clearfix"><span>Comapny Contact name</span><p>: ${immigration.attorneyContact}</p></li>
							  <li class="clearfix"><span>Email</span><p>: ${immigration.attorneyEmail} </p></li>
							  <li class="clearfix"><span>Phone</span><p>: ${immigration.attorneyPhone}</p></li>
							  <li class="clearfix"><span>Fax</span><p>: ${immigration.attorneyFax}</p></li>
							  
						  </ul>
					</c:when>
					<c:otherwise>
						 <h2>Attorney Details</h2>
						  <ul>
							  <li class="clearfix"><span>Attorney Name</span><p>: ${immigration.attorneyName}</p></li>
							  <li class="clearfix"><span>Attorney Contact name</span><p>: ${immigration.attorneyContact}</p></li>
							  <li class="clearfix"><span>Email</span><p>: ${immigration.attorneyEmail} </p></li>
							  <li class="clearfix"><span>Phone</span><p>: ${immigration.attorneyPhone}</p></li>
							  <li class="clearfix"><span>Fax</span><p>: ${immigration.attorneyFax}</p></li>
							  
						  </ul>
					</c:otherwise>
				</c:choose>
				  
			  </div>
			  <div class="immigraDetailsCta">
				  <h2>Job Details</h2>
				  <ul>
					  <li class="clearfix"><span>Job Title</span><p>: ${immigration.jobTitle}</p></li>
					  <li class="clearfix"><span>Employment Level</span><p>: ${immigration.employmentLevelName}</p></li>
					  <li class="clearfix"><span>Employer</span><p>: ${immigration.employer}</p></li>
					  <li class="clearfix"><span>Location</span><p>: ${immigration.jobLocation}</p></li>
					  <li class="clearfix"><span>Start Date</span><p>: ${immigration.jobStartDate}</p></li>
					  <li class="clearfix"><span>End Date</span><p>: ${immigration.jobEndDate}</p></li>
				  </ul>
			  </div>			  
			  </div>
			  <div class="immigraDetailsWrap">
			  <div class="immigraDetailsCta">
				  <h2>Beneficiary details</h2>
				  <ul>
					  <li class="clearfix"><span>Associate (Beneficiary)</span><p>: ${immigration.associateName}</p></li>
					  <li class="clearfix"><span>First Name</span><p>: ${immigration.firstName}</p></li>
					  <li class="clearfix"><span>Middle Name</span><p>: ${immigration.middleName}</p></li>
					  <li class="clearfix"><span>Last Name</span><p>: ${immigration.lastName}</p></li>
					  <li class="clearfix"><span>Email</span><p>: ${immigration.email}</p></li>
					  <li class="clearfix"><span>Mobile</span><p>: ${immigration.mobile}</p></li>
					  <li class="clearfix"><span>Alternative Mobile</span><p>: ${immigration.alternateMobile}</p></li>
					  <li class="clearfix"><span>DOB</span><p>: ${immigration.birthDate}</p></li>
					  <li class="clearfix"><span>Place of Birth</span><p>: ${immigration.birthPlace}</p></li>
					  <li class="clearfix"><span>Nationality</span><p>: ${immigration.nationality}</p></li>
					  <li class="clearfix"><span>Marital status</span><p>: ${immigration.maritalStatus}</p></li>
					  <li class="clearfix"><span>SSN</span><p>: ${immigration.ssnNumber}</p></li>
					  <li class="clearfix"><span>I-94 Number</span><p>: ${immigration.i_94_number}</p></li>
					  <li class="clearfix"><span>I-20 SEVIS Number If Student</span><p>: ${immigration.i_20_number}</p></li>
					  <li class="clearfix"><span>EAD Number If Student</span><p>: ${immigration.eadNumber}</p></li>
					 <%--  <li class="clearfix"><span>Previous LCA Number</span><p>: ${immigration.prevLcaNumber}</p></li>
					  <li class="clearfix"><span>Previous LCA Start Dates</span><p>: ${immigration.prevLcaStartDate}</p></li> --%>
					  <li class="clearfix"><span>Experience</span><p>: ${not empty immigration.experienceYears ? immigration.experienceYears : '0'} years ${not empty immigration.experienceMonths ? immigration.experienceMonths : '0'} months </p></li>
				  </ul>
			  </div>	
			  </div>		  			  			  
			  <div class="clearfix"></div>
		  </div>	
		  
		<div class="immFoot clearfix">
			<div class="pull-right text-right">
              <input type="button" value="Continue"  onclick="goToSummaryTab('#passportsTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>		  		  		  
	 
	  
	 <script>
	 
	 $(document).ready(function() {
		 //location.href = sourceleadUrl + immiModulePath + "/viewImmigration?immigrationId="+immigrationId;
	 });	
	 
	 </script>