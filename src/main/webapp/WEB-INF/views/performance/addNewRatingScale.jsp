<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<section class="content-header clearfix">
     <div class="pull-left"><h2>Add Rating Scale</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration'/>">Configuration</a> &raquo; <a onclick="performanceManagement.performanceRatingScaleList();">Rating Scale List</a> &raquo; Add Rating Scale</div>
  </section>

    
 <div class="project_wrap clearfix">
<form:form id="ratingScale_form"  name="ratingScale_form" method="post">
    <div class="clearfix newAddWidth">
    <div class="form-horizontal mt myForm">
    <input type="hidden" name="ratingScaleId" id="ratingScaleId" /> 
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt"> 
    <div class="form-group" id ="ratingScaleNameDiv"> 
        <label class="col-md-3">Rating Name</label>
        <div class="clearfix">
        <div class="col-md-9"><input type="text" class="form-control" id="ratingScaleName" name="ratingName" onblur="performanceManagement.validateRatingScaleName();" field-name="Rating Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-250"></div>
    </div>
          </div>
    <div class="form-group" id ="ratingShortCodeDiv">
        <label class="col-md-3">Short code</label>
        <div class="col-md-9"><input type="text" class="form-control" id="ratingShortCode" name="shortCode"  onblur="performanceManagement.validateRatingShortCode();" field-name="Short Code " data-validation="required validate_Space validate_AlphaNumber validate_length length1-5" ></div>
    </div>
    
    
    <div class="form-group">
        <label class="col-md-3">Level</label>
        <div class="col-md-9">
            <select class="chosen-select form-control"
            name="ratingLevelId" id="performanceScaleLevel" field-name="Level" data-validation="required">
                
            </select>             
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-3" id="ratingScaleValueDiv">Value</label>
        <div class="col-md-9">
         	<input type="text" class="form-control" id="ratingScaleValue" name="ratingScaleValue" field-name="Value" onblur="performanceManagement.validateRatingScaleValue();" data-validation="required validate_Space validate_int" />
         	<div class="row" id="ratingScaleValueId">
                  <input type="hidden" name="ratingScaleValueValidation" id="ratingScaleValueValidation">
            </div>
        </div>
    </div>       
    </div>
        
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
				<div class="clearfix">
						<label class="col-md-12"><span class="pull-left mr">Description</span>
						</label>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12  txtEditor_p">
							<textarea id="txtEditor" name="description"   value="" class="form-control newContrtextarea"  data-validation-optional="true validate_length length1-5000"></textarea>
						</div>
				</div>
	</div> 
			
						
    </div>

    </div>
                
    <div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right mr">
			<button type="button" class="slBtn md colorBtn" id="savePerformanceRatingScale" onclick="performanceManagement.savePerformanceRatingScale();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="performanceManagement.performanceRatingScaleList();" >Cancel</button>
		</div>
	</div>
	</div>
</form:form>
</div>
	
