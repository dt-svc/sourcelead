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
							  
							  <div class="clearfix" id="fromEmailDetails">
								  <div class="col-md-10 mt col-md-offset-3">
								  <div class="col-md-3" id="emailDiv">
								  <input type="text" class="form-control input-sm" field-name="Email" placeholder="Email" id="email" onblur="validateEmail();" name="email" data-validation="required validate_Space validate_email">
								  </div> 
								  <div class="col-md-3 ">
								  <input type="text" class="form-control input-sm" data-validation-optional="true" field-name="Phone" placeholder="Phone" id="phone" name="phone" data-validation="validate_Space">
								  </div>
								  <div class="clearfix"></div>
								  <div class="col-md-6 mt">
									  <textarea class="form-control" rows="5" data-validation-optional="true" name="comment" placeholder="Comment" id="comment"></textarea>	
								  </div>
								  </div>
							  
							    <div class="col-md-6 mt10"><input type="button" class="btn btn-sm btn-primary pull-right" id="candidateButtonId" onclick="sendRequest();" value="Submit"></div>
							  </div>
						 </div>
					</form>
		 <div>
		 <div id ="popUp"></div>
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
function alertCandidates(){
	
	var result = "";
	result = result +"<div class='project_inn_mask'></div>";
	result = result +"<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<p><strong>Check atleast one candidte</strong></p>";
	result = result +"<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"<b href='javascript:void(0)'><input type='button' id='deleteCustomField'  value='Ok' onclick='cancel();' style='background-color:#1ab394;width:100%'/></b>";
	result = result +"</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>


<script type="text/javascript">
function cancel(){
	$("#remove_user_pop").slideUp(200);
	$(".project_inn_mask").hide();
	$("#candidateButtonId").removeAttr("disabled");
}
</script>

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
			$('#candidateButtonId').attr('disabled','disabled');
			var frm = $('#candidate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			var candidatesIds = [];
			 $("input[name='candidatesIds[]']:checked").each( function (i) {
				 candidatesIds[i] = $(this).val();
			});
			 
			if(candidatesIds.length > 0){
				  $.ajax({
							type : "POST",
							url : sourceleadUrl+"/sendRequestForResume?${_csrf.parameterName}=${_csrf.token}&json="+con+"&candidatesIds="+candidatesIds+"&UUID="+UUID,
							success : function(result) {
								
								if(result == 'success'){
									$("#campaigncandidatesList").empty(); 
									document.getElementById("fromEmailDetails").style.display="none";
									$('#campaigncandidatesList').append("<div class='text-center success-message'><i class='fa fa-handshake-o'></i>Request sent successfully. Kindly wait for the Response.</div>");
								}
								 
							},
				});   
			}else{
				
				alertCandidates();
			}
			
			
		}else{
			    $('body').on('blur', '#negotiation_form', function() {
				$("#negotiation_form").showHelpOnFocus();
				$("#negotiation_form").validateOnBlur(false, validationSettings);
			    });
		}
	} 
	    
	
</script>



<script>
function validateEmail(){
var UUID = '${UUID}';
var email = document.getElementById("email").value; 
var sourceleadUrl = '${sourceleadUrl}';

if(email){
$.ajax({
		type : "POST",
		url : sourceleadUrl+"/validateCampaignToEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&UUID="+UUID,
		success : function(data) {
			
			if (data == 'Invalid') {
				$('#emailDiv').find('p.jquery_form_error_message').remove(); 
				$("#candidateButtonId").removeAttr("disabled");
		        $("#candidateButtonId").attr("disabled", "disabled");
				$("input#email").after( "<p class='jquery_form_error_message'>Invalid Email</span>");
				document.getElementById("email").setAttribute( 'record-exist', 'yes');
				document.getElementById("email").setAttribute( 'record-exist-errorMsg', "Invalid Email");
				$('input#email').removeAttr( "record-exist record-exist-errorMsg");
				
			}else {
				$("#candidateButtonId").removeAttr("disabled");
				$('input#email').removeAttr("class record-exist record-exist-errormsg");
				$('input#email').attr('class','valid form-control');
				$('#emailDiv').find('p.jquery_form_error_message').remove();
				$("#candidateButtonId").click(function() {
					
				});
			}
			
		}
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
			                     result = result +"<tr>"; 
			                 	 result = result +'<th><input type="checkbox" id="checkAllCandidates" onclick="toggleCandidates(this);"></th>';
			                     result  = result + '<th> Name </th>';
			                 	 result  = result + '<th> Experience </th>';
			                 	 result  = result + '<th> Rate </th>';
			                 	 result  = result + '<th> Skills </th>';
			                     result = result +"  </tr>"; 
			                     result = result +"</thead>";
			                     result = result +"<tbody>"; 
			                     for (var i = 0; i < json.length; i++) { 
			                    	 var opt = json[i];  
			                    	 result  = result + ' <td><div><span>&nbsp;&nbsp;&nbsp;<input id="'+opt.id+'" value="'+opt.id+'"  type="checkbox" name="candidatesIds[]" class="myCheckbox" ></span></div></td>';
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


 <script>
function toggleCandidates(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAllCandidates)').click(function() {
    if(!this.checked) {
        $('#checkAllCandidates').removeAttr('checked');
    }
});
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

