<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="/resources/css/style.css" />


<!-- For add New Recomended Applicant -->
<script>
function getAddRecomendedApplicant(requirementId){
	generateOrgSubmissionForm(sourceleadUrl,requirementId)
	var result = "";
	result = result + '<form id="addRecomApplicant_form" name="addRecomApplicant_form" enctype="multipart/form-data" method="post">';
	result = result + '<h3>Add Applsdfsdfsdficant</h3>';
	result = result + '<div class="form-horizontal clearfix">';
	result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
	
	result = result + '<div id="sjfb-fields" class="viewTemplate"> <div id="loading"></div> <div id="emailDiv"></div>';
	
	result = result + '</div>';
	
                        
	result = result + '</div>';
	 result = result + '<div class="form-footer clearfix">';
	result = result + '<div class="pull-right mr">';
	result = result + '<button type="submit" id="save_Recom_button"  class="btn btn-sm btn-primary ">SUBMIT</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';  
	result = result + '</form>';
	 
    
	
	$("#addRecomApplicantDiv").empty();
	$("#addRecomApplicantDiv").append(result);
	
	$(".new_side_recomm_wrap").addClass("new_side_recomm_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
}
</script>


<!-- For add New Applicant -->
<script>
    $(".addRecomendedApplicant_close, .side_mask").click(function() {
    	$(".new_recomm_side_wrap").removeClass("new_side_recomm_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<!-- For add New Applicant -->
<script type="text/javascript">
$('body').on('submit', '#addRecomApplicant_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
      var frm = $('#addRecomApplicant_form').serializeFormJSON();
      var sourceleadUrl ='${sourceleadUrl}';
      var requirementId = $("#requirementId").val();
      var frm = $('#addRecomApplicant_form').serializeFormJSON();
      var con1 = JSON.stringify(frm);
      con1= con1.replace(/[{}]/g, "");
      var jsonData = encodeURIComponent(con1);
      var form = $('#addRecomApplicant_form')[0];
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
      
      
		
       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      
  	     $.ajax({
	    	type: "POST",
	    	data: formData,
 	        contentType : 'application/json; charset=utf-8',
 	        enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
 	        dataType : 'json',
 	    	url : sourceleadUrl+"/saveAddApplicant?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData+"&con="+con,
	        success: function(result) {
	        	if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
		        	if((result.object === "success")){
		        		location.replace(sourceleadUrl+"/requirementSummary?requirementId="+requirementId); 
			        }
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
	        },
	    }); 
      return true; 
    }
    else{
    	return false;	
       }
  });

    
   	$('body').on('blur', '#addRecomApplicant_form', function() {
		$("#addRecomApplicant_form").showHelpOnFocus();
		$("#addRecomApplicant_form").validateOnBlur(false, validationSettings);
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
function getAllCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/sourceLeadCountryAutoComplete?"+tokenParamName+"="+tokenValue+"",
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
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
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
function getCities(){
	
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
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
	       getAllCountries();
	      
	       $("#country").on('change', function (e) {
	    	   getStates();
	    	   getCities();
	       });
	       $("#state").on('change', function (e) {
	    	   getCities();
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
	var educationList = [];
	var skillList = [];
	$(function() {
		 var i= 0;
		 <c:forEach var="skill" items="${skillList}">
		 	skillList[i++] = "${skill.name}";
		 </c:forEach>
		 var i= 0;
		 <c:forEach var="edu" items="${educationsList}">
		 	educationList[i++] = "${edu.name}";
		 </c:forEach>
	});
</script>


 <script>
 $(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
 	 if(validate_fileupload(this.value)){
 		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#addRecomApplicant_form')[0];
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
					url : sourceleadUrl+"/parseResume?"+tokenParamName+"="+tokenValue+"",
					success : function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							if(response.object !== null) {
								var json = response.object;
								var firstName = json.firstName;
								var lastName = json.lastName;
								
								
								 $("input[name='FirstName']").val(firstName);
					        	 $("input[name='LastName']").val(lastName);
					        	 $("input[name='EmailId']").val(json.email.split("<<>>")[0]);
					        	 
					        	 for(var e in json.phoneNumbers) {
					        		 if(e === "0")
						        	 	$("input[name='PhoneNumber']").val(json.phoneNumbers[e]);
					        		 else
					        			 $("input[name='AlternateNumber']").val(json.phoneNumbers[e]);
					     		 }
					        	 
					        	 $("input[name='country']").val(json.country === null && json.country !== "" ? json.country : "");
					        	 $("input[name='state']").val(json.state === null && json.state !== "" ? json.state : "");
					        	 $("input[name='city']").val(json.city === null && json.city !== "" ? json.city : "");
					        	 $("input[name='zipcode']").val(json.zipcode === null && json.zipcode !== "" ? json.zipcode : "");
					        	 
					        	 $("#DOB").val(json.dob !== null && json.dob !== "" ? json.dob : "");
					        	 $("input[name='Current/LastCompany']").val(json.employeers);
					        	 
					        	 $("input[name='Designation']").val(json.designation !== null && json.designation !== "" ? json.designation : "");
					        	 
					        	 $("input[name='Education']").val(json.educations);
					        	 
				        	 	if(json.totalYearsExp > 0) {
				        			$("select[name='WorkExperience']").val(json.totalYearsExp);
				        		} else if(json.totalMonthsExp > 0) {
				        			$("select[name='WorkExperience']").val(0);
				        		}
				        		
				        		if(json.totalMonthsExp > 0) {
				        			$("select[name='monthExp']").val(json.totalMonthsExp);
				        		} else if(json.totalYearsExp > 0) {
				        			$("select[name='monthExp']").val(0);
				        		}
					        	 
					        	 $("#candidateSkills").empty();
		 						
		 						var internalOutput = "";
		 						var skills = json.skills;
		 						if(skills != null && skills != 'undefined' && skills != ''){
		 							var str_array = skills;
		 						} else {
		 							str_array = [];
		 						}
		 						
		 					   for(var j = 0; j< skillList.length; j++){
		 						    var existed = 0;
		 							for(var i = 0; i < str_array.length; i++) {
		 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		 							   if(str_array[i] == skillList[j]){
		 								   existed = 1;
		 								   internalOutput = internalOutput+"<option value='"+str_array[i]+ "' selected>"+str_array[i]+"</option>";
		 								   break;
		 							   }
		 							}
		 							if(existed == 0){
		 								internalOutput = internalOutput+"<option value='"+skillList[j]+"'>"+skillList[j]+"</option>";
		 					        }
		 						 }
		 					   
		 					   for(var e in str_array) {
		 							internalOutput = internalOutput+"<option value='" + str_array[e] + "' selected>" + str_array[e] + "</option>";
		 						}
		 						
		 						$("#candidateSkills").append(internalOutput);
		 		        		$("#candidateSkills").trigger("chosen:updated");
		 		        		
								 validateCandidateEmail();
							} else {
								showError("Resume Parsing Failed");								
							}
							 $("#loading").removeAttr("style");
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
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
	 if(email != null && email != ""){
		
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateCandidateEmail?"+tokenParamName+"="+tokenValue+"&email="+email, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if(response.object == 'true') {
			         		  $("#save_button").attr("disabled", "disabled");
				        	  $("#save_button" ).addClass('btn btn-sm btn-primary').val('SUBMIT');
				        	  $('.jquery_form_error_message').remove();
			        		  $("input[name=EmailId]").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
			        		  $("input[name=EmailId]").setAttribute('record-exist','yes');
			        		  $("input[name=EmailId]").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
			        	} else{
			        		$("#save_button").removeAttr("disabled");
			        		$("input[name=EmailId]").removeAttr( "record-exist record-exist-errormsg");
			        		$('.jquery_form_error_message').remove();
			        	} 
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	 }
 }
 </script>   