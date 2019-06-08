	<meta http-equiv="Pragma" content="private">
	<meta http-equiv="Cache-Control" content="private">
	<meta http-equiv="Expires" content="0">
	<!-- these files for autocomplete with tagit -->

	<%-- 
	     @author : prasadK.
	     this script is for display candidate Documents tab data.
	     1.usage     : used at clientSideBar().
	--%>
	
	<script>
	function documentsSubmissionDetails(candidateId){
	
		    var result = "";
			result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">';
		/* 	result = result	+ '<div class="project_tabs_inn_head"> <input class="vendorfild" ></div>'; */
			result = result + '   <div id="documentsSubmissionDetailsList" class="datatable_filter mt5"></div>';
			result = result + '   </div>';
	    
	    $("#tabb-1").empty();
		$("#tabb-2").empty();
		$("#tabb-3").empty();
		$("#tabb-4").empty();
		$("#tabb-5").empty();
		$("#tabb-6").empty();
		$("#tabb-7").empty();
		$("#tabb-8").empty();
		$("#tabb-8").append(result);
	    
		candSubmissionDocumentsList(candidateId);
	}
	</script>
	
	
<script>
function candSubmissionDocumentsList(candidateId){	 
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateDocumentsListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#documentsSubmissionDetailsList").empty();
                	var result = "";
                	result = result +"<table id='documentsSubPagination' class='display' cellspacing='0' width='100%' >"; 
                    result = result +"<thead>"; 
                    result = result +"  <tr>"; 
                    result  = result + '<th>Document Type</th>';
                 	result  = result + '<th>Document</th>';
                 	result  = result + '<th>Description</th>';
                 	result  = result + '<th>Action</th>';
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                  
                    for (var i = 0; i < response.data.length; i++) {
                    	var opt = response.data[i];  
                    	result  = result + ' <tr>';
                    	if(opt.documentType != null && opt.documentType != '' && opt.documentType != "undefined") {
       	            		if(opt.documentOtherType !=null && opt.documentOtherType !='' && opt.documentOtherType !="undefined"){
       	                   		result = result +"  <td><div>"+opt.documentOtherType+"</div></td>";
       	             	 	} else {
       	             			result = result +"  <td><div>"+opt.documentType+"</div></td>"; 
       	             	 	}
       	             	} else {
       	             		result = result +"  <td><div>----</div></td>"; 
       	             	}
                    	
                    	if(opt.fileName != null && opt.fileName != '' && opt.fileName != 'undefined'){
                    	 	result  = result + ' <td><div><span title="'+opt.fileName+'">'+opt.fileName+'</span></div></td>';
                    	} else {
                    		result  = result + ' <td><div><span>----</span></div></td>';
                    	}
                    	
                    	if(opt.description != null && opt.description != '' && opt.description != 'undefined'){
                        	result  = result + ' <td><div><span title="'+opt.description+'">'+opt.description+'</span></div></td>';
                        } else {
                        	result  = result + ' <td><div><span>----</span></div></td>';
                        }
                       	result = result+'<td><div class="tblicons"> <a download class="glyphicon glyphicon-download-alt" href=\"'+opt.fileURL+'\"></a>';
       	           		result = result+'</div></td>';
                    	result  = result + '</tr>';
                    } 
                    result = result +" </tbody>";
                    result = result +"</table>";
                    $("#documentsSubmissionDetailsList").append(result); 
                    documentsSubPagination();
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
          }
	});     
}
</script>

	<%-- 
	     @author : Prasad K.
	     this script is for prepare pagination.
	     1.usage     : used in candSubDocumentsList().
	--%>
	
	<script>
	function documentsSubPagination(){  
	    $('#documentsSubPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    } );
	}
	</script>

