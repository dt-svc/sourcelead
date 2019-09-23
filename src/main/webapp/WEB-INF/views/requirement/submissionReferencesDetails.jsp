<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%-- 
     @author : prasadK.
     this script is for display candidate references tab data.
     1.usage     : used at clientSideBar().
--%>

<script>
	function submissionReferencesDetails(candidateId){
	
		    var result = "";
		    result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">';
		    result = result + '   <div id="submissionReferencesList" class="datatable_filter mt5"></div>';
			result = result + '   </div>';
	    
		    $("#tabb-1").empty();
			$("#tabb-2").empty();
			$("#tabb-3").empty();
			$("#tabb-4").empty();
			$("#tabb-5").empty();
			$("#tabb-6").empty();
			$("#tabb-7").empty();
			$("#tabb-8").empty();
			$("#tabb-9").empty();
			$("#tabb-9").append(result);
			
			submissionReferencesList(candidateId);
	}
	</script>

<script>
function submissionReferencesList(candidateId){
	var locationCode ='${SourceLeadWeb_locationCode}';
	
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateReferencesListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&locationCode="+locationCode,        
          success :function(json) {
           
	                	 $("#submissionReferencesList").empty();
	                	 var result = "";
	                	 result = result +"<table id='submissionReferencesPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result + '<th>Name </th>';
	                 	 result  = result + '<th>Email</th>';
	                 	 result  = result + '<th>Phone</th>';
	                 	 result  = result + '<th>Relation </th>';
	                 	 result  = result + '<th>Comment </th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                      for (var i = 0; i < json.data.length; i++) { 
	                    	 var opt = json.data[i];  
	                    	 result  = result + ' <tr>';
	                    	 if(opt.name != null && opt.name != '' && opt.name != "undefined")
	    	             	 {
	    	             		result = result +"  <td><div class='tbldata-dotdot-me' title='"+opt.name+"'>"+opt.name+"</div></td>"; 
	    	             	 }else{
	    	             		 result = result +"  <td><div>----</div></td>"; 
	    	             	 }
	                    	 
	                    	 if(opt.email != null && opt.email != '' && opt.email != 'undefined'){
	                    	 result  = result + ' <td><div class="tbldata-dotdot-me" title="'+opt.email+'">'+opt.email+'</div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>----</span></div></td>';
	                    	 }
	                    	 
	                    	 if(opt.mobilePhone != null && opt.mobilePhone != '' && opt.mobilePhone != 'undefined'){
		                    	 result  = result + ' <td><div><span title="'+opt.mobilePhone+'">'+opt.mobilePhone+'</span></div></td>';
		                    	 }else{
		                    		 result  = result + ' <td><div><span>----</span></div></td>';
		                    	 }
		                    	
	                    	 if(opt.relation != null && opt.relation != '' && opt.relation != 'undefined'){
		                    	 result  = result + ' <td><div><span title="'+opt.relation+'">'+opt.relation+'</span></div></td>';
		                    	 }else{
		                    		 result  = result + ' <td><div><span>----</span></div></td>';
		                    	 }
	                    	 
	                    	 if(opt.comment != null && opt.comment != '' && opt.comment != 'undefined'){
		                    	 result  = result + ' <td><div><span title="'+opt.comment+'">'+opt.comment+'</span></div></td>';
		                    	 }else{
		                    		 result  = result + ' <td><div><span>----</span></div></td>';
		                    	 }
		                   
	                    	 result  = result + '</tr>';
	                      }
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#submissionReferencesList").append(result); 
	                     submissionReferencesPagination();
          }
          });     
}
</script>

<script>
function submissionReferencesPagination(){  
    $('#submissionReferencesPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
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
