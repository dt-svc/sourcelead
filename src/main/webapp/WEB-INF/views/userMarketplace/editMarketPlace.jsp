<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
$(document).ready(function() {
	setPriorityValue();
});
/* $("#priority > option").each(function() {
console.log(priorityValue);
if(this.value == priorityValue){
	 $("#priority").val(priorityValue);
}

}); */
function setPriorityValue(){
	var priorityValue = '${marketPlaceDto.priority}';
	if(priorityValue != null && priorityValue != ""){
		console.log(priorityValue);
		  $("#priority").val("Immediate");
		/* document.getElementById(""+priorityValue).selected = "true"; */
	}
}
</script>
<section class="content-wraper">
    <section class="content-header clearfix">
    </section>
<form:form id="edit_form"  name="edit_form">
<%--  <c:if test="${not empty requirementWorkFlowStagesDTO}"> --%>

 <div class="col-md-4 minHeight">
                <label>Product Type</label>
                <div class="clearfix">
                  <select class="chosen-select" name="category" id="category" field-name="Product Category" style="display: none;">
                    <option value="">Select</option>
                    <option value="Home" ${marketPlaceDto.category  == "Home" ? 'selected' : ''} >Home</option>
                    <option value="Vehicles" ${marketPlaceDto.category  == "Vehicles" ? 'selected' : ''} >Vehicles</option>
                    <option value="Home appliance" ${marketPlaceDto.category  == "Home appliance" ? 'selected' : ''} >Home appliance</option>
                    <option value="Utensils" ${marketPlaceDto.category  == "Utensils" ? 'selected' : ''} >Utensils</option>
                    <option value="Electronics/gadgets" ${marketPlaceDto.category  == "Electronics/gadgets" ? 'selected' : ''} >Electronics/gadgets</option>
                    <option value="Furnitures" ${marketPlaceDto.category  == "Furnitures" ? 'selected' : ''} >Furnitures</option>
                     <option value="Other" ${marketPlaceDto.category  == "Other" ? 'selected' : ''} >Other</option>
                  </select><div class="chosen-container chosen-container-single" style="width: 100%;" title=""><div class="chosen-drop"><div class="chosen-search"><input type="text"  autocomplete="off"></div><ul class="chosen-results"></ul></div></div>
                </div>
        </div> 

  
        <div class="col-md-4 minHeight">
            <label class="clearfix">Manufacturer</label>
            <div class="clearfix">
              <input type="text" id="manufacturerName"  name="manufacturerName" class="form-control" value="${marketPlaceDto.manufacturerName}">
            </div>
            <div class="clearfix"></div>
    </div>
   
    <div class="col-md-4 minHeight">
      <label class="clearfix">Date of Manufactured</label>
      <div class="clearfix input-group errorInptgroup">
        <input type="text" name="manufacturerDate" id="manufacturerDate" value="${marketPlaceDto.manufacturerDate}" class="form-control calendar">
         <span class="input-group-addon" onClick="clearExpiryDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
      </div>
      <div class="clearfix"></div>
</div>

 <div class="col-md-4 minHeight">
        <label class="clearfix">Price</label>
        <div class="clearfix row">
            <div class="col-md-9">
          <input type="text" id="price" name="price" value="${marketPlaceDto.price}" class="form-control" data-validation="validate_length length1-20 validate_phone"
							field-name="Price"	data-validation-optional="true" value=" ">
        </div>
          <div class="col-md-3">
       <select class="form-control sel" name ="currencySpecification"  value="${marketPlaceDto.currencySpecification}" id="currencySpecification" >
                <option selected="" value="0">${marketPlaceDto.currencySpecification}</option>
                <option>&#x20B9;</option>
                <option>&#36;</option>
                <option>&#163;</option>
                <option>&#162;</option>
                <option>&#65020;</option>
            </select>
            
          </div>
        </div>
        <div class="clearfix"></div>
</div>


    <div class="col-md-4 minHeight">
        <label class="clearfix">Priority</label>
        <div class="clearfix">
         <select class="chosen-select" name="priority" id="priority" field-name="priority" style="display: none;">
                <option value="select">Select  </option>
                <option id= "Immediate" value="Immediate" ${marketPlaceDto.priority  == "Immediate" ? 'selected' : ''}  >Immediate</option>
                <option id= "1 Week" value="1 Week" ${marketPlaceDto.priority  == "1 Week" ? 'selected' : ''} >1 Week</option>
                <option id= "Fortnight" value="Fortnight" ${marketPlaceDto.priority  == "Fortnight" ? 'selected' : ''} >Fortnight</option>
                <option id= "15 Days" value="15 Days" ${marketPlaceDto.priority  == "15 Days" ? 'selected' : ''} >15 Days</option>
                <option id= "1 Month" value="1 Month" ${marketPlaceDto.priority  == "1 Month" ? 'selected' : ''} >1 Month</option>
                <option id= "2 Month(s)" value="2 Month(s)" ${marketPlaceDto.priority  == "2 Month(s)" ? 'selected' : ''} >2 Month(s)</option>
                <option id= "3 Month(s)" value="3 Month(s)" ${marketPlaceDto.priority  == "3 Month(s)" ? 'selected' : ''} >3 Month(s)</option>
              </select>
        </div>
        <div class="clearfix"></div>
</div>

<!-- <div class="col-md-4 minHeight">
    <label class="clearfix">Attach document for the Product<span class="text-danger paddingLeft">*</span></label>
            <div class="input-group col-md-12 clearfix">
              <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
              <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
              </span> </label>
              <input type="text" class="form-control" name="docFile" onclick="addFileUpload()" readonly="" id="uploadFile" placeholder="Upload Document ...">
            </div>
  <div class="clearfix"></div> 
</div>    -->

<div class="col-md-4 minHeight">
    <label class="clearfix">&nbsp;</label>
    <div class="clearfix row">
        <label class="col-md-8 mt5">Negotiate</label>
        <div class="col-md-4">
        <label class="switch">
        <c:choose>
        <c:when test="${marketPlaceDto.isNegotiable eq 'Y'}">
        	<input checked="" id="isNegotiable" name="isNegotiable" value ="Y" onclick="negotiable()" type="checkbox">
        </c:when>
        <c:otherwise>
   		     <input id="isNegotiable" name="isNegotiable" value ="N" onclick="negotiable()" type="checkbox">
        </c:otherwise>
        </c:choose>
            <span class="slider round"></span>
        </label>
      </div>
    </div>
    <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight">
    <label class="clearfix">&nbsp;</label>
    <div class="clearfix row">
        <label class="col-md-8 mt5">Show Contact details <i class="fa fa-info text-primary ml cursorPointer" title="Contact details would be viewable to the buyers"></i></label>
        <div class="col-md-4">
        <label class="switch">
         <c:choose>
        <c:when test="${marketPlaceDto.shareContactDetails eq 'Y'}">
      <input checked="" id="shareContactDetails" name="shareContactDetails" type="checkbox" value ="Y"> 
      </c:when>       
        <c:otherwise>
   		     <input id="shareContactDetails" name="shareContactDetails" value ="N" type="checkbox">
        </c:otherwise>
        </c:choose>
            <span class="slider round"></span>
        </label>
      </div>
    </div>
    <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight">
    <label class="clearfix">&nbsp;</label>
    <div class="clearfix row">
        <label class="col-md-8 mt5">Post Public</label>
        <div class="col-md-4">
        <label class="switch">
       <c:choose>
        <c:when test="${marketPlaceDto.productShareType eq 'Y'}">
         <input checked="" type="checkbox" name="productShareType" value ="Y"  id="productShareType" >
        </c:when>
        <c:otherwise>
         <input type="checkbox" name="productShareType" value ="N"  id="productShareType" >
        </c:otherwise>
        </c:choose>
           
            <span class="slider round"></span>
        </label>
      </div>
    </div>
    <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight">
    <label class="clearfix">Product Description <span class="text-danger paddingLeft">*</span></label>
    <div class="clearfix">
      <input placeholder="Description" class="form-control" name="productDescription" id="productDescription" value="${marketPlaceDto.productDescription}" field-name="Product Description" data-validation="required validate_AlphaNumber validate_length length1-100"  cols="2">
    </div>
  <div class="clearfix"></div> 
</div>

 <!-- <div class="clearfix ">
          <div class="col-md-4 minHeight clearfix">
            <div style="float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ">
	            <input id="file0" type="file" name="uploadPhotos" class="pull-left" onchange="uploadFileButton(0);">
	              <a id="Button0value=" "="" onclick="addProductFileUpload()">
	               <i class="fa fa-plus btn btn-default btn-sm btn-sm2 pull-right"></i>
	             </a>
	         </div>
            <div id="FileUploadContainer"></div>
          </div>  
      </div> -->


  <div class="pull-right mp10">
            <input value="Save" class="btn btn-info" id="EditSave" type="button" onclick="SaveEdits('${marketPlaceDto.uuid}');">
          </div>
</form:form>
</section>
 <script type="text/javascript" src="${SourceLeadWeb_atsUrl}/resources/js/slead-common.js"></script>
 

<script type="text/javascript">
function SaveEdits(uuid){
	alert("dsfsdf")
 var atsUrl ='${SourceLeadWeb_atsUrl}';
 if ($('#edit_form').validate(false, validationSettings)){
 var formData = getFormData("edit_form");
		  $.ajax({
				type : "POST",
				data : formData, 
				url : atsUrl+"/saveEditMarketPlaceDetails?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,
				success : function(dataList) {
					 if (dataList.response === "AWKWARD") {
		      	    location.href = "/errorPage";
		      	} else if (dataList.response === "SUCCESS") {
		      		location.replace(atsUrl+"/redirectToAddproduct");
		      	}
		      		if(dataList.object === "DataMissing") {
							showError("Invalid data");
						}
	            }
				
	            });
}
}
</script>
<script>
$(window).load(function() {
			 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			  var finalDateFormatProduct = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatProduct = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatProduct = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatProduct = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatProduct = 'yy-dd-mm'; 
			  }
		  
		    $( "#manufacturerDate" ).datepicker({
		    	dateFormat : finalDateFormatProduct,
		        changeMonth: true,
		        changeYear: true
		      });
});

function clearExpiryDate(){
	  $("#manufacturerDate").val("");
}; 

</script>


    
 <jsp:include page="../masterFooter.jsp" />	