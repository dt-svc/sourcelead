<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <link href="/resources/css/crowdsourcingstyles.css?staticTime=${staticTime}" rel="stylesheet">

 <!--  <script> 
$(document).ready(function(){
	 var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	
	 if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
	addCandidate();
	 }
	//getProfessionalInfo();
});
</script>   -->
<div class="addcandidate-popup">
			<div class="row">
                  <div class="col-md-offset-2 col-md-8">
				  <a href="javascript:void(0)" class="close-popdiv" onClick="closeCandidate()"><i class="fa fa-close"></i></a>
                          <div class="col-md-6 mt">
	                        <div class="small-widget bg-purple">
	                            <a href="<c:url value='${appPath}/professionalPraseCandidate'/>"  title="Parse Resume">
	                                <div class="clearfix mb10">
	                                <i class="fa fa-file-text-o fa-4x"></i>
	                                    <span class="fa fa-arrow-right"></span>
	                                </div>
	                                <strong class="clearfix">Parse Resume</strong>
	                                </a>
	                        </div>
					  </div>
					  <div class="col-md-6 mt">
                          <div class="small-widget bg-yellow">
                              <a href="<c:url value='${appPath}/professionalAddProfile'/>" title="Add Profile">
                                  <div class="clearfix mb10">
                                      <i class="fa fa-user-circle fa-4x"></i>
                                      <span class="fa fa-plus"></span>
                                  </div>
                                  <strong class="clearfix">Add Profile</strong>
                                </a>
                                
                          </div>
					  </div>
                  </div>
		    </div>
</div>
<div class="pop_mask"></div>


<script>
    //addcandidate-popup script
    function addCandidate(){
        $('.addcandidate-popup').fadeIn(200);
        $('.pop_mask').fadeIn(200);
        $('#addmorepop').slideUp(200);
    };
    
    function closeCandidate(){
	      $('.addcandidate-popup').fadeOut(200);
	      $('.pop_mask').fadeOut(200);
	      
	      /*    var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	     
	      if(loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'){
	      	location.replace(sourceleadUrl+"/professionalDashboard");
	      }
	      else if(loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'){
	      	location.replace(sourceleadUrl+"/recruiterDashboard");
	      }
	      /*  else{
	      	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	      	location.replace(sourceleadUrl+"/dashboard/"+orgLocationId);
	      } */
    }
</script>

<!-- <section class="content-wraper">
        
     <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Update Profile</h2>
      </div>
    </section>
 
</section> -->


