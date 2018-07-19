<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Approval</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Add Configure</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
      <div class="clearfix form-horizontal">
         <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Approvers</label>
				<div class="col-md-12">
					<select class="form-control chosen-select" multiple data-placeholder="Select Approvers">
							<option>Approvers1</option>
							<option>Approvers2</option>
							<option>Approvers3</option>
					</select>
				</div>
            </div>
			  </div>
            <div class="col-md-5">
            <div class="form-group">
         <label class="col-md-12">&nbsp;</label>
		<div class="col-md-12">
            <input value="Save" class="btn btn-success btnSuccess" type="button">
            <input value="Cancel" class="btn btn-defualt ml10" type="button">
		</div>
            </div>
			</div>
      </div>
	</div>
	<div class="clearfix"></div>
    <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group clearfix">
            	<div class="row">
             <label class="col-md-12">&nbsp;</label>
              <label class="col-md-7">Automatic Payroll</label>
				<div class="col-md-5">
				<label class="switch">
			  <input checked="" type="checkbox">
			  <span class="slider round"></span>
			</label>
				</div>
            </div>
            </div>
		</div>
		</div>

         <div class="clearfix row"> 
          <div class="col-md-5">
            <div class="form-group clearfix">
              <label class="clearfix">Payroll Type</label>
				<div class="clearfix">
					<select class="sel">
						<option selected>Select</option>
						<option>Work Sheet</option>
						<option>General</option>
					</select>
				</div>
            </div>
			</div>
      </div>
     
      <!-- Content Area Ends--> 
    </div>
    
  </section>
  <!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />


 		