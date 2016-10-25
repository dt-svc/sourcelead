
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/jquery-1.11.3.min.js"></script> 
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script>
function educationSubmissionDetails(candidateId){
	var result = "";
	result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result = result	+ '<div class="project_tabs_inn_head"> <input class="vendorfild" ></div>';
	result = result + '   <div id="educationSubmissionDetailsList" ></div>';
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
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#educationSubmissionDetailsList").empty();
	                	 var result = "";
	                	 result = result +"<table id='educationSubmissionPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result + '           <th>Education</th>';
	                 	 result  = result + '           <th>Specialization</th>';
	                 	 result  = result + '           <th>Start date</th>';
	                 	 result  = result + '           <th>End date</th>';
	                 	 result  = result + '           <th>score</th>';
	                 	 result  = result + '           <th>score Type</th>';
	                 	 result  = result + '           <th>Institute/University</th>';
	                 	 result  = result + '           <th>Address</th>';
	                 	 result  = result + '           <th>Action</th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 result  = result + '         <tr>';
	                    	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
	                    	 result  = result + '<td><div><span>'+opt.name+'</span></div></td>';
	                    	    }else{
	                    	    	 result  = result + ' <td><div><span>----</span></div></td>';
	                    	    }
	                    	 if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
	                    	 result  = result + ' <td><div><span>'+opt.specialization+'</span></div></td>';
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
	                    	 result  = result + '<td><div><span>'+opt.institute+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + '<td><div><span>----</span></div></td>';
	                    	 }
	                    	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
	                    	 result  = result + ' <td><div><span>'+opt.address+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>----</span></div></td>';
	                    	 }
	                    	 if(opt.id != null && opt.id != '' && opt.id != 'undefined'){
	                    		 if(window.candidateStatusGlobal == '0'){
	                    			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
	                    		 }
	                    		 else{
	                    			 result = result+' <td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick ="EditEducation('+ candidateId+ ','+ opt.id+ ');"></a>';	 
	                    		 }
	                    		 if(window.candidateStatusGlobal == '0'){
									result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
	                    		 }else{
	                    			 result = result+'<i class="glyphicon glyphicon-trash" onclick="removeEducation('+opt.id+','+candidateId+');"></i></div></td>'; 
	                    		 }
	                    	}
	                    	 result  = result + '</tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#educationSubmissionDetailsList").append(result); 
	                     educationSubmissionPagination(); 
			    	} 
               }else{
	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No CandidatesCertifications Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#educationSubmissionDetailsList").empty();
				    $("#educationSubmissionDetailsList").append(result); 
               }
        },
          
     }); 
}
</script> 

<script>
function educationSubmissionPagination(){  
    $('#educationSubmissionPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
 
 }
</script>
