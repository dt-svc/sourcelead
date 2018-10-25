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
    <div class="clearfix">
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
			  <div id="listView">
			  
			  
				  <!-- <div class="mt"></div>
            	<div class="media profileDetails-Widget positionRelative">
					<a class="removeSelected mt" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
              <div class="media-left grid-prfileImg pull-left"> <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix"></div>
              </div>
              <div class="media-body">
                <div class="col-md-9">
                  <h4 class="media-heading mb10"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9k+<small class="text-muted paddingLeft">earned</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt5 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
					
                </div>
                <div class="col-md-3 text-center">
									  <div class="clearfix mt25 textBold text-center">
						<label class="text-muted mr">Rate</label>
						<label class="jobSucces_list clearfix"><a><i class="fa fa-dollar mr5"></i>2500</a></label>
				  </div>
                <div class="clearfix mt text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
                </div>
              </div>
            </div> -->
            
            <!--  <div class="mt"></div>
            	<div class="media profileDetails-Widget positionRelative">
					<a class="removeSelected mt" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
              <div class="media-left grid-prfileImg pull-left"> <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix"></div>
              </div>
              <div class="media-body">
                <div class="col-md-9">
                  <h4 class="media-heading mb10"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9k+<small class="text-muted paddingLeft">earned</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt5 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
					
                </div>
                <div class="col-md-3 text-center">
									  <div class="clearfix mt25 textBold text-center">
						<label class="text-muted mr">Rate</label>
						<label class="jobSucces_list clearfix"><a><i class="fa fa-dollar mr5"></i>2500</a></label>
				  </div>
                <div class="clearfix mt text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
                </div>
              </div>
            </div> -->
            
			  </div>
			  <!-- Grid Model end -->
			  
			  <div id="cardView" style="display: none;" class="clearfix row">
				  <!-- <div class="clearfix mt"></div> -->
				  <!-- <div class="col-md-4 mb">
						 <div class="media profileDetails-Widget">
              <div class="media-left prfileImg pull-left">
				  <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix mt5 textBold text-center">
						<div class="text-muted">Rate</div>
						<div class="jobSucces_list clearfix mt2"><a><i class="fa fa-dollar mr5"></i>2500</a></div>
				  </div>
				  <div class="clearfix mb"></div>
				<div class="clearfix text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
				</div>
              <div class="media-body">
				  <a class="removeSelected" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
                <div class="col-md-12 padding_right">
                  <h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9<small class="text-muted paddingLeft">/ K+</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt10 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
                </div>
              </div>
            </div>				  
				  </div><div class="col-md-4 mb">
						 <div class="media profileDetails-Widget">
              <div class="media-left prfileImg">
				  <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix mt5 textBold text-center">
						<div class="text-muted">Rate</div>
						<div class="jobSucces_list clearfix mt2"><a><i class="fa fa-dollar mr5"></i>2500</a></div>
				  </div>
				  <div class="clearfix mb"></div>
				<div class="clearfix text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
				</div>
              <div class="media-body">
				  <a class="removeSelected" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
                <div class="col-md-12 padding_right">
                  <h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9<small class="text-muted paddingLeft">/ K+</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt10 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
                </div>
              </div>
            </div>				  
				  </div><div class="col-md-4 mb">
						 <div class="media profileDetails-Widget">
              <div class="media-left prfileImg">
				  <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix mt5 textBold text-center">
						<div class="text-muted">Rate</div>
						<div class="jobSucces_list clearfix mt2"><a><i class="fa fa-dollar mr5"></i>2500</a></div>
				  </div>
				  <div class="clearfix mb"></div>
				<div class="clearfix text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
				</div>
              <div class="media-body">
				  <a class="removeSelected" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
                <div class="col-md-12 padding_right">
                  <h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9<small class="text-muted paddingLeft">/ K+</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt10 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
                </div>
              </div>
            </div>				  
				  </div><div class="col-md-4 mb">
						 <div class="media profileDetails-Widget">
              <div class="media-left prfileImg">
				  <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix mt5 textBold text-center">
						<div class="text-muted">Rate</div>
						<div class="jobSucces_list clearfix mt2"><a><i class="fa fa-dollar mr5"></i>2500</a></div>
				  </div>
				  <div class="clearfix mb"></div>
				<div class="clearfix text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
				</div>
              <div class="media-body">
				  <a class="removeSelected" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
                <div class="col-md-12 padding_right">
                  <h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9<small class="text-muted paddingLeft">/ K+</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt10 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
                </div>
              </div>
            </div>				  
				  </div><div class="col-md-4 mb">
						 <div class="media profileDetails-Widget">
              <div class="media-left prfileImg">
				  <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix mt5 textBold text-center">
						<div class="text-muted">Rate</div>
						<div class="jobSucces_list clearfix mt2"><a><i class="fa fa-dollar mr5"></i>2500</a></div>
				  </div>
				  <div class="clearfix mb"></div>
				<div class="clearfix text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
				</div>
              <div class="media-body">
				  <a class="removeSelected" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
                <div class="col-md-12 padding_right">
                  <h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9<small class="text-muted paddingLeft">/ K+</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt10 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
                </div>
              </div>
            </div>				  
				  </div><div class="col-md-4 mb">
						 <div class="media profileDetails-Widget">
              <div class="media-left prfileImg">
				  <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix mt5 textBold text-center">
						<div class="text-muted">Rate</div>
						<div class="jobSucces_list clearfix mt2"><a><i class="fa fa-dollar mr5"></i>2500</a></div>
				  </div>
				  <div class="clearfix mb"></div>
				<div class="clearfix text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
				</div>
              <div class="media-body">
				  <a class="removeSelected" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
                <div class="col-md-12 padding_right">
                  <h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9<small class="text-muted paddingLeft">/ K+</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt10 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
                </div>
              </div>
            </div>				  
				  </div><div class="col-md-4 mb">
						 <div class="media profileDetails-Widget">
              <div class="media-left prfileImg">
				  <img src="images/24.jpg" class="media-object" alt="Image">
				  <div class="clearfix mt5 textBold text-center">
						<div class="text-muted">Rate</div>
						<div class="jobSucces_list clearfix mt2"><a><i class="fa fa-dollar mr5"></i>2500</a></div>
				  </div>
				  <div class="clearfix mb"></div>
				<div class="clearfix text-center"> <a type="button" class="anchorHighlight">Add</a> </div>
				</div>
              <div class="media-body">
				  <a class="removeSelected" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
                <div class="col-md-12 padding_right">
                  <h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>
                  <h5 class="text-muted mb5">Software Development Engineer</h5>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline mt10">
                    <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>
                    <li class="mr"><span class="paddingRight">$</span>9<small class="text-muted paddingLeft">/ K+</small></li>
                    <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>
                  </ul>
                  <div class="clearfix"></div>
                  <ul class="list-unstyled list-inline profileSkills mt10 mb0">
                    <li>React.Js</li>
                    <li>Redux for JavaScript</li>
                    <li>Node.Js</li>
                    <li>GraphQL</li>
                    <li> <span class="text-success">more</span> </li>
                  </ul>
					<div class="clearfix"></div>
					<h5 class="text-muted small">Profile Updated:</h5>
					<div class="progress mb0">
						<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">
						  10%
						</div>
  					</div>
                </div>
              </div>
            </div>				  
				  </div>
			  </div>
			  </div>
			  </div>
        </div>
      </div>
    </div>
  </section>-->
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
<div class="cand-advsrch-popup advancedsearch-popup" id="filterSearch" style="display: none;">
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
					if(divType=="listModel"){
						for(var i=0;i<object.length;i++){
							result = result + '<div class="mt"></div>';
							result = result + '<div class="media profileDetails-Widget positionRelative">';
							result = result + '	<a class="removeSelected mt" title="Remove Selected"><i class="fa fa-trash-o"></i></a>';
							result = result + '<div class="media-left grid-prfileImg pull-left"><img src="'+object[i].imageUrl+'" onerror="predefineProfileImage(this);" class="media-object" alt="Image">';
							result = result + '  <div class="clearfix"></div>';
							result = result + '</div>';
							result = result + '<div class="media-body">';
							result = result + '<div class="col-md-9">';
							result = result + ' <h4 class="media-heading mb10"><a href="profileSummary.html">'+object[i].fullName+'</a></h4>';
							result = result + ' <h5 class="text-muted mb5">'+object[i].designation+'</h5>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' <ul class="list-unstyled list-inline mt10">';
							result = result + '   <li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>';
							result = result + '  <li class="mr"><span class="paddingRight">$</span>9k+<small class="text-muted paddingLeft">earned</small></li>';
							result = result + '  <li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>'+object[i].location+'</li>';
							result = result + ' </ul>';
							result = result + '<div class="clearfix"></div>';
							result = result + ' <ul class="list-unstyled list-inline profileSkills mt5 mb0">';
							result = result + '   <li>React.Js</li>';
							result = result + ' <li>Redux for JavaScript</li>';
							result = result + '  <li>Node.Js</li>';
							result = result + '  <li>GraphQL</li>';
							result = result + '  <li> <span class="text-success">more</span> </li>';
							result = result + ' </ul>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<h5 class="text-muted small">Profile Updated:</h5>';
							result = result + '<div class="progress mb0">';
							result = result + '	<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:'+object[i].profilePercentage+'%">';
							result = result + object[i].profilePercentage+'%';
							result = result + '</div>';
							result = result + '</div>';
							result = result + ' </div>';
							result = result + ' <div class="col-md-3 text-center">';
							result = result + '					  <div class="clearfix mt25 textBold text-center">';
							result = result + '		<label class="text-muted mr">Rate</label>';
							result = result + '	<label class="jobSucces_list clearfix"><a>'+object[i].marketRateCurrencey+' '+object[i].marketPrice+'</a></label>';
							result = result + '  </div>';
							result = result + '<div class="clearfix mt text-center"> <a type="button" class="anchorHighlight">Add</a> </div>';
							result = result + '  </div>';
							result = result + ' </div>';
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
							result = result + '  <div class="col-md-4 mb">';
							result = result + '<div class="media profileDetails-Widget">';
							result = result + '<div class="media-left prfileImg pull-left">';
							result = result + '<img src="images/24.jpg" onerror="predefineProfileImage(this);" class="media-object" alt="Image">';
							result = result + '<div class="clearfix mt5 textBold text-center">';
							result = result + '<div class="text-muted">Rate</div>';
							result = result + '<div class="jobSucces_list clearfix mt2"><a><i class="fa fa-dollar mr5"></i>2500</a></div>';
							result = result + '</div>';
							result = result + '<div class="clearfix mb"></div>';
							result = result + '<div class="clearfix text-center"> <a type="button" class="anchorHighlight">Add</a> </div>';
							result = result + '</div>';
							result = result + '<div class="media-body">';
							result = result + '<a class="removeSelected" title="Remove Selected"><i class="fa fa-trash-o"></i></a>';
							result = result + '<div class="col-md-12 padding_right">';
							result = result + '<h4 class="media-heading mb0 tbldata-dotdot-me inlineBlock"><a href="profileSummary.html">Rajkumar Santoshi Alapati</a></h4>';
							result = result + '<h5 class="text-muted mb5">Software Development Engineer</h5>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<ul class="list-unstyled list-inline mt10">';
							result = result + '<li class="mr"><span class="paddingRight">$</span>550<small class="text-muted paddingLeft">/ hr</small></li>';
							result = result + '<li class="mr"><span class="paddingRight">$</span>9<small class="text-muted paddingLeft">/ K+</small></li>';
							result = result + '<li class="mr"><span class="paddingRight"><i class="fa fa-map-marker"></i></span>Dallas</li>';
							result = result + '</ul>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<ul class="list-unstyled list-inline profileSkills mt10 mb0">';
							result = result + '<li>React.Js</li>';
							result = result + '<li>Redux for JavaScript</li>';
							result = result + '<li>Node.Js</li>';
							result = result + '<li>GraphQL</li>';
							result = result + '<li> <span class="text-success">more</span> </li>';
							result = result + '</ul>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<h5 class="text-muted small">Profile Updated:</h5>';
							result = result + '<div class="progress mb0">';
							result = result + '<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:10%">';
							result = result + ' 10%';
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
				//alert("success");
				//alert(result.length);
			}
		});
}
function predefineProfileImage(imageId){
	$(imageId).attr('src','/resources/images/userprofileImg.jpg');
}
</script>
 <!--  <script type="text/javascript">
	$(document).ready(function () {
		getDynamicGlobalMarketCandidateList(null);
	   
	});
	function getDynamicGlobalMarketCandidateList() {
		
	     var result = "";
	     result = result + '<table id="dynamicGlobalMarketCandidateListTbl">';
	     result = result + '<thead>';
	     result = result + '<tr>';
	     result = result + '<th>Name</th>';
	     result = result + '<th>Location</th>';
	     result = result + '<th>Primary Skills</th>';
	     result = result + '<th>Current CTC</th>';
	     result = result + '<th>Expected CTC</th>';
	     result = result + '<th>Profile %</th>';
	     result = result + '<th>Action</th>';
	     result = result + '</tr>';
	     result = result + '</thead>';

	     result = result + '</table>';

	     $("#dynamicGlobalMarketCandidateViewDiv").html(result);
	     
         var sourceleadUrl = '${sourceleadUrl}';
	     
	     var searchValue = $("#globalMarketSearh").val();
	     searchValue = searchValue ? searchValue : "";
	     
	     var url ="";
	     url = sourceleadUrl + "/getGlobalMarketplaceList?searchValue="+searchValue; 
	     
	     dynamicGlobalMarketCandidateListPagination(url);
	 }
	 //var count = 0;
	 function dynamicGlobalMarketCandidateListPagination(url) {
		 var colIndex = 0;
	    $('#dynamicGlobalMarketCandidateListTbl').DataTable({
	         "processing": false,
	         "serverSide": true,
	         "searching": false,
	         "ajax": $.fn.dataTable.pipeline({
	             url: url,
	             method: 'POST',
	             pages: 10
	         }), 
	         "columns": [
	             {"data": "Name"},
	             {"data": "Location"},
	             {"data": "Primary Skills"},
	             {"data": "Current CTC"},
	             {"data": "Expected CTC"},
	             {"data": "Action"}
	         ],
	         "columnDefs": [
	             {
	                 "targets": colIndex++,
	                 "data": "fullName", 
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.fullName !== null && opt.fullName !== '' && opt.fullName !== 'undefined') {
	                    	    result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.fullName +"'>" + opt.fullName + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "location",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.location !== null && opt.location !== '' && opt.location !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.location+"'>" + opt.location + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result; 
	                 }
	             },
	      
	             {
	                 "targets": colIndex++,
	                 "data": "skillsName",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.skillsName !== null && opt.skillsName !== '' && opt.skillsName !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.skillsName+"'>" + opt.skillsName + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "currentRate",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.currentRate !== null && opt.currentRate !== '' && opt.currentRate !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.currentRate+"'>" + opt.currentRate + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "expectedRate",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.expectedRate !== null && opt.expectedRate !== '' && opt.expectedRate !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.expectedRate+"'>" + opt.expectedRate + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "profilePercentage",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.profilePercentage !== null && opt.profilePercentage !== '' && opt.profilePercentage !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.profilePercentage+"%'>" + opt.profilePercentage + "%</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	               /*   "data": "action", */
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = 'Add To Marketplace'><a onclick='addCandidateToMarketCart(\""+opt.id+"\")'>Add</a></div></td>";
	                     return result;
	                 }
	             }
	         ],
	         "scrollCollapse": false,
	         "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	         "aaSorting": [],
	         "bLengthChange": true,
	         "bDestroy": true,
	         "ordering": true,
	         "initComplete": function (settings, json) {
	         },
	         "oLanguage": {
	             "sSearch": "",
	             "sZeroRecords": "", "sEmptyTable": "",
	             "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	         }
	     });
	    
	     $("#dynamicGlobalMarketCandidateListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	      
	     $("#globalMarketSearh").keyup(function (e) {
	         if (e.which === 13) {
	        	 getDynamicGlobalMarketCandidateList( $("#globalMarketSearh").val());
	         }
	     });
	     
	 }	
	
</script> -->
<script type="text/javascript">

function addCandidateToMarketCart(id) {
   $.ajax({
        url: sourceleadUrl + "/addCandidateToMarketCart?id="+id,
        type: "GET",
        data: id,
        success: function (response) {
        	if(response=="success"){
          	getDynamicGlobalMarketCandidateList();
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
	//Advanced Search
	function closePop(){
		$(".advancedsearch-mask, #profileAdSearch").fadeOut();
		$(".advancedsearch-mask, #filterSearch").fadeOut();
	}
	function openPop(){
	$("#profileAdSearch, .advancedsearch-mask").fadeIn();
	}
function filterPop() {
	$(".advancedsearch-mask, #filterSearch").fadeIn();
}
/* $("#cardModel").click(function(){
  	$("#listView").hide();
	$("#cardView").show();
	$(this).find("i").toggleClass("text-muted text-primary");
	$("#listModel").find("i").toggleClass("text-primary text-muted");
  }); */
/* $("#listModel").click(function(){
  	$("#listView").show();
	$("#cardView").hide();
	$(this).find("i").addClass("text-muted text-primary");
	$("#cardModel").find("i").removeClass("text-primary text-muted");
  }); */
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