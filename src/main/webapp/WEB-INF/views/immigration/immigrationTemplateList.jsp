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
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		 <table>
			<!-- <body> -->
			  <tr>
				<th>Template Name</th>
				<th>Description</th>
				<th>No. of Docs</th>
				<th>No. of Forms</th>
				<th>No. of Tasks</th>
				<th>&nbsp;</th>
			  </tr>
			  <c:forEach var="temp" items="${templateList}">
				<tr><td>${temp.name}</td><td>${temp.description}</td><td>${temp.docsCount}</td><td>${temp.formsCount}</td><td>${temp.tasksCount}</td><td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td></tr>
			  </c:forEach>
			  <!-- <tr>
				<td><div class="tbldata-dotdot-me">Template 1</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td><div class="tbldata-dotdot-vsm">02</div></td>
				<td><div class="tbldata-dotdot-vsm">05</div></td>
				<td><div class="tbldata-dotdot-vsm">08</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			  <tr>
				<td><div class="tbldata-dotdot-me">Template 1</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td><div class="tbldata-dotdot-vsm">02</div></td>
				<td><div class="tbldata-dotdot-vsm">05</div></td>
				<td><div class="tbldata-dotdot-vsm">08</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr> 
			  <tr>
				<td><div class="tbldata-dotdot-me">Template 1</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td><div class="tbldata-dotdot-vsm">02</div></td>
				<td><div class="tbldata-dotdot-vsm">05</div></td>
				<td><div class="tbldata-dotdot-vsm">08</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>    
			  <tr>
				<td><div class="tbldata-dotdot-me">Template 1</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td><div class="tbldata-dotdot-vsm">02</div></td>
				<td><div class="tbldata-dotdot-vsm">05</div></td>
				<td><div class="tbldata-dotdot-vsm">08</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-me">Template 1</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td><div class="tbldata-dotdot-vsm">02</div></td>
				<td><div class="tbldata-dotdot-vsm">05</div></td>
				<td><div class="tbldata-dotdot-vsm">08</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-me">Template 1</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td><div class="tbldata-dotdot-vsm">02</div></td>
				<td><div class="tbldata-dotdot-vsm">05</div></td>
				<td><div class="tbldata-dotdot-vsm">08</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr> 
			  <tr>
				<td><div class="tbldata-dotdot-me">Template 1</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td><div class="tbldata-dotdot-vsm">02</div></td>
				<td><div class="tbldata-dotdot-vsm">05</div></td>
				<td><div class="tbldata-dotdot-vsm">08</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  --> 
			  						
			<!-- </body> -->
		  </table>
		</div>
		
		<div class="immigraPagi clearfix">
			<div class="immiPagiText">Showing 1 to 6 of 6 entries</div>
			  <ul class="pagination">
				<li><a href="#"> << </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> >> </a></li>
			  </ul>
		</div>
	  </div>
	    
	</section>    
  </section>
  <jsp:include page="../masterFooter.jsp" />
  <script>
/*   $( document ).ready(function() {
	  getDynamicTemplateList();
  });
 */  
  
  /* function getDynamicTemplateList(){
	  alert("hi");
	  var result = "";
		result = result + '<table id="dynamicTemplateListView" class="display" cellspacing="0" width="100%" >';
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
		<c:forEach var='temp' items='${templateList}'>
		result = result + '<tr><td>${temp.name}</td><td>${temp.description}</td><td>${temp.status}</td><td>${temp.docsCount}</td><td>${temp.formsCount}</td><td>${temp.tasksCount}</td></tr>'
		</c:forEach>
		result = result + '</table>';
		$("#dynamicImmigrationListViewDiv").empty();
		$("#dynamicImmigrationListViewDiv").append(result);
		//dynamicImmigrationListPagination(url);
  } */
  
  /* function dynamicImmigrationListPagination(url){
	  var colIndex = 0;
	    $('#dynamicUserListView').DataTable( { 
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
	            { "data": "status"},
	            { "data": "docsCount"},
	            { "data": "formsCount"},
	            { "data": "tasksCount"},
	        ],
			 "columnDefs": [/*  {
			    	  	"targets": colIndex++,
			    	  	"data": "name",
			    	  	"render": function(data, type, userRole, meta) {
			    	  		var result = "";
			    	  		if(userRole.userName !=null && userRole.userName !='' && userRole.userName !='undefined'){
			        			   '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE\',\'ROLE_VIEW_EDIT_ASSOCIATE\',\'ROLE_VIEW_DELETE_ASSOCIATE\')">' 
			        			   result = result +"<td><div class='tbldata-dotdot-200'>"    
			        			    result = result +" <a href='<c:url value='/app/userSummary/"+userRole.userCode+"'/>'' class='UserTextLeft' title = '"+userRole.userName+"'>";
			        			    if(userRole.fileUrl != null && userRole.fileUrl !="" && userRole.fileUrl != "undefined"){
					       	    	result = result +"<img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' class='m-t-xs'  src='"+userRole.fileUrl+"' alt='image'>";
			        			    }else{
						       	    result = result +"<img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' class='m-t-xs'  src='/resources/images/userprofileImg.jpg' alt='image'>";
			        			    }
					       	    	result = result + ''+userRole.userName+'</a></div></td>';
									'</sec:authorize>';
								}else{
								result = result + ' <td><div>--</div></td>';
								}
				             return result;
			    	  	}
			      }, {
			    	  "targets": colIndex++,
			          "data": "name",
			          "render": function ( data, type, userRole, meta ) {
			        	  var result = "";
			        	  alert(userRole.name);
			        	  if(userRole.userEmail !=null && userRole.userEmail !='' && userRole.userEmail !='undefined'){
								result = result + ' <td><div class="tbldata-dotdot" title = "'+userRole.userEmail+'">'+userRole.userEmail+'</div></td>';
							}else{
								result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
							}
			              return result;
			          		
			          }
			          },{
			        "targets": colIndex++,
			        "data": "description",
			        "render": function ( data, type, userRole, meta ) {
			        	if(userRole.mobilePhone != null && userRole.mobilePhone != ''){
						    	mobilePhone = userRole.mobilePhone;
						    }else{
						    	mobilePhone = "--"
						    }
			        	  var result = "";
								result = result + ' <td><div class="tbldata-dotdot-100" title = "'+mobilePhone+'">'+mobilePhone+'</div></td>';
			        	 return result;
			        }
			      }, {
			          "targets": colIndex++,
			          "data": "displayName",
			          "render": function ( data, type, userRole, meta ) {
			        	  var result = "";
			        	  result = result + ' <td><div class="tbldata-dotdot-100" title = "'+userRole.userRoleName+'">'+userRole.userRoleName+'</div></td>';
			        	  return result;
			          }
			        }, {
			            "targets": colIndex++,
			            "data": "manager",
			            "sortable": false,
			            "render": function ( data, type, userRole, meta ) {
			            	var result = "";
			            	if(userRole.reportingManagerName !=null && userRole.reportingManagerName !='' && userRole.reportingManagerName !='undefined'){
								result = result + ' <td><div class="tbldata-dotdot-sm" title = "'+userRole.reportingManagerName+'">'+userRole.reportingManagerName+'</div></td>';
							}else{
								result = result + ' <td><div class="tbldata-dotdot-sm">--</div></td>';
							}
				              return result;
			            }
			      },
			      {
			          "targets": colIndex++,
			          "data": "userEmploymentType",
			          "sortable": false,
			          "render": function ( data, type, userRole, meta ) {
			          	var result = "";
			          	 if(userRole.employeMentType !=null && userRole.employeMentType !='' && userRole.employeMentType !='undefined'){
								result = result + ' <td><div class="tbldata-dotdot-100" title = "'+userRole.employeMentType+'">'+userRole.employeMentType+'</div></td>';
							}else{
								result = result + ' <td><div class="tbldata-dotdot-100">--</div></td>';
							}
				              return result;
			          }
			    },
			    {
			        "targets": colIndex++,
			        "data": "status",
			        "sortable": false,
			        "render": function ( data, type, userRole, meta ) {
			        	var result = "";
			            if(userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'N'){
			       	    	  result = result +"<td><p class='tblminwidth-50'><label id='user"+userRole.userId+"' class='label label-info'><b class='fa fa-envelope' aria-hidden='true'></b><a class='color_white' href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Invite</a></label></p></td>";
			 			    }else if((userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'Y') &&
			 			    		(userRole.userState != null && userRole.userState != "" && userRole.userState == 'NOT_LOGGEDIN') ){
			 			    	 result = result +"<td><p class='tblminwidth-50'><label id='user"+userRole.userId+"' class='label label-info'><b class='fa fa-envelope' aria-hidden='true'></b><a class='color_white' href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Resend</a></label></p></td>";
			 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'ACTIVE'){
			 			    	result = result +"<td><p class='tblminwidth-50'><label class='label label-success'><b class='fa fa-check'></b>&nbsp;&nbsp;Active</label></p></td>";
			 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'INACTIVE'){
			 			    	result = result +"<td><p class='tblminwidth-50'><label class='label label-danger'><b class='fa fa-ban'></b>&nbsp;&nbsp;In-active</label></p></td>";
			 			    } 
			              return result;
			        }
			  }],
			    "scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"initComplete": function( settings, json ) {
		    		 $('#dynamicUserDataTableSearch').removeAttr('disabled');
		    	  },
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     }
	    } );
	    $("#dynamicUserListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
  } */
  </script>