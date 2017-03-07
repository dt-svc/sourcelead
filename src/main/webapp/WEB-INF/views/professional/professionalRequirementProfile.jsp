<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <style type="text/css">
 .require_wrap_details_cta b {
    width: 110px;
}
 </style>

<script>
$(document).ready(function() {
	 var requirementUUID = '${requirementUUID}'; 
	 requirementProfile(requirementUUID);
});	
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement Details</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='professionalRequirements'/>">Requirement List </a> » Requirement Details</div>
  </section>
  
  <div class="side_mask"><div class="proj_close"></div></div>
	 <div class="new_side_wrap">
		<div id="applytoJobDiv"></div>
	 </div>    
            
  
  <div id="professionalRequirementProfileDiv"></div>

  </section>
<script>
function requirementProfile(requirementUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/getRequirementProfessionalProfileDetails?requirementUUID="+requirementUUID, 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
		    	var result = "";
		    	if(json.minExp != null && json.minExp != '' && json.minExp != 'undefined'){
		    		minExp = json.minExp +'&nbsp;Years';
		    	}else{
		    		minExp = "";
		    	}
		    	if(json.maxExp != null && json.maxExp != '' && json.maxExp != 'undefined'){
		    		maxExp = json.maxExp + '&nbsp;Years';
		    	}else{
		    		maxExp = "";
		    	}
		    	if(json.rate != null && json.rate != '' && json.rate != 'undefined'){
		    		rate = json.rate;
		    	}else{
		    		rate = "";
		    	}
		    	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
		    		rateType = json.rateType +' / ';
		    	}else{
		    		rateType = "";
		    	}
		    	
		    	if(json.currencyCode != null && json.currencyCode != '' && json.currencyCode != 'undefined'){
		    		currencyCode = json.currencyCode;
		    	}else{
		    		currencyCode = "";
		    	}
		    	
		    	if(json.city != null && json.city != '' && json.city != 'undefined'){
		    		city = json.city +' , ';
		    	}else{
		    		city = "";
		    	}
		    	if(json.state != null && json.state != '' && json.state != 'undefined'){
		    		state = json.state + ' , ';
		    	}else{
		    		state = "";
		    	}
		    	if(json.country != null && json.country != '' && json.country != 'undefined'){
		    		country = json.country;
		    	}else{
		    		country = "";
		    	}
		    	if(json.description != null && json.description != '' && json.description != 'undefined'){
		    		description = json.description;
		    	}else{
		    		description = "NO DATA";
		    	}
		    	if(json.positions != null && json.positions != '' && json.positions != 'undefined'){
		    		positions = json.positions;
		    	}else{
		    		positions = "";
		    	}
		    	
		    	if(json.education != null && json.education != '' && json.education != 'undefined'){
		    		education = json.education;
		    	}else{
		    		education = "NO DATA";
		    	}
		    	
		    	
				 result = result +'	<div class="timesheet_out">';
				 result = result +'    <div class="clients_side_wrap_inn no_pad">';
				 result = result +'      <div class="profile_details requirement_details requirement_details_a clearfix">';
				 result = result +'        <h4 id="id1">'+json.jobTitle+'</h4>';
				 result = result +'        <div class="hide-row" style="display: block;">';
				 result = result +'          <div class="col-md-9">';
				 result = result +'            <div class="row">';
				 result = result +'              <div class="col-md-3">';
				 result = result +'                <div class="company-logo">';
				 result = result +'                  <div class="orgImage"> <img onerror="predefineProfileImage(this);" src="<c:url value="'+sourceleadUrl+'/displayOrgImage/'+json.orgcode+'"/>" alt="Company" title="Company Logo" class="img-responsive"> </div>';
				 result = result +'                 </div>';
				 result = result +'              </div>';
				 result = result +'              <div class="col-md-9">';
				 result = result +'                <div class="canDetails clearfix">';
				 result = result +'                  <div class="row">';
				 result = result +'                    <div class="col-md-12">';
				 result = result +'                      <ul class="list-unstyled can-perdt">';
				 result = result +'                        <li><i class="fa fa-black-tie"></i><span>Min Experience - '+minExp+'</span></li>';
				 result = result +'                        <li><i class="fa fa-black-tie"></i><span>Max Experience - '+maxExp+'</span></li>';
				 result = result +'                        <li><i class="fa fa-rupee"></i><span>'+rate+' '+rateType+' '+currencyCode+'</span></li>';
				 result = result +'                        <li><i class="fa fa-map-marker"></i><span>'+city+' '+state+' '+country+'</span></li>';
				 result = result +'                      </ul>';
				 result = result +'                    </div>';
				 result = result +'                  </div>';
				 result = result +'                </div>';
				 result = result +'                <div class="pro-detials">';
				 result = result +'                  <div class="professinal-details">';
				 result = result +'                    <ul class="list-inline list-unstyled">';
				 if(json.skills != null  && json.skills != '' && json.skills != 'undefiend'){
			    		var requirmentSkills = json.skills;
				    	var skills = new Array();
				    	skills = requirmentSkills.split(",");
				        for (var i = 0; i < skills.length; i++) { 
				        	 var customSkill = skills[i];
				        	 result = result +'<li>'+customSkill+'</li>';
				        }
			    	}else{
	 					result = result +'<li>No Data</li>';
	 				}
				 result = result +'                    </ul>';
				 result = result +'                  </div>';
				 result = result +'                </div>';
				 result = result +'              </div>';
				 result = result +'            </div>';
				 result = result +'          </div>';
				 result = result +'          <div class="col-md-3">';
				 result = result +'         <div class="openPostBtns">';
				 
				 if(json.isJobApplied == 'N'){
				 result = result +'<a href="javascript:void(0)" onclick="cancelReferPopUp();applyToJob('+json.id+');"  class="jobApply">Apply</a>';
				 }else{
					 result = result +'<a href="javascript:void(0)" class="jobApply">Applied</a>';
				 }
				 
				 if(json.isCrowdSourcing == 'Y'){
				    result = result +'<a class="refer" onclick="referToJob('+json.id+')" href="javascript:void(0)">Refer</a>';
				    result = result +'<div class="referWrap" id="refer">';
	 				result = result +'<div class="referWrapInn">';
	 				result = result +'<div id="refertoJobDiv"></div>';  
	 				result = result +'</div>';
	 				result = result +'</div>';
				 }
				 result = result +'          </div>';
				 result = result +'          </div>';
				 result = result +'          <div class="clearfix"></div>';
				 result = result +'        </div>';
				 result = result +'      </div>';
				 result = result +'      <div class="project_side_wrap_inn_tabs" id="new-id">';
				 result = result +'        <div class="project_tabs">';
				 result = result +'          <div id="tab-1" class="project_tabs_inn" style="display: block;">';
				 result = result +'            <div class="require_wrap_details"> ';
				 result = result +'              <h2>Descriptions:</h2>';
				 result = result +'              <textarea placeholder="Enter description">'+description+'</textarea>';
				 result = result +'              <h2>Other details:</h2>';
				 result = result +'              <div class="require_one">';
				 result = result +'                <div class="require_wrap_details_cta"> <b>No. of positions:</b> <span>'+positions+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 result = result +'                <div class="require_wrap_details_cta"> <b>Education:</b> <span title="'+education+'">'+education+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
		            	result = result +'<div class="require_wrap_details_cta require_wrap_details_view require_uploadfile"><b>Attachment:</b><span>'+json.fileName+'</span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadRequirementAttachment?requirementId='+json.id+'\"></a> ';
		            }
		            else{
		            result = result +'<div class="require_wrap_details_cta"> <b>Attachment:</b> <span> -- </span>';	
		            }
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 result = result +'                <div class="clearfix"></div>';
				 result = result +'              </div>';
				 result = result +'            </div>';
				 result = result +'          </div>';
				 result = result +'        </div>';
				 result = result +'      </div>';
				 result = result +'    </div>';
				 result = result +'  </div>';
				 $("#professionalRequirementProfileDiv").empty();
				 $("#professionalRequirementProfileDiv").append(result); 
		    }
	});
}

</script>



<script>
function referToJob(requirementId){
	   appendReferToJob(requirementId); 
	    
	   var result = ''
	    
	    result = result + '<form id="referToJob_form" name="referToJob_form" method="post">';
		result = result +'<div class="form-group col-md-6">';
		result = result +'<input type="hidden" name="referRequirementId" id="referRequirementId" value='+requirementId+' >';
		result = result +'<label class="col-md-12" for="Project Id">First Name *</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="firstName" id="firstName" field-name="First Name" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6">';
		result = result +'<label class="col-md-12" for="Project Id">Last Name</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="lastName" id="lastName" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6">';
		result = result +'<label class="col-md-12" for="Project Id">Email Id *</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="email" id="email" onblur="validateProfessionalEmail('+requirementId+')"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">';
		result = result +'<span class="hidden">Enter Project Id</span> </div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6">';
		result = result +'<label class="col-md-12" for="Project Id">Phone *</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="phone" id="phone" field-name="Phone" data-validation-optional="true"  data-validation="validate_Space validate_phone validate_length length9-20">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="col-md-12 mt5 clearfix">';            
		result = result +'<div class="pull-right">';
		result = result +'<input type="submit" value="Refer" id="submit_refer"  class="btn btn-primary">';
		result = result +'<input type="button" value="Cancel" onclick="cancelReferPopUp()" class="btn btn-default ml10 canBtn">';
		result = result +'</div>';    
		result = result +'</div>'; 
		result = result +'<div class="clearfix"></div>'; 
		result = result + '</form>';
		
		$("#refertoJobDiv").empty(); 
	    $("#refertoJobDiv").append(result); 
}
</script>



<script type="text/javascript">
$('body').on('submit', '#referToJob_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
     
      var frm = $('#referToJob_form').serializeFormJSON();
      var con = JSON.stringify(frm);
      con = con.replace(/[{}]/g, "");
      
      //It will escape all the special characters
      var jsonData = encodeURIComponent(con);
      var sourceleadUrl ='${sourceleadUrl}';
      
        $("#submit_refer").attr("disabled", "disabled");
		$( "#submit_refer" ).addClass('button').val('Processing..');
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	      $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/submitReferToJob?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(result) {
		        	cancelReferPopUp();
		        },
		    });  
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#referToJob_form', function() {
		$("#referToJob_form").showHelpOnFocus();
		$("#referToJob_form").validateOnBlur(false, validationSettings);
	});
  </script>


<script>	
function appendReferToJob(requirementId){
		$("#refer").toggleClass("referWrapActive");
		
}
</script> 

<script>
function cancelReferPopUp(){
	$("#refer").removeClass("referWrapActive");
	$("#refertoJobDiv").empty(); 
	
}
</script>
<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/office-image.jpg');
	}
</script>
<jsp:include page="applyToJob.jsp" />
<jsp:include page="../masterFooter.jsp" />