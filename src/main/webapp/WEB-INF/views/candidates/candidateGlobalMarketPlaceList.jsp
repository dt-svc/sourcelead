<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/candidateTable.js"></script>
    <!-- Content Area -->
    
    <section class="content-wraper">
    <section class="content-header clearfix">
    <div id="popUpDeleteCart"></div>
    <div class="pull-left">
      <h2 id="headerLbl">Global Marketplace List</h2>
    </div>
      <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; <label id="brudCrumLbl">Global Marketplace List</label> </div>
	  </c:if>
	  </section>
    <div class="clearfix"></div>
	  <div class="clearfix">
			<ul class="project-tabs-menu projecttabs-custom clearfix">
		         <li id="" ><a href="${SourceLeadWeb_atsUrl}/candidatesList">Candidate List</a></li>
		         <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
		         <li id="" class="current"><a href="javascript:void(0)">Global Marketplace</a></li>
		         <li id=""><a href="javascript:void(0)" onclick="javascript:goToCartList();">Cart</a></li>
		         <li id=""><a href="javascript:void(0)" onclick="javascript:goToPurchaseOrderList();">Purchase Orderlist</a></li>
                 <li id=""><a href="javascript:void(0)" onclick="javascript: goToSalesOrderList();">Sales Orderlist</a></li>
                 <li id=""><a href="javascript:void(0)" onclick="javascript:goToWalletList();">Wallet</a></li> 
		         <div class="clearfix"></div>
		     </ul>
      </div>
    <div class="clearfix"></div>
      <div class="searchprofile-profile">
        <div class="row clearfix search-tools">
        
          <div class="col-md-7 col-xs-12 reports-head">
            <input type="text" class="form-control" id="globalMarketSearh" value="" placeholder="Search Profile" id="" />
            
          </div>
          <div class="col-md-4 col-xs-12 padding_right">
            <input type="text" class="form-control" placeholder="Location" name="pwd" id = "candidateLocation" value = "">
          </div>
          <div class="col-md-1 col-xs-12 padding_left"> <a onclick = "getGloblaMarketPlaceCandidates('listModel')" class="btn btn-primary btn-block width50px pull-right"><i class="fa fa-search"></i></a> </div>
          <!-- <div class="clearfix"></div>
              <div class="tbldata-dotdot-100 mt">
		   	  	   <a href="#" onclick="initMarketplacePayment()">Pay</a>
		   </div> -->
          
          <div class="col-md-12 mt10"> <a href="#" class="pull-right small advancedSearch" title="Advanced Search" onClick=" openPop()">Advanced Search</a> </div>
        </div>
      </div>
      <div class="clerfix row">
        <div class="col-md-12">
          <div class="profileDetails">
            <div class="profileDetails-header row">
              <div class="col-md-3 mt5">
               <h5 id = "profileCount" class="clearfix pull-left small mb0 text-muted mt5">Showing  0  results</h5>
                <div class="pull-left headerItems">
                  <ul class="list-unstyled list-inline ml mb0 mt2">
                    <li><a title="List Model" href="javascript:void(0)" onclick="getGloblaMarketPlaceCandidates('listModel');" id="listModel"><i class="fa fa-th-list text-primary"></i></a></li>
					  <li><a title="Card Model" href="javascript:void(0)" onclick="getGloblaMarketPlaceCandidates('cardModel');" id="cardModel"><i class="fa fa-th text-muted"></i></a></li>
                  </ul>
                </div>
              </div>
              
                <div class="col-md-9">
				 <ul class="pull-right mb0 list-unstyled list-inline ulliBorder">
				 <sec:authorize access="hasRole('ROLE_VIEW_CART')">
				  <li><i class="shopping-cart inlineBlock pull-left mr10" title="Cart"></i><a id = "cartCount" href="${SourceLeadWeb_atsUrl}/candidateMarketplaceCart" class="inlineBlock pull-left mt2 mr10" href="javascript:void(0)">0</a></li>
				  <li><i class="dollarSvg inlineBlock pull-left mr10" title="Cart"></i><a id = "totalPrice" class="inlineBlock pull-left mt2 mr10" href="javascript:void(0)">0.00</a></li>
				  </sec:authorize>
                  <li> <a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a> </li>
					<li><span id="advanceSearchDiv" class="pull-left refreIcon noPad"> <a href="javascript:void(0)" class="refeIcon noMar" id="resetVendor" title="Refresh" onclick="refreshPage();"></a> </span> </li>
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
  
  <form:form id="candidatelist_form" name="candidatelist_form"  enctype='multipart/form-data' >
				  <!-- <div class="cand-advsrch-popup cand-advsrchPopup"> -->
				  <div class="cand-advsrch-popup advancedsearch-popup" id="candidateAdvanceSearchDiv">
					  <h5 class="headerAdsrch">Advanced Search
					  <a href="javascript:void(0)" class="close-div" onclick="closePop();clearCandidateSearchKeys();"><i class="fa fa-close"></i></a>
					  </h5>
					  <div class="addInner">
					  <!-- <div class="candAdvsrchPop"> -->
					  <div class="row">
						  <div class="col-md-4">
							  <label>Any of Keyword</label>
							  <input type="hidden" name="isSharedInMarketplace" value="Y"/>
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
						<c:set var="currency" value=""></c:set>
						<div class="col-md-8">
							<div class="row">
								<div class="col-md-6 payrate_p">
									<label>Rate</label>
									 <div class="input-group error-field">
										<div id="currencyDiv" class="input-group-btn selp-custom-min custommin">
										    <select class="selectpicker" name="currency" id="currency" field-name="Currency" onchange="ratecheck();" >
										        <option value="0">Currency</option>
										         <option ${currency  == 'INR' ? 'selected' : ''}>INR</option>
								                 <option ${currency  == 'AUD' ? 'selected' : ''}>AUD</option>
								                 <option ${currency  == 'USD' ? 'selected' : ''}>USD</option>
								                 <option ${currency  == 'GBP' ? 'selected' : ''}>GBP</option>
								                 <option ${currency  == 'SGD' ? 'selected' : ''}>SGD</option>
								                 <option ${currency  == 'EUR' ? 'selected' : ''}>EUR</option>
				                            </select>
										</div>
										 <div id="minRateDiv">
									      <input type="text" id="minRate" name="minRate" field-name="Min Rate" data-validation="validate_Space validate_float" data-validation-optional="true"  class="form-control input-sm" placeholder="Min Rate" onblur="ratecheck();">
								         </div>
								         <div class="row" id="rateId">
											<input type="hidden" name="rateValidation"
												id="rateValidation">
										</div>
								    </div>
								</div> 
							<div class="col-md-6 payrate_p">
								<label>&nbsp;</label> 
								<div class="input-group error-field">
								 <div id="maxRateDiv">
									  <input type="text" class="form-control input-sm" id="maxRate"  name="maxRate"  field-name="Max Rate" onblur = "ratevalidation();" data-validation="validate_Space validate_float" data-validation-optional="true"  placeholder="Max Rate">
								</div>
								<div class="row" id="ratecheckId">
											<input type="hidden" name="ratecheckValidation"
												id="ratecheckValidation">
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
								  <select  class="chosen-select" name="city" id="citySearch" field-name="City"  >
								      <option value="">---select city---</option>
                                  </select>
							  </div>
						  </div>
						  </div>
						  <div class="clearfix">
					  	<div class="col-md-4">
							  <label>Education</label>
							  <div class="clearfix"> 
								  <select class="chosen-select form-control input-sm" multiple data-placeholder="Education" id="education" name="education" onchange="EducationValidation();">
                                       <c:forEach var="edu" items="${educationsList}">
							                     <option value="${edu.name}">${edu.name}</option>
						                </c:forEach>
                                  </select>
							  </div>
						  </div> 
						  <div class="col-md-4">
							  <label>Exclude Education</label>
							  <div class="clearfix">
								   <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Education" id="excludeEducation" name="excludeEducation" onchange="EducationValidation();">
                                       <c:forEach var="edu" items="${educationsList}">
							                     <option value="${edu.name}">${edu.name}</option>
						                </c:forEach>
                                   </select>
                                   <div class="row" id="eduId">
											<input type="hidden" name="eduValidation"
												id="eduValidation">
										</div>
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
							  <div class="row" id="empId">
											<input type="hidden" name="empValidation"
												id="empValidation">
										</div>
						  </div>
						  
						   <!-- <div class="col-md-4">
							  <label>Applicant Type</label>
							  <div class="clearfix">
								   <select class="chosen-select" name="applicantType" id="applicantType" field-name="Applicant Type"  >
								      <option value="">---select Applicant Type---</option>
								      <option value="candidate">Candidate</option>
									  <option value="consultant">Consultant</option> 
				                   </select>
							  </div>
						  </div> -->
						  </div>
					  </div>
					  </div>
				  </div>
				  <div class="clearfix advancedsearch-footer">
						  <div class="pull-right mr">
							   <input type="button" value="Search" id="search_candidate" onclick="candidateList('gMarketPlace'); closePop();minMaxExperience();" class="btn btn-sm btn-success"/>
                               <input type="button"  value="Reset"  onClick="clearCandidateSearchKeys();"class="btn btn-sm btn-primary"/>
                               <!--  <input type="button" value="Cancel" onClick="closePop();clearCandidateSearchKeys();" class="btn btn-sm btn-danger"/> -->
						  </div>
					  </div>
				  </div>
			  </form:form>

<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="candidateFilterPopup">
	<h5 class="headerAdsrch">Filters
		<a href="javascript:void(0)" class="close-div" onClick="closeFilterPopup()"><i
			class="fa fa-close"></i></a>
	</h5>
	<div class="addInner">
		<div class="row">
			<div class="col-md-2">
			<div class="row">
			
			<div class="col-md-12 mb">
				<label><strong>All Candidates</strong></label>
				 <div class="clearfix">
					<ul class="list-unstyled" id="AllTypeFilter">
					    <li class="radioIput positionRelative"><input id="candidateAll" type="radio" name="candidateStatus" checked  value ="2"   ><label for="candidateAll">All</label></li>
						<li class="radioIput positionRelative"><input id="candidateActive" type="radio" name="candidateStatus" value ="1"  ><label for="candidateActive">Active</label></li>
						<li class="radioIput positionRelative" ><input id="candidateInActive" name="candidateStatus" type="radio" value ="0" ><label for="candidateInActive">In Active</label></li>
                    </ul>
				 </div>
				 </div> 
				 
				 
			    <div class="col-md-12 mb">
				<label><strong>Type</strong></label>
				 <div class="clearfix">
					<ul class="list-unstyled" id="TypeFilter">
					     <li class="radioIput positionRelative" ><input id="candidateTypeAll" type="radio" name="candidateType" checked value ="All" ><label for="candidateTypeAll">All</label></li>
						 <li class="radioIput positionRelative" ><input id="onlycandidate" type="radio" name="candidateType" value ="Favourite" ><label for="onlycandidate">Favourite</label></li>
                    </ul>
				 </div>
				 </div>
				 
				 
				 <div class="col-md-12 mb">
				<label><strong>Candidate Type</strong></label>
				 <div class="clearfix">
					<ul class="list-unstyled" id="candidateTypeFilter">
					    <li class="checkBox positionRelative" ><input  type="checkbox" id="candidateCheck" name="candidateTypeFilter" checked value ="Candidates"  ><label>Candidates</label></li>
						 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
						<li class="checkBox positionRelative" ><input  type="checkbox" id="associateCheck"  name="candidateTypeFilter"  value ="Associates" ><label>Associates</label></li>
                   </c:if>
                    </ul>
				 </div>
				 </div>  
			  </div>
			</div>
			
			<div class="col-md-2">
				<div class="row">
			       <div class="col-md-12 mb">
				     <label><strong>Updated</strong></label>
					 <div class="clearfix">
						<ul class="list-unstyled" id="UpdatedFilter">
						<li class="radioIput positionRelative" ><input id="allCandidates" type="radio" name="upcandidateStatus" value ="all" ><label for="allCandidates">All</label></li>
                        <li class="radioIput positionRelative" ><input id="candidateToDay" type="radio" name="upcandidateStatus" value ="today"  ><label for="candidateDayAgo">Today</label></li>
                        <li class="radioIput positionRelative" ><input id="candidateDayAgo" type="radio" name="upcandidateStatus" value ="1_day_ago" ><label for="candidateDayAgo">1 Day ago</label></li>
						<li class="radioIput positionRelative" ><input id="candidate3DayAgo" type="radio" name="upcandidateStatus" value ="3_days_ago" ><label for="candidate3DayAgo">3 Days ago</label></li>
						<li class="radioIput positionRelative" ><input id="candidate1WeekAgo" name="upcandidateStatus" type="radio" value ="1_week_ago" ><label for="candidate1WeekAgo">1 Week ago</label></li>
						<li class="radioIput positionRelative" ><input id="candidate3WeekAgo" name="upcandidateStatus" type="radio" value ="2_weeks_ago" ><label for="candidate3WeekAgo">2 Weeks ago</label></li>
						<li class="radioIput positionRelative" ><input id="candidateMonthAgo" name="upcandidateStatus" type="radio" value ="1_month_ago" ><label for="candidateMonthAgo">1 Month ago</label></li>
	                    <li class="radioIput positionRelative" ><input id="candidate3Months"  name="upcandidateStatus" type="radio" value ="3_months_ago"><label for="candidate3Months">3 Months ago</label></li>
	                    
                        </ul>
					 </div>
				 </div>
				 
				 <!-- <div class="col-md-12 mb">
				<label><strong>Created By</strong></label>
				 <div class="clearfix">
					<ul class="list-unstyled" id="createdByTypeFilter">
					    <li class="radioIput positionRelative" ><input  type="radio" id="myCandidatesRadio" name="createdByFilter"  value ="myCandidates" checked ><label>My Candidates</label></li>
						<li class="radioIput positionRelative" ><input  type="radio" id="allCandidatesRadio"  name="createdByFilter"  value ="allCandidates"  ><label>All Candidates</label></li>
                    </ul>
				 </div>
				 </div> -->  
			 </div>
			</div> 
			
			<div class="col-md-2">
				<div class="row">
					<div class="col-md-12 mb">
						<label><strong>Employment Type</strong></label>
						 <div class="clearfix">
							<ul class="list-unstyled" id="employmentFilter">
	                    	    <li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="C2C"  ><label>C2C</label></li>
								<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="C2C-1099" ><label>C2C-1099</label></li>
								<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="W-2" ><label>W-2</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="C2H"  ><label>C2H</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="C2H -1099" ><label>C2H -1099</label></li>
	               			    <li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="Part Time" ><label>Part Time</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="employmentType"  value ="Full Time" ><label>Full Time</label></li>
	                        </ul>
						 </div>
						 </div> 
				     </div>
			</div>
			
			
			<div class="col-md-2">
				<div class="row">
					<div class="col-md-12 mb">
						<label><strong>Availability</strong></label>
						 <div class="clearfix">
							<ul class="list-unstyled" id="availabilityFilter">
	                    	    <li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="Immediately available"  ><label>Immediately available</label></li>
								<li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="15 Days" ><label>15 Days</label></li>
								<li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="1 Month" ><label>1 Month</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="2 Months"  ><label>2 Months</label></li>
	               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="availability"  value ="3 Months" ><label>3 Months</label></li>
	                        </ul>
						 </div>
						 </div> 
				     </div>
			</div>
			
			<div class="col-md-3">
					<div class="clearfix dynamic_search filterDynamicsearch">
						<input type="text" id="mySkillsInput" onkeyup="mySkillsFunction()" placeholder="Skills" title="Type in a skills">
						<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
					</div>
					 <div class="clearfix filterSearch-ul">
						<ul class="list-unstyled positionRelative filterSearch mt5" id="skillsUL">
							<c:forEach var="skill" items="${skillsList}">
							<li class="checkBox positionRelative"><input  type="checkbox" name="candidateFilterSkills" value ="${skill.name}" ><label>${skill.name}</label></li>
							</c:forEach>
						</ul>
					 </div> 
		 </div> 
			
		</div>
		</div>
		<div class="clearfix advancedsearch-footer">
			<div class="pull-right mr">
				<input type="button" value="Apply" id="search_requiremet" onclick="closeFilterPopup();getGloblaMarketPlaceCandidates('listModel');<!-- candidateList('gMarketPlace'); -->" class="btn btn-sm btn-success" />
				<input type="button" value="Reset" id="clear_search" onclick="resetCandidateFilter();" class="btn btn-sm btn-primary" />
			</div>
		</div>
</div>

<!-- Footer Area --> 
<script>
var atsUrl = '${SourceLeadWeb_atsUrl}';
function deleteCandidateMarketplaceCart(candidateName, marketPlaceUUID, gridType){ // only stauts updated with reason. active 1 , deactive 0.
	 $(".project_inn_mask").remove();
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_marketCart_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +" <div class='modal-body'><b>Do you want to remove "+candidateName+" from the cart!</b></div>";
		result = result + ' <div id="reasonForTemplateDiv"/>';
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteCandidateMarketCart(\""+candidateName+"\",\""+marketPlaceUUID+"\", \""+gridType+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelMarketCartPopup();'></b>";
		result = result +"	</div>";
		$("#popUpDeleteCart").empty().append(result);
		$("#remove_marketCart_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
function cancelMarketCartPopup(){
	$("#remove_marketCart_pop").fadeOut(200);
	$(".project_inn_mask").fadeOut(200);
}

function mySkillsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("mySkillsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("skillsUL");
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


  function goToCandidateList(){
		location.href = "${SourceLeadWeb_atsUrl}/candidatesList";
	}
  $(document).ready(function () {
	 getGloblaMarketPlaceCandidates('listModel'); 
	 updateMarketplaceCartCountAndPrice();
	});
var globalMarketDivType = null;
function getGloblaMarketPlaceCandidates(divType, globalCandidateIds){
	globalMarketDivType = divType;
	
	//fetching filter values..
	var status = $('input[name=candidateStatus]:checked').val();
	var updatedDate = $('input[name=upcandidateStatus]:checked').val();
	var isAssociate = $('input[name=candidateTypeFilter]:checked').val();
	var isFavourite = $('input[name=candidateType]:checked').val();
	var createdBy = $('input[name=createdByFilter]:checked').val();
	var employmentType = [];
		$('input:checkbox[name=employmentType]:checked').each(function() 
		     {
		 		employmentType.push($(this).val());
			 });
	var noticePeriod = [];
		$('input:checkbox[name=availability]:checked').each(function() 
			 {
				noticePeriod.push($(this).val());
			 });
	var candidateSkills = [];
	 	$('input:checkbox[name=candidateFilterSkills]:checked').each(function() 
     		{
	 			candidateSkills.push($(this).val());
	 		});
	 	
	var searchValue = $("#globalMarketSearh").val();
	searchValue = searchValue ? searchValue : "";
	var candidateLocation = $("#candidateLocation").val();
	candidateLocation = (candidateLocation.length != null) ? candidateLocation : "" ;
	$.ajax({
			type : "POST",
			url : atsUrl +"/getGlobalMarketplaceListGridCard?searchValue="+searchValue+"&candidateLocation="+candidateLocation+"&searchCandidateIds="+globalCandidateIds+
			"&status="+status+"&updatedDate="+updatedDate+"&employmentType="+employmentType+"&noticePeriod="+noticePeriod+"&skillsName="+candidateSkills+
			"&isFavourite="+isFavourite+"&isAssociate="+isAssociate+"&createdBy="+createdBy,
			success : function(object) {
				if(object != null){
					var result = "";
					var marketAddStatus = "";
					var marketDelelIcon = "";
					var profilePercentage = "";
					if(divType=="listModel"){
						if(object.length != 0){
						for(var i=0;i<object.length;i++){
							if(object[i].marketCartStatus == null){
								marketDelelIcon = "";
								 '<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')">'; 
								marketAddStatus = '<a type="button" onclick="addCandidateToMarketCart(\''+(object[i].fullName == null ? "---" : object[i].fullName)+'\',\''+object[i].marketplaceUUID+'\', \'ListModel\'); disableOnClick(this);" class="anchorHighlight">Add</a>';
								'</sec:authorize>';
							}else if(object[i].marketCartStatus == "In_Cart"){
								 '<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')">'; 
								marketDelelIcon = '	<a class="removeSelected mt" title="Remove Selected" onclick="deleteCandidateMarketplaceCart(\''+(object[i].fullName == null ? "---" : object[i].fullName)+'\', \''+object[i].marketplaceUUID+'\', \'ListModel\');"><i class="fa fa-trash-o"></i></a>';
								marketAddStatus = '<a type="button" id="addedCartListModel'+object[i].marketplaceUUID+'" class="anchorHighlight anchorHighlightActive">Added</a>';
								'</sec:authorize>';
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
							result = result + ' <h4 class="media-heading mb10"><a href="${SourceLeadWeb_atsUrl}/globalCandidateSummary?marketplaceUUID='+object[i].marketplaceUUID+'">'+(object[i].fullName == null ? "---" : object[i].fullName)+'</a></h4>';
							result = result + ' <h5 class="text-muted mb5">'+(object[i].designation == null ? "---" : object[i].designation)+'</h5>';
							result = result + ' <div class="clearfix"></div>';
							result = result + '  <ul class="list-unstyled list-inline mt10">';
							result = result + '    <li class="mr mb5 text-info" title="Current Rate"><span class="paddingRight">'+(object[i].currentRateCurrency == null ? "---" : object[i].currentRateCurrency)+'</span>'+(object[i].currentRate == null ? "---" : object[i].currentRate)+'<small class="paddingLeft">/'+(object[i].currentRateType == null ? "---" : object[i].currentRateType)+' (Current)</small></li>';
							result = result + '    <li class="mr mb5 textDanger" title="Expected Rate"><span class="paddingRight">'+(object[i].expectedRateCurrencey == null ? "---" : object[i].expectedRateCurrencey)+'</span>'+(object[i].expectedRate == null ? "---" : object[i].expectedRate)+'<small class="paddingLeft">/'+(object[i].expectedRateType == null ? "---" : object[i].expectedRateType)+' (Expected)</small></li>';
							result = result + '   <li class="mr mb5" title="Experience"><span class="paddingRight"><i class="fa fa-briefcase text-muted" aria-hidden="true"></i></span>'+(object[i].experienceYears == null ? "---" : object[i].experienceYears )+'.'+(object[i].experienceMonths == null ? "---" : object[i].experienceMonths )+'<small class="text-muted paddingLeft">(Yrs)</small></li>';
							result = result + '   <li class="mr mb5" title="Location"><span class="paddingRight"><i class="fa fa-map-marker text-muted"></i></span>'+(object[i].location == null ? "---" : object[i].location)+'</li>';
							result = result + ' </ul>';
							result = result + '<div class="clearfix"></div>';
							if(object[i].showCompanyName != null && object[i].showCompanyName =="Y"){
							result = result + '<p class="clearfix text-primary textBold mb0 tbldata-dotdot-verybig inlineBlock">'+object[i].companyName+'</p>';
							result = result + '<div class="clearfix"></div>';
							}
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
							result = result + '	<p>'+(object[i].summary == null ? "---" : object[i].summary)+'</p>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '<div class="col-md-3">';
							result = result + '<div class="clearfix mt25 textBold">';
							result = result + '	<label class="text-muted mr">Price : <a>'+(object[i].marketRateCurrencey == null ? "---" : object[i].marketRateCurrencey)+' '+(object[i].marketPrice == null ? "---" : object[i].marketPrice)+'</a></label>';
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
						}else{
							result = result + "<div class='dataTables_empty'></div>";
						}
						$("#listView").empty().append(result);
						$("#listView").show();
						$("#cardView").hide();
						$("#listModel").find("i").addClass("text-muted text-primary");
						$("#cardModel").find("i").removeClass("text-primary text-muted");
					}
					if(divType=="cardModel"){
						result = result + '<div class="clearfix mt"></div>';
						if(object.length != 0){
						for(var i=0;i<object.length;i++){
							if(object[i].marketCartStatus == null){
								marketDelelIcon = "";
								'<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')">'; 
								marketAddStatus = '<a type="button" id="addCartCardModel'+object[i].marketplaceUUID+'" onclick="addCandidateToMarketCart(\''+(object[i].fullName == null ? "---" : object[i].fullName)+'\',\''+object[i].marketplaceUUID+'\', \'CardModel\'); disableOnClick(this);" class="anchorHighlight">Add</a>';
								 '</sec:authorize>';
							}else if(object[i].marketCartStatus == "In_Cart"){
								 '<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')">'; 
								marketDelelIcon = '	<a class="removeSelected mt" title="Remove Selected" onclick="deleteCandidateMarketplaceCart(\''+(object[i].fullName == null ? "---" : object[i].fullName)+'\', \''+object[i].marketplaceUUID+'\', \'CardModel\');"><i class="fa fa-trash-o"></i></a>';
								marketAddStatus = '<a type="button" id="addedCartCardModel'+object[i].marketplaceUUID+'" class="anchorHighlight anchorHighlightActive">Added</a>';
								 '</sec:authorize>';
							}
							if(object[i].profilePercentage == null){
								profilePercentage = 0;
							}else{
								profilePercentage =object[i].profilePercentage;
							}
							result = result + '  <div class="col-md-4 mb">';
							result = result + '<div class="media profileDetails-Widget overflow_Auto" style="height:350px;">';
							result = result + '<div class="media-left prfileImg pull-left">';
							result = result + '<img src="'+object[i].imageUrl+'" onerror="predefineProfileImage(this);" class="media-object" alt="Image">';
							result = result + '<div class="clearfix mt5 textBold text-center">';
							result = result + '<div class="text-muted">Rate</div>';
							result = result + '<div class="jobSucces_list clearfix mt2"><a>'+(object[i].marketRateCurrencey == null ? "---" : object[i].marketRateCurrencey)+' '+(object[i].marketPrice == null ? "---" : object[i].marketPrice)+'</a></div>';
							result = result + '</div>';
							result = result + '<div class="clearfix mb"></div>';
							result = result + '<div class="clearfix text-center" id="addCartCardModelDiv'+object[i].marketplaceUUID+'">'+marketAddStatus+'</div>';
							result = result + '</div>';
							result = result + '<div class="media-body"><div id="deleteIconCardModel'+object[i].marketplaceUUID+'">';
							result = result + marketDelelIcon;
							result = result + '</div><div class="col-md-12 padding_right">';
							result = result + '<h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="${SourceLeadWeb_atsUrl}/globalCandidateSummary?marketplaceUUID='+object[i].marketplaceUUID+'">'+(object[i].fullName == null ? "---" : object[i].fullName)+'</a></h4>';
							result = result + '<h5 class="text-muted mb5">'+(object[i].designation == null ? "---" : object[i].designation)+'</h5>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<ul class="list-unstyled list-inline mt10">';
							result = result + '<li class="mb10 text-info" title="Current Rate"><span class="paddingRight">'+(object[i].currentRateCurrency == null ? "---" : object[i].currentRateCurrency)+'</span>'+(object[i].currentRate == null ? "---" : object[i].currentRate)+'<small class="paddingLeft">/'+(object[i].currentRateType == null ? "---" : object[i].currentRateType)+' (Current)</small></li>';
							result = result + '<li class="mr5 mb10 textDanger" title="Expected Rate"><span class="paddingRight">'+(object[i].expectedRateCurrencey == null ? "---" : object[i].expectedRateCurrencey)+'</span>'+(object[i].expectedRate == null ? "---" : object[i].expectedRate)+'<small class="paddingLeft">/'+(object[i].expectedRateType == null ? "---" : object[i].expectedRateType)+' (Expected)</small></li>';
							result = result + '<li class="mr mb10" title="Experience"><span class="paddingRight"><i class="fa fa-briefcase text-muted" aria-hidden="true"></i></span>'+(object[i].experienceYears == null ? "---" : object[i].experienceYears )+'.'+(object[i].experienceMonths == null ? "---" : object[i].experienceMonths )+'<small class="text-muted paddingLeft">(Yrs)</small></li>';
							result = result + '<li class="mr mb10" title="Location"><span class="paddingRight"><i class="fa fa-map-marker text-muted"></i></span>'+(object[i].location == null ? "---" : object[i].location)+'</li>';
							result = result + '</ul>';
							if(object[i].showCompanyName != null && object[i].showCompanyName =="Y"){
							result = result + '<p class="clearfix text-primary textBold mb0 inlineBlock tbldata-dotdot-200">'+object[i].companyName+'</p>';
							result = result + '<div class="clearfix"></div>';
							}
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
						}else{
							result = result + "<div class='dataTables_empty'></div>";
						}
						$("#cardView").empty().append(result);
						$("#listView").hide();
						$("#cardView").show();
						$("#cardView").find("i").addClass("text-muted text-primary");
						$("#listModel").find("i").removeClass("text-primary text-muted");
					}
					$("#profileCount").html("Showing "+object.length+" results ");
				}
			}
		});
}
function predefineProfileImage(imageId, viewModel){
	$(imageId).attr('src','/resources/images/userprofileImg.jpg');
}

function deleteCandidateMarketCart(candidateName, candidateMarketUUID, viewModel){
	$.ajax({
		type : "GET",
		url : atsUrl + "/deleteCandidateMarketCart?marketUUID="+candidateMarketUUID,
		success : function(result) {
			if (result.response === "AWKWARD") {
                location.href = "/errorPage";
            }else if(result.response === "SUCCESS") {
            	if(viewModel == "ListModel"){
					$("#deleteIconListModel"+candidateMarketUUID).empty();
					$("#addCartListModel"+candidateMarketUUID).empty()
					$("#addCartListModelDiv"+candidateMarketUUID).empty().append('<a type="button" id="addCartListModel'+candidateMarketUUID+'" onclick="addCandidateToMarketCart(\''+candidateName+'\', \''+candidateMarketUUID+'\', \'ListModel\'); disableOnClick(this);" class="anchorHighlight">Add</a>');
				}
				if(viewModel == "CardModel"){
					$("#deleteIconCardModel"+candidateMarketUUID).empty();
					$("#addCartCardModel"+candidateMarketUUID).empty()
					$("#addCartCardModelDiv"+candidateMarketUUID).empty().append('<a type="button" id="addCartCardModel'+candidateMarketUUID+'" onclick="addCandidateToMarketCart(\''+candidateName+'\', \''+candidateMarketUUID+'\', \'CardModel\'); disableOnClick(this);" class="anchorHighlight">Add</a>');
				}
				updateMarketplaceCartCountAndPrice();
				$("#remove_marketCart_pop").fadeOut(200);
				$(".project_inn_mask").fadeOut(200);
				}else{
	            	 showError("Operation failed, please try again");
	            }
				}
		}); 
	}
</script>
<script>

function ratecheck(){
	//Function to validate currency option
	var a = $("#currency").val();
	if(a==0){
		 $('#rateId').find('p.jquery_form_error_message').remove(); 
			$('input#rateValidation').attr('class','error signin_fild');
			$("input#rateValidation").after("<p class='jquery_form_error_message'>Select Currency</p>");
			$('input#rateValidation').attr('record-exist','yes');
			$('input#rateValidation').attr('record-exist-errorMsg','Currency Selected'); 
	}else{
    	$('#rateId').find('p.jquery_form_error_message').remove();
    	$('input#rateValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#rateId').find('p.jquery_form_error_message').remove();
		
	}
}

function ratevalidation(){
	//Function to validate if maxrate  value is more the minrate  value
	var minRate = $('#minRate').val();
	var maxRate = $('#maxRate').val();
	if (parseFloat(maxRate) <= parseFloat(minRate)){
        $('#ratecheckId').find('p.jquery_form_error_message').remove(); 
		$('input#ratecheckValidation').attr('class','error signin_fild');
		$("input#ratecheckValidation").after("<p class='jquery_form_error_message'>Max Rate should be greater than Min Rate</p>");
		$('input#ratecheckValidation').attr('record-exist','yes');
		$('input#ratecheckValidation').attr('record-exist-errorMsg','Max Rate should be greater than Min Rate'); 
    }  else{
    	$('#ratecheckId').find('p.jquery_form_error_message').remove(); 
    	$('input#ratecheckValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#ratecheckId').find('p.jquery_form_error_message').remove();
    } 
}

function EducationValidation(){
	//Function to validate Education and Exclude Education value 
	var a = [];
	var b = [];
	 a =$('#education').val();
	 b =$('#excludeEducation').val();
	if(JSON.stringify(a)===JSON.stringify(b)){
		if(a==null&&b==null){
			$('#eduId').find('p.jquery_form_error_message').remove();
		 	$('input#eduValidation').removeAttr( "class record-exist record-exist-errormsg");
				$('#eduId').find('p.jquery_form_error_message').remove();
		}else{
		 $('#eduId').find('p.jquery_form_error_message').remove(); 
			$('input#eduValidation').attr('class','error signin_fild');
			$("input#eduValidation").after("<p class='jquery_form_error_message'>Education and Exclude Education Should not be same</p>");
			$('input#eduValidation').attr('record-exist','yes');
			$('input#eduValidation').attr('record-exist-errorMsg','Education and Exclude Education Should not be Same'); 
		}}else if(a==null||b==null){
			 $('#eduId').find('p.jquery_form_error_message').remove();
		$('input#eduValidation').attr('class','error signin_fild');
		$("input#eduValidation").after("<p class='jquery_form_error_message'>Enter the value</p>");
		$('input#eduValidation').attr('record-exist','yes');
		$('input#eduValidation').attr('record-exist-errorMsg','Enter the value'); 
	}else{
 	$('#eduId').find('p.jquery_form_error_message').remove();
 	$('input#eduValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#eduId').find('p.jquery_form_error_message').remove();
	}
}
</script>
<script>
var selectthree, chosenthree;
selectthree = $("#excludeEmployers");
selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
chosenthree = selectthree.data('chosen');
chosenthree.search_field.on('keydown keyup', function(e3)
{
	var employers = [];
	var excludeEmployers = [];
	excludeEmployers = $("#excludeEmployers").val();
	 employers = $("#employers").val();
	 if(employers!=null){
	 employers.sort();
	 }
	 if(excludeEmployers!=null){
	 excludeEmployers.sort();

}
	 if(JSON.stringify(employers) == JSON.stringify(excludeEmployers)){
		if(employers==null&&excludeEmployers==null){
			$('#empId').find('p.jquery_form_error_message').remove();
		 	$('input#empValidation').removeAttr( "class record-exist record-exist-errormsg");
				$('#empId').find('p.jquery_form_error_message').remove();
		}else {
	 $("#excludeEmployers").val('').trigger("chosen:updated");
	 $('#empId').find('p.jquery_form_error_message').remove(); 
		$('input#empValidation').attr('class','error signin_fild');
		$("input#empValidation").after("<p class='jquery_form_error_message'>Employer and Exclude Employer Should not be same</p>");
		$('input#empValidation').attr('record-exist','yes');
		$('input#empValidation').attr('record-exist-errorMsg','Employer and Exclude Employer Should not be Same'); 
	}
	 }
	else{
		$('#empId').find('p.jquery_form_error_message').remove();
	 	$('input#empValidation').removeAttr( "class record-exist record-exist-errormsg");
			$('#empId').find('p.jquery_form_error_message').remove();
	}

	/* var organizations = this.value.replace(",","");
    if ((e3.which == 13 || e3.which == 188) && (organizations != null  && organizations != '' && organizations != 'undefined'))
    {
    	var par = this.parent;
        var option = $("<option>").val(organizations).text(organizations);
        selectthree.append(option);
        selectthree.find(option).prop('selected', true);
        $("#organizations option").each(function(){
              $(this).siblings("[value='"+ this.value+"']").remove();
        });
		$("#organizations").trigger("chosen:updated");
		
    } */
});
 </script>
 <script>
 
var selectfour, chosenfour;
selectfour = $("#employers");
selectfour.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
chosenfour = selectfour.data('chosen');
chosenfour.search_field.on('keydown keyup', function(e4)
{
	
	var employers = [];
	var excludeEmployers = [];
	excludeEmployers = $("#excludeEmployers").val();
	 employers = $("#employers").val();
	 if(employers!=null){
	 employers.sort();
	 }
	 if(excludeEmployers!=null){
	 excludeEmployers.sort();
}
	 if(JSON.stringify(excludeEmployers) == JSON.stringify(employers)){
		if(employers==null&&excludeEmployers==null){
			$('#empId').find('p.jquery_form_error_message').remove();
		 	$('input#empValidation').removeAttr( "class record-exist record-exist-errormsg");
				$('#empId').find('p.jquery_form_error_message').remove();
		}else {
	 $("#employers").val('').trigger("chosen:updated");
	 $('#empId').find('p.jquery_form_error_message').remove(); 
		$('input#empValidation').attr('class','error signin_fild');
		$("input#empValidation").after("<p class='jquery_form_error_message'>Employer and Exclude Employer Should not be same</p>");
		$('input#empValidation').attr('record-exist','yes');
		$('input#empValidation').attr('record-exist-errorMsg','Employer and Exclude Employer Should not be Same'); 
	}
	 }
	else{
		$('#empId').find('p.jquery_form_error_message').remove();
	 	$('input#empValidation').removeAttr( "class record-exist record-exist-errormsg");
			$('#empId').find('p.jquery_form_error_message').remove();
	}
});
</script>

<script type="text/javascript">

function addCandidateToMarketCart(candidateName, UUID, viewModel) {
	  $.ajax({
	        url: atsUrl + "/addCandidateToMarketCart?id="+UUID,
	        type:'GET',
	        success: function (result) {
	        	 if (result.response === "AWKWARD") {
	                 location.href = "/errorPage";
	             }else if(result.response === "SUCCESS") {
	             	if(viewModel=="ListModel"){
		        		$("#addCartListModel"+UUID).empty();
		        		$("#addCartListModelDiv"+UUID).empty().append('<a type="button" id="addedCartListModel'+UUID+'" class="anchorHighlight anchorHighlightActive">Added</a>');
		        		$("#deleteIconListModel"+UUID).empty().append('<a class="removeSelected mt" title="Remove Selected" onclick="deleteCandidateMarketplaceCart(\''+candidateName+'\', \''+UUID+'\', \'ListModel\');"><i class="fa fa-trash-o"></i></a>');
	        		}
	        		if(viewModel=="CardModel"){
		        		$("#addCartCardModel"+UUID).empty();
		        		$("#addCartCardModelDiv"+UUID).empty().append('<a type="button" id="addedCartCardModel'+UUID+'" class="anchorHighlight anchorHighlightActive">Added</a>');
		        		$("#deleteIconCardModel"+UUID).empty().append('<a class="removeSelected mt" title="Remove Selected" onclick="deleteCandidateMarketplaceCart(\''+candidateName+'\', \''+UUID+'\', \'CardModel\');"><i class="fa fa-trash-o"></i></a>');
	        		}
	        		updateMarketplaceCartCountAndPrice();
	        	}else{
	            	 showError("Operation failed, please try again");
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
	location.href = "${SourceLeadWeb_atsUrl}/OrgMarketplace";
}
function goToCartList(){
	location.href = "${SourceLeadWeb_atsUrl}/candidateMarketplaceCart";
}
function goToPurchaseOrderList(){
	location.href ="${SourceLeadWeb_atsUrl}/PurchaseOrderList";
	}
function goToSalesOrderList(){
	location.href = "${SourceLeadWeb_atsUrl}/SalesOrderList";
}
function goToWalletList(){
    location.href = "${SourceLeadWeb_atsUrl}/walletList";
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
	 $("div.cand-advsrch-popup").hide();
	 $('#expId').find('p.jquery_form_error_message').remove();
}
function openPop(){
//$("#profileAdSearch, .advancedsearch-mask").fadeIn();
$("#candidateAdvanceSearchDiv").toggle();
$(".advancedsearch-mask").fadeIn();
//$("#candidateFilterPopup").toggle();
}

function closeFilterPopup(){
	$("#candidateFilterPopup").hide();
	$(".advancedsearch-mask").fadeOut();
}
function filterPop() {
	$("#candidateFilterPopup").toggle();
	 $(".advancedsearch-mask").fadeIn();
//$(".advancedsearch-mask, #filterSearchMarket").fadeIn();
}
</script>
<script type="text/javascript">
    function initMarketplacePayment() {
         $.ajax({
	      type: "POST",
    	   url: "${SourceLeadWeb_sourceleadUrl}/init-marketplace-payment?cartId=1234",
      	success: function(data) {
      		 if (data) {
	                location.href = data;
	            }
	        	
	        },  
  		 }); 
    }
    
    function minMaxExperience(){
        var maxExpYears = ($('#maxExpYears').val());
        var minExpYears = ($('#minExpYears').val());
       
       if (parseFloat(maxExpYears) <= parseFloat(minExpYears)){
           $('#expId').find('p.jquery_form_error_message').remove(); 
   		$('input#expValidation').attr('class','error signin_fild');
   		$("input#expValidation").after("<p class='jquery_form_error_message'>Max Experience is greater than Min Experience</p>");
   		$('input#expValidation').attr('record-exist','yes');
   		$('input#expValidation').attr('record-exist-errorMsg','Max Experience is greater than Min Experience'); 
       }  else{
       	$('#expId').find('p.jquery_form_error_message').remove(); 
       	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
   		$('#expId').find('p.jquery_form_error_message').remove();
       } 
   }
function clearCandidateSearchKeys(){
		
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated");
	    $("#designation").val('').trigger("chosen:updated");
	    $("#noticePeriod").val('').trigger("chosen:updated");
	    $("#minExpYears").val('').trigger("chosen:updated");
	    $("#maxExpYears").val('').trigger("chosen:updated");
	 /*    $("#country").val('').trigger("chosen:updated"); */
	    $("#state").val('').trigger("chosen:updated");
	    $("#citySearch").val('').trigger("chosen:updated");
	    $("#education").val('').trigger("chosen:updated");
	    $("#excludeEducation").val('').trigger("chosen:updated");
	    $("#certification").val('').trigger("chosen:updated");
	    $("#employers").val('').trigger("chosen:updated");
	    $("#excludeEmployers").val('').trigger("chosen:updated");
	    $("#applicantType").val('').trigger("chosen:updated");
	    $("#canSkills").val('').trigger("chosen:updated");
	    $("#canVisa").val('').trigger("chosen:updated");
	    $("#canTags").val('').trigger("chosen:updated");
	    $("#primarySkill").val('').trigger("chosen:updated");
	    
	    $("#designationType").val('').trigger("chosen:updated");
	    
	    $('#candidatelist_form').find('p.jquery_form_error_message').remove();
	    
	     $("#anyOfKeyword").empty();
	     $("#allKeywords").empty();
	     $("#excludeKeyWord").empty();
	     $("#designation").empty();
	      $("#minRate").val(''); 
	     
	      
	     $("#currency").val('');
	     $('.selectpicker').selectpicker('refresh');
	     
	     $("#maxRate").val(''); 
	     
	     $("#rateType").val(''); 
	     $('.selectpicker').selectpicker('refresh');
	     
	     $("#minExpYears").val(''); 
	     $("#maxExpYears").val(''); 
	     $('#expId').find('p.jquery_form_error_message').remove();
	     
	     $("#noticePeriod").val(''); 
	     
	     $("#country").val(''); 
	     $("#state").val(''); 
	     $("#citySearch").val(''); 
	     
	     $("#designationType").val(''); 
	     
	     
	     $("#education").val(''); 
	     $("#excludeEducation").val('');
	     $("#certification").val(''); 
	     
	     $("#employers").val(''); 
	     $("#excludeEmployers").val(''); 
	     $("#applicantType").val('');
	     $("#canSkills").val('');
	     $("#canVisa").val('');
	     $("#canTags").val('');
	     $("#primarySkill").val('');
	    
	}
$(function() { 
    //Adding Customized Any KeyWord Title list this method is slead-common.js
	   tagIt("anyOfKeyword", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','Any Keyword');
    
	   //Adding Customized All KeyWord Title list this method is slead-common.js
	   tagIt("allKeywords", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','All Keywords');
	   
	   //Adding Customized Exclude KeyWord Title list this method is slead-common.js
	   tagIt("excludeKeyWord", 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:', 'tagItBykeyForSkills','skills','Exclude Keyword');
	   
	   //Adding Customized Designation list this method is slead-common.js
	   tagIt("designation", 'Press Enter-Key Or Comma(,) Or Tab To Add Designation:', 'tagItBykeyForSkills','skills','Designation');
	   
	  //Adding Customized Certification list this method is slead-common.js
	   tagIt("certification", 'Press Enter-Key Or Comma(,) Or Tab To Add Certification:', 'tagItBykeyForSkills','skills','Certification');
	  
	   //Adding Customized Employers list this method is slead-common.js
	   tagIt("employers", 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:', 'tagItBykeyForSkills','skills','Employers');
	   
	   //Adding Customized ExcludeEmployers list this method is slead-common.js
	   tagIt("excludeEmployers", 'Press Enter-Key Or Comma(,) Or Tab To Add Employer:', 'tagItBykeyForSkills','skills','Exclude Employers');
	   
	   //Adding Customized PrimarySkill list this method is slead-common.js
	   tagIt("primarySkill", 'Press Enter-Key Or Comma(,) Or Tab To Add Peimary Skills:', 'tagItBykeyForSkills','skills','Primary Skill');
	   
	 //Adding Customized Skill list this method is slead-common.js
	   tagIt("canSkills", 'Press Enter-Key Or Comma(,) Or Tab To Add Skills:', 'tagItBykeyForSkills','skills','Skills');
	 
	   //Adding Customized Visa list this method is slead-common.js
	   tagIt("canVisa", 'Press Enter-Key Or Comma(,) Or Tab To Add Visa:', 'tagItBykeyForSkills','skills','Visa');
	   
	   tagIt("canTags", 'Press Enter-Key Or Comma(,) Or Tab To Add Tags:', 'tagItBykeyForSkills','skills','Visa');
	   
	   //Adding Customized Visa list this method is slead-common.js
	   tagIt("education", 'Press Enter-Key Or Comma(,) Or Tab To Add Education:', 'tagItBykeyForSkills','skills','Education');
	   
	   //Adding Customized Visa list this method is slead-common.js
	   tagIt("excludeEducation", 'Press Enter-Key Or Comma(,) Or Tab To Add Exclude Education:', 'tagItBykeyForSkills','skills','Exclude Education');
	 
	   
	   
});

function getStates(){
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select state---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#citySearch').empty();
	            $('#citySearch').append('<option value="">---select city---</option>');
	            $('#citySearch').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select state---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#citySearch').empty();
    	$('#citySearch').append('<option value="">---select city---</option>');
        $('#citySearch').trigger("chosen:updated");
	}
}

function getCities(){
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#citySearch').empty();
	        	$('#citySearch').append('<option value="">---select city---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#citySearch').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#citySearch').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#citySearch').empty();
    	$('#citySearch').append('<option value="">---select city---</option>');
    	$('#citySearch').trigger("chosen:updated");
	}
}

$("#country").on('change', function (e) {
	   getStates();
});
$("#state").on('change', function (e) {
	   getCities();
});

// here call experience validation
$("#maxExpYears").on('change', function (e) {
	   minMaxExperience();
});

$("#minExpYears").on('change', function (e) {
	   minMaxExperience();
});
var isAssociate = 'N';
var candidateIDsArr=[];
var _csrfParam = '${_csrf.parameterName}';
var _csrfToken = '${_csrf.token}';
function resetCandidateFilter(){
	clearCandidateSearchKeys();
	$("ul#TypeFilter > li input:radio").prop("checked",false);
	$("ul#AllTypeFilter > li input:radio").prop("checked",false);
	$("ul#UpdatedFilter > li input:radio").prop("checked",false);
	$("ul#employmentFilter > li input:checkbox").prop("checked",false);
	$("ul#availabilityFilter > li input:checkbox").prop("checked",false);
	$("ul#skillsUL > li input:checkbox").prop("checked",false);
	$("ul#candidateTypeFilter > li input:checkbox").prop("checked",false);
	$("ul#createdByTypeFilter > li input:radio").prop("checked",false);
	
	
	$("#dataTableSearch").val('');
	document.getElementById("candidateTypeAll").checked = true;
	document.getElementById("candidateAll").checked = true;
	document.getElementById("candidateCheck").checked = true;
//	document.getElementById("myCandidatesRadio").checked = true;
	$("#globalMarketSearh").val("");
	$("#candidateLocation").val("");
	//candidateList('gMarketPlace');
	getGloblaMarketPlaceCandidates('listModel');
}
function refreshPage(){
	resetCandidateFilter(); // refresh the page.
}
</script>
<script> 
function disableOnClick(link) {
  link.onclick = function(event) {
     event.preventDefault();
  }
}   
</script>