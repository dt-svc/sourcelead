
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />

<title>Welcome to Source Lead :: The Recruitment Application</title>
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">

<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
</head>

 <script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
 <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script>
 <script src="/resources/js/slead-common.js"></script> 
<script>
		$(document).ready(function() { 
			var UUID = '${UUID}';
			getCandidates(UUID)
			
			$('#email').tooltip({'trigger':'hover', 'title': 'Enter Your Mail Receiver Id'});
			
		});
</script>
<script>
var sourceleadUrl = '${sourceleadUrl}';
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

.project_wrap_table table tr th:first-child{
text-align: left;
}
.container {
padding-bottom: 55px;
}
#campaigncandidatesList .dataTables_info {
padding: 0 !important; padding-left: 165px !important; margin-top: 15px;
}
</style>
<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/logo-sourceld.png" alt="Source Lead"> </i>
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
		  		<div id="loading"></div>
		  			<form id="candidate_form" name="candidate_form" method="post" >
				          <div class="col-md-8  project_wrap_table publicpageTop" id="candidateDivId">
				          
							  <div class="clearfix">
							  <h4>Candidates / Consultants</h4>
							  </div>
							  <div id="campaigncandidatesList" class="datatable_filter positionRelative"></div>
							  
							  <div class="clearfix" id="fromEmailDetails">
								  <div class="col-md-10 mt col-md-offset-3">
								  <div class="col-md-3" id="emailDiv">
								  <input type="text" class="form-control input-sm" field-name="Email" placeholder="Email" id="email" onblur="validateEmail();" name="email" data-validation="required validate_Space validate_email">
								  </div> 
								  <div class="col-md-3 ">
								  <input type="text" class="form-control input-sm" data-validation-optional="true" field-name="Phone" placeholder="Phone" id="phone" name="phone" data-validation="validate_Space validate_int validate_length length9-20">
								  </div>
								    <div class="clearfix"></div>
						          <div class="col-md-6 mt attachment">
						            <div class="input-group">
						              <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-paperclip ColWhite"></i>
						                <input type="file"  style="display: none;"  id="attachfileBtn" class="upload">
						                </b> </label>
						              <input type="text" class="form-control" readonly id="attachfile-mail" placeholder="File Attachment">
						            </div>
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
<script>
$(function() {
	$("#attachfileBtn").change(function() {
		fileUpload("attachfileBtn", "Hotlist Campaign Documents", "candidate_form" ,"" ,"${orgId}","${orgLocationId}");
	});
 });

</script>
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
			var UUID = '${UUID}';
			if ($('#candidate_form').validate(false, validationSettings)){ 
			$('#candidateButtonId').attr('disabled','disabled');
			 
	  		var formData = getFormData1("candidate_form");
			
			 var candidatesIds = [];
			 var oTable = $('#candidateTable').dataTable();
			 var rowcollection =  oTable.$("input[name='candidatesIds[]']:checked", {"page": "all"});
			 rowcollection.each(function(index,elem){
				candidatesIds[index] = $(elem).val();
			});
				
			 
			if(candidatesIds.length > 0){
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				   $.ajax({
					    type : "POST",
			    	    data: formData,
				        processData: false,
				        contentType:false, 
						url :"/sendRequestForResume?${_csrf.parameterName}=${_csrf.token}&candidatesIds="+candidatesIds+"&UUID="+UUID,
							success : function(result) {
								
								if(result == 'success'){
									$("#loading").removeAttr("style"); 
									$("#campaigncandidatesList").empty(); 
									document.getElementById("fromEmailDetails").style.display="none";
									$('#campaigncandidatesList').append("<div class='text-center success-message'><i class='fa fa-handshake-o'></i>Request sent successfully. Kindly wait for the response.</div>");
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
$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
$.ajax({
		type : "POST",
		url :"/validateCampaignToEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&UUID="+UUID,
		success : function(data) {
			
			if (data == 'Invalid') {
				$('#emailDiv').find('p.jquery_form_error_message').remove(); 
				$("#candidateButtonId").removeAttr("disabled");
		        $("#candidateButtonId").attr("disabled", "disabled");
				$("input#email").after( "<p class='jquery_form_error_message'>Invalid Email</span>");
				document.getElementById("email").setAttribute( 'record-exist', 'yes');
				document.getElementById("email").setAttribute( 'record-exist-errorMsg', "Invalid Email");
				$('input#email').removeAttr( "record-exist record-exist-errorMsg");
				$("#loading").removeAttr("style"); 
				
			}else {
				$("#candidateButtonId").removeAttr("disabled");
				$('input#email').removeAttr("class record-exist record-exist-errormsg");
				$('input#email').attr('class','valid form-control');
				$('#emailDiv').find('p.jquery_form_error_message').remove();
				$("#loading").removeAttr("style"); 
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
						url :"/getRequestedCandidates?${_csrf.parameterName}=${_csrf.token}&UUID="+UUID,
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
			                    	 var skills  =  "";
			                    	 
			                    	 if(opt.skills == 'undefined' || opt.skills == null || opt.skills == ''){
			                    		 skills = "-------"
			                    	 }else{
			                    		 skills = opt.skills;
			                    	 }
			                    	 
			                    	 result  = result + ' <td><div><span>&nbsp;&nbsp;&nbsp;<input id="'+opt.id+'" value="'+opt.id+'"  type="checkbox" name="candidatesIds[]" class="myCheckbox" ></span></div></td>';
			                    	 result  = result + ' <td><div><span>'+opt.candidateName+'</span></div></td>';
			                    	 result  = result + ' <td><div><span>'+opt.totalExp+'</span></div></td>';
			                    	 result  = result + ' <td><div><span>'+opt.rate+'</span></div></td>';
			                    	 result  = result + ' <td><div>'+skills+'</div></td>';
			                    	
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
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
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

<script>
document.getElementById("attachfileBtn").onchange = function () {
	$('#attachfile-mail').val($(this).val().replace(/C:\\fakepath\\/i, ''))
};
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
<script type="text/javascript" src='/resources/js/slead-common.js'></script>
