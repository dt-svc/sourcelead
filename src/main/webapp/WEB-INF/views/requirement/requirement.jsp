<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>


<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">

<script src="resources/js/jquery-fun.js"></script> 

<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
 
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script> 
<!-- end files for autocomplete with tagit -->

<jsp:include page="requirementDetails.jsp"></jsp:include>
<jsp:include page="requirementClientDetails.jsp"></jsp:include>
<jsp:include page="requirementInternalDetails.jsp"></jsp:include>


<style type="text/css">
.modal-bodyCustom {
margin-bottom: 15px;
padding-bottom: 15px;
min-height: 150px;
}
.popupFilter #example_filter {
top:-63px !important;
}
.dataTable thead {
background: #368ee0;
color:#fff;
}
table.dataTable.no-footer {
border-bottom:0;
}
table.dataTable thead th, table.dataTable thead td {
border-bottom:0;
}
table.dataTable thead th, table.dataTable thead td {
padding: 10px;
}
.dataTables_wrapper .dataTables_paginate {
padding: 0 !important;
margin-top: 15px;
}
.dataTables_info {
padding: 0 !important;
margin-top: 10px;
}
.dataTables_wrapper .dataTables_filter input {
background-color:#fff;
height: 34px;
width:400px;
border: 1px solid rgb(206, 204, 204);
border-radius:3px;
}
.modal-footer {
background-color: #ccc;
}
.ratetype p {
bottom: -14px;
left:-20px !important;
}
.pras.req-title p{
bottom:-14px;
}
</style>

<script>
$(document).ready(function() {
	var requirementAssignedIds ='${requirementAssignedIds}';
	if (requirementAssignedIds != null && requirementAssignedIds !=  '' && requirementAssignedIds !='undefined')
	{
		
		if(requirementAssignedIds != null && requirementAssignedIds != 'undefined' && requirementAssignedIds != ''){
			var result = "";
			var requirementAssignedToIds = [];
			requirementAssignedToIds = requirementAssignedIds.split("&");
			var count = 1;
			
			for (i=0;i < requirementAssignedToIds.length;i++)
	        {
	        	 result = result+ "<input type='hidden' class='defaultCheckBox' name='assignUserId"+count+"' id='assignUserId"+count+"' value='"+requirementAssignedToIds[i]+"' />";
	        	 count++;
	        }
			result = result+"<input type='hidden' name='assignedToMaxCount' id='assignedToMaxCount' value='"+count+"'/>";
			$("#assignedToSavingDiv").empty();
			$("#assignedToSavingDiv").append(result); 
			assignedToValidation();
		}	
	} 
});
</script>


<script type="text/javascript">
		window.onload = function () {
			getAllCountries();
			removeEditButton();
			disableMonths();
			
			/*  $(".isClient").click(function(){  
		        $(".clientDetails").toggleClass('hidden', 500);
		    });  */
			
			$(".cd1").click(function(){  
		        $(".col-md-12 clearfix").toggleClass('hidden', 500);
		    });
			
		 };
</script> 

 <script>
		$(document).ready(function() {
			document.getElementById("requirement_form").reset();
			getIsClientVal();
		});
</script>

<script>
$(document).ready(function () {
   /*  var selectedUserRole = document.getElementById('SelectedUserRole').value; */
    var approverMode = '${approverMode}';
    var parallelApproverIds = '${parallelApproverIds}';
    var sequentialApprover1 = '${sequentialApprover1}';
    var sequentialApprover2 = '${sequentialApprover2}';
	if(approverMode != null && approverMode != '' && approverMode != 'undefined'){
			/*  $("#apprverReq").prop('checked', true); */
		    if(approverMode == 'Sequential'){
		    		$('#apprverReq').click();
		    	    approvalType('Sequential');     
		    	    var str_array = sequentialApprover1.split(',');
		    	    for (var i = 0; i < str_array.length; i++) {
		    	        str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		    	    }
		    	    $("#sequentialApprover1").val(str_array).trigger("chosen:updated");
		    	    
		    	    
		    	    var str_array2 = sequentialApprover2.split(',');
		    	    for (var i = 0; i < str_array2.length; i++) {
		    	    	str_array2[i] = str_array2[i].replace(/^\s*/, "").replace(/\s*$/, "");
		    	    }
		    	    $("#sequentialApprover2").val(str_array2).trigger("chosen:updated");
		    	    
		    } 
			if(approverMode == 'Parallel'){
				$('#apprverReq').click();
				approvalType('Parallel');
				var str_array = parallelApproverIds.split(',');
			    for (var i = 0; i < str_array.length; i++) {
			        str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
			    }
			    $("#parallelApprover").val(str_array).trigger("chosen:updated");
		    	
		    } 
			if(approverMode == 'noApprovers'){
				 $("#apprverReq").prop('checked', false); 
			}
	}
    
    
});    
</script>
    

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
     <div class="pull-left">
      <h2>Requirement Details</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/requirementList'/>">Requirement List  </a>&raquo; Add Requirement </div>
  </section>
  <div class="content-area content-new clearfix">
  <div id="loading"></div>
   <form:form id="requirement_form" name="requirement_form"  enctype='multipart/form-data' >
    <div class="form-horizontal req-dtls myForm row">
      <div class="clearfix">
        <div class="col-lg-12">
          <h3>Add Requirement Details</h3>
        </div>
	       <div class="col-md-12 clearfix">
        	  <div class="col-md-12">
	          <div style="margin:0 auto" class="input-group col-md-6">
	            <label class="input-group-btn">
	            	<span class="btn btn-primary">Select Template
	                </span>
	            </label>
                <select class="form-control chosen-select" id="template"  onChange="setTemplateValues();">
                   <option value=""  selected>Select template</option>
                    <c:forEach var="title" items="${jobsTemplateList}">
	                    <option value="${title.id}">${title.jobTitle}</option>
                     </c:forEach>   
                </select>
	          </div>
	          
	          <div class="text-center clearfix mt text-muted">
	          	<div class="clearfix text-center mb">
	          <span class="text-info">
	          	<strong>
	            <b class="hidden-xs">---------------------------------</b><span class="r-circle">OR</span>
	            <b class="hidden-xs">------------------------------</b>
	            </strong>
	            </span>
	          </div>
	          </div>
	 </div>
</div>

          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt pras req-title">
           <div class="form-group" id="jobTitleDiv">
	         <label class="col-md-3">Title<span class="text-danger">*</span></label>
	         <div class="col-md-9">
	          <div class="input-group">
	             <input type="text" id="jobTitle" name="jobTitle" value="${requirement.jobTitle}" field-name="Job Title"  data-validation="required validate_Space validate_length length2-250"   value="" class="form-control" onblur="getTemplateVal();">
	             
	             <div class="input-group-btn selp-custom-min">
	             <select class="selectpicker" id="jobType" name="jobType">
	                 <option  ${requirement.jobType  == 'c2c' ? 'selected' : ''}><a href="#">C2C</a></option>
	                 <option  ${requirement.jobType  == 'W-2' ? 'selected' : ''}><a href="#">W-2</a></option>
	                 <option  ${requirement.jobType  == 'Part Time' ? 'selected' : ''}><a href="#">Part Time</a></option>
	                 <option  ${requirement.jobType  == 'Full Time' ? 'selected' : ''}><a href="#">Full Time</a></option>
	                 <option  ${requirement.jobType  == 'Other' ? 'selected' : ''}><a href="#">Other</a></option>
	             </select>
	             </div>
	           </div>
	         </div>
	       </div>
	       
          <div class="form-group" id="jobIdDiv">
            <label class="col-md-3">Id</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="jobId" name="jobId" value="${requirement.jobId}"  field-name="JobId" data-validation="validate_Space validate_length length2-100" data-validation-optional="true"  onblur="validateJobId();"  onmouseout="validateJobId();" value="">
            </div>
          </div>
          
          <div class="form-group">
            <label class="col-md-3">No. of Positions</label>
            <div class="col-md-9">
               <input type="text" class="form-control" id="positions" name="positions" value="${requirement.positions}"  field-name="Positions" data-validation="validate_Space validate_length length1-5 validate_int" data-validation-optional="true"  value="">
            </div>
          </div>
          
          <div class="form-group">
              <label class="col-md-3">Education</label>
              <div class="col-md-9">
                <select multiple  class="chosen-select form-control" name="education" id="education" data-validation="validate_Space" data-validation-optional="true">
                <c:forEach var="edu" items="${educationsList}">
                		  <c:if test="${fn:contains(requirement.reqEducationListString, edu.name)}">
                		  	 <option  selected value="${edu.name}">${edu.name}</option>
                		  </c:if>
                		  <c:if test="${!fn:contains(requirement.reqEducationListString, edu.name)}">
                		  	 <option  value="${edu.name}">${edu.name}</option>
                		  </c:if>
                </c:forEach>
                </select>
              </div>
            </div>
            
         <div class="form-group" >
            <label class="col-md-3">Experience</label>
            <div class="col-md-9">
              <div class="row">
	                <div class="col-md-6">
	                 <input type="text" class="form-control" placeholder="minExp" id="minExp" name="minExp" value="${requirement.minExp}"  field-name="MinExp" data-validation="validate_Space validate_length length1-2 validate_float" data-validation-optional="true" onblur="minMaxExperience();" value="">
	                </div>
	                <div class="col-md-6" >
	                <input type="text" class="form-control" id="maxExp" name="maxExp" placeholder="maxExp" value="${requirement.maxExp}"  field-name="MaxExp" data-validation="validate_Space validate_length length1-2 validate_float" data-validation-optional="true" onblur="minMaxExperience();" value="">
	                </div>
              </div>
              <div class="row" id="expId">
                  <input type="hidden" name="expValidation" id="expValidation">
              </div> 
        <%--          <div class="row">
             <div class="col-xs-6 col-md-6">
             		<select class="form-control" id="minExp" name="minExp"  onchange="disableMonths();">
             		  <option selected disabled>Year</option>
             		  <c:forEach begin="0" end="24" varStatus="loop">
						    <option  ${requirement.minExp == loop.index ? 'selected' : ''} >${loop.index}</option>
						</c:forEach>
             		     <option>25+</option>
             		</select>
             </div>
             
             <div class="col-xs-6 col-md-6">
             		<select class="form-control" id="maxExp" value = "" name="maxExp">
             		<option selected disabled>Month</option>
             		 <c:forEach begin="0" end="11" varStatus="loop">
						    <option  ${requirement.maxExp == loop.index ? 'selected' : ''}>${loop.index}</option>
						</c:forEach>
             		</select>
             </div>
            </div> --%>
              
            </div>
          </div>
          
          <div class="form-group">
            <label class="col-md-3">Pay Rate/Salary</label>
            <div class="col-md-9">
             <div class="input-group">
                <div class="input-group-btn selp-custom-min">
                  
                  <select class="selectpicker" id="currencyCode" name="currencyCode">
	                 <option ${requirement.currencyCode  == 'INR' ? 'selected' : ''}><a href="#">INR</a></option>
	                 <option ${requirement.currencyCode  == 'AUD' ? 'selected' : ''}><a href="#">AUD</a></option>
	                 <option ${requirement.currencyCode  == 'USD' ? 'selected' : ''}><a href="#">USD</a></option>
	                 <option ${requirement.currencyCode  == 'GBP' ? 'selected' : ''}><a href="#">GBP</a></option>
	                 <option ${requirement.currencyCode  == 'SGD' ? 'selected' : ''}><a href="#">SGD</a></option>
	                 <option ${requirement.currencyCode  == 'EUR' ? 'selected' : ''}><a href="#">EUR</a></option>
                </select>
                
                </div>
                <input type="text" class="form-control" id="rate" name="rate" value="${requirement.rate}" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true"  value="">
                <div class="input-group-btn selp-custom-min">
                  <select class="selectpicker ratetype" id="rateType" name="rateType" field-name="Rate Type">
                    <option value="">Rate Per</option>
                    <option ${requirement.rateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
                    <option ${requirement.rateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
                    <option ${requirement.rateType  == 'Biweekly' ? 'selected' : ''}>Bi-weekly</option>
                    <option ${requirement.rateType  == 'SemiMonthly' ? 'selected' : ''}>Semi-Monthly</option>
                    <option ${requirement.rateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
                    <option ${requirement.rateType  == 'BiMonthly' ? 'selected' : ''}>Bi-Monthly</option>
                    <option ${requirement.rateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          
        
          
          <div class="form-group">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				 <select class="chosen-select form-control select-bg" name="countryReq" id="countryReq" field-name="Country" onchange="changeCurrency();" >
                 </select>
                   <input type="hidden" id="existingCountry" value="${requirement.country}" >
				</div>
		</div>
			    
          
          <div class="form-group">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<select  class="chosen-select form-control select-bg" name="state" id="state" field-name="State"  >
                 </select>
                 <input type="hidden" id="existingState" value="${requirement.state}" >
				</div>
		 </div>
		 
		 <div class="form-group">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
			     <select  class="chosen-select form-control select-bg" name="city" id="city" field-name="City"  >
                 </select>
                 <input type="hidden" id="existingCity" value="${requirement.city}" >
			</div>
		</div>
		
         <div class="form-group">
              <label class="col-md-3">Skills</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="skills" id="skills" data-validation-optional="true">
               <c:forEach var="skill" items="${skillsList}">
             				 <c:if test="${fn:contains(requirement.reqSkillsListString, skill.name)}">
	                		  	 <option  selected value="${skill.name}">${skill.name}</option>
	                		 </c:if>
	                		 <c:if test="${!fn:contains(requirement.reqSkillsListString, skill.name)}">
	                		  	 <option  value="${skill.name}">${skill.name}</option>
	                		 </c:if>
                </c:forEach>
                </select>
              </div>
            </div>
         
          <div class="form-group">
            <input type="hidden" name="templateId" id="templateId" value="" />
            <label class="col-md-3">Attachment</label>
            <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
            <div class="col-md-9">
              <div class="input-group">
                <label class="input-group-btn">
                <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                       <input type="file" name="uploadBtn" id="uploadBtn"  class="hidden upload">
                  </span>
                </label>
                
                <input type="text" class="form-control" readonly id="uploadFile" name="docFile"  value="${requirement.fileName}" type="file"   placeholder="Upload File ..." field-name="Upload Resume" data-validation="validate_docimgpdf" data-validation-optional="true">
              <div class="clearfix"></div> <div class="clearfix"></div>
              </div>
            </div>
            <div class="clearfix"></div>
            <!-- upload file html need to add bottom JavaScript otherwise it will not work --> 
          </div>
           <div class="form-group">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
             <textarea rows="2" class="form-control" id="description" name="description" value="${requirement.description}" field-name="Description" data-validation="validate_length length2-5000" data-validation-optional="true">${requirement.description}</textarea>
            </div>
          </div>
          
          <div class="form-group">
            
            <label class="col-md-3">Is Template</label>
            <div class="col-md-9">
            <input type="checkbox" name="isTemplate" id="isTemplate" value="" onclick="getTemplateVal();">
		    </div>
        </div>
        
      </div>
      <div class="clearfix mt">
        <div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
	        <c:if test="${(SourceLeadWeb_org.vms == 'Y' && SourceLeadWeb_org.ats == 'Y') || (SourceLeadWeb_org.vms != 'Y')}">
	        	<div class="ml">
		            <input type="checkbox" name="isClient" id="isClientJob" class="isClient" value="${requirement.isClientJob}"
		            ${requirement.isClientJob  == 'Y' ? "checked" : ''} onclick="getIsClientVal();">
		            <label>&nbsp;&nbsp;<strong>Is Client Job</strong>
		            &nbsp;&nbsp;
		            <i class="fa fa-question-circle-o text-info" title="Click the Checkbox"></i></label>
			  	</div>
	        </c:if>
	      
          <div id="clientDetails" style="display: none;">
            <div class="clearfix">
              <h3>Client Details</h3>
            </div>
            <div class="clearfix">
              <div class="form-group">
                <label class="col-md-3">Client <span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control select-bg" data-validation-optional="true" field-name = "Client" name="selectClient" id="addClientId" onchange="getContactList(this); getClientSubmissionTemplates(this); ">
                    	<option value="">-- Select Client --</option>
	                    <c:forEach var="report" items="${add_clientList}">
							 	<c:if test="${report.locationId.id == loginOrgLocId}">  
			                    	<option value="${report.id},${report.responderLocationId.id}" ${report.id  == requirement.clientId.id ? 'selected' : ''} >${report.responderLocationId.org.orgName}</option>
			                    </c:if>  
			                    <c:if test="${report.responderLocationId.id == loginOrgLocId}"> 
			                  	 	<option value="${report.id},${report.locationId.id}" ${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option>
			                    </c:if>
						</c:forEach>
                    </select>
                    <i data-target="#addClient" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new" onClick="addClientPopUp();"></i>
                    <div id="addClient" class="modal fade" role="dialog"></div>
                  </div>
                  <div role="dialog" id="addLevel" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Level</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                            <div class="col-xs-12 col-md-12 col-lg-12">
                              <div class="form-group">
                                <label class="col-lg-3">Level Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Description </label>
                                <div class="col-lg-9">
                                  <textarea class="form-control" rows="2"></textarea>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="modal-footer">
                          <button data-dismiss="modal" class="btn btn-primary" type="button" >Save</button>
                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Client Contact <span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control" name="selectContact" data-validation-optional="true" field-name = "Client Contact" id="selectContact">
                      <option value=''>-- Select Contact --</option>
                      <c:forEach var="report" items="${clientContactList}">
			         		<%-- <option value="${report.id}" ${report.id  == requirement.spocId.id ? 'selected' : ''} >${report.spocId.SpocUser.firstName}&nbsp;${report.spocId.SpocUser.lastName}</option> --%>
			         		<option value="${report.id}" ${report.id  == requirement.spocId.id ? 'selected' : ''} >${report.spocName} (${report.ownerName})</option> 
					     </c:forEach>
                    </select>
                    <i data-target="#addContact" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new" id="addContactPlus" style="display: none;" ></i> 
                 </div>
                  
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Bill Rate / Salary</label>
              <div class="col-md-9 pras">
	              <div class="input-group">
	                <div class="input-group-btn selp-custom-min">
	                  <select class="selectpicker" id="clientCurrencyCode" name="clientCurrencyCode">
		                 <option  ${requirement.clientCurrencyCode  == 'INR' ? 'selected' : ''}><a href="#">INR</a></option>
		                 <option  ${requirement.clientCurrencyCode  == 'AUD' ? 'selected' : ''}><a href="#">AUD</a></option>
		                 <option  ${requirement.clientCurrencyCode  == 'USD' ? 'selected' : ''}><a href="#">USD</a></option>
		                 <option  ${requirement.clientCurrencyCode  == 'GBP' ? 'selected' : ''}><a href="#">GBP</a></option>
		                 <option  ${requirement.clientCurrencyCode  == 'SGD' ? 'selected' : ''}><a href="#">SGD</a></option>
		                 <option  ${requirement.clientCurrencyCode  == 'EUR' ? 'selected' : ''}><a href="#">EUR</a></option>
	                </select>
	                
	                </div>
	                <input type="text" class="form-control" id="clientRate" name="clientRate" value="${requirement.clientRate}" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">
	                <div class="input-group-btn selp-custom-min">
	                 <select class="selectpicker ratetype"  id="clientRateType" field-name="Client Rate Type" value="${requirement.clientRateType}" name="clientRateType">
	                    
			                <option value="">Rate Per</option>
		                    <option ${requirement.clientRateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
		                    <option ${requirement.clientRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
		                    <option ${requirement.clientRateType  == 'Biweekly' ? 'selected' : ''}>Bi-weekly</option>
		                    <option ${requirement.clientRateType  == 'SemiMonthly' ? 'selected' : ''}>Semi-Monthly</option>
		                    <option ${requirement.clientRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
		                    <option ${requirement.clientRateType  == 'BiMonthly' ? 'selected' : ''}>Bi-Monthly</option>
		                    <option ${requirement.clientRateType  == 'others' ? 'selected' : ''}>Yearly</option>
	                  </select>
           		</div>
              </div>
              </div>
              <div class="clearfix"></div>
              <div class="clearfix"></div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Submission Form<span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control" id="clientSubmissionForm" name="clientSubmissionForm" field-name = "Submission Form" data-validation-optional="true" >
                       <option value="">-- Select Submission Form --</option>
                       <c:forEach var="report" items="${clientSubmissionTempletesList}">
							 <%-- 	<c:if test="${report.id == loginOrgLocId}">   --%>
			                    	<option value="${report.id}" ${report.id  == requirement.clientSubmissionTemplateId.id  ? 'selected' : ''} >${report.templateName}</option>
			                 <%--    </c:if>   --%>
						</c:forEach>
						</select>
                  </div>
                  
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

       <!-- Internal Details html -->
      <div class="clearfix">
        <div class="col-lg-12">
          <h3>Internal Details</h3>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-12 col-lg-12  myForm">

        <!-- Approver Required Check box html -->
        <div class="clearfix mb">
                <div class="ml">
                
                <div class="pull-left">
                <input type="checkbox" name="cd" id="apprverReq" onClick="approvalType('Default');" checked>
                <label>&nbsp;&nbsp;<strong>Approver required</strong></label>
                </div>
                
                <!-- Radio buttons for Sequential or Parallel function -->
                <div  class="ml pull-left apprverReq" style="display:none;">
                <label class="rad" id="parallel">
                <input type="radio" name="approver"  checked="checked" onClick="approvalType('Parallel');">
                <i class="fa fa-align-center" data-placement="top" data-toggle="tooltip"  title="Parallel"></i>
                </label>
                <label class="rad" id="sequential">
                <input type="radio" name="approver" onClick="approvalType('Sequential');">
                <i class="fa fa-retweet" data-placement="top" data-toggle="tooltip" title="Sequential"></i>
                </label>
                
                 <i data-placement="top" data-toggle="tooltip" class="glyphicon glyphicon-info-sign text-info ml" title="info"></i>
                </div>
                <!-- Radio buttons for Sequential or Parallel function -->
               
                </div>
        </div>
       <!-- Approver Required Check box html ends -->  
         
         
        <!--  <div class="form-group parallel" style="display:none;">
                <label class="col-md-3">Approver<span class="text-danger">*</span></label>
                <div class="col-md-9">
                    <select multiple class="chosen-select form-control">
                      <option disabled>Select Approver</option>
                      <option>Approver-1</option>
                      <option>Approver-2</option>
                      <option>Approver-3</option>
                    </select>
                </div>
         </div> -->
         
         <div class="form-group parallel" style="display:none;">
                <label class="col-md-3">Approver<span class="text-danger">*</span></label>
                <div class="col-md-9">
                    <select multiple class="chosen-select form-control" id="parallelApprover" name="parallelApprover" field-name="Approver" data-validation="required" data-validation-optional="true">
                       <option disabled>Select Approver</option>
                       <c:forEach var="report" items="${approverList}">
		                    <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
		               </c:forEach>
                    </select>
                </div>
         </div>
         
         
         <div class="form-group sequential" id="addLvl" style="display:none;">
                <label class="col-md-3">Approver1<span class="text-danger">*</span></label>
                <div class="col-md-9">   
                    <select   class="chosen-select form-control" id="sequentialApprover1" name="sequentialApprover1" field-name="Approver" data-validation="required" data-validation-optional="true" onchange="getLevel2Approvers();">
                       <option value="">Select Approver</option>
                       <c:forEach var="report" items="${approverList}">
		                    <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
		               </c:forEach>
                    </select>
                    <!-- <span onClick="addLvl();"><i class="fa fa-level-down btn btn-default formic1" data-toggle="tooltip" data-placement="left" title="Add Level"></i></span> -->
                </div>
                
	            <label class="col-md-3 mt">Approver2<span class="text-danger">*</span></label>
	            <div class="col-md-9 mt">
	              		<select   class="chosen-select form-control" id="sequentialApprover2" name="sequentialApprover2" field-name="Approver 2" data-validation="required" data-validation-optional="true">
	                       <option value="">Select Approver</option>
	                       <c:forEach var="report" items="${approverList}">
			                    <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
			               </c:forEach>
	                    </select>
	              </div>
              
              </div>
           
          <div class="form-group">
            <label class="col-md-3">Manager<span class="text-danger">*</span></label>
            <div class="col-md-9">
           
              <select  class="chosen-select form-control"  data-validation = "required" field-name = "Manager" name="manager" id="managerId">
                       <option value="">Select Manager</option>
                       <c:forEach var="report" items="${approverList}">
		                    <option value="${report.id}" ${report.id  == requirement.managerUserId.id ? 'selected' : ''}>${report.firstName}&nbsp;${report.lastName}</option>
		               </c:forEach>   
              </select>
            </div>
          </div>
          <div class="form-group">
             
            <div id="assignedToSavingDiv"></div>
													
            <label class="col-md-3">Assign To <span class="text-danger">*</span></label>
            <div class="col-md-9">
                <div class="form-icon">
                <span class="icon-inside"> <i class="fa fa-search-plus"></i>
                  <input type="text" id="assignedTo" placeholder="Assign Team" data-validation = "required" field-name = "Assign To" class="form-control" data-target="#assignTeam" data-toggle="modal" onclick="getUserList();">
                  </span>
                  <span data-target="#addTeam" data-toggle="modal"> <i class="fa fa-plus btn btn-default formic1" onclick="flushStoreTeam();getTeamOwners();"></i> </span>
                  </div>
                <!-- Modal content Assign To -->
                <div role="dialog" id="assignTeam"   class="modal fade">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Assign To</h4>
                      </div>
                       <div id="userList"></div>
                      
                    </div>
                  </div>
                </div>
                <!-- Assign To modal popup ends -->
              </div>
          </div>
          <div class="form-group">
            <label class="col-md-3">Priority<span class="text-danger">*</span></label>
            <div class="col-md-9">
              <select class="selectpicker form-control" id="priority" name="priority" data-validation = "required" field-name = "priority">
                <option value="">Select Priority</option>
                <option ${requirement.priority  == 'Urgent' ? 'selected' : ''}>Urgent</option>
                <option ${requirement.priority  == 'High' ? 'selected' : ''}>High</option>
                <option ${requirement.priority  == 'Medium' ? 'selected' : ''}>Medium</option>
                <option ${requirement.priority  == 'Low' ? 'selected' : ''}>Low</option>
              </select>
            </div>
          </div>
          <div class="form-group" id="internalSubmissionFrom">
            <label class="col-md-3">Application Form<span class="text-danger">*</span></label>
            <div class="col-md-9">
               
               <select class="chosen-select form-control select-bg" name="applicationFrom" id="applicationFrom"  data-validation="required"  field-name = "Application Form">
                    	<option value="">-- Select Form --</option>
		                <c:forEach var="report" items="${submissionTempletesList}">
					                  <option value="${report.id}" ${report.id  == requirement.submissionTemplateId.id ? 'selected' : ''}>${report.templateName}</option>
						</c:forEach>
               </select>					
          </div>
        </div>
      </div>
    </div>
    <!-- Internal Details html Ends -->
      
      
    </div>
    </div>
    <input type="hidden" value="${requirement.id}" name ="requirementId"  id ="requirementId">
     <div class="form-footer clearfix">
    <div class="col-lg-12">   
      <div class="pull-right">
        <input type="button" value="Submit for Approval" id="submitForApproval" onclick="storeRequirement('submitForApproval');" class="btn btn-primary" style="display:none;">
        <c:if test="${empty requirement.requirementSubmissionStatus || requirement.requirementSubmissionStatus == 'DRAFT' || requirement.requirementSubmissionStatus == 'REJECTED'}">
        	<input type="button" value="Draft" id="draft" onclick="storeRequirement('draft');" class="btn btn-danger ml">
        </c:if>
        <input type="button" value="Save" id="save" onclick="storeRequirement('save');" class="btn btn-primary ml" >    
        <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/requirementList'/>'" class="btn btn-default ml">
      </div>
    </div>
  </div>
    </form:form>
  <!-- Content Area Ends-->
  <div id="addContact" class="modal fade" role="dialog">
  </div>
                    
     		 <!-- Modal content Demo Popup: Need to Update -->
                <div role="dialog" id="addTeam" class="modal fade">
                  <div class="modal-dialog">
                    <form role="form"  id="team_form" name="team_form" method="post" >
                    <div class="modal-content" id="assignTeamDiv">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Add Team</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal custom-form clearfix">
                          <div class="col-xs-12 col-md-12 col-lg-12">
                            <div class="form-group">
                            <label class="col-lg-4">Team Name <span class="text-danger">*</span></label>
                            <div class="col-lg-8">
                              <!-- <input type="text" class="form-control" value=""> -->
                              <input type="text" class="form-control"  placeholder="Team Name" id="name" name="name" data-validation="required validate_Space validate_AlphaNumaric validate_length length2-100" field-name="Team Name" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-4">Team Owner <span class="text-danger">*</span></label>
                            <div class="col-lg-8">
                             <select class="form-control"  name="ownerUserId" id="ownerUserId" placeholder="Team Lead *" data-validation="required validate_Space" field-name="Team Lead"  onchange="getTeamMembers();">
			                    	
			                  </select>
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-4">Team Members <span class="text-danger">*</span></label>
                            <div class="col-lg-8">
                             <select multiple class="chosen-select form-control select-bg" name="memberUserId" id="memberUserId" placeholder="Team Member *" data-validation="required validate_Space" field-name="Team Member">
					                  
		                       </select>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button class="btn btn-primary" type="button" id="add_team" onclick="storeTeam();" >Save</button>
                        <button data-dismiss="modal" class="btn btn-default"   type="button">Cancel</button>
                      </div>
                    </div>
                    </form>
                  </div>
                </div>
                <!-- Modal content Demo Popup: Need to Update ends --> 
  
  </div>
</section>

<script type="text/javascript">
	function storeRequirement(saveType) {
		
		$('#addClient').empty();
		$('#addContact').empty();
		$('#addTeam').empty();
		
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		
          if(saveType=='draft'){
			
			//For internal details in draft
			 $('#managerId').removeAttr('data-validation');
			 $('#managerId').attr("data-validation-optional","true"); 
			 
			 
			 $('#assignedTo').removeAttr('data-validation');
			 $('#assignedTo').attr("data-validation-optional","true");
			 
			 
			 $('#priority').removeAttr('data-validation');
			 $('#priority').attr("data-validation-optional","true");
			
			 
			 $('#applicationFrom').removeAttr('data-validation');
			 $('#applicationFrom').attr("data-validation-optional","true");
			 
			 //for client details in draft
			 
			 $('#addClientId').removeAttr('data-validation');
			 $('#addClientId').attr("data-validation-optional","true");
			 
			 $('#selectContact').removeAttr('data-validation');
			 $('#selectContact').attr("data-validation-optional","true");
			 
			 $('#clientSubmissionForm').removeAttr('data-validation');
			 $('#clientSubmissionForm').attr("data-validation-optional","true");
		}
		
		 var rate = document.getElementById("rate").value; 
		 if(rate != null && rate != ""){
			 $('#rateType').removeAttr('data-validation-optional');
			 $('#rateType').attr("data-validation","required"); 
		 }else{
			 $('#rateType').removeAttr('data-validation');
			 $('#ratePer').attr("data-validation-optional","true"); 
		 }
		 
		 var clientRate = document.getElementById("clientRate").value; 
		 if(clientRate != null && clientRate != ""){
			 $('#clientRateType').removeAttr('data-validation-optional');
			 $('#clientRateType').attr("data-validation","required"); 
		 }else{
			 $('#clientRateType').removeAttr('data-validation');
			 $('#ratePer').attr("data-validation-optional","true"); 
		 }
		 
		   if ($('#requirement_form').validate(false, validationSettings)){   
			
			var frm = $('#requirement_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			//It will escape all the special characters
			var jsonData = encodeURIComponent(con);
			var form = $('#requirement_form')[0];
      		var formData = new FormData(form); 
			  
			var sourceleadUrl = '${sourceleadUrl}';
			$( "#submitForApproval" ).addClass('button').val('Processing..');
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		
			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeRequirement?${_csrf.parameterName}=${_csrf.token}&saveType="+saveType+"&json="+jsonData,
						success : function(result) {
								location.replace(sourceleadUrl+"/requirementList");
						},
					});
			return true;
		   }else{ 
				return false;
		 }   
	} 
	
	$('body').on('blur', '#requirement_form', function() {
		$("#requirement_form").showHelpOnFocus();
		$("#requirement_form").validateOnBlur(false, validationSettings);
	});
</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
 <script>
function storeTeam(){
	
	   var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };
	   if ($('#team_form').validate(false, validationSettings)){ 
			var frm = $('#team_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
		     var jsonData = encodeURIComponent(con);
			 var sourceleadUrl = '${sourceleadUrl}';
			
			 $("#add_team").attr("disabled", "disabled");
			
			$.ajax({
					type : "POST",
					url : sourceleadUrl+"/storeTeam?${_csrf.parameterName}=${_csrf.token}&json="+con,
					success :function(data) { 
						$('#addTeam').modal('hide');
				      }, 
		    });  
			return true; 
	   }else{ 
			return false;
	        } 
		} 
 
		$('body').on('blur', '#team_form', function() {
			$("#team_form").showHelpOnFocus();
			$("#team_form").validateOnBlur(false, validationSettings);
		});
</script>   


<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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

<!-- Country,State,City Script Starts Here -->

<script type="text/javascript">
function getAllCountries(){
	
 setTimeout(function(){ 
	var countryName ;
	
	 if ($('#existingCountry').length)
	 {
		 countryName = document.getElementById("existingCountry").value; 
	 }
	 else
	 {
		 countryName = '';
	 }
	 
	 
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#countryReq').empty();
        	$('#countryReq').append('<option value="">---select Country---</option>');
        	for(var i=0;i<json.length;i++){
            	if(json[i] == countryName ){
            		$('#countryReq').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else if((countryName ==null ||  countryName =='' || countryName =='undefined') && (json[i] == '${loginCountryName}')){
            		
            		$('#countryReq').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#countryReq').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#countryReq').trigger("chosen:updated");
            getStates();
        },
        error: function(e){
        }
    });
 }, 100);
} 
</script>


<script>
function getStates(){
	var countryName = $("#countryReq").val();

	//to set existing state value(Auto populate)
	var stateName = $("#state").val();
	if(stateName == null || stateName == 'undefined' || stateName == ''){
		stateName = document.getElementById("existingState").value;
	}
	//end state Auto populate
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select State---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == stateName){
	            		$('#state').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#state').trigger("chosen:updated");
	            getCities();
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select State---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select State---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	
	var countryName = $("#countryReq").val();
	var stateName = $("#state").val();
	
	//to set existing city value(Auto populate)
	var cityName = $("#city").val();
	if(cityName == null || cityName == 'undefined' || cityName == ''){
		cityName = document.getElementById("existingCity").value;
	}
	//end city Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select City---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == cityName){
	            		$('#city').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select City---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>



<!-- Country,State,City Script Ends Here -->

<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			
		   $("#countryReq").chosen();
	       $("#state").chosen();
	       $("#city").chosen();
	       
	       
	       $("#countryReq").on('change', function (e) {
	    	   getStates();
	       });
	       $("#state").on('change', function (e) {
	    	   getCities();
	       });
		       
	     //Adding Customized Skills to the list
           var select, chosen;
           select = $("#skills");
           select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
           chosen = select.data('chosen');
           chosen.search_field.on('keyup', function(e)
           {
        	   var newSkill = this.value.replace(",","");
               if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
               {
                   var option = $("<option>").val(newSkill).text(newSkill);
                   select.append(option);
                   select.find(option).prop('selected', true);
                   $("#skills").trigger("chosen:updated");
               }
           });
           //Adding Customized Skills to the list
           
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
			})
			
			
			 
            
            
			/* upload file script */
			document.getElementById("uploadBtn").onchange = function () {
			//document.getElementById("uploadFile").value = this.value;
			$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
			};
				
				
</script> 
<!-- Eductions and Skills Autocomplete Scripts End Here -->


<script>
function getTemplateVal(){
    if(document.getElementById('isTemplate').checked) {
        $("#isTemplate").val('Y');
        checkTemplateforDouplicate();
    }else{
        $("#isTemplate").val('N');
        $('#jobTitleDiv').find('p.jquery_form_error_message').remove();

    }
}

</script>

<!-- <script type="text/javascript">
 function disableMonths() {
     $("#minExp").change(function() {
    	 
         if ($(this).val() == '25+'){
             $("#maxExp").attr("disabled", true);
             $("#maxExp").val("disabled", true);
         }else
        	 $("#maxExp").attr("disabled", false); 
     });
 }
 </script> -->

<script>
function setTemplateValues(){
 var sourceleadUrl ='${sourceleadUrl}';
	var requirementId = document.getElementById('template').value;
	if(requirementId != null && requirementId !='' && requirementId != 'undefined'){
		
	
	$.ajax({
	   	type: "POST",
	   	
	    url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId, 	   
		    success: function(data) {
		    	
		    	
		    	if(data != null && data != 'undefined' && data !='' ){ 
		    		
		    		var json = JSON.parse(data);
		    		
		    		if(json.fileName != null && json.fileName != '' && json.fileName !='undefined'){
		    			$("#templateId").val(json.id);
		    		}
		    		
			    	$("#jobTitle").val(json.jobTitle);
			    	$("#jobType").val(json.jobType);
			    	$("#jobId").val(json.jobId);
			    	$("#positions").val(json.positions);
			    	
			    	$("#minExp").val(json.minExp);
			    	$("#maxExp").val(json.maxExp);
			    	
			    	var country = '';
					 var state = '';
					 var city = '';
					
					
						if(json.country != null && json.country !='' && json.country !='undefined'){
							$("#existingCountry").val(json.country);
							$("#countryReq").val(json.country);
						}else{
							$("#existingCountry").val('');
						}
						
						if(json.state != null && json.state !='' && json.state !='undefined'){
							 $("#existingState").val(json.state);
							 $("#state").val(json.state);
							
						}else{
							$("#existingState").val('');
						}
						if(json.city != null && json.city !='' && json.city !='undefined'){
							 $("#existingCity").val(json.city);
							 $("#city").val(json.city);
						} else{
							$("#existingCity").val('');
						}
						
					getAllCountries(); 		
					
					$('#description').val('');
					$('#rate').val('');
					$('#education').val('').trigger('chosen:updated');
					$('#skills').val('').trigger('chosen:updated');
					
					var skills = [];
		            skills = json.skills.split(",");
		            $("#skills").val(skills);
		            $('#skills').trigger("chosen:updated");
		            
		           
		            var educations = [];
		            educations = json.education.split(",");
		            $("#education").val(educations);
		            $('#education').trigger("chosen:updated");
					
					 
			    	
			    	$("#description").val(json.description);
			    	$("#rate").val(json.rate);
			    	$("#currencyCode").val(json.currencyCode);
			    	$("#rateType").val(json.rateType);
			    	
			    	 existedFile = json.fileName;
			    	 $("#uploadFile").val(existedFile);
			    	 $('#uploadFile').trigger("chosen:updated");
		    	}else{
		    		
		    		$("#jobTitle").val('');
			    	$("#jobType").val('');
			    	$("#jobId").val('');
			    	$("#positions").val('');
			    	$("#minExp").val('');
			    	$("#maxExp").val('');
			    	
					$("#countryReq").val('');
					$("#state").val('');
					$("#city").val('');
					
					$("#existingCountry").val('');
					$("#existingState").val('');
					$("#existingCity").val('');
					
		            $("#skills").val('');
		            $("#education").val('');
			    	$("#description").val('');
			    	$("#rate").val('');
			    	$("#currencyCode").val('');
			    	$("#rateType").val('');
			    	existedFile = json.fileName;
			    	 $("#fileName").val(existedFile);
			    	 $('#fileName').trigger("chosen:updated");
		    	}
		    }
     });
	}else{
		 document.getElementById("requirement_form").reset();
		 $("#skills").trigger("chosen:updated");
		 $("#education").trigger("chosen:updated");
		 $("#existingCountry").val('');
		 $("#existingState").val('');
		 $("#existingCity").val('');
		/*  $("#state").trigger("chosen:updated");
		 $("#city").trigger("chosen:updated"); */
		 getAllCountries(); 		
		 $("#rate").val('');
		 $("#description").val('');
		 
	}
}

$(function(){
    var config = {
		  '.chosen-select'           : {},
		  '.chosen-select-deselect'  : {allow_single_deselect:true},
		  '.chosen-select-no-single' : {disable_search_threshold:10},
		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		  '.chosen-select-width'     : {width:"95%"}
		}
    $("#countryReq").chosen();
    $("#state").chosen();
    $("#city").chosen();
    $("#owner").chosen();
    
    $("#countryReq").on('change', function (e) {
 	   getStates();
    });
   
    $("#state").on('change', function (e) {
 	   getCities();
    });
   
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});
</script>

<script>
// script for tooltip
$('[data-toggle="tooltip"]').tooltip();
///////////////////// Back To Top 
$(document).ready(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
});

// Show hide Approver script
$("#apprverReq").removeAttr("checked");
$("#sequential input").removeAttr("checked");
/* 
function selApprover(){
	
  if ($("#apprverReq").prop('checked') ) {
			$(".parallel, #parallel, .apprverReq").show();
			$(".sequential").hide();
			$("#parallel i").addClass("apprverReq-active");
			$("#sequential i").removeClass("apprverReq-active");

} 
else {
	$(".parallel, #parallel, .apprverReq, .sequential").hide();
}
};

$("#sequential").click(function(){
	$(".parallel").hide();
	$(".sequential").show();
	$("#parallel i").removeClass("apprverReq-active");
	$("#sequential i").addClass("apprverReq-active");
});
$("#parallel").click(function(){
	$(".parallel").show();
	$(".sequential").hide();
	$("#parallel i").addClass("apprverReq-active");
	$("#sequential i").removeClass("apprverReq-active");
}); */
function addLvl(){
	$(".lvlTow").toggle();
}

</script> 

<script>
function approvalType(type){
	    if($("#apprverReq").is(':checked')){
			$("#submitForApproval").removeAttr("style");
		    $("#save").attr("style","display:none"); 
		    
		    $(".parallel, #parallel, .apprverReq").show();
			$(".sequential").hide();
			$("#parallel i").addClass("apprverReq-active");
			$("#sequential i").removeClass("apprverReq-active");
			
			
			if(type == "Default" || type == "Parallel"){
				
				$("#parallel i").show();
			    $("#sequential i").show();
				 
			    $(".parallel").show();
			    $("#parallel i").addClass("apprverReq-active");
			    
				$(".sequential").hide();
				$("#sequential i").removeClass("apprverReq-active"); 
				
				$("#sequentialApproverDiv").hide();
				
				
				$('#sequentialApprover1').attr("data-validation-optional","true"); 
				$('#sequentialApprover2').attr("data-validation-optional","true"); 
				$('#parallelApprover').removeAttr('data-validation-optional');
				
				$('#sequentialApprover1').val('').trigger('chosen:updated');
				$('#sequentialApprover2').val('').trigger('chosen:updated');
				  
				
		  }
		  if(type == "Sequential"){
			    $(".parallel").hide();
			    $("#parallel i").removeClass("apprverReq-active");
			    
				$(".sequential").show();
				$("#sequential i").addClass("apprverReq-active");
				
				$('#parallelApprover').attr("data-validation-optional","true"); 
				
				$('#sequentialApprover1').removeAttr('data-validation-optional');
				$('#sequentialApprover2').removeAttr('data-validation-optional');
				
				$('#parallelApprover').val('').trigger('chosen:updated');
		  }
		  
		}
		else{
		    
			$("#save").removeAttr("style");
		    $("#submitForApproval").attr("style","display:none"); 
		    
		    $(".parallel").hide();
		    $("#parallel i").hide();
		    
		    $(".sequential").hide();
			$("#sequential i").hide();
			
			$('#sequentialApprover1').attr("data-validation-optional","true"); 
			$('#sequentialApprover2').attr("data-validation-optional","true"); 
			$('#parallelApprover').attr("data-validation-optional","true"); 
			
			
		}
	  
}
</script>



<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script>
function getUserList(teamType){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#userList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getUserList?${_csrf.parameterName}=${_csrf.token}&teamType="+teamType,    
            success :function(data) {
            	
            	getTeamsList(teamType);
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#userList").removeAttr("style");
	                	    var result = '';
	                	    result = result+"<input type='hidden' name='assignedTeamId' id='assignedTeamId' value='"+teamType+"'/>";
	                	    result = result +'<div class="modal-body clearfix">';
	                		result = result +'<div class="form-horizontal custom-form">';
	                		result = result +'     <div class="row">';
	                		result = result +'       <div class="col-xs-12 col-md-8 col-lg-8 hidden">';
	                		result = result +'         <div class="input-group">';
	                		result = result +'           <input type="search" class="form-control" value="">';
	                		result = result +'           <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>';
	                		result = result +'       </div>';
	                		result = result +'       <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100">';
	                		result = result +'          <select id="rolesDiv" onchange="filterTeamMembers();" class="selectpicker form-control" style="display:block !important">';
	                		result = result +'          </select>';
	                		result = result +'     </div>';
	                		result = result +'     </div>';
	                		result = result +'     </div>';
	                		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter">'; 
	                		result = result +'       <table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
	                		result = result +'           <thead>';
	                		result = result +'           <tr>';
	                		result = result +'             <th><input type="checkbox" id="checkAll" onclick="toggle(this);"></th>';
	                		result = result +'             <th>Name</th>';
	                		result = result +'             <th>Email</th>';
	                		result = result +'             <th>Role</th>';
	                		result = result +'             <th>Active Jobs</th>';
	                		result = result +'           </tr>';
	                		result = result +'           </thead>';
		                    result = result +"<tbody>"; 
		                    var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 result = result +'<tr>';
		                    	 var localCheckedStatus = "";
		                    	 $(".defaultCheckBox").each(function() { 
		         							var localValue = this.value;
		         							if(localValue == opt.id){
		         								localCheckedStatus = "checked";
		         							}  
		                    	 });
		                    	 result = result +'             <td><input id="'+opt.id+'" value="'+opt.id+'"  type="checkbox" name="team" '+localCheckedStatus+' class="myCheckbox" ></td>';
		                    	 result = result +'             <td>'+opt.firstName+' '+opt.lastName+'</td>';
		                    	 result = result +'             <td>'+opt.email+'</td>';
		                    	 result = result +'             <td>'+opt.userType+'</td>';
		                    	 result = result +'             <td>'+opt.assignJobCount+'</td>';
		                    	 result = result +'           </tr>';
		                    	
	                     }
		                    
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	 /*result = result +'       <ul class="pagination pull-right pagination-sm" style="margin:0;">';
	                 	 result = result +'         <li><a href="#">1</a></li>';
	                 	 result = result +'         <li class="active"><a href="#">2</a></li>';
	                 	 result = result +'         <li><a href="#">3</a></li>';
	                 	 result = result +'         <li><a href="#">4</a></li>';
	                 	 result = result +'         <li><a href="#">5</a></li>';
	                 	 result = result +'       </ul>'; */
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="assignMembers();">Assign</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>';
	                 	 result = result +'   </div>'; 
	                 	 $("#userList").empty(); 
	                     $("#userList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
                	 $("#userList").removeAttr("style");
  	    	         var result ="";
		    	    
  	    	       result = result +'<div class="modal-body clearfix">';
 	    	        result = result +'<div class="form-horizontal custom-form">';
	           		result = result +'     <div class="row">';
	           		result = result +'       <div class="col-xs-12 col-md-8 col-lg-8 hidden">';
	           		result = result +'         <div class="input-group">';
	           		result = result +'           <input type="search" class="form-control" value="">';
	           		result = result +'           <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>';
	           		result = result +'       </div>';
	           		result = result +'       <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100">';
	           		result = result +'          <select id="rolesDiv" onchange="filterTeamMembers();" class="selectpicker form-control" style="display:block !important">';
	           		result = result +'          </select>';
	           		result = result +'     </div>';
	           		result = result +'     </div>';
	           		result = result +'     </div>';
	           		
	           		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter">'; 
	        		result = result +'       <table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
	        		result = result +'           <thead>';
	        		result = result +'           <tr>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;"><input type="checkbox"></th>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Name</th>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Email</th>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Role</th>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Active Jobs</th>';
	        		result = result +'           </tr>';
	        		result = result +'           </thead>';
	        		result = result +'           </table>';
	        		result = result +'     </div>';
	        		result = result +'     </div>';
					$("#userList").empty();
				    $("#userList").append(result); 
				    pagination();  
                }
          },
            
       }); 
  } 
</script>

<script>
function getTeamsList(teamType){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getTeamsList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
            	var json = JSON.parse(data);
				var output = "";
				output = output +'          <option selected disabled>Select Role</option>';
				
				if(teamType == 'All'){
					output = output +'           <option value="All" selected>All</option>';
				}else{
					output = output +'           <option value="All">All</option>';
				}
				if(teamType == 'Admin'){
					output = output +'           <option value="Admin" selected>Admin</option>';
				}else{
					output = output +'           <option value="Admin">Admin</option>';
				}
				if(teamType == 'Account Manager'){
					output = output +'           <option value="Account Manager" selected>Account Manager</option>';
				}else{
					output = output +'           <option value="Account Manager">Account Manager</option>';
				}if(teamType == 'Recruiter'){
					output = output +'           <option value="Recruiter" selected>Recruiter</option>';
				}else{
					output = output +'           <option value="Recruiter">Recruiter</option>';
				} if(teamType == 'HR Manager'){
					output = output +'           <option value="HR Manager" selected>HR Manager</option>';
				}else{
					output = output +'           <option value="HR Manager">HR Manager</option>';
				} 
               if(teamType == 'Project Manager'){
				output = output +'           <option value="Project Manager" selected>Project Manager</option>';
			  }else{
				output = output +'           <option value="Project Manager">Project Manager</option>';
			  } 
				for (var i = 0; i < json.length; i++) {
					var opt = json[i];
					if(teamType == opt.id){
						output = output + "<option value='"+opt.id+"' selected>"+ opt.name + "</option>";
					}else{
						output = output + "<option value='"+opt.id+"'>"+ opt.name + "</option>";	
					}
				}
				$("#rolesDiv").empty();
			    $("#rolesDiv").append(output);
            	
            },
      }); 
 } 
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to requirementList.
     1.usage     : used in candidateList() function.
--%>

<script>
function pagination(){
    $("#example").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>

<script>
function assignMembers() {
	var result = "";
	var oTable = $('#example').dataTable();
	var rowcollection =  oTable.$(".myCheckbox:checked", {"page": "all"});
	var count = 1;
	rowcollection.each(function(index,elem){
	    var checkbox_value = $(elem).val();
	    result = result+ "<input type='hidden' class='defaultCheckBox' name='assignUserId"+count+"' id='assignUserId"+count+"' value='"+checkbox_value+"' />";
	    count++;
	});
	result = result+"<input type='hidden' name='assignedToMaxCount' id='assignedToMaxCount' value='"+count+"'/>";
	$("#assignedToSavingDiv").empty();
	$("#assignedToSavingDiv").append(result);
	assignedToValidation();
}
</script>

<script type="text/javascript">
	function assignedToValidation() {
		var count = $('#assignedToMaxCount').val();
		if(count != null && count != 'undefined' && count!= ''){
				 $('#assignedTo').attr("data-validation-optional","true"); 
		} 
	}
</script>	


<script type="text/javascript">
	function flushStoreTeam() { 
		document.getElementById("team_form").reset();
		$('#assignTeamDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("add_team").removeAttribute("disabled");
	}
</script>

<script type="text/javascript">
function getTeamOwners(){ 
	 var sourceleadUrl ='${sourceleadUrl}';
	 var teamMemberUserId = document.getElementById("memberUserId").value;
     $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllUsersByOrgLocationCodeExceptTeamOwner?${_csrf.parameterName}=${_csrf.token}&teamOwnerUserId="+teamMemberUserId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Team Owners-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#ownerUserId").empty();
			        $("#ownerUserId").append(output);
			        $("#ownerUserId").trigger("chosen:updated");
			        getTeamMembers(); 
		        },
		        error: function(e){
		            
		        }
		    });  
} 
</script>


<script type="text/javascript">
function getTeamMembers(){ 
	 var sourceleadUrl ='${sourceleadUrl}';
	 var teamOwnerUserId = document.getElementById("ownerUserId").value;
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllUsersByOrgLocationCodeExceptTeamOwner?${_csrf.parameterName}=${_csrf.token}&teamOwnerUserId="+teamOwnerUserId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option disabled >-Select Team Members-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#memberUserId").empty();
			        $("#memberUserId").append(output);
			        $("#memberUserId").trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
} 
</script>

<script>
function getIsClientVal(){
var existingValue = document.getElementById('isClientJob').value;

	if (document.getElementById('isClientJob').checked){
		 $("#isClientJob").val('Y');	
		 existingValue = document.getElementById('isClientJob').value;
	}else {
		 $("#isClientJob").val('N');
		 existingValue = document.getElementById('isClientJob').value;
		 
    // Adding the Internal  Submission Form  as we are Unselecting the Client
		 document.getElementById("internalSubmissionFrom").style.display = '';
		 $("#applicationFrom").attr("data-validation", "required");
		 
	}
	 if ((document.getElementById('isClientJob').checked) || (existingValue != null && existingValue != '' && existingValue !='undefined' && existingValue == 'Y') ) {
		 document.getElementById("clientDetails").style.display="";
		 $("#isClientJob").val('Y');
		 
		 
		//to remove the  Internal  Submission Form  as we are selecting the Client 
		 document.getElementById("internalSubmissionFrom").style.display = 'none';
		 $('#applicationFrom').attr("data-validation-optional","true"); 
		
		 
         $('#addClientId').removeAttr('data-validation-optional');
	     $('#addClientId').attr("data-validation","required"); 
	   
	     $('#selectContact').removeAttr('data-validation-optional');
	     $('#selectContact').attr("data-validation","required"); 
	   
	     $('#clientSubmissionForm').removeAttr('data-validation-optional');
	     $('#clientSubmissionForm').attr("data-validation","required"); 
	 } else {
		 document.getElementById("clientDetails").style.display="none";
		 $("#isClientJob").val('N');
		 
		 $('#addClientId').removeAttr('data-validation');
		 $('#addClientId').attr("data-validation-optional","true"); 
		 
		 $('#selectContact').removeAttr('data-validation');
		 $('#selectContact').attr("data-validation-optional","true"); 
		 
		 $('#clientSubmissionForm').removeAttr('data-validation');
		 $('#clientSubmissionForm').attr("data-validation-optional","true"); 
		 
		 $('#applicationFrom').removeAttr("data-validation-optional"); 
	 }
}

</script>

<script type="text/javascript">
function filterTeamMembers(){
	var teamType = $("#rolesDiv").val();
	getUserList(teamType);
}
</script>


<script type="text/javascript">
function getLevel2Approvers(currentApproverId){
	var currentApproverId = document.getElementById("sequentialApprover1").value;
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getApproverTwoByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+currentApproverId,  
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Approver-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#sequentialApprover2").empty();
			        $("#sequentialApprover2").append(output);
			        $('#sequentialApprover2').trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
	 }
} 
</script> 


<!-- Job Id validation script starts here -->

<script type="text/javascript">
 function validateJobId() {
	
	 var jobId = document.getElementById("jobId").value; 
	 $('#jobIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(jobId != null && jobId != ""){
		
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateJobId?${_csrf.parameterName}=${_csrf.token}&jobId="+jobId, 
		        success: function(data) {
		        	
		        	if(data == '"duplicate"'){
		        		  $('#jobIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#jobId").after("<p class='jquery_form_error_message'>"+jobId+" is Already Existed</span>");
						  document.getElementById("jobId").setAttribute('record-exist','yes');
						  document.getElementById("jobId").setAttribute('record-exist-errorMsg',jobId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#jobId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#jobIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#requirement_form" ).addClass('button').val('Save');
                         $("#requirement_form").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>     
 
 <script>
function toggle(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAll)').click(function() {
    if(!this.checked) {
        $('#checkAll').removeAttr('checked');
    }
});
}
</script>
 
 <!-- Job Id validation script ends here -->
 
<script>
//Function to validate if maximum exp value is more the minimum exp value
function minMaxExperience(){
    var maxExp = ($('#maxExp').val());
    var minExp = ($('#minExp').val());
    
    if (parseFloat(maxExp) < parseFloat(minExp)){
        $('#expId').find('p.jquery_form_error_message').remove(); 
		$('input#expValidation').attr('class','error signin_fild');
		$("input#expValidation").after("<p class='jquery_form_error_message'>Max Experience is greater than Min Experience</p>");
		$('input#expValidation').attr('record-exist','yes');
		$('input#expValidation').attr('record-exist-errorMsg','Max Experience is greater than Min Experience'); 
    }  else{
    	$('#expId').find('p.jquery_form_error_message').remove(); 
    	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#expId').find('p.jquery_form_error_message').remove();
    }
}

</script>

<script type="text/javascript">
function checkTemplateforDouplicate(){
var templateName = document.getElementById("jobTitle").value; 
$('#jobTitleDiv').find('p.jquery_form_error_message').remove();
var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/validatetemplateName?${_csrf.parameterName}=${_csrf.token}&templateName="+templateName, 
	        success: function(data) {
	        	if(data == "true"){
	        		  $('#jobTitleDiv').find('p.jquery_form_error_message').remove();
					  $("input#jobTitle").after("<p class='jquery_form_error_message'><span>Template Already Exists</span>");
					  document.getElementById("jobTitle").setAttribute('record-exist','yes');
					  document.getElementById("jobTitle").setAttribute('record-exist-errorMsg','Template Already Exists'); 
	        	}
	        	else{
	        		 $('input#jobTitle').removeAttr( "record-exist record-exist-errormsg");
	        		 $('#jobTitleDiv').find('p.jquery_form_error_message').remove(); 
	        		 $("#requirement_form" ).addClass('button').val('Save');
                    $("#requirement_form").removeAttr("disabled");
	        	}
	        },
	    });
}
</script>

<!-- <script type="text/javascript">
function removeEditButton() {
	var reqId = document.getElementById("requirementId").value;
	if(reqId != null && reqId != 0){
		 var draftId = document.getElementById("draft");
		 draftId.parentNode.removeChild(draftId);
		    return false;
	}
}
</script> -->

<script>
function changeCurrency(requirement){
	var cntrynme = document.getElementById("countryReq").value;

	 if (cntrynme == "United States") {
			    $("#currencyCode").val("USD");
			    $("#currencyCode").change();
			  }else if(cntrynme == "India"){
				  $("#currencyCode").val("INR");
				    $("#currencyCode").change();
			  }else if(cntrynme == "Australia"){
				  $("#currencyCode").val("AUD");
				    $("#currencyCode").change();
			  }else if(cntrynme == "United Kingdom"){
				  $("#currencyCode").val("GBP");
				    $("#currencyCode").change();
			  }else if(cntrynme == "Singapore"){
				  $("#currencyCode").val("SGD");
				    $("#currencyCode").change();
			  }else if(cntrynme == "Germany"){
				  $("#currencyCode").val("EUR");
				    $("#currencyCode").change();
			  }else {
				  $("#currencyCode").val("INR");
				  $("#currencyCode").change();
		}
}
</script>


<jsp:include page="../masterFooter.jsp" />