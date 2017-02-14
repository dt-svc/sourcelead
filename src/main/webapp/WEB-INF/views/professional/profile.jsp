<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <link href="resources/css/ crowdsourcingstyles.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<script> 
$(document).ready(function(){
	getProfessionalInfo();
	//getVendorContactLists();
});
</script>
<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<section class="content-wraper">
        
     <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Profile</h2>
      </div>
      <div class="pull-right brud-crum">Home » Profile</div>
    </section>
    
  <!-- Profile Content Start-->
    <div id="professionalInfoDiv"></div>
     <!-- Profile Content End -->
    
</section>

<script>
function getProfessionalInfo(){ 
	var sourceleadUrl ='${sourceleadUrl}';
	var candidateType= "Professional";
	$.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/getProfessionalInfo?candidateStatus="+candidateType, 	   
		    success: function(data) {
		    	 var json = JSON.parse(data);
		    	 alert("json>>>>>>>>>>"+data);
		    	 alert("json.firstName"+json.id);
		    	 var result = "";
				 result = result +'	<div class="content-area clearfix panelBody"> ';
				 result = result +'	<section class="row pro_info">';
				 result = result +'	    <div class="col-md-5">';
				 result = result +'	        <h5>Professional Info</h5>';
				 result = result +'	        <div class="chip">';
				 result = result +'	        <img onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+json.id+'"/>" alt="Profile Image" width="96" height="96">';
				 
				 result = result +'	        <span>';
				 result = result +'	        <strong>'+json.firstName+'</strong> <br>';
				 result = result +'	        UI / Frontend Developer';
				 result = result +'	        </span>';
				 result = result +'	        </div>';
				 result = result +'	        <ul class="list-unstyled professional-details">';
				 result = result +'	            <li><i class="fa fa-magnet" title="Designation Type"></i><strong>Business Intelli Solutions, <span class="text-muted">Full Time</span></strong></li>';
				 result = result +'	            <li><i class="fa fa-history" title="Experience"></i>6 Years</li>';
				 result = result +'	            <li><i class="fa fa-calendar" title="Notice Period"></i>One Month</li>';
				 result = result +'	            <li>';
				 result = result +'	                <i class="fa fa-money" title="Current and Expected Rate"></i>';
				 result = result +'	                <span>Current: <strong>EUR</strong></span> -||- <span>Expected: <strong>EUR</strong></span>';
				 result = result +'	            </li>';
				 result = result +'	        </ul>';
				 result = result +'	    </div>';
				 result = result +'	    <div class="col-md-3">';
				 result = result +'	        <h5>Can be Reach</h5>';
				 result = result +'	        <ul class="list-inline list-unstyled reachout-links mt25">';
				 result = result +'	            <li><a href="javascript:void(0)" title="FaceBook" class="btn-fb"><i class="fa fa-facebook-square fa-2x"></i></a></li>';
				 result = result +'	            <li><a href="javascript:void(0)" title="LinkedIn" class="btn-li"><i class="fa fa-linkedin-square fa-2x"></i></a></li>';
				 result = result +'	            <li><a href="javascript:void(0)" title="Twitter" class="btn-tw"><i class="fa fa-twitter-square fa-2x"></i></a></li>';
				 result = result +'	            <li><a href="javascript:void(0)" title="Google" class="btn-go"><i class="fa fa-google-plus-square fa-2x"></i></a></li>';
				 result = result +'	            <li><a href="javascript:void(0)" title="Globe" class="btn-ww"><i class="fa fa-globe fa-2x"></i></a></li>';
				 result = result +'	        </ul>';
				 result = result +'	        <div class="reachout">';
				 result = result +'	        <p><i class="fa fa-at" title="Mail me"></i>srinivas.l@businessintelli.com</p>';
				 result = result +'	        <p><i class="fa fa-phone" title="Call us"></i>91 8730966489</p>';
				 result = result +'	        <p><i class="fa fa-map-marker" title="Location"></i>Location</p>';
				 result = result +'	        </div>';
				 result = result +'	    </div>';
				 result = result +'	    <div class="col-md-4">';
				 result = result +'	        <h5>Skills</h5>';
				 result = result +'	        <div class="skills text-center mt25">';
				 result = result +'	            <p><strong>';
				 result = result +'	        Java, PHP, Hibernet, Spring, jQuery, javascript, HTML, HTML5, CSS3</strong>';
				 result = result +'	        </p>';
				 result = result +'	            </div>';
				 result = result +'	    </div>';
				 result = result +'	</section>';
				 result = result +'	</div>';
				 
				 $("#professionalInfoDiv").empty();
				 $("#professionalInfoDiv").append(result); 
		    	 
		    },
	});
            
}

</script>


<jsp:include page="../masterFooter.jsp" />