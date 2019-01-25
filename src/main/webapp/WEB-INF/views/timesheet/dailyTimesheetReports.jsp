<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<body>

 <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Daily Timesheet</h2>
      </div>
      <div class="pull-right brud-crum"><a href="/app/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="/pms/reportsDashboard">Reports Dashboard</a> &raquo; Daily Timesheet</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
      <div class="clearfix form-horizontal minHeight_225 minHeightcol5">
        <div class="row min">
           
            <div class="col-md-5">
              <label class="clearfix">Type of Project</label>
              <div class="clearfix">
                <select class="form-control sel">
                  <option>Select</option>
                  <option selected>All</option>
                  <option>Client</option>
                  <option>Internal</option>
                </select>
              </div>
            </div>
            <div class="col-md-5">
             <div class="row">
            	  <div class="col-md-6">
              <label class="clearfix">From Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" class="form-control calendar"  value="" id="fromDate">
                 <span class="input-group-addon">
                 	<i class="colorRed fa fa-times cursorPointer"></i>
                 </span>
              </div> 
              </div>
            	  <div class="col-md-6">
              <label class="clearfix">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" class="form-control calendar"  value="" id="toDate">
                 <span class="input-group-addon">
                 	<i class="colorRed fa fa-times cursorPointer"></i>
                 </span>
              </div>
              </div>
            
            </div>
            </div>
            
           <div class="col-md-5">
              <label class="clearfix">By Project</label>
              <div class="clearfix">
                <select class="form-control chosen-select" data-placeholder="Select Project" multiple>
                  <option>Project-1</option>
                  <option>Project-2</option>
                  <option>Project-3</option>
                  <option>Project-4</option>
                  <option>Project-5</option>
                </select>
              </div>
            </div><div class="col-md-5">
              <label class="clearfix">Emp Type</label>
              <div class="clearfix">
                <select class="form-control chosen-select" data-placeholder="Select Emp Type" multiple>
                  <option>Emp type-1</option>
                  <option>Emp type-2</option>
                  <option>Emp type-3</option>
                  <option>Emp type-4</option>
                  <option>Emp type-5</option>
                </select>
              </div>
            </div>
			<div class="clearfix"></div>
             <div class="col-md-5">
              <label class="clearfix">By Associate</label>
              <div class="clearfix">
                <select class="form-control chosen-select" data-placeholder="Select Associate" multiple>
                  <option>Associate-1</option>
                  <option>Associate-2</option>
                  <option>Associate-3</option>
                  <option>Associate-4</option>
                  <option>Associate-5</option>
                </select>
              </div>
            </div>
             <div class="col-md-5">
              <label class="clearfix">Export to</label>
              <div class="clearfix">
                <select class="form-contro sel">
                  <option>PDF</option>
                  <option>Export</option>
                </select>
              </div>
            </div>
        <div class="clearfix"></div>
												
      </div>
</div>
</div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button">
            <input value="Reset" class="btn btn-default ml10" type="button">
          </div>
        </div>
      </div>  
      </section> 
  	
  	<script type="text/javascript">
	  $(function() {
		$("#fromDate").datepicker();
		$('#toDate').datepicker();
	  });
	</script>       
      
      
</body>
<jsp:include page="../masterFooter.jsp" />
 
      
    