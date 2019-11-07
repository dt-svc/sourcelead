<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@page session="true"%>
 
<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Forgot Password .::</title>
    <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/register-style.css?staticTime=${staticTime}" rel="stylesheet">
     <link href="/resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">
    
</head>

<body>
  
<div class="regis_bg">
	<div class="regis_logo"><img src="/resources/images/SourceLead-logo.png" alt=""/></div>
    <div class="regis_wrap">
     <div id="loading"></div> 
    	<div class="signin_wrap">
        	<h1>Forgot Password</h1>
            <form:form class="login-form clearfix"  id="forgetPassword_form"  name="forgetPassword_form" method="post" >
	            <div id="forgotPasswordDiv">
	            <span>
	            	<label>User Name</label>
	                <i class="fa fa-user"></i>
	                <input type="text" name="email"  value="${email}" id="email" class="signin_fild" required />
	            </span>
	            </div>
	            <input type="submit" value="Submit" id="submit_button" class="sign_btn sign_btn_for" />
	            <div class="cancel_for"><a href="<c:url value='/login'/>">Cancel</a></div>  
            </form:form>         
        </div>        
    </div>
</div>  
  

    <script src="/resources/js/jquery-1.11.1.min.js"></script>
    <script src="/resources/js/jquery-ui.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/jquery-fun.js"></script> 
    
</body>

<script>
$(function () {
     applyInputSlide();
});

function applyInputSlide() {
    $(".signin_wrap span input").each(function (i) {
        if ($(this).val().trim() !== "")
            $(this).parent(".signin_wrap span").find('label').addClass('test');
    });
    $(".signin_wrap span input").on('focus', function () {
        $(this).parent(".signin_wrap span").find('label').addClass('test');
    });
    $(".signin_wrap span input").on('blur', function () {
        if ($(this).val().trim() === "")
            $(this).parent(".signin_wrap span").find('label').removeClass('test');
    });	    
}
</script>




<script type="text/javascript">
        $('#forgetPassword_form')
           .submit(function(e) {
        	    e.preventDefault(); 
        		var form = $('#forgetPassword_form').serializeFormJSON();
        		var sourceleadUrl ='${sourceleadUrl}';
        		$('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
        		  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
                $.ajax({
            	      type: "POST",
            	      data :JSON.stringify(form),
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: sourceleadUrl+"/ws/resetPasswordWithUserName?${_csrf.parameterName}=${_csrf.token}",
                    	success: function(json) {
                    		if(json.status == 'Invalid'){
	                   			 $('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
	             		         document.getElementById("email").setAttribute('class','error signin_fild');
	             		         $("input#email").after("<span class='jquery_form_error_message'> <font color='red'>Invalid User Name</font></span>");
	             				 document.getElementById("email").setAttribute('pass-exist','yes');
	             				 document.getElementById("email").setAttribute('pass-exist-errorMsg', ' Invalid User Name'); 
	             				 $("input#email").removeAttr("style");
	             				 $("#loading").removeAttr("style");
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

   
</html>



























