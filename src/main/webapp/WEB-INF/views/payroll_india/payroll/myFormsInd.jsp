<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/tax/my_payroll.js" type="text/javascript"></script>
	<div class="tablelist-wrap">
		<div class="positionRelative reportHead clearfix">
			<ul class="pull-right list-unstyled list-inline">
				<li>
					<select class="selectpicker mr5"
						data-live-search="true" title="Select Year" id="financialYear" onchange="getFormSubmissionMasterList();">
						<c:forEach items="${yearList}" var="year">
							<option ${year.value==true?"selected=selected":""} value="${year.key}">${year.key}</option>
						</c:forEach>
					</select>		
				</li>
				<li>
					<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Submit Form <i class="fa fa-caret-down"></i></a>
					<div class="action-pop">
						<span> 
							<a href="/app/itDeclaration?autoSelectUser=true">IT Declaration</a>
							<!-- <a href="javascript:void(0)">IT Challan</a>
							<a href="javascript:void(0)">Form 16</a> -->
						</span>
					</div>
				</li>
			  	<li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dynamicUserDataTableSearch'/><span class="repSearchIcon" onClick="getDynamciUserList();"></span></li>
	        </ul>
		</div>			
		<div class="clearfix">
			<table id="submissionTable">
				<thead>
					<tr>
						<th>Assessment Year</th>
						<th>Form Type</th>
						<th>Filling Date</th>
						<th>Status</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody id="submissionList">
				</tbody>
			</table>
			<table id="noResultsTable" style="display: none;">
				<tr class='odd'>
					<td colspan='7' class='dataTables_empty' valign='top'></td>
				</tr>
			</table>
		</div>
		<div class="clearfix"></div>
		<!-- <div id="paginationDiv" class="clearfix">
			<div class="col-md-6">
				<div class="pull-left mt5">
					<label class="pull-left">
					<span class="pull-left mt10 mr5">Show</span>
						<div class="dropdown-select pull-left mr5 tblminwidth-50">
							<select id="pageSize" onchange="getFormSubmissionMasterList();" class="form-control" style="width:50px;">
								<option value="5">5</option>
								<option value="10">10</option>
								<option value="15">15</option>
								<option value="20">20</option>
							</select>
						</div>
					<span class="pull-left mt10">entries</span>
					</label>
					<span class="pull-left mt10 ml" id="pageMsg"></span>
				</div>
			</div>
			<div id="paginationholder" class="col-md-6 pagiPad">
				<ul id="pagination-demo"
					class="pagination-sm pull-right pagination light-theme simple-pagination">
					<li class="disabled"><span class="current prev">Prev</span></li>
					<li class="active"><span class="current">1</span></li>
					<li class="disabled"><span class="current next">Next</span></li>
				</ul>
			</div>
		</div> -->
	</div>

<script type="text/javascript">
	$(document).ready(function () {
		getFormSubmissionMasterList();
	});
	function getActions(){
		$(".action-pop").slideToggle(200);
	}	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
	});
</script>




<%-- 


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="tablelist-wrap">
        <div class="timesheet_head minHeight">
          <div class="col-md-7 mt">
            <div class="row">
              <div class="col-md-3 paddingRight">
                <input name="" class="form-control calendar" placeholder="Selct Year" type="text" id="payDay">
              </div>
              <div class="col-md-2">
              	<a href="run-payroll.html" class="btn btn-success btnSuccess btn-sm anchorWhite"><i class="fa fa-search"></i></a>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
          <div class="remove_search">
            <input placeholder="Search" type="text">
          </div>
        </div>
          <div class="styleTables clearfix mt">
            <table>
              <thead>
                <tr>
                  <th>Assessment Year</th>
                  <th>Form Type</th>
                  <th>Filling Date</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">IT Declaration</div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td><div class="tblminwidth-150 clearfix">Submitted</div></td>
                </tr>
                 <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">Form-16</div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							31/05/2018                 	
					  </span>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">Processed</div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        <div class="clearfix"></div>
        <nav>
          <ul id="paging" class="pagination pull-right mr">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
      </div> --%>
