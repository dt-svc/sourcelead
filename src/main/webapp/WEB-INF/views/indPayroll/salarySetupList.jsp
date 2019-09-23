<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script> 
	$(document).ready(function(){
		getPayRollSalarySetupList();
	});
</script>

<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Salary Setup</h2>
  </div>
  <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/payrollConfiguration">Payroll Configure</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/payheadList">Payhead List</a> &raquo; Salary Setup</div>
</section>
 <div class="clearfix"></div>
 
  <div class="tablelist-wrap">
   
    <div class="timesheet_head minHeight40">
      <div class="clearfix"></div>
      <div class="remove_search">
        <input type="text" placeholder="Search" id="salarySetupDataTableSearch" onblur="getPayRollSalarySetupList();"/>
      </div>
    </div>
    
	<div class="styleTables">
		<div id="salarySetupListLoading"></div>
		<div id="choicepopUp"></div>
		<div id="payrollSalarySetupList" class="project_wrap_table datatable_filter"></div>
	</div>
  </div>
</section>
  
  
 <div class="comMask"></div>
 <div class="modal salarySetup" id="salarySetup"></div>

<script>	
	/* $("#aa").click(function() {
	  $(".subMenuView").slideToggle(100);
	}); */
//Salary setup popup
function salarySetup(event) {
	$(".salarySetup, .comMask").fadeIn();
	var uuid = $(event).attr('id');
	 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
    $.ajax({
			type: "POST",
		    url: pmsUrl+"/getSalarySetupForPayhead?"+tokenParamName+"="+tokenValue+"&uuid="+uuid, 
		    success: function(data) {
		    	$('#salarySetup').empty();
		    	$('#salarySetup').append(data);
		    },
		});
}
function closesalarySetup() {
	$(".salarySetup, .comMask").fadeOut();
}
</script>



<script>
 function getPayRollSalarySetupList(){
	 $("#payrollSalarySetupList").empty();
	  var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  $("#salarySetupListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  salarySetupListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  salarySetupListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  salarySetupListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  salarySetupListDate = 'YYYY-DD-MM'; 
	  }
	  
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +"</div>";
	 	result = result + '<table id="salarySetupListTable"  cellspacing="0" width="100%" >';
	 	result = result +"<thead>";
		result = result +"	<tr>";
		result = result +"		<th>Pay head</th>";
		result = result +"		<th>Method</th>";
		result = result +"		<th>Formula</th>";
		result = result +"		<th>Action</th>";
		result = result +"	</tr>";
		result = result +"</thead>";
	    result = result +"</table>";
	    $("#payrollSalarySetupList").empty();
	    $("#payrollSalarySetupList").append(result);
	    
	    payrollSalarySetupList();
	  
 }
 </script>
 
 <script>
 function payrollSalarySetupList(){
	 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	 var searchValue = $("#salarySetupDataTableSearch").val();
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 var listType = "SSList";
	 searchValue = searchValue ? searchValue : "";
	 var url = pmsUrl+"/getPayRollSalarySetupListWithPagination?searchValue="+searchValue+"&locationCode="+locationCode+"&listType="+listType;
	 salarySetupListPagination(url,sourceleadUrl);
	 $("#salarySetupListLoading").removeAttr("style");
	 
 }
 </script>
 
 
 
 <script>
function salarySetupListPagination(url,sourceleadUrl){
	var colIndex = 0;
    $('#salarySetupListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "payhead" },
            { "data": "method"},
            { "data": "formula"},
            { "data": "actions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "payhead",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		 var pname = opt.name+" ("+opt.shortCode+")";
	    	  		 result = result +"<td><a href='${SourceLeadWeb_pmsUrl}/payrollPayheadSummary?uuid="+opt.uuid+"'><div class='proj_show'><div class='pull-left' title='"+pname+"'>"+pname+"</div><i class='fa fa-binoculars'></i></div></a></td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "method",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  var pMethod = "";
            	  if(opt.method != null && opt.method != '' && opt.method != 'undefined'){
            		  pMethod = opt.method;
            	  }else{
            		  pMethod = "--";
            	  }
            	  result = result +"<td><div><span id='methodSpan"+opt.uuid+"'>"+pMethod+"</span></div></td>";
	              return result;
              	 	
              }
	          },{
		    	  "targets": colIndex++,
	              "data": "formula",
	              "data": "method",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var pFormula = "";
	            	  if(opt.formula != null && opt.formula != '' && opt.formula != 'undefined'){
	            		  
		            	  if( opt.method == '% of CTC'){
		            		  pFormula = opt.formula+"%";           		  
		            	  }else{
		            		 pFormula = opt.formula;
		            	      }
		            	  }
	            	  else{
	            		  pFormula = "--";
	            	  }
	            	  result = result +"<td><div class='tblminwidth-100 text-center' id='formulaSpan"+opt.uuid+"'>"+pFormula+"</div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td>";
	            	    result = result +"<div class='tbldata-dotdot_80'>";
	            	    if(opt.isFormulaEditable != 'N'){
	            	    	result = result +"<a href='javascript:void(0)' title='Set' id="+opt.uuid+" onClick='salarySetup(this); '>Set</a>";
	            	    }
	            	    result = result +"</div>";
	            	    result = result +"</td>";
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#salarySetupListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#salarySetupDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#salarySetupDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#salarySetupDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		payrollSalarySetupList();
   	    	}else{
   	    		$("#salarySetupDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#salarySetupDataTableSearch').val(searchData);
    	   }
    	});
}

</script>

<jsp:include page="../masterFooter.jsp" />