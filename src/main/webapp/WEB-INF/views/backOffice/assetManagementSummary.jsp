<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!-- <script type="text/javascript" src="jquery-1.2.6.min.js"></script> -->
<jsp:include page="assetManagementDetails.jsp" />
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function() {
	assetDetails('${asset.assetUUID}');
	getAssetTagsList('${asset.id}')
});	
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
	  <div class="pull-left">
	    <h2>Asset Management Summary</h2>
	  </div>
	  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/assetManagement'/>">Assets List</a> &raquo; Asset Summary</div>
	</section>

	   <div class="success_pop tagStyles" id="addTaskTagAlert">
			<i class="fa fa-check-circle"></i>
	   		<span></span>
	   </div>
	   <div class="remove_pop tagStyles" id="deleteTaskTagAlert">
			<i class="fa fa-times"></i>
	   		<span></span>
	   </div>
	   

	<!-- Asset Management Summary tabs starts here  -->
	<div class="clearfix Project_wrap">
	<div class="project_side_wrap_inn_tabs">
		<ul class="project-tabs-menu">
			<li class="current"><a href="#tab-1" onclick="assetDetails('${asset.assetUUID}');">Details</a></li>
			<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSET_ADDITIONAL_DETAILS\')">
			  <li><a href="#tab-3" onclick="assetHistory('${asset.assetUUID}');">Assign History</a></li>
			</sec:authorize>
			<li><a href="#tab-4" onclick="assetServiceHistory('${asset.assetUUID}');">Service History</a></li>
			<li><a href="#tab-2" onclick="assetNotes('${asset.id}','${asset.assetUUID}');">Notes</a></li>
		</ul>
		<div class="clearfix"></div>
			<div class="project_tabs">
				<div id="tab-1" class="project_tabs_inn">
					<div class="panel-body" >Asset Management Details</div>
				</div>
				<div id="tab-2" class="project_tabs_inn"></div>
				<div id="tab-3" class="project_tabs_inn_table dataFilterNew datatable_filter mt10"></div>
				<div id="tab-4" class="project_tabs_inn_table dataFilterNew datatable_filter mt10"></div>
			</div>
	</div>
	</div>
	
	<div class="cearfix">
	  <div class="newTagsWrap">
	    <ul class="clearfix tagsWrap tagsWrapBor">
			<select multiple class="chosen-select form-control" name="tagName" id="assetTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
			</select>
		</ul>
			<div class="clearfix"></div>
	  </div>
	 </div>	
	
	<!-- Asset Management Summary tabs starts here  -->

</section>

<script>
$("#tab-1").show();
$(".project-tabs-menu a").click(function(event) {
//     event.preventDefault();
    $(this).parent().addClass("current");
    $(this).parent().siblings().removeClass("current");
    var tab = $(this).attr("href");
    $(".project_tabs_inn").not(tab).css("display", "none");
    $(tab).fadeIn();
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
/*  $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	 */
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
//    event.preventDefault();
});
</script>


<!-- ===================== History related scripts written by Laxman Start ======================================== -->
<script>
function assetHistory(assetUUID){
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/getReturnedOnAssetDetaisl?${_csrf.parameterName}=${_csrf.token}&assetUUID="+assetUUID,
			success : function(json) {
				var result = "";
   	        	result = result +'<table id="assignAssetPagination">';
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Shift Name</th>';
   		    	result = result +'    <th>Employee Name</th>'; 
   		    	result = result +'     <th>Assigned On</th>';
   		    	result = result +'     <th>Returned On</th>';
   		    	result = result +'     <th>Document</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{
   		    		var report = json[i];
   		    		result = result +'<tr>';
   		    		result = result +'<td><div class="tbldata-dotdot-sm" title="'+report.shift.shiftName+'">'+report.shift.shiftName+'</div></td>';
   		    		result = result +'<td><div class="tbldata-dotdot-sm" title="'+report.userRole.user.fullName+'">'+report.userRole.user.fullName+'</div></td>';
   		    		if(report.assignedOnStr != null && report.assignedOnStr !="" && report.assignedOnStr != "undefined"){
   	   		    		result = result +'<td><div class="tbldata-dotdot_80" title="'+report.assignedOnStr+'">'+report.assignedOnStr+'</div></td>';
   		    		}else{
   	   		    		result = result +'<td><div class="tbldata-dotdot_80">--</div></td>';
   		    		}
   		    		if(report.returnedOnStr != null && report.returnedOnStr !="" && report.returnedOnStr != "undefined"){
   	   		    		result = result +'<td><div class="tbldata-dotdot_80" title="'+report.returnedOnStr+'">'+report.returnedOnStr+'</div></td>';
   		    		}else{
   	   		    		result = result +'<td><div class="tbldata-dotdot_80">--</div></td>';
   		    		}
   		    		if(report.fileName != null && report.fileName != "" && report.fileName != "undefined"){
						result = result + ' <td><ul class="list-unstyled list-inline text-center mt10" ><li><a class="fa fa-download cursorPointer" href="'+report.fileURL+'" title="download"></a><a class="fa fa-eye ml cursorPointer" onClick="viewFile(\''+report.fileURL+'\',\''+report.fileName+'\',\''+report.fileType+'\');" title="view"></a></li></ul></td>';
					}else{
						result = result + ' <td><div class="pull-left">--</div></td>';
					}
   		    		result = result +'</tr>';
   				}
	   		    $("#tab-1").empty(); 
	   		    $("#tab-2").empty();
	  		    $("#tab-3").empty();
	  		    $("#tab-4").empty();
	   		    $("#tab-3").append(result);
   	    	    returnedAssetPagination();
			}
		});
}
function returnedAssetPagination(){
	$('#assignAssetPagination').DataTable( { 
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

<!-- ===================== History related scripts written by Laxman Ends ======================================== -->


<!-- ===================== Assert Service History related scripts written by Laxman Start ======================================== -->
<script>
function assetServiceHistory(assetUUID){
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/getAssetManagementServiceHistory?${_csrf.parameterName}=${_csrf.token}&assetUUID="+assetUUID,
			success : function(json) {
				var result = "";
   	        	result = result +'<table id="assetServicePagination">';
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	  <th>Service Date</th>';
   		    	result = result +'    <th>Received Date</th>'; 
   		    	result = result +'     <th>Cost</th>';
   		    	result = result +'     <th>Description</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{
   		    		var report = json[i];
   		    		result = result +'<tr>';
   		    		result = result +'<td><div class="tblminwidth-175" title="'+report.serviceDateStr+'">'+report.serviceDateStr+'</div></td>';
   		    		result = result +'<td><div class="tblminwidth-175" title="'+report.receivedDateStr+'">'+report.receivedDateStr+'</div></td>';
   	   		    	result = result +'<td><div class="tblminwidth-175" title="'+report.cost+'">'+report.cost+'</div></td>';
   	   		    	result = result +'<td><div class="tbldata-dotdot-verybig" title="'+report.description+'">'+report.description+'</div></td>';
   		    		result = result +'</tr>';
   				}
	   		    $("#tab-1").empty(); 
	   		    $("#tab-2").empty();
	  		    $("#tab-3").empty();
	  		    $("#tab-4").empty();
	   		    $("#tab-4").append(result);
   	    	    assetServicePagination();
			}
		});
}
function assetServicePagination(){
	$('#assetServicePagination').DataTable( { 
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

<!-- ===================== Assert Service History related scripts written by Laxman Ends ======================================== -->


<!--  ======================== Tags Related Scripts written by Laxman Start =============================== -->
 <script>
var select, chosen;
select = $("#assetTagName");
select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
chosen = select.data('chosen');
chosen.search_field.on('keyup', function(e)
{
	var anyKeyword = this.value.replace(",","");
    if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
    {
    	var par = this.parent;
        var option = $("<option>").val(anyKeyword).text(anyKeyword);
        select.append(option);
        select.find(option).prop('selected', true);
        $("#assetTagName option").each(function(){
              $(this).siblings("[value='"+ this.value+"']").remove();
        });
		$("#assetTagName").trigger("chosen:updated");
		saveTaskTags("add");
    }
});

$('.chosen-select').chosen().on('change', function(evt, params) {
	if (params.selected && params.selected != undefined) {
		saveTaskTags("add");
	}
	if (params.deselected && params.deselected != undefined) {
		saveTaskTags("delete");
	}
	
});
$("#assetTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag")
</script>
<script>
function getAssetTagsList(assetId){
	$.ajax({  
        type : "POST",
        url: sourceleadUrl+"/getAssetTags?"+tokenParamName+"="+tokenValue+"&assetId="+assetId,        
        success :function(json) {
        	var result = "";
			$.each( json.tagsList, function( i, tags ) {
			  var existed = 0;
                if(json.assetTags != null && json.assetTags != 'undefined' && json.assetTags != '') {
	                var res = [];
	                res = json.assetTags.split(",");
	                res.forEach(function(item) {
	                	if(tags == item){
	                		existed = 1;
	                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
	                	}
	                });
                }
                if(existed == 0){
                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
                }
			});
            $("#assetTagName").empty();
            $("#assetTagName").append(result);
            $("#assetTagName").chosen().trigger("chosen:updated")
        },
	});
}
</script>
<script>
function saveTaskTags(type){
	var assetId = '${asset.id}';
	 var tagName = $("#assetTagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = sourceleadUrl+"/saveOrUpdateTagsByAssetId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&assetId="+assetId+"&tagName="+jsonData
	 } else {
		 Urlcheck = sourceleadUrl+"/saveOrUpdateTagsByAssetId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&assetId="+assetId+"&tagName="+""
	 } 
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		if(type == "add"){
        			showSuccessStatusPopup("Tag added successfully");
        		}else if(type == "delete"){
        			showDeleteStatusPopup("Tag deleted successfully");
        		}
        		getAssetTagsList(assetId);
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
       },
    }); 
}
function showSuccessStatusPopup(status) {
	$("#addTaskTagAlert").css("display","block");
	$("#addTaskTagAlert span").html(status);
    $('#addTaskTagAlert').delay(2000).fadeOut('slow');
}
function showDeleteStatusPopup(status) {
	$("#deleteTaskTagAlert").css("display","block");
	$("#deleteTaskTagAlert span").html(status);
    $('#deleteTaskTagAlert').delay(2000).fadeOut('slow');
}
</script>
<!--  ======================== Tags Related Scripts written by Laxman Ends =============================== -->

<jsp:include page="../masterFooter.jsp" />