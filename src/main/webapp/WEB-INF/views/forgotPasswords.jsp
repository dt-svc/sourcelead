<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 
 <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	
<script src="/resources/js/slead-common.js"></script> 
 
  

<!--  Forgot Pass  -->		
<div class="signin_wrap ForPasSlide">
	<div class="backBtn">
		<a onclick="backToLogin();"><u class="fa fa-arrow-left OrBak"></u></a>
	</div>
		<h1>Forgot Password</h1>
		<form:form class="login-form clearfix"  id="forgetPassword_form"  name="forgetPassword_form" method="post" >
	            <div id="forgotPasswordDiv">
	            <span>
	                <i class="fa fa-user"></i>
	                <input type="text" name="email" placeholder="Mail id"  value="${email}" id="email" class="signin_fild" required />
	            </span>
	            </div>
	            <input type="submit" value="Submit" id="submit_button" class="sign_btn sign_btn_for" />
<%-- 	            <div class="cancel_for"><a href="<c:url value='/login'/>">Cancel</a></div>   --%>
            </form:form>     
  </div> 
<!--  Forgot Pass  -->	



<script type="text/javascript">
        $('#forgetPassword_form')
           .submit(function(e) {
        	   e.preventDefault(); 
        	  var formData = getFormData("forgetPassword_form");
//         		var form = $('#forgetPassword_form').serializeFormJSON();
        		var email = $("#email").val();
        		var sourceleadUrl ='${sourceleadUrl}';
        		$('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
//         		  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
                $.ajax({
            	      type: "POST",
            	      data :formData,
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: "app/ws/resetPasswordWithUserName?${_csrf.parameterName}=${_csrf.token}&email="+email,
                    	success: function(json) {
                    		if(json.status == 'Invalid'){
	                   			 $('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
	             		         document.getElementById("email").setAttribute('class','error signin_fild');
	             		         $("input#email").after("<span class='jquery_form_error_message'> <font color='red'>Invalid User Name</font></span>");
	             				 document.getElementById("email").setAttribute('pass-exist','yes');
	             				 document.getElementById("email").setAttribute('pass-exist-errorMsg', ' Invalid User Name'); 
	             				 $("input#email").removeAttr("style");
    			        	}else if(json.status == 'Inactive'){
    			        		 $('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
	             		         document.getElementById("email").setAttribute('class','error signin_fild');
	             		         $("input#email").after("<span class='jquery_form_error_message'> <font color='red'>User is Inactive</font></span>");
	             				 document.getElementById("email").setAttribute('pass-exist','yes');
	             				 document.getElementById("email").setAttribute('pass-exist-errorMsg', 'User is Inactive'); 
	             				 $("input#email").removeAttr("style");
    			        	}
                    		else if(json.status == 'login'){
    			        		  location.replace("/login"); 
    			        	}
                    		else{
                    			  location.replace("/resetPassword"); 
    			        	}
			        },  
                });
            });
</script>

<script>
function backToLogin(){
	$(".signWrap").removeClass("forPassOut");
	$("#signinDiv").removeAttr("style");
	$("#signinDiv").attr("style","display:block");
	$("#forgotPasswordDiv").attr("style","display:none");
	$(".ForPasSlide").attr("style","display:none");
}
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


<script type="text/javascript" src="https://cdn.ywxi.net/js/1.js" async></script>
