<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
$(document).ready(function(){
	getLetterHeadList();
});
</script>

  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Letterhead List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>"> Templates </a>&raquo; Letterhead List</div>
    </section>
    

<div class="project_wrap">

	 <div class="reportHead clearfix">
	    <ul class="pull-right list-unstyled list-inline">
         	<li><a href="<c:url value="/app/letterHead"/>" title="Create Letterhead" class="newAdBtn mr5">+ Letterhead</a> </li>
         	<li class=""><input type="text" class="repSearchBar" placeholder='Search' id='letterHeadTableSearch'/><span class="repSearchIcon" onClick=""></span></li>
		</ul>
	</div>	

     <div class="candidate_wrap_table datatable_filter">
       <div id="letterHeadList"></div>
      </div>
      
</div>
 
  </section>
  
  
  
  
  <script>
  function getLetterHeadList(){
		var searchValue = $("#letterHeadTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getAllLetterHeadList?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue;
		prepareLetterHeadList(url);
  }
  </script>
  
  <script>
function prepareLetterHeadList(url){
	 $("#letterHeadList").removeAttr("style");
 	 $("#letterHeadList").empty();
 	 var result = "";
 	 
/*  	result = result +" <div class='clearfix'>";
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='letterHeadTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>"; */
 	
 	result = result +"<table id='letterHeadPagination' class='display' cellspacing='0' width='100%' >"; 
    result = result + '	<thead>';
	result = result + '   <tr>';
	result = result + '     <th>Title</th>';
	result = result + '    <th>Description</th>';
 	result = result + '    <th>Action</th>';
 	result = result + '   </tr>';
 	result = result + '	</thead>';
    result = result +"</table>";
    $("#letterHeadList").append(result);
    letterHeadPagination(url); 
}
</script>

<script>
function letterHeadPagination(url){
	
	var colIndex = 0;
    $('#letterHeadPagination').DataTable( { 
        "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "Title" },
            { "data": "Description"},
            { "data": "Action"}
            
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "Title",
	    	  	"render": function(data, type, report, meta) {
	    	  		  var result = "";
	    	  			if(report.letterHeadName != null && report.letterHeadName != '' && report.letterHeadName !='undefined'){
	 	                   result = result + '<div><span title="'+report.letterHeadName+'">'+report.letterHeadName+'</span></div>';
	 	     			} else{
	 	    		       result = result + '<div><span>---</span></div>';
	 	    		    }
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
	    	  "sortable": false,
              "data": "Description",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  if(report.letterHeadDescription != null && report.letterHeadDescription != '' && report.letterHeadDescription !='undefined'){
	                   result = result + '<div><span title="'+report.letterHeadDescription+'">'+report.letterHeadDescription+'</span></div>';
	     			} else{
	    		       result = result + '<div><span>---</span></div>';
	    		  }
	              return result;
              		
              }
	          },{
             "targets": colIndex++,
             "data": "Action",
             "render": function ( data, type, report, meta ) {
             	var result = "";
             	result = result + '    	<td>                                                                                                          '; 
				result = result + '       <div class="tblminwidth-75">                                                                                '; 
				result = result + '       		<ul class="list-unstyled list-inline actionIcons clearfix ml5">                                       '; 
				result = result + '       			<li><a href="javascript:void(0)" title="View"><i class="fa fa-eye textView" onclick="previewEmpTemplate(\''+report.letterHeadUUID+'\');"></i></a></li>         '; 
				result = result + '       			<li><a href="'+sourceleadUrl+'/editLetterhead?letterHeadUUID='+report.letterHeadUUID+'" title="Edit"><i class="fa fa-pencil textEdit"></i></a></li>      '; 
				result = result + '       			<li><a href="javascript:void(0)" title="Delete"><i class="fa fa-trash textDelete" onclick="deleteLetterhead(\''+report.letterHeadUUID+'\');"></i></a></li>   '; 
				result = result + '       		</ul>                                                                                                 '; 
				result = result + '       </div>                                                                                                      '; 
				result = result + '     </td>																											';
             	
             	return result;
             }
 	          
             }], 
          
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20,], [10, 15, 20,]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>" 
 	     }
    } );
    $("#letterHeadPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#letterHeadTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getLetterHeadList();
    	   }
    	});
}
</script>

<script>
function deleteLetterhead(letterHeadUUID){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/deleteLetterhead?"+tokenParamName+"="+tokenValue+"&letterHeadUUID="+letterHeadUUID,
		    success: function(data) {
// 			 	location.replace(sourceleadUrl+"/getAllEmploymentVerificationListByOrgLocationId");
			 	getLetterHeadList();
		    }
	     });
}
</script>

<script>
function previewEmpTemplate(letterHeadUUID){
	window.open(sourceleadUrl+'/previewLetterHead/'+letterHeadUUID+'','_newtab');
}
</script>
  
  
  <jsp:include page="../masterFooter.jsp" />