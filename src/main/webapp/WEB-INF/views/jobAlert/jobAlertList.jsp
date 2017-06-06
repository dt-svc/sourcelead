<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/formvalidator.js"></script>


<section class="content-wraper">
    <div id="offboardingLoading"></div>
    <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Job Alert List</h2>
          </div>
    </section>
    
    <div class="timesheet_out">
			<div class="project_wrap">
			<div class="timesheet_head">
			<ul>
			<sec:authorize access="hasRole('ROLE_CREATE_OFF_BOARD_TEMPLATE')"> 
			<li><a href="javascript:void(0)" class="alertBtn" onClick="alertFunction();createAlert();"><i class="fa fa-bell-o paddingRight"></i>Create Alert</a></li>
			</sec:authorize>
			</ul>
			     
            </div>
			
			    <div class="clients_wrap_table datatable_scroll">
				    <div id="jobAlertList"></div>
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
<script src="resources/js/jobs/js/bootstrap-select.min.js"></script>
<script>
function createAlert(){
	 result = "";
	 result = result +' <form id="create_alert_form" name="create_alert_form" method="post">';
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
	 result = result +'         		<input type="text" class="form-control input-sm" value="" placeholder="Alert Title" name="alertTitle" field-name="Alert Title" data-validation="required validate_length length2-100">';
	 result = result +'         	</div>';
	 result = result +'         </div>';
/* 	 result = result +'         <div class="form-group clearfix">';
	 result = result +'         	<label class="col-md-3">Email<span class="text-danger paddingLeft">*</span></label>';
	 result = result +'         	<div class="col-md-9">';
	 result = result +'         		<input type="text" class="form-control input-sm" value="" placeholder="Email" name="email" field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">';
	 result = result +'         	</div>';
	 result = result +'         </div>'; */
	 result = result +'         <div class="form-group clearfix">';
	 result = result +'         	<label class="col-md-3">Country</label>';
	 result = result +'         	<div class="col-md-9">';
	 result = result +'         		<select class="form-control chosen-select" data-placeholder="Country" id="country" name="country">';
	 result = result +'         		</select>';
	 result = result +'         	</div>';
	 result = result +'         </div>';
	 
	 result = result +'         <div class="form-group clearfix">';
	 result = result +'         	<label class="col-md-3">State</label>';
	 result = result +'         	<div class="col-md-9">';
	 result = result +'         		<select class="form-control chosen-select" data-placeholder="State" id="state" name="state">';
	 result = result +'         		</select>';
	 result = result +'         	</div>';
	 result = result +'         </div>';
	 
	 
	 result = result +'         <div class="form-group clearfix">';
	 result = result +'         	<label class="col-md-3">City</label>';
	 result = result +'         	<div class="col-md-9">';
	 result = result +'         		<select class="form-control chosen-select" data-placeholder="City" id="city" name="city">';
	 result = result +'         		</select>';
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
	 result = result +'            <div class="input-group er ratetype">';
	 result = result +'               <div class="input-group-btn tblminwidth-75">';
	 result = result +'                 <select class="btn-primary select_picker form-control" id="rateCurrency" name="rateCurrency">';
	 result = result +'                <option >INR</option>';
	 result = result +'                <option >AUD</option>';
	 result = result +'                <option >USD</option>';
	 result = result +'                <option >GBP</option>';
	 result = result +'                <option >SGD</option>';
	 result = result +'                <option >EUR</option>';
	 result = result +'               </select>';
	 result = result +'               </div>';
	 
	 result = result +'              <input type="text" id="currentRate" name="rate" value="" field-name="rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true" value="" class="form-control" placeholder="Rate">';
	 result = result +'               <div class="input-group-btn tblminwidth-75 ratetype">';
	 result = result +'                 <select class="btn-primary select_picker form-control" id="rateType" name="rateType" field-name="Rate Type">';
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
	 result = result +'         	<label class="col-md-3">Duration<span class="text-danger paddingLeft">*</span></label>';
	 result = result +'         	<div class="col-md-9">';
	 result = result +'         		<select class="form-control" id="duration" name="duration" field-name="Duration" data-validation="required">';
	 result = result +'         			<option selected  value="">---Select Duration---</option>';
	 result = result +'         			<option>Daily</option>';
	 result = result +'         			<option>Weekly</option>';
	 result = result +'         			<option>Monthly</option>';
	 result = result +'         		</select>';
	 result = result +'         	</div>';
	 result = result +'         </div>';
	 result = result +'         <div class="form-group clearfix">';
	 result = result +'         	<label class="col-md-3">Keywords/Skills</label>';
	 result = result +'         	<div class="col-md-9">';
	 result = result +'			<select class="form-control chosen-select" data-placeholder="Keywords/Skills" multiple id="skills" name="skills">';
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
 getAllCountries();
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
	    
	    $("#country").chosen();
        $("#state").chosen();
        $("#city").chosen();
        
        $("#country").on('change', function (e) {
        	alert("hello");
     	   getStates();
        });
       
        $("#state").on('change', function (e) {
     	   getCities();
        });
        
        
        
        
        
        var select, chosen;
	    select = $("#skills");
	    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
	    chosen = select.data('chosen');
	    
	    $("#skills").chosen().change(function(event) {
	    	 var sourceleadUrl ='${sourceleadUrl}';
	    	var allSkills = $(event.target).val();
	    	$.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/profileBySkillSet?${_csrf.parameterName}=${_csrf.token}&allSkills="+allSkills, 
		        success: function(data) {
		        	var primarySkill = data;
		        	      $("#primarySkill").val(primarySkill);
		        	
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
}
</script>

<script>
function saveAlert(){
	if ($('#create_alert_form').validate(false, validationSettings)){ 
		var sourceleadUrl = '${sourceleadUrl}';
		var frm = $('#create_alert_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, ""); 
		//It will escape all the special characters
	    var jsonData = encodeURIComponent(con);

		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/storeJobAlert?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
					success : function(result) {
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

<script type="text/javascript">
function getAllCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---select country---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${SourceLeadWeb_loginCountryName}'){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#country').trigger("chosen:updated");
            getStates();
        },
        error: function(e){
        }
    });
} 
</script>


<script>
function getStates(){
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---Select State---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').append('<option value="">---Select City---</option>');
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---Select State---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---Select City---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---Select City---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---Select City---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>

<script>
function jobAlertList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
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
	                    		 experience = report.experience + "&nbsp; Yeras";
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
	                    	 
	                    	 result = result +"<tr>";
	                    		 result = result +"<td><div class='tbldata-dotdot-sm' title="+report.alertTitle+"><span >"+report.alertTitle+"</span></div></td>";
	                    		 result = result +"<td><div><span>"+experience+"</span></div></td>";
	                    		 result = result +"<td><div  title="+rate+"><span>"+rate+"</span></div></td>";
	                    		 result = result +"<td><div  title='"+industryName+"'><span>"+industryName+"</span></div></td>";
	                    		 result = result +"<td><div  title='"+skillsName+"'><span>"+skillsName+"</span></div></td>";
			                     result = result +"<td><div class=''>";
			                     result = result +'<a class="glyphicon glyphicon-edit" title="Edit" onclick="editDepartment();"></a>';	
			                     result = result +'<a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteProject();"></a>';	
			                     result = result +"</div></td>"; 
		                     result = result +"</tr>";
	                    }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#jobAlertList").append(result); 
	                     
	                     offboardingPagenation(); 
                    
          },
            
       }); 
  } 
</script>   

<script>
function offboardingPagenation(){ 
	 $.fn.dataTable.moment( 'DD-MM-YYYY' );
	 $('#offboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
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


<jsp:include page="../masterFooter.jsp" />