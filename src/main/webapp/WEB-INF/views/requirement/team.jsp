<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- end files for autocomplete with tagit -->
<script>
 window.onload = function() { 
 	 teamList();
};
</script>

<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script>
function teamList(){  
	  $("#teamList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
		  type : "POST",
            url: sourceleadUrl+"/getTeamListByOrgLocationID?"+tokenParamName+"="+tokenValue+"",       
            success :function(data) {
                /*  if(data != null && data != 'undefined' && data != "" && data != '[]'){ */
			    	     $("#teamList").removeAttr("style");
	                	 $("#teamList").empty();
	                	 var result = "";
	                	 result = result +"<table id='teamPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Team Name</th>"; 
	                     result = result +"    <th>Team Leader</th>"; 
	                     result = result +"    <th>Members</th>"; 
	                     result = result +"    <th>Assigned Hotlist</th>";
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr id='deleteTeam"+opt.id+"'>";
				             
				             result = result +' <td><div>'+opt.name+'</div></td>';
				             
				             if(opt.teamOwnerName != null && opt.teamOwnerName != '' && opt.teamOwnerName != 'undefined'){
				             result = result +"<td><div><span>"+opt.teamOwnerName+"</span></div></td>";
				             }else{
				            	 result = result +"<td>-----</td>";
				             }
		            	     result = result +"<td><div><span>"+opt.teamMembersCount+"</span></div></td>";
		            	     result = result +' <td><div onclick="getCandidateListForTeam('+opt.id+',\''+opt.name+'\');" class="proj_show"><a>'+opt.candidateHotlistCount+'</a></div></td>';
				             result = result +"<td><div class='tblicons'>";
				             '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_TEAM\')">'; 
				             	result = result +"<i class='glyphicon glyphicon-pencil paddingRight' title='Edit' onclick='sideBar("+opt.id+");'></i>";
				             '</sec:authorize>';
				             '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_TEAM\')">'; 
				             	result = result +'<i class="glyphicon glyphicon-trash paddingLeft" title="Delete" onclick="deleteTeam('+opt.id+',\''+opt.name+'\');"></i>';
				             '</sec:authorize>';
				             result = result +"</div></td>";
				             result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#teamList").empty();
	                     $("#teamList").append(result); 
	                     pagination(); 
          },
            
       }); 
  } 
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to requirementList.
     1.usage     : used in candidateList() function.
--%>

<script>
function pagination(){
    $('#teamPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at candidateList() function.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function sideBar(teamId){
	
	      
	    var outPut = '';
	    outPut = outPut+'<div id="removeCandidateAlert" ></div>  ';
		outPut = outPut+ '	<div class="project_side_wrap_inn_tabs">';
		outPut = outPut+ '    <div class="project_tabs">';
		outPut = outPut+ '      <div id="tab-1" class="project_tabs_inn">';
		outPut = outPut+ '        <div  class="project_tabs_inn_head"> <a href="#" class="addUser-org" onClick="addMember('+teamId+');getTeamMembersListExceptExistedTeamMembersList('+teamId+');"> <i class="glyphicon glyphicon-plus"></i>Add Member</a> ';
		
		outPut = outPut  +'     <div id="popUpDivTeam" class="edit-details col-md-4"> ';
		outPut = outPut  +'     </div>  ';
		outPut = outPut  +'     </div>  ';
				
		outPut = outPut+ ' <div class="project_tabs_inn_table vendor_wrap_inn_table vendorwrap_inner_table">';
		
		outPut = outPut+ ' <div id="teamEditDiv"></div>';
		outPut = outPut+ '<input type = "hidden" id="existedMemberId" name="existedMemberId"/>';
		outPut = outPut+ ' <div id="projectMemberList"></div>';
		outPut = outPut+ '        </div>';
		outPut = outPut+ '      </div>';
		outPut = outPut+ '    </div>';
		
		outPut = outPut+ '  </div>';
		
		$("#teamSideDiv").empty();  
	    $("#teamSideDiv").append(outPut);
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		$("#tab-1").show();
		projectMemberList(teamId);
		teamEditDetails(teamId);
}
</script>

<%-- 
     @author : shivayogi
     this script is for Edit team Details
     1.usage     : used in projectMemberList();
--%>
<script type="text/javascript">
function teamEditDetails(teamId){
	  $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getTeamByTeamID?"+tokenParamName+"="+tokenValue+"&teamId="+teamId,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var outPut = '';
	        	outPut = outPut+'<form role="form"  id="edit_team_form" name="team_form" method="post" > ';
	        	outPut = outPut+ '<div class="form-horizontal clearfix teamEditDetails">';
	        	outPut = outPut+ '<div class="clearfix mb10">';
	        	outPut = outPut+ '<div class="col-md-6" id="teamNameDiv">';
	        	outPut = outPut +'<input type="hidden" class="form-control input-sm" name="teamId"  value="'+teamId+'">';
	        	outPut = outPut+ '<input type = "hidden" name="tabType" value="editTeamForm"/>';
	        	outPut = outPut+ '<label>Team Name <b class="valStar">*</b> </label>';
	        	outPut = outPut+ '<input type="text" class="form-control"  placeholder="Team Name" id="candidateName" name="name" data-validation="required validate_Space validate_AlphaNumaric validate_Name validate_length length2-100" field-name="Team Name" value="'+json.name+'"  onblur="validateTeamName();">';
	        	outPut = outPut+ '                </div>';
	        	
	        	
	        	outPut = outPut+ '                <div class="col-md-5">';
	        	outPut = outPut+ '                <label>Team Lead <b class="valStar">*</b> </label>';
	        	outPut = outPut + '<select   class="form-control" name="editOwnerUserId" value="'+json.teamOwnerName+'" id="editOwnerUserId" placeholder="Team Lead *" data-validation="required validate_Space" field-name="Team Lead">';
	            outPut = outPut + '</select>';
	        	outPut = outPut+ '                </div>';
	        	outPut = outPut+ '                <div class="col-md-1">';
	        	outPut = outPut+ '                <label class="col-md-12">&nbsp;</label>';
	        	outPut = outPut+ '<button type="button" id="edit_team" class="btn btn-primary btn-sm btn-block" onclick="storeEditedTeam(\''+teamId+'\');">Add</button>';
	        	outPut = outPut+ '              </div>';
	        	outPut = outPut+ '              </div>';
	        	outPut = outPut+ '              </div> ';
	        	outPut = outPut+ '              <div class="clearfix mr">';
	        	/* outPut = outPut+ '<button type="button" id="edit_team" class="btn btn-sm btn-primary pull-left" onclick="storeEditedTeam();">Add</button>'; */
	        	outPut = outPut+ '              </div>';
	        	outPut = outPut+ '            </div>'
	        	outPut = outPut+ '</form>';
	        	
	        	outPut = outPut+ '  </hr>';
	        	outPut = outPut+ '  </hr>';
	        	outPut = outPut+ '  </hr>';
	        	$("#teamEditDiv").empty();
	        	$("#teamEditDiv").append(outPut); 
	        	getEditTeamOwners(json.ownerUserId.id,json.teamOwnerName,teamId);
	        },
	        error: function(e){
	            
	        }
	    }); 
}
</script>

<%-- 
     @author : shivayogi
     this script is for get team owners
     1.usage     : used in teamEditDetails();
--%>
<script type="text/javascript">
function getEditTeamOwners(teamOwnerUserId,teamOwnerName,teamId){ 
	/*  var editOwnerUserId = document.getElementById("editOwnerUserId").value; */
	 var existedTeamMembersIds = [];
	 $('.existedTeamMember').each(function() {
		 var localId = this.value;
		 existedTeamMembersIds.push(localId);
	 });  
      
     $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getTeamMembersListExceptExistedTeamMembersList?"+tokenParamName+"="+tokenValue+"&existedTeamMembersIds="+existedTeamMembersIds+"&teamId="+teamId,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option disabled >-Select Team Members-</option>"; 
	        	output = output + "<option selected value="+teamOwnerUserId+" >"+teamOwnerName+"</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		
		        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
		        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+ opt.lastName+"</option>";
		        		}else{
		        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"</option>";
		        		}
				}
	        	$("#editOwnerUserId").empty();
		        $("#editOwnerUserId").append(output);
		        $("#editOwnerUserId").trigger("chosen:updated");
	        },
	        error: function(e){
	            
	        }
});
     
} 
</script>

<script>
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
     };
</script>

<script>
function storeEditedTeam(teamId){
	
	   if ($('#edit_team_form').validate(false, validationSettings)){ 
		   
		    var formData = getFormData("edit_team_form");
			$("#team_form").attr("disabled", "disabled");
	       
			$.post(sourceleadUrl+"/storeEditedTeam?"+tokenParamName+"="+tokenValue+"", formData, function(data) {
				if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
	        		showError(data.responseMessage);
	    		}
        		else if (data.response === "AWKWARD") {
        			location.href = "/errorPage";
	    		}
        		else if(data.response === "SUCCESS"){
        			sideBar(teamId);
  				  	teamList();
	        	    }
        		else if (data.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
	        });
		   
			return true; 
	   }else{ 
			return false;
	        } 
		} 
 
		$('body').on('blur', '#team_form', function() {
			$("#team_form").showHelpOnFocus();
			$("#team_form").validateOnBlur(false, validationSettings);
		});

</script>  

<script>
function addTeam(){ 
	var outPut = '';
	outPut = outPut+'<form role="form"  id="team_form" name="team_form" method="post" > ';
	outPut = outPut+ '<div class="popupHead">Add Team <i class="closePop pull-right popClose" onclick="closeTeamPopup();"></i></div>';
	
	outPut = outPut+ '<div class="popupContent clearfix">';
	outPut = outPut+ '<div class="clearfix mb">';
	/* outPut = outPut + '<input type="hidden" class="form-control input-sm" name="teamId"  value="'+teamId+'">'; */
	outPut = outPut+ '<input type = "hidden" name="tabType" value="teamForm"/>';
	outPut = outPut+ '<div class="">';
	outPut = outPut+ '<label>Team Name <b class="valStar">*</b></label>';
	outPut = outPut+ '<input type="text" class="form-control"  placeholder="Team Name" id="candidateName" name="name" data-validation="required validate_Space validate_AlphaNumaric validate_length length2-100" field-name="Team Name" value="" onblur="validateTeamName();">';
	outPut = outPut+ '</div>';
	outPut = outPut+ '</div>';
	
	outPut = outPut+ '              <div class="clearfix mb">';
	outPut = outPut+ '                <div class="">';
	outPut = outPut+ '                <label>Team Lead <b class="valStar">*</b></label>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '                <div class="">';
	outPut = outPut + '<select class="form-control" name="ownerUserId" id="ownerUserId" placeholder="Team Lead *" data-validation="required validate_Space" field-name="Team Lead"  onchange="getTeamMembers();">';
    outPut = outPut + '</select>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '              </div> ';
	
	outPut = outPut+ '              <div class="clearfix mb">';
	outPut = outPut+ '                <div class="">';
	outPut = outPut+ '                <label>Team Member <b class="valStar">*</b></label>';
	outPut = outPut + '<select multiple class="chosen-select form-control" name="memberUserId" id="memberUserId" data-placeholder="Select Team Members" data-validation="required validate_Space" field-name="Team Member">';
    outPut = outPut + '</select>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '              </div> ';
	
	outPut = outPut+ '            </div>';
	
	outPut = outPut+ ' <div class="popupFoot text-right">';
	outPut = outPut+ ' <button type="button" id="add_team" class="slBtn md colorBtn" onclick="storeTeam();">Add</button>';	
	outPut = outPut+ ' <input type="button" value="Cancel" class="slBtn md popClose" onclick="closeTeamPopup();">';
	outPut = outPut+ ' </div>';
	
/* 	outPut = outPut+ '              <div class="clearfix mr">';
	outPut = outPut+ '<button type="button" id="add_team" class="btn btn-sm btn-primary pull-right" onclick="storeTeam();">Add</button>';
	outPut = outPut+ '              </div>'; */
	
	outPut = outPut+ '</form>';
	$("#popUpDivTeamParent").empty();
	$("#popUpDivTeamParent").append(outPut); 
	
	$("#popUpDivTeamParent").fadeIn(100);
	$(".mask").show();
	$(".popClose").click(function(){
		$("#popUpDivTeamParent").fadeOut(100);
		$(".mask").hide();
	});
	 
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
          });
	
   }
</script>

<%-- 
     @author : prasad K.
     1.usage     : called at closeTeamPopup() function.
     3.close Create.
--%>

<script>
function closeTeamPopup(){
		$(".mask").hide();
	 $("#popUpDivTeamParent").fadeOut(100);
}
</script>

<script>
function closePopup(){
	 $("#popUpDivTeam").removeClass("edit-details-active");
}
</script>

<script>
function addMember(teamId){
	var outPut = '';
	outPut = outPut+'          <form role="form"  id="teamMember_form" name="teamMember_form" method="post" > ';
	outPut = outPut+ '<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	outPut = outPut+ '            <div class="form-horizontal clearfix">';
	outPut = outPut +'<input type="hidden" class="form-control input-sm" name="teamId"  value="'+teamId+'">';
	outPut = outPut+ '              <div class="clearfix mb10">';
	outPut = outPut+ '                <div class="col-md-12">';
	outPut = outPut + '<select multiple class="chosen-select form-control" name="teamMemberUserId" id="teamMemberUserId" data-validation="required validate_Space" field-name="Team Member">';
     outPut = outPut + '</select>';
	outPut = outPut+ '                </div>';
	outPut = outPut+ '              </div>';
	outPut = outPut+ '              <div class="clearfix mr">';
	outPut = outPut+ '<button type="button" id="add_teamMember" class="btn btn-sm btn-primary pull-right" onclick="storeTeamMembers('+teamId+');">Add</button>';
	outPut = outPut+ '</div>';
	outPut = outPut+ '</div>';
	outPut = outPut+ '</form>';
		
	$("#popUpDivTeam").empty();
	$("#popUpDivTeam").append(outPut); 
	$("#popUpDivTeam").addClass("edit-details-active"); 
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
	 $( function() {
		    $( "#edu-startDate, #edu-endDate" ).datepicker();
		  });
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

		});
	
}
</script>

<script>
function projectMemberList(teamId){
		  $("#projectMemberList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  $.ajax({
	           type : "POST",
	            url: sourceleadUrl+"/getTeamMembersListByTeamID?"+tokenParamName+"="+tokenValue+"&teamId="+teamId,       
	            success :function(data) {
	                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
				    	     $("#projectMemberList").removeAttr("style");
		                	 $("#projectMemberList").empty();
		                	 
		                	 var result = "";
		                	
		                	 result = result +"";
		                	 
		                	 result = result +"<table id='teamMemberPagination' class='display' cellspacing='0' width='100%' >"; 
		                     result = result +"<thead>"; 
		                     result = result+ '              <tr>';
		                     result = result+ '                <th>Name</th>';
		                     result = result+ '                <th>Email ID</th>';
		                     result = result+ '                <th>Role</th>';
		                     result = result+ '                <th>Jobs</th>';
		                     result = result+ '                <th>Active<br>Jobs</th>';
		                     result = result+ '                <th>Action</th>';
		                     result = result+ '              </tr>';
		                     result = result +"</thead>";
		                     result = result +"<tbody>"; 
		                     
		                     var json = JSON.parse(data);
		                     
		                     var existedMemId = [];
		                     for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i];
		                    	 var  output = "";
		                    	 existedMemId.push(opt.teamMemberUserId);
		                    	 result = result+ '<tr>';
		                    	 if(opt.memberName != null && opt.memberName != '' && opt.memberName !="undefined"){
		                    	 result = result+ '<td><div><span>'+opt.memberName+'</span></div></td>';
		                    	 }else{
		                    		 result = result+ '<td><div><span>-----</span></div></td>'; 
		                    	 }
		                    	 result = result+ '<input class="existedTeamMember" type="hidden" name="existedTeamMember" id="existedTeamMember" value="'+existedMemId+'"/>'; 
		                    	 if(opt.email != null && opt.email != '' && opt.email !="undefined"){
		                    	 result = result+ '<td><div><span>'+opt.email+'</span></div></td>';
		                    	 }else{
		                    		 result = result+ '<td><div><span>-----</span></div></td>'; 
		                    	 }
		                    	 if(opt.userType != null && opt.userType != '' && opt.userType !="undefined"){
		                    	 result = result+ '<td><div><span>'+opt.userType+'</span></div></td>';
		                    	 }else{
		                    		 result = result+ '<td><div><span>-----</span></div></td>'; 
		                    	 }
		                    	 result = result+ '<td><div><span>'+opt.teamMemberRequirementAssignedCount+'</span></div></td>';
		                    	 result = result+ '<td><div><span>'+opt.teamMemberRequirementAssignedOpenCount+'</span></div></td>';
		                    	 result = result+ '<td><div><i class="glyphicon glyphicon-trash" onclick="removeProjectMemberAlertMessage('+opt.id+','+teamId+');"></i></div></td>';
		                    	 result = result+ '</tr>';
		                     }
		                     result = result +" </tbody>";
		                     result = result +"</table>"; 
		                     $("#existedMemberId").val(existedMemId);
		                     $("#projectMemberList").append(result); 
		                     TeamMemberpagination(); 
		                     teamEditDetails(teamId);
	                 }else{
	                	 $("#projectMemberList").removeAttr("style");
	  	    	         var result ="";
			    	    
						 result = result+"			<div class='blank-pages'>";
						 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
						 result = result+"			        <p>No Team Member Found</p>";
						 result = result+"			 </div>";
						
						$("#projectMemberList").empty();
					    $("#projectMemberList").append(result); 
	                }
	          },
	            
	       }); 
}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function removeProjectMemberAlertMessage(teamMemberId,teamId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' onkeypress='teamMemberRemoveSpaceComment();' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"	<div id='reasonDiv'/>";
	result = result +"		<b href='#'><input id='confirm-id' type='button' value='Ok' onclick='deleteTeamMember("+teamMemberId+","+teamId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result +"	</div>";
	result = result +"	</div>";
	$("#removeCandidateAlert").append(result);
	$("#remove_user_pop").slideDown(200); 
	$(".project_inn_mask").fadeIn(200);
}
</script>
<script>
   function teamMemberRemoveSpaceComment() {
	    var output = document.getElementById("rejectReason").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#rejectReason").removeAttr("data-validation");
	    }
	}
</script>
<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function cancelAlert(){ 
	$("#removeCandidateAlert").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token, candidateId
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function deleteTeamMember(teamMemberId,teamId){
	  var reason = document.getElementById("rejectReason").value;
	  reason = reason.replace(/\s/g, ""); 
	  //var reason1 = document.getElementById("rejectReason").value;
	  if(reason.match(/^[^\s-](.*)*$/) != null){
		 $('#reasonDiv').empty();
		 $.ajax({
        type : "POST",
         url: sourceleadUrl+"/deleteTeamMemberByCandidateId?"+tokenParamName+"="+tokenValue+"&teamMemberId="+teamMemberId+"&reason="+reason,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != ""){
            			 projectMemberList(teamId);
            			 $("#removeCandidateAlert").empty();
            			 $(".project_inn_mask").fadeOut(200);
                }
                 }, 
              }); 
	  }else{
			if(reason.length == 0 ){
				$("#reasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ;
			}
		}
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to requirementList.
     1.usage     : used in candidateList() function.
--%>

<script>
function TeamMemberpagination(){ 
    $('#teamMemberPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<section class="content-wraper">

  <section class="content-header clearfix">
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
    <div class="pull-left">
      <h2>Team</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo; <a href="${SourceLeadWeb_sourceleadUrl}/orgRolesList"> Roles List </a>&raquo; Team</div>
  </section>
  
  <div class="timesheet_out">
    <div class="vendor_wrap">
    
<div class="reportHead reportHeadDataTab  clearfix">
      <ul class="pull-right list-unstyled list-inline">
          <sec:authorize access="hasAnyRole('ROLE_ADD_TEAM')">
<!--        <input type = "hidden" id="existedMemberId" name="existedMemberId"/> -->
          	<li><a href="javascript:void(0)" onclick="addTeam();getTeamOwners();" title="Add Team" class="action newAdBtn">+ Team</a></li>
          </sec:authorize>			
        </ul>
      </div>    
      
      <div class="project_tabs_inn_table vendor_wrap_inn_table vendorwrap_inner_table">
       			<div id="popUpDivTeamParent" class="popupWrap popupWrapFixed"></div>  
      			<div id="teamList" class="datatable_filter dataSeaTop"></div>
      			<div id="deleteTeamPopUp"></div>
              </div>
        </div>      
  
    <!-- Compnay details -->
    <div class="side_mask"><div class="proj_close"></div></div>
    <div class="clients_side_wrap">
      <!-- <div class="clients_side_wrap_inn candidate_wrap_table"> -->
      <div class="clients_side_wrap_inn candidate_wrap_table candidatewrap_Table">
      <div id="teamSideDiv" class="datatable_filter">
	  </div>
      
      </div>
      
    </div>
  </div>
</section>

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 
 
<script>
function storeTeam(){
	
	   var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };
	   if ($('#team_form').validate(false, validationSettings)){ 
		   
		   var formData = getFormData1("team_form");
			$("#add_team").attr("disabled", "disabled"); 
		         
		         $.ajax({
					 type : "POST",
					 data: formData,
	  	    	     contentType : 'application/json; charset=utf-8',
	  		         processData: false,
	  		         contentType:false, 
					url : sourceleadUrl+ "/storeTeam?"+tokenParamName+"="+tokenValue+"",
					success : function(data) {
						if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
	 		        		showError(data.responseMessage);
	      	    		}
	                		else if (data.response === "AWKWARD") {
	                			location.href = "/errorPage";
	      	    		}
	                		else if(data.response === "SUCCESS"){
	                			location.replace(sourceleadUrl+"/team");
				        	    }
	                		else if (data.indexOf('Sign In') !== -1) {
	                			location.replace(sourceleadUrl);
	      				} else {
	      					showError("Operation failed, please try again");
	      				}
						
						
						

					},
				});
			return true; 
	   }else{ 
			return false;
	        } 
		} 
 
		$('body').on('blur', '#team_form', function() {
			$("#team_form").showHelpOnFocus();
			$("#team_form").validateOnBlur(false, validationSettings);
		});
</script>     

<script type="text/javascript">
function getTeamOwners(){ 
	 var teamMemberUserId = document.getElementById("memberUserId").value;
     $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllUsersByOrgLocationCodeExceptTeamOwner?"+tokenParamName+"="+tokenValue+"&teamOwnerUserId="+teamMemberUserId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Team Lead-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+ opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"</option>";
			        		}
					}
		        	$("#ownerUserId").empty();
			        $("#ownerUserId").append(output);
			        $("#ownerUserId").trigger("chosen:updated");
			        
			        getTeamMembers();
		        },
		        error: function(e){
		            
		        }
		    });
} 
</script>

<script type="text/javascript">
function getTeamMembers(){  
	 var teamOwnerUserId = document.getElementById("ownerUserId").value;
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllUsersByOrgLocationCodeExceptTeamOwner?"+tokenParamName+"="+tokenValue+"&teamOwnerUserId="+teamOwnerUserId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option disabled >-Select Team Members-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"</option>";
			        		}
					}
		        	$("#memberUserId").empty();
			        $("#memberUserId").append(output);
			        $("#memberUserId").trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
} 
</script>

<script>
function deleteTeam(teamId,teamName){
	
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to delete <strong>"+teamName+"</strong> ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedTeam("+teamId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#deleteTeamPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	
}
</script>  

<script type="text/javascript">
function cancel(){
	$("#deleteTeamPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function deleteSelectedTeam(teamId){
	 if (teamId) {
		 
	     $("#deleteTeamPopUp").empty();
		 $(".project_inn_mask").fadeOut(200);
			
		 $("#deleteTeam"+teamId).html("<td colspan='5'  id='loading"+teamId+"'></td>");
		 $("#loading"+teamId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 
		 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/deleteTeamById?"+tokenParamName+"="+tokenValue+"&teamId="+teamId, 
   		    success: function(data) {
   		    	
   		    	if(data != null && data != 'undefined' && data != ""){
   		    		    $("#loading"+teamId).removeAttr("style");
             			document.getElementById("deleteTeam"+teamId).remove();
   		    	}
   		    }
	     }); 
	} 
}
</script>
 
 <!-- Team Member Store -->
 <script>
function storeTeamMembers(teamId){
		var validationSettings = {
		        errorMessagePosition : 'element',
		        borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         };
		
	   if ($('#teamMember_form').validate(false, validationSettings)){ 
			
		    var formData = getFormData1("teamMember_form");
			$("#teamMember_form").attr("disabled", "disabled");
			
			$.ajax({
				type : "POST",
        	    data: formData,
    	        processData: false,
    	        contentType:false,
        	   	url : sourceleadUrl+"/storeTeamMembers?"+tokenParamName+"="+tokenValue+"teamId="+teamId,
        		success : function(data) {
					sideBar(teamId);
					teamList();
            		 
				},
			}); 
			
			
	       /*  $.post(sourceleadUrl+"/storeTeamMembers?"+tokenParamName+"="+tokenValue+"&teamId="+teamId, formData, function(data) {
	        	sideBar(teamId);
				teamList();
	        }); */
		   
			return true; 
	   }else{ 
			return false;
	        } 
		}  
 
$('body').on('blur', '#teamMember_form', function() {
	$("#teamMember_form").showHelpOnFocus();
	$("#teamMember_form").validateOnBlur(false, validationSettings);
});
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

 <!-- Team Memberend -->
<script type="text/javascript">

	  $( function() {
	    $( "#edu-startDate, #edu-endDate" ).datepicker();
	  });
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
	
	});
</script> 
<script>
$('#selector').delay(2000).fadeOut('slow');

// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
$(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
	 $(".collapse-div").removeClass("edit-details-active");
 });
 $(".close-div").click(function(){
	 $(this).closest(".edit-details").toggleClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   //event.preventDefault();
});

$(".addSkills").click(function(){
	$(".add-skills").toggleClass('add-skills-active');
	});
$(".close-div").click(function(){
	$(".add-skills").removeClass('add-skills-active');
	});
$(".add-skills").click(function(e){
	 e.stopPropagation();
 });
</script> 
<script>
  $(function() {
    $("#validFrom, #edu-endDate, #edu-startDate, #validTo").datepicker();
  } );
  </script>
  
 <script type="text/javascript">
function getTeamMembersListExceptExistedTeamMembersList(teamId){  
	 var existedTeamMembersIds = $('#existedMemberId').val();
	/*  $('#existedTeamMember').each(function() {
		 var localId = this.value;
		 existedTeamMembersIds.push(localId);
	 });   */
	 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getTeamMembersListExceptExistedTeamMembersList?"+tokenParamName+"="+tokenValue+"&existedTeamMembersIds="+existedTeamMembersIds+"&teamId="+teamId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option disabled >-Select Team Members-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+ opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"</option>";
			        		}
					}
		        	$("#teamMemberUserId").empty();
			        $("#teamMemberUserId").append(output);
			        $("#teamMemberUserId").trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
	});
} 
</script>

<script>
function getCandidateListForTeam(teamId,teamName){
	$("#teamSideDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getHotCandidateListForTeamByTeamId?"+tokenParamName+"="+tokenValue+"&teamId="+teamId,
	    	  success: function(data) {
	    		/*   if(data != null && data != 'undefined' && data != "" && data != '[]'){ */
	               			 $("#teamSideDiv").removeAttr("style");
		                	 $("#teamSideDiv").empty();
		                	 var result = "";
		                	 result = result +"    <div class='clearfix'>";
		                	 result = result +"    <p><strong>"+teamName+" (Associate)</strong></p>";
		                	 result = result +"    </div>";
		                	 result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
		                     result = result +"<thead>"; 
		                     result = result +"  <tr>"; 
		                     result = result +"    <th>Name</th>"; 
		                     result = result +"    <th>Designation</th>"; 
		                     result = result +"    <th>Position type</th>"; 
		                     result = result +"    <th>Email</th>"; 
		                     result = result +"    <th>Mobile</th>"; 
		                     result = result +"    <th>Type</th>"; 
		                     result = result +"    <th>Location</th>";
		                     result = result +"    <th>Action</th>";
		                     result = result +"  </tr>"; 
		                     result = result +"</thead>";
		                     result = result +"<tbody>"; 
		                     
		                     var json = JSON.parse(data);
		                     for (var i = 0; i < json.length; i++) {
		                    	 var opt = json[i]; 
		                    	 var candidateFullName = "";
		                    	 var candidateDesignation = "";
		                    	 var positionType = "";
		                    	 var candidateEmail = "";
		                    	 var candidateMobileNumber = "";
		                    	 var candidateType = "";
		                    	 var candidateLocation = "";
		                    	 if(opt.candidateName != null && opt.candidateName != 'undefined' && opt.candidateName != ""){
		                    		 candidateFullName = opt.candidateName;
					             }else{
					            	 candidateFullName = "----";
					             }
		                    	 if(opt.designation != null && opt.designation != 'undefined' && opt.designation != ""){
		                    		 candidateDesignation = opt.designation;
					             }else{
					            	 candidateDesignation = "----";
					             }
		                    	 if(opt.designationType != null && opt.designationType != 'undefined' && opt.designationType != ""){
		                    		 positionType = opt.designationType;
					             }else{
					            	 positionType = "----";
					             }
		                    	 if(opt.email != null && opt.email != 'undefined' && opt.email != ""){
		                    		 candidateEmail = opt.email;
					             }else{
					            	 candidateEmail = "----";
					             }
		                    	 if(opt.mobilePhone != null && opt.mobilePhone != 'undefined' && opt.mobilePhone != ""){
		                    		 candidateMobileNumber = opt.mobilePhone;
					             }else{
					            	 candidateMobileNumber = "----";
					             }
		                    	 if(opt.candidateType != null && opt.candidateType != 'undefined' && opt.candidateType != ""){
		                    		 candidateType = opt.candidateType;
					             }else{
					            	 candidateType = "----";
					             }
		                    	 if(opt.candidateLocation != null && opt.candidateLocation != 'undefined' && opt.candidateLocation != ""){
		                    		 candidateLocation = opt.candidateLocation;
					             }else{
					            	 candidateLocation = "----";
					             }
		                    	 
		                    	 result = result +"<tr id='removeCandidate"+opt.id+"'>";
				                 result = result +"  <td><div><span title='"+candidateFullName+"'>"+candidateFullName+"</span></div></td>";
				                 result = result +"  <td><div><span>"+candidateDesignation+"</span></div></td>";
				                 result = result +"  <td><div><span>"+positionType+"</span></div></td>";
				                 result = result +"  <td><div><span title='"+candidateEmail+"'>"+candidateEmail+"</span></div></td>";
				                 result = result +"  <td><div>"+candidateMobileNumber+"</div></td>";
				                 result = result +"  <td><div>"+candidateType+"</div></td>";
		                         result = result +"  <td><div>"+candidateLocation+"</div></td>";
		                         result = result +"  <td><i class='glyphicon glyphicon-trash text-danger' onclick='removeFromTeam("+opt.id+");'></i></td>";
				                 result = result +"</tr>";
		                     }
		                     result = result +" </tbody>";
		                     result = result +"</table>";
		                     $("#teamSideDiv").append(result);
		                     cpagination();
	    		  
	    	  }
	 });
 $(".clients_side_wrap").addClass("clients_side_wrap_active");
 $("#teamSideDiv").show();
 $(".side_mask").fadeIn(200);
 $("body").css('overflow-y', 'hidden');
  
}
</script>

<script>
function cpagination(){ 
    $('#candidatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
function removeFromTeam(id){
	if(id){
		 $("#removeCandidate"+id).html("<td colspan='8'  id='loading"+id+"'></td>");
		 $("#loading"+id).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 
			 $.ajax({ 
		    	type: "POST",
		    	url: sourceleadUrl+"/removeFromTeam?"+tokenParamName+"="+tokenValue+"&id="+id,
		    	  success: function(data) {
		    		 var table = $('#candidatePagination').DataTable();
		 			 table.row('#removeCandidate'+id).remove().draw( false );
		 			 
		    	  }
			}); 
	}
}
</script>

<script>
    $(".viewCandidate_close, .side_mask").click(function() {
    	$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>

<!-- Team Name validation script starts here -->
<script type="text/javascript">
 function validateTeamName() {
	 var name = $('#candidateName').val(); 
	 $('#teamNameDiv').find('p.jquery_form_error_message').remove();
	 if(name != null && name != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateTeamName?"+tokenParamName+"="+tokenValue+"&name="+name, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $("input#candidateName").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
						  $("#add_team").attr("disabled", "disabled");
						  $("#add_team").addClass('button').val('Processing..');
		        	}else{
		        		 $('input#candidateName').removeAttr("record-exist record-exist-errormsg");
		        		 $('#teamNameDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#add_team" ).addClass('button').val('Save');
                         $("#add_team").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>     
 
 <!-- Team Name validation script ends here -->

<jsp:include page="../masterFooter.jsp" />