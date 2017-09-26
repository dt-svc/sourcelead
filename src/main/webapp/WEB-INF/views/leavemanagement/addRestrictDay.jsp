<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Restrict Day</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management &raquo; Restrict Day</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
    <div class="form-group">
       <label class="col-md-3">Name<span class="text-danger paddingLeft">*</span></label>
       <div class="col-md-9">
       <input type="text" placeholder="Shift Name" class="form-control">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Start Date</label>
        <div class="col-md-9">
       <input type="text" placeholder="Start Date" class="form-control calendar">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">End Date</label>
        <div class="col-md-9">
       <input type="text" placeholder="End Date" class="form-control calendar">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Reason</label>
        <div class="col-md-9">
            <textarea class="form-control" rows="2" placeholder="Reason"></textarea>
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3 paddingRight">Applicable For All</label>
       <div class="col-md-9">
 			<div class="clearfix mb"> 
              <label class="paddingLeft paddingRight">
                <input name="applicable" type="radio" id="yesApplica" onClick="yesApplica();">
                <span class="paddingLeft">Yes</span></label>
              <label class="paddingLeft">
                <input name="applicable" type="radio" id="noApplica" onClick="noApplica();">
                <span class="paddingLeft">No</span></label>
            </div>
       </div>
    </div>
              <div class="clearfix"></div>
   <div class="form-group" style="display: none;" id="selectRoles">
       <label class="col-md-3 paddingRight">Select Roles</label>
       <div class="col-md-9">
           <select class="chosen-select" data-placeholder="Select Roles">
               <option>--Select Roles--</option>
               <option>Roles-1</option>
               <option>Roles-2</option>
               <option>Roles-3</option>
           </select>
       </div>
    </div>
          </div>
        </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" class="btn btn-primary">
            <input type="button" value="Cancel" class="btn btn-default ml10">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>


<jsp:include page="../masterFooter.jsp" />
