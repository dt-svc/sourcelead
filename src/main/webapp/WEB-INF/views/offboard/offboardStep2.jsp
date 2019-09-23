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
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />

<script src="/resources/js/offboardCheckListBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/offboardCheckList-html-generator.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form generator -->

<script type="text/javascript">
 var sourceleadUrl ='${sourceleadUrl}';
</script>

<script type="text/javascript">
	window.onload = function () {
		var offboardingId = '${offboardingId}';
		getOffboardCheckList(offboardingId);
	 };
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
				  <li class="actLi"><span class="fill">1</span><b>Details</b><i class="line"></i></li>
				  <li class="actLi"><span>2</span><b>Check List</b><i class="line"></i></li>
				  <li><span>3</span><b>Tasks</b><i class="line"></i></li>
				  <li><span>4</span><b>Documents</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
	<form:form class="login-form clearfix"  id="offboardCheckList_form"  name="offboardCheckList_form" method="post" >
	<div id="loading"></div>
		<div class="welCo1">
			<div class="clearfix">
				<h5>Offboard Check List</h5>
				<div id="offboardCheckList"></div>	
								
			</div>
					
		</div>
			  
		<div class="welBtns">
			<input type="button"  value="Previous" onClick="javascript:window.location.href = '<c:url value='/app/dashboard/${SourceLeadWeb_orgLocationId}'/>'" >
			<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOffboardingCheckList();" class="Brig">
			<div class="clearfix"></div>
		</div>
	</form:form>
	  
		</section>
	</section>  
</div>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="/resources/js/jquery-ui.min.js"></script> 
  <script src="/resources/js/chosen.jquery.min.js"></script> 
  

<script src="/resources/js/formvalidator.js"></script> 


<script>
function getOffboardCheckList(offboardingId){
	
	
	var sourceleadUrl = '${sourceleadUrl}';
	
	getCheckList(sourceleadUrl,offboardingId);
	
    var result = "";
	
    result = result + '<div id="sjfb-fields" class="viewTemplate checklist">';
	
	result = result + '</div>';
	
	
	$("#offboardCheckList").empty();
	$("#offboardCheckList").append(result);  
	
}
</script>

<script>
 function saveOffboardingCheckList(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#offboardCheckList_form').validate(false, validationSettings)){ 
	        
	        var offboardId = '${offboardingId}';
	        
	        var fields = [];
	        $('.sjfb-field').each(function() { 
	        	  var $this = $(this); 
	        	  var divId = $(this).attr('id');  
	        	  var fieldLabel = $this.find('label:first').text();
	        	  var inputValue = $this.find('input[type="radio"]:checked').val();
	        	  
	        	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	      		   inputValue = $this.find('input[type="checkbox"]:checked').val();
	      		   if(inputValue != null && inputValue != '' && inputValue != 'undefined'){
	      			   var selected = [];
	                 	$this.find('input[type="checkbox"]:checked').each(function() {
	        		        selected.push($(this).val()); 
	        		       });
	                 	inputValue = selected.toString()
	      		   }
	      		   
	        	 }
	        	 
	       	     
	       	      
	       	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	       	    	 inputValue = $this.find('textarea').val();
	       	      }
	       	      
	       	    
	       	     
	       	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	       	    	inputValue = '';
	       	      }
	       	      
	       	      
	       	      
	  	       	  var name = $this.find('input[type="radio"]:checked').attr('name');
	  	       	  var id = $this.find('input[type="radio"]:checked').attr('id');
	  	     	  if(name == null || name == '' || name == 'undefined'){
	  	     		  name = $this.find('input').attr('name');
	  	     		  id = $this.find('input').attr('id');
	  	     	  }
	  	     	  
	  	     	  if(name == null || name == '' || name == 'undefined'){
	  	     		 name = $this.find('select').attr('name');
	  	     		 id = $this.find('select').attr('id');
	       	      }
	  	     	  
	  	     	  
	        	  fields.push({
	                  label: fieldLabel,
	                  id :id,
	                  name :name,
	                  value: inputValue,
	              });
	         });
	        var con = JSON.stringify(fields);
	        
	        con = encodeURIComponent(con);
	        
	         $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	           $.ajax({
		    	type: "POST",
		    	url: sourceleadUrl+"/saveOffboardingCheckList?${_csrf.parameterName}=${_csrf.token}&jsonStringValues="+con+"&offboardId="+offboardId, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		location.replace(sourceleadUrl+"/offboardStep3");  
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
 
 $('body').on('blur', '#offboardCheckList_form', function() {
		$("#offboardCheckList_form").showHelpOnFocus();
		$("#offboardCheckList_form").validateOnBlur(false, validationSettings);  
	});
 </script>
 
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




