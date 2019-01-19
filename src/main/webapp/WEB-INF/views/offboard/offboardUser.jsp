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
            
        <c:if test="${!empty SourceLeadWeb_userSpocRolesList}">
		<div class="offOrg">
             <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
	              <div id="spockLinksList" style="display: none;">
	              <ul class="list-unstyled">
	              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
		              <li>
			              <a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}'/>" class="sopcSwith">
				                <div class="clearfix">
				                   <div class="pull-left image-parent positionRelative">
				                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId ? 'active-comp' : ''}">
				                     <img onerror='predefineOrgImage(this);' data-id="orgLogoImage" src="${links.orgLocations.org.fileUrl}">
				                     </div>
				                   </div>
				                   <div class="pull-left mt5">
				                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocations.org.orgName}</p>
				                     <p class="tbldata-dotdot"><i class="fa fa-map-marker"></i>&nbsp;${!empty links.orgLocations.city ? links.orgLocations.city : ""} ${links.orgLocations.country}</p>
				                     <p><i class="fa fa-user"></i>&nbsp;${links.roles.displayName}</p>
				                   </div>
				                </div>
			                </a>
		              </li>
	              </c:forEach>
	              </ul>
	            </div>
	            </div>
          </c:if>	
          
              <div class="blank-pages-tech">
                <p class="text-center text-danger permissondenied mb">
                    <img src="/resources/images/SourceLead-logo.png" alt="Source Lead" title="Source Lead">
                </p>
                <p class="big-txt mt">You are offboard user</p>
                <p class="text-muted">You don't have permission to access this page.</p>
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 

<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>


<script>
  function navtoggle(elementRef){
	  
	 if('${SourceLeadWeb_userSpocRolesListCount}' < 6){
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area'
	     document.getElementById("spockLinksList").style.display="block";
	 }else{
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area spoclinksMore'
		 document.getElementById("spockLinksList").style.display="block";
	 }
  }
  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  </script>
  
 <script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>