<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<jsp:include page="../socialinviterscripts.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function() {
	socialInviterScripts();
	refercontactList();
});	
</script>
<style type="text/css">
.emailWidth {
	width:400px;
}
</style>
<script>
 function contactsImportSucess(service,status){
	 closePop();
	 msgFadein();
     $("#msgDiv").html(service+" Contacts Are Imported Successfully..");
	 refercontactList();
 }
</script>

<script>
	function msgFadein() {
		$("#selector").fadeIn(200);
        $('#selector').delay(2000).fadeOut('slow');
	}
</script> 

<section class="content-wraper">
<section class="content-header clearfix">
     <div class="success_pop" id="selector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
    <div class="pull-left">
      <h2>Contacts List</h2>
    </div>
    <div class="pull-right brud-crum">Contacts List</div>
  </section>
  <div class="timesheet_out">
      <div class="vendor_wrap">
      <div class="timesheet_head topfilters">
          <ul>
            <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
            </li>
            <!-- <li id="contactLevel">
		                  <div id="contactsFilter"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" style="right:20px;"></i></div>
		                	<div id="contactsFilterPop" class="approved_pop" style="display: none;">
		                    	<ul>
									<li><input id="c6" type="checkbox" name="clinetLevel" value="Tier-1" onclick="getClientDetails();"><label>Accepted</label></li>
									<li><input id="c7" type="checkbox" name="clinetLevel" value="Tier-2" onclick="getClientDetails();"><label>Rejected</label></li>
									<li><input id="c8" type="checkbox" name="clinetLevel" value="Tier-3" onclick="getClientDetails();"><label>Sent</label></li>
		               				<li><input id="c8" type="checkbox" name="clinetLevel" value="Tier-4" onclick="getClientDetails();"><label>Received</label></li>
		               				<li><input id="c9" type="checkbox" name="clinetLevel" value="Tier-5" onclick="getClientDetails();"><label>New</label></li>
		                        </ul>
		                    </div>                	
		                </li>
             -->
            
            
            <li><a href="javascript:void(0)" id="imCon"><i class="fa fa-plus"></i>Import Contacts</a></li>
            <li><a id="addPop" href="javascript:void(0)"><i class="fa fa-plus"></i>Add Contact</a>
            
            <form:form id="addcontact_form"  name="addcontact_form" method="post"> 
				<div class="referWrap referWrap_custom">
				<div id="loading"></div> 
				  <div class="referWrapInn">
					<div class="form-group col-md-6" id="firstNameDiv">
					  <label class="col-md-12">First Name *</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="firstName" id="firstName" field-name="First Name" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">
						<span class="hidden"></span> </div>
					</div> 
					<div class="form-group col-md-6" id="lastNameDiv">
					  <label class="col-md-12">Last Name</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="lastName" id="lastName" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
						<span class="hidden"></span> </div>
					</div> 
					<div class="form-group col-md-6" id="emailDiv">
					  <label class="col-md-12">Email Id *</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="email" id="email" onblur="validateContactEmail()"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">
						<span class="hidden"></span> </div>
					</div> 
					<div class="form-group col-md-6" id="phoneDiv">
					  <label class="col-md-12">Phone </label>
					  <div class="col-md-12" >
						<input type="text" class="form-control"  name="phone" id="phone" field-name="Phone" data-validation-optional="true" data-validation="validate_Space validate_phone validate_length length9-20">
						<span class="hidden"></span> </div>
					</div> 
					<div class="col-md-12 mt10 clearfix">            
					<div class="pull-right">
					  <input type="submit" value="Add Contact" id="add_contact"  class="btn btn-primary">
					  <input type="button" value="Cancel" onclick="flushContactDetails()" class="btn btn-default ml10 canBtn">
					</div>    
					</div> 

				  <div class="clearfix"></div>
				  </div>
			  </div> 
		</form:form>
            </li>
          </ul>
          <div class="clearfix"></div>
        </div>
      <div id="refercontactListDiv" class="datatable_filter"></div>
      </div>
	</div>
  
</section>


<jsp:include page="../masterFooter.jsp" />
<div class="backtotop" style="display: none;"><img src="resources/images/backtotop.png"></div> 

<script>
function refercontactList(){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getRefercontactList?${_csrf.parameterName}=${_csrf.token}", 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
		    	var result = "";
			   	 result = result +'	<div class="project_tabs_inn_table vendor_wrap_inn_table">';
			   	 result = result +'    <table id="refercontactpagenationid" class="display" cellspacing="0" width="100%">';
			   	 result = result + '	<thead>';
			   	 result = result +'        <tr>';
			   	 result = result +'          <th>Name</th>';
			   	 result = result +'          <th class="emailWidth">Email Id</th>';
			   	 result = result +'          <th>Phone No</th>';
			   	 result = result +'          <th>Source</th>';
			   	/*  result = result +'          <th>Action</th>';  */
			   	 result = result +'        </tr>';
			   	result = result + '	     </thead>';
			   	 result = result +'      <tbody>';
			   for(var i=0; i<json.length; i++){
				   var report = json[i];
				   if(report.contactUserPhone != null && report.contactUserPhone != '' && report.contactUserPhone != 'undefiend'){
					   contactUserPhone = report.contactUserPhone;
				   }else{
					   contactUserPhone = "--";
				   }
				   
				   if(report.lastName != null && report.lastName != '' && report.lastName != 'undefiend'){
					   contactUserName = report.firstName+" "+report.lastName;
				   }else{
					   contactUserName = report.firstName;
				   }
				     result = result +'<tr>';
				     result = result +'<td><div class="text-left tbldata-dotdot-big"><img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayContactUserImage/'+report.id+'" class="img-circle pull-left img-responsive paddingRight" width="35px" height="35px"><span class="mid-span"title="User Image">'+contactUserName+'</span></div></td>';
				   	 result = result +'<td><div class="text-left"><span class="mid-span">'+report.contactUserEmail+'</span></div></td>';
				   	 result = result +'<td><div  class="tbldata-dotdot"><span>'+contactUserPhone+'</span></div></td>';
				   	 result = result +'<td><div class="tbldata-dotdot-100"><span>'+report.contactSource+'</span></div></td>';
				   	/*  result = result +'<td><div class="tbldata-dotdot-100"><span class="bg-blue status_contact paddingRight">Accepted</span><b title="Chat" class="text-success fa fa-comments"></b></div></td>'; */ 
				   	 result = result +'</tr>';
			   }
			   	 result = result +'      </tbody>';
			   	 result = result +'    </table>';
			   	 result = result +'  </div>';
			   	 $("#refercontactListDiv").empty();
			   	 $("#refercontactListDiv").append(result); 
			   	 refercontactpagenation();
		    }
	});
	
}

</script>

<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofileImg.jpg');
	}
</script>

<script>
function refercontactpagenation(){
	
	  $('#refercontactpagenationid').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    } );
}
</script>


<script>
function validateContactEmail(){
	var sourceleadUrl ='${sourceleadUrl}';
	var contactEmail = document.getElementById("email").value;
	if(contactEmail){
		  $("#add_contact").attr("disabled", "disabled");
			$.ajax({
			   	type: "GET",
			       url: sourceleadUrl+"/validateContactEmail?${_csrf.parameterName}=${_csrf.token}&contactEmail="+contactEmail,
			       success: function(data) {
			    		if(data != null && data != ''){
						     if(data == 'Invalid'){
						    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
						    	    $("input#email").after("<p class='jquery_form_error_message'>"+contactEmail+" is already added as Contact</span>");
				    				$('input#email').attr('record-exist','yes');
				    				$('input#email').attr('record-exist-errorMsg',contactEmail+' is already added as Contact'); 
						     } 
						     
					       	else{
					       		 $("#add_contact").removeAttr("disabled");
								 $('input#email').removeAttr( "record-exist record-exist-errormsg");
				        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
								 $( "#add_contact" ).removeAttr("style");
								 $( "#add_contact" ).click(function() {
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


<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#addcontact_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var frm = $('#addcontact_form').serializeFormJSON();
        	        var con = JSON.stringify(frm);
        	        con = con.replace(/[{}]/g, "");
        	        
        	        //It will escape all the special characters
        	        var jsonData = encodeURIComponent(con);
				    
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		
	          		 $("#add_contact").attr("disabled", "disabled");
	          		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	          	     $.ajax({
	     		    	type: "POST",
	     		    	 url: sourceleadUrl+"/addCrowdSourcingUserContact?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
	     		        success: function(data) {
	     		        	location.replace(sourceleadUrl+ "/referRequirementList");
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#addcontact_form').showHelpOnFocus();
         $('#addcontact_form').validateOnBlur(false, validationSettings); 
            
     
</script> 
<script>
function flushContactDetails(){
	$('#firstName').val('');
	$('#lastName').val('');
	$('#email').val('');
	$('#phone').val('');
	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
	$('#lastNameDiv').find('p.jquery_form_error_message').remove();
	$('#emailDiv').find('p.jquery_form_error_message').remove();
	$('#phoneDiv').find('p.jquery_form_error_message').remove();
}
</script>
<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 

  
	
<script>	
    $("#addPop").click(function(){
		$(this).parent("li").find(".referWrap").toggleClass("referWrapActive");
		$(".side_mask").fadeOut(200);
		$(".impConPop").fadeOut(100);
	});
    $(".canBtn").click(function(){
		$(".referWrap").removeClass("referWrapActive");
	});		
</script> 
	
   <div class="side_mask"></div>
   <div class="impConPop">
      <div id="registerPopClose" class="pavClose" onclick="closePop();"><i class="fa fa-times"></i></div>
      <div class="socialinviter" type="contactimporter"></div>
   </div>
<script>	
	$("#imCon").click(function(){
		$(".referWrap").removeClass("referWrapActive");
		$(".side_mask").fadeIn(200);
		$(".impConPop").fadeIn(100);
	});
</script> 
<script>
    function closePop(){
    	$(".side_mask").fadeOut(200);
		$(".impConPop").fadeOut(100);
    }
</script>
<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script>
<script>
//Filter Script
$("#contactsFilter").click(function(){
			$("#contactsFilterPop").slideToggle(200);
			$(".export_pop").slideUp(200);
		});
	    $("html").click(function() {
	        $("#contactsFilterPop").slideUp(200);
	    });
	    $("#contactsFilterPop, #contactsFilter").click(function(e) {  
	        e.stopPropagation();
	    });	
</script>