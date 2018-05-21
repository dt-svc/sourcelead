<jsp:include page="../staticHeader.jsp" /> 
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
 window.setTimeout(function() {
				addthis.toolbox(".addthis_inline_share_toolbox_g28q");
				addthis.init(".addthis_inline_share_toolbox_g28q");
				addthis.layers.refresh();
	        }, 500);
</script> 
<div class="clearfix"></div>
<section class="staticBan blogBan">
	<div class="staticBanInn"><h4>SourceLead blogs</h4><div class="clear"></div></div>
</section>  
<section class="testiWrap">
<section class="blogWrap">
<div class="blogWrapRight">
       <div class="writtenBy blogRight">
       <div class="writer clearfix">
        <h5 class="text-muted">Written By</h5>
       	<!-- <div class="writer-img">
       		<img src="images/userimage.jpg" alt="Writer Image">
       	</div> -->
       	<div class="writer-name pull-left">
       		<h4>${blogDetails.createdBy.fullName}</h4>
       		<%-- <p>${userRoleDisplayName}</p> --%>
       	</div>
       </div>
       
       <div class="clearfix"></div>
       <%-- <div class="published">
       		<h5 class="text-muted">Published</h5>
       		<p>${publishon}</p>
       </div> --%>
       <div class="clearfix"></div>
       <div class="category">
       		<h5 class="text-muted">Category</h5>
       		<p id="main-content">${blogDetails.categoryId.categoryName}</p>
       </div>
      <div class="clearfix"></div>
       
       <div class="sharelinks clearfix">			  
	    <div class="addthis_inline_share_toolbox_g28q" data-url="${sleadUrl}/blog/${blogDetails.href}.html" addthis:url="${sleadUrl}/blog/${blogDetails.href}.html" addthis:title="${blogDetails.href}" data-title="${blogDetails.href}" addthis:description="${blogDetails.href}" data-description="${blogDetails.href}"></div>
	   </div>
             
       </div>
       
       <h2>Recent Posts<i class="fa fa-caret-down"></i></h2>
        <div class="resSlide">
        
    	<div class="blogRight recentPost">
    		<c:forEach var="relatedBlogs" items="${relatedBlogs}">
    		<a class="linDotDot"  onclick="recentPosts('${relatedBlogs.href}');"><i class="fa fa-caret-right"></i>${relatedBlogs.blogTitle}</a>
    	  </c:forEach>
        </div>    
                          
        <div class="blogRightBot hidden">
        	<p>Ready to Learn More?</p>
            <span>Let us help you choose the perfect solution for your firm.</span>
            <a href="javascript:void(0)" class="requestDemo">Get a Demo</a>
        </div>
        </div>
        <div class="clearfix"></div>
         <h2>Categories<i class="fa fa-caret-down"></i></h2>
        <div class="resSlide categoriesPost">
	    	<div class="blogRight">
	    		
	    		<c:forEach var="blogCategory" items="${blogCategoryList}">
	    		<a href="/blog/topic/${blogCategory.href}.html"><i class="fa fa-caret-right"></i>${blogCategory.categoryName}
	    		
	    		<div class="categories-count"><span> ${blogCategory.blogCount} </span></div>
	    		
	    		</a>
	    	   </c:forEach>
	        </div>                      
        </div>
        
        
        <form:form id="blog_subscription_form" name="blog_subscription_form" method="post">
       		<div class="subscribe" id="emailError" >
			    <input type="text" class="form-control" name="email" id="email" placeholder="Subscribe News Letter" field-name="Email"
								data-validation="required validate_Space validate_length length2-100 validate_email">
			    <input type="button" value="Subscribe" class="btn btn-danger pull-right" id="saveSubscription" onclick="saveBlogSubscription(${blogDetails.id});" >
  			</div>
  			
  			<div class="clearfix"></div>
  			 <div class="success_pop text-success text-center panel-heading bg-success" id="selector4" style="display: none;"> 
		     <i class="fa fa-check-circle mr"></i><span id="subMsgDiv"></span> 
		    </div>
		   
		  
  	   </form:form>		
    </div>

	<div class="blogWrapLeft">
    	<div class="blogInn">
        	<h1> ${blogDetails.blogTitle} </h1>
            <h3><i class="fa fa-calendar-check-o"></i>Published On : <span>${publishon}</span></h3>
            <div class="blogImg"><img src="${blogDetails.imgFileUrl}" alt=""></div>
            ${blogDetails.description}
          </div>
          
    </div>
    
<div class="clear"></div>  
</section>	
</section>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script type="text/javascript">
function saveBlogSubscription(blogId){
	
	var sleadUrl = '${sleadUrl}';
	if ($('#blog_subscription_form').validate(false, validationSettings)) {
	  	var formData = $($('#blog_subscription_form')[0]).serialize();
		$("#blogSubLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sleadUrl+"/addBlogSubscription?blogId="+blogId, formData, function(data) {
			$("#email").val('');
			$("#selector4").removeAttr("style");
   			$("#selector4").css({ display: "block" });
   			$("#subMsgDiv").html("Great ! you have successfully subscribed");
   			subscriptionMsgFadeinFunction();
		}); 
		return true;
		} else {
			return false;
		}
	
}

$('body').on('blur', '#blog_subscription_form', function() {
	$("#blog_subscription_form").showHelpOnFocus();
	$("#blog_subscription_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
	function subscriptionMsgFadeinFunction() {
		$("#selector4").fadeIn(200);
        $('#selector4').delay(2000).fadeOut('slow');
	}
</script>

<script>
function validateBlogSubscriptionEmail(){
	var sleadUrl = '${sleadUrl}';
	var email = $('#email').val(); 
	$('#emailError').find('p.jquery_form_error_message').remove();
 if(email != null && email != ""){
	$.ajax({
			type : "POST",
			url : sleadUrl +"/validateBlogSubscriptionEmail?&email="+email,
			success : function(data) {
				if(data == true){
	        		  $('#email').find('p.jquery_form_error_message').remove();
					  $("input#email").after("<p class='jquery_form_error_message' style='position:absolute;bottom:4px; left:0; text-overflow:ellipsis; width:325px;overflow:hidden; display:block; white-space:nowrap;'>"+email+" is Already Existed</p>");
					  document.getElementById("email").setAttribute('record-exist','yes');
					  document.getElementById("email").setAttribute('record-exist-errorMsg',email+' is Already Existed');
	        	}
	        	else{
	        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
	        		 $('#email').find('p.jquery_form_error_message').remove(); 
	        	}
			},
		});
}
}

</script>  

<script>
	function recentPosts(href){
		var sourceleadUrl = '${sourceleadUrl}';
		href = encodeURIComponent(href);
		var url = sourceleadUrl.split("/app");
		window.location.href = url[0] + "/blog/"+href+".html";
	}
</script>

<jsp:include page="../staticFooter.jsp" />
<script type="text/javascript" src="http://s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5a60382a93e935c2"></script>
<jsp:include page="../socialinviterscripts.jsp" />
