
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/resources/js/performance/performanceBadge.js"></script>

<div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
      <div class="popupHead"><span>Add Badge</span><i class="closePop pull-right popClose" onclick="closeBadgePopup();"></i></div>
         <div id="badgeDetailsLoading"></div>
        <form:form id="performance_badge_form" name="performance_badge_form" method="post">
	        <div class="popupContent">
	        <input type="hidden" name="badgeId" id="badgeId"/>
	            <div class="clearfix mb" id="badgeNameDiv">
	                <label class="noPad">Badge Name <span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
			                    <input type="text" id="badgeName" name="name" class="form-control" placeholder="Name" field-name="Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-250">
	                </div>
	                 
	            </div>
				<div class="clearfix mb positionRelative" id="badgeImageDiv">
	                <label class="noPad">Image</label>
	                <div class="clearfix">
						<input type="file" multiple id="uploadBtn" class="upload" field-name="Image" data-validation-optional="true" data-validation="validate_Space validateImg ">
	        		</div>
	        		<!--  <label id="newUploadFile"></label> --> 
					 <input type="text" hidden readonly id="dataFilePath" value="" >
					 
					 <img id="badgeImg" onerror='predefineBadgeImage(this);' src='' class="mediaObject" alt="Image">
	                </div>
	
	            <div class="clearfix mb" id="badgeDescriptionDiv">
	                <label class="noPad">Description<span class="text-danger">&nbsp;*</span></label>
	                <div class="clearfix">
						<textarea field-name="Description" id="badgeDescription"  name="description" class="form-control" data-validation="required validate_Space" rows="2"></textarea>
	                </div>
	            </div>
	
	        </div>
        
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="savePerformanceBudget" onclick="performanceBadge.savePerformanceBadgeDetails();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="closeBadgePopup();" >Cancel</button>
	        </div>
	 </form:form>
</div>

<script>
	function predefineBadgeImage(imageId){
		$(imageId).attr('src','/resources/images/rewards_badges.svg');
	}
</script>

<script type="text/javascript">
document.getElementById("uploadBtn").onchange = function () {
	/* $('#newUploadFile').text($(this).val().replace(/C:\\fakepath\\/i, '')); */
	   fileUpload("uploadBtn", "badgeImages", "performance_badge_form");
};
</script>
  
  
  <script>
  function addBadgePopup(){
	  $(".mask, #modalTasktype").fadeIn();
  }
  
  function closeBadgePopup(){
	  $(".mask, #modalTasktype").fadeOut();
	  $('#badgeNameDiv').find('p.jquery_form_error_message').remove();
	  $('#badgeImageDiv').find('p.jquery_form_error_message').remove();
	  $('#badgeDescriptionDiv').find('p.jquery_form_error_message').remove();
	/*   $('#newUploadFile').text(''); */
	  $('#uploadBtn').val('');
	  
	     $("#badgeId").val('');
		 $("#badgeName").val('');
		 $("textarea[id='badgeDescription']").text('');
		 document.getElementById("badgeImg").src = '/resources/images/rewards_badges.svg';
	  
  }
  </script>
  
  
  <script>
  function editBadge(badgeId){
	  addBadgePopup();
	  $("#badgeDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			  type: "GET",
			    url: sourceleadUrl+"/getPerformanceRewardsAndBadgeDetailsById?badgeId="+badgeId,
			     success :function(response) {
			    	 
			    		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	     	    		    showError(response.responseMessage);
	     	    		    $("#badgeDetailsLoading").removeAttr('style');
	     	    		}
			        	else if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		 $("#badgeId").val(response.object.badgeId);
			        		 $("#badgeName").val(response.object.name);
			        		 $("textarea[id='badgeDescription']").text(response.object.description);
			        		 if(response.object.fileURL != null && response.object.fileURL != 'undefined' && response.object.fileURL != ''){
			        			 document.getElementById("badgeImg").src = response.object.fileURL;
			        		 }
			        		
			        		 $("#badgeDetailsLoading").removeAttr('style');
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
			    	  
					},
				}); 
  }
  </script>
  