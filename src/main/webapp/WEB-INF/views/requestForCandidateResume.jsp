<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Source Lead :: Account List</title>
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link href="resources/css/timesheet.css" rel="stylesheet">

<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script> 
</head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <script src="resources/js/formvalidator.js"></script>
 <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script>
		$(document).ready(function() { 
			var UUID = '${UUID}';
			getCandidates(UUID)
		});
</script>

<style>
body {
background-color:#e1e6fa;
}
.project_wrap_table table {
	border: 1px solid #fff;
	border-bottom: 1px solid #909090;
	margin-bottom: 10px;
}
.project_wrap_table table tr td:last-child {
width: auto;
}
</style>
<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="resources/images/logo-sourceld.png" alt="Source Lead"> </i>
    <h1>Source Lead</h1>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
  <!-- Header Area --> 

  <!-- Content Area -->
  <section class="container">
		<div class="row">
		  		
		  			<form id="candidate_form" name="candidate_form" method="post" >
				          <div class="col-md-8  project_wrap_table" id="candidateDivId">
				          
							  <div class="clearfix">
							  <h4>Candidates / Consultants</h4>
							  </div>
							  <div id="campaigncandidatesList"></div>
							  
							  <div class="clearfix">
								  <div class="col-md-10 mt col-md-offset-3">
								  <div class="col-md-3">
								  <input type="text" class="form-control input-sm" field-name="Email" placeholder="Email" id="email" name="email" data-validation="required validate_Space validate_email">
								  </div> 
								  <div class="col-md-3 ">
								  <input type="text" class="form-control input-sm" field-name="Phone" placeholder="Phone" id="phone" name="phone" data-validation="validate_Space">
								  </div>
								  <div class="clearfix"></div>
								  <div class="col-md-6 mt">
									  <textarea class="form-control" rows="5" name="comment" placeholder="Comment" id="comment"></textarea>	
								  </div>
								  </div>
								  </div>
							    <div class="col-md-6 mt10"><input type="button" class="btn btn-sm btn-primary pull-right" id="candidateButtonId" onclick="sendRequest();" value="Submit"></div>
							  
						 </div>
					</form>
		 <div>
  </section>
  <!-- Footer Area -->
  <footer class="footer-publicpages clearfix">
  <div class="container">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div></div>
  </footer>
  <!-- Footer Area --> 
</div>
</body>
</html>



<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script type="text/javascript">
	function sendRequest() {
			var sourceleadUrl = '${sourceleadUrl}';
			var UUID = '${UUID}';
			
			if ($('#candidate_form').validate(false, validationSettings)){ 
			$('#candidateButtonId').addClass('button').val('Processing..').attr('disabled','disabled');
			var frm = $('#candidate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			/* $.ajax({
						type : "POST",
						url : sourceleadUrl+"/storeNegotiation?${_csrf.parameterName}=${_csrf.token}&json="+con+"&submissionId="+submissionId+"&type="+type,
						success : function(result) {
							$('#rateProposal').val('');
							$('#rateType').val('');
							$('#negotiationButtonId').addClass('button').val('submit').removeAttr("disabled");
							$("#fieldsDiv").css({ display: "none" });
							getNegotiationHistory();
						},
			});   */ 
		}else{
			    $('body').on('blur', '#negotiation_form', function() {
				$("#negotiation_form").showHelpOnFocus();
				$("#negotiation_form").validateOnBlur(false, validationSettings);
			    });
		}
	} 
	    
	
</script>

<script type="text/javascript">
	function getCandidates(UUID) {
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/getRequestedCandidates?${_csrf.parameterName}=${_csrf.token}&UUID="+UUID,
						success : function(data) {
							
								var json = JSON.parse(data);
								
								var result = "";
								result = result +"<html>";
			                	 result = result +"<table id='candidateTable' width='100%' cellpadding='0' border='2' cellspacing='0'>"; 
			                     result = result +"<thead>"; 
			                     result = result +"  <tr>"; 
			                     result  = result + '<th> <input type="checkbox" name=""> </th>';
			                     result  = result + '<th> Name </th>';
			                 	 result  = result + '<th> Experience </th>';
			                 	 result  = result + '<th> Rate </th>';
			                 	 result  = result + '<th> Skills </th>';
			                     result = result +"  </tr>"; 
			                     result = result +"</thead>";
			                     result = result +"<tbody>"; 
			                     for (var i = 0; i < json.length; i++) { 
			                    	 var opt = json[i];  
			                    	 result  = result + ' <td><div><span>&nbsp;&nbsp;&nbsp;<input type="checkbox" name=""></span></div></td>';
			                    	 result  = result + ' <td><div><span>'+opt.candidateName+'</span></div></td>';
			                    	 result  = result + ' <td><div><span>'+opt.totalExp+'</span></div></td>';
			                    	 result  = result + ' <td><div><span>'+opt.rate+'</span></div></td>';
			                    	 result  = result + ' <td><div>'+opt.skills+'</div></td>';
			                    	
			                    	 result  = result + '</tr>';
			                      } 
			                     result = result +" </tbody>";
			                     result = result +"</table>";
			                     result = result +"</html>";
			                 
			                     $("#campaigncandidatesList").empty(); 
			                     $("#campaigncandidatesList").append(result); 
			                     pagination();
							
						},
					});
			       
	} 
</script>

<script>
function pagination(){  
    $('#candidateTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[6, 15, 20, -1], [6, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>

<script type="text/javascript">
function isShowForm(actionUsertype){
	 var lastProposedBy = $("#lastProposedBy").val();
     if(lastProposedBy != null && lastProposedBy != "" && lastProposedBy !='undefined' && lastProposedBy == actionUsertype){
    	 $('#negotiationDivId').append("<span><center>Rate submitted successfully !! Kindly wait for the Response...</center></span>");
    	 document.getElementById("fieldsDiv").style.display="none";
     }else{
    	 document.getElementById("fieldsDiv").style.display="";
     }
     
}
</script>

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

