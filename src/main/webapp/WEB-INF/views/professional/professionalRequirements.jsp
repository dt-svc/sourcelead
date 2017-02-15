<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="resources/css/style.css" />
<script src="resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<script type="text/javascript">
window.onload = function () {
	requirementsList(0);
	document.getElementById('requirementType').value = 'OpenJObs';
 };
</script>



<section class="content-wraper openJobsWrap">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Requirements<span class="select-fild">
          <select id="requirementType" onchange="requirementsList('0');">
           	<option value="OpenJObs">Open</option>
            <option value="CrowdSourcingJobs">Crowdsourcing</option>
            <option value="AppliedJobs">Applied</option>
          </select>
          </span>
			<div class="timeNewSe timeNewSe_h">
                <select class="form-control chosen-select">
                  <option>All open jobs</option>
                  <option>Referred to me</option>
                  <option>Referred by me</option>
                </select>
            </div> 
		  <div class="clearfix"></div>            
          </h2>        
      </div>
    </section>
    
    <!-- Project -->
 
	  <section class="openJobsHead">
	  
		  <span>
			  <i class="fa fa-search"></i><input type="text" placeholder="Search" class="searchJob">
		  </span>
		  <span>
			  <i class="fa fa-map-marker"></i><input type="text" placeholder="Location" class="searchJob searchJobLo">
		  </span>
		  <span>
			  <input type="button" value="Search" class="jobBtn">
		  </span>		  		  	  
	  
			<div class="timeNewSe" style="float:right;">
                <select class="form-control chosen-select">
                  <option>1 day ago</option>
                  <option>3 day ago</option>
                  <option>1 week ago</option>
                  <option>3 week ago</option>
                  <option>1 month ago</option>
                </select>
            </div>    
            
		  <div class="clearfix"></div>
	  </section>
	   <br/>
	   <input type="hidden" name="requirementOffset" id="requirementOffset" value="0"/>
	  <div id="requirementsList"></div> 
	  <a href="#" id="loadMoreSection" style="display: block;"><section class="loadJob" onclick="getViewMoreRecords();"><i id="viewMoreLoading"></i><span id="viewMore">View more</span></section></a>
	  
	 <div class="side_mask"><div class="proj_close"></div></div>
	 <div class="new_side_wrap">
		<div id="applytoJobDiv"></div>
	 </div>    
            
	  </section>

<jsp:include page="../masterFooter.jsp" />


<script>
function getViewMoreRecords(){
	 offset = $("#requirementOffset").val();
	 offset = parseInt(offset)+parseInt(10);
	 $("#requirementOffset").val(offset);
	 requirementsList(offset);
	 
	 document.getElementById("viewMoreLoading").className = 'fa fa-spinner fa-spin'
	 
}
</script>


<script>
function requirementsList(offset){
	var sourceleadUrl = '${sourceleadUrl}';
	var requirementType = document.getElementById('requirementType').value;
	if(offset == 0){
		$("#requirementsList").empty();
		$("#requirementOffset").val(offset)
		$('#viewMore').html("View More");
	}
	
	
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getCrowdSourcingRequirements?${_csrf.parameterName}=${_csrf.token}&requirementType="+requirementType+"&offset="+offset,
	   			
	    success: function(data) {
       	 var json = JSON.parse(data);
       	 var result = "";
	     if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 	
	   	  if(json.length > 0)	
	   		{
	 			for(var i=0; i<json.length; i++){
	 				
			 				var requirements = json[i];
			 				
			 				
			 				var jobType = '';
			 				var experience = '';
			 				var rate = '';
			 				var location = '';
			 				
			 				if(requirements.jobType != null && requirements.jobType != '' && requirements.jobType != 'undefined'){
			 					jobType = requirements.jobType;
			 				}else{
			 					jobType = 'No Data';
			 				}
			 				
			 				
			 				if(requirements.minExperience != 'undefined' && requirements.maxExperience == 'undefined'){
			 					experience = requirements.minExperience+" Years";
			 				}
			 				else if(requirements.minExperience == 'undefined' && requirements.maxExperience != 'undefined'){
			 					experience = requirements.maxExperience+" Years";
			 				}
			 				else if(requirements.minExperience != null && requirements.maxExperience != null){
			 					experience = requirements.minExperience+" to "+requirements.maxExperience+" Years";
			 				}else{
			 					experience = 'No Data';
			 				}
			 				
			 				if(requirements.state != null && requirements.state != '' && requirements.state != 'undefined'){
			 					location = requirements.state+", "+requirements.country;
			 				}else{
			 					location = requirements.country;
			 				}
			 				
			 				if(requirements.rate != null && requirements.rate != '' && requirements.rate != 'undefined'){
			 					rate = requirements.rate+" / "+requirements.rateType;
			 				}else{
			 					rate = 'No Data';
			 				}
			 				
			 				result = '<section class="openCta">';
			 				result = result +'<div class="OpenJob">';
			 				result = result +'<a href="open-jobs-details.html"><h3>'+requirements.jobTitle+'</h3></a>';
			 				result = result +'<h4>'+requirements.postedOrg+'</h4>';
			 				result = result +'<span>';
			 				result = result +'<b><i class="fa fa-clock-o"></i>'+jobType+'</b>';
			 				result = result +'<b><i class="fa fa-briefcase"></i>'+experience+'</b>';
			 				result = result +'<b><i class="fa fa-map-marker"></i>'+location+'</b>';
			 				result = result +'<b><i class="fa fa-money"></i>'+rate+'</b>';
			 				result = result +'</span>';
			 				result = result +'<h5>';
			 				result = result +'<strong>Skills :</strong>';
			 				result = result +'<ul>';
			 				
			 				if(requirements.skillsList != null){
			 					for(var j=0; j<requirements.skillsList.length; j++){
				 					result = result +'<li>'+requirements.skillsList[j]+'</li>';
				 				}
			 				}else{
			 					result = result +'<li>No Data</li>';
			 				}
			 				
			 				
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</ul>';
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</h5>';
			 		  
			 				result = result +'</div>';
			 				
			 				
			 				if(requirementType != "AppliedJobs"){
			 					result = result +'<div class="openCtaRight">';
				 				/* result = result +'<p>Share Requirement</p>';
				 				result = result +'<span>';
				 				result = result +'<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>';
				 				result = result +'<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>';				  
				 				result = result +'<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>';
				 				result = result +'<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>';
				 				result = result +'</span>'; */
				 				result = result +'<div class="openPostBtns">';
				 				result = result +'<a href="javascript:void(0)" onclick="applyToJob('+requirements.requirementId+');"  class="jobApply">Apply</a>';
				 				
				 				if(requirementType == "CrowdSourcingJobs"){
				 					result = result +'<a class="refer" href="javascript:void(0)">Refer</a>';
				 				}
				 				
				 				result = result +'</div>';
				 				result = result +'<div class="openPostDate">';
				 				result = result +'Posted :';
				 				result = result +'<b><i class="fa fa-clock-o"></i> '+requirements.calDays+'</b>';
				 				result = result +'</div>';			  
				 				result = result +'</div>';
			 				}
			 				else if(requirementType == "AppliedJobs"){
			 					
			 					result = result +'<div class="openCtaRight text-center">';
			 					result = result +'<div class="appliedWrap">';
			 					result = result +'<i class="fa fa-check-circle"></i>';
			 					result = result +'<div class="openPostDate">';
			 					result = result +'<b><h5>Applied</h5></b>';
			 					result = result +'</div>';
			 					result = result +'</div>';
			 					result = result +'</div>';
			 				  
			 				}
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</section>';
			 				
			       	    	$('#requirementsList').append(result);
			       	    	document.getElementById("viewMoreLoading").removeAttribute("class");
			       	    	document.getElementById("loadMoreSection").style.display="";
		            		
				   }
	   		    }
          }else if(offset == 0){
	   		     var result ="";
	    	     result = result+"   <div class='row'>"; 
	    	     result = result +'<div class="clearfix"></div>';
				 result = result+"	<div class='col-lg-12'>"; 
				 result = result+"		<div class='content-area clearfix'>"; 
				 result = result+"			<div class='blank-pages'>";
				 result = result+"			        <p><i class='fa fa-5x fa-briefcase'></i></p>";
				 result = result+"			        <p>No Requirements Found</p>";
				 result = result+"			 </div>";
				 result = result+"		</div>"; 
				 result = result+"	</div>"; 
				 result = result+"  </div> "; 
				$("#requirementsList").empty();
			    $("#requirementsList").append(result); 
			    document.getElementById("loadMoreSection").style.display="none";
   		    }else{
   		    	
   		    	$('#viewMore').html("No Records Found");
   		    	document.getElementById("viewMoreLoading").removeAttribute("class");
   		    }
   	
		 },
       error: function(e){
       }
	 }); 
}
</script>



<script>
function applyToJob(requirementId){
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	getRequirementSubmissionTemaplate(requirementId);
}
</script>

<script>
function getRequirementSubmissionTemaplate(requirementId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var candidateId = '${candidateId}';
	 generateOrgSubmissionForm(sourceleadUrl,requirementId) 
	
	
	var result = "";
	result = result + '<form id="applyToJob_form" name="applyToJob_form" method="post">';
	result = result + '<div class="form-horizontal clearfix select-field">';
	result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
	result = result + '<input type="hidden" id="candidateId" name="candidateId" value='+candidateId+'>';
	
	result = result + '<div class="row">';
	
	result = result + '<div class="venk clearfix">';
	result = result + '<div class="col-md-8 pull-left">';
	result = result + '<div class="panel-border_a col-md-5 mt10" id="resumesDiv">';
	result = result + '<select id="candidateResumesList" data-validation="required" onchange="getCandidateDetails();" name="candidateResume" field-name="Resume" class="form-control block">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div id="sjfb-fields" class="viewTemplate"> <div id="loading"></div></div>';
	result = result + '</div>';
	
	result = result + '</div>';  
                        
	
	result = result + '<div class="form-footer clearfix">';
	result = result + '<div class="pull-right mr">';
	result = result + '<button type="submit" id="save_button"  class="btn btn-sm btn-primary ">SUBMIT</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';  
	result = result + '</form>';
	
	$("#applytoJobDiv").empty();
	$("#applytoJobDiv").append(result);
	
	getResumesList();
	
	
	
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
}
</script>


<script>
function getResumesList(){
	
	 var candidateId = '${candidateId}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 
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
			        
	        	}
	        	
	        },
	    });
}
</script>


<script>
function getCandidateDetails(){
	 $("input[name=FirstName]").val('${SourceLeadWeb_user.firstName}');
	 
	 if('${SourceLeadWeb_user.firstName}' != null && '${SourceLeadWeb_user.firstName}' != "" && '${SourceLeadWeb_user.firstName}' != 'undefined'){
		 $("input[name=LastName]").val('${SourceLeadWeb_user.lastName}');
	 }
	 
	 $("input[name=EmailId]").val('${SourceLeadWeb_user.email}');
	 $("input[name=PhoneNumber]").val('${SourceLeadWeb_user.mobilePhone}');
	 
	 getAllSubmissionCandidateCountries();
     
     $("#submissionCountry").on('change', function (e) {
  	   getCandidateStates();
  	   getCandidateCities();
     });
     $("#submissionState").on('change', function (e) {
  	   getCandidateCities();
     });
    
     $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
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


<script type="text/javascript">
function getAllSubmissionCandidateCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#submissionCountry').empty();
        	$('#submissionCountry').append('<option value="">---Select---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${SourceLeadWeb_user.country}'){
            		$('#submissionCountry').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#submissionCountry').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#submissionCountry').trigger("chosen:updated");
            getCandidateStates();
        },
        error: function(e){
        }
    }); 
} 
</script>

<script>
function getCandidateStates(){
	
	var countryName = $("#submissionCountry").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#submissionState').empty();
	        	$('#submissionState').append('<option value="">---Select---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#submissionState').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#submissionState').trigger("chosen:updated");
	            
	            $('#submissionCity').empty();
	            $('#submissionCity').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#submissionState').empty();
    	$('#submissionState').append('<option value="">---Select---</option>');
    	$('#submissionState').trigger("chosen:updated");
    	$('#submissionCity').empty();
    	$('#submissionCity').append('<option value="">---Select---</option>');
        $('#submissionCity').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCandidateCities(city){
	
	var countryName = $("#submissionCountry").val();
	var stateName = $("#submissionState").val();
	
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
	        	$('#submissionCity').empty();
	        	$('#submissionCity').append('<option value="">---Select---</option>');
	            for(var i=0;i<json.length;i++){
	            	var selected = '';
	            	if(city != null && city != "" && city !="undefined" && json[i] == city){
	            		selected = 'selected';
	            	}
	            	$('#submissionCity').append('<option value="'+json[i]+'" '+selected+'>'+json[i]+'</option>');
	            }
	            $('#submissionCity').trigger("chosen:updated");
	            $("#loading").removeAttr("style"); 
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#submissionCity').empty();
    	$('#submissionCity').append('<option value="">---Select---</option>');
    	$('#submissionCity').trigger("chosen:updated");
	}
}
</script>


<script>	
   
    $(".side_mask").click(function(){
		$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
	
// Refer job	
    $(".refer").click(function(){
		$(this).parent(".openPostBtns").find(".referWrap").toggleClass("referWrapActive");
	});
    $(".canBtn").click(function(){
		$(".referWrap").removeClass("referWrapActive");
	});		
	
</script> 


<script type="text/javascript">
$('body').on('submit', '#applyToJob_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
      var frm = $('#applyToJob_form').serializeFormJSON();
      var sourceleadUrl ='${sourceleadUrl}';
      var requirementId = $("#requirementId").val();
      var frm = $('#applyToJob_form').serializeFormJSON();
      var con1 = JSON.stringify(frm);
      con1= con1.replace(/[{}]/g, "");
      var jsonData = encodeURIComponent(con1);
      var form = $('#applyToJob_form')[0];
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
            });
       });
      var con = JSON.stringify(fields);
      
      con = encodeURIComponent(con);
     
      
		
       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
      
  	     $.ajax({
	    	type: "POST",
	    	data: formData,
 	        contentType : 'application/json; charset=utf-8',
	        processData: false,
	        contentType:false,
 	        dataType : 'json',
 	    	url : sourceleadUrl+"/applyToJobFromCrowdSourcing?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData+"&con="+con,
	        success: function(result) {
	        	location.replace(sourceleadUrl+"/professionalRequirements"); 
	        	
	        },
	    }); 
      return true; 
    }
    else{
    	return false;	
       }
  });

    
   	$('body').on('blur', '#applyToJob_form', function() {
		$("#applyToJob_form").showHelpOnFocus();
		$("#applyToJob_form").validateOnBlur(false, validationSettings);
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

