<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript" src="/resources/js/contractCommonScripts.js"></script>

<script>
$(document).ready(function(){
	$("#tempEye").hide();
	
	if('${contract.ownersType}' == "Sequential"){
		 getSequentialApprovers();
	 }else{
		 getParallelApprovers();
	 }
});
</script>

<!-- these files for autocomplete with tagit -->
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Existing Contract</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/contractListbyStatus'/>">Contracts</a> &raquo;Add Existing Contract</div>
    </section>
    <div class="content-area content-new clearfix">
     <div id="existLoading"></div>
     <form:form id="newcontract_form" name="newcontract_form" method="post">
      <div class="clearfix form-horizontal">
       
        <div class="row newContrWrap">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           
           <h2>Contract Details</h2>
            <div class="form-group">
              <label class="col-md-4">Contract Type<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <c:choose>
				    <c:when test="${!empty contract.contractType}">
				    	<input type="text" readonly class="form-control" id="contractType" name="contractType" value="${contract.contractType == 'Vendor NDA' ? 'NDA' : contract.contractType == 'Vendor NSA' ? 'NSA' : contract.contractType}" data-validation="required" field-name="Contract Type">
				    </c:when>
				    <c:otherwise>
				        <select class="chosen-select form-control" id="contractType" name="contractType" onchange="contractTypeLabels(); submissionOffers();" data-validation="required" field-name="Contract Type">
		                    <option value=''>Contract Type</option>
		                    <option>Client MSA</option>
		                    <option>Vendor MSA</option>
		                    <option>Client SOW</option>
		                    <option>Vendor SOW</option>
		                    <option>NDA</option>
		                    <option>NSA</option>
		                    <option>Full time offer</option>
		                 </select>
				    </c:otherwise>
				 </c:choose>
            </div>
            </div>	
             <div class="form-group">
              <label class="col-md-4">Attachment<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8 clearfix">
							
							<div class="input-group positionRelative ratetype col-md-10 pull-left" id="uploadFileGroup" title="Upload Documents">
	                        <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
	                          <input type="file" id="uploadBtn" style="display: none;"  class="upload">
	                            </span> </label>
		                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload .pdf File." field-name="Attachment" data-validation="required validate_Space validatepdf">
		                         
	                        </div>
	                        <!-- <p class="text-muted pull-right small"><i class="fa fa-info-circle"></i>Please upload PDF type doc.</p> -->
	                        <span class="col-md-2 noPad clearfix" title="View File" id="tempEye" style="display:block;"> <i class="fa fa-eye btn btn-default formic1 pull-right minHeBtn" onclick="previewTemplatePDF()"></i> </span>
	                        
              </div>
            </div>  			
			</div>
			
			
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
          	
          	<div class="form-group">
              <label class="col-md-4">Contract Title<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                <input type="text" class="form-control" id="contractTitle" name="contractTitle" value="" data-validation="required validate_length length1-250" field-name="Contract Title" onblur="validateContractTitle();">
              </div>
            </div>
                       
           <!--  <div class="form-group">
              <label class="col-md-4">Select template<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="template" name="template"  data-validation="required" field-name="Template">
                  </select>
              </div>
            </div>   -->
            
            <div class="form-group" id="requirementDiv" style="display: none;">
              <label class="col-md-4">Job</label>
              <div class="col-md-8">
                  <select class="form-control" id="requirementsList" name="requirementsList" field-name="Job" onchange="submissionOffers1();">
                  </select>
              </div>
            </div>
            
            <div class="form-group" id="offeredSubmissionDiv" style="display: none;">
              <label class="col-md-4">Select Applicant<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8" id="submissionOffersListDiv">
                  <select class="form-control" id="submissionOffersList" name="submissionOffersList" onchange="getOfferRate(); getVendor();" field-name="Applicant">
                  </select>
              </div>
            </div>
                     
             
          <%-- <div class="form-group" >
              <label class="col-md-4">Signing Authority<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="owner" name="owner" data-validation="required" field-name="Owner">
                  <option value=''>Select Signing Authority</option>
                  <c:forEach var="user" items="${userList}">
	                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
                  </c:forEach>
                   <!--  <option>Select Owner</option>ER
                    <option>Owner-1</option>
                    <option>Owner-2</option>
                    <option>Owner-3</option> -->
                  </select>
              </div>
          </div>   --%>           
             <div class="form-group" id="dateDiv">
              <label class="col-md-4">Start & End date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
					<div class="row">
					  <div class="col-lg-6">
						<div id="startDateDiv">
						  <input type="text" class="form-control calendar" id="startDate" name="startDate" placeholder="Start date" data-validation="required" field-name="Start Date" readonly="readonly">
						</div>
					  </div>
					  <div class="col-lg-6">
						<div id="endDateDiv">
						<div class="input-group errorInptgroup">
						  <input type="text" class="form-control calendar" id="endDate" name="endDate" placeholder="End date" readonly="readonly">
						  <span class="input-group-addon" onClick="clearExistedContractEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
						  </div>
						</div>
					  </div>
					</div>
              </div>
            </div>
          
         <div class="form-group" id="vendorDiv" style="display: ${contract.contractType == 'Vendor MSA' || contract.contractType == 'Vendor SOW' || contract.contractType == 'Vendor NDA' || contract.contractType == 'Vendor NSA' || contract.contractType == 'NDA' || contract.contractType == 'NSA' ? 'block' : 'none'};">
              <label class="col-md-4">Vendor<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8 chooRadius" id="errorVendor">
               <div class="input-group col-md-10 pull-left errorInptgroup">
                  <select class="chosen-select form-control" id="orgRelationId" name="orgRelationId" onchange="getVendorContacts();" data-validation="${contract.contractType == 'Vendor MSA' || contract.contractType == 'Vendor SOW' ? 'required' : ''}" field-name="Vendor Name">
                    <option value=''>Select Vendor</option>
                   <c:forEach var="orgrelations" items="${orgRelationsList}">
                         <option value="${orgrelations.id}" ${orgrelations.id  == contract.orgRelations.id ? 'selected' : ''}>${orgrelations.orgName}</option>
                  </c:forEach>
                  </select>
                   <span class="input-group-addon cursorPointer bg-blue" onclick="refreshVendorList();addExistedEmptyVendorContact();"><i class="fa fa-refresh "></i></span>
              </div>
               <sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')"> 
               <span class="col-md-2 noPad clearfix"><a href="<c:url value="/app/addVendor" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>
          </sec:authorize>
          </div>     
           </div>                   
                      
            <div class="form-group" id="vendorContactDiv"  style="display: ${contract.contractType == 'Vendor MSA' || contract.contractType == 'Vendor SOW' || contract.contractType == 'Vendor NDA' || contract.contractType == 'Vendor NSA' || contract.contractType == 'NDA' || contract.contractType == 'NSA' ? 'block' : 'none'};">
              <label class="col-md-4">Vendor Contact<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8 chooRadius" id="errorvendorContact">
                <div class="input-group col-md-10 pull-left errorInptgroup">
              		<c:choose>
	              		<c:when test="${fn:length(orgSpocList) gt 0}">
		              		<select class="chosen-select form-control" id="vendorContact" name="vendorContact" data-validation="${contract.contractType == 'Vendor MSA' || contract.contractType == 'Vendor SOW' || contract.contractType == 'Vendor NDA' || contract.contractType == 'Vendor NSA' || contract.contractType == 'NDA' || contract.contractType == 'NSA' ? 'block' : 'none'};" field-name="Vendor Contact">
							    <option value=''>Select Vendor Contact</option>
							    <c:forEach var="element" items="${orgSpocList}" >
							    	<c:if test = "${element.tempOwnerId == contract.contactUser.id}">
								         <option selected value="${element.tempOwnerId}">${element.spocName} (${element.ownerUser.firstName})</option>
								    </c:if>
								    <c:if test = "${element.tempOwnerId != contract.contactUser.id}">
								         <option value="${element.tempOwnerId}">${element.spocName} (${element.ownerUser.firstName})</option>
								    </c:if>
								</c:forEach>
							</select>
					    </c:when>
					    <c:otherwise>
					        <select class="chosen-select form-control" id="vendorContact" name="vendorContact" data-validation="required" field-name="Vendor Contact" onchange="removeVendorContactValidation();">
					        <option value=''>Select Vendor Contact</option></select>
					    </c:otherwise>
					 </c:choose>
					  <span class="input-group-addon cursorPointer bg-blue" onclick="getVendorContacts();"><i class="fa fa-refresh "></i></span>
              </div>
                 <span class="col-md-2 noPad clearfix" onclick="selectedVendor();">
			<sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR_CONTACT')"> 
			<a href="javascript:void(0)"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
			</sec:authorize>
		 </span>
            </div>
             </div>
            
             <div class="form-group" id="clientDiv" style="display: ${contract.contractType == 'Client MSA' || contract.contractType == 'Client SOW' ? 'block' : 'none'};">
              <label class="col-md-4">Client<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8 chooRadius" id="errorClient">
 					 <div class="input-group col-md-10 pull-left errorInptgroup">
	                  <select class="chosen-select form-control" id="clientOrgRelationId" name="clientOrgRelationId" onchange="getClientContacts();" data-validation="${contract.contractType == 'Client MSA' || contract.contractType == 'Client SOW' ? 'required' : ''}" field-name="Client Name">
	                    <option value=''>Select Client</option>
	                   <c:forEach var="orgrelations" items="${clientList}">
	                         <option value="${orgrelations.id}" ${orgrelations.id  == contract.orgRelations.id ? 'selected' : ''}>${orgrelations.orgName}</option>
	                  </c:forEach>
	                  </select>
	                        <span class="input-group-addon cursorPointer bg-blue" onclick="refreshClientList();addExistedEmptyClientContact();"><i class="fa fa-refresh "></i></span>
                  </div>
                  <sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT')"> 
                      <span class="col-md-2 noPad clearfix"><a href="<c:url value="/app/client" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>
                 </sec:authorize>
              </div>
          </div>                      
                      
            <div class="form-group" id="clientContactDiv" style="display: ${contract.contractType == 'Client MSA' || contract.contractType == 'Client SOW' ? 'block' : 'none'};">
              <label class="col-md-4">Client Contact<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8 chooRadius" id="errorClientContact">
              		<div class="input-group col-md-10 pull-left errorInptgroup">
              		<c:choose>
	              		<c:when test="${fn:length(orgSpocList) gt 0}">
		              		<select class="chosen-select form-control" id="clientContact" name="clientContact" data-validation="${contract.contractType == 'Client MSA' || contract.contractType == 'Client SOW' ? 'block' : 'none'};" field-name="Client Contact">
							    <c:forEach var="element" items="${orgSpocList}" >
							    	<c:if test = "${element.spocUserId == contract.contactUser.id}">
								         <option selected value="${element.spocUserId}">${element.ownerName} (${element.ownerUser.firstName})</option>
								    </c:if>
								    <c:if test = "${element.spocUserId != contract.contactUser.id}">
								         <option value="${element.spocUserId}">${element.ownerName} (${element.ownerUser.firstName})</option>
								    </c:if>
								</c:forEach>
							</select>
					    </c:when>
					    <c:otherwise>
					        <select class="chosen-select form-control" id="clientContact" name="clientContact" data-validation="required" field-name="Client Contact">
					        <option value=''>Select Client Contact</option></select>
					    </c:otherwise>
					 </c:choose>
					 <span class="input-group-addon cursorPointer bg-blue" onclick="getClientContacts();"><i class="fa fa-refresh "></i></span>
					 </div>
					  <span class="col-md-2 noPad clearfix" onclick="selectedClient();">
			           <sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT_CONTACT')"> 
			           <a href="javascript:void(0)"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
			         </sec:authorize>
			</span>
              </div>

            </div>
        
            <div class="form-group" id="offerRateDiv" style="display: ${contract.contractType == 'Client SOW' || contract.contractType == 'Vendor SOW' ? 'block' : 'none'};">
	              <label class="col-md-4">Offer Rate</label>
	              <div class="col-md-8">
	                <input type="text" class="form-control" disabled value="${requirementOffer.paymentType} ${requirementOffer.amount}/${requirementOffer.paymentMode}" id="offerRate" name="offerRate" field-name="Offer Rate">
	              </div>
            </div>
                          
            <div class="form-group " id="descriptionDiv">
              <label class="col-md-4">Description</label>
              <div class="col-md-8">
					<textarea class="form-control" rows="3" id="mainDescription" name="description" data-validation-optional="true" data-validation="validate_length length1-250"></textarea>
              </div>
            </div>    
            <div class="form-group" id="workLocationDiv">
              <label class="col-md-4">Work Location</label>
              <div class="col-md-8">
					<textarea class="form-control" rows="3" id="workLocation" name="workLocation" data-validation-optional="true" data-validation="validate_length length1-500"></textarea>
              </div>
            </div> 
            
            <div class="form-group" id="joinDateDiv">
              <label class="col-md-4">Join Date</label>
                <div class="col-lg-6">
                <div class="input-group errorInptgroup">
					<input type="text" class="form-control calendar" value="${contract.startDateStr}" id="joinDate" name="joinDate" placeholder="Join date"  field-name="Join Date" readonly="readonly">
					 <span class="input-group-addon" onClick="clearExistedContractJoinDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
				</div>	
				</div>
            </div> 
            <div class="form-group">
              <label class="col-md-4">State of Jurisdiction</label>
              <div class="col-md-8">
                <input type="text" class="form-control" id="jurisdiction" name="jurisdiction" field-name="State Jurisdiction" data-validation="validate_length length0-250">
              </div>
            </div>     
            
            <!-- Radio buttons for Sequential or Parallel function -->
	   		 <div  class="clearfix"></div>
                <div  class="ml pull-left apprverReq" >
                <label class="rad" id="parallel">
                <input type="radio" name="ownersType" id="parallelapprovers" value="Parallel" checked="checked" onclick="getParallelApprovers();">
                <i class="fa fa-align-center apprverReq-active" title="Parallel"></i>
                </label>
                <label class="rad" id="sequential">
                <input type="radio" name="ownersType" id="seqentialApprovers" value="Sequential" ${'SEQUENTIAL'  == contract.ownersType ? 'checked' : ''} onclick="getSequentialApprovers();">
                <i class="fa fa-retweet" title="Sequential"></i>
                </label>
                 
                  <div id="navbar" class="glyphicon glyphicon-info-sign text-info ml"></div>
                </div>
                <!-- Radio buttons for Sequential or Parallel function -->
           
             <div  class="clearfix"></div>
          <div class="form-group row" id="parallelAppDiv" style="display:block">
              <label class="col-md-4">Signing Authority<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select multiple class="chosen-select form-control" id="parallelOwner" name="parallelOwner" data-placeholder="Select signing authority" data-validation="required" field-name="Owner">
                  <c:forEach var="user" items="${userList}">
	                  <c:if test = "${!empty contract.ownerUserId.id}">
	                 	 <option value="${user.id}" ${user.id  == contract.ownerUserId.id ? 'selected' : user.id  == contract.ownerUserId.id ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
	                  </c:if>
	                  <c:if test = "${empty contract.ownerUserId.id}">
	                  	 <option value="${user.id}" ${user.id  == contract.ownerUserId.id ? 'selected' : user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
	                  </c:if>
                  </c:forEach>
                  </select>
              </div>
          </div> 
          
           <div id="sequentialAppDiv" style="display:none">
          <div class="form-group row">
              <label class="col-md-4">Signing Authority1<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="sequentialOwner1" name="sequentialOwner1" data-placeholder="Select signing authority1" data-validation="required" onchange="refreshSigningAuthority2();" field-name="sequentialOwner1">
                  <option value=''>Select Signing Authority1</option>
                  <c:forEach var="user" items="${userList}">
	                  <c:if test = "${!empty contract.sequentialOwner1}">
	                 	 <option value="${user.id}" ${user.id  == contract.sequentialOwner1 ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
	                  </c:if>
	                  <c:if test = "${empty contract.sequentialOwner1}">
	                  	 <option value="${user.id}" ${user.id  == contract.sequentialOwner1 ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
	                  </c:if>
                  </c:forEach>
                  </select>
              </div>
          </div> 
          
          <div class="form-group row">
              <label class="col-md-4">Signing Authority2<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="sequentialOwner2" name="sequentialOwner2" data-placeholder="Select signing authority2" onchange="changeSaveButton();" data-validation="required" field-name="sequentialOwner2">
                  <option value=''>Select Signing Authority2</option>
                  <c:forEach var="user" items="${userList}">
	                  <c:if test = "${!empty contract.sequentialOwner2}">
	                 	 <option value="${user.id}" ${user.id  == contract.sequentialOwner2 ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
	                  </c:if>
	                  <c:if test = "${empty contract.sequentialOwner2}">
	                  	 <option value="${user.id}" ${user.id  == contract.sequentialOwner2 ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
	                  </c:if>
                  </c:forEach>
                  </select>
              </div>
          </div>
          </div>  
            
                
            
        </div>
      </div>
      
      
<div class="row newContrBill">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm" id="billingDiv" >
           <h2>Billing Details</h2>
           
           <div class="form-group" id="hoursPerWeekDiv" style="display: ${contract.contractType == 'Client SOW' || contract.contractType == 'Vendor SOW' ? 'block' : 'none'};">
              <label class="col-md-4">Hours Per Week</label>
	              <div class="col-md-8">
				  		<input type="text" class="form-control" id="hoursPerWeek" name="hoursPerWeek" value="${!empty contract && !empty contract.hoursPerWeek  ? contract.hoursPerWeek : contract.contractType == 'Vendor NSA' || contract.contractType == 'Vendor NDA' || contract.contractType == 'NDA' || contract.contractType == 'NSA' ? '' : '40' }" data-validation-optional="true" data-validation="validate_int validate_length length1-3" placeholder="Enter Hours" field-name="Hours Per Week">
	              </div>
            </div>
            
            <div class="form-group" id="overTimeHoursDiv" style="display: ${contract.contractType == 'Client SOW' || contract.contractType == 'Vendor SOW' ? 'block' : 'none'};">
              <label class="col-md-4">Overtime Hours</label>
              	<div class="col-md-8">
					  	<input type="text" class="form-control" id="overTimeHours" name="overTimeHours" value="${contract.overTimeHours}" data-validation-optional="true" data-validation="validate_int validate_length length1-3" placeholder="Enter Hours" field-name="Overtime Hours">
              	</div> 
            </div>
            
            <div class="form-group" id="totalHoursEstimatedDiv" style="display: ${contract.contractType == 'Client SOW' || contract.contractType == 'Vendor SOW' ? 'block' : 'none'};">
              <label class="col-md-4">Total Hours Estimated</label>
              	<div class="col-md-8">
					  	<input type="text" class="form-control" id="totalHoursEstimated" name="totalHoursEstimated" value="${contract.totalHours}" data-validation-optional="true" data-validation="validate_int" placeholder="Enter Total Estimated Hours" field-name="Total Estimated Hours">
              	</div>
            </div>
           
           <div class="form-group" id="overtimeRateDiv" style="display: ${contract.contractType == 'Client SOW' || contract.contractType == 'Vendor SOW' ? 'block' : 'none'};">
              <label class="col-md-4">Overtime Rate</label>
              	<div class="col-md-8">
					  	<input type="text" class="form-control" id="overtimeRate" name="overtimeRate" value="${contract.overtimeRate}" data-validation-optional="true" data-validation="validate_int validate_length length1-8" placeholder="Overtime Rate" field-name="Overtime Rate">
              	</div>
            </div>
           
            <div class="form-group">
                <label class="col-md-4">Billing Amount<span class="text-danger">&nbsp;*</span></label>
					<div class="col-lg-8">
			             <div class="input-group er ratetype">
			                <div class="input-group-btn tblminwidth-75" style="min-width: 65px !important;">
				                
				                <select class="selectpicker" id="billingRateType" name="billingRateType" field-name="Rate Per" ${contract.contractType == 'Vendor NSA' || contract.contractType == 'Vendor NDA' || contract.contractType == 'NDA' || contract.contractType == 'NSA'  ? 'data-validation-optional="true"' : 'data-validation="required"'}>
					                 <option value="">Currency</option>
					                 <option ${contract.billingRateType == 'INR' ? 'selected' : currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
					                 <option ${contract.billingRateType == 'AUD' ? 'selected' : currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
					                 <option ${contract.billingRateType == 'USD' ? 'selected' : currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
					                 <option ${contract.billingRateType == 'GBP' ? 'selected' : currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
					                 <option ${contract.billingRateType == 'SGD' ? 'selected' : currencyCode  == 'AUD' ? 'selected' : ''}>SGD</option>
					                 <option ${contract.billingRateType == 'EUR' ? 'selected' : currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
				                
				                </select>
			                </div>
							  	
							  	<input type="text" class="form-control" id="flatReward" name="flatReward" value="${contract.billingAmount}" placeholder="Billing Amount" ${contract.contractType == 'Vendor NSA' || contract.contractType == 'Vendor NDA' || contract.contractType == 'NDA' || contract.contractType == 'NSA'  ? 'data-validation-optional="true"' : 'data-validation="required validate_int validate_length length1-8"'} field-name="Billing Amount">
			                
			                <div class="input-group-btn tblminwidth-75 ratetype">
			                  <select class="selectpicker" id="billingRatePer" name="billingRatePer" field-name="Rate Per" ${contract.contractType == 'Vendor NSA' || contract.contractType == 'Vendor NDA' || contract.contractType == 'NDA' || contract.contractType == 'NSA'  ? 'data-validation-optional="true"' : 'data-validation="required"'}>
			                    <option value="">Rate Per</option>
			                    <option ${contract.billingRatePer  == 'Hourly' ? 'selected' : ''}>Hourly</option>
			                    <option ${contract.billingRatePer  == 'Weekly' ? 'selected' : ''}>Weekly</option>
			                    <option ${contract.billingRatePer  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
			                    <option ${contract.billingRatePer  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
			                    <option ${contract.billingRatePer  == 'Monthly' ? 'selected' : ''}>Monthly</option>
			                    <option ${contract.billingRatePer  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
			                    <option ${contract.billingRatePer  == 'Yearly' ? 'selected' : ''}>Yearly</option>
			                  </select>
			                </div>
			            </div>
		            </div>
					
            </div>
            <div class="form-group">
              <label class="col-md-4">Billing Cycle<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8 dropdown-select" id="errInvoice">
                  <select class="form-control" id="invoiceFrequency" name="invoiceFrequency" ${contract.contractType == 'Vendor NSA' || contract.contractType == 'Vendor NDA' || contract.contractType == 'NDA' || contract.contractType == 'NSA'  ? 'data-validation-optional="true"' : 'data-validation="required"'} field-name="Billing Cycle" onchange="removeBillCycleValidation();">
                    <option value="">Select Cycle</option>
                    <option ${contract.invoiceFrequency == 'Weekly' ? 'selected' : ''} value="Weekly">Weekly</option>
                    <option ${contract.invoiceFrequency == 'Bi-Weekly' ? 'selected' : ''} value="Bi-Weekly">Bi-Weekly</option>
                    <option ${contract.invoiceFrequency == 'Semi-Monthly' ? 'selected' : ''} value="Semi-Monthly">Semi-Monthly</option> 
                    <option ${contract.invoiceFrequency == 'Monthly' ? 'selected' : ''} value="Monthly">Monthly</option>
                  </select>
              </div>
            </div>
            
              <div class="form-group">
              <label class="col-md-4">Payment Frequency<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8 dropdown-select" id="errPayment">
                  <select class="form-control" id="paymentFrequency" name="paymentFrequency" ${contract.contractType == 'Vendor NSA' || contract.contractType == 'Vendor NDA' || contract.contractType == 'NDA' || contract.contractType == 'NSA'  ? 'data-validation-optional="true"' : 'data-validation="required"'} field-name="Payment Frequency" onchange="removePaymentvalidation();">
                    <option value="">Select Payment Frequency</option>
                    <option ${contract.paymentTerm == '10 days' ? 'selected' : ''} value="10 days">10 days</option>
                    <option ${contract.paymentTerm == '15 days' ? 'selected' : ''} value="15 days">15 days</option>
                    <option ${contract.paymentTerm == '30 days' ? 'selected' : ''} value="30 days">30 days</option>
                    <option ${contract.paymentTerm == '45 days' ? 'selected' : ''} value="45 days">45 days</option>
                    <option ${contract.paymentTerm == '60 days' ? 'selected' : ''} value="60 days">60 days</option>
                  </select>
              </div>
            </div>
            
            <div class="form-group">
              <!-- <label class="col-md-4">Billing Amount<span class="text-danger">&nbsp;*</span></label> -->

                <label class="col-md-4">Tax</label>
                   <div class="clearfix col-md-8" id="errTax">
                      <div class="col-md-5 paddingLeft">
                        <div class="btn-group btn_Block">
                          <button type="button" class="btn btn-sm btn-primary" id="taxPercentageBtn" value="percentage" onClick="getTaxRewardType('Percentage')">%</button>
                          <button type="button" class="btn btn-sm btn-default" id="taxflatBtn" value="flat" onClick="getTaxRewardType('Flat')">Flat</button>
                          <input type="hidden" name="taxRewardType" id="taxRewardType" value=""/>
                        </div>
                      </div>
                      <div class="col-md-7 paddingLeft" id="taxPercentageReward">
                        <div class="form-group positionRelative iconHolder percentage width100percnt" > 
                          <input type="text" class="form-control input-sm" field-name="Percentage" value="${contract.tax}" id="taxPercentageRewardValue" name="taxPercentageReward" onblur="getTaxPercentageValue()" placeholder="Percentage">
                          <i class="fa fa-percent"></i> 
                        </div>
                        </div>
                        <div class="col-md-7 paddingLeft" id="taxFlatReward"  style="display: none">
                        <div class="row flat">
                          <div class="form-group col-md-8 paddingRight positionRelative iconHolder" >
                            <input type="text" class="form-control input-sm" field-name="Flat" id="taxFlatRewardValue"  name="taxFlatReward" placeholder="Flat">
                            <i class="fa fa-money" id="taxFlatRewardIcon"></i> </div>
                          <div class="form-group dropdown-select col-md-4 paddingLeft"> 
                            <select class="form-control input-sm" id="taxFlatRateType" name="taxFlatRateType"  style="display: none">
                              <option  ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}><i class="fa fa-rupee" title="INR"></i>INR</option>
                              <option  ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}><i class="fa fa-dollar" title="USD"></i>USD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}><i class="fa fa-aud" title="AUD"></i>AUD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}><i class="fa fa-eur" title="Eur"></i>EUR</option>
                              <option  ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}><i class="fa fa-sgd" title="SGD"></i>SGD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}><i class="fa fa-gdp" title="GDP"></i>GBP</option>
                            </select>
                          </div>
                        </div>
                      </div>
                    </div>
            </div>
        </div>
         </div>
 	<div class="row reqAddWrap">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
  			<table width="100%" cellpadding="0" cellspacing="0" class="table table-striped table-responsive">
           <thead >
           <tr>
           <th style="vertical-align: middle;">Request Documents</th>
           <th colspan="4">
           <div class="pull-right"><button type="button" class="btn btn-sm btn-primary" title="Add Doc" onclick="addDoc()">
           <i class="fa fa-plus"></i></button>
            <input type="hidden" id="maxTaxRowNum" name="maxTaxRowNum" value="1" /></div>
           </th>
          </tr>
          </thead>
          <tbody id="addTaxTbl">
          
   				<tr><td><strong>Name</strong></td><td><strong>Attachment</strong></td><td><strong>Doc Type</strong></td><td><strong>Description</strong></td><td><strong>Action</strong></td></tr>
		
            <c:forEach var="doc" items="${docList}">
	                     <tr id="tr${doc.id}"><td><div class="tbldata-dotdot-me">${doc.fileName}</div></td><td><div class="tbldata-dotdot-big">${doc.description}</div></td><td><span title="Remove row" onclick="removeDocument(${doc.id})"><i class="fa fa-trash text-danger"></i></span></td></tr>
                </c:forEach>
          <tr id="divTaxRow0">
          </tr>
          </tbody>
          </table>
        </div>
        
      </div>   
        <!-- billing end -->
      </div>      
      
      <div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Finalize" id="save_template" onmouseover="validateContractTitle();" onclick="saveExistigContract();" class="btn btn-success ml10">
            <input type="button" value="Cancel" class="btn btn-default ml10" id="cancel_contract" onclick="cancelContract();">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
    </form:form>
  </section>
  
  <div class="comMask"></div>
<div class="modal validatePreexistingContact" id="validatePreexistingContact" style="display: none">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
			<h4 class="strong pull-left mb0 margin-top0">Generated Contracts</h4>
				<a href="javascript:void(0)" class="close" onClick="closeVpec();">&times;</a>
			</div>
			<div class="modal-body">
				 <div id="preExistContractMsg"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary pull-right" onClick="closeVpec();">Ok</button>
			</div>
		</div>
	</div>
</div>
  
<div id="divTaxTd1" style="display: none; float: left;">
	<div class="select-field tblminwidth-75">
		<div class="tblminwidth-50">
			<input type="text" class="form-control" value=""
				placeholder="Document Name" name='document[REPLACEROWID].documentName'
				id='REPLACEROWIDdocumentName' field-name="Document Name" data-validation="required"> 
		</div>
	</div>
</div>

<div id="divTaxTd2" style="display: none; float: left;">
<!-- 	<div class="tblminwidth-75">
	<input type='file' name='REPLACEROWIDattachment' id='REPLACEROWIDattachment' field-name='Attachment' 
	class='timesheetAttachmentValidation'></div> -->
	
							 <div class="col-md-12">                                                                                                            
					  			<div class="input-group positionRelative ratetype" id="uploadFileGroupREPLACEROWID" title="Upload a file">     
					  				<label class="input-group-btn"> <span class="btn btn-primary padSm"><i class="fa fa-folder-open-o"></i>&nbsp;Browse        
					               <input id="uploadBtnREPLACEROWID" style="display: none;" multiple="" class="upload" type="file" onclick="check(REPLACEROWID);fileStore(REPLACEROWID);"  >                      
					               </span> </label>                                                                                                                
					  				<input class="form-control padSm"  id="uploadFileREPLACEROWID" name="document[REPLACEROWID].docFileName"  field-name="Attachment" data-validation="required" placeholder="Upload File" readonly type="text" >  
					  			</div>                                                                                                                       
					         </div>			                                                                                                                 
				
	</div>


<div id="divTaxTd3" style="display: none; float: left;">
   	 <select class="form-control input-sm sel"  id="REPLACEROWIDcandiDoc" name="document[REPLACEROWID].candiDoc" >
		<option value="">--Select Type--</option>
		<c:forEach var="doctype" items="${documentsList}">
		<option value="${doctype.name}">${doctype.name}</option>
		</c:forEach>
	</select>
</div>

<div id="divTaxTd4" style="display: none; float: left;">
	<div class="tblminwidth-75">
			<textarea class="form-control miDHei" rows="3" placeholder="Description" id="REPLACEROWIDdescription"
			 name="document[REPLACEROWID].description"></textarea>
	</div>
</div>

<%-- <div id="divTaxTd5" style="display: none; float: left;">
   	 <select class="form-control input-sm"  id="REPLACEROWIDcandiDoc" name="REPLACEROWIDcandiDoc" >
		<option value="">--Select Type--</option>
		<c:forEach var="doctype" items="${documentsList}">
		<option value="${doctype.name}">${doctype.name}</option>
		</c:forEach>
	</select>
</div> --%>

<div id="divTaxTd5" style="display: none; float: right;">
	<a class="btn btn-sm btn-danger pull-right" title="Remove row"
		onclick="removeTaxRow(REPLACEROWID)"><i class="fa fa-remove"></i></a>
</div>

<script>
function closeVpec(){
	$(".validatePreexistingContact").fadeOut();
	$(".comMask").fadeOut();
}
</script>

<script>

function check(id){
document.getElementById("uploadBtn"+id).onchange = function () {
    document.getElementById("uploadFile"+id).value = this.value.split( '\\' ).pop();;
    };
    
    /* upload file script for size validation 2MB*/
    var selectedFile = document.getElementById("uploadBtn"+id);
    selectedFile.addEventListener("change", function() {
      var docSize = this.files[0].size;
      $("#uploadFileGroup"+id).find('p.jquery_form_error_message').remove();
      if(docSize > 2097152){
    	  document.getElementById("uploadFile"+id).value = '';
    	  $("#uploadFileGroup"+id).append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
      }
    });
}

function fileStore(id){
	 $("#uploadBtn"+id).change(function() {
		fileUpload("uploadBtn"+id, "RequestDocuments", "newcontract_form","reqDocs");
	});
}
</script>

<script>
function refreshClientList(){
		 var sourceleadUrl ='${sourceleadUrl}';
		 var selectedOrgId = '${contract.orgRelations.id}';
		 $.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/refreshClientList",       
	       success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>Select Client</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(opt.id == selectedOrgId){
		        			output = output + "<option value='"+opt.id+"' selected>"+opt.orgName+"</option>";
		        		}else{
		        			output = output + "<option value='"+opt.id+"' >"+opt.orgName+"</option>";
		        		}
				}
	        	$("#clientOrgRelationId").empty();
		        $("#clientOrgRelationId").append(output);
		        $('#clientOrgRelationId').trigger("chosen:updated");
	        }
		 });
}

		 

</script>

<script>
	function addDoc(){  
				var checkMaxTsRowNum = $("#maxTaxRowNum").val();
				var tbl = document.getElementById("addTaxTbl");
				var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
				$("input[name = 'maxTaxRowNum']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum);
				row.setAttribute("id", "divTaxRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divTaxTdStr = document.getElementById("divTaxTd1").innerHTML;
				divTaxTdStr = divTaxTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell0.innerHTML = divTaxTdStr;
				
				var cell1 = row.insertCell(1);
				var divTaxTdStr1 = document.getElementById("divTaxTd2").innerHTML;
				divTaxTdStr1 = divTaxTdStr1.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTaxTdStr1;
				
				var cell2 = row.insertCell(2);
				var divTaxTdStr2 = document.getElementById("divTaxTd3").innerHTML;
				divTaxTdStr2 = divTaxTdStr2.replace(/REPLACEROWID/g, (maxRowNum));
				cell2.innerHTML = divTaxTdStr2;
				
				var cell3 = row.insertCell(3);
				var divTaxTdStr3 = document.getElementById("divTaxTd4").innerHTML;
				divTaxTdStr3 = divTaxTdStr3.replace(/REPLACEROWID/g, (maxRowNum));
				cell3.innerHTML = divTaxTdStr3;
				
				var cell4 = row.insertCell(4);
				var divTaxTdStr4 = document.getElementById("divTaxTd5").innerHTML;
				divTaxTdStr4 = divTaxTdStr4.replace(/REPLACEROWID/g, (maxRowNum));
				cell4.innerHTML = divTaxTdStr4;
				
				/* var cell5 = row.insertCell(5);
				var divTaxTdStr5 = document.getElementById("divTaxTd6").innerHTML;
				divTaxTdStr5 = divTaxTdStr5.replace(/REPLACEROWID/g, (maxRowNum));
				cell5.innerHTML = divTaxTdStr5; */
	}			
</script>
<script type="text/javascript">
	function removeTaxRow(rowId) {
		
		     
		document.getElementById('divTaxRow' + rowId).style.display = 'none';
		var r = document.getElementById('addTaxTbl').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
		
	}
</script>

<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script>
    <script>
    $("#uasrch").selectpicker('refresh');
    </script>
<script>
function removeValid(){
$('#startDateDiv').find('p.jquery_form_error_message').remove();
}
</script>

<script>
	$(function() {
		  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatExistedContractSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatExistedContractSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatExistedContractSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatExistedContractSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatExistedContractSave = 'yy-dd-mm'; 
		  }
		$('#startDate').datepicker({
            dateFormat: finalDateFormatExistedContractSave,
            changeMonth: true,
              changeYear: true,
            onSelect: function (selected) {
            	 var dt = $(this).datepicker('getDate', '+1d');
  	            dt.setDate(dt.getDate() + 1);
  	            $("#endDate").datepicker("option", "minDate", dt);
                  removeValid();  
            }
        });
        
        $('#endDate').datepicker({
            dateFormat: finalDateFormatExistedContractSave,
            changeMonth: true,
              changeYear: true,
              onSelect: function (selected) {
            	  var dt = $(this).datepicker('getDate', '-1d');
                  dt.setDate(dt.getDate() - 1);
                  $("#startDate").datepicker("option", "maxDate", dt);
              }
        });
        $('#joinDate').datepicker({
            dateFormat: finalDateFormatExistedContractSave,
            changeMonth: true,
              changeYear: true,
            onSelect: function (selected) {
                  var dt = toDate("#joinDate");
                  dt.setDate(dt.getDate() + 1);
                  removeValid();
              }
        });
        document.getElementById("uploadBtn").onchange = function () {
    	    document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();;
    	    };
    	    
    	    /* upload file script for size validation 2MB*/
    	    var selectedFile = document.getElementById("uploadBtn");
    	    selectedFile.addEventListener("change", function() {
    	      var docSize = this.files[0].size;
    	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
    	      if(docSize > 2097152){
    	    	  document.getElementById("uploadFile").value = '';
    	    	  $("#uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
    	      }
    	    });
});
</script>

<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>


<!-- <script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("startDate").value;
		 if(StartDate){
			 $('#startDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var EndDate = document.getElementById("endDate").value;
		 if(EndDate){
			 $('#endDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
}
</script> -->

<script>
function getVendorContacts(spocId){
	$('#errorVendor').find('p.jquery_form_error_message').remove();
	 var orgRelationId = $("#orgRelationId").val();
	 if("" != orgRelationId){
		 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/getVendorContactsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&listName=Vendor",       
	       success :function(data) {
	    	var json = JSON.parse(data);
	       	var output = "";
	       	output = output + "<option value=''>Select Vendor Contact</option>";
	       	for (var i=0; i<json.length; i++)
				{
		        var opt = json[i];
		            // need to use in edit
	       			//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
	       			if(spocId != null && spocId != '' && spocId != 'undefined' && spocId == opt.id){
	       				output = output + "<option value='" +opt.tempOwnerId+ "' selected>"+opt.spocName+" ("+opt.ownerUserName+")</option>";
	       			}else{
	       				output = output + "<option value='" +opt.tempOwnerId+ "'>"+opt.spocName+" ("+opt.ownerUserName+")</option>";
	       			}
		        
				}
	       	    $("#vendorContact").empty();
		        $("#vendorContact").append(output);
		        $('#vendorContact').trigger("chosen:updated");
	      },
	  });  
	 }else{
			var output = "";
			output = output + "<option value=''>Select Vendor Contact</option>";
			$("#vendorContact").empty();
	        $("#vendorContact").append(output);
	        $('#vendorContact').trigger("chosen:updated");
		}
}

function getClientContacts(){
	$('#errorClient').find('p.jquery_form_error_message').remove();
	var orgRelationId = $("#clientOrgRelationId").val();
	if("" != orgRelationId){
		var sourceleadUrl ='${sourceleadUrl}';
		$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getVendorContactsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&listName=Client",
		success :function(data) {
		var json = JSON.parse(data);
		var output = "";
		output = output + "<option value=''>Select Client Contact</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		// need to use in edit
		//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
		output = output + "<option value='" +opt.spocUserId+ "'>"+opt.ownerName+" ("+opt.ownerUserName+")</option>";
		}
		$("#clientContact").empty();
		$("#clientContact").append(output);
		$('#clientContact').trigger("chosen:updated");
		},
		});
	}else{
		var output = "";
		output = output + "<option value=''>Select Client Contact</option>";
		$("#clientContact").empty();
		$("#clientContact").append(output);
		$('#clientContact').trigger("chosen:updated");
	}
	}
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 



<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>

<script>
function clearSearchKey() {
	   $('#tax').val('');
	   $('#taxDiv').parent().find('p.jquery_form_error_message').remove();

}
</script>  
<script type="text/javascript">
function selectTaxType(){
	 var taxType = document.getElementById("taxType").value;
	 
	 var tax = document.getElementById("tax").value;
	 $('#taxDiv').parent().find('p.jquery_form_error_message').remove(); 
	 if(taxType =='Percentage'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(tax.match(floatNum)){
			  $("#draft_template").removeAttr("disabled");  
			  $("#save_template").removeAttr("disabled"); 
			  return true;
		  }		  
		 else{
				$("input#tax").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid Percentage</span>");
				 $("#draft_template").attr("disabled", "disabled");
				 $("#save_template").attr("disabled", "disabled");
				return false; 
		 	 }
	  }
	 
		 }
	  
</script>


<script>
function getContractTemplates(){
	var contractType = $("#contractType").val();
	
	if(contractType == ""){
		$("#clientOrgRelationId option:selected").removeAttr("selected");
		$('#clientOrgRelationId').trigger("chosen:updated");
		document.getElementById("clientDiv").style.display="none";
		
		$("#clientContact option:selected").removeAttr("selected");
		$('#clientContact').trigger("chosen:updated");
		document.getElementById("clientContactDiv").style.display="none";
		
		$("#orgRelationId option:selected").removeAttr("selected");
		$('#orgRelationId').trigger("chosen:updated");
		document.getElementById("vendorDiv").style.display="none";
		
		$("#vendorContact option:selected").removeAttr("selected");
		$('#vendorContact').trigger("chosen:updated");
		document.getElementById("billingDiv").style.display="block";
		document.getElementById("vendorContactDiv").style.display="none";
		document.getElementById("offeredSubmissionDiv").style.display="none";
		document.getElementById("uploadFile").value = '';
	}
 	if(contractType == "Client MSA"){
 		document.getElementById("billingDiv").style.display="none";
		document.getElementById("clientDiv").style.display="block";
		document.getElementById("clientContactDiv").style.display="block";
		document.getElementById("vendorDiv").style.display="none";
		document.getElementById("vendorContactDiv").style.display="none";
		document.getElementById("joinDateDiv").style.display="none";
		document.getElementById("workLocationDiv").style.display="none";
		document.getElementById("dateDiv").style.display="block";
		document.getElementById("descriptionDiv").style.display="block";
		document.getElementById("offeredSubmissionDiv").style.display="none";
		document.getElementById("uploadFile").value = '';
	}
	if(contractType == "Vendor MSA"){
		document.getElementById("billingDiv").style.display="none";
		document.getElementById("clientDiv").style.display="none";
		document.getElementById("clientContactDiv").style.display="none";
		document.getElementById("vendorDiv").style.display="block";
		document.getElementById("vendorContactDiv").style.display="block";
		document.getElementById("joinDateDiv").style.display="none";
		document.getElementById("workLocationDiv").style.display="none";
		document.getElementById("dateDiv").style.display="block";
		document.getElementById("descriptionDiv").style.display="block";
		document.getElementById("offeredSubmissionDiv").style.display="none";
		document.getElementById("uploadFile").value = '';
	}
	if(contractType == "Client SOW"){
		document.getElementById("billingDiv").style.display="block";
		document.getElementById("clientDiv").style.display="block";
		document.getElementById("clientContactDiv").style.display="block";
		document.getElementById("vendorDiv").style.display="none";
		document.getElementById("vendorContactDiv").style.display="none";
		document.getElementById("joinDateDiv").style.display="none";
		document.getElementById("workLocationDiv").style.display="none";
		document.getElementById("dateDiv").style.display="block";
		document.getElementById("descriptionDiv").style.display="block";
		document.getElementById("offeredSubmissionDiv").style.display="block";
		document.getElementById("uploadFile").value = '';
	}
	if(contractType == "Vendor SOW"){
		document.getElementById("clientDiv").style.display="none";
		document.getElementById("clientContactDiv").style.display="none";
		document.getElementById("billingDiv").style.display="block";
		document.getElementById("vendorDiv").style.display="block";
		document.getElementById("vendorContactDiv").style.display="block";
		document.getElementById("joinDateDiv").style.display="none";
		document.getElementById("workLocationDiv").style.display="none";
		document.getElementById("dateDiv").style.display="block";
		document.getElementById("descriptionDiv").style.display="block";
		document.getElementById("offeredSubmissionDiv").style.display="block";
		document.getElementById("uploadFile").value = '';
	}
	if(contractType == "NDA" || contractType == "NSA"){
		document.getElementById("clientDiv").style.display="none";
		document.getElementById("clientContactDiv").style.display="none";
		document.getElementById("vendorDiv").style.display="block";
		document.getElementById("vendorContactDiv").style.display="block";
		document.getElementById("billingDiv").style.display="none";
		document.getElementById("joinDateDiv").style.display="none";
		document.getElementById("workLocationDiv").style.display="none";
		document.getElementById("dateDiv").style.display="block";
		document.getElementById("descriptionDiv").style.display="block";
		document.getElementById("offeredSubmissionDiv").style.display="none";
		document.getElementById("uploadFile").value = '';
	}
	if(contractType == "Full time offer"){
		document.getElementById("clientDiv").style.display="none";
		document.getElementById("clientContactDiv").style.display="none";
		document.getElementById("vendorDiv").style.display="none";
		document.getElementById("vendorContactDiv").style.display="none"; 
		document.getElementById("billingDiv").style.display="none";
		document.getElementById("dateDiv").style.display="none";
		document.getElementById("descriptionDiv").style.display="none";
		document.getElementById("joinDateDiv").style.display="block";
		document.getElementById("workLocationDiv").style.display="block";
		document.getElementById("offeredSubmissionDiv").style.display="block";
		document.getElementById("uploadFile").value = '';
	}	 
 	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
     type : "GET",
      url: sourceleadUrl+"/getContractsTemplatesByContractType?${_csrf.parameterName}=${_csrf.token}&contractType="+contractType,       
      success :function(data) {
    	var json = JSON.parse(data);
      	var output = "";
      	output = output + "<option>Select Vendor Contact</option>";
      	for (var i=0; i<json.length; i++)
			{
	        var opt = json[i];
	            // need to use in edit
      			//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
      			output = output + "<option value='" +opt.id+ "'>"+opt.templateName+"</option>";
			}
      	    $("#template").empty();
	        $("#template").append(output);
	        $('#template').trigger("chosen:updated"); 
     },
     complete: function (data) {
    	 flush(); 
    }
 }); 
	
}

</script>

<script>
function contractTypeLabels(){
	var contractType = $("#contractType").val();
	/* 	if(contractType == ""){
			$('#orgRelationId').removeAttr('data-validation');
			$('#orgRelationId').attr('data-validation-optional',"true");
			$('#vendorContact').removeAttr('data-validation');
			$('#vendorContact').attr('data-validation-optional',"true");
			
			
			
			hideCommonSOWFields();
		} */
		if(contractType == "Client MSA"){
			$('#orgRelationId').removeAttr('data-validation');
			$('#orgRelationId').attr('data-validation-optional',"true");
			$('#vendorContact').removeAttr('data-validation');
			$('#vendorContact').attr('data-validation-optional',"true");
			
			$('#clientOrgRelationId').removeAttr('data-validation-optional');
			$('#clientOrgRelationId').attr("data-validation","required");
			
			$('#clientContact').removeAttr('data-validation-optional');
			$('#clientContact').attr("data-validation","required");
			
			/* $('#flatReward').removeAttr('data-validation-optional');
			$('#flatReward').attr('data-validation',"required");
			
			$('#billingRateType').removeAttr('data-validation-optional');
			$('#billingRateType').attr('data-validation',"required");
			
			$('#billingRatePer').removeAttr('data-validation-optional');
			$('#billingRatePer').attr('data-validation',"required");
			
			$('#invoiceFrequency').removeAttr('data-validation-optional');
			$('#invoiceFrequency').attr('data-validation',"required");
			
			$('#paymentFrequency').removeAttr('data-validation-optional');
			$('#paymentFrequency').attr('data-validation',"required"); */
			
			//added by Pavan Jampana for change request #5851(taiga Id)
			$("#billingDiv").hide();
			$('#billingRateType').removeAttr('data-validation');
			$('#billingRateType').attr('data-validation-optional',"true");
			
			$('#flatReward').removeAttr('data-validation');
			$('#flatReward').attr('data-validation-optional',"true");
			
			$('#billingRatePer').removeAttr('data-validation');
			$('#billingRatePer').attr('data-validation-optional',"true");
			
			$('#invoiceFrequency').removeAttr('data-validation');
			$('#invoiceFrequency').attr('data-validation-optional',"true");
			
			$('#paymentFrequency').removeAttr('data-validation');
			$('#paymentFrequency').attr('data-validation-optional',"true");
			
			$('#candiFlatReward').removeAttr('data-validation');
			$('#candiFlatReward').attr('data-validation-optional',"true");
			
			$('#candiRateType').removeAttr('data-validation');
			$('#candiRateType').attr('data-validation-optional',"true");
			
			$('#candiRatePer').removeAttr('data-validation');
			$('#candiRatePer').attr('data-validation-optional',"true");
			
			$("#orgRelationId").val(''); 
			$('#orgRelationId').trigger("chosen:updated");
			
			$("#clientOrgRelationId").val(''); 
			$('#clientOrgRelationId').trigger("chosen:updated");

			$("#jurisdiction").val(''); 
			$("#mainDescription").val(''); 
			$("#startDate").val('');
			$("#endDate").val('');
			
			$("#approver").val('');
			$('#approver').trigger("chosen:updated");
			
			$("#vendorContact").val(''); 
			$('#vendorContact').trigger("chosen:updated");
			
			$("#clientContact").val(''); 
			$('#clientContact').trigger("chosen:updated");
			
			$("#clientOrgRelationId").val(''); 
			$('#clientOrgRelationId').trigger("chosen:updated");
			
			$("#orgRelationId").val(''); 
			$('#orgRelationId').trigger("chosen:updated");
			
			$("#addTaxTbl").find("tr:not(:first)").empty();
			
			hideCommonSOWFields();
		}
		if(contractType == "Vendor MSA"){
			$('#clientOrgRelationId').removeAttr('data-validation');
			$('#clientOrgRelationId').attr('data-validation-optional',"true");
			$('#clientContact').removeAttr('data-validation');
			$('#clientContact').attr('data-validation-optional',"true");
			
			$('#orgRelationId').removeAttr('data-validation-optional');
			$('#orgRelationId').attr("data-validation","required");
			
			$('#vendorContact').removeAttr('data-validation-optional');
			$('#vendorContact').attr("data-validation","required");
			
			/* $('#flatReward').removeAttr('data-validation-optional');
			$('#flatReward').attr('data-validation',"required");
			
			$('#billingRateType').removeAttr('data-validation-optional');
			$('#billingRateType').attr('data-validation',"required");
			
			$('#billingRatePer').removeAttr('data-validation-optional');
			$('#billingRatePer').attr('data-validation',"required");
			
			$('#invoiceFrequency').removeAttr('data-validation-optional');
			$('#invoiceFrequency').attr('data-validation',"required");
			
			$('#paymentFrequency').removeAttr('data-validation-optional');
			$('#paymentFrequency').attr('data-validation',"required"); */
			
			//added by Pavan Jampana for change request #5851(taiga Id)
			$("#billingDiv").hide();
			$('#billingRateType').removeAttr('data-validation');
			$('#billingRateType').attr('data-validation-optional',"true");
			
			$('#flatReward').removeAttr('data-validation');
			$('#flatReward').attr('data-validation-optional',"true");
			
			$('#billingRatePer').removeAttr('data-validation');
			$('#billingRatePer').attr('data-validation-optional',"true");
			
			$('#invoiceFrequency').removeAttr('data-validation');
			$('#invoiceFrequency').attr('data-validation-optional',"true");
			
			$('#paymentFrequency').removeAttr('data-validation');
			$('#paymentFrequency').attr('data-validation-optional',"true");
			
			$('#candiFlatReward').removeAttr('data-validation');
			$('#candiFlatReward').attr('data-validation-optional',"true");
			
			$('#candiRateType').removeAttr('data-validation');
			$('#candiRateType').attr('data-validation-optional',"true");
			
			$('#candiRatePer').removeAttr('data-validation');
			$('#candiRatePer').attr('data-validation-optional',"true");
			
			$("#orgRelationId").val(''); 
			$('#orgRelationId').trigger("chosen:updated");
			
			$("#vendorContact").val(''); 
			$('#vendorContact').trigger("chosen:updated");
			
			$("#jurisdiction").val(''); 
			$("#mainDescription").val(''); 
			$("#startDate").val('');
			$("#endDate").val(''); 
			
			$("#approver").val('');
			$('#approver').trigger("chosen:updated");
			
			$("#orgRelationId").val(''); 
			$('#orgRelationId').trigger("chosen:updated");
			
			$("#vendorContact").val(''); 
			$('#vendorContact').trigger("chosen:updated");
			
			$("#addTaxTbl").find("tr:not(:first)").empty();
			
			hideCommonSOWFields();
		}
		if(contractType == "Client SOW"){
			$('#orgRelationId').removeAttr('data-validation');
			$('#orgRelationId').attr('data-validation-optional',"true");
			$('#vendorContact').removeAttr('data-validation');
			$('#vendorContact').attr('data-validation-optional',"true");
			
			$('#clientOrgRelationId').removeAttr('data-validation-optional');
			$('#clientOrgRelationId').attr('data-validation',"required");
			$('#clientContact').removeAttr('data-validation-optional');
			$('#clientContact').attr('data-validation',"required");
			
			$('#flatReward').removeAttr('data-validation-optional');
			$('#flatReward').attr('data-validation',"required");
			
			$('#billingRateType').removeAttr('data-validation-optional');
			$('#billingRateType').attr('data-validation',"required");
			
			$('#billingRatePer').removeAttr('data-validation-optional');
			$('#billingRatePer').attr('data-validation',"required");
			
			$('#invoiceFrequency').removeAttr('data-validation-optional');
			$('#invoiceFrequency').attr('data-validation',"required");
			
			$('#paymentFrequency').removeAttr('data-validation-optional');
			$('#paymentFrequency').attr('data-validation',"required");
			
			$('#candiFlatReward').removeAttr('data-validation');
			$('#candiFlatReward').attr('data-validation-optional',"true");
			
			$('#candiRateType').removeAttr('data-validation');
			$('#candiRateType').attr('data-validation-optional',"true");
			
			$('#candiRatePer').removeAttr('data-validation');
			$('#candiRatePer').attr('data-validation-optional',"true");
			
			$("#jurisdiction").val(''); 
			$("#mainDescription").val(''); 
			$("#startDate").val('');
			$("#endDate").val('');
			
			$("#hoursPerWeek").val('');
			$("#paymentFrequency").val(''); 
			$("#taxPercentageRewardValue").val('');
			$("#taxFlatRewardValue").val('');
			
			$("#clientContact").val('');
			$('#clientContact').trigger("chosen:updated");
			
			$("#clientOrgRelationId").val('');
			$('#clientOrgRelationId').trigger("chosen:updated");
			
			$("#approver").val('');
			$('#approver').trigger("chosen:updated");
			$("#invoiceFrequency").val('');
			$('#invoiceFrequency').trigger("chosen:updated");
			
			$("#addTaxTbl").find("tr:not(:first)").empty();
			
			hideCommonSOWFields();
			showCommonSOWFields();
// 			getRequirements("Client SOW");
		}
		if(contractType == "Vendor SOW"){
			$('#clientOrgRelationId').removeAttr('data-validation');
			$('#clientOrgRelationId').attr('data-validation-optional',"true");
			$('#clientContact').removeAttr('data-validation');
			$('#clientContact').attr('data-validation-optional',"true");
			
			$('#orgRelationId').removeAttr('data-validation-optional');
			$('#orgRelationId').attr('data-validation',"required");
			$('#vendorContact').removeAttr('data-validation-optional');
			$('#vendorContact').attr('data-validation',"required");
			
			$('#flatReward').removeAttr('data-validation-optional');
			$('#flatReward').attr('data-validation',"required");
			
			$('#billingRateType').removeAttr('data-validation-optional');
			$('#billingRateType').attr('data-validation',"required");
			
			$('#billingRatePer').removeAttr('data-validation-optional');
			$('#billingRatePer').attr('data-validation',"required");
			
			$('#invoiceFrequency').removeAttr('data-validation-optional');
			$('#invoiceFrequency').attr('data-validation',"required");
			
			$('#paymentFrequency').removeAttr('data-validation-optional');
			$('#paymentFrequency').attr('data-validation',"required");
			
			$('#candiFlatReward').removeAttr('data-validation');
			$('#candiFlatReward').attr('data-validation-optional',"true");
			
			$('#candiRateType').removeAttr('data-validation');
			$('#candiRateType').attr('data-validation-optional',"true");
			
			$('#candiRatePer').removeAttr('data-validation');
			$('#candiRatePer').attr('data-validation-optional',"true");
			
			$("#jurisdiction").val(''); 
			$("#mainDescription").val(''); 
			$("#startDate").val('');
			$("#endDate").val('');
			
			$("#hoursPerWeek").val('');
			$("#paymentFrequency").val(''); 
			$("#taxPercentageRewardValue").val('');
			$("#taxFlatRewardValue").val('');
			
			$("#approver").val('');
			$('#approver').trigger("chosen:updated");   
			
			$("#vendorContact").val('');
			$('#vendorContact').trigger("chosen:updated");
			
			$("#orgRelationId").val('');
			$('#orgRelationId').trigger("chosen:updated");
			
			$("#invoiceFrequency").val('');
			$('#invoiceFrequency').trigger("chosen:updated");
			
			$("#addTaxTbl").find("tr:not(:first)").empty();
			
			hideCommonSOWFields();
			showCommonSOWFields();
// 			getRequirements("Vendor SOW");
		}
		if(contractType == "NDA" || contractType == "NSA"){

			//$('#percentageRewardValue').removeAttr('data-validation');
			//$('#flatRewardValue').removeAttr('data-validation');
			$('#invoiceFrequency').removeAttr('data-validation');
			$('#paymentFrequency').removeAttr('data-validation');
			$('#taxPercentageRewardValue').removeAttr('data-validation');
			$('#taxFlatRewardValue').removeAttr('data-validation');
			
			
			//$('#percentageRewardValue').attr('data-validation-optional',"true");
			//$('#flatRewardValue').attr('data-validation-optional',"true");
			$('#invoiceFrequency').attr('data-validation-optional',"true");
			$('#paymentFrequency').attr('data-validation-optional',"true");
			$('#taxPercentageRewardValue').attr('data-validation-optional',"true");
			$('#taxFlatRewardValue').attr('data-validation-optional',"true");
			
			$('#flatReward').removeAttr('data-validation');
			$('#flatReward').attr('data-validation-optional',"true");
			
			$('#billingRateType').removeAttr('data-validation');
			$('#billingRateType').attr('data-validation-optional',"true");
			
			$('#billingRatePer').removeAttr('data-validation');
			$('#billingRatePer').attr('data-validation-optional',"true");
			
			$('#clientOrgRelationId').removeAttr('data-validation');
			$('#clientOrgRelationId').attr('data-validation-optional',"true");
			$('#clientContact').removeAttr('data-validation');
			$('#clientContact').attr('data-validation-optional',"true");
			
			/* $('#orgRelationId').removeAttr('data-validation');
			$('#orgRelationId').attr('data-validation-optional',"true"); */
			/* $('#vendorContact').removeAttr('data-validation');
			$('#vendorContact').attr('data-validation-optional',"true"); */
			
			$('#orgRelationId').removeAttr('data-validation-optional');
			$('#orgRelationId').attr('data-validation',"required");
			$('#vendorContact').removeAttr('data-validation-optional');
			$('#vendorContact').attr('data-validation',"required");
			
			//prasanna
			/* $('#requirementsList').removeAttr('data-validation');
			$('#requirementsList').attr('data-validation-optional',"true");
			$('#requirementDiv').find('p.jquery_form_error_message').remove();
			document.getElementById("requirementDiv").style.display="none"; */
			
			$('#submissionOffersList').removeAttr('data-validation');
			$('#submissionOffersList').attr('data-validation-optional',"true");
			$('#offeredSubmissionDiv').find('p.jquery_form_error_message').remove();
			document.getElementById("offeredSubmissionDiv").style.display="none";
			
			$('#hoursPerWeek').val("");
			$('#hoursPerWeekDiv').find('p.jquery_form_error_message').remove();
			document.getElementById("hoursPerWeekDiv").style.display="none";
			
			$('#overTimeHours').val("");
			$('#overTimeHoursDiv').find('p.jquery_form_error_message').remove();
			document.getElementById("overTimeHoursDiv").style.display="none";
			
			$('#totalHoursEstimated').val("");
			$('#totalHoursEstimatedDiv').find('p.jquery_form_error_message').remove();
			document.getElementById("totalHoursEstimatedDiv").style.display="none";
			
			$('#overtimeRate').val("");
			$('#overtimeRateDiv').find('p.jquery_form_error_message').remove();
			
			$('#candiFlatReward').removeAttr('data-validation');
			$('#candiFlatReward').attr('data-validation-optional',"true");
			
			$('#candiRateType').removeAttr('data-validation');
			$('#candiRateType').attr('data-validation-optional',"true");
			
			$('#candiRatePer').removeAttr('data-validation');
			$('#candiRatePer').attr('data-validation-optional',"true");
			$("#vendorContact").val(''); 
			$('#vendorContact').trigger("chosen:updated");
			
			$("#clientContact").val(''); 
			$('#clientContact').trigger("chosen:updated");
			
			$("#orgRelationId").val(''); 
			$('#orgRelationId').trigger("chosen:updated");
			
			$("#clientOrgRelationId").val(''); 
			$('#clientOrgRelationId').trigger("chosen:updated");
			
			$("#jurisdiction").val(''); 
			$("#mainDescription").val(''); 
			$("#startDate").val('');
			$("#endDate").val('');
			
			$("#approver").val('');
			$('#approver').trigger("chosen:updated");
			
			$("#invoiceFrequency").val('');
			$('#invoiceFrequency').trigger("chosen:updated");
			
			$("#addTaxTbl").find("tr:not(:first)").empty();
		}
		
	if(contractType == "Full time offer"){
		
		$('#candiFlatReward').removeAttr('data-validation-optional');
		$('#candiFlatReward').attr('data-validation',"required validate_int validate_length length1-8");
		
		$('#candiRateType').removeAttr('data-validation-optional');
		$('#candiRateType').attr('data-validation',"required");
		
		$('#candiRatePer').removeAttr('data-validation-optional');
		$('#candiRatePer').attr('data-validation',"required");
		
		$('#clientOrgRelationId').removeAttr('data-validation');
		$('#clientOrgRelationId').attr('data-validation-optional',"true");
		$('#clientContact').removeAttr('data-validation');
		$('#clientContact').attr('data-validation-optional',"true");
		
		$('#endDate').removeAttr('data-validation');
		$('#endDate').attr('data-validation-optional',"true");
		$('#startDate').removeAttr('data-validation');
		$('#startDate').attr('data-validation-optional',"true");
		
		$('#orgRelationId').removeAttr('data-validation');
		$('#orgRelationId').attr('data-validation-optional',"true");
		$('#vendorContact').removeAttr('data-validation');
		$('#vendorContact').attr('data-validation-optional',"true");
		
		$('#invoiceFrequency').removeAttr('data-validation');
		$('#paymentFrequency').removeAttr('data-validation');
		$('#taxPercentageRewardValue').removeAttr('data-validation');
		$('#taxFlatRewardValue').removeAttr('data-validation');
		
		
		$('#invoiceFrequency').attr('data-validation-optional',"true");
		$('#paymentFrequency').attr('data-validation-optional',"true");
		$('#taxPercentageRewardValue').attr('data-validation-optional',"true");
		$('#taxFlatRewardValue').attr('data-validation-optional',"true");
		
		$('#flatReward').removeAttr('data-validation');
		$('#flatReward').attr('data-validation-optional',"true");
		
		$('#billingRateType').removeAttr('data-validation');
		$('#billingRateType').attr('data-validation-optional',"true");
		
		$('#billingRatePer').removeAttr('data-validation');
		$('#billingRatePer').attr('data-validation-optional',"true");
		
	
		$('#hoursPerWeek').val("");
		$('#hoursPerWeekDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("hoursPerWeekDiv").style.display="none";
		
		$('#overTimeHours').val("");
		$('#overTimeHoursDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("overTimeHoursDiv").style.display="none";
		
		$('#totalHoursEstimated').val("");
		$('#totalHoursEstimatedDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("totalHoursEstimatedDiv").style.display="none";
		
		$('#overtimeRate').val("");
		$('#overtimeRateDiv').find('p.jquery_form_error_message').remove();
		
		$("#vendorContact").val(''); 
		$('#vendorContact').trigger("chosen:updated");
		
		$("#clientContact").val(''); 
		$('#clientContact').trigger("chosen:updated");
		
		$("#orgRelationId").val(''); 
		$('#orgRelationId').trigger("chosen:updated");
		
		$("#clientOrgRelationId").val(''); 
		$('#clientOrgRelationId').trigger("chosen:updated");
		
		$("#jurisdiction").val(''); 
		$("#mainDescription").val(''); 
		
		$("#approver").val('');
		$('#approver').trigger("chosen:updated");
		
		$("#addTaxTbl").find("tr:not(:first)").empty();
// 		getRequirements("Vendor SOW");
	}
	getContractTemplates();
	
	$("#flatReward").val("");
	$("#billingRateType").val('${currencyCode}').trigger('change');
	$("#billingRatePer").val("").trigger('change');
	}
</script>
<script>
function getParallelApprovers(){
	$("#parallel i").addClass("apprverReq-active");
	$("#sequential i").removeClass("apprverReq-active");
	$("#parallelAppDiv").show();
	$("#sequentialAppDiv").hide();
	$("#sequentialOwner1").val('');
	$("#sequentialOwner2").val('');
// 	$("#parallelOwner").val('');
	$('#sequentialOwner1').trigger("chosen:updated");
	$('#sequentialOwner2').trigger("chosen:updated");
// 	$('#parallelOwner').trigger("chosen:updated");
	
	$('#parallelOwner').removeAttr('data-validation-optional');
	$('#parallelOwner').attr('data-validation',"required");
	$('#sequentialOwner2').removeAttr('data-validation');
	$('#sequentialOwner2').attr('data-validation-optional',"true");
	$('#sequentialOwner1').removeAttr('data-validation');
	$('#sequentialOwner1').attr('data-validation-optional',"true");
}

function getSequentialApprovers(){
		$("#parallel i").removeClass("apprverReq-active");
		$("#sequential i").addClass("apprverReq-active");
		$("#sequentialAppDiv").show();
		$("#parallelAppDiv").hide();
//	 	$("#sequentialOwner1").val('');
//	 	$("#sequentialOwner2").val('');
		$("#parallelOwner").val('');
		
//	 	$('#sequentialOwner1').trigger("chosen:updated");
//	 	$('#sequentialOwner2').trigger("chosen:updated");
		$('#parallelOwner').trigger("chosen:updated");
		$('#sequentialOwner1').removeAttr('data-validation-optional');
		$('#sequentialOwner1').attr('data-validation',"required");
		$('#sequentialOwner2').removeAttr('data-validation-optional');
		$('#sequentialOwner2').attr('data-validation',"required");
		$('#parallelOwner').removeAttr('data-validation');
		$('#parallelOwner').attr('data-validation-optional',"true");
}
</script>
<script>
function showCommonSOWFields(){
	document.getElementById("hoursPerWeekDiv").style.display="block";
	$('#hoursPerWeek').val("40");
	$('#hoursPerWeekDiv').find('p.jquery_form_error_message').remove();
	
	document.getElementById("overTimeHoursDiv").style.display="block";
	
	document.getElementById("totalHoursEstimatedDiv").style.display="block";
	
	document.getElementById("overtimeRateDiv").style.display="block";
}
</script>

<script>
function hideCommonSOWFields(){
	/* $("#requirementsList option:selected").removeAttr("selected");
	$('#requirementsList').trigger("chosen:updated");
	$('#requirementsList').removeAttr('data-validation');
	$('#requirementsList').attr('data-validation-optional',"true");
	$('#requirementDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("requirementDiv").style.display="none"; */
	
	$("#submissionOffersList option:selected").removeAttr("selected");
	$('#submissionOffersList').trigger("chosen:updated");
	$('#submissionOffersList').removeAttr('data-validation');
	$('#submissionOffersList').attr('data-validation-optional',"true");
	$('#offeredSubmissionDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("offeredSubmissionDiv").style.display="none";
	
	$('#offerRate').val("");
	document.getElementById("offerRateDiv").style.display="none";
	
	$('#hoursPerWeek').val("");
	$('#hoursPerWeekDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("hoursPerWeekDiv").style.display="none";
	
	$('#overTimeHours').val("");
	$('#overTimeHoursDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("overTimeHoursDiv").style.display="none";
	
	$('#totalHoursEstimated').val("");
	$('#totalHoursEstimatedDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("totalHoursEstimatedDiv").style.display="none";
	
	$('#overtimeRate').val("");
	$('#overtimeRateDiv').find('p.jquery_form_error_message').remove();
	document.getElementById("overtimeRateDiv").style.display="none";
}

</script>

<script>
function previewTemplatePDF(){
	var res = $('input[name="fileInfo[0].fileURL"]').val();
	var fileurl = res.replace("ContractDocs", "temp");
	var filename = $('input[name="fileInfo[0].fileName"]').val();
	var contentType = $('input[name="fileInfo[0].contentType"]').val();
	viewFile(fileurl, filename, contentType);
}
</script>

<script type="text/javascript">
	function saveExistigContract() {
		if ($('#newcontract_form').validate(false, validationSettings)) {
			/* $("#save_template").attr("disabled", "disabled");
			$("#save_template" ).addClass('button').val('Processing..');
			var form = $('#newcontract_form')[0];
      		var formData = new FormData(form); 
			var sourceleadUrl = '${sourceleadUrl}';
			$("#newcontract_form").attr("disabled", "disabled");
			
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 		$.ajax({
		 			type : "POST",
	        	    data: formData,
	    	        processData: false,
	    	        contentType:false,
	        	    dataType : 'json',
					url : sourceleadUrl+ "/saveExistigContract?${_csrf.parameterName}=${_csrf.token}",
					success : function(result) {
						location.replace(sourceleadUrl+"/contractListbyStatus");
					},
					}); */ 
			var formData = getFormData("newcontract_form");
					$("#existLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	           $.post( sourceleadUrl+ "/saveExistigContract?${_csrf.parameterName}=${_csrf.token}", formData, function(data) {
	                      if(data == true){
	                   	   location.replace(sourceleadUrl+"/contractListbyStatus");
	                      }
	                   });		
					
			return true;
		} else {
			$('#submissionOffersListDiv').find('p.text-primary').remove();
			return false;
		}
	}
	$('body').on('blur', '#newcontract_form', function() {
		$("#newcontract_form").showHelpOnFocus();
		$("#newcontract_form").validateOnBlur(false, validationSettings);
	});
</script>


<script>
	function getRequirements(sowType){
		$("#requirementsList").empty();
		var sourceleadUrl ='${sourceleadUrl}';
		$.ajax({
	        type : "GET",
	        url: sourceleadUrl+"/getRequirementsForSOWByOrgLocationId?sowType="+sowType,    
	         success :function(data) {
	              if(data != null && data != 'undefined' && data != "" && data != '[]'){
	            	  var json = JSON.parse(data);
	            	  var result = "";
	            	  result = result + '<option value="">Select Job</option>';
	                  for (var i = 0; i < json.length; i++) {
	                 	 var opt = json[i]; 
	                 	 result = result +  '<option value="'+opt.id+'"> '+opt.jobTitle+''+opt.spocName+'</option>';
	                  }
	                  $("#requirementsList").empty();
	                  $("#requirementsList").append(result);
	                  $("#requirementsList").chosen();
	              	  $("#requirementsList").trigger('chosen:update');
	              }
	              $('#requirementsList')
	                .find('option:first-child').prop('selected', true)
	                .end().trigger('chosen:updated');
	              document.getElementById("requirementDiv").style.display="block";
	             // $('#requirementsList').removeAttr('data-validation-optional');
	  			 // $('#requirementsList').attr("data-validation","required");
	       },
	    });
	}
		
	function submissionOffers(){
		var sourceleadUrl ='${sourceleadUrl}';
		var requirementId = "";
		var sowType = $("#contractType").val();
		var locationId ='${SourceLeadWeb_orgLocationId}';
	if(sowType == 'Client SOW' || sowType == 'Vendor SOW' || sowType == 'Full time offer'){
	$("#flatReward").val("");
	    $("#billingRateType").val('${currencyCode}').trigger('change');
	    $("#billingRatePer").val("").trigger('change');
	    var url = "";
	    if(sowType == 'Client SOW' || sowType == 'Vendor SOW'){
	    	url = "/getAssociatesListForSow?locationId="+locationId+"&sowType="+sowType;
	    }else{
	    	url = "/getSubmissionsForSOWByRequirementId?requirementId="+requirementId+"&sowType="+sowType;
	    }
	    $.ajax({
	        type : "GET",
	        url: sourceleadUrl+url,  
	         success :function(data) {
	              if(data != null && data != 'undefined' && data != "" && data != '[]'){
	            	  
	            	  if(sowType == 'Client SOW' || sowType == 'Vendor SOW'){
	            		  var json = data;
		            	  var result = "";
		            	  result = result + '<option value="">Select Applicant</option>';
		                  for (var i = 0; i < json.length; i++) {
		                 	 var opt = json[i]; 
		                 	result = result +  '<option value="'+opt.id+'"> '+opt.candidateName+'</option>';
		                  } 
	            	  }else{
	            		  var json = JSON.parse(data);
		            	  var result = "";
		            	  result = result + '<option value="">Select Applicant</option>';
		                  for (var i = 0; i < json.length; i++) {
		                 	 var opt = json[i]; 
		                 	result = result +  '<option value="'+opt.id+'"> '+opt.candidateName+'</option>';
		                  }
	            	  }
	            	  
	                  $('#submissionOffersListDiv').find('p.text-primary').remove();
	                  $("#submissionOffersList").empty();
	                  $("#submissionOffersList").append(result);
	                  $("#submissionOffersList").chosen();
	              	  $("#submissionOffersList").trigger('chosen:update');
	              }else{
	            	  var result = "";
	            	  result = result + '<option value="">Select Applicant</option>';
	            	  $("#submissionOffersList").empty();
	            	  $("#submissionOffersList").append(result);
	                  $("#submissionOffersList").chosen();
	              	  $("#submissionOffersList").trigger('chosen:update');
	              	  $('#submissionOffersListDiv').find('p.jquery_form_error_message').remove();
	              	  $('#submissionOffersListDiv').find('p.text-primary').remove();
	              	  $("#submissionOffersListDiv").append("<p class='text-primary pull-left small'><i class='fa fa-info-circle paddingRight'></i>No Applicants found </p>");
	              }
	              $('#submissionOffersList')
	                .find('option:first-child').prop('selected', true)
	                .end().trigger('chosen:updated');
	              document.getElementById("offeredSubmissionDiv").style.display="block";
	              $('#submissionOffersList').removeAttr('data-validation-optional');
	  			  $('#submissionOffersList').attr("data-validation","required");
	       },
	    });
	}
	}
	function getOfferRate(){
		var sourceleadUrl ='${sourceleadUrl}';
		var reqSubId = $("#submissionOffersList").val();
		var sowType = $("#contractType").val();
		var locationId ='${SourceLeadWeb_orgLocationId}';
		if(sowType!="Full time offer"){
			$("#flatReward").val("");
		    $("#billingRateType").val('${currencyCode}').trigger('change');
		    $("#billingRatePer").val("").trigger('change');
		}
		
		 if(sowType != 'Client SOW' && sowType != 'Vendor SOW'){
			 $.ajax({
			        type : "GET",
			        url: sourceleadUrl+"/getSubmissionOfferForSOWByRequirementSubmissionId?reqSubId="+reqSubId+"&locationId="+locationId,    
			         success :function(data) {
			        	 
				              if(data != null && data != 'undefined' && data != "" && data != '[]'){
				            	  var json = JSON.parse(data);
				            	  $("#offerRate").val("");
				            	  document.getElementById("offerRateDiv").style.display="block";
				            	  $("#offerRate").val(json.paymentType+" "+json.amount+"/"+json.paymentMode);
				            	  if(sowType!="Full time offer"){
					            	  $("#flatReward").val("");
					            	  $("#flatReward").val(json.amount);
					            	  
					            	  $("#billingRateType").val("");
					            	  $("#billingRateType").val(json.paymentType).trigger('change');
					            	  
					            	  $("#billingRatePer").val("");
					            	  $("#billingRatePer").val(json.paymentMode).trigger('change');
				            	  }
				            	  else{
				            		  $("#joinDate").val(json.dateOfJoiningStr); 
				            		  
				            		  $("#flatReward").val("");
					            	  $("#flatReward").val(json.amount);
					            	  
					            	  $("#billingRateType").val("");
					            	  $("#billingRateType").val(json.paymentType).trigger('change');
					            	  
					            	  $("#billingRatePer").val("");
					            	  $("#billingRatePer").val(json.paymentMode).trigger('change');
				            	  }
				            	  
			        	 		}
				       },
			    });
		 }else{
		 	$("#flatReward").val("");
       	  
       	    $("#billingRateType").val("${currencyCode}");
       	    $("#billingRateType").trigger('change');
       	  
       	    $("#billingRatePer").val("");
       	    $("#billingRatePer").val("").trigger('change');
		 } 

	}
	
	function submissionOffers1(){
		var sourceleadUrl ='${sourceleadUrl}';
		var requirementId = $("#requirementsList").val();
		var sowType = $("#contractType").val();
		$("#flatReward").val("");
	    $("#billingRateType").val('${currencyCode}').trigger('change');
	    $("#billingRatePer").val("").trigger('change');
	    $.ajax({
	        type : "GET",
	        url: sourceleadUrl+"/getSubmissionsForSOWByRequirementId?requirementId="+requirementId+"&sowType="+sowType,    
	         success :function(data) {
	              if(data != null && data != 'undefined' && data != "" && data != '[]'){
	            	  var json = JSON.parse(data);
	            	  var result = "";
	            	  result = result + '<option value="">Select Applicant</option>';
	                  for (var i = 0; i < json.length; i++) {
	                 	 var opt = json[i]; 
	                 	result = result +  '<option value="'+opt.id+'"> '+opt.candidateName+'('+opt.createdUser+')</option>';
	                  }
	                  $('#submissionOffersListDiv').find('p.text-primary').remove();
	                  $("#submissionOffersList").empty();
	                  $("#submissionOffersList").append(result);
	                  $("#submissionOffersList").chosen();
	              	  $("#submissionOffersList").trigger('chosen:update');
	              }else{
	            	  var result = "";
	            	  result = result + '<option value="">Select Applicant</option>';
	            	  $("#submissionOffersList").empty();
	            	  $("#submissionOffersList").append(result);
	                  $("#submissionOffersList").chosen();
	              	  $("#submissionOffersList").trigger('chosen:update');
	              	  $('#submissionOffersListDiv').find('p.jquery_form_error_message').remove();
	              	  $('#submissionOffersListDiv').find('p.text-primary').remove();
	              	  $("#submissionOffersListDiv").append("<p class='text-primary pull-left small'><i class='fa fa-info-circle paddingRight'></i>No Applicants found </p>");
	              }
	              $('#submissionOffersList')
	                .find('option:first-child').prop('selected', true)
	                .end().trigger('chosen:updated');
	              document.getElementById("offeredSubmissionDiv").style.display="block";
	              $('#submissionOffersList').removeAttr('data-validation-optional');
	  			  $('#submissionOffersList').attr("data-validation","required");
	       },
	    });
	}
	
	function getVendor(){
		var sourceleadUrl ='${sourceleadUrl}';
		var relationList = '${orgRelationsList}';
		var associateId = $("#submissionOffersList").val();
		var map = new Object(); // or var map = {};
		<c:forEach var="vendor" items="${orgRelationsList}">
		        map['${vendor.id}'] = '${vendor.orgName}';
		</c:forEach>
		$.ajax({
	        type : "GET",
	        url: sourceleadUrl+"/getCandidateDetails?candidateId="+associateId,    
	         success :function(data) {
	        	 if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		var json = data.object;
			           	 var result = "";
			           	 result = result + '<option value="">Select Vendor</option>';
			           	 $.each(map, function(key, value) {
			                 if(json.relationId == key){
		                		 result = result +  '<option value="'+key+'" selected> '+value+'</option>';
		                	 }else{
		                		 result = result +  '<option value="'+key+'"> '+value+'</option>';
		                	 }
			           		});
		                 $("#orgRelationId").empty();
		                 $("#orgRelationId").append(result);
		                 $('#orgRelationId').trigger("chosen:updated");
		                 getVendorContacts(json.venSpocId);
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
	         }
		});
	}
</script>


<script type="text/javascript">
function getTaxRewardType(rewardType){
	
	 $("#taxRewardType").val(rewardType);
	 
	 $("#taxPercentageRewardValue").val('');
	 $("#taxFlatRewardValue").val('');
	 
	 $('#taxPercentageReward').find('p.jquery_form_error_message').remove();
	 $('#taxFlatReward').find('p.jquery_form_error_message').remove();
	 
	if(rewardType == 'Percentage'){
		document.getElementById("taxPercentageReward").style.display="";
		document.getElementById("taxFlatReward").style.display="none";
		document.getElementById("taxFlatRateType").style.display="none";
		
		document.getElementById("taxPercentageBtn").className = 'btn btn-sm btn-primary active'
	    document.getElementById("taxFlatBtn").className = 'btn btn-sm btn-primary'
    	$('#taxPercentageRewardValue').attr("data-validation","required validate_float");  
		$('#taxPercentageRewardValue').removeAttr('data-validation-optional');
	    $('#taxFlatRewardValue').removeAttr('data-validation');
	    $('#taxFlatRewardValue').attr("data-validation-optional","true"); 
	    	
	    
	}
	
	if(rewardType == 'Flat'){
		/* document.getElementById("crowd_rate").style.display=""; */
		document.getElementById("taxFlatReward").style.display="";
		document.getElementById("taxFlatRateType").style.display="";
		document.getElementById("taxPercentageReward").style.display="none";
		
		document.getElementById("taxPercentageBtn").className = 'btn btn-sm btn-primary'
		document.getElementById("taxFlatBtn").className = 'btn btn-sm btn-primary active'
		$('#taxFlatRewardValue').attr("data-validation","required validate_float");
		$('#taxFlatRewardValue').removeAttr('data-validation-optional');
		$('#taxPercentageRewardValue').removeAttr('data-validation');
		$('#taxPercentageRewardValue').attr("data-validation-optional","true"); 
		
	}
}
</script>

<script>
function getTaxPercentageValue(){
	var percentageValue = document.getElementById("taxPercentageRewardValue").value;
	if(percentageValue > 100 || percentageValue < 0){
		/* $("#releaseToCWBtn").attr("disabled", "disabled"); */
	    $('#taxPercentageReward').find('p.jquery_form_error_message').remove(); 
	   
	    if(percentageValue < 0){
	    	$("input#taxPercentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value should not in negitive</span>");
	    }else{
	    	$("input#taxPpercentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value not more than 100</span>");
	    }
	    
		$('input#taxPercentageRewardValue').attr('record-exist','yes');
		 if(percentageValue < 0){
			 $('input#taxPercentageRewardValue').attr('record-exist-errorMsg','Percentage value should not in negitive'); 
		 }else{
			 $('input#taxPercentageRewardValue').attr('record-exist-errorMsg','Percentage value not more than 100'); 
		 }
		
	}else{
		 /* $("#releaseToCWBtn").removeAttr("disabled"); */
		 $('input#taxPercentageRewardValue').removeAttr( "record-exist record-exist-errormsg");
		 $('#taxPercentageReward').find('p.jquery_form_error_message').remove(); 
		/*  $( "#releaseToCWBtn" ).removeAttr("style");
		 $( "#releaseToCWBtn" ).click(function() {
		 if ($(this).validate(false, validationSettings)){
		 }
		}); */
	}
	
}
</script>
<script>
function changeSaveButton(){
	var userId = '${SourceLeadWeb_userId}';
	 var approverId = $("#approver").val();
	if(approverId !='' && approverId != null){
		if(approverId == userId){
			document.getElementById("save_approver_template").value="Approve";
		}else{
			document.getElementById("save_approver_template").value="Send For Approve";
		}
		document.getElementById("save_approver_template").style.display="block";
		document.getElementById("save_template").style.display="none";
	}else{
		document.getElementById("save_approver_template").style.display="none";
		document.getElementById("save_template").style.display="block";
	}
}
</script>

<script>
function flush(){
	document.getElementById("contractTitle").value="";
//document.getElementById("owner").value="";
	document.getElementById("template").value="";
	document.getElementById("requirementsList").value="";
	document.getElementById("submissionOffersList").value="";
	document.getElementById("startDate").value="";
	document.getElementById("endDate").value="";
	
	document.getElementById("orgRelationId").value="";
	
	document.getElementById("vendorContact").value="";
	document.getElementById("clientOrgRelationId").value="";
	document.getElementById("clientContact").value="";
	document.getElementById("approver").value="";
	document.getElementById("description").value="";
	
	//document.getElementById("hoursPerWeek").value="";
	document.getElementById("overTimeHours").value="";
	document.getElementById("totalHoursEstimated").value="";
	document.getElementById("overtimeRate").value="";
	//document.getElementById("overtimeRateType").value="";
	
	document.getElementById("invoiceFrequency").value="";
	document.getElementById("taxFlatRateType").value="";
	document.getElementById("submissionOffersList").value="";
	
	
	
	$("newcontract_form").validate().resetForm();
}
</script>

<script>
function cancelContract(){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/contractListbyStatus");
}

</script>

<script>
	$("#uploadBtn").change(function() {
		fileUpload("uploadBtn", "ContractDocs", "newcontract_form");
		$("#tempEye").show();
	});
</script>

<script>
function validateContractTitle(){
	var contractTitle = $('#contractTitle').val();
	var contractType = $('#contractType').val();
	if(contractTitle != null && contractTitle != "" && contractType != null && contractType != ""){
		$.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateContractTitle?${_csrf.parameterName}=${_csrf.token}&contractTitle="+contractTitle+"&contractType="+contractType, 
		        success: function(data) {
		        	if(data == 'true'){
		        		 // $('input#contractTitle').removeAttr( "record-exist record-exist-errormsg");
		        		  $('#titleDiv').find('p.jquery_form_error_message').remove(); 
						  $("input#contractTitle").after("<p class='jquery_form_error_message'>"+contractTitle+" is Already Existed</span>");
						  $("#save_template").attr("disabled", "disabled");
						 // $("#draft_template").attr("disabled", "disabled");
		        	}
		        	else{
		        		 $('input#contractTitle').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#titleDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#save_template" ).addClass('button').val('Send');
                         $("#save_template").removeAttr("disabled");
                        //$("#draft_template").removeAttr("disabled");
		        	}
		        	}
		        });
	 }
	
}

</script>


<script>
function refreshVendorList(){
		 var sourceleadUrl ='${sourceleadUrl}';
		 var selectedOrgId = '${contract.orgRelations.id}';
		 $.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/refreshVendorList",       
	       success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>Select Vendor</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(opt.id == selectedOrgId){
		        			output = output + "<option value='"+opt.id+"' selected>"+opt.orgName+"</option>";
		        		}else{
		        			output = output + "<option value='"+opt.id+"' >"+opt.orgName+"</option>";
		        		}
				}
	        	$("#orgRelationId").empty();
		        $("#orgRelationId").append(output);
		        $('#orgRelationId').trigger("chosen:updated");
	        }
		 });
		 
		
	
} 

</script>
<script>
function addExistedEmptyVendorContact(){
	$("#vendorContact").empty();
	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
    $('#vendorContact').trigger("chosen:updated");
}

</script>
 
<script>
function addExistedEmptyClientContact(){
	 $("#clientContact").empty();
	 $('#clientContact').append('<option value="">--select Client contact--</option>');
     $('#clientContact').trigger("chosen:updated");
}

</script>
<script>
	 function selectedVendor(){
		 var vid =   $("#orgRelationId").val();
		 window.open(sourceleadUrl+"/addVendorContact?vid="+vid,'_blank');
	 }
	 </script>
	 <script>
	 function selectedClient(){
		 var cid =  $("#clientOrgRelationId").val();
		 window.open(sourceleadUrl+"/addClientContact?cid="+cid,'_blank');
	 }
	 </script>

<script>
/* function clearExistedContractStartDate(){
	$("#startDate").val("");
	}; */
function clearExistedContractEndDate(){
	$("#endDate").val("");
	};
function clearExistedContractJoinDate(){
	$("#joinDate").val("");
	};
</script>

<script>
function refreshSigningAuthority2(){
	var sequentialOwner1 = $("#sequentialOwner1").val();
	var result = "";
	<c:forEach var="user" items="${userList}">
		if(sequentialOwner1 != "${user.id}"){
			result = result +  '<option value="${user.id}"> ${user.firstName} ${user.lastName}</option>';
		}
	</c:forEach>
	$("#sequentialOwner2").empty();
	$("#sequentialOwner2").append(result);
	$('#sequentialOwner2').trigger("chosen:updated");
}
</script>
<!-- <script>
	$(".clearUpload").click(function(){
		$("#uploadFile").val("");
		$(".clearUpload").hide();
		$("#newcontract_form input[data-id*=uploadBtn]").remove();
	});
</script> -->

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->
