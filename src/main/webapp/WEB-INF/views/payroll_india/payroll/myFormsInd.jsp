<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="tablelist-wrap">
        <div class="timesheet_head minHeight">
          <div class="col-md-7 mt">
            <div class="row">
              <div class="col-md-3 paddingRight">
                <input name="" class="form-control calendar" placeholder="Selct Year" type="text" id="payDay">
              </div>
              <div class="col-md-2">
              	<a href="run-payroll.html" class="btn btn-success btnSuccess btn-sm anchorWhite"><i class="fa fa-search"></i></a>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
          <div class="remove_search">
            <input placeholder="Search" type="text">
          </div>
        </div>
          <div class="styleTables clearfix mt">
            <table>
              <thead>
                <tr>
                  <th>Assessment Year</th>
                  <th>Form Type</th>
                  <th>Filling Date</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">IT Declaration</div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td><div class="tblminwidth-150 clearfix">Submitted</div></td>
                </tr>
                 <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">Form-16</div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							31/05/2018                 	
					  </span>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">Processed</div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        <div class="clearfix"></div>
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
