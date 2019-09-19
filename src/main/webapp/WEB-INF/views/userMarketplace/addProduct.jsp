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
 <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/OrgMarketplace'/>">Associate Marektplace </a>&raquo;<a href="<c:url value='${SourceLeadWeb_atsUrl}/productList'/>">Product List </a>&raquo;Add Product</div>     
     
    </section>
    <div class="content-area content-new clearfix backgroundWhite">
      <div class="minHeightcol6 mt mb clearfix">
  
<h4 class="col-md-12  text-muted">Add Perosonal Info</h4>

<div class="col-md-4 minHeight">
  <label class="clearfix">Current Location</label>
  <div class="clearfix">
    <input type="text" class="form-control">
  </div>
  <div class="clearfix"></div>
</div>
<div class="col-md-4 minHeight">
  <label class="clearfix">Select Type Location</label>
  <div class="clearfix">
    <select class="sel form-control" onchange="relocationHandler();" id="relocationFromtoSelect">
      <option selected="">Select</option>
      <option value="Relocate">Relocate</option>
      <option value="Other">Other</option>
    </select>
  </div>
  <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight" id="relocationFromto" style="display: none;">
    <div class="clearfix row">
  <label class="clearfix col-md-5">From</label>
  <label class="clearfix col-md-5">To</label>
  </div>
  <div class="clearfix row">
    <div class="col-md-5"><input type="text" class="form-control calendar"></div>
    <div class="col-md-5"><input type="text" class="form-control calendar"></div>
  </div>
  <div class="clearfix"></div>
</div>

<div class="col-md-4 minHeight">
  <label class="clearfix">Comments<span class="text-danger paddingLeft">*</span></label>
  <div class="clearfix">
    <textarea placeholder="Comments" class="form-control" cols="2"></textarea>
  </div>
<div class="clearfix"></div> 
</div>
        <div class="clearfix"></div>
        <h4 class="col-md-12 text-muted">Add Product Info</h4>
        <div class="col-md-4 minHeight">
          <label class="clearfix">Product Name</label>
          <div class="clearfix">
            <input type="text" class="form-control">
          </div>
          <div class="clearfix"></div>
  </div>
        <div class="col-md-4 minHeight">
                <label>Product Type</label>
                <div class="clearfix">
                  <select class="chosen-select" name ="category" id="category" field-name="Product Category" style="display: none;">
                    <option selected="">Select</option>
                    <option>Home</option>
                    <option>Vehicles</option>
                    <option>Home appliance</option>
                      <option>Utensils</option>
                    <option>Electronics/gadgets</option>
                    <option>Furnitures</option>
                     <option>Other</option>
                  </select><div class="chosen-container chosen-container-single" style="width: 100%;" title=""><div class="chosen-drop"><div class="chosen-search"><input type="text"  autocomplete="off"></div><ul class="chosen-results"></ul></div></div>
                </div>
        </div> 
    <div class="col-md-4 minHeight">
            <label class="clearfix">Manufacturer</label>
            <div class="clearfix">
              <input type="text" class="form-control">
            </div>
            <div class="clearfix"></div>
    </div>
    
    <div class="col-md-4 minHeight">
      <label class="clearfix">Date of Manufactured</label>
      <div class="clearfix">
        <input type="text" class="form-control calendar">
      </div>
      <div class="clearfix"></div>
</div>

    <div class="col-md-4 minHeight">
      
      <div class="row">
        <div class="col-md-4">
            <label class="clearfix">Warranty Eligible</label>
            <label class="switch mt5 ml">
                <input type="checkbox" onclick="warrantyHandler();" id="warrantyCheckbox">
                <span class="slider round"></span>
            </label>
        </div>
        <div class="col-md-8">
            <div class="clearfix" id="WarrantyEligibile" style="display: none;">
            <label class="clearfix">Warranty Expiry Date</label>
                <input type="text" class="form-control calendar">
              </div>
        </div>
      </div>
      
      
      <div class="clearfix"></div>
</div>

    <div class="col-md-4 minHeight">
        <label class="clearfix">Price</label>
        <div class="clearfix row">
            <div class="col-md-9">
          <input type="text" class="form-control">
        </div>
          <div class="col-md-3">
            <select class="form-control sel">
                <option selected="">Select</option>
                <option>₹</option>
                <option>$</option>
                <option>£</option>
                <option>¢</option>
                <option>﷼</option>
            </select>
          </div>
        </div>
        <div class="clearfix"></div>
</div>

    <div class="col-md-4 minHeight">
        <label class="clearfix">Priority</label>
        <div class="clearfix">
            <select class="sel">
                <option selected="">Select</option>
                <option>Immediate</option>
                <option>1 Week</option>
                <option>Fortnight</option>
                <option>Fortnight</option>
                <option>15 Days</option>
                <option>1 Month</option>
                <option>2 Month(s)</option>
                <option>3 Month(s)</option>
              </select>
        </div>
        <div class="clearfix"></div>
</div>


<div class="col-md-4 minHeight">
    <label class="clearfix">Attach document for the Product<span class="text-danger paddingLeft">*</span></label>
            <div class="input-group col-md-12 clearfix">
              <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
              <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
              </span> </label>
              <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Document ...">
            </div>
  <div class="clearfix"></div> 
</div>


<div class="col-md-4 minHeight">
    <label class="clearfix">&nbsp;</label>
    <div class="clearfix row">
        <label class="col-md-8 mt5">Negotiate</label>
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
        <label class="col-md-8 mt5">Show Contact details <i class="fa fa-info text-primary ml cursorPointer" title="Contact details would be viewable to the buyers"></i></label>
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
        <label class="col-md-8 mt5">Post Public</label>
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
    <label class="clearfix">Product Description <span class="text-danger paddingLeft">*</span></label>
    <div class="clearfix">
      <textarea placeholder="Description" class="form-control" cols="2"></textarea>
    </div>
  <div class="clearfix"></div> 
</div>
<div class="clearfix"></div>

    
    <div class="clearfix mt">
        <label class="clearfix col-md-12 mb10">
          Attachments / Upload Photos<span class="text-danger paddingLeft">&nbsp;*</span>
          <small class="text-muted ml">Upload upto 6 photos per item / product</small>
        </label>
        <div class="clearfix ">
          <div class="col-md-4 minHeight clearfix">
            <div class="input-group col-md-11 pull-left">
              <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
              <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
              </span> </label>
              <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Product Image ...">
            </div>
            <div class="input-group col-md-1 text-primary text-center">
              <i class="fa fa-plus cursorPointer mt10"></i>
            </div>
          </div>
          <div class="col-md-4 minHeight">
              <div class="input-group col-md-11 pull-left">
                <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                </span> </label>
                <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Product Image ...">
              </div>
              <div class="input-group col-md-1 colorRed text-center">
                <i class="fa fa-times cursorPointer mt10"></i>
              </div>
            </div>

            
          <div class="col-md-4 minHeight">
              <div class="input-group col-md-11 pull-left">
                <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                </span> </label>
                <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Product Image ...">
              </div>
              <div class="input-group col-md-1 colorRed text-center">
                <i class="fa fa-times cursorPointer mt10"></i>
              </div>
            </div>

            <div class="col-md-4 minHeight">
                <div class="input-group col-md-11 pull-left">
                  <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                  <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                  </span> </label>
                  <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Product Image ...">
                </div>
                <div class="input-group col-md-1 colorRed text-center">
                  <i class="fa fa-times cursorPointer mt10"></i>
                </div>
              </div>
              <div class="col-md-4 minHeight">
                  <div class="input-group col-md-11 pull-left">
                    <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                    <input type="file" style="display: none;" multiple="" id="uploadBtn" class="upload">
                    </span> </label>
                    <input type="text" class="form-control" readonly="" id="uploadFile" placeholder="Upload Product Image ...">
                  </div>
                  <div class="input-group col-md-1 colorRed text-center">
                    <i class="fa fa-times cursorPointer mt10"></i>
                  </div>
                </div>
                <div class="col-md-4 minHeight">
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
        <div class="clearfix col-md-12">
          <div class="pull-right">
            <input type="button" value="Add Product" class="btn btn-info" id="save" onclick="">
            <input type="button" value="Rest" class="btn btn-default ml">
          </div>
        </div>
</div>

<div class="clearfix col-md-12">
  <div class="require_wrap_table clearfix">
    <table>
      <thead>
        <tr>
          <th>Product ID</th>
          <th>Product Type</th>
          <th>Manufacturer</th>
          <th>Date of Manufactured</th>
          <th>Price</th>
          <th>Priority</th>
          <th>Warranty Doc.</th>
          <th>Negotiate</th>
          <th>Show Contact details</th>
          <th>Post Details</th>
          <th>Product Description</th>
          <th>Product Image</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr>
        <td>
        <div class="width150px">
          <a href="javascript:void(0)">
          <div class="clearfix" title="002345678">
           002345678
          </div>
          </a>
          </div>
        </td>
        <td>
            <div class="width150px" title="Business Intelli Solutions">Electronics/Gadgets</div>
            </td>
        <td>
        <div class="tbldata-dotdot-sm" title="ABC Sony Corporation">ABC Sony Corporation</div>
        </td>
        <td>
        <div class="width100px" title="10/11/2014">10/11/2014</div>
        </td>
        <td>
        <div class="width150px" title="10/11/2014">65,000 <small class="ml">(₹)</small></div>
        </td>
        <td><div class="width100px" title="Immediate">Immediate</div></td>
        <td><div class="width100px" title="Warranty Document"><i class="fa fa-download cursorPointer"></i></div></td>
        <td><div class="width100px" title="Negotiate">Yes</div></td>
        <td><div class="width100px" title="Contact details">Yes</div></td>
        <td><div class="width100px" title="Post Details">Yes</div></td>
        <td>
  <div class="tbldata-dotdot-big" title="Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.">
      Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.
  </div>
</td>
<td><div class="width100px" title="Warranty Document"><i class="fa fa-download cursorPointer"></i></div></td>
          <td>
          <div class="width100px">
            <ul class="list-inline list-unstyled">
              <li><a href="javascript:void(0)"><i class="fa fa-share-alt cursorPointer" title="Share the Product"></i></a></li>
              <li><a href="javascript:void(0)" class="text-warning"><i class="fa fa-clock-o cursorPointer" title="Share Later"></i></a></li>
              <li><a href="editworkflow.html"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
              <li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
            </ul>	
          </div>
          </td>
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

<div class="clearfix"></div>
<div class="form-footer clearfix mt">
    <div class="col-lg-12">
      <div class="pull-right">
        <input type="button" value="Save" class="btn btn-primary" id="save" onclick="listfunction()">
        <input type="button" value="Cancel" class="btn btn-default ml">
      </div>
    </div>
  </div>

      </section>
}



 <jsp:include page="../masterFooter.jsp" />	