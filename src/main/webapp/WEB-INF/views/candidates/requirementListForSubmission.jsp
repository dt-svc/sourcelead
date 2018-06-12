<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/resources/css/style.css" />
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<script src="/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<script>
function submitCandidatesToJob(){
	$('.action-pop').slideUp();
	
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  createdDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  createdDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  createdDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  createdDate = 'YYYY-DD-MM'; 
	  }
	  
	var result = "";
	result = result + '<h3>Requirement List</h3>';
	result = result +" <div class='dynamic_search pull-right'>";
 	result = result +" <input type='text' placeholder='Search' id='reqDataTableSearch'/>";
 	result = result +" <span class='cursorPointer faSearch' title='Search'><i class='fa fa-search' onClick='getRequirementTableList();'></i></span>";
 	result = result +"</div>";
 	result = result +"<div class='clearfix'></div>";
 	result = result + '<table id="reqListTable" class="display mt10" cellspacing="0" width="100%" >';
	result = result + '<thead>';
	result = result + '<tr>';
	result = result +'  <th>&nbsp;</th>';
	result = result +'	<th>Generated Id</th>';
	result = result +'	<th>Title</th>';
	result = result +'  <th>Posted On<br>(<small class="custom-small">'+createdDate+'</small>)</th>';
  	result = result +'  <th>Location</th>';
	result = result + '</tr>';
	result = result + '</thead>	';
    result = result +"</table>";
	$("#addApplicantDiv").empty();
	$("#addApplicantDiv").append(result);
	
	getRequirementTableList();
	
	$(".new_side_wrap").addClass("new_side_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
}

</script>

<script>
function getRequirementTableList(){
	/* var candidateIds = new Array();
	 for(var e in shareCandidates) {
		 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
			   continue;
		 candidateIds.push(shareCandidates[e]);
	 } */
	 
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#reqDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getRequirementListForCandidateBulkSubmission?searchValue="+searchValue;
	 requirementListPagination(url,sourceleadUrl);
}
</script>

<script>
function requirementListPagination(url,sourceleadUrl){
	
}
</script>


