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
		  	<a href="<c:url value='${appPath}/loadAddPetitionTemplate'/>"><i class="glyphicon glyphicon-plus"></i> Add Template</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Template Name</th>
				<th>Description</th>
				<th>No. of Docs</th>
				<th>No. of Forms</th>
				<th>No. of Tasks</th>
				<th>&nbsp;</th>
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
			  						
			</tbody>
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