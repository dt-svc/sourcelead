<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>.::Guest Apply to Job::.</title>
<!-- <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet"> -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jquery-ui.css" rel="stylesheet">
<script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.timepicker.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<script src="/resources/js/InterViewFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script>
<script src="/resources/js/interViewForm-html-generator.js?staticTime=${staticTime}" type="text/javWascript" ></script> 
<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=AIzaSyCIbxD8-aZ7Wgp6N-JeEJKVRIHGRpNMdRE" type="text/javascript"></script>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="/resources/js/submissionFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/guestUserSubmissionForm-html-generator.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form generator -->	
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script>

<style>
.viewTemplate {
background-color: #fff !important;
}
.new_side_wrap_inn.applytoJobDiv {
margin: 20px auto; padding-bottom: 15px;
}
.applytoJobDiv .form-footer {
	display: inline-block; width: 100%; padding: 20px 0; min-height: 30px; line-height: 30px;
}
.pac-container {
z-index: 1000000000 !important;
}
.pac-container::after {
	display:none;
}
</style>
</head>
<body>
<div class="container new_side_wrap_inn applytoJobDiv">
	<div class="row">
		<div class="col-md-12">
			<!-- IncludeJSP -->
			<div id="applytoJobDiv"></div>
			<!-- IncludeJSP -->
		</div>
	</div>
</div>

<script>
$(document).ready(function() {
	var requirementId = '${reqId}';
	var jobTitle = '${reqName}'
	var sourceleadUrl = '${sourceleadUrl}';
	if(sourceleadUrl.indexOf("/app")!=-1){
		sourceleadUrl = sourceleadUrl.split("/app")[0];
	}
	getRequirementSubmissionTemaplate(sourceleadUrl,jobTitle,requirementId)
	});
</script>
<script>
function getRequirementSubmissionTemaplate(sourceleadUrl,jobTitle,requirementId){
	  var finalDateFormatScript = 'dd-mm-yy'; 
	 generateOrgSubmissionForm(sourceleadUrl,requirementId,finalDateFormatScript); 
	 var loginUserRole = '${SourceLeadWeb_loginUserRole}';
		var result = "";
		result = result + '<form id="guestApplicant_form" name="guestApplicant_form" enctype="multipart/form-data" method="post">';
		result = result + '<h3>Application Form</h3>';
		result = result + '<div class="form-horizontal clearfix select-field applicant-label">';
		result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
		
		result = result + '<div class="row">';
		result = result + '<div class="venk clearfix">';
	 
		result = result + '<div class="clearfix"></div>';
		result = result + '<div class="panel-border_a col-md-5 mt10" id="resumesDiv"  style="display: none;">';
		result = result + '<select id="candidateResumesList" name="candidateResume" field-name="Resume" class="form-control block">';
		result = result + '</select>';
		result = result + '</div>';
		result = result + '<div id="orConditionDiv" class="text-center text-info col-md-2" style="display: none;">';
		result = result + '------- ( OR ) -------';
		result = result + '</div>';
		
	/* 	result = result + '<div class="col-md-5 pull-right">';
		result = result + '<div class="col-md-12" id="browseDiv"  style="display: block;">';
		result = result + '<div class="input-group" id="uploadFileGroup">';
		result = result + '<label class="input-group-btn" onchange="importGuestAddApplicantDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
		result = result + '<input type="file" style="display: none;" name = "uploadBtn" id="uploadBtn"  class="upload">';
		result = result + '</span> </label>';
		result = result + '<input type="text" class="form-control" data-validation="validatedoc" data-validation-optional="true" field-name="Resume" readonly id="uploadFile" placeholder="Upload Resume">';
		result = result + '</div>';
		result = result + '</div>';	
		result = result + '</div>'; */
		
		result = result + '</div>';
		
		
		result = result + '<div id="sjfb-fields" class="viewTemplate"> <div id="loading"></div> <div id="emailDiv"></div>';
		
		result = result + '</div>';
		
	                        
		result = result + '</div>';
		
		
		result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="skillsDiv" >';
		result = result + '<label class="col-md-4">Skills</label>';
		result = result + '<div class="col-md-8">';
		result = result + '<select multiple data-placeholder="select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills"  data-validation-optional="true">';
		result = result + '<c:forEach var="skill" items="${skillsList}">';
		result = result + '<option value="${skill.name}">${skill.name}</option>';
		result = result + ' </c:forEach>';
		result = result + '</select>';
		result = result + '		 </div>';
		result = result + '	 </div>';
		result = result + '	 </div>';
		
	    result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="primarySkillDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Primary Skill</label>';
		result = result + '<div class="col-md-8">';
		result = result + '<input type="text" class="form-control" id="primarySkill" name="primarySkill"  field-name="Primary Skill">';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>'; 
		
	    result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="employmentTypeDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Employment Type<span class="paddingLeft">*</span></label>';
		result = result + '<div class="col-md-8">';
		result = result + '<select class="form-control" id="designationType" name="designationType" onchange="getEmployementType();" field-name="Employment Type">';
		result = result + '<option value="">--Select--</option>';
		result = result + '</select>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>'; 
		
		
	    result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="employmentOtherTypeDiv" style="display: none;">';
	    result = result + '<label class="col-md-4"></label>';
	    result = result + '<div class="col-md-8">';
		result = result + '<input type="text" class="form-control" placeholder="Employment Other Type" id="designationOtherType" name="designationOtherType" field-name="Employment Other Type" >';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>'; 
		
		
	    result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="vendorsDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Vendors</label>';
		result = result + '<div class="col-md-8">';
		result = result + '<select class="chosen-select form-control" id="vendors" name="vendors" onchange="getAddApplicantVendorContactList();" field-name="Vendors">';
		result = result + '</select>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>';
		
		
		result = result + '<div class="row">';
	    result = result + '	<div class="col-md-6 upresume mb" id="vendorContactsDiv" style="display: none;">';
		result = result + '<label class="col-md-4">Vendor Contacts</label>';
		result = result + '<div class="col-md-8">';
		result = result + '<select class="chosen-select form-control" id="vendorContact" name="vendorContact"  field-name="vendor Contacts">';
		result = result + '<option value="">--Select Vendor Contacts--</option>';
		result = result + '</select>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>'; 
		
		result = result + '</div>';
		
		
		result = result + '<div class="row">';
		result = result + '	<div class="col-md-6 upresume mb" id="newResumeDiv">';
		result = result + '<label class="col-md-4">Import Resume &nbsp;*</label>';
		result = result + '		<div class="col-md-8">';
		result = result + '			    <div class="input-group col-md-12 attachfile-error" id="uploadFileGroup1" style="margin:0 auto">';
		result = result + '			        <label class="input-group-btn" onchange="uploadAddApplicantCandidateDoc();"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
		result = result + '                   <input type="file" style="display: none;"  id="newUploadBtns"  class="upload">';
		result = result + '			          </span> </label>';
		result = result + '			          <input type="text" class="form-control" readonly id="newUploadFiles" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation="required validatedoc "  >';
		result = result + '			    </div>';
		result = result + '		 </div>';
		result = result + '	 </div>';
		
		
		result = result + '<div class="form-footer clearfix mt">';
		result = result + '<div class="pull-right mr">';
		result = result + '<button type="button" id="save_button"  class="btn btn-sm btn-primary " onclick="addApplicantSubmission();">SUBMIT</button>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>';  
		result = result + '</form>';
		
	
	
	$("#submitDetailsDiv").empty();
	$("#applytoJobDiv").empty();
	$("#applytoJobDiv").append(result);
	test();
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
	    select = $("#candidateSkills");
	    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
	    chosen = select.data('chosen');
	    
	    
	    $("#candidateSkills").chosen().change(function(event) {
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
			 	        		 $("#candidateSkills").trigger("chosen:updated");
			                 }
				    	 }else{
			    			var newSkill = this.value.replace(","," ");
			    			 if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			                {
			    				 var option = $("<option>").val(newSkill).text(newSkill);
			                     select.append(option);
			                     select.find(option).prop('selected', true);
			                     $("#candidateSkills option").each(function(){
			                         $(this).siblings("[value='"+ this.value+"']").remove();
			                   });
			                   $("#candidateSkills").trigger("chosen:updated");
			                }
					       
		    	         }
	    	}); 
	    //Adding Customized Skills to the list
			
	$("#candidateSkills").trigger("chosen:updated");
	
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
/* 	$("body").css('overflow-y', 'hidden'); */
	
	var selectedFile = document.getElementById("uploadBtn");
	selectedFile.addEventListener("change", function() {
		 $("#uploadFile").focus();
		 if(validate_fileupload(this.value)){
			 var docSize = this.files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
			  $("input#uploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  document.getElementById("uploadFile").setAttribute('record-exist','yes');
			  document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
			 $('#uploadFileGroup').find('p.jquery_form_error_message').remove(); 
		    }
		 }
	});

	document.getElementById("uploadBtn").onchange = function () {
		$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	};
	
	document.getElementById("newUploadBtn123").onchange = function () {
		$('#newUploadFile123').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	};
	
}
</script>

<script>
/* function importGuestAddApplicantDoc(){
	fileUpload("uploadBtn", "addApplicants", "guestApplicant_form");
} */
function importGuestAddApplicantDoc(){
	fileUpload("uploadBtn", "addApplicants", "guestApplicant_form");
	var uploadfiles = $("#uploadBtn").val();
	$('#uploadFile').val(uploadfiles.replace(/C:\\fakepath\\/i, ''));
}
</script>
<script type="text/javascript">
var sourceleadUrl ='${sourceleadUrl}';
</script>
<script>
function uploadAddApplicantCandidateDoc(){
	fileUpload("newUploadBtns", "addApplicants", "guestApplicant_form",'', '${orgId}', '${locationId}');
	var uploadfiles = $("#newUploadBtns").val();
	$('#newUploadFiles').val(uploadfiles.replace(/C:\\fakepath\\/i, ''));
}
</script>
<script>
 $(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
	 if(validate_fileupload(this.value)){
	 var docFileSize = $('#uploadFile');
 		 var docSize = this.files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  
	    	  $("input[name=FirstName]").val('');
	        	 $("input[name=LastName]").val('');
	        	 $("input[name=EmailId]").val('');
	        	 $("input[name=PhoneNumber]").val('');
	        	 $("#candidateSkills").empty();
	        	 
	        	 $("#candidateSkills").append('');
	        	 $("#candidateSkills").trigger("chosen:updated");
	        	 $("#primarySkill").val('');
	    	  
	    	  document.getElementById("uploadFile").value = '';
	    	  $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
			  $("input#uploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  document.getElementById("uploadFile").setAttribute('record-exist','yes');
			  document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
			  
			 	
			  
	      }else{
	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
   		     $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
 	 
 		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#guestApplicant_form')[0];
  		var formData = new FormData(form); 
		   $.ajax({
				    type: "POST",
			    	data: formData,
		   	        contentType : 'application/json; charset=utf-8',
		   	        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
		   	        dataType : 'json',
					url : "/parseResume?"+tokenParamName+"="+tokenValue+"",
					success : function(data) {
						var json = JSON.parse(data);
						var name = json.name;
						var firstName = '';
						var lastName = '';
						if(name != null && name != 'undefined' && name != ''){
							 firstName = name.split(".")[0]; 
							 lastName = name.split(".")[1]; 
							 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
									 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
								 firstName = name;
							 }
						}
						
						
						 $("input[name=FirstName]").val(firstName);
			        	 $("input[name=LastName]").val(lastName);
			        	 $("input[name=EmailId]").val(json.email);
			        	 $("input[name=PhoneNumber]").val(json.phoneNumbers);
			        	 
			        	 // Here parseresume skills and parseresume skills,lookup skills duplicate check
			        	 $("#candidateSkills").empty();
			        	 var internalOutput = "";
				        	 var skills = json.skills;
	 						 <c:forEach var="skill" items="${skillsList}">
	 						var existed = 0;
	 						if(skills != null && skills != 'undefined' && skills != ''){
	 							var str_array = skills.split(',');
	 							for(var i = 0; i < str_array.length; i++) {
	 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
	 							  if(str_array[i] == "${skill.name}"){
	 								 existed = 1;
	 								internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
	 							  }
	 							}
	 						}
	 						if(existed == 0){
	 							internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
			                }
	 	                     </c:forEach>
 						
 						$("#candidateSkills").append(internalOutput);
 		        		$("#candidateSkills").trigger("chosen:updated");
  						
			        	 
						 validateSubmissionCandidateEmail();
						 getUserByUserEmail();
						 $("#loading").removeAttr("style");
					},
				});
 	 }
	}
 });

</script> 

<script>
function test(){
	$(".DatePicker").datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
	}
	})
}
</script>

  <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>
 

<script type="text/javascript">
function addApplicantSubmission(){
	var requirementId = '${reqId}';
	if ($('#guestApplicant_form').validate(false, validationSettings)) {
    var frm = $('#guestApplicant_form').serializeFormJSON();
    var con1 = JSON.stringify(frm);
    con1= con1.replace(/[{}]/g, "");
    var jsonData = encodeURIComponent(con1);
    var form = $('#guestApplicant_form')[0];
    var formData = new FormData(form); 
    //Loop through fields and save field data to array
    var fields = [];
    $('.sjfb-field').each(function() { 
    	  var $this = $(this); 
    	  var divId = $(this).attr('id');  
    	  var fieldLabel = $this.find('label:first').text().replace("*","");
    	  var inputValue = $this.find('input[type="radio"]:checked').val();
    	  
    	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
  		   inputValue = $this.find('input[type="checkbox"]:checked').val();
  		   if(inputValue != null){
  			   var selected = [];
             	$this.find('input[type="checkbox"]:checked').each(function() {
    		        selected.push($(this).val()); 
    		       });
             	inputValue = selected.toString()
  		   }
  		   
    	 }
    	 
    	
    	  if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
    		  inputValue = $this.find('input').val();
    	  }
    	  
    	   
  	  
   	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
   	    	 inputValue = $this.find('select').val();
   	      }
   	      
   	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
   	    	 inputValue = $this.find('textarea').val();
   	      }
   	      
   	    
   	     
   	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
   	    	inputValue = '';
   	      }
   	      
   	      
   	      
	       	  var name = $this.find('input[type="radio"]:checked').attr('name');
	       	  var id = $this.find('input[type="radio"]:checked').attr('id');
	     	  if(name == null || name == '' || name == 'undefined'){
	     		  name = $this.find('input').attr('name');
	     		  id = $this.find('input').attr('id');
	     	  }
	     	  if(name == null || name == '' || name == 'undefined'){
	     		 name = $this.find('select').attr('name');
	     		 id = $this.find('select').attr('id');
   	      }
	     	  
	     	  if(name == "WorkExperience"){
	     		 var monthExp = document.getElementById("monthExp").value; 
	     		  if(null != inputValue){
	     			  if(inputValue == "50"){
	     				 inputValue = inputValue+" Years";
	     			  }else{
	     				 inputValue = inputValue+" Years"+" "+monthExp+" Months" ;
	     			  }
	     			 
	     		  }
	     		  
	     		  if(null == inputValue && monthExp != '0'){
	     			 inputValue = monthExp+" Months" ;
	     		  }
	     	  }
	     	  
	     	  
	     	 if(name == "CurrentCTC"){
	     		 inputValue = $this.find('input').val();
	     		 if(inputValue){
	     			 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value; 
	     			 var currentCTCRateType = document.getElementById("currentCTCRateType").value; 
	     			 inputValue = currentCTCRateType+" "+inputValue+" /"+" "+currentCTCRatePer ;
	     		  }
	     	  }
	     	 
	     	if(name == "ExpectedCTC"){
	     		 inputValue = $this.find('input').val();
	     		  if(inputValue){
	     			 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value; 
	     			 var expectedCTCRateType = document.getElementById("expectedCTCRateType").value; 
	     			 inputValue = expectedCTCRateType+" "+inputValue+" /"+" "+expectedCTCRatePer ;
	     		  }
	     	  }
	     	  
	     	  
    	  fields.push({
              label: fieldLabel,
              id :id,
              name :name,
              value: inputValue,
          })
     });

    var con = JSON.stringify(fields);
    
    con = encodeURIComponent(con);
   
    
     $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    
	     $.ajax({
	    	type: "POST",
	    	data: formData,
	        contentType : 'application/json; charset=utf-8',
	        enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
	        dataType : 'json',
	    	url : "/saveGuestAddApplicant?&jsonData="+jsonData+"&con="+con,
	        success: function(result) {
	        	window.location.href = "/jobs";
	        	 window.location = "http://localhost:8080/jobs"
	        },
	    }); 
    return true; 
   }
  else{
  	return false;	
     } 
}
$('body').on('blur', '#guestApplicant_form', function() {
	$("#guestApplicant_form").showHelpOnFocus();
	$("#guestApplicant_form").validateOnBlur(false, validationSettings);
});
  
</script>

<script type="text/javascript">
function init(){
	initLocationAuto('submissionLocation','submissionCountry','submissionState','submissionCity','submissionZipCode','','','');
}
</script>
<script>
function initLocationAuto(locationId,countryId,stateId,cityId,zipCodeId,latitudeId,longtitudeId,type,addressId) {
    initialize(locationId,countryId,stateId,cityId,zipCodeId,type,addressId);
    geolocate(latitudeId,longtitudeId);
}
function initialize(locationId,countryId,stateId,cityId,zipCodeId,type,addressId) {
    if(type == "invite"){
    	 var input = (document.getElementById(locationId));
    	    var options = {
    	    		types: ['geocode'],
    	        componentRestrictions: {
    	        	country: ["IN","US","AU","SG","GB","FR"]
    	        }
    	    };
    	    autocomplete = new google.maps.places.Autocomplete(input, options);
    }else{
    	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locationId)), {
    		types: ['geocode'],
      
        });
    }
   
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
       case "route":
    	 address.name=place.address_components[i].long_name;
    	 break;
      case "locality":
        address.city = place.address_components[i].long_name;
        break;
      case "administrative_area_level_1":
        address.state = place.address_components[i].long_name;
        break;
      case "country":
        address.country = place.address_components[i].long_name;
        break;
      case "postal_code":
        address.zipcode = place.address_components[i].long_name;
        
    }
  }
 
  $("#"+stateId).val(address.state).change();
  $("#"+cityId).val(address.city).change();
  $("#"+zipCodeId).val(address.zipcode).change();
  $("#"+addressId).val($("#"+locationId).val().replace(", "+$("#"+cityId).val()+", "+$("#"+stateId).val()+", "+$("#"+countryId).val(), ""));
  $("#"+countryId).val(address.country).change();
  $("#"+countryId).trigger("chosen:updated");

  if(type == "invite"){
	  getTimezonesList();
  }
  zipcodeMask(address.country,zipCodeId);	
  
});
}
function geolocate(latitudeId,longtitudeId) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            if(latitudeId != null && latitudeId != '' && latitudeId !='undefined'){
                document.getElementById("latitude").value = latitude;
            }
            if(longtitudeId != null && longtitudeId != '' && longtitudeId != 'undefined'){
                document.getElementById("longitude").value = longitude;
            }
            autocomplete.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));
        });
    }
}
</script>
<script>
function refreshGoogleApiLocation(locationId,countryId,stateId,cityId,zipCodeId,addressId){
	$("#"+locationId).val('');
	$("#"+stateId).val('');
	$("#"+cityId).val('');
	$("#"+zipCodeId).val('');
	$("#"+addressId).val('');
	$("#"+countryId).val('');
	$("#"+countryId).trigger("chosen:updated");
}
</script>
<script>
function zipcodeMask(country,zipCodeId){
	if(country == "India" || country == "Singapore"){
		 $("#"+zipCodeId).mask("999999");
	}else if(country == "United States" || country == "France"){
		$("#"+zipCodeId).mask("99999");
	}else if(country == "Australia"){
		$("#"+zipCodeId).mask("9999");
	}else if(country == "United Kingdom"){
		$("#"+zipCodeId).mask("aa99");
	}else{
		$("#"+zipCodeId).mask("999999");
	}
		     
}
</script>
 <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
  $("#DOB").datepicker();
  </script>
  
 <script type="text/javascript">
 var staticTime='${staticTime}';
 var config = {
			  '.chosen-select'           : {},
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
</script>

  
<script type="text/javascript" src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>
