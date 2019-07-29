<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="informationDetails" class="tab-pane fade form-horizontal">
  <div class="accountsettings-tab noMar">
	<div class="tab-content panel-body noBor">
		<form:form class="login-form clearfix"  id="informationDetails_form"  name="informationDetails_form" method="post" >
		<div class="comLogContent emptyShaPad">
				   <div id="loading"></div> 
			         <jsp:include page="onboard/informationDetails.jsp" />
						<div class="clearfix">
							<div class="pull-right">
							<input type="submit" value="Save" id="save_information" class="btn btn-primary mr">
							</div>
						</div>
	    </div>
		</form:form>
	</div>
  </div>
</div>

<script type="text/javascript">
function resetInformation(){
	$('#cinDiv').find('p.jquery_form_error_message').remove();
	$('#gstinDiv').find('p.jquery_form_error_message').remove();
	$('#incorporationDateDiv').find('p.jquery_form_error_message').remove();
	$('#panDiv').find('p.jquery_form_error_message').remove();
	$('#tanDiv').find('p.jquery_form_error_message').remove();
	$('#tdsDiv').find('p.jquery_form_error_message').remove();
	$('#pfNumberDiv').find('p.jquery_form_error_message').remove();
	$('#pfregistrationDateDiv').find('p.jquery_form_error_message').remove();
	$('#esiNumberDiv').find('p.jquery_form_error_message').remove();
	$('#esiregistrationDateDiv').find('p.jquery_form_error_message').remove();
	$('#ptNumberDiv').find('p.jquery_form_error_message').remove();
	$('#ptregistrationDateDiv').find('p.jquery_form_error_message').remove(); 
}

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
		  
		  
	     var locationDate ='${SourceLeadWeb_dateFormat}'; 
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