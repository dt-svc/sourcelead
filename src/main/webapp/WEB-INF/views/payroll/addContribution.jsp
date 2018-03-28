<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



 
  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Contribution</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Add Contribution</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
      <div class="clearfix form-horizontal minHeight_225">
        <div class="clearfix row">
          <div class="col-md-5">
			<div class="form-group">
			<label class="col-md-12">For Deduction</label>
			<div class="col-md-12">
			<select class="form-control chosen-select">
			<option>Select Deduction Type</option>
			<option>Pretax Selection 125</option>
			<option>401 (k)</option>
			<option>403 (b)</option>
			<option>457 (b)</option>
			<option>Roth 401(k)</option>
			<option>Roth 403 (b)</option>
			<option>Roth 457 (b)</option>
			<option>Simple IRA</option>
			<option>HSA-Single Limit</option>
			<option>HSA-Family Limit</option>
			<option>FSA-Medical</option>
			<option>FSA-Medical</option>
			<option>FSA-Prependent Care</option>
			<option>Post Tax</option>
			<option>Post Tax % Discount Net Pay</option>
			</select>
			</div>
			</div>
			<div class="form-group">
			<label class="col-md-12">Method</label>
			<div class="col-md-12">
			<select class="form-control sel" id="methodContribution" onChange="methodContribution();">
			<option>Select Method</option>
			<option value="amountPerpay">Fixed</option>
			<option value="perDeduction">Percent of Gross Pay</option>
			<option value="perDeduction">Percent of Net Pay</option>
			<option value="perDeduction">Single Formula</option>
			</select>
			</div>
			</div>
			<div class="form-group" id="amountPerpay" style="display: none;">
			<label class="col-md-12">Amount per pay</label>
			<div class="col-md-12">
			<input type="text" class="form-control" value="">
			</div>
			</div>
			<div class="form-group" id="perDeduction" style="display: none;">
			<label class="col-md-12">Per Deduction</label>
			<div class="col-md-12 positionRelative">
			<input type="text" class="form-control" value="">
			<i class="fa fa-percent" style="position: absolute; top: 10px; right:25px;"></i>
			</div>
			</div>           
			</div>
			<div class="col-md-5">
			<div class="form-group">
			<label class="col-md-12">Description</label>
			<div class="col-md-12">
			<textarea rows="2" class="form-control"></textarea>
			</div>
			</div>
        </div>
      </div>
</div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Save" class="btn btn-success btnSuccess" type="button">
            <input value="Cancel" class="btn btn-default ml10" type="button">
          </div>
        </div>
      </div>      
		
      <!-- Content Area Ends--> 
    </div>
    
  </section>


<jsp:include page="../masterFooter.jsp" />