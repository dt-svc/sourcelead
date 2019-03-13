<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Email Reminder Configuration</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Email Reminder Configuration</div>
    </section>
    <div class="clearfix reportNewDash">
      <div class="repDashCta"> <span class="navIcons jobIcon svgReport margin-top0"></span>
        <div class="repDashCtaWrap ml">
          <h2>Jobs</h2>
          <div class="clearfix mt5"></div>
          <a href="<c:url value='${appPath}/jobsConfigureEmails'/>" class="fontSmall">Configure</a> </div>
      </div>
      <%-- <div class="repDashCta"> <span class="navIcons canIcon svgReport margin-top0"></span>
        <div class="repDashCtaWrap ml">
          <h2>Candidates</h2>
          <div class="clearfix mt5"></div>
          <a href="javascript:void(0)" class="fontSmall">Configure</a> </div>
      </div> --%>
      <div class="repDashCta"> <span class="navIcons leaveIcon svgReport margin-top0"></span>
        <div class="repDashCtaWrap ml">
          <h2>Leaves</h2>
          <div class="clearfix mt5"></div>
          <a href="<c:url value='${appPath}/emailReminderLeaveConfiguration'/>" class="fontSmall">Configure</a> </div>
      </div>
       <div class="repDashCta"> <span class="navIcons contIcon svgReport margin-top0"></span>
        <div class="repDashCtaWrap ml">
          <h2>Contracts</h2>
          <div class="clearfix mt5"></div>
          <a href="<c:url value='${appPath}/contractConfigureEmails'/>" class="fontSmall">Configure</a> </div>
      </div> 
      <%-- <div class="repDashCta"> <span class="navIcons finIcon svgReport margin-top0"></span>
        <div class="repDashCtaWrap ml">
          <h2>Finance</h2>
          <div class="clearfix mt5"></div>
          <a href="<c:url value='${appPath}/financeConfigureEmails'/>" class="fontSmall">Configure</a> </div>
      </div> 
      <div class="repDashCta"> <span class="navIcons cliIcon svgReport margin-top0"></span>
        <div class="repDashCtaWrap ml">
          <h2>Associates</h2>
          <div class="clearfix mt5"></div>
          <a href="associatesConfigure_Emails.html" class="fontSmall">Configure</a> </div>
      </div>
      <div class="repDashCta"> <span class="navIcons timIcon svgReport margin-top0"></span>
        <div class="repDashCtaWrap ml">
          <h2>Timesheets</h2>
          <div class="clearfix mt5"></div>
          <a href="javascript:void(0)" class="fontSmall">Configure</a> </div>
      </div> --%>
      <div class="clearfix"></div>
    </div>
  </section>
   <jsp:include page="masterFooter.jsp" />