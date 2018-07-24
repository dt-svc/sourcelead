<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
   getPaySheetFrequencySummary();
});
</script>


<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll List Summary</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payroll List Summary</div>
    </section>
    
    <div class="clearfix"></div>
    <div class="datatable_filter">
    	<div id="paySummaryDiv"></div>
    </div>
    <div class="clearfix"></div>
    
<!--     <div class="clearfix"></div>
    <div class="timesheet_out">
      <div class="tablelist-wrap">
        <div class="timesheet_head minHeight">
          <div class="col-md-7 mt">
            <div class="row">
              <div class="col-md-3">
                <div class="dropdown-select">
                  <select>
                    <option selected>Pay Period</option>
                    <option>Group-1</option>
                    <option>Group-2</option>
                    <option>Group-3</option>
                  </select>
                </div>
              </div>
              <div class="col-md-3 paddingRight">
                <input name="" class="form-control calendar" placeholder="Pay Day" type="text" id="payDay">
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
                  <th>Emp Name</th>
                  <th>Rate</th>
                  <th>Regular Hours</th>
                  <th>Overtime Hours</th>
                  <th>Gross Pay</th>
                  <th>Pre-Tax Deduction</th>
                  <th>Post-Tax Deduction</th>
                  <th>Taxes</th>
                  <th>Net Pay</th>
                  <th>Last Pay</th>
                  <th>YTD</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tbldata-dotdot-vsm">Laxmisaraswathi</div></td>
                  <td><div class="tblminwidth-50">20$/hrs</div></td>
                  <td><div class="tblminwidth-50 clearfix">40Hrs</div><div class="tblminwidth-50 clearfix">800$</div></td>
                  <td><div class="tblminwidth-50 clearfix">10Hrs</div><div class="tblminwidth-50 clearfix">300$</div></td>
                  <td><div class="tblminwidth-50 clearfix">100$</div></td>
                  <td><div class="tblminwidth-50 clearfix">100$</div></td>
                  <td><div class="tblminwidth-50 clearfix">50$</div></td>
                  <td><div class="tblminwidth-50 clearfix">850$</div></td>
                  <td><div class="tblminwidth-50 clearfix"><a href="javascript:void(0)" onclick="usRunpayroll();">200$</a></div></td>
                  <td><div class="tblminwidth-50 clearfix">850$</div></td>
                  <td><div class="tblminwidth-50 clearfix">20,000$</div></td>
                  <td>
					  <div class="tblminwidth-30 text-center">
					  	<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a>
					  </div>
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
      </div>
    </div> -->
  </section>
  <!-- Content Area Ends--> 
  
  
<script>
function getPaySheetFrequencySummary(){
	var sourceleadUrl = '${sourceleadUrl}';
	var uuid = '${paySheetFrequencyUUID}';
	 $.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getPaySheetFrequencySummary?uuid="+uuid,
		success : function(data) {
			var result = "";
	        	result = result +'<table id="paySheetSummary">';
		    	result = result +'<thead>';
		    	result = result +'	<tr>';
		    	result = result +'	<th>Emp Name</th>';
		    	result = result +'	<th>Rate</th>';
		    	result = result +'	<th>Regular Hours</th>';
		    	result = result +'	<th>Overtime Hours</th>';
		    	result = result +'	<th>Gross Pay</th>';
		    	result = result +'	<th>Pre-Tax Deduction</th>';
		    	result = result +'	<th>Post-Tax Deduction</th>';
		    	result = result +'	<th>Taxes</th>';
		    	result = result +'    <th>Net Pay</th>'; 
		    	result = result +'    <th>Last Pay</th>'; 
		    	result = result +'    <th>YTD</th>'; 
		    	result = result +'     <th>Action</th>';
		    	result = result +'    </tr>';
		    	result = result +'</thead>	';
		    	result = result +'<tbody>';
		    	
		    	for (var i=0; i<data.length; i++)
   				{
			 
		    	var report = data[i];
		    	result = result +'<tr>																													';	
	            result = result +'  <td><div class="tbldata-dotdot-vsm">'+report.empName+'</div></td>                                                      ';
	            result = result +'  <td><div class="tblminwidth-50">'+report.payRate+'</div></td>                                                                  ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.regularHours+'</div><div class="tblminwidth-50 clearfix">800$</div></td>            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.overtimeHours+'</div><div class="tblminwidth-50 clearfix">300$</div></td>            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.grossPay+'</div></td>                                                            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.preTaxDeduction+'</div></td>                                                            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.postTaxDeduction+'</div></td>                                                             ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.taxes+'</div></td>                                                            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix"><a href="javascript:void(0)" onclick="usRunpayroll();">'+report.netPay+'</a></div></td> ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">--</div></td>                                                            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">--</div></td>                                                         ';
	            result = result +'  <td>                                                                                                                ';
				result = result +'	  <div class="tblminwidth-30 text-center">                                                                          ';
				result = result +'	  	<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a>                                  ';
				result = result +'	  </div>                                                                                                            ';
	            result = result +'  </td>                                                                                                               ';
	            result = result +'</tr>                                                                                                                 ';
   			}
		    	result = result +'</tbody>';
			    $("#paySummaryDiv").empty();
			    $("#paySummaryDiv").append(result);
			    paySummaryPagination();
		}
	});
}
</script>

 <script>
function paySummaryPagination(){  
    $('#paySheetSummary').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<jsp:include page="../masterFooter.jsp" />


 		