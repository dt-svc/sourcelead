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
						  <li class="hoverLi">Information<i class="fa fa-check-circle"></i></li>
						  <li>Banking details</li>
						  <li>Documents</li>
						  <li>Signatory</li>
						  <div class="clearfix"></div>
					  </ul>
				  <div class="clearfix"></div>
			  </div>
			  
			  
			  <form:form class="login-form clearfix"  id="informationDetails_form"  name="informationDetails_form" method="post" > 	  
				  <div class="comLogContent">
				   <div id="loading"></div> 
			         <jsp:include page="informationDetails.jsp" />
					<div class="welBtns welBtnsA">
				    <input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardLocations'/>'"><input type="submit" value="Save & Continue" class="Brig" id="save_button">
					<input type="button" value="Skip this step" class="skipBtn" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardBankingDetails'/>'"></a>
						<div class="clearfix"></div>
					</div>
				  </div>
			  </form:form>
			  
	  </section>
	  </section>
	  </div>

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
  <script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
 
 <script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	    
           
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
		  
		  
	     var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	 	 var jsDateFormat= '';
	 	  if(locationDate.match("yyyy-MM-dd")){
	 		  jsDateFormat = 'yy-mm-dd';
	 	  }if(locationDate.match("dd-MM-yyyy")){
	 		  jsDateFormat = 'dd-mm-yy'; 
	 	  }if(locationDate.match("MM-dd-yyyy")){
	 		  jsDateFormat = 'mm-dd-yy'; 
	 	  }if(locationDate.match("yyyy-dd-MM")){
	 		  jsDateFormat = 'yy-dd-mm'; 
	 	  }
	 	 
	 	  $('#incorporationDate').datepicker({
	 	      dateFormat: jsDateFormat,
	 	      changeMonth: true,
	 	  	  changeYear: true, 
	 	  	  onClose : function(selected) {
                removeValidation();
			}
	 	  });
	 	  
	 	 $('#pfregistrationDate').datepicker({
	 	      dateFormat: jsDateFormat,
	 	      changeMonth: true,
	 	  	  changeYear: true, 
	 	  	  onClose : function(selected) {
	 	  		removePfRegDateValidation();
			}
	 	  });
	 	 
	 	 
	 	 $('#esiregistrationDate').datepicker({
	 	      dateFormat: jsDateFormat,
	 	      changeMonth: true,
	 	  	  changeYear: true, 
	 	  	  onClose : function(selected) {
	 	  		removeEsiRegDateValidation();
			}
	 	  });
	 	 
	 	 $('#ptregistrationDate').datepicker({
	 	      dateFormat: jsDateFormat,
	 	      changeMonth: true,
	 	  	  changeYear: true, 
	 	  	  onClose : function(selected) {
	 	  		removePtRegDateValidation();
			}
	 	  });
});
</script>


<script>
function removeValidation(){
	$('#incorporationDateDiv').find('p.jquery_form_error_message').remove();
}

function clearDate(){
	$("#incorporationDate").val("");
}

function removePfRegDateValidation(){
	$('#pfregistrationDateDiv').find('p.jquery_form_error_message').remove();
}
function clearPfRegDate(){
	$("#pfregistrationDate").val("");
}

function removeEsiRegDateValidation(){
	$('#esiregistrationDateDiv').find('p.jquery_form_error_message').remove();
}
function clearEsiRegDate(){
	$("#esiregistrationDate").val("");
}

function removePtRegDateValidation(){
	$('#ptregistrationDateDiv').find('p.jquery_form_error_message').remove();
}
function clearPtRegDate(){
	$("#ptregistrationDate").val("");
}	
	
</script>



 <script type="text/javascript">
$('body').on('submit', '#informationDetails_form', function(e){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	if ($('#informationDetails_form').validate(false, validationSettings)){
      e.preventDefault();
      var sourceleadUrl ='${sourceleadUrl}';
      var formData = getFormData("informationDetails_form");
    
	 	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	       $.ajax({
		    	type: "POST",
		    	data : formData,
		    	  url: sourceleadUrl+"/saveCompanyOnboardInformationDetails?${_csrf.parameterName}=${_csrf.token}",		
		        success: function(result) {
		        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
      	    		    showError(result.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	else if (result.response === "AWKWARD") {
      	    		    location.href = "/errorPage";
      	    		}
                	else if(result.response === "SUCCESS"){
                		 $("#loading").removeAttr('style');
                		location.replace(sourceleadUrl+"/companyOnboardBankingDetails"); 
		        	}else if (result.indexOf('Sign In') !== -1) {
      					location.replace(sourceleadUrl);
      				}else {
      				    showError("Operation failed, please try again");
      				    $("#loading").removeAttr('style');
      				}
		        },
		    });    
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#informationDetails_form', function() {
		$("#informationDetails_form").showHelpOnFocus();
		$("#informationDetails_form").validateOnBlur(false, validationSettings);
	});

  </script>
 
 
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

