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
	result = result + '<form id="addApplicant_form" >';
	result = result + '<h3>Add Applicant</h3>';
	result = result + '<div class="form-horizontal clearfix">';
	result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
	
	result = result + '<div class="row">';
	result = result + '<div class="col-md-5 mb10">';
	result = result + '<label>Select Candiate</label>';
	result = result + '<select  onchange="getCandidateResumes();" onclick="getCandidateDetails();" name="candidateId" id="candidateId" field-name="Candidate" class="form-control  block">';
	result = result + '<option value="">--Select Candidate--</option>';
	result = result + '<c:forEach var="option" items='${candidatesList}'>';
	result = result + '<option value="'+${option.id}+'">${option.firstName} ${option.lastName}</option>';
	result = result + '</c:forEach>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '<div class="text-center text-info col-md-2 mb10">';
	result = result + '------- ( OR ) -------';
	result = result + '</div>';
	
	result = result + '<div class="col-md-5">';
	result = result + '<div class="panel-border col-md-12">';
	result = result + '<div class="col-md-12" id="browseDiv"  style="display: block;">';
	result = result + '<div class="input-group">';
	result = result + '<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '<input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">';
	result = result + '</span> </label>';
	result = result + '<input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume *">';
	result = result + '</div>';
	result = result + '</div>';
                    
	result = result + '<div class="col-md-12" id="resumesDiv"  style="display: none;">';
	result = result + '<div class="col-md-12 mt10 mb10 clearfix text-center text-info">';
	result = result + '</div>';
	result = result + '<select id="candidateResumesList" name="candidateResume" field-name="Resume" class="form-control block">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div></div>';
	
	result = result + '<div id="sjfb-fields" class="viewTemplate">';
	
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
        var con = JSON.stringify(frm);
        con = con.replace(/[{}]/g, "");
        var jsonData = encodeURIComponent(con);
        var sourceleadUrl ='${sourceleadUrl}';
        var requirementId = $("#requirementId").val();
        alert(requirementId);
        $( "#save_button" ).addClass('button').val('Processing..');
        
    	    $.ajax({
	    	type: "POST",
   	    	url : sourceleadUrl+"/saveAddApplicant?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData+"&con="+con,
	        success: function(result) {
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
			        		output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
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
function getAllCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/sourceLeadCountryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
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

