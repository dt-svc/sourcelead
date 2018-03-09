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
<script type="text/javascript" src="/resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script>
<script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script>


<style type="text/css">
.active {
color: red !important;
}

</style>
<section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix cusFieldHead">
      <div class="pull-left positionRelative"><h2>Petition Templates</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; Petition Templates</div>
	<div class="clearfix"></div>
    </section>
    
    
<section class="immigraTabsWrap">
	  <div class="cusFielInn">

	<div class="dropdown">
		<strong>Column Chooser </strong>
		<button class="glyphicon glyphicon-plus" type="button" id="menu1"
			data-toggle="dropdown"></button>

		<ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
			<li><a class="toggle-vis " data-column="0">Name</a></li>
			<li><a class="toggle-vis" data-column="1">Description</a></li>
			<li><a class="toggle-vis" data-column="2">No. of Docs</a></li>
			<li><a class="toggle-vis" data-column="3">No. of Forms</a></li>
			<li><a class="toggle-vis" data-column="4">No. of Tasks</a></li>
			<li><a class="toggle-vis" data-column="5">Action</a></li>
		</ul>
	</div>
	<div class="immigraTabsHead">
		  	<a href="<c:url value='${appPath}/immigration/addNewPetitionTemplate'/>"><i class="glyphicon glyphicon-plus"></i> Add Template</a> 
		  	<span><input class="immigraTabSerach" id ="immiTemplateSerach" placeholder="Name/Description" type="text"></span>
		</div>
		
	  	<div id ="dynamicImmigrationListViewDiv" class="project_wrap_table datatable_filter">
		</div>
	  </div>
	</section>    
  </section>
  <jsp:include page="../masterFooter.jsp" />
  <script>
  var table ="";
  var inlineSearch='';
   $( document ).ready(function() {
	 
	   getDynamicTemplateList();
	 
	    $('a.toggle-vis').on( 'click', function (e) {
	        e.preventDefault();
	        // Get the column API object
	        var column = table.column( $(this).attr('data-column') );
	        $(this).toggleClass("active");
	        // Toggle the visibility
	        column.visible( ! column.visible() );
	    } );
	    
  });
  
  
  function getDynamicTemplateList(){
	 
	  var result = "";
		result = result + '<table id="dynamicTemplateListTbl">';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Template Name</th>';
		result = result + '<th>Description</th>';
		result = result + '<th>No. of Docs</th>';
		result = result + '<th>No. of Forms</th>';
		result = result + '<th>No. of Tasks</th>';
		result = result + '<th>Action</th>';
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
	  table =   $('#dynamicTemplateListTbl').DataTable( { 
	  "processing": true,
	  "serverSide": true,
	  "searching": false,
	  "colReorder": true,
	  //'dom': 'Rlfrtip',
	 // "stateSave": true, // used to save the page state local or server side.
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
			  result = result + "<td><div class='tbldata-dotdot-me'>"+opt.name+"</div></td>"
			  }else{
			  result = result + "<td><div class='tbldata-dotdot-me'>---</div></td>";
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
			  result = result + "<td><div class='tbldata-dotdot-big'>"+opt.description+"</div></td>"
			  }else{
			  result = result + "<td><div class='tbldata-dotdot-big'>---</div></td>";
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
				  result = result + "<td><div class='tbldata-dotdot-vsm'>"+opt.docsCount+"</div></td>"
			  }else{
			  result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
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
				  result = result + "<td><div class='tbldata-dotdot-vsm'>"+opt.formsCount+"</div></td>"
			  }else{
			  result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
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
					  result = result + "<td><div class='tbldata-dotdot-vsm'>"+opt.tasksCount+"</div></td>"
				  }else{
				  result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
				  }
				   
				  return result;
				  }
				  },
			  {
			  "targets": colIndex++,
				   /*"data": "formsCount",*/
				  "render": function ( data, type, opt, meta ) {
				  var result = "";
				  result = result + '<td><div><i id="edit'+opt.id+'" onclick="immigration.editTemplateDetailsByRowId('+opt.id+');" class="glyphicon glyphicon-pencil"></i><i id="delete'+opt.id+'" onclick="immigration.deleteTempalteDetailsByRowId('+opt.id+');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
				  return result;
				  }
				  },
				  
			  ],
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
	  
	  $("#immiTemplateSerach").keyup(function(e) {
	  if(e.which === 13) {
		  getDynamicTemplateList();
	  }
	  });
	 
	 $("table").colResizable(); // for resizing columns in tables.
  } 
  </script>
 
  