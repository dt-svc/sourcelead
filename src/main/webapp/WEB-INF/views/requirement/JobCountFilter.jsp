    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
        <div class="pull-left ml">
				<span class="select-fild padding_left">
				    <select id="reqList" onchange="loadRequirementList();">
                  		<option value="appList"  ${reqList == 'appList' ? 'selected="selected"':''} >Applicant List</option>
                  		<option value="shortlist" ${reqList == 'shortlist' ? 'selected="selected"':''}>Shortlist</option>
                        <option value="subList" ${reqList == 'subList' ? 'selected="selected"':''}>Client Submission List</option> 
                        <option value="interList" ${reqList == 'interList' ? 'selected="selected"':''}>Interview List</option>
                        <option value="offerlist" ${reqList == 'offerlist' ? 'selected="selected"':''}>Offer List</option> 
                        <option value="rejectedlist" ${reqList == 'rejectedlist' ? 'selected="selected"':''} >Rejected List</option> 
                        <option value="joinedlist" ${reqList == 'joinedlist' ? 'selected="selected"':''} >Joined List</option> 
                        <option value="backoutlist" ${reqList == 'backoutlist' ? 'selected="selected"':''} >Backout List</option> 
                    </select>
				</span>
				
			</div>
			 <div class="pull-left ml mt10 mb10 hidden">
				<span class="select-fild padding_left">
				    <select id="tabList" onchange="loadRequirementList();" >
                  		<option value="today" ${tab == 'today' ? 'selected="selected"' : ''}>Today</option>
                        <option value="previous" ${tab == 'previous' ? 'selected="selected"' : ''} >Previous</option> 
                    </select>
				</span>
			</div>
			
   <script type="text/javascript">
   var atsUrl = '${SourceLeadWeb_atsUrl}';
	function loadRequirementList(){
		var tab = $('#tabList').val();
		var reqList = $('#reqList').val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		if(reqList == 'appList'){
			window.location.replace(atsUrl + "/requirementApplicantsList?&tab="+tab+"&orgLocationId="+orgLocId);
		}else if(reqList == 'shortlist'){
			window.location.replace(atsUrl + "/requirementShortList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'subList'){
			window.location.replace(atsUrl + "/requirementSubmissionsList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'interList'){
			window.location.replace(atsUrl + "/requirementInterviewsList?&tab="+tab+"&orgLocationId="+orgLocId);		
	    }
		else if(reqList == 'offerlist'){
			window.location.replace(atsUrl + "/requirementOffersList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'rejectedlist'){
			window.location.replace(atsUrl + "/requirementRejectedList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'joinedlist'){
			window.location.replace(atsUrl + "/requirementJoinedList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'backoutlist'){
			window.location.replace(atsUrl + "/requirementBackoutList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
	 }
	</script> 
