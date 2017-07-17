<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<jsp:include page="requirementDetails.jsp"></jsp:include>
<jsp:include page="requirementClientDetails.jsp"></jsp:include>
<jsp:include page="requirementInternalDetails.jsp"></jsp:include>

<script src="resources/js/editor.js"></script>
<link href="resources/css/editor.css" type="text/css" rel="stylesheet"/>
		<script>
			$(document).ready(function() {
				$("#txtEditor").Editor();
				    /* $("#closedDate").datepicker({
			                minDate: 0
			            });
			        */
			  
			});
       </script> 

<style type="text/css">
.modal-bodyCustom {
margin-bottom: 15px;
padding-bottom: 15px;
min-height: 150px;
}
.popupFilter #example_filter {
top:-55px !important;
}
.dataTable thead th {
background: #375d81; color:#fff; height: 34px; padding-top: 5px; padding-bottom: 5px; text-align:center;
}
.dataTable thead tr th {
vertical-align: middle;
}
.dataTable thead tr td {
text-align: center;
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
dataTables_wrapper .dataTables_filter input {
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
bottom: -14px !important;
left:-20px !important;
}
.pras.req-title p{
bottom:-2px;
}
.payrate_p p.jquery_form_error_message{
 bottom: -13px;padding-left: 0 !important;
}
.form-control {
padding: 6px 12px !important; height: 34px !important;
}
.bootstrap-select {
width: 100% !important;
}
.icon-inside .form-control {
    padding: 6px 12px 6px 30px !important;
}
.chosen-container-multi .chosen-choices li.search-field input[type="text"] { width: 100% !important;}
.refresh-icon {
right: 15px;
}

</style>


<script>
$(document).ready(function() {
	initLocationAuto('ReqLocation','ReqCountry','ReqState','ReqCity','ReqZipCode','','','');
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
	        	 result = result+ "<input type='hidden' class='defaultAssignCheckBox' name='defaultAssignUserId"+count+"' id='defaultAssignUserId"+count+"' value='"+requirementAssignedToIds[i]+"' />";
	        	 count++;
	        }
			result = result+"<input type='hidden' name='defaultAssignedToMaxCount' id='defaultAssignedToMaxCount' value='"+count+"'/>";
			$("#defaultAssignedToSavingDiv").empty();
			$("#defaultAssignedToSavingDiv").append(result); 
			assignedToValidation();
		}	
	} 
});
</script>


<script type="text/javascript">
		window.onload = function () {
			getNoticePeriodList();
			var jobType = '${requirement.jobType}';
			if(jobType !=null && jobType != "" && jobType != "undefined" && jobType == 'Other'){
				  document.getElementById("jobTypeDiv").style.display="block";
				  $("#jobOtherType").attr("data-validation-optional", "true");
				  $("#jobOtherType").attr("data-validation", "validate_Space validate_length length2-250");
			}else{
				document.getElementById("jobTypeDiv").style.display="none";
				  $("#jobOtherType").attr("data-validation-optional", "true");
				  $("#jobTypeDiv").find('p.jquery_form_error_message').remove();
				  $("#jobOtherType").val('');
			}
			
			  if(jobType == 'C2C' || jobType == 'C2H' || jobType == 'Contract'){
					 
					
					 document.getElementById("projectDurationYearsDiv").style.display="block";
					 document.getElementById("projectDurationMonthsDiv").style.display="block";
					 
					 
				  
				}else{
					document.getElementById("projectDurationYearsDiv").style.display="none";
					document.getElementById("projectDurationMonthsDiv").style.display="none";
					
				}
			
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
			var requirementStatus = '${requirement.requirementSubmissionStatus}';
			if(requirementStatus == "OPEN" ||  requirementStatus == "ONHOLD"){
				$(function() {
				    enable_cb();
				    $("#apprverReq").click(enable_cb);
				    $("#parallelapprovers").click(enable_cb);
				    $("#seqentialApprovers").click(enable_cb);
				    /* $('#sequentialApprover1').click(enable_cb);
					$('#sequentialApprover2').click(enable_cb); */
				});
				function enable_cb() {
					$("#parallelApprover").prop('disabled', true).trigger('chosen:updated');
				    $("#parallelApprover").off('click');
				    
					$('#apprverReq').prop("disabled", !this.checked);
					$('#parallelapprovers').prop("disabled", !this.checked);
					$('#seqentialApprovers').prop("disabled", !this.checked);
					/* $('#sequentialApprover1').prop("disabled", !this.checked); 
					$('#sequentialApprover2').prop("disabled", !this.checked); 
					$('#sequentialApprover1').attr("data-validation-optional","true"); 
					$('#sequentialApprover2').attr("data-validation-optional","true");  */
					$('#seqentialApprovers').attr("data-validation-optional","true");
					$('#parallelApprover').attr("data-validation-optional","true"); 
					
				}
				
			}else{
				$('#apprverReq').removeAttr("disabled");
				$('#parallelapprovers').removeAttr("disabled")
				$('#seqentialApprovers').removeAttr("disabled")
				$('#sequentialApprover1').removeAttr("disabled")
				$('#sequentialApprover2').removeAttr("disabled")
				
			}
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
		    	    /*$("#sequentialApprover2").val(str_array2).trigger("chosen:updated");  */
		    	    
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
		    				        		if(str_array2 == opt.id){
		    				        			output = output + "<option value='"+opt.id+"' selected>"+opt.firstName+" "+opt.lastName+"</option>";
		    				        		}else{
		    				        			output = output + "<option value='"+opt.id+"'>"+opt.firstName+" "+opt.lastName+"</option>";
		    				        		}
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
     <c:if test="${empty requirementId}"><h2>Add Job</h2></c:if>
      <c:if test="${!empty requirementId}"><h2>Update Job</h2></c:if>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/requirementList'/>">Jobs List  </a>&raquo; Add Job </div>
  </section>
  <div class="content-area content-new clearfix">
  <div id="loading"></div>
   <form:form id="requirement_form" name="requirement_form"  enctype='multipart/form-data'>
    <div class="form-horizontal req-dtls myForm row">
      <div class="clearfix">
        <div class="col-lg-12">
          <h3>Job Details</h3>
        </div>
	       <div class="col-md-12 clearfix">
        	  <div class="col-md-12">
	          <div style="margin:0 auto" class="input-group col-md-6">
	            <label class="input-group-btn">
	            	<span class="btn btn-primary">Select Template
	                </span>
	            </label>
                <select class="form-control chosen-select" id="template"  onChange="setTemplateValues();">
                   <option value=""  selected>--- Select template ---</option>
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

          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
           <div class="form-group pras req-title" id="jobTitleDiv">
	         <label class="col-md-3">Title<span class="text-danger paddingLeft">*</span></label>
	        <div class="col-md-9 validLabel">
	             <input type="text" id="jobTitle" name="jobTitle" value="${fn:escapeXml(requirement.jobTitle)}"  field-name="Job Title"  data-validation="required validate_Space validate_length length1-250"   value="" class="form-control" onblur="getTemplateVal();">
	         </div>
	       </div>

		<div class="form-group">
         <label class="col-md-3">Employment Type</label>
         <div class="col-md-9">
             <select class="selectpicker" id="jobType" name="jobType" onChange="getJobType(this);">
	                 <option value="" disabled selected>--- Select Employment Type ---</option>
	                 <option  ${requirement.jobType  == 'C2C' ? 'selected' : ''}>C2C</option>
	                 <option  ${requirement.jobType  == 'W-2' ? 'selected' : ''}>W-2</option>
	                 <option  ${requirement.jobType  == 'C2H' ? 'selected' : ''}>C2H</option>
	                 <option  ${requirement.jobType  == 'Contract' ? 'selected' : ''}>Contract</option>
	                 <option  ${requirement.jobType  == 'Part Time' ? 'selected' : ''}>Part Time</option>
	                 <option  ${requirement.jobType  == 'Full Time' ? 'selected' : ''}>Full Time</option>
	                 <option  ${requirement.jobType  == 'Other' ? 'selected' : ''}>Other</option>
	             </select>
         </div>
       </div>
       
       
       <div class="form-group" id="jobIdDiv">
            <label class="col-md-3">Id</label>
            <div class="col-md-9">
			 <input type="text" class="form-control" id="jobId" name="jobId" value="${requirement.jobId}" field-name="JobId" data-validation="validate_Space validate_Alphanumeric validate_length length2-100" data-validation-optional="true" onkeyup="validateJobId();" value=""> 
				<input type="hidden" name="requirementId" id="requirementId" value="${requirement.id}"/>
            </div>
          </div>
       
       
       <div class="form-group jobTypeDiv" id="jobTypeDiv" style="display:none">
            <label class="col-md-3">Other</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="jobOtherType" name="jobOtherType" value="${requirement.otherJobType}"  field-name="Job Type" value="">
            </div>
          </div>
          
          <div class="form-group" id="projectDurationYearsDiv" style="display:none">
            <label class="col-md-3">Project Duration</label>
            <div class="col-md-9">
              <div class="row">
	                <div class="col-md-6 dropdown-select">
						<select class="form-control" id="projectDurationYears" name="projectDurationYears">
							<option value="">--Select Years--</option>
	                 <option  ${requirement.projectDurationYears  == '0' ? 'selected' : ''}>0</option>
	                 <option  ${requirement.projectDurationYears  == '1' ? 'selected' : ''}>1</option>
	                 <option  ${requirement.projectDurationYears  == '2' ? 'selected' : ''}>2</option>
	                 <option  ${requirement.projectDurationYears  == '3' ? 'selected' : ''}>3</option>
	                 <option  ${requirement.projectDurationYears  == '4' ? 'selected' : ''}>4</option>
	                 <option  ${requirement.projectDurationYears  == '5' ? 'selected' : ''}>5</option>
	                 <option  ${requirement.projectDurationYears  == '6' ? 'selected' : ''}>6</option>
	                 <option  ${requirement.projectDurationYears  == '7' ? 'selected' : ''}>7</option>
	                 <option  ${requirement.projectDurationYears  == '8' ? 'selected' : ''}>8</option>
	                 <option  ${requirement.projectDurationYears  == '9' ? 'selected' : ''}>9</option>
	                 <option  ${requirement.projectDurationYears  == '10' ? 'selected' : ''}>10</option>
	                 <option  ${requirement.projectDurationYears  == '11' ? 'selected' : ''}>11</option>
	                 <option  ${requirement.projectDurationYears  == '12' ? 'selected' : ''}>12</option>
	                 <option  ${requirement.projectDurationYears  == '13' ? 'selected' : ''}>13</option>
	                 <option  ${requirement.projectDurationYears  == '14' ? 'selected' : ''}>14</option>
	                 <option  ${requirement.projectDurationYears  == '15' ? 'selected' : ''}>15</option>
	                 <option  ${requirement.projectDurationYears  == '16' ? 'selected' : ''}>16</option>
	                 <option  ${requirement.projectDurationYears  == '17' ? 'selected' : ''}>17</option>
	                 <option  ${requirement.projectDurationYears  == '18' ? 'selected' : ''}>18</option>
	                 <option  ${requirement.projectDurationYears  == '19' ? 'selected' : ''}>19</option>
	                 <option  ${requirement.projectDurationYears  == '20' ? 'selected' : ''}>20</option>
	                 <option  ${requirement.projectDurationYears  == '21' ? 'selected' : ''}>21</option>
	                 <option  ${requirement.projectDurationYears  == '22' ? 'selected' : ''}>22</option>
	                 <option  ${requirement.projectDurationYears  == '23' ? 'selected' : ''}>23</option>
	                 <option  ${requirement.projectDurationYears  == '24' ? 'selected' : ''}>24</option>
	                 <option  ${requirement.projectDurationYears  == '25' ? 'selected' : ''}>25</option>
	                 <option  ${requirement.projectDurationYears  == '26' ? 'selected' : ''}>26</option>
	                 <option  ${requirement.projectDurationYears  == '27' ? 'selected' : ''}>27</option>
	                 <option  ${requirement.projectDurationYears  == '28' ? 'selected' : ''}>28</option>
	                 <option  ${requirement.projectDurationYears  == '29' ? 'selected' : ''}>29</option>
	                 <option  ${requirement.projectDurationYears  == '30' ? 'selected' : ''}>30</option>
	                   
						</select>	                 
	                </div>
	                <div class="col-md-6 dropdown-select" id="projectDurationMonthsDiv" style="display:none">
	                <select class="form-control" id="projectDurationMonths" name="projectDurationMonths">
							<option value="">--Select Months--</option>
							 <option  ${requirement.projectDurationMonths  == '0' ? 'selected' : ''}>0</option>
							 <option  ${requirement.projectDurationMonths  == '1' ? 'selected' : ''}>1</option>
							 <option  ${requirement.projectDurationMonths  == '2' ? 'selected' : ''}>2</option>
							 <option  ${requirement.projectDurationMonths  == '3' ? 'selected' : ''}>3</option>
							 <option  ${requirement.projectDurationMonths  == '4' ? 'selected' : ''}>4</option>
							 <option  ${requirement.projectDurationMonths  == '5' ? 'selected' : ''}>5</option>
							 <option  ${requirement.projectDurationMonths  == '6' ? 'selected' : ''}>6</option>
							 <option  ${requirement.projectDurationMonths  == '7' ? 'selected' : ''}>7</option>
							 <option  ${requirement.projectDurationMonths  == '8' ? 'selected' : ''}>8</option>
							 <option  ${requirement.projectDurationMonths  == '9' ? 'selected' : ''}>9</option>
							 <option  ${requirement.projectDurationMonths  == '10' ? 'selected' : ''}>10</option>
							 <option  ${requirement.projectDurationMonths  == '11' ? 'selected' : ''}>11</option>
							 
						</select>
	                </div>
              </div>
              <!-- <div class="row" id="projectduration">
                  <input type="hidden" name="expValidation" id="projectDuration">
              </div>  -->
            </div>
          </div>
          
		          
          
            <!--  <div class="form-group">
            <label class="col-md-3">Closed Date</label>
            <div class="col-md-9">
             <div class="col-lg-6 positionRelative" id="closedDateDiv">
			<input type="text" class="form-control" value="" id="closedDate" name="closedDate" field-name="Closed Date"  readonly="readonly"> <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
			</div>
            </div>
          </div> -->
            
          <div class="form-group">
            <label class="col-md-3">No. of Positions</label>
            <div class="col-md-9">
               <input type="text" class="form-control" id="positions" name="positions" value="${requirement.positions}"  field-name="Positions" data-validation="validate_Space validate_length length1-5 validate_int" data-validation-optional="true"  value="">
            </div>
          </div>
          
          <%-- <div class="form-group">
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
            </div> --%>
            
              <div class="form-group">
              <label class="col-md-3">Education</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="education" data-placeholder="Select Education Options" id="education" data-validation-optional="true">
               <c:forEach var="education" items="${educationsList}">
               		<c:set var = "NUM" value = "${0}"/>
               		<c:forEach var="existedEducation" items="${requirement.reqEducationListString}">
					    <c:if test="${existedEducation eq education}">
					    	<c:set var = "NUM" value = "${1}"/>
					       <option  selected value="${education}">${education}</option>
					    </c:if>
					</c:forEach>
					<c:if test="${NUM == 0}">
						<option  value="${education}">${education}</option>
					</c:if>
                </c:forEach>
                </select>
              </div>
            </div>
            
         <div class="form-group pras req-title" >
            <label class="col-md-3">Experience</label>
            <div class="col-md-9">
              <div class="row">
	                <div class="col-md-6">
	                 <input type="text" class="form-control" placeholder="MinExp" id="minExp" name="minExp" value="${requirement.minExp}"  field-name="MinExp" data-validation="validate_Space validate_float validate_length length1-5" data-validation-optional="true" onblur="minMaxExperience();" value="">
	                </div>
	                <div class="col-md-6" >
	                <input type="text" class="form-control" id="maxExp" name="maxExp" placeholder="MaxExp" value="${requirement.maxExp}"  field-name="MaxExp" data-validation="validate_Space validate_float validate_length length1-5" data-validation-optional="true" onblur="minMaxExperience();" value="">
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
				<label class="col-md-3">Industry</label>
				<div class="col-md-9">
				<select  class="chosen-select form-control" name="industryName" id="industryName" data-placeholder="--Select Industry--"  field-name="Industry" data-validation-optional="true">
				<option value="">--Select Industry--</option>
				<%-- <c:forEach var="industry" items="${industryList}">
	                     <option value="${industry.name}">${industry.name}</option>
                </c:forEach>  --%>
                
                  <c:forEach var="industry" items="${industryList}">
               		<c:set var = "NUM" value = "${0}"/>
               		<c:forEach var="existedIndustry" items="${requirement.industrySavedName}">
					    <c:if test="${existedIndustry eq industry}">
					    	<c:set var = "NUM" value = "${1}"/>
					       <option  selected value="${industry}">${industry}</option>
					    </c:if>
					</c:forEach>
					<c:if test="${NUM == 0}">
						<option  value="${industry}">${industry}</option>
					</c:if>
                </c:forEach>
                
                 </select>
                 <%-- <input type="hidden" id="existingState" value="${requirement.state}" > --%>
				</div>
		 </div>
          
          <div class="form-group pras" >
            <label class="col-md-3">Min / Max Rate</label>
            <div class="col-md-9">
             <div class="input-group payrate_p">
                <div class="input-group-btn selp-custom-min selp-customMin">
                  
                  <select class="selectpicker" id="currencyCode" name="currencyCode" >
               
                <c:if test="${empty requirement.id}">
	                 <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
	                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
	                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                 </c:if>
                 
                 <c:if test="${! empty requirement.id}">
	                 <option ${requirement.currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
	                 <option ${requirement.currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
	                 <option ${requirement.currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
	                 <option ${requirement.currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
	                 <option ${requirement.currencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
	                 <option ${requirement.currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
                 </c:if>
                </select>
                </div>
                <div class="pull-left input_minmax">
                <input type="text" class="form-control input-sm" id="rate" name="rate" field-name="Min Rate" placeholder="Min Rate" value="${requirement.rate}" data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true"  value="" onblur="minMaxRate();">
                <input type="text" class="form-control input-sm" id="maxRate" name="maxRate" field-name="Max Rate" placeholder="Max Rate" value="${requirement.maxRate}" data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true"  value="" onblur="minMaxRate();">
                </div>
                 <div class="row" id="rateValidateId">
                  <input type="hidden" name="rateValidation" id="rateValidation">
              </div> 
                <div class="input-group-btn selp-custom-min payrate_p">
                  <select class="selectpicker ratetype" id="rateType" name="rateType" field-name="Rate Per">
                    <option value="">Rate Per</option>
                    <option ${requirement.rateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
                    <option ${requirement.rateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
                    <option ${requirement.rateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
                    <option ${requirement.rateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
                    <option ${requirement.rateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
                    <option ${requirement.rateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
                    <option ${requirement.rateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
         
          
      
          
          
		<div class="form-group">
				<label class="col-md-3">Travel Allowance</label>
				<div class="col-md-9">
				<select  class="selectpicker" name="travelAllowance" id="travelAllowance" field-name="Travel Allowance">
	                 <option  ${requirement.travelAllowance  == '0%' ? 'selected' : ''}>0%</option>
	                 <option  ${requirement.travelAllowance  == '10%' ? 'selected' : ''}>10%</option>
	                 <option  ${requirement.travelAllowance  == '15%' ? 'selected' : ''}>15%</option>
	                 <option  ${requirement.travelAllowance  == '20%' ? 'selected' : ''}>20%</option>
	                 <option  ${requirement.travelAllowance  == '25%' ? 'selected' : ''}>25%</option>
	                 <option  ${requirement.travelAllowance  == '30%' ? 'selected' : ''}>30%</option>
	                 <option  ${requirement.travelAllowance  == '35%' ? 'selected' : ''}>35%</option>
	                 <option  ${requirement.travelAllowance  == '40%' ? 'selected' : ''}>40%</option>
	                 <option  ${requirement.travelAllowance  == '45%' ? 'selected' : ''}>45%</option>
	                 <option  ${requirement.travelAllowance  == '50%' ? 'selected' : ''}>50%</option>
                 </select>
                 
				</div>
		 </div>
		 
		 <div class="form-group">
                      <label class="col-md-3">Closed Date</label>
                         <div class="col-md-9">
                           <div class="input-group">
                              <input type="text"  name="closedDate" class="form-control" id="closedDate" readonly="readonly" value="${requirement.closedDateStringFormat}">
                              <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                           </div>
                        </div>
                  </div>
                  
                  
              <div class="form-group" id="receivingApplicantsDiv"  style="display:none">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                 <label> <input type="checkbox"  id="receivingApplicantsId" value="${requirement.isreceivingApplicants}"
                 
						 ${requirement.isreceivingApplicants  == 'Y' ? "checked" : ''}	name="isreceivingApplicants"  onclick="receivingApplicants();"> Allow Receiving Applicants
				</label>
              </div>
            </div>
		     <c:if test="${! empty requirement.closedDateStringFormat}">
		     <div class="form-group"  style="display:block">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                 <label> <input type="checkbox"  id="receivingApplicantsId" value="${requirement.isreceivingApplicants}"
                 
						 ${requirement.isreceivingApplicants  == 'Y' ? "checked" : ''}	name="isreceivingApplicants"  onclick="receivingApplicants();"> Allow Receiving Applicants
				</label>
              </div>
            </div>
		     </c:if>
          
          <div class="form-group">
				<label class="col-md-3">Location<span class="text-danger paddingLeft">*</span></label>
				<div class="col-md-9">
				 <input class="form-control positionRelative" id="ReqLocation" name="location" placeholder="Enter your address"  value="${requirement.location}" type="text" field-name="Locatin" data-validation="required"></input>
          			<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('ReqLocation','ReqCountry','ReqState','ReqCity','ReqZipCode');"></i>
				</div>
		</div>
		
		<div class="form-group">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				 <input type="text" class="form-control"  name="countryReq" id="ReqCountry" placeholder="Country" value="${requirement.country}" field-name="Country" readonly>
				</div>
		</div>
		
		 <div class="form-group">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<input type="text"  class="form-control" name="state" id="ReqState" value="${requirement.state}" placeholder="State" field-name="State" readonly>
				</div>
		 </div>
		
		 <div class="form-group">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
			     <input type="text"  class="form-control" name="city" id="ReqCity" value="${requirement.city}" placeholder="City" field-name="City"  readonly>
			</div>
		</div>
		
		
		 <div class="form-group">
			<label class="col-md-3">Zip Code</label>
			<div class="col-md-9">
			     <input type="text"  class="form-control" name="zipcode" id="ReqZipCode" value="${requirement.zipCode}" placeholder="Zip Code" field-name="zip Code" >
			</div>
		</div>
		
	 
         <div class="form-group">
              <label class="col-md-3">Skills</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="skills" id="skills" data-placeholder="Select Skills" data-validation-optional="true">
               <c:forEach var="skill" items="${skillsList}">
               		<c:set var = "NUM" value = "${0}"/>
               		<c:forEach var="existedSkill" items="${requirement.reqSkillsListString}">
					    <c:if test="${existedSkill eq skill}">
					    	<c:set var = "NUM" value = "${1}"/>
					       <option  selected value="${skill}">${skill}</option>
					    </c:if>
					</c:forEach>
					<c:if test="${NUM == 0}">
						<option  value="${skill}">${skill}</option>
					</c:if>
                </c:forEach>
                </select>
              </div>
            </div>
            
              <div class="form-group">
              <label class="col-md-3">Immigration Status</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="visa" id="visa" data-placeholder="Select Visa" data-validation-optional="true">
               <c:forEach var="visaList" items="${visaList}">
               		<c:set var = "NUM" value = "${0}"/>
               		<c:forEach var="existedVisa" items="${requirement.visaType}">
					    <c:if test="${existedVisa eq visaList}">
					    	<c:set var = "NUM" value = "${1}"/>
					       <option  selected value="${visaList}">${visaList}</option>
					    </c:if>
					</c:forEach>
					
					<c:if test="${NUM == 0}">
						<option  value="${visaList}">${visaList}</option>
					</c:if>
                </c:forEach>
                </select>
              </div>
            </div>
            
          <div class="form-group pras">
            <input type="hidden" name="templateId" id="templateId" value="" />
            <label class="col-md-3">Attachment</label>
            <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
            <div class="col-md-9">
              <div class="input-group attachfile" id= "uploadFileGroup">
                <label class="input-group-btn">
                <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                       <input type="file" name="uploadBtn" id="uploadBtn"  class="hidden upload">
                  </span>
                </label>
                
                <input type="text" class="form-control input-sm" readonly id="uploadFile" name="docFile"  value="${requirement.fileName}" type="file"   placeholder="Upload File ..." field-name="Upload Resume" data-validation="validate_docimgpdf" data-validation-optional="true">
              <div class="clearfix"></div> <div class="clearfix"></div>
              </div>
            </div>
            <div class="clearfix"></div>
            <!-- upload file html need to add bottom JavaScript otherwise it will not work --> 
          </div>
           <%-- <div class="form-group">
            <label class="col-md-3">Description</label>
            <div class="col-md-9 nicEditpanelContain">
             <textarea rows="5" class="form-control" id="bodytext" name="description" value="${requirement.description}" field-name="Description"  data-validation-optional="true">${requirement.description}</textarea>
             <textarea rows="5" class="form-control" id="bodytext" name="description" value="${requirement.description}" field-name="Description" data-validation=" validate_length length2-5000" data-validation-optional="true"></textarea>
           
            </div>
          </div> --%>
          <div class="clearfix"></div>
          <div class="clearfix mb">
          <label class="col-md-3">Description</label>
          <div class="col-md-12 txtEditor_p">
          <%-- <textarea rows="5" class="form-control" id="bodytext" name="description" value="${requirement.description}" field-name="Description" data-validation="" data-validation-optional="true">${requirement.description}</textarea> --%> 
          <textarea id="txtEditor" name="description" value="${requirement.description}" class="form-control newContrtextarea">${requirement.description}</textarea>
          </div>
        </div> 
          
          <div class="form-group">
              <label class="col-md-3">Notice Period</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="noticePeriod" data-placeholder="Select Notice Period"  id="noticePeriod" data-validation-optional="true" onclick="getNoticeperiod();">
					     <option  selected value="${noticePrd}">${noticePrd}</option>
					        <option  ${requirement.noticePeriod  == 'Immediately Available' ? 'selected' : ''}>Immediately Available</option>
	                		 <option  ${requirement.noticePeriod  == '15-Days' ? 'selected' : ''}>15 Days</option>
	               			  <option  ${requirement.noticePeriod  == '1-Month' ? 'selected' : ''}>1 Month</option>
	                		 <option  ${requirement.noticePeriod  == '2-Month' ? 'selected' : ''}>2 Months</option>
	                 		<option  ${requirement.noticePeriod  == '3-Month' ? 'selected' : ''}>3 Months</option>
                </select>
              </div>
            </div>
          
          <div class="form-group">
	         <label class="col-md-3">Cooling-Off Period &nbsp;&nbsp;<i class="fa fa-info-circle" title="A Cooling Off Period is the time during which an applicant is disqualified from applying for requirement, because of applied recently."></i></label>
	         <div class="col-md-9 selectpicker_maxheight">
	             <select class="selectpicker" id="coolingOff" name="coolingOff" >
	             
	             		<c:if test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
	                     	 <option ${requirement.reqCoolingOffPeriod  == '0 Days' ? 'selected' : ''} value="0 Days">0 Days</option>
	                    	 <option ${requirement.reqCoolingOffPeriod  == '1 Week' ? 'selected' : ''} value="1 Week">1 Week</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '2 Weeks' ? 'selected' : ''} value="2 Weeks">2 Weeks</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '3 Weeks' ? 'selected' : ''} value="3 Weeks">3 Weeks</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '1 Month' ? 'selected' : ''} value="1 Month">1 Month</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '2 Months' ? 'selected' : ''} value="2 Months">2 Months</option>
		                     <option ${requirement.reqCoolingOffPeriod  == '3 Months' ? 'selected' : ''} value="3 Months" >3 Months</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '4 Months' ? 'selected' : ''} value="4 Months">4 Months</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '5 Months' ? 'selected' : ''} value="5 Months">5 Months</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '6 Months' ? 'selected' : ''} value="6 Months">6 Months</option>
	                    	 <option ${requirement.reqCoolingOffPeriod  == '7 Months' ? 'selected' : ''} value="7 Months">7 Months</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '8 Months' ? 'selected' : ''} value="8 Months">8 Months</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '9 Months' ? 'selected' : ''} value="9 Months">9 Months</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '10 Months' ? 'selected' : ''} value="10 Months">10 Months</option>
	                     	 <option ${requirement.reqCoolingOffPeriod  == '11 Months' ? 'selected' : ''} value="11 Months">11 Months</option>
	                      	 <option ${requirement.reqCoolingOffPeriod  == '1 Year' ? 'selected' : ''} value="1 Year">1 Year</option>
                     	 </c:if>
                     	 <c:if test="${requirement.requirementSubmissionStatus != 'OPEN'  &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
		                	 <option ${loginOrgLocCoolingOffPeriod  == '0 Days' ? 'selected' : ''} value="0 Days">0 Days</option>
	                    	 <option ${loginOrgLocCoolingOffPeriod  == '1 Week' ? 'selected' : ''} value="1 Week">1 Week</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '2 Weeks' ? 'selected' : ''} value="2 Weeks">2 Weeks</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '3 Weeks' ? 'selected' : ''} value="3 Weeks">3 Weeks</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '1 Month' ? 'selected' : ''} value="1 Month">1 Month</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '2 Months' ? 'selected' : ''} value="2 Months">2 Months</option>
		                     <option ${loginOrgLocCoolingOffPeriod  == '3 Months' ? 'selected' : ''} value="3 Months" >3 Months</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '4 Months' ? 'selected' : ''} value="4 Months">4 Months</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '5 Months' ? 'selected' : ''} value="5 Months">5 Months</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '6 Months' ? 'selected' : ''} value="6 Months">6 Months</option>
	                    	 <option ${loginOrgLocCoolingOffPeriod  == '7 Months' ? 'selected' : ''} value="7 Months">7 Months</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '8 Months' ? 'selected' : ''} value="8 Months">8 Months</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '9 Months' ? 'selected' : ''} value="9 Months">9 Months</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '10 Months' ? 'selected' : ''} value="10 Months">10 Months</option>
	                     	 <option ${loginOrgLocCoolingOffPeriod  == '11 Months' ? 'selected' : ''} value="11 Months">11 Months</option>
	                      	 <option ${loginOrgLocCoolingOffPeriod  == '1 Year' ? 'selected' : ''} value="1 Year">1 Year</option>
		                 </c:if>
		             </select>
	         </div>
       	 </div>
          
          <div class="clearfix"></div>
         <div class="form-group">
				<div class="ml">
					<c:set var="val" value="${requirement.isClientJob}" />
					<c:choose>
						<c:when test="${val == 'Y'}">
							<input disabled="disabled" type="checkbox" name="isTemplate"
								id="isTemplate" value="" onclick="getTemplateVal();">
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="isTemplate" id="isTemplate"
								value="" onclick="getTemplateVal();">
						</c:otherwise>
					</c:choose>
					<label>&nbsp;&nbsp;<strong>Save as a Template</strong>
						&nbsp;&nbsp;
					</label>
				</div>
			</div>
        
      </div>
      <div class="clearfix mt">
        <div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
	        <c:if test="${(SourceLeadWeb_vms == 'Y' && SourceLeadWeb_ats == 'Y') || (SourceLeadWeb_vms != 'Y')}">
	        
	        <c:set var="val" value="${requirement.isClientJob}"/>
			<c:choose> 
			  <c:when test="${val == 'Y'}">
			   				<div class="ml">
			   				<!-- here the isClient checkbox name is changes to isClientEdit because, readonly check box is not working and onclick methods are effected (Venkat Kesav) -->
					            <input type="checkbox" name="isClient" id="isClientJob" class="isClient" value="${requirement.isClientJob}"
					            ${requirement.isClientJob  == 'Y' ? "checked" : ''} onclick="getIsClientVal();" hidden="true" checked="checked">
					            <label>&nbsp;&nbsp;<strong>Is Client Job</strong>
					            &nbsp;&nbsp;
					            </label>
						  	</div>
			  </c:when>
						  <c:otherwise>
						   <div class="ml">
							  <input type="checkbox" name="isClient" id="isClientJob" class="isClient" value="${requirement.isClientJob}"
							   ${requirement.isClientJob  == 'Y' ? "checked" : ''} onclick="getIsClientVal();">
							   <label>&nbsp;&nbsp;<strong>Is Client Job</strong>
								&nbsp;&nbsp;
							   </label>
							</div>
						 </c:otherwise>
			</c:choose>
	        </c:if>
	      
          <div id="clientDetails" style="display: none;">
            <div class="clearfix">
              <h3>Client Details</h3>
            </div>
            <div class="clearfix">
              <div class="form-group">
                <label class="col-md-3">Client<span class="text-danger paddingLeft">*</span></label>
                <div class="col-md-9">
                  <div>
                   <c:set var="val" value="${requirement.isClientJob}"/>
                   <c:choose> 
			 			 <c:when test="${val == 'Y'}">
			   				<select class="chosen-select form-control select-bg" disabled="disabled" data-validation-optional="true" field-name = "Client" name="selectClient" id="addClientId">
                    			<c:set var="report" value="${orgClient}"/>
							 	<c:if test="${report.locationId.id == loginOrgLocId}">  
			                    	<option selected="selected" value="${report.id},${report.responderLocationId.id}">${report.responderLocationId.org.orgName}</option>
			                    </c:if>  
			                    <c:if test="${report.responderLocationId.id == loginOrgLocId}"> 
			                  	 	<option selected="selected" value="${report.id},${report.locationId.id}">${report.locationId.org.orgName}</option>
			                    </c:if>
                   		    </select>
						 </c:when>
						  <c:otherwise>
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
						  </c:otherwise>
				</c:choose>
                
                
                    <%-- <sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT')">
                    <i data-target="#addClient" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new" onClick="addClientPopUp();"></i>
                    <div id="addClient" class="modal fade" role="dialog"></div>
                    </sec:authorize> --%>
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
                <label class="col-md-3">Client Contact <span class="text-danger paddingLeft">*</span></label>
                <div class="col-md-9">
                  <div>
                  <c:set var="val" value="${requirement.isClientJob}"/>
                  <c:choose> 
			 			 <c:when test="${val == 'Y'}">
			   				<select class="chosen-select form-control" disabled="disabled" data-validation-optional="true" field-name = "Client Contact" >
                      			 <c:if test="${fn:length(clientContactList) gt 0}">
								   <c:forEach var="report" items="${clientContactList}">
			         		<%-- <option value="${report.id}" ${report.id  == requirement.spocId.id ? 'selected' : ''} >${report.spocId.SpocUser.firstName}&nbsp;${report.spocId.SpocUser.lastName}</option> --%>
			         				<option value="${report.id}" ${report.id  == requirement.spocId.id ? 'selected' : ''} >${report.spocName} (${report.ownerName})</option> 
					    		 </c:forEach>
								</c:if>
                      			 <c:if test="${fn:length(clientContactList) lt 1}">
					    		 	<option >${clientContactWithOwner}</option>
					    		 </c:if>
                    		</select>
						 </c:when>
					<c:otherwise>
						  <select class="chosen-select form-control" name="selectContact" data-validation-optional="true" field-name = "Client Contact" id="selectContact">
                     			 <option value=''>-- Select Contact --</option>
                      			 <c:forEach var="report" items="${clientContactList}">
			         		<%-- <option value="${report.id}" ${report.id  == requirement.spocId.id ? 'selected' : ''} >${report.spocId.SpocUser.firstName}&nbsp;${report.spocId.SpocUser.lastName}</option> --%>
			         		     <option value="${report.id}" ${report.id  == requirement.spocId.id ? 'selected' : ''} >${report.spocName} (${report.ownerName})</option> 
					     		 </c:forEach>
                    	</select>
				    </c:otherwise>
				</c:choose>
                    
                    <%-- <sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT_CONTACT')">
                    <i data-target="#addContact" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new" id="addContactPlus" style="display: none;" ></i>
                    </sec:authorize>  --%>
                 </div>
                  
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Bill Rate / Salary</label>
              <div class="col-md-9 pras ratetype">
	              <div class="input-group">
	              <c:set var="val" value="${requirement.isClientJob}"/>
                  <c:choose> 
			 	<c:when test="${val == 'Y'}">
			   		<div class="input-group-btn selp-custom-min">
	                  <select class="selectpicker" disabled="disabled" id="clientCurrencyCode" name="clientCurrencyCode">
		                 <option  ${requirement.clientCurrencyCode  == 'INR' ? 'selected' : ''}>INR</option>
		                 <option  ${requirement.clientCurrencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option  ${requirement.clientCurrencyCode  == 'USD' ? 'selected' : ''}>USD</option>
		                 <option  ${requirement.clientCurrencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option  ${requirement.clientCurrencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option  ${requirement.clientCurrencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
	                </select>
	                
	                </div>
	                <input type="text" disabled="disabled" class="form-control" id="clientRate" name="clientRate" field-name="Client Rate" value="${requirement.clientRate}" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">
	                <div class="input-group-btn selp-custom-min">
	                 <select disabled="disabled" class="selectpicker ratetype"  id="clientRateType" field-name="Client Rate Per" value="${requirement.clientRateType}" name="clientRateType">
	                    
			                <option value="">Rate Per</option>
		                    <option ${requirement.clientRateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
		                    <option ${requirement.clientRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
		                    <option ${requirement.clientRateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
		                    <option ${requirement.clientRateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
		                    <option ${requirement.clientRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
		                    <option ${requirement.clientRateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
		                    <option ${requirement.clientRateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
	                  </select>
           			</div>
			 </c:when>
			    <c:otherwise>
  
				<div class="input-group-btn selp-custom-min">
	             <select class="selectpicker" id="clientCurrencyCode" name="clientCurrencyCode">
		         
		          <c:if test="${empty requirement.id}">
	                <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
	                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
	                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                 </c:if>
                 
                 <c:if test="${! empty requirement.id}">
	                 <option ${requirement.clientCurrencyCode  == 'INR' ? 'selected' : ''}>INR</option>
	                 <option ${requirement.clientCurrencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
	                 <option ${requirement.clientCurrencyCode  == 'USD' ? 'selected' : ''}>USD</option>
	                 <option ${requirement.clientCurrencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
	                 <option ${requirement.clientCurrencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
	                 <option ${requirement.clientCurrencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
                 </c:if>
                </select>
	                
	                </div>
	                <input type="text" class="form-control" id="clientRate" name="clientRate" field-name="Client Rate" value="${requirement.clientRate}" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">
	                <div class="input-group-btn selp-custom-min">
	                 <select class="selectpicker ratetype"  id="clientRateType" field-name="Client Rate Per" value="${requirement.clientRateType}" name="clientRateType">
	                    
			                <option value="">Rate Per</option>
		                    <option ${requirement.clientRateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
		                    <option ${requirement.clientRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
		                    <option ${requirement.clientRateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
		                    <option ${requirement.clientRateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
		                    <option ${requirement.clientRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
		                    <option ${requirement.clientRateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
		                    <option ${requirement.clientRateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
	                  </select>
           			</div>
				</c:otherwise>
				</c:choose>
	                
              </div>
              </div>
              <div class="clearfix"></div>
              <div class="clearfix"></div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Submission Form<span class="text-danger paddingLeft">*</span></label>
                <div class="col-md-9">
                  <div>
                  <c:set var="val" value="${requirement.isClientJob}"/>
                  <c:choose> 
				 	<c:when test="${val == 'Y'}">
				   		<select class="chosen-select form-control" onchange="false" id="clientSubmissionForm" name="clientSubmissionForm" field-name = "Submission Form" data-validation-optional="true" >
	                       <c:set var="report" value="${clientSubmissionTemplete}"/>
				           <option value="${report.id}">${report.templateName}</option>
						</select>
				    </c:when>
				    <c:otherwise>
							<select class="chosen-select form-control" id="clientSubmissionForm" name="clientSubmissionForm" field-name = "Submission Form" data-validation-optional="true" >
	                       <option value="">-- Select Submission Form --</option>
	                       <c:forEach var="report" items="${clientSubmissionTempletesList}">
								 <%-- 	<c:if test="${report.id == loginOrgLocId}">   --%>
				                    	<option value="${report.id}" ${report.id  == requirement.clientSubmissionTemplateId.id  ? 'selected' : ''} >${report.templateName}</option>
				                 <%--    </c:if>   --%>
							</c:forEach>
							</select>
					</c:otherwise>
				</c:choose>
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
                
                <c:if test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
                	<input type="checkbox"    name="cd" id="apprverReq"   readonly checked >
                </c:if>
                	
                <c:if test="${requirement.requirementSubmissionStatus != 'OPEN'  &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
                	<input type="checkbox" name="cd" id="apprverReq"  onClick="approvalType('Default');removeApprovers();" checked >
                </c:if>
               		 <label>&nbsp;&nbsp;<strong>Approver Required</strong></label>
                </div>
                
                <!-- Radio buttons for Sequential or Parallel function -->
                <div  class="ml pull-left apprverReq" style="display:none;">
                <label class="rad" id="parallel">
                <input type="radio" name="approver" id="parallelapprovers" checked="checked" onClick="approvalType('Parallel');">
                <i class="fa fa-align-center" title="Parallel"></i>
                </label>
                <label class="rad" id="sequential">
                <input type="radio" name="approver" id="seqentialApprovers"  onClick="approvalType('Sequential');">
                <i class="fa fa-retweet" title="Sequential"></i>
                </label>
                
                  <i id="navbar" class="glyphicon glyphicon-info-sign text-info ml" title="Sequential approval mechanism is hierarchy based Parallel approval mechanism is any approver can approve requirement"></i>
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
                <label class="col-md-3">Approver<span class="text-danger paddingLeft">*</span></label>
                <div class="col-md-9">
                    <select multiple class="chosen-select form-control" id="parallelApprover" name="parallelApprover" field-name="Approver" data-validation="required" data-validation-optional="true" data-placeholder="Select Approver">
                       <c:if test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
                       <option disabled>Select Approver</option>
                       <c:forEach var="report" items="${approverList}">
		                    <option value="${report.id}" disabled="disabled"  >${report.firstName}&nbsp;${report.lastName}</option>
		               </c:forEach>
		               </c:if>
		               <c:if test="${requirement.requirementSubmissionStatus != 'OPEN' &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
                       <option disabled>Select Approver</option>
                       <c:forEach var="report" items="${approverList}">
		                    <option value="${report.id}"   >${report.firstName}&nbsp;${report.lastName}</option>
		               </c:forEach>
		               </c:if>
                    </select>
                </div>
         </div>
         
         
         <div class="form-group sequential" id="addLvl" style="display:none;">
                <label class="col-md-3">Approver1<span class="text-danger paddingLeft">*</span></label>
                <div class="col-md-9">   
                    
                        <c:if test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
                        <select  disabled="disabled" class="chosen-select form-control" id="sequentialApprover1" name="sequentialApprover1" field-name="Approver" data-validation="required" data-validation-optional="true" onchange="getLevel2Approvers();">
	                       <option value="">Select Approver1</option>
	                       <c:forEach var="report" items="${approverList}">
			                    <option value="${report.id}" >${report.firstName}&nbsp;${report.lastName}</option>
			               </c:forEach>
			               </select>
		               </c:if>
		               <c:if test="${requirement.requirementSubmissionStatus != 'OPEN' &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
		               <select   class="chosen-select form-control" id="sequentialApprover1" name="sequentialApprover1" field-name="Approver" data-validation="required" data-validation-optional="true" onchange="getLevel2Approvers();">
	                       <option value="">Select Approver1</option>
	                       <c:forEach var="report" items="${approverList}">
			                    <option value="${report.id}" >${report.firstName}&nbsp;${report.lastName}</option>
			               </c:forEach>
			              </select>
		               </c:if>
                    <!-- <span onClick="addLvl();"><i class="fa fa-level-down btn btn-default formic1" data-toggle="tooltip" data-placement="left" title="Add Level"></i></span> -->
                </div>
                
	            <label class="col-md-3 mt">Approver2<span class="text-danger paddingLeft">*</span></label>
	            <div class="col-md-9 mt">
	              		
                       <c:if test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
                       <select  disabled="disabled" class="chosen-select form-control" id="sequentialApprover2" name="sequentialApprover2" field-name="Approver 2" data-validation="required" data-validation-optional="true">
	                       <option value="">Select Approver2</option>
	                       <c:forEach var="report" items="${approverList}">
			                    <option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
			               </c:forEach>
			           </select>
		               </c:if>
		               <c:if test="${requirement.requirementSubmissionStatus != 'OPEN' &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
		               <select   class="chosen-select form-control" id="sequentialApprover2" name="sequentialApprover2" field-name="Approver 2" data-validation="required" data-validation-optional="true">
	                       <option value="">Select Approver2</option>
	                       <c:forEach var="report" items="${approverList}">
			                    <option value="${report.id}"    >${report.firstName}&nbsp;${report.lastName}</option>
			               </c:forEach>
			               </select>
		               </c:if>
	                    
	              </div>
              
              </div>
           
          <div class="form-group">
            <label class="col-md-3">Manager<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-9">
           
              <select  class="chosen-select form-control"  data-validation = "required" field-name = "Manager" name="manager" id="managerId">
                       
                       <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == null}">
                       <option selected="selected" value="${SourceLeadWeb_user.id}">${SourceLeadWeb_user.firstName}&nbsp;${SourceLeadWeb_user.lastName}</option>
                       </c:if>
                       
                       <c:forEach var="report" items="${approverList}">
                       	<c:if test="${SourceLeadWeb_user.id != report.id}">
                       		<option value="${report.id}" ${report.id  == requirement.managerUserId.id ? 'selected' : ''}>${report.firstName}&nbsp;${report.lastName}</option>
                       	</c:if>
		               </c:forEach>   
              </select>
            </div>
          </div>
          <div class="form-group">
             
            <div id="assignedToSavingDiv"></div>
            <div id="defaultAssignedToSavingDiv"></div>
													
            <label class="col-md-3">Assign To</label>
            <div class="col-md-9">
                <div class="form-icon">
                <span class="icon-inside"> <i class="fa fa-search-plus"></i>
				
                 <input type="text" id="assignedTo" placeholder="Assign Team" field-name ="Assign To" class="form-control" data-target="#assignTeam" data-toggle="modal" onclick="popupWindow(${requirement.id});">
                  </span>
                  <span data-target="#addTeam" data-toggle="modal" title="Add Team"> <i class="fa fa-plus btn btn-default formic1" onclick="flushStoreTeam();getTeamOwners();"></i> </span>
                  </div>
                <!-- Modal content Assign To -->
                <div role="dialog" id="assignTeam"   class="modal fade">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button class="close" type="button" id="closebtnId" onclick="clearSelections();">×</button>
                        <h4 class="modal-title">Assign To</h4>
                      </div>
                       <!-- <div id="userList"></div> -->
                       <div id="popupWindow"></div>
                      
                    </div>
                  </div>
                </div>
                <!-- Assign To modal popup ends -->
              </div>
          </div>
          <div class="form-group">
            <label class="col-md-3">Priority<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-9">
              <select class="selectpicker" id="priority" name="priority" data-validation = "required" field-name = "priority">
                <option ${requirement.priority  == 'Medium' ? 'selected' : ''}>Medium</option>
                <option ${requirement.priority  == 'Low' ? 'selected' : ''}>Low</option>
                <option ${requirement.priority  == 'Urgent' ? 'selected' : ''}>Urgent</option>
                <option ${requirement.priority  == 'High' ? 'selected' : ''}>High</option>
              </select>
            </div>
          </div>
          <div class="form-group" id="internalSubmissionFrom">
            <label class="col-md-3">Application Form<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-9">
               
               <select class="chosen-select form-control select-bg" name="applicationFrom" id="applicationFrom"  data-validation="required"  field-name = "Application Form">
		                <c:forEach var="report" items="${submissionTempletesList}">
					                  <option value="${report.id}" ${report.id  == requirement.submissionTemplateId.id ? 'selected' : ''}>${report.templateName}</option>
						</c:forEach>
               </select>					
          </div>
        </div>
        
        <div class="clearfix"></div>
         <div class="form-group">
				<div class="ml">
					<c:set var="val" value="${requirement.releaseJobToAllUsers}" />
					<c:choose>
						<c:when test="${val == 'Y'}">
							<input disabled="disabled" type="checkbox" checked name="releaseToAllUsers" id="releaseToAllUsers" value="Y">
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="releaseToAllUsers" id="releaseToAllUsers" value="Y">
						</c:otherwise>
					</c:choose>
					<label>&nbsp;&nbsp;<strong>Release to all Users</strong>
						&nbsp;&nbsp;
					</label>
				</div>
			</div>
      </div>
    </div>
    <!-- Internal Details html Ends -->
      
      
    </div>
    </div>
   <%--  <input type="hidden" value="${requirement.id}" name ="requirementId"  id ="requirementId"> --%>
     <div class="form-footer clearfix">
    <div class="col-lg-12">   
      <div class="pull-right">
	      <c:if test="${ requirement.requirementSubmissionStatus != 'OPEN'}">
	      		  <input type="button" value="Submit for Approval" id="submitForApproval" onclick="storeRequirement('submitForApproval');" class="btn btn-primary" style="display:none;">
	      </c:if>
          
        <c:if test="${empty requirement.requirementSubmissionStatus || requirement.requirementSubmissionStatus == 'DRAFT' || requirement.requirementSubmissionStatus == 'REJECTED'}">
        	<input type="button" value="Draft" id="draft" onclick="storeRequirement('draft'); " class="btn btn-danger ml">
        </c:if>
         <c:if test="${requirement.requirementSubmissionStatus == 'OPEN'}">
       			  <input type="button" value="Save" id="save" onclick="storeRequirement('save');" class="btn btn-primary ml" >   
         </c:if>
          <c:if test="${requirement.requirementSubmissionStatus != 'OPEN'}">
       			  <input type="button" value="Save" id="save" onclick="storeRequirement('save');" class="btn btn-primary ml" >   
         </c:if>
        <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/requirementList'/>'" class="btn btn-default ml">
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
                            <label class="col-lg-4">Team Name <span class="text-danger paddingLeft">*</span></label>
                            <div class="col-lg-8" id="teamNameDiv">
                              <!-- <input type="text" class="form-control" value=""> -->
                              <input type="text" class="form-control"  placeholder="Team Name" id="name" name="name" data-validation="required validate_Space validate_AlphaNumaric validate_Name validate_length length2-100" field-name="Team Name" value="" onblur="validateTeamName();">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-4">Team Lead <span class="text-danger paddingLeft">*</span></label>
                            <div class="col-lg-8 select-field">
                             <select class="form-control"  name="ownerUserId" id="ownerUserId" placeholder="Team Lead *" data-validation="required validate_Space" field-name="Team Lead"  onchange="getTeamMembers();">
			                    	
			                  </select>
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-4">Team Members <span class="text-danger paddingLeft">*</span></label>
                            <div class="col-lg-8">
                             <select multiple class="chosen-select form-control select-bg" name="memberUserId" id="memberUserId" data-placeholder="Select Team Member" data-validation="required validate_Space" field-name="Team Member">
					                  
		                       </select>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer mt">
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

<script>
  $( function() {
	  
	  $('#closedDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true,
	  	  minDate: 1,
	  	onSelect : function(selected) {
	  		  document.getElementById("receivingApplicantsDiv").style.display="block";
	  	}
	  });
	  
  } );
</script>

<script>
var selectedFile = document.getElementById("uploadBtn");
selectedFile.addEventListener("change", function() {
	 $("#uploadFile").focus();
	 if(validate_fileupload(this.value)){
		 var docSize =  $("#uploadBtn")[0].files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
			  $("input#uploadFile").after("<p class='jquery_form_error_message'>File size should be less then 2MB </span>");
			  document.getElementById("uploadFile").setAttribute('record-exist','yes');
			  document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','File size should be less then 2MB');
			  document.getElementById("uploadFile").value="";
	      }else{
	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
  		 $('#uploadFileGroup').find('p.jquery_form_error_message').remove(); 
	    }
	 }
});

/* upload file script */
document.getElementById("uploadBtn").onchange = function () {
	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
};

</script>

<script type="text/javascript">
function validate_fileupload(fileName)
{
    var allowed_extensions = new Array("docx","doc","pdf","rtf");
    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>


<script type="text/javascript">

 function receivingApplicants(){
	
	if (document.getElementById('receivingApplicantsId').checked){
		 $("#receivingApplicantsId").val('Y');
	}
	
	else{
		
		 $("#receivingApplicantsId").val('N');
		
		}
	
} 
	  
</script>



<script type="text/javascript">
	function storeRequirement(saveType) {
		
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		
		
		var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		
		checkTemplateforDouplicate();
		validateJobId();
		$('#addClient').empty();
		$('#addContact').empty();
		//$('#addTeam').empty(); /* commented by Venkat Kesav because of issue #2673 */ 
		
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
			 
			 /* $('#addClientId').removeAttr('data-validation');
			 $('#addClientId').attr("data-validation-optional","true");
			 
			 $('#selectContact').removeAttr('data-validation');
			 $('#selectContact').attr("data-validation-optional","true");
			 
			 $('#clientSubmissionForm').removeAttr('data-validation');
			 $('#clientSubmissionForm').attr("data-validation-optional","true"); */
		}
		
          var rateType = document.getElementById("rateType").value; 
  		 if(rateType != null && rateType != ""){
  			 $('#rate').removeAttr('data-validation-optional');
  			 $('#rate').attr("data-validation","required validate_Space validate_int validate_length length1-8"); 
  		 }else{
  			 $('#rate').removeAttr('data-validation');
  			 $('#rate').attr("data-validation-optional","true"); 
  		 }
  		 
 		 var rate = document.getElementById("rate").value; 
 		 if(rate != null && rate != ""){
 			 $('#rateType').removeAttr('data-validation-optional');
 			 $('#rateType').attr("data-validation","required");
 			 $('#rate').attr("data-validation","required validate_Space validate_int validate_length length1-8");
 		 }else{
 			 $('#rateType').removeAttr('data-validation');
 			 $('#rateType').attr("data-validation-optional","true"); 
 		 }
 		 
 		var maxRate = document.getElementById("maxRate").value; 
		 if(maxRate != null && maxRate != ""){
			 $('#rateType').removeAttr('data-validation-optional');
			 $('#rateType').attr("data-validation","required");
			 $('#rate').attr("data-validation","required validate_Space validate_int validate_length length1-8");
		 }else{
			 $('#rateType').removeAttr('data-validation');
			 $('#rateType').attr("data-validation-optional","true"); 
		 }
 		 
 		 var clientRateType = document.getElementById("clientRateType").value; 
 		 if(clientRateType != null && clientRateType != ""){
 			 $('#clientRate').removeAttr('data-validation-optional');
 			 $('#clientRate').attr("data-validation","required"); 
 		 }else{
 			 $('#clientRate').removeAttr('data-validation');
 			 $('#clientRate').attr("data-validation-optional","true"); 
 		 }
 		 
 		 var clientRate = document.getElementById("clientRate").value; 
 		 if(clientRate != null && clientRate != ""){
 			 $('#clientRateType').removeAttr('data-validation-optional');
 			 $('#clientRateType').attr("data-validation","required"); 
 		 }else{
 			 $('#clientRateType').removeAttr('data-validation');
 			 $('#clientRateType').attr("data-validation-optional","true"); 
 		 }
		
		   if ($('#requirement_form').validate(false, validationSettings)){   
			
			var frm = $('#requirement_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
			
			//con = con.replace(/[{}]/g, "");
			//It will escape all the special characters
			//var jsonData = encodeURIComponent(con);
			
			var form = $('#requirement_form')[0];
      		var formData = new FormData(form); 
      		formData.append("json",jsonData);
			var sourceleadUrl = '${sourceleadUrl}';
			//var text = $('textarea#bodytext').val();
			
			
			$( "#submitForApproval" ).addClass('button').val('Processing..');
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
     		
			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeRequirement?${_csrf.parameterName}=${_csrf.token}&saveType="+saveType,
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
					url : sourceleadUrl+"/storeTeam?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
					success :function(data) { 
						$('#addTeam').hide();
						$('.modal-backdrop').hide();
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
        	$('#countryReq').append('<option value="">---Select Country---</option>');
        	for(var i=0;i<json.length;i++){
            	if(json[i] == countryName ){
            		
            		$('#countryReq').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else if((countryName ==null ||  countryName =='' || countryName =='undefined') && (json[i] == '${SourceLeadWeb_loginCountryName}')){
            		
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
	        	$('#state').append('<option value="">---Select State---</option>');
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
    	$('#state').append('<option value="">---Select State---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---Select State---</option>');
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
	        	$('#city').append('<option value="">---Select City---</option>');
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
    	$('#city').append('<option value="">---Select City---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>



<!-- Country,State,City Script Ends Here -->

<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script type="text/javascript">
			/* var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			} */
	       
	     //Adding Customized Skills to the list
	       var select, chosen;
		    select = $("#skills");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Email:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keydown keyup', function(e)
		    {
		    	   var skillsArray = this.value.split(",");
			    	 if(skillsArray.length > 1){
		        		 for (var i=0; i < skillsArray.length; i++) {
		        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
		        			 select.append(option);
		 		             select.find(option).prop('selected', true);
		 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
		 	        		 $("#skills").trigger("chosen:updated");
		                 }
			    	 }else{
		    			var newSkill = this.value.replace(","," ");
		    			 if (tagItBykeyForSkills(e) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
		                {
		    				 preventTabkey(e); 
		    				 var option = $("<option>").val(newSkill).text(newSkill);
		                     select.append(option);
		                     select.find(option).prop('selected', true);
		                     $("#skills option").each(function(){
		                         $(this).siblings("[value='"+ this.value+"']").remove();
		                   });
		                   $("#skills").trigger("chosen:updated");
		                }
				       
	    	         }
		    });
		    
		    
		    
		    //Adding Customized Educations to the list 
		    var selectone, chosenone;
		    selectone = $("#education");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Education:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keydown keyup', function(e1)
		    {
		    	var newEducation = this.value.replace("\'","");
		    	   if (tagItBykeyForSkills(e1) && (newEducation != null  && newEducation != '' && newEducation != 'undefined'))
			        {
		    		   preventTabkey(e1); 
		    		   var par = this.parent;
			            var option = $("<option>").val(newEducation).text(newEducation);
			            selectone.append(option);
			            selectone.find(option).prop('selected', true);
			            
			            $("#education option").each(function(){
	                         $(this).siblings("[value='"+ this.value+"']").remove();
	                   });
			            
			            $("#education").trigger("chosen:updated");
			           
			        }
		    
		    });
		    
            
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
        $('#jobTitle').removeAttr('data-validation-optional');
		$('#jobTitle').attr("data-validation","required validate_Space validate_length length2-250"); 
		checkTemplateforDouplicate();
		
    }else{
    	
        $("#isTemplate").val('N');
       /*  $('#jobTitle').removeAttr('data-validation');
		$('#jobTitle').attr("data-validation-optional","true");  */
        $('#jobTitleDiv').find('p.jquery_form_error_message').remove();

    }
}

</script>
<script>
function getRequirementAndLookUpSkills(requirementid){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
   	type: "POST",
    url: sourceleadUrl+"/getRequirementAndLookUpSkills?${_csrf.parameterName}=${_csrf.token}&requirementid="+requirementid, 	   
	    success: function(data) {
	    	if(data != null && data != 'undefined' && data !='' ){ 
	    		var json = JSON.parse(data);
	    		var output = "";
	    		for (var i = 0; i < json.length; i++) { 
               	    var skill = json[i];
               
	        		output = output + "<option value='" +skill+ "'>"+skill+"</option>";
	    		}
	            $("#skills").empty();
	            $("#skills").append(output);
	            $('#skills').trigger("chosen:updated");
	            
	    	} 
	    }
	});
}
</script>
<script>
function setTemplateValues(){
	var sourceleadUrl ='${sourceleadUrl}';
	var requirementId = document.getElementById('template').value;
	getEditRequirementDetails(requirementId);
	if(requirementId != null && requirementId !='' && requirementId != 'undefined'){
		
		getRequirementAndLookUpSkills(requirementId);
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
			    	
			    	$("#coolingOff").val(json.reqCoolingOffPeriod);
			    	$("#industryName").val(json.industryName);
			    	$("#industryName").trigger("chosen:updated");
			    	
			    	$("#jobOtherType").val(json.otherJobType);
			    	$('.selectpicker').selectpicker('refresh');
			    	$("#jobId").val(json.jobId);
			    	$("#positions").val(json.positions);
			    	$("#travelAllowance").val(json.travelAllowance);
			    	$("#minExp").val(json.minExp);
			    	$("#maxExp").val(json.maxExp);
			    	
			    	if(null != json.jobType && json.jobType != "" && json.jobType != "undefined" && json.jobType == 'Other'){
						  $( ".jobTypeDiv" ).show();
						  $("#jobOtherType").attr("data-validation-optional", "true");
						  $("#jobOtherType").attr("data-validation", "validate_Space validate_length length2-250"); 
					}else{
						  $( ".jobTypeDiv" ).hide();
						  $("#jobOtherType").attr("data-validation-optional", "true");
						  $("#jobTypeDiv").find('p.jquery_form_error_message').remove();
						  $("#jobOtherType").val('');
					}
			    	
			    	var country = '';
					 var state = '';
					 var city = '';
					 validateJobId(json.jobId);
					
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
					if(json.skills != null && json.skills != '' && json.skills != 'undefined'){
						var skills = [];
			            skills = json.skills.split(",");
			            $("#skills").val(skills);
			            $('#skills').trigger("chosen:updated");
					}

					
					if(json.education != null && json.education != '' && json.education != 'undefined'){
			            var educations = [];
			            educations = json.education.split(",");
			            $("#education").val(educations);
			            $('#education').trigger("chosen:updated");
					}
			    	
			    	//$("textarea#description").val(json.description);
			    	
			    	$('#txtEditor').html(json.description); 
			    	
			    	$("#rate").val(json.rate);
			    	$("#maxRate").val(json.maxRate);
			    	
			    	if(json.currencyCode != null && json.currencyCode != '' && json.currencyCode != 'undefined'){
			    	$("#currencyCode").val(json.currencyCode);
			    	}
			    	$('.selectpicker').selectpicker('refresh');
			    	$("#rateType").val(json.rateType);
			    	$('.selectpicker').selectpicker('refresh');
			    	 existedFile = json.fileName;
			    	 $("#uploadFile").val(existedFile);
			    	 $('#uploadFile').trigger("chosen:updated");
			   
			    	 //$( ".jobTypeDiv" ).hide();
		    	}else{
		    		$("#jobTitle").val('');
			    	$("#jobType").val('');
			    	$("#travelAllowance").val('');
			    	$("#jobOtherType").val('');
			    	$("#jobId").val('');
			    	$("#positions").val('');
			    	$("#industryName").val('');
			    	$("#industryName").trigger("chosen:updated");
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
		            $(".Editor-editor").text('');
			    	$("#rate").val('');
			     	$("#currencyCode").val('');
			    	$("#rateType").val('');
			    	$("#maxRate").val('');
			    	$( ".jobTypeDiv" ).show();
			    	$(".")
			    	
			    	existedFile = json.fileName;
			    	 $("#fileName").val(existedFile);
			    	 $('#fileName').trigger("chosen:updated");
		    	}
		    }
     });
	}else{
		 document.getElementById("requirement_form").reset();
		 $(".Editor-editor").text('');
		 $("#skills").trigger("chosen:updated");
		 $("#education").trigger("chosen:updated");
		 $("#existingCountry").val('');
		 $("#existingState").val('');
		 $("#existingCity").val('');
		 getAllCountries(); 		
		 $("#rate").val('');
		
		 
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


<!-- This Method used to remove the  selected Approvers values  when  the approval mechanism is unchecked-->

<script>
function removeApprovers(){
	
	$('#parallelApprover').val('');
	$('#parallelApprover').trigger('chosen:updated');
	
	$('#sequentialApprover1').val('');
	$('#sequentialApprover1').trigger('chosen:updated');
	
	$('#sequentialApprover2').val('');
	$('#sequentialApprover2').trigger('chosen:updated');
	
	
	if(!$("#apprverReq").is(':checked')){
	     
        $("#navbar").removeClass("glyphicon glyphicon-info-sign text-info ml");
    } 

else{
	
	
	 $("#navbar").addClass("glyphicon glyphicon-info-sign text-info ml");
	
	
}
	
	
	
}
</script> 

<script>
function approvalType(type){
	
	    if($("#apprverReq").is(':checked')){
	    	
	    	$("#parallelApprover").val('');
			$('#parallelApprover').trigger("chosen:updated");
	    	
			var requiremenStatus  = '${requirement.requirementSubmissionStatus}';
			
			if(requiremenStatus !='OPEN'){
				$("#submitForApproval").removeAttr("style");
			    $("#save").attr("style","display:none"); 
			}
			
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
     this script is for get model dialog box.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
     AssignedTo model dialog box
--%>

<script>
function popupWindow(reqId){ 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#popupWindow").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $("#popupWindow").removeAttr("style");
	  var result = '';
	    result = result +'<div class="modal-body clearfix">';
        result = result +' <div class="form-horizontal custom-form">';
 		result = result +'     <div class="row">';
 		
 		result = result +'       <div class="col-xs-12 col-md-8 col-lg-8 hidden">';
 		result = result +'         <div class="input-group">';
 		result = result +'           <input type="search" class="form-control" value="">';
 		result = result +'           <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> ';
 		result = result +'			</div>';
 		result = result +'       </div>';
 		
 		result = result +'       <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100">';
 		result = result +'          <select id="rolesDiv" class="selectpicker" multiple data-actions-box="true" onchange="filterTeamMembers();" style="display:block !important" title="--Select Assign Team--">';
 		result = result +'          </select>';
 		result = result +'       </div>';
 		
 		result = result +'     </div>';
 		result = result +'  </div>';
 		result = result +'  <div id="usersGrid" class="modal-bodyCustom mt clearfix popupFilter">'; 
    	
 		result = result +'     </div>';
		result = result +'     </div>';
		$("#popupWindow").empty();
	    $("#popupWindow").append(result); 
	    
	    getTeamsList(reqId);
	    $("#rolesDiv").selectpicker("refresh");
}
</script>

<%-- 
     @author : venkat kesav
     this script is for get users list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
     append Users list to AssignedTo model dialog box
--%>
<script>
function getUserList(teamType){  
	   var sourceleadUrl ='${sourceleadUrl}';
	  
	   var selectedUsersForAssign = [];
	   $('input[name^="assignUserId"]').each(function() {
	 		selectedUsersForAssign.push($(this).val());
	   });
 	   $("#usersGrid").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	   $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getUserList?${_csrf.parameterName}=${_csrf.token}&teamType="+teamType,    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#usersGrid").removeAttr("style");
	                	    var result = '';
	                	    result = result+"<input type='hidden' name='assignedTeamId' id='assignedTeamId' value='"+teamType+"'/>";
	                		result = result +'       <table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
	                		result = result +'           <thead>';
	                		result = result +'           <tr>';
	                		result = result +'             <th style="padding:2px 15px !important;"><input type="checkbox" id="checkAll" onclick="toggle(this); handleClick();"></th>';
	                		result = result +'             <th style="padding:2px 15px !important;">Name</th>';
	                		result = result +'             <th style="padding:2px 15px !important;">Email</th>';
	                		result = result +'             <th style="padding:2px 15px !important;">Role</th>';
	                		result = result +'             <th style="padding:2px 15px !important;">Active Jobs</th>';
	                		result = result +'           </tr>';
	                		result = result +'           </thead>';
		                    result = result +"<tbody>"; 
		                    var json = JSON.parse(data);
		                    var refreshIds = [];
		                    for (var i = 0; i < json.length; i++) {
		                    	 var opt = json[i]; 
		                    	 result = result +'<tr>';
		                    	 var localCheckedStatus = "";
		                    	 $(".defaultAssignCheckBox").each(function() {
         							var localValue = this.value;
         							if(localValue == opt.id){
         								localCheckedStatus = "checked";
         							}  
		                    	 });
		                    	 
		                    	 var lastName = '';
		                    	 if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
		                    		 lastName = ' '+opt.lastName;
		                    	 }
		                    	 var owner ='';
		         				 if(null != opt.isAssignedOwner && 'undefined' != opt.isAssignedOwner){
		         					 owner = opt.isAssignedOwner;
		         				 }
		         				 var assignedTeam = '';
		         				 if(null != opt.assignedTeam && 'undefined' != opt.assignedTeam && '' != opt.assignedTeam){
		         					 assignedTeam = opt.assignedTeam;
		         				 }
		                    	 
		                    	 if(ids.indexOf(''+opt.id) > -1){
		                    		 refreshIds.push(''+opt.id);
		                    		 result = result +'             <td><input id="'+opt.id+'"  class="myCheckbox" onclick="handleClick();" checked="checked" value="'+opt.id+','+opt.assignedTeamId+'"  type="checkbox" name="team" '+localCheckedStatus+' data-validation="required"></td>';
		                    	 }else if('' != assignedTeam){
		                    		 if(localCheckedStatus == "checked"){
			                    		 result = result +'  <td><i class="text-muted fa fa-check-square" aria-hidden="true"></i></td>';
		                    		 }else{
		                    		 	 result = result +'  <td><input id="'+opt.id+'"  class="myCheckbox" onclick="handleClick();" checked="checked" value="'+opt.id+','+opt.assignedTeamId+'"  type="checkbox" name="team" '+localCheckedStatus+' data-validation="required" ></td>';
		                    		 }
		                    	 }else if(localCheckedStatus == "checked"){
		                    		 result = result +'  <td><i class="text-muted fa fa-check-square" aria-hidden="true"></i></td>';
		                    		 //result = result +'  <td><input id="'+opt.id+'"  class="myCheckbox" onclick="handleClick();" value="'+opt.id+'"  type="checkbox" name="team" '+localCheckedStatus+' data-validation="required" ></td>';
		                    	 }else{
		                    		 if(localCheckedStatus == "checked"){
			                    		 result = result +'  <td><i class="text-muted fa fa-check-square" aria-hidden="true"></i></td>';
		                    		 }else{
		                    			 
		                    			 var selectedUserIdForAssign = "";
		                    			 var iteratedUserValue = opt.id+","+opt.assignedTeamId;
				                    	 $.each(selectedUsersForAssign, function (i, value)  
		                                 {  
				                    		 selectedUserIdForAssign = value;
		                                     if(selectedUserIdForAssign == iteratedUserValue){
		                                    	 localCheckedStatus = "checked";
		                                     }
		                                 });
		                    			 result = result +'  <td><input id="'+opt.id+'"  class="myCheckbox" onclick="handleClick();" value="'+opt.id+','+opt.assignedTeamId+'"  type="checkbox" name="team" '+localCheckedStatus+' data-validation="required" ></td>';
		                    		 }
		                    	 }
		                    	 
		                    	 result = result +'             <td><span class="tbldata-dotdot-me" title="'+opt.firstName+''+lastName+''+assignedTeam+''+owner+'">'+opt.firstName+''+lastName+''+assignedTeam+''+owner+'</span></td>';
		                    	 result = result +'             <td><span class="tbldata-dotdot" title="'+opt.email+'">'+opt.email+'</span></td>';
		                    	 result = result +'             <td><span class="tbldata-dotdot-100">'+opt.userType+'</span></td>';
		                    	 result = result +'             <td><span class="tbldata-dotdot-100">'+opt.assignJobCount+'</span></td>';
		                    	 result = result +'           </tr>';
	                       }
		                   ids =  refreshIds;
		                    
	                       result = result +" </tbody>";
	                       result = result +"</table>"; 
	                       result = result +'   <div class="clearfix"></div>'; 
	                 	   result = result +'   </div>'; 
	                 	   result = result +'	<span class="jquery_form_error_message" style="display:none;">Assign at least to a new member</span>';
	                 	   result = result +'   <div class="modal-footer mt">';
	                 	   result = result +'     <button class="btn btn-primary" type="button" id="assignbtnId" onClick="assignMembers();">Assign</button>';
	                 	   result = result +'     <button class="btn btn-default" type="button" id="cancelbtnId" onClick="clearSelections();">Clear</button>';
	                 	   result = result +'   </div>'; 
	                 	   $("#usersGrid").empty(); 
	                       $("#usersGrid").append(result); 
	                       pagination(); 
			    		}
                 	}else{
                	 	$("#usersGrid").removeAttr("style");
  	    	        	var result ="";
		        		result = result +'       <table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
		        		result = result +'           <thead>';
		        		result = result +'           <tr>';
		        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;"></th>';
		        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Name</th>';
		        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Email</th>';
		        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Role</th>';
		        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Active Jobs</th>';
		        		result = result +'           </tr>';
		        		result = result +'           </thead>';
		        		result = result +'           </table>';
		        		var refreshIds = [];
		        		ids =  refreshIds;
		        		
                 	    result = result +'   <div class="modal-footer mt">';
                 	    result = result +'     <button class="btn btn-default" type="button" id="cancelbtnId" onClick="clearSelections();">Cancel</button>';
                 	    result = result +'   </div>'; 
						$("#usersGrid").empty();
					    $("#usersGrid").append(result); 
					    pagination(); 
                	}
          		},
       		}); 
  	} 
</script>

<%-- 
     @author : venkat kesav
     this script is for get checked users.
     1.usage     : onload.
     declared 'ids' as a global variable for keep all checked user ids
--%>
<script>
window.ids = new Array();
function handleClick() {
	ids = [];
	$("input:checkbox[name=team]:checked").each(function(){
		$('span.jquery_form_error_message').hide();
		ids.push($(this).val());
	});
	if(ids.length < 1){
		$('span.jquery_form_error_message').show();
	}
}
</script>

<%-- 
     @author : venkat kesav
     this script is for get selected team(s) and role(s).
     1.usage     : onload.
     declared 'teamRoleIds' as a global variable for keep all selected role and teams
--%>
<script>
window.teamRoleIds = new Array();
function handleTeamRoleClick() {
	teamRoleIds = [];
	$("option[name=TeamAndRole]:selected").each(function(){
		teamRoleIds.push($(this).val());
	});
}
</script>

<%-- 
     @author : venkat kesav
     this script is for get Roles and Teams list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
     append Roles and Teams list to AssignedTo model dialog box
--%>
<script>
function getTeamsList(reqId){
	if(reqId == '' || reqId == 'undefined' || reqId == null){
		reqId = '';
	}
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getTeamsList?${_csrf.parameterName}=${_csrf.token}&reqId="+reqId,    
            success :function(data) {
            	var json = JSON.parse(data);
				var output = "";
			  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_TEAM\')">';
			  if(json.teams.length > 0){
				  output = output + "<optgroup label='Teams'>";
				  for (var i = 0; i < json.teams.length; i++) {
						var opt = json.teams[i];
						if('Y' == opt.isTeamAssigned){
							output = output + "<option name='TeamAndRole' value='"+opt.id+"' selected >"+ opt.name + "</option>";
						}else if($.inArray(''+opt.id, teamRoleIds) > -1){
							output = output + "<option name='TeamAndRole' value='"+opt.id+"' selected>"+ opt.name + "</option>";	
					    }else{
							output = output + "<option name='TeamAndRole' value='"+opt.id+"'>"+ opt.name + "</option>";	
						}

					}
				  output = output + "</optgroup>";
			  }
			  '</sec:authorize>';

			  if(json.roles.length > 0){
				  output = output + "<optgroup label='Roles'>"; 
					for (var i = 0; i < json.roles.length; i++) {
						var opt = json.roles[i];
						if('Y' == opt.isRoleAssigned){
							output = output + "<option name='TeamAndRole' value='"+opt.id+"role' selected >"+ opt.displayName + "</option>";
						}else if($.inArray(''+opt.id+'role', teamRoleIds) > -1){
							output = output + "<option name='TeamAndRole' value='"+opt.id+"role' selected>"+ opt.displayName + "</option>";
						}else{
							output = output + "<option name='TeamAndRole' value='"+opt.id+"role'>"+ opt.displayName + "</option>";	
						}
					}
					output = output + "</optgroup>";
			  }
				$("#rolesDiv").empty();
			    $("#rolesDiv").append(output);
			    $("#rolesDiv").selectpicker("refresh");
            	
            },
            complete: function (data) {
            	var teamType = $("#rolesDiv").val();
          		getUserList(teamType);
            }
            
      }); 
 } 
</script>

<script type="text/javascript">
	function filterTeamMembers(){
		var teamType = $("#rolesDiv").val();
		getUserList(teamType);
	}
</script>

<%-- 
     @author : venkat kesav
     this script is for clear uncheck all users.
     1.usage     : onload.
     clear all checked users when click on cancel and close buttons of AssignedTo model dialog
--%>
<script type="text/javascript">
	function clearSelections(){
		$('#cancelbtnId').attr("data-dismiss","modal");
		$('#closebtnId').attr("data-dismiss","modal");
		$("#assignedToSavingDiv").empty();
		teamRoleIds = [];
		$("#rolesDiv").empty();
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
    	"ordering": true,
    	"bPaginate":false,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
function assignMembers() {
	handleTeamRoleClick();
	var result = "";
	var oTable = $('#example').DataTable();
	var rowcollection =  oTable.$(".myCheckbox:checked", {"page": "all"});
	//var totalCount = $("[type='checkbox']:checked").length;
	var count = 1;
	var totalCount = oTable
    .rows()
    .nodes()
    .to$()
	.find('input[type="checkbox"].myCheckbox:checked').length;
	
	if(totalCount == 0){
		$('span.jquery_form_error_message').show();
	}else{
		$("#assignedToSavingDiv").empty();
		$('#assignbtnId').attr("data-dismiss","modal");
		rowcollection.each(function(index,elem){
		    var checkbox_value = $(elem).val();
		    result = result+ "<input type='hidden' class='defaultCheckBox' name='assignUserId"+count+"' id='assignUserId"+count+"' value='"+checkbox_value+"' />";
		    count++;
		});
		result = result+"<input type='hidden' name='assignedToMaxCount' id='assignedToMaxCount' value='"+count+"'/>";
		//$("#assignedToSavingDiv").empty();
		$("#assignedToSavingDiv").append(result);
		assignedToValidation();
		$("#assignedTeamId").val($("#rolesDiv").val());
	}
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
		        	output = output + "<option value=''>-Select Team Lead-</option>"; 
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
/* $("#selectContact").empty().trigger('chosen:updated');
$("#clientSubmissionForm").empty().trigger('chosen:updated'); */
var existingValue = document.getElementById('isClientJob').value;

	if (document.getElementById('isClientJob').checked){
		 $("#isClientJob").val('Y');
		 
		// $("#addClientId").val('');
		 $('#addClientId').trigger("chosen:updated");
		 
		 $("#selectContact").trigger('chosen:updated');
		
		 $("#clientCurrencyCodge").trigger('chosen:updated');
		 
		 $("#clientCurrencyCode").val('${currencyCode}').trigger('change');
		 
		 var isEdit = '${val}'
		 if(isEdit != 'Y'){
			$("#clientRateType").val("").trigger('change');
		 }
		 $('#clientDetails').find('p.jquery_form_error_message').remove();

		 /* $('select[name=clientCurrencyCodgetIsClientVale]').val(1);
		 $('.selectpicker').selectpicker('refresh');
		  */
		 //$('#clientRate').val('');
		 
		$("#clientRateType").trigger('chosen:updated');

		 /* $('select[name=clientRateType]').val(1);
		 $('.selectpicker').selectpicker('refresh');
		  */
		// $("#clientSubmissionForm").val('');
		 $('#clientSubmissionForm').trigger("chosen:updated");
		 existingValue = document.getElementById('isClientJob').value;
	}else {
		 $("#isClientJob").val('N');
		 
		 $("#addClientId").val('');
		 $('#addClientId').trigger("chosen:updated");
		 
		// $("#selectContact").find('.chosen-results').val('').trigger('chosen:updated');
		
		$("#selectContact").val('');
		$('#selectContact').trigger("chosen:updated");
		
		 
		/*  $('select[name=clientCurrencyCodgetIsClientVale]').val(1);
		 $('.selectpicker').selectpicker('refresh');
		  */
		  
		 // $("#clientCurrencyCode").val('');
			
		 $('#clientRate').val('');
		 $("#clientCurrencyCode").val('${currencyCode}').trigger('change');
		 $("#clientRateType").val("").trigger('change');
		 $('#clientDetails').find('p.jquery_form_error_message').remove();
		 
		 //$('select[name=clientRateType]').val(1);
		// $('.selectpicker').selectpicker('refresh');
		 $("#clientSubmissionForm").val('');
		 $('#clientSubmissionForm').trigger("chosen:updated");
		 
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
			        		if(opt.lastName != null && opt.lastName !='undefined'){
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"</option>";	
			        		}
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
 function validateJobId(jobId) {
	 
	 var requirementid = document.getElementById("requirementId").value;
	/*  if(requirementid != null && requirementid != '' && requirementid != 'undefined' ){ */
		 if(jobId == null || jobId == "" || jobId == "undefined"){
			 jobId = document.getElementById("jobId").value; 
		 }
		 var specialCharacter = jobId.match(/^[A-Za-z0-9][A-Z a-z 0-9]*$/);
		 $('#jobIdDiv').find('p.jquery_form_error_message').remove();
		 var sourceleadUrl ='${sourceleadUrl}';
		 if(jobId != null && jobId != "" && specialCharacter != null){
			 $("#draft").attr("disabled", "disabled");
			 $("#save").attr("disabled", "disabled");
			 $("#submitForApproval").attr("disabled", "disabled");
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/validateJobId?${_csrf.parameterName}=${_csrf.token}&jobId="+jobId+"&requirementid="+requirementid, 
			        success: function(data) {
			        	if(data == true){
							  $("input#jobId").after("<p class='jquery_form_error_message'>"+jobId+" is Already Existed</span>");
							  document.getElementById("jobId").setAttribute('record-exist','yes');
							  document.getElementById("jobId").setAttribute('record-exist-errorMsg',jobId+' is Already Existed'); 
			        	}
			        	else{
			        		 $('input#jobId').removeAttr( "record-exist record-exist-errormsg");
			        		//$('#jobIdDiv').find('p.jquery_form_error_message').remove(); 
	                         $("#draft").removeAttr("disabled");
	                         $("#save").removeAttr("disabled", "disabled");
	                		 $("#submitForApproval").removeAttr("disabled", "disabled");
			        	}
			        },
			    });
		 }
 }
 </script>     
 
 <script>
function toggle(source) {
    var checkboxes = document.querySelectorAll('input[name="team"]');
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
    
    if (parseFloat(maxExp) <= parseFloat(minExp)){
        $('#expId').find('p.jquery_form_error_message').remove(); 
		$('input#expValidation').attr('class','error signin_fild');
		$("input#expValidation").after("<p class='jquery_form_error_message'>Max Experience should be greater than Min Experience</p>");
		$('input#expValidation').attr('record-exist','yes');
		$('input#expValidation').attr('record-exist-errorMsg','Max Experience should be greater than Min Experience'); 
    }  else{
    	$('#expId').find('p.jquery_form_error_message').remove(); 
    	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#expId').find('p.jquery_form_error_message').remove();
    }
}

</script>


<script>
//Function to validate if maximum rate value is more the minimum rate value
function minMaxRate(){
    var maxRate = ($('#maxRate').val());
    var minRate = ($('#rate').val());
    
    if (parseFloat(maxRate) <= parseFloat(minRate)){
        $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
		$('input#rateValidation').attr('class','error signin_fild');
		$("input#rateValidation").after("<p class='jquery_form_error_message'>Max Rate should be greater than Min Rate</p>");
		$('input#rateValidation').attr('record-exist','yes');
		$('input#rateValidation').attr('record-exist-errorMsg','Max Rate should be greater than Min Rate'); 
    }  else{
    	$('#rateValidateId').find('p.jquery_form_error_message').remove(); 
    	$('input#rateValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#rateValidateId').find('p.jquery_form_error_message').remove();
    }
}

</script>

<script type="text/javascript">
function checkTemplateforDouplicate(){
	if(document.getElementById('isTemplate').checked) {
		var templateName = document.getElementById("jobTitle").value; 
		
		// This will allow all special chars
         var jsonData = encodeURIComponent(templateName);	  
       
		$('#jobTitleDiv').find('p.jquery_form_error_message').remove();
		var sourceleadUrl ='${sourceleadUrl}';
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/validatetemplateName?${_csrf.parameterName}=${_csrf.token}&templateName="+jsonData, 
			        success: function(data) {
			        	if(data == "true"){
			        		  $('#jobTitleDiv').find('p.jquery_form_error_message').remove();
							  $("input#jobTitle").after("<p class='jquery_form_error_message'><span>Template Already Exists</span>");
							  document.getElementById("jobTitle").setAttribute('record-exist','yes');
							  document.getElementById("jobTitle").setAttribute('record-exist-errorMsg','Template Already Exists'); 
			        	}
			        	else{
			        		 $('input#jobTitle').removeAttr( "record-exist record-exist-errormsg");
			        		// $('#jobTitleDiv').find('p.jquery_form_error_message').remove(); 
			        		 $("#requirement_form" ).addClass('button').val('Save');
		                    $("#requirement_form").removeAttr("disabled");
			        	}
			        },
			    });
	}else{
		$('input#jobTitle').removeAttr( "record-exist record-exist-errormsg");
		$('#jobTitleDiv').find('p.jquery_form_error_message').remove();
	}

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
function getJobType(jobType){
	var existingJobType = '${requirement.jobType}';
	if(jobType.value == 'Other'){
		 document.getElementById("jobTypeDiv").style.display="block";
		  $("#jobOtherType").attr("data-validation-optional", "true");
		  $("#jobOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		document.getElementById("jobTypeDiv").style.display="none";
		  $("#jobOtherType").attr("data-validation-optional", "true");
		  $("#jobTypeDiv").find('p.jquery_form_error_message').remove();
		  if(existingJobType ==null || existingJobType == "" || existingJobType == "undefined" || existingJobType != 'Other'){
			  $("#jobOtherType").val('');
		  }
		  
		  
		  if(jobType.value == 'C2C' || jobType.value == 'C2H' || jobType.value == 'Contract'){
			 
	
				 document.getElementById("projectDurationYearsDiv").style.display="block";
				 document.getElementById("projectDurationMonthsDiv").style.display="block";
				 
				 
			  
			}else{
				document.getElementById("projectDurationYearsDiv").style.display="none";
				document.getElementById("projectDurationMonthsDiv").style.display="none";
				
			}
		  
		  
		  
		  
		  
		  
	}
}
</script>




<!-- <script type="text/javascript">
function checkcheckbox(){
	var count = $("[type='checkbox']:checked").length;
	alert(count);
	if(count==0){
		alert("please select checkbox");
	}else{
		alert("selected checkbox"+count);
	}
}
</script> -->




<script>
function getNoticePeriodList(){
var notice = '${requirement.noticePeriod}';	
var splitNotice = notice.split(",");
for (var i = 0; i < splitNotice.length; i++) {
	splitNotice[i] = splitNotice[i].replace(/^\s*/, "").replace(/\s*$/, "");
}
	$("#noticePeriod").val(splitNotice).trigger("chosen:updated");
}
$('.chosen-select').each(function() {
	var $this = $(this);
	$this.next().css({'width': '100%'});
})
</script>

<script>
function getEditRequirementDetails(requirementId){
	
	var sourceleadUrl = '${sourceleadUrl}';
	if(templateId !=''){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/getEditRequirementDetails?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,
			success : function(data) {
				var json = JSON.parse(data);
				$(".Editor-container").find('.Editor-editor').html(json['description']);
			},
		}); 
	}
	
}
</script>

<script type="text/javascript">
 function validateTeamName() {
	 var name = $('#name').val(); 
	 $('#teamNameDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(name != null && name != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateTeamName?${_csrf.parameterName}=${_csrf.token}&name="+name, 
		        success: function(data) {
		        	if(data == 'true'){
						  
		        		  $("input#name").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
						  $("#add_team").attr("disabled", "disabled");
						  $("#add_team").addClass('button').val('Processing..');
		        	}
		        	else{
		        		
		        		 $('input#name').removeAttr("record-exist record-exist-errormsg");
		        		 $('#teamNameDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#add_team" ).addClass('button').val('Save');
                         $("#add_team").removeAttr("disabled");
		        	}
		        },
		    });
	 } 
 }
 </script> 

<script type="text/javascript">
$(document).ready(function(){
	var requirementId  = "${requirementId}";
	getEditRequirementDetails(requirementId);
	
});
</script>
 
<jsp:include page="../masterFooter.jsp" />