<!DOCTYPE html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->

<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">


<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${googleMapsLocationApiKey}" type="text/javascript"></script>


<script type="text/javascript">
 var sourceleadUrl ='${sourceleadUrl}';
</script>

<div class="wraper"> 
  <!--   Welcome Screens   -->
  
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				
		<c:if test="${!empty SourceLeadWeb_userSpocRolesList}">
		<div class="offOrg">
             <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
	              <div id="spockLinksList" style="display: none;">
	              <ul class="list-unstyled">
	              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
		              <li>
			              <a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}'/>" class="sopcSwith">
				                <div class="clearfix">
				                   <div class="pull-left image-parent positionRelative">
				                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId ? 'active-comp' : ''}">
				                     <img onerror='predefineOrgImage(this);' data-id="orgLogoImage" src="${links.orgLocations.org.fileUrl}">
				                     </div>
				                   </div>
				                   <div class="pull-left mt5">
				                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocations.org.orgName}</p>
				                     <p class="tbldata-dotdot"><i class="fa fa-map-marker"></i>&nbsp;${!empty links.orgLocations.city ? links.orgLocations.city : ""} ${links.orgLocations.country}</p>
				                     <p><i class="fa fa-user"></i>&nbsp;${links.roles.displayName}</p>
				                   </div>
				                </div>
			                </a>
		              </li>
	              </c:forEach>
	              </ul>
	            </div>
	            </div>
          </c:if>
				
				<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogo">
					<img class="logo" data-id="orgLogoImage" src="${SourceLeadWeb_org.fileUrl}" title="${SourceLeadWeb_orgName}" />
					</div>
				</c:if>
				
				<c:if test="${empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogoText">
					<span  title="${SourceLeadWeb_orgName}">${SourceLeadWeb_orgName}</span>
					</div>
				</c:if>
				
				<div class="welSiO" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out"></i></div>
			  <ul>
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">2</span><b>Step two</b><i class="line"></i></li>
				  <li class="actLi"><span>3</span><b>Step three</b><i class="line"></i></li>
				  <li><span>4</span><b>Step four</b><i class="line"></i></li>
				  <li><span>5</span><b>Step five</b><i class="line"></i></li>
				  <li><span>6</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
 <form:form class="login-form clearfix"  id="onboardCandidateBank_form"  name="onboardCandidateBank_form" method="post" >
 <div id="loading"></div>
<div class="welCo1">
<div class="row clearfix">
<h2 class="pull-left">Bank Details</h2>
<a  onclick="showIndiaPaymentAccountPopup();"  class="pull-left ml mt2"><i class="fa fa-plus paddingRight"></i>Add</a>
</div>

<div class="clearfix"></div>
<!--  table design -->

<div class="clearfix styleTables mt overflow_Auto">
	<table width="100%">
		<thead>
			<tr>
			  <th>Type</th>           
			  <th>Bank Name</th>         
			  <th>Account Number</th>     
			  <th>Routing Number</th>     
			  <th>First Name</th>        
			  <th>Last Name</th>         
<!-- 			  <th>Location</th>            -->
			  <th>Default</th>           
			  <th>Active</th>           
			  <th>Action</th>                  
			</tr>
		</thead>
		<tbody>
		<c:if test="${paymentListSize > 0}">
		   <c:forEach var="payment" items="${paymentList}">
				<tr>
				<td><div class="tblminwidth-50">${!empty payment.accountType ? payment.accountType : "--"}</div></td>
			    <td><div class="tbldata-dotdot-100" title="${payment.bankName}">${!empty payment.bankName ? payment.bankName : "--"}</div></td>
			    <td><div class="tblminwidth-30" title="${payment.accountNumber}" >${!empty payment.accountNumber ? payment.accountNumber : "--"}</div></td>
			    <td><div class="tblminwidth-30" title="${payment.routingNumber}">${!empty payment.routingNumber ? payment.routingNumber : "--"}</div></td>
			    <td><div class="tbldata-dotdot-100" title="${payment.firstName}">${!empty payment.firstName ? payment.firstName : "--"}</div></td>
				<td><div class="tbldata-dotdot-100" title="${payment.lastName}">${!empty payment.lastName ? payment.lastName : "--"}</div></td>
<%-- 				<td><div class="tbldata-dotdot-100">${!empty payment.orgLocationName ? payment.orgLocationName : "--"}</div></td> --%>
				<td><div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="changeDefaultPaymentAccount(this, '${payment.id}')" type="checkbox" ${payment.defaultAccount ?  'checked' : ''}/> <div class="slider round" style="position:absolute !important;"></div> </label> </div></div> </td>
				<td><div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="changeActivePaymentAccount('${payment.id}')" type="checkbox" ${!payment.disabled ?  'checked' : '' }/> <div class="slider round" style="position:absolute !important;"></div> </label> </div></div></td>
				<td><div class="tblminwidth-50"><a href="javascript:void(0)" onclick="editPaymentAccount('${payment.accountType}','${payment.id}','${payment.orgLocationId}','${payment.firstName}','${payment.lastName}','${payment.accountNumber}','${payment.routingNumber}','${payment.bankName}','${payment.bsrNumber}','${payment.micrCode}')">Edit</a></div></td>
				</tr>
			</c:forEach>
		</c:if>
		</tbody>
		</table>
		<c:if test="${paymentListSize == 0}">
		 <div class='row'> 
			 <div class='col-lg-12'> 
				 <div class='dataTables_empty'>
				 </div>
			 </div>
		 </div>
		</c:if>
		</div>
<!--  table design -->

</div>

		  
<div class="welBtns">
	<input type="button"  value="Previous" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/onboardingCandidateStep2'/>'" >
	<input type="button"  value="Proceed to next step" id="save_button"  onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/onboardingCandidateTaxDetails'/>'" class="Brig">
	<div class="clearfix"></div>
</div>
</form:form>
		  
		</section>
	</section>  
  </div>
  <!--   Welcome Screens   -->

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
  
  				<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
  		  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 

<script src="/resources/js/formvalidator.js"></script>
 
 
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
 
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script>  

<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>
 
<script>
  function navtoggle(elementRef){
	  
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
  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  </script>
  
 <script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<script src="/resources/js/slead-common.js?staticTime=${staticTime}"></script>







  
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
                    <option value="CHEQUE" data-type="2">Cheque</option>
                    <c:if test="${SourceLeadWeb_orgLocations.country != 'India'}"> 
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
	                <input type="number" id="paymentBSRNumber" data-min='9' placeholder="BSR Number" data-max='15' name="paymentBSRNumber" class="form-control"/>
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
                <input type="text" id="paymentAccountRoutingNumber" data-min='9' data-max='9' placeholder="IFSC Code" name="paymentAccountRoutingNumber" class="form-control" data-required="required"/>
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
    var candidateId = '${candidateId}';
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
                    && validate('paymentAccountRoutingNumber', "IFSC Code")
                    && validate('paymentAccountFirstName', "First Name")
                    && validate('paymentAccountLastName', "Last Name")
                    && validate('paymentMICRCode', "MICR Code")
                    && validate('paymentBSRNumber', "BSR Number")) {
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
        data["candidateId"] = candidateId;
        data["id"] = $("#paymentAccountId").val();
    }
    if(flag) {
    	$("#savePaymentButton").attr("disabled", "disabled");
        $.post(sourceleadUrl+"/save-payment-account", data, function(data) {
            showError(data.responseMessage);
             if(data.response === "SUCCESS" || data.response === "UNABLE_SET_PAYMENT_ACCOUNT") {
                closePaymentAccountPopup(val);
                $("#savePaymentButton").removeAttr("disabled", "disabled");
                location.replace(sourceleadUrl+"/onboardingCandidateBankingDetails"); 
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
    	location.replace(sourceleadUrl+"/onboardingCandidateBankingDetails"); 
        showError(data.responseMessage);
    });
}
function changeActivePaymentAccount(id){
	 $.post(sourceleadUrl+"/change-active-payment-account", {id: id}, function(data) {
	        showError(data.responseMessage);
	        location.replace(sourceleadUrl+"/onboardingCandidateBankingDetails"); 
	    });
}
function grantWePayAccess(id) {
    $.post(sourceleadUrl+"/resend-wepay-mail", {id: id}, function(data) {
        showError(data.responseMessage);
    });
}
</script>