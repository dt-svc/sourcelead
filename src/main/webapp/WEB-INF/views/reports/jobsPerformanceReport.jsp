<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    <div class="pull-left">
      <h2 class="pull-left">Jobs Report</h2>
    </div>
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Jobs Report  </div>
    </section>
       
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
	      <div class="positionRelative reportHead clearfix">
	      <span class="pull-left refreIcon" id="advanceSearchDiv">
	      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="refreshList();"></a> 
	      </span>
	       <a href="javascript:void(0)" id="columPop" title="Column Chooser" class="columIcon mt10"></a>
	       <div class="colPop">
	<div class="colPopMid coldropdown-parent">
	<div class="clearfix">
		<div class="row">
				<div class="col-md-10 mb10 mt5 col-md-offset-2">
				<span class="checkbox-new  pull-left mr">
					<input id="columnsPickerUnCheckAll" class="radio-custom" name="columnsPicker" onclick="checkAllColumns(this);"  type="radio">
					<label for="columnsPickerUnCheckAll" class="radio-custom-label">Uncheck All</label>
				</span>
				<span class="checkbox-new pull-left">
					<input id="columnsPickerCheckAll" class="radio-custom" name="columnsPicker" onclick="checkAllColumns(this);"  type="radio" checked="">
					<label for="columnsPickerCheckAll" class="radio-custom-label" >Check All</label>
				</span>
			</div>
		</div>
	<div class="clearfix"></div>
		<div class="coldropdown-inner">
		<ul>
			<li>
				<span class="checkbox-new clearfix">
					<input id="a" class="checkbox-custom toggle-vis" name="paidComponent" data-column="0" type="checkbox" checked="">
					<label for="a" class="checkbox-custom-label" >Job Id</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="b" class="checkbox-custom toggle-vis" name="paidComponent" data-column="1" type="checkbox" checked="">
					<label for="b" class="checkbox-custom-label" >Job Title</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="c" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" checked="">
					<label for="c" class="checkbox-custom-label" >Client</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="d" class="checkbox-custom toggle-vis" name="paidComponent" data-column="3" type="checkbox" checked="">
					<label for="d" class="checkbox-custom-label" >Location</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="e" class="checkbox-custom toggle-vis" name="paidComponent" data-column="4" type="checkbox" checked="">
					<label for="e" class="checkbox-custom-label" >Priority</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="f" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox" checked="">
					<label for="f" class="checkbox-custom-label" >Applicants</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="g" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6"  type="checkbox" checked="">
					<label for="g" class="checkbox-custom-label" >Shortlisted</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="h" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="7" checked="">
					<label for="h" class="checkbox-custom-label" >Interview</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="i" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="8" checked="">
					<label for="i" class="checkbox-custom-label" >Offered</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="9" checked="">
					<label for="j" class="checkbox-custom-label" >SubmitToClient</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="k" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="10" checked="">
					<label for="k" class="checkbox-custom-label" >Backout</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="l" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="11" checked="">
					<label for="l" class="checkbox-custom-label" >Joined</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="m" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="12" checked="">
					<label for="m" class="checkbox-custom-label" >Rejected</label>
				</span>
			</li>
		</ul>
		</div>
	</div>
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="columHide();">Apply</a>
		<a href="javascript:void(0)" onclick="columClose();">Cancel</a>
	</div>
</div>				
</div>
	       
	       
	       
	      <ul class="pull-right list-unstyled list-inline">
					<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openFilterPop();"></a></li>
					
					<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
						<div class="newActPop" style="display: none;">
							<span>
			           	   		<a href="javascript:void(0)" onclick="jobsExport('Pdf');" title="PDF">Export PDF</a>
								<a href="javascript:void(0)" onclick="jobsExport('Excel');" title="Excel">Export Excel</a>            
							</span>
						</div>
					</li>
			  <li class=""><input type="text" class="repSearchBar" placeholder="Search" id="dataTableSearch"><span class="repSearchIcon" onclick="filter();"></span></li>
	        </ul>
	      </div>
        <div class="timesheet_head">
      
       </div>
        <div class="clients_wrap_table datatable_filter">
        
        <div id="previousOfferDiv"></div>
        
        
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
    
    
  </section>
  
  
  
  
  
  <script>
  var hideColumns = [];
  var table = "";
 $(document).ready(function() {
	 requirementJoinedList();
	 $('label.toggle-vis').on('click', function (e) {
         e.preventDefault();
         // Get the column API object
         var column = table.column($(this).attr('data-column'));
         $(this).toggleClass("active");
         // Toggle the visibility
         column.visible(!column.visible());
     });
 });
 
 </script>
 <script type="text/javascript">
 
 $("#columPop").click(function() {
	 $(".colPop").show();
 })
 $("html").click(function(){
		$(".colPop").hide();
	});
	$("#columPop, .colPop").click(function(e){
	 	e.stopPropagation();
	});
	function columClose(){
		$(".colPop").hide();
	}

	function checkAllColumns(obj){
		var id = obj.id;
		if(id =='columnsPickerCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',true);
		}
		else if(id =='columnsPickerUnCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',false);
		}
	}
	
	function columHide(){
		hideColumns=[]; // reset the array object.
		$(".toggle-vis").each(function() {
			columClose();// 
		   var chkdVal= $(this).prop('checked');
		   var column = table.column($(this).attr('data-column'));
			  if(chkdVal == true){
			         column.visible(true);
			    }
			    else{
			      column.visible(false);
			      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
			  }
	    });
		$(".colPop").hide();
	   
	}
	function refreshList(){
		location.reload();
	}	
</script>
 
 <script type="text/javascript">
function requirementJoinedList(){
var sourceleadUrl = '${sourceleadUrl}';
var jobCreatedDateRange = null;
var jobType = null;
var startDateStr = null;
var endDateStr = null;
var clientIds =[];
var returnStat = true;
	$('input:radio[name=jobCustomDays]:checked').each(function() {
		jobCreatedDateRange = $(this).val();
		if(jobCreatedDateRange == 'custom'){
			startDateStr = $("#jobStartDate").val();
			endDateStr = $("#jobEndDate").val();
			$("#errStartDate").find('p.jquery_form_error_message').remove();
			$("#errEndDate").find('p.jquery_form_error_message').remove();
			if(startDateStr == null || startDateStr == '' || startDateStr == 'undefined'){
				$("input#jobStartDate") .after( "<p class='jquery_form_error_message'>Start Date is Required</span>");
				returnStat =  false;
			}
			if(endDateStr == null || endDateStr == '' || endDateStr == 'undefined'){
				$("input#jobEndDate") .after( "<p class='jquery_form_error_message'>End Date is Required</span>");
				returnStat =  false;
			}
		}
	});
	$('input:checkbox[name=clientNameById]:checked').each(function() 
	{
	clientIds.push($(this).val());
	});
	$('input:radio[name=jobsRadio]:checked').each(function() 
	{
		jobType=($(this).val());
	});
	var searchValue = $("#dataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	if(returnStat){
		   	var url= sourceleadUrl+"/getJobsPerformancetListByOrgLocationIDAndUserId?${_csrf.parameterName}=${_csrf.token}&jobCreatedDateRange="+jobCreatedDateRange+"&startDateStr="+startDateStr+"&endDateStr="+endDateStr+"&clientIds="+clientIds+"&jobType="+jobType+"&searchValue="+searchValue;
		   	prepareOfferList(url);
		}
}
</script> 


<script>
function prepareOfferList(url){
        var result = "";
        result = result +'<table id="jobsPerformanceId" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Job Id</th>';
		result = result + '<th>Job Title</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Location</th>';
		result = result + '<th>Priority</th>';
		result = result + '<th>Applicants</th>';
		result = result + '<th>Shortlisted</th>';
		result = result + '<th>Interview</th>';
		result = result + '<th>Offered</th>';
		result = result + '<th>Submit To Client</th>';
		result = result + '<th>Backout</th>';
		result = result + '<th>Joined</th>';
		result = result + '<th>Rejected</th>';
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'</table>';
        $("#previousOfferDiv").empty();
        $("#previousOfferDiv").append(result);
         offerpagination(url);
}
</script>

<script>
function offerpagination(url){
	var colIndex = 0;
	table = $('#jobsPerformanceId').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "jobId" },
            { "data": "jobTitle"},
            { "data": "client"},
            { "data": "location"},
            { "data": "priority"},
            { "data": "applicants"},
            { "data": "shortlisted"},
            { "data": "interview"},
            { "data": "offered"},
            { "data": "submitToClient"},
            { "data": "backOut"},
            { "data": "joined"},
            { "data": "rejected"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "jobId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 if(opt.jobId !=null && opt.jobId !="" && opt.jobId !='undefined'){
						result = result + ' <td><div class="tblminwidth-100">'+opt.jobId+'</div></td>';
	    	  		 }else{
	            		  result = result + '<td><div class="tblminwidth-100">---</div></td>'; 
	            	  }
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "jobTitle",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
						result = result + ' <td><div class="tblminwidth-175">'+opt.generatedId+'-'+opt.jobTitle+'</div></td>';
            	  return result;
              		
              }
	          },
	      
	      {
	    	  "targets": colIndex++,
              "data": "client",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.clientName !=null && opt.clientName !="" && opt.clientName !='undefined'){
						result = result + ' <td><div class="tbldata-dotdot">'+opt.clientName+'</div></td>';
            	  }else{
            		  result = result + '<td><div class="tblminwidth-100">---</div></td>'; 
            	  }
            	  return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "location",
            "sortable": false,
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	   result = result +'<td>';
  		        	result = result +'<div class="positionRelative">';
	   		         for(var k=0; k<1; k++){
	   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.locationsList[k]+'</p>';
	   		         }
  		    	
	   		        result = result +'<div class="show_Empdetails showjoblist">';
  		        	result = result +'<i class="fa fa-ellipsis-v"></i>';

  		        	result = result +'<div class="emailvalues emailvaluesNew">';
  		        	
						for(var k=0; k<opt.locationsList.length; k++){
							result = result +'<p><i class="fa fa-map-marker"></i>'+opt.locationsList[k]+'</p>';
		   		        	
  		    	 	}
  		        	result = result +'</div>';
  		        	result = result +'</div>';
  

		        	result = result +'</div>';
		        	result = result +'</td>';
   				return result;
            }
          } ,{
              "targets": colIndex++,
              "data": "priority",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	    	  		result = result + ' <td><div class="tblminwidth-100">'+opt.priority+'</div></td>';
	    	  		return result;
              }
            },
          {
                "targets": colIndex++,
                "data": "applicants",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.assignedMembersCount >0){
						result = result + ' <td><div class="tblminwidth-100"><a onClick="loadJobAllApplicants('+opt.id+')">'+opt.assignedMembersCount+'</a></div></td>';
                		}else{
    					result = result + ' <td><div class="tblminwidth-100">'+opt.assignedMembersCount+'</div></td>';
                		}
    	              return result;
                }
          } ,{
                      "targets": colIndex++,
                      "data": "shortlisted",
                      "sortable": false,
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
                    	  if(opt.shortlistCount >0){
      	    	  			result = result + ' <td><div class="tblminwidth-100"><a onClick="loadJobShortlistCandidates('+opt.id+');">'+opt.shortlistCount+'</a></div></td>';
                    	  }else{
          					result = result + ' <td><div class="tblminwidth-100">'+opt.shortlistCount+'</div></td>';
                    	  }
      	    	  			return result;
                      }
               },{
                     "targets": colIndex++,
                     "data": "interview",
                     "sortable": false,
                     "render": function ( data, type, opt, meta ) {
                   	  var result = "";
                   	  if(opt.interviewCount>0){
     	    	  		result = result + ' <td><div class="tblminwidth-100"><a onClick="loadJobInterviewCandidates('+opt.id+')">'+opt.interviewCount+'</a></div></td>';
                   	  }else{
                   		result = result + ' <td><div class="tblminwidth-100">'+opt.interviewCount+'</div></td>';  
                   	  }
     	    	  		return result;
                     }
                   } ,{
                       "targets": colIndex++,
                       "data": "offered",
                       "sortable": false,
                       "render": function ( data, type, opt, meta ) {
                     	  var result = "";
                     	 if(opt.offersCount>0){
       	    	  			result = result + ' <td><div class="tblminwidth-100"><a onClick="loadJobOfferedCandidates('+opt.id+')">'+opt.offersCount+'</a></div></td>';
                     	 }else{
                     		result = result + ' <td><div class="tblminwidth-100">'+opt.offersCount+'</div></td>';  	 
                     	 }
       	    	  			return result;
                       }
                },{
                    "targets": colIndex++,
                    "data": "submitToClient",
                    "sortable": false,
                    "render": function ( data, type, opt, meta ) {
                  	  var result = "";
                  	if(opt.submitToClientCount>0){
    	    	  			result = result + ' <td><div class="tblminwidth-100"><a onClick="loadJobSubmitToClientCandidates('+opt.id+')">'+opt.submitToClientCount+'</a></div></td>';
                  	}else{
                 		result = result + ' <td><div class="tblminwidth-100">'+opt.submitToClientCount+'</div></td>';  	 
                  	}
    	    	  		return result;
                    }
             },{
                 "targets": colIndex++,
                 "data": "backOut",
                 "sortable": false,
                 "render": function ( data, type, opt, meta ) {
               	  var result = "";
               	if(opt.backoutCount>0){
 	    	  			result = result + ' <td><div class="tblminwidth-100"><a onClick="loadJobBackOutCandidates('+opt.id+')">'+opt.backoutCount+'</a></div></td>';
               	}else{
             		result = result + ' <td><div class="tblminwidth-100">'+opt.backoutCount+'</div></td>';  	 
               	}
 	    	  		return result;
                 }
          },{
                 "targets": colIndex++,
                 "data": "joined",
                 "sortable": false,
                 "render": function ( data, type, opt, meta ) {
               	  var result = "";
               	  if(opt.joinedCount>0){
 	    	  			result = result + ' <td><div class="tblminwidth-100"><a onClick="loadJobJoinedCandidates('+opt.id+')">'+opt.joinedCount+'</a></div></td>';
               	  }else{
               		result = result + ' <td><div class="tblminwidth-100">'+opt.joinedCount+'</div></td>';  	 
               	  }
 	    	  		return result;
                 }
          },{
              "targets": colIndex++,
              "data": "rejected",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.rejectedCount>0){
	    	  			result = result + ' <td><div class="tblminwidth-100"><a onClick="loadJobRejectedCandidates('+opt.id+')">'+opt.rejectedCount+'</a></div></td>';
            	  }else{
                 		result = result + ' <td><div class="tblminwidth-100">'+opt.rejectedCount+'</div></td>';  	 
            	  }
	    	  			return result;
              }
       }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
 	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    closeFilterPop();
    $("#jobsPerformanceId").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   requirementJoinedList();
    		   for (var i=0; i<hideColumns.length; i++) {
          		 table.column(hideColumns[i]).visible(false); // hide the columns after filters also.
          		}
    	   }
    	});
}

</script>

   <script type="text/javascript">
   function loadJobAllApplicants(requirementId){
		var tab = $("#dateActiveTab").val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/jobPerformanceCandidatesByStatus?&tab="+tab+"&orgLocationId="+orgLocId+"&statusType=All&requirementId="+requirementId;
	}
 function loadJobInterviewCandidates(requirementId){
		var tab = $("#dateActiveTab").val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/jobPerformanceCandidatesByStatus?&tab="+tab+"&orgLocationId="+orgLocId+"&statusType=Interview&requirementId="+requirementId;
	}
 function loadJobShortlistCandidates(requirementId){
		var tab = $("#dateActiveTab").val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/jobPerformanceCandidatesByStatus?&tab="+tab+"&orgLocationId="+orgLocId+"&statusType=Shortlisted&requirementId="+requirementId;
	}
 function loadJobOfferedCandidates(requirementId){
		var tab = $("#dateActiveTab").val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/jobPerformanceCandidatesByStatus?&tab="+tab+"&orgLocationId="+orgLocId+"&statusType=Offered&requirementId="+requirementId;
	}
 
 function loadJobSubmitToClientCandidates(requirementId){
		var tab = $("#dateActiveTab").val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/jobPerformanceCandidatesByStatus?&tab="+tab+"&orgLocationId="+orgLocId+"&statusType=SubmitToClient&requirementId="+requirementId;
	}
function loadJobBackOutCandidates(requirementId){
		var tab = $("#dateActiveTab").val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/jobPerformanceCandidatesByStatus?&tab="+tab+"&orgLocationId="+orgLocId+"&statusType=BackOut&requirementId="+requirementId;
	}
function loadJobJoinedCandidates(requirementId){
		var tab = $("#dateActiveTab").val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/jobPerformanceCandidatesByStatus?&tab="+tab+"&orgLocationId="+orgLocId+"&statusType=Joined&requirementId="+requirementId;
	}
	
function loadJobRejectedCandidates(requirementId){
	var tab = $("#dateActiveTab").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocId = '${SourceLeadWeb_orgLocationId}';
	window.location.href = sourceleadUrl + "/jobPerformanceCandidatesByStatus?&tab="+tab+"&orgLocationId="+orgLocId+"&statusType=Rejected&requirementId="+requirementId;
}
</script>
 
   <!-- ========================== Filter popup Written By Laxman ===============================  -->
<script>
function closeFilterPop(){
	$("#filterPopupId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openFilterPop(){
	 $("#filterPopupId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function resetFilterPopup(){
	$("ul#myClientUL > li input:checkbox").prop("checked",false);
	document.getElementById("jobDateRadio").checked = true;
	document.getElementById("jobsRadio").checked = true;
	customJobsList();
}
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	

function customJobsList() {
    var selectVal = $('input[name=jobCustomDays]:checked').val();
    if (selectVal == "custom") {
    	var result = '';
    	result  = result + '<div class="col-md-6 paddingLeft" id="errStartDate">';                                                                       
    	result = result +'     <input type="text" class="form-control input-sm calendar" onclick="showStartDate();" id="jobStartDate" placeholder="Start Date"> ';
    	result = result +'   </div> ';
    	result = result +'   <div class="col-md-6 noPad" id="errEndDate">';
    	result = result +'     <input type="text" class="form-control input-sm calendar" onclick="showEndDate();" id="jobEndDate" placeholder="End Date">';
    	result = result +'   </div>';
        $(".custom_search").empty().append(result);
        $(".custom_search").show();
    }else {
        $(".custom_search").empty().hide();
    }
}

function showStartDate(){
    
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
    var finalDateFormat = getJSDateFormat(locationDate);
	
	$('#jobStartDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : finalDateFormat,
		onSelect : function(selected) {
			 var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
			$("#jobEndDate").datepicker("option", "minDate", dt);
		}
	}).datepicker('show');;
}
	
function showEndDate(){
   var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
   var finalDateFormat = getJSDateFormat(locationDate);
   
	$('#jobEndDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : finalDateFormat,
		beforeShow: function(){
	           this.value='';
	     },
		onSelect : function(selected) {
			   var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
			$("#jobStartDate").datepicker("option", "maxDate", dt);
		}
	}).datepicker('show');;
}

</script>
   
   <div class="advancedsearch-mask"></div>
     <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small" id="filterPopupId">
				<h5 class="headerAdsrch">Filter
					<a href="javascript:void(0)" class="close-div" onClick="closeFilterPop();"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
				
				<div class="col-md-4">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myClientInput" onkeyup="myClientFunction()" placeholder="Clients" title="Type in a Client">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myClientUL">
									<c:forEach var="clientsList" items="${orgClientList}">  
									
									<c:if test="${clientsList.locationId.id == loginOrgLocId}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="clientNameById" value ="${clientsList.id}" ><label>${clientsList.responderLocationId.org.orgName}</label></li>
									</c:if>
									
									<c:if test="${clientsList.responderLocationId.id == loginOrgLocId}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="clientNameById" value ="${clientsList.id}" ><label>${clientsList.locationId.org.orgName}</label></li>
									</c:if>
									
									</c:forEach>
								</ul>
							 </div> 
						</div>
				
				<div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Job Posted From</strong></label>
								 <div class="clearfix positionRelative">
									<ul class="list-unstyled" id="postedDateFilter">
                                    <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" checked="checked" id="jobDateRadio" value="month" name="jobCustomDays"><label for="onboard2">Last 30 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="twomonths" name="jobCustomDays"><label for="onboard2">Last 60 Days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="threemonths" name="jobCustomDays"><label for="onboard2">Last 90 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="sixmonths" name="jobCustomDays"><label for="onboard2">Last 180 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="oneyear" name="jobCustomDays"><label for="onboard2">Last 365 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="custom" name="jobCustomDays"><label for="onboard2">Custom</label></li>
			                        </ul>
						              <div class="custom_search popCusDate">
						            </div>
							      </div>
						     </div> 
						  </div>
						</div>
						
						<div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Jobs</strong></label>
								 <div class="clearfix positionRelative">
									<ul class="list-unstyled" id="jobsFilter">
                                    <li class="checkBox positionRelative" ><input type="radio" checked="checked" id="jobsRadio" value="all" name="jobsRadio"><label for="onboard2">All</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" value="client" name="jobsRadio"><label for="onboard2">Client Jobs</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" value="internal" name="jobsRadio"><label for="onboard2">Internal Jobs</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
				
						
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="requirementJoinedList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetFilterPopup();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>     

<script>
function getClientList(){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/getClientList?${_csrf.parameterName}=${_csrf.token}",
		success : function(data) {
			var result = "";
			  for (var i=0; i<data.length; i++)
				{
				var report = data[i];
				result = result + '<li class="checkBox positionRelative">';
				result = result + '<input  type="checkbox" name="clientNameById" value ='+report.orgRelId+'><label>'+report.clientName+'</label>';
				result = result + '</li>';
				}
			  $("#myClinetListUL").empty();
			  $("#myClinetListUL").append(result);

		}
	});
}
function myClientFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myClientInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myClientUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

function getTeamsList(){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/getTeamsListForLoginUser?${_csrf.parameterName}=${_csrf.token}",
		success : function(data) {
			var result = "";
			  for (var i=0; i<data.length; i++)
				{
					var report = data[i];
					result = result + '<li class="checkBox positionRelative">';
					result = result + '<input  type="checkbox" name="teamNameById" value ='+report.id+'><label>'+report.name+'</label>';
					result = result + '</li>';
				}
			  $("#myTeamsListUL").empty();
			  $("#myTeamsListUL").append(result);
		}
	});
}

function myTeamsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTeamsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTeamsListUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
</script>


<!-- =============== Pdf and  Excel Export scripts writen by Laxman =================== -->
<script>
function jobsExport(type){
	if(type == "Pdf"){
		exportType = "pdf";
	}else{
		exportType = "xls";
	}
	var sourceleadUrl = '${sourceleadUrl}';
	var jobCreatedDateRange = null;
	var jobType = null;
	var startDateStr = null;
	var endDateStr = null;
	var clientIds =[];
		$('input:radio[name=jobCustomDays]:checked').each(function() {
			jobCreatedDateRange = $(this).val();
			if(jobCreatedDateRange == 'custom'){
				startDateStr = $("#jobStartDate").val();
				endDateStr = $("#jobEndDate").val();
				$("#errStartDate").find('p.jquery_form_error_message').remove();
				$("#errEndDate").find('p.jquery_form_error_message').remove();
				if(startDateStr == null || startDateStr == '' || startDateStr == 'undefined'){
					$("input#jobStartDate") .after( "<p class='jquery_form_error_message'>Start Date is Required</span>");
				}
				if(endDateStr == null || endDateStr == '' || endDateStr == 'undefined'){
					$("input#jobEndDate") .after( "<p class='jquery_form_error_message'>End Date is Required</span>");
				}
			}
		});
		$('input:checkbox[name=clientNameById]:checked').each(function() 
		{
		clientIds.push($(this).val());
		});
		$('input:radio[name=jobsRadio]:checked').each(function() 
		{
			jobType=($(this).val());
		});
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		window.location.href = sourceleadUrl+"/getJobsPerformancetListExportByOrgLocationIDAndUserId?jobCreatedDateRange="+jobCreatedDateRange+"&startDateStr="+startDateStr+"&endDateStr="+endDateStr+"&clientIds="+clientIds+"&jobType="+jobType+"&searchValue="+searchValue+"&exportType="+exportType;

// 		 window.location.href = sourceleadUrl+"/applicantsJoinedListExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&exportType="+exportType;
	     
}
</script>
	
<jsp:include page="../masterFooter.jsp" />
