<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/resources/css/chosen.min.css">

<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" onclick="bulkAdjustmentClose();">×</button>
				 <h4 class="modal-title">Bulk Pay Adjustments</h4>
			 </div>
			<div class="modal-body" style="min-height: 150px;">
					<div class="row mb10 clearfix">
					<div class="col-md-6">
							<div class="clearfix">
								<div class="checkbox-new pull-left ml">
										<input id="sae" class="checkbox-custom" name="selectAllEmployees" value="selectAllEmployees" type="checkbox">
										<label for="sae" class="checkbox-custom-label"></label><span class="ml10">Select All Employees</span>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="clearfix">
							<select class="chosen-select" multiple data-placeholder="Select Employees" id="selectEmployees">
								<option>Employee1</option>
								<option>Employee2</option>
								<option>Employee3</option>
								<option>Employee4</option>
								<option>Employee5</option>
							</select>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					<hr>
					<div class="clearfix mt25 minDivHeight">
						<div class="mb clearfix">
							<label class="col-md-3 textBold text-right">Bonus</label>
							<div class="col-md-6">
							<input type="text" value="" class="form-control input-sm">
							</div>
						</div>
						<div class="mb clearfix">
							<label class="col-md-3 textBold text-right">Bonus</label>
							<div class="col-md-6">
							<input type="text" value="" class="form-control">
							</div>
						</div>
						<div class="mb clearfix">
							<label class="col-md-3 textBold text-right">Bonus</label>
							<div class="col-md-6">
							<input type="text" value="" class="form-control">
							</div>
						</div>
					</div>
				</div>
			
	<div class="modal-footer clearfix">
		<button type="button" class="btn btn-primary" onclick="bulkAdjustmentSaving();">Save</button>
        <button type="button" class="btn btn-default" onclick="bulkAdjustmentClose();">Cancel</button>
     </div>
     </div>
     </div>
</div>
 
<script src="/resources/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
    var staticTime='${staticTime}';
    var config = {
			  '.chosen-select'           : {},
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
    $("#selectEmployees").chosen();
 </script>
 