<!-- Side Nav Bar -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <aside class="side-bar">
    <div class="side-bar-inner">
      <div class="user-panel">
        <div class="media">
          <div class="media-left image"> <img src="resources/images/pratap-bis.jpg" alt="Admin" title="admin" class="img-circle"> </div>
          <div class="media-body">
            <p>Pratap Reddy</p>
            <p>Admin</p>
          </div>
        </div>
      </div>
      <div class="side-nav">
        <ul class="list-unstyled">
          <li><a href="<c:url value='/home'/>"><span><i class="fa fa-dashboard"></i></span>Dashboard</a></li>
          <li><a href="javascript:void(0)"><span><i class="fa fa-envelope"></i></span>Mailbox</a></li>
          <li><a href="javascript:void(0)"><span><i class="fa fa-calendar"></i></span>Calendar</a></li>
          <li><a href="javascript:void(0)"><span><i class="fa fa-users"></i></span>Users</a></li>
          <li><a href="<c:url value='/project'/>"><span><i class="fa fa-bar-chart"></i></span>Projects</a></li>
          <li><a href="javascript:void(0)"><span><i class="fa fa-user-plus"></i></span>Clients</a></li>
          <li><a href="javascript:void(0)"><span><i class="fa fa-file-text"></i></span>Documentation</a></li>
        </ul>
      </div>
    </div>
  </aside>
  <!-- Side Nav Bar --> 