<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/resources/js/performance/performanceBadge.js"></script>



    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Badges</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="${appPath}/performanceManagementConfiguration">Performance Management</a> &raquo; Badges <span id = "fname"></a> </div>
    </section>
      <div class="timesheet_out" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative clearfix">
            <ul class="pull-right list-unstyled list-inline mb0">
              <li class="noMar"><a class="newAdBtn" title="Add" id="addPop" onclick="addBadgePopup();">+ Add Bage</a></li>
            </ul>
          </div>
          <div class="clearfix row">
            
            <c:if test="${badgesListSize > 0}">
	            <c:forEach var="badges" items="${badgesList}">
	            
		            <div class="col-md-12 mb mt">
					 <div class="media profileDetails-Widget">
		              <div class="media-left rewardImage">
						  <img src="../images/rewards_badges.svg" class="media-object" alt="Image">
						</div>
		              <div class="media-body">
		                <div class="col-md-11">
		                <h4 class="media-heading">
							Best Developer of the Year
						</h4>
		                <p class="text-muted fontSmall mt10">
						Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.	
						</p>
		                  <div class="clearfix"></div>
		               </div>
						  <div class="col-md-1">
							  <ul class="list-unstyled list-inline text-center mt25">
							  	<li><a href="javascript:void(0)" title="Edit"><i class="fa fa-pencil"></i></a></li>
							  	<li><a href="javascript:void(0)" title="Delete"><i class="fa fa-trash"></i></a></li>
							  </ul>
						  </div>
		              </div>
		            </div>				  
					</div>
	            </c:forEach>
            </c:if>
            
            
			<c:if test="${badgesListSize == 0}">
				 <div class='row'> 
					 <div class='col-lg-12'> 
						 <div class='dataTables_empty'>
						 </div>
					 </div>
				 </div>
			</c:if>
			
			
          </div>
        </div>
    </div>
  
  <div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
      <div class="popupHead"><span>Add Badge</span><i class="closePop pull-right popClose" onclick="closeBadgePopup();"></i></div>
         <div id="badgeDetailsLoading"></div>
        <form:form id="performance_badge_form" name="performance_badge_form" method="post">
	        <div class="popupContent">
	            <div class="clearfix mb" id="badgeNameDiv">
	                <label class="noPad">Badge Name <span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
			                    <input type="text" name="name" class="form-control" placeholder="Name" field-name="Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-250">
	                </div>
	            </div>
				<div class="clearfix mb" id="badgeImageDiv">
	                <label class="noPad">Image<span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
						<input type="file" multiple id="uploadBtn" class="upload" field-name="Image" data-validation="required validate_Space validateImg ">
	        		</div>
	        		 <label id="newUploadFile"></label> 
					 <input type="text" hidden readonly id="dataFilePath" value="" >
	                </div>
	
	            <div class="clearfix mb" id="badgeDescriptionDiv">
	                <label class="noPad">Description<span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
						<textarea field-name="Description" name="description" class="form-control" data-validation="required validate_Space" rows="2"></textarea>
	                </div>
	            </div>
	
	        </div>
        
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="savePerformanceBudget" onclick="performanceBadge.savePerformanceBadgeDetails();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="closeBadgePopup();" >Cancel</button>
	        </div>
	 </form:form>
</div>


<script type="text/javascript">
document.getElementById("uploadBtn").onchange = function () {
	$('#newUploadFile').text($(this).val().replace(/C:\\fakepath\\/i, ''));
	   fileUpload("uploadBtn", "badgeImages", "performance_badge_form");
};
</script>
  
  
  <script>
  function addBadgePopup(){
	  $(".mask, #modalTasktype").fadeIn();
  }
  
  function closeBadgePopup(){
	  $(".mask, #modalTasktype").fadeOut();
	  $('#badgeNameDiv').find('p.jquery_form_error_message').remove();
	  $('#badgeImageDiv').find('p.jquery_form_error_message').remove();
	  $('#badgeDescriptionDiv').find('p.jquery_form_error_message').remove();
  }
  </script>
  
  