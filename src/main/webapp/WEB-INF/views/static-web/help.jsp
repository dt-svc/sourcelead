<jsp:include page="../staticHeader.jsp" /> 
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 
 <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/resources/css/font-awesome.min.css?staticTime=${staticTime}">
    <link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/login.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">
     <script src="/resources/js/jquery-2.1.4.min.js"></script> 


<script>
 window.onload = function() {
		getUserManualHelperOne();
		getUserManualHelperTwo(1);
		getUserManualHelperThree(1);
 };
 </script>

 
<section class="staticBan blogBan" style="margin-top: -10px">
	<div class="staticBanInn"><h4>SourceLead User Manuals</h4><div class="clear"></div></div>
	
        
</section> 

<section class="helpmessage-cotentarea clearfix">
	<section class="clearfix">
<div class="clear"></div>
		<div class="col-md-2">
			<div class="helpmsg-sidebar accordion-toggle">
			<div class="clearfix">
				<div id="UserManualWebDiv" class="datatable_filter"></div>
				<div id="UserManualWebDivTwo" class="datatable_filter"></div>
			</div>
			</div>
			
		</div>
		

<div class="helpmessage-innercotentarea">
	<div class="col-md-10">
	<div id="UserManualWebDivThree" class="datatable_filter"></div>
	</div>
</div>
	</section>
</section>

<script>
function getUserManualHelperOne(){
		
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    type : "GET",
     url: sourceleadUrl+"/ws/getUserManualHelperOneTitles",       
     success: function(response) {
    	 
    	 if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
 		    showError(response.responseMessage);
 	  	} else if (response.response === "AWKWARD") {
 		    location.href = "/errorPage";
 		} else if (response.response === "SUCCESS") {
 			var result = "";
 	    	 //result = result	+ '<div class="helpmsg-sidebar accordion-toggle">';
 	     	for (var i=0; i<response.data.length; i++)
 				{
 	     		 var opt = response.data[i];
 	     		 
 				//result = result +'<div class="clearfix"></div>';
 				result = result + '<h5 onClick="getUserManualHelperTwo('+opt.id+');" class="accordion-link">'+opt.title+' <i class="fa fa-angle-down"></i></h5>';
 				result = result + '<div id="demo'+opt.id+'" class="accordion-panel clearfix"></div>';
 				}
 	     		//result = result + '</div>';
 	     		$("#UserManualWebDiv").empty(); 
 		        $("#UserManualWebDiv").append(result);
 		}
 		 else {
 		    showError("Operation failed, please try again");
 		}
     	
	       
     }
});
	}
	
</script>
<script>
function getUserManualHelperTwo(userManualId){
	$(".accordion-link").find("i").toggleClass("fa-angle-down fa-angle-up");
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    type : "GET",
     url: sourceleadUrl+"/ws/getUserManualHelperTwoTitles?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualId,   
     success: function(response) {
    	 
    	 if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
  		    showError(response.responseMessage);
  	  	} else if (response.response === "AWKWARD") {
  		    location.href = "/errorPage";
  		} else if (response.response === "SUCCESS") {
  			var result = "";
  	    	 result = result	+ '<ul class="list-unstyled">';
  	     	for (var i=0; i<response.data.length; i++)
  				{
  	     		 var opt = response.data[i];
  	     		 
  				result = result + '<li onClick="getUserManualHelperThree('+opt.id+');">'+opt.title+'</li>';
  				
  				}
  	     		result = result + '</ul>';
  	     		result = result + '<div class="clearfix"></div>';
  	     		$("#demo"+userManualId).empty(); 
  		        $("#demo"+userManualId).append(result); 
  		}
    	 
  		else {
 		    showError("Operation failed, please try again");
 		}
	       
     } 
});
	 $("#demo"+userManualId).slideToggle();
	 
	 /* $(this).next(".accordion-panel").slideToggle().siblings(".accordion-panel:visible").slideToggle(); */
	 /* $('.accordion-toggle .accordion-link').click(function(){
			if(!$(this).hasClass('accordion-on'))
			$('.accordion-toggle .accordion-link').removeClass('accordion-on');
			$(this).toggleClass('accordion-on');
			$(this).next(".accordion-panel").slideToggle().siblings(".accordion-panel:visible").slideToggle();
		}) */
	 
}
function demo() {
	getUserManualHelperTwo(1);
	$("#demo"+userManualId).slideToggle();
}
</script>

<script>
function getUserManualHelperThree(userManualTwoId){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    type : "GET",
     url: sourceleadUrl+"/ws/getUserManualHelperThree?${_csrf.parameterName}=${_csrf.token}&userManualId="+userManualTwoId,   
     success: function(response) {
    	 
    	 if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
   		    showError(response.responseMessage);
   	  	} else if (response.response === "AWKWARD") {
   		    location.href = "/errorPage";
   		} else if (response.response === "SUCCESS") {
   			var result = "";
       	 result = result	+ '<div class="clearfix">';
       	 
        	for (var i=0; i<response.data.length; i++)
   			{
        		 var opt = response.data[i];
        		 
        		if (opt.description != null && opt.description != 'undefined') {
   						description = opt.description;
   				}else{
   				description = "";
   				}
        		if (opt.title != null && opt.title != 'undefined') {
       			title = opt.title;
       			}else{
       				title = "";
       			}
        		if (opt.urlName != null && opt.urlName != 'undefined') {
        			urlName = opt.urlName;
       			}
        		
        		result = result	+ '<div class="row mb  moduledescription-text">';
        		result = result + '<div class="col-md-7">';
   			result = result + '<h4>'+title+'</h4>';
   			
   			result = result + '<div class="module-description">';
   			result = result + '<p>'+description+'</p>';
   			result = result + '</div>';
   			result = result + '</div>';
   			
   			if (opt.urlName != null && opt.urlName != 'undefined') {
   				result = result + '<div class="col-md-5">';
   				result = result + '<a href="'+urlName+'" rel="prettyPhoto" title="Know about Jobs"><img src="/resources/static-web/images/videoBanner.jpg" alt="YouTube" class="img-responsive"/></a>';
   				result = result + '</div>';
   					
   			}
   			//result = result + '<a href="'+urlName+'" rel="prettyPhoto" title="Know about Jobs"><img src="/resources/static-web/images/videoBanner.jpg" alt="YouTube" class="img-responsive"/></a>';
   			//result = result + '<p>'+opt.urlName+'</p>';
   			
   				if('undefined' != opt.userManualAttachment){
   					result = result + '<div class="col-md-5">';
   				for (var j=0; j < opt.userManualAttachment.length; j++)
   				{
   				var value = opt.userManualAttachment[j];
   					if(value.fileUrl !=null && value.fileUrl !='' && value.fileUrl !='undefined'){
   						
   						
   						result = result + '<div class="demo-images clearfix">';
   						result = result + '<img src="'+value.fileUrl+'" class="img-responsive">';
   						result = result + '</div>';
   						
   						
   						

//    						result = result + ' <img class="img-circle" title='+value.fileName+'  src='+value.fileUrl+'>';
   					 }
   				}	
   				result = result + '</div>';
   			}
   			
   				result = result + '</div>';
   			
   			}
        		
        		result = result + '</div>';
        		result = result + '<div class="clearfix"></div>';
        		$("#UserManualWebDivThree").empty(); 
   	        $("#UserManualWebDivThree").append(result);
   	        $("a[rel^='prettyPhoto']").prettyPhoto();
   		}
   		else {
 		    showError("Operation failed, please try again");
 		}
     	
     } 
});
}
</script>
<script type="text/javascript" src="https://cdn.ywxi.net/js/1.js" async></script>
<jsp:include page="../staticFooter.jsp" />
<!-- <script type="text/javascript">
window.onload = function() {
	$("#featuresID").addClass("slactive");
	$("#atsID").addClass("slactive");
}
</script> -->