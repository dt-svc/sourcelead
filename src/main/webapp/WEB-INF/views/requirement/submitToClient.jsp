<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<script>
function submitToClient(requirementId){
	
	
	var sourceleadUrl ='${sourceleadUrl}';
	var result = "";
	
	result = result + '<div class="add_inter_head"> <span>Submissions</span><a href="javascript:void(0)" class="sub-btn1">Submit to client</a>';
	result = result + ' <div class="clearfix"></div>';
	result = result + ' </div>';
	
	/*  $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getUsersList?${_csrf.parameterName}=${_csrf.token}&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole,
	    		
	 }); */ 
	

	$("#submitToClientDiv").empty();
	$("#submitToClientDiv").append(result);
	
	$(".submitnew-wrap").addClass("submitnew-wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
}
</script>

<!-- For add New Applicant -->
<script>
    $(".submitToClient_close, .side_mask").click(function() {
    	$(".submitnew-wrap").removeClass("submitnew-wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>