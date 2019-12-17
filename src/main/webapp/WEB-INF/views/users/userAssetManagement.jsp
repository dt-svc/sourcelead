<%-- 
     @author : Laxman.
     this script is for gettting Asset Management list based on user.
     1.usage     : 
     2.parameter : csrf token .
     3.ajax url implementation in UserRestController.
--%>

<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
function userAssetManagement(userId){  
	var orgLocationId ='${SourceLeadWeb_orgLocationId}';
	  $.ajax({
           type : "GET",
           url: atsUrl+"/getUserBasedAssetManagementList?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&orgLocationId="+orgLocationId,    
            success :function(json) {
            	var result = "";
            	result = result + '<div id="assetServicePopUp" class="popupWrap" style="position:fixed;"/>';
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
   					var selected3 = '';
   					var selected4 = '';
   					var selected5 = '';
				    
				    var report = json[i];
				  
					if (report.asset.model != null && report.asset.model != '' && report.asset.model != 'undefined') {
						model = report.asset.model;
					}
					
					if(report.asset.workingStatus == 'Assigned'){
						selected1 = 'selected';
					} 
					
					if(report.asset.workingStatus == 'Unassigned'){
						selected2 = 'selected';
					} 
					
					if(report.asset.workingStatus == 'End Of Life'){
							selected3 = 'selected';
					}
				
					if(report.asset.workingStatus == 'Out Of Service'){
						selected4 = 'selected';
					} 
					
					if(report.asset.workingStatus == 'Service'){
						selected5 = 'selected';
					}
					

					result = result+'<tr id="asset'+report.id+'">';
		   	        result = result+'<td><a href="'+atsUrl+'/assetManagementSummary?assetUUID='+report.asset.assetUUID+'" target="_blank">';
       		       	result = result +"<div class='proj_show tbldata-dotdot_140 tbldata-dotdot-sm'><span class='pull-left' title='"+report.asset.slno+"'>"+report.asset.slno+"</span><i class='fa fa-binoculars'></i></div>";
				   	result = result+'</a></td>';
				   	result = result+'<td><div class="tbldata-dotdot-sm" title="'+report.asset.categoryName+'">'+report.asset.categoryName+'</div></td>';
					result = result+'<td><div class="tbldata-dotdot-vsm" title="'+model+'">'+model+'</div></td>';
				   	
				   	result = result + ' <td><div class="tbldata-dotdot-150">';
					result = result + ' <select id="workingStatus'+report.asset.id+'" name="workingStatus" onChange="updateWorkingStatus(\''+report.asset.assetUUID+'\',\''+report.asset.id+'\');">';
					result = result + ' <option '+selected1+'>Assigned</option>';
					result = result + ' <option '+selected2+'>Unassigned</option>';
					result = result + ' <option '+selected3+'>End Of Life</option>';
					result = result + ' <option '+selected4+'>Out Of Service</option>';
				    result = result + ' <option '+selected5+'>Service</option>'; 
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
function updateWorkingStatus(assetUUID,assetId){
    var workingStatus = $('#workingStatus'+assetId).val();
	var rowHtmlContent =  $("#asset"+assetId).html();
	
	 $("#asset"+assetId).empty();
	 $("#asset"+assetId).html("<td colspan='7'  id='loading"+assetId+"'></td>");
	 $("#loading"+assetId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	
		if(workingStatus == "Service"){
			assetmanagemetServicePopUp(assetId,workingStatus,assetUUID);
		}else{
			 $.ajax({
				   	type: "POST",
				    url: atsUrl+"/updateAssetWorkingStatus?"+tokenParamName+"="+tokenValue+"&assetId="+assetId+"&workingStatus="+workingStatus,	    	   
				    success: function(data) {
				    	$("#asset"+assetId).empty();
				    	$("#asset"+assetId).html(rowHtmlContent);
				        $("#workingStatus"+assetId).val(workingStatus);
				    	
				    }
			   });
		}

}

function assetmanagemetServicePopUp(assetId,workingStatus,assetUUID){
	$("#assetServicePopUp, .mask").fadeIn();
	 var result = "";
	 result = result + '<form id="assetService_form" name="assetService_form" method="post">';
	 result = result + '	<input type="hidden" name="assetId" id="assetId" value='+assetId+'>';
	 result = result + '	<input type="hidden" name="assetUUID" id="assetUUID" value='+assetUUID+'>';
	 result = result + '	<input type="hidden" name="workingStatus" id="workingState" value='+workingStatus+'>';
	 result = result + '	<div class="clearfix">';
		result = result + '	<div class="popupHead">Asset Management Sevice<i class="closePop pull-right popClose" onclick="closeAssetPopup(\''+assetUUID+'\',\''+assetId+'\');"></i></div>';
			result = result + '	<div class="popupContent">';
				result = result + '	<div class="row form-group">';
					result = result + '<div class="col-md-4">';
					result = result + '    	<label class="clearfix">Service Date<span class="colorRed paddingLeft">*</span></label>';
					result = result + '    	<div class="clearfix validationTxt" id="errServiceDateDiv">';
					result = result + '    		<input type="text" class="form-control calendar"  placeholder="Service Date" name="serviceDate" id="serviceDate" data-validation="required" field-name="Service Date" readonly="readonly">';
					result = result + '    	</div>';
					result = result + '</div>';
					
					result = result + '			<div class="col-md-4">';
					result = result + '				<label class="clearfix">Received Date<span class="colorRed paddingLeft">*</span></label>';
					result = result + '				<div class="clearfix validationTxt" id="errRecivedDateDiv">';
					result = result + '					<input type="text" class="form-control calendar" placeholder="Received Date" name="receivedDate" id="receivedDate" data-validation="required" field-name="Received  Date" readonly="readonly">';
					result = result + '				</div>';
					result = result + '			</div>';
					
					result = result + '			<div class="col-md-4">';
					result = result + '				<label class="clearfix">Cost<span class="colorRed paddingLeft">*</span></label>';
					result = result + '				<div class="clearfix validationTxt">';
					result = result + '					<input type="text" class="form-control" placeholder="Cost" name="cost" id="cost" data-validation="required validate_float validate_length length1-50" field-name="Cost">';
					result = result + '				</div>';
					result = result + '			</div>';
					result = result + '	</div>';
					result = result + '	<div class="row form-group">';
						result = result + '			<div class="col-md-12">';
						result = result + '				<label class="clearfix">Description<span class="colorRed paddingLeft">*</span></label>';
						result = result + '				<div class="clearfix validationTxt">';
						result = result + '					<textarea class="form-control" rows="3" name="description" id="description" placeholder="Description" data-validation="required validate_length length2-500" field-name="Description"></textarea>';
						result = result + '				</div>';
						result = result + '			</div>';
					result = result + '	</div>';
				
			result = result + '	</div>';
			
			result = result + '	<div class="popupFoot text-right">';
			result = result + '		<input type="button" value="Save" name="btnAddExpCat" id="save_button" class="slBtn md colorBtn" onclick="saveAssetService('+assetId+');">';
			result = result + '		<input type="button" value="Cancel" class="slBtn md popClose" onclick="closeAssetPopup(\''+assetUUID+'\',\''+assetId+'\');">';
			result = result + '	</div>';
	 result = result + '</div>';
	 result = result + '</form>';
	$("#assetServicePopUp").empty();
	$("#assetServicePopUp").append(result)

	
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	var finalDateFormat = getJSDateFormat(locationDate);
	$('#serviceDate').datepicker({
	    dateFormat: finalDateFormat,
	    changeMonth: true,
		changeYear: true, 
		onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate() + 1);
			$('#receivedDate').datepicker("option", "minDate", dt);
			errServiceDate();
		}
	});
	$('#receivedDate').datepicker({
	    dateFormat: finalDateFormat,
	    changeMonth: true,
		changeYear: true, 
		onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate() - 1);
			$('#serviceDate').datepicker("option", "maxDate", dt);
			errReceiveDate();
		}
	});
}

function errServiceDate(){
	$('#errServiceDateDiv').find('p.jquery_form_error_message').remove(); 
}
function errReceiveDate(){
	$('#errRecivedDateDiv').find('p.jquery_form_error_message').remove(); 
}
//close Popup
function closeAssetPopup(assetUUID,assetId) {
	var rowHtmlContent =  $("#asset"+assetId).html();
	$("#assetServicePopUp, .mask").fadeOut();
	 $.ajax({
		   	type: "POST",
		    url: atsUrl+"/cancelAssetManagementService?"+tokenParamName+"="+tokenValue+"&assetUUID="+assetUUID,	    	   
		    success: function(json) {
		    	$("#asset"+assetId).empty();
		    	$("#workingStatus"+assetId).val(json.workingStatus);
		    	$("#asset"+assetId).html(rowHtmlContent);
		    }
	 });
	
}
</script>

<script>
function saveAssetService(assetId){
	if ($('#assetService_form').validate(false, validationSettings)) {
		$("#save_button").attr("disabled", "disabled");
		var rowHtmlContent =  $("#asset"+assetId).html();
		var workingState = $("#workingState").val();
		 $("#asset"+assetId).empty();
		 $("#asset"+assetId).html("<td colspan='7'  id='loading"+assetId+"'></td>");
		 $("#loading"+assetId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData("assetService_form");
	 	$.ajax({
			type: "POST",
			data: formData,
			url : atsUrl+ "/saveAssetService?${_csrf.parameterName}=${_csrf.token}",
			success : function(data) {
					if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
			        		showError(data.responseMessage);
	  	    		}else if (data.response === "AWKWARD") {
	            			location.href = "/errorPage";
	  	    		}else if(data.response === "SUCCESS"){
	  	    			 closeAssetPopup();
	  	    			$("#asset"+assetId).empty();
	  			    	$("#asset"+assetId).html(rowHtmlContent);
	  			        $("#workingStatus"+assetId).val(workingState);
			        }else if (data.indexOf('Sign In') !== -1) {
            			location.replace(sourceleadUrl);
	  				} else {
	  					showError("Operation failed, please try again");
	  				}
			},
		}); 
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#assetService_form', function() {
	$("#assetService_form").showHelpOnFocus();
	$("#assetService_form").validateOnBlur(false, validationSettings);
});
 

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
	    url: atsUrl+"/removeAssocateAssingAssetManagement?"+tokenParamName+"="+tokenValue+"&assignAssetId="+assignAssetId,	    	   
	    success: function(data) {
	    	 var table = $('#assetManagementListPagination').DataTable();
  		     table.row("#asset"+assignAssetId).remove().draw( false );
	    }
   });
}
</script>

