<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>



<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: Job Summary</title>
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<script src="/resources/js/InterViewFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script>
<script src="/resources/js/interViewForm-html-generator.js?staticTime=${staticTime}" type="text/javWascript" ></script> 
<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
</head>
 
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="/resources/js/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}" type="text/css" >


<style type="text/css">
.viewTemplate .sjfb-field{
margin: 0;
}
.positionRelative {
position: relative;
}
.container.container-wraper{
	padding: 15px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	background: #fafafa;
	border-radius:5px;
	margin-top: 25px;
}
.container-wraper h3 {
margin: 0;
margin-bottom: 10px;
font-size: 20px;
}
.container-wraper .viewTemplate .sjfb-field{
display: inline-flex;
margin-bottom: 10px;
}
.container-wraper .viewTemplate .sjfb-field label {
font-size:14px !important;
font-weight: 600;
}
.container-wraper viewTemplate .sjfb-field{
width: 48.5%;
}
.ml{
margin-left: 15px;
}
.choices label {
width: auto !important;
padding-right: 15px;
}
.choices input[type="checkbox"], .choices input[type="radio"]{
 margin-right: 10px;
 position: relative;
 top: 1px;
}
.stickyfooter {
	bottom: 0;
	width: 100%;
	height: 40px;
	line-height: 40px;
	background-color: #f5f5f5;
	padding:0;
}
.require_wrap_details {
min-height:100px;
}
.reqPadTop {
	padding: 70px 10px 20px 10px;
}
.profile_details h4 {
width: 950px;
}
</style>

<script type="text/javascript">
 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>

<script type="text/javascript">
var postdatavalues = { title: "", link: "", picture: "", description: "", comment: "" };
function setPostValues(title,link,picture,description,comment){
	 if (title) {
         postdatavalues.title = title;
     }
     if (link) {
         postdatavalues.link = link;
     }
     if (picture) {
         postdatavalues.picture = picture;
     }
     if (description) {
         postdatavalues.description = description;
     }
     if (description) {
         postdatavalues.comment = description;
     }
}
function getPostValueDetails(){
	 return postdatavalues;
  }

</script>


<script>
window.onload = function () {
	 var requirementUUID = '${requirementUUID}'; 
	 publicRequirementProfile(requirementUUID);
};	
</script>

<body>
<div class="clearfix"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/SourceLead-logo.png" alt="Source Lead"> </i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
   <div id="publicRequirementProfileDiv"></div>
   
  
   
   <footer class="clearfix stickyfooter">
   <div class="container">
    <div class="pull-left"> @ Copyright : Source Lead, All right reserved </div>
    <div class="pull-right"> Powered by: businessintelli.com </div>
    </div>
  </footer>
</div>


<script>
function publicRequirementProfile(requirementUUID){
	
	var sourceleadUrl = '${sourceleadUrl}';
	var requirementUUID = '${requirementUUID}';
	
	$.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/getRequirementProfessionalProfileDetails?requirementUUID="+requirementUUID, 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
		    	var result = "";
		    	if(json.minExp != null && json.minExp != '' && json.minExp != 'undefined'){
		    		minExp = json.minExp +'&nbsp;Years';
		    	}else{
		    		minExp = "";
		    	}
		    	if(json.maxExp != null && json.maxExp != '' && json.maxExp != 'undefined'){
		    		maxExp = json.maxExp + '&nbsp;Years';
		    	}else{
		    		maxExp = "";
		    	}
		    	if(json.rate != null && json.rate != '' && json.rate != 'undefined'){
		    		rate = json.rate;
		    	}else{
		    		rate = "";
		    	}
		    	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
		    		rateType = json.rateType;
		    	}else{
		    		rateType = "";
		    	}
		    	
		    	if(json.city != null && json.city != '' && json.city != 'undefined'){
		    		city = json.city +' , ';
		    	}else{
		    		city = "";
		    	}
		    	if(json.state != null && json.state != '' && json.state != 'undefined'){
		    		state = json.state + ' , ';
		    	}else{
		    		state = "";
		    	}
		    	if(json.country != null && json.country != '' && json.country != 'undefined'){
		    		country = json.country;
		    	}else{
		    		country = "";
		    	}
		    	if(json.description != null && json.description != '' && json.description != 'undefined'){
		    		description = json.description;
		    	}else{
		    		description = "NO DATA";
		    	}
		    	if(json.positions != null && json.positions != '' && json.positions != 'undefined'){
		    		positions = json.positions;
		    	}else{
		    		positions = "";
		    	}
		    	
		    	if(json.education != null && json.education != '' && json.education != 'undefined'){
		    		education = json.education;
		    	}else{
		    		education = "NO DATA";
		    	}
		    	
				 result = result +'	<div class="container-fluid">';
				 result = result +'    <div class="clients_side_wrap_inn reqPadTop">';
				 result = result +'      <div class="profile_details requirement_details requirement_details_a clearfix">';
				 result = result +'        <h4 id="id1" title="'+json.jobTitle+'">'+json.jobTitle+'</h4>';
				 result = result +'        <div class="hide-row" style="display: block;">';
				 result = result +'          <div class="col-md-9">';
				 result = result +'            <div class="row">';
				 result = result +'              <div class="col-md-3">';
				 result = result +' 			 <div class="company-logo">';
				 result = result +' 				<img id="orgImage" onerror="predefineProfileImage(this);"  data-id="orgLogoImage" src="'+json.fileUrl+'" alt="Company" title="Company Logo" class="img-responsive">';
				 result = result +' 				</div>';
				 result = result +'              </div>';
				 result = result +'              <div class="col-md-9">';
				 result = result +'                <div class="canDetails clearfix">';
				 result = result +'                  <div class="row">';
				 result = result +'                    <div class="col-md-12">';
				 result = result +'                      <ul class="list-unstyled can-perdt">';
				 result = result +'                        <li><i class="fa fa-black-tie"></i><span>Min Experience - '+minExp+'</span></li>';
				 result = result +'                        <li><i class="fa fa-black-tie"></i><span>Max Experience - '+maxExp+'</span></li>';
				 result = result +'                        <li><i class="fa fa-rupee"></i><span>'+rate+' '+rateType+'</span></li>';
				 result = result +'                        <li><i class="fa fa-map-marker"></i><span>'+city+' '+state+' '+country+'</span></li>';
				 result = result +'                      </ul>';
				 result = result +'                    </div>';
				 result = result +'                  </div>';
				 result = result +'                </div>';
				 result = result +'                <div class="pro-detials">';
				 result = result +'                  <div class="professinal-details">';
				 result = result +'                    <ul class="list-inline list-unstyled">';
				 if(json.skills != null  && json.skills != '' && json.skills != 'undefiend'){
			    		var requirmentSkills = json.skills;
				    	var skills = new Array();
				    	skills = requirmentSkills.split(",");
				        for (var i = 0; i < skills.length; i++) { 
				        	 var customSkill = skills[i];
				        	 result = result +'<li>'+customSkill+'</li>';
				        }
			    	}else{
	 					result = result +'<li>-----</li>';
	 				}
				 result = result +'                    </ul>';
				 result = result +'                  </div>';
				 result = result +'                </div>';
				 result = result +'              </div>';
				 result = result +'            </div>';
				 result = result +'          </div>';
				 result = result +'          <div class="col-md-3">';
				 result = result +'         <div class="openPostBtns">';
				 
				 //result = result +'<a href="'+sourceleadUrl+'" class="refer">Refer</a>';
				 result = result +'<a href="'+sourceleadUrl+"/login?requirementUUID="+requirementUUID+'" class="jobApply">Apply To Job</a>';
				 
				
				 result = result +'          </div>';
				 result = result +'          </div>';
				 result = result +'          <div class="clearfix"></div>';
				 result = result +'        </div>';
				 result = result +'      </div>';
				 result = result +'      <div class="project_side_wrap_inn_tabs mt" id="new-id">';
				 result = result +'        <div class="project_tabs">';
				 result = result +'          <div id="tab-1" class="project_tabs_inn" style="display: block;">';
				 result = result +'            <div class="require_wrap_details"> ';
				 result = result +'              <h2>Descriptions:</h2>';
				 result = result +'              <div class="RDdetails">'+description+'</div>';
				 result = result +'              <h2>Other details:</h2>';
				 result = result +'              <div class="require_one">';
				 result = result +'                <div class="require_wrap_details_cta"> <b>No. of positions:</b> <span>'+positions+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 result = result +'                <div class="require_wrap_details_cta"> <b>Education:</b> <span title="'+education+'">'+education+'</span>';
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
		            	result = result +'<div class="require_wrap_details_cta require_wrap_details_view require_uploadfile"><b>Attachment:</b><span>'+json.fileName+'</span><a download class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadRequirementAttachment?requirementId='+json.id+'\"></a> ';
		            }
		            else{
		            result = result +'<div class="require_wrap_details_cta"> <b>Attachment:</b> <span> -- </span>';	
		            }
				 result = result +'                  <div class="clearfix"></div>';
				 result = result +'                </div>';
				 result = result +'                <div class="clearfix"></div>';
				 result = result +'              </div>';
				 result = result +'            </div>';
				 result = result +'          </div>';
				 result = result +'        </div>';
				 result = result +'      </div>';
				 result = result +'    </div>';
				 result = result +'  </div>';
				 $("#publicRequirementProfileDiv").empty();
				 $("#publicRequirementProfileDiv").append(result); 
		    }
	});
}

</script>
<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>
<jsp:include page="../socialinviterscripts.jsp" />
</body>
</html>




