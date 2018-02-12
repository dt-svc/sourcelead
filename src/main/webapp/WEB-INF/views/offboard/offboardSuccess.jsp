<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/css/bootstrap-select.css" rel="stylesheet">
<link href="/resources/css/jquery.dataTables.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery-ui.css">
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<link href="/resources/css/font-awesome.min.css" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css" rel="stylesheet">

<link rel="stylesheet" href="/resources/css/bootstrap-select.min.css">
<link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<div class="wraper"> 

<section class="welcoBg">
		<section class="welcoInn">
		
		<div class="row">
          <div class="col-lg-12">
            <div class="content-area mt clearfix">
              <div class="blank-pages-tech">
                <p class="text-center text-danger permissondenied mb">
                    <img src="/resources/images/SourceLead-logo.png" alt="Source Lead" title="Source Lead">
                </p>
                <p class="big-txt mt">Your offboard sucessfully done.</p>
                <p class="positionRelative move-btn btn-group mt">
                <i class="fa fa-home btn btn-primary"></i>
                <a href="#" onclick="logoutFormSubmit();" type="button" class="btn btn-primary">Logout<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>
                </span></a>
                </p>
              </div>
            </div>
          </div>
        </div>
        
		</section>
		
		</section>
</div>


<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>