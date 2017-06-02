<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 
<div class="wraper"> 
  <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Expense</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; My Expenses &raquo; Add Expense</div>
    </section>
    
	<div class="content-area content-new clearfix">
         
          <div class="clearfix form-horizontal inner-content">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
                <div class="form-group">
                  <label class="col-md-3">Expenses Id<span class="text-danger" >&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="First Name" value="${ExpensesId}">
                  </div>
                </div>  
                
                <div class="form-group">
                  <label class="col-md-3">Purpose</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="First Name" value="">
                  </div>
                </div>                                          

			<div class="form-group">
				<label class="col-md-3">Select Project<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9">
					<select class="chosen-select form-control">
					  <option>Select Project</option>
					  <option>Project-1</option>
					  <option>Project-2</option>
					  <option>Project-3</option>
					</select>
				</div>
			  </div>
               
			<div class="form-group">
				<label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="First Name" value="">
                  </div>
			  </div>               
                
            <div class="form-group">
              <label class="col-md-3">Start & End date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
					<div class="row">
					  <div class="col-lg-6 evDaPa">
						  <input type="text" class="form-control calendar" value="" name="Date" id="" placeholder="Start date">
					  </div>
					  <div class="col-lg-6">
						  <input type="text" class="form-control calendar" value="" name="Date" id="" placeholder="End date">
					  </div>					  
					</div>
              </div>
            </div>
               
		</div>
	</div>
			</div>
  
<div class="expensHead">
	<a href="#" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask"><i class="fa fa-plus"></i>&nbsp; Row</a>
	<div class="clearfix"></div>
</div>

<jsp:include page="addExpenseEntries.jsp"></jsp:include>


                
<div class="form-footer clearfix mt">
		  <div class="col-lg-12">
			<div class="pull-right">
			  <input type="button" value="Save" class="btn btn-sm btn-primary">
			  <input type="button" value="Sub for Approval" class="btn btn-sm btn-success ml10">
			  <input type="button" value="Cancel" class="btn btn-sm btn-default ml10">
			</div>
		  </div>
		</div>
          
      <!-- Content Area Ends--> 
	  </div>
  </section>
  <!-- Content Area Ends--> 
  
  <!--   Error Message   -->
  <div class="vendor_wrap_mask"></div>
  <div class="vendor_error">
    <textarea placeholder="Enter message"></textarea>
    <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
  
  <!--   Error Message   --> 
  
  
  
  
	
</div>

   
    <script>
// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	
</script>    

     <script>
  $( function() {
    $( "#datepicker,#datepicker2" ).datepicker();
	$("#datepicker5" ).datepicker();
  } );
  </script>
  

  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				});
 		</script>






 
<jsp:include page="../masterFooter.jsp" />