<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<div class="wraper"> 
  
  <!-- Content Area -->
  <section class="content-wraper">
  <div id="popUpDeleteCart"></div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Profie Summary</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo;<a href="<c:url value='${appPath}/globalMarketplacePage'/>"> Global Marketplace List</a> &raquo; Profie Summary</div>
    </section>
    <div class="clearfix">
    	<div class="clerfix row mt10">
			<div class="col-md-8">
				<div class="clearfix" style="background-color: #f5f5f5; border: 1px solid #ddd;">
				<div class="clearfix"></div>
				<div class="media">
				<div class="clearfix">
					<div class="col-md-9">
						<div class="media">
					  <div class="media-left">
						<img onerror="predefineProfileImage(this);" src="${marketplaceDTO.imageUrl}" class="media-object" alt="Image" style="width: 100px;">
					  </div>
					  	<div class="media-body">
						<h4 class="media-heading mb5"><a>${marketplaceDTO.fullName != null? marketplaceDTO.fullName: "---"}</a></h4>
						<p class="text-muted">${marketplaceDTO.location != null? marketplaceDTO.location: "---"}</p>
							<h4><span class="label label-primary">Price &nbsp;&nbsp;&nbsp;  ${marketplaceDTO.marketRateCurrencey != null? marketplaceDTO.marketRateCurrencey: "---"} ${marketplaceDTO.marketPrice != null? marketplaceDTO.marketPrice: "---"}</span></h4>
							</div>
						</div>
					</div>
					<div class="col-md-3 text-center">
					<div class="text-center">
						<div class="single-chart mt" title="Profile Updated	">
							<svg viewBox="0 0 36 36" class="circular-chart orange">
							  <path class="circle-bg" d="M18 2.0845
								  a 15.9155 15.9155 0 0 1 0 31.831
								  a 15.9155 15.9155 0 0 1 0 -31.831" />
							  <path class="circle"
								stroke-dasharray="${marketplaceDTO.profilePercentage != null? marketplaceDTO.profilePercentage: '0'}, 100"
								d="M18 2.0845
								  a 15.9155 15.9155 0 0 1 0 31.831
								  a 15.9155 15.9155 0 0 1 0 -31.831" />
							  <text x="18" y="20.35" class="percentage">${marketplaceDTO.profilePercentage != null? marketplaceDTO.profilePercentage: "0"}%</text>
							</svg>
						</div>
						<div id="deleteCandidateFromCartDiv">
						<c:if test="${marketplaceDTO.marketCartStatus == 'In_Cart'}">
						<a class="removeSelected" title="Remove Selected" onclick="deleteCandidateMarketplaceCart('${marketplaceDTO.fullName == null ? '---' : marketplaceDTO.fullName}','${marketplaceDTO.marketplaceUUID}');"><i class="fa fa-trash-o"></i></a>
						</c:if>
						</div>
						<div class="clearfix mt" id="addCandidateMarketToCartDiv">
						<c:if test="${marketplaceDTO.marketCartStatus == null}"> 
						<a type="button" class="anchorHighlight" onclick="addCandidateToMarketCart('${marketplaceDTO.fullName == null ? '---' : marketplaceDTO.fullName}','${marketplaceDTO.marketplaceUUID}')">Add</a>
						</c:if>
						<c:if test="${marketplaceDTO.marketCartStatus == 'In_Cart'}">
						<a type="button" class="anchorHighlight">Added</a>
						</c:if>
						</div>
					</div>
					</div>
				</div>
					<div class="clearfix mt"></div>
				<div style="background-color:#fff; padding:15px 15px; border-top:1px solid #ddd;">
					<div class="row">
					<div class="col-md-12">
						<div class="clearfix">
						<h4 class="margin-top0">${marketplaceDTO.designation != null? marketplaceDTO.designation: "---"}</h4>
						<!-- <p class="text-muted fontSmall">To be able to apply my knowledge and skills as Fullstack Developer.</p> -->
						<p class="lineHeight mt">${marketplaceDTO.summary != null? marketplaceDTO.summary: "---"} </p>
						</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-md-12">
							<h4>Skills</h4>
							<c:choose>
							    <c:when test="${marketplaceDTO.skillsName == null}">
							        ---
							    </c:when>
							    <c:otherwise>
							        <ul class="list-unstyled list-inline expandible">
							        <c:set var="count" value="0" scope="page" />
							        <c:forEach items="${marketplaceDTO.skills}" var="skillsObj">
							        	 <c:if test="${count < 6}">
									    	<li>${skillsObj},</li>
									    </c:if>
									    <c:set var="count" value="${count + 1}" scope="page"/>
									    <c:if test="${count == 6}">
									    	<li title="${marketplaceDTO.skillsName}">more...</li>
									    </c:if>
									</c:forEach>
								    </ul>
							    </c:otherwise>
							</c:choose>
						</div>
						
						
						<div class="col-md-12">
						<ul class="list-unstyled candidateInfo clearfix list-inline">
								<li>
									<div class="clearfix rateProfile"><span class="paddingRight">${marketplaceDTO.currentRateCurrency != null? marketplaceDTO.currentRateCurrency: "---"}</span>${marketplaceDTO.currentRate != null? marketplaceDTO.currentRate: "---"}/${marketplaceDTO.currentRateType != null? marketplaceDTO.currentRateType: "---"}</div>
									<div class="text-muted">Current</div>
								</li>
								<li>
									<div class="clearfix rateProfile"><span class="paddingRight">${marketplaceDTO.expectedRateCurrencey != null? marketplaceDTO.expectedRateCurrencey: "---"}</span>${marketplaceDTO.expectedRate != null? marketplaceDTO.expectedRate: "---"}/${marketplaceDTO.expectedRateType != null? marketplaceDTO.expectedRateType: "---"}</div>
									<div class="text-muted">Expected</div>
								</li>
								<li>
									<div class="clearfix rateProfile">
										${marketplaceDTO.experienceYears != null? marketplaceDTO.experienceYears: "---"}.${marketplaceDTO.experienceMonths != null? marketplaceDTO.experienceMonths: "---"} <span class="small text-muted padding_left">(yrs)</span>
									</div>
									<div class="text-muted">Experience </div>
								</li>
						</ul>
					</div>
				</div>
					</div>
				</div>
				</div>
				
				<div class="clearfix"></div>
				<div class="profileDetails mt">
					<h5>Employers</h5>
					<div class="clearfix"></div>
					<c:choose>
						<c:when test="${marketplaceDTO.candidateEmployers == null}">
							---
						</c:when>
							<c:otherwise>
							    <c:forEach items="${marketplaceDTO.candidateEmployers}" var="employerObj">
							      	<div class="profileDetails-Widget-inner">
										<div class="row">
											<div class="col-md-12">
												<div class="media">
									  <div class="media-left">
										<img src="/resources/images/professionalExperinece.svg" class="media-object" alt="Image" style="width: 50px;">
									  </div>
									  	<div class="media-body">
											<div class="row">
												<div class="col-md-10">
											<h4 class="media-heading mb5">${employerObj.designation != null? employerObj.designation: "---"}</h4>
											<p class="text-muted">${employerObj.name != null? employerObj.name: "---"}</p>
											
											<p class="text-muted mb0">${employerObj.startMonth != null? employerObj.startMonth: "---"} ${employerObj.startYear != null? employerObj.startYear: "---"} – ${employerObj.endMonth != null? employerObj.endMonth: "---"} ${employerObj.endYear != null? employerObj.endYear: "---"}</p>
													</div>
												<div class="col-md-2 text-center">
													<i class="currentCompnay" title="Current Company"></i>
												</div>
											</div>
											</div>
										</div>
											</div>
										</div>
									</div>
								</c:forEach>
							    </c:otherwise>
							</c:choose>
				</div>
				<div class="clearfix"></div>
				<div class="profileDetails mt">
					<h5>Education</h5>
					<div class="clearfix"></div>
					<c:choose>
						<c:when test="${marketplaceDTO.candidateEducation == null}">
							---
						</c:when>
							<c:otherwise>
							 <c:forEach items="${marketplaceDTO.candidateEducation}" var="educationObj">
					<div class="profileDetails-Widget-inner">
						<div class="row">
							<div class="col-md-12">
								<div class="media">
					  <div class="media-left">
						<img src="/resources/images/graduate-cap.svg" class="media-object" alt="Image" style="width: 50px;">
					  </div>
					  	<div class="media-body">
							<div class="row">
								<div class="col-md-10">
							<h4 class="media-heading mb5">${educationObj.name != null? educationObj.name : "---"}</h4>
							<p class="text-muted">${educationObj.institute != null? educationObj.institute : "---"}</p>
							<p class="text-muted mb0">${educationObj.startMonth != null? educationObj.startMonth : "---"} ${educationObj.startYear != null? educationObj.startYear : "---"} – ${educationObj.endMonth != null? educationObj.endMonth : "---"} ${educationObj.endYear != null? educationObj.endYear : "---"}</p>
									</div>
								<div class="col-md-2 text-center">
									<i class="currentCompnay" title="Current Company"></i>
								</div>
							</div>
							</div>
						</div>
							</div>
						</div>
					</div>
					</c:forEach>
					</c:otherwise>
					</c:choose>
				</div>
			
				<div class="clearfix"></div>
				<div class="profileDetails mt">
					<h5>Certifications</h5>
					<div class="clearfix"></div>
					<c:choose>
						<c:when test="${marketplaceDTO.candidateCertifications == null}">
							---
						</c:when>
							<c:otherwise>
							 <c:forEach items="${marketplaceDTO.candidateCertifications}" var="certificationObj">
					<div class="profileDetails-Widget-inner">
						<div class="row">
							<div class="col-md-12">
								<div class="media">
					  <div class="media-left">
						<img src="/resources/images/diploma.svg" class="media-object" alt="Image" style="width:50px; height: 50px;">
					  </div>
					  	<div class="media-body">
							<div class="row">
								<div class="col-md-10">
							<h4 class="media-heading mb5">${certificationObj.name != null? certificationObj.name : "---"}</h4>
							<p class="text-muted">${certificationObj.provider != null? certificationObj.provider : "---"}</p>
							<p class="text-muted mb0">${certificationObj.validFrom != null? certificationObj.validFrom : "---"} -- ${certificationObj.validTo != null? certificationObj.validTo : "---"}</p>
									</div>
								<div class="col-md-2 text-center">
									<i class="currentCompnay" title="Current Company"></i>
								</div>
							</div>
							</div>
						</div>
							</div>
						</div>
					</div>
					</c:forEach>
					</c:otherwise>
					</c:choose>
				</div>
			
			</div>
		<div class="col-md-4">
				<div class="clerfix">
					<h5 class="clearfix small margin-top0">Suggested profiles</h5>
			<c:choose>
						<c:when test="${marketplaceDTO.candidateMarketPlaceDTOs == null && fn:length(marketplaceDTO.candidateMarketPlaceDTOs) eq 0}">
							---
						</c:when>
							<c:otherwise>
							 <c:forEach items="${marketplaceDTO.candidateMarketPlaceDTOs}" var="suggestedProfilesObj">
								<div class="media profileDetails-Widget clearfix">
								<div class="media">
									<div class="media-left">
									<img src="${suggestedProfilesObj.imageUrl}" onerror="predefineProfileImage(this);" class="media-object" style="width:40px; border-radius: 50%;" alt="Image">
									</div>
									<div class="media-body">
									<div class="row">
										<div class="col-md-12">
											<h5 class="margin-top0"><a href="/app/globalCandidateSummary?marketplaceUUID=${suggestedProfilesObj.marketplaceUUID}">${suggestedProfilesObj.fullName}</a></h5>
									<p class="text-muted small mb5">${suggestedProfilesObj.designation}</p>
																		<p class="text-muted small mb5">${suggestedProfilesObj.companyName}</p>
									<p class="text-muted small">${suggestedProfilesObj.location}</p>
					
										</div>
									</div>
									</div>
								</div>
								</div>
							</c:forEach>
						</c:otherwise>
				</c:choose>
			</div>
			</div>
		</div>
	  </div>
  </section>
  </div>
  <script>
  function deleteCandidateMarketplaceCart(candidateName, marketPlaceUUID){ // only stauts updated with reason. active 1 , deactive 0.
		 $(".project_inn_mask").remove();
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='remove_marketCart_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +" <div class='modal-body'><b>Do you want to remove "+candidateName+" from the cart!</b></div>";
			result = result + ' <div id="reasonForTemplateDiv"/>';
			result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteCandidateMarketCart(\""+candidateName+"\",\""+marketPlaceUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelMarketCartPopup();'></b>";
			result = result +"	</div>";
			$("#popUpDeleteCart").empty().append(result);
			$("#remove_marketCart_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}
	function cancelMarketCartPopup(){
		$("#remove_marketCart_pop").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
	}
	
	function deleteCandidateMarketCart(candidateName, candidateMarketUUID){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/deleteCandidateMarketCart?marketUUID="+candidateMarketUUID,
			success : function(result) {
				if (result.response === "AWKWARD") {
	                location.href = "/errorPage";
	            }else if(result.response === "SUCCESS") {
	            	updateMarketplaceCartCountAndPrice();
	            	$("#deleteCandidateFromCartDiv").empty();
					$("#addCandidateMarketToCartDiv").empty().append('<a type="button" id="addCartListModel" onclick="addCandidateToMarketCart(\''+candidateName+'\', \''+candidateMarketUUID+'\')" class="anchorHighlight">Add</a>');
					$("#remove_marketCart_pop").fadeOut(200);
					$(".project_inn_mask").fadeOut(200);
					}else{
		            	 showError("Operation failed, please try again");
		            }
				}
			}); 
		}
	function addCandidateToMarketCart(candidateName, UUID) {
			$.ajax({
	        url: sourceleadUrl + "/addCandidateToMarketCart?id="+UUID,
		        type:'GET',
		        success: function (result) {
		        	 if (result.response === "AWKWARD") {
		                 location.href = "/errorPage";
		             }else if(result.response === "SUCCESS") {
		            	$("#addCandidateMarketToCartDiv").empty().append('<a type="button" id="addedCartCardModel" class="anchorHighlight anchorHighlightActive">Added</a>');
			        	$("#deleteCandidateFromCartDiv").empty().append('<a class="removeSelected mt" title="Remove Selected" onclick="deleteCandidateMarketplaceCart(\''+candidateName+'\', \''+UUID+'\');"><i class="fa fa-trash-o"></i></a>');
			        	updateMarketplaceCartCountAndPrice();
		             }else{
		            	 showError("Operation failed, please try again");
		            }
		        }
		    });
		}
  
  function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
  </script>
<jsp:include page="../masterFooter.jsp" />