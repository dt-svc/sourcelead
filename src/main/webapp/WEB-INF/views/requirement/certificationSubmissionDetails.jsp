<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<script>
function certificationSubmissionDetails(candidateId){
	var result = "";
	result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result = result	+ '<div class="project_tabs_inn_head"> <input class="vendorfild" ></div>';
	result = result + '   <div id="certificationSubmissionDetailsList" class="datatable_filter"></div>';
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
	 
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  certSubListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  certSubListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  certSubListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  certSubListDate = 'YYYY-DD-MM'; 
	  }  
	 
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               /* if(data != null && data != 'undefined' && data != "" && data != '[]'){ */
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
	                 	result  = result + "           <th>Valid from <br>(<small class='custom-small'>"+certSubListDate+"</small>)</th>";
	                 	result  = result + "           <th>Valid to <br>(<small class='custom-small'>"+certSubListDate+"</small>)</th>";
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
	                    	 result  = result + '           <td><div><span title="'+opt.name+'">'+opt.name+'</span></div></td>';
	                    	 result  = result + '           <td><div><span title="'+opt.provider+'">'+opt.provider+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+score+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+scoreType+'</span></div></td>';
	                    	 result  = result + '           <td><div><span title="'+description+'">'+description+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+candidatevalidFromDateToString+'</span></div></td>';
	                    	 result  = result + '           <td><div><span>'+candidatevalidToDateToString+'</span></div></td>';
	                     result  = result + '         </tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#certificationSubmissionDetailsList").append(result); 
	                     certificationSubmissionDetailsPagination(); 
			    	} 
               /* }else{
	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No SubmissionCertifications Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#certificationSubmissionDetailsList").empty();
				    $("#certificationSubmissionDetailsList").append(result);
				    
               } */
        },
          
     }); 
}
</script>

<script>
function certificationSubmissionDetailsPagination(){  
    $('#certificationSubmissionDetailPagination').DataTable( { 
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