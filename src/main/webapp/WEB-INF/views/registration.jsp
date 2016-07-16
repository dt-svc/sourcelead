



<!DOCTYPE html>

<html>
<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Registration .::</title>
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
	<div class="regis_logo regis_logo_a"><img src="resources/images/logo-regis.png" alt=""/></div>
    <div class="regis_wrap regis_wrap_a">
    	<div class="signin_wrap" id="signin">
        	<h1>Registration</h1>
            <!-- <div class="regis_tabs">
            	<a id="pro-tab" href="javascript:void(0)" class="activetab">Professional</a> <a id="comp-tab" href="javascript:void(0)">Company</a>
                <div class="clearfix"></div>
            </div> -->
        <!-- <div id="profe-regis">    
            <div>
            <span>
            	<label>First Name</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            <span>
            	<label>Last Name</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            </div>
            
            <div>
            <span>
            	<label>Personal email</label>
                <input type="text" class="signin_fild" />
                <b></b>
                <p>User name incorrect.</p>
            </span>
            <span>
            	<label>Select Country</label>
                <input id="professional-autocomplete" type="text" class="signin_fild signin_fild_arrow" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            </div>
            
            <div>
            <span>
            	<label>Password</label>
                <input type="password" class="signin_fild" />
                <b></b>
                <p>User name incorrect.</p>
            </span>
            <span>
            	<label>Confirm password</label>
                <input type="password" class="signin_fild" />
                <b></b>
                <p>Password incorrect</p>
            </span>
            </div>  
            
            <div class="clearfix"></div>
<div class="terms_text">By Signing Up you agree to our <a href="javascript:void(0)">Terms</a> and <a href="javascript:void(0)">Privacy Policy.</a></div>                      
            <input type="submit" value="Register" class="sign_btn" /><div class="for-pas-text">Already have an account? <a href="sign-in.html"> Sign In</a></div> 
           
        </div> -->
        
<div style="padding:20px 0px 0px 0px;">
       <form:form class="login-form clearfix"  id="companyRegistration_form"  name="companyRegistration_form" method="post" >    
                     <div id="comp-regis">  
                 <div>
                 <span>
	            	<label>First Name</label>
	                <input type="text" name="firstName"  value="${firstName}" class="signin_fild" field-name="First Name" data-validation="required" />
	                <b></b>
	            </span>
	            <span>
	            	<label>Last Name</label>
	                <input type="text" name="lastName"  value="${lastName}" class="signin_fild" field-name="Last Name" data-validation="required"  />
	                <b></b>
	            </span>
	               <div class="clearfix"></div>
	            </div>
	            
	            <div>
	            <span>
	            	<label>Company Name</label>
	                <input type="text" name="companyName"  value="${companyName}" class="signin_fild"  field-name="Company Name" data-validation="required" />
	                <b></b>
	            </span>
	            <span id="companyWebsiteDiv">
	            	<label>Company website</label>
	                <input type="text" name="website"  value="${website}"   id="companyWebsite"  onblur="validateWebsite();"  field-name="Company Website" data-validation="required"  onblur="validateWebsite();"   class="signin_fild" />
	                <b></b>
	            </span>
	            <div class="clearfix"></div>
	            </div>
	            
	            <div>
	            <span id="emailDiv">
	            	<label>Company email</label>
	                <input type="text" name="email" id="email"   value="${email}" onblur="validateCompanyEmail();" field-name="Company Email" data-validation="required" class="signin_fild"  />
	                <b></b>
	            </span>
	            <span>
	            	<label>Select Country</label>
	                <input id="company-autocomplete" name="country" field-name="Country" data-validation="required" value="${country}" type="text" class="signin_fild signin_fild_arrow"  />
	                <b></b>
	            </span>
	             <div class="clearfix"></div>
	            </div>            
	            <div id=passwordDiv>
	            <span>
		            	<label>Password</label>
		                <input type="password" name="password"  field-name="Password" data-validation="required" value="${password}" id="password" class="signin_fild"   />
		                <b></b>
		            </span>
		            <span>
		            	<label>Confirm password</label>
		                <input type="password"  value="" field-name="Confirm Password" data-validation="required" id="confirmPassword" class="signin_fild"  onblur="checkPass();" />
		                <b id='confirmPasswordb'></b>
		            </span>
		            <div class="clearfix"></div>
	            </div>
	            
		           <div class="terms_text">By Signing Up you agree to our <a href="javascript:void(0)">Terms</a> and <a href="javascript:void(0)">Privacy Policy.</a></div>                      
		           <input type="submit" value="Register" id="register_button" class="sign_btn" /><div class="for-pas-text">Already have an account? <a href="<c:url value='/login' />"> Sign In</a></div>   
		    </div>
       
	           <%--  <div>
	            <span>
	            	<label>First Name</label>
	                <input type="text" name="firstName"  value="${firstName}" class="signin_fild" required />
	                <b></b>
	            </span>
	            <span>
	            	<label>Last Name</label>
	                <input type="text" name="lastName"  value="${lastName}" class="signin_fild" required />
	                <b></b>
	            </span>
	            </div>
	            
	            <div>
	            <span>
	            	<label>Company Name</label>
	                <input type="text" name="companyName"  value="${companyName}" class="signin_fild" data-validation="required" />
	                <b></b>
	            </span>
	            <div  id="companyWebsiteDiv">
	            <span>
	            	<label>Company website</label>
	                <input type="text" name="website"  value="${website}"   id="companyWebsite"   onblur="validateWebsite();"   class="signin_fild" required />
	                <b></b>
	            </span>
	            </div>
	            </div>
	            
	            <div>
	            <span>
	            	<label>Company email</label>
	                <input type="text" name="email" id="email"   onblur="validateCompanyEmail();"   value="${email}" class="signin_fild" required />
	                <b></b>
	            </span>
	            <span>
	            	<label>Select Country</label>
	                <input id="company-autocomplete" name="country"  value="${country}" type="text" class="signin_fild signin_fild_arrow" required />
	                <b></b>
	            </span>
	            </div>            
	            
	            <div id=passwordDiv>
	            <span>
	            	<label>Password</label>
	                <input type="password" name="password"  value="${password}" id="password" class="signin_fild" required  onkeydown="$(this).clear();" />
	                <b></b>
	            </span>
	            <span>
	            	<label>Confirm password</label>
	                <input type="password"  value="" id="confirmPassword" class="signin_fild"  onblur="$(this).checkPass();" required/>
	                <b></b>
	            </span>
	            </div>  
	            <div class="clearfix"></div>
		    <div class="terms_text">By Signing Up you agree to our <a href="javascript:void(0)">Terms</a> and <a href="javascript:void(0)">Privacy Policy.</a></div>                      
		            <input type="submit" value="Register" id="register_button" class="sign_btn" /><div class="for-pas-text">Already have an account? <a href="<c:url value='/login' />"> Sign In</a></div> --%> 
		   
		   </form:form>        
        </div>            
                       
        </div>       
    </div>
</div>  
  
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/jquery-fun.js"></script> 
    <script src="resources/js/formvalidator.js"></script>
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


<!-- <script type="text/javascript">
        $('#companyRegistration_form')
           .submit(function(e) {
        	    e.preventDefault(); 
        	    var frm = $('#companyRegistration_form').serialize();
        	    frm = "testvalue=testvalue&"+frm;
        		var con = JSON.stringify(frm);
        		var sourceleadUrl ='${sourceleadUrl}';
                $.ajax({
            	      type: "GET",
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: sourceleadUrl+"/companyRegistration",
                  	  data:con,
                    	success: function(result) {
			        	if((result == 'home')){
			        		location.replace(sourceleadUrl+"/home");  
			        	}
			        },  
                });
            });
</script> -->


<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#companyRegistration_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		  e.preventDefault(); 
            	var frm = $('#companyRegistration_form').serialize();
         	    frm = "testvalue=testvalue&"+frm;
         		var con = JSON.stringify(frm);
         		var sourceleadUrl ='${sourceleadUrl}';
         		$("#register_button").attr("disabled", "disabled");
         		$( "#register_button" ).addClass('button').val('Processing..');
            	
	           	$.ajax({
          	      type: "POST",
          	      contentType : 'application/json; charset=utf-8',
          	      dataType : 'json',
                	  url: "http://localhost:8080/sourcelead/companyRegistration?${_csrf.parameterName}=${_csrf.token}&json="+con,
                  	success: function(result) {
			        	if((result == 'home')){
			        		location.replace(sourceleadUrl+"/home");  
			        	}
			        	
			        },  
              });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#companyRegistration_form').showHelpOnFocus();
            
        // $('#companyRegistration_form').validateOnBlur(false, validationSettings); 
         /*  var validationSettings = {
	             errorMessagePosition : 'element',
	            borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
            };         
            
	    $('#companyRegistration_form').showHelpOnFocus();
        $('#companyRegistration_form').validateOnBlur(false, validationSettings);  */
     
	</script>




<script type="text/javascript">
	 function checkPass() {   
		var pass1 =$('input#password').val();  
	    var pass2 = $('input#confirmPassword').val();
	    if((pass1 != null && pass1 != '' && pass1 != 'undefined') && (pass2 != null && pass2 != '' && pass2 != 'undefined')){
	    	if(pass1 == pass2){
	    		$("#register_button").removeAttr("disabled");
	    		$('input#confirmPassword').removeAttr( "class record-exist record-exist-errormsg");
				$('#passwordDiv').find('p.jquery_form_error_message').remove();
				$('input#confirmPassword').attr('class','valid signin_fild');
				$( "#register_button" ).removeAttr("style");
				$( "#register_button" ).addClass('sign_btn').val('Submit');
				$( "#register_button" ).click(function() {
				 if ($(this).validate(false, validationSettings)){
				 }
				});
				
	    	}
	    	else{
	    		$("#register_button").attr("disabled", "disabled");
	    		$('#passwordDiv').find('p.jquery_form_error_message').remove(); 
	    		$('input#confirmPassword').attr('class','error signin_fild');
	    		$("input#confirmPassword").closest('b').after("<p class='jquery_form_error_message'> Passwords Do Not Match</p>");
				$('input#confirmPassword').attr('record-exist','yes');
				$('input#confirmPassword').attr('record-exist-errorMsg',' Passwords Do Not Match'); 
				$( "#register_button" ).addClass('sign_btn').val('Submit');
				$("#register_button").removeAttr("disabled");
	    	
	    	}
	    }
   }
	
   
</script>

<!--   Starts This Script is for validating the Company web site , to find the Duplicate values -->

<script type="text/javascript">
function validateWebsite(){
      var website = document.getElementById("companyWebsite").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){
		
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/ws/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
		        		if(result == "success"){
		        			//existed website
		    	    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyWebsite').attr('class','error signin_fild');
		    	    		$("input#companyWebsite").closest('b').after("<p class='jquery_form_error_message'> "+website+" Already Existed</p>");
		    				$('input#companyWebsite').attr('record-exist','yes');
		    				$('input#companyWebsite').attr('record-exist-errorMsg',website+' Already Existed'); 
		    				$( "#register_button" ).addClass('sign_btn').val('Submit');
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New WebSite
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyWebsite').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$('input#companyWebsite').attr('class','valid signin_fild');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Submit');
							$( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
	}
}
</script>
<!--   Ends  This Script is for validating the Company web site , to find the Duplicate values -->

<!--    Starts Ends of validating the Company Email i,e removing duplicate values--->
<script type="text/javascript">
function validateCompanyEmail(){
    var websiteValue = document.getElementById("companyWebsite").value;
    var emailValue = document.getElementById("email").value;
    var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}'; 
    var sourceleadUrl ='${sourceleadUrl}';
		if((websiteValue != null && websiteValue != '') && (emailValue != null && emailValue != '')){
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/ws/emailAndWebsiteRegistrationValidation?website="+websiteValue+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites+"&email="+emailValue,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid Email'){
					    	    //Invalid Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#email').attr('class','error signin_fild');
			    	    		$("input#email").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Invalid</p>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',emailValue+' is Invalid'); 
			    				$( "#register_button" ).addClass('sign_btn').val('Submit');
			    				$("#register_button").removeAttr("disabled");
					     } 
					     else if(data == 'Existed Email'){
					    	   //Existed Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#email').attr('class','error signin_fild');
			    	    		$("input#email").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Already Existed</p>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',emailValue+' is Already Existed'); 
			    				$( "#register_button" ).addClass('sign_btn').val('Submit');
			    				$("#register_button").removeAttr("disabled");
					    	 
					     }
				       	else{
				       		//Valid Email
				       		$("#register_button").removeAttr("disabled");
				    		$('input#email').removeAttr( "class record-exist record-exist-errormsg");
							$('#emailDiv').find('p.jquery_form_error_message').remove();
							$('input#email').attr('class','valid signin_fild');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Submit');
							$( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
}
</script>
<!--    Ends of validating the Company Email i,e removing duplicate values-->
</html>