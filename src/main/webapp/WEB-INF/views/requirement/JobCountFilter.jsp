    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div class="timesheet_head">
        <div class="pull-left ml mt10">
				<p><span class="select-fild">
				    <select id="reqList" onchange="loadRequirementList();">
                  		<option value="appList"  ${reqList == 'appList' ? 'selected="selected"':''} >Applicant List</option>
                        <option value="subList" ${reqList == 'subList' ? 'selected="selected"':''}>Submission List</option> 
                        <option value="interList" ${reqList == 'interList' ? 'selected="selected"':''}>Interview List</option>
                        <option value="offerlist" ${reqList == 'offerlist' ? 'selected="selected"':''}>Offer List</option> 
                    </select>
				</span>
				</p>
			</div>
			 <div class="pull-left ml mt10">
				<p><span class="select-fild">
				    <select id="tabList" onchange="loadRequirementList();" >
                  		<option value="today" ${tab == 'today' ? 'selected="selected"' : ''}>Today</option>
                        <option value="previous" ${tab == 'previous' ? 'selected="selected"' : ''} >Previous</option> 
                    </select>
				</span>
				</p>
			</div>
          <div class="clearfix"></div>
          <div class="remove_search hidden">
            <input type="text" placeholder="search" />
          </div>
        </div>
        
   <script type="text/javascript">
	function loadRequirementList(){
		var tab = $('#tabList').val();
		var reqList = $('#reqList').val();
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		if(reqList == 'appList'){
			window.location.replace(sourceleadUrl + "/requirementApplicantsList?&tab="+tab+"&orgLocationId="+orgLocId);
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
	 }
	</script> 