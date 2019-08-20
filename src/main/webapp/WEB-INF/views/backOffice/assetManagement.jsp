<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" src="jquery-1.2.6.min.js"></script>

<script>
var atsUrl = '${SourceLeadWeb_atsUrl}';
$(document).ready(function(){
	getDynamicAssetList();
	loadCategory();
});
</script> 

<script>
	$(document).ready(function() {
		document.getElementById("addAsset_form").reset();
	});
</script>

<script type="text/javascript">
	function columPop(){
		$(".colPop").show();
	}
	function columClose(){
		$(".colPop").hide();
	}
	$("html").click(function(){
		$(".colPop").hide();
	});
	$(".refreIcon, .colPop").click(function(e){
	 	e.stopPropagation();
	});	
		
	function columHide(){
		columClose();
		$(".toggle-vis").each(function() {
		   var chkdVal= $(this).prop('checked');
		   var column = table.column($(this).attr('data-column'));
			  if(chkdVal == true){
			         column.visible(true);
			    }
			    else{
			      column.visible(false);
			  }
	    });
		
	}
	function checkAllColumns(obj){
		var id = obj.id;
		if(id =='columnsPickerCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',true);
		}
		else if(id =='columnsPickerUnCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',false);
		}
	}
	
	
	
</script>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left"><h2>Asset Management</h2></div>
  <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Asset Management</div>
</section>

<div class="project_wrap">
<div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
         <li class="current"><a href="#tab-8" onclick="getDynamicAssetList()" id="asset">Assets</a></li>
         <li class=""><a href="#tab-9" onclick="getDynamicAssignedList()" id="assigned">Assigned</a></li>
         <div class="clearfix"></div>
     </ul>
</div>
<div class="timesheet_out">
	 <div class="colPop">
		<div class="colPopMid coldropdown-parent">
		<div class="clearfix">
			<div class="row">
					<div class="col-md-10 mb10 mt5 col-md-offset-2">
					<span class="checkbox-new  pull-left mr">
						<input id="columnsPickerUnCheckAll" class="radio-custom" name="columnsPicker" onclick="checkAllColumns(this);"  type="radio">
						<label for="columnsPickerUnCheckAll" class="radio-custom-label">Uncheck All</label>
					</span>
					<span class="checkbox-new pull-left">
						<input id="columnsPickerCheckAll" class="radio-custom" name="columnsPicker" onclick="checkAllColumns(this);"  type="radio" checked="">
						<label for="columnsPickerCheckAll" class="radio-custom-label" >Check All</label>
					</span>
				</div>
			</div>
		  <div class="clearfix"></div>
			
			<div class="coldropdown-inner">
				<ul>
					<li>
						<span class="checkbox-new clearfix">
							<input id="a" class="checkbox-custom toggle-vis" name="paidComponent" data-column="0" type="checkbox" checked="">
							<label for="a" class="checkbox-custom-label" >Status</label>
						</span>
					</li>
					<li>
						<span class="checkbox-new clearfix">
							<input id="c" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" checked="">
							<label for="c" class="checkbox-custom-label" >Country</label>
						</span>
					</li>
					<li>
						<span class="checkbox-new clearfix">
							<input id="d" class="checkbox-custom toggle-vis" name="paidComponent" data-column="3" type="checkbox" checked="">
							<label for="d" class="checkbox-custom-label" >Type</label>
						</span>
					</li>
					<li>
						<span class="checkbox-new clearfix">
							<input id="f" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox" checked="">
							<label for="f" class="checkbox-custom-label" >Email</label>
						</span>
					</li>
					<li>
						<span class="checkbox-new clearfix">
							<input id="g" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6" type="checkbox" checked="">
							<label for="g" class="checkbox-custom-label" >Phone</label>
						</span>
					</li>
					<li>
						<span class="checkbox-new clearfix">
							<input id="h" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7" type="checkbox" checked="">
							<label for="h" class="checkbox-custom-label" >On Date</label>
						</span>
					</li>
					<li>
						<span class="checkbox-new clearfix">
							<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" data-column="8"  type="checkbox" checked="">
							<label for="j" class="checkbox-custom-label" >LCA Start Date</label>
						</span>
					</li>
					<li>
						<span class="checkbox-new clearfix">
							<input id="k" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="9" checked="">
							<label for="k" class="checkbox-custom-label" >LCA End Date</label>
						</span>
					</li>
				</ul>
				</div>
			</div>
			<div class="colPopFot">
				<a href="javascript:void(0)" onclick="columHide();">Apply</a>
				<a href="javascript:void(0)" onclick="columClose();">Cancel</a>
			</div>
		</div>				
	</div>  
			
 <div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="advanceSearchDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="asset_reset" title="Refresh" onclick="resetAssetFilter();" style="display: block"></a>
<!--       		<a href="javascript:void(0)" onclick="columPop();" title="Column Chooser" class="columIcon"></a> -->
      		
      		<a href="javascript:void(0)" class="refeIcon" id="assign_reset" title="Refresh" onclick="resetAssignAssetFilter();" style="display: none"></a>
      </span>
      
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
					        <sec:authorize access="hasRole('ROLE_ADD_ASSET')"> 
					        	<a href="javascript:void(0)"  onClick="addAssetManagementPopup();">Add Asset</a> 
					        </sec:authorize>
					        <sec:authorize access="hasRole('ROLE_ADD_ASSET_CATEGORY')"> 
					        	<a href="${SourceLeadWeb_atsUrl}/assetCategory" class="">Categories List</a>
					        </sec:authorize>	
					        
					        <a href="${SourceLeadWeb_sourceleadUrl}/uploadassetManagement">Import</a>	  
					</span>
				</div>
			</li>
				<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openAssetFilterPop();"></a></li>
				
				<li id="assetExport" style="display:block"><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop" >
						<span>
		           	   		<a href="javascript:void(0)" onclick="assetExport('Pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="assetExport('Excel');" title="Excel">Export Excel</a>  
						</span>
					</div>
				</li>
				<li id="assignAssetExport" style="display:none"><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop" >
						<span>
		           	   		<a href="javascript:void(0)" onclick="assignAssetExport('Pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="assignAssetExport('Excel');" title="Excel">Export Excel</a>  
						</span>
					</div>
				</li> 
		    
		  <li class="" id="assetSearch" style="display: block;"><input type="text" class="repSearchBar" placeholder='Search' id='assetDataTableSearch'/><span class="repSearchIcon" onClick="getDynamicAssetList();"></span></li>
		  <li class="" id="assignAssetSearch" style="display: none;"><input type="text" class="repSearchBar" placeholder='Search' id='assignAssetDataTableSearch'/><span class="repSearchIcon" onClick="getDynamicAssignedList();"></span></li>
		  
        </ul>
      </div> 
  
      
      <div class="clearfix"></div>
      <input type="hidden" id="assetmanagementPdfFile" name="assetmanagementPdfFile">
      <input type="hidden" id="assetAssignFileSize" name="assetAssignFileSize">
    <!-- list Table -->
    <div class="require_wrap_table table-wrap datatable_filter overflow_inherint project_tabs_inn" id="tab-8">
	    <div id= "assetManagementLoderDiv"></div>
	    <table id="assetManagementList" class="display" cellspacing="0" width="100%"> </table>
	    <div id="dynamicAssetManagementList"></div>
    </div>
    
    <div class="timesheet_out project_tabs_inn require_wrap_table table-wrap datatable_filter overflow_inherint" id="tab-9">
    </div>
    
    
    <!-- list Table -->
  <div class="side_mask">
    <div class="proj_close"></div>
  </div>
  <div class="clients_side_wrap">
    <div class="clients_side_wrap_inn">
      <h1>Asset Management Details</h1>
      <div class="project_side_wrap_inn_tabs">
      <div id="projectSideDiv">
		     </div> 
        <div class="project_tabs">
          <div id="tab-1" class="project_tabs_inn" >
              
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Project -->
  </div>
  </section>
  
   <div class="comMask"></div>
  		<div class="modal assetManagementPopup" id="assetManagementPopup" style="display: none;">
 		<jsp:include page="addAssetPopup.jsp"></jsp:include> 
 	</div>
 	</div>

<script>
//project side bar tabs
$(document).ready(function() {
	$("#tab-8").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });

	
});
</script>
 	
  <script>
  function addAndEditAssetAssign(assetUUID){
	  $.ajax({
	    	type: "POST",
	    	url: atsUrl+"/addAndEditAssetAssign?&assetUUID="+assetUUID, 
	        success: function(result) {
	        	$(".assetManagementPopup, .comMask").fadeIn();
	        	$('#assetManagementPopup').empty();
	        	$('#assetManagementPopup').append(result);
// 	        	issueDateScript();
				assetCategory();
	        	populateDropdownValues();
	        	<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
	  		 	 var sectionId = '${section.id}';
	  		  	 getCustomDependentField(sectionId);
	  		 	</c:forEach>
		        
	        },
	    }); 
  }
  </script>
  
   
  <script>
  function issueDateScript1(){
  var locationDate ='${SourceLeadWeb_dateFormat}'; 
  var finalDateFormat = getJSDateFormat(locationDate);
  $('#issueDate').datepicker({
      dateFormat: finalDateFormat,
      changeMonth: true,
  	  changeYear: true, 
  });
  $('#purchaseDate').datepicker({
      dateFormat: finalDateFormat,
      changeMonth: true,
  	  changeYear: true, 
  });
  $('#depreciationDate').datepicker({
      dateFormat: finalDateFormat,
      changeMonth: true,
  	  changeYear: true, 
  });
  
  
  <c:forEach var="shift" items="${siftList}" varStatus="shiftOrder">
	     $('#assignedOn${shiftOrder.index}').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$('#returnedOn${shiftOrder.index}').datepicker("option", "minDate", dt);
			}
		  });
		  
		  $('#returnedOn${shiftOrder.index}').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$('#assignedOn${shiftOrder.index}').datepicker("option", "minDate", dt);
			}
		  });
     
	  
  </c:forEach>
  }
  function clearDate(){
  	$("#issueDate").val("");
  	};
  function clearPurchaseDate(){
	  $("#purchaseDate").val("");
  };
  function clearDepreciationDate(){
	  $("#depreciationDate").val("");
  };
</script>
  
  <script type="text/javascript">
  function storeAsset() {
			var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
			   };  
			   if ($('#addAsset_form').validate(false, validationSettings)){
        	        var form = $('#addAsset_form')[0];
	           		var formData = new FormData(form);
	           		
	          		 $("#add_asset").attr("disabled", "disabled");
	          		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	          	      $.ajax({
	     		    	type: "POST",
	     		    	data: formData,
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
	     		    	 url: atsUrl+"/addCategoryAssets?"+tokenParamName+"="+tokenValue+"", 
	     		        success: function(result) {
	     		        	
	     		       	if (result && result.response === "INVALID_DATA") {
						    showError(result.responseMessage);
						    $("#loading").hide();
						} else if (result.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							location.replace(atsUrl+ "/assetManagement");
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
	     			        
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
         }
         
         $('body').on('blur', '#addAsset_form', function() {
     		$("#addAsset_form").showHelpOnFocus();
     		$("#addAsset_form").validateOnBlur(false, validationSettings);
     	});
</script> 

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>
  
  <script>
  function flushAsset(){
	  $('#addAsset_form')[0].reset();
	  $("#addAsset_form").find("#category").val("");
	  $("#addAsset_form").find("#category").trigger("chosen:updated");
	  $("#addAsset_form").find("#workingStatus").val("");
	  $("#addAsset_form").find("#workingStatus").trigger("chosen:updated");
	  $("addAsset_form").validate().resetForm();
  }
  
  </script>
  
<script>
function closeAssetFilterPop(){
	$("#assetFilterId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openAssetFilterPop(){
	 $("#assetFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}

</script>
     <div class="advancedsearch-mask"></div>
          <div class="cand-advsrch-popup advancedsearch-popup" id="assetFilterId">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeAssetFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
						<div class="col-md-3">
						<div class="row">
						<div class="col-md-12 mb">
							<label><strong>Working Status</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled" id="workStatusFilter">
				                    <li class="checkBox positionRelative"><input id="c1" type="checkbox"  name="workingStatus"  value ="Working" ><label>Working</label></li>
                                    <li class="checkBox positionRelative"><input id="c2" type="checkbox"  name="workingStatus"  value ="Out Of Service" ><label>Out of service</label></li>
                                    <li class="checkBox positionRelative"><input id="c3" type="checkbox"  name="workingStatus"  value ="Service" ><label>Service</label></li>
		                        </ul>
							 </div>
						</div> 
						 </div>
						</div>
						
						<div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myCategoryInput" onkeyup="myCategoryFunction()" placeholder="Category" title="Type in a clients">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myCategoryUL">
								    <c:forEach var="report" items="${assetCategoriesList}">
									     <li class="checkBox positionRelative"><input  id="category${report.id}" name="categoryId" value ="${report.id}"  type="checkbox"><label>${report.categoryName}</label></li>
									</c:forEach>
								</ul>
							 </div> 
					   </div> 
					   
					   <div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myShiftInput" onkeyup="myShiftFunction()" placeholder="Availability" title="Type in a clients">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myShiftUL">
								    <c:forEach var="shift" items="${siftList}">
									     <li class="checkBox positionRelative"><input  id="shift${shift.id}" name="shiftId" value ="${shift.id}"  type="checkbox"><label>${shift.shiftName}</label></li>
									</c:forEach>
								</ul>
							 </div> 
					   </div>
					   
					   <div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myTagsInput" onkeyup="myTagsFunction()" placeholder="Tags" title="Type in a tags">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myTagsUL">
								    <c:forEach var="tag" items="${assetTagsList}">
									     <li class="checkBox positionRelative"><input  name="tags" value ="${tag}"  type="checkbox"><label>${tag}</label></li>
									</c:forEach>
								</ul>
							 </div> 
					   </div> 
					   
						
					</div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr"> 
							<input type="button" value="Apply" id="asset_apply"  style="display: inline-block;" onclick="closeAssetFilterPop();getDynamicAssetList();" class="btn btn-sm btn-success" />
							<input type="button" value="Apply" id="assignAsset_apply"  style="display: none;" onclick="closeAssetFilterPop();getDynamicAssignedList();" class="btn btn-sm btn-success" />
							
							<input type="button" value="Reset" id="asset_clear_search" onclick="resetAssetFilter();" style="display: inline-block;"  class="btn btn-sm btn-primary" />
							<input type="button" value="Reset" id="assignAsset_clear_search" onclick="resetAssignAssetFilter();" style="display: none;"  class="btn btn-sm btn-primary" />
							
						</div>
					</div>
				</div>
<!-- ========================================== Dynamic pageLoading in AssetManegment written By Laxman  Start============================== -->
<script>
function resetAssetFilter(){
	$("ul#workStatusFilter > li input:checkbox").prop("checked",false);
	$("ul#myCategoryUL > li input:checkbox").prop("checked",false);
	$("ul#myShiftUL > li input:checkbox").prop("checked",false);
	$("ul#myTagsUL > li input:checkbox").prop("checked",false);
	$("#assetDataTableSearch").val('');
	$("#myCategoryInput").val("");
	$("#myShiftInput").val("");
	$("#myTagsInput").val("");
	getDynamicAssetList();
	myCategoryFunction();
	myShiftFunction();
	myTagsFunction();
}
function resetAssignAssetFilter(){
	$("ul#workStatusFilter > li input:checkbox").prop("checked",false);
	$("ul#myCategoryUL > li input:checkbox").prop("checked",false);
	$("ul#myShiftUL > li input:checkbox").prop("checked",false);
	$("ul#myTagsUL > li input:checkbox").prop("checked",false);
	$("#assignAssetDataTableSearch").val('');
	$("#myCategoryInput").val("");
	$("#myShiftInput").val("");
	$("#myTagsInput").val("");
	getDynamicAssignedList();
	myCategoryFunction();
	myShiftFunction();
	myTagsFunction();
}
function myCategoryFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myCategoryInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myCategoryUL");
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

function myShiftFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myShiftInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myShiftUL");
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


function myTagsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTagsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTagsUL");
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


function getDynamicAssetList(){
	var categoryIds = [];
	$('input:checkbox[name=categoryId]:checked').each(function() 
    {
		categoryIds.push($(this).val());
    });
	
	var shiftIds = [];
	$('input:checkbox[name=shiftId]:checked').each(function() 
    {
		shiftIds.push($(this).val());
    });
	
	var workingStatus = [];
	$('input:checkbox[name=workingStatus]:checked').each(function() 
    {
		workingStatus.push($(this).val());
    });
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
    {
		tags.push($(this).val());
    });
	
	var searchValue = $("#assetDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	var url = atsUrl	+"/getAssetDynamicList?searchValue="+searchValue+"&categoryIds="+categoryIds+"&workingStatus="+workingStatus+"&shiftIds="+shiftIds+"&tags="+tags;
	prepareDynamicAssetList(url);	
}
function prepareDynamicAssetList(url){
	var result = "";
    result = result	+ '<div class="">';
	result = result	+ '<div id="tasksLoadingImg"></div>';
	result = result + '<table id="assetPagination" class="display" cellspacing="0" width="100%">';
	result = result + '<thead>';
	result = result + '<tr">';
	result = result +'	<th>ID</th>';
	result = result +'	<th>Name</th>';
	result = result +'	<th>Serial Number</th>';
   	result = result +'  <th>Category</th>';
   	result = result +'  <th>Model</th>';
	result = result +'  <th>Manufactured Date</th>';
	result = result +'  <th>MAC Address</th>';
   	result = result +' <th>Status</th>';
   	result = result +' <th>Action</th>';
	result = result + '</tr>';
	result = result + '</thead>';
	result = result + '</table>';
	result = result	+ '</div>';
	$("#dynamicAssetManagementList").empty();  
	$("#dynamicAssetManagementList").append(result);  
	projectAssetPaginations(url);
}
function projectAssetPaginations(url){
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	var colIndex = 0;
    $('#assetPagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        
        "columnDefs": [{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"display": "none",
		  	"render": function(data, type, report) {
		  		var result = "";
		  		$("#assetmanagementPdfFile").val(1);
			   	result = result+'<td><div class="tbldata-dotdot" title="'+report.id+'">'+report.id+'</div></td>';

	             return result;
	  	      }
       		 },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report) {
		  		var result = "";
		  		if(report.name != null && report.name != "") {
				   	result = result+'<td><div class="tbldata-dotdot" title="'+report.name+'">'+report.name+'</div></td>';
		  		} else {
		  			result = result+'<td><div class="tbldata-dotdot">--</div></td>';	  			
		  		}
	            return result;
	  	      }
	  		},{
    	  	"targets": colIndex++,
    	  	"data": "",
    	  	"render": function(data, type, report) {
    	  		var result = "";
    	  	    result = result+'<td>';
    	  	  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSET_BASIC_DETAILS\',\'ROLE_VIEW_ASSET_ADDITIONAL_DETAILS\')">'; 
	   	  	     result = result +"<a class='tbldata-dotdot-sm'><a href='"+atsUrl+"/assetManagementSummary?assetUUID="+report.assetUUID+"'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+report.slno+"'>"+report.slno+"</span><i class='fa fa-binoculars'></i></div></a></a>";
	   	  	  '</sec:authorize>';
	   	  	  
		   	   '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_ASSET_BASIC_DETAILS\',\'ROLE_VIEW_ASSET_ADDITIONAL_DETAILS\')">'; 
	 	  	     result = result +"<div class='tbldata-dotdot_140'><span class='pull-left' title='"+report.slno+"'>"+report.slno+"</span></div>";
	 	  	  '</sec:authorize>';
	   	  	  
			   	result = result+'</td>';
	             return result;
    	  	}
        },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report) {
		  		var result = "";
			   	result = result+'<td><div class="tbldata-dotdot" title="'+report.categoryName+'">'+report.categoryName+'</div></td>';

	             return result;
	  	      }
	  		},{
		  	  	"targets": colIndex++,
			  	"data": "",
			  	"render": function(data, type, report) {
			  		var result = "";
			  		if (report.model != null && report.model != '' && report.model != 'undefined') {
						result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.model+'">'+report.model+'</div></td>';
					}else{
						result = result+'<td><div class="tbldata-dotdot-vsm" >--</div></td>';
					}
		           return result;
		  	  }
	  		},{
		  	  	"targets": colIndex++,
			  	"data": "",
			  	"render": function(data, type, report) {
			  		var result = "";
			  		if (report.issueDateStr != null && report.issueDateStr != '' && report.issueDateStr != 'undefined') {
						result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.issueDateStr+'">'+report.issueDateStr+'</div></td>';
					}else{
						result = result+'<td><div class="tbldata-dotdot-vsm" >--</div></td>';
					}
		           return result;
		  	  }
	  		},{
		  	  	"targets": colIndex++,
			  	"data": "",
			  	"render": function(data, type, report) {
			  		var result = "";
			  		if (report.macAddress != null && report.macAddress != '' && report.macAddress != 'undefined') {
						result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.macAddress+'">'+report.macAddress+'</div></td>';
					}else{
						result = result+'<td><div class="tbldata-dotdot-vsm" >--</div></td>';
					}
		           return result;
		  	  }
	  		},{
		  	  	"targets": colIndex++,
			  	"data": "",
			  	"render": function(data, type, report) {
			  		var result = "";
			  		 var selected1 = '';
	   				 var selected2 = '';
	   				 var selected3 = '';
			  		 if(report.workingStatus == 'Working'){
						selected1 = 'selected';
					 } 
				
					 if(report.workingStatus == 'Out Of Service'){
						selected2 = 'selected';
					 }
					 if(report.workingStatus == 'Service'){
						selected3 = 'selected';
					 }
					
			  	 	result = result + ' <td><div class="tblminwidth-150">';
					result = result + ' <select id="workingStatus'+report.id+'" name="workingStatus" onChange="updateWorkingStatus(\''+report.assetUUID+'\',\''+report.id+'\');">';
					result = result + ' <option '+selected1+'>Working</option>';
					result = result + ' <option '+selected2+'>Out Of Service</option>';
					result = result + ' <option '+selected3+'>Service</option>';
					result = result + ' </select>';
					result = result + ' </div></td>';
		           return result;
		  	  }
	  		},{
		  	  	"targets": colIndex++,
			  	"data": "",
			  	"sortable": false,
			  	"render": function(data, type, report) {
			  		var result = "";
				   	result = result+'<td><div class="tblminwidth-50">';
				    '<sec:authorize access="hasRole(\'ROLE_EDIT_ASSET\')">' 
				   			result = result+'<a class="glyphicon glyphicon-edit" title="Edit" onclick="addAndEditAssetAssign(\''+report.assetUUID+'\');"></a> ';
				   	'</sec:authorize>';
				   	
				   	'<sec:authorize access="hasRole(\'ROLE_DELETE_ASSET\')">' 
				   	   result = result+'<i class="glyphicon glyphicon-trash text-danger ml" onClick="deleteAsset(\''+report.assetUUID+'\')";></i>';
				   	'</sec:authorize>';
				   	
				   	
				   	result = result+'</div></td>';
		           return result;
		  	  }
	  		} ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting":[[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("assetPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $('#assetPagination').DataTable().column(0).visible(false);
    $("input[type=search]").hide();
    $("#assetDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getDynamicAssetList();
    		   
    	   }
    	});
}
 
</script>

<div id="assetServicePopUp" class="popupWrap"/>

<!-- ===================================================Dynamic pageLoading End ================================== -->


<script>
function getDynamicAssignedList(){
	var categoryIds = [];
	$('input:checkbox[name=categoryId]:checked').each(function() 
	{
		categoryIds.push($(this).val());
	});
	
	var shiftIds = [];
	$('input:checkbox[name=shiftId]:checked').each(function() 
	{
		shiftIds.push($(this).val());
	});
	
	var workingStatus = [];
	$('input:checkbox[name=workingStatus]:checked').each(function() 
	{
		workingStatus.push($(this).val());
	});
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
	{
		tags.push($(this).val());
	});
	var searchValue = $("#assignAssetDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	var url = atsUrl	+"/getAssignAssetDynamicList?searchValue="+searchValue+"&categoryIds="+categoryIds+"&workingStatus="+workingStatus+"&shiftIds="+shiftIds+"&tags="+tags;
	prepareDynamicAssignAssetList(url);	
}


function prepareDynamicAssignAssetList(url){
	var result = "";
    result = result	+ '<div class="">';
	result = result	+ '<div id="tasksLoadingImg"></div>';
	result = result + '<table id="assignAssetPagination" class="display" cellspacing="0" width="100%">';
	result = result + '<thead>';
	result = result + '<tr>';
	result = result +'	<th>Emp Id</th>';
	result = result +'	<th>Emp Name</th>';
   	result = result +'  <th>Asset Name</th>';
   	result = result +'  <th>Serial Number</th>';
	result = result +'  <th>Assigned On</th>';
	result = result +'  <th>Return On</th>';
   	result = result +' <th>Shift</th>';
   	result = result +' <th>Category</th>';
	result = result + '</tr>';
	result = result + '</thead>';
	result = result + '</table>';
	result = result	+ '</div>';
	$("#dynamicAssetManagementList").empty();  
	$("#tab-9").empty();
	$("#tab-9").append(result);  
	assignAssetPagination(url);
}

function assignAssetPagination(url){
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	var colIndex = 0;
    $('#assignAssetPagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        
        "columnDefs": [{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"sortable": false,
		  	"display": "none",
		  	"render": function(data, type, report) {
		  		var result = "";
		  		   $("#assetAssignFileSize").val(1);
			  		if(report.employeeId != null && report.employeeId != "" && report.employeeId !="undefined"){
					   	result = result+'<td><div class="tbldata-dotdot_80" title="'+report.employeeId+'">'+report.employeeId+'</div></td>';
			  		}else{
					   	result = result+'<td><div class="tbldata-dotdot_80">--</div></td>';
			  		}
	             return result;
	  	       }
	  		 },{
		  	  	"targets": colIndex++,
			  	"data": "",
			  	"display": "none",
			  	"render": function(data, type, report) {
			  		var result = "";
				  		if(report.employeeName != null && report.employeeName != "" && report.employeeName !="undefined"){
						   	result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.employeeName+'">'+report.employeeName+'</div></td>';
				  		}else{
						   	result = result+'<td><div class="tbldata-dotdot-vsm">--</div></td>';
				  		}
		             return result;
		  	      }
	  		  },{
			  	  	"targets": colIndex++,
				  	"data": "",
				  	"display": "none",
				  	"render": function(data, type, report) {
			  		 var result = "";
				  		if(report.assetName != null && report.assetName != "" && report.assetName !="undefined"){
						   	result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.assetName+'">'+report.assetName+'</div></td>';
				  		}else{
						   	result = result+'<td><div class="tbldata-dotdot-vsm">--</div></td>';
				  		}
		             return result;
		  	      }
		  		},{
			  	  	"targets": colIndex++,
				  	"data": "",
				  	"display": "none",
				  	"render": function(data, type, report) {
				  		var result = "";
					  		if(report.serialNumber != null && report.serialNumber != "" && report.serialNumber !="undefined"){
							   	result = result+"<td><a href='"+atsUrl+"/assetManagementSummary?assetUUID="+report.assetUUID+"'><div class='proj_show tbldata-dotdot-vsm'><span class='pull-left' title='"+report.serialNumber+"'>"+report.serialNumber+"</span><i class='fa fa-binoculars'></i></div></a></div></td>";
							   	
					  		}else{
							   	result = result+'<td><div class="tbldata-dotdot-vsm">--</div></td>';
					  		}
			             return result;
		  	      }
			  },{
			  	  	"targets": colIndex++,
				  	"data": "",
				  	"display": "none",
				  	"render": function(data, type, report) {
				  		var result = "";
					  		if(report.assignedOnStr != null && report.assignedOnStr != "" && report.assignedOnStr !="undefined"){
							   	result = result+'<td><div class="tbldata-dotdot_80" title="'+report.assignedOnStr+'">'+report.assignedOnStr+'</div></td>';
					  		}else{
							   	result = result+'<td><div class="tbldata-dotdot_80">--</div></td>';
					  		}
			             return result;
		  	      }
			  },{
			  	  	"targets": colIndex++,
				  	"data": "",
				  	"display": "none",
				  	"render": function(data, type, report) {
				  		var result = "";
					  		if(report.returnedOnStr != null && report.returnedOnStr != "" && report.returnedOnStr !="undefined"){
							   	result = result+'<td><div class="tbldata-dotdot_80" title="'+report.returnedOnStr+'">'+report.returnedOnStr+'</div></td>';
					  		}else{
							   	result = result+'<td><div class="tbldata-dotdot_80">--</div></td>';
					  		}
			             return result;
		  	      }
			  },{
			  	  	"targets": colIndex++,
				  	"data": "",
				  	"display": "none",
				  	"render": function(data, type, report) {
				  		var result = "";
					  		if(report.shiftName != null && report.shiftName != "" && report.shiftName !="undefined"){
							   	result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.shiftName+'">'+report.shiftName+'</div></td>';
					  		}else{
							   	result = result+'<td><div class="tbldata-dotdot-vsm">--</div></td>';
					  		}
			             return result;
		  	      }
			  },{
			  	  	"targets": colIndex++,
				  	"data": "",
				  	"display": "none",
				  	"render": function(data, type, report) {
				  		var result = "";
					  		if(report.categoryName != null && report.categoryName != "" && report.categoryName !="undefined"){
							   	result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.categoryName+'">'+report.categoryName+'</div></td>';
					  		}else{
							   	result = result+'<td><div class="tbldata-dotdot-vsm">--</div></td>';
					  		}
			             return result;
		  	      }
			  }
        ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting":[[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("assignAssetPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
}
 
$("#assignAssetDataTableSearch").keyup(function(e) {
	   if(e.which === 13) {
		   getDynamicAssignedList();
	   }
	});
</script>




<script>
function updateWorkingStatus(assetUUID,assetId){
	var workingStatus = $('#workingStatus'+assetId).val();
	var rowHtmlContent =  $("#asset"+assetId).html();
	 $("#asset"+assetId).empty();
	 $("#asset"+assetId).html("<td colspan='7'  id='loading"+assetId+"'></td>");
	 $("#loading"+assetId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	if(workingStatus == "Service"){
		assetmanagemetServicePopUp(assetId,workingStatus,assetUUID);
	}else{
		$.ajax({
		   	type: "POST",
		    url: atsUrl+"/updateAssetWorkingStatus?"+tokenParamName+"="+tokenValue+"&assetId="+assetId+"&workingStatus="+workingStatus,	    	   
		    success: function(data) {
		    	$("#asset"+assetId).empty();
		    	$("#asset"+assetId).html(rowHtmlContent);
		        $("#workingStatus"+assetId).val(workingStatus);
		    	
		    }
	   });
	}
	 
}

function assetmanagemetServicePopUp(assetId,workingStatus,assetUUID){
	$("#assetServicePopUp, .mask").fadeIn();
	 var result = "";
	 result = result + '<form id="assetService_form" name="assetService_form" method="post">';
	 result = result + '	<input type="hidden" name="assetId" id="assetId" value='+assetId+'>';
	 result = result + '	<input type="hidden" name="assetUUID" id="assetUUID" value='+assetUUID+'>';
	 result = result + '	<input type="hidden" name="workingStatus" id="workingState" value='+workingStatus+'>';
	 result = result + '	<div class="clearfix">';
		result = result + '	<div class="popupHead">Asset Management Service<i class="closePop pull-right popClose" onclick="closePopup(\''+assetUUID+'\',\''+assetId+'\');"></i></div>';
			result = result + '	<div class="popupContent">';
				result = result + '	<div class="row form-group">';
					result = result + '<div class="col-md-4">';
					result = result + '    	<label class="clearfix">Service Date<span class="colorRed paddingLeft">*</span></label>';
					result = result + '    	<div class="clearfix validation_p" id="errServiceDateDiv">';
					result = result + '    		<input type="text" class="form-control calendar"  placeholder="Service Date" name="serviceDate" id="serviceDate" data-validation="required" field-name="Service Date" readonly="readonly">';
					result = result + '    	</div>';
					result = result + '</div>';
					
					result = result + '			<div class="col-md-4">';
					result = result + '				<label class="clearfix">Received Date<span class="colorRed paddingLeft">*</span></label>';
					result = result + '				<div class="clearfix validation_p" id="errRecivedDateDiv">';
					result = result + '					<input type="text" class="form-control calendar" placeholder="Received Date" name="receivedDate" id="receivedDate" data-validation="required" field-name="Received  Date" readonly="readonly">';
					result = result + '				</div>';
					result = result + '			</div>';
					
					result = result + '			<div class="col-md-4">';
					result = result + '				<label class="clearfix">Cost<span class="colorRed paddingLeft">*</span></label>';
					result = result + '				<div class="clearfix validation_p">';
					result = result + '					<input type="text" class="form-control" placeholder="Cost" name="cost" id="cost" data-validation="required validate_float validate_length length1-50" field-name="Cost">';
					result = result + '				</div>';
					result = result + '			</div>';
					result = result + '	</div>';
					result = result + '	<div class="row form-group">';
						result = result + '			<div class="col-md-12">';
						result = result + '				<label class="clearfix">Description<span class="colorRed paddingLeft">*</span></label>';
						result = result + '				<div class="clearfix validation_p">';
						result = result + '					<textarea class="form-control" rows="3" name="description" id="description" placeholder="Description" data-validation="required validate_length length2-500" field-name="Description"></textarea>';
						result = result + '				</div>';
						result = result + '			</div>';
					result = result + '	</div>';
				
			result = result + '	</div>';
			
			result = result + '	<div class="popupFoot text-right">';
			result = result + '		<input type="button" value="Save" name="btnAddExpCat" id="save_button" class="slBtn md colorBtn" onclick="saveAssetService('+assetId+');">';
			result = result + '		<input type="button" value="Cancel" class="slBtn md popClose" onclick="closePopup(\''+assetUUID+'\',\''+assetId+'\');">';
			result = result + '	</div>';
	 result = result + '</div>';
	 result = result + '</form>';
	$("#assetServicePopUp").empty();
	$("#assetServicePopUp").append(result)
	
	var locationDate ='${SourceLeadWeb_dateFormat}'; 
	var finalDateFormat = getJSDateFormat(locationDate);
	$('#serviceDate').datepicker({
	    dateFormat: finalDateFormat,
	    changeMonth: true,
		changeYear: true, 
		onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate() + 1);
			$('#receivedDate').datepicker("option", "minDate", dt);
			errServiceDate();
		}
	});
	$('#receivedDate').datepicker({
	    dateFormat: finalDateFormat,
	    changeMonth: true,
		changeYear: true, 
		onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate() - 1);
			$('#serviceDate').datepicker("option", "maxDate", dt);
			errReceiveDate();
		}
	});
}

function errServiceDate(){
	$('#errServiceDateDiv').find('p.jquery_form_error_message').remove(); 
}
function errReceiveDate(){
	$('#errRecivedDateDiv').find('p.jquery_form_error_message').remove(); 
}
//close Popup
function closePopup(assetUUID,assetId) {
	var rowHtmlContent =  $("#asset"+assetId).html();
	$("#assetServicePopUp, .mask").fadeOut();
	 $.ajax({
		   	type: "POST",
		    url: atsUrl+"/cancelAssetManagementService?"+tokenParamName+"="+tokenValue+"&assetUUID="+assetUUID,	    	   
		    success: function(json) {
		    	$("#asset"+assetId).empty();
		    	$("#workingStatus"+assetId).val(json.workingStatus);
		    	$("#asset"+assetId).html(rowHtmlContent);
		    }
	 });
	
}
</script>
<script>
function saveAssetService(assetId){
	if ($('#assetService_form').validate(false, validationSettings)) {
		$("#save_button").attr("disabled", "disabled");
		var rowHtmlContent =  $("#asset"+assetId).html();
		var workingState = $("#workingState").val();
		 $("#asset"+assetId).empty();
		 $("#asset"+assetId).html("<td colspan='7'  id='loading"+assetId+"'></td>");
		 $("#loading"+assetId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData("assetService_form");
	 	$.ajax({
			type: "POST",
			data: formData,
			url : atsUrl+ "/saveAssetService?${_csrf.parameterName}=${_csrf.token}",
			success : function(data) {
					if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
			        		showError(data.responseMessage);
	  	    		}else if (data.response === "AWKWARD") {
	            			location.href = "/errorPage";
	  	    		}else if(data.response === "SUCCESS"){
	  	    			 closePopup();
	  	    			$("#asset"+assetId).empty();
	  			    	$("#asset"+assetId).html(rowHtmlContent);
	  			        $("#workingStatus"+assetId).val(workingState);
			        }else if (data.indexOf('Sign In') !== -1) {
            			location.replace(sourceleadUrl);
	  				} else {
	  					showError("Operation failed, please try again");
	  				}
			},
		}); 
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#assetService_form', function() {
	$("#assetService_form").showHelpOnFocus();
	$("#assetService_form").validateOnBlur(false, validationSettings);
});
 

</script>

<script>
 function assetSideBar(assetId){ 
		    $(".clients_side_wrap").addClass("clients_side_wrap_active");
		    $(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
// 			assetDetails(assetId);
 }
</script> 
  
   <script>
  function assetCategory(){
	  var selectedCategory = $("#category option:selected").val();
	    if(selectedCategory == '2'){
			  document.getElementById("monitorDiv").style.display="";
			  document.getElementById("name").style.display="";
			  document.getElementById("memoryDiv").style.display="";
			  document.getElementById("cpuDiv").style.display="";
			  document.getElementById("harddiskDiv").style.display="";
			  
		  }else if((selectedCategory != 'Desktop' && selectedCategory == '1') || category == '1'){
			  document.getElementById("monitorDiv").style.display="none";
			  document.getElementById("name").style.display="";
			  document.getElementById("memoryDiv").style.display="";
			  document.getElementById("cpuDiv").style.display="";
			  document.getElementById("harddiskDiv").style.display="";
			  
			  $("#monitor").val('');
		  }else{
			  document.getElementById("monitorDiv").style.display="none";
			  document.getElementById("memoryDiv").style.display="none";
			  document.getElementById("cpuDiv").style.display="none";
			  document.getElementById("harddiskDiv").style.display="none";
			  $("#monitor").val('');
			  $("#memory").val('');
			  $("#cpu").val('');
			  $("#harddisk").val('');
			 
		  } 
  }
  </script>
  
  
  <script>
  function loadCategory(){
	  document.getElementById("monitorDiv").style.display="none";
	  document.getElementById("memoryDiv").style.display="";
	  document.getElementById("cpuDiv").style.display="";
	  document.getElementById("harddiskDiv").style.display="";
  }
  </script>
   
  <script>
	  $('#laptop-edit, #desktop-edit, #others-edit').addClass('asstmake');
		  $('#laptop-edit').removeClass('asstmake');
	  function myasetEdit(local){
         if(local.value == 'desktop-edit'){
			$('#desktop-edit').removeClass('asstmake');
			$('#laptop-edit, #others-edit').addClass('asstmake');
			}
	     if(local.value == 'others-edit'){  
			$('#others-edit').removeClass('asstmake');
			$('#laptop-edit, #desktop-edit').addClass('asstmake');
		 }
		 if(local.value === 'laptop-edit'){
			$('#laptop-edit').removeClass('asstmake');
			$('#desktop-edit, #others-edit').addClass('asstmake');
		 }
	 };  
    </script> 
 
 
 
 <script>

</script> 
  <script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script> 
  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});

});
 		</script> 
  <script>

// Error pop


// Approved Popup
    $("#category").click(function(){
    	$('#category').find(".approved_pop").toggle();
    	$("#workingStatus").find(".approved_pop").slideUp();
    	$(".collapse-div-adduser").removeClass("show-div");
	});
    $("html").click(function() {
        $(".approved_pop").slideUp();
    });
    $(".approved_pop, #category").click(function(e) {
        e.stopPropagation();
    });
    
    $("#workingStatus").click(function(){
		$("#workingStatus").find(".approved_pop").toggle();
		$("#category").find(".approved_pop").slideUp();
		$(".collapse-div-adduser").removeClass("show-div");
	});
    $("html").click(function() {
        $(".approved_pop").slideUp();
    });
    $(".approved_pop, #workingStatus").click(function(e) {
        e.stopPropagation();
    });
    
    

// Export Popup
    $("#export-pop").click(function(){
		$(".export_pop").toggle();
	});
    $("html").click(function() {
        $(".export_pop").slideUp();
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
	
// project side bar
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// project side bar tabs
$(document).ready(function() {
	$("#tabb-1").show()
    $(".new-tabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".new-tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
 $(".collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
   event.preventDefault();
});

$(".close-div").click(function(){
	 $(this).parents().toggleClass("show-div");
});
// Bar chat pop
$(document).ready(function() {
    $(".graf_icon").click(function(){
		$(".pipeline_graf").slideToggle(200);
	});
$( "ul#canName-ch li").click(function(){
   var m = $(this).text();
  $("#canName").text(m);
});
});
</script> 
  <script>
$( "#pri-ch li").click(function(){
   var m = $(this).text();
    var mid = $(this).attr('id');
    $("#pri-val").text(m);
     if('h' === $(this).attr("id")){
          $("#pri-val").removeClass("text-warning text-primary").addClass("text-danger");
     }else if('m' === $(this).attr("id")){
        $("#pri-val").removeClass("text-danger text-warning").addClass("text-primary");
     }else{
      $("#pri-val").removeClass("text-danger text-primary").addClass("text-warning");
     }   
});
</script> 
  <script>
$(document).ready(function(){
	// Levels adding script

 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".div-close").click(function(){
	 $(this).parent().toggleClass("showDiv");
	 });

 $(".reminder").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $("").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   });
   
// new SlideToggle
 $("#id1").click(function(){
	  $(".hide-row").slideToggle(200);
	  $(".profile_details h4 i").toggleClass('icon-add');
	 });	 
   
   
   
</script> 
  <script type="text/javascript">
            $(function () {
                $('#datetimepicker5').datetimepicker({
					locale: 'ru'
                });
            });
        </script>
<script>
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
	 flushAsset();
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
</script>



<script type="text/javascript">
  function checkAttachment() {
    var uploadedFile = document.getElementById('attachment').value;
    if(uploadedFile){
    	var sizeinbytes = document.getElementById('attachment').files[0].size;
        
        var extension = uploadedFile.substring(uploadedFile.lastIndexOf(".")+1).toLowerCase();
       if(sizeinbytes > 500000){
        	 $("input#attachment").after("<p class='jquery_form_error_message'>");
         	   document.getElementById("attachment").setAttribute('record-exist', 'yes');
         	   document.getElementById("attachment").setAttribute('record-exist-errorMsg',"Invalid file size. Should less than 500 kb.");
     	  	 $("#addAsset_form").click(function() {
     	 		if ($(this).validate(false, validationSettings)) {
     	 		}
     	 	});
        	
        }else{
        	$('#attachment').find('p.jquery_form_error_message').remove();
        	$('input#attachment').removeAttr( "class record-exist record-exist-errormsg");
        	$('input#attachment').attr('class','hidden upload');
        }
    }
    
   
  };
</script>


<script type="text/javascript">
  function checkShiftAAttachment() {
    
    var uploadedFile = document.getElementById('uploadBtn-dskA').value;
    if(uploadedFile){
    var sizeinbytes = document.getElementById('uploadBtn-dskA').files[0].size;
    
    if(sizeinbytes > 500000){
    	 $("input#uploadBtn-dskA").after("<p class='jquery_form_error_message'>");
     	   document.getElementById("uploadBtn-dskA").setAttribute('record-exist', 'yes');
     	   document.getElementById("uploadBtn-dskA").setAttribute('record-exist-errorMsg',"Invalid file size. Should less than 500 kb.");
 	  	 $("#addAsset_form").click(function() {
 	 		if ($(this).validate(false, validationSettings)) {
 	 		}
 	 	});
    	
    }else{
    	$('#uploadBtn-dskA').find('p.jquery_form_error_message').remove();
    	$('input#uploadBtn-dskA').removeAttr( "class record-exist record-exist-errormsg");
    	$('input#uploadBtn-dskA').attr('class','hidden upload');
    }
    }
   
  };
</script>

<script type="text/javascript">
        
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
<script>
function uploadFile(){
	fileUpload("attachment", "assetDocuments", "addAsset_form","attachmentUpload");
	$('#uploadFileAttachment').val($('#attachment').val().replace(/C:\\fakepath\\/i, ''))
}
</script>

<script>
function setFilePath(shiftIndex){
	$('#shiftFile'+shiftIndex).val($('#uploadBtn-dskA'+shiftIndex).val().replace(/C:\\fakepath\\/i, ''));
}
function uploadShiftFile(shiftIndex){
	fileUpload("uploadBtn-dskA"+shiftIndex, "assetDocuments", "addAsset_form","assignAsset["+shiftIndex+"].fileInfo","","");
}
</script>

<script>
function closeassetManagementPopup() {
	$(".assetManagementPopup, .comMask").fadeOut();
	document.getElementById("addAsset_form").reset();
	$(".selectpicker").selectpicker('refresh');
}
function addAssetManagementPopup() {
	$(".assetManagementPopup, .comMask").fadeIn();
	addAndEditAssetAssign('');
}
</script>

<!-- =============================================================================== -->


<script>
function deleteDocument(assetId,url){
	document.getElementById("normalUploadFile").style.display="inline-table";
	document.getElementById("dbUploadFile").style.display="none";
	 $.ajax({
   	  type: "POST",
      url: atsUrl+"/deleteAssetDocument?"+tokenParamName+"="+tokenValue+"&assetId="+assetId+"&url="+url, 	   
 	  success: function(data) {  },
	   });	
	}
</script>	

<script>
function uploadFileAttachment(){
	fileUpload("fileAttachment", "assetDocuments", "addAsset_form","attachmentUpload");
	$('#dbUploadFileAttachment').val($('#fileAttachment').val().replace(/C:\\fakepath\\/i, ''))
}

</script>


<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	


function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	


</script>

<!-- ==================================  Asset Export script Written By Laxman Start ==============================  -->
<script>
function assetExport(type){
	if(type == "Pdf"){
		exportType = "pdf";
	}else{
		exportType = "xls";
	}
	var categoryIds = [];
	$('input:checkbox[name=categoryId]:checked').each(function() 
    {
		categoryIds.push($(this).val());
    });
	
	var shiftIds = [];
	$('input:checkbox[name=shiftId]:checked').each(function() 
    {
		shiftIds.push($(this).val());
    });
	
	var workingStatus = [];
	$('input:checkbox[name=workingStatus]:checked').each(function() 
    {
		workingStatus.push($(this).val());
    });
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
    {
		tags.push($(this).val());
    });
	var emptyFileSize = $('#assetmanagementPdfFile').val();
	var searchValue = $("#assetDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 if(emptyFileSize > 0 ){
	 	 window.location.href = atsUrl	+"/getAssetmanagementExport?searchValue="+searchValue+"&categoryIds="+categoryIds+"&workingStatus="+workingStatus+"&shiftIds="+shiftIds+"&tags="+tags+"&exportType="+exportType;
	 }else{
		   $('.mask').show();
		    var result = "";
			result = result +"	   <div id='pipeline_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#pipeline_user_pop").slideDown(200);
	 }
}
</script>
<!-- ==================================  Asset Export script Written By Laxman Ends==============================  -->

<!-- ================================== AssigneAsset Export script Written By Laxman Start==============================  -->

<script>
function assignAssetExport(type){
	if(type == "Pdf"){
		exportType = "pdf";
	}else{
		exportType = "xls";
	}
	var categoryIds = [];
	$('input:checkbox[name=categoryId]:checked').each(function() 
    {
		categoryIds.push($(this).val());
    });
	
	var shiftIds = [];
	$('input:checkbox[name=shiftId]:checked').each(function() 
    {
		shiftIds.push($(this).val());
    });
	
	var workingStatus = [];
	$('input:checkbox[name=workingStatus]:checked').each(function() 
    {
		workingStatus.push($(this).val());
    });
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
    {
		tags.push($(this).val());
    });
	var fileSize = $("#assetAssignFileSize").val();
	var searchValue = $("#assignAssetDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 if(fileSize > 0){
		 window.location.href = atsUrl	+"/getAssignAssetmanagementExport?searchValue="+searchValue+"&categoryIds="+categoryIds+"&workingStatus="+workingStatus+"&shiftIds="+shiftIds+"&tags="+tags+"&exportType="+exportType;
	 }else{
		 $('.mask').show();
		 var result = "";
			result = result +"	   <div id='pipeline_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#pipeline_user_pop").slideDown(200);
	 }
}
</script>
<!-- ================================== AssigneAsset Export script Written By Laxman Ends==============================  -->




<script>
document.getElementById("asset").addEventListener("click", function(){
	$("#assetExport, #assetSearch, #asset_apply, #asset_clear_search, #asset_reset").show();
    $("#assignAssetExport, #assignAssetSearch, #assignAsset_apply, #assignAsset_clear_search, #assign_reset").hide();
    
});
document.getElementById("assigned").addEventListener("click", function(){
	$("#assetExport, #assetSearch, #asset_apply, #asset_clear_search, #asset_reset").hide();
    $("#assignAssetExport, #assignAssetSearch, #assignAsset_apply, #assignAsset_clear_search, #assign_reset").show();
});
</script>




<%-- <jsp:include page="assetManagementDetails.jsp" />
<jsp:include page="assetManagementSummary.jsp" /> --%>
<jsp:include page="../masterFooter.jsp" />

<div id="popUp"></div>
<script type="text/javascript">
function deleteAsset(assetUUID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure delete Asset?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='removeAssetManagement(\""+assetUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function cancel(){
	$('.mask').hide();
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
function removeAssetManagement(assetUUID){
	 $("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
	
	 $("#asset"+assetUUID).empty();
	 $("#asset"+assetUUID).html("<td colspan='7'  id='loading"+assetUUID+"'></td>");
	 $("#loading"+assetUUID).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	
	 $.ajax({
	   	type: "POST",
	    url: atsUrl+"/removeAssetManagement?"+tokenParamName+"="+tokenValue+"&assetUUID="+assetUUID,	    	   
	    success: function(result) {
  			if (result && result.response === "INVALID_DATA") {
			    showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				location.replace(atsUrl + "/assetManagement");
				/*  var table = $('#assetManagementList').DataTable();
	  		     table.row("#asset"+assetId).remove().draw( false );  */
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			}
	    }
   });
}
</script>
