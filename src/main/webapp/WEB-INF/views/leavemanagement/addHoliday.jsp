<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Holiday</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management &raquo; Holiday</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Holiday Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Name" value="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Date</label>
              <div class="col-md-9">
                      <div class="width110px">
                      <input type="text" class="form-control calendar" placeholder="From Date">
                      </div>
                      <div class="width110px">
                      <input type="text" class="form-control calendar" placeholder="To Date">
                      </div>
                      <div class="width90px checkBox">
                      <input type="checkbox" name="halfday">Half Day
                      </div>
                      <div class="width50px dropdown-select dropdown-select-custom">
                      <select>
                              <option selected>AM</option>
                              <option selected>PM</option>
                          </select>
                      </div>
                  </div>
            </div>
            
            <div class="form-group hidden">
              <label class="col-md-3"></label>
              <div class="col-md-9">
                  <div class="row">
                      <div class="col-md-8 checkBox">
                      <input type="checkbox" name="halfday">Half Day
                      </div>
                      <div class="col-md-4 dropdown-select">
                          <select class="form-control">
                              <option selected>AM</option>
                              <option selected>PM</option>
                          </select>
                      </div>
                  </div>
              </div>
            </div>
              
           <div class="form-group">
              <label class="col-md-3">Description</label>
              <div class="col-md-9">
                  <textarea rows="2" class="form-control" placeholder="Description"></textarea>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3">Holiday Group</label>
              <div class="col-md-9">
                  <div class="form-icon">
                  <select class="form-control chosen-select" data-placeholder="Select Group">
                      <option selected>--Select Holiday Group--</option>
                      <option>Group1</option>
                      <option>Group2</option>
                      <option>Group3</option>
                  </select>
                  <span data-target="#addHolidaygroup" data-toggle="modal" title="Add Team">
                      <i class="fa fa-plus btn btn-default formic1"></i>
                  </span>
                  </div>
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
