<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <c:forEach var="activity" items="${timesheetSubmissionDTO.activityList}">

<div class="content content-custom clearfix">
            <div class="timeline_inner">
              <div class="row mt">
                <div class="col-md-12"> 
                  <!-- The time line -->
                  <ul class="timeline">
                    <!-- timeline item -->
                    <c:if test="${activity.activityStatus eq 'APPROVED'}">
                    <li> <i class="fa fa-check" style="background-color: #34c244; color:#fff;"></i>
                      <div class="timeline-item"><span class="time"><i class="fa fa-clock-o paddingRight"></i>${activity.displayTime}
                       <span class="paddingLeft"><i class="fa fa-calendar paddingRight"></i>${activity.displayDate}</span></span>
                        <h3 class="timeline-header">Approved by<a href="#" class="paddingLeft">${activity.userFullName} ${activity.actionPerformedBy}</a></h3>
                        <div class="timeline-body">
                          <ul class="list-unstyled">
                              <li>
                                <div class="events-date clearfix">
                                	<span class="paddingLeft">8hrs has been modified to  6hrs</span>
                                </div>
                         	 </li>
                          </ul>
                        </div>
                     </div>
                    </li>
                    </c:if>
                    <!-- END timeline item --> 
                    <!-- timeline item -->
                    <c:if test="${activity.activityStatus eq 'REJECTED'}">
                    <li> <i class="fa fa-times bgDanger"></i>
                      <div class="timeline-item"> <span class="time"><i class="fa fa-clock-o paddingRight"></i>${activity.displayTime}
                       <span class="paddingLeft"><i class="fa fa-calendar paddingRight"></i>${activity.displayDate}</span></span>
                        <h3 class="timeline-header">Rejected by<a href="#" class="paddingLeft">${activity.userFullName} ${activity.actionPerformedBy}</a></h3>
                        <div class="timeline-body">
                          <ul class="list-unstyled">
                              <li>
                                <div class="des-timesheetevents clearfix text-muted mt5 clearfix small">
                                <p class="mb5"><strong>Rejected Reason:</strong></p>${activity.rejectedReason}
                               </div>
                         </li>
                          </ul>
                        </div>
                     </div>
                    </li>
                    </c:if>
                    <!-- END timeline item -->
                    <!-- timeline item -->
                     <c:if test="${activity.activityStatus eq 'NOTIFY'}">
                    <li> <i class="fa fa-pencil" style="background-color: #0f9fa1; color:#fff;"></i>
                      <div class="timeline-item"> <span class="time"><i class="fa fa-clock-o paddingRight"></i>${activity.displayTime}
                       <span class="paddingLeft"><i class="fa fa-calendar paddingRight"></i>${activity.displayDate}</span></span>
                        <h3 class="timeline-header">Re-Opened by<a href="#" class="paddingLeft">${activity.userFullName} ${activity.actionPerformedBy}</a></h3>
                        <div class="timeline-body">
                          <ul class="list-unstyled">
                              <li>
                                <div class="des-timesheetevents clearfix text-muted mt5 clearfix small">
                                <p class="mb5"><strong>Re-Opened Reason:</strong></p>${activity.reopenReason}
                                </div>
                         </li>
                          </ul>
                        </div>
                     </div>
                    </li>
                    </c:if>
                    <!-- END timeline item -->                    
                    <c:if test="${activity.activityStatus eq 'SUBMITTED'}">
                    <li> <i class="fa fa-send-o" style="background-color: #d98f47;color: #fff;"></i>
                      <div class="timeline-item"> <span class="time"><i class="fa fa-clock-o paddingRight"></i>${activity.displayTime}
                       <span class="paddingLeft"><i class="fa fa-calendar paddingRight"></i>${activity.displayDate}</span></span>
                        <h3 class="timeline-header">Updated by<a href="#" class="paddingLeft">${activity.userFullName} ${activity.actionPerformedBy}</a></h3>
                        <div class="timeline-body">
                          <ul class="list-unstyled">
                              <li>
                                <div class="des-timesheetevents clearfix text-muted mt5 clearfix small">
                                <p class="mb5"><strong>Reason:</strong></p>
                                </div>
                         </li>
                          </ul>
                        </div>
                     </div>
                    </li> 	
                    </c:if>
                    <!-- END timeline item --> 
                    <c:if test="${activity.activityStatus eq 'DRAFT'}">
                    <li> <i class="fa fa-floppy-o" style="background-color: #656565; color:#fff; font-size:18px;"></i>
                      <div class="timeline-item"> <span class="time"><i class="fa fa-clock-o paddingRight"></i>${activity.displayTime}
                       <span class="paddingLeft"><i class="fa fa-calendar paddingRight"></i>${activity.displayDate}</span></span>
                        <h3 class="timeline-header">Draft by<a href="#" class="paddingLeft">${activity.userFullName} ${activity.actionPerformedBy}</a></h3>
                        <div class="timeline-body">
                          <ul class="list-unstyled">
                              <li>
                                <div class="des-timesheetevents clearfix text-muted mt5 clearfix small">
                                <p class="mb5"><strong>Reason:</strong></p>
                                </div>
                         </li>
                          </ul>
                        </div>
                     </div>
                    </li>
                    </c:if>
                    
                  </ul>
                </div>
                <!-- /.col --> 
              </div>
            </div>
          </div>
   </c:forEach>