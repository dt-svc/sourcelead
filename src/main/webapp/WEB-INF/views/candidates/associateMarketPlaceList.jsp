<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Associate List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/OrgMarketplace'/>">Associate Marektplace </a>&raquo; Associate List</div>
    </section>
    <div class="project_wrap clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
              <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span>
            <ul class="pull-right list-unstyled list-inline">
				<li>
            <a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a>
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table clearfix">
            <table>
              <thead>
                <tr>
                  <th>Associate Name</th>
                  <th>Name of the Company</th>
                  <th>Relocate to</th>
                  <th>No. Items</th>
                  <th>Reason for Relocation</th>
                  <th>Created on</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                <td>
                <div class="mt5 mb5 width215px">
                  <a href="${SourceLeadWeb_atsUrl}/productsList">
                  <div class="pull-left" style="width: 35px; height: 35px;">
                    <img src="../resources/images/userprofile-img.png" class="img-responsive" alt="Images">
                  </div>
                  <div class="pull-left ml10 mt5 tbldata-dotdot-sm inlineBlock text-left" title="Jaden Smith Roy">
                    Jaden Smith Roy
                  </div>
                  </a>
                  </div>
                </td>
                <td>
                    <div class="tbldata-dotdot-sm" title="Business Intelli Solutions">Business Intelli Solutions</div>
                    </td>
                <td>
                <div class="tbldata-dotdot-sm" title="Banglore">Banglore</div>
                </td>
                <td>
                <div class="tblminwidth-75" title="20">20</div>
                </td>
                <td>
					<div class="tbldata-dotdot-me" title="Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.">
						  Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.
					</div>
				</td>
                  <td><div class="tblminwidth-75" title="23-7-2019">23-7-2019</div></td>
                  <td>
                  <div class="width90px">
                  	<ul class="list-inline list-unstyled">
                  		<li><a href="editworkflow.html"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                  		<li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                  	</ul>	
                  </div>
                  </td>
                </tr>

                <tr>
                    <td>
                    <div class="mt5 mb5 width215px">
                      <a href="${SourceLeadWeb_atsUrl}/productsList">
                      <div class="pull-left" style="width: 35px; height: 35px;">
                        <img src="../resources/images/userprofile-img.png" class="img-responsive" alt="Images">
                      </div>
                      <div class="pull-left ml10 mt5 tbldata-dotdot-sm inlineBlock text-left" title="Rajkumar Santoshi Rao">
                        Rajkumar Santoshi Rao
                      </div>
                      </a>
                      </div>
                    </td>
                    <td>
                        <div class="tbldata-dotdot-sm" title="Business Intelli Solutions">Business Intelli Solutions</div>
                        </td>
                    <td>
                    <div class="tbldata-dotdot-sm" title="Banglore">Hyderabad</div>
                    </td>
                    <td>
                    <div class="tblminwidth-75" title="20">10</div>
                    </td>
                    <td>
              <div class="tbldata-dotdot-me" title="Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.">
                  Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.
              </div>
            </td>
                      <td><div class="tblminwidth-75" title="23-7-2019">15-4-2019</div></td>
                      <td>
                      <div class="width90px">
                        <ul class="list-inline list-unstyled">
                          <li><a href="editworkflow.html"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                          <li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                        </ul>	
                      </div>
                      </td>
                    </tr>
             </tbody>
            </table>
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>
  
<div class="advancedsearch-mask" onclick="closeAdsrch()"></div>
  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-medium">
    <h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a> </h5>
    <div class="addInner">
      <div class="row">
        <div class="col-md-6 mb positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myManagersInput" onkeyup="myManagersFunction()" placeholder="Location" title="Type in a managers" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL">
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="1883" type="checkbox">
                <label>Hyderabad</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Pune</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Mumbai</label>
              </li>
             <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Banglore</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Hyderabad</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Visakhapatnam</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Noida</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Delhi NCR</label>
              </li>

            </ul>
          </div>
        </div>
        <div class="col-md-6 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myRolesInput" onkeyup="myRolesFunction()" placeholder="By Date" title="Type in a roles" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="">
              <li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="checkbox">
                <label>This Week</label>
              </li>              <li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="checkbox">
                <label>Month</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>Year</label>
              </li>
                        </ul>
          </div>
        </div>
        
        <div class="clearfix"></div>
      </div>
    </div>
	
	<div class="clearfix">
		<div class="col-md-12 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
          <h5>Filter by Date <span class="text-danger paddingLeft">*</span></h5>
		  </div>
          <div class="clearfix">
            <ul class="list-unstyled positionRelative filterSearch" id="">
              <li class="positionRelative mb">
                <div class="row">
                <div class="col-md-6">
                	<input type="text" value="" class="form-control calendar" placeholder="From Date" id="frmDate">
                </div>
                <div class="col-md-6">
                	<input type="text" value="" class="form-control calendar" placeholder="To Date" id="toDate">
				</div>
             </div>
              </li>
            </ul>
          </div>
        </div>
	</div>
	
	
<script type="text/javascript">

  // Close Advanced Search
	function closePop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").hide();
	}
	function openPop(){
	$(".advancedsearch-mask").show();
	$("div.cand-advsrch-popup").toggle();
	}
//Date Picker
$( function(){
	$("#frmDate").datepicker();
	$("#toDate").datepicker();
 });
</script>


 <jsp:include page="../masterFooter.jsp" />	