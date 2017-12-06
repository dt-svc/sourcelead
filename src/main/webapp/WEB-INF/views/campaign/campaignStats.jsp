<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
		$(document).ready(function() {
			campaignStats();
		});
	</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Campaigns Stats</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='hotListCampaigns'/>">Bank Office</a> &raquo; Campaigns List</div>
    </section>
      <div class="clearfix">
        <div class="tableStyles_head clearfix positionRelative"> 
          <!-- From date to date picker -->
          <div class="col-md-12 clearfix">
            <ul class="list-unstyled">
              <li>
                <div class="remove_search">
                  <input type="text" placeholder="Search..." />
                </div>
              </li>
            </ul>
          </div>
          <!-- Advanced Search -->
        </div>
        <div class="clearfix tableStyles">
          <table class="table table-bordered">
            <thead>
              <tr>
                <th>Status</th>
                <th>Campaign Name</th>
                <th>Delivered</th>
                <th>Unique Opens</th>
                <th>Unique Clicks</th>
                <th>Unsubscribes</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
             	<tr>
             	<td>Financial Institute</td>
                <td>A/C No</td>
                <td>Debit (USD)</td>
                <td>Credit (USD)</td>
                <td>Balance (USD)</td>
                <td>Unsubscribes</td>
                <td>
                	<div class="action-div">
                		<i class="fa fa-ellipsis-h cursorPointer"></i>
                		<div class="action-inner">
                			<ul>
                			<li><a href="javascript:void(0)" class="fa fa-pie-chart"></a></li>
                			<li><a href="javascript:void(0)" class="fa fa-trash-o"></a></li>
                			</ul>
                		</div>
					</div>
                </td>
                </tr>
                <tr>
             	<td>Financial Institute</td>
                <td>A/C No</td>
                <td>Debit (USD)</td>
                <td>Credit (USD)</td>
                <td>Balance (USD)</td>
                <td>Unsubscribes</td>
                <td>
                	<div class="action-div">
                		<i class="fa fa-ellipsis-h cursorPointer"></i>
                		<div class="action-inner">
                			<ul>
                			<li><a href="javascript:void(0)" class="fa fa-pie-chart"></a></li>
                			<li><a href="javascript:void(0)" class="fa fa-trash-o"></a></li>
                			</ul>
                		</div>
					</div>
                </td>
                </tr>

              </tbody>
          </table>
        </div>
      </div>
  </section>
  
  <jsp:include page="../masterFooter.jsp" />