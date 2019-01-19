<%-- 
     @author : Rakshith.
     this script is for gettting projects list based on assigned user.
     1.usage     : 
     2.parameter : csrf token .
     3.ajax url implementation in UserRestController.
--%>
<script>
function userAssignedProjects(userId){  
	var orgLocationId ='${SourceLeadWeb_orgLocationId}';
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/getAssignedProjectsListForUser?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&orgLocationId="+orgLocationId+"&fromType=Associate",    
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
    result = result +"    <th>Project Name</th>"; 
    result = result +"    <th>Start Date</th>";
    result = result +"    <th>End Date</th>"; 
    result = result +"    <th>Bill Rate</th>"; 
    result = result +"    <th>Pay Rate</th>"; 
    result = result +"    <th>Estimated hrs</th>"; 
    result = result +"    <th>Estimated Budget</th>";
    result = result +"    <th>Actual hrs</th>"; 
    result = result +"	  <th>Status</th>";
    result = result +"  </tr>"; 
    result = result +"</thead>";
    result = result +"<tbody>"; 
        
   for (var i = 0; i < data.length; i++) { 
    var opt = data[i];
    
    
    if(opt.currentBillRate != null && opt.currentBillRate != 'undefined' && opt.currentBillRate != ''){
		billRate = opt.billRateCurrency+" "+opt.currentBillRate+" /"+opt.rateType
	}else{
		billRate = "--";
	}
	
	if(opt.payRate != null && opt.payRate != 'undefined' && opt.payRate != ''){
		payRate = opt.payRateCurrency+" "+opt.payRate+" /"+opt.payRateType
	}else{
		payRate = "---";
	}
	
	if(opt.estimatedHours != null && opt.estimatedHours != 'undefined' && opt.estimatedHours != ''){
		estimatedHours = opt.estimatedHours;
	}else{
		estimatedHours = "--";
	}
	
	if(opt.estimatedBudget != null && opt.estimatedBudget != 'undefined' && opt.estimatedBudget != ''){
		estimatedBudget = opt.estimatedBudget;
	}else{
		estimatedBudget = "--";
	}
	
   	result = result +"<tr>"; 
 
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.projectName != '' && opt.projectName != null && opt.projectName != 'undefined'){
   		result = result +"<div class='tbldata-dotdot'><a href='"+sourceleadUrl+"/projectSummary?projectUUID="+opt.projectUUID+"&projectClientUUID="+opt.projectClientUUID+"'><span title='"+opt.projectName+"'>"+opt.projectName+"</span></a></div>";
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
	   	result = result +"<div class='tblminwidth-75' title='"+opt.projectEndDateStr+"'>"+opt.projectEndDateStr+"</span></div>";
   	}else{
   		result = result +"<div>-----</div>"; 
   	}
	result = result +"</td>";
	
	result = result +"<td>";
	   	result = result +"<div class='tbldata-dotdot-vsm' title='"+billRate+"'>"+billRate+"</span></div>";
	result = result +"</td>";
	
	result = result +"<td>";
	   	result = result +"<div class='tbldata-dotdot-vsm' title='"+payRate+"'>"+payRate+"</span></div>";
	result = result +"</td>";
	
	result = result +"<td>";
	   	result = result +"<div class='tbldata-dotdot_50' title='"+estimatedHours+"'>"+estimatedHours+"</span></div>";
	result = result +"</td>";
	
	result = result +"<td>";
	   	result = result +"<div class='tbldata-dotdot_50' title='"+estimatedBudget+"'>"+estimatedBudget+"</span></div>";
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.approvedHrs != '' && opt.approvedHrs != null && opt.approvedHrs != 'undefined'){
	   	result = result +"<div class='tbldata-dotdot_50' title='"+opt.approvedHrs+"'>"+opt.approvedHrs+"</span></div>";
   	}else{
   		result = result +"<div>0.0</div>";
   	}
	result = result +"</td>";
	
	result = result +"<td>";
   	if(opt.projectStatus != '' && opt.projectStatus != null && opt.projectStatus != 'undefined'){
   		if(opt.projectStatus == 'CLOSED'){
   			result = result +"<div class='tbldata-dotdot_50' title='Inactive'>Inactive</span></div>";
   		}else{
   			result = result +"<div class='tbldata-dotdot_50' title='Active'>Active</span></div>";
   		}
   	}else{
   		result = result +"<div>-----</div>";
   	}
	result = result +"</td>";
	
	result = result + " </tr>";
	
   }
    result = result +"</tbody>";
    result = result +"</table>";
    $("#candidateSideDiv").empty();
    $("#tab-18").empty(); 
    $("#tab-17").empty(); 
    $("#tab-19").empty();
    $("#tab-25").empty();
    $("#tab-19").append(result);  
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
	 var endDate = document.getElementById(id).value;
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateEndDateByProjectMemberId?"+tokenParamName+"="+tokenValue+"&endDate="+endDate+"&projectMemberId="+projectMemberId, 	   
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
	window.location.href = sourceleadUrl+"/projectDashboard";
}
</script>
