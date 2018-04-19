<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	 
	  var candidateType = 'Candidate';
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getCandidateReportsListByOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateType="+candidateType,       
            success :function(response) {
            	if (response.response === "AWKWARD") {
            	    location.href = "/errorPage";
            	} else if (response.response === "SUCCESS") {
            		prepareCandidateReportsDataDiv(response.data);            		
            	} else if (response.indexOf('Sign In') !== -1) {
            		location.replace(sourceleadUrl);
            	} else {
            	    showError("Operation failed, please try again");
            	}
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
	    	 var reqListDate= getOrgDateFormat(locationList);
	     	 var result = "";
	     	  result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
	          result = result +"<thead>"; 
	          result = result +"  <tr>"; 
	          result = result +"    <th>Name</th>"; 
	          result = result +"    <th>Mobile</th>";
	          result = result +"    <th>Primary Skill</th>";
	          result = result +"    <th>Experience</th>"; 
	          result = result +"    <th>No of Jobs</th>"; 
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
	                result = result +"  <td><div class='proj_show divmax-width' ><div class='text-center tbldata-dotdot-sm pull-left' title='"+opt.firstName+" "+lastName+"'>"+opt.firstName+" "+lastName+"</div></div></td>";
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
	          $("#candidateLoderDiv").empty();
	          $("#candidateLoderDiv").removeAttr('style');
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
    	"bLengthChange": true,
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
		  var finalDateFormatreports = getJSDateFormat(locationDate);
		
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
      <h2 class="pull-left">Candidate Reports</h2>
		  
    </div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; Added Candidate Reports </div>
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
	 <p id="reportStartDate" class="ReError" style="display: none"></p>
	 </span>
	<span class="dashboard-datepkr">
	<input type="text"  class="form-control" value="" id="ExportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<p id="reportEndDate" class="ReError" style="display: none"></p>
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
   <div id="candidateLoderDiv"></div>
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
	var ownersList = $("#user").val();
	var reportType = "Candidate";
	$.ajax({
		type : "POST",
		url: sourceleadUrl+"/getOwnersByCandidates?"+tokenParamName+"="+tokenValue+"&ownersList="+ownersList+"&reportType="+reportType,
	    success :function(response) {
        	if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
        		prepareCandidateReportsDataDiv(response.data);
        	} else if (response.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
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
	document.getElementById("reportStartDate").style.display="none";
	document.getElementById("reportEndDate").style.display="none";
	var ownersList = $("#user").val();
	var userList = "";
	if(ownersList != null && ownersList != ''){
		userList = ownersList;
	}
	 var reportType = $("#reportTypeId").val(); 
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#ExportEndDate").val();
	
	if(reportType != "custom"){
		$("#candidateLoderDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
         type : "POST",
         url: sourceleadUrl+"/getCandidateReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate+"&ownersList="+userList,     
         success :function(response) {
        	 	if (response.response === "AWKWARD") {
	       		    location.href = "/errorPage";
	       		} else if (response.response === "SUCCESS") {
	       			prepareCandidateReportsDataDiv(response.data);
	       		} else if (response.indexOf('Sign In') !== -1) {
	       			location.replace(sourceleadUrl);
	       		} else {
	       		    showError("Operation failed, please try again");
	       		}
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
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		document.getElementById("reportStartDate").style.display="block";
		$("#reportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		document.getElementById("reportEndDate").style.display="block";
		$("#reportEndDate").empty().append("End Date is Required") ; 
	}else{
		$("#candidateLoderDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
	         type : "POST",
	         url: sourceleadUrl+"/getCandidateReports?"+tokenParamName+"="+tokenValue+"&reportType="+reportType+"&startDate="+startDate+"&endDate="+endDate+"&ownersList="+userList,     
	         success :function(response) {
	        	 	if (response.response === "AWKWARD") {
	        		    location.href = "/errorPage";
	        		} else if (response.response === "SUCCESS") {
	        			 prepareCandidateReportsDataDiv(response.data);
	        		} else if (response.indexOf('Sign In') !== -1) {
	        			location.replace(sourceleadUrl);
	        		} else {
	        		    showError("Operation failed, please try again");
	        		}
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