<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Sales Orders List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;Sales Order List</div>
    </section>
    
       <div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
         <li id="" ><a href="${appPath}/candidatesList">Candidate List</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToGlobalMarketPlaceList();">Global Marketplace</a></li>
          <li id=""><a href="javascript:void(0)" onclick="javascript:goToPurchaseOrderList();">Purchase Orderlist</a></li>
            <li id="" class="current"><a href="javascript:void(0)">Sales Orderlist</a></li>
           
         
         <div class="clearfix"></div>
     </ul>
</div>
    
    <div class="contentareaInner-styles clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
			  <span class="pull-left refreIcon noPad mb10">
				  <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a>
			  </span>
            <ul class="pull-right list-unstyled list-inline noPad">
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
                <div class="newActPop" style=""> <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div>
              </li>
            <li class="noMar">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
			</li>
            </ul>
          </div>
          <div class="require_wrap_table tableFirstSecond clearfix">
            <table>
              <thead>
                <tr>
                  <th>Order No</th>
                  <th>Order Date</th>
                  <th>Name of the Company</th>
                  <th>No. of Resumes</th>
                  <th>Total Resumes Price</th>
                  <th>Payment Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tblminwidth-75"  id=""><a href="${appPath}/SalesOrder">MIS00414</a></div></td>                  
                  <td><div class="tblminwidth-75">2/07/2018</div></td>
                  <td><div class="tbldata-dotdot">ANBC Corporation PVT. Ltd</div></td>
                  <td><div class="tblminwidth-75">150</div></td>
				  <td><div class="tblminwidth-50">112,2233</div></td>
                  <td><div class="tblminwidth-75 reColor">Failed</div></td>
                </tr>
				  <tr>
                  <td><div class="tblminwidth-75"  id=""><a href="${appPath}/SalesOrder">MIS00414</a></div></td>       
                  <td><div class="tblminwidth-75">2/07/2018</div></td>
                  <td><div class="tbldata-dotdot">ANBC Corporation PVT. Ltd</div></td>
                  <td><div class="tblminwidth-75">150</div></td>
				  <td><div class="tblminwidth-50">112,2233</div></td>
                  <td><div class="tblminwidth-75 suColor">Pending</div></td>
                </tr>
				  <tr>
                   <td><div class="tblminwidth-75"  id=""><a href="${appPath}/SalesOrder">MIS00414</a></div></td>       
                  <td><div class="tblminwidth-75">2/07/2018</div></td>
                  <td><div class="tbldata-dotdot">ANBC Corporation PVT. Ltd</div></td>
                  <td><div class="tblminwidth-75">150</div></td>
				  <td><div class="tblminwidth-50">112,2233</div></td>
                  <td><div class="tblminwidth-75 apColor">Success</div></td>
                </tr>
              </tbody>
            </table>
          </div>
          <nav>
            <ul id="paging" class="pagination">
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
      </div>
    </div>
  </section>
   <script>
  
  function goToCandidateList(){
		location.href = "${appPath}/candidatesList";
	}
		
	function goToGlobalMarketPlaceList(){
		location.href = "${appPath}/globalMarketplacePage";
	}

	function goToOrgMarketPlaceList(){
		location.href = "${appPath}/OrgMarketplace";
	}
	function goToPurchaseOrderList(){
		location.href ="${appPath}/PurchaseOrderList";
		}
	function goToSalesOrderList(){
		location.href = "${appPath}/SalesOrderList";
	}
  
  </script>
  <jsp:include page="../masterFooter.jsp" /> 