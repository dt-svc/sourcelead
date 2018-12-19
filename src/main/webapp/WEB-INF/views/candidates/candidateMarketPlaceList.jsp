<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Source Lead :: Marketplace List</title>
<div id="popUp"></div>
    <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Marketplace List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/candidatesList'/>">Candidates</a> &raquo; Marketplace</div>
    </section>
    <div class="timesheet_out">
      <div class="tablelist-wrap">
        
        <div class="timesheet_head minHeight40">
          <div class="clearfix"></div>
          <div class="col-md-6 mt10">
          
          <div class="row">
          		<div class="col-md-3">
					<button type="button" class="btn btn-primary btn-block" onClick="setPrice();">
						<i class="fa fa-dollar paddingRight"></i>Set Price
					</button>
          		</div>
          		<div class="col-md-9 customSetprice">
          				<div class="form-inline">
          					<div class="form-group">
          						<input type="text" class="form-control" id="amountForAll" onkeypress="return isNumbersPrice(event);" placeholder="Enter">
          					</div>
          					<div class="form-group dropdown-select bgPosition">
          						<select id="priceTypeVal">
          						<option value="" selected>--Select--</option>
								<option>INR</option>
								<option>AUD</option>
								<option>USD</option>
								<option>GBP</option>
								<option>SGD</option>
								<option>EUR</option>
								</select>
          					</div>
          					<div class="form-group">
          						<button type="button" onclick="setValuesForAllMCandidates();" class="btn btn-primary">Set</button>
          					</div>
          				</div>			
          		</div>
          </div>
          
          </div>
        </div>
        <div class="clearfix mt"></div>
        	<div id="marketPlaceTableDiv" class="project_wrap_table datatable_filter"></div>
        </div>
        <div class="footerstyelsNew mt clearfix">
        <div class="col-lg-12">
        <div class="pull-right">

          <sec:authorize access="hasAnyRole(\'ROLE_SHARE_PROFILE\',\'ROLE_ADD_PROFILE\')"> 

        <input type="button" onclick="callValidatePriceAmount();" class="btn btn-success btnSuccess ml" value="Share">
         </sec:authorize> 
        <input type="button" class="btn btn-default ml" onclick="cancelPriceAmount();" value="Cancel">
        </div>
        </div>
        </div>
      </div>
  </section>
<script type="text/javascript">
function setValuesForAllMCandidates(){
	var amount = $("#amountForAll").val();
	var amountType = $("#priceTypeVal").val();
	var val = parseFloat($('#amountForAll').val());
	if (isNaN(val) || (val === 0))
	{
		popupDisplay("Please enter proper amount");
		return false;
	} 
	/* var formatDigit = /^\d+\.\d\d$/
	if(!formatDigit.test(amount)){
		popupDisplay("Please enter two digits");
		return false;
	} */
	
	var format = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,<>\/?]/;
	if(format.test($('#amountForAll').val()) || isNaN($('#amountForAll').val())){
		popupDisplay("Please enter proper amount");
		return false;
	}
	if(amount != '' && amountType != ''){
		$("input[id='priceVal']").each(function() {
			$(this).val(amount);
		});
		$("select[id='amountTypeEach']").each(function(){
			$(this).val(amountType);
		});
		if($("#amountForAll").val() != ''){
			$('input[id="sharedcheckbox"]').prop('checked', 'checked');
			$('input[id="sharedcheckboxAll"]').prop('checked', 'checked');
		}
	}else {
		popupDisplay("Please enter amount and type");
	}
	
	
}

function callValidatePriceAmount(){
	var flagAtleastOneCheck = false;
	var flagRowForEnteredPrice = false;
	var flagPriceForSelectedRow = false;
	var flagPriceTypeForEnteredPrice = false;
	var flagPriceForEnteredPriceType = false;
	var flagPriceValueEnterSpecialChar = false;
	var flagPriceValueEnterZeroValue = false;
	var flagPriceValueDotTwoDigit = false;
	if(!$('input[id="sharedcheckbox"]').is(":checked")){
		flagAtleastOneCheck = true;
	}
	$("input[id='priceVal']").each(function() {
		if($(this).val() != ""){
			flagAtleastOneCheck = false;
		}
	});
	
	$("#sharedcheckbox:checked").each(function() {
		if($(this).closest('tr').find("input[id='priceVal']").val() == ''){
			flagPriceForSelectedRow = true;
		}
    });

	$("input[id='priceVal']").each(function() {
		if($(this).closest('tr').find("input[id='priceVal']").val() == ''){
			if($(this).closest('tr').find("select[id='amountTypeEach']").val() != ''){
				flagPriceForEnteredPriceType = true;
			}
		}
    });
	
	$("input[id='priceVal']").each(function() {
		if($(this).val() != ""){
			if(!$(this).closest('tr').find("input[type='checkbox']").is(":checked")){
				flagRowForEnteredPrice = true;
			}
		}
	});
	
	$("input[id='priceVal']").each(function() {
		if($(this).val() != ""){
			if($(this).closest('tr').find("select[id='amountTypeEach']").val() == ''){
				flagPriceTypeForEnteredPrice = true;
			}
		}
	});
	
	$("input[id='priceVal']").each(function() {
		var format = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,<>\/?]/;
		if(format.test($(this).val())){
			flagPriceValueEnterSpecialChar = true;
		}
	});
	
	$("input[id='priceVal']").each(function() {
		if(isNaN($(this).val()) || (val === $(this).val())){
			flagPriceValueEnterZeroValue = true;
		}
	});
	
	var amountText = $("#priceVal").val();
	var val = parseFloat($('#priceVal').val());
	if(amountText.length < 1 || amountText.length > 5){
		popupDisplay("Please enter 1 to 5 digits");
		return false;
	}else if (isNaN(val) || (val === 0))
	{
		popupDisplay("Please enter proper amount");
		return false;
	} 
	
	if(flagPriceValueEnterZeroValue){
		popupDisplay("Please enter proper amount");
		return false;
	}
	
	if(flagPriceValueEnterSpecialChar){
		popupDisplay("Please enter proper amount");
		return false;
	}
	
	if(flagRowForEnteredPrice){
		popupDisplay("Please select checkbox for entered price");
		return false;
	}
	
	if(flagPriceForSelectedRow){
		popupDisplay("Please enter price for selected row");
		return false;
	}
	
	if(flagAtleastOneCheck){
		popupDisplay("Please select at least one Candidate");
		return false;
	}
	
	if(flagPriceTypeForEnteredPrice){
		popupDisplay("Please select amount type");
		return false;
	}
	
	if(flagPriceForEnteredPriceType){
		popupDisplay("Please enter amount for amount type");
		return false;
	}
	
	var val = parseFloat($('#priceVal').val());
	if (isNaN(val) || (val === 0))
	{
		popupDisplay("Please enter proper amount");
		return false;
	}
		
	var marketCandidateObj = [];
	$("input:checkbox[id='sharedcheckbox']:checked").each(function(){
		var objectValue = {};
		objectValue = {
				"candidateId" : $(this).closest('tr').find("input[id='candidateId']").val(),
				"marketPrice" : $(this).closest('tr').find("input[id='priceVal']").val(),
				"marketRateCurrencey" : $(this).closest('tr').find("select[id='amountTypeEach']").val()
		}
		marketCandidateObj.push(objectValue);
 	});
	
	$.ajax({
		url : sourceleadUrl + "/saveMarketPlaceData",		
		type : "POST",
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
	    'dataType': 'json',
		data : JSON.stringify(marketCandidateObj),	
		success : function(result) {
		 if (result.response === "AWKWARD") {
                location.href = "/errorPage";
            }else if(result.response === 'SUCCESS') {
            	location.href = "/app/globalMarketplacePage";
            }else{
            	 showError("Operation failed, please try again");
            }
		}
	});
	
	
	
}
function popupDisplay(message){
	$(".project_inn_mask").remove();
		
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a'>";
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

function toggleMarketPlaceAllCheck(source){
	if($(source).is(":checked")){
		  $('input[id="sharedcheckbox"]').prop('checked', 'checked');
	}else{ 
		  $('input[id="sharedcheckbox"]').removeProp('checked');
	}
}

$(document).ready(function() {
	loadMarketPlaceDraftData();
});

function loadMarketPlaceDraftData(){
	
	$("#marketPlaceTableDiv").empty();
	var result = "";
	result = result + "<table id='marketPlaceTable' class='display' cellspacing='0' width='100%'><thead>";
	result = result + '<tr><th class="text-center"><input type="checkbox" onclick="toggleMarketPlaceAllCheck(this);" id="sharedcheckboxAll" onclick=""/></th>';
	result = result + "<th>Generated ID</th>";
	result = result + "<th>Name</th>";
	result = result + "<th>Email</th>";
	result = result + "<th>Primary Skills</th>";
	result = result + "<th>Experience</th>";
	result = result + "<th>Current Rate</th>";
	result = result + "<th>Expected Rate</th>";
	result = result + "<th>Price</th>";
	result = result + "</tr>";
	result = result + "</thead>";
	result = result +"<tbody>"; 
    result = result +" </tbody>";
    result = result +"</table>";
    $("#marketPlaceTableDiv").append(result);
    marketPlacePagination();
}

function marketPlacePagination(){
	var colIndex = 0;
    $('#marketPlaceTable').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "searchHighlight": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: sourceleadUrl + "/getMarketPlaceCandidateMovedList",
            method: 'POST',
            pages: 10
        }),
        "columns": [
        	{ "data": "id" },
            { "data": "generatedId"},
            { "data": "firstName"},
            { "data": "email" },
            { "data": "primarySkill" },
            { "data": "experienceMonths"},
            { "data": "currentRate" },
            { "data": "expectedRate" },
            { "data": "price"}
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"sortable": false,
    	  	"data": "id",
    	  	"render": function(data, type, full, meta) {
				var result = "";
    	  		 result = result + '<input type="checkbox" id="sharedcheckbox" />';
	        		result = result + "<input type='hidden' id='candidateId' value='"+full.id+"'/>";
    	  		 return result; 
    	  	}
        	},  {
	    	  	"targets": colIndex++,
	    	  	"data": "generatedId",
	    	  	"sortable": false,
	    	  	"render": function(data, type, full, meta) {
	    	  		var result = "";
	    	  		if(full.generatedId != null && full.generatedId != '' && full.generatedId != "undefined"){
	            	  		result = result +"<div class='tblminwidth-30'><span title='"+full.generatedId+"'>"+full.generatedId+"</span></div>";
	              	}else{
	            	  		result = result +"<div>--</div>";
	             	}
		              return  result;
	    	  	}
	      }  ,{
            "targets": colIndex++,
            "data": "firstName",
            "render": function ( data, type, full, meta ) {
            		var result = "";
            		var lastName = "";
            		var middleName = "";
            		if(full.lastName != null && full.lastName != "" && full.lastName != 'undefined'){
	            		lastName = full.lastName;
	            	}else{
	            		lastName = "";
	            	}
            		if(full.middleName != null && full.middleName != "" && full.middleName != 'undefined'){
	            		middleName = full.middleName;
	            	}else{
	            		middleName = "";
	            	}
	               result = result +"  <td><div class='tbldata-dotdot-100' onclick='#'><span title='"+full.firstName+" "+middleName+" "+lastName+"'>"+full.firstName+" "+middleName+" "+lastName+"</span></div></td>";
	              return result;
            }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "email",
	    	  	"render": function(data, type, opt, meta) {
	    	  		return "<div ><span title='"+opt.email+"'>"+opt.email+"</span></div>";
	    	  	}
	    	  },{
              "targets": colIndex++,
              "data": "primarySkill",
              "render": function ( data, type, full, meta ) {
              		var result = "";
              		if(full.primarySkill != null && full.primarySkill != '' && full.primarySkill != "undefined"){
  	            	  		result = result +"<div><span title='"+full.primarySkill+"'>"+full.primarySkill+"</span></div>";
  	              	}else{
  	            	  		result = result +"<div>--</div>";
  	             	}
  	              return result;
              }
            }, {
                "targets": colIndex++,
                "data": "experience",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		var experienceYears='';
                		if(opt.experienceYears == null || opt.experienceYears == "null" || opt.experienceYears == '' || opt.experienceYears == "undefined"){
                			experienceYears = "--, ";
   	             	 	}else{
   	             	 		experienceYears = opt.experienceYears+" Years, ";
   	             	 	}
                		var experienceMonths='';
                		if(opt.experienceMonths == null || opt.experienceMonths == "null" || opt.experienceMonths == '' || opt.experienceMonths == "undefined"){
                			experienceMonths = "--";
   	             	 	}else{
   	             	 		experienceMonths = opt.experienceMonths+" MOnths";
   	             	 	}
                		var experience = experienceYears+experienceMonths;
                		result = result + "<td><div class='tblminwidth-50' onclick='#'><span title='"+experience+"'>"+experience+"</span></div></td>";
   	            	  
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "currentRate",
	    	  	"sortable": false,
	    	  	"render": function(data, type, full, meta) {
	    	  		var currentRate = "";
	    	  		if(full.currentRate != null && full.currentRate != 'undefined' && full.currentRate != ""){
	    	  			currentRate = full.currentRate;
		            }else{
		            	currentRate = "--";
		            }
	    	  		return "<td><div class='tblminwidth-30' onclick='#'><span title='"+currentRate+"'>"+currentRate+"</span></div></td>";
	    	  	}
	    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "expectedRate",
		    	  	"sortable": false,
		    	  	"render": function(data, type, full, meta) {
		    	  		var expectedRate = "";
		    	  		if(full.expectedRate != null && full.expectedRate != 'undefined' && full.expectedRate != ""){
		    	  			expectedRate = full.expectedRate;
			            }else{
			            	expectedRate = "--";
			            }
		    	  		return "<td><div class='tblminwidth-30' onclick='#'><span title='"+expectedRate+"'>"+expectedRate+"</span></div></td>";
		    	  		
		    	  		
		    	  	}
		    	  }, {
	    	    	  	"targets": colIndex++,
	    	    	  	"data": "price",
	    	    		"sortable": false,
	    	    	  	"render": function(data, type, opt, meta) {
	    	    	  		var result = "";
	    	    	  		result = result + '<td><div class="tblminwidth-75 clearfix"><div class="dropdown-inputbox"><input type="text" id="priceVal" value="" placeholder="Enter" class="form-control input-sm"><select id="amountTypeEach" value="${SourceLeadWeb_currencyCode}" class="form-control input-sm sel">';
	    	    	  		result = result + ' <option  ${SourceLeadWeb_currencyCode  == "" ? "selected" : ""} value="">--Select--</option>';
	    	    	  		result = result + ' <option  ${SourceLeadWeb_currencyCode  == "INR" ? "selected" : ""} value="INR">INR</option> ';
	    	    	  		result = result + ' <option  ${SourceLeadWeb_currencyCode  == "AUD" ? "selected" : ""} value="AUD">AUD</option>';
	    	    	  		result = result + ' <option  ${SourceLeadWeb_currencyCode  == "USD" ? "selected" : ""} value="USD">USD</option>';
	    	    	  		result = result + ' <option  ${SourceLeadWeb_currencyCode  == "GBP" ? "selected" : ""} value="GBP">GBP</option> ';
	    	    	  		result = result + ' <option  ${SourceLeadWeb_currencyCode  == "SGD" ? "selected" : ""} value="SGD">SGD</option>';
	    	    	  		result = result + ' <option  ${SourceLeadWeb_currencyCode  == "EUR" ? "selected" : ""} value="EUR">EUR</option>';
	    	    	  		result = result + '</select></div></div></td>';
	    	    	  		return result;
	    	    	  	}
    	    	  } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    // Removing pagination for market place list 
     $('#marketPlaceTable_length').remove(); // removes the pagination values.
    $('#marketPlaceTable_info').remove(); // removes the pagination information.
    $('#marketPlaceTable_paginate').remove(); // removes the pagination numbers.
}

$(".funPrice").on("click", "li", function(){
	var x = $(this).text();
	$(this).parents(".selectPriceli").find(".selectPrice").text(x);
});
function setPrice(){
	$(".customSetprice").toggle(function(){
		if($(this).is(":hidden")){
			$("input[id='priceVal']").each(function() {
				$(this).val('');
			});
			$("select[id='amountTypeEach']").each(function(){
				$(this).val('');
			});
			$('input[id="sharedcheckbox"]').removeProp('checked');
			$('input[id="sharedcheckboxAll"]').removeProp('checked');
			$("#amountForAll").val('');
			$("#priceTypeVal").val('');
		}
	});
}

function cancelPriceAmount(){
	$("input[id='priceVal']").each(function() {
		$(this).val('');
	});
	$("select[id='amountTypeEach']").each(function(){
		$(this).val('');
	});
	$('input[id="sharedcheckbox"]').removeProp('checked');
	$('input[id="sharedcheckboxAll"]').removeProp('checked');
	$("#amountForAll").val('');
	$("#priceTypeVal").val('');
}
</script>	
<jsp:include page="../masterFooter.jsp" /> 	