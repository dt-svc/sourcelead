<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- these files for export -->
<link href="https://cdn.datatables.net/buttons/1.2.4/css/buttons.dataTables.min.css" rel="stylesheet">
<script src="//code.jquery.com/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdn.datatables.net/buttons/1.2.4/js/dataTables.buttons.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.flash.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.html5.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.print.min.js" type="text/javascript" charset="utf-8"></script>


 <style type="text/css">
 .require_wrap_details_cta b {
    width: 110px;
}
.top_o{
top:0 !important;
}
 </style>

<script>
$(document).ready(function() {
	 var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	 var requirementStatus = '${requirementStatus}'; 
	 professionalDashboardPortletListCounts(startDate,endDate,requirementStatus);
	 professionalDashboardPortletList(startDate,endDate,requirementStatus);
	 
	 var applicantStatus = "";
	 if(requirementStatus == "SHORTLIST"){
		 applicantStatus ="Shortlist";
	 }
	 if(requirementStatus == "OFFERMADE"){
		  applicantStatus = "Offered";
	 }
	 if(requirementStatus == "INTERVIEW"){
		  applicantStatus = "Interviews";
	 }
	 if(requirementStatus == "REJECTED"){
		  applicantStatus = "Rejected";
	 }
	 if(requirementStatus == "" || requirementStatus =='undefined'  || requirementStatus == null){
		  applicantStatus = "Applied";
	 }
	 $(".applicantStatus").append(applicantStatus); 
	 
});	
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Referred To Me</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='professionalDashboard'/>">Home</a> » <span class="applicantStatus"></span>&nbsp; List </div>
  </section>
  <section class="clearfix">
  <div class="timesheet_out timesheet_background crowdDash_btn">
	<div class="timesheet_head pull-left zIndex col-md-8 paddingBtm">
  	      <h4 class="pull-left"> <span class="applicantStatus"></span> List</h4>
  	      <div id ="statusDropDown" class="pull-right mt10"></div>
  	</div>
  	<div id="professionalDashboardPortletList"></div>
  </div>
  </section>
<script>
function myFunction(){
	var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	 var requirementStatus = $("#navyOp").val();
	 $(".applicantStatus").empty('');
	 var applicantStatus = "";
	 if(requirementStatus == "SHORTLIST"){
		 applicantStatus ="Shortlist";
	 }
	 if(requirementStatus == "OFFERMADE"){
		  applicantStatus = "Offered";
	 }
	 if(requirementStatus == "INTERVIEW"){
		  applicantStatus = "Interviews";
	 }
	 if(requirementStatus == "REJECTED"){
		  applicantStatus = "Rejected";
	 }
	 if(requirementStatus == "" || requirementStatus =='undefined'  || requirementStatus == null){
		  applicantStatus = "Applied";
	 }
	 $(".applicantStatus").append(applicantStatus); 
	 
	
    professionalDashboardPortletList(startDate,endDate,requirementStatus);
}

</script>  
<script>
function portletListFilter(requirementStatus){
	 var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	
	professionalDashboardPortletList(startDate,endDate,requirementStatus);
  
}

</script>	
   
   
   
   
<script type="text/javascript">
  function professionalDashboardPortletListCounts(startDate,endDate,requirementStatus){
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({ 
		   	type: "GET",
			url: sourceleadUrl+"/getJobSummaryPortlets?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	    	 var json = JSON.parse(data);
	    	 var result ='';
	    	 result =result +'<span class="select-field"><i class="fa fa-filter"></i>';
	    	 result =result +'<select name = "navyOp" id="navyOp" onChange="myFunction(this);">'; 
	    	 result =result +'<option '+ (requirementStatus == "" ? "selected" : '') +' value = "">Applied ('+json.appliedJobs+')</span></option>';
	    	 result =result +'<option '+ (requirementStatus == "SHORTLIST" ? "selected" : '') +' value = "SHORTLIST">Short Listed ('+json.shortlistJobs+')</option>';
	    	 result =result +'<option '+ (requirementStatus == "INTERVIEW" ? "selected" : '') +' value = "INTERVIEW">Interviews ('+json.interviews+') </option>';
	    	 result =result +'<option '+ (requirementStatus == "OFFERMADE" ? "selected" : '') +' value = "OFFERMADE">Offered ('+json.offeredJobs+') </option>';
	    	 result =result +'<option '+ (requirementStatus == "REJECTED" ? "selected" : '') +' value = "REJECTED">Rejected ('+json.rejectedJobs+') </option>';
	    	 result =result +'</select>';
	    	 result =result +'</span>';
	    	   $("#statusDropDown").empty();
	           $("#statusDropDown").append(result);
		    }
	  });
	 
  }
  </script>
   
   
   
            
  <script type="text/javascript">
  function professionalDashboardPortletList(startDate,endDate,requirementStatus){
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getJobSummaryPortletsList?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&requirementStatus="+requirementStatus,
		    success: function(data) {
	    	 var json = JSON.parse(data);
	    	 var result =""; 
	    	   result = result + '<div class="require_wrap_table overflow-data filter-position">';
	    	   result = result + '<div class="clerfix">';
	    	   result = result + '<table id="professionalDashboardPortletPagenation" class="display" cellspacing="0" width="100%"">';
	           result = result + '<thead>';
	           result = result + '<tr>';
	           result = result + '<th>Name</th>';
	           result = result + '<th>Email</th>';
	           result = result + '<th>Ph</th>';
	           result = result + '<th>Req. Name</th>';
	           result = result + '<th>Req. Status</th>';
	           result = result + '<th>Referred on</th>';
		       result = result + '<th>Applicant Status</th>';
	           result = result + '</tr>';
	           result = result + '</thead>';
	           result = result + '<tbody>';
	           for (var i=0; i<json.crowdSourcingPortLetList.length; i++)
  				{
	        	   var report = json.crowdSourcingPortLetList[i];
	        	   
	        	   if(report.requirementJobId != null && report.requirementJobId != '' && report.requirementJobId != 'undefined'){
	        		   requirementJobId = report.requirementJobId +' - ';
	        	   }else{
	        		   requirementJobId = "";
	        	   }
	        	   result = result +'         <tr>';
	        	   result = result +'         <td><div class="tbldata-dotdot">'+report.candidateName+'</div></td>';
	        	   result = result +'         <td><div class="tbldata-dotdot-vsm">'+report.candidateEmail+'</div></td>';
	        	   result = result +'         <td><div class="tbldata-dotdot-100">'+report.phoneNumber+'</div></td>';
	        	   result = result +'         <td><a href="<c:url value="/requirementProfile/'+report.requirementUUID+'"/>"><div class="tbldata-dotdot-sm">'+requirementJobId+' '+report.requirementName+'</div></a></td>';
	        	   result = result +'         <td><div class="tblminwidth-50">'+report.requirementSubmissionStatus+'</div></td>';
	        	   result = result +'         <td><div class="tblminwidth-50">'+report.createdDateStr+'</div></td>';
	        	   result = result +'         <td><div class="tblminwidth-50">'+report.submissionStatus+'</div></td>';
	        	   result = result +'         </tr>';
	        	   
  				}
	    	 
	           result = result + '       </tbody>';
	           result = result + '     </table>';
	           result = result + '   </div>';
	           $("#professionalDashboardPortletList").empty();
	           $("#professionalDashboardPortletList").append(result);
	    	 rejectedPagenatio();
		    }
	  });
	 
  }
  </script>

  </section>

<script>
function rejectedPagenatio(){  
    $('#professionalDashboardPortletPagenation').DataTable( { 
    	dom: 'Bfrtip',
        buttons: [
            'excel', 'pdf'
        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
<script>
//Dropdown Pop
$(function(){
	$('#referred_pop').click(function(){
		$("#exportPop").toggle();
	});
});
$('html').click(function(){
	$(".referred_pop").hide();
});
$('#referred_pop, #exportPop, .referred_pop').click(function(e){
	e.stopPropagation();
});
</script>

<jsp:include page="../masterFooter.jsp" />