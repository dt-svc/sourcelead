<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">


<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<script type="text/javascript" src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>



<script>
  function navtoggle(elementRef){
	  
	 if('${SourceLeadWeb_prodSupportLoginUserRole}' != 'ROLE_PRODUCTION_SUPPORT'){
		 if('${SourceLeadWeb_userSpocRolesListCount}' < 6){
			 document.getElementById("spockLinksList").removeAttribute("class")
			 document.getElementById('spockLinksList').className = 'spoclinks-area'
		     document.getElementById("spockLinksList").style.display="block";
		 }else{
			 document.getElementById("spockLinksList").removeAttribute("class")
			 document.getElementById('spockLinksList').className = 'spoclinks-area spoclinksMore'
			 document.getElementById("spockLinksList").style.display="block";
		 }
	 }
	 
	 if('${SourceLeadWeb_prodSupportLoginUserRole}' == 'ROLE_PRODUCTION_SUPPORT'){
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area'
	     document.getElementById("spockLinksList").style.display="block";
	 }
	 
	  
  }
  </script>

 <script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<script type="text/javascript">
$(function(){
  $('.crsl-items').carousel({
    visible: 3,
    itemMinWidth: 180,
    itemEqualHeight: 370,
    itemMargin: 9,
  });
  
  $("a[href=#]").on('click', function(e) {
    e.preventDefault();
  });
  
});
</script>

<div class="wraper"> 
	<section class="comLogBg">
			<section class="comLogInn">
				<div class="comLogHead">
				<jsp:include page="../userManageAccounts.jsp" />
				 <div class="comLogLogoText">${SourceLeadWeb_orgName}</div>
				  <div class="welSiO onboard-logoutbtn" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out mt5"></i></div>
				       <ul>
						  <li class="actLi">Organization details<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Locations<i class="fa fa-check-circle"></i></li>
						   <c:if test="${SourceLeadWeb_country == 'India'}">
						  <li class="actLi">Information<i class="fa fa-check-circle"></i></li>
						  </c:if>
						  <c:if test="${SourceLeadWeb_country == 'United States'}">
						  <li class="actLi">Federal & state taxes<i class="fa fa-check-circle"></i></li>
						  </c:if>
						  <li class="hoverLi">Banking details</li>
						  <li>Documents</li>
						  <li>Signatory</li>
						  <div class="clearfix"></div>
					  </ul>
				  <div class="clearfix"></div>
			  </div>
			  
			  <div class="comLogContent">
			  <div id='bankdetailsListLoading'></div>
			       <div class="row clearfix"> 
			       <div class="col-md-5">
			        <h2 class="pull-left">Organization Banking Details </h2>
			        	<a  onclick="showIndiaPaymentAccountPopup();"  class="pull-left ml mt2"><i class="fa fa-plus paddingRight"></i>Add</a>
			        </div>
					</div>
					<jsp:include page="locationIndiaBankingDetails.jsp" />
					
					<div class="welBtns welBtnsA">
									<c:if test="${SourceLeadWeb_country == 'United States'}">
									<input type="button" value="Prev" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/companyOnboardFederalAndStateTaxes'">
									</c:if>
									
									<c:if test="${SourceLeadWeb_country == 'India'}">
									<input type="button" value="Prev" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/companyOnboardInformationDetails'">
									</c:if>
									
									<c:if test="${SourceLeadWeb_country != 'India' && SourceLeadWeb_country != 'United States'}">
									<input type="button" value="Prev" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/companyOnboardLocations'">
									</c:if>
									
									<input type="button" value="Skip this step" class="skipBtn" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/companyOnboardDocuments'"></a>
									<div class="clearfix"></div>
								</div>	
				</div>	
			</section>
	</section>
</div>

<div id="bankingDetailsPopUp"></div>

<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
  
 <script type="text/javascript" src="/resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="/resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
	
  <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
 <script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
 <script src="/resources/js/formvalidator.js"></script>
 
  
<script>
  $(document).ready(function(){
  	  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  });
</script>

<script>
$(".loc-alertclose").click(function(){
	 $(".location-alert").fadeOut();
})
</script>



  
<!--   ================ Laxman changet the payment account ======================== -->

<!-- Add Payment Account Modal -->
  <div class="modal paymentaccount-modal" id="paymentAccountPopup" role="dialog">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="closePaymentAccountPopup();" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Add/Edit Payment Account</h4>
        </div>
        <div class="modal-body minHeightcol5">
        <form:form class="clearfix"  id="paymentaccount_form"  name="paymentaccount_form" method="POST" >
         <inpu type="hidden" id='paymentAccountId' value='0'/>
         <input type="hidden" name="id" id="paymentAccountId"/>
         
          <div class="col-md-5">
           <label class="clearfix">Account Type<span class="text-danger">&nbsp;*</span></label>
           <div class="clearfix">
                <select onchange="showOrHidePaymentFormFields($(this).val());" id="paymentAccountType" name="paymentAccountLocation" class="form-control" data-required="required">
                    <option value="0">--Select Location--</option>
                    <option value="SAVINGS" data-type="1">Savings</option>
                    <option value="CHECKING" data-type="2">Checking</option>
                    <c:if test="${SourceLeadWeb_country != 'India'}"> 
                      <option value="WEPAY" data-type="3">WePay</option>
                    </c:if>
                    
                </select>
           </div>
          </div>
           
        <div data-fieldtype="account"  data-fieldfor="1,2" class="col-md-5">
           <label class="clearfix">Bank Name<span class="text-danger">&nbsp;*</span></label>
           <div class="clearfix">
                <input type="text" id="paymentAccountBankName" name="paymentAccountBankName" placeholder="Bank Name" class="form-control" data-required="required"/>
           </div>
           </div>
          <div data-fieldtype="account"  data-fieldfor="1,2" class="col-md-5">
           <label class="clearfix">Account Number<span class="text-danger">&nbsp;*</span></label>
           <div class="clearfix">
                <input type="number" id="paymentAccountNumber" data-min='9' data-max='15' placeholder="Account Number" name="paymentAccountNumber" class="form-control" data-required="required"/>
           </div>
           </div>
           <div data-fieldtype="account"  data-fieldfor="1,2" class="col-md-5">
              <label class="clearfix">BSR Number <span class="text-danger">&nbsp;*</span></label>
	           <div class="clearfix">
	                <input type="number" id="paymentBSRNumber" data-min='4' placeholder="BSR Number" data-max='15' name="paymentBSRNumber" class="form-control"/>
	           </div>
           </div>
           
           <div data-fieldtype="account"  data-fieldfor="1,2" class="col-md-5">
              <label class="clearfix">MICR Code <span class="text-danger">&nbsp;*</span></label>
	           <div class="clearfix">
	                <input type="text" id="paymentMICRCode" data-min='9' data-max='15' placeholder="MICR Code" name="paymentMICRCode" class="form-control"/>
	           </div>
           </div>
        
        <div data-fieldtype="account" data-fieldfor="1,2" class="col-md-5">
              <label class="clearfix">IFSC Code <span class="text-danger">&nbsp;*</span></label>
           <div class="clearfix">
                <input type="text" id="paymentAccountRoutingNumber" data-min='9' data-max='9' placeholder="IFC Code" name="paymentAccountRoutingNumber" class="form-control" data-required="required"/>
           </div>
           </div>
           <div data-fieldtype="account" data-fieldfor="3" class="col-md-5">
           <label class="clearfix">Email<span class="text-danger">&nbsp;*</span></label>
           <div class="clearfix">
                <input type="text" id="paymentAccountEmail" name="paymentAccountEmail" placeholder="Email"  class="form-control" data-required="required"/>
           </div>
           </div>
        <div data-fieldtype="account" data-fieldfor="1,2,3" class="col-md-5">
           <label class="clearfix">First Name<span class="text-danger">&nbsp;*</span></label>
           <div class="clearfix">
                <input type="text" id="paymentAccountFirstName" name="paymentAccountFirstName" placeholder="First Name" class="form-control" data-required="required"/>
           </div>
           </div>
        
        <div data-fieldtype="account" data-fieldfor="1,2,3" class="col-md-5">
           <label class="clearfix">Last Name<span class="text-danger">&nbsp;*</span></label>
           <div class="clearfix">
                <input type="text" id="paymentAccountLastName" name="paymentAccountLastName" placeholder="Last Name" class="form-control" data-required="required"/>
           </div>
           </div>
        
        
        </form:form>
          
        </div>
        <div class="clearfix"></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" id="savePaymentButton" onclick="savePaymentAccount();" >Save</button>
        </div>
      </div>
    </div>
  </div>
<!-- Add IP Modal -->
<script>
var sourceleadUrl = '${sourceleadUrl}';
function showIndiaPaymentAccountPopup() {
    $("#paymentAccountId").val('0');
    $("[data-fieldtype=account]").hide();
    showOrHidePaymentFormFields('SAVINGS');
    $(".modal.paymentaccount-modal").fadeIn();
    $(".comMask").fadeIn();
    $("input[id*=paymentAccount]").val("");
}
function showOrHidePaymentFormFields(type) {
    $("#paymentAccountLocation").val('0');
    $("#paymentAccountLocation option").removeProp('disabled');
    if(type === "WEPAY") {
        $("#paymentAccountLocation option").prop('disabled', 'disabled');
        $("#paymentAccountLocation option").each(function() {
            if($(this).data('country') === "USA" || $(this).data('country') === "United States"
                    ||  $(this).attr('value') === '0' ||  $(this).attr('value') === 0)
                $(this).removeProp('disabled');        
        });
    }
    $("[data-fieldtype=account]").hide();
    $("#paymentAccountType").val(type);
    var id = $("#paymentAccountType option[value="+type+"]").data('type');
    $("[data-fieldtype=account]").each(function() {
        var val = $(this).data('fieldfor');
        if(typeof val === "number") {
            if(parseInt(val) === parseInt(id)) 
                $(this).show();
        } else {
            console.log(val);
            val = val.split(",");
            for(var e in val) {
                if(parseInt(val[e]) === parseInt(id)) {
                    $(this).show();
                }
            }
        }
    });
}
function closePaymentAccountPopup(type) {
    $(".modal.paymentaccount-modal").fadeOut();
    $(".comMask").fadeOut();
    if(type) {
        showOrHidePaymentFormFields('0');
        showOrHidePaymentFormFields(type);
        $("input[id*=paymentAccount]").val("");
    }
}

function savePaymentAccount() {
    var data =  {};
    var val = $("#paymentAccountType").val();
    var orgloationId = '${orgLocationId}';
    var flag = false;
    if(validateSelect('paymentAccountType', 'Account Type')) {
        if(val === "WEPAY") {
            if(validateEmail('paymentAccountEmail')
                    && validate('paymentAccountFirstName', "First Name")
                    && validate('paymentAccountLastName', "Last Name")) {
                data["email"] = $("#paymentAccountEmail").val();
                flag = true;
            }
        } else {
            if(validate('paymentAccountBankName', "Bank Name")
                    && validate('paymentAccountNumber', "Account Number")
                    && validate('paymentBSRNumber', "BSR Number")
                    && validate('paymentMICRCode', "MICR Code")
                    && validate('paymentAccountRoutingNumber', "IFSC Code	")
                    && validate('paymentAccountFirstName', "First Name")
                    && validate('paymentAccountLastName', "Last Name")) {
            	data["micrCode"] = $("#paymentMICRCode").val().trim();
            	data["bsrNumber"] = $("#paymentBSRNumber").val().trim();
                data["accountNumber"] = $("#paymentAccountNumber").val().trim();
                data["bankName"] = $("#paymentAccountBankName").val().trim();
                data["routingNumber"] = $("#paymentAccountRoutingNumber").val().trim();
                flag = true;
            }
        }
        data["firstName"] = $("#paymentAccountFirstName").val().trim();
        data["lastName"] = $("#paymentAccountLastName").val().trim();
        data["orgLocationId"] = orgloationId;
        data["accountType"] = val;
        data["id"] = $("#paymentAccountId").val();
    }
    if(flag) {
    	$("#savePaymentButton").attr("disabled", "disabled");
        $.post(sourceleadUrl+"/save-payment-account", data, function(data) {
            showError(data.responseMessage);
             if(data.response === "SUCCESS" || data.response === "UNABLE_SET_PAYMENT_ACCOUNT") {
                closePaymentAccountPopup(val);
                $("#savePaymentButton").removeAttr("disabled", "disabled");
                location.replace(sourceleadUrl+"/companyOnboardBankingDetails"); 
            }
            getPaymentAccounts(true);
        });
    }
}
function editPaymentAccount(type, id, locationId, firstName, lastName, accountNumber, routingNumber, bankName,bsrNumber,micrCode) {
	$("[data-fieldtype=account]").hide();
	showOrHidePaymentFormFields(type);
	$(".modal.paymentaccount-modal").fadeIn();
	$(".comMask").fadeIn();
	$("#paymentAccountFirstName").val(firstName);
	$("#paymentAccountBankName").val(bankName);
	$("#paymentAccountLastName").val(lastName);
	$("#paymentAccountLocation").val(locationId);
	$("#paymentAccountNumber").val(accountNumber);
	$("#paymentBSRNumber").val(bsrNumber);
	$("#paymentMICRCode").val(micrCode);
	$("#paymentAccountRoutingNumber").val(routingNumber);
	$("#paymentAccountId").val(id);
}

function changeDefaultPaymentAccount(ele, id) {
    $.post(sourceleadUrl+"/change-default-payment-account", {id: id, defaultAccount: $(ele).is(":checked")}, function(data) {
    	location.replace(sourceleadUrl+"/companyOnboardBankingDetails"); 
        showError(data.responseMessage);
    });
}
function changeActivePaymentAccount(id){
	 $.post(sourceleadUrl+"/change-active-payment-account", {id: id}, function(data) {
	        showError(data.responseMessage);
	        location.replace(sourceleadUrl+"/companyOnboardBankingDetails"); 
	    });
}
function grantWePayAccess(id) {
    $.post(sourceleadUrl+"/resend-wepay-mail", {id: id}, function(data) {
        showError(data.responseMessage);
    });
}
</script>



