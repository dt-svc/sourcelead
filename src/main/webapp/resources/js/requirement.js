/*// requirementLst.jsp scripts are started.
var sourceleadUrl ="http://localhost:8080/app";//'${sourceleadUrl}'; // setting common user settings 
var locationList ="dd-MM-yyyy";//'${SourceLeadWeb_orgLocations.dateFormat}';	
var orgATS = "Y";//'${SourceLeadWeb_ats}';
var orgVMS ="Y";// '${SourceLeadWeb_vms}';
var userRoleName = "ROLE_ADMIN";// '${SourceLeadWeb_loginUserRole}';
var userId ="4";
var reqCoolingOffPeriod = "15"; //'${requirement.reqCoolingOffPeriod}';
var orgLocationId ="3" ;// need to be dynamic '${SourceLeadWeb_orgLocationId}';
var country = "India";//'${SourceLeadWeb_orgLocations.country}';
// below are duplicated for job UI.
var loginUser = 'Snigitha Paruchuri';
var tabAccessArray = '';
var roleActionsArray = '';
var loginUserRole ="ROLE_ADMIN";// '${SourceLeadWeb_loginUserRole}';
*///above are duplicated for job UI.

function clearList(){ // updated.
	location.reload();
}

function statusvalidating(id){ //updated.
	changeStatusAuthorisation(id);
}

function getSourceleadTags(){ // updated.
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
/*function filterPop(){ // updated.
	$("#requirementFilterPopup").toggle();
	$(".sideMask").fadeIn();
};
function closeFilter(){ // updated.
	$("#requirementFilterPopup").hide();
	$(".sideMask").fadeOut();

}*/

/*function columPop(){ // updated
	$(".colPop").show();
	$(".colPopFot").show();
}
function columClose(){// updated
	$(".colPop").hide();
}
$("html").click(function(){// updated
	$(".colPop").hide();
});
$(".refreIcon, .colPop").click(function(e){// updated
 	e.stopPropagation();
});		
	
function columHide(){// updated
	hideColumns=[]; // reset the array object.
	$(".toggle-vis").each(function() {
		columClose();// 
	   var chkdVal= $(this).prop('checked');
//		alert("chkdVal "+chkdVal);
	   var column = table.column($(this).attr('data-column'));
//		   alert("column "+column);
		  if(chkdVal == true){
//				  alert("1");
		         column.visible(true);
		    }
		    else{
//			    	 alert("2");
		      column.visible(false);
		      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
		  }
    });
	$(".colPop").hide();
	
	reqTable.column(0).visible(false);

}
function checkAllColumns(obj){// updated
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
*/function changeStatus(requirementId, requirementStatus){ // updated.
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

	 //var userId ='${SourceLeadWeb_userId}';
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
	function setTM (){ // updated.
		  setTimeout(function() { 
		  $("tr").removeClass("row-info");
		  $("tr").removeClass("row-success");
		  $("tr").removeClass("row-warning");
		  $("tr").removeClass("row-danger");
		}, 2000);
	};
   
	$('#mypop').click(function(){ // updated from 
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
	$("#exportPop, #exportPopJobFair, #mypop").click(function(e) {   // updated to 
	    e.stopPropagation();
	});
	function exportToPdf(type) { // updated.
		
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
	function exportToExcel(type) { // updated.
		
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
	function cancel(){ // updated
		$("#popUp").empty();
		$(".project_inn_mask").fadeOut(200);
	}
	function msgFadein1() {
        setTimeout(function(){
                $('#selector2').fadeOut('slow'); }, 1000);
    }

	// Close Advanced Search
	function closePop(){ // updated
		$("div.cand-advsrch-popup").hide();
		$(".advancedsearch-mask").fadeOut();
	}
	function openPop(){// updated
		 getSourceleadTags();
		 $("#dataTableSearch").val('');
		 $("div.cand-advsrch-popup").toggle();
		 $(".advancedsearch-mask").fadeIn();
		 $("#requirementFilterPopup").toggle();
		 
		 
	}
	function closeAdsrch(){// updated
		$("div.cand-advsrch-popup").fadeOut();
		$(".advancedsearch-mask").fadeOut();
	};


	function loadChosenRequirementScripts(){ // updated.
	     $("#city").empty();
		 $("#city").val('').trigger("chosen:updated");
		 $("#requirementTitle").val('').trigger("chosen:updated");
		 $("#rate").val('').trigger("chosen:updated");
		 $("#minExp").val('').trigger("chosen:updated");
		 $("#maxExp").val('').trigger("chosen:updated");
		 $("#requirementStatu").val('').trigger("chosen:updated");
		 $("#updatedOn").val('').trigger("chosen:updated");
		 $("#education").val('').trigger("chosen:updated");
		 $("#client").val('').trigger("chosen:updated");
		 $("#skills").val('').trigger("chosen:updated"); 
		
	}
	function clearSearch(){ // updated.
		document.getElementById("requirement_form").reset();
		$('#requirementStatu').val('').trigger('chosen:updated');
		$('#updatedOn').val('').trigger('chosen:updated');
		$('#client').val('').trigger('chosen:updated');
		$('#country').val('').trigger('chosen:updated');
		$('#visaType').val('').trigger('chosen:updated');
		$('#requirementSkills').val('').trigger('chosen:updated');
		$('#state').val('').trigger('chosen:updated');
		$('#city').val('').trigger('chosen:updated');
		$('#noticePeriod').val('').trigger('chosen:updated');
		$('#requirementTitle').val('').trigger('chosen:updated');
		$('#jobId').val('').trigger('chosen:updated');
		$('#generatedId').val('').trigger('chosen:updated');
	}
	// 
	function myClientFunction() { // updated
	    var input, filter, ul, li, a, i;
	    input = document.getElementById("myClientInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myClientUL");
	    li = ul.getElementsByTagName("li");
	    for (i = 0; i < li.length; i++) {
	        a = li[i].getElementsByTagName("label")[0];
	        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
	            li[i].style.display = "";
	        } else {
	            li[i].style.display = "none";

	        }
	    }
	}


	function myProjectFunction() { // updated
	    var input, filter, ul, li, a, i;
	    input = document.getElementById("myProjectInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myprojectUL");
	    li = ul.getElementsByTagName("li");
	    for (i = 0; i < li.length; i++) {
	        a = li[i].getElementsByTagName("label")[0];
	        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
	            li[i].style.display = "";
	        } else {
	            li[i].style.display = "none";

	        }
	    }
	}

	function myCityFunction() { // updated
	    var input, filter, ul, li, a, i;
	    input = document.getElementById("myCityInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myCitytUL");
	    li = ul.getElementsByTagName("li");
	    for (i = 0; i < li.length; i++) {
	        a = li[i].getElementsByTagName("label")[0];
	        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
	            li[i].style.display = "";
	        } else {
	            li[i].style.display = "none";

	        }
	    }
	}


	$(document).keyup(function(e) { // updated
		  if (e.keyCode === 27) {
			  closeFilter();
		  }
		});
	
	function resetJobFilter(){ // updated.
		$("ul#myprojectUL > li input:checkbox").prop("checked",false);
		$("ul#myCitytUL > li input:checkbox").prop("checked",false);
		$("ul#myClientUL > li input:checkbox").prop("checked",false);
		$("ul#jobs > li input:checkbox").prop("checked",false);
		$("ul#status > li input:checkbox").prop("checked",false);
		$("ul#jobType > li input:checkbox").prop("checked",false);
		$("ul#priority > li input:checkbox").prop("checked",false);
		$("ul#assignTo > li input:radio").prop("checked",false);
		
		requirementList('list',atsUrl);
	}
	var ele = "";
	function showChangeJobFairStatusPopup(jobFairId, status, oldValue, element) { // updated.
		ele = element;
		var statusText = "";
		if(status === "0") {
			statusText = "Are you sure to make the JobFair status to 'CLOSE' ?";
		} else if(status === "1") {
			statusText = "Are you sure to make the JobFair status to 'OPEN' ?";
		} else {
			statusText = "Are you sure to delete the JobFair ?";
		}
		$("#changeStatusPopup p").html(statusText);
		$("#confirmId").data("job_fair_id", jobFairId);
		$("#confirmId").data("status",status);
		$("#cancelStatusPopup").data("old_value", oldValue);
		$("#changeStatusPopup").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	
	function closeChangeStatusPopup() {  // updated.
		var oldValue = $("#cancelStatusPopup").data("old_value");
		$(ele).val(oldValue);
		$("#changeStatusPopup").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
	}
	
	function changeJobFairStatus() {  // updated.
		var jobFairId = $("#confirmId").data("job_fair_id");
		var status = $("#confirmId").data("status");
		var data = {
				jobFairId: jobFairId,
				status : status
		};
		$.post(atsUrl + "/changeJobFairStatus?", data, function(result) { // updated.
			if (result.response === "SESSION_TIMEDOUT") {
				location.replace(sourceleadUrl);
			} else if (result.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				location.href = atsUrl + "/jobsList?viewTab=jobFair";
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}

// requirementLst.jsp scripts are completed.
// requirementTable.jsp scripts are started here============== all are updated.
	function jobFairListCheck(){
		viewTab = "jobFair";
		$("#filtersDiv").addClass("hide_filters");
		$("#jobsTab").removeClass("current");
	    $("#jobFairTab").addClass("current");
		
		$("#jobsTabContent").hide();
		$("#jobFairTabContent").show();
		
		
		$("#jobFairList").empty();
		$("#jobFairList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	//	var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  var canEduDatePattern = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  var canEduDatePattern = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  var canEduDatePattern = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  var canEduDatePattern = 'YYYY-DD-MM'; 
		  }
		//var orgATS = '${SourceLeadWeb_ats}';
		//var orgVMS = '${SourceLeadWeb_vms}';
		 $("#jobFairList").removeAttr("style");
		 $("#jobFairList").empty();
		var result = "";
		result = result +"<table id='jobFairPagination' class='display' cellspacing='0' width='100%' >"; 
	 	result = result +"<thead>"; 
	 	result = result +"	<tr>";
	 	result = result +"		<th>Generated ID</th>";
	 	result = result +"		<th>Title</th>";
	 	result = result +"		<th>Start Date</th>";
	 	result = result +"		<th>End Date</th>";
	 	result = result +"		<th>No.of Jobs</th>";
	 	result = result +"		<th>Applicants</th>";
	 	result = result +"		<th>Offered</th>";
	 	result = result +"		<th>Venue</th>"; 
	 	result = result +"		<th>Status</th>";
	 	result = result +"		<th>Action(s)</th>";
		result = result +"	</tr>"; 
	 	result = result +"</thead>";
	 	result = result +"<tbody>"; 
	     
	  	result = result + '<div class="new_side_wrap">';
		result = result + '<div class="new_side_wrap_inn">';
		result = result + '<div class="new_side_wrap_cta">';
		result = result +'<div id="addApplicantDiv"></div>';
		result = result +'</div>';
		result = result +'</div>';
		result = result +'</div>';
	 	result = result +" </tbody>";
	 	result = result +"</table>"; 
	 	$("#noReq").val('');
	 	$("#jobFairList").append(result); 
	 	var url = atsUrl+"/searchJobFairs?includeInactive=true&searchValue="+$("#jobFairDataTableSearch").val();
	 	jpaginationForJobFairs(url); 
	 
		 /* to call requirement conformation saving message */
	 	msgFadein1();
	}
	function jpaginationForJobFairs(url){ 
		var colIndex = 0;
	    $('#jobFairPagination').DataTable( {
	   		"processing": true,
	       "serverSide": true,
	       "searching": false,
	       "searchHighlight": true,
	       "ajax": $.fn.dataTable.pipeline( {
	           url: url,
	           method: 'POST',
	           pages: 1
	       }),
	       "columns": [
	    	   {"data": "generatedId"},
	           {"data": "name"},
	           {"data": "startDate"},
	           {"data": "endDate"},
	           {"data": "requirementsList"},
	           {"data": "applicantCount"},
	           {"data": "offeredCount"},
	           {"data": "venue"},
	           {"data": "status"},
	           {"data": "actions" }
	       	],
		       "columnDefs": [
	    	   {
		            "targets": colIndex++,
		            "data": "generatedId",
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.generatedId != null && opt.generatedId != '' && opt.generatedId != 'undefined') {
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.generatedId+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			           		return result;
		             }
		        },
		    	{
		           "targets": colIndex++,
		           "data": "name",
		           "render": function ( data, type, opt, meta ) {
		        	   $("#requirementEmptyListMessage").val(2);
		           		var result = "";
		       		  	//'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
		           	 if(roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") > -1 || roleActionsArray.indexOf("ROLE_VIEW_EDIT_REQUIREMENT") > -1){
		       		    	result = result +"<td><a href='/ats/jobFairSummary?uuid="+opt.uuid+"'><div class='proj_show width150px'><span class='pull-left' title='"+opt.name+"'>"+opt.name+"</span><i class='fa fa-binoculars'></i></div></a></td>";
		           	 }//'</sec:authorize>';
			            return result;
		            }
	            },
	            {
		            "targets": colIndex++,
		            "data": "startDate",
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.startDate != null && opt.startDate != '' && opt.startDate != 'undefined') {
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.startDate+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			           		return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "endDate",
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.endDate != null && opt.endDate != '' && opt.endDate != 'undefined'){
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.endDate+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			                return result;
		             }
		        },
				{
		            "targets": colIndex++,
		            "data": "requirementsList",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.jobFairRequirementList != null && opt.requirementsLjobFairRequirementListist != '' && opt.jobFairRequirementList != 'undefined') {
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.jobFairRequirementList.length +'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			           		return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "applicantCount",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.applicantCount+'</div></td>';
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "offeredCount",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.offeredCount+'</div></td>';
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "venue",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.venue != null && opt.venue != '' && opt.venue != 'undefined'){
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.venue+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "status",
		            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">';
	     	        	result += '<select class="fairStatus">';
	     	        	result += '<option data-id='+opt.id+' value="1" ' + (opt.status == 1?'selected=""':'') + '>OPEN</option>';
	     	        	result += '<option data-id='+opt.id+' value="0" ' + (opt.status == 0?'selected=""':'') + '>CLOSE</option>';
	     	        	result += '</select>';
	     	        	result += '</div></td>';
		                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "actions",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            	var result = '<div class=" widthIcon widthIcon-three">';
		            	result +='	<div class="text-center">';
		            	result +='		<a href="' + atsUrl + '/editJobFair?uuid=' + opt.uuid + '" title="Edit Fair" onclick="" class="add-applicant">';
		            	result +='			<i class="fa fa-edit"></i>';
		            	result +='		</a>';
		            	result +='	</div>';
		            	result +='	<div class="text-center">';
		            	result +='		<a href="javascript:void(0)" title="Delete Fair" onclick="showChangeJobFairStatusPopup(' + opt.id + ', -1)">';
		            	result +='		<i class="fa fa-trash-o"></i>';
		            	result +='		</a>';
		            	result +='	</div>';
		            	result +='</div>';
	           	        return result;
		             }
		        }
	           ],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[6,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"createdRow": function ( row, data, index ) {
		    		row.setAttribute('id',"jobFair_"+data.id);
	    	    },
		    	"initComplete": function( settings, json ) {
		    		 $('#jobFairDataTableSearch').removeAttr('disabled');
		    	},
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     },
		     drawCallback: function(){
	   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
	   	    	jobFairSearchHighlight();
	   	      });
	   	   		updateFairStatusEvents();
	   	   	}
	   });
	   
	  
	   $("#jobFairPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	   $("input[type=search]").hide();
	   
	   $("#jobFairDataTableSearch").keyup(function(e) {
	   	   if(e.which === 13) {
	   		jobFairListCheck();
	   	   }
	   	});
	 }
// end jpaginationUrl end 
	function requirementListCheck(searchValue,universalRequirementSearchList,searchType,atsUrl){
		viewTab = "jobs";
		$("#filtersDiv").removeClass("hide_filters");
		if(universalRequirementSearchList != null && universalRequirementSearchList != '' && universalRequirementSearchList != 'undefined' 
			 && universalRequirementSearchList=="universalRequirementList")	 {
			 $("#dataTableSearch").val(searchValue);
			 $("#searchKey").val(searchValue);
			 if(searchType == null || searchType == '' || searchType == 'undefined'){
				    $("#c6").prop("checked", true);
				  	$("#c8").prop("checked", true);
				 	$("#c9").prop("checked", true);
			 }
			 requirementList(searchType,atsUrl);
		  }
		  else if(searchType != null && searchType != '' && searchType != 'undefined'
			   && searchType=="advanceSearch")	 {
			   $("#c6").prop("checked", false);
			   $("#c8").prop("checked", false);
			   $("#c9").prop("checked", false);
			   requirementList(searchType,atsUrl);
		 }
		 else{
			  	$("#c6").prop("checked", true);
			  	$("#c8").prop("checked", true);
			 	$("#c9").prop("checked", true);
			 	requirementList('list',atsUrl);
		} 
	}
	function requirementList(searchType,atsUrl){  
		    var url = '';
		    if(searchType == 'list' || searchType == 'universalSearch'){
		    	var filterStatus = [];
				$('input:checkbox[name=requirementStatus]:checked').each(function() 
			    {
					filterStatus.push($(this).val());
			    });
			    var filterClients = [];
				$('input:checkbox[name=clientNameById]:checked').each(function() 
			    {
					filterClients.push($(this).val());
			    });
				
				var filterPriority = [];
				$('input:checkbox[name=requirementPriority]:checked').each(function() 
			    {
					filterPriority.push($(this).val());
			    });
				
				var filterJobType = [];
				$('input:checkbox[name=requirementType]:checked').each(function() 
			    {
					filterJobType.push($(this).val());
			    });
				
				var filterAssignTo = [];
				$('input:radio[name=requirementAssignTo]:checked').each(function() 
			    {
					filterAssignTo.push($(this).val());
			    });
				
				var filterSpecJobs = [];
				$('input:checkbox[name=requirementSpecJobs]:checked').each(function() 
			    {
					filterSpecJobs.push($(this).val());
			    });
				
				var filterProjects = [];
				$('input:checkbox[name=projectNameById]:checked').each(function() 
			    {
					filterProjects.push($(this).val());
			    });
				
				var city = [];
				$('input:checkbox[name=cityName]:checked').each(function() 
			    {
					city.push($(this).val());
			    });
				
				var frm = $('#requirement_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, ""); 
			    var jsonData = encodeURIComponent(con);
				var form = $('#requirement_form')[0];
		  		var formData = new FormData(form); 
		  		var searchValue = $("#dataTableSearch").val();
		  		searchValue = searchValue ? searchValue : "";
		  		$("#requirementList").empty();
				$("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				url = atsUrl+"/searchRequirement?searchType="+searchType+"&filterStatus="+filterStatus+"&filterClients="+filterClients+"&searchValue="+searchValue+"&filterPriority="+filterPriority+"&filterJobType="+filterJobType+"&filterAssignTo="+filterAssignTo+"&filterSpecJobs="+filterSpecJobs+"&filterProjectId="+filterProjects+"&city="+city;
		  		url += "&"+$($('#requirement_form')[0]).serialize();
				
				/* var searchValue = $("#dataTableSearch").val();
				searchValue = searchValue ? searchValue : "";
				$("#requirementList").empty();
				$("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			    url = atsUrl+"/searchRequirement?searchType="+searchType+"&filterStatus="+filterStatus+"&filterClients="+filterClients+"&searchValue="+searchValue; */
		    }
		    else if(searchType == 'advanceSearch'){
		    			 url = atsUrl+"/searchRequirement?searchType="+searchType;
		    			url += "&"+$($('#requirement_form')[0]).serialize();
		    			closePop();
		    			//clearAlldata();
		    }
		    $('#dataTableSearch').removeAttr('disabled');
		    $('#dataTableSearch').attr('disabled','disabled');
		    
		    $("#jobsTab").addClass("current");
		    $("#jobFairTab").removeClass("current");
		    $(".advsrch_chosen").show();
		    $("#jobsTabContent").show();
		    $("#jobFairTabContent").hide();
		    
		    prepareRequirementData(url,atsUrl);
	 } 

	function prepareRequirementData(url,atsUrl){
		
		// var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  var canEduDatePattern = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  var canEduDatePattern = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  var canEduDatePattern = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  var canEduDatePattern = 'YYYY-DD-MM'; 
		  }
		//var orgATS = '${SourceLeadWeb_ats}';
		//var orgVMS = '${SourceLeadWeb_vms}';
		 $("#requirementList").removeAttr("style");
		 $("#requirementList").empty();
		 var result = "";
		 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='100%' >"; 
	   result = result +"<thead>"; 
	   result = result +"  <tr>"; 
	   result = result +"    <th>requirementId</th>";
	   result = result +"    <th>Action</th>"; 
	   result = result +"    <th>Generated ID</th>"; 
	   result = result +"    <th>Title</th>";
	   result = result +"    <th>Process</th>";
	   result = result +"    <th>Client</th>";
	   result = result +"    <th>Pay Rate</th>";
	   result = result +"    <th>Bill Rate</th>";
	   result = result +"    <th>Type</th>"; 
	   result = result +"    <th>Location</th>"; 
	   result = result +"    <th>Applicants</th>";
	   result = result +"    <th>Shortlisted</th>";
	   result = result +"    <th>Submit to Client</th>";
	   result = result +"    <th>Interview</th>";
	   result = result +"    <th>Offer</th>";
	   result = result +"    <th>Onhold</th>";
	   result = result +"    <th>No Response</th>";
	   result = result +"    <th>Joined</th>";
	   result = result +"    <th>Backout</th>";
	   result = result +"    <th>Rejected</th>";
	   result = result +"    <th>Owner</th>"; 
	   result = result +"    <th>Posted On<br>(<small class='custom-small'>"+canEduDatePattern+"</small>)</th>";
	   result = result +"    <th>Experience</th>";
	   result = result +"    <th>noOfpositions</th>";
	   result = result +"    <th>Industry Name</th>";
	   result = result +"    <th>Status</th>"; 
//	    result = result +"<th>Actions</th>";
	   result = result +"  </tr>"; 
	   result = result +"</thead>";
	   result = result +"<tbody>"; 
	       
	    result = result + '<div class="new_side_wrap">';
		result = result + '<div class="new_side_wrap_inn">';
		result = result + '<div class="new_side_wrap_cta">';
		result = result +'<div id="addApplicantDiv"></div>';
		result = result +'</div>';
		result = result +'</div>';
		result = result +'</div>';
	   result = result +" </tbody>";
	   result = result +"</table>"; 
	   $("#noReq").val('');
	   $("#requirementList").append(result); 
	   jpagination(url,atsUrl); 
	   
	   /* to call requirement conformation saving message */
	   msgFadein1();
	}
	//inlcuded in jsp due to jstl tags inclusion.
	var reqTable = "";
	function jpagination(url,atsUrl){ 
		var selectedColumns = "";
		$("#jobCustomColumns").each(function () {
			
		});
		var colIndex = 0;
		reqTable =  $('#requirementPagination').DataTable( { 
	   		"processing": true,
	       "serverSide": true,
	       "searching": false,
	       "searchHighlight": true,
	       "ajax": $.fn.dataTable.pipeline( {
	           url: url,
	           method: 'POST',
	           pages: 1
	       }),
	       "columns": [
	    	   { "data": "requirementId" },
	    	   { "data": "requirementUUID" },
	    	   { "data": "generatedId"},
	           { "data": "jobTitle"},
	           { "data": "process"},
	           { "data": "clientName"},
	           { "data": "rate"},
	           { "data": "billRate"},
	           { "data": "jobType" },
	           { "data": "country"},
	           { "data": "totalApplicants" },
	           { "data": "shortListApplicantsCount" },
	           { "data": "submitToClientsCount" },
	           { "data": "interviewCount" },
	           { "data": "offerCount" },
	           { "data": "onHoldCount" },
	           { "data": "noResponseCount" },
	           { "data": "joinedCount" },
	           { "data": "backOutCount" },
	           { "data": "rejectedCount" },
	           { "data": "manager" },
	           { "data": "createdDate"},
	           { "data": "experience" },
	           { "data": "positions" },
	           { "data": "industryName" },
	           { "data": "requirementSubmissionStatus" }
	          
	       ],
		       "columnDefs": [ 
		    	   {
		   	   	  	"targets": colIndex++,
		   		  	"data": "requirementId",
		   		  	"sortable": true,
		   		    "visible": false,
		   		  	"render": function(data, type, opt, meta) {
		   		  		 var result = "";
		   		  		 result = result +"<td>";
		   		  	  	 if(opt.id != '' && opt.id != null){
		   		  		   	 result = result +"<div class='tbldata-dotdot_50 tblminwidth-75' title='"+opt.id+"'>"+opt.id+"</span></div>";
		   		  	    	}else{
		   		  	  		 result = result +"<div>-----</div>";
		   		  	  	 }
		   		  		 result = result +"</td>";
		   	             return  result;
		   		  	}
		   	        },{
			         "targets": colIndex++,
			         "sortable": false,
			         "data": "requirementUUID",
			         "render": function ( data, type, opt, meta ) {
		         		   var result = "";
		         		   var trashActive = "";
		         		  result = result +" <td><div class=' widthIcon widthIcon-four'>";
		         		
		         		 //'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_REQUIREMENT\',\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
		         		 if(roleActionsArray.indexOf("ROLE_CREATE_REQUIREMENT") > -1 || roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") || roleActionsArray.indexOf("ROLE_VIEW_EDIT_REQUIREMENT")){
		         		 if((opt.isRequirementReleased != 'Y' && !opt.totalApplicants > 0) && loginUserRole ==  'ROLE_ADMIN' || loginUserRole== 'ROLE_SOURCELEAD_ADMIN'){
		         			if(jQuery.inArray(opt.requirementUUID, reqUUIDArr) !== -1){
		         				trashActive = "trashActive";
		         			}else{
		         				trashActive = "trashIcon";
		         			}
		         		 		result = result +'<div class="text-center"><a href="javascript:void(0)" title="Select for Delete Job" class="add-applicant anchorBlack"><i class="fa fa-trash '+trashActive+'" id="'+opt.requirementUUID+'" onclick="selectDeleteJob(this);" ></i></a></div>';
		         		 }else{
		         				result = result +'<div class="text-center"><i class="fa fa-trash permissionDenied"></i></div>';
		         		 }
		         		 }//'</sec:authorize>';
		         		
		         		
		         		//  '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
		         		 if(roleActionsArray.indexOf("ROLE_ADD_APPLICANT")){
		      		        if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
		      		        	result = result +'<div class="text-center"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant('+opt.id+',\''+opt.reqCoolingOffPeriod+'\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>';		        
		      		        }else{
		      		        	result = result +'<div class="text-center"><i class="fa fa-user-plus permissionDenied"></i></div>';
		      		        }
		         		 }//'</sec:authorize>';
			      	     // '<sec:authorize access="!hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
			      	       if(roleActionsArray.indexOf("ROLE_ADD_APPLICANT") == -1){
			      	    		result = result +'<div class="text-center"><i class="fa fa-user-plus permissionDenied"></i></div>';
			      	       }//'</sec:authorize>';
		      	        
		      	      //	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
		      	      if(roleActionsArray.indexOf("ROLE_VIEW_EDIT_REQUIREMENT") > -1){
	       				result = result +'<div class="text-center"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv('+opt.id+');"><i class="fa fa-address-book '+(opt.assignedMembersCount == 0 ? "anchorBlack" : "")+' "></i></a></div>';
		      	      }//'</sec:authorize>';
	       		 	//'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
	       		    if(roleActionsArray.indexOf("ROLE_VIEW_EDIT_REQUIREMENT")  == -1){
	       				result = result +'<div class="text-center"><i class="fa fa-address-book permissionDenied"></i></div>';
	       		    }//'</sec:authorize>';
		      	        
		      	       // '<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
		      	        if(roleActionsArray.indexOf("ROLE_RELEASE_REQUIREMENT") > -1){
		      					if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
		      						   var userRoleName = loginUserRole; 
		      						   var userId = userId;
		      						   var contains = false;
		      						   if(opt.requirementReleasedUserIds != null && opt.requirementReleasedUserIds != '' && opt.requirementReleasedUserIds != 'undefined'){
		      							   for(var k = 0;k< opt.requirementReleasedUserIds.length ; k++){
		      								  if(userId == opt.requirementReleasedUserIds[k]){
		      									  var contains = true;
		      								  }
		      							   }
		      						   }
		      						   if((opt.isRequirementReleased != null && opt.isRequirementReleased != '' && opt.isRequirementReleased != 'undefined'  && opt.isRequirementReleased == 'Y') 
		      								   && (contains == true || userRoleName ==  'ROLE_ADMIN' || userRoleName == 'ROLE_SOURCELEAD_ADMIN')){
		      				    		 	result = result +"<div><a href='/ats/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"' title='Release Job'><i class='fa fa-bullhorn success'></i></a></div>"; 
		      			
		      						   	 }else{	
		      								result = result +"<div><a href='/ats/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"' title='Release Job'><i class='fa fa-bullhorn'></i></a></div>"; 
		      						   	 }
		      			       }
		      				   else{
		      					 result = result +'<div><i class="fa fa-bullhorn permissionDenied"></i></div>';
		      				   } 
		      	        }//'</sec:authorize>'; 
		                   //'<sec:authorize access="!hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
		                   if(roleActionsArray.indexOf("ROLE_RELEASE_REQUIREMENT") == -1){
		                   		result = result +'<div><i class="fa fa-bullhorn permissionDenied"></i></div>';
		                   }//'</sec:authorize>';
		      		       result = result +"</div></td>";
			              return result;
		          }
			 },{
		   	  	"targets": colIndex++,
			  	"data": "generatedId",
			  	"sortable": true,
			  	"visible": $("input[data-name='generatedId']").is(":checked"),
			  	"render": function(data, type, opt, meta) {
			  		 var result = "";
			  		 result = result +"<td>";
			  	  	 if(opt.generatedId != '' && opt.generatedId != null){
			  	  		
			  		   	 result = result +"<div class='tbldata-dotdot_50 tblminwidth-75' title='"+opt.generatedId+"'>"+opt.generatedId+"</span></div>";
			  	    	}else{
			  	  		 result = result +"<div>-----</div>";
			  	  	 }
			  		 result = result +"</td>";
		             return  result;
			  	}
		        } ,{
	           "targets": colIndex++,
	           "data": "jobTitle",
	           "render": function ( data, type, opt, meta ) {
	        	   //for export pop-up
	        	   $("#requirementEmptyListMessage").val(1);
	           		var result = "";
	           	 if(opt.jobId != '' && opt.jobId != null){
	       		  //	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
	       		  if(roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") > -1 || roleActionsArray.indexOf("ROLE_VIEW_EDIT_REQUIREMENT") > -1){
	       		       	result = result +"<td><a href='/ats/requirementSummary?requirementUUID="+opt.requirementUUID+"'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
	       		  }//   '</sec:authorize>';
	       		    //   '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
	       		    if(roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") == -1 || roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") == -1){
	       		       	result = result +"<td><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
	       		    }// '</sec:authorize>';
	       		  	 }else{
	       		  		 //'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
	       		  	 if(roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") > -1 || roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") >-1){
	       		           	result = result +"<td><a href='/ats/requirementSummary?requirementUUID="+opt.requirementUUID+"'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
	       		  	 }//  '</sec:authorize>';
	       		        //   '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
	       		  	 	if(roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") == -1 || roleActionsArray.indexOf("ROLE_VIEW_REQUIREMENT") ==-1){
	       		           	result = result +"<td><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
	       		  	 	}// '</sec:authorize>';
	       		  	 }
		                return result;
	            }
	           },
	           {
		            "targets": colIndex++,
		            "data": "process",
		            "sortable": false,
		            "visible": $("input[data-name='process']").is(":checked"),
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
		       		       	result = result +"<td><div class='tbldata-dotdot_140' " + (opt.process != null && opt.process != "" ? "title='" + opt.process + "'" : "") + ">" + (opt.process != null && opt.process != "" ? opt.process : "------") + "</div></td>";
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "clientName",
		            "visible": $("input[data-name='clientName']").is(":checked"),
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
	           	      		
	           			 //'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	           			 if(roleActionsArray.indexOf("ROLE_VIEW_CLIENT") > -1 || roleActionsArray.indexOf("ROLE_VIEW_EDIT_CLIENT") >-1){
	           	            result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+vmsUrl+'/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.clientName+'">'+opt.clientName+'</a></div>';
	           	         if(opt.clientContactName != null && opt.clientContactName != '' && opt.clientContactName != 'undefined'){
		     	        	  result = result + '\n<div  class="tbldata-dotdot-sm">'+opt.clientContactName+'</div>';
		     	           }
		     	           result = result + '</td>';
	           			 }//'</sec:authorize>';
	           	         
		           	     // '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	           			if(roleActionsArray.indexOf("ROLE_VIEW_CLIENT") == -1 || roleActionsArray.indexOf("ROLE_VIEW_EDIT_CLIENT") ==-1){
		     	            result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.clientName+'</div>';
		     	           if(opt.clientContactName != null && opt.clientContactName != '' && opt.clientContactName != 'undefined'){
		     	        	  result = result + '\n<div  class="tbldata-dotdot-sm">'+opt.clientContactName+'</div>';
		     	           }
		     	           result = result + '</td>';
	           			}// '</sec:authorize>';
	           	         
	           		   }else{
							result = result +"<td><div>-----</div></td>";
	           	       }
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "rate",
		            "sortable": false,
		            "visible": $("input[data-name='payRate']").is(":checked"),
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if((opt.rate != null && opt.rate != '' && opt.rate != 'undefined') || (opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined')){
	           				result = result +"<div class='tbldata-dotdot'>"+opt.currencyCode+" ";
	           				if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
	           					result = result + opt.rate;
	           				}
	           				if((opt.rate != null && opt.rate != '' && opt.rate != 'undefined') && (opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined')){
	           					result = result + " - ";
	           				}
							if(opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined'){
								result = result + opt.maxRate;
	           				}
	           				result = result +" / "+opt.rateType+"</span></div>";
	           		   }else{
							result = result +"<td><div>-----</div></td>";
	           	       }
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "billRate",
		            "sortable": false,
		            "visible": $("input[data-name='billRate']").is(":checked"),
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
		            		if((opt.clientRate != null && opt.clientRate != '' && opt.clientRate != 'undefined') || (opt.clientMaxRate != null && opt.clientMaxRate != '' && opt.clientMaxRate != 'undefined')){
		           				result = result +"<div class='tbldata-dotdot'>"+opt.clientCurrencyCode+" ";
		           				if(opt.clientRate != null && opt.clientRate != '' && opt.clientRate != 'undefined'){
		           					result = result + opt.clientRate;
		           				}
		           				if((opt.clientRate != null && opt.clientRate != '' && opt.clientRate != 'undefined') && (opt.clientMaxRate != null && opt.clientMaxRate != '' && opt.clientMaxRate != 'undefined')){
		           					result = result + " - ";
		           				}
								if(opt.clientMaxRate != null && opt.clientMaxRate != '' && opt.clientMaxRate != 'undefined'){
									result = result + opt.clientMaxRate;
		           				}
		           				result = result +" / "+opt.clientRateType+"</span></div>";
		           		   }else{
							result = result +"<td><div>-----</div></td>";
	           	       }
			                return result;
		             }
		        },
	       	{
		            "targets": colIndex++,
		            "data": "jobType",
		            "visible": $("input[data-name='jobType']").is(":checked"),
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
		            		 if(opt.jobType != null && opt.jobType != '' && opt.jobType != "undefined")
		            	   	 {
		            	  	  if(opt.otherJobType !=null && opt.otherJobType !='' && opt.otherJobType !="undefined"){
		            	         result = result +"  <td><div>"+opt.otherJobType+"</div></td>";
		            	   	 }else{
		            	   		result = result +"  <td><div class='tblminwidth-75'>"+opt.jobType+"</div></td>"; 
		            	   	 }
		            	   	 }else{
		            	   		 result = result +"  <td><div class='tblminwidth-75'>----</div></td>"; 
		            	   	 }
			                return result;
		             }
	       	},
	        	{
		            "targets": colIndex++,
		            "data": "country",
		            "visible": $("input[data-name='location']").is(":checked"),
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
		            		
			            	    result = result +'<td>';
			   		        	result = result +'<div class="positionRelative">';
				   		         for(var k=0; k<1; k++){
				   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.locationsList[k]+'</p>';
				   		         }
			   		    	
				   		        result = result +'<div class="show_Empdetails showjoblist">';
			   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';
		
			   		        	result = result +'<div class="emailvalues emailvaluesNew">';
			   		        	
	 							for(var k=0; k<opt.locationsList.length; k++){
	 								result = result +'<p><i class="fa fa-map-marker"></i>'+opt.locationsList[k]+'</p>';
	 			   		        	
			   		    	 	}
			   		        	result = result +'</div>';
			   		        	result = result +'</div>';
			   

		   		        	result = result +'</div>';
		   		        	result = result +'</td>';
		            		
		            		
		       /*      		if(opt.state!=null && opt.state !='' && opt.state !='undefined' &&  opt.city !='undefined' && opt.city !='' &&  opt.city!=null){
		            	           result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.city+', '+opt.state+', '+opt.country+"'>"+opt.city+', '+opt.state+', '+opt.country+"</span></div></td>";
		            	    }else if(opt.state!=null && opt.state !='' && opt.state !='undefined'){
		            	           result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.state+', '+opt.country+"'>"+opt.state+', '+opt.country+"</span></div></td>";
		            	    }else if(opt.country!=null && opt.country !='' && opt.country !='undefined'){
		            	           result = result +"<td><div><span title='"+opt.country+"'>"+opt.country+"</span></div></td>";
		            	    }else if(opt.location!=null && opt.location !='' && opt.location !='undefined'){
		            	    		result = result +"<td><div><span title='"+opt.location+"'>"+opt.location+"</span></div></td>";
		            	    }else{
		            	   		 result = result +"  <td><div class='tblminwidth-75'>----</div></td>"; 
		            	   	 }  */
			                return result;
		             }
			    },
			 	{
			         "targets": colIndex++,
			         "data": "totalApplicants",
			     	 "sortable": false,
			     	"visible": $("input[data-name='totalApplicants']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.totalApplicants!=null && opt.totalApplicants!='' && opt.totalApplicants!='undefined' && opt.totalApplicants > 0){
			         			result = result +"<td><a href='/requirementSummary?type=applicants&requirementUUID="+opt.requirementUUID+"'><div>"+opt.totalApplicants+"</div></a></td>";
			         		}
			         		else{
			         			result = result +"<td><div>"+opt.totalApplicants+"</div></td>";
			         		}
				            return result;
			          }
			 	 },
				 	{
			         "targets": colIndex++,
			         "data": "shortListApplicantsCount",
			     	 "sortable": false,
			     	"visible": $("input[data-name='Shortlisted']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.shortlistCount!=null && opt.shortlistCount!='' && opt.shortlistCount!='undefined' && opt.shortlistCount > 0){
			                     result = result +"<td><a href='requirementSummary?type=SHORTLIST&requirementUUID="+opt.requirementUUID+"'><div>"+opt.totalApplicants+"</div></a></td>";
//	 		         			result = result +"<td><div>"+opt.shortlistCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
				 	{
			         "targets": colIndex++,
			         "data": "submitToClientsCount",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='submitToClient']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.submitToClientCount!=null && opt.submitToClientCount!='' && opt.submitToClientCount!='undefined' && opt.submitToClientCount > 0){
			              
			         			result = result +"<td><a href='requirementSummary?type=SUBMIT_TO_CLIENT&requirementUUID="+opt.requirementUUID+"'><div>"+opt.submitToClientCount+"</div></a></td>";

			         			// 		         			result = result +"<td><div>"+opt.submitToClientCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
				 	{
			         "targets": colIndex++,
			         "data": "interviewCount",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='interview']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.interviewCount!=null && opt.interviewCount!='' && opt.interviewCount!='undefined' && opt.interviewCount > 0){
			         			
			         			result = result +"<td><a href='/requirementSummary?type=INTERVIEW&requirementUUID="+opt.requirementUUID+"'><div>"+opt.interviewCount+"</div></a></td>";

//	 		         			result = result +"<td><div>"+opt.interviewCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
				  	{
			         "targets": colIndex++,
			         "data": "offerCount",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='offer']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.offersCount!=null && opt.offersCount!='' && opt.offersCount!='undefined' && opt.offersCount > 0){
			         			
			         			result = result +"<td><a href='/requirementSummary?type=OFFERMADE&requirementUUID="+opt.requirementUUID+"'><div>"+opt.offersCount+"</div></a></td>";

//	 		         			result = result +"<td><div>"+opt.offersCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
			 	 
				  	{
			         "targets": colIndex++,
			         "data": "onHoldCount",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='onHold']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.onHoldCount!=null && opt.onHoldCount!='' && opt.onHoldCount!='undefined'){
			         			result = result +"<td><div>"+opt.onHoldCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
			 	 
				 	{
			         "targets": colIndex++,
			         "data": "noResponseCount",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='noResponse']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.noResponseCount!=null && opt.noResponseCount!='' && opt.noResponseCount!='undefined'){
			         			result = result +"<td><div>"+opt.noResponseCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
			 	 
				 	{
			         "targets": colIndex++,
			         "data": "joinedCount",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='joined']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.joinedCount!=null && opt.joinedCount!='' && opt.joinedCount!='undefined' && opt.joinedCount > 0){
			         			
			         			result = result +"<td><a href='/requirementSummary?type=JOINED&requirementUUID="+opt.requirementUUID+"'><div>"+opt.joinedCount+"</div></a></td>";

//	 		         			result = result +"<td><div>"+opt.joinedCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
			 	 
				 	{
			         "targets": colIndex++,
			         "data": "backOutCount",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='backOut']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.backoutCount!=null && opt.backoutCount!='' && opt.backoutCount!='undefined' && opt.backoutCount > 0){
			         			
			         			result = result +"<td><a href='/requirementSummary?type=BACKOUT&requirementUUID="+opt.requirementUUID+"'><div>"+opt.backoutCount+"</div></a></td>";

//	 		         			result = result +"<td><div>"+opt.backoutCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
				 	{
			         "targets": colIndex++,
			         "data": "rejectedCount",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='rejected']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.rejectedCount!=null && opt.rejectedCount!='' && opt.rejectedCount!='undefined' && opt.joinedCount > 0){
			         			
			         			result = result +"<td><a href='/requirementSummary?type=REJECTED&requirementUUID="+opt.requirementUUID+"'><div>"+opt.rejectedCount+"</div></a></td>";

//	 		         			result = result +"<td><div>"+opt.rejectedCount+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
			 	 
				 {
			         "targets": colIndex++,
			         "data": "manager",
			         "visible": $("input[data-name='owner']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.manager!=null && opt.manager!='' && opt.manager!='undefined'){
			         	      	 result = result +"<td><div class='tbldata-dotdot-100' title='"+opt.manager+"'>"+opt.manager+"</div></td>";
		         	        }else{
		         	      	 result = result +"<td><div>----</div></td>";
		         	        }
				            return result;
			          }
			 },
			 {
		         "targets": colIndex++,
		         "data": "createdDate",
		         "visible": $("input[data-name='postedOn']").is(":checked"),
		         "render": function (data, type, opt, meta ) {
	         		   var result = "";
	         		   if(opt.postedOnStr!=null && opt.postedOnStr !='' && opt.postedOnStr !='undefined'){
	                  	 result = result +"<td><div class='tblminwidth-125'>"+opt.postedOnStr+"</div></td>";
	                   }else{
	                  	 result = result +"<td><div class='tblminwidth-125'>----</div></td>";
	                   }
		               return result;
		          }
		 },
				 	{
			         "targets": colIndex++,
			         "data": "experience",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='experience']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.minExp!=null && opt.minExp!='' && opt.minExp!='undefined' && opt.maxExp == null){
			         			result = result +"<td><div>min Exp - "+opt.minExp+"</div></td>";
			         		}
			         		else if(opt.maxExp!=null && opt.maxExp!='' && opt.maxExp!='undefined' && opt.maxExp == null){
			         			result = result +"<td><div>max Exp - "+opt.maxExp+"</div></td>";
			         		}
			         		else if((opt.maxExp!=null && opt.maxExp!='' && opt.maxExp!='undefined') && (opt.minExp!=null && opt.minExp!='' && opt.minExp!='undefined')){
			         			result = result +"<td><div>min Exp- "+opt.minExp+"- max Exp - "+opt.maxExp+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
				 	{
			         "targets": colIndex++,
			         "data": "positions",
			     	 "sortable": false,
			     	 "visible": $("input[data-name='noOfPositions']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.positions!=null && opt.positions!='' && opt.positions!='undefined'){
			         			result = result +"<td><div>"+opt.positions+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>0</div></td>";
			         		}
				            return result;
			          }
			 	 },
			 	{
			         "targets": colIndex++,
			         "data": "industryName",
			     	 "sortable": true,
			     	 "visible": $("input[data-name='industryName']").is(":checked"),
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.industryName!=null && opt.industryName!='' && opt.industryName!='undefined'){
			         			result = result +"<td><div>"+opt.industryName+"</div></td>";
			         		}
			         		else{
			         			result = result +"<td><div>--</div></td>";
			         		}
				            return result;
			          }
			 	 },
				 {
				         "targets": colIndex++,
				         "sortable": false,
				         "data": "requirementSubmissionStatus",
				         "render": function (data, type, opt, meta) {
				        	        var contains = false;
				        	        var isAllow = false;
				        	        var approver = false;
				         		    var result = "";
				         		   result = result +"<td><div style='width:75px'>";
				         		   var userRoleName = loginUserRole
				         		   var userId = userId;
				         		  if((opt.level1ApproverUserId != null && opt.level1ApproverUserId != '' && opt.level1ApproverUserId != 'undefined') || (opt.level2ApproverUserId != null && opt.level2ApproverUserId !='' && opt.level2ApproverUserId != 'undefined')){ 
				         		 if(opt.level2ApproverUserId != null && opt.level2ApproverUserId !=''){
				         			 if(userId == opt.level2ApproverUserId){
										   approver = true;
									  } 
				         		 }else{
				         			  for(var a = 0;a< opt.level1ApproverUserId.length ; a++){
	 								  if(userId == opt.level1ApproverUserId[a]){
	 									   approver = true;
	 								  }
	 							   }
				         		 }
				         		  }
				         		   if(opt.isLoginUserAsManager != null && opt.isLoginUserAsManager != '' && opt.isLoginUserAsManager != 'undefined'){
				         			   if(opt.isLoginUserAsManager == userId){
				         				   contains = true;
				         			   }
				         		   }
				         		   
				         		   if(approver == true || contains == true || userRoleName ==  'ROLE_ADMIN' || userRoleName == 'ROLE_SOURCELEAD_ADMIN' || opt.isLoginUserAsApprover == true){   
				         			  isAllow = true;
				         		   }
				         		  
				         		   if(isAllow == true){
				         			  result = result +"<select name='changeStatusId"+opt.id+"' value= '' onchange='changeStatus("+opt.id+");'  id='changeStatusId"+opt.id+"'>";
				         		   }
				         		   else{
				         			  result = result +"<select name='changeStatusId"+opt.id+"' disabled='true'  value= '' onchange='changeStatus("+opt.id+");'  id='changeStatusId"+opt.id+"'>";
				         		   }
				                   if(opt.requirementSubmissionStatus == "DRAFT"){
				         				result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_ONHOLD_REQUIREMENT") > -1 ){
				         	
				         				result = result +"<option value='ONHOLD'>ONHOLD</option>";
				         				}//'</sec:authorize>';
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_CANCEL_REQUIREMENT") > -1 ){
				         					result = result +"<option value='CANCEL'>CANCEL</option>";
				         				}//'</sec:authorize>';
				         			}
				                   else if(opt.requirementSubmissionStatus == "CLOSED"){
				         				result = result +"<option disabled selected >CLOSED</option>";
				         				result = result +"<option value='OPEN'>OPEN</option>";
				         			}
				         			else if(opt.requirementSubmissionStatus == "CANCEL"){
				         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
				         			

				         			}else if(opt.requirementSubmissionStatus == "OPEN"){
				         				result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_CLOSE_FILLED_REQUIREMENT") > -1 ){
				         					result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
				         				}//'</sec:authorize>';
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_CLOSE_UN_FILLED_REQUIREMENT") > -1 ){
				         					result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
				         				}//'</sec:authorize>';
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_ONHOLD_REQUIREMENT") > -1 ){
				         					result = result +"<option value='ONHOLD'>ONHOLD</option>";
				         				}//'</sec:authorize>';
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_CANCEL_REQUIREMENT") > -1 ){
				         					result = result +"<option value='CANCEL'>CANCEL</option>";
				         				}//'</sec:authorize>';
				         			}
				         			else if(opt.requirementSubmissionStatus == "NEW"){
				         				result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
				         				result = result +"<option value='OPEN'>OPEN</option>";
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_ONHOLD_REQUIREMENT") > -1 ){
				         					result = result +"<option value='ONHOLD'>ONHOLD</option>";
				         				}//'</sec:authorize>';
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_CLOSE_FILLED_REQUIREMENT") > -1 ){
				         				result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
				         				}//'</sec:authorize>';
				         			//'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_CLOSE_UN_FILLED_REQUIREMENT") > -1 ){
				         				result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
				         				}//'</sec:authorize>';
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_CANCEL_REQUIREMENT") > -1 ){
				         					result = result +"<option value='CANCEL'>CANCEL</option>";
				         				}//'</sec:authorize>';
				         			}else if(opt.requirementSubmissionStatus == "APPROVED"){
				         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
				         				
				         			}
				         			else if(opt.requirementSubmissionStatus == "REJECTED"){
				         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
				         				
				         			}
				         			
				         			else if(opt.requirementSubmissionStatus == "SUBMITTED"){
				         				result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_ONHOLD_REQUIREMENT") > -1 ){
				         					result = result +"<option value='ONHOLD'>ONHOLD</option>";
				         				}'</sec:authorize>';
				         				//'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
				         				if(roleActionsArray.indexOf("ROLE_CANCEL_REQUIREMENT") > -1 ){
				         					result = result +"<option value='CANCEL'>CANCEL</option>";
				         				}//'</sec:authorize>';
				         				
				         			}else if(opt.requirementSubmissionStatus == "ONHOLD"){
				         				
				         				if(opt.requirementSubmissionPreviousStatus == "OPEN" || opt.requirementSubmissionPreviousStatus == null || opt.requirementSubmissionPreviousStatus == '' || opt.requirementSubmissionPreviousStatus == 'undefined' ){
				         					result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
				         					result = result +"<option value='OPEN'>OPEN</option>";
	 			         					//'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
				         					if(roleActionsArray.indexOf("ROLE_CLOSE_FILLED_REQUIREMENT") > -1 ){
				         						result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
				         					}//'</sec:authorize>';
				         					//'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
				         					if(roleActionsArray.indexOf("ROLE_CLOSE_UN_FILLED_REQUIREMENT") > -1 ){
				         						result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
				         					}//'</sec:authorize>';
				         					 
				         				}
				         					if(opt.requirementSubmissionPreviousStatus == "DRAFT"){
				         					result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
				         					result = result +"<option value='DRAFT'>DRAFT</option>";
				         					//'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
				         					if(roleActionsArray.indexOf("ROLE_CANCEL_REQUIREMENT") > -1 ){
				         						result = result +"<option value='CANCEL'>CANCEL</option>";
				         					}//'</sec:authorize>';
				         				}
				         				if(opt.requirementSubmissionPreviousStatus == "SUBMITTED"){
				         					result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
				         					result = result +"<option value='SUBMITTED'>SUBMITTED</option>";
				         					//'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
				         					if(roleActionsArray.indexOf("ROLE_CANCEL_REQUIREMENT") > -1 ){
				         						result = result +"<option value='CANCEL'>CANCEL</option>";
				         					}//'</sec:authorize>';
				         				}if(opt.requirementSubmissionPreviousStatus == "NEW"){
				         					result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
				         					result = result +"<option value='OPEN'>OPEN</option>";
				         					//'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
				         					if(roleActionsArray.indexOf("ROLE_ONHOLD_REQUIREMENT") > -1 ){
				         						result = result +"<option value='ONHOLD'>ONHOLD</option>";
				         					}//'</sec:authorize>';
				         					//'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
				         					if(roleActionsArray.indexOf("ROLE_CLOSE_FILLED_REQUIREMENT") > -1 ){
				         					result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
				         					}//'</sec:authorize>';
				         				  //'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
				         					if(roleActionsArray.indexOf("ROLE_CLOSE_UN_FILLED_REQUIREMENT") > -1 ){
				         					result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
				         					}//'</sec:authorize>';
				         					//'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
				         					if(roleActionsArray.indexOf("ROLE_CANCEL_REQUIREMENT") > -1 ){
				         						result = result +"<option value='CANCEL'>CANCEL</option>";
				         					}//'</sec:authorize>';
				         				}
				         				 
				         			}else if(opt.requirementSubmissionStatus == "UNFILLEDCLOSED"){
				         				result = result +"<option disabled selected hidden>"+opt.requirementSubmissionStatus+"</option>";
				         				result = result +"<option value='OPEN'>OPEN</option>";
				         				
				         			}else if(opt.requirementSubmissionStatus == "FILLEDCLOSED"){
				         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
				         				result = result +"<option value='OPEN'>OPEN</option>";
				         			}

				         			result = result +" </select>";	
				                    result = result +" </div></td>";
					                return result;
				          }
				 }
				 
				 
	           ],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[0,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"createdRow": function ( row, data, index ) {
		    		row.setAttribute('id',"job_"+data.id);
	    	    },
		    	"initComplete": function( settings, json ) {
		    		 $('#dataTableSearch').removeAttr('disabled');
		    	  },
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     },
		     drawCallback: function(){
	   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
	   	    	jobSearchHighlight();
	   	         }); 
	   	   }
	  
	   });
	   
	  
	   $("#requirementPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	   $("input[type=search]").hide();
	   $('#requirementPagination').DataTable().column(0).visible(false);
	   $("#dataTableSearch").keyup(function(e) {
	   	   if(e.which === 13) {
	   		 requirementList('list',atsUrl);
	   		jobSearchHighlight();
	   	 for (var i=0; i<hideColumns.length; i++) {
			 table.column(hideColumns[i]).visible(false); // hide the columns after filters also.
			}
	   	   }
	   	});
	 }
	function clearAlldata(){
	   $("#requirementStatu").val('').trigger("chosen:updated");
	   $("#updatedOn").val('').trigger("chosen:updated");
	   $("#client").val('').trigger("chosen:updated");
	   $("#state").val('').trigger("chosen:updated");
	   $("#statusAdvanced").val('').trigger("chosen:updated");
	   $("#invitationAdvanced").val('').trigger("chosen:updated");
	   $("#contactNameAdvanced").val('').trigger("chosen:updated");
	   $("#emailAdvanced").val('').trigger("chosen:updated");
	   $("#orgNameAdvanced").val('').trigger("chosen:updated");
	   $("#noticePeriod").val('').trigger("chosen:updated");
	 
	   $("#requirementTitle").empty();
	   $("#jobId").empty();
	 
	}
	/* function clearSearch(){
		document.getElementById("requirement_form").reset();
		$('#requirementStatu').val('').trigger('chosen:updated');
		$('#updatedOn').val('').trigger('chosen:updated');
		$('#client').val('').trigger('chosen:updated');
		$('#state').val('').trigger('chosen:updated');
		$('#city').val('').trigger('chosen:updated');
		$('#noticePeriod').val('').trigger('chosen:updated');
		$('#requirementTitle').val('').trigger('chosen:updated');
		$('#jobId').val('').trigger('chosen:updated');
	} */

	 
	 
	
	function updateFairStatusEvents() {
		$(".fairStatus").each(function() {
			$(this).on('focusin', function(){
			    $(this).data('prev_value', $(this).val());
			}).on('change', function(){
				var jobFairId = $(this).find("option:selected").data("id");
			    var prev = $(this).data("prev_value");
			    showChangeJobFairStatusPopup(jobFairId, $(this).val(), prev, $(this));
			});
		});
	}
	 function jobSearchHighlight(){
		 setTimeout(function () {
	 		var body = $( table.table().body() );
		        body.unhighlight();
		        body.highlight( $("#dataTableSearch").val() );   
				
				}, 1000); 
	 }
	 function jobFairSearchHighlight(){
		 setTimeout(function () {
	 		var body = $( table.table().body() );
		        body.unhighlight();
		        body.highlight( $("#jobFairDataTableSearch").val() );   
				
				}, 1000); 
	 }
		$(document).on( 'init.dt.dth', function (e, settings, json) {
	    table = new $.fn.dataTable.Api( settings );
	    var body = $( table.table().body() );
	    if ( $( table.table().node() ).hasClass( 'searchHighlight' ) || settings.oInit.searchHighlight ||  $.fn.dataTable.defaults.searchHighlight ) {
		
		}
	} );
	function getAssignedMembersDiv(reqId){
		$("#summaryButtonsDiv").hide();
		$("#listButtonsDiv").show();
		$('#listSaveButton').removeAttr('onclick');
		$('#listSaveButton').attr('onClick', 'editAssignedTeamsOrUsers('+reqId+');');
		getAssignedMembersListForEdit(reqId);
		buildSummaryButtonsDivHtml(); // added to build the assign pop up . 
		 //Initially it was prepared with JSTL code. now converted script build code.
		addAssignToCheckBooxListener();//  adding events to the assign to popup.
	}
	var reqUUIDArr = new Array();
	function selectDeleteJob(event) {
		$(event).toggleClass("trashIcon trashActive");
		var x = $(event).attr('id');
		reqUUIDArr.push(x);
		if($(event).attr('class') == "fa fa-trash trashIcon"){
			reqUUIDArr = jQuery.grep(reqUUIDArr, function(value) {
				  return value != x;
				});
		}
		if (reqUUIDArr != "") {
		 $("#forceDeleteBtn").show();	
		}else {
			$("#forceDeleteBtn").hide();
		}
	}

	function confirmForceDeleteBtn(){
		$("#requirementList").empty();
		$("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			type: "POST",
		    url: atsUrl+"/forceDeleteForSelectedJobs?"+tokenParamName+"="+tokenValue+"&reqUUIDArr="+reqUUIDArr, 
		    success: function(result) {
		    	if(result.object === 'MissingData'){
		  			showError("Invalid data");
		  		}else{
		  			if (result.response === "SESSION_TIMEDOUT") {
		  				location.replace(sourceleadUrl);
	                } else if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
	                	location.reload();
	                } else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
	                    showError("Operation failed, please try again");
	                }
		  		}
		    },
		});
		$("#remove_user_pop, .project_inn_mask").fadeOut();
	}

	function jobForceDelete(){
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>You are sure to want to Delete?</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input id='confirmForceDeleteBtn' type='button' value='Ok' onClick='confirmForceDeleteBtn();'><input class='u_can_color' type='button'  value='Cancel' onClick='closeJobDeleteConformation();'></b>";
		result = result +"	</div>";
		$("#deleteJobPopUpDiv").empty();
		$("#deleteJobPopUpDiv").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}

	function closeJobDeleteConformation(){
		$("#remove_user_pop, .project_inn_mask").fadeOut();
	}// requirementTable.jsp scripts end here. updated till now.

//requirementAssignedTo.jsp start here...
	function getAssignedMembersListForEdit(reqId){
		if(reqId != '' && reqId != 'undefined' && reqId != null){
			$.ajax({
		           type : "POST",
		           url: atsUrl+"/getAssignedMembersForEdit?reqId="+reqId,    
		            success :function(result) {
			            	var json = result.object;
			            	if (result.response === "SESSION_TIMEDOUT") {
				  				location.replace(sourceleadUrl);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	selectAssignUsersIds = json;
				            	$("#assignedTeamId").val(json);	
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    showError("Operation failed, please try again");
			                }
		            },
		            complete: function (result) {
		            	getTeamsList(reqId);
		            }
		      }); 
			
		}
	}

function filterTeamMembers(){
			var teamType = $("#teamSelectionCategory").val();
			getUserList(teamType);
		}
	window.selectAssignUsersIds = new Array();
	var isIE = /*@cc_on!@*/false || !!document.documentMode;
	if(isIE){
	(function() {
		  function remove() { this.parentNode && this.parentNode.removeChild(this); }
		  if (!Element.prototype.remove) Element.prototype.remove = remove;
		  if (Text && !Text.prototype.remove) Text.prototype.remove = remove;
		})();
	};	
	function deSelectAssignUsers(){
		$("input:checkbox[name=selectedUser]:checked").each(function(){
			var deSelectedId = $(this).attr('value');
			$(this).closest( 'tr' )[0].remove();
			$("#assignToDataTableId input[type=checkbox]").each(function(){
				if(deSelectedId == $(this).attr('value')){
					$(this).prop('disabled', false);
				}
		     });
			
			const index = selectAssignUsersIds.indexOf(deSelectedId);
			selectAssignUsersIds.splice(index,1);
			
//	 		selectAssignUsersIds = selectAssignUsersIds.filter(item => item !== deSelectedId);
		});
		$("#assignToSelectedPopupCount").text(" ("+$('#assignToSelectedDataTableDiv >table >tbody >tr').length+") ");
		if($('#assignToSelectedDataTableDiv >table >tbody >tr').length == 0){
			$("#noAssignedmembers").show();
		}else{
			$("#noAssignedmembers").hide();
		}
	}

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
			$(this).attr('checked', false);
	     });
		$("#assignToSelectedPopupCount").text(" ("+$('#assignToSelectedDataTableDiv >table >tbody >tr').length+") ");
		if($('#assignToSelectedDataTableDiv >table >tbody >tr').length == 0){
			$("#noAssignedmembers").show();
		}else{
			$("#noAssignedmembers").hide();
		}
	}
	function getUserList(teamType){
		$("#usersGrid").empty(); 
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
	 	   $("#usersGrid-loader").attr('style','position:absolute; width:100%; height:200px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	   $.ajax({
	           type : "POST",
	           url: atsUrl+"/getUserList?"+tokenParamName+"="+tokenValue+"&teamType="+teamType,    
	            success :function(data) {
	            	$("#usersGrid").removeAttr("style");
	            	if (data.response === "SESSION_TIMEDOUT") {
		  				location.replace(sourceleadUrl);
	                } else if (data.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (data.response === "SUCCESS") {
		                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
			                	    var result = '';
			                	    //result = result+"<input type='hidden' name='assignedTeamId' id='assignedTeamId' value='"+teamType+"'/>";
				                    var json = data.data;
				                    //var selectedTempIds = $("#assignedTeamId").val();
				                    for (var i = 0; i < json.length; i++) {
				                    	 var opt = json[i];
					                     var isSelected = "";
					                     var isBlockedBySelected = "";
					                     var selectAssignUsersIdsStr = ""+selectAssignUsersIds+"";
					                     var str_array = selectAssignUsersIdsStr.match(/[^,]+,[^,]+/g);
					                     if(str_array != null && str_array != 'undefined' && str_array != "" && str_array != '[]'){
						                     for(var j = 0; j < str_array.length; j++) {
						                        if((""+str_array[j]+"") === (""+opt.id+','+opt.assignedTeamId+"")){
							                    	 isSelected = "checked disabled";
							                     }else if((""+str_array[j]+"") === (""+opt.id+"")){
							                    	 isBlockedBySelected = "fa fa-ban";
							                    	 isSelected = "disabled";
							                     }
						                     }
					                     }
					                     /* if(selectAssignUsersIds.includes(opt.id+','+opt.assignedTeamId)){
					                    	 isSelected = "checked disabled";
					                     }else if(selectAssignUsersIdsStr.includes(opt.id)){
					                    	 isBlockedBySelected = "fa fa-ban";
					                    	 isSelected = "disabled";
					                     } */
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
				                    	 result = result +'             <td><span class="tbldata-dotdot_80"><div id="'+opt.id+'assignedUserType"><i class="fa fa-spinner fa-pulse"></i></div></span></td>';
				                    	 result = result +'             <td><span class="tbldata-dotdot_30 requirementAssignedCount"  id="'+opt.id+'"><div id="'+opt.id+'requirementAssignedCount"><i class="fa fa-spinner fa-pulse"></i></div></span></td>';
				                    	 result = result +'           </tr>';
			                       }
				                    
			                 	   //result = result +'	<span class="jquery_form_error_message" style="display:none;">Assign at least to a new member</span>'; */
			                 	   $("#usersGrid-loader").removeAttr("style");
			                       $("#usersGrid").append(result);
			                       getUserAssingedJobCounts();
			                       $("#checkAll").prop('checked', false);
			                       if(isTeamExists && isRoleExists){
			                    	   $("#checkAll").prop('disabled', true);
			                       }else{
			                    	   $("#checkAll").prop('disabled', false);
			                       }
			                      
			                       //paginations(); 
		                 	}
		                } else if (data.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
	          		}
	       		});
	  	} 
	function getUserAssingedJobCounts(){
		 $('.requirementAssignedCount').each(function() {
			 var userId = this.id;
			 if(userId != null &&  userId !='undefined' && userId != ''){
				 $.ajax({
			           type : "GET",
			           url: atsUrl+"/getUserAssingedJobsListCountForRequirementAssignedTo?userId="+userId,    
			            success :function(data) {
			            	  $("#"+userId+"assignedUserType").empty();
			             	  $("#"+userId+"assignedUserType").html(data.userType);
			             	  		             	  
			             	  $("#"+userId+"requirementAssignedCount").empty();
			             	  $("#"+userId+"requirementAssignedCount").html(data.assignedJobsCount);
			            },complete: function (data) {
			            	selectAssignUsers();
		                }
				    });
			 }
				
	   });  
	}
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
	  var  addAssignToCheckBooxListener = function(){ // used for check box events on assign to popup
		$(':checkbox[id=checkAll]').click(function() {
			//toggle all checkboxes except disabled checkboxes
			$(':checkbox[name=team]').not("[disabled]").prop('checked', this.checked);
		});

		$(':checkbox[id=checkAllSelected]').click(function() {
			//toggle all checkboxes
			$(':checkbox[name=selectedUser]').prop('checked', this.checked);
		});
	  }	

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
			$("#usersGrid").empty();
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
			           url: atsUrl+"/saveOrUpdateAssignToTeam?"+tokenParamName+"="+tokenValue+"&assignedIdVal="+assignedIdVal+"&requirementId="+requirementId,    
			            success :function(result) {
			            	if (result.response === "SESSION_TIMEDOUT") {
				  				location.replace(sourceleadUrl);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    showError("Operation failed, please try again");
			                }
			            },
		   		});
			$("#usersGrid").empty();
		}
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

	$('#assignToPopUpCloseId').submit(function() {
		$("#usersGrid").empty();
	}); // requirementAssignedTo.jsp end here updated.
	
// addApplicantFromRequirementList.jsp starts here======= // updated.

	function ApplicantPermit(){
		 if($("#needApplicantPermit").is(':checked')){
			  $("#needApplicantPermit").val("Y");
		 }else{
			 $("#needApplicantPermit").val("N");
		 }
	}
	function NotifyToApplicant(){
		 if($("#notifyToApplicantId").is(':checked')){
			  $("#notifyToApplicantId").val("Y");
		 }else{
			 $("#notifyToApplicantId").val("N");
		 }
	}

	function isCoolingOffAlert(){
		var reqCoolingOffPeriod = document.getElementById("reqCoolingOffPeriod").value;
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Applicant Cooling-Off period "+reqCoolingOffPeriod+" not completed</p>";
		result = result + ' <div id="reasonForSpocDiv"/>';
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Proceed' onClick='proceedApplicant();'><input class='u_can_color' type='button' value='Discard' onClick='discordApplicant();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	function proceedApplicant(){
		$("#popUp").empty();
		$(".project_inn_mask").fadeOut(200);
	}
	function discordApplicant(){
		$("#save_button").attr("disabled", "disabled");
		$("#popUp").empty();
		$(".project_inn_mask").fadeOut(200);
		
		$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	}
	 function isCoolingOff(requirementId){
		var candidateEmail =   $("input[name=EmailId]").val();
		//var reqCoolingOffPeriod ='${requirement.reqCoolingOffPeriod}';
		//var orgLocationId ='${SourceLeadWeb_orgLocationId}';
		$.ajax({
			type: "POST",
		    url: atsUrl+"/verifyCoolingOffPeriod?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&candidateEmail="+candidateEmail+"&reqCoolingOffPeriod="+reqCoolingOffPeriod+"&orgLocationId="+orgLocationId, 
		    success: function(response) {
		    	if (response.response === "AWKWARD") {
		    	    location.href = "/errorPage";
		    	} else if (response.response === "SUCCESS") {
		    		if(response.object === true) {
			    		isCoolingOffAlert();
			    		return true;
			    	} else {
			    		return false;
			    	}
		    	} else if (response.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    	    showError("Operation failed, please try again");
		    	}
		    },
		});
	}
	 function getCandidatesList(requirementId,candidateType) {
		 $('#sjfb-fields').find('p.jquery_form_error_message').remove();
		 $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
		 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
		 
		 $("input[name=AlternateNumber]").val(''); 
		 $("input[name=DOB]").val('');
		 $("input[name='Current/LastCompany']").val('');
		 $("select[name=WorkExperience]").val(0);
		 $("select[name=monthExp]").val(0);
		 if($("#needApplicantPermit").is(':checked')){
			 document.getElementById("needApplicantPermit").checked = false;
			 $("#needApplicantPermit").val('N');
		 }
		 
		 $('#candidateId').removeAttr('data-validation-optional');
		 $("#candidateId").attr("data-validation", "required");
		 
		 if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType != 'New' && candidateType != 'parseCandidate'){
			 $("#selectCandidateDiv").css("display","block");
			
			 var skillsDiv = document.getElementById('skillsDiv'); 
			 if(skillsDiv != null){
				 document.getElementById("skillsDiv").style.display="none";
				 $("#candidateSkills").val('').trigger("chosen:updated");
				 document.getElementById("primarySkillDiv").style.display="none";
				 $("#primarySkill").val('');
			 }
			 
			 var employmentTypeDiv = document.getElementById('employmentTypeDiv');
			 if(employmentTypeDiv != null){
				 document.getElementById("employmentTypeDiv").style.display="none";
				 $("#designationType").removeAttr("data-validation");
			     $('#designationType').attr("data-validation-optional","true");
			     $("#designationType").empty();
			 } 
			 
			 
			 if(requirementId != null && requirementId != "" && requirementId != 'undefined'){
		
				 $.ajax({
				    	type: "POST",
				        url: atsUrl+"/getCandidatesList?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 
				        success: function(response) {
				        	if (response.response === "AWKWARD") {
				        	    location.href = "/errorPage";
				        	} else if (response.response === "SUCCESS") {
				        		var json = response.data;
					        	var output = "";
					        	output = output + "<option value=''>-- Select Candidate --</option>"; 
					        	for (var i=0; i<json.length; i++) {
						        		var opt = json[i];
						        		var candidateType = "";
						        		if(opt.isAssociate == 'Y'){
						        			candidateType = 'Associate';
						        		}else{
						        			candidateType = 'Candidate';
						        		}
						        		output = output + "<option value='" +opt.candId+ "'>"+opt.candidateName+" ("+candidateType+")</option>";
								}
					        	$("#candidateId").empty();
						        $("#candidateId").append(output);
						        $('#candidateId').trigger("chosen:updated");
						        getCandidateResumes();
				        	} else if (response.indexOf('Sign In') !== -1) {
				        		location.replace(sourceleadUrl);
				        	} else {
				        	    showError("Operation failed, please try again");
				        	}
				        },
				    }); 
			 }else{
				 var output = "";
		     	 output = output + "<option value=''>-- Select Candidate --</option>"; 
		     	 $("#candidateId").empty();
			     $("#candidateId").append(output);
			     $('#candidateId').trigger("chosen:updated");
			 }
		 }else{
			 var output = "";
	     	 $("#candidateId").empty();
		     $("#candidateId").append(output);
		     $('#candidateId').trigger("chosen:updated");
		     $("#selectCandidateDiv").css("display","none");
		     
		     $("#candidateId").removeAttr("data-validation");
		     $('#candidateId').attr("data-validation-optional","true"); 
		     
		     
		     if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'parseCandidate'){
		    	 document.getElementById("browseDiv").style.display="";
		    	 $('#uploadFile').removeAttr('data-validation-optional');
				 $('#uploadFile').attr("data-validation","required validatedoc "); 
				 
				 document.getElementById("newResumeDiv").style.display="none";
				 document.getElementById("skillsDiv").style.display="";
				 document.getElementById("primarySkillDiv").style.display="";
				 
				 document.getElementById("employmentTypeDiv").style.display="";
				 $("#designationType").removeAttr("data-validation-optional");
			     $('#designationType').attr("data-validation","required"); 
				
			     $("#candidateSkills").val('').trigger("chosen:updated");
				 $("#primarySkill").val('');
		     }else if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'New'){
		    	 document.getElementById("browseDiv").style.display="none";
		    	 $('#uploadFile').removeAttr('data-validation');
				 $('#uploadFile').attr("data-validation-optional","true"); 
		    	 
		    	 document.getElementById("newResumeDiv").style.display="";
		    	 document.getElementById("skillsDiv").style.display="";
		    	 document.getElementById("primarySkillDiv").style.display="";
		    	 
		     	 document.getElementById("employmentTypeDiv").style.display="";
		    	 $("#designationType").removeAttr("data-validation-optional");
			     $('#designationType').attr("data-validation","required"); 
		    	
			     $("#candidateSkills").val('').trigger("chosen:updated");
				 $("#primarySkill").val('');
				 
				
		     }
		     document.getElementById("resumesDiv").style.display="none";
			 document.getElementById("orConditionDiv").style.display="none";
			
		 }
		 
		 $("input[name=FirstName]").val('');
		 $("input[name=LastName]").val('');
		 $("input[name=EmailId]").val('');
		 $("input[name=EmailId]").removeAttr("readonly");
		 
		 
		 $("input[name=PhoneNumber]").val('');
		 $("input[name=Designation]").val('');
		 $("select[name=NoticePeriod]").val('');
		 $("select[name=WorkExperience]").val(0);
		 $("input[name=ExpectedCTC]").val('');
		 $("input[name=CurrentCTC]").val('');
		 $("input[name=Education]").val('');
		 
		 $("#newUploadFile").val('');
		 $("#uploadFile").val('');
		 
		 getCandidateStates();
		 getSubmissionCandidateCities();
		
	 }

	//<!-- For add New Applicant -->

	   var addListernerPopupClose= function (){
	
	   }	
	<!-- For add New Applicant -->
	function addApplicantForm(){
		  var requirementId = $("#requirementId").val();
		  if ($('#addApplicant_form').validate(false, validationSettings)){
	      //e.preventDefault();
	      var frm = $('#addApplicant_form').serializeFormJSON();
	      var frm = $('#addApplicant_form').serializeFormJSON();
	      var con1 = JSON.stringify(frm);
	      con1= con1.replace(/[{}]/g, "");
	      var jsonData = encodeURIComponent(con1);
	      var form = $('#addApplicant_form')[0];
	      var formData = new FormData(form); 
	      //Loop through fields and save field data to array
	      var fields = [];
	      $('.sjfb-field').each(function() { 
	      	  var $this = $(this); 
	      	  var divId = $(this).attr('id');  
	      	  var fieldLabel = $this.find('label:first').text().replace("*","");
	      	  var inputValue = $this.find('input[type="radio"]:checked').val();
	      	  
	    	 var inputtype = $(this).find('input').attr('type');
		   	  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
		   		  if($(this).find('select').val() != null){
		   			  inputtype = 'select';
		   		  }else{
		   			  inputtype = 'textarea';
		   		  }
		   	  }
	      	  
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
		     	  
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('textarea').attr('name');
	     	      }
		     	  
		     	  if(name == "WorkExperience"){
		     		 var monthExp = document.getElementById("monthExp").value; 
		     		 if(inputValue != null && inputValue != 'undefined' && inputValue != ''){
		     			  if(inputValue == "50"){
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
	                type : inputtype,
	                id :id,
	                name :name,
	                value: inputValue,
	            });
	       });
	      var con = JSON.stringify(fields);
	      
	      con = encodeURIComponent(con);
	     
	      
			
	       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	      
	  	     $.ajax({
		    	type: "POST",
		    	data: formData,
	 	        contentType : 'application/json; charset=utf-8',
	 	        enctype: 'multipart/form-data',
		        processData: false,
		        contentType:false,
	 	        dataType : 'json',
	 	    	url : atsUrl+"/saveAddApplicant?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData+"&con="+con,
		        success: function(result) {
		        	 $(".new_side_wrap").removeClass("new_side_wrap_active");
		        	 if(result.response === "SESSION_TIMEDOUT") {
							location.replace(sourceleadUrl);
						} else if (result.response === "AWKWARD") {
							location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
				    		$(".side_mask").fadeOut(200);
				    		$("body").css('overflow-y', 'auto');
				    		location.replace(atsUrl+"/jobsList");
						}
		        },
		    }); 
	      return true; 
	    }
	    else{
	    	return false;	
	       }
	  }    
	   	$('body').on('blur', '#addApplicant_form', function() {
			$("#addApplicant_form").showHelpOnFocus();
			$("#addApplicant_form").validateOnBlur(false, validationSettings);
		});
	  

	function getCandidateSelectionType(){
		var output = "";
		output = output + "<option value=''>--Select Employment Type--</option>"; 
		output = output + "<option value='C2C'>C2C</option>"; 
		output = output + "<option value='C2C-1099'>C2C-1099</option>"; 
		output = output + "<option value='W-2'>W-2</option>"; 
		output = output + "<option value='C2H'>C2H</option>"; 
		output = output + "<option value='C2H-1099'>C2H-1099</option>"; 
		output = output + "<option value='Part Time'>Part Time</option>"; 
		output = output + "<option value='Full Time'>Full Time</option>"; 
		output = output + "<option value='Other'>Other</option>"; 
		$("#designationType").empty();
	    $("#designationType").append(output);
	}

	 function getCandidateResumes() {
		 $("#save_button").removeAttr("disabled");
		 $('.jquery_form_error_message').remove();
		 var candidateId = document.getElementById("candidateId").value
		 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
			
			 document.getElementById("resumesDiv").style.display="";
			 document.getElementById("browseDiv").style.display="none";
			 document.getElementById("orConditionDiv").style.display="none";
			 $("#uploadFile").removeAttr("data-validation");
			 $("#uploadFile").val('');
			 $('.jquery_form_error_message').remove();
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/getCandidateResumes?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, 
			        success: function(data) {
			        	var json = data.data;
			        	if (data.response === "FAILED") {
				    		showError(data.responseMessage);
				    	} else if (data.response === "AWKWARD") {
				    		location.href = "/errorPage";
				    	} else if (data.response === "SUCCESS") {
					        	if(json!=null){
					        	var output = "";
					        	if(json.length == 1){
					        		var opt = json[0];
					        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
					        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
					            	}else{
					            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
					            	}
					        	}else{
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
					        	}
					        	
					        	
					        	$("#candidateResumesList").empty();
						        $("#candidateResumesList").append(output);
						        
						        document.getElementById("newResumeDiv").style.display="none";
						        document.getElementById("skillsDiv").style.display="none";
						        document.getElementById("primarySkillDiv").style.display="none";
						        
						       /*  document.getElementById("employmentTypeDiv").style.display="none";
							    $('#designationType').removeAttr("data-validation");
							    $("#designationType").attr("data-validation-optional","true");
							    $("#designationType").empty(); */
						        
				        		
				        	}else{
				        		
				        		
				        		 document.getElementById("resumesDiv").style.display="none";
				        		 document.getElementById("browseDiv").style.display="none";
				        		 document.getElementById("orConditionDiv").style.display="none";
				        		
				        		 document.getElementById("newResumeDiv").style.display="";
				        		 
				        		 
				        		 
				        		 $('#uploadFile').removeAttr('data-validation');
				        		 $('#uploadFile').attr("data-validation-optional","true"); 
				        	}
				    	}else if (data.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    		showError("Operation failed, please try again");
				    	}
			        },
			    });
		 }else{
			 var output = "";
	     	 output = output + "<option value=''>-- Select Resume --</option>"; 
	     	 $("#candidateResumesList").empty();
			 document.getElementById("resumesDiv").style.display="none";
			 document.getElementById("browseDiv").style.display="none";
			 document.getElementById("orConditionDiv").style.display="none";
			 $('#uploadFile').removeAttr('data-validation');
			 $('#uploadFile').attr("data-validation-optional","true"); 
		     $("#candidateResumesList").append(output);
		 }
		
	 }

	 function getRecomendedCandidateResumes(candidateId) {
		 $("#save_button").removeAttr("disabled");
		 $('.jquery_form_error_message').remove();
		 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
			
			 $("#uploadFile").removeAttr("data-validation");
			 $("#uploadFile").val('');
			 $('.jquery_form_error_message').remove();
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/getCandidateResumes?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, 
			        success: function(data) {
			        	var json = data.data;
			        	if (data.response === "FAILED") {
				    		showError(data.responseMessage);
				    	} else if (data.response === "AWKWARD") {
				    		location.href = "/errorPage";
				    	} else if (data.response === "SUCCESS") {
				        	if(json!=null){
					        	var output = "";
					        	
					        	if(json.length == 1){
					        		var opt = json[0];
					        		output = output + "<option value=''>-- Select Resume --</option>"; 
					        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
					        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
					            	}else{
					            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
					            	}
					        	}else{
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
					        	}
					        	
					        	
					        	$("#candidateResumesList").empty();
						        $("#candidateResumesList").append(output);
						        
						        document.getElementById("newResumeDiv").style.display="none";
						        
						      
				        		
				        	}else{
				        		
				        		
				        		 document.getElementById("resumesDiv").style.display="none";
				        		 document.getElementById("newResumeDiv").style.display="";
				        		
				        		 
				        		 
				        	}
				    	}else if (data.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    		showError("Operation failed, please try again");
				    	}
			        	
			        },
			    });
		 }else{
			 var output = "";
	     	 output = output + "<option value=''>-- Select Resume --</option>"; 
	     	 $("#candidateResumesList").empty();
			 document.getElementById("resumesDiv").style.display="none";
		     $("#candidateResumesList").append(output);
		 }
		
	 }

	function getCandidateDetails(){
		var candidateId = document.getElementById("candidateId").value
		var requirementId = $("#requirementId").val();
	//	var orgLocationId ='${SourceLeadWeb_orgLocationId}';
		if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/getCandidateDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&requirementId="+requirementId+"&orgLocationId="+orgLocationId, 
			        success: function(data) {
			        	if (data.response === "FAILED") {
				    		showError(data.responseMessage);
				    	} else if (data.response === "AWKWARD") {
				    		location.href = "/errorPage";
				    	} else if (data.response === "SUCCESS") {
				    		var json = data.object;
			        		$("input[name=FirstName]").val(json['firstName']);
			        	    $("input[name=EmailId]").val(json['email']);
			        	    $("input[name=EmailId]").attr('readonly', 'readonly'); 
				        	 
				        	if(json['email'] != null && json['email'] != "" && json['email'] != 'email'){
				        		 var requirementId = $("#requirementId").val();
				        		 isCoolingOff(requirementId);
				        	 } 
				        	 
				       	     if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] != 'undefined'){
				        		 $("input[name=LastName]").val(json['lastName']);
				        	 }else{
				        		 $("input[name=LastName]").val(''); 
				        	 }
				        	 
				        	  if(json['mobilePhone'] != null && json['mobilePhone'] != "" && json['mobilePhone'] != 'undefined'){
				        		 $("input[name=PhoneNumber]").val(json['mobilePhone']);
				        	 }else{
				        		 $("input[name=PhoneNumber]").val(''); 
				        	 }
				        	 
				        	 if(json['alternateMobilePhone'] != null && json['alternateMobilePhone'] != "" && json['alternateMobilePhone'] != 'undefined'){
				        		 $("input[name=AlternateNumber]").val(json['alternateMobilePhone']);
				        	 }else{
				        		 $("input[name=AlternateNumber]").val(''); 
				        	 }
				        	 
				        	   if(json['employer'] != null && json['employer'] != "" && json['employer'] != 'undefined'){
				        		 $("input[name='Current/LastCompany']").val(json['employer']);
				        	 }else{
				        		 $("input[name='Current/LastCompany']").val(''); 
				        	 }
				        	 
				        	 if(json['designation'] != null && json['designation'] != "" && json['designation'] != 'undefined'){
				        		 $("input[name=Designation]").val(json['designation']);
				        	 }else{
				        		 $("input[name=Designation]").val(''); 
				        	 }
				        	 
				        	 if(json['candidateEdu'] != null && json['candidateEdu'] != "" && json['candidateEdu'] != 'undefined'){
				        		 $("input[name=Education]").val(json['candidateEdu']);
				        	 }else{
				        		 $("input[name=Education]").val(''); 
				        	 }
				        	 
				        	 if(json['location'] != null && json['location'] != "" && json['location'] != 'undefined'){
				        		 $("#submissionLocation").val(json['location']);
				        	 }else{
				        		 $("#submissionLocation").val('');
				        	 }
		
				        	 if(json['country'] != null && json['country'] != "" && json['country'] != 'undefined'){
				        		 $("#submissionCountry").val(json['country']);
				        	 }else{
				        		 $("#submissionCountry").val('');
				        	 }
				        	 
				        	 if(json['state'] != null && json['state'] != "" && json['state'] != 'undefined'){
				        		 $("#submissionState").val(json['state']);
				        	 }else{
				        		 $("#submissionState").val('');
				        	 }
				        	 
				        	 
				        	 if(json['city'] != null && json['city'] != "" && json['city'] != 'undefined'){
				        		 $("#submissionCity").val(json['city']);
				        	 }else{
				        		 $("#submissionCity").val('');
				        	 }
				        	 
				        	 if(json['zipCode'] != null && json['zipCode'] != "" && json['zipCode'] != 'undefined'){
				        		 $("#submissionZipCode").val(json['zipCode']);
				        	 }else{
				        		 $("#submissionZipCode").val('');
				        	 }
				        	 
				        	 if(json['dateofBirthString'] != null && json['dateofBirthString'] != "" && json['dateofBirthString'] != 'undefined'){
				        		 $("#DOB").val(json['dateofBirthString']);
				        	 }else{
				        		 $("#DOB").val('');
				        	 }
				        
				        	 if(json['noticePeriod'] != null && json['noticePeriod'] != "" && json['noticePeriod'] != 'undefined'){
				        		 $("select[name=NoticePeriod]").val(json['noticePeriod']);
				        	 }else{
				        		 $("select[name=NoticePeriod]").val('');
				        	 }
				        	 
				        	 if(json['experienceYears'] != null && json['experienceYears'] != "" && json['experienceYears'] != 'undefined'){
				        		 $("select[name=WorkExperience]").val(json['experienceYears']);
				        		 if(json['experienceYears'] == "25+"){
				        			 $("#monthExp").attr("disabled", "disabled");
				        		 }else{
				        			 $("#monthExp").removeAttr("disabled");
				        		 }
				        	 }else{
				        		 $("select[name=WorkExperience]").val(0);
				        	 }
				        	 
				        	 if(json['experienceMonths'] != null && json['experienceMonths'] != "" && json['experienceMonths'] != 'undefined'){
				        		 $("select[name=monthExp]").val(json['experienceMonths']);
				        	 }else{
				        		 $("select[name=monthExp]").val(0);
				        	 }
				        	 
				        	 if(json['expectedRate'] != null && json['expectedRate'] != "" && json['expectedRate'] != 'undefined'){
				        		 $("input[name=ExpectedCTC]").val(json['expectedRate']);
				        	 }else{
				        		 $("input[name=ExpectedCTC]").val(''); 
				        	 }
				        	 
				        	 
				        	 if(json['rateType'] != null && json['rateType'] != "" && json['rateType'] != 'undefined'){
				        		 $("select[name=expectedCTCRatePer]").val(json['rateType']);
				        		 validateExpectedCTCRatePer();
				        	 }else{
				        		 $("select[name=expectedCTCRatePer]").val('');
				        		 validateExpectedCTCRatePer();
				        	 }
				        	 
				        	 if(json['expectedRateCurrencey'] != null && json['expectedRateCurrencey'] != "" && json['expectedRateCurrencey'] != 'undefined'){
				        		 $("select[name=expectedCTCRateType]").val(json['expectedRateCurrencey']);
				        	 }
				        	 
				        	 if(json['currentRate'] != null && json['currentRate'] != "" && json['currentRate'] != 'undefined'){
				        		 $("input[name=CurrentCTC]").val(json['currentRate']);
				        		 validateCurrentCTC();
				        	 }else{
				        		 $("input[name=CurrentCTC]").val(''); 
				        		 validateCurrentCTC();
				        	 }
				        	 
				        	 if(json['currentRateType'] != null && json['currentRateType'] != "" && json['currentRateType'] != 'undefined'){
				        		 $("select[name=currentCTCRatePer]").val(json['currentRateType']);
				        		 validateExpectedCTC();
				        		 validateCurrentCTCRatePer();
				        	 }else{
				        		 $("select[name=currentCTCRatePer]").val(''); 
				        		 validateExpectedCTC();
				        		 validateCurrentCTCRatePer();
				        	 }
				        	 
				        	 if(json['currentRateCurrency'] != null && json['currentRateCurrency'] != "" && json['currentRateCurrency'] != 'undefined'){
				        		 $("select[name=currentCTCRateType]").val(json['currentRateCurrency']);
				        	 } 
				    	}else if (data.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    		showError("Operation failed, please try again");
				    	}
			        },
			    });
		 }else{
			 $("input[name=FirstName]").val('');
	    	 $("input[name=LastName]").val('');
	    	 $("input[name=EmailId]").val('');
	    	 $("input[name=EmailId]").removeAttr("readonly");
	    	 
	    	 
	    	 $("input[name=PhoneNumber]").val('');
	    	 $("input[name=Designation]").val('');
	    	 $("select[name=country]").val('');
	    	 $("select[name=state]").val('');
	    	 $("select[name=city]").val('');
	    	 $("select[name=NoticePeriod]").val('');
	    	 $("select[name=WorkExperience]").val('');
	    	 $("input[name=ExpectedCTC]").val('');
	    	 $("input[name=CurrentCTC]").val('');
	    	 $("input[name=Education]").val('');
		 }
	}

	function getExpYears(){
		var expYears = $("select[name=WorkExperience]").val();
		 if(expYears == "25+"){
			 $("#monthExp").attr("disabled", "disabled");
		 }else{
			 $("#monthExp").removeAttr("disabled");
		 }
	}

	function getAllSubmissionCandidateCountries(){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: atsUrl+"/countryAutoComplete?"+tokenParamName+"="+tokenValue+"",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#submissionCountry').empty();
	        	$('#submissionCountry').append('<option value="">---Select Country---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == country){
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

	function getCandidateStates(state){
		
		var countryName = $("#submissionCountry").val();
		if(countryName != null && countryName != "" && countryName !="undefined"){
			$.ajax({
		    	type: "POST",
		    	dataType: "json",
		    	 url: atsUrl+"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#submissionState').empty();
		        	$('#submissionState').append('<option value="">---Select State---</option>');
		            for(var i=0;i<json.length;i++){
		            	if(state != null && state != 'undefined' && json[i] == state){
		            		$('#submissionState').append('<option value="'+state+'" selected>'+json[i]+'</option>');
		            	}
		            	else{
		            		$('#submissionState').append('<option value="'+json[i]+'">'+json[i]+'</option>');
		            	}
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
	    	$('#submissionState').append('<option value="">---Select State---</option>');
	    	$('#submissionState').trigger("chosen:updated");
	    	$('#submissionCity').empty();
	    	$('#submissionCity').append('<option value="">---Select City---</option>');
	        $('#submissionCity').trigger("chosen:updated");
		}
	}

	function getSubmissionCandidateCities(city){
		
		var countryName = $("#submissionCountry").val();
		var stateName = $("#submissionState").val();
		
		if(stateName == null || stateName == "" || stateName =="undefined"){
			stateName = document.getElementById("state");
		}
		
		if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
		    	type: "POST",
		    	dataType: "json",
		    	 url: atsUrl+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#submissionCity').empty();
		        	$('#submissionCity').append('<option value="">---Select City---</option>');
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
	    	$('#submissionCity').append('<option value="">---Select City---</option>');
	    	$('#submissionCity').trigger("chosen:updated");
		}
	}

	function init(){
		getAllSubmissionCandidateCountries();
		initLocationAuto('submissionLocation','submissionCountry','submissionState','submissionCity','submissionZipCode','','','');
		//var country = '${SourceLeadWeb_orgLocations.country}';
		$("#submissionCountry").val(country)
		      
	/* 	 $("#submissionCountry").on('change', function (e) {
		    	   getCandidateStates();
		    	   getSubmissionCandidateCities();
		       });
		       $("#submissionState").on('change', function (e) {
		    	   getSubmissionCandidateCities();
		       }); */
		      
		       $('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': '100%'});
			  })
			  
	    setRateTypesByLocation();
	}

	function setRateTypesByLocation(){
		//var country = '${SourceLeadWeb_country}';
		if(country == 'India'){
			$('#currentCTCRateType').val('INR');
			$('#expectedCTCRateType').val('INR');
		}else if(country == 'Australia'){
			$('#currentCTCRateType').val('AUD');
			$('#expectedCTCRateType').val('AUD');
		}else if(country == 'United States'){
			$('#currentCTCRateType').val('USD');
			$('#expectedCTCRateType').val('USD');
		}else if(country == 'Singapore'){
			$('#currentCTCRateType').val('SGD');
			$('#expectedCTCRateType').val('SGD');
		}else if(country == 'United Kingdom'){
			$('#currentCTCRateType').val('GBP');
			$('#expectedCTCRateType').val('GBP');
		}else if(country == 'France'){
			$('#currentCTCRateType').val('EUR');
			$('#expectedCTCRateType').val('EUR');
		}
	}

	  function dobDatePicker() {
		  $('#DOB').datepicker({
		      dateFormat: 'yy-mm-dd',
		  });
		  
	  }
	//<!-- Country,State,City Script Starts Here -->
	function validate_fileupload(fileName)
	{
	    var allowed_extensions = new Array("docx","doc","pdf","rtf");
	    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

	    for(var i = 0; i <= allowed_extensions.length; i++)
	    {
	        if(allowed_extensions[i]==file_extension)
	        {
	            return true; // valid file extension
	        }
	    }

	    return false;
	}
	 function validateSubmissionCandidateEmail() {
		 var email =   $("input[name=EmailId]").val();
		$('#emailDiv').find('p.jquery_form_error_message').remove();
		 var requirementId = $("#requirementId").val();
		 if(email != null && email != ""){
			 $("#save_button").attr("disabled", "disabled");
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/validateRequirementCandidateEmail?"+tokenParamName+"="+tokenValue+"&email="+email+"&requirementId="+requirementId, 
			        success: function(data) {
			         	if(data == 'true'){
				        	  $("#save_button" ).addClass('btn btn-sm btn-primary').val('SUBMIT');
				        	  $('.jquery_form_error_message').remove();
			        		  $("input[name=EmailId]").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
			        		  $("input[name=EmailId]").setAttribute('record-exist','yes');
			        		  $("input[name=EmailId]").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
			        	}
			        	else{
			        		$("#save_button").removeAttr("disabled");
			        		$("input[name=EmailId]").removeAttr( "record-exist record-exist-errormsg");
			        		$('.jquery_form_error_message').remove();
			        		isCoolingOff(requirementId);
			        	} 
			        },
			    });
		 }
		 
	 }
	 function getUserByUserEmail() {
		 var emailId =   $("input[name=EmailId]").val();
		 if(emailId != null && emailId != ""){
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/getUserByUserEmail?"+tokenParamName+"="+tokenValue+"&EmailId="+emailId, 
			        success: function(json) {
			        		
			        		if(json.firstName != null && json.firstName != ''){
			        			$("input[name=FirstName]").val(json.firstName);
			        			$("input[name=FirstName]").attr("readonly", "readonly");
			        			$("input[name=FirstName]").find('p.jquery_form_error_message').remove();
			        		}else{
			        			$("input[name=FirstName]").removeAttr("readonly", "readonly");
			        			var firstName = $("input[name=FirstName]");
			        			if(firstName == ''){
			        				$("input[name=FirstName]").val('');
			        			}
			        			
			        		}
			        		
			        		if(json.lastName != null && json.lastName != ''){
			        			$("input[name=LastName]").attr("readonly", "readonly");
			        			$("input[name=LastName]").val(json.lastName);
			        		}else{
			        			$("input[name=LastName]").removeAttr("readonly", "readonly");
			        			
			        			var lastName = $("input[name=LastName]");
			        			if(lastName == ''){
			        				$("input[name=LastName]").val('');
			        			}
			        			
			        			
			        		}
			        		
			        		if(json.mobilePhone != null && json.mobilePhone != ''){
			        			$("input[name=PhoneNumber]").val(json.mobilePhone);
			        			$("input[name=PhoneNumber]").attr("readonly", "readonly");
			        		}else{
			        			$("input[name=PhoneNumber]").removeAttr("readonly", "readonly");
			        			
			        			var phno = $("input[name=PhoneNumber]");
			        			if(phno == ''){
			        				$("input[name=PhoneNumber]").val('');
			        			}
			        			
			        			
			        		}
			        		
			        },
			    });
		 }else{
			 $("input[name=FirstName]").attr("disabled", false);
			 $("input[name=FirstName]").val('');
			 
			 $("input[name=LastName]").attr("disabled", false);
			 $("input[name=LastName]").val('');
			 
			 $("input[name=PhoneNumber]").attr("disabled", false);
			 $("input[name=PhoneNumber]").val('');
			 
		 }
	 }
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
	 function validateExpectedCTCRatePer(){
		 var expectedCTCRatePer = document.getElementById("expectedCTCRatePer").value;
		 if(expectedCTCRatePer){
			 $("input[name=ExpectedCTC]").removeAttr("data-validation-optional");
			 $("input[name=ExpectedCTC]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
		 }else{
			 $("input[name=ExpectedCTC]").removeAttr("data-validation");
			 $("input[name=ExpectedCTC]").attr("data-validation-optional", "true");
			 $("input[name=ExpectedCTC]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
		 }
	 }
	 function validateCurrentCTCRatePer(){
		 var currentCTCRatePer = document.getElementById("currentCTCRatePer").value;
		 if(currentCTCRatePer){
			 $("input[name=CurrentCTC]").removeAttr("data-validation-optional");
			 $("input[name=CurrentCTC]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
		 }else{
			 $("input[name=CurrentCTC]").removeAttr("data-validation");
			 $("input[name=CurrentCTC]").attr("data-validation-optional", "true");
			 $("input[name=CurrentCTC]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
		 }
	 }
	 function changeRateLabels(){
		 
		 var currentRate = document.getElementById("currentRate").textContent;
		 var currentstringLength = currentRate.length; 
		 var currentRatelastChar = currentRate.charAt(currentstringLength - 1);
		 
		 if(currentRatelastChar == "*"){
			 $("#currentRate").html("Current Rate *"); 
		 }else{
			 $("#currentRate").html("Current Rate");
		 }
		 
		 var expectedRate = document.getElementById("expectedRate").textContent;
		 var expectedstringLength = expectedRate.length; 
		 var expectedRatelastChar = expectedRate.charAt(expectedstringLength - 1);
		 
		 if(expectedRatelastChar == "*"){
			 $("#expectedRate").html("Expected Rate *"); 
		 }else{
			 $("#expectedRate").html("Expected Rate");
		 }
		 
	 }
	 function getEmployementType(){
		 var designationType = document.getElementById("designationType").value;
		 
		 if(designationType == 'C2C'){
			 $("#needApplicantPermit").attr("disabled", "disabled");
			 document.getElementById("needApplicantPermit").checked = false;
			 $("#needApplicantPermit").val('N');
		 }else{
			 $("#needApplicantPermit").removeAttr("disabled");
			
		 }
		 
		 if(designationType == 'C2C'){
			 document.getElementById("vendorsDiv").style.display="";
			 document.getElementById("vendorContactsDiv").style.display="";
			 document.getElementById("employmentOtherTypeDiv").style.display="none";
			
			 
			 $('#designationOtherType').removeAttr("data-validation"); 
			 $('#designationOtherType').attr("data-validation-optional", "true"); 
			 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
			 
			 getVendorsByOrgLocationForAddApplicant();
		 }
		 else if(designationType == 'C2C-1099'){
			 document.getElementById("vendorsDiv").style.display="";
			 document.getElementById("vendorContactsDiv").style.display="";
			 document.getElementById("employmentOtherTypeDiv").style.display="none";
			
			 
			 $('#designationOtherType').removeAttr("data-validation"); 
			 $('#designationOtherType').attr("data-validation-optional", "true"); 
			 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
			 
			 getVendorsByOrgLocationForAddApplicant();
		 }else if(designationType == 'Other'){
			 
			 document.getElementById("vendorsDiv").style.display="none";
			 document.getElementById("vendorContactsDiv").style.display="none";
			 document.getElementById("employmentOtherTypeDiv").style.display="";
			 
			 
			 $('#designationOtherType').removeAttr("data-validation-optional"); 
			 $('#designationOtherType').attr("data-validation","required validate_Space validate_length length2-250"); 
			 
		 }
		 else{
			 
			 
			 document.getElementById("employmentOtherTypeDiv").style.display="none";
			 document.getElementById("vendorsDiv").style.display="none";
			 document.getElementById("vendorContactsDiv").style.display="none";
			 
			 
			 $('#designationOtherType').removeAttr("data-validation"); 
			 $('#designationOtherType').attr("data-validation-optional", "true"); 
			 $('#designationOtherType').attr("data-validation","validate_Space validate_length length2-250"); 
			 
		 }
		 
		 
	 }
	 function getVendorsByOrgLocationForAddApplicant(){
		 $.ajax({
		    	type: "GET",
		        url: atsUrl+"/getVendorsByOrgLocation?"+tokenParamName+"="+tokenValue+"", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>--Select Vendor--</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		
			        		output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
					}
		        	$("#vendors").empty();
			        $("#vendors").append(output);
			        $('#vendors').trigger("chosen:updated"); 
			        
		        },
		    }); 
		 
	 }
	 function getAddApplicantVendorContactList(){
		 var orgRelationID = $('#vendors').val();
			 $.ajax({
				    type: "POST",
			        url: vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
			        success: function(json) {
			        	//var json = JSON.parse(data);
			        	$('#vendorContact').empty();
			        	$('#vendorContact').append('<option value="">--Select Vendor contact--</option>');
			             for(var i=0;i<json.data.length;i++){
			            	 var opt = json.data[i];
			            	 $('#vendorContact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
			            } 
			            $('#vendorContact').trigger("chosen:updated");
			        }
			 }); 
	 }
	function uploadAddApplicantCandidateDoc(){
		fileUpload("newUploadBtn", "addApplicants", "addApplicant_form");
	}
	function importAddApplicantDoc(){
		fileUpload("uploadBtn", "addApplicants", "addApplicant_form");
	}
// addApplicantFromRequirementList.jsp  End HERE upated.
// updating catrt count in header view in cart icon count value.
	function updateMarketplaceCartCountAndPrice(){
		var totalPriceInCurrency;
		$.ajax({
			 	url: sourceleadUrl + "/getMarketplaceCartCountAndPrice",
		        type: 'GET',
		        success: function (object) {
		        	console.log(object)
		        	if(object != null){
		        		$("#headerCartCount").html(object[0]);
		        		$("#cartCount").html(object[0]);
		        		totalPriceInCurrency = new Intl.NumberFormat('en-US', { minimumFractionDigits : 2 }).format(object[1]);
		        		$("#totalPrice").html((object[1] === null || object[0] === 0) ? "0.00" : totalPriceInCurrency);
		        	}}
		        });
		}
	function buildSummaryButtonsDivHtml(){
		
		var summaryButtonsDivHtml ="";
			if(!jQuery.isEmptyObject(requirementId)){
			 summaryButtonsDivHtml = summaryButtonsDivHtml + '<button type="button" id = "editAssignedTeamsOrUsersBtn" class="btn btn-primary mr5 btn-sm" data-dismiss="modal" onclick="editAssignedTeamsOrUsers('+requirementId+');">Save<span id="assignToSelectedPopupCount"> (0) </span></button>';
			 summaryButtonsDivHtml = summaryButtonsDivHtml + '<button type="button" id = "resetAssignedTo" class="btn btn-default btn-sm" data-dismiss="modal" onclick="clearAssignedTeamsOrUsers();">Reset</button>';
			}else{
				summaryButtonsDivHtml = summaryButtonsDivHtml + ' <button type="button" class="btn btn-primary mr5 btn-sm" data-dismiss="modal" onclick="getAssignedTeamsOrUsers();">Assign<span id="assignToSelectedPopupCount"> (0) </span></button>';
			}
			if(jQuery.isEmptyObject(requirementId)){
				summaryButtonsDivHtml = summaryButtonsDivHtml +'<button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="clearAssignedTeamsOrUsers();">Reset</button>';
			}
		//	console.log(summaryButtonsDivHtml);
			$('#summaryButtonsDiv').append(summaryButtonsDivHtml);
		}
