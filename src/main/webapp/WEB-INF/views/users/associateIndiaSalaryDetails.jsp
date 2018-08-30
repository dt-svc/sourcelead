 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-5" class="tabsMenu_tabs_inn clearfix">
  <div id="indSalaryDetailsLoading"></div>
  
</div>

<script>
function indAssociateSalarySummary(newAssociate){
	var locationCode = '${SourceLeadWeb_locationCode}';
	var candidateId =  $("#associateCandidateId").val();
	
	if(candidateId != null && candidateId != '' && candidateId != 'undefined'){
		$.ajax({
			  type: "GET",
			    url: sourceleadUrl+"/getAssociateSalaryDataByOrgLocation?candidateId="+candidateId+"&orgLocationId="+locationCode,
			     success :function(response) {
			    	    $("#indSalaryDetailsLoading").empty();  
			    	    $("#indSalaryDetailsLoading").append(response);
			    	    $("#assocandidateId").val(candidateId);
			    	    $("#status").val('NEW');
					},
				});
	}
}
</script>

