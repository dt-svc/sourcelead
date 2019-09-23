<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<script>
function educationSubmissionDetails(candidateId){
	var result = "";
	result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">';
	/* result = result	+ '<div class="project_tabs_inn_head"> <input class="vendorfild" ></div>'; */
	result = result + '   <div id="educationSubmissionDetailsList" class="datatable_filter mt5"></div>';
	result = result + '   </div>';
	
	$("#tabb-1").empty();
	$("#tabb-2").empty();
	$("#tabb-3").empty();
	$("#tabb-4").empty();
	$("#tabb-5").empty();
	$("#tabb-6").empty();
	$("#tabb-5").append(result);
	
	educationSubmissionDetailsList(candidateId);
    
}
</script>

<script>
function educationSubmissionDetailsList(candidateId){
	 
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  eduSubListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  eduSubListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  eduSubListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  eduSubListDate = 'YYYY-DD-MM'; 
	  }  
	  
	 
	 $.ajax({  
          type : "GET",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#educationSubmissionDetailsList").empty();
               	 	var result = "";
               	 	result = result +"<table id='educationSubmissionPagination' class='display' cellspacing='0' width='100%' >"; 
                    result = result +"<thead>"; 
                    result = result +"  <tr>"; 
                    result  = result + '           <th>Education</th>';
                	 result  = result + '           <th>Specialization</th>';
                	 result  = result + '           <th>Start date <br>(<small class="custom-small">'+eduSubListDate+'</small>)</th>';
                	 result  = result + '           <th>End date <br>(<small class="custom-small">'+eduSubListDate+'</small>)</th>';
                	 result  = result + '           <th>Score</th>';
                	 result  = result + '           <th>Score Type</th>';
                	 result  = result + '           <th>Institute/University</th>';
                	 result  = result + '           <th>Address</th>';
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                    for (var i = 0; i < response.data.length; i++) { 
                   	 var opt = response.data[i];  
                   	 result  = result + '         <tr>';
                   	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
                   	 result  = result + '<td><div><span title="'+opt.name+'">'+opt.name+'</span></div></td>';
                   	    }else{
                   	    	 result  = result + ' <td><div><span>----</span></div></td>';
                   	    }
                   	 if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
                   	 result  = result + ' <td><div><span title="'+opt.specialization+'">'+opt.specialization+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
                   	 result  = result + ' <td><div><span>'+opt.startDateStr+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
                   	 result  = result + '<td><div><span>'+opt.endDateStr+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.grade != null && opt.grade != '' && opt.grade  != 'undefined'){
                   	 result  = result + '<td><div><span>'+opt.grade+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>'; 
                   	 }
                   	 if(opt.gradeType != null && opt.gradeType != '' && opt.grgradeTypeade  != 'undefined'){
		                 result  = result + '<td><div><span>'+opt.gradeType+'</span></div></td>';
		                 }else{
		                 result  = result + '<td><div><span>----</span></div></td>'; 
		                 }
                   	 if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
                   	 result  = result + '<td><div><span title="'+opt.institute+'">'+opt.institute+'</span></div></td>';
                   	 }else{
                   		 result  = result + '<td><div><span>----</span></div></td>';
                   	 }
                   	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
                   	 result  = result + ' <td><div><span title="'+opt.address+'">'+opt.address+'</span></div></td>';
                   	 }else{
                   		 result  = result + ' <td><div><span>----</span></div></td>';
                   	 }
                   
                   	 result  = result + '</tr>';
                     } 
                    result = result +" </tbody>";
                    result = result +"</table>";
                    $("#educationSubmissionDetailsList").append(result); 
                    educationSubmissionPagination();
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
function educationSubmissionPagination(){  
    $('#educationSubmissionPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
 }
</script>
