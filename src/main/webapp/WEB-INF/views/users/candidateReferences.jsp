<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- these files for autocomplete with tagit -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%-- <jsp:include page="candidatesQuickView.jsp"></jsp:include> --%>
<style>
.addUser-org { overflow-x: auto; }
</style>
<%-- 
     @author : prasadK.
     this script is for display candidate references tab data.
     1.usage     : used at clientSideBar().
--%>
<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
	function candidateReferences(candidateId) {
		var candidateType = '${candidateType}';
		var result = "";

		if (window.candidateStatusGlobal == '0') {

			result = result
					+ '    <div class="project_tabs_inn_head addUser-org overflow_inherint mt25">  ';

		} else {
			result = result
					+ '    <div class="project_tabs_inn_head addUser-org overflow_inherint mt10">  ';
		}
		result = result
				+ '<div class="success_pop" id="selector10" style="display: none;">';
		result = result
				+ '<i class="fa fa-check-circle"></i><span id="refMsgDiv"></span>';
		result = result + '</div>';
		if (window.candidateStatusGlobal == '0') {
			if (candidateType == "Candidates") {
				'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_REFERENCE\')">';
				result = result
						+ '<a href="javascript:void(0)"  class="addUser-org" ></a> ';
				'</sec:authorize>';
			} else {
				'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_REFERENCE\')">';
				result = result
						+ '<a href="javascript:void(0)"  class="addUser-org" ></a> ';
				'</sec:authorize>';
			}

		} else {
			if (candidateType == "Candidates") {
				'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_REFERENCE\')">';
				result = result + '<span class="pad-sea"><a href="javascript:void(0)"  onClick="addReference('+ candidateId + ')" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Reference</a></span>';
				'</sec:authorize>';
				
				'<sec:authorize access="!hasAnyRole(\'ROLE_ADD_CANDIDATE_REFERENCE\')">';
				result = result + '<span class="pad-sea"><a href="javascript:void(0)"  class="addUser-org" ></a></span>';
				'</sec:authorize>';
			} else {
				'<sec:authorize access="hasAnyRole(\'ROLE_ADD_ASSOCIATE_REFERENCE\')">';
				result = result + '<span class="pad-sea"><a href="javascript:void(0)"  onClick="addReference('+ candidateId + ')" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Reference</a></span>';
				'</sec:authorize>';
				
				'<sec:authorize access="!hasAnyRole(\'ROLE_ADD_ASSOCIATE_REFERENCE\')">';
				result = result + '<span class="pad-sea"><a href="javascript:void(0)"   class="addUser-org" ></a></span>';
				'</sec:authorize>';
			}

		}
		result = result
				+ '     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result + '     </div>  ';
		result = result
				+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter">';

		result = result + '   <div id="candidateReferencesList" ></div>';
		result = result + '   </div>';
		result = result + '	    </div>';

		$("#tab-1").empty();
		$("#tab-2").empty();
		$("#tab-3").empty();
		$("#tab-4").empty();
		$("#tab-5").empty();
		$("#tab-6").empty();
		$("#tab-7").empty();
		$("#tab-8").empty();
		$("#tab-9").empty();
		$("#tab-10").empty();
		$("#tab-10").append(result);
		candReferencesList(candidateId);
	}
</script>
<script>
	function candReferencesList(candidateId) {
		var candidateType = '${candidateType}';
		var locationCode = '${SourceLeadWeb_locationCode}';

		$
				.ajax({
					type : "POST",
					url : atsUrl
							+ "/getCandidateReferencesListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="
							+ candidateId + "&locationCode=" + locationCode,
					success : function(json) {

						/*  var json = JSON.parse(data);  */
						$("#candidateReferencesList").empty();
						var result = "";
						result = result
								+ "<table id='candidateReferencesPagination' class='display' cellspacing='0' width='100%' >";
						result = result + "<thead>";
						result = result + "  <tr>";
						result = result + '<th>Name </th>';
						result = result + '<th>Email</th>';
						result = result + '<th>Phone</th>';
						result = result + '<th>Relation </th>';
						result = result + '<th>Designation </th>';
						result = result + '<th>Comment </th>';
						result = result + '<th>Action </th>';
						result = result + "  </tr>";
						result = result + "</thead>";
						result = result + "<tbody>";

						for (var i = 0; i < json.data.length; i++) {
							var opt = json.data[i];
							result = result + ' <tr>';
							if (opt.name != null && opt.name != ''
									&& opt.name != "undefined") {
								result = result
										+ "  <td><div class='tbldata-dotdot-me' title='"+opt.name+"'>"
										+ opt.name + "</div></td>";
							} else {
								result = result + "  <td><div>----</div></td>";
							}

							if (opt.email != null && opt.email != ''
									&& opt.email != 'undefined') {
								result = result
										+ ' <td><div class="tbldata-dotdot-me" title="'+opt.email+'">'
										+ opt.email + '</div></td>';
							} else {
								result = result
										+ ' <td><div><span>----</span></div></td>';
							}

							if (opt.mobilePhone != null
									&& opt.mobilePhone != ''
									&& opt.mobilePhone != 'undefined') {
								result = result
										+ ' <td><div><span title="'+opt.mobilePhone+'">'
										+ opt.mobilePhone
										+ '</span></div></td>';
							} else {
								result = result
										+ ' <td><div><span>----</span></div></td>';
							}

							if (opt.relation != null && opt.relation != ''
									&& opt.relation != 'undefined') {
								result = result
										+ ' <td><div><span title="'+opt.relation+'">'
										+ opt.relation + '</span></div></td>';
							} else {
								result = result
										+ ' <td><div><span>----</span></div></td>';
							}
							if (opt.designation != null && opt.designation != ''&& opt.designation != 'undefined') {
								result = result	+ ' <td><div><span title="'+opt.designation+'">'+ opt.designation + '</span></div></td>';
							   } else {
								result = result	+ ' <td><div><span>----</span></div></td>';
							   }

							if (opt.comment != null && opt.comment != ''
									&& opt.comment != 'undefined') {
								result = result
										+ ' <td><div><span title="'+opt.comment+'">'
										+ opt.comment + '</span></div></td>';
							} else {
								result = result
										+ ' <td><div><span>----</span></div></td>';
							}

							result = result + '<td><div class="tblicons">';
							if (window.candidateStatusGlobal == 0) {
								result = result
										+ '<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
								result = result
										+ '<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
							} else {
								result = result
										+ ' <sec:authorize access="hasAnyRole(\'ROLE_VIEW_AND_EDIT_CANDIDATE_REFERENCE\')"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="editCandidateReference('
										+ candidateId
										+ ','
										+ opt.id
										+ ');editRelation(this);" title="Edit"></a></sec:authorize>';
								result = result
										+ '<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_REFERENCE\')"><i class="glyphicon glyphicon-trash" onclick="removeReference('
										+ candidateId
										+ ','
										+ opt.id
										+ ');" title= "Remove"></i></sec:authorize>';
							}

						}
						result = result + '</div></td>';
						result = result + '</tr>';

						result = result + " </tbody>";
						result = result + "</table>";
						$("#candidateReferencesList").append(result);
						candidateReferencesPagination();
					}
				});
	}
</script>
<%-- 
     @author : prasadK.
     this script is for get candidateReferencesList.
     1.usage     : used in candidateReferences().
     2.parameter : csrf token,candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
	function candidateReferencesList(candidateId) {
		var sourceleadUrl = '${sourceleadUrl}';

	}
</script>

<%-- 
     @author : Prasad K.
     this script is for prepare pagination.
     1.usage     : used in candidateReferencesList().
--%>

<script>
	function candidateReferencesPagination() {
		$('#candidateReferencesPagination').DataTable({
			"scrollCollapse" : false,
			"lengthMenu" : [ [ 10, 15, 20, -1 ], [ 10, 15, 20, "All" ] ],
			"bLengthChange" : true,
			"bDestroy" : true,
			"ordering" : true,
			"aoColumnDefs" : [ {
				'bSortable' : false,
				'aTargets' : 5
			} ],
			"oLanguage" : {
				"sSearch" : "",
				"sZeroRecords" : "",
				"sEmptyTable" : ""
			}
		});
	}
</script>

<%-- 
     @author : Prasad K.
     this script is for remove popUp.
     1.usage   : used in addReference() function.
--%>

<script>
	function closePopup() {
		$("#popUpDiv").removeClass("edit-details-active");
	}
</script>

<script>
	$(function() {
		$("#relation").change(function() {
			$('#row_dim').hide();
			if (this.options[this.selectedIndex].value == "Work Related") {
				$("#row_dim").show();
			}
		});
	});

	
</script>
<%-- 
     @author : prasad K.
     this script is for shown popUp.
     1.usage   : used in addReference() function.
--%>
<script>
	function addReference(candidateId) {
		var result = "";
		result = result + '<!-- Popup Div -->';
		result = result
				+ '<form role="form"  id="canReference_form" name="canReference_form" method="post" > ';
		result = result
				+ '<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
		result = result + '<div class="form-horizontal clearfix"> ';
		result = result + '   ';

		result = result + '<div class="clearfix mb10">';
		result = result + '<div class="col-md-12">';
		result = result
				+ '<input type="text" name="name" field-name="Name" data-validation="required validate_Space validate_length length1-200" class="form-control input-sm" value="" placeholder="Name *">';
		result = result + '</div>';
		result = result + '</div>';

		result = result + '<div class="clearfix mb">';
		result = result + '<div class="col-md-12" id="emailDivId">';
		
		result = result + '<div class="input-group errorInptgroup">';
		result = result+ '<input type="text" name="email" field-name="Email" id="email" data-validation="required validate_Space validate_email validate_length length9-100" class="form-control input-sm" value="" placeholder="Email *" onblur="validateReferenceEmail('+candidateId+');">';
		result = result + ' <span id="emailStatusIconDiv" class="input-group-addon">';
		result = result + '<i class="fa fa-check text-muted" style="color:#f6f6f6">';
		result = result + '</i>';
		result = result + '</span>';
	  	result = result + '</div>';
		
		result = result + '</div>';
		result = result + '</div>';

		result = result + '<div class="clearfix mb10">';
		result = result + '<div class="col-md-12">';
		result = result
				+ '<input type="text" name="mobilePhone" field-name="MobilePhone" data-validation="validate_length length9-20 validate_phone" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Mobile Phone">';
		result = result + '</div>';
		result = result + '</div>';

		result = result + '<div class="clearfix mb10">';
		result = result + '<div class="col-md-12">';
		result = result
				+ '<select class="form-control width100percnt" id="relation" name="relation" field-name="Relation" onChange="getRelation();">';
		result = result + '<option value="">--Select Relation--</option>';
		result = result
				+ ' <option id="workRelated" valeu="Work Related">Work Related</option>';
		result = result
				+ '<option  id="familyRelated" value="Family Related">Family Related</option>';
		result = result
				+ '<option  id="friend" value="Friend">Friend</option>';
		result = result
				+ '<option  id="neighbour" value="Neighbour">Neighbour</option>';
		result = result + ' </select>';
		result = result + '</div>';
		result = result + '</div>';

		result = result
				+ '<div class="clearfix mb10" id="dvDesignation" style="display: none;">';
		result = result + '<div class="col-md-12" >';
		result = result
				+ '<input type="text" name="designation" id="designationVal" field-name="Designation"  class="form-control input-sm" value="" placeholder="Designation *">';
		result = result + '</div>';
		result = result + '</div>';

		result = result + '<div class="clearfix mb10 mt25">';
		result = result + '<div class="col-md-12">';
		result = result
				+ '<textarea rows="2" placeholder="Comment" value="" id="comment" name="comment" data-validation-optional="true" data-validation="validate_length length1-500"  field-name="comment" class="form-control"></textarea>';
		result = result + '</div>';
		result = result + '</div>';

		result = result
				+ '<div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
		result = result
				+ '<button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
		result = result + '</div>';

		result = result + '<div class="clearfix mr" id="docAddButton">';
		result = result
				+ '<input type="button" value="Save" name="btnAddPr" id="save_ref" class="btn btn-sm btn-primary pull-right" onClick ="storeCandidateReferences('
				+ candidateId + ');" />';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</form>';
		$("#popUpDiv").empty();
		$("#popUpDiv").append(result);

		$(".edit-details").toggleClass("edit-details-active");
		$(".close-div").click(
				function() {
					$(this).parent("div.edit-details").removeClass(
							"edit-details-active");
				});
		$(
				".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details")
				.click(function(e) {
					e.stopPropagation();
				});

	}
</script>
<script>
function validateReferenceEmail(candidateId,referId){
	if(referId != null && referId != "" && referId != "undefined"){
		referId = referId;
	}else{
		referId = "";
	}
	var email = $("#email").val();
	 $('#emailDivId').find('p.jquery_form_error_message').remove();
	 if (email != null && email != "") {
	$.ajax({
			type : "POST",
			url : atsUrl + "/validateReferEmail?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&referId="+referId+"&email="+email,
			success : function(response) {
				if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					if(response.object == true){
		        		  $('#emailDivId').find('p.jquery_form_error_message').remove();
						  $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
						  document.getElementById("email").setAttribute('record-exist','yes');
						  document.getElementById("email").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
		        	} else {
		        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#emailDivId').find('p.jquery_form_error_message').remove(); 
		        		 validateEmailForExistenceUniversally(email, "input#email");
		        	}
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				}
			},
		});
	 }else{
		 $("#emailStatusIconDiv").find("i").remove();
   		 $("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
	 }
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

<%-- 
     @author : prasad K
     this script is for saving document
     1.usage     : onclick
     2.parameter : candidateId.
     3.ajax url implementation in CandidateReferencesRestController.
--%>
<script type="text/javascript">
	function storeCandidateReferences(candidateId) {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#canReference_form').validate(false, validationSettings)) {
			    var formData = getFormData("canReference_form");
			    var userId = '${SourceLeadWeb_user.id}';
			    var locationCode = '${SourceLeadWeb_locationCode}';
				$("#save_ref").attr("disabled", "disabled");
				$("#save_ref").addClass('button').val('Processing..'); 
		        $.post(atsUrl+"/saveCandidateReferences?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&userId="+userId+"&locationCode="+locationCode, formData, function(response) {
		        	if (response && response.response === "INVALID_DATA") {
		        	    showError(response.responseMessage);
		        	} else if (response && response.response === "INVALID_USER") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		closePopup();
			        	$("#popUpDiv").removeClass("edit-details-active");
						$("#selector10").removeAttr("style");
						$("#selector10").css({display : "block"	});
						$("#refMsgDiv").html("Success!! New References added");
			        	candReferencesList(candidateId);
						refMsgFadein(); 
						setTimeout(function(){
				 			candidateList();					 			
				 	     },100);
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        });
		        
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#canReference_form', function() {
		$("#canReference_form").showHelpOnFocus();
		$("#canReference_form").validateOnBlur(false, validationSettings);
	});
</script>

<%-- 
     @author : Prasad K
     this script is for get document details in edit mode
     1.usage     : onclick
     2.parameter : candidateId, documentId
     3.ajax url implementation in CandidateResumeRestController.
--%>

<script type="text/javascript">
	function deleteReferencesPopup() {
		$("#remove_user_pop_reference, .project_inn_mask").fadeOut();
	}
</script>

<div id="deleteReferencesPopup"></div>


<script>
	function refMsgFadein() {
		$("#selector10").fadeIn(200);
		$('#selector10').delay(2000).fadeOut('slow');
	}
</script>
<!-- End -->

<script type="text/javascript">
	function removeReference(candidateId, refId) {

		var result = "";
		result = result + "	<div class='project_inn_mask'></div>";
		result = result
				+ "	   <div id='remove_user_pop_reference' class='remove_user_pop remove_user_pop_a'>";
		result = result + "		<p>You are choosing to delete the Reference</p>";
		result = result + "		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result
				+ "<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteReference("
				+ candidateId
				+ ","
				+ refId
				+ ");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteReferencesPopup();'></b>";
		result = result + "	</div>";
		$("#deleteReferencesPopup").empty();
		$("#deleteReferencesPopup").append(result);
		$("#remove_user_pop_reference").slideDown(200);
		$(".project_inn_mask").fadeIn(200);

	}
</script>
<%-- 
     @author : prasad K
     this script is for terminate References (inactive status)
     1.usage     : onclick
     2.parameter : candidateId, documentId
     3.ajax url implementation in CandidateReferencesRestController.
--%>
<script>
	function deleteReference(candidateId, refId) {
		var loginUserId = '${SourceLeadWeb_userId}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		//var message = confirm("You are choosing to delete the reference");
		var userCode = '${SourceLeadWeb_userCode}';
		$
				.ajax({
					type : "POST",
					url : atsUrl
							+ "/terminateReference?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="
							+ candidateId + "&refId=" + refId + "&userCode="
							+ userCode,
					success : function(response) {
						if (response.response === "AWKWARD") {
                            location.href = "/errorPage";
                        } else if (response.response === "SUCCESS") {
                        	$("#selector10").removeAttr("style");
							$("#selector10").css({
								display : "block"
							});
							$("#refMsgDiv").html("Reference deleted");
							$("#popUpDiv").removeClass("edit-details-active");
							$("#remove_user_pop_reference, .project_inn_mask")
									.fadeOut();
							candReferencesList(candidateId);
							refMsgFadein();
							setTimeout(function(){
					 			candidateList();					 			
					 	 },100);               
                        } else if (response.indexOf('Sign In') !== -1) {
                            location.replace(sourceleadUrl);
                        } else {
                            showError("Operation failed, please try again");
                        }
					},
				});

	}
</script>

<script type="text/javascript">
	function updateCandidateReference(candidateId, refId) {

		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		
		if ($('#canReferenceEdit_form').validate(false, validationSettings)) {

			
			var formData = getFormData("canReferenceEdit_form");
		    var userId = '${SourceLeadWeb_user.id}';
		    var locationCode = '${SourceLeadWeb_locationCode}';
			
			 $("#save_ref").attr("disabled", "disabled");
			$("#save_ref").addClass('button').val('Processing..'); 
	        $.post(atsUrl+"/updateCandidateReferenceBycandidateIdAndReferenceId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&userId="+userId+"&locationCode="+locationCode, formData, function(result) {
	        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
	    		    showError(result.responseMessage);
	    		} else if (result.response === "AWKWARD") {
	    		    location.href = "/errorPage";
	    		} else if (result.response === "SUCCESS") {
	    			$("#selector10").removeAttr("style");
					$("#selector10").css({display : "block"});
					$("#refMsgDiv").html("Reference details updated");
					closePopup();
					candReferencesList(candidateId);
					refMsgFadein();
	    		} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				}
	        });
			return true;
		} else {
			return false;
		}
		$('body').on(
				'blur',
				'#canReference_form',
				function() {
					$("#canReferenceEdit_form").showHelpOnFocus();
					$("#canReferenceEdit_form").validateOnBlur(false,
							validationSettings);
				});
	}
</script>

<script>
	function editCandidateReference(candidateId, refId) {
		$.ajax({
			type : "POST",
			url : atsUrl
					+ "/getCandidateReferencesByCandidateIdAndReferenceId?"+tokenParamName+"="+tokenValue+"&candidateId="
					+ candidateId + "&refId=" + refId,
			success : function(response) {
				if (response && response.response === "INVALID_DATA") {
				    showError(response.responseMessage);
				} else if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					var json = response.object;
					var result = '';
					result = result
							+ '     <!-- Popup Div -->                                                                                         ';
					result = result
							+ '         <form id="canReferenceEdit_form" name="canReferenceEdit_form" method="post">        ';
					result = result
							+ '<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
					result = result
							+ '<div class="form-horizontal clearfix"> ';
					result = result + '   ';

					result = result
							+ '<div class="clearfix mb10">';
					result = result
							+ '<div class="col-md-12">';
					result = result
							+ '<input type="text" name="name" field-name="Name" value="'+json.name+'" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Name *">';
					result = result + '</div>';
					result = result + '</div>';

					
					result = result+ '<div class="clearfix mb10">';
					result = result+ '<div class="col-md-12" id="emailDivId">';
					result = result + '<div class="input-group errorInptgroup">';
					result = result+ '<input type="text" name="email" id="email" field-name="Email" value="'+json.email+'" data-validation="required validate_Space validate_email validate_length length9-100" class="form-control input-sm" value="" placeholder="Email *" onblur="validateReferenceEmail('+candidateId+','+json.id+');">';
					result = result + ' <span id="emailStatusIconDiv" class="input-group-addon">';
					result = result + '<i class="fa fa-check text-muted" style="color:#f6f6f6">';
					result = result + '</i>';
					result = result + '</span>';
					result = result + '</div>';
					result = result + '</div>';
					result = result + '</div>';

					result = result
							+ '<div class="clearfix mb10">';
					result = result
							+ '<div class="col-md-12">';
					result = result
							+ '<input type="text" name="mobilePhone" field-name="MobilePhone" value="'+json.mobilePhone+'" data-validation="validate_length length9-20 validate_phone" data-validation-optional="true" class="form-control input-sm" value="" placeholder="Mobile Phone">';
					result = result + '</div>';
					result = result + '</div>';

					result = result
							+ '<div class="clearfix mb10">';
					result = result
							+ '<div class="col-md-12">';
					result = result
							+ '<select class="form-control width100percnt" id="relation" name="relation" field-name="Relation" onChange="editRelation(this);" onkeyup="editRelation(this);">';
					result = result
							+ '<option value="">--Select Relation--</option>';
					result = result
							+ ' <option '
							+ (json.relation == "Work Related" ? "selected"
									: '')
							+ '>Work Related</option>';
					result = result
							+ '<option  '
							+ (json.relation == "Family Related" ? "selected"
									: '')
							+ '>Family Related</option>';

					result = result
							+ '<option  '
							+ (json.relation == "Friend" ? "selected"
									: '')
							+ '>Friend</option>';
							
					result = result
							+ '<option  '
							+ (json.relation == "Neighbour" ? "selected"
									: '')
							+ '>Neighbour</option>';
					result = result + ' </select>';
					result = result + '</div>';
					result = result + '</div>';

					result = result
							+ '<div class="clearfix mb10" id="dvDesignationEdit" style="display:none">';
					result = result
							+ '<div class="col-md-12">';
					result = result
							+ '<input type="text" name="designation" id="designationValEdit" field-name="Designation" value="'+json.designation+'" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Designation *">';
					result = result + '</div>';
					result = result + '</div>';

					result = result
							+ '<div class="clearfix mb10 mt25">';
					result = result
							+ '<div class="col-md-12">';
					result = result
							+ '<textarea rows="2" placeholder="Comment" value="+json.comment" id="comment" name="comment" data-validation-optional="true" data-validation="validate_length length1-500"  field-name="comment" class="form-control">'
							+ json.comment + '</textarea>';
					result = result + '</div>';
					result = result + '</div>';

					result = result
							+ '<div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
					result = result
							+ '<button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
					result = result + '</div>';

					result = result
							+ '<div class="clearfix mr" id="docAddButton">';
					result = result
							+ '<input type="hidden"  value="'+refId+'" id="refId" name="refId">';
					result = result
							+ '<input type="hidden"  value="'+candidateId+'" id="candidateId" name="candidateId">';

					result = result
							+ '<button type="button"  id="addCandidate"  onClick ="updateCandidateReference('
							+ candidateId
							+ ','
							+ refId
							+ ');" class="btn btn-sm btn-primary pull-right"  >Update</button>';

					result = result + '</div>';
					result = result + '</div>';
					result = result + '</form>';
					$("#popUpDiv").empty();
					$("#popUpDiv").append(result);

					$(".edit-details").toggleClass(
							"edit-details-active");
					$(".close-div")
							.click(
									function() {
										$(this)
												.parent(
														"div.edit-details")
												.removeClass(
														"edit-details-active");
									});
					$(
							".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details")
							.click(function(e) {
								e.stopPropagation();
							});
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				}

			},
			}).done(function() {
				editRelation(this);
			});
	}
</script>

<!-- If Selected value is Work Related then Designation text box will appear -->
<script type="text/javascript">
	function getRelation() {
		var relation = $("#relation").val();
		
		if (relation == 'Work Related') {
			$('#designationVal').val('');
			$('#designationVal').attr("data-validation",
					"required validate_Space validate_length length2-100");
			$("#dvDesignation").show();
		} else {
			$('#designationVal').val('');
			$("#designationVal").attr("data-validation-optional", "true");
			$("#dvDesignation").hide();
		}
	}
</script>

<script>
	function editRelation(e) {
		var relationEdittype = $("#relation").val();
		if (relationEdittype == 'Work Related') {
			document.getElementById("dvDesignationEdit").style.display = "block";
			$("#designationValEdit").removeAttr("data-validation-optional");
			$('#designationValEdit').attr("data-validation","required validate_Space validate_length length2-100"); 
			$("#designationValEdit").show();
		} else {
			document.getElementById("dvDesignationEdit").style.display = "none";
			$("#designationValEdit").val('');
			$('#designationValEdit').find('p.jquery_form_error_message').remove();
			$('#designationValEdit').removeAttr("data-validation");
		} 
	}
</script>
