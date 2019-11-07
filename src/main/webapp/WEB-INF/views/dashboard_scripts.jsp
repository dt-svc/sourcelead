<script type="text/javascript">
	$('#dasboardTaskDatpicker').datepicker({
		dateFormat : 'dd-mm-yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			assignTasksList();
		}
	});
</script>
<script type="text/javascript">
	function configureDiv(){
	getDateFormatByOrgLocation();
	var result = '';
	result=result+'<div class="sideMask" style="display:block"></div>';
	result=result+'<div class="configure_wrap">';
	result=result+'	<div id="loading"></div>';
	result=result+'<div class="configure_wrap-inner">';
	result=result+'<h2 class="text-danger">Configure</h2>';
	result=result+'<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">'; 
	result=result+'<div class="form-group">';
	result=result+'<label>Time Zone<span class="text-danger paddingLeft">*</span></label>';
	result=result+'<div class="clearfix">';
	result=result+'<select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Time zone" data-validation="required" id="timeZone" name="timeZone">';
	result=result+'</select>';
	//result=result+'<span class="hidden">Time Zone</span>';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="form-group">';
	result=result+'<label>Date Format</label>';
	result=result+'<div class="clearfix dropdown-select">';
	result=result+'<select class="form-control" name="dateFormatForOrg" id="dateFormatForOrgId" data-validation="required" field-name="Date Format">';
	result=result+'</select>';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="form-group mb10">';
	result=result+'<label>Organization Short Code<span class="text-danger paddingLeft">*</span></label>';
	result=result+'<div class="clearfix" id="generatedUserIdDiv">';
	result=result+'<input type="text" class="form-control" id="generatedUserId" style="text-transform:uppercase" name="generatedUserId" data-validation="required validate_length length1-4" field-name="Organization Short Code" onkeyup="validateOrgShortCode();" maxlength="4">';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="clearfix">';
	result=result+'<p class="text-muted small" style="font-size: 11px;" title="Org Short code cannot be modified once submitted."><i class="fa fa-info-circle"></i> Org Short code cannot be modified once submitted.</p>';
	result=result+'</div>';
	
	result=result+'<div class="form-group">';
	result=result+'<label>Average Working Hours</label>';
	result=result+'<div class="clearfix dropdown-select" id="averageWorkingHoursDiv">';
	result=result+'<input type="text" class="form-control" maxlength="2" name="averageWorkingHours" id="averageWorkingHours" data-validation="required" field-name="Average Working Hours">';
	result=result+'</div>';
	result=result+'</div>';
	
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="clearfix">';
	result=result+'<div class="col-md-6">';
	result=result+'<input type="button" value="Proceed" id="saveOrgConfiguration" class="btn btn-primary btn-block" onclick="storeOrgShortCode();">';
	result=result+'</div>';
	result=result+'<div class="col-md-6">';
	result=result+'<input type="button" value="Cancel" onclick="logoutForConfigure();" class="btn btn-default btn-block">';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'</div>'; 
	result=result+'</div>';
	$("#configurePopUpDiv").empty();
	$("#configurePopUpDiv").append(result);
	getTimezonesList(); 
}
</script>
<script>
function checkForOldrecords(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orglocationId ='${SourceLeadWeb_orgLocationId}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/checkForOldrecords?${_csrf.parameterName}=${_csrf.token}&orglocationId="+orglocationId,
	        success: function(data) {
	        	if(data == true){
	        		  var result = '';
	               	  result=result+'<div class="sideMask" style="display:none"></div>';
	               	  $("#configurePopUpDiv").empty();
	               	  $("#configurePopUpDiv").append(result);
	        	}else{
	        		  var result = '';
	               	  result=result+'<div class="sideMask" style="display:none"></div>';
	               	  $("#configurePopUpDiv").empty();
	               	  $("#configurePopUpDiv").append(result);
	        	}
	        }
	 }); 
}
</script>
<script>
function validateOrgShortCode(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgShortId = $("#generatedUserId").val();
	$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		  $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/validateOrgShortCode?${_csrf.parameterName}=${_csrf.token}&orgShortId="+orgShortId, 
		        success: function(data) {
		        	if(data == true){
		        		$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		$("input#generatedUserId").parent().after("<p class='jquery_form_error_message'><span>Org Short Code Already Exists !!!</span>");
		        		$("#saveOrgConfiguration").attr("disabled","disabled");
		        		
		        	}else{
		        		 $('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		 $("#saveOrgConfiguration").removeAttr("disabled","disabled");
		        	}
		        }
		 }); 
}
$(function(){
	$("#generatedUserId").keypress(function (event) {
        return event.key.length > 1 || event.ctrlKey || !!event.key.match(/[a-zA-Z]/);
    });
});
$(function() {
	  $('#averageWorkingHoursDiv').on('keydown', '#averageWorkingHours', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
});
$(function(){
    $("#averageWorkingHours").keyup(function(event){
        var ew = $("#averageWorkingHours").val();
        $('#averageWorkingHoursDiv').parent().find('p.jquery_form_error_message').remove(); 
        if(ew > 24){
        	$("#averageWorkingHours").val('');
        	$("input#averageWorkingHours").parent().after("<p class='jquery_form_error_message'><span>Average Working Hours should be less then or equal to 24</span>");
        }
    });
});
</script>
<script type="text/javascript">
function storeOrgShortCode(){
 var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
   };  
   		if ($('#configure_form').validate(false, validationSettings)){   
		var frm = $('#configure_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		var form = $('#configure_form')[0];
  		var formData = new FormData(form);
   		formData.append("json",jsonData);
		var sourceleadUrl = '${sourceleadUrl}';
	 	var locationCode ='${SourceLeadWeb_locationCode}';
		$("#saveOrgConfiguration" ).addClass('button').val('Processing..');
   		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        $.ajax({
       	      type: "POST",
       	      data: formData,
       	      contentType : 'application/json; charset=utf-8',
       	      enctype: 'multipart/form-data',
   	          processData: false,
   	          contentType:false,
       	      dataType : 'json',
            	  url: sourceleadUrl+"/saveOrgShortCodeform?${_csrf.parameterName}=${_csrf.token}&locationCode="+locationCode,
                  success: function(data) {
                	  checkForOldrecords();
      			},  
             });
    	    return true;
    	 }else{
    		return false;	
       } 
         $('#configure_form').showHelpOnFocus();
         $('#configure_form').validateOnBlur(false, validationSettings); 
}  
</script>
<script>
function getTimezonesList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var country = '${SourceLeadWeb_orgLocations.country}';
	if(country){
		 $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/getTimezonesList?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        var json = JSON.parse(data);
		        var output = "";
		        output = output + "<option value=''>--Select--</option>";
		        if(country == 'India'){
	    			output = output + "<option value='246' selected> Asia/Kolkata </option>";
	    		}else if(country == 'United States'){
	    			output = output + "<option value='132' selected> America/Chicago </option>";
	    		}else if(country == 'United Kingdom'){
	    			output = output + "<option value='333' selected> Europe/London </option>";
	    		}else if(country == 'Singapore'){
	    			output = output + "<option value='272' selected> Asia/Singapore </option>";
	    		}else if(country == 'France'){
	    			output = output + "<option value='342' selected> Europe/Paris </option>";
	    		}else if(country == 'Australia'){
	    			output = output + "<option value='298' selected> Australia/Adelaide </option>";
	    		}
		        	for (var i=0; i<json.length; i++){
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.timeZoneId+"</option>";
					}
		        	$("#timeZone").empty();
		        	$("#timeZone").append(output);
		        	$("#timeZone").trigger("chosen:updated");
		        }
		 });
	}

}
</script>
<script type="text/javascript">
function getDateFormatByOrgLocation() {
		    var locationCode ='${SourceLeadWeb_locationCode}';
			var sourceleadUrl = '${sourceleadUrl}';
           $.ajax({
       	      type: "POST",
             	url: sourceleadUrl+"/getDateFormatByOrgLocation?${_csrf.parameterName}=${_csrf.token}&locationCode="+locationCode, 
               	success: function(result) {
						 $("#dateFormatForOrgId").empty();
						$("#dateFormatForOrgId").append("<option value='' disabled selected>-- Select Date Format --</option>");
						if(result == 'dd-MM-yyyy'){
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy' selected>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
						}else if(result == 'MM-dd-yyyy'){
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy' selected>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
						}else if(result == 'yyyy-dd-MM'){
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM' selected>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
						}else if(result == 'yyyy-MM-dd'){
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd' selected>yyyy-MM-dd</option>");
						}else{
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
						}
						
						$('#dateFormatForOrgId').trigger("chosen:updated"); 
		        },  
           });
           $(function(){
       	    var config = {
       			  '.chosen-select'           : {},
       			  '.chosen-select-deselect'  : {allow_single_deselect:true},
       			  '.chosen-select-no-single' : {disable_search_threshold:10},
       			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
       			  '.chosen-select-width'     : {width:"95%"}
       			}
       	    $("#c2owner").chosen();
       	    $('.chosen-select').each(function() {
       				var $this = $(this);
       				$this.next().css({'width': '100%'});
       		  })
       	});
}
</script>
<script type="text/javascript">
	function assignTasksList() {
		var sourceleadUrl ='${sourceleadUrl}';
		var date = $("#dasboardTaskDatpicker").val();
		var status =  "myTask";
		
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getTasksList?${_csrf.parameterName}=${_csrf.token}&status="+status+"&date="+date,
	        success: function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 result = result + '<table id="dasboardTasksPagination">'; 
	        	 result = result + '<thead>';
	         	result = result + '<tr>';
	         	result = result + '<th>Subject</th>';
	         	result = result + '<th>Date &amp; Time</th>';
	         	result = result + '<th>Priority</th>';
	         	result = result + '<th>Status</th>';
	         	result = result + ' </tr>';
	        	result = result + ' </thead>';
	        	result = result + ' <tbody>';
	        	for (var i=0; i<json.length; i++)
				{
	        		var report = json[i];
	        		
	        		if(report.taskDateStr != null && report.taskDateStr != '' && report.taskDateStr != 'undefined'){
	        			taskDateStr = report.taskDateStr
	        		}else{
	        			taskDateStr = "--";
	        		}
	        		if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
	        			taskTime = report.taskTime;
	        		}else{
	        			taskTime = "";
	        		}
	        		if(report.priority != null && report.priority != '' &&  report.priority != 'undefined'){
	        			priority = report.priority;
	        		}else{
	        			priority = "--";
	        		}
	        		
	        		result = result + '<tr>';
	        		result = result + ' <td><div title="'+report.subject+'"><span>'+report.subject+'</span></div></td>';
	        		result = result + '<td><div class="text-center">'+taskDateStr+'  '+taskTime+'</div></td>';
	        		result = result + '<td>'+priority+'</td>';
	        		result = result + ' <td>'+report.taskStatus+'</td>';
	        		result = result + ' </tr>';
	               
				}
	        	 result = result + '     </tbody>';
	        	 result = result + '   </table>';
	        	 $("#assignTaskList").empty();
	           	 $("#assignTaskList").append(result); 
	           	 if(json.length > 0){
	           		$("#assignTaskListDiv").css("display", "block");
	           	 }
	           	 
	           	dasboardTasksPagination();
	        }
		});
		
	}
</script>
<script>
function dasboardTasksPagination(){
    $('#dasboardTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
<script>
  $("#selector").fadeIn(300);
  $('#selector').delay(3000).fadeOut('slow');
</script> 
<script>
  $("#selector1").fadeIn(300);
  $('#selector1').delay(3000).fadeOut('slow');
</script>