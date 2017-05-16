<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="resources/js/formvalidator.js"></script>

<script type="text/javascript">
var postdatavalues = { title: "", link: "", picture: "", description: "", comment: "" };
function setPostValues(title,link,picture,description,comment){
	 if (title) {
         postdatavalues.title = title;
     } 
     if (link) {
         postdatavalues.link = link;
     }
     if (picture) {
         postdatavalues.picture = picture;
     }
     if (description) {
         postdatavalues.description = description;
     }
     if (description) {
         postdatavalues.comment = description;
     }
}
function getPostValueDetails(){
	 return postdatavalues;
  }

</script>
<script>
 function socialPostSucess(service,status){
	 msgFadein();
     $("#msgDiv").html(" Job is Successfully Posted on Your "+service+" Wall.");
 }
</script>
<script>
	function msgFadein() {
		$("#selector").fadeIn(200);
        $('#selector').delay(2000).fadeOut('slow');
	}
</script> 
<script type="text/javascript">
window.onload = function () {
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
		document.getElementById('requirementType').value = 'OpenJobs';
		document.getElementById('openjobs').checked = true;
		document.getElementById('openActive').className = 'activeBackground'
	}else{
		document.getElementById('requirementType').value = 'CrowdSourcingJobs';
		document.getElementById('crowdSourcingjobs').checked = true;
		document.getElementById('crowd').className = 'ml'
		document.getElementById('crowdActive').className = 'activeBackground'
	}
	requirementsList(0);
	
 };
</script>

<section class="content-wraper openJobsWrap">
    <section class="content-header clearfix">
      <div class="pull-left mt20">
        <h2>Requirements<span class="select-fild"> </h2>  
        <div class="clearfix"></div>    
          </div>
          <div class="clearfix"></div> 
         <div class="reportsCount req_Crowd clearfix">
          <ul>
          <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
          <li><label class="mr"><span><i class="fa fa-folder-open-o"></i></span><b id="openActive"><input class="myCheckbox valid" type="radio" name="CSRequirementType" id="openjobs" onchange="selectRequirementType('OpenJobs');" value="OpenJobs">&nbsp;Open</label></b></li>
          </c:if>
          <li id="crowd"><label class="mr"><span><i class="fa fa-mixcloud"></i></span><b id="crowdActive"><input class="myCheckbox valid" type="radio" name="CSRequirementType" id="crowdSourcingjobs" onchange="selectRequirementType('CrowdSourcingJobs');" value="CrowdSourcingJobs">&nbsp;Crowdsourcing</label></b></li>
          <li><label class="mr"><span><i class="fa fa-handshake-o"></i></span><b id="referByMeActive"><input class="myCheckbox valid" type="radio" name="CSRequirementType" id="referedByMejobs" onchange="selectRequirementType('ReferredByMe');" value="ReferredByMe">&nbsp;Referred By Me</label></b></li>
       	  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
       	  <li><label class="mr"><span><i class="fa fa-handshake-o"></i></span><b id="referToMeActive"><input class="myCheckbox valid" type="radio" name="CSRequirementType" id="referedToMejobs" onchange="selectRequirementType('ReferredToMe');" value="ReferredToMe">&nbsp;Referred To Me</label></b></li>
          <li><label class="mr"><span><i class="fa fa-send-o"></i></span><b id="appliedActive"><input class="myCheckbox valid" type="radio" name="CSRequirementType" id="appliedjobs" onchange="selectRequirementType('AppliedJobs');" value="AppliedJobs">&nbsp;Applied</label></b></li>
          </c:if>
          <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
          <li><label class="mr"><span><i class="fa fa-send-o"></i></span><b id="submittedActive"><input class="myCheckbox valid" type="radio" name="CSRequirementType" id="submittedjobs" onchange="selectRequirementType('AppliedJobs');" value="AppliedJobs">&nbsp;Submitted</label></b></li>
          </c:if>
          </ul>
          <input type="hidden" name="requirementType" id="requirementType" value="" />
          </span>
          </div> 
		  <div class="clearfix"></div>            
        <div class="success_pop" id="selector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
         </div>
    </section>
    
    <!-- Project -->
 
	  <section class="openJobsHead">
	  
		  <span>
			  <i class="fa fa-search"></i><input type="text" placeholder="Search" id="jobTitleSearch" name="jobTitleSearch" class="searchJob">
		  </span>
		  <span>
			  <i class="fa fa-map-marker"></i><input type="text" placeholder="Location" id="jobLocationSearch" name="jobLocationSearch" class="searchJob searchJobLo">
		  </span>
		  <span>
			  <input type="button" value="Search" class="jobBtn" onclick="requirementsList('0');">
		  </span>	
		  
		  <div class="cand-advsrch">
            <a href="javascript:void(0)" class=" AdSer" id="advancedSearch" style="display: block;" title="Advanced Search" onClick="openPop()"> Advanced Search</a>
          
          <form:form id="professional_requirement_search_form" name="professional_requirement_search_form"  enctype='multipart/form-data' >
          <div class="cand-advsrch-popup"> <a href="javascript:void(0)" class="close-div" onClick="closePop();loadChosenRequirementScripts();"><i class="fa fa-close"></i></a>
            <div class="row">
              <div class="col-md-4">
                <label>Any of Keyword <i class="fa fa-info-circle" title="Any of Keyword"></i></label>
                
                <div class="clearfix">
				 <select class="chosen-select form-control input-sm" multiple data-placeholder="Any Keyword" id="anyKeyWord" name="anyKeyWord"></select>
				 </div>
              </div>
              <div class="col-md-4">
                <label>All Keywords <i class="fa fa-info-circle" title="All Keywords"></i></label>
                <div class="clearfix">
				 <select class="chosen-select form-control input-sm" multiple data-placeholder="All KeyWord" id="allReqKeyWord" name="allKeyWord"></select>
				 </div>
                
              </div>
              <div class="col-md-4">
                <label>Exclude Keyword <i class="fa fa-info-circle" title="Exclude Keyword"></i></label>
                <div class="clearfix">
				 <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude KeyWord" id="excludeReqKeyWord" name="excludeKeyWord"></select>
				 </div>
                
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <h4>Details:</h4>
              </div>
              <div class="clearfix">
                <div class="col-md-4">
                  <label>Requirement Title</label>
                  <div class="clearfix">
                    <input type="text" id="requirementTitle" name="requirementTitle" class="form-control input-sm" placeholder="Requirement Title">
                  </div>
                </div>
                <div class="col-md-8">
                  <div class="row">
                    <div class="col-md-6">
                      <label>Rate</label>
                      <div class="input-group">
                        <div class="input-group-btn selp-custom-min custommin">
                          <select class="selectpicker" id="currencyCode" name="currencyCode" >
                            <option selected disabled>INR</option>
                            <option>AUD</option>
                            <option>USD</option>
                            <option>GPB</option>
                            <option>SGD</option>
                            <option>EUR</option>
                          </select>
                        </div>
                        <input type="text" class="form-control input-sm" name="minRate" id="minRate" value="" placeholder="Min Rate">
                      </div>
                    </div>
                    <div class="col-md-6">
                      <label>&nbsp;</label>
                      <div class="input-group">
                        <input type="text" class="form-control input-sm" value="" name="maxRate" id="maxRate" placeholder="Max Rate">
                        <div class="input-group-btn selp-custom-min custommin">
                          <select class="selectpicker">
                            <option selected disabled>Per</option>
                            <option>Hourly</option>
                            <option>Weekly</option>
                            <option>Bi-weekly</option>
                            <option>Semi-Monthly</option>
                            <option>Monthly</option>
                            <option>Bi-Monthly</option>
                            <option>Yearly</option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="clearfix">
                <div class="col-md-4">
                  <label>Experience</label>
                  <div class="clearfix row">
                    <div class="col-md-6">
                      <select class="chosen-select" id="minExp" name="minExp"  data-placeholder="Min Years" onblur="minMaxExperienceOfProfessional();">
                       <option value="" selected>Min Years</option>
                       <c:forEach begin="0" end="24" varStatus="loop">
									    <option value="${loop.index}">${loop.index} Years</option>
									</c:forEach>
                      </select>
                    </div>
                     
                    <div class="col-md-6">
                       <select class="chosen-select" id="maxExp" name="maxExp" data-placeholder="Max Years" onblur="minMaxExperienceOfProfessional();">
                         <option value="" selected>Max Years</option>
                         <c:forEach begin="0" end="24" varStatus="loop">
									    <option value="${loop.index}">${loop.index} Years</option>
									</c:forEach>
                      </select>
                    </div>
                  </div>
                  <div class="row" id="experienceId">
		  <input type="hidden" name="expValidationId" id="expValidationId">
		 </div>
                </div>
                
                <div class="col-md-4">
                  <label>Notice Period</label>
                  <div class="clearfix">
                    <select class="chosen-select" name="noticePeriod" id="noticePeriod" data-placeholder="Notice Period" multiple>
                      <option>Immediately available</option>
                      <option>15 Days</option>
                      <option>1 Month</option>
                      <option>2 Months</option>
                      <option>3 Months</option>
                    </select>
                  </div>
                </div>
                
              </div>
              <div class="clearfix">
                <div class="col-md-4">
							  <label>Country</label>
							  <div class="clearfix adv-srh-chosen">
								 <select class="chosen-select" name="country" id="country" field-name="Country"  >
								     <c:forEach var="cntry" items="${countryList}">
							                     <option value="${cntry.name}" ${cntry.name  == loginCountryName ? 'selected' : ''}>${cntry.name}</option>
						                </c:forEach>
				                 </select>
							  </div>
						  </div>
                <div class="col-md-4"> 
							  <label>State</label>
							  <div class="clearfix adv-srh-chosen">
								  <select  class="chosen-select" name="state" id="state" field-name="State"  >
								        <option value="">---Select State---</option>
								        <c:forEach var="state" items="${statesList}">
							                     <option value="${state.stateName}">${state.stateName}</option>
						                </c:forEach>
                                 </select>
							  </div>
						  </div>
			  <div class="col-md-4">
				  <label>City</label>
				  <div class="clearfix adv-srh-chosen">
					  <select  class="chosen-select" name="city" id="city" field-name="City"  >
					      <option value="">---Select City---</option>
                               </select>
				  </div>
			  </div>
              </div>
              
              <div class="clearfix">
                <div class="col-md-4">
                <label>Organizations <i class="fa fa-info-circle" title="Organizations"></i></label>
                <div class="clearfix">
				 <select class="chosen-select form-control input-sm" multiple data-placeholder="Organizations" id="organizations" name="organizations"></select>
				 </div>
                
              </div>
              
                <div class="col-md-8">
                  <label>Skills</label>
                  <div class="clearfix">
                     <select class="chosen-select"  id="skills" name="skills" multiple data-placeholder="Select Skills">
                      <c:forEach var="skill" items="${skillsList}">
             				 <c:if test="${fn:contains(requirement.reqSkillsListString, skill)}">
	                		  	 <option  selected value="${skill.name}">${skill.name}</option>
	                		 </c:if>
	                		 <c:if test="${!fn:contains(requirement.reqSkillsListString, skill)}">
	                		  	 <option  value="${skill.name}">${skill.name}</option>
	                		 </c:if>
                    </c:forEach>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="row form-footer">
              <div class="pull-right mr"> 
              <input type="button" value="Search" id="search_requiremet" onclick="searchRequirement('0');" class="btn btn-sm btn-primary"/>
               <input type="button" value="Cancel" onClick="closePop();" class="btn btn-sm btn-default ml10"/> 
              </div>
            </div>
          </div>
          
          </form:form>
        </div> 
		  	  		  	  
	  
			<div class="timeNewSe" style="float:right;">
                <select class="form-control chosen-select"  id="postedOn" name="postedOn" onchange="requirementsList('0');">
                  <option value="Today">Today</option>
                  <option value="1 Day Ago">1 day ago</option>
                  <option value="3 Days Ago">3 day ago</option>
                  <option value="1 Week Ago">1 week ago</option>
                  <option selected="selected" value="2 Weeks Ago">2 weeks ago</option>
                  <option value="1 Month Ago">1 month ago</option>
                   <option value="3 Months Ago">3 Months Ago</option>
                </select>
            </div>    
            
		  <div class="clearfix"></div>
	  </section>
	   <br/>
	   <input type="hidden" name="requirementOffset" id="requirementOffset" value="0"/>
	    <input type="hidden" name="requirementSearchOffset" id="requirementSearchOffset" value="0"/>
	    <div id="requirementsListLoading"></div>  
	  <div id="requirementsList"></div>
	  <a href="#" id="loadMoreSection" style="display: none;"><section class="loadJob" onclick="getViewMoreRecords();"><i id="viewMoreLoading"></i><span id="viewMore">View more</span></section></a>
	   <a href="#" id="noMoreRecordsSection" style="display: none;"><section class="loadJob"><span id="viewMore">No More Records</span></section></a>
	  <a href="#" id="loadMoreSearchSection" style="display: none;"><section class="loadJob" onclick="getViewMoreSearchRecords();"><i id="viewMoreSearchLoading"></i><span id="viewSearchMore">View more</span></section></a>
	  
	 <div class="side_mask"><div class="proj_close"></div></div>
	 <div class="new_side_wrap">
	 <div class="new_side_wrap_inn">
		<div class="new_side_wrap_cta">
			<div id="applytoJobDiv"></div>
			<div id="submitDetailsDiv"></div>
		</div></div>
	 </div> 
	 
            
	  </section>

<jsp:include page="../masterFooter.jsp" />
<jsp:include page="applyToJob.jsp" />

<div class="backtotop" style="display: none;"><img src="resources/images/backtotop.png"></div>  


<script>
//Function to validate if maxExpYears  value is more the minExpYears  value
function minMaxExperienceOfProfessional(){
	var maxExpYears = ($('#maxExp').val());
     var minExpYears = ($('#minExp').val());
    
    if (parseFloat(maxExpYears) <= parseFloat(minExpYears)){
        $('#experienceId').find('p.jquery_form_error_message').remove(); 
		$('input#expValidationId').attr('class','error signin_fild');
		$("input#expValidationId").after("<p class='jquery_form_error_message'>Max Experience is greater than Min Experience</p>");
		$('input#expValidationId').attr('record-exist','yes');
		$('input#expValidationId').attr('record-exist-errorMsg','Max Experience is greater than Min Experience'); 
    }  else{
    	$('#experienceId').find('p.jquery_form_error_message').remove(); 
    	$('input#expValidationId').removeAttr( "class record-exist record-exist-errormsg");
		$('#experienceId').find('p.jquery_form_error_message').remove();
    } 
}

</script>

<script type="text/javascript">
    	   var config = {
    				  '.chosen-select'           : {},
    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
    				  '.chosen-select-no-single' : {disable_search_threshold:10},
    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    				  '.chosen-select-width'     : {width:"95%"}
    	   }
			//Adding Customized Any Of Keyword to the list 
		    var select, chosen;
		    select = $("#anyKeyWord");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keyup', function(e)
		    {
		    	var anyofKeyword = this.value.replace(",","");
		        if ((e.which == 13 || e.which == 188) && (anyofKeyword != null  && anyofKeyword != '' && anyofKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(anyofKeyword).text(anyofKeyword);
		            select.append(option);
		            select.find(option).prop('selected', true);
                    $("#anyKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#anyKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		   
		    var selectone, chosenone;
		    selectone = $("#allReqKeyWord");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keyup', function(e1)
		    {
		    	var allofKeyWord = this.value.replace(",","");
		        if ((e1.which == 13 || e1.which == 188) && (allofKeyWord != null  && allofKeyWord != '' && allofKeyWord != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(allofKeyWord).text(allofKeyWord);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
                    $("#allReqKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#allReqKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selecttwo, chosentwo;
		    selecttwo = $("#excludeReqKeyWord");
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosentwo = selecttwo.data('chosen');
		    chosentwo.search_field.on('keyup', function(e2)
		    {
		    	var excludeKeyWord = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (excludeKeyWord != null  && excludeKeyWord != '' && excludeKeyWord != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(excludeKeyWord).text(excludeKeyWord);
		            selecttwo.append(option);
		            selecttwo.find(option).prop('selected', true);
                    $("#excludeReqKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#excludeReqKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    var selectthree, chosenthree;
		    selectthree = $("#organizations");
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keyup', function(e3)
		    {
		    	var organizations = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (organizations != null  && organizations != '' && organizations != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(organizations).text(organizations);
		            selectthree.append(option);
		            selectthree.find(option).prop('selected', true);
                    $("#organizations option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#organizations").trigger("chosen:updated");
	        		
		        }
		    });
		    
		 // here call experience validation
		       $("#maxExp").on('change', function (e) {
		    	   minMaxExperienceOfProfessional();
		       });
		       
		       $("#minExp").on('change', function (e) {
		    	   minMaxExperienceOfProfessional();
		       });
		       
		      $("#country").on('change', function (e) {
		    	   getStates();
		       });
		       $("#state").on('change', function (e) {
		    	   getCities();
		       });
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
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
    	$('#city').append('<option value="">---select city---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>



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
function getViewMoreSearchRecords(){
	 offset = $("#requirementSearchOffset").val();
	 offset = parseInt(offset)+parseInt(10);
	 $("#requirementSearchOffset").val(offset);
	 searchRequirement(offset);
	 
	 document.getElementById("viewMoreSearchLoading").className = 'fa fa-spinner fa-spin'
	 
}
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

<script>
function requirementsList(offset){
	var sourceleadUrl = '${sourceleadUrl}';
	var requirementType = document.getElementById('requirementType').value;
	var postedOn = document.getElementById('postedOn').value;
	var jobTitleSearch = document.getElementById('jobTitleSearch').value;
	var jobLocationSearch = document.getElementById('jobLocationSearch').value;
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	var candidateId = '${SourceLeadWeb_candidateId}'
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	
    loadChosenRequirementScripts();
	
	
	if(offset == 0){
		$("#requirementsList").empty();
		$("#requirementOffset").val(offset)
		$('#viewMore').html("View More");
	}
	
   if(requirementType == "OpenJobs" || requirementType == "CrowdSourcingJobs"){
		document.getElementById("advancedSearch").style.display="";
	}else{
		document.getElementById("advancedSearch").style.display="none";
	}
	
	if(requirementType == 'ReferredToMe' || requirementType == 'ReferredByMe'){
		document.getElementById("advancedSearch").style.display="none";
	}
	
	closePop();
	loadChosenRequirementScripts();
	$("#requirementsListLoading").attr('style','position:absolute; width:100%; height:30%; background-color:rgba(255,255,255,0.8); top:248px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getCrowdSourcingRequirements?${_csrf.parameterName}=${_csrf.token}&requirementType="+requirementType+"&offset="+offset+"&postedOn="+postedOn+"&jobTitleSearch="+jobTitleSearch+"&jobLocationSearch="+jobLocationSearch,
	   			
	    success: function(data) {
       	 var json = JSON.parse(data);
       	 var result = "";
	     if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 	
	   	  if(json.length > 0)	
	   		{
	 			for(var i=0; i<json.length; i++){
	 				
			 				var requirements = json[i];
			 				
			 				var title="";
							var link="";
							var picture="";
							var description='';
							var comment='';
			 				
			 				var jobType = '';
			 				var experience = '';
			 				var rate = '';
			 				var location = '';
			 				var jobId = '';
			 				
			 				if(requirements.jobType != null && requirements.jobType != '' && requirements.jobType != 'undefined'){
			 					jobType = requirements.jobType;
			 				}else{
			 					jobType = 'No Data';
			 				}
			 				
			 				if(requirements.jobId != null && requirements.jobId != '' && requirements.jobId != 'undefined'){
			 					jobId = requirements.jobId+" - "	
			 				}
			 				
			 				
			 				if(requirements.minExperience != '' && requirements.maxExperience == ''){
			 					experience = requirements.minExperience+" Years";
			 				}
			 				else if(requirements.minExperience == '' && requirements.maxExperience != ''){
			 					experience = requirements.maxExperience+" Years";
			 				}
			 				else if(requirements.minExperience != '' && requirements.maxExperience != ''){
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
			 					rate = requirements.rate+" / "+requirements.rateType+" "+requirements.currentRateCurrency;
			 				}else{
			 					rate = 'No Data';
			 				}
			 				
			 				description =  description+'Job Title:'+requirements.jobTitle+";";
			 				description =  description+'Posted By :'+requirements.postedOrg+";";
			 				link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+requirements.requirementUUID;
			 				result = '<section class="openCta">';
			 				result = result +'<div class="OpenJob">';
			 				result = result +'<a href="<c:url value="/requirementProfile/'+requirements.requirementUUID+'"/>"><h3>'+jobId+' '+requirements.jobTitle+'</h3></a>';
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
			 					description =  description+'Skills :'+requirements.skillsList+";";
			 					for(var j=0; j<requirements.skillsList.length; j++){
				 					result = result +'<li>'+requirements.skillsList[j]+'</li>';
				 				}
			 				}else{
			 					result = result +'<li>No Data</li>';
			 				}
			 				if(jobType != null &&jobType != 'No Data'){
			 					description =  description+'Job Type:'+requirements.jobType+";";
			 				}
			 				if(experience != null && experience != 'No Data'){
								description =  description+'Experience :'+experience+";";
							}
			 				if(location != null && location != 'No Data'){
			 					description =  description+'Job Location: '+location+";";
							}
			 				description =  description+'Posted :'+requirements.calDays+";";
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</ul>';
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</h5>';
			 				
			 				if(requirementType != "OpenJobs" && requirements.isCrowdSourcingJob == 'Y'){
			 				result = result +'<div class="disCount">';
			 				result = result +'<div class="disCountTop">';
			 				result = result +'<span>You will get</span>';
			 				if(requirements.rewardType == 'Percentage'){
			 					result = result +'<b>'+requirements.rewardPercentage+'%</b>';
			 				}
			 				
			 				if(requirements.rewardType == 'Flat'){
			 					result = result +'<strong>'+requirements.rewardFlatRateType+'</strong>';
			 					result = result +' <b class="inr">'+requirements.rewardFlat+'</b>';
			 				}
			 				
			 				result = result +'<span>on base salary</span>';
			 				result = result +'</div>';
			 				
			 				result = result +'<p>Referral reward guarantee period '+requirements.rewardGuaranteePeriod+' ';
			 				result = result +'</p>';
			 				result = result +'</div>';
			 				}
			 		  
			 				result = result +'</div>';
			 				
			 				
			 				if(requirementType != "AppliedJobs"){
			 					result = result +'<div class="openCtaRight">';
				 			  result = result +'<p>Share Requirement</p>';
				 				result = result +'<span>';
			 				    result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'linkedin\');"><i class="fa fa-linkedin-square"></i></a>';
								result = result +'  <a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';				  
								result = result +'  <a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
				 				result = result +'</span>'; 
				 				result = result +'<div class="openPostBtns">';
				 				
				 				if((requirementType == "OpenJobs")|| ((requirementType == "CrowdSourcingJobs" || requirementType == "ReferredToMe" || requirementType == "ReferredByMe") && requirements.isJobApplied == 'N')){
				 					
				 					if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
				 					  result = result +'<a href="javascript:void(0)" onclick="removeReferPopUp();applyToJob('+requirements.requirementId+','+requirements.referId+');"  class="jobApply">Apply</a>';
				 					}
				 					
				 					if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
					 					  result = result +'<a href="javascript:void(0)" onclick="removeReferPopUp();applyToJob('+requirements.requirementId+','+requirements.referId+');"  class="jobApply">Submit</a>';
				 					 }
				 				}
				 				
				 				if((requirementType == "CrowdSourcingJobs" || requirementType == "ReferredToMe" || requirementType == "ReferredByMe") && requirements.isJobApplied == 'Y'){
				 					
				 						result = result +'<a href="javascript:void(0)" onclick="removeReferPopUp();getSubmissionDetailsSideBar('+requirements.submissionId+','+candidateId+','+requirements.requirementId+');" class="jobApply">Applied</a>';
				 					
				 				}
				 				
				 				if(requirementType != 'OpenJobs'){
				 					if(requirementType != 'ReferredToMe'){
					 					result = result +'<a class="refer" onclick="referToJob('+requirements.requirementId+','+requirements.referId+');getUserContats('+requirements.requirementId+');" href="javascript:void(0)">Refer</a>';
					 					result = result +'<div class="referWrap" id="refer'+requirements.requirementId+'">';
						 				result = result +'<div class="referWrapInn">';
						 				result = result +'<div id="refertoJobDiv'+requirements.requirementId+'"></div>';  
						 				result = result +'</div>';
						 				result = result +'</div>';
				 					}else if(requirementType == 'ReferredToMe'){
				 						result = result +'<a class="refer" onclick="referToJob('+requirements.requirementId+','+requirements.referId+');getUserContats('+requirements.requirementId+');" href="javascript:void(0)">Refer</a>';
					 					result = result +'<div class="referWrap" id="refer'+requirements.referId+'">';
						 				result = result +'<div class="referWrapInn">';
						 				result = result +'<div id="refertoJobDiv'+requirements.referId+'"></div>';  
						 				result = result +'</div>';
						 				result = result +'</div>';
				 					}
				 				}
				 				
				 				result = result +'</div>';
				 				result = result +'<div class="openPostDate">';
				 				result = result +'Posted :';
				 				result = result +'<b><i class="fa fa-clock-o"></i> '+requirements.calDays+'</b>';
				 				result = result +'</div>';
				 				if(requirementType == 'ReferredToMe'){
				 					result = result +'<div class="applShare Refer">';
					 				result = result +'Referred by : '+requirements.referedUser+'';
					 				result = result +'</div>';
				 				}
				 				
				 				result = result +'</div>';
			 				}
			 				else if(requirementType == "AppliedJobs"){
			 					
			 					result = result +'<div class="openCtaRight text-center">';
			 					result = result +'<div class="appliedWrap">';
			 					
			 					if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
			 						result = result +'<a href="javascript:void(0)" onclick="removeReferPopUp();getSubmissionDetailsSideBar('+requirements.submissionId+','+candidateId+','+requirements.requirementId+');" class="jobApply"><i class="fa fa-check-circle"></i></a>';
			 					}else{
			 						result = result +'<i class="fa fa-check-circle"></i>';
			 					}
			 					
			 					result = result +'<div class="openPostDate">';
			 					
			 					if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
			 						result = result +'<i class="fa fa-clock-o"></i> Applied on : <b>'+requirements.requirementAppliedDate+'</b>';
			 					}else{
			 						result = result +'<i class="fa fa-user-circle"></i> Submissions Count : <b>'+requirements.recruiterSubmissionsCount+'</b>';
			 					}
			 					
			 					result = result +'</div>';
			 					if(requirements.referedUser != '' && requirements.referedUser != null && requirements.referedUser != 'undefined'){
				 					result = result +'<div class="applShare Refer">';
					 				result = result +'Referred by : '+requirements.referedUser+'';
					 				result = result +'</div>';
				 				}
			 					result = result +'</div>';
			 					result = result +'</div>';
			 				  
			 				}
			 				result = result +'<div class="clearfix"></div>';
			 				result = result +'</section>';
			 				
			       	    	$('#requirementsList').append(result);
			       	    	document.getElementById("viewMoreLoading").removeAttribute("class");
			       	    	
			       	    	if(requirements.noOfSearchHits >= 10){
			       	    		document.getElementById("loadMoreSection").style.display="";
			       	    		document.getElementById("noMoreRecordsSection").style.display="none";
			       	    	}else{
			       	    		document.getElementById("loadMoreSection").style.display="none";
			       	    		document.getElementById("noMoreRecordsSection").style.display="";
			       	    	}
			       	    	
			       	    	document.getElementById("loadMoreSearchSection").style.display="none";
		            		
				   }
	 			 socialInviterScripts();
	   		    }
          }else if(offset == 0){
	   		     var result ="";
	    	     result = result+"   <div class='dataTables_empty'>"; 
				 result = result+"  </div> "; 
				$("#requirementsList").empty();
			    $("#requirementsList").append(result); 
			    document.getElementById("loadMoreSection").style.display="none";
			    document.getElementById("loadMoreSearchSection").style.display="none";
			    document.getElementById("noMoreRecordsSection").style.display="none";
   		    }else{
   		    	
   		    	document.getElementById("viewMoreLoading").removeAttribute("class");
   		    	document.getElementById("loadMoreSection").style.display="none";
   		    	document.getElementById("noMoreRecordsSection").style.display="";
   		    	
   		    }
	     
	     $("#requirementsListLoading").removeAttr("style"); 
   	
		 },
       error: function(e){
       }
	 }); 
}
</script>


<script>
function searchRequirement(offset){
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
 if ($('#professional_requirement_search_form').validate(false, validationSettings)){  
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#professional_requirement_search_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#professional_requirement_search_form')[0];
   		   var formData = new FormData(form); 
			//It will escape all the special characters
			$("#search_requiremet").attr("disabled", "disabled");
			$("#search_requiremet").addClass('button').val('Processing..');
			var candidateId = '${SourceLeadWeb_candidateId}'
			
			var requirementType = document.getElementById('requirementType').value;
			var loginUserRole = '${SourceLeadWeb_loginUserRole}';
			$("#requirementsListLoading").attr('style','position:absolute; width:100%; height:30%; background-color:rgba(255,255,255,0.8); top:248px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			
			if(offset == 0){
				$("#requirementsList").empty();
				$("#requirementSearchOffset").val(offset)
				$('#viewSearchMore').html("View More");
			}

			$.ajax({
						type : "GET",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/searchCrowdSourcingRequiremets?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&requirementType="+requirementType+"&offset="+offset,
						success : function(data) {
							$("#search_requiremet").addClass('button').val('Search');
							$("#search_requiremet").removeAttr("disabled");
							 closePop();
							 var json = JSON.parse(data);
					       	 var result = ""
					       	 
							 if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){
							   	  if(json.length > 0)	
							   		{
							 			for(var i=0; i<json.length; i++){
							 				
									 				var requirements = json[i];
									 				var title="";
													var link="";
													var picture="";
													var description='';
													var comment='';
									 				
									 				var jobType = '';
									 				var experience = '';
									 				var rate = '';
									 				var location = '';
									 				
									 				var jobId = '';
									 				description =  description+'Job Title:'+requirements.jobTitle +";";
									 				description =  description+' Posted By :'+requirements.postedOrg+";";
									 				if(requirements.jobType != null && requirements.jobType != '' && requirements.jobType != 'undefined'){
									 					jobType = requirements.jobType;
									 				}else{
									 					jobType = 'No Data';
									 				}
									 				
									 				if(requirements.jobId != null && requirements.jobId != '' && requirements.jobId != 'undefined'){
									 					jobId = requirements.jobId+" - "	
									 				}
									 				
									 				
									 				if(requirements.minExperience != 'undefined' && requirements.maxExperience == 'undefined'){
									 					experience = requirements.minExperience+" Years";
									 				}
									 				else if(requirements.minExperience == 'undefined' && requirements.maxExperience != 'undefined'){
									 					experience = requirements.maxExperience+" Years";
									 				}
									 				else if(requirements.minExperience != '' && requirements.maxExperience != ''){
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
									 					rate = requirements.rate+" / "+requirements.rateType+" "+requirements.currentRateCurrency;
									 				}else{
									 					rate = 'No Data';
									 				}
									 				link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+requirements.requirementUUID;
									 				result = '<section class="openCta">';
									 				result = result +'<div class="OpenJob">';
									 				result = result +'<a href="<c:url value="/requirementProfile/'+requirements.requirementUUID+'"/>"><h3>'+jobId+' '+requirements.jobTitle+'</h3></a>';
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
									 					description =  description+'Skills :'+requirements.skillsList+";";
									 					for(var j=0; j<requirements.skillsList.length; j++){
										 					result = result +'<li>'+requirements.skillsList[j]+'</li>';
										 				}
									 				}else{
									 					result = result +'<li>No Data</li>';
									 				}
									 				if(jobType != null &&jobType != 'No Data'){
									 					description =  description+'Job Type:'+requirements.jobType+";";
									 				}
									 				if(experience != null && experience != 'No Data'){
														description =  description+'Experience :'+experience+";";
													}
									 				if(location != null && location != 'No Data'){
									 					description =  description+'Job Location: '+location+";";
													}
									 				description =  description+'Posted :'+requirements.calDays+";";
									 				result = result +'<div class="clearfix"></div>';
									 				result = result +'</ul>';
									 				result = result +'<div class="clearfix"></div>';
									 				result = result +'</h5>';
									 				
									 				if(requirementType != "OpenJobs" && requirements.isCrowdSourcingJob == 'Y'){
										 				result = result +'<div class="disCount">';
										 				result = result +'<div class="disCountTop">';
										 				result = result +'<span>You will get</span>';
										 				if(requirements.rewardType == 'Percentage'){
										 					result = result +'<b>'+requirements.rewardPercentage+'%</b>';
										 				}
										 				
										 				if(requirements.rewardType == 'Flat'){
										 					result = result +'<strong>'+requirements.rewardFlatRateType+'</strong>';
										 					result = result +' <b class="inr">'+requirements.rewardFlat+'</b>';
										 				}
										 				
										 				result = result +'<span>on base salary</span>';
										 				result = result +'</div>';
										 				
										 				result = result +'<p>Referral reward guarantee period '+requirements.rewardGuaranteePeriod+' ';
										 				result = result +'</p>';
										 				result = result +'</div>';
										 				}
									 		  
									 				result = result +'</div>';
									 				
									 				
									 				if(requirementType != "AppliedJobs"){
									 					result = result +'<div class="openCtaRight">';
										 				 result = result +'<p>Share Requirement</p>';
										 				 result = result +'<span>';
									 				    result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'linkedin\');"><i class="fa fa-linkedin-square"></i></a>';
														result = result +'  <a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';				  
														result = result +'  <a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
										 				result = result +'</span>'; 
										 				result = result +'<div class="openPostBtns">';
										 				
										 				if((requirementType == "OpenJobs") || (requirementType == "CrowdSourcingJobs" && requirements.isJobApplied == 'N')){
										 					if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
										 					result = result +'<a href="javascript:void(0)" onclick="removeReferPopUp();applyToJob('+requirements.requirementId+');"  class="jobApply">Apply</a>';
										 					}
										 					
										 					if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
											 					result = result +'<a href="javascript:void(0)" onclick="removeReferPopUp();applyToJob('+requirements.requirementId+');"  class="jobApply">Submit</a>';
										 					}
										 				}
										 				
										 				if(requirementType == "CrowdSourcingJobs" && requirements.isJobApplied == 'Y'){
										 					result = result +'<a href="javascript:void(0)" onclick="removeReferPopUp();getSubmissionDetailsSideBar('+requirements.submissionId+','+candidateId+','+requirements.requirementId+');" class="jobApply">Applied</a>';
										 				}
										 				
										 				
										 				if(requirementType == "CrowdSourcingJobs"){
										 					result = result +'<a class="refer" onclick="referToJob('+requirements.requirementId+');getUserContats('+requirements.requirementId+');" href="javascript:void(0)">Refer</a>';
										 					result = result +'<div class="referWrap" id="refer'+requirements.requirementId+'">';
											 				result = result +'<div class="referWrapInn">';
											 				result = result +'<div id="refertoJobDiv'+requirements.requirementId+'"></div>';  
											 				result = result +'</div>';
											 				result = result +'</div>';
										 				}
										 				
										 				result = result +'</div>';
										 				result = result +'<div class="openPostDate">';
										 				result = result +'Posted :';
										 				result = result +'<b><i class="fa fa-clock-o"></i> '+requirements.calDays+'</b>';
										 				result = result +'</div>';			  
										 				result = result +'</div>';
									 				}
									 				
									 				result = result +'<div class="clearfix"></div>';
									 				result = result +'</section>';
									       	     $('#requirementsList').append(result);
									       	     document.getElementById("loadMoreSection").style.display="none";
									       	     
									       	    if(requirements.noOfSearchHits >= 10){
								       	    		document.getElementById("loadMoreSearchSection").style.display="";
								       	    		document.getElementById("noMoreRecordsSection").style.display="none";
								       	    	}else{
								       	    		document.getElementById("loadMoreSearchSection").style.display="none";
								       	    		document.getElementById("noMoreRecordsSection").style.display="";
								       	    	}
									       	  
									       	     document.getElementById("viewMoreSearchLoading").removeAttribute("class");
								            		
										   }
							   		    }
						          }else if(offset == 0){
				                	 var result ="";
				    	    	     result = result+"   <div class='dataTables_empty'>"; 
				    				 result = result+"  </div> "; 
				    				$("#requirementsList").empty();
				    			    $("#requirementsList").append(result); 
				    			    document.getElementById("loadMoreSection").style.display="none";
				    			    document.getElementById("loadMoreSearchSection").style.display="none";
				    			    document.getElementById("noMoreRecordsSection").style.display="";
		               		 }else{
		           		    	document.getElementById("viewMoreSearchLoading").removeAttribute("class");
		           		    	document.getElementById("noMoreRecordsSection").style.display="none";
		           		    	document.getElementById("noMoreRecordsSection").style.display="none";
		               		 }
					       	$("#requirementsListLoading").removeAttr("style"); 
					        socialInviterScripts();
						},
					});  
			return true;
		}else{ 
				return false;
		 }
	} 
$('body').on('blur', '#professional_requirement_search_form', function() {
	$("#professional_requirement_search_form").showHelpOnFocus();
	$("#professional_requirement_search_form").validateOnBlur(false, validationSettings);
});
</script>


<script>
function referToJob(requirementId,referId){
	   appendReferToJob(requirementId,referId); 
	   removeReferPopUp();
	    
	   var result = ''
	   
	    result = result +'<div class="clearfix refercontacts">';
	    result = result + '<ul class="nav nav-tabs">';
	    result = result + '<li class="active"><a data-toggle="tab" href="#contacts" onclick="getUserContats('+requirementId+')">Contacts</a></li>';
	    result = result + '<li><a data-toggle="tab" href="#addnew" onclick="flushContactDetails();">Refer New</a></li>';
	    result = result + '</ul>';
	    result = result +'</div>';
	    
	    
	    result = result +'<div class="tab-content refercontacts_content">';
	    result = result +'<div id="loading"></div>';
	    result = result +'<div id="contacts" class="tab-pane fade in active">';
	    result = result + '<form id="referToContacts" name="referToContacts" method="post">';
	    result = result +'<input type="hidden" name="contactReferRequirementId" id="contactReferRequirementId" value='+requirementId+' >';
	    result = result +'<div id="contactsDiv">';
	    result = result +' <select  multiple data-placeholder="Select Contacts" onchange="getContactsCount()" class="chosen-select form-control" data-validation="required" field-name="Contacts"  name="userContacts" id="userContacts" data-placeholder="Select Concatts" multiple >';
	    result = result +'</select>';
	    result = result +'</div>';
	    result = result + '<div class="pull-left mt">';
	    result = result +'<span style="font-size:13px;">No Of Contacts Selected: <b id="contactsCount">0</b>&nbsp;&nbsp;&nbsp;</span>';
	    result = result +'</div>';
	    result = result +'<div class="pull-right">';
		result = result +'<input type="submit" value="Refer" id="submit_contacts"  class="btn btn-primary">';
		result = result +'<input type="button" value="Cancel" onclick="cancelReferPopUp('+requirementId+','+referId+')" class="btn btn-default ml10 canBtn">';
		result = result +'</div>';  
	    result = result + '</form>';
	    result = result +'</div>';
	    result = result +'<div class="clearfix"></div>';  
	    
	    result = result +'<div id="addnew" class="tab-pane fade">';
	    result = result + '<form id="referToJob_form" name="referToJob_form" method="post">';
	    result = result +'<div class="row">';
	    result = result +'<div class="form-group col-md-6">';
		result = result +'<input type="hidden" name="referRequirementId" id="referRequirementId" value='+requirementId+' >';
		
		if(referId != null && referId != 'undefined' && referId != ''){
			result = result +'<input type="hidden" name="referId" id="referId" value='+referId+' >';
		}
		
		result = result +'<label class="col-md-12" for="Project Id">First Name *</label>';
		result = result +'<div class="col-md-12" id="referFirstName">';
		result = result +'<input type="text" class="form-control" name="firstName" id="firstName" field-name="First Name" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6" id="referLastName">';
		result = result +'<label class="col-md-12" for="Project Id">Last Name</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="lastName" id="lastName" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6" id="emailDiv">';
		result = result +'<label class="col-md-12" for="Project Id">Email Id *</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="email" id="email" onblur="validateProfessionalEmail('+requirementId+')"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">';
		result = result +'<span class="hidden">Enter Project Id</span> </div>';
		result = result +'</div>'; 
		result = result +'<div class="form-group col-md-6" id="referPhone">';
		result = result +'<label class="col-md-12" for="Project Id">Phone</label>';
		result = result +'<div class="col-md-12">';
		result = result +'<input type="text" class="form-control" name="phone" id="phone" field-name="Phone" data-validation-optional="true" data-validation="validate_Space validate_phone validate_length length9-20">';
		result = result +'</div>';
		result = result +'</div>'; 
		result = result +'<div class="col-md-12 mt5 clearfix">';            
		result = result +'<div class="pull-right">';
		result = result +'<input type="submit" value="Refer" id="submit_refer"  class="btn btn-primary">';
		result = result +'<input type="button" value="Cancel" onclick="cancelReferPopUp('+requirementId+','+referId+')" class="btn btn-default ml10 canBtn">';
		result = result +'</div>';    
		result = result +'</div>'; 
		result = result +'</div>'; 
		result = result +'<div class="clearfix"></div>'; 
		result = result + '</form>';
		result = result +'</div>';
		result = result +'</div>';
		
		if(referId == null || referId == '' || referId =='undefined'){
			$("#refertoJobDiv"+requirementId+"").empty(); 
		    $("#refertoJobDiv"+requirementId+"").append(result); 
		}else{
			$("#refertoJobDiv"+referId+"").empty(); 
		    $("#refertoJobDiv"+referId+"").append(result); 
		}
		
}
</script>



<script>
function getUserContats(requirementId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getCrowdSourcingUserContacts?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,  
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.contactUserName+" ("+opt.contactUserEmail+")</option>";
		        		
				}
	        	
	        	
	        	$("#userContacts").empty();
	        	$("#userContacts").append(output);
	        	
	        	$("#userContacts").chosen();
	    		$("#userContacts").trigger("chosen:updated");
		       
	        },
	    });
}
</script>


<script>
function flushContactDetails(){
	$("#firstName").val('');
	$("#lastName").val('');
	$("#email").val('');
	$("#phone").val('');

    $('#referFirstName').find('p.jquery_form_error_message').remove(); 
    $('#referLastName').find('p.jquery_form_error_message').remove(); 
    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
    $('#referPhone').find('p.jquery_form_error_message').remove(); 
    
    $('#contactsDiv').find('p.jquery_form_error_message').remove(); 
    $("#contactsCount").html(0);
}
</script>

<script>
function getContactsCount(){
	var contactsCount =  $('#userContacts_chosen  span').size();
	$("#contactsCount").html(contactsCount);
	$('#userContacts_chosen').next("p").remove();
	
	$('.search-choice-close').click(function() {
		var contactsCount =  $('#userContacts_chosen  span').size();
		$("#contactsCount").html(contactsCount);
	});
}
</script>


<script>	
function appendReferToJob(requirementId,referId){
	if(referId == null || referId == '' || referId =='undefined'){
		$("#refer"+requirementId+"").toggleClass("referWrapActive");
	}else{
		$("#refer"+referId+"").toggleClass("referWrapActive");
	}
		
		
}
</script> 

<script>
function cancelReferPopUp(requirementId,referId){
	$("#refer"+requirementId+"").removeClass("referWrapActive");
	$("#refertoJobDiv"+requirementId+"").empty(); 
	
	if(referId != null && referId != '' && referId !='undefined'){
		$("#refer"+referId+"").removeClass("referWrapActive");
		$("#refertoJobDiv"+referId+"").empty(); 
	}
	
	
}
</script>

 <script type="text/javascript">
		function removeReferPopUp(){
			var referRequirementId = document.getElementById('referRequirementId'); 
			var referId = document.getElementById('referId');
			
			if(referRequirementId != null) {
				$("#refertoJobDiv"+referRequirementId.value+"").empty(); 
				$("#refer"+referRequirementId.value+"").removeClass("referWrapActive"); 
			} 
			
			if(referId != null) {
				$("#refertoJobDiv"+referId.value+"").empty(); 
				$("#refer"+referId.value+"").removeClass("referWrapActive"); 
			} 
			
		 }
</script>


<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	       $("#reportingManager").chosen();
	       $("#role").chosen();
	       $("#invoiceFrequency").chosen();
	       $("#userContacts").chosen();
	       
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>


<script>
    // Close Advanced Search
    function closePop(){
    $("div.cand-advsrch-popup").hide();
    };
    
    function openPop(){
    	 var referRequirementId = document.getElementById('referRequirementId'); 
    	 if(referRequirementId != null) {
    		$("#refertoJobDiv"+referRequirementId.value+"").empty(); 
    		$("#refer"+referRequirementId.value+"").removeClass("referWrapActive"); 
    	 } 
    $("div.cand-advsrch-popup").toggle();
    };
	</script>	
	

<script>
function loadChosenRequirementScripts(){
	 $('#country').val('${loginCountryName}'); 
     $('#state').val(''); 
     $('#city').val('');
     $("#anyKeyWord").val('').trigger("chosen:updated");
	 $("#allReqKeyWord").val('').trigger("chosen:updated");
	 $("#excludeReqKeyWord").val('').trigger("chosen:updated");
	 $("#requirementTitle").val('').trigger("chosen:updated");
	 $("#rate").val('').trigger("chosen:updated");
	 $("#minExp").val('').trigger("chosen:updated");
	 $("#maxExp").val('').trigger("chosen:updated");
	 $("#skills").val('').trigger("chosen:updated"); 
	 $("#noticePeriod").val('').trigger("chosen:updated"); 
	 $("#organizations").val('').trigger("chosen:updated");
	 
	 $("#anyKeyWord").empty();
	 $("#allReqKeyWord").empty();
	 $("#excludeReqKeyWord").empty();
}
</script>


<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
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
		        	removeReferPopUp();
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
  
  
  
  
  
 <script type="text/javascript">
$('body').on('submit', '#referToContacts', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
     
      var frm = $('#referToContacts').serializeFormJSON();
      var con = JSON.stringify(frm);
      con = con.replace(/[{}]/g, "");
      
      //It will escape all the special characters
      var jsonData = encodeURIComponent(con);
      var sourceleadUrl ='${sourceleadUrl}';
      
        $("#submit_contacts").attr("disabled", "disabled");
		$( "#submit_contacts" ).addClass('button').val('Processing..');
	 	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 	
	 	  $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/submitUserContactsToRequirement?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(result) {
		        	removeReferPopUp();
		        },
		    }); 
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#referToContacts', function() {
		$("#referToContacts").showHelpOnFocus();
		$("#referToContacts").validateOnBlur(false, validationSettings);
	});
  </script>
  
  
  <script>
  function validateProfessionalEmail(requirementId){
	  
	  var sourceleadUrl ='${sourceleadUrl}';
	  var email = document.getElementById('email').value; 
	  if(email != null && email != ''){
		  $("#submit_refer").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/validateReferedEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&requirementId="+requirementId,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#email").after("<p class='jquery_form_error_message'>"+email+" is already refered</span>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',email+' is already refered'); 
					     } 
					     
					     else if(data == 'Invalid Email'){
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#email").after("<p class='jquery_form_error_message'> you can't refer user self</span>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg'," you can't refer user self"); 
					     } 
					     
				       	else{
				       		 $("#submit_refer").removeAttr("disabled");
							 $('input#email').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#submit_refer" ).removeAttr("style");
							 $( "#submit_refer" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
  }
  
  </script>
  
<script type="text/javascript">
 function selectRequirementType(type) {
    	$("#requirementType").val(type);
    	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    	
    	if(type == "OpenJobs"){
    		document.getElementById('openActive').className = 'activeBackground'
    		document.getElementById("crowdActive").removeAttribute("class");
    		document.getElementById("referByMeActive").removeAttribute("class")
    		document.getElementById("referToMeActive").removeAttribute("class")
    		document.getElementById("appliedActive").removeAttribute("class")
    	}
    	else if (type == "CrowdSourcingJobs"){
   		    document.getElementById('crowdActive').className = 'activeBackground'
       		
   		    if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
   		    	document.getElementById("openActive").removeAttribute("class");
   	       		document.getElementById("referToMeActive").removeAttribute("class")
   	       		document.getElementById("appliedActive").removeAttribute("class")
   		    }
       		
       		document.getElementById("referByMeActive").removeAttribute("class")
       		if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
       		document.getElementById("submittedActive").removeAttribute("class")
       		}
    	}
    	else if (type == "ReferredByMe"){
	        document.getElementById('referByMeActive').className = 'activeBackground'
       		document.getElementById("crowdActive").removeAttribute("class")
       		if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
       		document.getElementById("submittedActive").removeAttribute("class")
       		}
       		
       		if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
	       		document.getElementById("referToMeActive").removeAttribute("class")
	       		document.getElementById("openActive").removeAttribute("class");
	       		document.getElementById("appliedActive").removeAttribute("class")
       		}
       		
       		
	    }
    	else if (type == "ReferredToMe"){
	        document.getElementById('referToMeActive').className = 'activeBackground'
       		document.getElementById("openActive").removeAttribute("class");
       		document.getElementById("crowdActive").removeAttribute("class")
       		document.getElementById("referByMeActive").removeAttribute("class")
       		document.getElementById("appliedActive").removeAttribute("class")
       		if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
       		document.getElementById("submittedActive").removeAttribute("class")
       		}
	    }
    	    	
    	else if (type == "AppliedJobs"){
       		document.getElementById("crowdActive").removeAttribute("class")
       		document.getElementById("referByMeActive").removeAttribute("class")
       		if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
       		document.getElementById("submittedActive").className = 'activeBackground'
       		}
       		 
   			if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
   			document.getElementById('appliedActive').className = 'activeBackground'
       		document.getElementById("openActive").removeAttribute("class");
       		document.getElementById("referToMeActive").removeAttribute("class")
   			}
       	   
	    }
    	
    	requirementsList('0')
    }
</script>
<jsp:include page="../socialinviterscripts.jsp" />