
<!DOCTYPE html>
<html>

<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Forgot Password .::</title>
    <link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/register-style.css" rel="stylesheet">
     <link href="resources/css/jquery-ui.css" rel="stylesheet">
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@page session="true"%>
</head>

<body>
  
<div class="regis_bg">
	<div class="regis_logo"><img src="resources/images/logo-regis.png" alt=""/></div>
    <div class="regis_wrap">
    	<div class="signin_wrap">
        	<h1>Forgot Password</h1>
            <form:form class="login-form clearfix"  id="resetPassword_form"  name="resetPassword_form" method="post" >
	            <div id="forgotPasswordDiv">
	            <span>
	            	<label>Email/User Name</label>
	                <i class="fa fa-envelope"></i>
	                <input type="text" name="email" value="${email}" id="email" class="signin_fild" required />
	            </span>
	            </div>
	            <input type="submit" value="Sumbit" id="submit_button" class="sign_btn sign_btn_for" />
	            <div class="cancel_for"><a href="<c:url value='/login'/>">Cancel</a></div>  
            </form:form>         
        </div>        
    </div>
</div>  
  
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/jquery-fun.js"></script> 
    
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
        $('#resetPassword_form')
           .submit(function(e) {
        	    e.preventDefault(); 
        	    var form = $('#resetPassword_form').serialize();
        	    form = "testvalue=testvalue&"+form;
        		var con = JSON.stringify(form);
        		var sourceleadUrl ='${sourceleadUrl}';
        		$('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
        		 $("#submit_button").attr("disabled", "disabled");
        		 $( "#submit_button" ).addClass('button').val('Processing..');
                $.ajax({
            	      type: "GET",
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: sourceleadUrl+"/resetPasswordWithUserName?${_csrf.parameterName}=${_csrf.token}",
                  	  data:con,
                    	success: function(result) {
                    		if((result == 'Invalid')){
	                   			 $('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
	             		         document.getElementById("email").setAttribute('class','error signin_fild');
	             		         $("input#email").after("<span class='jquery_form_error_message'> <font color='red'>Invalid Email</font></span>");
	             				 document.getElementById("email").setAttribute('pass-exist','yes');
	             				 document.getElementById("email").setAttribute('pass-exist-errorMsg', ' Invalid Email'); 
	             				 $("input#email").removeAttr("style");
    			        	}
                    		else if((result == 'login')){
    			        		  location.replace(sourceleadUrl+"/login"); 
    			        	}
                    		else{
                    			  location.replace(sourceleadUrl+"/resetPassword"); 
    			        	}
			        },  
                });
            });
</script>

   
</html>



























