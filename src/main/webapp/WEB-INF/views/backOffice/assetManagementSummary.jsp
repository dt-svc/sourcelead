<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
<jsp:include page="assetManagementDetails.jsp" />
<script>
$(document).ready(function() {
	assetDetails('${asset.id}');
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

	<div class="cearfix">
	  <div class="newTagsWrap">
	    <ul class="clearfix tagsWrap tagsWrapBor">
			<select multiple class="chosen-select form-control" name="tagName" id="assetTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
			</select>
		</ul>
			<div class="clearfix"></div>
	  </div>
	 </div>
	   <div class="success_pop tagStyles" id="addTaskTagAlert">
			<i class="fa fa-check-circle"></i>
	   		<span></span>
	   </div>
	   <div class="remove_pop tagStyles" id="deleteTaskTagAlert">
			<i class="fa fa-times"></i>
	   		<span></span>
	   </div>
	   

	<!-- Asset Management Summary tabs starts here  -->
	<div class="clearfix"></div>
	<div class="project_side_wrap_inn_tabs">
		<ul class="project-tabs-menu">
			<li class="current"><a href="#tab-1" onclick="assetDetails('${asset.id}');">Details</a></li>
			<li><a href="#tab-2" onclick="assetNotes('${asset.id}');">Notes</a></li>
		</ul>
		<div class="clearfix"></div>
			<div class="project_tabs">
				<div id="tab-1" class="project_tabs_inn">
					<div class="panel-body" >Asset Management Details</div>
				</div>
				<div id="tab-2" class="project_tabs_inn">
				</div>
			</div>
	</div>
	<!-- Asset Management Summary tabs starts here  -->

</section>

<script>
$("#tab-1").show();
$(".project-tabs-menu a").click(function(event) {
    event.preventDefault();
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
   event.preventDefault();
});
</script>





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