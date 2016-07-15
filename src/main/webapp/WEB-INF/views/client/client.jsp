<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
 
 
 

  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Create Client</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Client</div>
    </section>
    <div class="content-area clearfix">
      <section class="action-btns clearfix">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
        <div class="row">
        <form:form id="client_form"  name="client_form" method="post">
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <div class="form-group">
              <label class="col-md-3" for="Project Id">Client Name<span class="text-danger">*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="orgName" name="orgName" field-name="Client Name" data-validation="required" value="">
                <span class="hidden">Enter Client Name</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Client Country</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="country" value="">
                <span class="hidden">Client Country</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Client State</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="state" value="">
                <span class="hidden">Client state</span> </div>
            </div>
            
            
             <div class="form-group">
              <label class="col-md-3">ClientCity</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="city" value="">
                <span class="hidden">Client City</span> </div>
            </div>
            
           

            <div class="form-group">
              <label class="col-md-3">Add Projects</label>
              <div class="col-md-9">
                <div class="input-group">
                  <div class="input-group-btn">
                    <button class="btn btn-default" type="button"> <span class="badge">10</span></button>
                    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addTask"> <i class="fa fa-plus" title="Add Projects"></i> &nbsp;&nbsp;View / Add Projects</button>
                  </div>
                </div>
                <!-- Modal -->
                <div class="modal fade" id="addTask" role="dialog">
                  <div class="modal-dialog modal-lg"> 
                    
                    <!-- Modal content-->
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add Tasks</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="modal-body-custom custom-table custom-table-new">
                          <table class="table table-bordered table-striped  clearfix" cols="0">
                            <tr>
                              <th>Project Id</th>
                              <th>Project Name</th>
							  <th>Project Description</th>
							  <th>Manager</th>
							  <th>Date</th>
                              <th>Action</th>
                            </tr>
                            <tr>
                              <td>001</td>
							  <td>UI Development</td>
                              <td>The home page has a section in the middle of the page that needs to be to the right.</td>
							  <td>James Smith</td>
							  <td>08/07/2016</td>
							  <td>
							  <span><a title="Edit" href="javasript:void(0)"><i class="fa fa-edit"></i></a></span>
                              <span><a title="Remove" href="javasript:void(0)"><i class="fa fa-trash-o"></i></a></span>
							  </td>
                            </tr>
                          </table>
                        </div>
                        <div class="form-horizontal row custom-form">
                          <div class="col-xs-12 col-lg-7">
                          <div class="form-group">
                            <label class="col-md-3">Task Name</label>
                            <div class="col-md-9">
                              <input type="text" value="" class="form-control">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-3">Description</label>
                            <div class="col-md-9">
                              <textarea class="form-control" rows="2"></textarea>
                            </div>
                          </div>
                          </div>
                          <div class="col-xs-12 col-lg-5">
                          <a href="javascript:void(0)" class="btn btn-primary"> <i class="fa fa-plus"></i> Add Task</a>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Save</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Modal contente Ends--> 
                
              </div>
            </div>
          </div>
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
           <div class="form-group">
              <label class="col-md-3">Website</label>
              <div class="col-md-9">
                 <input type="text" class="form-control" id="webSite" name="webSite" field-name="Website" data-validation="required" value="">
                <span class="hidden">Website</span> </div>
            </div>
            <div class="form-group">
              <label class="col-md-3" for="Project Manager">Email</label>
              <div class="col-md-9">
				<input type="text" value="" class="form-control">
                    <span class="hidden">Enter Email</span>
                </div>
            </div>
            
			   <div class="form-group">
              <label class="col-md-3">Mobile Number</label>
              <div class="col-md-9">
			  <input type="text" class="form-control" name="mobileNumber" value="">
                <span class="hidden">Mobile Number</span> </div>
              </div>
			    </div>
		</form:form>
        </div>
        
        <div class="form-footer clearfix row">
          <div class="col-lg-12">
            <div class="pull-right">
               <input type="submit" value="Save" id="save_client" onclick="saveClient();"  class="btn btn-primary">
               <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/clientDashboard'/>'"  class="btn btn-default ml">
            </div>
          </div>
        </div>
        
      </div>
    </div>
  </section>
  <!-- Content Area Ends--> 
  
  <!-- Footer Area -->
 <jsp:include page="../masterFooter.jsp" />
  <!-- Footer Area --> 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script type="text/javascript">
	$(document).ready(function(e) {
		$('.side-menu').click(function(){
			$('.side-bar').toggleClass("side-bar-active");
			$('.user-panel').toggleClass("user-panel-active");
			$('.content-wraper').toggleClass("content-wraper-active");
			});
		$('.srch-btn-xs').click(function(){
		  $(".search-area").toggleClass("search-area-active");
	    });
		$('html').click(function(){
		  $(".search-area").removeClass("search-area-active");
	    });
		$('.srch-btn-xs, .search-area-txt').click(function(e){
		  e.stopPropagation();
	    });
		$('.login-user').click(function(){
		 $('.user-details').toggleClass('user-details-active');
	    });
		
		$("#notification").click(function(){
			$('.note-area').toggleClass('note-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.alert-area').removeClass('alert-area-active');
		});
		$("#alert").click(function(){
			$('.alert-area').toggleClass('alert-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.note-area').removeClass('note-area-active');
			$('.qlinks-area').removeClass('qlinks-area-active');
		});
		$("#qlinks").click(function(){
			$('.qlinks-area').toggleClass('qlinks-area-active');
			$('.alert-area').removeClass('alert-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.note-area').removeClass('note-area-active');
		});
		$('html').click(function(){
		  $('.user-details').removeClass("user-details-active");
		  $('.setting-area').removeClass('setting-area-active');
		  $('.note-area').removeClass('note-area-active');
		  $('.alert-area').removeClass('alert-area-active');
		  $('.qlinks-area').removeClass('qlinks-area-active');
	    });
		$('.header-nav .navbar, .custom-navbar, .user-details').click(function(e){
		  e.stopPropagation();
	    });
		$('.settings').click(function(){
		$('.setting-area').toggleClass('setting-area-active');
		$('.note-area').removeClass('note-area-active');
		$('.user-details').removeClass('user-details-active');
		$('.alert-area').removeClass('alert-area-active');
	    });
		$(".collpase-div").click(function(){
			$(".toggle-div").slideToggle(800);
		});
		$(".faclose").click(function(){
			$(".remove-div").addClass('hidden');
		});
		$(".bell").click(function(){
				$(this).toggleClass('bell-active');			
			});
       $(".addLevel").click(function(){
		   $(".level-second").toggleClass("level-second-active");
		});
    });   
   </script>
   
 <script type="text/javascript">
 function saveClient() {
	 var frm = $('#client_form').serializeFormJSON();
     var con = JSON.stringify(frm);
     $.ajax({
	    	type: "POST",
	        url: "http://localhost:8080/sourcelead/saveClient?${_csrf.parameterName}=${_csrf.token}&json="+con, 
	        success: function(data) {
	        	if(data == 'clientDashboard'){
	        		location.replace("http://localhost:8080/sourcelead"+"/clientDashboard");  
	        	}
	        }
     });
     
 }
</script> 


<script type="text/javascript">
 function saveProject() {
 var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
	   }; 
	 if ($('#client_form').validate(false, validationSettings)){ 
     var frm = $('#client_form').serializeFormJSON();
        var con = JSON.stringify(frm);
        $.ajax({
	    	type: "POST",
	    	url: "http://localhost:8080/sourcelead/saveClient?${_csrf.parameterName}=${_csrf.token}&json="+con, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	
	        	$("#maxTsRowNum").empty();
		        $("#maxTsRowNum").append(output);
		        
	        },
	    });
       		 return true;
		}else{
			return false;
		}
    }
 $('body').on('blur', '#client_form', function() {
		$("#client_form").showHelpOnFocus();;
		$("#client_form").validateOnBlur(false, validationSettings);  
	});
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