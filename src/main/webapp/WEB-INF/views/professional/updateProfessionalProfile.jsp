<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/crowdsourcingstyles.css?staticTime=${staticTime}" rel="stylesheet">

<div class="addcandidate-popup">
			<div class="row">
                  <div class="col-md-offset-2 col-md-8">
				  <a href="javascript:void(0)" class="close-popdiv" onClick="closeCandidate()"><i class="fa fa-close"></i></a>
                          <div class="col-md-6 mt">
	                        <div class="small-widget bg-purple">
	                            <a href="${SourceLeadWeb_atsUrl}/professionalPraseCandidate"  title="Parse Resume">
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
                              <a href="${SourceLeadWeb_atsUrl}/professionalAddProfile" title="Add Profile">
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
    function addCandidate(){
        $('.addcandidate-popup').fadeIn(200);
        $('.pop_mask').fadeIn(200);
        $('#addmorepop').slideUp(200);
    };
    
    function closeCandidate(){
	      $('.addcandidate-popup').fadeOut(200);
	      $('.pop_mask').fadeOut(200);
    }
</script>



