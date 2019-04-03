<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="icon" type="image/png" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/fav.png" />
<title>Welcome to Source Lead</title>
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/cropper.min.css" >
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/style.css" />
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/chosen.min.css">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/sourcelead-bundle.css" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/timesheet.css" type="text/css">
<!--  scripts are added here  -->

<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-1.11.1.min.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/masterHeaderAction.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-ui.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/bootstrap-select.min.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/sourcelead-app.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery.dataTables.min.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery.timepicker.js"></script>
<script  src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jobs/js/controls.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/cropper.min.js"></script> 
<script src="${SourceLeadWeb_atsUrl}/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="${SourceLeadWeb_atsUrl}/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/chosen.jquery.min.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/slead-common.js"></script>
<script src="${SourceLeadWeb_atsUrl}/resources/js/requirement.js"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${SourceLeadWeb_googleMapsLocationApiKey}" type="text/javascript"></script>

<style>
.pac-container {
z-index: 1000000000 !important;
}
.pac-container::after {
display:none;
}

.varsCheckbox .checkbox label, .varsCheckbox .radio label {
	padding-left: 0 !important;
}
.varsCheckbox .checkbox input[type=checkbox] {
    margin-left: 0 !important;
}
.varsCheckboxDynamic .checkbox input[type=checkbox] {
    margin-left: 0 !important;
}
</style>
</head>
<%-- 
     @author : Srinivas Sabbu.<!
     this script is to get filter popup
     1.usage     : onclick on requirementList() ; 
     
--%>
<script>
var  tabAccessArray = '${SourceLeadWeb_tabsList}';
var roleActionsArray = '${SourceLeadWeb_actionsList}';

var sourceleadUrl ='${sourceleadUrl}';
var atsUrl = '${SourceLeadWeb_atsUrl}';
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
var vmsUrl ='${SourceLeadWeb_vmsUrl}';

var locationList = '${SourceLeadWeb_dateFormat}';
//reruirement list variables.
var orgATS = '${SourceLeadWeb_ats}';
var orgVMS = '${SourceLeadWeb_vms}';
var userRoleName = '${SourceLeadWeb_loginUserRole}';
var userId =  '${SourceLeadWeb_userId}';
var reqCoolingOffPeriod ='${requirement.reqCoolingOffPeriod}';
var orgLocationId  = '${SourceLeadWeb_orgLocationId}';
var country = '${SourceLeadWeb_country}';
var loginUser = '${SourceLeadWeb_loginUserFullName}';
var loginUserRole = '${SourceLeadWeb_loginUserRole}';
var userImgUrl =  '${SourceLeadWeb_userImgUrl}'; 
var tokenParamName = "${_csrf.parameterName}";
var tokenValue = "${_csrf.token}";
//

var viewTab = "${viewTab}";
var searchValue = "${searchValue}"
var universalRequirementList = "${universalRequirementList}";

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
	        var validationSettings = {
	            errorMessagePosition : 'element',
	            borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	            };
	        
	        var config = {
	      		  '.chosen-select'           : {},
	      		  '.chosen-select-deselect'  : {allow_single_deselect:true},
	      		  '.chosen-select-no-single' : {disable_search_threshold:10},
	      		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	      		  '.chosen-select-width'     : {width:"95%"}
	      		}
	</script> 
 <script type="text/javascript">
 var hideColumns = [];
 var table = "";
 var advnSearchPopEle="";
 var advnFilterPopEle="";
 $(document).ready(function () {
	 advnSearchPopEle= $("#requirement_form");
	 advnFilterPopEle =$("#requirementFilterPopup");
	 $('body').load(atsUrl+"/resources/sourceleadHtmls/requirementListHtml.html");
     $('label.toggle-vis').on('click', function (e) {
         e.preventDefault();
         // Get the column API object
         var column = table.column($(this).attr('data-column'));
         $(this).toggleClass("active");
         // Toggle the visibility
         column.visible(!column.visible());
     });
});
</script>
<script>
// toggle events are not worked properly thats why kept in jsp.
function selectAllPriority(){
	$("ul#priority > li input:checkbox").prop("checked",true);
	requirementList('list','${SourceLeadWeb_atsUrl}');
}
function clearAllPriority(){
	$("ul#priority > li input:checkbox").prop("checked",false);
	requirementList('list','${SourceLeadWeb_atsUrl}');
}
function selectAllJobType(){
	$("ul#jobType > li input:checkbox").prop("checked",true);
	requirementList('list','${SourceLeadWeb_atsUrl}');
}
function clearAlljobType(){
	$("ul#jobType > li input:checkbox").prop("checked",false);
	requirementList('list','${SourceLeadWeb_atsUrl}');
}
function selectAllStatus(){
	$("ul#status > li input:checkbox").prop("checked",true);
	requirementList('list','${SourceLeadWeb_atsUrl}');
}
function clearAllStatus(){
	$("ul#status > li input:checkbox").prop("checked",false);
	requirementList('list','${SourceLeadWeb_atsUrl}');
}
/* function getActions(){// // toggle events are not worked properly thats why kept in jsp.
	$(".action-pop").slideToggle(200);
	$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, #exportPop, #exportPopJobFair").slideUp(200);
	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	});	
} */
/* function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
 $("html").click(function(){
	$(".newActPop").slideUp(200);
}); 
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	 */
</script>
<script>
$(document).ready(function() {
	getcites('apiLocation','city','recommendation');
});
</script>
<script>
 window.onload = function() {
	
};
</script>
<script>
function statusvalidating(id){
	changeStatusAuthorisation(id);
}
</script>

<script>
   function getSourceleadTags(){
 		 $.ajax({
		    	type: "GET",
		        url: atsUrl+"/getSourceleadTagsList?"+tokenParamName+"="+tokenValue, 
		        success: function(json) {
		        	var output = "";
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
			        		
					}
		        	$("#jobsTags").empty();
			        $("#jobsTags").append(output);
			        $('#jobsTags').trigger("chosen:updated");
		        },
		    }); 
   }
/*  $(function() { // updated need to check working properly or not  
	       //Adding Customized Job Title list this method is slead-common.js
		   tagIt("requirementTitle", 'Press Enter-Key Or Comma(,) Or Tab To Add Title:', 'tagItBykeyForSkills','skills','Job Title');
	       
		   //Adding Customized Job Id list this method is slead-common.js
		   tagIt("jobId", 'Press Enter-Key Or Comma(,) Or Tab To Add Add Requirement ID:', 'tagItBykeyForSkills','skills','Job Id');
		   
		 //Adding Customized Job Id list this method is slead-common.js
		   tagIt("generatedId", 'Press Enter-Key Or Comma(,) Or Tab To Add Add Generated ID:', 'tagItBykeyForSkills','skills','Generated ID');
		 
		 //Adding Customized Job Skills list this method is slead-common.js
		   tagIt("requirementSkills", 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:', 'tagItBykeyForSkills','skills','Skills');

		   tagIt("city", 'Press Enter-Key Or Comma(,) Or Space To Add New Cites:', 'tagItBykeyForSkills','skills','sletected cites');

		   
});
  */
 
   </script>
<script type="text/javascript">
		     /*  $("#country").on('change', function (e) {
		    	  getcites('apiLocation','city','recommendation');
		    	  $("#apiLocation").val('');
		    	  $("#city").val('').trigger("chosen:updated");
		       });  */
				 for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				}) 
				
</script>
<script>
$(document.body).on('change', '#uploadBtn' ,function(){
	 $('#uploadFile').val(this.value.split( '\\' ).pop());
	 $("#uploadFile").focus();
	 if(validate_fileupload(this.value)){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var form = $('#addApplicant_form')[0];
 		var formData = new FormData(form); 
		   $.ajax({
				    type: "POST",
			    	data: formData,
		   	        contentType : 'application/json; charset=utf-8',
		   	        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
		   	        dataType : 'json',
					url : atsUrl+"/parseResume?${_csrf.parameterName}=${_csrf.token}",
					success : function(response) {
						if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							if(response.object !== null) {
								var json = response.object;
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
		 						 <c:forEach var="skill" items="${skillList}">
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
		  						
								 validateSubmissionCandidateEmail();
								 getUserByUserEmail();
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
<script>

<<<<<<< HEAD
//inlcuded in jsp due to jstl tags inclusion.
//inlcuded in jsp due to jstl tags inclusion.
function getTeamsList(reqId){
	  $.ajax({
         type : "POST",
         url: atsUrl+"/getTeamsList?"+tokenParamName+"="+tokenValue+"&reqId="+reqId,    
          success :function(result) {
          	var json = result.object;
          	if (result.response === "SESSION_TIMEDOUT") {
	  				location.replace(sourceleadUrl);
              } else if (result.response === "AWKWARD") {
                  location.href = "/errorPage";
              } else if (result.response === "SUCCESS") {
					var output = "";
				  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_TEAM\')">';
				  if(json.teams.length > 0){
					  output = output + "<optgroup label='Teams'>";
					  for (var i = 0; i < json.teams.length; i++) {
							var opt = json.teams[i];
							if('Y' == opt.isTeamAssigned){
								output = output + "<option name='TeamAndRole' value='"+opt.id+"' selected >"+ opt.name + "</option>";
							}else if($.inArray(''+opt.id, teamRoleIds) > -1){
								output = output + "<option name='TeamAndRole' value='"+opt.id+"' selected>"+ opt.name + "</option>";	
						    }else{
								output = output + "<option name='TeamAndRole' value='"+opt.id+"'>"+ opt.name + "</option>";	
							}
						}
					  output = output + "</optgroup>";
				  }
				  '</sec:authorize>';
	
				  if(json.roles.length > 0){
					  output = output + "<optgroup label='Roles'>"; 
						for (var i = 0; i < json.roles.length; i++) {
							var opt = json.roles[i];
							if('Y' == opt.isRoleAssigned){
								output = output + "<option name='TeamAndRole' value='"+opt.id+"role' selected >"+ opt.displayName + "</option>";
							}else if($.inArray(''+opt.id+'role', teamRoleIds) > -1){
								output = output + "<option name='TeamAndRole' value='"+opt.id+"role' selected>"+ opt.displayName + "</option>";
							}else{
								output = output + "<option name='TeamAndRole' value='"+opt.id+"role'>"+ opt.displayName + "</option>";	
							}
						}
						output = output + "</optgroup>";
				  }
					$("#teamSelectionCategory").empty();
				    $("#teamSelectionCategory").append(output);
				    $("#teamSelectionCategory").selectpicker("refresh");
              } else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
                  showError("Operation failed, please try again");
              }
          },
          complete: function (result) {
          	var teamType = $("#teamSelectionCategory").val();
        		getUserList(teamType);
          }
          
    }); 
}
// inlcuded in jsp due to jstl tags inclusion.
function getAddApplicant(requirementId,reqCoolingOffPeriod){
	$('.action-pop').slideUp();
	var locationDate ='${SourceLeadWeb_dateFormat}'; 
	  var finalDateFormatScript = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatScript = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatScript = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatScript = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatScript = 'yy-dd-mm'; 
	  }
	generateOrgSubmissionForm(atsUrl,requirementId,finalDateFormatScript)
	var result = "";
	result = result + '<form id="addApplicant_form" name="addApplicant_form" enctype="multipart/form-data" method="post">';
	result = result + '<h3>Add Applicant</h3>';
	result = result + '<div class="form-horizontal clearfix select-field applicant-label">';
	result = result + '<input type="hidden" id="requirementId" name="requirementId" value='+requirementId+'>';
	result = result + '<input type="hidden" id="reqCoolingOffPeriod" name="reqCoolingOffPeriod" value=\''+reqCoolingOffPeriod+'\'>';
	
	result = result + '<div class="row">';
	result = result + '<div class="venk clearfix">';
	result = result + '<div class="col-md-7 radioInline">';
	result = result + '<label class="mr"><input type="radio" checked="checked" value="N" name="candidateType" onClick="getCandidatesList('+requirementId+',\'N\');">Candidates</label>';
	
	result = result + '<label class="mr"><input type="radio" value="New candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'New\');getCandidateSelectionType();">Add New Candidate</label>';
	result = result + '<label class="mr"><input type="radio" value="Parese candidate" name="candidateType" onClick="getCandidatesList('+requirementId+',\'parseCandidate\');getCandidateSelectionType();">Import Candidate</label>';
	result = result + '</div>';	
	
	result = result + '<div class="col-md-5 pull-right">';
	result = result + '<div class="col-md-12" id="browseDiv"  style="display: none;">';
	result = result + '<div class="input-group" id="uploadFileGroup">';
	result = result + '<label class="input-group-btn"  onchange="importAddApplicantDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
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
	result = result + '<option value="">-- Select Candidate --</option>';
	result = result + '</select>';
	result = result + '</div>';	
	result = result + '</div>';	
	
	result = result + '<div class="panel-border_a col-md-5 mt10" id="resumesDiv"  style="display: none;">';
	result = result + '<select id="candidateResumesList" name="candidateResume" data-validation-optional="true" field-name="Resume" class="form-control block">';
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
	result = result + '<c:forEach var="skill" items="${skillList}">';
	result = result + "<option value='${skill.name}'>${skill.name}</option>";
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
	result = result + '<label class="col-md-4">Employment<br/> Type<span class="paddingLeft">*</span></label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select class="form-control" id="designationType" name="designationType" onchange="getEmployementType();" field-name="Employment Type">';
	result = result + '<option value="">--Select Employment Type--</option>';
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
	result = result + '<select class="chosen-select form-control" id="vendors" name="vendors" data-validation-optional="true"  onchange="getAddApplicantVendorContactList();" field-name="Vendors">';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	
	result = result + '<div class="row">';
    result = result + '	<div class="col-md-6 upresume mb" id="vendorContactsDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Vendor Contacts</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<select class="chosen-select form-control" id="vendorContact" data-validation-optional="true"  name="vendorContact"  field-name="vendor Contacts">';
	result = result + '<option value="">--Select Vendor Contacts--</option>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>'; 
	
	
	result = result + '<div class="row">';
	result = result + '	<div class="col-md-6 upresume mb" id="newResumeDiv" style="display: none;">';
	result = result + '<label class="col-md-4">Add Resume</label>';
	result = result + '		<div class="col-md-8">';
	result = result + '			    <div class="input-group col-md-12 attachfile-error" id="uploadFileGroup1" style="margin:0 auto">';
	result = result + '			        <label class="input-group-btn" onchange="uploadAddApplicantCandidateDoc();"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result + '                   <input type="file" style="display: none;" name="newUploadBtn" id="newUploadBtn"  class="upload">';
	result = result + '			          </span> </label>';
	result = result + '			          <input type="text" class="form-control" readonly id="newUploadFile" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation-optional="true" data-validation="validatedoc "  >';
	result = result + '			    </div>';
	result = result + '		 </div>';
	result = result + '	 </div>';
	
	
	result = result + '	<div class="col-md-6 upresume mb">';
	result = result + '<label class="col-md-4">&nbsp;</label>';
	result = result + '<input type="checkbox" name="needApplicantPermit" id="needApplicantPermit" onClick="ApplicantPermit();" >&nbsp;&nbsp;<strong>Need applicant permission</strong><br> ';
	result = result + '<input type="checkbox" name="notifyToApplicant" value="Y" id="notifyToApplicantId" onClick="NotifyToApplicant();" >&nbsp;&nbsp;<span><strong>Notify to applicant</strong></span> ';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="form-footer clearfix mt">';
	result = result + '<div class="pull-right mr">';
	result = result + '<button type="button" onclick="addApplicantForm();" id="save_button"  class="btn btn-sm btn-primary " >SUBMIT</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';  
	result = result + '</form>';
	
	
	 
	getCandidatesList(requirementId,'N');
	
	
	$("#addApplicantDiv").empty();
	$("#addApplicantDiv").append(result);
	
	   var config = {
				  '.chosen-select'           : {},
				  '.chosen-select-deselect'  : {allow_single_deselect:true},
				  '.chosen-select-no-single' : {disable_search_threshold:10},
				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				  '.chosen-select-width'     : {width:"95%"}
				}
	   
	 //Adding Customized Skills to the list
       var select, chosen;
	    select = $("#candidateSkills");
	    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
	    chosen = select.data('chosen');
	    
	    $("#candidateSkills").chosen().change(function(event) {
	    	var allSkills = $(event.target).val();
	    	$.ajax({
		    	type: "POST",
		        url: atsUrl+"/profileBySkillSet?"+tokenParamName+"="+tokenValue+"&allSkills="+allSkills, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var primarySkill = response.object;
			        	$("#primarySkill").val(primarySkill);
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	    });
	    chosen.search_field.on('keydown keyup', function(e)
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
				    		var focussedSkills = $("ul.chosen-results").find("li.active-result.highlighted");
			    			var newSkill = this.value.replace(","," ");
			    			 if (tagItBykeyForSkills(e) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			                 {
			    				 preventTabkey(e);
			                   // below code manually enter skills are taken
			    				  
			                   var isNewSkill = true;
								 $("#candidateSkills option:selected").each(function(){
									 if(this.value.toUpperCase() === newSkill.toUpperCase() && this.value.toLowerCase() === newSkill.toLowerCase()){
										 isNewSkill = false;
										 return false;
									 }
			                    
			                   });
								
									if(focussedSkills.length > 0){
										var skillOption  = $("<option>").val(focussedSkills.first().text()).text(focussedSkills.first().text());
										select.append(skillOption);
										select.find(skillOption).prop('selected',true);
										select.find(skillOption).siblings("[value='"+ focussedSkills.first().text()+"']").remove();
										$("#candidateSkills").trigger("chosen:updated");
								   }
								   else{
									   if(isNewSkill){
										var option = $("<option>").val(newSkill).text(newSkill);
										select.append(option);
										select.find(option).prop('selected', true);
									   }
								   }
								
				               $("#candidateSkills").trigger("chosen:updated"); 
			                   
			                }
					       
		    	         }
	    	});
	    //Adding Customized Skills to the list
			
	$("#candidateSkills").val('').trigger("chosen:updated");
	    
		for (var selector in config) {
		  $(selector).chosen(config[selector]);
		}
		$('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
			})
	
	
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
=======
<jsp:include page="requirementTable.jsp" />
<jsp:include page="requirementAssignedTo.jsp"/>
<!-- <script type="text/javascript" src="/resources/js/requirementTable.js"></script> -->
<input type="hidden" name="noReq" id="noReq" />
<input type="hidden" id="requirementEmptyListMessage" name="requirementEmptyListMessage" />
<div id="popUp"></div>
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
        	<h2>Jobs 
					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
						<a href="${SourceLeadWeb_atsUrl}/professionalRequirements" class="jobHeadBtn">Crowdsourcing Jobs</a>
					</sec:authorize>        	
        	</h2>
      	</div>
		<c:if test="${!empty msg}">
			<div class="success_pop" id="selector2" style="display: block;">
				<div class="pull-left"><i class="fa fa-check-circle"></i></div>
				<div class="pull-left" style="width:325px; padding:10px 0;"><span>${msg}</span></div>
			</div>
		</c:if>
		
		<%-- <div class="pull-left">
			<h2 class="pull-left paddingRight">Jobs List</h2>
			<span class="paddingLeft pull-left">|<a class="paddingLeft"
				href="<c:url value='${appPath}/professionalRequirements'/>">Crowdsourcing
					Jobs</a></span>
		</div> --%>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Job List
		</div>
	</section>
	<div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
         <li class="current" id="jobsTab"><a href="javascript:void(0)" onclick="requirementListCheck('','','','${SourceLeadWeb_atsUrl}');">Jobs</a></li>
         <li id="jobFairTab"><a href="javascript:void(0)"  onclick="jobFairListCheck();">Job Fair</a></li>
         <div class="clearfix"></div>
     </ul>
	</div>
>>>>>>> c260c83988b6f10109026bc2eb21bd079b51c067


</script>
<!-- advanced search popup Start  Block -->
<form id="requirement_form" name="requirement_form" enctype='multipart/form-data'>
<div class="cand-advsrch-popup advancedsearch-popup reqadvsrch candAdvsrch ">
<h5 class="headerAdsrch">Advanced Search
	<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
		class="fa fa-close"></i></a>
</h5>
<div class="addInner">
	<div class="row">
		<div class="clearfix">
			<div class="col-md-4">
				<label>Generated ID</label>
				<div class="clearfix">
					<select class="chosen-select form-control input-sm" multiple
						data-placeholder="Generated ID" id="generatedId" name="generatedId">
					</select>
				</div>
			</div>
			
			<div class="col-md-4">
				<label>Title</label>
				<div class="clearfix">
					<select class="chosen-select form-control input-sm" multiple
						data-placeholder="Job Title" id="requirementTitle"
						name="requirementTitle">
					</select>
				</div>
			</div>
			
			<div class="col-md-4">
				<label>Job Id</label>
				<div class="clearfix">
					<select class="chosen-select form-control input-sm" multiple
						data-placeholder="Job Id" id="jobId" name="jobId">
					</select>
				</div>
			</div>

		</div>
		<div class="clearfix">

			<div class="col-md-4">
				<label>Job status </label>
				<div class="clearfix">
					<select id="requirementStatu" name="requirementStatus"
						class="chosen-select" data-placeholder="Job status " multiple>
						<option>NEW</option>
						<option>DRAFT</option>
						<option>SUBMITTED</option>
						<option>OPEN</option>
						<option>CANCEL</option>
						<option>ONHOLD</option>
						<option>FILLEDCLOSED</option>
						<option>UNFILLEDCLOSED</option>
						<option>REJECTED</option>
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<label>Posted On</label>
				<div class="clearfix">
					<select class="chosen-select" id="updatedOn" name="updatedOn"
						data-placeholder="Posted On">
						<option>All</option>
						<option>1 Day Ago</option>
						<option>3 Days Ago</option>
						<option>1 Week Ago</option>
						<option>2 Weeks Ago</option>
						<option>1 Month Ago</option>
						<option>3 Months Ago</option>
					</select>
				</div>
			</div>

			<div class="col-md-4">
				<label>Client</label>
				<div class="clearfix">
					<select class="chosen-select" id="client" name="client"
						data-placeholder="Select Client" multiple>
						<c:forEach var="report" items="${add_clientList}">
							<c:if test="${report.locationId.id == loginOrgLocId}">
								<option
									value="${report.responderLocationId.org.orgName}"
									${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.responderLocationId.org.orgName}</option>
							</c:if>
							<c:if
								test="${report.responderLocationId.id == loginOrgLocId}">
								<option
									value="${report.locationId.org.orgName}"
									${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<%-- <div class="clearfix">
			<div class="col-md-4">
				<label>Country</label>
				<div class="clearfix adv-srh-chosen">
					<select class="chosen-select" name="country" id="country" field-name="Country">
						<option value="">---select Country---</option>
						<c:forEach var="cntry" items="${countryList}">
							<option value="${cntry.name},${cntry.code2}">${cntry.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="col-md-4">
				<label>State</label>
				<div class="clearfix adv-srh-chosen">
					<select class="chosen-select" name="state" id="state"
						field-name="State">
						<option value="">---select state---</option>
						<c:forEach var="state" items="${statesList}">
							<option value="${state.stateName}">${state.stateName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<label>City</label>
				<div class="clearfix adv-srh-chosen">
					<select class="chosen-select" name="city" id="city"
						field-name="City">
						<option value="">---select city---</option>
					</select>
				</div>
			</div>
		</div> --%>
		<div class="clearfix">
			<div class="col-md-4 hidden">
				<label>Client</label>
				<div class="clearfix">
					<select class="chosen-select" id="client" name="client"
						data-placeholder="Client" multiple>
						<c:forEach var="report" items="${add_clientList}">
							<c:if test="${report.locationId.id == loginOrgLocId}">
								<option
									value="${report.id},${report.responderLocationId.org.orgName}"
									${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.responderLocationId.org.orgName}</option>
							</c:if>
							<c:if
								test="${report.responderLocationId.id == loginOrgLocId}">
								<option
									value="${report.id},${report.responderLocationId.org.orgName}"
									${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<label>Notice Period</label>
			<div class="clearfix">
				<select multiple class="chosen-select form-control"
					id="noticePeriod" name="noticePeriod"
					data-validation-optional="true"
					data-placeholder="Select Notice Period">
					<option
						${requirement.noticeperiodList  == 'Immediately Available' ? 'selected' : ''}>Immediately Available</option>
					<option
						${requirement.noticeperiodList  == '15-Days' ? 'selected' : ''}>15-Days</option>
					<option
						${requirement.noticeperiodList  == '1-Month' ? 'selected' : ''}>1-Month</option>
					<option
						${requirement.noticeperiodList  == '2-Month' ? 'selected' : ''}>2-Month</option>
					<option
						${requirement.noticeperiodList  == '3-Month' ? 'selected' : ''}>3-Month</option>
				</select>
			</div>
		</div>
			
		<div class="col-md-4">
			<label>Skills</label>
			<div class="clearfix">
				<select class="chosen-select form-control input-sm" multiple data-placeholder="Skills" id="requirementSkills" name="requirementSkills">
				<c:forEach var="skill" items="${skillList}">
				<option value="${skill.name}">${skill.name}</option>
				</c:forEach>
				</select>
			 </div>
		</div>
		
		
		<div class="col-md-4">
			<label>Immigration Status</label>
			<div class="clearfix">
                <select multiple class="chosen-select form-control" name="visaType" id="visaType" data-placeholder="Select Visa" data-validation-optional="true">
				<c:forEach var="visaList" items="${visaList}">
				<option value="${visaList}">${visaList}</option>
				</c:forEach>
				</select>
			 </div>
		</div>
		
		<div class="col-md-12 mb">
			<label>Tags</label>
				<div class="clearfix">
					<select class="chosen-select form-control input-sm" multiple data-placeholder="Tags" id="jobsTags" name="jobsTags">
					</select>
				 </div>
			</div>
			
		<div class="clearfix">
		   <div class="col-md-4">
				<label>Choose City</label>
				<div class="clearfix">
				<div class="col-md-14">
			      <input type="text" class="form-control positionRelative"  multiple data-placeholder="select city" multiple name="apiLocation"  id="apiLocation">
				  <i class="fa fa-refresh btn btn-primary refresh-icon mr ref-top" title="Refresh" onClick="refreshGoogleApiCity('apiLocation');"></i>
				 </div>
				</div>
			</div>
			
			
			<div class="col-md-8">
				<label>Selected Cities</label>
				<div class="clearfix">
				   <select class="chosen-select form-control input-sm" multiple
						data-placeholder="sletected cites" id="city" name="city">
					</select>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="clearfix advancedsearch-footer">
		<div class="pull-right mr">
			<input type="button" value="Search" id="search_requiremet"
				onclick="requirementListCheck('','','advanceSearch','${SourceLeadWeb_atsUrl}');" class="btn btn-sm btn-success" />
			<input type="button" value="Reset" id="clear_search"
				onclick="clearSearch();" class="btn btn-sm btn-primary" />
		</div>
	</div>
</div>
</form>
<!-- advanced search End Block -->

<div class="timesheet_out overflow_inherint" id="jobsTabContent">
		
<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="filtersDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="clearList();"></a>
      		<a href="javascript:void(0)" onclick="columPop();" title="Column Chooser" class="columIcon"></a>
      		<a href="javascript:void(0)" class="newAdBtn pull-left" title="Advanced Search" onClick="openPop();"> Advanced Search &nbsp; <i class="fa fa-search"></i> </a>
    
    
    
    <div class="colPop">
	<div class="colPopMid coldropdown-parent">
	<div class="clearfix">
		<div class="row">
				<div class="col-md-10 mb10 mt5 col-md-offset-2">
<!-- 				<span class="checkbox-new  pull-left mr">
					<input id="columnsPickerUnCheckAll" class="checkbox-custom toggle-vis" name="columnsPicker" onclick="checkAllColumns(this);"  type="checkbox">
					<label for="columnsPickerUnCheckAll" class="checkbox-custom-label">Uncheck All</label>
				</span> -->
				<span class="checkbox-new pull-left">
					<input id="columnsPickerCheckAll" class="checkbox-custom toggle-vis" name="columnsPicker" onclick="checkAllColumns(this);"  type="checkbox">
					<label for="columnsPickerCheckAll" class="checkbox-custom-label" >Check All</label>
				</span>
			</div>
		</div>
	<div class="clearfix"></div>
		<div class="coldropdown-inner">
		<ul id="jobCustomColumns">
			<li>
				<span class="checkbox-new clearfix">
					<input id="a" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" checked="" data-name="generatedId">
					<label for="a" class="checkbox-custom-label" >Generated ID</label>
				</span>
			</li>
			
			<li>
				<span class="checkbox-new clearfix">
					<input id="c" class="checkbox-custom toggle-vis" name="paidComponent" data-column="4" type="checkbox" checked="" data-name="process">
					<label for="c" class="checkbox-custom-label" >Process</label>
				</span>
			</li>
			
			<li>
				<span class="checkbox-new clearfix">
					<input id="d" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox" checked="" data-name="clientName">
					<label for="d" class="checkbox-custom-label" >Client</label>
				</span>
			</li>

			<li>
				<span class="checkbox-new clearfix">
					<input id="e" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6" type="checkbox" checked="" data-name="payRate">
					<label for="e" class="checkbox-custom-label" >Pay Rate</label>
				</span>
			</li>
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="f" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7" type="checkbox" checked="" data-name="billRate">
					<label for="f" class="checkbox-custom-label" >Bill Rate</label>
				</span>
			</li>
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="g" class="checkbox-custom toggle-vis" name="paidComponent" data-column="8" type="checkbox" checked="" data-name="jobType">
					<label for="g" class="checkbox-custom-label" >Type</label>
				</span>
			</li>
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="h" class="checkbox-custom toggle-vis" name="paidComponent" data-column="9" type="checkbox" checked="" data-name="location">
					<label for="h" class="checkbox-custom-label" >Location</label>
				</span>
			</li>
			
							<li>
				<span class="checkbox-new clearfix">
					<input id="i" class="checkbox-custom toggle-vis" name="paidComponent" data-column="10" type="checkbox" checked="" data-name="totalApplicants">
					<label for="i" class="checkbox-custom-label" >Applicants</label>
				</span>
			</li>
				
							
				<li>
				<span class="checkbox-new clearfix">
					<input id="s" class="checkbox-custom toggle-vis" name="paidComponent" data-column="20" type="checkbox" checked="" data-name="owner">
					<label for="s" class="checkbox-custom-label" >Owner</label>
				</span>
			</li>
			
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="t" class="checkbox-custom toggle-vis"  name="paidComponent" data-column="21" type="checkbox" checked="" data-name="postedOn">
					<label for="t" class="checkbox-custom-label" >PostedOn</label>
					</span>
					</li>
					
							<li>
				<span class="checkbox-new clearfix">
					<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" data-column="11" type="checkbox" data-name="Shortlisted">
					<label for="j" class="checkbox-custom-label" >Shortlisted</label>
				</span>
			</li>
			
										<li>
				<span class="checkbox-new clearfix">
					<input id="k" class="checkbox-custom toggle-vis" name="paidComponent" data-column="12" type="checkbox" data-name="submitToClient">
					<label for="k" class="checkbox-custom-label" >SubmitToClient</label>
				</span>
			</li>
							<li>
				<span class="checkbox-new clearfix">
					<input id="l" class="checkbox-custom toggle-vis" name="paidComponent" data-column="13" type="checkbox" data-name="interview">
					<label for="l" class="checkbox-custom-label" >Interview</label>
				</span>
			</li>
			
								<li>
				<span class="checkbox-new clearfix">
					<input id="m" class="checkbox-custom toggle-vis" name="paidComponent" data-column="14" type="checkbox" data-name="offer">
					<label for="m" class="checkbox-custom-label" >Offer</label>
				</span>
			</li>
			
										<li>
				<span class="checkbox-new clearfix">
					<input id="n" class="checkbox-custom toggle-vis" name="paidComponent" data-column="15" type="checkbox" data-name="onHold">
					<label for="n" class="checkbox-custom-label" >OnHold</label>
				</span>
			</li>
			
							<li>
				<span class="checkbox-new clearfix">
					<input id="o" class="checkbox-custom toggle-vis" name="paidComponent" data-column="16" type="checkbox" data-name="noResponse">
					<label for="o" class="checkbox-custom-label" >NoResponse</label>
				</span>
			</li>
						
				<li>
				<span class="checkbox-new clearfix">
					<input id="p" class="checkbox-custom toggle-vis" name="paidComponent" data-column="17" type="checkbox" data-name="joined">
					<label for="p" class="checkbox-custom-label" >Joined</label>
				</span>
			</li>
			
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="q" class="checkbox-custom toggle-vis" name="paidComponent" data-column="18" type="checkbox" data-name="backOut">
					<label for="q" class="checkbox-custom-label" >BackOut</label>
				</span>
			</li>
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="r" class="checkbox-custom toggle-vis" name="paidComponent" data-column="19" type="checkbox" data-name="rejected">
					<label for="r" class="checkbox-custom-label" >Rejected</label>
				</span>
			</li>
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="u" class="checkbox-custom toggle-vis" name="paidComponent" data-column="22" type="checkbox" data-name="experience">
					<label for="u" class="checkbox-custom-label" >Experience</label>
				</span>
			</li>
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="v" class="checkbox-custom toggle-vis" name="paidComponent" data-column="23" type="checkbox" data-name="noOfPositions">
					<label for="v" class="checkbox-custom-label" >NoOfPositions</label>
				</span>
			</li>
			
				<li>
				<span class="checkbox-new clearfix">
					<input id="w" class="checkbox-custom toggle-vis" name="paidComponent" data-column="24" type="checkbox" data-name="industryName">
					<label for="w" class="checkbox-custom-label" >IndustryName</label>
				</span>
			</li>
		</div>
	</div>
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="columHide();">Apply</a>
		<a href="javascript:void(0)" onclick="columClose();">Cancel</a>
	</div>
</div>				
			</div> 
    
      </span>
      <span class="pull-left refreIcon">
      		<button type="button" class="btn btn-danger ml pull-left btn-sm" onclick="jobForceDelete();" id="forceDeleteBtn" style="display:none">Delete</button>
      </span>
      <ul class="pull-right list-unstyled list-inline">
      
					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_EDIT_REQUIREMENT')">
							<div id="filter1ApprovedPop" class="approved_pop">
								<ul>
									<c:forEach var="report" items="${add_clientList}">
										<c:if test="${report.locationId.id == loginOrgLocId}">
											<li><input id="clientName${report.id}" name="clientNameById" value="${report.id}" onclick="requirementList('list','${SourceLeadWeb_atsUrl}');" type="checkbox"><label
												title="${report.responderLocationId.org.orgName}">${report.responderLocationId.org.orgName}</label></li>
											<%-- 			                    	<option value="${report.id},${report.responderLocationId.org.orgName}" ${report.id  == requirement.clientId.id ? 'selected' : ''} >${report.responderLocationId.org.orgName}</option> --%>
										</c:if>
										<c:if test="${report.responderLocationId.id == loginOrgLocId}">
											<li><input id="clientName${report.id}" name="clientNameById" value="${report.id}" onclick="requirementList('list','${SourceLeadWeb_atsUrl}');" type="checkbox"><label
												title="${report.locationId.org.orgName}">${report.locationId.org.orgName}</label></li>
											<%-- 			                  	 	<option value="${report.id},${report.responderLocationId.org.orgName}" ${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option> --%>
										</c:if>
									</c:forEach>
								</ul>
						 </div>
					</sec:authorize>  
					
					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
		                    <li>
								<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
								<div class="action-pop">
									<span> 
									<a href="${SourceLeadWeb_atsUrl}/requirement">Job</a>
									<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
										<a href="${SourceLeadWeb_sourceleadUrl}/uploadJobs">Import</a>
									</sec:authorize>									
									</span>
								</div>
							</li>						
						
					</sec:authorize>	
									
				<li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a></li>
				
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>	           	   		
							<sec:authorize access="hasAnyRole('ROLE_EXPORT_REQUIREMENTS')">
							 	<a href="javascript:void(0)" onclick="exportToPdf(1);" title="PDF">Export PDF</a>
								<a href="javascript:void(0)" onclick="exportToExcel(1);" title="Excel">Export Excel</a>					
							</sec:authorize>
						</span>
					</div>
				</li>
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dataTableSearch'/><span class="repSearchIcon" onClick="requirementListCheck('','','','${SourceLeadWeb_atsUrl}');"></span></li>
        </ul>
      </div>		
		
<div class="require_wrap_table datatable_filter">
	<div id="requirementList"></div>
</div>

</div>
	
	<div class="timesheet_out" id="jobFairTabContent" style="display: none;">
		<div class="vendor_wrap">
<div class="positionRelative reportHead clearfix">
      
      <ul class="pull-right list-unstyled list-inline">
					
					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
						<li><a href="${SourceLeadWeb_atsUrl}/addJobFair" class="newAdBtn">+ Job Fair</a></li>
					</sec:authorize>					
									
					<sec:authorize access="hasAnyRole('ROLE_EXPORT_REQUIREMENTS')">
						<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
							<div class="newActPop">
								<span>
									 	<a href="javascript:void(0)" onclick="exportToPdf(2);" title="PDF">Export PDF</a>
										<a href="javascript:void(0)" onclick="exportToExcel(2);" title="Excel">Export Excel</a>					
								</span>
							</div>
						</li>
					</sec:authorize>
				
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='jobFairDataTableSearch'/><span class="repSearchIcon" onClick="jobFairListCheck();"></span></li>
        </ul>
      </div>	
      	
			<div class="require_wrap_table datatable_filter">
				<div id="jobFairList"></div>
			</div>
		</div>
	</div>
	
	
	<div class="side_mask">
  <div class="proj_close" onClick="activeTab();"></div>
</div>
	
	
</section>
 <div class="advancedsearch-mask" onClick="closeAdsrch()"></div>
<jsp:include page="addApplicantFromRequirementList.jsp" />

<input type="hidden" name="assignedTeamId" id="assignedTeamId" value=""/>


<script>
function filterPop(){
	$("#requirementFilterPopup").toggle();
	$(".sideMask").fadeIn();
};
function closeFilter(){
	$("#requirementFilterPopup").hide();
	$(".sideMask").fadeOut();

}
</script>
<script type="text/javascript">

	function columPop(){
		$(".colPop").show();
		$(".colPopFot").show();
	}
	function columClose(){
		$(".colPop").hide();
	}
	$("html").click(function(){
		$(".colPop").hide();
	});
	$(".refreIcon, .colPop").click(function(e){
	 	e.stopPropagation();
	});		
		
	function columHide(){
		hideColumns=[]; // reset the array object.
		$(".toggle-vis").each(function() {
			columClose();// 
		   var chkdVal= $(this).prop('checked');
// 		alert("chkdVal "+chkdVal);
		   var column = table.column($(this).attr('data-column'));
// 		   alert("column "+column);
			  if(chkdVal == true){
// 				  alert("1");
			         column.visible(true);
			    }
			    else{
// 			    	 alert("2");
			      column.visible(false);
			      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
			  }
	    });
		$(".colPop").hide();
		
		reqTable.column(0).visible(false);

	}
	function checkAllColumns(obj){
		var id = obj.id;
		var chkdVal= $('.toggle-vis:input:checkbox').prop('checked');
		if(chkdVal == true){
		if(id =='columnsPickerCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',true);
		}
		}
		else{
			if(id =='columnsPickerCheckAll'){
				$('.toggle-vis:input:checkbox').prop('checked',false);
			}
		} 
			
	}
	</script>
<!-- 

<script type="text/javascript">
function changeStatusAuthorisation(requirementId){
	var status = document.getElementById('changeStatusId' + requirementId).value;
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userId = '${SourceLeadWeb_userId}';
	   $.ajax({
         type : "POST",
          url: sourceleadUrl+"/changeStatusAuthorisation?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status+"&userId="+userId,        
          success :function(data) {
        	  if(data == "true"){
        		  document.getElementById("changeStatusId" + requirementId).disabled=false;
        		  
        	  }else if(data == "false"){
        		  document.getElementById("changeStatusId" + requirementId).disabled=true;
        		  
        	  }
        	  
          }
}); 
}
</script>
 -->


<script type="text/javascript">
function changeStatus(requirementId, requirementStatus){
	var status = document.getElementById('changeStatusId' + requirementId).value;
	$("tr").find("td.sorting_1").removeClass("sorting_1");
	if(status == "FILLEDCLOSED" ||  status == "UNFILLEDCLOSED" || status == "ONHOLD" || status == "OPEN" ){
		$("#job_"+requirementId).addClass("row-success");
		location.reload();
	}else if(status == "CANCEL"){
		$("#job_"+requirementId).addClass("row-danger");
		location.reload();
	}else{
		$("#job_"+requirementId).addClass("row-warning");
		location.reload();
	}

	 var userId ='${SourceLeadWeb_userId}';
     if(status == "DRAFT"){
        	 $.ajax({
             type : "POST",
        	 url: atsUrl+"/saveChangedStatusFromDraft?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status+"&userId="+userId,
	        	 success :function(result) {
	        		 	if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	$("#changeStatusId" +requirementId).val(status);
		  	           	  //location.replace(sourceleadUrl+"/requirementList");	
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
	         	}
        	});
         }else{
        	 $.ajax({
             type : "POST",
        	 url: atsUrl+"/saveChangedStatus?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status,
	        	 success :function(result) {
	        		 if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	$("#changeStatusId" +requirementId).val(status);
		   	           	 //location.replace(sourceleadUrl+"/requirementList");
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
	         	}
        	});
         }
	 /* var statusArray = [];
	 $("input:checkbox[name=requirementStatus]:checked").each(function () {
		 statusArray.push($(this).val());
     });
	 if(statusArray.indexOf(status) > -1){
		
	 }else{
		 var table = $('#requirementPagination').DataTable();
		 $('#Row_'+requirementId).remove();
	 } */
	 setTM();
	}
	function setTM (){
		  setTimeout(function() { 
		  $("tr").removeClass("row-info");
		  $("tr").removeClass("row-success");
		  $("tr").removeClass("row-warning");
		  $("tr").removeClass("row-danger");
		}, 2000);
	};
</script>
<script>
	$('#mypop').click(function(){
		$("#exportPop").toggle();
		$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, .action-pop").slideUp();
 	});
	$('#mypopJobFair').click(function(){
		$("#exportPopJobFair").toggle();
		$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, .action-pop").slideUp();
 	});
	$("html").click(function(){
		$("#exportPop").hide(200);
		/* $("#exportPopJobFair").hide(200); */
	});
	$("#exportPop, #exportPopJobFair, #mypop").click(function(e) {  
	    e.stopPropagation();
	});
</script>
<script>

function exportToPdf(type) {
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	if(type === 1) {
		var req = $("#noReq").val();
	 	var requirementEmptyMessage = $("#requirementEmptyListMessage").val();
	  	var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() {
			status.push($(this).val());
	    });
	 	var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).attr("data-val"));
	    });
		if(requirementEmptyMessage > 0) {
			window.location.href = atsUrl+"/getRequirementExportListByOrgLocationID?"+tokenParamName+"="+tokenValue+"&status="+status+"&clients="+clients+"&exportType=pdf";
		} else {
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Jobs List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_requirement_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}
	} else {
		/* alert("Export JobFair to PDF"); */
		$(".buttons-pdf").click();
		window.location.href = atsUrl +"/pdfExportForJobfair?includeInactive=true";
	}
}
</script>
<script>
function exportToExcel(type) {
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	if(type === 1) {
		var req = $("#noReq").val();
		var requirementEmptyMessage = $("#requirementEmptyListMessage").val();
	  	var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() {
			status.push($(this).val());
	    });
	 	var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).attr("data-val"));
	    });
		if(requirementEmptyMessage > 0) {
			window.location.href = atsUrl+"/getRequirementExportListByOrgLocationID?"+tokenParamName+"="+tokenValue+"&status="+status+"&clients="+clients+"&exportType=xls";	
		} else {
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Jobs List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_requirement_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}
	} else {
		/* alert("Export JobFair to Excel"); */
		window.location.href = atsUrl +"/excelExportForJobfair?includeInactive=true"
		$(".buttons-excel").click();
	}
}
</script>
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
       function msgFadein1() {
           setTimeout(function(){
                   $('#selector2').fadeOut('slow'); }, 1000);
       }
</script>
<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, #exportPop, #exportPopJobFair").slideUp(200);
	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	});	
}
</script>

<script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
			$(".advancedsearch-mask").fadeOut();
		}
		function openPop(){
			 getSourceleadTags();
			 $("#dataTableSearch").val('');
			 $("div.cand-advsrch-popup").toggle();
			 $(".advancedsearch-mask").fadeIn();
			 $("#requirementFilterPopup").toggle();
		}
		function closeAdsrch(){
			$("div.cand-advsrch-popup").fadeOut();
			$(".advancedsearch-mask").fadeOut();
		};
	</script>

<script>
  $(function() { 
	       //Adding Customized Job Title list this method is slead-common.js
		   tagIt("requirementTitle", 'Press Enter-Key Or Comma(,) Or Tab To Add Title:', 'tagItBykeyForSkills','skills','Job Title');
	       
		   //Adding Customized Job Id list this method is slead-common.js
		   tagIt("jobId", 'Press Enter-Key Or Comma(,) Or Tab To Add Add Requirement ID:', 'tagItBykeyForSkills','skills','Job Id');
		   
		 //Adding Customized Job Id list this method is slead-common.js
		   tagIt("generatedId", 'Press Enter-Key Or Comma(,) Or Tab To Add Add Generated ID:', 'tagItBykeyForSkills','skills','Generated ID');
		 
		 //Adding Customized Job Skills list this method is slead-common.js
		   tagIt("requirementSkills", 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:', 'tagItBykeyForSkills','skills','Skills');


<!-- advanced filter code is below -->
<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="requirementFilterPopup">
<h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onClick="closeFilter()"><i class="fa fa-close"></i></a></h5>
<div class="addInner">
<div class="row">
 
 <div class='col-md-2'>
 <div class='row'>
 <div class='col-md-12 varsCheckbox'>
 <label><strong>Assign To</strong></label>
 <div class='clearfix'>
 	<ul id="assignTo" class="list-unstyled">
		<li class="radioIput positionRelative"><input id="r1" type="radio" name="requirementAssignTo" value="Alljobs" ><label for="r1">All Jobs </label></li>
		<li class="radioIput positionRelative"><input id="r2" type="radio" name="requirementAssignTo" value="Myjobs" ><label for="r2">My Jobs </label></li>
		<!-- <li class="radioIput positionRelative"><input id="r3" type="radio" name="requirementAssignTo" value="CrowdsourcingJobs" ><label for="r3">Crowdsourcing Jobs</label></li> -->
	</ul>
	</div>
 </div>
 
 			<div  class="col-md-12 varsCheckbox">
 			<label><strong>Priority</strong></label>
 			<div class='clearfix'>
								<ul id="priority" class="list-unstyled">
									<li class="checkBox positionRelative mb0"><input id="c1" type="checkbox" name="requirementPriority" value="Medium" ><label for="c1">Medium </label></li>
									<li class="checkBox positionRelative mb0"><input id="c2" type="checkbox" name="requirementPriority" value="Low" ><label for="c2">Low </label></li>
									<li class="checkBox positionRelative mb0"><input id="c3" type="checkbox" name="requirementPriority" value="Urgent" ><label for="c3">Urgent </label></li>
									<li class="checkBox positionRelative mb0"><input id="c4" type="checkbox" name="requirementPriority" value="High" ><label for="c4">High </label></li>
								</ul>
				</div>
			</div>
			
			<div class="col-md-12 varsCheckbox">
				<label><strong>Jobs</strong></label>
					<div class='clearfix'>
				<ul id="jobs" class="list-unstyled">
					<li class="checkBox positionRelative mb0"><input id="j1" type="checkbox" name="requirementSpecJobs" value="ClientJobs" ><label for="j1">All Client Jobs </label></li>
					<li class="checkBox positionRelative mb0"><input id="j2" type="checkbox" name="requirementSpecJobs" value="NormalJobs" ><label for="j2">All Internal jobs</label></li>
				
				</ul>
			</div>
           </div>
			</div>
			</div>
			
			<div  class="col-md-2 varsCheckbox">
			<label><strong>Job Type</strong></label>
			<div class='clearfix'>
								<ul id="jobType" class="list-unstyled">
									<li class="checkBox positionRelative mb0"><input id="c11" type="checkbox" name="requirementType" value="C2C" ><label for="c11">C2C </label></li>
									<li class="checkBox positionRelative mb0"><input id="c12" type="checkbox" name="requirementType" value="W-2" ><label for="c12">W-2 </label></li>
									<li class="checkBox positionRelative mb0"><input id="c13" type="checkbox" name="requirementType" value="C2H" ><label for="c13">C2H </label></li>
									<li class="checkBox positionRelative mb0"><input id="c14" type="checkbox" name="requirementType" value="Contract" ><label for="c14">Contract </label></li>
									<li class="checkBox positionRelative mb0"><input id="c15" type="checkbox" name="requirementType" value="Part Time" ><label for="c15">Part Time </label></li>
									<li class="checkBox positionRelative mb0"><input id="c16" type="checkbox" name="requirementType" value="Full Time" ><label for="c16">Full Time </label></li>
									<li class="checkBox positionRelative mb0"><input id="c17" type="checkbox" name="requirementType" value="Other" ><label for="c17">Other </label></li>
								</ul>
				</div>
							</div>
							
						
						
						<div  class="col-md-2 varsCheckbox">
			<label><strong>Status</strong></label>
			<div class='clearfix'>	
			
								<ul id="status" class="list-unstyled">
									<li class="checkBox positionRelative mb0"><input id="c6" type="checkbox"
										name="requirementStatus" value="NEW"><label for="c6">New<span id="newRequirementCount"></span></label></li>
									<li class="checkBox positionRelative mb0"><input id="c7" name="requirementStatus" type="checkbox" value="DRAFT" ><label
										for="c7">Draft<span id="draftRequirementCount"></span></label></li>
									<li class="checkBox positionRelative mb0"><input id="c8" type="checkbox"
										name="requirementStatus" type="checkbox" value="SUBMITTED" ><label for="c8">Submitted
											<span id="submittedRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative mb0"><input id="c9" type="checkbox"
										name="requirementStatus" type="checkbox" value="OPEN" ><label for="c9">Open
											<span id="openRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative mb0"><input id="c10" type="checkbox"
										name="requirementStatus" type="checkbox" value="ONHOLD" ><label for="c10">On
											Hold <span id="onHoldRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative mb0"><input id="c11" type="checkbox"
										name="requirementStatus" type="checkbox" value="CANCEL" ><label for="c11">Cancel
											<span id="cancelRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative mb0"><input id="c12" type="checkbox"
										name="requirementStatus" type="checkbox" value="FILLEDCLOSED" ><label for="c12">Filled
											Closed<span id="filledClosedRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative mb0"><input id="c13" type="checkbox"
										name="requirementStatus" type="checkbox" 
										value="UNFILLEDCLOSED" ><label
										for="c13">UnFilled Closed<span
											id="unFilledClosedRequirementCount"></span></label></li>
									<li class="checkBox positionRelative mb0"><input id="c14" type="checkbox"
										name="requirementStatus" type="checkbox" value="REJECTED" ><label for="c14">Rejected<span
											id="rejectedRequirementCount"></span></label></li>
									<li class="checkBox positionRelative mb0"><input id="c15" type="checkbox"
										name="requirementStatus" type="checkbox" value="CLOSED" ><label for="c15">Closed<span
											id="closedRequirementCount"></span></label></li>
								</ul>
							</div>
			
			</div>
							
							
							
							
						<div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch ">
								<input type="text" id="myClientInput" onkeyup="myClientFunction()" placeholder="Clients" title="Type in a teams">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul varsCheckboxDynamic">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myClientUL">
									<c:forEach var="team" items="${add_clientList}">
									
									<c:if test="${team.locationId.id == loginOrgLocId}">
									<li class="checkBox positionRelative  mb0"><input  type="checkbox" name="clientNameById" data-val = "${team.id}" value ="${team.responderLocationId.org.orgName}" ><label>${team.responderLocationId.org.orgName}</label></li>
									</c:if>
									
									<c:if test="${team.responderLocationId.id == loginOrgLocId}">
									<li class="checkBox positionRelative mb0"><input  type="checkbox" name="clientNameById" data-val = "${team.id}"  value ="${team.locationId.org.orgName}" ><label>${team.locationId.org.orgName}</label></li>
									</c:if>
									
									</c:forEach>
								</ul>
							 </div> 
						</div>
						
							<div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myCityInput" onkeyup="myCityFunction()" placeholder="City" title="Type in a teams">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul varsCheckboxDynamic">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myCitytUL">
									<c:forEach var="city" items="${cityList}">
									
<%-- 									<c:if test="${team.city == loginOrgLocId}"> --%>
									<li class="checkBox positionRelative mb0"><input  type="checkbox" name="cityName" value ="${city}" ><label>${city}</label></li>
<%-- 									</c:if> --%>
									
<%-- 									<c:if test="${team.responderLocationId.id == loginOrgLocId}"> --%>
<%-- 									<li class="checkBox positionRelative"><input  type="checkbox" name="cityNames" value ="${city.locationId.id}" ><label>${city.locationId.id}</label></li> --%>
<%-- 									</c:if> --%>
									
									</c:forEach>
								</ul>
							 </div> 
						</div>
						
			<!-- //projectList -->
			<div class="clearfix"></div>
			
			<div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myProjectInput" onkeyup="myProjectFunction()" placeholder="Projects" title="Type in a teams">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul varsCheckboxDynamic">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myprojectUL">
									<c:forEach var="project" items="${projectList}">
									
									<li class="checkBox positionRelative mb0"><input  type="checkbox" name="projectNameById" value ="${project.id}" ><label>${project.projectId.projectName}</label></li>
									
									<%-- <c:if test="${team.responderLocationId.id == loginOrgLocId}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="clientNameById" value ="${team.locationId.org.orgName}" ><label>${team.locationId.org.orgName}</label></li>
									</c:if> --%>
									
									</c:forEach>
								</ul>
							 </div> 
						</div>
			
			
			</div>
			</div>
			<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" 
								onclick="closeFilter();requirementList('list','${SourceLeadWeb_atsUrl}');" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="resetJobFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
							
							
							
 
</div>
<!-- advanced filter code end.-->