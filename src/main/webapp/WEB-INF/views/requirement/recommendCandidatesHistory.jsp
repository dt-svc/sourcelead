<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">


<script>
function candidateHistory(candidateId){
	var result = "";
	result  = result + '   <div class="project_tabs_inn_head addUser-org"> ';
	result  = result + '   	<span class="pad-sea"> <a href="javascript:void(0)"></a></span> ';
	result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result  = result + '   <div id="candidateHistoryList" class="datatable_filter"></div>';
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
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateHistoryList?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
             		 
	                	 $("#candidateHistoryList").empty();
	                	 var result = "";
	                	 result = result +"<table id='candidateHistoryPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result +"           <th>Job Name</th>";
	                     result  = result +"             <th>Client Name</th>";
	                 	 result  = result + "           <th>Stage</th>";
	                 	 result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                      var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var sub = json[i];  
		                    	 if(sub.requirementName != null && sub.requirementName != '' && sub.requirementName != 'undefined'){
		                    		 requirementName = sub.requirementName;
		                    	 }else{
		                    		 requirementName = "";
		                    	 }
		                    	 if(sub.clientName != null && sub.clientName != '' && sub.clientName != 'undefined'){
		                    		 clientName = sub.clientName
		                    	 }else{
		                    		 clientName = "--";
		                    	 }
		                    	 if(sub.requirementStage != null && sub.requirementStage != '' && sub.requirementStage != 'undefined'){
		                    		 requirementStage = sub.requirementStage;
		                    	 }else{
		                    		 requirementStage = "";
		                    	 }
		                    	 if(sub.requirementJobId != null && sub.requirementJobId != '' && sub.requirementJobId != 'undefined'){
		                    		 requirementJobId = sub.requirementJobId +'-';
		                    	 }else{
		                    		 requirementJobId = "";
		                    	 }
	    			    		 result = result +'<tr><td><span class="mid-span" title="'+requirementJobId+' '+requirementName+'">'+requirementJobId+' '+requirementName+'</span></td>';
	    			    		 result = result +'<td><span class="mid-span" title="'+clientName+'">'+clientName+'</span></td>';
	    			    		 result = result +'<td><span class="mid-span">'+requirementStage+'</span></td></tr>'; 
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateHistoryList").append(result); 
	                     candidateHistoryPagination(); 
        },
          
     }); 
}
</script>

<script>
function candidateHistoryPagination(){  
    $('#candidateHistoryPagination').DataTable( { 
    	"scrollCollapse": false,
    	"scrollX": true,
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