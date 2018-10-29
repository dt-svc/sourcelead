<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <!-- Content Area -->
    <section class="content-wraper">
    <section class="content-header clearfix">
    <div class="pull-left">
      <h2 id="headerLbl">Marketplace List</h2>
    </div>
      <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <label id="brudCrumLbl">Marketplace List </label> </div>
	  </c:if>
	  </section>
    <div class="clearfix"></div>
	  <div class="clearfix">
			<ul class="project-tabs-menu projecttabs-custom clearfix">
		         <li id="" ><a href="${appPath}/candidatesList">Candidate List</a></li>
		         <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
		         <li id="" class="current"><a href="javascript:void(0)">Global Marketplace</a></li>
		         <div class="clearfix"></div>
		     </ul>
      </div>
    <div class="clearfix"></div>
      <div class="searchprofile-profile">
        <div class="row clearfix search-tools">
          <div class="col-md-7 col-xs-12 reports-head">
            <select class="form-control chosen-select" multiple data-placeholder="Search Profile">
              <option>Profile1</option>
              <option>Profile2</option>
              <option>Profile3</option>
              <option>Profile4</option>
              <option>Profile5</option>
            </select>
          </div>
          <div class="col-md-4 col-xs-12 padding_right">
            <input type="text" class="form-control" placeholder="Location" name="pwd">
          </div>
          <div class="col-md-1 col-xs-12 padding_left"> <a class="btn btn-primary btn-block width50px pull-right"><i class="fa fa-search"></i></a> </div>
          <div class="clearfix"></div>
          <div class="col-md-12 mt10"> <a href="#" class="pull-right small advancedSearch" title="Advanced Search" onClick=" openPop()">Advanced Search</a> </div>
        </div>
      </div>
      <div class="clerfix row">
        <div class="col-md-12">
          <div class="profileDetails">
            <div class="profileDetails-header row">
              <div class="col-md-3 mt5">
                <h5 class="clearfix pull-left small mb0 text-muted mt5">Showing 4 results</h5>
                <div class="pull-left headerItems">
                  <ul class="list-unstyled list-inline ml mb0 mt2">
                    <li><a title="List Model" href="javascript:void(0)" onclick="getGloblaMarketPlaceCandidates('listModel');" id="listModel"><i class="fa fa-th-list text-primary"></i></a></li>
					  <li><a title="Card Model" href="javascript:void(0)" onclick="getGloblaMarketPlaceCandidates('cardModel');" id="cardModel"><i class="fa fa-th text-muted"></i></a></li>
                  </ul>
                </div>
              </div>
              <div class="col-md-9">
                <ul class="pull-right mb0 list-unstyled list-inline">
                  <li> <span id="advanceSearchDiv" class="pull-left refreIcon noPad"> <a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetCandidateFilter();"></a> </span> </li>
                  <li> <a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a> </li>
                </ul>
              </div>
            </div>
            <div class="clearfix"></div>
			  <div class="scrollContent">
			  
			  <!-- Grid model start -->
			  <div id="listView"></div>
			  
			  
				 
            
			  
			  
			  <div id="cardView" style="display: none;" class="clearfix row"></div>
				  				  
				 
			  </div>
			  </div>
			  </div>
        </div>
  </section>
  <div class="advancedsearch-mask"></div>
<div class="cand-advsrch-popup advancedsearch-popup" id="profileAdSearch" style="display: none;">
  <div class="clearfix headerAdsrch">
    <h5 class="col-md-8 mb0 mt5">Advanced Search</h5>
    <a href="javascript:void(0)" class="closePop margin-top0" onclick="closePop();"></a> </div>
  <div class="addInner"> 
     <div class="candAdvsrchPop"> 
    <div class="row">
      <div class="col-md-4">
        <label>Any of Keyword</label>
        <div class="clearfix">
          <select class="chosen-select form-control input-sm" multiple data-placeholder="Any Keyword" id="anyOfKeyword" name="anyOfKeyword">
          </select>
          <!-- <input type="text" class="form-control input-sm" placeholder="Any of Keyword"> --> 
        </div>
      </div>
      <div class="col-md-4">
        <label>All Keywords</label>
        <div class="clearfix">
          <select class="chosen-select form-control input-sm" multiple data-placeholder="All Keywords" id="allKeywords" name="allKeywords">
          </select>
        </div>
      </div>
      <div class="col-md-4">
        <label>Exclude Keyword</label>
        <div class="clearfix">
          <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Keyword" id="excludeKeyWord" name="excludeKeyWord">
          </select>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="clearfix">
        <div class="col-md-8">
          <label>Tags</label>
          <div class="clearfix">
            <select class="chosen-select form-control input-sm" multiple data-placeholder="Tags" id="canTags" name="candidateTags">
            </select>
          </div>
        </div>
        <div class="col-md-8">
          <label>Immigration Status</label>
          <div class="clearfix">
            <select class="chosen-select form-control input-sm" multiple data-placeholder="Visa" id="canVisa" name="candidateVisa">
              <c:forEach var="visa" items="${visaList}">
                <option value="${visa.name}">${visa.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="clearfix">
          <div class="col-md-8 mt5">
            <label>Skills</label>
            <div class="clearfix">
              <select class="chosen-select form-control input-sm" multiple data-placeholder="Skills" id="canSkills" name="candidateSkills">
                <c:forEach var="skill" items="${skillsList}">
                  <option value="${skill.name}">${skill.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-md-4">
            <label>Primary Skill</label>
            <div class="clearfix">
              <select class="chosen-select form-control input-sm" multiple data-placeholder="Primary Skill" id="primarySkill" name="primarySkill">
              </select>
            </div>
          </div>
        </div>
        <c:set var="currency" value="">
        </c:set>
        <div class="col-md-8">
          <div class="row">
            <div class="col-md-6 payrate_p">
              <label>Rate</label>
              <div class="input-group error-field">
                <div id="currencyDiv" class="input-group-btn selp-custom-min custommin">
                  <select class="selectpicker" name="currency" id="currency" field-name="Currency"  >
                    <option value="">Currency</option>
                    <option ${currency  == 'INR' ? 'selected' : ''}>INR</option>
                    <option ${currency  == 'AUD' ? 'selected' : ''}>AUD</option>
                    <option ${currency  == 'USD' ? 'selected' : ''}>USD</option>
                    <option ${currency  == 'GBP' ? 'selected' : ''}>GBP</option>
                    <option ${currency  == 'SGD' ? 'selected' : ''}>SGD</option>
                    <option ${currency  == 'EUR' ? 'selected' : ''}>EUR</option>
                  </select>
                </div>
                <div id="minRateDiv">
                  <input type="text" id="minRate" name="minRate" field-name="Min Rate" data-validation="validate_Space validate_float" data-validation-optional="true"  class="form-control input-sm" placeholder="Min Rate">
                </div>
              </div>
            </div>
            <div class="col-md-6 payrate_p">
              <label>&nbsp;</label>
              <div class="input-group error-field">
                <div id="maxRateDiv">
                  <input type="text" class="form-control input-sm" id="maxRate"  name="maxRate"  field-name="Max Rate"  data-validation="validate_Space validate_float" data-validation-optional="true"  placeholder="Max Rate">
                </div>
                <div id="rateTypeDiv" class="input-group-btn selp-custom-min custommin">
                  <select class="selectpicker" name="rateType" id="rateType" field-name="Rate Type"  >
                    <option value="">Rate Type</option>
                    <option value="Hourly">Hourly</option>
                    <option value="Weekly">Weekly</option>
                    <option value="Bi-weekly">Bi-weekly</option>
                    <option value="Semi-Monthly">Semi-Monthly</option>
                    <option value="Monthly">Monthly</option>
                    <option value="Bi-Monthly">Bi-Monthly</option>
                    <option value="Yearly">Yearly</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix">
          <div class="col-md-4">
            <label>Designation</label>
            <div class="clearfix">
              <select class="chosen-select form-control input-sm" multiple data-placeholder="Designation" id="designation" name="designation">
              </select>
            </div>
          </div>
        </div>
        <div class="clearfix">
          <div class="col-md-4">
            <label>Experience</label>
            <div class="clearfix row">
              <div class="col-md-6">
                <select class="chosen-select"  id="minExpYears" name="minExpYears" data-placeholder="Years" onblur="minMaxExperience();">
                  <option  selected value="">Min Years</option>
                  <c:forEach begin="0" end="24" varStatus="loop">
                    <option value="${loop.index}">${loop.index} Years</option>
                  </c:forEach>
                </select>
              </div>
              <div class="col-md-6">
                <select class="chosen-select" id="maxExpYears" name="maxExpYears" data-placeholder="Months" onblur="minMaxExperience();">
                  <option  value="">Max Years</option>
                  <c:forEach begin="0" end="25" varStatus="loop">
                    <option value="${loop.index}">${loop.index} Years</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="row" id="expId">
              <input type="hidden" name="expValidation" id="expValidation">
            </div>
          </div>
          <div class="col-md-4">
            <label>Notice Period</label>
            <div class="clearfix">
              <select class="chosen-select" id="noticePeriod" name="noticePeriod" data-placeholder="Notice Period" multiple>
                <option value="Immediately available">Immediately available</option>
                <option value="15 Days">15 Days</option>
                <option value="1 Month">1 Month</option>
                <option value="2 Months">2 Months</option>
                <option value="3 Months">3 Months</option>
              </select>
            </div>
          </div>
          <div class="col-md-4">
            <label>Employment Type</label>
            <div class="clearfix">
              <select class="chosen-select form-control" id="designationType" name="designationType">
                <option value="">--Select--</option>
                <option>C2C</option>
                <option>W-2</option>
                <option>C2H</option>
                <option>Part Time</option>
                <option>Full Time</option>
                <option>Other</option>
              </select>
            </div>
          </div>
        </div>
        <div class="clearfix">
          <div class="col-md-4">
            <label>Country</label>
            <div class="clearfix">
              <select class="chosen-select" name="country" id="country" field-name="Country"  >
                <option value="">--Select country--</option>
                <c:forEach var="cntry" items="${countryList}">
                  <option value="${cntry.name}">${cntry.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-md-4">
            <label>State</label>
            <div class="clearfix">
              <select  class="chosen-select" name="state" id="state" field-name="State"  >
                <option value="">---select state---</option>
                <%-- <c:forEach var="state" items="${statesList}">
							                     <option value="${state.stateName}">${state.stateName}</option>
						                </c:forEach> --%>
              </select>
            </div>
          </div>
          <div class="col-md-4">
            <label>City</label>
            <div class="clearfix">
              <select  class="chosen-select" name="city" id="city" field-name="City"  >
                <option value="">---select city---</option>
              </select>
            </div>
          </div>
        </div>
        <div class="clearfix">
          <div class="col-md-4">
            <label>Education</label>
            <div class="clearfix">
              <select class="chosen-select form-control input-sm" multiple data-placeholder="Education" id="education" name="education">
                <c:forEach var="edu" items="${educationsList}">
                  <option value="${edu.name}">${edu.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-md-4">
            <label>Exclude Education</label>
            <div class="clearfix">
              <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Education" id="excludeEducation" name="excludeEducation">
                <c:forEach var="edu" items="${educationsList}">
                  <option value="${edu.name}">${edu.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-md-4">
            <label>Certification</label>
            <div class="clearfix">
              <select class="chosen-select form-control input-sm" multiple data-placeholder="Certification" id="certification" name="certification">
              </select>
            </div>
          </div>
        </div>
        <div class="clearfix">
          <div class="col-md-4">
            <label>Employers</label>
            <div class="clearfix">
              <select class="chosen-select form-control input-sm" multiple data-placeholder="Employers" id="employers" name="employers">
              </select>
            </div>
          </div>
          <div class="col-md-4">
            <label>Exclude Employers</label>
            <div class="clearfix">
              <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Employers" id="excludeEmployers" name="excludeEmployers">
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="clearfix advancedsearch-footer">
    <div class="pull-right mr">
      <input type="button" value="Search" id="search_candidate" onclick="candidateList(); closePop();minMaxExperience();" class="btn btn-sm btn-success"/>
      <input type="button"  value="Reset"  onClick="clearCandidateSearchKeys();"class="btn btn-sm btn-primary"/>
      <!--  <input type="button" value="Cancel" onClick="closePop();clearCandidateSearchKeys();" class="btn btn-sm btn-danger"/> --> 
    </div>
  </div>
</div>
</div>
<div class="cand-advsrch-popup advancedsearch-popup" id="filterSearchMarket" style="display: none;">
  <div class="clearfix headerAdsrch">
    <h5 class="col-md-8 mb0 mt5">Profile Filter Search</h5>
    <a href="javascript:void(0)" class="closePop margin-top0" onclick="closePop();"></a> </div>
  <div class="addInner">
    <div class="row">
      <div class="col-md-3 mb positionRelative">
        <label><strong>All Candidates</strong></label>
        <div class="clearfix"></div>
        <div class="clearfix filterSearch-ul">
          <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL">
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>Any amount earned (1,710,407)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$1+ earned (143,386)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$100+ earned (125,894)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$10k+ earned (51,776)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>No earnings yet (1,567,021)</label>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-3 mb positionRelative">
        <label><strong>Job Success</strong></label>
        <div class="clearfix"></div>
        <div class="clearfix filterSearch-ul">
          <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL1">
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>Any amount earned (1,710,407)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$1+ earned (143,386)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$100+ earned (125,894)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$10k+ earned (51,776)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>No earnings yet (1,567,021)</label>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-3 mb positionRelative">
        <label><strong>Hourly Rate</strong></label>
        <div class="clearfix"></div>
        <div class="clearfix filterSearch-ul">
          <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL1">
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>Any amount earned (1,710,407)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$1+ earned (143,386)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$100+ earned (125,894)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$10k+ earned (51,776)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>No earnings yet (1,567,021)</label>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-3 mb positionRelative">
        <label><strong>Hours billed</strong></label>
        <div class="clearfix"></div>
        <div class="clearfix filterSearch-ul">
          <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL1">
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>Any amount earned (1,710,407)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$1+ earned (143,386)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$100+ earned (125,894)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$10k+ earned (51,776)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>No earnings yet (1,567,021)</label>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-3 mb positionRelative">
        <label><strong>Category</strong></label>
        <div class="clearfix"></div>
        <div class="clearfix filterSearch-ul">
          <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL1">
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>Any amount earned (1,710,407)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$1+ earned (143,386)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$100+ earned (125,894)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$10k+ earned (51,776)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>No earnings yet (1,567,021)</label>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-3 mb positionRelative">
        <label><strong>English level</strong></label>
        <div class="clearfix"></div>
        <div class="clearfix filterSearch-ul">
          <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL1">
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>Any amount earned (1,710,407)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$1+ earned (143,386)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$100+ earned (125,894)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$10k+ earned (51,776)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>No earnings yet (1,567,021)</label>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-3 mb positionRelative">
        <label><strong>Freelancer Type</strong></label>
        <div class="clearfix"></div>
        <div class="clearfix filterSearch-ul">
          <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL1">
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>Any amount earned (1,710,407)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$1+ earned (143,386)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$100+ earned (125,894)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$10k+ earned (51,776)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>No earnings yet (1,567,021)</label>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-3 mb positionRelative">
        <label><strong>Last Activity</strong></label>
        <div class="clearfix"></div>
        <div class="clearfix filterSearch-ul">
          <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL1">
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>Any amount earned (1,710,407)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$1+ earned (143,386)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$100+ earned (125,894)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>$10k+ earned (51,776)</label>
            </li>
            <li class="radioIput positionRelative">
              <input name="" value="" type="radio">
              <label>No earnings yet (1,567,021)</label>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Footer Area --> 
<script>
  function goToCandidateList(){
		location.href = "${appPath}/candidatesList";
	}
  $(document).ready(function () {
	 getGloblaMarketPlaceCandidates('listModel'); 
  });

function getGloblaMarketPlaceCandidates(divType){
	 $.ajax({
			type : "GET",
			url : sourceleadUrl + "/getGlobalMarketplaceListGridCard",
			success : function(object) {
				if(object != null){
					var result = "";
					var marketAddStatus = "";
					var marketDelelIcon = "";
					var profilePercentage = "";
					if(divType=="listModel"){
						for(var i=0;i<object.length;i++){
							if(object[i].marketCartStatus == null){
								marketDelelIcon = "";
								marketAddStatus = '<a type="button" onclick="addCandidateToMarketCart(\''+object[i].marketplaceUUID+'\', \'ListModel\')" class="anchorHighlight">Add</a>';
							}else if(object[i].marketCartStatus == "In_Cart"){
								marketDelelIcon = '	<a class="removeSelected mt" title="Remove Selected"><i class="fa fa-trash-o"></i></a>';
								marketAddStatus = '<a type="button" id="addedCartListModel'+object[i].marketplaceUUID+'" class="anchorHighlight anchorHighlightActive">Added</a>';
							}
							if(object[i].profilePercentage == null){
								profilePercentage = 0;
							}else{
								profilePercentage =object[i].profilePercentage;
							}
							result = result + '<div class="mt"></div>';
							result = result + '<div class="media profileDetails-Widget positionRelative"><div id="deleteIconListModel'+object[i].marketplaceUUID+'">';
							result = result + marketDelelIcon;
							result = result + '</div><div class="media-left grid-prfileImg pull-left"><img src="'+object[i].imageUrl+'" onerror="predefineProfileImage(this);" class="media-object" alt="Image">';
							result = result + '  <div class="clearfix"></div>';
							result = result + '</div>';
							result = result + '<div class="media-body">';
							result = result + '<div class="col-md-9">';
							result = result + ' <h4 class="media-heading mb10"><a href="profileSummary.html">'+object[i].fullName+'</a></h4>';
							result = result + ' <h5 class="text-muted mb5">'+object[i].designation+'</h5>';
							result = result + ' <div class="clearfix"></div>';
							result = result + '  <ul class="list-unstyled list-inline mt10">';
							result = result + '    <li class="mr mb5 text-info" title="Current Rate"><span class="paddingRight">'+object[i].currentRateCurrency+'</span>'+object[i].currentRate+'<small class="paddingLeft">/'+object[i].currentRateType+' (Current)</small></li>';
							result = result + '    <li class="mr mb5 textDanger" title="Expected Rate"><span class="paddingRight">'+object[i].expectedRateCurrencey+'</span>'+object[i].expectedRate+'<small class="paddingLeft">/'+object[i].expectedRateType+' (Expected)</small></li>';
							result = result + '   <li class="mr mb5" title="Experience"><span class="paddingRight"><i class="fa fa-briefcase text-muted" aria-hidden="true"></i></span>'+(object[i].experienceYears == null ? "0" : object[i].experienceYears )+'.'+(object[i].experienceMonths == null ? "0" : object[i].experienceMonths )+'<small class="text-muted paddingLeft">(Yrs)</small></li>';
							result = result + '   <li class="mr mb5" title="Location"><span class="paddingRight"><i class="fa fa-map-marker text-muted"></i></span>'+object[i].location+'</li>';
							result = result + ' </ul>';
							result = result + '<div class="clearfix"></div>';
							if(object[i].skillsName != null){
								var skills = object[i].skillsName.split(',');
								result = result + '<ul class="list-unstyled list-inline profileSkills mt10 mb0">';
								var count = 0;
								for(var entry of skills) {
									result = result + '<li>'+entry+'</li>';
									if(count == 6){
										result = result + '<li> <span class="text-success" title="'+object[i].skillsName+'">more</span> </li>';
										break;
									}
									count++;
								}
								result = result + ' </ul>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '<div class="clearfix fontSmall">';
							result = result + '	<h5 class="text-muted mb5 mt5">Description:</h5>';
							result = result + '	<p>'+object[i].summary+'</p>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '<div class="col-md-3">';
							result = result + '<div class="clearfix mt25 textBold">';
							result = result + '	<label class="text-muted mr">Price : <a>'+object[i].marketRateCurrencey+' '+object[i].marketPrice+'</a></label>';
							result = result + '<div class="clearfix"></div>';
							result = result + '	<label class="jobSucces_list clearfix mt" id="addCartListModel'+object[i].marketplaceUUID+'">'+marketAddStatus+'</label>';
							result = result + '</div>';
							result = result + '<div class="clearfix mt" id="addCartListModelDiv'+object[i].marketplaceUUID+'"></div>';
							result = result + '<h5 class="text-muted small clearfix mt">Profile :</h5>';
							result = result + '<div class="progress progressProfile mb0">';
							result = result + '<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:'+profilePercentage+'%">';
							result = result + profilePercentage+'%';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
						}
						$("#listView").empty().append(result);
						$("#listView").show();
						$("#cardView").hide();
						$("#listModel").find("i").addClass("text-muted text-primary");
						$("#cardModel").find("i").removeClass("text-primary text-muted");
					}
					if(divType=="cardModel"){
						result = result + '<div class="clearfix mt"></div>';
						for(var i=0;i<object.length;i++){
							if(object[i].marketCartStatus == null){
								marketDelelIcon = "";
								marketAddStatus = '<a type="button" id="addCartCardModel'+object[i].marketplaceUUID+'" onclick="addCandidateToMarketCart(\''+object[i].marketplaceUUID+'\', \'CardModel\')" class="anchorHighlight">Add</a>';
							}else if(object[i].marketCartStatus == "In_Cart"){
								marketDelelIcon = '	<a class="removeSelected mt" title="Remove Selected"><i class="fa fa-trash-o"></i></a>';
								marketAddStatus = '<a type="button" id="addedCartCardModel'+object[i].marketplaceUUID+'" class="anchorHighlight anchorHighlightActive">Added</a>';
							}
							if(object[i].profilePercentage == null){
								profilePercentage = 0;
							}else{
								profilePercentage =object[i].profilePercentage;
							}
							result = result + '  <div class="col-md-4 mb">';
							result = result + '<div class="media profileDetails-Widget">';
							result = result + '<div class="media-left prfileImg pull-left">';
							result = result + '<img src="'+object[i].imageUrl+'" onerror="predefineProfileImage(this);" class="media-object" alt="Image">';
							result = result + '<div class="clearfix mt5 textBold text-center">';
							result = result + '<div class="text-muted">Rate</div>';
							result = result + '<div class="jobSucces_list clearfix mt2"><a>'+object[i].marketRateCurrencey+' '+object[i].marketPrice+'</a></div>';
							result = result + '</div>';
							result = result + '<div class="clearfix mb"></div>';
							result = result + '<div class="clearfix text-center" id="addCartCardModelDiv'+object[i].marketplaceUUID+'">'+marketAddStatus+'</div>';
							result = result + '</div>';
							result = result + '<div class="media-body"><div id="deleteIconCardModel'+object[i].marketplaceUUID+'">';
							result = result + marketDelelIcon;
							result = result + '</div><div class="col-md-12 padding_right">';
							result = result + '<h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">'+object[i].fullName+'</a></h4>';
							result = result + '<h5 class="text-muted mb5">'+object[i].designation+'</h5>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<ul class="list-unstyled list-inline mt10">';
							result = result + '<li class="mb10 text-info" title="Current Rate"><span class="paddingRight">'+object[i].currentRateCurrency+'</span>'+object[i].currentRate+'<small class="paddingLeft">/'+object[i].currentRateType+' (Current)</small></li>';
							result = result + '<li class="mr5 mb10 textDanger" title="Expected Rate"><span class="paddingRight">'+object[i].expectedRateCurrencey+'</span>'+object[i].expectedRate+'<small class="paddingLeft">/'+object[i].expectedRateType+' (Expected)</small></li>';
							result = result + '<li class="mr mb10" title="Experience"><span class="paddingRight"><i class="fa fa-briefcase text-muted" aria-hidden="true"></i></span>'+(object[i].experienceYears == null ? "0" : object[i].experienceYears )+'.'+(object[i].experienceMonths == null ? "0" : object[i].experienceMonths )+'<small class="text-muted paddingLeft">(Yrs)</small></li>';
							result = result + '<li class="mr mb10" title="Location"><span class="paddingRight"><i class="fa fa-map-marker text-muted"></i></span>'+object[i].location+'</li>';
							result = result + '</ul>';
							if(object[i].skillsName != null){
								var skills = object[i].skillsName.split(',');
								result = result + '<ul class="list-unstyled list-inline profileSkills mt10 mb0">';
								var count = 0;
								for(var entry of skills) {
									result = result + '<li>'+entry+'</li>';
									if(count == 4){
										result = result + '<li> <span class="text-success" title="'+object[i].skillsName+'">more</span> </li>';
										break;
									}
									count++;
								}
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<h5 class="text-muted small mt5">Profile Updated:</h5>';
							result = result + '<div class="progress mb0 progressProfile">';
							result = result + '	<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:'+profilePercentage+'%">';
							result = result + profilePercentage+'%';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';				  
							result = result + '</div>';
						}
						$("#cardView").empty().append(result);
						$("#listView").hide();
						$("#cardView").show();
						$("#cardView").find("i").addClass("text-muted text-primary");
						$("#listModel").find("i").removeClass("text-primary text-muted");
					}
				}
			}
		});
}
function predefineProfileImage(imageId){
	$(imageId).attr('src','/resources/images/userprofileImg.jpg');
}
</script>
 
<script type="text/javascript">

function addCandidateToMarketCart(UUID, viewModel) {
   $.ajax({
        url: sourceleadUrl + "/addCandidateToMarketCart?id="+UUID,
        type: "GET",
        //data: id,
        success: function (response) {
        	if(response=="success"){
        		if(viewModel=="ListModel"){
	        		$("#addCartListModel"+UUID).hide();
	        		$("#addCartListModelDiv"+UUID).append('<a type="button" id="addedCartListModel'+UUID+'" class="anchorHighlight anchorHighlightActive">Added</a>');
	        		$("#deleteIconListModel"+UUID).append('<a class="removeSelected mt" title="Remove Selected"><i class="fa fa-trash-o"></i></a>');
        		}
        		if(viewModel=="CardModel"){
	        		$("#addCartCardModel"+UUID).hide();
	        		$("#addCartCardModelDiv"+UUID).append('<a type="button" id="addedCartCardModel'+UUID+'" class="anchorHighlight anchorHighlightActive">Added</a>');
	        		$("#deleteIconCardModel"+UUID).append('<a class="removeSelected mt" title="Remove Selected"><i class="fa fa-trash-o"></i></a>');
        		}
        		//alert("success");
        	}
        }
    });
}
$(".funPrice").on("click", "li", function(){
	var x = $(this).text();
	$(this).parents(".selectPriceli").find(".selectPrice").text(x);
});
			  
function listModel() {
	$("#tableDesign").show();
	$("#cardDesign").hide();
}
function cardModel() {
	$("#tableDesign").hide();
	$("#cardDesign").show();
}

function goToOrgMarketPlaceList(){
	location.href = "${appPath}/OrgMarketplace";
}
</script>
<script>
//Window scroll fixed header
$(window).scroll(function(){
    if ($(window).scrollTop() >= 100) {
        $('.profileDetails-header').addClass('profileDetails-headerFixed');
    }
    else {
        $('.profileDetails-header').removeClass('profileDetails-headerFixed');
    }
});
	
	
</script>
<jsp:include page="../masterFooter.jsp" />
<!-- <div class="mask"></div> -->
<script>
//Advanced Search
function closePop(){
	$(".advancedsearch-mask, #profileAdSearch").fadeOut();
	$(".advancedsearch-mask, #filterSearchMarket").fadeOut();
}
function openPop(){
$("#profileAdSearch, .advancedsearch-mask").fadeIn();
}
function filterPop() {
$(".advancedsearch-mask, #filterSearchMarket").fadeIn();
}
</script>