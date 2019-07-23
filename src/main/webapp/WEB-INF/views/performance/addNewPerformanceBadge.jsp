
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
      <div class="popupHead"><span>Add Badge</span><i class="closePop pull-right popClose" onclick="performanceManagement.closeBadgePopup();"></i></div>
         <div id="badgeDetailsLoading"></div>
        <form:form id="performance_badge_form" name="performance_badge_form" method="post">
	        <div class="popupContent">
	        <input type="hidden" name="badgeId" id="badgeId"/>
	            <div class="clearfix mb" id="badgeNameDiv">
	                <label class="noPad">Badge Name <span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
			                    <input type="text" id="badgeName" name="name" onblur="performanceManagement.validatePerformanceBadgeName()" class="form-control" placeholder="Name" field-name="Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-250">
	                </div>
	                 
	            </div>
				<div class="clearfix mb positionRelative" id="badgeImageDiv">
	                <label class="noPad">Image</label>
	                <div class="clearfix">
						<input type="file" multiple id="uploadBtn" class="upload" field-name="Image" data-validation-optional="true" data-validation="validate_Space validateImg ">
	        		</div>
	        		<!--  <label id="newUploadFile"></label> --> 
					 <input type="text" hidden readonly id="dataFilePath" value="" >
					 
					 <img id="badgeImg" onerror='predefineBadgeImage(this);' style="width: 30px;" src='' class="mediaObject" alt="Image">
	                </div>
	
	            <div class="clearfix mb" id="badgeDescriptionDiv">
	                <label class="noPad">Description<span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
						<textarea field-name="Description" id="badgeDescription"  name="description" class="form-control" data-validation="required validate_Space validate_length length1-5000" rows="2"></textarea>
	                </div>
	            </div>
	
	        </div>
        
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="savePerformanceBadge" onclick="performanceManagement.savePerformanceBadgeDetails();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="performanceManagement.closeBadgePopup();" >Cancel</button>
	        </div>
	 </form:form>
</div>

<script>
	function predefineBadgeImage(imageId){
		$(imageId).attr('src','/resources/images/rewards_badges.svg');
	}
</script>

<script type="text/javascript">
document.getElementById("uploadBtn").onchange = function () {
	   fileUpload("uploadBtn", "badgeImages", "performance_badge_form");
};
</script>
  
  
  
  