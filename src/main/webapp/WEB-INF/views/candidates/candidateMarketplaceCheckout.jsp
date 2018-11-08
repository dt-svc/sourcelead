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
      <h2 id="headerLbl">Marketplace List</h2>
    </div>
      <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
	    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <label id="brudCrumLbl">Marketplace List </label> </div>
	  </c:if>
	  </section>
 	<div class="clearfix"></div>
	  <div class="clearfix">
			<ul class="project-tabs-menu projecttabs-custom clearfix">
		         <li id="" ><a href="${appPath}/candidatesList">Candidate List</a></li>
		         <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
		         <li id="" class="current"><a href="javascript:void(0)">Global Marketplace</a></li>
		     </ul>
     
  <div class="clerfix row">
    <div class="col-md-12">
      <div class="profileDetails">
        <div class="row reportHead">
          <div class="col-md-12"> <span class="pull-left refreIcon noPad mt5"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span>
            <ul class="pull-right mb0 list-unstyled list-inline ulliBorder">
              <li>
				  <i class="inlineBlock pull-left mr10 trashIcon" title="Cart"></i>
				  <div class="checkbox-new pull-left">
                      <input id="trashHdr" class="checkbox-custom" name="paidComponent" type="checkbox">
                      <label for="trashHdr" class="checkbox-custom-label"></label>
                    </div>
				  <a class="pull-left mt2 mr10" href="javascript:void(0)" title="Delete All" id="deleteAll" style="display: none;"><small class="ml10 colorRed">Delete</small></a>
				</li>
              <li><i class="shopping-cart inlineBlock pull-left mr10" title="Cart"></i>
				  <a class="inlineBlock pull-left mt2 mr10 animated zoomInDown" href="javascript:void(0)">10000</a></li>
              <li><i class="dollarSvg inlineBlock pull-left mr10" title="Cart"></i>
				  <a class="animated zoomInDown inlineBlock pull-left mt2 mr10" href="javascript:void(0)">2000000</a></li>
              <li> <a href="javascript:void(0)" class="sortSvg mr10" title="Sorting"></a> </li>
				<li> <a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a> </li>
              <li class="mr">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
              <li class="noMar">
                <button type="button" onclick="initMarketplacePayment()" class="btn btn-primary"><i class="fa fa-send paddingRight"></i>Check Out</button>
                </li>
            </ul>
          </div>
        </div>
        <div class="clearfix row">
          <div class="col-md-8">
            <div class="scrollContent">
              <div id="gridView">
                <div class="mt"></div>
                <div class="media profileDetails-Widget positionRelative"> <a class="removeSelected mt" title="Remove Selected"><i class="fa fa-trash-o"></i></a>
                  <div class="media-left">
                    <div class="cartImg positionRelative">
							<img src="images/sunset.jpg" class="media-object" alt="Image">
							<i class="fa fa-shopping-cart faCart"></i>
                      <div class="clearfix"></div>
						<h4 class="text-center"><span class="label label-default">100%</span></h4>
                    </div>
                  </div>
                  <div class="media-body">
                    <div class="col-md-9">
                      <h4 class="media-heading mb0"><a href="javascript:void(0)">Rajkumar Santoshi Alapati</a></h4>
                      <h6 class="text-muted mb5 fontSmall mt5">Software Development Engineer</h6>
                      <div class="clearfix"></div>
                      <ul class="list-unstyled list-inline mt5 mb0 skillCart">
                    
						  <li class="mb5" title="Current Rate"><i class="fa fa-location-arrow mr text-info"></i> : <span class="paddingRight"><i class="fa fa-usd"></i></span>550<small class="paddingLeft">/ hr</small></li>
                    
						  <li class="mb5" title="Expected Rate">
						<i class="fa fa-check mr textDanger"></i> : <span class="paddingRight"><i class="fa fa-usd"></i></span>900<small class="paddingLeft">/ hr</small></li>
                    
						  <li class="mb5" title="Total Experience"><i class="fa fa-object-group mr text-warning"></i> : <span class="paddingRight"><i class="fa fa-briefcase text-muted" aria-hidden="true"></i></span>6.5<small class="text-muted paddingLeft">(Yrs)</small></li>
                    
						  <li class="mb5" title="Location"><span class="paddingRight"><i class="fa fa-map-marker text-muted"></i></span> : Dallas</li>
                      </ul>
                      <div class="clearfix"></div>
                      <ul class="list-unstyled list-inline mb0 mt5">
                        <li>React.Js,</li>
                        <li>Redux for JavaScript,</li>
                        <li>Node.Js,</li>
                        <li>GraphQL.</li>
                      </ul>
                      <div class="clearfix"></div>
                    </div>
                    <div class="col-md-3 text-center">
                      <div class="clearfix mt25"></div>
						<h4><span class="label label-primary">Price:<i class="fa fa-dollar ml mr5"></i>20000</span></h4>
                    </div>
					  <div class="clearfix"></div>
					  <div class="cartinnerFooter">
						<div class="clearfix">
							<div class="col-md-6">
								<label class="mr text-muted">Added by :</label><span>James Robertjessi</span>
							</div>	
							<div class="col-md-6">
								<label class="mr text-muted">Added on :</label><span>26/12/2018</span>
							</div>	
						</div>
						</div>
                  </div>
                </div>
				</div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="mt"></div>
            <div class="cartInvoice">
              <div class="cartInvoiceHeader">
                <h5 class="margin-top0">Invoice</h5>
              </div>
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
								<td><div id = 'cartCount' class="text-center">0</div></td>
								<td><div id = 'totalPrice' class="text-right"><i class="fa fa-dollar mr5"></i><span>0.0</span></div></td>
							</tr>
							<tr>
								<td><div class="text-left">Tax</div></td>
								<td><div class="text-center"></div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i> <span>500.00</span></div></td>
							</tr>
							<tr>
								<td><div class="text-left">Other</div></td>
								<td><div class="text-center"></div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i> <span>500.00</span></div></td>
							</tr>
							<tr>
								<td><div class="text-left">Discount</div></td>
								<td><div class="text-center"></div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i> <span>1500.00</span></div></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="2"><div class="text-left">Total Amount</div></td>
								<td><div class="text-right"><i class="fa fa-dollar mr5"></i><span>201500.00</span></div></td>
							</tr>
						</tfoot>
					</table>
                </div>
              </div>
              <div class="cartInvoiceBodyFooter">
                <div class="clearfix">
                  <div class="pull-right"> <a onclick="initMarketplacePayment()" href="javascript:void(0)" class="btn btn-primary mr"><i class="fa fa-send paddingRight"></i>Check Out</a> <a class="btn btn-default" href="javascript:void(0)">Cancel</a> </div>
                </div>
              </div>
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
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="myManagersInput" onkeyup="myManagersFunction()" placeholder="Added On" title="Added On" type="text">
            <span class="cursorPointer" title="Add on"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL">
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="1883" type="checkbox">
                <label>Today</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>This Week</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>last Week</label>
              </li>
             <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>One Month</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>One Month Ago</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>This Year</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="orgManagers" value="" type="checkbox">
                <label>Closed</label>
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
              <li class="checkBox positionRelative">
                <input name="userRole" value="2366" type="checkbox">
                <label>Admin De</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>Client</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>Customer</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="userRole" value="2367" type="checkbox">
                <label>Other</label>
              </li>
            </ul>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
    </div>
	
	<div class="clearfix">
		<div class="col-md-12 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
          <h5>Filter by Date <span class="text-danger paddingLeft">*</span></h5>
		  </div>
          <div class="clearfix">
            <ul class="list-unstyled positionRelative filterSearch" id="">
              <li class="positionRelative mb">
                <div class="row">
                <div class="col-md-6">
                	<input type="text" value="" class="form-control calendar" placeholder="From Date" id="frmDate">
                </div>
                <div class="col-md-6">
                	<input type="text" value="" class="form-control calendar" placeholder="To Date" id="toDate">
				</div>
             </div>
              </li>
            </ul>
          </div>
        </div>
	</div>
	
<div class="clearfix"></div>
    <div class="clearfix advancedsearch-footer">
      <div class="pull-right mr">
        <input value="Apply" id="search_requiremet" onclick="closePop();getDynamciUserList();" class="btn btn-sm btn-success" type="button">
        <input value="Reset" id="clear_search" onclick="resetFilter();" class="btn btn-sm btn-primary" type="button">
      </div>
    </div>
  </div>
  <jsp:include page="../masterFooter.jsp" />
</body>	
<script type="text/javascript">

$(document).ready(function () {
	 $("#cartCount").html(localStorage.getItem("dynamicCartCount"));
	 $("#totalPrice").html('<i class="fa fa-dollar mr5"></i><span>'+localStorage.getItem("dynamicTotalPrice")+'</span>');

});

function initMarketplacePayment() {
     $.ajax({
      type: "POST",
	   url: "/app/init-marketplace-payment?cartId=1234",
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
	location.href = "${appPath}/OrgMarketplace";
}
</script> 
<script>
//Window scroll fixed header
$(window).scroll(function(){
    if ($(window).scrollTop() >= 150) {
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
//Date Picker
$( function(){
	$("#frmDate").datepicker();
	$("#toDate").datepicker();
 });
$("#trashHdr").click(function() {
	 $("#deleteAll").toggle();
})
</script>

      