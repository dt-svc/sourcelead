<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Shift</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management &raquo; Shift</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
    <div class="form-group">
       <label class="col-md-3">Shift Name<span class="text-danger paddingLeft">*</span></label>
       <div class="col-md-9">
       <input type="text" placeholder="Shift Name" class="form-control">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Time in</label>
        <div class="col-md-9">
       <input type="text" placeholder="HH:MM" class="timepicker form-control">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Time Out</label>
        <div class="col-md-9">
       <input type="text" placeholder="HH:MM" class="timepicker form-control">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Shift Description</label>
       <div class="col-md-9">
 			<textarea rows="2" placeholder="Shift Description" class="form-control"></textarea>
       </div>
    </div>
          </div>
        </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" class="btn btn-primary">
            <input type="button" value="Cancel" class="btn btn-default ml10">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>


<jsp:include page="../masterFooter.jsp" />
