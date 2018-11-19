<%-- 
     @author : Laxman.
     this script is for gettting Asset Management list based on user.
     1.usage     : 
     2.parameter : csrf token .
     3.ajax url implementation in UserRestController.
--%>
<script>
function userAssetManagement(userId){  
	var orgLocationId ='${SourceLeadWeb_orgLocationId}';
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/getUserBasedAssetManagementList?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&orgLocationId="+orgLocationId,    
            success :function(json) {
            	var result = "";
            	result = result +"<table id='assetManagementListPagination' class='display' cellspacing='0' width='100%' >"; 
                result = result +"<thead>"; 
                result = result +" <tr>"; 
                result = result +"    <th>Serial / Port Number</th>"; 
                result = result +"    <th>Category</th>";
                result = result +"    <th>Model</th>"; 
                result = result +"    <th>Status</th>"; 
                result = result +"    <th>Action</th>"; 
                
                result = result +"  </tr>"; 
                result = result +"</thead>";
                result = result +"<tbody>"; 
                for (var i = 0; i < json.length; i++) { 
                    var report = json[i];
                    
				    var model = "---";
				    var selected1 = '';
   					var selected2 = '';
				    
				    var report = json[i];
				  
					if (report.asset.model != null && report.asset.model != '' && report.asset.model != 'undefined') {
						model = report.asset.model;
					}
					
					if(report.asset.workingStatus == 'Working'){
						selected1 = 'selected';
					} 
				
					if(report.asset.workingStatus == 'Out Of Service'){
						selected2 = 'selected';
					} 

					result = result+'<tr id="asset'+report.id+'">';
		   	        result = result+'<td><a href="'+sourceleadUrl+'/assetManagementSummary?assetUUID='+report.asset.assetUUID+'" target="_blank">';
       		       	result = result +"<div class='proj_show tbldata-dotdot_140 tbldata-dotdot-sm'><span class='pull-left' title='"+report.asset.slno+"'>"+report.asset.slno+"</span><i class='fa fa-binoculars'></i></div>";
				   	result = result+'</a></td>';
				   	result = result+'<td><div class="tbldata-dotdot-sm" title="'+report.asset.categoryName+'">'+report.asset.categoryName+'</div></td>';
					result = result+'<td><div class="tbldata-dotdot-vsm" title="'+model+'">'+model+'</div></td>';
				   	
				   	result = result + ' <td><div class="tbldata-dotdot-150">';
					result = result + ' <select id="workingStatus'+report.id+'" name="workingStatus" onChange="updateWorkingStatus(\''+report.id+'\');">';
					result = result + ' <option '+selected1+'>Working</option>';
					result = result + ' <option '+selected2+'>Out Of Service</option>';
					result = result + ' </select>';
					result = result + ' </div></td>';
				   	
				   	result = result+'<td><div class="tblminwidth-50"> <a onClick="deleteAssignAsset(\''+report.id+'\')";>Un-Assign</a></div></td>';
		   	        result = result +'</tr>';
                }
                
                result = result +"</tbody>";
                result = result +"</table>";
                $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty();
                $("#tab-25").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").append(result);  
                assetmentPagination(); 
          },
            
       }); 
  } 
</script>
 
<script>
function assetmentPagination(){ 
    $('#assetManagementListPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $('#assetManagementListPagination').wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>

<script type="text/javascript">
function deleteAssignAsset(assignAssetId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure Un-Assign Asset ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='removeAssetManagement("+assignAssetId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='assetCancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>


<script>
function updateWorkingStatus(assetId){
    var workingStatus = $('#workingStatus'+assetId).val();
	var rowHtmlContent =  $("#asset"+assetId).html();
	
	 $("#asset"+assetId).empty();
	 $("#asset"+assetId).html("<td colspan='7'  id='loading"+assetId+"'></td>");
	 $("#loading"+assetId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/updateAssetWorkingStatus?"+tokenParamName+"="+tokenValue+"&assetId="+assetId+"&workingStatus="+workingStatus,	    	   
	    success: function(data) {
	    	$("#asset"+assetId).empty();
	    	$("#asset"+assetId).html(rowHtmlContent);
	        $("#workingStatus"+assetId).val(workingStatus);
	    	
	    }
   });
}
</script>
<script type="text/javascript">
function assetCancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<div id="popUp"></div>

<script>
function removeAssetManagement(assignAssetId){
	 $("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
	 $("#asset"+assignAssetId).empty();
	 $("#asset"+assignAssetId).html("<td colspan='7'  id='loading"+assignAssetId+"'></td>");
	 $("#loading"+assignAssetId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	 $.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/removeAssocateAssingAssetManagement?"+tokenParamName+"="+tokenValue+"&assignAssetId="+assignAssetId,	    	   
	    success: function(data) {
	    	 var table = $('#assetManagementListPagination').DataTable();
  		     table.row("#asset"+assignAssetId).remove().draw( false );
	    }
   });
}
</script>

