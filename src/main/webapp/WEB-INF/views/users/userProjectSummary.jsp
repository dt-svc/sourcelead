<%-- 
     @author : Rakshith.
     this script is for gettting projects list based on assigned user.
     1.usage     : 
     2.parameter : csrf token .
     3.ajax url implementation in UserRestController.
--%>
<script>
function userAssignedProjects(userId){  
	var sourceleadUrl ='${sourceleadUrl}';
	var orgLocationId ='${SourceLeadWeb_orgLocationId}';
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/getAssignedProjectsListForUser?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId,    
            success :function(data) {
                	prepareAssignedProjectsData(data);
          },
            
       }); 
  } 
</script>

<%-- 
     @author : Rakshith.
     this script is for prepare projects list data in user summary.
--%>

<script>
function prepareAssignedProjectsData(data){
	
	var sourceleadUrl ='${sourceleadUrl}';

	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  assignedProjectsDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  assignedProjectsDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  assignedProjectsDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  assignedProjectsDate = 'YYYY-DD-MM'; 
	  }  
	  
	var result = "";
	result = result +"<table id='assignedProjectsListPagination' class='display' cellspacing='0' width='100%' >"; 
    result = result +"<thead>"; 
    result = result +" <tr>"; 
    result = result +"    <th>Project Id</th>"; 
    result = result +"    <th>Project Name</th>"; 
    result = result +"    <th>Rate</th>"; 
    result = result +"    <th>Start Date <br>(<small class='custom-small'>"+assignedProjectsDate+"</small>)</th>";
    result = result +"    <th>End Date <br>(<small class='custom-small'>"+assignedProjectsDate+"</small>)</th>";
    result = result +"    <th>Approved Hours</th>"; 
    result = result +"    <th>Rejected Hours</th>"; 
    result = result +"    <th>Waiting For Approval</th>"; 
    result = result +"    <th>Saved Hours</th>";
    result = result +"    <th>Claimed Expense</th>"; 
    result = result +"	  <th>Status</th>";
    result = result +"  </tr>"; 
    result = result +"</thead>";
    result = result +"<tbody>"; 
        
   for (var i = 0; i < data.length; i++) { 
    var opt = data[i];
   	result = result +"<tr>"; 
   	 
   	result = result +"<td>";
   	if(opt.id != '' && opt.id != null && opt.id != 'undefined'){
	   	result = result +"<div class='tbldata-dotdot_50' title='"+opt.id+"'>"+opt.id+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.projectName != '' && opt.projectName != null && opt.projectName != 'undefined'){
   		result = result +"<div class='proj_show'><a href='"+sourceleadUrl+"/projectSummary?projectUUID="+opt.projectUUID+"'><span title='"+opt.projectName+"'>"+opt.projectName+"</span></a><i class='fa fa-binoculars'></i></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.flatPercent != '' && opt.flatPercent != null && opt.flatPercent != 'undefined'){
	   	result = result +"<div class='tbldata-dotdot_50' title='"+opt.flatPercent+"'>"+opt.flatPercent+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.projectAssignedDateStr != '' && opt.projectAssignedDateStr != null && opt.projectAssignedDateStr != 'undefined'){
	   	result = result +"<div class='tblminwidth-75' title='"+opt.projectAssignedDateStr+"'>"+opt.projectAssignedDateStr+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>"; 
   	}
	result = result +"</td>";
	
	result = result +"<td>";
	if(opt.projectEndDateStr != '' && opt.projectEndDateStr != null && opt.projectEndDateStr != 'undefined'){
   		result = result +"	    <div><input type='text' value='"+opt.projectEndDateStr+"' class='project_inline_field project_inline_field_enddate' id='endDate"+opt.id+"' name='endDate'  readonly='readonly'></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.approvedHrs != '' && opt.approvedHrs != null && opt.approvedHrs != 'undefined'){
	   	result = result +"<div class='tbldata-dotdot_50' title='"+opt.approvedHrs+"'>"+opt.approvedHrs+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.rejectedHrs != '' && opt.rejectedHrs != null && opt.rejectedHrs != 'undefined'){
	   	result = result +"<div class='tbldata-dotdot_50' title='"+opt.rejectedHrs+"'>"+opt.rejectedHrs+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.submittedHrs != '' && opt.submittedHrs != null && opt.submittedHrs != 'undefined'){
	   	result = result +"<div class='tbldata-dotdot_50' title='"+opt.submittedHrs+"'>"+opt.submittedHrs+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.draftHrs != '' && opt.draftHrs != null && opt.draftHrs != 'undefined'){
	   	result = result +"<div class='tbldata-dotdot_50' title='"+opt.draftHrs+"'>"+opt.draftHrs+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.userExpensesClaimedAmount != '' && opt.userExpensesClaimedAmount != null && opt.userExpensesClaimedAmount != 'undefined'){
	   	result = result +"<div class='tbldata-dotdot_50' title='"+opt.userExpensesClaimedAmount+"'>"+opt.userExpensesClaimedAmount+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.projectStatus != '' && opt.projectStatus != null && opt.projectStatus != 'undefined'){
   		if(opt.projectStatus == "CLOSED"){
	   	result = result +"<div class='tbldata-dotdot_80' title='In-Active'>In-Active</span></div>";
   		}else{
   			result = result +"<div class='tbldata-dotdot_80' title='Active'>Active</span></div>";
   		}
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result + " </tr>";
	
   }
    result = result +"</tbody>";
    result = result +"</table>";
    $("#tab-1").empty(); 
    $("#tab-2").empty(); 
    $("#tab-2").append(result);  
    pagination(); 

    
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatEduSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatEduSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatEduSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatEduSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatEduSave = 'yy-dd-mm'; 
	  }

    for (var i = 0; i < data.length; i++) { 
        var opt = data[i];
      //Assign date of project member
		 var selected = opt.projectAssignedDateStr;
		 var from = selected.split("-");
		 //var dt = new Date(from[2], from[1] - 1, from[0]);
	 if(locationDate.match("yyyy-MM-dd")){
        	 var dt = new Date(from[0], from[1] - 1, from[2]);
		    }if(locationDate.match("dd-MM-yyyy")){
		    	var dt = new Date(from[2], from[1] - 1, from[0]);
		    }if(locationDate.match("MM-dd-yyyy")){
		    	var dt = new Date(from[2], from[0] - 1, from[1]);
		    }if(locationDate.match("yyyy-dd-MM")){
		    	var dt = new Date(from[0], from[2] - 1, from[1]);
		    } 
         dt.setDate(dt.getDate());
        //Main project end date
         var projectEndDate = opt.memberEnddate;
         var endDate = "";
         /* if(projectEndDate != null && projectEndDate != "" && projectEndDate != 'undefined'){
        	 var fromEnd = projectEndDate.split("-");
        	 endDate = new Date(fromEnd[2], fromEnd[1] - 1, fromEnd[0]);
	             endDate.setDate(endDate.getDate()); 
         }
          */
          if(projectEndDate != null && projectEndDate != "" && projectEndDate != 'undefined'){
        	  var proEndDateTP = projectEndDate.split("-");
         if(locationDate.match("yyyy-MM-dd")){
        	 var endDate = new Date(proEndDateTP[0], proEndDateTP[1] - 1, proEndDateTP[2]);
		    }if(locationDate.match("dd-MM-yyyy")){
		    	var endDate = new Date(proEndDateTP[2], proEndDateTP[1] - 1, proEndDateTP[0]);
		    }if(locationDate.match("MM-dd-yyyy")){
		    	var endDate = new Date(proEndDateTP[2], proEndDateTP[0] - 1, proEndDateTP[1]);
		    }if(locationDate.match("yyyy-dd-MM")){
		    	var endDate = new Date(proEndDateTP[0], proEndDateTP[2] - 1, proEndDateTP[1]);
		    }
          }
          
        $('#endDate'+opt.id).datepicker({
  	      dateFormat: finalDateFormatEduSave,
  	      changeMonth : true,
  	      changeYear : true,
  	      minDate: dt,
  	      maxDate: endDate,
  	      showOn: 'focus',
          dateFormat: finalDateFormatEduSave,
          showButtonPanel: true,
          closeText: 'Clear',
  	       onClose: function() {
  	        	 var id = $(this).attr('id');
  				 var projectMemberId = $(this).attr('id').split('endDate')[1];
  	        	 var event = arguments.callee.caller.caller.arguments[0];
  	                // If "Clear" gets clicked, then really clear it
  	                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
  	                    $(this).val('');
  	                }
  	                inlineEditForEndDate(id,projectMemberId);
  	      }  
  	  });  
    }
    
}
</script>
<script> 
function inlineEditForEndDate(id,projectMemberId){
	var sourceleadUrl = '${sourceleadUrl}';
	 var endDate = document.getElementById(id).value;
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateEndDateByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&endDate="+endDate+"&projectMemberId="+projectMemberId, 	   
   		    success: function(data) {
   			   
   		    }
	 });  
}
</script>
<script>
function pagination(){ 

    $('#assignedProjectsListPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[7,'desc']],
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $('#assignedProjectsListPagination').wrap("<div class='overflow_Auto width100percnt'></div>")
}
function projectSummaryRedirection(){
	var sourceleadUrl ='${sourceleadUrl}'; 
	window.location.href = sourceleadUrl+"/projectDashboard";
}
</script>
