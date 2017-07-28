<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>::Welcome to Source Lead:Action History ::</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link href="resources/css/dataTablePagination.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=AIzaSyDzIM1uth14u-kSIN_jZ75pRelInyGnYWU" type="text/javascript"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
 <script src="resources/js/jquery.dataTables.min.js"></script>
<body>
<style type="text/css">

.actionHistory {
margin-top: 75px; padding:10px; border: 1px solid #ddd;
}
</style>

<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
    <h1>Source Lead</h1>
   	 	<a href="javascript.void(0)" title="Source Lead"> <i> <img src="resources/images/logo-sourceld.png" alt="Source Lead"> </i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <div class="clearfix"></div>
  <section class="container actionHistory">
  <h4>Actions Performed</h4>
  <div class="clients_wrap_table">
	<div id="actionHistoryList" class="clearfix require_wrap_table overflow_inherint"></div>
<div class="clearfix text-center mb">
<div class="welSiO" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out"></i></div>
<%-- 	<a href="<c:url value='/login'/>" class="btn btn-primary" title="Go to Loginpage">Go to Login <i class="fa fa-arrow-right paddingLeft"></i></a> --%>
</div>
</div>
</section>
</div>



<script> 
 window.onload = function() {
	 var userSessionId = '${userLoginSession.id}';
actionHistoryList(userSessionId);
 };
</script>

<script>
function actionHistoryList(userSessionId){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#actionHistoryLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/getRestActionHistoryList?userSessionId="+userSessionId,    
            success :function(json) {
            	
			    	     $("#actionHistoryLoading").removeAttr("style");
	                	 $("#actionHistoryList").empty();
	                	 var result = "";
	                	 result = result +"<table id='actionHistoryPagenation' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Action Name</th>";
	                     result = result +"    <th>Action Performed</th>";
	                     result = result +"    <th>Action Date</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     /* var json = JSON.parse(data); */
	                    	for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
	                    	   
	                    	    if(opt.actionName != null && opt.actionName !='' && opt.actionName !='undefined'){
	                    		 result = result +"<td><div class='tbldata-dotdot-100' title='"+opt.actionName+"'>"+opt.actionName+"</div></td>";
	                    	    }else{
	                    	     result = result +"<td><div><span title=''>---</span></div></td>";	
	                    	    }
	                    	    
	                    	    if(opt.actionPerformed != null && opt.actionPerformed !='' && opt.actionPerformed !='undefined'){
	                    		 result = result +"<td><div>"+opt.actionPerformed+"</div></td>";
	                    	    }else{
	                    	     result = result +"<td><div><span>---</span></div></td>";
	                    	    }
	                    	    
	                    	    if(opt.actionTimeStr != null && opt.actionTimeStr !='' && opt.actionTimeStr !='undefined'){
	                    		 result = result +"<td><div class='tbldata-dotdot-vsm' title='"+opt.actionTimeStr+"'>"+opt.actionTimeStr+"</div></td>"; 
	                    	    }else{
	                    	     result = result +"<td><div><span>---</span></div></td>"; 
	                    	    }
	                    	    
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#actionHistoryLoading").removeAttr("style");
	                     $("#actionHistoryList").append(result); 
	                     
	                     actionHistoryPagenation(); 
                    
          },
            
       });  
  } 
</script>   

 <script>
function actionHistoryPagenation(){ 
	 $('#actionHistoryPagenation').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
	    	"bLengthChange": false,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    });
	 $('#actionHistoryPagenation').wrap('<div class="overflow_Auto width100percnt"></div>');
}
</script>

<script>
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>
          
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

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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

</body>
</html>
