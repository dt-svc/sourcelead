<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Bonus Setup</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Bonus Setup</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
      <div class="clearfix form-horizontal minHeight_225 minHeightcol5">
        <div class="row min">
           
            <div class="col-md-5">
              <label class="clearfix">Payment Month<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix row">
               <div class="col-md-6 dropdown-select">
                <select>
						  <option selected>Select Month</option>
							  <option>January</option>
							  <option>February</option>
							  <option>March</option>
							  <option>April</option>
							  <option>May</option>
							  <option>June</option>
							  <option>July</option>
							  <option>August</option>
							  <option>September</option>
							  <option>October</option>
							  <option>November</option>
							  <option>December</option>
				</select>
              </div>
              <div class="col-md-6 dropdown-select">
                <select>
						  <option selected>Select Year</option>
						  <option>January</option>
						  <option>February</option>
						  <option>March</option>
				</select>
              </div>
              </div>
            </div>
            <div class="col-md-5">
              <label class="clearfix">Bounus Percentage (%)<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
               <div class="row">
				   <div class="col-md-6">
				   	  <input type="text" class="form-control" name="" placeholder="Min" value="">
				   </div>   	
				   <div class="col-md-6">
				   	<input type="text" class="form-control" name="" placeholder="Max" value="">
				   </div>   	
               </div>
              </div>
            </div>
            <div class="col-md-5">
              <label class="clearfix">Bonus Payment Date</label>
              <div class="clearfix">
                <input type="text" class="form-control calendar" name="" value="">
              </div>
            </div>
            
            <div class="col-md-5">
             <div class="row">
				<div class="col-md-6">
					<label class="clearfix">From Month <span class="text-danger">&nbsp;*</span></label>
						<div class="clearfix dropdown-select">
							<select>
							<option selected="">Select Month</option>
							  <option>January</option>
							  <option>February</option>
							  <option>March</option>
							  <option>April</option>
							  <option>May</option>
							  <option>June</option>
							  <option>July</option>
							  <option>August</option>
							  <option>September</option>
							  <option>October</option>
							  <option>November</option>
							  <option>December</option>
							</select>
						</div>
				</div>
             	<div class="col-md-6">
					<label class="clearfix">To Month<span class="text-danger">&nbsp;*</span></label>
						<div class="clearfix dropdown-select">
							<select>
							<option selected="">Select Month</option>
							  <option>January</option>
							  <option>February</option>
							  <option>March</option>
							  <option>April</option>
							  <option>May</option>
							  <option>June</option>
							  <option>July</option>
							  <option>August</option>
							  <option>September</option>
							  <option>October</option>
							  <option>November</option>
							  <option>December</option>
							</select>
						</div>
				</div>
             </div>
              <label class="clearfix"></label>
            </div>
            <div class="col-md-5">
              <label class="clearfix">Max Month Salary</label>
              <div class="clearfix">
                <input type="text" class="form-control" name="Website" value="" placeholder="">
              </div>
            </div>
        <div class="clearfix"></div>
        <div class="col-md-10">
              <label class="clearfix">Description</label>
              <div class="clearfix">
				  <textarea class="form-control" placeholder="" rows="2"></textarea>
              </div>
            </div>
      </div>
</div>
</div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Setup" class="btn btn-success btnSuccess" type="button">
            <input value="Cancel" class="btn btn-default ml10" type="button">
          </div>
        </div>
      </div>      
		
      <!-- Content Area Ends--> 
    
    
  </section>

<jsp:include page="../masterFooter.jsp" />