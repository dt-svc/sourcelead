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
	function loadRequirementList(){
		var tab = $('#tabList').val();
		var reqList = $('#reqList').val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		if(reqList == 'appList'){
			window.location.replace(sourceleadUrl + "/requirementApplicantsList?&tab="+tab+"&orgLocationId="+orgLocId);
		}else if(reqList == 'shortlist'){
			window.location.replace(sourceleadUrl + "/requirementShortList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'subList'){
			window.location.replace(sourceleadUrl + "/requirementSubmissionsList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'interList'){
			window.location.replace(sourceleadUrl + "/requirementInterviewsList?&tab="+tab+"&orgLocationId="+orgLocId);		
	    }
		else if(reqList == 'offerlist'){
			window.location.replace(sourceleadUrl + "/requirementOffersList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'rejectedlist'){
			window.location.replace(sourceleadUrl + "/requirementRejectedList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'joinedlist'){
			window.location.replace(sourceleadUrl + "/requirementJoinedList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
		else if(reqList == 'backoutlist'){
			window.location.replace(sourceleadUrl + "/requirementBackoutList?&tab="+tab+"&orgLocationId="+orgLocId);
		}
	 }
	</script> 
