<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link rel="stylesheet" href="/resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">

<script type="text/javascript">
  var sourceleadUrl ='${sourceleadUrl}';
</script>

<div class="wraper"> 

<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				
		<jsp:include page="../userManageAccounts.jsp" />
				
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
				  <li class="actLi"><span>1</span><b>Details</b><i class="line"></i></li>
				  <li><span>2</span><b>Check List</b><i class="line"></i></li>
				  <li><span>3</span><b>Tasks</b><i class="line"></i></li>
				  <li><span>4</span><b>Documents</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  
			  
			  <div class="clearfix"></div>
		  </div>
		  
<form:form class="login-form clearfix"  id="offCandidate_form"  name="offCandidate_form" method="post" >
<div id="loading"></div>
<div class="welCo1">
<h5>Off-boarding Details</h5>

<div class="form-horizontal padBot">
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">First Name </label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" value="${SourceLeadWeb_candidateOffBoarding.firstName}" readonly="readonly">
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Last Name </label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" value="${SourceLeadWeb_candidateOffBoarding.lastName}" readonly="readonly">
			  </div>
			</div>
		</div>
	</div>
	
	
	
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Mobile number </label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" value="${SourceLeadWeb_candidateOffBoarding.mobileNumber}" readonly="readonly">
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
			<div class="form-group">
			  <label class="col-md-4 control-label">Email </label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" value="${SourceLeadWeb_candidateOffBoarding.email}" readonly="readonly">
			  </div>
			</div>
		</div>	
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Role</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" value="${SourceLeadWeb_candidateOffBoarding.role}" readonly="readonly">
			  </div>
			</div>
		</div>
		
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Reason</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" value="${SourceLeadWeb_candidateOffBoarding.reason}" readonly="readonly">
			  </div>
			</div>
		</div>
	</div>
	
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Explanation</label>
                  <div class="col-md-8">
                      <textarea rows="2" class="form-control" readonly="readonly" >${SourceLeadWeb_candidateOffBoarding.explanation}</textarea>
                  </div>
                </div>
		</div>		
	</div>	
	
</div>
 
 <h5>Survey</h5>
 <div class="form-horizontal padBot">
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Reason<span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<select class="chosen-select" data-placeholder="Select Reason" name="surveyReason" data-validation="required" field-name="Survey Reason" >
                      <option  selected="selected" value=''>- Select Reason -</option>
                      <option  value='Personal problems' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Personal problems' ? 'selected' : ''}>Personal problems</option>
                      <option  value='Growth Prospects' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Growth Prospects' ? 'selected' : ''}>Growth Prospects</option>
                      <option  value='Miscellaneous Reasons' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Miscellaneous Reasons' ? 'selected' : ''}>Miscellaneous Reasons</option>
                      <option  value='Family Reasons' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Family Reasons' ? 'selected' : ''}>Family Reasons</option>
                      <option  value='Discipline' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Discipline' ? 'selected' : ''}>Discipline</option>
                      <option  value='Compensation' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Compensation' ? 'selected' : ''}>Compensation</option>
                      <option  value='Performance' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Performance' ? 'selected' : ''}>Performance</option>
                      <option  value='Health issues' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Health issues' ? 'selected' : ''}>Health issues</option>
                      <option  value='Expiration of contract' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Expiration of contract' ? 'selected' : ''}>Expiration of contract</option>
                      <option  value='Voluntary exit' ${SourceLeadWeb_candidateOffBoarding.surveyReason  == 'Voluntary exit' ? 'selected' : ''}>Voluntary exit</option>
               </select>
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Rating<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-8">
                    <select class="chosen-select" data-placeholder="Select Rating" name="rating" data-validation="required" field-name="Rating" >
                      <option  selected="selected" value=''>- Select Rating -</option>
                      <option  value='1' ${SourceLeadWeb_candidateOffBoarding.rating  == '1' ? 'selected' : ''}>1</option>
                      <option  value='2' ${SourceLeadWeb_candidateOffBoarding.rating  == '2' ? 'selected' : ''}>2</option>
                      <option  value='3' ${SourceLeadWeb_candidateOffBoarding.rating  == '3' ? 'selected' : ''}>3</option>
                      <option  value='4' ${SourceLeadWeb_candidateOffBoarding.rating  == '4' ? 'selected' : ''}>4</option>
                      <option  value='5' ${SourceLeadWeb_candidateOffBoarding.rating  == '5' ? 'selected' : ''}>5</option>
                      <option  value='6' ${SourceLeadWeb_candidateOffBoarding.rating  == '6' ? 'selected' : ''}>6</option>
                      <option  value='7' ${SourceLeadWeb_candidateOffBoarding.rating  == '7' ? 'selected' : ''}>7</option>
                      <option  value='8' ${SourceLeadWeb_candidateOffBoarding.rating  == '8' ? 'selected' : ''}>8</option>
                      <option  value='9' ${SourceLeadWeb_candidateOffBoarding.rating  == '9' ? 'selected' : ''}>9</option>
                      <option  value='10' ${SourceLeadWeb_candidateOffBoarding.rating  == '10' ? 'selected' : ''}>10</option>
                    </select>
                  </div>
                </div>
		</div>		
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Explanation</label>
                  <div class="col-md-8">
                      <textarea rows="2" class="form-control" name="surveyExplanation" >${SourceLeadWeb_candidateOffBoarding.surveyExplanation}</textarea>
                  </div>
                </div>
		</div>		
	</div>				
	
	
</div>
	<div class="welCheck">
		<span>
			<input type="checkbox" id="cb1" class="chB" name="sellProperty" value="Y" ${SourceLeadWeb_candidateOffBoarding.wantedToSellProperty  == 'Y' ? 'checked' : ''}/>
			<label for="cb1">Wanted to sell the property / items</label>
		</span>
		<span>
			<input type="checkbox" id="cb2" class="chB" name="sublease" value="Y" ${SourceLeadWeb_candidateOffBoarding.wantedToSubLease  == 'Y' ? 'checked' : ''} />
			<label for="cb2">Wanted to sub-lease</label>
		</span>
		<span>
			<input type="checkbox" id="cb3" class="chB" name="itemsStorage" value="Y" ${SourceLeadWeb_candidateOffBoarding.lookingForPublicStorage  == 'Y' ? 'checked' : ''} />
			<label for="cb3">Looking for public storage for items</label>
		</span>
		<span>
			<input type="checkbox" id="cb4" class="chB" name="" name="accommodation" value="Y" ${SourceLeadWeb_candidateOffBoarding.lookingForAccommodation  == 'Y' ? 'checked' : ''} />
			<label for="cb4">Looking for accommodation</label>
		</span>
		<div class="clearfix"></div>						
	</div>
</div>		  
		  
<div class="welBtns">
	<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOffboardingCandidateDetails();" class="Brig"><div class="clearfix"></div>
</div>

</form:form>
		  
		</section>
	</section>  
</div>

					<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
 <script type="text/javascript" src="/resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="/resources/js/responsiveCarousel.min.js?staticTime=${staticTime}"></script>
 
  <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
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
			  $(".loc-alertclose").click(function(){
					 $(".location-alert").fadeOut();
				})
</script> 

<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>


<script>
 function saveOffboardingCandidateDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#offCandidate_form').validate(false, validationSettings)){ 
	        var formData = getFormData1("offCandidate_form");
	       	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          $.ajax({
		    	type: "POST",
		    	data: formData,
		        processData: false,
		        contentType:false,
		    	 url: sourceleadUrl+"/saveOffboardingCandidateStep1Details?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		location.replace(sourceleadUrl+"/offboardStep2"); 
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#offCandidate_form', function() {
		$("#offCandidate_form").showHelpOnFocus();
		$("#offCandidate_form").validateOnBlur(false, validationSettings);  
 });
 </script>
 
 <script src="/resources/js/slead-common.js"></script>
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

