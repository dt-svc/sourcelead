 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
	<div class="modal-dialog modal-lg" style="width: 98%;">
		<div class="modal-content">
			<div class="modalHeader">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Assign To</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="clearfix"></div>
					<div class="swaptables">
						<div class="row positionRelative">
							<div class="col-md-6 mb5 bootSelectpicker">
								<select id="teamSelectionCategory" class="selectpicker" multiple data-actions-box="true"
									onchange="filterTeamMembers();" style="display: block !important"
									title="--Select Assign Team--">
								</select>
							</div>
							<!-- <div class="remove_search fa-pull-right">
								<input placeholder="Search" type="text">
							</div> -->
						</div>

						<div class="clearfix positionRelative assignTableScroll">
							<table id="assignToDataTableId"
								class="display table table-stripped table-bordered dataTable no-footer"
								role="grid" aria-describedby="assignToDataTableId_info" style="width: 100%;" cellspacing="0">
								<thead>
									<tr role="row">
										<th style="vertical-align: middle"><input type="checkbox" id="checkAll"></th>
										<th style="vertical-align: middle">Name</th>
										<th style="vertical-align: middle">Email</th>
										<th style="vertical-align: middle">Role</th>
										<th>Active<br>Jobs</th>
									</tr>
								</thead>
								<tbody id="usersGrid">
								</tbody>
							</table>
							<div class="clearfix"></div>
						</div>
					</div>
					<div class="swaptables-buttons">
						<div class="swaptables-buttons-inner">
							<div class="btn-arrow clearfix mb">
								<i class="fa fa-2x fa-arrow-circle-right cursorPointer" onclick="selectAssignUsers();"></i>
							</div>
							<div class="btn-arrow clearfix">
								<i class="fa fa-2x fa-arrow-circle-left cursorPointer" onclick="deSelectAssignUsers();"></i>
							</div>
						</div>
					</div>
					<div class="swaptables">
						<div class="clearfix mt40 assignTableScroll" style="height:250px; overflow-y:auto;" id="assignToSelectedDataTableDiv">
							<table id="assignToSelectedDataTableId"
								class="display table table-stripped table-bordered dataTable no-footer"
								role="grid" aria-describedby="assignToDataTableId_info"
								style="width: 100%;" cellspacing="0">
								<thead>
									<tr role="row">
										<th style="vertical-align: middle"><input id="checkAllSelected" type="checkbox"></th>
										<th style="vertical-align: middle"><span class="tbldata-dotdot-custom">Name</span></th>
										<th style="vertical-align: middle"><span class="tbldata-dotdot-100">Email</span></th>
										<th style="vertical-align: middle"><span class="tbldata-dotdot_80">Role</span></th>
										<th><span class="tbldata-dotdot_30">Active<br>Jobs</span>
										</th>
									</tr>
								</thead>
								<tbody id="selectedUsersGrid">
								</tbody>
							</table>
							  <div class="panel-default text-center" id="noAssignedmembers">
							    <div class="panel-body">
								    <i class="fa fa-users fa-3x text-muted mt mb"></i>
									<p>No Assigned Members</p>
							    </div>
							  </div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix mb">
				<div class="pull-right mr">
					<c:choose>
					  <c:when test="${!empty requirement.id}">
					    <button type="button" class="btn btn-primary mr5 btn-sm" data-dismiss="modal" onclick="editAssignedTeamsOrUsers('${requirement.id}');">Save<span id="assignToSelectedPopupCount"> (0) </span></button>
					  </c:when>
					  <c:otherwise>
					    <button type="button" class="btn btn-primary mr5 btn-sm" data-dismiss="modal" onclick="getAssignedTeamsOrUsers();">Assign<span id="assignToSelectedPopupCount"> (0) </span></button>
					  </c:otherwise>
					</c:choose>
					<c:if test="${empty requirement.id}"> 
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="clearAssignedTeamsOrUsers();">Reset</button>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal Pop ends -->



<script>
function getAssignedMembersListForEdit(reqId){
	if(reqId != '' && reqId != 'undefined' && reqId != null){
		$.ajax({
	           type : "POST",
	           url: sourceleadUrl+"/getAssignedMembersForEdit?"+tokenParamName+"="+tokenValue+"&reqId="+reqId,    
	            success :function(data) {
	            	var json = JSON.parse(data);
	            	selectAssignUsersIds = json;
	            	$("#assignedTeamId").val(json);
	            },
	            complete: function (data) {
	            	getTeamsList(reqId);
	            }
	      }); 
	}
}

function getTeamsList(reqId){
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getTeamsList?"+tokenParamName+"="+tokenValue+"&reqId="+reqId,    
            success :function(data) {
            	var json = JSON.parse(data);
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
            	
            },
            complete: function (data) {
            	var teamType = $("#teamSelectionCategory").val();
          		getUserList(teamType);
            }
            
      }); 
 } 
</script>

<script type="text/javascript">
	function filterTeamMembers(){
		var teamType = $("#teamSelectionCategory").val();
		getUserList(teamType);
	}
</script>

<script>
function getUserList(teamType){ 
	var teamTypeArray = new Array();
	var isTeamExists = false;
	var isRoleExists = false;
	var teamTypeStr = ""+teamType+"";
	if(teamTypeStr != null){
		teamTypeStr.split(',').forEach(function (item) {
			if(item.includes("role")){
				isRoleExists = true;
			}else{
				isTeamExists = true;
			}
		});
	}
	
	   /* var selectedUsersForAssign = [];
	   $('input[name^="assignUserId"]').each(function() {
	 		selectedUsersForAssign.push($(this).val());
	   }); */
 	   $("#usersGrid").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	   $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getUserList?"+tokenParamName+"="+tokenValue+"&teamType="+teamType,    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#usersGrid").removeAttr("style");
	                	    var result = '';
	                	    //result = result+"<input type='hidden' name='assignedTeamId' id='assignedTeamId' value='"+teamType+"'/>";
		                    var json = JSON.parse(data);
		                    //var selectedTempIds = $("#assignedTeamId").val();
		                    for (var i = 0; i < json.length; i++) {
		                    	 var opt = json[i]; 
			                     var isSelected = "";
			                     var isBlockedBySelected = "";
			                     var selectAssignUsersIdsStr = ""+selectAssignUsersIds+"";
			                     if(selectAssignUsersIds.includes(opt.id+','+opt.assignedTeamId)){
			                    	 isSelected = "checked disabled";
			                     }else if(selectAssignUsersIdsStr.includes(opt.id)){
			                    	 isBlockedBySelected = "fa fa-ban";
			                    	 isSelected = "disabled";
			                     }
		                    	 result = result +'<tr>';
		                    	 
		                    	 var lastName = '';
		                    	 if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
		                    		 lastName = ' '+opt.lastName;
		                    	 }
		                    	 var owner ='';
		         				 if(null != opt.isAssignedOwner && 'undefined' != opt.isAssignedOwner){
		         					 owner = opt.isAssignedOwner;
		         				 }
		         				 var assignedTeam = '';
		         				 if(null != opt.assignedTeam && 'undefined' != opt.assignedTeam && '' != opt.assignedTeam){
		         					 assignedTeam = opt.assignedTeam;
		         				 }
		                    	 
		         				 result = result +'  <td><input id="'+opt.id+'"  class="myCheckbox '+isBlockedBySelected+'" value="'+opt.id+','+opt.assignedTeamId+'"  type="checkbox" '+isSelected+' name="team" onchange="handleChange(this);"></td>';
		                    	 result = result +'             <td><span class="tbldata-dotdot-custom" title="'+opt.firstName+''+lastName+''+assignedTeam+''+owner+'">'+opt.firstName+''+lastName+''+assignedTeam+''+owner+'</span></td>';
		                    	 result = result +'             <td><span class="tbldata-dotdot-100" title="'+opt.email+'">'+opt.email+'</span></td>';
		                    	 result = result +'             <td><span class="tbldata-dotdot_80">'+opt.userType+'</span></td>';
		                    	 result = result +'             <td><span class="tbldata-dotdot_30">'+opt.assignJobCount+'</span></td>';
		                    	 result = result +'           </tr>';
	                       }
		                    
	                 	   //result = result +'	<span class="jquery_form_error_message" style="display:none;">Assign at least to a new member</span>'; */
	                 	   $("#usersGrid").empty(); 
	                       $("#usersGrid").append(result);
	                       $("#checkAll").prop('checked', false);
	                       if(isTeamExists && isRoleExists){
	                    	   $("#checkAll").prop('disabled', true);
	                       }else{
	                    	   $("#checkAll").prop('disabled', false);
	                       }
	                       //paginations(); 
			    		}
                 	}
          		},complete: function (data) {
                	selectAssignUsers();
                }
       		});
  	} 
</script>

<script>
function paginations(){
    $("#assignToDataTableId").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"aaSorting": [[1,'desc']],
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	},
    });
    $("#assignToDataTableId").wrap("<div class='modal-body-custom mailerpopup modalAlign'></div>");
}
</script>

<script>
window.selectAssignUsersIds = new Array();
function selectAssignUsers(){
	$("#selectedUsersGrid").empty();
	selectAssignUsersIds = [];
	$("input:checkbox[name=team]:checked").each(function(){
		var thisRow = $(this).closest( 'tr' )[0];
	    $("#selectedUsersGrid").append($(thisRow).clone());
	    $(this).attr('disabled', 'disabled');
	    selectAssignUsersIds.push($(this).val());
	});
	$("#selectedUsersGrid input[type=checkbox]").each(function(){
		$(this).attr('name', 'selectedUser');
		$(this).prop('disabled', false);
		$(this).removeAttr('onchange');
     });
	$("#assignToSelectedPopupCount").text(" ("+$('#assignToSelectedDataTableDiv >table >tbody >tr').length+") ");
	if($('#assignToSelectedDataTableDiv >table >tbody >tr').length == 0){
		$("#noAssignedmembers").show();
	}else{
		$("#noAssignedmembers").hide();
	}
}

function deSelectAssignUsers(){
	$("input:checkbox[name=selectedUser]:checked").each(function(){
		var deSelectedId = $(this).attr('value');
		$(this).closest( 'tr' )[0].remove();
		$("#assignToDataTableId input[type=checkbox]").each(function(){
			if(deSelectedId == $(this).attr('value')){
				$(this).prop('disabled', false);
			}
	     });
		selectAssignUsersIds = selectAssignUsersIds.filter(item => item !== deSelectedId);
	});
	$("#assignToSelectedPopupCount").text(" ("+$('#assignToSelectedDataTableDiv >table >tbody >tr').length+") ");
	if($('#assignToSelectedDataTableDiv >table >tbody >tr').length == 0){
		$("#noAssignedmembers").show();
	}else{
		$("#noAssignedmembers").hide();
	}
}
</script>


<script>
	$(':checkbox[id=checkAll]').click(function() {
		//toggle all checkboxes except disabled checkboxes
		$(':checkbox[name=team]').not("[disabled]").prop('checked', this.checked);
	});

	$(':checkbox[id=checkAllSelected]').click(function() {
		//toggle all checkboxes
		$(':checkbox[name=selectedUser]').prop('checked', this.checked);
	});
	
	function getAssignedTeamsOrUsers(){
		$("#assignedTeamsOrUsers").slideUp();
		handleTeamRoleClick();
		$("#assignedTeamsOrUsers").empty();
		$("#assignedTeamsOrUsers").append($("#assignToSelectedDataTableId").clone());
	    $('#assignedTeamsOrUsers').find('thead').remove();
		var selectedIDs = $("#assignedTeamsOrUsers input:checkbox").map(function(){
			$(this).prop('disabled', true);
			$(this).closest( 'td' )[0].remove();
			return $(this).val();
	    }).toArray();
		$(".showGrid").text(selectedIDs.length);
		if(selectedIDs.length===0){
			$("#showGrid").prop('disabled', 'disabled');
		}else{
			$("#showGrid").removeAttr('disabled');
		}
	    $("#assignedTeamId").empty();
	    $("#assignedTeamId").val(selectedIDs);
	    $("#usersGrid").empty();
	}
	
	function clearAssignedTeamsOrUsers(){
		$("#assignToSelectedPopupCount").text(" (0) ");
		$("#assignedTeamId").val('');
		$("#selectedUsersGrid").empty();
		$("#assignedTeamsOrUsers").empty();
		$(".showGrid").text('0');
		$("#showGrid").prop('disabled', 'disabled');
		teamRoleIds = [];
		selectAssignUsersIds = [];
		$("#teamSelectionCategory").empty();
	}
	
	function editAssignedTeamsOrUsers(requirementId){
		//assignedMemberList(requirementId);
		$('.assignedGrid2').click();
		var assignedIdVal = [];
		var assignedToEditCount = 0;
		$("#selectedUsersGrid input[type=checkbox]").each(function(){
			assignedIdVal.push($(this).val());
			assignedToEditCount++;
	     });
		$("#assignedTeamId").val(assignedIdVal);
		handleTeamRoleClick();
		$("#forAssignedToCount1").text(assignedToEditCount);
		$("#forAssignedToCount2").text(assignedToEditCount);
			$.ajax({
		           type : "POST",
		           url: sourceleadUrl+"/saveOrUpdateAssignToTeam?"+tokenParamName+"="+tokenValue+"&assignedIdVal="+assignedIdVal+"&requirementId="+requirementId,    
		            success :function(data) {
		            	
		            },
	   		});
		$("#usersGrid").empty();
	}
</script>

<script>
window.teamRoleIds = new Array();
function handleTeamRoleClick() {
	teamRoleIds = [];
	$("option[name=TeamAndRole]:selected").each(function(){
		teamRoleIds.push($(this).val());
	});
}

/* this function is for block remaining duplicate users */
function handleChange(e){
	$("#assignToDataTableId input[type=checkbox]").each(function(){
		/* if(e.checked){
			selectAssignUsersIds.push($(this).val());
		} */
	    if(e.checked && e.id == $(this).attr('id') && e.value != $(this).attr('value')){
	    	$(this).prop('disabled', true);
	    	$(this).addClass('fa fa-ban');
	    }else if(!e.checked && e.id == $(this).attr('id')){
	    	$(this).prop('disabled', false);
	    	$(this).removeClass('fa fa-ban');
	    }
	});
}

</script>

