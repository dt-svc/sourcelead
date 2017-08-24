<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Area -->
<script type="text/javascript">
window.onload = function() { 
	getDayWiseReports();
	}
</script>

<%-- 
     @author : prasad K.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<input type="hidden" id="noCandidates" name="noCandidates"/>
<script>
  function getCandidateList(){ 	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  var candidateType = 'Candidate';
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getCandidateReportsListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateType="+candidateType,       
            success :function(data) {
            	prepareCandidateReportsDataDiv(data);
           },
            
       }); 
  } 
</script>

 <script>
  function prepareCandidateReportsDataDiv(data){
	  var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';	     
	  $("#candidateList").removeAttr("style");
	     	 $("#candidateList").empty();
	    	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	   	  if(locationList.match("yyyy-MM-dd")){
	   		  reqListDate = 'YYYY-MM-DD';
	   	  }if(locationList.match("dd-MM-yyyy")){
	   		  reqListDate = 'DD-MM-YYYY'; 
	   	  }if(locationList.match("MM-dd-yyyy")){
	   		  reqListDate = 'MM-DD-YYYY'; 
	   	  }if(locationList.match("yyyy-dd-MM")){
	   		  reqListDate = 'YYYY-DD-MM'; 
	   	  }  
	   	
	     	 var result = "";
	     	  result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
	          result = result +"<thead>"; 
	          result = result +"  <tr>"; 
	          result = result +"    <th>Name</th>"; 
	          result = result +"    <th>Mobile</th>";
	          result = result +"    <th>Primary Skill</th>";
	          result = result +"    <th>Experience</th>"; 
	          result = result +"    <th>No of Jobss</th>"; 
	          result = result +"    <th>Owner</th>"; 
	          result = result +"    <th>Last Update <br>(<small class='custom-small'>"+reqListDate+"</small>)</th>";
	          result = result +"  </tr>"; 
	          result = result +"</thead>";
	          result = result +"<tbody>"; 
	          var json = JSON.parse(data);
	          for (var i = 0; i < json.length; i++) { 
	         	 var opt = json[i]; 
	         	 var mobilePhone = "";
	         	 var designation ="";
	         	 var address="";
	         	 var designationType = "";
	         	 var candidateUpdateDateToString ="";
	         	 var candidateRequiementCount ="";
	         	        
	         	      if(opt.mobilePhone != null && opt.mobilePhone != 'undefined' && opt.mobilePhone != ""){
	         		       mobilePhone = opt.mobilePhone;
		            	}else{
		            		mobilePhone = "----";
		            	}
		            	
		            	if(opt.address != null && opt.address != 'undefined' && opt.address != ""){
		            		address = opt.address;
		            	}else{
		            		address = "----";
		            	}
		            	if(opt.candidateUpdateDateToString != null && opt.candidateUpdateDateToString != 'undefined' && opt.candidateUpdateDateToString != ""){
		            		candidateUpdateDateToString = opt.candidateUpdateDateToString; 
		            	}else{
		            		candidateUpdateDateToString = "---";
		            	} 
		            	
		            	var Experience = "---";
		            	if(opt.experienceYears != null && opt.experienceYears !='' && opt.experienceYears != 'undefined'){
		            		Experience = opt.experienceYears;
		            		if(opt.experienceMonths != null && opt.experienceMonths !='' && opt.experienceMonths != 'undefined'){
		            			Experience = Experience+"-"+opt.experienceMonths;
		            		}
		            	}else{
		            		
		            	}
		            	
		            	if(opt.lastName != null && opt.lastName != "" && opt.lastName != 'undefined'){
		            		lastName = opt.lastName;
		            	}else{
		            		lastName = "";
		            	}
		            	
	                result = result +"<tr>";
	                result = result +"  <td><div class='proj_show divmax-width' ><span title='"+opt.firstName+" "+lastName+"'>"+opt.firstName+" "+lastName+"</span><i class='fa fa-binoculars'></i></div></td>";
	                result = result +"  <td><div>"+mobilePhone+"</div></td>";
	              
		              if(opt.primarySkill != null && opt.primarySkill != '' && opt.primarySkill != "undefined")
		             	 {
		                   result = result +"  <td><div><span title='"+opt.primarySkill+"'>"+opt.primarySkill+"</span></div></td>";
		             	 }else{
		             		 result = result +"  <td><div>----</div></td>"; 
		             	 }
		              result = result +"  <td><div><span title='"+Experience+"'>"+Experience+"</span></div></td>";
	                 
		              result = result +"  <td><div ><span >"+opt.candidateRequiementCount+"</span></div></td>";
		              
		              if(opt.createdBy != null && opt.createdBy !='' & opt.createdBy !='undefined'){
		              result = result +"  <td><div><span title='"+opt.createdBy+"'>"+opt.createdBy+"</span></div></td>";
		              }else{
		            	  result = result +"  <td><div>----</div></td>";  
		              }
		              result = result +"  <td><div class='tblminwidth-125'>"+candidateUpdateDateToString+"</div></td>";
		              result = result +"</tr>"; 
	          }
	          result = result +" </tbody>";
	          result = result +"</table>";
	          $("#noCandidates").val('');
	          $("#candidateList").append(result);
	          cpagination(SourceLeadWeb_loginOrg);  
	 
  }
</script>
<%-- 
     @author : prasad K.
     this script is for prepare pagination to candidateList.
     1.usage     : used in candidateList() function.
--%>


<script>
function cpagination(SourceLeadWeb_loginOrg){ 
    $('#candidatePagination').DataTable( { 
    	 dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'excel',
	                title: SourceLeadWeb_loginOrg+' - Added Candidates Reports',
	            },
	            {
	                extend: 'pdf',
	                title: SourceLeadWeb_loginOrg+' - Added Candidates Reports',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
    $(".vendor_wrap_inn_table").find('a.buttons-excel').prop("title", "Export to Excel");
	$(".vendor_wrap_inn_table").find('a.buttons-pdf').prop("title", "Export to PDF");
}
</script> 

<script>
	$(function() {
		
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatreports = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatreports = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatreports = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatreports = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatreports = 'yy-dd-mm'; 
		  }
		
		$('#ExportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatreports,
			onSelect : function(selected) {
				var dt = new Date(selected);
				//var dt = toDate("#ExportStartDate");
				//var dt = $(this).datepicker('getDate', '+1d');
				//dt.setDate(dt.getDate() + 1);
				$("#ExportEndDate").datepicker("option", "minDate", selected);
				addValidClass();
			}
		
		});
		
		$('#ExportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatreports,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				//var dt = toDate("#ExportEndDate");
				//var dt = $(this).datepicker('getDate', '-1d');
			//	dt.setDate(dt.getDate() - 1);
				$("#ExportStartDate").datepicker("option", "maxDate", selected);
				addValidClass();
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

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2 class="pull-left">Reports</h2>
		 <div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i>Added Candidates</a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Posted</a></li>
					<li><a href="<c:url value='/requirementStagesListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Stages</a></li>
<%-- 					<li><a href="<c:url value='/applicantsReceivedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Received</a></li> --%>
<%-- 				    <li><a href="<c:url value='/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li> --%>
				    <li><a href="<c:url value='/applicantsSubmittedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Submitted</a></li>
<%-- 				    <li><a href="<c:url value='/requirementAssignToReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Assign To</a></li> --%>
				    <li><a href="<c:url value='/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				    <li><a href="<c:url value='/timeSheetReports'/>"><span><i class="fa fa-briefcase"></i></span>TimeSheet Reports</a></li>
				</ul>
			</div>
		</div>  
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&nbsp;&raquo;&nbsp;<a href="<c:url value='/recruitersReports'/>">Reports</a>&nbsp;&raquo; Added Candidates </div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">

      <div class="timesheet_head">

<div class="pull-left reportBtns reportcutom_Btns">

	  <div class="reportBtn dropdown-select">
		<select class="form-control" id="reportTypeId" onChange="flushCustomDates(); getDayWiseReports(); custDates();">
<!-- 		 <option value="">select</option> -->
		  <option value="today">Today</option>
		  <option value="yesterday">Yesterday</option>
		  <option value="week">1 week</option>
		  <option value="twoweeks">2 weeks</option>
		  <option value="month">1 month</option>
		  <option value="twomonths">2 months</option>
		   <option value="custom">Custom</option> 
		</select>
	 </div>	
	 <div class="cusShow pull-left">
	 <span class="dashboard-datepkr">
	 <input type="text" class="form-control" value="" id="ExportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
	 <p id="reportStartDate" class="ReError"></p>
	 </span>
	<span class="dashboard-datepkr">
	<input type="text"  class="form-control" value="" id="ExportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<p id="reportEndDate" class="ReError"></p>
	</span>
	<span><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getCustomReports();" /></span>
	</div>
		<!-- <span class="repoBtns">
		<a href="javascript:void(0)" class="oneA actRE"><i class="fa fa-list"></i></a>
		<a href="javascript:void(0)" class="oneB"><i class="fa fa-pie-chart"></i></a>
	</span> -->
</div>
                     
        <ul style="padding: 2px 300px 2px 0px;">
         
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
      </div>
    
   <div class="reportA">
          <label>Owner</label>
          <div class="reportAChoo">
           <select multiple data-placeholder="Select Owner" class="chosen-select"   name="user" id="user" >
                <c:forEach var="user" items="${ownersList}">
            			  <option value="${user.userCode}">${user.firstName} ${user.lastName}</option>
                </c:forEach>
                </select>
          </div>
          
          <input type="button" value="Submit" class="btn btn-primary rePBT"  id="owner"  onclick="getDayWiseReports();getCandidatesByOwners();"/>
			<div class="clearfix"></div>		
		</div>
    
   <div class="repTab1">   
	<div class="reportsCount">
		<ul>
			<li title="Candidates">
				<span><i class="fa fa-users"></i></span><b>${candidateCount}</b>
			</li>
			
<!-- 			commented by pavan jampana -->
			<%-- <li title="Associates" class="colorTwo">
				<span><i class="fa fa-file-text"></i></span><b>${consultantCount}</b>
			</li> --%>
			
			<div class="clearfix"></div>
		</ul>
	</div>

  <div class="project_tabs_inn_table vendor_wrap_inn_table">
                 <div id="candidateList" class="repoPositiAs"></div>
                </div>      
                
    </div>            
      
 </div>


  </div>
</section>
<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

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
<script type="text/javascript">
function getCandidatesByOwners(){

	    var sourceleadUrl = '${sourceleadUrl}';
		var ownersList = $("#user").val();
		var reportType = "Candidate";
 		 
 		$.ajax({
 	         type : "POST",
				url: sourceleadUrl+"/getOwnersByCandidates?${_csrf.parameterName}=${_csrf.token}&ownersList="+ownersList+"&reportType="+reportType,
 	         success :function(data) {
  		        	 prepareCandidateReportsDataDiv(data);
  	        },
 	          
 	     }); 
}

</script>
 		
 		
<script>
function reset(){
	$(".chosen-select").val('').trigger("chosen:updated");
}

</script> 		
 		

<script type="text/javascript">
	function flushCustomDates() {
		$('#ExportStartDate').val('');
		$('#ExportEndDate').val('');
		$.datepicker._clearDate('#ExportStartDate');
		$.datepicker._clearDate('#ExportEndDate');
	}
</script>
<script>
function getDayWiseReports(){
	$('#reportStartDate').empty();
	$('#reportEndDate').empty(); 
	
	var ownersList = $("#user").val();
	var userList = "";
	if(ownersList != null && ownersList != ''){
		userList = ownersList;
	}
	 var reportType = $("#reportTypeId").val(); 
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(reportType != "custom"){
	$.ajax({
         type : "POST",
         url: sourceleadUrl+"/getCandidateReports?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate+"&ownersList="+userList,     
         success :function(data) {
	        	 prepareCandidateReportsDataDiv(data);
        },
          
     });  
	}
	
}

</script>
<script>
function custDates(){
	var reportType = $("#reportTypeId").val();
	if(reportType=="custom"){
	    $(".cusShow").show();
	}
	if(reportType!="custom"){
	    $(".cusShow").hide();
	}
}
</script>

<script>
function getCustomReports(){
	
	var ownersList = $("#user").val();
	var userList = "";
	if(ownersList != null && ownersList != ''){
		userList = ownersList;
	}
	var reportType = $("#reportTypeId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#reportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#reportEndDate").empty().append("End Date is Required") ; 
	}else{
		$.ajax({
	         type : "POST",
	         url: sourceleadUrl+"/getCandidateReports?${_csrf.parameterName}=${_csrf.token}&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate+"&ownersList="+userList,     
	         success :function(data) {
		        	 prepareCandidateReportsDataDiv(data);
	        },
	          
	     });  
	}
	 
}
</script>
<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("ExportStartDate").value;
		 if(StartDate){
			 $('#reportStartDate').empty() 
		 }
		 
		 var EndDate = document.getElementById("ExportEndDate").value;
		 if(EndDate){
			 $('#reportEndDate').empty(); 
		 }
}
</script>

<jsp:include page="../masterFooter.jsp" /> 