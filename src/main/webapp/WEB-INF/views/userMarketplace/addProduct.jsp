<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Products</h2>
      </div>
                 <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/OrgMarketplace'/>">Associate Marektplace </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/associateMarketplaceList'/>">Associate List </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/productsList'/>">Product List </a>&raquo;Add Product</div>
      
    </section>
    <div class="content-area content-new clearfix backgroundWhite">
      <div class="minHeightcol6 mt mb">
        
          <div class="col-md-4 minHeight">
              <label class="clearfix">Product Code</label>
              <div class="clearfix">
                  <input type="text" class="form-control">
              </div>
              <div class="clearfix"></div>
          </div>
        <div class="col-md-4 minHeight">
                <label>Product Type</label>
                <div class="clearfix">
                  <select class="chosen-select" style="display: none;">
                    <option selected="">Select</option>
                    <option>Product-1</option>
                    <option>Product-2</option>
                    <option>Product-3</option>
                  </select><div class="chosen-container chosen-container-single" style="width: 100%;" title=""><div class="chosen-drop"><div class="chosen-search"><input type="text" autocomplete="off"></div><ul class="chosen-results"></ul></div></div>
                </div>
                
        </div>
    <div class="col-md-4 minHeight">
            <label class="clearfix">Category</label>
            <div class="clearfix">
              <input type="text" class="form-control">
            </div>
            <div class="clearfix"></div>
    </div>

    <div class="col-md-4 minHeight">
        <label class="clearfix">Price</label>
        <div class="clearfix">
          <input type="text" class="form-control">
        </div>
        <div class="clearfix"></div>
</div>

    <div class="col-md-4 minHeight">
        <label class="clearfix">Priority</label>
        <div class="clearfix">
            <select class="sel">
                <option selected="">Select</option>
                <option>Immediate</option>
                <option>One Week</option>
                <option>One Month</option>
                <option>Two Months</option>
                <option>Three Months</option>
              </select>
        </div>
        <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight">
  <div class="form-group clearfix">
    <label class="clearfix">Status</label>
    <div class="clearfix">
        <select class="sel">
            <option selected="">Select</option>
            <option>Open</option>
            <option>Close</option>
          </select>
    </div>
    <div class="clearfix"></div>
  </div>
</div>
<div class="col-md-4 minHeight">
    <label class="clearfix">Notify To</label>
    <div class="clearfix">
        <input type="text" class="form-control">
    </div>
    <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight">

    <label class="clearfix">&nbsp;</label>
    
    <div class="clearfix row">
        <label class="col-md-8 mt5">Public OR Private</label>
        <div class="col-md-4">
        <label class="switch">
            <input checked="" type="checkbox">
            <span class="slider round"></span>
        </label>
      </div>
    </div>
    <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight">

    <label class="clearfix">&nbsp;</label>
    
    <div class="clearfix row">
        <label class="col-md-8 mt5">Share Details</label>
        <div class="col-md-4">
        <label class="switch">
            <input checked="" type="checkbox">
            <span class="slider round"></span>
        </label>
      </div>
    </div>
    <div class="clearfix"></div>
</div>

<div class="clearfix"></div>
    <div class="col-md-6">
          <label class="clearfix">Product Description <span class="text-danger paddingLeft">*</span></label>
          <div class="clearfix">
            <textarea placeholder="Description" class="form-control" cols="2"></textarea>
          </div>
        <div class="clearfix"></div> 
    </div>
    
    <div class="col-md-6">
        <label class="clearfix">Attachment<span class="text-danger paddingLeft">&nbsp;*</span></label>
        <div class="clearfix">
          <div class="row mb">
            <div class="input-group col-md-11 pull-left">
              <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
              <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
              </span> </label>
              <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Resume ...">
            </div>
            <div class="input-group col-md-1 text-primary text-center">
              <i class="fa fa-plus cursorPointer mt10"></i>
            </div>
          </div>

          <div class="row mb">
              <div class="input-group col-md-11 pull-left">
                <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                </span> </label>
                <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Resume ...">
              </div>
              <div class="input-group col-md-1 colorRed text-center">
                <i class="fa fa-times cursorPointer mt10"></i>
              </div>
            </div>

            
          <div class="row mb">
              <div class="input-group col-md-11 pull-left">
                <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                </span> </label>
                <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Resume ...">
              </div>
              <div class="input-group col-md-1 colorRed text-center">
                <i class="fa fa-times cursorPointer mt10"></i>
              </div>
            </div>

            <div class="row">
                <div class="input-group col-md-11 pull-left">
                  <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                  <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                  </span> </label>
                  <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Resume ...">
                </div>
                <div class="input-group col-md-1 colorRed text-center">
                  <i class="fa fa-times cursorPointer mt10"></i>
                </div>
              </div>
            
      </div>
      </div>

</div>
<div class="clearfix"></div>
<div class="form-footer clearfix mt">
    <div class="col-lg-12">
      <div class="pull-right">
        <input type="button" value="Save" class="btn btn-primary" id="save" onclick="listfunction()">
        <input type="button" value="Cancel" class="btn btn-default ml">
      </div>
    </div>
  </div>

      </div>  

        </section>




 <jsp:include page="../masterFooter.jsp" />	