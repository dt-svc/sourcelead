<html lang="en">
<head>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <!-- Content Area -->
    </head>
    <body>
    	  <div class="wraper">
    <section class="content-wraper">
    <section class="content-header clearfix">
    <div class="pull-left">
      <h2 id="headerLbl">Cart</h2>
    </div>
      <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; <label id="brudCrumLbl"><a href="${SourceLeadWeb_atsUrl}/globalMarketplacePage">Global Marketplace List</a></label> &raquo; <label id="brudCrumLbl">Cart</label> </div>
	  </c:if>
	  </section>
	  <div id="popUp"></div>
 	<div class="clearfix"></div>
	  <div class="clearfix">
			<ul class="project-tabs-menu projecttabs-custom clearfix">
		         <li id="" ><a href="${SourceLeadWeb_atsUrl}/candidatesList">Candidate List</a></li>
		         <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
		         <li id=""><a href="javascript:void(0)" onclick="javascript:goToGlobalMarketPlaceList();">Global Marketplace</a></li>
		         <li id="" class="current"><a href="javascript:void(0)">Cart</a></li>
		         <li id=""><a href="javascript:void(0)" onclick="javascript:goToPurchaseOrderList();">Purchase Orderlist</a></li>
            	 <li id=""><a href="javascript:void(0)" onclick="javascript: goToSalesOrderList();">Sales Orderlist</a></li>
                 <li id=""><a href="javascript:void(0)" onclick="javascript:goToWalletList();">Wallet</a></li> 
		     </ul>
     
  <div class="clerfix row">
    <div class="col-md-12">
      <div class="profileDetails">
        <div class="row reportHead">
          <div class="col-md-12"> <span class="pull-left refreIcon noPad mt5"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="refreshPage();"></a><!-- <h4 style="display:inline-block">Cart View</h4> --></span>
            <ul class="pull-right mb0 list-unstyled list-inline ulliBorder">
              <li>
				  <i class="inlineBlock pull-left mr10 trashIcon" title="Cart"></i>
				  <div class="checkbox-new pull-left">
                      <input id="trashHdr" class="checkbox-custom" name="paidComponent" type="checkbox">
                      <label for="trashHdr" class="checkbox-custom-label"></label>
                    </div>
				  <a class="pull-left mt2 mr10" onclick="deleteAllCandidatesFromMarketcart()" title="Delete All" id="deleteAll" style="display: none;"><small class="ml10 colorRed">Delete All</small></a>
				</li>
				 <sec:authorize access="hasRole('ROLE_VIEW_CART')">
              <li><i class="shopping-cart inlineBlock pull-left mr10" title="Cart"></i>
				  <div id = "cartCount" class="inlineBlock pull-left mt2 mr10 animated zoomInDown" href="javascript:void(0)">0</div></li>
              <li><i class="dollarSvg inlineBlock pull-left mr10" title="Cart"></i>
				  <div id = "totalPrice" class="animated zoomInDown inlineBlock pull-left mt2 mr10" href="javascript:void(0)">0.00</div></li>
				  </sec:authorize>
              <li> <a href="javascript:void(0)" class="sortSvg mr10" title="Sorting"></a> </li>
				<li> <a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a> </li>
              <li class="mr">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getCandidateDetailsFromMarketcart()"></span></li>
                 <sec:authorize access="hasRole('PURCHASE_PROFILE')">
              <li class="noMar">
                <button type="button" onclick="initMarketplacePayment()" class="btn btn-primary" id="checkOutButton1"><i class="fa fa-send paddingRight"></i>Check Out</button>
                </li>
                </sec:authorize>
            </ul>
          </div>
        </div>
        <div class="clearfix row">
          <div class="col-md-8">
            <div class="scrollContent">
            
              <div id="gridView">
                <!-- Dynamic gridView here... -->
				</div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="mt"></div>
            <div class="cartInvoice">
              <div class="cartInvoiceHeader">
                <h5 class="margin-top0">Invoice</h5>
              </div>
               <sec:authorize access="hasRole('PURCHASE_PROFILE')">
              <div class="cartInvoiceBody">
                <div class="cartInvoiceTable clearfix">
					<table class="table">
						<thead>
						<th class="text-left text-muted">Details</th>
						<th class="text-center  text-muted">Qty</th>
						<th class="text-right text-muted">Amount</th>
						</thead>
						<tbody>
							<tr>
								<td><div class="text-left">Total</div></td>
								<td><div id = 'invoiceCartCount' class="text-center">0</div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i><span id = 'invoiceCartTotal'>0.00</span></div></td>
							</tr>
							<tr>
								<td><div class="text-left">Tax</div></td>
								<td><div class="text-center"></div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i><span id = 'invoiceTax'>0.00</span></div></td>
							</tr>
							<tr>
								<td><div class="text-left">Other</div></td>
								<td><div class="text-center"></div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i><span id = 'invoiceOther'>0.00</span></div></td>
							</tr>
							<tr>
								<td><div class="text-left">Discount</div></td>
								<td><div class="text-center"></div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i><span id = 'invoiceDiscount'>0.00</span></div></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="2"><div class="text-left">Total Amount</div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i><span id= 'invoiceTotalAmount'>0.00</span></div></td>
							</tr>
						</tfoot>
					</table>
                </div>
              </div>
              </sec:authorize>
                <sec:authorize access="hasRole('PURCHASE_PROFILE')">
              <div class="cartInvoiceBodyFooter">
                <div class="clearfix">
                  <div class="pull-right"> <a onclick="initMarketplacePayment()" href="javascript:void(0)" class="btn btn-primary mr" id = "checkOutButton2"><i class="fa fa-send paddingRight"></i>Check Out</a> <a class="btn btn-default" href="${SourceLeadWeb_atsUrl}/globalMarketplacePage">Cancel</a> </div>
                </div>
              </div>
              </sec:authorize>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
     
     
      </div>
   
	</section>
	</div>  
	
	
<div class="advancedsearch-mask"></div>
<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-medium">
    <h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a> </h5>
    <div class="addInner">
      <div class="row">
        <div class="col-md-6 mb positionRelative">
          <!-- <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myManagersInput" onkeyup="myManagersFunction()" placeholder="Added On" title="Added On" type="text">
            <span class="cursorPointer" title="Add on"><i class="fa fa-search"></i></span> </div> -->
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="MarketCartCheckBox">
              <li class=" positionRelative">
                <input class="dateCheckBox" name="MarketCartDate" value="today" type="radio" >
                <label>Today</label>
              </li>
              <li class="positionRelative">
                <input class="dateCheckBox" name="MarketCartDate" value="1_week" type="radio">
                <label>This Week</label>
              </li>
              <li class="positionRelative">
                <input class="dateCheckBox" name="MarketCartDate" value="Last_Week" type="radio" >
                <label>last Week</label>
              </li>
             <li class="positionRelative">
                <input class="dateCheckBox" name="MarketCartDate" value="1_month" type="radio">
                <label>One Month</label>
              </li>
              <li class="positionRelative">
                <input class="dateCheckBox" name="MarketCartDate" value="1_Month_Ago" type="radio">
                <label>One Month Ago</label>
              </li>
              <li class="positionRelative">
                <input class="dateCheckBox" name="MarketCartDate" value="YTD" type="radio">
                <label>This Year</label>
              </li>
              <li class="positionRelative">
                <input class="dateCheckBox" name="MarketCartDate" value="custom" type="radio" id = "customDateCheckBox">
                <label>Custom</label>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-md-6 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myRolesInput1" onkeyup="myRolesFunction()" placeholder="Added By" title="Type in a roles" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="myRolesUL">
              <li class="positionRelative">
                <input name="userRole" value="2366" type="checkbox">
                <label>Admin De</label>
              </li>
              <li class="positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>Client</label>
              </li>
              <li class="positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>Customer</label>
              </li>
              <li class="positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>Other</label>
              </li>
            </ul>
          </div>
        </div>
        <div class="clearfix"></div>
   
	
	<div class="clearfix" id = "customDates" style="display: none;"> 
		<div class="col-md-12 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
          <h5>Custom Dates<span class="text-danger paddingLeft">*</span></h5>
            <ul class="list-unstyled positionRelative filterSearch" id="">
              <li class="positionRelative mb">
                <div class="row">
                <div class="col-md-6"  id= "cartFromDiv">
                	<input type="text" value="" class="form-control calendar" placeholder="From Date" id="creationStartDate">
                </div>
                <div class="col-md-6"  id= "cartFromDiv">
                	<input type="text" value="" class="form-control calendar" placeholder="To Date" id="creationEndDate">
				</div>
             </div>
              </li>
            </ul>
			</div>          
        </div>
           </div>
    </div>
	</div> 
	
<div class="clearfix"></div>
    <div class="clearfix advancedsearch-footer">
      <div class="pull-right mr">
        <input value="Apply" id="search_requiremet" onclick="validateMarketCartFilter();" class="btn btn-sm btn-success" type="button">
        <input value="Reset" id="clear_search" onclick="resetMarketCartFilter();" class="btn btn-sm btn-primary" type="button">
      </div>
    </div>
  </div>
  <jsp:include page="../masterFooter.jsp" />
</body>	
<script type="text/javascript">
var atsUrl = '${SourceLeadWeb_atsUrl}';
$(document).ready(function () {
	getCandidateDetailsFromMarketcart();
	/* updateInvoiceDetails(); */
	validateMarketCartFilter();

});

function initMarketplacePayment() {
     $.ajax({
      type: "POST",
	   url: "/app/init-marketplace-payment",
  	success: function(data) {
  		 if (data) {
                location.href = data;
            }
        	
        },  
		 }); 
}

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
function goToOrgMarketPlaceList(){
	location.href = "${SourceLeadWeb_atsUrl}/OrgMarketplace";
}

function goToGlobalMarketPlaceList(){
	location.href = "${SourceLeadWeb_atsUrl}/globalMarketplacePage";
}

function goToPurchaseOrderList(){
	location.href ="${SourceLeadWeb_atsUrl}/PurchaseOrderList";
	}
function goToSalesOrderList(){
	location.href = "${SourceLeadWeb_atsUrl}/SalesOrderList";
}

function goToWalletList(){
    location.href = "${SourceLeadWeb_atsUrl}/walletList";
}

function getCandidateDetailsFromMarketcart(customDate, fromDate, toDate){
	var candidateLength = 0;
	 if(customDate == null || customDate == "undefined"){
			customDate = "";
			}
			
			if(fromDate == null || toDate== null){
				fromDate = "";
				toDate = "";
			}
	/* var checkedDate = $('input[name=MarketCartDate]:checked').val();
	var creationStartDate = $("#creationStartDate").val();
	var creationEndDate = $("#creationEndDate").val();  */
	var searchValue = $("#dynamicUserDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	$.ajax({
		type : "POST",
		url : atsUrl +"/getCandidateDetailsFromMarketcart?searchValue="+searchValue+"&creationStartDate="+fromDate+"&creationEndDate="+toDate+"&checkedDate="+customDate,
		async : false,
		success : function(object) {
			var result ="";
			if(object != null && object.length != 0){
				for(var i=0;i<object.length;i++){
				result = result + "<div class='mt'></div>";
				result = result + "<div class='media profileDetails-Widget positionRelative'><a class='removeSelected mt' title='Remove Selected' onclick = 'deleteCandidateFromMarketcart(\""+(object[i].fullName == null ? '---' : object[i].fullName)+"\" , \""+object[i].candidateUuid+"\");'><i class='fa fa-trash-o mt5'></i></a>";
				result = result + "<div class='media-left'>";
				result = result + "<div class='cartImg positionRelative'>";
				result = result + "<img onerror='predefineProfileImage(this);' src='"+object[i].imageUrl+"' class='media-object' alt='Image'>";
				result = result + "<i class='fa fa-shopping-cart faCart'></i>";
				result = result + "<div class='clearfix'></div>";
				result = result + "<h4 class='text-center'><span class='label label-default'>"+object[i].profilePercentage+"%</span></h4>";
				result = result + "</div></div>";
				result = result + "<div class='media-body'>";
				result = result + "<div class='col-md-9'>";
				result = result + "<h4 class='media-heading mb0'><a href='javascript:void(0)'>"+(object[i].fullName == null ? "---" : object[i].fullName)+"</a></h4>";
				result = result + "<h6 class='text-muted mb5 fontSmall mt5'>"+(object[i].designation == null ? "---" : object[i].designation)+"</h6>";
				result = result + "<div class='clearfix'></div>";
				result = result + "<ul class='list-unstyled list-inline mt5 mb0 skillCart'>";
				result = result + "<li class='mb5' title='Current Rate'><i class='fa fa-location-arrow mr text-info'></i> : <span class='paddingRight'><i class='fa fa-usd'></i></span>"+(object[i].currentRate == null ? "---" : object[i].currentRate)+"<small class='paddingLeft'>/ "+(object[i].currentRateType == null ? "---" : object[i].currentRateType)+"</small></li>";
				result = result + "<li class='mb5' title='Expected Rate'>";
				result = result + "<i class='fa fa-check mr textDanger'></i> : <span class='paddingRight'><i class='fa fa-usd'></i></span>"+(object[i].expectedRate == null ? "---" : object[i].expectedRate)+"<small class='paddingLeft'>/ "+(object[i].expectedRateType == null ? "---" : object[i].expectedRateType)+"</small></li>";
				result = result + "<li class='mb5' title='Total Experience'><i class='fa fa-object-group mr text-warning'></i> : <span class='paddingRight'><i class='fa fa-briefcase text-muted' aria-hidden='true'></i></span>"+(object[i].experienceYears == null ? "---" : object[i].experienceYears )+'.'+(object[i].experienceMonths == null ? "---" : object[i].experienceMonths )+"<small class='text-muted paddingLeft'>(Yrs)</small></li>";
				result = result + "<li class='mb5' title='Location'><span class='paddingRight'><i class='fa fa-map-marker text-muted'></i></span> : "+(object[i].location == null ? "---" : object[i].location)+"</li>";
				result = result + "</ul> <div class='clearfix'></div>";
				if(object[i].skillsName != null){
					var skills = object[i].skillsName.split(',');
					result = result + '<ul class="list-unstyled list-inline profileSkills mb0 mt5">';
					var count = 0;
					for(var entry of skills) {
						result = result + '<li>'+entry+'</li>';
						if(count == 6){
							result = result + '<li> <span class="text-success" title="'+object[i].skillsName+'">more</span> </li>';
							break;
						}
						count++;
					}
					result = result + ' </ul>';
				}
				result = result + "<div class='clearfix'></div></div>"
				result = result + "<div class='col-md-3 text-center'>";
				result = result + "<div class='clearfix mt25'></div>";
				result = result + "<h4><span class='label label-primary'>Price:<i class='fa fa-dollar ml mr5'></i>"+object[i].marketPrice+"</span></h4>";
				result = result + "</div>";
				result = result + "<div class='clearfix'></div>";
				result = result + "<div class='cartinnerFooter'>";
				result = result + "<div class='clearfix'>";
				result = result + "<div class='col-md-6'>";
				result = result + "<label class='mr text-muted'>Added by :</label><span>"+object[i].createdByName+"</span>";
				result = result + "</div>";	
				result = result + "<div class='col-md-6'>";
				result = result + "<label class='mr text-muted'>Added on :</label><span>"+object[i].createdDatestr+"</span>";
				result = result + "</div></div></div></div></div>";
         		}
			candidateLength =  object.length;
			updateInvoiceDetails();
			}
			else{
				$(".ulliBorder > li").first().hide();
				result = result + "<div class='dataTables_empty'></div>";	
				$("#checkOutButton1").attr("disabled", true);
				$("#checkOutButton2").attr("disabled", true);
			}
			$("#gridView").empty().append(result);
		}
		});
	return candidateLength;
	}

function updateInvoiceDetails(){
	var totalPriceInCurrency;
	$.ajax({
		 	url: atsUrl + "/updateMarketplaceCartCountAndPrice",
	        type: 'GET',
	        success: function (object) {
	        	if(object != null){
	        		$("#checkOutButton1").attr("disabled", false);
	        		$("#checkOutButton2").attr("disabled", false);
		        	$("#invoiceCartCount").html(object[0]);
		        	totalPriceInCurrency = new Intl.NumberFormat('en-US', { minimumFractionDigits : 2 }).format(object[1]);
	        		$("#invoiceCartTotal").html((object[1] === null || object[0] === 0) ? "0.00" : totalPriceInCurrency);
	        		$("#invoiceTotalAmount").html((object[1] === null || object[0] === 0) ? "0.00" : totalPriceInCurrency);
	        		}}
		});
	}


function cancelMarketCart(){
	$("#remove_marketCart_pop, .project_inn_mask").fadeOut();
	}
	
function deleteCandidateFromMarketcart(candidateName, candidateUuid){ 
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	<div id='remove_marketCart_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"  <div class='modal-body'> <b>Do you want to remove "+candidateName+" from Market cart!</b> </div>";
	result = result +"	<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteCandidateFromMarketcartByCandidateUuid(\""+candidateUuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelMarketCart();'></b>";
	result = result +"	</div>";
	$("#popUp").empty().append(result);
	$("#remove_marketCart_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	}

function deleteCandidateFromMarketcartByCandidateUuid(candidateUuid){
	var cLength;
	$.ajax({
	 	url: atsUrl + "/deleteCandidateFromMarketcartByCandidateUuid?candidateUuid="+candidateUuid,
		type: 'GET',
		success : function(result){
			if (result.response === "AWKWARD") {
                location.href = "/errorPage";
            }else if(result.response === "SUCCESS") {
            	getCandidateDetailsFromMarketcart();
            	updateInvoiceDetails();
				updateMarketplaceCartCountAndPrice();
            	$("#remove_marketCart_pop").fadeOut(200);
    			$(".project_inn_mask").fadeOut(200);
            }else{
           	 	showError("Operation failed, please try again");
            }
			
		}
	});
	}

//deleteAllCandidatesFromMarketCart
function cancelMarketCartDeleteAll(){
	$("#remove_marketCartDeleteAll_pop, .project_inn_mask").fadeOut();
	}
	
function deleteAllCandidatesFromMarketcart(){ 
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	<div id='remove_marketCartDeleteAll_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"  <b>Do you want to remove All Candidates from Market cart!</b>";
	result = result +"	<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteAllCandidatesForCheckout();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelMarketCartDeleteAll();'></b>";
	result = result +"	</div>";
	$("#popUp").empty().append(result);
	$("#remove_marketCartDeleteAll_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	}

function deleteAllCandidatesForCheckout(){
	$.ajax({
	 	url: atsUrl + "/deleteAllCandidatesFromMarketcart",
		type: 'GET',
		success : function(result){
			if (result.response === "AWKWARD") {
                location.href = "/errorPage";
            }else if(result.response === "SUCCESS") {
            	getCandidateDetailsFromMarketcart();
            	updateInvoiceDetails();
				updateMarketplaceCartCountAndPrice();
				$("#remove_marketCartDeleteAll_pop").fadeOut(200);
    			$(".project_inn_mask").fadeOut(200);
            }else{
           	 	showError("Operation failed, please try again");
            }}
	});
	}


//

var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormatPetitionSave = getJSDateFormat(locationDate);

	function createMarketCartStartDate(){
	$("#creationStartDate").datepicker({
 	dateFormat: finalDateFormatPetitionSave,
 	changeMonth: true,
	  	changeYear: true, 
 	onSelect: function (selected) {
       var dt = $(this).datepicker('getDate', '+1d');
       dt.setDate(dt.getDate() + 1);
       $("#creationEndDate").datepicker("option", "minDate", dt);
   	}
		})
	}
	
	function createMarketCartEndDate(){
	$("#creationEndDate").datepicker({
 	dateFormat: finalDateFormatPetitionSave,
 	changeMonth: true,
	  	changeYear: true, 
   	onSelect: function (selected) {
       var dt = $(this).datepicker('getDate', '-1d');
       dt.setDate(dt.getDate() - 1);
       $("#creationStartDate").datepicker("option", "maxDate", dt);
   	}
		})
	}
	
	$(function(){
		createMarketCartStartDate();
		createMarketCartEndDate();
 	 });
	
  	function validateMarketCartFilter(){ 
  				
  				var customDate = $('input[name=MarketCartDate]:checked').val();
  		  		var fromDate = $("#creationStartDate").val();
  		  		var toDate = $("#creationEndDate").val();
  		  	//cartFromDiv, cartToDiv
  				   if(customDate === "custom"){ 
  				   if(fromDate === '' && toDate != null){ 
  				   $('#cartFromDiv').find('p.jquery_form_error_message').remove(); 
  				   $("input#creationStartDate").after( "<p class='jquery_form_error_message'>From Date is required.</span>"); 
  				   $("input#creationStartDate").empty(); 
  				   return false; 
  				   } 
  				   if(toDate === '' && fromDate !== ''){ 
  				   $('#cartToDiv').find('p.jquery_form_error_message').remove(); 
  				   $("input#creationEndDate").after( "<p class='jquery_form_error_message'>To Date is required.</span>"); 
  				   $("input#creationEndDate").empty(); 
  				   return false; 
  				   } 
  				   if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && 
  				   (toDate != '' && toDate != 'undefined' && toDate != null)){ 
  				   $('#cartFromDiv').find('p.jquery_form_error_message').remove(); 
  				   $('#cartToDiv').find('p.jquery_form_error_message').remove(); 
  				   } 
  				   } 
  				   else{
  				   $('#cartFromDiv').find('p.jquery_form_error_message').remove(); 
  				   $('#cartToDiv').find('p.jquery_form_error_message').remove(); 
  				   }
  				   closePop(); 
  				   getCandidateDetailsFromMarketcart(customDate, fromDate, toDate);
  				   } 
  	
  	 $('input[name=MarketCartDate]').on('change', function() {
  			   		if($(this).val() == "custom"){
  			   			$("#customDates").show();
  				   		$('#creationStartDate').attr('data-validation',"required");
  				        $('#salesOrderToDate').attr('data-validation',"required");
  			   		}else{
  			   			$("#customDates").hide();
  			   			$('#creationStartDate').removeAttr('data-validation');
  			   	        $('#creationEndDate').removeAttr('data-validation');
  			   	     	$('#creationEndDate, #creationStartDate').val('').datepicker('setDate', null);
  			   		}
  		});
  	
	function resetMarketCartFilter(){
		emptyMarketCartSearchValues();
		$("#customDates").hide();
		$("ul#MarketCartCheckBox > li input:radio").prop("checked", false);
		$("ul#myRolesUL > li input:checkbox").prop("checked",false);
	}
	
	function emptyMarketCartSearchValues(){
		$("#customDateCheckBox").val('');
		checkedDate = '';
		$("#creationStartDate").datepicker('setDate', null);
		$("#creationEndDate").datepicker('setDate', null);
	}
	
</script> 
<script>
//Window scroll fixed header
$(window).scroll(function(){
    if ($(window).scrollTop() >= 100) {
        $('.reportHead').addClass('profileDetails-headerFixed');
		$('.cartInvoice').addClass('cartInvoiceFixed');
    }
    else {
        $('.reportHead').removeClass('profileDetails-headerFixed');
		$('.cartInvoice').removeClass('cartInvoiceFixed');
    }
});
	// Close Advanced Search
	function closePop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").hide();
	}
	function filterPop(){
	$(".advancedsearch-mask").show();
	$("div.cand-advsrch-popup").toggle();
	}
$("#trashHdr").click(function() {
	 $("#deleteAll").toggle();
})
function refreshPage(){
	resetMarketCartFilter();
	$("#dynamicUserDataTableSearch").val('');
	getCandidateDetailsFromMarketcart(); // refresh the page.
}
function predefineProfileImage(imageId){
	$(imageId).attr('src','/resources/images/userprofileImg.jpg');
}
</script>

      