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
		    		rateType = json.rateType;
		    	}else{
		    		rateType = "";
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
				 result = result +'                  <div class="imageParent"> <img onerror="predefineProfileImage(this);" src="<c:url value="'+sourceleadUrl+'/displayOrgImage/'+json.orgcode+'"/>" alt="Company" title="Company Logo" class="img-responsive"> </div>';
				 result = result +'                 </div>';
				 result = result +'              </div>';
				 result = result +'              <div class="col-md-9">';
				 result = result +'                <div class="canDetails clearfix">';
				 result = result +'                  <div class="row">';
				 result = result +'                    <div class="col-md-12">';
				 result = result +'                      <ul class="list-unstyled can-perdt">';
				 result = result +'                        <li><i class="fa fa-black-tie"></i><span>Min Experience - '+minExp+'</span></li>';
				 result = result +'                        <li><i class="fa fa-black-tie"></i><span>Max Experience - '+maxExp+'</span></li>';
				 result = result +'                        <li><i class="fa fa-rupee"></i><span>'+rate+' '+rateType+'</span></li>';
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
				 result = result +'         <a href="javascript:void(0)" class="jobApply">Apply</a>';
				 result = result +'         <a class="refer" href="javascript:void(0)">Refer</a>';
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
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>
<jsp:include page="../masterFooter.jsp" />