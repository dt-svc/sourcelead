

<script>
function candidateHistory(candidateId){
	var result = "";
	result  = result + '   <div class="project_tabs_inn_head addUser-org"> ';
	result  = result + '   	<span class="pad-sea"> <a href="javascript:void(0)"></a></span> ';
	result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result  = result + '   <div id="candidateHistoryList" ></div>';
	result  = result + '   </div>';
	result = result+'	    </div>';

    $("#tab-11").empty();
    $("#tab-12").empty(); 
    $("#tab-13").empty();  
    $("#tab-14").empty();  
    $("#tab-15").empty();  
    $("#tab-16").empty();  
    $("#history").empty();
    $("#history").append(result);
    
    candidateHistoryList(candidateId);
    
}
</script>
<script>
function candidateHistoryList(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateHistoryList?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
             		 
	                	 $("#candidateHistoryList").empty();
	                	 var result = "";
	                	 result = result +"<table id='candidateCertificatePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result +"           <th>Requirement Name</th>";
	                    //result  = result +"           <th>Applied on</th>";
	                 	 result  = result + "           <th>Stage</th>";
	                 	 result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                      var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var sub = json[i];  
	                    	 if(sub.requirementName != null && sub.requirementName !='' && sub.requirementName !='undefined' && sub.requirementStage != null && sub.requirementStage !='' && sub.requirementStage !='undefined'){
	    			    		 result = result +'<tr><td><span>'+sub.requirementName+'</span></td>';
	    			    		 //result = result +'<tr><td><span>"ssss"</span></td>';
	    			    		 result = result +'<td><span>'+sub.requirementStage+'</span></td></tr>'; 
	    			    	 }else{
	    			    		 result = result +'<tr><td><span>--</span></td> <td><span>--</span></td></tr>';
	    			    	 }
	                    	  
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateHistoryList").append(result); 
	                     certificationPagination(); 
        },
          
     }); 
}
</script>

<script>
function candidateHistoryPagination(){  
    $('#candidateHistoryPagination').DataTable( { 
    	"scrollCollapse": false,
    	"scrollX": true,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
    $(".dataTables_empty").html("");
 }
</script>