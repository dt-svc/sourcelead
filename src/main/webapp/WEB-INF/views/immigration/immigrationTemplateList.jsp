<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/resources/js/immigration.js"></script>
<section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix cusFieldHead">
      <div class="pull-left positionRelative"><h2>Petitation Templates</h2></div>
      <div class="pull-right brud-crum">Home &raquo; Petitation Templates</div>
	<div class="clearfix"></div>
    </section>
    
    
<section class="immigraTabsWrap">
	  
	  <div class="cusFielInn">

		<div class="immigraTabsHead">
		  	<a href="<c:url value='${appPath}/immigration/addNewPetitionTemplate'/>"><i class="glyphicon glyphicon-plus"></i> Add Template</a> 
		  	<span><input class="immigraTabSerach" id ="immiTemplateSerach" placeholder="search" type="text"></span>
		</div>
		
	  	<div id ="dynamicImmigrationListViewDiv" class="immigraTable">
		</div>
	  </div>
	    
	</section>    
  </section>
  <jsp:include page="../masterFooter.jsp" />
  <script>
   $( document ).ready(function() {
	  getDynamicTemplateList();
  });
  
  
  function getDynamicTemplateList(){
	 // alert("hi");
	  var result = "";
		result = result + '<table id="dynamicTemplateListTbl">';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Template Name</th>';
		result = result + '<th>Description</th>';
		result = result + '<th>No. of Docs</th>';
		result = result + '<th>No. of Forms</th>';
		result = result + '<th>No. of Tasks</th>';
		result = result + '<th>&nbsp;</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		$("#dynamicImmigrationListViewDiv").empty();
		$("#dynamicImmigrationListViewDiv").append(result);
		
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#immiTemplateSerach").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/immigration/getImmigrationTemplateList?searchValue="+searchValue;
		dynamicImmigrationListPagination(url);
  } 
  
  function dynamicImmigrationListPagination(url){
	  var colIndex = 0;
	  $('#dynamicTemplateListTbl').DataTable( { 
	  "processing": true,
	  "serverSide": true,
	  "searching": false,
	  "ajax": $.fn.dataTable.pipeline( {
	  url: url,
	  method: 'POST',
	  pages: 10
	  }),
	  "columns": [
			  { "data": "name" },
			  { "data": "description"},
			  { "data": "docsCount"},
			  { "data": "formsCount"},
			  { "data": "tasksCount"}
	  		],
	 "columnDefs": [ {
			  "targets": colIndex++,
			  "data": "name",
			  "render": function(data, type, opt, meta) {
			  var result = "";
			  if(opt.name !=null && opt.name !='' && opt.name !='undefined'){
			  result = result + '<td>'+opt.name+'</td>'
			  }else{
			  result = result + '<td>---</td>';
			  }
			  return result;
			  }
			  },
	 		 {
			  "targets": colIndex++,
			  "data": "description",
			  "render": function ( data, type, opt, meta ) {
			  var result = "";
			  if(opt.description !=null && opt.description !='' && opt.description !='undefined'){
			  result = result + '<td>'+opt.description+'</td>';
			  }else{
			  result = result + '<td>---</td>';
			  }
			  return result;
			  }
			  },
			  {
			  "targets": colIndex++,
			  "data": "docsCount",
			  "render": function ( data, type, opt, meta ) {
			  var result = "";
			  if(opt.docsCount !=null && opt.docsCount !='undefined' ){
			  result = result + '<td>'+opt.docsCount+'</td>';
			  }else{
			  result = result + '<td>---</td>';
			  }
			  return result;
			  }
			  },
			  {
			  "targets": colIndex++,
			   "data": "formsCount",
			  "render": function ( data, type, opt, meta ) {
			  var result = "";
			  if(opt.formsCount !=null && opt.formsCount !='undefined'){
				  result = result + '<td>'+opt.formsCount+'</td>';
			  }
			  else{
				  result = result + '<td>---</td>';
				  }
			  return result;
			  }
			  },
			  {
			  "targets": colIndex++,
			   "data": "tasksCount",
			  "render": function ( data, type, opt, meta ) {
			  var result = "";
			  if(opt.tasksCount !=null && opt.tasksCount !='undefined'){
				  result = result + '<td>'+opt.tasksCount+'</td>';
			  }
			  else{
				  result = result + '<td>---</td>';
				  }
			  return result;
			  }
			  }],
	  "scrollCollapse": false,
	  "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	  "aaSorting": [[0,'asc']],
	  "bLengthChange": true,
	  "bDestroy": true,
	  "ordering": true,
	  "initComplete": function( settings, json ) {
	 
	  },
	  "oLanguage": {
	  "sSearch": "",
	  "sZeroRecords": "", "sEmptyTable": "", 
	  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	  }
	  });
	  $("#dynamicTemplateListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	  /* $("input[type=search]").hide(); */
	  $("#immiTemplateSerach").keyup(function(e) {
	  if(e.which === 13) {
		  getDynamicTemplateList();
	  }
	  });
  } 
  </script>
  
  