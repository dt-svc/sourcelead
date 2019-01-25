
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="indiabankingDetails" class="tab-pane fade form-horizontal">
  <div class="accountsettings-tab">
	<div class="tab-content panel-body">
		<div class="comLogContent">
					  <div id='bankdetailsListLoading'></div>
				       <div class="row clearfix"> 
				       <div class="col-md-5">
				        <h2 class="pull-left">Organization Banking Details</h2>
				        	<a onclick="addNewBankingDetails();"  class="pull-left ml mt2"><i class="fa fa-plus paddingRight"></i>Add</a>
				        </div>
						</div>
						<jsp:include page="onboard/locationIndiaBankingDetails.jsp" />
	    </div>
	</div>
  </div>
</div>
	
<div id="bankingDetailsPopUp"></div>



<script>
 function deleteBankingDetails(uuid){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#bankdetailsListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	    	type: "POST",
	    	  url: sourceleadUrl+"/deleteIndiaOnboardBankingDetails?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,		
	        success: function(result) {
	        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
  	    		    showError(result.responseMessage);
  	    		    $("#bankdetailsListLoading").removeAttr('style');
  	    		}
	        	else if (result.response === "AWKWARD") {
  	    		    location.href = "/errorPage";
  	    		}
            	else if(result.response === "SUCCESS"){
            		location.replace(sourceleadUrl+"/orgSettings"); 
	        	}else if (result.indexOf('Sign In') !== -1) {
  					location.replace(sourceleadUrl);
  				}else {
  				    showError("Operation failed, please try again");
  				    $("#bankdetailsListLoading").removeAttr('style');
  				}
	        },
	    });  
 }
 </script>
 
 
 <script type="text/javascript">
$('body').on('submit', '#indiabankingDetails_form', function(e){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	if ($('#indiabankingDetails_form').validate(false, validationSettings)){
      e.preventDefault();
      var sourceleadUrl ='${sourceleadUrl}';
      var formData = getFormData("indiabankingDetails_form");
     
	 	$("#detailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	data : formData,
		    	  url: sourceleadUrl+"/saveOrgLocationsIndiaBankingDetails?${_csrf.parameterName}=${_csrf.token}",		
		        success: function(result) {
		        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
      	    		    showError(result.responseMessage);
      	    		    $("#detailsLoading").removeAttr('style');
      	    		}
		        	else if (result.response === "AWKWARD") {
      	    		    location.href = "/errorPage";
      	    		}
                	else if(result.response === "SUCCESS"){
                		location.replace(sourceleadUrl+"/orgSettings"); 
		        	}else if (result.indexOf('Sign In') !== -1) {
      					location.replace(sourceleadUrl);
      				}else {
      				    showError("Operation failed, please try again");
      				    $("#detailsLoading").removeAttr('style');
      				}
		        },
		    });     
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#indiabankingDetails_form', function() {
		$("#indiabankingDetails_form").showHelpOnFocus();
		$("#indiabankingDetails_form").validateOnBlur(false, validationSettings);
	});
	
	$(".loc-alertclose").click(function(){
		 $(".location-alert").fadeOut();
	})
  </script>