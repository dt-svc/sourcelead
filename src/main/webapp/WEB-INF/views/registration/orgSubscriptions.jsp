<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="subscription" class="tab-pane fade">
 <div id="choicepopUp"></div>
<div class="project_tabs_inn_table vendor_wrap_inn_table mt">
<a href="javascript:void(0)" class="addcon-pop btn btn-sm btn-primary mb10" onclick="addSubscription();"><i class="glyphicon glyphicon-plus"></i>&nbsp;Subscribe</a>
<div id="subscriptionList"></div>
<div id="subscriptionListLoading"></div>
<div id="editsubscriptionListLoading"></div>
</div>

<form:form class="login-form clearfix"  id="subscribe_form"  name="subscribe_form" method="post" >
<div id="subscriptionLoading"></div>
<div id="subscribeOrg" class="clearfix"></div>
</form:form>


</div>



<script>
function addSubscription(licenseUUID){
	  $("#subscriptionList").empty();
	 
	  $("#subscriptionListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  var result = '';
	  
	    result = result +'<div class="comLogContent">';
		result = result +'<div class="row clearfix compOnBorLoca">';
		
		result = result +'<div class="col-md-6">';
		
		result = result +'<div class="form-group clearfix id="errCountryMsg">';
		result = result +'<label class="col-md-4 noPad">Country<i class="text-danger">&nbsp;*</i></label>';
		result = result +'<div class="col-md-6 padding_right">';
		result = result +'<select class="form-control" field-name="Country" data-validation="required" onchange="changeCountry(); getTimezonesList();" id="subscribeCountries" name="country">';
		result = result + '<option value="">Select Country</option>';
		result = result +'</select>';
		result = result +'</div>';
		result = result +'</div>';
		
		result = result +'<input type="hidden" name="licenseUUID" value=""  id="subscriptionLicenseUUID">';
		
		result = result +'<div class="form-group clearfix">';
		result = result +'<label class="col-md-4 noPad">Org Enrollment<i class="text-danger">&nbsp;*</i></label>';
		result = result +'<div class="col-md-6 padding_right">';
		
		result = result + '   <select class="form-control" name="subscriptionType" id="subscriptionType" onchange="getInvoiceRate();" field-name="Org Enrollment" data-validation="required">';
	 	result = result + '       <option value="">Select Enrollment Type</option>';
	 	result = result +'		<c:forEach var="subscriptionType" items="${subscriptionTypeList}">';
	 	result = result + '       <option value="${subscriptionType.id}">${subscriptionType.name}</option>';
	    result = result +'		</c:forEach>';
		result = result + '       <option value="6" id="guestOpt" style="display:none">Guest</option>';
		result = result + '       <option value="7" id="unlimitedOpt" style="display:none">Unlimited</option>';
	    result = result + '    </select>';
		
		result = result +'</div>';
		result = result +'</div>';
		
		result = result +'<div class="form-group clearfix">';
		result = result +'<label class="col-md-4 noPad">No.Of Users<i class="text-danger">&nbsp;*</i></label>';
		result = result +'<div class="col-md-6 padding_right">';
		
		result = result + '   <select class="form-control" name="userSubscription" id="userSubscription" onchange="getInvoiceRate();" field-name="Users" data-validation="required">';
	 	result = result + '       <option value="">Select Users</option>';
	 	result = result +'		<c:forEach var="userSubscription" items="${userSubscriptionTypeList}">';
	 	result = result + '       <option value="${userSubscription.id}">${userSubscription.name}</option>';
	    result = result +'		</c:forEach>';
	    result = result + '    </select>';
		
		result = result +'</div>';
		result = result +'</div>';
		
		
		result = result +'</div>';
		
		
		
		result = result +'<div class="col-md-6">';
		
		result = result +'<div class="form-group clearfix">';
		result = result +'<label class="col-md-4 noPad">Data Storage<i class="text-danger">&nbsp;*</i></label>';
		result = result +'<div class="col-md-6 padding_right">';
		
		result = result + '   <select class="form-control" name="storageType" id="dataStorage" field-name="Data Storate" data-validation="required">';
	 	result = result + '       <option value="">Select Data Storage</option>';
	 	result = result +'		<c:forEach var="dataStorage" items="${dataStorageTypeList}">';
	 	result = result + '       <option value="${dataStorage.id}">${dataStorage.name}</option>';
	    result = result +'		</c:forEach>';
	    result = result + '    </select>';
		
		result = result +'</div>';
		result = result +'</div>';
		
		
		result = result +'<div class="form-group clearfix">';
		result = result +'<label class="col-md-4 noPad">Time Zone<i class="text-danger">&nbsp;*</i></label>';
		result = result +'<div class="col-md-6 padding_right">';
		
		result = result + '   <select class="form-control chosen-select"  data-placeholder="Time Zone" name="timezone" id="timeZone" field-name="Time Zone" data-validation="required">';
	    
		result = result + '    </select>';
		
		result = result +'</div>';
		result = result +'</div>';
		
		
		result = result +'<div class="form-group">';
		result = result +'<label class="col-md-4 noPad">Subscription Date<i class="text-danger">&nbsp;*</i></label>';
		result = result +'<div class="col-md-6 padding_right">';
		result = result +'<div class="input-group" id="subscriptionDateDiv">';
		result = result +'<input type="text" name="subscriptionDate" class="form-control calendar" id="subscriptionDate" data-validation="required" field-name="Subscription Date" readonly="readonly">';
		result = result +'<b class="input-group-addon" onClick="clearDate();"><i class="colorRed fa fa-times cursorPointer"></i></b>';
		result = result +'</div>';
		result = result +'</div>';
		result = result +'</div>';
		
		result = result +'</div>';
		
		result = result +'<div class="clearfix"></div>';
		result = result +'<div class="tab-pane clearfix">';
		result = result +'<div class="form-horizontal">';
		result = result +'<div class="panel-group mt orgmodeulsAccordion" id="accordion">';
		result = result +'<div id="modulesList"></div>';
		result = result +'<div class="clearfix"></div>';
		result = result +'<div class="clearfix colmd-12 modal-footer">';
		result = result +'<div class="clearfix"></div>';
		result = result +'<div class="pull-right">';
		result = result +'<h4><strong>Total: <b class="ml mr5" id="subscriptionRate">0</b></strong><strong>$</strong></h4>';
		result = result +'<input type="hidden" name="invoiceAmount" id="invoiceAmount" value=""/>';
		result = result +'</div>';
		result = result +'</div>';
		result = result +'</div>';
		result = result +'<div class="clearfix"></div>';
		result = result +'</div>';
		result = result +'</div>';
		
		
		
		result = result +'</div>';
		result = result +'</div>';
		
		result = result +'<div class="form-footer clearfix miniFoot">';
		result = result +'<div class="col-lg-12">';
		result = result +'<div class="pull-right">';
		result = result +'<input type="submit" value="Register" onClick="registerCompanyModules()" class="btn btn-primary ml10">';
		result = result +'<input type="button" value="Cancel" onClick="orgSubscriptionList()"  class="btn btn-default ml">';
		result = result +'</div>';
		result = result +'</div>';
		result = result +'</div>';
		
	    $("#subscribeOrg").empty();
	    $("#subscribeOrg").append(result);
	    
	    
	    $("#subscriptionListLoading").removeAttr("style");
	    
	    var config = {
    			  '.chosen-select'           : {},
    			  '.chosen-select-deselect'  : {allow_single_deselect:true},
    			  '.chosen-select-no-single' : {disable_search_threshold:10},
    			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    			  '.chosen-select-width'     : {width:"95%"}
    			}
	    
	    $("#timeZone").chosen();
	    
	    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	 	  var finalDateFormat = getJSDateFormat(locationDate);
	 	  $('#subscriptionDate').datepicker({
	 	      dateFormat: finalDateFormat,
	 	      changeMonth: true,
	 	  	  changeYear: true, 
	 	  	  minDate : 0,
	 	  	 onClose : function(selected) {
              removeValidation();
			}
	 	  });
	  
	    getOrgModules();
	    
	    if(licenseUUID != null && licenseUUID != '' && licenseUUID != 'undefined'){
	    	 $("#subscriptionLicenseUUID").val(licenseUUID);
	    	 $("#editsubscriptionListLoading").attr('style','position:absolute; width:100%; height:950px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        setTimeout(function () {
	        	  licenseSubscriptionDetails(licenseUUID);
	        	  $("#editsubscriptionListLoading").removeAttr("style");
	 			}, 3000);
	    	
	    }else{
	    	  getSubscribeCountries();
	    	  getTimezonesList();
	    }
	    
}
</script>

<script>
function getTimezonesList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var country = $('#subscribeCountries').val();
	if(country){
		if(country == "India"){
			$("select[name = 'dateFormat']").val('dd-MM-yyyy');
		}else{
			$("select[name = 'dateFormat']").val('MM-dd-yyyy');
		}
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




<script>
function removeValidation(){
	$('#subscriptionDateDiv').find('p.jquery_form_error_message').remove();
}

function clearDate(){
	$("#subscriptionDate").val("");
	};
	
function changeCountry(){
	 $("#errCountryMsg").find("p.jquery_form_error_message").remove();
}
	
	
function getInvoiceRate(){
	var subscriptionType = $("#subscriptionType").val();
	var userSubscription = $("#userSubscription").val();
	
	if(subscriptionType && userSubscription){
		
		 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getInvoiceRateBySubscriptionRateAndUserSubscription?${_csrf.parameterName}=${_csrf.token}&subscriptionType="+subscriptionType+"&userSubscription="+userSubscription, 
		        success: function(invoiceAmount) {
		        	
		        	 $("#subscriptionRate").html(invoiceAmount);
			        
		        },
		    }); 
	}
}	
	
</script>

<script>
function getOrgModules(){
	 var result = '';
	
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getRegistrationTabs?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(json) {
	        	
	        	 result = result +'<ul class="list-unstyled list-inline">';
	        	 
	        	
	        	for(var i=0; i<json.length; i++){
        			var opt = json[i];
        			var checked = '';
        			var disabled = '';
        			var selectedTab = '';
        			if(opt.tabName === 'Associates' || opt.tabName === 'Settings'){
        				checked = 'checked';
        				disabled = 'disabled';
        				selectedTab = 'activeOrgbox';
        			}
        			
        			 result = result +'<li class="col-md-4">';
        			 result = result +'<div class="panelDefault clearfix '+selectedTab+'" id="module'+opt.id+'">';
        			 result = result +'<div class="mediaLeft pull-left">';
        			 result = result +'<i class="'+opt.tabIcon+'"></i>';
        			 result = result +'</div>';
        			 result = result +'<div class="pull-left checkBox mt10">';
        			 result = result +'<input type="checkbox" id="checkbox'+opt.id+'" '+checked+' '+disabled+' value="'+opt.id+'" name="registeredModules" onclick="checkModule('+opt.id+')">';
        			 result = result +'<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" class="checkBox">';
        			 result = result +''+opt.tabName+'</a>';
        			 result = result +'</div>';
        			 result = result +'</div>';
        			 result = result +'</li>';
        			 
	        	}
	        	
	        	 result = result +'</ul>';
	        	 
	        	 
	        	 $("#modulesList").empty();
	             $("#modulesList").append(result);
		        
	        },
	    }); 
	  
	
}
</script>

<script>
function getSubscribeCountries(country){
	  var orgId = '${orgId}';
	  var sourceleadUrl ='${sourceleadUrl}';
	
	  if(country == null || country == '' || country == 'undefined'){
		  $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/getOrgSubscribeCountries?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId, 
		        success: function(json) {
		        var output = "";
		           output = output + "<option value=''>Select Country</option>";
		            for (var i=0; i<json.length; i++){
		        		var opt = json[i];
		        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
					}
		        	$("#subscribeCountries").empty();
		        	$("#subscribeCountries").append(output);
		        }
		 });
	  }else{
	      	    var output = "";
	            output = output + "<option value='" +country+ "'>"+country+"</option>";
	        	$("#subscribeCountries").empty();
	        	$("#subscribeCountries").append(output);
	  }

	  
}
</script>


<script>
function orgSubscriptionList(){
	  var sourceleadUrl ='${sourceleadUrl}';
      var orgId = '${orgId}';
      
      $("#subscribeOrg").empty();
      
	 $("#subscriptionListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		  type : "POST",
		  url: sourceleadUrl+"/getOrgSubscribedList?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId,      
           success :function(json) {
	                	 var result = "";
	                	 result = result +"<table id='example' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"<tr>"; 
	               		 result = result +'<th>Country</th>';
	               	     result = result +'<th>Subscription Type</th>';
	               	     result = result +'<th>User Subscription</th>';
	               		 result = result +'<th>Invoice Amount</th>';
	               		 result = result +'<th>From Date<br>(<small class="custom-small">DD-MM-YYYY</small>)</th>';
	               		 result = result +'<th>To Date<br>(<small class="custom-small">DD-MM-YYYY</small>)</th>';
	               		 result = result +'<th>Storage</th>';
	               		 result = result +'<th>Modules</th>';
		                 result = result +"</tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 
	                    	 var userSubscription = '';
	                    	 var invoiceAmount = '';
	                    	 var toDateStr = '';
	                    	 
	                    	 if(opt.userSubscription != null && opt.userSubscription != '' && opt.userSubscription != 'undefined'){
	                    		 userSubscription =  opt.userSubscription
	                    	 }else{
	                    		 userSubscription = '----';
	                    	 }
	                    	 
	                    	 if(opt.invoiceAmount != null && opt.invoiceAmount != '' && opt.invoiceAmount != 'undefined'){
	                    		 invoiceAmount =  opt.currency+" "+opt.invoiceAmount
	                    	 }else{
	                    		 invoiceAmount = '----';
	                    	 }
	                    	 
	                    	 if(opt.toDateStr != null && opt.toDateStr != '' && opt.toDateStr != 'undefined'){
	                    		 toDateStr =  opt.toDateStr
	                    	 }else{
	                    		 toDateStr = '----';
	                    	 }
	               
	                    	 result = result +'<tr>';
	                    	 result = result +'<td><a href="#"><div class="tbldata-dotdot-100" title='+opt.country+' onclick="addSubscription(\''+opt.licenseUUID+'\');">'+opt.country+'</div></a></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-80" title='+opt.subscriptionType+'>'+opt.subscriptionType+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot_80" title='+userSubscription+'>'+userSubscription+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-80" title='+invoiceAmount+'>'+invoiceAmount+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-80" title='+opt.fromDateStr+'>'+opt.fromDateStr+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-80" title='+toDateStr+'>'+toDateStr+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-80" title='+opt.storageType+'>'+opt.storageType+'</div></td>';
	                    	 
	                 	    result = result +'<td>';
		   		        	result = result +'<div class="positionRelative">';
			   		         for(var k=0; k<2; k++){
			   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.modulesList[k]+'</p>';
			   		         }
		   		    	
			   		        result = result +'<div class="show_Empdetails showjoblist">';
		   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';
	
		   		        	result = result +'<div class="emailvalues emailvaluesNew">';
		   		        	
 							for(var k=0; k<opt.modulesList.length; k++){
 								result = result +'<p><i class="fa fa-check-square-o"></i>'+opt.modulesList[k]+'</p>';
 			   		        	
		   		    	 	}
		   		        	result = result +'</div>';
		   		        	result = result +'</div>';

		   		        	result = result +'</div>';
		   		        	result = result +'</td>';
	                    	 
	                    	result = result +'</tr>';
	                    	
                    }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#subscriptionList").empty();
	                     $("#subscriptionList").append(result); 
	                     $("#subscriptionListLoading").removeAttr("style");
         },
           
      }); 
	 
}
</script>

<script>
function licenseSubscriptionDetails(licenseUUID){
	   var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
		  type : "POST",
		  url: sourceleadUrl+"/getLocationSubscriptionDetails?${_csrf.parameterName}=${_csrf.token}&licenseUUID="+licenseUUID,
           success :function(json) {
        	  getSubscribeCountries(json.country);
        	 
        	  
        	  if(json.subscriptionType == '6'){
        		  document.getElementById("guestOpt").style.display="";
        	  }
        	  
        	  if(json.subscriptionType == '7'){
        		  document.getElementById("unlimitedOpt").style.display="";
        	  }
        	  
        	  $("select[name=subscriptionType]").val(json.subscriptionType);
        	
        	 if(json.userSubscription != null && json.userSubscription != '' && json.userSubscription != 'undefined'){
        		 $("select[name=userSubscription]").val(json.userSubscription);
        	 }
        	 
        	 if(json.storageType != null && json.storageType != '' && json.storageType != 'undefined'){
        		 $("select[name=storageType]").val(json.storageType);
        	 }
        	
        	 getTimezonesList(); 
        	
        	 if(json.timezone != null && json.timezone != '' && json.timezone != 'undefined'){
        		 $("select[name=timezone]").val(json.timezone);
    	         $("#timeZone").trigger("chosen:updated");
        	 }
        	
	         $("input[name=subscriptionDate]").val(json.fromDateStr);
	         
	         if(json.invoiceAmount != null && json.invoiceAmount != '' && json.invoiceAmount != 'undefined'){
	        	 $("#subscriptionRate").html(json.invoiceAmount);
	         }else{
	        	 $("#subscriptionRate").html("0");
	         }
	         
	         
	        	 for(var i=0; i<json.modulesIdsList.length; i++){
	        		var opt = json.modulesIdsList[i];
	        		
	        		if(document.getElementById("checkbox"+opt) != null){
	        			 document.getElementById("checkbox"+opt).checked = true;
		    		     document.getElementById("module"+opt).removeAttribute("class")
		    		     document.getElementById("module"+opt).className = 'panelDefault clearfix activeOrgbox'
	        		}
	        		
	        			
	        	 }
	         
	         
         },
           
      });  
}

</script>




<script>
function checkModule(id){
	
	var isChecked = document.getElementById("checkbox"+id).checked
	if(isChecked == true){
		if(id == '19'){
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
				 
			 document.getElementById("checkbox10").checked = true;
		     document.getElementById("module10").removeAttribute("class")
		     document.getElementById("module10").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '4'){
			
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox3").checked = true;
			 document.getElementById("module3").removeAttribute("class")
		     document.getElementById("module3").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '23'){
			
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox3").checked = true;
			 document.getElementById("module3").removeAttribute("class")
		     document.getElementById("module3").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '16'){
			
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox7").checked = true;
			 document.getElementById("module7").removeAttribute("class")
		     document.getElementById("module7").className = 'panelDefault clearfix activeOrgbox'
		     
	    	 document.getElementById("checkbox8").checked = true;
			 document.getElementById("module8").removeAttribute("class")
		     document.getElementById("module8").className = 'panelDefault clearfix activeOrgbox'
		     
	    	 document.getElementById("checkbox9").checked = true;
			 document.getElementById("module9").removeAttribute("class")
		     document.getElementById("module9").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '20'){
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox7").checked = true;
			 document.getElementById("module7").removeAttribute("class")
		     document.getElementById("module7").className = 'panelDefault clearfix activeOrgbox'
		     
	    	 document.getElementById("checkbox9").checked = true;
			 document.getElementById("module9").removeAttribute("class")
		     document.getElementById("module9").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '30'){
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox8").checked = true;
			 document.getElementById("module8").removeAttribute("class")
		     document.getElementById("module8").className = 'panelDefault clearfix activeOrgbox'
		     
	    	 document.getElementById("checkbox10").checked = true;
			 document.getElementById("module10").removeAttribute("class")
		     document.getElementById("module10").className = 'panelDefault clearfix activeOrgbox'
		}else{
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
		}
		
		
		
	}else{
		
		var isdependentTabChecked = false;
		dependentTabId = '';
		var tab1 = '';
		var tab2 = '';
		var tab3 = '';
		var tab4 = '';
		if(id == '10'){
			dependentTabId = '19';
			tab1 = 'On & Off Boarding'
			tab2 = 'Candidate';
			
			var financeTabChecked = document.getElementById("checkbox30").checked;
			if(financeTabChecked){
				var jobsTab = document.getElementById("checkbox8").checked;
				if(jobsTab == false){
					dependentTabId = '30';
					tab1 = 'Campaigns'
					tab2 = 'Candidates';
					tab3 = 'Jobs'
				}
			}
			
		}else if(id == '3'){
			var tsTabChecked = document.getElementById("checkbox4").checked;
			var expencesTabChecked = document.getElementById("checkbox23").checked;
			if(tsTabChecked == true){
				dependentTabId = '4';
				tab1 = 'Time Sheets'
				tab2 = 'Projects';
			}else if(expencesTabChecked == true){
				dependentTabId = '23';
				tab1 = 'Expences'
				tab2 = 'Projects';
			}
		}else if(id == '7'){
			var contractsTabChecked = document.getElementById("checkbox16").checked;
			if(contractsTabChecked == true){
				var jobsTab = document.getElementById("checkbox8").checked;
				var vendorTab = document.getElementById("checkbox9").checked;
				if(jobsTab == false && vendorTab == false){
					dependentTabId = '16';
					tab1 = 'Contracts'
					tab2 = 'Clients';
					tab3 = 'Vendors';
					tab4 = 'Jobs';
				}
			}
			
			
			var financeTabChecked = document.getElementById("checkbox20").checked;
			if(financeTabChecked){
				var vendorTab = document.getElementById("checkbox9").checked;
				if(vendorTab == false){
					dependentTabId = '20';
					tab1 = 'Finance'
					tab2 = 'Clients';
					tab3 = 'Vendors'
				}
			}
			
			
		}else if(id == '8'){
			var contractsTabChecked = document.getElementById("checkbox16").checked;
			if(contractsTabChecked == true){
				var clientTab = document.getElementById("checkbox7").checked;
				var vendorTab = document.getElementById("checkbox9").checked;
				if(clientTab == false && vendorTab == false){
					dependentTabId = '16';
					tab1 = 'Contracts'
					tab2 = 'Jobs';
					tab3 = 'Clients';
					tab4 = 'Vendors';
				}
			}
			
			var financeTabChecked = document.getElementById("checkbox30").checked;
			if(financeTabChecked){
				var candidatesTab = document.getElementById("checkbox10").checked;
				if(candidatesTab == false){
					dependentTabId = '30';
					tab1 = 'Campaigns'
					tab2 = 'Jobs';
					tab3 = 'Candidates'
				}
			}
			
			
		}else if(id == '9'){
			var contractsTabChecked = document.getElementById("checkbox16").checked;
			if(contractsTabChecked == true){
				var clientTab = document.getElementById("checkbox7").checked;
				var jobTab = document.getElementById("checkbox8").checked;
				if(clientTab == false && jobTab == false){
					dependentTabId = '16';
					tab1 = 'Contracts'
					tab2 = 'Vendor';
					tab3 = 'Clients';
					tab4 = 'Jobs';
				}
			}
			
			var financeTabChecked = document.getElementById("checkbox20").checked;
			if(financeTabChecked){
				var clientTab = document.getElementById("checkbox7").checked;
				if(clientTab == false){
					dependentTabId = '20';
					tab1 = 'Finance'
					tab2 = 'Vendors';
					tab3 = 'Clients'
				}
			}
		}
		
		if(dependentTabId != null && dependentTabId != '' && dependentTabId != 'undefined'){
			isdependentTabChecked = document.getElementById("checkbox"+dependentTabId).checked;
		}
		
		if(isdependentTabChecked == true){
			document.getElementById("checkbox"+id).checked = true;
			if(tab3 != '' && tab4 != ''){
				alertMessage2(tab1,tab2,tab3,tab4);
			}else if(tab3 != '' && tab4 == ''){
				alertMessage3(tab1,tab2,tab3,tab4);
			}else{
				alertMessage1(tab1,tab2);
			}
			
		}else{
			document.getElementById("module"+id).removeAttribute("class")
			document.getElementById("module"+id).className = 'panelDefault clearfix'
		}
		
	}
	
	
}
</script>


<script type="text/javascript">
function alertMessage1(tab1,tab2){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p><strong>"+tab2+"</strong> should must for <strong>"+tab1+"</strong></p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script type="text/javascript">
function alertMessage2(tab1,tab2,tab3,tab4){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p><strong>"+tab2+"</strong> or <strong>"+tab3+"</strong> or <strong>"+tab4+"</strong>  should must for <strong>"+tab1+"</strong></p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function alertMessage3(tab1,tab2,tab3){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p><strong>"+tab2+"</strong> or <strong>"+tab3+"</strong> should must for <strong>"+tab1+"</strong></p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#subscribe_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		var formData = getFormData("subscribe_form");
         		var sourceleadUrl ='${sourceleadUrl}';
         		var orgId = '${orgId}';
         		
         		$("#subscriptionLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 
         		
         		var registeredModules = [];
        		$('input:checkbox[name=registeredModules]:checked').each(function() 
        	    {
        			registeredModules.push($(this).val());
        	    });
        		
        		
           		 $.ajax({
          	      type: "POST",
          	    	data: formData,
                	  url: sourceleadUrl+"/saveOrgLocationSubscription?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId+"&registeredModules="+registeredModules,
                  	success: function(result) {
			         	if(result == 'subscribeList'){
			         		$("#subscriptionLoading").removeAttr('style');
			         		orgSubscriptionList();
			         	}
			        	else if(orglocations.registrationStatusStr == 'DataMissing'){
			        		$("#subscriptionLoading").removeAttr('style');
			        	} 
			        	
			        },  
               });    
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
            
         $('body').on('blur', '#subscribe_form', function() {
     		$("#subscribe_form").showHelpOnFocus();
     		$("#subscribe_form").validateOnBlur(false, validationSettings);
     	});
     
	</script>

