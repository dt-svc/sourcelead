<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>New Contract</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; New Contract</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal">
       
        <div class="row newContrWrap">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           
           <h2>Contract Details</h2>
            <div class="form-group">
              <label class="col-md-4">Contract Type<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control">
                    <option>MSA</option>
                    <option>NDA</option>
                    <option>NCA</option>
                    <option>SOW</option>
                  </select>
            </div>
            </div>				
			</div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
                       
            <div class="form-group">
              <label class="col-md-4">Select template<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                <div class="form-icon">
                  <select class="chosen-select form-control">
                    <option>Select template</option>
                    <option>Template-1</option>
                    <option>Template-2</option>
                    <option>Template-3</option>
                  </select>
                  <!-- <i data-target="#addLevel" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i>  --></div>
              </div>
            </div>  
                     
            <div class="form-group">
              <label class="col-md-4">Contract title<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="Company Name" value="">
              </div>
            </div>
             
          <div class="form-group">
              <label class="col-md-4">Owner<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control">
                  <c:forEach var="user" items="${userList}">
	                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
                  </c:forEach>
                   <!--  <option>Select Owner</option>
                    <option>Owner-1</option>
                    <option>Owner-2</option>
                    <option>Owner-3</option> -->
                  </select>
              </div>
          </div>             
            
            <div class="form-group">
              <label class="col-md-4">Start & End date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
					<div class="row">
					  <div class="col-lg-6">
						<div class="input-group" id="startDateDiv"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						  <input type="text" class="form-control" id="startDate" name="startDate" placeholder="Start date">
						</div>
					  </div>
					  <div class="col-lg-6">
						<div class="input-group" id="endDateDiv"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						  <input type="text" class="form-control" id="endDate" name="endDate" placeholder="End date">
						</div>
					  </div>
					</div>
              </div>
            </div>
          
          <div class="form-group">
              <label class="col-md-4">Vendor<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control">
                    <option>Select Vendor</option>
                    <option>Vendor-1</option>
                    <option>Vendor-2</option>
                    <option>Vendor-3</option>
                  </select>
              </div>
          </div>                      
                      
            <div class="form-group">
              <label class="col-md-4">Vendor Contact<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control">
                    <option>Select Vendor Contact</option>
                    <option>Vendor Contact-1</option>
                    <option>Vendor Contact-2</option>
                    <option>Vendor Contact-3</option>
                  </select>
              </div>
            </div>
            
          <div class="form-group">
              <label class="col-md-4">Approver</label>
              <div class="col-md-8">
                  <select class="chosen-select form-control">
                  <c:forEach var="user" items="${userList}">
	                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
                  </c:forEach>
                    <!-- <option>Select Approver</option>
                    <option>Approver-1</option>
                    <option>Approver-2</option>
                    <option>Approver-3</option> -->
                  </select>
              </div>
          </div> 
                       
            <div class="form-group">
              <label class="col-md-4">Description</label>
              <div class="col-md-8">
					<textarea class="form-control" rows="3" name="Project Description"></textarea>
              </div>
            </div>            
            
        </div>
      </div>
      
      
<div class="row newContrBill">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           <h2>Billing Details</h2>
            <div class="form-group">
              <label class="col-md-4">Billing Amount<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
					<div class="row">
					  <div class="col-lg-8">
					  	<input type="text" class="form-control" name="" value="" placeholder="Enter Amount">
					  </div>
					  <div class="col-lg-4">
						<select class="form-control selectpicker">
						  <option>India</option>
						  <option>USA</option>
						  <option>UK</option>
						</select>
					  </div>
					  <span class="hidden">Enter Project Id</span>
					</div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">invoice frequency<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control">
                    <option>Select frequency</option>
                    <option>Frequency-1</option>
                    <option>Frequency-2</option>
                    <option>Frequency-3</option>
                  </select>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Payment frequency<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
              	<input type="text" class="form-control" name="Company Name" value="">
            </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Tax</label>
              <div class="col-md-8">
					<div class="row">
					  <div class="col-lg-8">
					  	<input type="text" class="form-control" name="Company Name" value="">
					  </div>
					  <div class="col-lg-4">
						<select class="form-control selectpicker">
						  <option>%</option>
						  <option>INR</option>
						</select>
					  </div>
					  <span class="hidden">Enter Project Id</span>
					</div>
              </div>
		</div>         
            
        </div>
      </div>      
      
      <div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" class="btn btn-primary">
            <input type="button" value="View" class="btn btn-success ml10">
            <input type="button" value="Cancel" class="btn btn-danger ml10">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>


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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script>
    <script>
    $("#uasrch").selectpicker('refresh');
    </script>


<script>
	$(function() {

		$('#startDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = toDate("#startDate");
				dt.setDate(dt.getDate() + 1);
				$("#endDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		});
		
		$('#endDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = toDate("#endDate");
				dt.setDate(dt.getDate() + 1);
				$("#endDate").datepicker("option", "minDate", dt);
				addValidClass(); 
			}
		});
});
</script>


<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("startDate").value;
		 if(StartDate){
			 $('#startDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var EndDate = document.getElementById("endDate").value;
		 if(EndDate){
			 $('#endDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
}
</script>
<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->




