<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to SourceLead:: Login Page</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="resources/css/login-styles.css" type="text/css" >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
</head>
<body class="login-bg">
<div class="container parent">

<!-- Logo Ends --> 
<div class="row"> 
        <div class="col-lg-12 element">
      <div class="col-sm-2"> <img src="resources/images/logo-sourcelead.png" alt="Source Lead" class="img-responsive"> </div>
    </div>
  </div>
  <!-- Logo Ends --> 
  <!-- Form Starts here -->
  <div class="row mt">
    <div class="form-divmain element clearfix">
      <div class="col-xs-12 col-sm-5 col-md-5 center-form">
     <%--  <c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if> --%>
		
		<c:if test="${!empty login_blocked_error}">
				 <div class="error">Your user account is locked. Please contact your customer relationship manager</div>
      	</c:if>
      		
      	<c:if test="${!empty login_inActive_error}">
      			<div class="error">You are In Active user</div>
    	</c:if>
        <c:if test="${!empty login_unRegistered_error}">
         		<div class="error">You are not registered user. Please get register.</div>
    	</c:if> 
       <c:if test="${!empty login_password_error}">
         		<div class="error">Invalid Username or Password.</div>
    	</c:if> 
    	
    	
		        				
        <form class="login-form clearfix" action="<c:url value='/login' />" method='POST'>
          <div class="form-group">
          <div class="input-group">
            <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span>
            <input type="text" name="username" class="formcontrol" value="${username}" required>
            <span class="highlight"></span>
            <label>Name</label>
          </div>
          </div>
          <div class="form-group">
          <div class="input-group">
                  <span class="input-group-addon"><i class="fa fa-key" aria-hidden="true"></i></span>
            <input type="password"  name="password"  value="${password}" class="formcontrol" required>
            <span class="highlight"></span>
            <label>Password</label>
             <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
            </div>
          </div>
          
          <!-- button -->
          <div class="clearfix mt">
            <button type="submit" class="btn btn-info" >Login</button>
            <p class="mt clearfix">
            <a href="#" class="pull-left forgot" title="Forgot Password ?">Forgot Password ?</a>
            <a href="#" class="pull-right btn btn-warning" title="Click for Help">Click for Help</a>
            </p>
          </div>
          <!-- button -->
         
        </form>
      </div>
    </div>
    </div>
      <!-- Form Starts here --> 
</div>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
