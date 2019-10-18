<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
window.onload = function () {
	getmarketPlaceList();
};
</script>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Products</h2>
      </div>
 <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/OrgMarketplace'/>">Associate Marektplace </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/productList'/>">Product List </a>&raquo;Add Product</div>     
    </section>
    
    <div class="content-area content-new clearfix backgroundWhite">
      <div class="minHeightcol6 mt mb clearfix">
<h4 class="col-md-12  text-muted">Add Personal Info</h4>
 <form:form id="personalAndProductInfo_form"  name="personalAndProductInfo_form">

<div class="col-md-4 minHeight">
  <label class="clearfix">Current City</label>
  <div class="clearfix">
    <input type="text" id ="currentCity" name="currentCity" class="form-control">
  </div>
  <div class="clearfix"></div>
</div>
<div class="col-md-4 minHeight">
  <label class="clearfix">Select Region Type </label>
  <div class="clearfix">
    <select class="sel form-control" name="regionType" onchange="relocationHandler();" id="relocationFromtoSelect">
      <option selected="" value="0">Select</option>
      <option value="Relocate">Relocate</option>
      <option value="Other">Other</option>
    </select>
  </div>
  <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight" id="relocationFromto" style="display: none;">
    <div class="clearfix row">
  
  
  </div>
  <div class="clearfix row">
    <div class="col-md-6">
    <label class="clearfix">From</label>
    <div class="clearfix"></div>
	    <div class="input-group errorInptgroup">
		    <input type="text" class="form-control calendar" name="fromDate" id="fromDate">   
		    <span class="input-group-addon" onClick="clearFromDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
	    </div>
    </div>

    <div class="col-md-6">
    <label class="clearfix">To</label>
    <div class="clearfix"></div>
     <div class="input-group errorInptgroup">
	    <input type="text" class="form-control calendar" name="toDate" id="toDate"> 
	    <span class="input-group-addon" onClick="clearToDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
    </div>
    </div>
   
    
  </div>
  <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight">
  <label class="clearfix">Comments<span class="text-danger paddingLeft">*</span></label>
  <div class="clearfix">
   <textarea placeholder="Comments" type="text" id="comment" name="comments" field-name= "comment" data-validation="required validate_AlphaNumber validate_length length1-100" class="form-control" cols="2"></textarea>
 </div>
<div class="clearfix"></div> 
</div>
 
        <div class="clearfix"></div>
        <h4 class="col-md-12 text-muted">Add Product Info</h4>
        <div class="col-md-4 minHeight">
          <label class="clearfix">Product Name</label>
          <div class="clearfix">
            <input type="text" id="productName" name="productName" field-name= "product name"  class="form-control">
          </div>
          <div class="clearfix"></div>
  </div>
        <div class="col-md-4 minHeight">
                <label>Product Type</label>
                <div class="clearfix">
                  <select class="chosen-select" name="category" id="category" field-name="Product Category" style="display: none;">
                    <option selected="" value="0">Select</option>
                    <option>Home</option>
                    <option>Vehicles</option>
                    <option>Home appliance</option>
                      <option>Utensils</option>
                    <option>Electronics/gadgets</option>
                    <option>Furnitures</option>
                     <option>Other</option>
                  </select><div class="chosen-container chosen-container-single" style="width: 100%;" title=""><div class="chosen-drop"><div class="chosen-search"><input type="text"  autocomplete="off"></div><ul class="chosen-results"></ul></div></div>
                </div>
        </div> 
    <div class="col-md-4 minHeight">
            <label class="clearfix">Manufacturer</label>
            <div class="clearfix">
              <input type="text" id="manufacturerName"  name="manufacturerName" class="form-control">
            </div>
            <div class="clearfix"></div>
    </div>

    <div class="col-md-4 minHeight">
      <label class="clearfix">Date of Manufactured</label>
      <div class="clearfix input-group errorInptgroup">
        <input type="text" name="manufacturerDate" id="manufacturerDate" class="form-control calendar">
         <span class="input-group-addon" onClick="clearExpiryDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
      </div>
      <div class="clearfix"></div>
</div>

    <div class="col-md-4 minHeight">
      
      <div class="row">
        <div class="col-md-4">
            <label class="clearfix">Warranty Eligible</label>
            <label class="switch mt5 ml">
                <input type="checkbox" onclick="warrantyHandler();" id="warrantyCheckbox">
                <span class="slider round"></span>
            </label>
        </div>
        <div class="col-md-8">
            <div id="WarrantyEligibile" style="display: none;">
            <label class="clearfix">Warranty Expiry Date</label>
            <div class="clearfix input-group errorInptgroup">
              	<input type="text" class="form-control calendar" id="warrantyExperyDate" name="warrantyEndDate" >
                <span class="input-group-addon" onClick="clearWarrantyExpiryDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
            </div>
            </div>
        </div>
      </div>
    
      
      <div class="clearfix"></div>
</div>
   
    <div class="col-md-4 minHeight">
        <label class="clearfix">Price</label>
        <div class="clearfix row">
            <div class="col-md-9">
          <input type="text" id="price" name="price" value="${price}" class="form-control" data-validation="validate_length length1-20 validate_phone"
							field-name="Price"	data-validation-optional="true" value=" ">
        </div>
          <div class="col-md-3">
       <select class="form-control sel" name ="currencySpecification" id="currencySpecification" >
                <option selected="" value="0">Select</option>
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
         <select class="chosen-select" name="priority" id="priorityy" field-name="Priority" style="display: none;">
         
                <option selected="" value="0">Select</option>
                <option>Immediate</option>
                <option>1 Week</option>
                <option>Fortnight</option>
                <option>Fortnight</option>
                <option>15 Days</option>
                <option>1 Month</option>
                <option>2 Month(s)</option>
                <option>3 Month(s)</option>
              </select>
        </div>
        <div class="clearfix"></div>
</div>
  

   <div class="col-md-4 minHeight">
    <label class="clearfix">Attach document for the Product<span class="text-danger paddingLeft">*</span></label>
            <div class="input-group col-md-12 clearfix">
              <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
              <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
              </span> </label>
              <input type="text" class="form-control" name="docFile" onclick="addFileUpload()" readonly="" id="uploadFile" placeholder="Upload Document ...">
            </div>
  <div class="clearfix"></div> 
</div>   

<div class="col-md-4 minHeight">
    <label class="clearfix">&nbsp;</label>
    <div class="clearfix row">
        <label class="col-md-8 mt5">Negotiate</label>
        <div class="col-md-4">
        <label class="switch">
            <input checked="" id="isNegotiable" name="isNegotiable" value ="Y" onclick="negotiable()" type="checkbox">
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
            <input checked="" id="shareContactDetailss" name="shareContactDetails" type="checkbox" value ="Y" onclick="ContactDetails()">
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
            <input checked="" type="checkbox" name="productShareType" value ="Y" onclick="shareType()" id="productShareType" >
            <span class="slider round"></span>
        </label>
      </div>
    </div>
    <div class="clearfix"></div>
</div>


<div class="col-md-4 minHeight">
    <label class="clearfix">Product Description <span class="text-danger paddingLeft">*</span></label>
    <div class="clearfix">
      <textarea placeholder="Description" class="form-control" id="productDescription" field-name="Product Description" data-validation="required validate_AlphaNumber validate_length length1-100" name="productDescription" cols="2"></textarea>
    </div>
  <div class="clearfix"></div> 
</div>

<div class="clearfix"></div>
    
     <div class="clearfix mt">
        <label class="clearfix col-md-12 mb10">
          Attachments / Upload Photos<span class="text-danger paddingLeft">&nbsp;*</span>
          <small class="text-muted ml">Upload upto 6 photos per item / product</small>
        </label>
        <div class="clearfix ">
          <div class="col-md-4 minHeight clearfix">
            <div style="float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ">
	            <input id="file0" type="file" name="uploadPhotos" class="pull-left" onchange="uploadFileButton(0);">
	              <a id="Button0value=" "="" onclick="addProductFileUpload()">
	               <i class="fa fa-plus btn btn-default btn-sm btn-sm2 pull-right"></i>
	             </a>
	         </div>
            <div id="FileUploadContainer" />
          </div>  
                
      </div>
      
        <div class="clearfix col-md-12">
          <div class="pull-right">
            <input value="Save Product" class="btn btn-info" id="productSave" type="button" onclick="saveProduct();">
            <input type="button" value="Reset" onclick="resetFunction();" class="btn btn-default ml">
          </div>
        </div>
</div>
</form:form>

 <div class="clearfix col-md-12">
  <div class="project_wrap_table datatable_filter" id = "dynamicMarketPlaceList">
    
  </div>
 <nav>
    <ul id="paging" class="pagination">
      <li><a href="#">Previous</a></li>
      <li><a href="#">2</a></li>
      <li><a href="#">3</a></li>
      <li><a href="#">4</a></li>
      <li><a href="#">5</a></li>
      <li class="disabled"><a href="#">6</a></li>
      <li data-page="last"><a href="#">Next</a></li>
    </ul>
    <div class="clearfix"></div>
  </nav> 
</div>

</div>

<div class="clearfix"></div>
<div class="form-footer clearfix mt">
    <div class="col-lg-12">
      <div class="pull-right">
        <input type="button" value="Finish" class="btn btn-primary" id="finish" onclick="finishFunction()">
      </div>
    </div>
  </div>
      </section>
    <script type="text/javascript" src="${SourceLeadWeb_atsUrl}/resources/js/projects.js"></script>
    <script type="text/javascript" src="${SourceLeadWeb_atsUrl}/resources/js/slead-common.js"></script>
  <script>
  function relocationHandler() {
    var relocationID = document.getElementById("relocationFromtoSelect");
    var relocationValue = relocationID.options[relocationID.selectedIndex].value;
      if(relocationValue === "Relocate") {
        document.getElementById("relocationFromto").style.display = "block";
      }else {
        document.getElementById("relocationFromto").style.display = "none";
      }
  }

  function warrantyHandler() {
   let warrantyCheckbox = document.getElementById("warrantyCheckbox");
   if(warrantyCheckbox.checked === true){
      document.getElementById("WarrantyEligibile").style.display="block";
   }else {
      document.getElementById("WarrantyEligibile").style.display="none";
   }
  }
</script> 
 
 <script type="text/javascript">
function saveProduct(){     
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
       /*  var atsUrl='${atsUrl}'; */
        if ($('#personalAndProductInfo_form').validate(false, validationSettings)){
        var formData = getFormData("personalAndProductInfo_form");
			  $.ajax({
					type : "POST",
					data : formData, 
					url : atsUrl+"/saveMarketPlaceDetails?${_csrf.parameterName}=${_csrf.token}",
					
							success : function(dataList) {
		            	
								location.reload(); 
		            	/* if(dataList.response != null){
							console.log("response "+dataList.response);
		            		getmarketPlaceList(json);
							
						}if(dataList.response == null){
							showError("Operation failed, please try again");
						} */
		            	getmarketPlaceList();
		            
		            }
		            });
   }else {
	   return false;
   }
        }
        
        
   function deleteMarketPlaceList(uuid)  { 
	
	   $.ajax({
			type : "POST",
			url : atsUrl+"/deleteMarketPlaceProductByUuid?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,
			 success : function(dataList){
				  location.reload(); 
			 }
   });
   }
   
    function editMarketPlace(){
    	alert("ghj")
	    location.href = "${SourceLeadWeb_atsUrl}/editMarketPlace";
	} 
  </script>

<script>
           function getmarketPlaceList()
           {
					$("#dynamicMarketPlaceList").empty();
					
					var result = "";
				  	var url = "";
				  	result = result +"<table id='marketPlaceList' class='display' cellspacing='0' width='100%'>";
				  	result = result +"<thead>";
				  	result = result +"<tr>"
				  	result = result +"<th>Product ID</th>";
				  	result = result +"<th>Product Type</th>";
				  	result = result +"<th>Manufacturer</th>";
				  	result = result +"<th>Date of Manufactured</th>";  
				 	result = result +"<th>price</th>";   	
				 	result = result +"<th>Priority</th>";  
					result = result +"<th>Warranty Doc.</th>";   	 	
				  	result = result +"<th>Negotiate</th>";
				  	result = result +"<th>Show Contact details</th>"; 
				    result = result +"<th>Post Details</th>";   	
				    result = result +"<th>Product Description</th>";  	 
					result = result +"<th>Product Image</th>"; 
					result = result +"<th>Action</th>"; 
				  	result = result +"</tr>";
				  	result = result +"</thead>";
				  	result = result +"</table>";
				  	$("#dynamicMarketPlaceList").html(result);
				  	getDynamicMarketPlaceList();
				}

  function getDynamicMarketPlaceList()
  {
	  console.log("getDynamicMarketPlaceList");
					   var colIndex = 0;
					    $('#marketPlaceList').DataTable( { 
					    	"processing": true,
					        "serverSide": true,
					        "searching": false,
					        "searchHighlight": true,
					        "ajax": $.fn.dataTable.pipeline( {
					            url: atsUrl + "/getMarketPlaceListDetails",
					            method: 'GET',
					            pages: 10
					        }),
					        "columns": [
					        	{ "data": "productCode" },
					        	{ "data": "category" },
					        	{ "data": "manufacturerName" },
					        	{ "data": "manufacturerDate" },
					        	{ "data": "price" },
					        	{ "data": "currencySpecification" },
					        	{ "data": "priority" },
					        	{ "data": "docFile" },
					        	{ "data": "isNegotiate" },
					        	{ "data": "shareContactDetails" },
					        	{ "data": "productShareType" },
					        	{ "data": "productDescription" },
					        	{ "data": "uploadPhotos" },
					        ],
					        "columnDefs": [ {
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "productCode",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
				              		if(productList.productCode != null && productList.productCode != '' && productList.productCode != "undefined"){
				              			result = result +"<div class='tbldata-dotdot-sm'>"+productList.productCode+"</div>";
				  	              	}else{
				  	            	  		result = result +"<div>---</div>";
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "category",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		if(productList.category != null && productList.category != '' && productList.category != "undefined"){
				              			result = result +"<div class='tbldata-dotdot-vsm'>"+productList.category+"</div>";
				  	              	}else{
				  	            	  		result = result +"<div>---</div>";
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "manufacturerName",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		if(productList.manufacturerName != null && productList.manufacturerName !== '' && productList.manufacturerName != "undefined"){
					    	  				result = result +"<div class='tbldata-dotdot-vsm'>"+productList.manufacturerName+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "manufacturerDate",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		var result = "";
					    	  		if(productList.manufacturerDate != null && productList.manufacturerDate !== '' && productList.manufacturerDate != "undefined"){
					    	  				result = result +"<div class='tblminwidth-100 pull-left text-center ml10'>"+productList.manufacturerDate+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "price",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		if(productList.price != null && productList.price !== '' && productList.price != "undefined"){
					    	  			
					    	  			if(productList.currencySpecification != null && productList.currencySpecification !== '' && productList.currencySpecification != "undefined"){
					    	  				result = result +"<div class='tblminwidth-100 pull-left text-center ml10'>"+productList.price+'  '+productList.currencySpecification+"</div>";
					    	  			}else{
					    	  				result = result +"<div class='tblminwidth-100 pull-left text-center ml10'>"+productList.price+"</div>";
					    	  			}
					    	  			
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					      
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "priority",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		if(productList.priority != null && productList.priority !== '' && productList.priority != "undefined"){
					    	  				result = result +"<div class='tblminwidth-100 pull-left text-center ml10'>"+productList.priority+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "docFile",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		result = result+'<td><div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt"  href=\"'+productList.docFile+'\" download title="Warranty Document"></a>';
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "isNegotiable",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		if(productList.isNegotiable != null && productList.isNegotiable !== '' && productList.isNegotiable != "undefined"){
					    	  				result = result +"<div class='tblminwidth-100 pull-left text-center ml10'>"+productList.isNegotiable+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "shareContactDetails",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		if(productList.shareContactDetails != null && productList.shareContactDetails !== '' && productList.shareContactDetails != "undefined"){
					    	  				result = result +"<div class='tblminwidth-100 pull-left text-center ml10'>"+productList.shareContactDetails+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "productShareType",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		if(productList.productShareType != null && productList.productShareType !== '' && productList.productShareType != "undefined"){
					    	  				result = result +"<div class='tblminwidth-100 pull-left text-center ml10'>"+productList.productShareType+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "productDescription",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		if(productList.productDescription != null && productList.productDescription !== '' && productList.productDescription != "undefined"){
					    	  				result = result +"<div class='tblminwidth-100 pull-left text-center ml10'>"+productList.productDescription+"</div>";
					    	  		}else{
				  	            	  		  result = result +"<div>---</div>"; 
				  	             	}
				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"data": "uploadPhotos",
					    	  	"render": function(data, type, productList, meta) {
					    	  		var result = "";
					    	  		result = result+'<td><div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt"  href=\"'+productList.uploadPhotos+'\" download title="Product Image"></a>';
//  		                             result = result + ' <span title="Warranty Document" class="paddingRight pull-middle"><i class="fa fa-download cursorPointer"></i></span>';
 				  	             	 return result;
					    	  	}
					        },{
					    	  	"targets": colIndex++,
					    	  	"sortable": false,
					    	  	"render": function(data, type, productList, meta) {
					    	  		
					    	  		 var result = "";
					    	  		 result = result + '<div class="tblminwidth-100 pull-left text-center ml10">';
		                             result = result + ' <span title="Share the Product" class="paddingRight pull-left"><i class="fa fa-share-alt cursorPointer"></i></span>';
		                             result = result + '  <span title="Share Later" class="paddingLeft  pull-left"><i class="fa fa-clock-o cursorPointer"></i></span>';
		                             result = result + '  <span title="Edit" class="paddingLeft  pull-left" href="${SourceLeadWeb_atsUrl}/editMarketPlace" onclick="editMarketPlace();"><i class="fa fa-pencil cursorPointer"></i></span>';
		                             result = result + '  <span title="Delete" onclick="deleteMarketPlaceList(\''+productList.uuid+'\')" class="paddingLeft  pull-left"><i class="fa fa-trash cursorPointer"></i></span>';
		                             result = result + '</div>';
				  	             	 return result;
				  	             	
					    	  	}
					        }],
					        "scrollCollapse": false,
					    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
					    	"aaSorting": [[2,'desc']],
					    	"bLengthChange": true,
					    	"bDestroy": true,
					    	"ordering": true,
					    	"oLanguage": {
					     		"sSearch": "",
					     		 "sZeroRecords": "", "sEmptyTable": "", 
					     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
					     }
					        
					    } );
					     $("#marketPlaceList").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
					  
				}
</script>

 <script>
 $(window).load(function() {
		var dbStartDate = $("#startDate").val();
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
		    $( "#fromDate" ).datepicker({
		    	dateFormat : finalDateFormatProduct,
		        changeMonth: true,
		        changeYear: true,
		  	    onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#toDate").datepicker("option", "minDate", dt);
			    }
		    });

		    $( "#toDate" ).datepicker({
		    	dateFormat : finalDateFormatProduct,
		      changeMonth: true,
		      changeYear: true,
		      onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#fromDate").datepicker("option", "maxDate", dt);
			    }
		    });
		    
		    $( "#warrantyExperyDate" ).datepicker({
		    	dateFormat : finalDateFormatProduct,
			      changeMonth: true,
			      changeYear: true
			    });
		    
		    $( "#manufacturerDate" ).datepicker({
		    	dateFormat : finalDateFormatProduct,
		        changeMonth: true,
		        changeYear: true
		      });
 });		  

 </script>
 
 <script>
function clearFromDate(){
	$("#fromDate").val("");
	};
	
 function clearToDate(){
	  $("#toDate").val("");
  } 
 function clearExpiryDate(){
	  $("#manufacturerDate").val("");
 } 
 function clearWarrantyExpiryDate(){
	  $("#warrantyExperyDate").val("");
}
 
 $(function () {
     $("#uploadBtn").change(function () {
         fileUpload("uploadBtn", "resumes", "personalAndProductInfo_form");
         $(".clearUpload").show();
     });
 });
 
 $(function () {
     $("#file0").change(function () {
         fileUpload("file0", "resumes", "personalAndProductInfo_form");
         $(".clearUpload").show();
     });
 });
</script>
<script>
function ContactDetails(){
 var contactDetails = $("#shareContactDetailss").val();
	  $("#shareContactDetailss").val("N");
}
/* function warrantyHandler(){
	$("#warrantyCheckbox").val("N");
} */
function shareType(){
	  $("#productShareType").val("N");
}
function negotiable(){
	  $("#isNegotiable").val("N");
}
</script>

<script>
function resetFunction(){
	$("#productName").val('').trigger("chosen:updated");
    $("#category").val('0').trigger("chosen:updated");
    $("#priorityy").val('0').trigger("chosen:updated");
    $("#relocationFromtoSelect").val('0').trigger("chosen:updated");
    $("#currentCity").val('').trigger("chosen:updated");
    $("#comment").val('').trigger("chosen:updated");
    $("#manufacturerName").val('').trigger("chosen:updated");
    $("#price").val('').trigger("chosen:updated");
    $("#currencySpecification").val('0').trigger("chosen:updated");
    $("#manufacturerDate").val('').trigger("chosen:updated");
    $("#warrantyExperyDate").val('').trigger("chosen:updated");
    $("#productDescription").val('').trigger("chosen:updated");
    $("#file0").val('').trigger("chosen:updated");
    $("#uploadFile").val('').trigger("chosen:updated");

}

</script>
<script>
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
        };
        
var fileCounter = 1;
var fileCounterArray = new Array();
function addProductFileUpload(){
	if(fileCounterArray.length < 6){
		fileCounter = fileCounter++;
	$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file'+fileCounter+'"  type="file" class="pull-left" onchange="uploadFileButton('+fileCounter+');"/>' +
            '<a id="Button' + fileCounter + '' +
            'value="" onclick = "RemoveFileUpload(this,\''+fileCounter+'\')"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>').appendTo($('#FileUploadContainer'));
	
	fileCounterArray.push(fileCounter);
	console.log(fileCounterArray);
}
}

function RemoveFileUpload(div,fileCounter)
{
	document.getElementById("FileUploadContainer").removeChild(div.parentNode);
	fileCounter = fileCounter--;
	fileCounterArray.pop();
	console.log(fileCounterArray);
}

function uploadFileButton(id){
fileUpload("file"+id+"", "userMarketplace", "personalAndProductInfo_form","uploadedFileInfo");
}
        
</script>
 <script>
$('#warrantyCheckbox').change(function(){
	   $(this).val(this.checked ? "Yes" : "No");
	   });	
</script>
 
 <script>
 document.getElementById("uploadBtn").onchange = function () {
		$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	};
 </script>
  <script>
 document.getElementById("FileUploadContainer").onchange = function () {
		$('#FileUploadContainer').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	};
 </script>
 
 <jsp:include page="../masterFooter.jsp" />	