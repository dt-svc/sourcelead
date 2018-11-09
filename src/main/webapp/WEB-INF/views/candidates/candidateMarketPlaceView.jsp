<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section class="content-wraper">
  <section class="content-header clearfix">
  
   <div class="success_pop" id="candSelector" style="display: none;"> 
     <i class="fa fa-check-circle"></i><span id="candMsgDiv"></span> 
   </div>
   
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="sucesspop"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
    <div class="pull-left">
      <h2 id="headerLbl">Marketplace List</h2>
    </div>
    
    <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <label id="brudCrumLbl">Marketplace List </label> </div>
    </c:if> 
  </section>
  <div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
         <li id="" ><a href="${appPath}/candidatesList">Candidate List</a></li>
         <li id="" class="current"><a href="javascript:void(0)" >Marketplace</a></li>
         <li id=""><a href="javascript:void(0)" onclick="javascript:goToGlobalMarketPlaceList();">Global Marketplace</a></li>
         <div class="clearfix"></div>
     </ul>
</div>
    <!-- Content Area -->
      <div class="tablelist-wrap">
         <div class="immigrationWrap noPad clearfix mt5">
		<div class="immigraInnWrap">
		<!-- Filter Global market place-->
		<div class="positionRelative reportHead clearfix">
     	 <ul class="pull-right list-unstyled list-inline">
		   <span><input class="immigraTabSerach" id ="globalMarketSearh" placeholder="Search" type="text"></span>
        </ul>
      </div>
	<div id="popUp"></div>
	<div id="dynamicGlobalMarketCandidateViewDiv" class="project_wrap_table datatable_filter"></div>
    </div>
    </div>
    </div>
    </section>
  <script>
  function goToCandidateList(){
		location.href = "${appPath}/candidatesList";
	}
  </script>
  <script type="text/javascript">
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
	     result = result + '<th>Price</th>';
	     result = result + '<th>Action</th>';
	     result = result + '</tr>';
	     result = result + '</thead>';

	     result = result + '</table>';

	     $("#dynamicGlobalMarketCandidateViewDiv").html(result);
	     
         var sourceleadUrl = '${sourceleadUrl}';
	     
	     var searchValue = $("#globalMarketSearh").val();
	     searchValue = searchValue ? searchValue : "";
	     
	     var url ="";
	     url = sourceleadUrl + "/getOrgMarketplaceList?searchValue="+searchValue;  
	     
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
	             {"data": "Profile %"},
	             {"data": "Price"},
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
	                    	 result = result + "<td><div class='tblminwidth-75' title = '"+opt.currentRate+"'>" + opt.currentRate + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tblminwidth-75'>---</div></td>";
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
	                    	 result = result + "<td><div class='tblminwidth-75' title = '"+opt.expectedRate+"'>" + opt.expectedRate + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tblminwidth-75'>---</div></td>";
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
	                    	 result = result + "<td><div class='tblminwidth-50' title = '"+opt.profilePercentage+"%'>" + opt.profilePercentage + "%</div></td>";
	                     } else {
	                         result = result + "<td><div class='tblminwidth-50'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "price",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.marketPrice !== null && opt.marketPrice !== '' && opt.marketPrice !== 'undefined') {
	                    	 result = result + "<td><div id='marketPlacePriceDiv'><div id='marketPlacePriceValDiv' class='tblminwidth-100' title = '"+opt.marketPrice+" "+ opt.marketRateCurrencey+"'>" + opt.marketPrice + " "+ opt.marketRateCurrencey+"</div></div></td>";
	                     } else {
	                         result = result + "<td><div class='tblminwidth-100'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             },
	             
	             {
	                 "targets": colIndex++,
	               /*   "data": "action", */
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     '<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')">'; 
	                    	 result = result + "<td><div class='tblminwidth-100' id='marketLinkDiv' ><a id='editMarketLink' onclick='editMarketPriceFun(\""+opt.id+"\",\""+opt.marketPrice+"\",\""+opt.marketRateCurrencey+"\", this);' title='Edit'>Edit</a> <a onclick = 'withdrawCandidateFromMarketPlace(\""+opt.id+"\")'>&nbsp;Withdraw</a></div></td>";
	                     '</sec:authorize>'
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
	
</script>
<script type="text/javascript">
/*function funPrice(event){
	var price = event.target.text || event.srcElement.text;
	$(".selectPrice").text(price);
	
}*/
function editMarketPriceFun(id, marketPrice, marketPriceCurrency, obj){
	//alert(marketPrice+" "+marketPriceCurrency);
	$(obj).closest('tr').find("div[id='marketPlacePriceValDiv']").empty();
	var result = "";
		result = result + '<div class="tblminwidth-100 clearfix"><div class="dropdown-inputbox"><input type="text"  onkeypress="return isNumberKey(event)" id="priceVal" value="'+marketPrice+'" placeholder="Enter" class="form-control input-sm"><select id="amountTypeEach" value="" class="form-control input-sm sel">';
		result = result + ' <option '+(marketPriceCurrency == "" ? "selected" : "")+' value="">--Select--</option>';
		result = result + ' <option '+(marketPriceCurrency == "INR" ? "selected" : "")+' value="INR">INR</option> ';
		result = result + ' <option '+(marketPriceCurrency == "AUD" ? "selected" : "")+' value="AUD">AUD</option>';
		result = result + ' <option '+(marketPriceCurrency == "USD" ? "selected" : "")+' value="USD">USD</option>';
		result = result + ' <option '+(marketPriceCurrency == "GBP" ? "selected" : "")+' value="GBP">GBP</option> ';
		result = result + ' <option '+(marketPriceCurrency == "SGD" ? "selected" : "")+' value="SGD">SGD</option>';
		result = result + ' <option '+(marketPriceCurrency == "EUR" ? "selected" : "")+' value="EUR">EUR</option>';
		result = result + '</select></div></div>';
	$(obj).closest('tr').find("div[id='marketPlacePriceDiv']").empty().append(result);
	'<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')">'; 
	$(obj).closest('tr').find("div[id='marketLinkDiv']").empty().append("<a href='#' id='saveMarketLink' onclick='saveEditMarketPriceFun(\""+id+"\",this);' title='Save'>Save</a><a href='#' id='cancelMarketLink' onclick='cancelEditMarketPriceFun(\""+marketPrice+"\",\""+marketPriceCurrency+"\",\""+id+"\", this);' title='Cancel'>&nbsp;Cancel</a>");
	'</sec:authorize>'
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
function popupDisplay(message){
	$(".project_inn_mask").remove();
		
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a'>";
	/* result = result +"		<  id='reasonForTemplate' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Reason' onkeypress=';' ></textarea>"; */
	result = result +"<div class='modal-body'><p id='popupMsg'></p></div>";
	result = result + ' <div id="reasonForTemplateDiv"/>';
	result = result +"<div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='cancelMarketplacePopup();'></div>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#popupMsg").empty().append(message);
	$("#remove_immigration_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelMarketplacePopup(){
	$("#remove_immigration_pop").fadeOut(200);
	$(".project_inn_mask").fadeOut(200);
}

function saveEditMarketPriceFun(id, obj){
	var  marketPrice = $(obj).closest('tr').find("input[id='priceVal']").val();
	var marketPriceCurrency = $(obj).closest('tr').find("select[id='amountTypeEach']").val();
	//alert(textVal+" : "+optionVal+" : "+id);
	if(marketPrice == ""){
		popupDisplay("Please enter amount!");
		return false;
	}
	$.ajax({
   		type: "POST",
	 	url : sourceleadUrl+"/updateMarketPlacePrice?marketPlaceId="+id+"&priceVal="+marketPrice+"&priceValType="+marketPriceCurrency,
		success: function(result) {
			if (result.response === "AWKWARD") {
                location.href = "/errorPage";
            }else if(result.response === "SUCCESS") {
            	//$(obj).closest('tr').find("div[id='marketPlacePriceDiv']").empty().append("<div id='marketPlacePriceValDiv' class='tbldata-dotdot-sm' title = '"+marketPrice+" "+ marketPriceCurrency+"'>" + marketPrice + " "+ marketPriceCurrency+"</div>");
				//$(obj).closest('tr').find("div[id='marketLinkDiv']").empty().append("<a href='#' id='editMarketLink' onclick='editMarketPriceFun(\""+id+"\",\""+marketPrice+"\",\""+marketPriceCurrency+"\", this);' title='Edit'>Edit</a>")				
				//$(obj).closest('tr').find("div[id='marketPlacePriceDiv']").empty().append("<div id='marketPlacePriceValDiv' class='tbldata-dotdot-sm' title = '"+marketPrice+" "+ marketPriceCurrency+"'>" + marketPrice + " "+ marketPriceCurrency+"</div>");
				//$(obj).closest('tr').find("div[id='marketLinkDiv']").empty().append("<a href='#' id='editMarketLink' onclick='editMarketPriceFun(\""+id+"\",\""+marketPrice+"\",\""+marketPriceCurrency+"\", this);' title='Edit'>Edit</a>")
				getDynamicGlobalMarketCandidateList();
			}else{
            	 showError("Operation failed, please try again");
            }
		}
	});
}
function cancelEditMarketPriceFun(marketPrice, marketPriceCurrency, id, obj){
	$(obj).closest('tr').find("div[id='marketPlacePriceDiv']").empty().append("<div id='marketPlacePriceValDiv' class='tblminwidth-100' title = '"+marketPrice+" "+ marketPriceCurrency+"'>" + marketPrice + " "+ marketPriceCurrency+"</div>");
	'<sec:authorize access="hasAnyRole(\'ROLE_ADD_PROFILE\',\'ROLE_WITHDRAW_PROFILE\')">'; 
	$(obj).closest('tr').find("div[id='marketLinkDiv']").empty().append("<a href='#' id='editMarketLink' onclick='editMarketPriceFun(\""+id+"\",\""+marketPrice+"\",\""+marketPriceCurrency+"\", this);' title='Edit'>Edit</a><a onclick = 'withdrawCandidateFromMarketPlace(\""+id+"\")'>&nbsp;Withdraw</a>")//
	'</sec:authorize>'
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
function goToGlobalMarketPlaceList(){
	location.href = "${appPath}/globalMarketplacePage";
}


function cancelMarketPlace(){
	$("#remove_marketPlace_pop, .project_inn_mask").fadeOut();
	$("#reasonForMarketPlace").val('');
	$("#reasonForMarketPlaceDiv").find("p.jquery_form_error_message").remove();
	$("#reasonForMarketPlaceDiv").empty(); 
}
function withdrawCandidateFromMarketPlace(id){ 
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	<div id='remove_marketPlace_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"	<textarea  id='reasonForMarketPlace' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Reason' onkeypress=';' ></textarea>";
	result = result + ' <div id="reasonForMarketPlaceDiv"/>';
	result = result +"	<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Withdraw' onclick='withdrawCandidate("+id+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelMarketPlace();'></b>";
	result = result +"	</div>";
	$("#popUp").empty().append(result);
	$("#remove_marketPlace_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
function withdrawCandidate(id) {
    var reason = $("#reasonForMarketPlace").val();
    if(reason.match(/^[^\s-](.*)*$/) != null && reason !="" && reason.length <= 250){
    $("#reasonForMarketPlaceDiv").empty();
   $.ajax({
        url: sourceleadUrl + "/withdrawCandidateFromMarketPlace?id="+id+"&withdrawReason="+reason,
        type: "GET",
        data: id,
        success: function (result) {
        	if (result.response === "AWKWARD") {
                location.href = "/errorPage";
            }else if(result.response === "SUCCESS") {
              	$("#remove_marketPlace_pop").fadeOut(200);
	    		$(".project_inn_mask").fadeOut(200);
	    		dynamicGlobalMarketCandidateListPagination(sourceleadUrl + "/getOrgMarketplaceList?searchValue="+$("#globalMarketSearh").val());
            }else{
           	 showError("Operation failed, please try again");
           }}
    });
    }else if (reason.length >= 251){
    	$("#reasonForMarketPlaceDiv").find('p.jquery_form_error_message').remove();
		$("#reasonForMarketPlaceDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason Length 1-250 characters</font>") ; 
    }else{
    	$("#reasonForMarketPlaceDiv").find('p.jquery_form_error_message').remove();
		$("#reasonForMarketPlaceDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
    }
}



</script>
<jsp:include page="../masterFooter.jsp" /> 	