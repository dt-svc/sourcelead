<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link rel="stylesheet" href="resources/css/jquery-ui.css" type="text/css" >


<!-- For add New Applicant -->
<script>
function getAddApplicant(requirementId){
	
	var sourceleadUrl ='${sourceleadUrl}';
	generateOrgSubmissionForm(sourceleadUrl,requirementId)
	var candidatesList ='${candidatesList}';
	var result = "";
	result = result + '<form id="addApplicant_form" name="addApplicant_form" enctype="multipart/form-data" method="post">';
	result = result + '<h3>Add Applicant</h3>';
	result = result + '<div class="form-horizontal clearfix">';
	result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
	
	result = result + '<div class="row">';
	result = result + '<div class="venk clearfix">';
	result = result + '<div class="col-md-5">';
	result = result + '<label>Select Candiate</label>';
	result = result + '<select  onchange="getCandidateResumes();" onclick="getCandidateDetails();" name="candidateId" id="candidateId" field-name="Candidate" class="form-control  block">';
	result = result + '<option value="">--Select Candidate--</option>';
	result = result + '<c:forEach var="option" items='${candidatesList}'>';
	result = result + '<option value="'+${option.id}+'">${option.firstName} ${option.lastName}</option>';
	result = result + '</c:forEach>';
	result = result + '</select>';
	result = result + '</div>';		
	result = result + '<div id="orConditionDiv" class="text-center text-info col-md-2" style="display: block;">';
	result = result + '------- ( OR ) -------';
	result = result + '</div>';
	
	result = result + '<div class="col-md-5 pull-right">';
	result = result + '<div class="panel-border col-md-12">';
	result = result + '<div class="" id="browseDiv"  style="display: block;">';
	result = result + '<div class="input-group">';
	result = result + '<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '<input type="file" style="display: none;" name="locationMapFile" multiple id="uploadBtn"  class="upload">';
	result = result + '</span> </label>';
	result = result + '<input type="text" class="form-control" data-validation="required" field-name="Resume" readonly id="uploadFile" placeholder="Upload Resume">';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';	
                    
	result = result + '<div class="panel-border_a col-md-12" id="resumesDiv"  style="display: none;">';
	result = result + '<select id="candidateResumesList" name="candidateResume" field-name="Resume" class="form-control block">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div></div>';
	
	result = result + '<div id="sjfb-fields" class="viewTemplate"> <div id="loading"></div> <div id="emailDiv"></div>';
	
	result = result + '</div>';
	
                        
	result = result + '</div>';
	 result = result + '<div class="form-footer clearfix">';
	result = result + '<div class="pull-right mr">';
	result = result + '<button type="submit" id="save_button"  class="btn btn-sm btn-primary ">SUBMIT</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';  
	result = result + '</form>';
	 
    
	
	$("#addApplicantDiv").empty();
	$("#addApplicantDiv").append(result);
	
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
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
	        	/* $(".new_side_wrap").removeClass("new_side_wrap_active");
	    		$(".side_mask").fadeOut(200);
	    		$("body").css('overflow-y', 'auto');
	    		requirementPipeline(requirementId); */
	        	 if((result == 'success')){
	        		location.replace(sourceleadUrl+"/requirementSummary?requirementId="+requirementId); 
		        }  
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
	 var candidateId = document.getElementById("candidateId").value
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		 $("#candidateResumesList").attr("data-validation", "required");
		 document.getElementById("resumesDiv").style.display="";
		 document.getElementById("browseDiv").style.display="none";
		 document.getElementById("orConditionDiv").style.display="none";
		 $("#uploadFile").removeAttr("data-validation");
		 $("#uploadFile").removeAttr("data-validation");
		 $('.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateResumes?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
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
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Resume --</option>"; 
     	 $("#candidateResumesList").empty();
     	 $("#candidateResumesList").removeAttr("data-validation");
		 document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("browseDiv").style.display="";
		 document.getElementById("orConditionDiv").style.display="";
		 $("#uploadFile").attr("data-validation", "required");
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
		        	 $("input[name=LastName]").val(json['lastName']);
		        	 $("input[name=EmailId]").val(json['email']);
		        	 $("input[name=PhoneNumber]").val(json['mobilePhone']);
		        },
		    });
	 }else{
		 $("input[name=FirstName]").val('');
    	 $("input[name=LastName]").val('');
    	 $("input[name=EmailId]").val('');
    	 $("input[name=PhoneNumber]").val('');
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
function getCandidateCities(){
	
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
	        	$('#city').append('<option value="">---Select---</option>');
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
	 if(email != null && email != ""){
		
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateCandidateEmail?${_csrf.parameterName}=${_csrf.token}&email="+email, 
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