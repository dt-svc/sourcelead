<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


 
 <!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Payroll Configuration</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Payroll Configuration</div>
</section>
 
   <div class="tabsMenu clearfix">
   <ul class="list-unstyled">
	   <li class="current"><a href="<c:url value='${appPath}/payrollConfigure'/>" >Deduction</a></li>
	   <li><a href="<c:url value='${appPath}/payrollContribution'/>" class="active">Contribution</a></li>
	   </ul>
   </div>  
 
  <div class="tablelist-wrap clearfix">
   
    <div class="table-header clearfix">
      <ul class="list-unstyled list-inline">
        <li><a href="<c:url value='${appPath}/addContribution'/>" class="addcon-pop"><i class="fa fa-plus paddingRight"></i>Add</a></li>
        <li>
		 <div class="dynamic_search">
			<input type="text" placeholder="Search" />
			<i class="fa fa-search"></i>
		 </div>
	   </li>
        <div class="clearfix"></div>
      </ul>
    </div>
    
<div class="styleTables">
    	<table>
		<thead>
            	<tr>
                	<th>Deduction Type</th>
                   	<th>Description</th>
                    <th>Method</th>
                    <th>Amount</th>
                    <th>Action</th>
                </tr>
			</thead>	
			<tbody>
			   	<tr>
                    <td><div class="proj_show tblminwidth-75"><span>401</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div class="tbldata-dotdot-verybig">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</div></td>
                    <td><div  class="tblminwidth-100">Fixed</div></td>
                    <td><div  class="tblminwidth-100">50000$</div></td>
                    <td><div class="tbldata-dotdot_50">
                   		 	<a href="javascript:void(0)" class="text-primary"><i class="fa fa-pencil-square-o"></i></a>
                    		<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i></a>
						</div>
               		</td>
                </tr>
			   	<tr>
                    <td><div class="proj_show tblminwidth-75"><span>Post Tax Deduction</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div class="tbldata-dotdot-verybig">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries</div></td>
                    <td><div  class="tblminwidth-100">Fixed</div></td>
                    <td><div  class="tblminwidth-100">50000$</div></td>
                    <td><div class="tbldata-dotdot_50">
                   		 	<a href="javascript:void(0)" class="text-primary"><i class="fa fa-pencil-square-o"></i></a>
                    		<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i></a>
						</div>
               		</td>
                </tr>      			
            </tbody>
        </table>
	</div>
    
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


  </section>
  

<jsp:include page="../masterFooter.jsp" />