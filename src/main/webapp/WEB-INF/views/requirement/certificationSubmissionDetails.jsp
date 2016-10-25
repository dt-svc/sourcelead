


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/jquery-1.11.3.min.js"></script> 
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script>
function certificationSubmissionDetails(candidateId){
	var result = "";
	result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result = result	+ '<div class="project_tabs_inn_head"> <input class="vendorfild" ></div>';
	result = result + '   <div id="certificationSubmissionDetailsList" ></div>';
	result = result + '   </div>';
	
	$("#tabb-1").empty();
	$("#tabb-2").empty();
	$("#tabb-3").empty();
	$("#tabb-4").empty();
	$("#tabb-5").empty();
	$("#tabb-6").empty();
	$("#tabb-7").empty();
	$("#tabb-6").append(result);
	
	certificationSubmissionDetailsList(candidateId);
    
}
</script>

<script>
function certificationSubmissionDetailsList(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#certificationSubmissionDetailsList").empty();
	                	 var result = "";
	                	 result = result +"<table id='certificationSubmissionDetailPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result +"           <th>Name</th>";
	                 	result  = result + "           <th>Provider</th>";
	                 	result  = result + "           <th>Score</th>";
	                 	result  = result + "           <th>Score Type</th>";
	                 	result  = result + "           <th>Description</th>";
	                 	result  = result + "           <th>Valid from</th>";
	                 	result  = result + "           <th>Valid to</th>";
	                 	result  = result + "           <th>Action</th>";
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                      var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 var score = "";
	                    	 var scoreType ="";
	                    	 var description ="";
	                    	 var candidatevalidFromDateToString ="";
	                    	 var candidatevalidToDateToString ="";
				            	if(opt.score != null && opt.score != 'undefined' && opt.score != ""){
				            		score = opt.score;
				            	}else{
				            		score = "----";
				            	}
				            	if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != ""){
				            		scoreType = opt.scoreType;
				            	}else{
				            		scoreType = "----";
				            	}
				            	if(opt.description != null && opt.description != 'undefined' && opt.description != ""){
				            		description = opt.description;
				            	}else{
				            		description = "----";
				            	}
				            	if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
				            	}else{
				            		candidatevalidFromDateToString = "----";
				            	}
				            	if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
				            		candidatevalidToDateToString = opt.candidatevalidToDateToString;
				            	}else{
				            		candidatevalidToDateToString = "----";
				            	}
	                    	 result  = result + '         <tr>';
	                    	 result  = result + '           <td><div><span>'+opt.name+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+opt.provider+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+score+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+scoreType+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+description+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+candidatevalidFromDateToString+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+candidatevalidToDateToString+'</span></div></td>';
	                    	 if(window.candidateStatusGlobal == '0'){
	                    		 result = result  +'	            <td><div class="tblicons"><a a href="javascript:void(0)" disabled="disabled" class="glyphicon glyphicon-pencil" id="editCandidateCertificate"></a>';
	                    	}else{
	                    		result = result  +'	            <td><div class="tblicons"><a href="javascript:void(0)" class="glyphicon glyphicon-pencil" id="editCandidateCertificate" href="javascript:void(0)" onClick="editCertification('+ opt.id+ ','+ candidateId+ ');"></a>';
	                    	}
	                    	 
	                    	 if(window.candidateStatusGlobal == '0'){
	                    		 result = result  +'<i class="glyphicon glyphicon-trash"></i></div></td>';
	                    	}else{
	                    		 result = result  +'<i class="glyphicon glyphicon-trash" onclick="removeCertification('+opt.id+','+candidateId+');" id="removeCandidate"></i></div></td>';
	                    	}
	                    	 result  = result + '         </tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#certificationSubmissionDetailsList").append(result); 
	                     certificationSubmissionDetailsPagination(); 
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
					$("#certificationSubmissionDetailsList").empty();
				    $("#certificationSubmissionDetailsList").append(result);
				    
               }
        },
          
     }); 
}
</script>

<script>
function certificationSubmissionDetailsPagination(){  
    $('#certificationSubmissionDetailPagination').DataTable( { 
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