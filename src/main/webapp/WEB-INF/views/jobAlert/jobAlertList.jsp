<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script>
	function alertMsgFadeinFunction() {
		$("#jobAlert1").fadeIn(200);
        $('#jobAlert1').delay(50000).fadeOut('slow');
	}
</script> 
<script>
	function editAlertMsgFadeinFunction() {
		$("#jobAlert2").fadeIn(200);
        $('#jobAlert2').delay(50000).fadeOut('slow');
	}
</script> 
<section class="content-wraper">
    <div id="offboardingLoading"></div>
   
    <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Job Alert List</h2>
          </div>
           <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Job Alerts</div>
    </section>
    
    <div class="timesheet_out">
			<div class="project_wrap">
			<div class="timesheet_head timeHd_minHeight">
			<ul>
			<li><a href="javascript:void(0)" class="alertBtn" onClick="alertFunction();createAlert();"><i class="fa fa-bell-o paddingRight"></i>Create Alert</a></li>
			</ul>
			     
            </div>
			
			    <div class="clients_wrap_table datatable_scroll">
				    <div id="jobAlertList" class="datatable_filter"></div>
				</div>	   
					
			</div>
	   </div>	
        
</section>

    <div class="pop_mask"></div>
 <!-- Modal -->
  <div class="modal modal_createalert" id="myModal" role="dialog">
    <div id="createAlertPopUpDiv"></div>
  </div>
  <!-- Create Alert modal Popup Ends -->
<script>
//Create Alert Poppup
function alertFunction() {
	$(".pop_mask").fadeIn();
	$(".modal.modal_createalert").fadeIn();
}
function alertClose(){
	$(".pop_mask").fadeOut();
	$(".modal.modal_createalert").fadeOut();
}
</script>
<script src="/resources/js/jobs/js/bootstrap-select.min.js"></script>
<script>
function createAlert(){
	var email = '${SourceLeadWeb_user.email}';
	
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/validateAlertUserEmail?${_csrf.parameterName}=${_csrf.token}&email="+email,
		success : function(data) {
			if(data != null && data != ''){
				
				if(data == "valid"){
					 
					 result = "";
					 result = result +' <form id="create_alert_form" name="create_alert_form" method="post">';
					 result = result +'	 <div class="modal-dialog modal-lg">';
					 result = result +' <div class="success_pop" id="jobAlert1" style="display: none;">';
					 result = result +'  <i class="fa fa-check-circle"></i><span id="jobAlertMsgDiv"></span>';
					 result = result +' </div> ';
					 result = result +'     <div class="modal-content">';
					 result = result +'       <div class="modal-header">';
					 result = result +'         <button type="button" class="close" data-dismiss="modal" onClick="alertClose();">&times;</button>';
					 result = result +'         <h4 class="modal-title">Job Alert</h4>';
					 result = result +'       </div>';
					 result = result +'       <div class="modal-body myForm clearfix">';
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Alert Title<span class="text-danger paddingLeft">*</span></label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'         		<input type="text" class="form-control input-sm" value="" placeholder="Alert Title" name="alertTitle" field-name="Alert Title" data-validation="required validate_length length2-100">';
					 result = result +'         	</div>';
					 result = result +'         </div>';

					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Location</label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result + '             <input type="text" class="form-control input-sm" name="location"  id="jobLocation"  placeholder="Location" field-name="Locatin" >';
					 result = result + '                <i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'jobLocation\',\'jobCountry\',\'jobState\',\'jobCity\',\'jobZipCode\');"></i>';
					 result = result +'         	</div>';
					 result = result +'         </div>';
					 
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Country</label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'         		<input type="text" class="form-control" placeholder="Country" id="jobCountry" name="country" readonly>';
					 result = result +'         	</div>';
					 result = result +'         </div>';
					 
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">State</label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'         		<input type="text" class="form-control" placeholder="State" id="jobState" name="state" readonly>';
					 result = result +'         	</div>';
					 result = result +'         </div>';
					 
					 
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">City</label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'         		<input type="text" class="form-control" placeholder="City" id="jobCity" name="city" readonly>';
					 result = result +'         	</div>';
					 result = result +'         </div>';
					 
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Zip Code</label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'         		<input type="text" class="form-control" placeholder="Zip Code" id="jobZipCode" name="zipcode">';
					 result = result +'         	</div>';
					 result = result +'         </div>';

					 
					 
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Experience</label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'         		<select class="form-control" id="experience" name="experience">';
					 result = result +'         			<option selected  value="">---Select Experience---</option>';
					 result = result +'         			<c:forEach begin="0" end="30" varStatus="loop">';
					 result = result +'         			<option>${loop.index}</option>';
					 result = result +'         			</c:forEach>';
					 result = result +'         		</select>';
					 result = result +'         	</div>';
					 result = result +'         </div>';
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Salary/Rate</label>';
					 result = result +'	<div class="col-md-9">';
					 result = result +'            <div class="input-group er ratetype-validation">';
					 result = result +'               <div class="input-group-btn tblminwidth-75">';
					 result = result +'                 <select class="btn-primary select_picker form-control" id="rateCurrency" name="rateCurrency">';
					 result = result +'                <option ${currencyCode  == "INR" ? "selected" : ''}>INR</option>';
					 result = result +'                <option ${currencyCode  == "AUD" ? "selected" : ''}>AUD</option>';
					 result = result +'                <option ${currencyCode  == "USD" ? "selected" : ''}>USD</option>';
					 result = result +'                <option ${currencyCode  == "GBP" ? "selected" : ''}>GBP</option>';
					 result = result +'                <option ${currencyCode  == "SGD" ? "selected" : ''}>SGD</option>';
					 result = result +'                <option ${currencyCode  == "EUR" ? "selected" : ''}>EUR</option>';
					 result = result +'               </select>';
					 result = result +'               </div>';
					 
					 result = result +'              <input type="text" id="currentRate" name="rate" field-name="rate" onblur="getRate();" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true" value="" class="form-control" placeholder="Rate">';
					 result = result +'               <div class="input-group-btn tblminwidth-75 ratetype-validation">';
					 result = result +'                 <select class="btn-primary select_picker form-control" id="rateType" name="rateType" data-validation-optional="true" field-name="Rate Type">';
					 result = result +'                   <option value="">Rate Per</option>';
					 result = result +'                   <option >Hourly</option>';
					 result = result +'                   <option >Weekly</option>';
					 result = result +'                   <option >Bi-weekly</option>';
					 result = result +'                   <option >Semi-Monthly</option>';
					 result = result +'                   <option >Monthly</option>';
					 result = result +'                   <option >Bi-Monthly</option>';
					 result = result +'                   <option >Yearly</option>';
					 result = result +'                 </select>';
					 result = result +'               </div>';
					 result = result +'                <div class="clearfix"></div> <div class="clearfix"></div>';
					 result = result +'             </div>';
					 result = result +'           </div>';
					 result = result +'         </div>';
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Industry</label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'         		<select class="form-control chosen-select" data-placeholder="Industry" id="industry" name="industry">';
					 result = result +'         			<option selected value="">---Select Industry---</option>';
					 result = result +'         			<c:forEach var="industry" items="${industryList}">';
					 result = result +'         			<option value="${industry}">${industry}</option>';
					 result = result +'         			</c:forEach>';
					 result = result +'         		</select>';
					 result = result +'         	</div>';
					 result = result +'         </div>';
					
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Alert Frequency<span class="text-danger paddingLeft">*</span></label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'         		<select class="form-control" id="duration" name="duration" field-name="Alert Frequency" data-validation="required">';
					 result = result +'         			<option selected  value="">---Select Alert Frequency---</option>';
					 result = result +'         			<option>Daily</option>';
					 result = result +'         			<option>Weekly</option>';
					 result = result +'         			<option>Monthly</option>';
					 result = result +'         		</select>';
					 result = result +'         	</div>';
					 result = result +'         </div>';
					 result = result +'         <div class="form-group clearfix">';
					 result = result +'         	<label class="col-md-3">Keywords / Skills <span class="text-danger paddingLeft">*</span></label>';
					 result = result +'         	<div class="col-md-9">';
					 result = result +'			<select class="form-control chosen-select" data-placeholder="Keywords/Skills"  multiple id="skills" name="skills" data-validation="required" field-name="Keywords/Skills">';
					 
					          		 <c:forEach var="skill" items="${skillsList}">
											result = result + "<option value='${skill.name}'>${skill.name}</option>";
						             </c:forEach>
						            
					 result = result +'       </select>';
					 result = result +'         	</div>';
					 result = result +'         </div>';
					 result = result +'       </div>';
					 result = result +'       <div class="modal-footer">';
					 result = result +'         <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal" onclick="saveAlert();">Save</button>';
					 result = result +'         <button type="button" class="btn btn-default btn-sm ml10" data-dismiss="modal" onClick="alertClose();">Cancel</button>';
					 result = result +'       </div>';
					 result = result +'     </div>';
					 result = result +'   </div>';	
					 result = result +'</form>';
				 $("#createAlertPopUpDiv").empty();
				 $("#createAlertPopUpDiv").append(result);
				 initLocationAuto('jobLocation','jobCountry','jobState','jobCity','jobZipCode','','','');
				 var config = {
							  '.chosen-select'           : {},
							  '.chosen-select-deselect'  : {allow_single_deselect:true},
							  '.chosen-select-no-single' : {disable_search_threshold:10},
							  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
							  '.chosen-select-width'     : {width:"95%"}
				 }
				 for (var selector in config) {
					    $(selector).chosen(config[selector]);
					    }
					    $('.chosen-select').each(function() {
					    var $this = $(this);
					    $this.next().css({'width': '100%'});
					    })
					    
					    var select, chosen;
					    select = $("#skills");
					    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
					    chosen = select.data('chosen');
					    
					    $("#skills").chosen().change(function(event) {
					    	var allSkills = $(event.target).val();
					    	$.ajax({
						    	type: "POST",
						        url: sourceleadUrl+"/profileBySkillSet?${_csrf.parameterName}=${_csrf.token}&allSkills="+allSkills, 
						        success: function(response) {
						        	if (response.response === "AWKWARD") {
						        	    location.href = "/errorPage";
						        	} else if (response.response === "SUCCESS") {
						        		var primarySkill = response.object;
							        	$("#primarySkill").val(primarySkill);
						        	} else if (response.indexOf('Sign In') !== -1) {
						        		location.replace(sourceleadUrl);
						        	} else {
						        	    showError("Operation failed, please try again");
						        	}
						        },
						    });
					    	
					    });
				    
				       chosen.search_field.on('keyup', function(e)
						    {
						    	   var skillsArray = this.value.split(",");
							    	 if(skillsArray.length > 1){
						        		 for (var i=0; i < skillsArray.length; i++) {
						        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
						        			 select.append(option);
						 		             select.find(option).prop('selected', true);
						 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
						 	        		 $("#skills").trigger("chosen:updated");
						                 }
							    	 }else{
						    			var newSkill = this.value.replace(","," ");
						    			 if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
						                {
						    				 var option = $("<option>").val(newSkill).text(newSkill);
						                     select.append(option);
						                     select.find(option).prop('selected', true);
						                     $("#skills option").each(function(){
						                         $(this).siblings("[value='"+ this.value+"']").remove();
						                   });
						                   $("#skills").trigger("chosen:updated");
						                }
								       
					    	         }
						    });
				}else{
					 
					 result = "";
					 result = result +'	 <div class="modal-dialog modal-lg">';
					 result = result +'     <div class="modal-content">';
					 result = result +'       <div class="modal-header">';
					 result = result +'         <button type="button" class="close" data-dismiss="modal" onClick="alertClose();">&times;</button>';
					 result = result +'         <h4 class="modal-title">Job Alert</h4>';
					 result = result +'       </div>';
					 
					 result = result +'       <div class="modal-body myForm clearfix">';
					 result = result +'       <p><i class="fa fa-smile-o text-success fa-4x;"></i> You can configure maximum 5 job alerts.</p>';
					 result = result +'       </div>';
					 
					 result = result +'       <div class="modal-footer">';
					 result = result +'         <button type="button" class="btn btn-default btn-sm ml10" data-dismiss="modal" onClick="alertClose();">Cancel</button>';
					 result = result +'       </div>';
					 result = result +'     </div>';
					 result = result +'   </div>';
					 $("#createAlertPopUpDiv").empty();
					 $("#createAlertPopUpDiv").append(result);
					
				}
				
			}
		},
	});
	
	
	 
}
</script>

<script>
function saveAlert(){
	var jobAlertUUID = $("#jobAlertUUID").val();
	if ($('#create_alert_form').validate(false, validationSettings)){ 

		var sourceleadUrl = '${sourceleadUrl}';
	    var formData = getFormData1("create_alert_form");

		$.ajax({
					type : "POST",
					data: formData,
	  		         processData: false,
	  		         contentType:false, 
					url : sourceleadUrl+"/storeJobAlert?${_csrf.parameterName}=${_csrf.token}",
					success : function(result) {
						 if(jobAlertUUID != null && jobAlertUUID != '' && jobAlertUUID != 'undefined'){
							$("#jobAlert2").removeAttr("style");
	            			$("#jobAlert2").css({ display: "block" });
	        				$("#editJobAlertMsgDiv").html("Job Alert Edit Successfully");
								editAlertMsgFadeinFunction();
						}else{
							$("#jobAlert1").removeAttr("style");
	            			$("#jobAlert1").css({ display: "block" });
	        				$("#jobAlertMsgDiv").html("Success!! New Alert added");
								alertMsgFadeinFunction();
						} 
						
							location.replace(sourceleadUrl+"/jobAlertList");
					},
				});
		return true;
	 }else{ 
			return false;
	 } 
}	 
$('body').on('blur', '#create_alert_form', function() {
	$("#create_alert_form").showHelpOnFocus();
	$("#create_alert_form").validateOnBlur(false, validationSettings);  
});
</script>
<script>
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
     }; 
</script>
<script> 
$(document).ready(function(){
	jobAlertList();
});
</script>
 
<script>
function jobAlertList(){  
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getJobAlertList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
               		
			    	     $("#offboardingLoading").removeAttr("style");
	                	 $("#jobAlertList").empty();
	                	 var result = "";
	                	 result = result +"<table id='offboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Title</th>";
	                     result = result +"    <th>Experience</th>";
	                     result = result +"    <th>Salary/Rate</th>";
	                     result = result +"    <th>Industry</th>";
	                     result = result +"    <th>Keyword/skills</th>";
	                     result = result +"<th>Actions</th>";
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var report = json[i]; 
	                    	 if(report.experience != null && report.experience != '' && report.experience != 'undefined'){
	                    		 experience = report.experience + "&nbsp; Years";
	                    	 }else{
	                    		 experience = "--";
	                    	 }
	                    	 
	                    	 if(report.rate != null && report.rate != '' && report.rate != 'undefined'){
	                    		 rate = report.rate;
	                    	 }else{
	                    		 rate = "--";
	                    	 }
	                    	 if(report.industryName != null && report.industryName != '' && report.industryName != 'undefined'){
	                    		 industryName = report.industryName;
	                    	 }else{
	                    		 industryName = "--";
	                    	 }
	                    	 
	                    	 if(report.skillsName != null && report.skillsName !='' && report.skillsName != 'undefined'){
	                    		 skillsName = report.skillsName;
	                    	 }else{
	                    		 skillsName =  "--";
	                    	 }
	                    	 
	                    	 result = result +"<tr id='jobAlert"+report.jobAlertUUID+"'>";
	                    		 result = result +"<td><div class='tbldata-dotdot-sm' title='"+report.alertTitle+"'><span >"+report.alertTitle+"</span></div></td>";
	                    		 result = result +"<td><div><span>"+experience+"</span></div></td>";
	                    		 if(report.rate != null && report.rate != '' && report.rate != 'undefined'){
	                    		 result = result +"<td><div  title="+rate+"><span>"+report.rateCurrency+" " +rate+"/"+report.rateType+"</span></div></td>";
	                    		 }
	                    		 else{
	                    			 result = result +"<td><div class='tbldata-dotdot-sm' ><span >--</span></div></td>";
		                    	 }
	                    		 result = result +"<td><div  title='"+industryName+"'><span>"+industryName+"</span></div></td>";
	                    		 result = result +"<td><div  title='"+skillsName+"'><span>"+skillsName+"</span></div></td>";
			                     result = result +"<td><div class=''>";
			                     result = result +'<a class="glyphicon glyphicon-edit" title="Edit" onclick="alertFunction();editAlert(\''+report.jobAlertUUID+'\');"></a>';	
			                     result = result +'<a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteJobAlert(\''+report.jobAlertUUID+'\',\''+report.alertTitle+'\');"></a>';	
			                     result = result +"</div></td>"; 
		                     result = result +"</tr>";
	                    }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result + '<div id="choicepopUp"></div>';
	                     $("#jobAlertList").append(result); 
	                     
	                     offboardingPagenation(); 
                    
          },
            
       }); 
  } 
</script> 



<script>
function editAlert(jobAlertUUID){
	
	 $.ajax({
         type : "GET",
         url: sourceleadUrl+"/getJobAlertDetails?${_csrf.parameterName}=${_csrf.token}&jobAlertUUID="+jobAlertUUID,    
          success :function(data) {
        	  var json = JSON.parse(data);
        	  var experience = json['experience'];
        	  var rateCurrency = json['rateCurrency'];
        	  var rate = '';
        	  var state = '';
        	  var location ='';
        	  var country = '';
        	  var city = '';
        	  var zipCode ='';
        	  if(json['rate'] != null && json['rate'] != 'undefined' && json['rate'] != ''){
        		  rate = json['rate'];
        	  }
        	  
        	  var rateType = json['rateType'];
        	  if(json['location'] != null && json['location'] != '' && json['location'] != 'undefined'){
        		  location = json['location'];
        	  }
        	  if(json['country'] != null && json['country'] != '' && json['country'] != 'undefined'){
        		  country = json['country'];
        	  }
        	  if(json['state'] != null && json['state'] != '' && json['state'] != 'undefined'){
        		  state = json['state'];
        	  }
        	  if(json['city'] != null && json['city'] != '' && json['city'] != 'undefined'){
        		  city = json['city'];
        	  }
        	  if(json['zipCode'] != null && json['zipCode'] != '' && json['zipCode'] != 'undefined'){
        		  zipCode = json['zipCode'];
        	  }
        	     result = "";
        		 result = result +' <form id="create_alert_form" name="create_alert_form" method="post">';
        		 result = result + '<input type="hidden" name="jobAlertUUID" id="jobAlertUUID" value="'+jobAlertUUID+'" >';
        		 result = result +' <div class="success_pop" id="jobAlert2" style="display: none;">';
				 result = result +'  <i class="fa fa-check-circle"></i><span id="editJobAlertMsgDiv"></span>';
				 result = result +' </div> ';
        		 result = result +'	 <div class="modal-dialog modal-lg">';
        		 result = result +'     <div class="modal-content">';
        		 result = result +'       <div class="modal-header">';
        		 result = result +'         <button type="button" class="close" data-dismiss="modal" onClick="alertClose();">&times;</button>';
        		 result = result +'         <h4 class="modal-title">Job Alert</h4>';
        		 result = result +'       </div>';
        		 result = result +'       <div class="modal-body myForm clearfix">';
        		 result = result +'         <div class="form-group clearfix">';
        		 result = result +'         	<label class="col-md-3">Alert Title<span class="text-danger paddingLeft">*</span></label>';
        		 result = result +'         	<div class="col-md-9">';
        		 result = result +'         		<input type="text" class="form-control input-sm" value="'+json['alertTitle']+'" placeholder="Alert Title" name="alertTitle" field-name="Alert Title" data-validation="required validate_length length2-100">';
        		 result = result +'         	</div>';
        		 result = result +'         </div>';
        		 
        		 result = result +'<input type="hidden" name="savedJobAlertId" value="'+json['id']+'">';
        		 
        		 result = result +'         <div class="form-group clearfix">';
				 result = result +'         	<label class="col-md-3">Location</label>';
				 result = result +'         	<div class="col-md-9">';
				 result = result + '             <input type="text" class="form-control input-sm" name="location"  id="jobLocation" value="'+location+'" placeholder="Location" field-name="Locatin" >';
				 result = result + '                <i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'jobLocation\',\'jobCountry\',\'jobState\',\'jobCity\',\'jobZipCode\');"></i>';
				 result = result +'         	</div>';
				 result = result +'         </div>';
				 
				 result = result +'         <div class="form-group clearfix">';
				 result = result +'         	<label class="col-md-3">Country</label>';
				 result = result +'         	<div class="col-md-9">';
				 result = result +'         		<input type="text" class="form-control" placeholder="Country" id="jobCountry" name="country" value="'+country+'" readonly>';
				 result = result +'         	</div>';
				 result = result +'         </div>';
				 
				 result = result +'         <div class="form-group clearfix">';
				 result = result +'         	<label class="col-md-3">State</label>';
				 result = result +'         	<div class="col-md-9">'; 
				 result = result +'         		<input type="text" class="form-control" placeholder="State" id="jobState" name="state" value="'+state+'" readonly>';
				 result = result +'         	</div>';
				 result = result +'         </div>';
				 
				 
				 result = result +'         <div class="form-group clearfix">';
				 result = result +'         	<label class="col-md-3">City</label>';
				 result = result +'         	<div class="col-md-9">';
				 result = result +'         		<input type="text" class="form-control" placeholder="City" id="jobCity" name="city" value="'+city+'" readonly>';
				 result = result +'         	</div>';
				 result = result +'         </div>';
				 
				 result = result +'         <div class="form-group clearfix">';
				 result = result +'         	<label class="col-md-3">Zip Code</label>';
				 result = result +'         	<div class="col-md-9">';
				 result = result +'         		<input type="text" class="form-control" placeholder="Zip Code" id="jobZipCode" name="zipcode" value="'+zipCode+'">';
				 result = result +'         	</div>';
				 result = result +'         </div>';
				 
        		 
        		 result = result +'         <div class="form-group clearfix">';
        		 result = result +'         	<label class="col-md-3">Experience</label>';
        		 result = result +'         	<div class="col-md-9">';
        		 result = result +'         		<select class="form-control" id="experience" name="experience">';
        		 result = result +'         			<option selected  value="">---Select Experience---</option>';
        		 result = result +'         			<c:forEach begin="0" end="30" varStatus="loop">';
        		 
        		 if(experience ==  "${loop.index}"){
        			 result = result +' <option selected>${loop.index}</option>';
        		 }else{
        			 result = result +' <option>${loop.index}</option>';
        		 }
        		 
        		 result = result +'         			</c:forEach>';
        		 result = result +'         		</select>';
        		 result = result +'         	</div>';
        		 result = result +'         </div>';
        		 result = result +'         <div class="form-group clearfix">';
        		 result = result +'         	<label class="col-md-3">Salary/Rate</label>';
        		 result = result +'	<div class="col-md-9">';
        		 result = result +'            <div class="input-group er ratetype-validation">';
        		 result = result +'               <div class="input-group-btn tblminwidth-75">';
        		 result = result +'                <select class="btn-primary select_picker form-control" id="rateCurrency" name="rateCurrency">';
        		 
        		 if(rateCurrency == "INR"){
        			 result = result +'<option selected>INR</option>';
        		 }else{
        			 result = result +'<option>INR</option>';
        		 }
        		 
        		 if(rateCurrency == "AUD"){
        			 result = result +'<option selected>AUD</option>';
        		 }else{
        			 result = result +'<option>AUD</option>';
        		 }
        		 
        		 if(rateCurrency == "USD"){
        			 result = result +'<option selected>USD</option>';
        		 }else{
        			 result = result +'<option>USD</option>';
        		 }
        		 
        		 if(rateCurrency == "GBP"){
        			 result = result +'<option selected>GBP</option>';
        		 }else{
        			 result = result +'<option>GBP</option>';
        		 }
        		 
        		 if(rateCurrency == "SGD"){
        			 result = result +'<option selected>SGD</option>';
        		 }else{
        			 result = result +'<option>SGD</option>';
        		 }
        		 
        		 if(rateCurrency == "EUR"){
        			 result = result +'<option selected>EUR</option>';
        		 }else{
        			 result = result +'<option>EUR</option>';
        		 }
        		 
        		 result = result +'               </select>';
        		 result = result +'               </div>';
        		 
        		 result = result +'<input type="text" id="currentRate" name="rate" onblur="getRate();" field-name="rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true" value="'+rate+'" class="form-control" placeholder="Rate">';
        		 result = result +'<div class="input-group-btn tblminwidth-75 ratetype-validation">';
        		 result = result +'<select class="btn-primary select_picker form-control" id="rateType" data-validation-optional="true" name="rateType" field-name="Rate Type">';
        		 result = result +'<option value="">Rate Per</option>';
        		 
        		 if(rateType == "Hourly"){
        			 result = result +'<option selected>Hourly</option>'; 
        		 }else{
        			 result = result +'<option>Hourly</option>'; 
        		 }
        		 
        		 if(rateType == "Weekly"){
        			 result = result +'<option selected>Weekly</option>'; 
        		 }else{
        			 result = result +'<option>Weekly</option>'; 
        		 }
        		 
        		 if(rateType == "Bi-weekly"){
        			 result = result +'<option selected>Bi-weekly</option>'; 
        		 }else{
        			 result = result +'<option>Bi-weekly</option>'; 
        		 }
        		 
        		 if(rateType == "Semi-Monthly"){
        			 result = result +'<option selected>Semi-Monthly</option>'; 
        		 }else{
        			 result = result +'<option>Semi-Monthly</option>'; 
        		 }
        		 
        		 if(rateType == "Monthly"){
        			 result = result +'<option selected>Monthly</option>'; 
        		 }else{
        			 result = result +'<option>Monthly</option>'; 
        		 }
        		 
        		 if(rateType == "Bi-Monthly"){
        			 result = result +'<option selected>Bi-Monthly</option>'; 
        		 }else{
        			 result = result +'<option>Bi-Monthly</option>'; 
        		 }
        		 
        		 if(rateType == "Yearly"){
        			 result = result +'<option selected>Yearly</option>'; 
        		 }else{
        			 result = result +'<option>Yearly</option>'; 
        		 }
        		
        		 result = result +'                 </select>';
        		 result = result +'               </div>';
        		 result = result +'                <div class="clearfix"></div> <div class="clearfix"></div>';
        		 result = result +'             </div>';
        		 result = result +'           </div>';
        		 result = result +'         </div>';
        		 result = result +'         <div class="form-group clearfix">';
        		 result = result +'         	<label class="col-md-3">Industry</label>';
        		 result = result +'         	<div class="col-md-9">';
        		 result = result +'         		<select class="form-control chosen-select" data-placeholder="Industry" id="industry" name="industry">';
        		 result = result +'         			<option selected value="">---Select Industry---</option>';
        		 result = result +'         			<c:forEach var="industry" items="${industryList}">';
        		 
        		 if(json['industryName'] == "${industry}"){
        			 result = result +'<option value="${industry}" selected>${industry}</option>';
        		 }else{
        			 result = result +'<option value="${industry}">${industry}</option>';
        		 }
        		 
        		 result = result +'         			</c:forEach>';
        		 result = result +'         		</select>';
        		 result = result +'         	</div>';
        		 result = result +'         </div>';
        		
        		 result = result +'         <div class="form-group clearfix">';
        		 result = result +'         	<label class="col-md-3">Alert Frequency<span class="text-danger paddingLeft">*</span></label>';
        		 result = result +'         	<div class="col-md-9">';
        		 result = result +'         		<select class="form-control" id="duration" name="duration" field-name="Alert Frequency" data-validation="required">';
        		 result = result +'         			<option selected  value="">---Select Alert Frequency---</option>';
        		 
        		 if(json['duration'] == "Daily"){
        			 result = result +'<option selected>Daily</option>';
        		 }else{
        			 result = result +'<option>Daily</option>';
        		 }
        		 
        		 if(json['duration'] == "Weekly"){
        			 result = result +'<option selected>Weekly</option>';
        		 }else{
        			 result = result +'<option>Weekly</option>';
        		 }
        		 
        		 if(json['duration'] == "Monthly"){
        			 result = result +'<option selected>Monthly</option>';
        		 }else{
        			 result = result +'<option>Monthly</option>';
        		 }
        		
        		 result = result +'         		</select>';
        		 result = result +'         	</div>';
        		 result = result +'         </div>';
        		 result = result +'         <div class="form-group clearfix">';
        		 result = result +'         	<label class="col-md-3">Keywords / Skills <span class="text-danger paddingLeft">*</span></label>';
        		 result = result +'         	<div class="col-md-9">';
        		 result = result +'			<select class="form-control chosen-select" data-placeholder="Keywords/Skills"  multiple id="skills" name="skills" data-validation="required" field-name="Skills">';
        		 result = result +'         			<c:forEach var="skill" items="${skillsList}">';
        		 result = result +'         			<option value="${skill.name}">${skill.name}</option>';
        		 result = result +'         			</c:forEach>';
        		 result = result +'         		</select>';
        		 result = result +'         	</div>';
        		 result = result +'         </div>';
        		 result = result +'       </div>';
        		 result = result +'       <div class="modal-footer">';
        		 result = result +'         <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal" onclick="saveAlert();">Save</button>';
        		 result = result +'         <button type="button" class="btn btn-default btn-sm ml10" data-dismiss="modal" onClick="alertClose();">Cancel</button>';
        		 result = result +'       </div>';
        		 result = result +'     </div>';
        		 result = result +'   </div>';	
        		 result = result +'</form>';
        	 $("#createAlertPopUpDiv").empty();
        	 $("#createAlertPopUpDiv").append(result);
        	 initLocationAuto('jobLocation','jobCountry','jobState','jobCity','jobZipCode','','','');
        	 
        	 
        	 var config = {
        				  '.chosen-select'           : {},
        				  '.chosen-select-deselect'  : {allow_single_deselect:true},
        				  '.chosen-select-no-single' : {disable_search_threshold:10},
        				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        				  '.chosen-select-width'     : {width:"95%"}
        	 }
        	 for (var selector in config) {
        		    $(selector).chosen(config[selector]);
        		    }
        		    $('.chosen-select').each(function() {
        		    var $this = $(this);
        		    $this.next().css({'width': '100%'});
        		    })
        		    
        	        
        	        var select, chosen;
        		    select = $("#skills");
        		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
        		    chosen = select.data('chosen');
        		    
        		    $("#skills").chosen().change(function(event) {
        		    	var allSkills = $(event.target).val();
        		    	$.ajax({
        			    	type: "POST",
        			        url: sourceleadUrl+"/profileBySkillSet?${_csrf.parameterName}=${_csrf.token}&allSkills="+allSkills, 
        			        success: function(response) {
        			        	if (response.response === "AWKWARD") {
        			        	    location.href = "/errorPage";
        			        	} else if (response.response === "SUCCESS") {
        			        		var primarySkill = response.object;
            			        	$("#primarySkill").val(primarySkill);
        			        	} else if (response.indexOf('Sign In') !== -1) {
        			        		location.replace(sourceleadUrl);
        			        	} else {
        			        	    showError("Operation failed, please try again");
        			        	}
        			        },
        			    });
        		    });
        		    
        		    if(json['skillsName'] != 'undefined' && json['skillsName'] != null && json['skillsName'] != ''){
        		      var alertSkillsList = json['skillsName'].split(',');
                   	  if(alertSkillsList.length > 1){
                   		 for (var i=0; i < alertSkillsList.length; i++) {
  		        			 var option = $("<option>").val(alertSkillsList[i].replace(",","")).text(alertSkillsList[i].replace(",",""));
  		        			 select.append(option);
  		 		             select.find(option).prop('selected', true);
  		 		             select.find(option).siblings("[value='"+ alertSkillsList[i].replace(",","")+"']").remove();
  		 	        		 $("#skills").trigger("chosen:updated");
  		                 }
                   	  }else{
                   		 var option = $("<option>").val(alertSkillsList).text(alertSkillsList);
  	        			 select.append(option);
  	 		             select.find(option).prop('selected', true);
  	 		             select.find(option).siblings("[value='"+ alertSkillsList+"']").remove();
  	 	        		 $("#skills").trigger("chosen:updated");
                   	  }
        		    }
        		    
        	    
        	       chosen.search_field.on('keyup', function(e)
        			    {
        			    	   var skillsArray = this.value.split(",");
        				    	 if(skillsArray.length > 1){
        			        		 for (var i=0; i < skillsArray.length; i++) {
        			        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
        			        			 select.append(option);
        			 		             select.find(option).prop('selected', true);
        			 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
        			 	        		 $("#skills").trigger("chosen:updated");
        			                 }
        				    	 }else{
        			    			var newSkill = this.value.replace(","," ");
        			    			 if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
        			                {
        			    				 var option = $("<option>").val(newSkill).text(newSkill);
        			                     select.append(option);
        			                     select.find(option).prop('selected', true);
        			                     $("#skills option").each(function(){
        			                         $(this).siblings("[value='"+ this.value+"']").remove();
        			                   });
        			                   $("#skills").trigger("chosen:updated");
        			                }
        					       
        		    	         }
        			    });
          }
	 });
}
</script>  


<script>
function deleteJobAlert(jobAlertUUID,alertTitle){
	alertMessage(jobAlertUUID,alertTitle);
}
</script>

<script type="text/javascript">
function alertMessage(jobAlertUUID,alertTitle){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to delete alert <strong>"+alertTitle+"</strong> ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteAlert(\""+jobAlertUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script>
function deleteAlert(jobAlertUUID){
	  $.ajax({
        type : "POST",
        url: sourceleadUrl+"/deleteJobAlert?${_csrf.parameterName}=${_csrf.token}&jobAlertUUID="+jobAlertUUID,    
         success :function(data) {
        	 	$("#jobAlert"+jobAlertUUID).remove();
        	 	$("#choicepopUp").empty();
        		$(".project_inn_mask").fadeOut(200);
      	     }
	   });
}

</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function offboardingPagenation(){ 
	 //$.fn.dataTable.moment( 'DD-MM-YYYY' );
	 $('#offboardingPagination').DataTable( { 
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

<script>
function getRate(){
	var rate = document.getElementById("currentRate").value;
	if(rate != 'undefined' && rate !=  null && rate != ''){
		$("#rateType").removeAttr("data-validation-optional");
		$("#rateType").attr("data-validation", "required");
	}else{
		$("#rateType").removeAttr("data-validation");
		$("#rateType").attr("data-validation-optional", "true");
	}
}
</script>


<jsp:include page="../masterFooter.jsp" />