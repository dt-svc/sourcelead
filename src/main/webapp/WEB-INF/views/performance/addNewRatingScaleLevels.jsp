
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="popupWrap" id="ratingScaleModalTasktype">
      <div class="popupHead"><span>Add Rating Level</span><i class="closePop pull-right popClose" onclick="performanceManagement.closeRatingScalePopup();"></i></div>
         <div id="ratingScaleLoading"></div>
        <form:form id="ratingLevel_form" name="ratingLevel_form" method="post">
	        <div class="popupContent">
	        <input type="hidden" name="ratingLevelId" id="ratingLevelId"/>
	            <div class="clearfix mb" id="ratingLevelNameDiv">
	                <label class="noPad">Name<span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
			                    <input type="text" id="ratingLevelName" name="name" onblur="performanceManagement.validateRatingLevelName();" class="form-control" placeholder="Name" field-name="Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-250">
	                </div>
	                 
	            </div>
	
	            <div class="clearfix mb" id="ratingLevelDescriptionDiv">
	                <label class="noPad">Description<span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
						<textarea field-name="Description" id="ratingLevelDescription"  name="description" class="form-control" data-validation="required validate_Space validate_length length1-5000" rows="2"></textarea>
	                </div>
	            </div>
	
	        </div>
        
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="savePerformanceRatingLevel" onclick="performanceManagement.savePerformanceRatingLevel();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="performanceManagement.closeRatingScalePopup();" >Cancel</button>
	        </div>
	 </form:form>
</div>


  
  