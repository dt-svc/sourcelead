<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <style type="text/css">
 .require_wrap_details_cta b {
    width: 110px;
}
 </style>

<script>
$(document).ready(function() {
	 var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	 var requirementStatus = '${requirementStatus}'; 
	 
	 professionalDashboardPortletList(startDate,endDate,requirementStatus);
});	
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Portlet Details</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='professionalDashboard'/>">Home</a> » Portlet List</div>
  </section>
  <section class="clearfix">
  <div class="timesheet_out timesheet_background">
	<div class="timesheet_head pull-right zIndex">
  	
  	<ul>
                <li id='referred_pop'><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i>|
                	<div id="exportPop" class="referred_pop">
                    	<ul>
                        	<li><input id="c5" type="radio" name="referred"  onclick="portletListFilter('');"><label for="c5">Applied</label></li>
							<li><input id="c6" type="radio" name="referred"  onclick="portletListFilter('SHORTLIST');"><label for="c6">Short Listed</label></li>
							<li><input id="c7" name="referred" type="radio"  onclick="portletListFilter('INTERVIEW');"><label for="c7">Interviews</label></li>
							<li><input id="c8" type="radio" name="referred"  onclick="portletListFilter('OFFERMADE');"><label for="c8">Offered</label></li>
               				<li><input id="c9" type="radio" name="referred"  onclick="portletListFilter('REJECTED');"><label for="c8">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>
                <div class="clearfix"></div>
            </ul>
  	
  	
  	
  	</div>
  	<div id="rejectList"></div>
  </div>
  </section>
<script>
function portletListFilter(requirementStatus){
	 var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	professionalDashboardPortletList(startDate,endDate,requirementStatus);
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
	    	   result = result + '<table id="rejectedPagenation" class="display" cellspacing="0" width="100%"">';
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
	        	   
	        	   if(report.requirementId != null && report.requirementId != '' && report.requirementId != 'undefined'){
	        		   requirementId = report.requirementId +' - ';
	        	   }else{
	        		   requirementId = "";
	        	   }
	        	   result = result +'         <tr>';
	        	   result = result +'         <td><div class="tbldata-dotdot">'+report.candidateName+'</div></td>';
	        	   result = result +'         <td><div class="tbldata-dotdot-vsm">'+report.candidateEmail+'</div></td>';
	        	   result = result +'         <td><div class="tbldata-dotdot-100">'+report.phoneNumber+'</div></td>';
	        	   result = result +'         <td><div class="tbldata-dotdot-sm">'+requirementId+' '+report.requirementName+'</div></td>';
	        	   result = result +'         <td><div class="tblminwidth-50">'+report.requirementSubmissionStatus+'</div></td>';
	        	   result = result +'         <td><div class="tblminwidth-50">'+report.createdDateStr+'</div></td>';
	        	   result = result +'         <td><div class="tblminwidth-50">'+report.submissionStatus+'</div></td>';
	        	   result = result +'         </tr>';
	        	   
  				}
	    	 
	           result = result + '       </tbody>';
	           result = result + '     </table>';
	           result = result + '   </div>';
	           $("#rejectList").empty();
	           $("#rejectList").append(result);
	    	 rejectedPagenatio();
		    }
	  });
	 
  }
  </script>

  </section>

<script>
function rejectedPagenatio(){  
    $('#rejectedPagenation').DataTable( { 
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