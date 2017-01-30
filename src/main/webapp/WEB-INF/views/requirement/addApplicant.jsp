<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script> 

<script src="resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<style type="text/css">
.mt10 {
    margin-top: 10px !important;
}
</style>



<!-- For add New Applicant -->
<script>
function getAddApplicant(requirementId){
	
	var sourceleadUrl ='${sourceleadUrl}';
	generateOrgSubmissionForm(sourceleadUrl,requirementId)
	var result = "";
	result = result + '<form id="addApplicant_form" name="addApplicant_form" enctype="multipart/form-data" method="post">';
	result = result + '<h3>Add Applicant</h3>';
	result = result + '<div class="form-horizontal clearfix select-field">';
	result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
	
	result = result + '<div class="row">';
	result = result + '<div class="venk clearfix">';
	result = result + '<div class="col-md-7 radioInline">';
	result = result + '<label class="mr"><input type="radio" checked="checked" value="Candidates" name="candidateType" onClick="getCandidatesList('+requirementId+',\'Candidate\');">Candidates</label>';
	result = result + '<label class="mr"><input type="radio" value="Consultants" name="candidateType" onClick="getCandidatesList('+requirementId+',\'Consultant\');">Consultants</label>';
	result = result + '<label class="mr"><input type="radio" value="New candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'New\');">Add New Candidate</label>';
	result = result + '<label class="mr"><input type="radio" value="Parese candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'parseCandidate\');">Parse Candidate</label>';
	result = result + '</div>';	
	
	result = result + '<div class="col-md-5 pull-right">';
	result = result + '<div class="col-md-12" id="browseDiv"  style="display: none;">';
	result = result + '<div class="input-group" id="uploadFileGroup">';
	result = result + '<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '<input type="file" style="display: none;" name="locationMapFile" multiple id="uploadBtn"  class="upload">';
	result = result + '</span> </label>';
	result = result + '<input type="text" class="form-control" data-validation="validatedoc" data-validation-optional="true" field-name="Resume" readonly id="uploadFile" placeholder="Upload Resume">';
	result = result + '</div>';
	result = result + '</div>';	
	result = result + '</div>';
	
	result = result + '<div class="clearfix"></div>';
	result = result + '<div class="col-md-6 mt10">';
	result = result + '<div id="selectCandidateDiv" style="display: block;">';
	result = result + '<select class="form-control chosen-select" onchange="getCandidateDetails(); getCandidateResumes();"  name="candidateId" id="candidateId" data-validation="required" field-name="Applicant" class="form-control  block mt10">';
	result = result + '<option value="">--Select Candidate--</option>';
	result = result + '</select>';
	result = result + '</div>';	
	result = result + '</div>';	
	
	result = result + '<div class="panel-border_a col-md-5 mt10" id="resumesDiv"  style="display: none;">';
	result = result + '<select id="candidateResumesList" name="candidateResume" field-name="Resume" class="form-control block">';
	result = result + '</select>';
	result = result + '</div>';
	
	result = result + '<div id="orConditionDiv" class="text-center text-info col-md-2" style="display: none;">';
	result = result + '------- ( OR ) -------';
	result = result + '</div>';
	
	
	
	
	result = result + '</div>';
	
	result = result + '<div id="sjfb-fields" class="viewTemplate"> <div id="loading"></div> <div id="emailDiv"></div>';
	
	result = result + '</div>';
	
                        
	result = result + '</div>';
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="skillsDiv" style="display: none;">';
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
	result = result + '	<div class="col-md-6 upresume mb" id="newResumeDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Add Resume &nbsp;*</label>';
	result = result + '		<div class="col-md-8">';
	result = result + '			    <div class="input-group col-md-12 attachfile-error" id="uploadFileGroup1" style="margin:0 auto">';
	result = result + '			        <label class="input-group-btn"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '                   <input type="file" style="display: none;" name="newUploadBtn" id="newUploadBtn"  class="upload">';
	result = result + '			          </span> </label>';
	result = result + '			          <input type="text" class="form-control" readonly id="newUploadFile" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation="required validatedoc "  >';
	result = result + '			    </div>';
	result = result + '		 </div>';
	result = result + '	 </div>';
	
	
	result = result + '	<div class="col-md-6 upresume mb">';
	result = result + '<label class="col-md-4">&nbsp;</label>';
	result = result + '<input type="checkbox" name="needApplicantPermit" id="needApplicantPermit" onClick="ApplicantPermit();" >&nbsp;&nbsp;<span><strong>Need Applicant Permit</strong></span> ';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="form-footer clearfix mt">';
	result = result + '<div class="pull-right mr">';
	result = result + '<button type="submit" id="save_button"  class="btn btn-sm btn-primary ">SUBMIT</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';  
	result = result + '</form>';
	
	
	 
	getCandidatesList(requirementId,'Candidate');
	
	
	$("#addApplicantDiv").empty();
	$("#addApplicantDiv").append(result);
	
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
			
			
	$("#candSkills").val('').trigger("chosen:updated");
	
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
	var selectedFile = document.getElementById("newUploadBtn");
    selectedFile.addEventListener("change", function() {
    	 $("#newUploadFile").focus();
    	 if(validate_fileupload(this.value)){
    		 var docSize = this.files[0].size;
   	      $("#uploadFileGroup1").find('p.jquery_form_error_message').remove();
   	      if(docSize > 2097152){
   	    	  document.getElementById("newUploadFile").value = '';
   	    	  $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
   			  $("input#newUploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
   			  document.getElementById("newUploadFile").setAttribute('record-exist','yes');
   			  document.getElementById("newUploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
   	      }else{
   	    	 $('input#newUploadFile').removeAttr( "record-exist record-exist-errormsg");
      		 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove(); 
    	    }
    	 }
    });
	
	document.getElementById("newUploadBtn").onchange = function () {
		$('#newUploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	};
	
}
</script>




<script type="text/javascript">
function ApplicantPermit(){
	 if($("#needApplicantPermit").is(':checked')){
		  $("#needApplicantPermit").val("Y");
	 }else{
		 $("#needApplicantPermit").val("N");
	 }
}
</script>


<script type="text/javascript">
 function getCandidatesList(requirementId,candidateType) {
	 $('#sjfb-fields').find('p.jquery_form_error_message').remove();
	 $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
	 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
	 
	 $('#candidateId').removeAttr('data-validation-optional');
	 $("#candidateId").attr("data-validation", "required");
	 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType != 'New' && candidateType != 'parseCandidate'){
		 $("#selectCandidateDiv").css("display","block");
		 if(requirementId != null && requirementId != "" && requirementId != 'undefined'){
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/getCandidatesList?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&candidateType="+candidateType, 
			        success: function(data) {
			        	var json = JSON.parse(data);
			        	var output = "";
			        	output = output + "<option value=''>-- Select "+candidateType+" --</option>"; 
			        	for (var i=0; i<json.length; i++)
						{
				        		var opt = json[i];
				        		var lastName = "";
				        		if(opt.lastName != null && opt.lastName != "" && opt.lastName != 'undefined'){
				        			lastName = opt.lastName;
				        		}
				        		output = output + "<option value='" +opt.candidateId+ "'>"+opt.firstName+" "+lastName+"</option>";
						}
			        	$("#candidateId").empty();
				        $("#candidateId").append(output);
				        $('#candidateId').trigger("chosen:updated");
				        
				        getCandidateResumes();
			        },
			    }); 
		 }else{
			 var output = "";
	     	 output = output + "<option value=''>-- Select Candidate --</option>"; 
	     	 $("#candidateId").empty();
		     $("#candidateId").append(output);
		     $('#candidateId').trigger("chosen:updated");
		 }
	 }else{
		 var output = "";
     	 $("#candidateId").empty();
	     $("#candidateId").append(output);
	     $('#candidateId').trigger("chosen:updated");
	     $("#selectCandidateDiv").css("display","none");
	     
	     $("#candidateId").removeAttr("data-validation");
	     $('#candidateId').attr("data-validation-optional","true"); 
	     
	     $("#candidateResumesList").removeAttr("data-validation");
	     $('#candidateResumesList').attr("data-validation-optional","true"); 
	     
	     if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'parseCandidate'){
	    	 document.getElementById("browseDiv").style.display="";
	    	 $('#uploadFile').removeAttr('data-validation-optional');
			 $('#uploadFile').attr("data-validation","required validatedoc "); 
			 
			 document.getElementById("newResumeDiv").style.display="none";
			 document.getElementById("skillsDiv").style.display="";
			 $('#newUploadFile').removeAttr('data-validation');
			 $('#newUploadFile').attr("data-validation-optional","true"); 
	     }else if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'New'){
	    	 document.getElementById("browseDiv").style.display="none";
	    	 $('#uploadFile').removeAttr('data-validation');
			 $('#uploadFile').attr("data-validation-optional","true"); 
	    	 
	    	 document.getElementById("newResumeDiv").style.display="";
	    	 document.getElementById("skillsDiv").style.display="";
			 $('#newUploadFile').removeAttr('data-validation-optional');
			 $("#newUploadFile").attr("data-validation", "required validatedoc");
			 
			
	     }
	     document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		
	 }
	 
	 $("input[name=FirstName]").val('');
	 $("input[name=LastName]").val('');
	 $("input[name=EmailId]").val('');
	 $("input[name=EmailId]").removeAttr("readonly");
	 
	 
	 $("input[name=PhoneNumber]").val('');
	 $("input[name=Designation]").val('');
	 $("select[name=state]").val('');
	 $("select[name=city]").val('');
	 $("select[name=NoticePeriod]").val('');
	 $("select[name=WorkExperience]").val(0);
	 $("input[name=ExpectedCTC]").val('');
	 $("input[name=CurrentCTC]").val('');
	 $("input[name=Education]").val('');
	 
	 $("#newUploadFile").val('');
	 $("#uploadFile").val('');
	
 }
</script>

<!-- For add New Applicant -->
<script>
    $(".addApplicant_close, .side_mask").click(function() {
    	$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<!-- For add New Applicant -->
<script type="text/javascript">
$('body').on('submit', '#addApplicant_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
      var frm = $('#addApplicant_form').serializeFormJSON();
      var sourceleadUrl ='${sourceleadUrl}';
      var requirementId = $("#requirementId").val();
      var frm = $('#addApplicant_form').serializeFormJSON();
      var con1 = JSON.stringify(frm);
      con1= con1.replace(/[{}]/g, "");
      var jsonData = encodeURIComponent(con1);
      var form = $('#addApplicant_form')[0];
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
	     			  if(inputValue == "25+"){
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
	     		  if(null != inputValue){
	     			 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value; 
	     			 var currentCTCRateType = document.getElementById("currentCTCRateType").value; 
	     			 inputValue = currentCTCRateType+" "+inputValue+" /"+" "+currentCTCRatePer ;
	     		  }
	     	  }
	     	 
	     	if(name == "ExpectedCTC"){
	     		  if(null != inputValue){
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
            });
       });
      var con = JSON.stringify(fields);
      
      con = encodeURIComponent(con);
     
      
		
       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
      
  	     $.ajax({
	    	type: "POST",
	    	data: formData,
 	        contentType : 'application/json; charset=utf-8',
 	        enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
 	        dataType : 'json',
 	    	url : sourceleadUrl+"/saveAddApplicant?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData+"&con="+con,
	        success: function(result) {
	        	 $(".new_side_wrap").removeClass("new_side_wrap_active");
	    		$(".side_mask").fadeOut(200);
	    		$("body").css('overflow-y', 'auto');
	    		var allApplicants = 'all';
	    		requirementPipeline(requirementId,allApplicants); 
	        	
	        },
	    }); 
      return true; 
    }
    else{
    	return false;	
       }
  });

    
   	$('body').on('blur', '#addApplicant_form', function() {
		$("#addApplicant_form").showHelpOnFocus();
		$("#addApplicant_form").validateOnBlur(false, validationSettings);
	});
  
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
 function getCandidateResumes() {
	 $("#save_button").removeAttr("disabled");
	 $('.jquery_form_error_message').remove();
	 var candidateId = document.getElementById("candidateId").value
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		
		 document.getElementById("resumesDiv").style.display="";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $("#uploadFile").removeAttr("data-validation");
		 $("#uploadFile").val('');
		 $('.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateResumes?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	if( json.length > 0){
			        	var output = "";
			        	output = output + "<option value=''>-- Select Resume --</option>"; 
			        	for (var i=0; i<json.length; i++)
						{
				        		var opt = json[i];
				        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
				        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
				            	}else{
				            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
				            	}
						}
			        	$("#candidateResumesList").empty();
				        $("#candidateResumesList").append(output);
				        
				        document.getElementById("newResumeDiv").style.display="none";
				        document.getElementById("skillsDiv").style.display="none";
				        $('#newUploadFile').removeAttr('data-validation');
		        		$('#newUploadFile').attr("data-validation-optional","true"); 
		        		
		        		 $('#candidateResumesList').removeAttr('data-validation-optional');
		    			 $("#candidateResumesList").attr("data-validation", "required");
		        	}else{
		        		
		        		 $('#candidateResumesList').removeAttr('data-validation');
		        		 $('#candidateResumesList').attr("data-validation-optional","true"); 
		        		
		        		 document.getElementById("resumesDiv").style.display="none";
		        		 document.getElementById("browseDiv").style.display="none";
		        		 document.getElementById("orConditionDiv").style.display="none";
		        		
		        		 document.getElementById("newResumeDiv").style.display="";
		        		 document.getElementById("skillsDiv").style.display="";
		        		 $('#newUploadFile').removeAttr('data-validation-optional');
		        		 $("#newUploadFile").attr("data-validation", "required validatedoc");
		        		 
		        		 $('#uploadFile').removeAttr('data-validation');
		        		 $('#uploadFile').attr("data-validation-optional","true"); 
		        	}
		        	
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Resume --</option>"; 
     	 $("#candidateResumesList").empty();
     	 $("#candidateResumesList").removeAttr("data-validation");
		 document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $('#uploadFile').removeAttr('data-validation');
		 $('#uploadFile').attr("data-validation-optional","true"); 
	     $("#candidateResumesList").append(output);
	 }
	
 }
</script>

<script type="text/javascript">
function getCandidateDetails(){
	var candidateId = document.getElementById("candidateId").value
	var sourceleadUrl ='${sourceleadUrl}';
	if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateDetails?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	 $("input[name=FirstName]").val(json['firstName']);
		        	 $("input[name=EmailId]").val(json['email']);
		        	 $("input[name=EmailId]").attr('readonly', 'readonly');
		        	 
		        	 if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] != 'undefined'){
		        		 $("input[name=LastName]").val(json['lastName']);
		        	 }else{
		        		 $("input[name=LastName]").val(''); 
		        	 }
		        	 
		        	 if(json['mobilePhone'] != null && json['mobilePhone'] != "" && json['mobilePhone'] != 'undefined'){
		        		 $("input[name=PhoneNumber]").val(json['mobilePhone']);
		        	 }else{
		        		 $("input[name=PhoneNumber]").val(''); 
		        	 }
		        	 
		        	 if(json['designation'] != null && json['designation'] != "" && json['designation'] != 'undefined'){
		        		 $("input[name=Designation]").val(json['designation']);
		        	 }else{
		        		 $("input[name=Designation]").val(''); 
		        	 }
		        	 
		        	 if(json['candidateEdu'] != null && json['candidateEdu'] != "" && json['candidateEdu'] != 'undefined'){
		        		 $("input[name=Education]").val(json['candidateEdu']);
		        	 }else{
		        		 $("input[name=Education]").val(''); 
		        	 }
		        	 
		        	 if(json['country'] != null && json['country'] != "" && json['country'] != 'undefined'){
		        		 $("select[name=country]").val(json['country']);
		        	 }else{
		        		 $("select[name=country]").val('');
		        	 }
		        	 
		        	 if(json['state'] != null && json['state'] != "" && json['state'] != 'undefined'){
		        		 $("select[name=state]").val(json['state']);
		        	 }else{
		        		 $("select[name=state]").val('');
		        	 }
		        	 
		        	 if(json['city'] != null && json['city'] != "" && json['city'] != 'undefined'){
		        		 getCandidateCities(json['city']);
		        	 }else{
		        		 $("select[name=city]").val('');
		        	 }
		        
		        	 if(json['noticePeriod'] != null && json['noticePeriod'] != "" && json['noticePeriod'] != 'undefined'){
		        		 $("select[name=NoticePeriod]").val(json['noticePeriod']);
		        	 }else{
		        		 $("select[name=NoticePeriod]").val('');
		        	 }
		        	 
		        	 if(json['experienceYears'] != null && json['experienceYears'] != "" && json['experienceYears'] != 'undefined'){
		        		 $("select[name=WorkExperience]").val(json['experienceYears']);
		        		 if(json['experienceYears'] == "25+"){
		        			 $("#monthExp").attr("disabled", "disabled");
		        		 }else{
		        			 $("#monthExp").removeAttr("disabled");
		        		 }
		        	 }else{
		        		 $("select[name=WorkExperience]").val(0);
		        	 }
		        	 
		        	 if(json['experienceMonths'] != null && json['experienceMonths'] != "" && json['experienceMonths'] != 'undefined'){
		        		 $("select[name=monthExp]").val(json['experienceMonths']);
		        	 }else{
		        		 $("select[name=monthExp]").val(0);
		        	 }
		        	 
		        	 if(json['expectedRate'] != null && json['expectedRate'] != "" && json['expectedRate'] != 'undefined'){
		        		 $("input[name=ExpectedCTC]").val(json['expectedRate']);
		        	 }else{
		        		 $("input[name=ExpectedCTC]").val(''); 
		        	 }
		        	 
		        	 
		        	 if(json['rateType'] != null && json['rateType'] != "" && json['rateType'] != 'undefined'){
		        		 $("select[name=expectedCTCRatePer]").val(json['rateType']);
		        	 }else{
		        		 $("select[name=expectedCTCRatePer]").val('');
		        	 }
		        	 
		        	 if(json['expectedRateCurrencey'] != null && json['expectedRateCurrencey'] != "" && json['expectedRateCurrencey'] != 'undefined'){
		        		 $("select[name=expectedCTCRateType]").val(json['expectedRateCurrencey']);
		        	 }
		        	 
		        	 if(json['currentRate'] != null && json['currentRate'] != "" && json['currentRate'] != 'undefined'){
		        		 $("input[name=CurrentCTC]").val(json['currentRate']);
		        		 validateCurrentCTC();
		        	 }else{
		        		 $("input[name=CurrentCTC]").val(''); 
		        		 validateCurrentCTC();
		        	 }
		        	 
		        	 if(json['currentRateType'] != null && json['currentRateType'] != "" && json['currentRateType'] != 'undefined'){
		        		 $("select[name=currentCTCRatePer]").val(json['currentRateType']);
		        		 validateExpectedCTC();
		        	 }else{
		        		 $("select[name=currentCTCRatePer]").val(''); 
		        		 validateExpectedCTC();
		        	 }
		        	 
		        	 if(json['currentRateCurrency'] != null && json['currentRateCurrency'] != "" && json['currentRateCurrency'] != 'undefined'){
		        		 $("select[name=currentCTCRateType]").val(json['currentRateCurrency']);
		        	 }
		        	 
		        },
		    });
	 }else{
		 $("input[name=FirstName]").val('');
    	 $("input[name=LastName]").val('');
    	 $("input[name=EmailId]").val('');
    	 $("input[name=EmailId]").removeAttr("readonly");
    	 
    	 
    	 $("input[name=PhoneNumber]").val('');
    	 $("input[name=Designation]").val('');
    	 $("select[name=country]").val('');
    	 $("select[name=state]").val('');
    	 $("select[name=city]").val('');
    	 $("select[name=NoticePeriod]").val('');
    	 $("select[name=WorkExperience]").val('');
    	 $("input[name=ExpectedCTC]").val('');
    	 $("input[name=CurrentCTC]").val('');
    	 $("input[name=Education]").val('');
	 }
}
</script>

<script>
function getExpYears(){
	var expYears = $("select[name=WorkExperience]").val();
	 if(expYears == "25+"){
		 $("#monthExp").attr("disabled", "disabled");
	 }else{
		 $("#monthExp").removeAttr("disabled");
	 }
}
</script>


<script type="text/javascript">
function getAllCandidateCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---Select---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${SourceLeadWeb_orgLocations.country}'){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#country').trigger("chosen:updated");
            getCandidateStates();
        },
        error: function(e){
        }
    });
} 
</script>

<script>
function getCandidateStates(){
	
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---Select---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---Select---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---Select---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCandidateCities(city){
	
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	if(stateName == null || stateName == "" || stateName =="undefined"){
		stateName = document.getElementById("state");
	}
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---Select---</option>');
	            for(var i=0;i<json.length;i++){
	            	var selected = '';
	            	if(city != null && city != "" && city !="undefined" && json[i] == city){
	            		selected = 'selected';
	            	}
	            	$('#city').append('<option value="'+json[i]+'" '+selected+'>'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	            $("#loading").removeAttr("style"); 
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---Select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>
<script type="text/javascript">
function init(){
	       getAllCandidateCountries();
	      
	       $("#country").on('change', function (e) {
	    	   getCandidateStates();
	    	   getCandidateCities();
	       });
	       $("#state").on('change', function (e) {
	    	   getCandidateCities();
	       });
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
}
</script>

<script>
  function dobDatePicker() {
	  $('#DOB').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
	  
  }
</script>


 <script>
 $(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
 	 if(validate_fileupload(this.value)){
 		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		var form = $('#addApplicant_form')[0];
  		var formData = new FormData(form); 
		var sourceleadUrl = '${sourceleadUrl}';
		   $.ajax({
				    type: "POST",
			    	data: formData,
		   	        contentType : 'application/json; charset=utf-8',
		   	        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
		   	        dataType : 'json',
					url : sourceleadUrl+"/parseResume?${_csrf.parameterName}=${_csrf.token}",
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
			        	 
			        	 $("#candidateSkills").empty();
			        	 var internalOutput = "";
 						 <c:forEach var="skill" items="${skillsList}">
 						    internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
 	                     </c:forEach>
 						var skills = json.skills;
 						if(skills != null && skills != 'undefined' && skills != ''){
 							var str_array = skills.split(',');
 							for(var i = 0; i < str_array.length; i++) {
 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
 							   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
 							}
 						}
 						$("#candidateSkills").append(internalOutput);
 		        		$("#candidateSkills").trigger("chosen:updated");
  						
			        	 
						 validateCandidateEmail();
						 $("#loading").removeAttr("style");
					},
				});
 	 }
 });

</script> 


<!-- Country,State,City Script Starts Here -->
<script type="text/javascript">
function validate_fileupload(fileName)
{
    var allowed_extensions = new Array("docx","doc","pdf","rtf");
    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>


<script type="text/javascript">
 function validateCandidateEmail() {
	 var email =   $("input[name=EmailId]").val();
	$('#emailDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId = $("#requirementId").val();
	 if(email != null && email != ""){
		
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateRequirementCandidateEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&requirementId="+requirementId, 
		        success: function(data) {
		         	if(data == 'true'){
		         		  $("#save_button").attr("disabled", "disabled");
			        	  $("#save_button" ).addClass('btn btn-sm btn-primary').val('SUBMIT');
			        	  $('.jquery_form_error_message').remove();
		        		  $("input[name=EmailId]").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
		        		  $("input[name=EmailId]").setAttribute('record-exist','yes');
		        		  $("input[name=EmailId]").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
		        	}
		        	else{
		        		$("#save_button").removeAttr("disabled");
		        		$("input[name=EmailId]").removeAttr( "record-exist record-exist-errormsg");
		        		$('.jquery_form_error_message').remove();
		        		
		        	} 
		        },
		    });
	 }
 }
 </script>   
 
 
 <script>
 function validateCurrentCTC(){
	 var currentCTC = $("input[name=CurrentCTC]").val();
	 if(currentCTC){
		 $("#currentCTCRatePer").removeAttr("data-validation-optional");
		 $("#currentCTCRatePer").attr("data-validation", "required");
	 }else{
		 $("#currentCTCRatePer").removeAttr("data-validation");
		 $("#currentCTCRatePer").attr("data-validation-optional", "true");
	 }
 }
 </script>
 
  <script>
 function validateExpectedCTC(){
	 var expectedCTC = $("input[name=ExpectedCTC]").val();
	 if(expectedCTC){
		 $("#expectedCTCRatePer").removeAttr("data-validation-optional");
		 $("#expectedCTCRatePer").attr("data-validation", "required");
	 }else{
		 $("#expectedCTCRatePer").removeAttr("data-validation");
		 $("#expectedCTCRatePer").attr("data-validation-optional", "true");
	 }
 }
 </script>
 
 <script>
 function changeRateLabels(){
	 
	 var currentRate = document.getElementById("currentRate").textContent;
	 var currentstringLength = currentRate.length; 
	 var currentRatelastChar = currentRate.charAt(currentstringLength - 1);
	 
	 if(currentRatelastChar == "*"){
		 $("#currentRate").html("Current Rate *"); 
	 }else{
		 $("#currentRate").html("Current Rate");
	 }
	 
	 var expectedRate = document.getElementById("expectedRate").textContent;
	 var expectedstringLength = expectedRate.length; 
	 var expectedRatelastChar = expectedRate.charAt(expectedstringLength - 1);
	 
	 if(expectedRatelastChar == "*"){
		 $("#expectedRate").html("Expected Rate *"); 
	 }else{
		 $("#expectedRate").html("Expected Rate");
	 }
	 
	 
	 
 }
 </script>