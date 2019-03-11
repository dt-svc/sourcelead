<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead</title>
<link rel="stylesheet" href="/resources/css/cropper.min.css" >
<link rel="stylesheet" href="/resources/css/style.css" />
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<link href="/resources/css/sourcelead-bundle.css" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/timesheet.css" type="text/css">
<!--  scripts are added here  -->

<script src="/resources/js/masterHeaderAction.js"></script>
<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/bootstrap-select.min.js"></script>
<script src="/resources/js/formvalidator.js"></script>
<script src="/resources/js/sourcelead-app.js"></script>
<script src="/resources/js/jquery.dataTables.min.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
<script  src="/resources/js/jobs/js/controls.js"></script>
<script src="/resources/js/cropper.min.js"></script> 
<script src="${SourceLeadWeb_atsUrl}/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="${SourceLeadWeb_atsUrl}/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->
<script src="/resources/js/chosen.jquery.min.js"></script>
<script src="/resources/js/slead-common.js"></script>
<script src="${SourceLeadWeb_atsUrl}/resources/js/requirement.js"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${SourceLeadWeb_googleMapsLocationApiKey}" type="text/javascript"></script>

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
 $(document).ready(function () {
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
function getActions(){// // toggle events are not worked properly thats why kept in jsp.
	$(".action-pop").slideToggle(200);
	$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, #exportPop, #exportPopJobFair").slideUp(200);
	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	});	
}
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	
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
</script>
