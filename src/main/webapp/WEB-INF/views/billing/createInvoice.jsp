<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
$(document).ready(function(){
	getUserList("0");
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Invoice</h2>
      </div>
      <div class="pull-right brud-crum">Home » Invoice</div>
    </section>
    <div class="content-area content-new clearfix">
    <div id="invoiceLoading"></div>
     <form:form id="invoice_form"  name="invoice_form" method="post" > 
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field">
                <select class="form-control" id="clientOrgRelationId" name="clientOrgRelationId" onchange="getClientContacts();">
                 <option value=''>--Select Customer--</option>
                   <c:forEach var="orgrelations" items="${clientList}">
                         <option value="${orgrelations.id}">${orgrelations.orgName}</option>
                  </c:forEach>
                 <!--  <option selected="" disabled="">--Select Customer--</option>
                  <option>Customer-1</option>
                  <option>Customer-2</option>
                  <option>Customer-3</option> -->
                </select>
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-4">Client Contact<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="clientContact" name="clientContact" data-validation="required" field-name="Vendor Contact">
                  </select>
              </div>
            </div>
            <!-- <div class="form-group">
              <label class="col-md-3">Client Contact</label>
              <div class="col-md-9">
                <input type="text" class="form-control"id="clientContact" name="clientContact" value="" placeholder="Client Contact">
              </div>
            </div> -->
            <div class="form-group">
              <label class="col-md-3">Invoice Id</label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="invoiceId" name="invoiceId" value="" placeholder="Invoice Id">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Invoice Date</label>
              <div class="col-md-9 select-field">
                <input type="text" class="form-control calendar" id="invoiceDate" name="invoiceDate" value="" placeholder="Invoice Date" id="invoiceDate" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Due Date</label>
              <div class="col-md-9">
                <input type="text" class="form-control calendar" id="dueDate" name="dueDate" value="" placeholder="Invoice Date" id="dueDate" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Billing Address</label>
              <div class="col-md-9">
                <textarea class="form-control" rows="2" id="billingAddress" name="billingAddress" placeholder="Billing Address"></textarea>
              </div>
            </div>
            
           <!--  <div class="form-group">
              <label class="col-md-3">Preojects<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field">
                <select class="form-control" id="projectId" name="projectId" onchange="getProjects();">
                </select>
              </div>
            </div> -->
          </div>
        </div>
       
        <div class="clearfix"></div>
        <!-- Drap and Drop Table Row -->
        
        
        <!-- Table starts here-->
          <div class="project_wrap_table mt25 invoice_table" id="invoiceTable">
          <input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1" />
          <table width="100%">
            <thead id="addInvoiceTbl">
              <tr id="divTsRow0">
                <!-- <th><i class="fa fa-ellipsis-h"></i></th> -->
                <th>Date</th>
                <th>Associate</th>
                <th>Sow</th>
                <th>Rate</th>
                <th>Total Hours</th>
                <th>Amount</th>
                <th><i class="fa fa-remove"></i></th>
              </tr>
            </thead>
            <tbody class="ui-sortable">
              <tr>
                <!-- <td><a href="javascript:void(0)"><i class="fa fa-ellipsis-h"></i></a></td> -->
                <td>
                  <div class="inputCustom">
                    <input type="text" class="calendar paddingRight" id="0sdate-input" placeholder="Start Date" name="0invoiceStartDate">
                    <input type="text" class="calendar paddingLeft" id="0edate-input" placeholder="End Date" name="0invoiceEndDate">
                  </div>
                </td>
                <td>
                  <div class="tbldata-dotdot-vsm select-field">
                    <select class="form-control input-sm" id="0candidateId" name="0candidate">
                    </select>
                  </div>
                </td>
                <td>
                  <div class="tbldata-dotdot-100 select-field">
                    <select class="form-control input-sm" id="0sow" name="0sow">
                      <option value="">selcet Sow</option>
                    </select>
                  </div>
                </td>
                <td>
                  <div class="tbldata-dotdot-100">
                    <input type="text" class="form-control input-sm" placeholder="Rate" name="0rate" name="0rate">
                  </div>
                </td>
                <td>
                  <div class="tbldata-dotdot-vsm">
                    <input type="text" class="form-control input-sm" placeholder="Total Hours" id="0totalHours" name="0totalHours">
                  </div>
                </td>
                <td><div class="tbldata-dotdot-100 text-right">10236985741</div></td>
                <td><a class="text-danger tblminwidth-50" title="Remove row"><i class="fa fa-remove"></i></a></td>
              </tr>
               
            </tbody>
            <tfoot class="table_footer">
              <tr>
                <td colspan="4"></td>
                <td><strong>Total</strong></td>
                <td>1000000.00</td>
                <td>&nbsp;</td>
              </tr>
            </tfoot>
          </table>
        </div>
        <!-- Table Ends here-->
          
          <!-- Adding Content Starts herer -->
          <div class="clearfix invoice_other">
          <div class="col-md-5">
            <div class="clearfix mb">
              <button type="button" class="btn btn-sm btn-primary mr" onclick="addInvoice();"><i class="fa fa-plus paddingRight"></i>Add</button>
              <!-- <button type="button" class="btn btn-sm btn-default" onclick="deleteRow()"><i class="fa fa-close paddingRight"></i>Clear</button> -->
            </div>
            <div class="clearfix mb"><p>Do you want to raise separate invoice for each item</p>
              <label class="paddingRight">
                <input type="radio" id="seperateInvoice" name="seperateInvoice">
                <strong class="paddingLeft">Yes</strong></label>
              <label class="paddingLeft">
                <input type="radio" id="seperateInvoice" name="seperateInvoice">
                <strong class="paddingLeft">No</strong></label>
            </div>
            <div class="clearfix mb">
              <label><strong>Message</strong></label>
              <textarea class="form-control" rows="2" id="comment" name="comment"></textarea>
            </div>
            
            
            <div class="clearfix mb invoiceAttachment">
            <input type='hidden' name='invoiceAttcahmentMaxRowNumber'  id='invoiceAttcahmentMaxRowNumber' value='1' />
            <table width="100%" width="100%" cellpadding="0" cellspacing="0">
            <tbody id="invoiceAttachmentTbl">
            <tr id='divAttachmentTsRow0'>
            	<td>
              	<label><strong>Attachments</strong></label>
	              <div class="timesheet_week_comm col-md-12 " >
	                <div class="form-group"> 
	                <span>
	                  <input type='file' name='0invoicedocFile' id='0invoicedocFile' field-name='Attachment' onchange='validateContractsUploadFile(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addInvoiceAttachment()'></i>
	                 </span> </div>
	              </div>
            	</td>
            </tr>
            </tbody>
            </table>
            
            </div>
          </div>
          
          
           <div class="col-md-7">
            <div class="form-horizontal">
              <div class="form-group">
                <label class="col-md-7 text-right"><strong>Subtotal</strong></label>
                <div class="col-md-5 text-right" id="subTotal" name="subTotal"><span><strong>120000.00</strong></span></div>
              </div>
              
              <div class="form-group">
                <div class="col-md-7 select-field">
                  <div class="row">
                    <div class="col-md-7 paddingRight">
                      <select class="form-control input-sm" id="disCount" name="disCount">
                        <option selected="" disabled="">Discount %</option>
                        <option>Discount Value</option>
                      </select>
                    </div>
                    <div class="col-md-5 paddingLeft">
                      <input type="text" class="form-control input-sm" value="10%">
                    </div>
                  </div>
                </div>
                <div class="col-md-5 text-right">
                  <input type="text" class="form-control input-sm" value="333">
                </div>
              </div>
              
              
              
              
              
              <div class="addtax_table">
                    <table width="100%" cellpadding="0" cellspacing="0" class="table-bordered table-striped table-responsive">
                        <thead>
                            <tr>
                                <th colspan="5">
                                Add Tax
                                    <button type="button" class="btn btn-sm btn-primary buttonCustom" title="Add Tax" onclick="cloneTax()">
                      <i class="fa fa-plus"></i></button>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                   <div class="select-field tblminwidth-75">
                                       <div class="tblminwidth-50">
                                        <input type="text" class="form-control input-sm" value="" placeholder="Tax type">
                                    </div>
                                   </div>
                                </td>
                                <td>
                                 <div class="btn-group tblminwidth-75">
                                  <button type="button" class="btn btn-sm btn-primary" title="Discount">%</button>
                                  <button type="button" class="btn btn-sm  btn-primary" title="Flat">Flat</button>
                                </div>
                                </td>
                                <td>
                                    <div class="tblminwidth-75">
                                        <input type="text" class="form-control input-sm" value="" placeholder="Flat / Discount">
                                    </div>
                                </td>
                                <td>
                                    <div class="tblminwidth-75">
                                        <input type="text" class="form-control input-sm" value="" placeholder="Total Value">
                                    </div>
                                </td>
                                <td>
                                    <span class="text-danger"><i class="fa fa-remove"></i></span>
                                </td>
                            </tr>
                           <!--  <tr class="hide-row">
                                <td colspan="2">
                                </td>
                                <td>
                                <strong>Total:4444444444</strong>
                                </td>
                                <td colspan="2" align="right" id="grandTotal" name="grandTotal"><strong>1800000.00</strong></td>
                            </tr> -->
                        </tbody>
                    </table>
                    <!-- AddTax Table Starts here -->
            </div>
            <div class="form-group mt">
                <label class="col-md-7 text-right"><strong>Total</strong></label>
                    <div class="col-md-5 text-right" id="grandTotal" name="grandTotal"><span><strong>120000.00</strong></span></div>
              </div>
            </div>
          </div>
        </div>
        <!-- Adding Content Starts herer -->
          <!-- Drap and Drop Table Row --> 
      <footer class="form-footer">
              <div class="pull-right mr">
                  <button type="button" class="btn btn-sm btn-primary" onclick="saveInvoice('DRAFT')">Save</button>
                  <button type="button" class="btn btn-sm btn-success ml">Send</button>
                  <button type="button" class="btn btn-sm btn-default ml">Cancel</button>
              </div>
          </footer>
      </div>
      </form:form>
    </div>
  </section>
  
  

<!-- ********************************************* Add Rows Invoice Table functionality start  ************************************************************ -->	
	<div id="divTaskTd2" style="display: none; float: left;">
	<div class="tbldata-dotdot-vsm select-field">
       <select class="form-control input-sm" id="REPLACEROWIDcandidateId" name="REPLACEROWIDcandidate" onchange="getSow(REPLACEROWID)"> </select>
       </div>
	</div>
	
	<div id="divTaskTd3" style="display: none; float: left;">
       <div class="tbldata-dotdot-100 select-field">
       <select class="form-control input-sm" id="REPLACEROWIDsow" name="REPLACEROWIDsow"> </select>
       </div>
	</div>
	
	<div id="divTaskTd4" style="display: none; float: left;">
       <div class="tbldata-dotdot-100">
       <input type="text" class="form-control input-sm" placeholder="Rate" id="REPLACEROWIDrate" name="REPLACEROWIDrate">
       </div>
	</div>

	<div id="divTaskTd5" style="display: none; float: left;">
	<div class="tbldata-dotdot-vsm">
	     <input type="text" class="form-control input-sm" placeholder="Total Hours" id="REPLACEROWIDtotlaHours" name="REPLACEROWIDtotlaHours">
	     </div>
	</div>
	
	<div id="divTaskTd6" style="display: none; float: left;">
	     <div class="tbldata-dotdot-100 text-right">741.00</div>
	</div>
	
	<div id="divTaskTd7" style="display: none; float: left;">
	  <a class="text-danger tblminwidth-50" title="Remove row" onclick="removeRow(REPLACEROWID)"><i class="fa fa-remove"></i></a>
	</div>
	
<script type="text/javascript">
	function removeRow(rowId) {
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		var r = document.getElementById('addInvoiceTbl').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
	}
</script>

  <script type="text/javascript">
	function addInvoice() {
		var checkMaxTsRowNum = $("#maxTsRowNum").val();
		var tbl = document.getElementById("addInvoiceTbl");
		var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
		$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
  
		
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divTsRow" + maxRowNum);
		
		var text = "";
		var cell0 = row.insertCell(0);
		text = text+"<div  class='inputCustom'>";
		text = text+'<input type="text" class="calendar paddingRight" id="'+maxRowNum+'sdate-input" name="'+maxRowNum+'invoiceStartDate" placeholder="Start Date">';
		text = text+'<input type="text" class="calendar paddingLeft" id="'+maxRowNum+'edate-input" name="'+maxRowNum+'invoiceEndDate" placeholder="End Date">';
		text = text+'</div>';
		cell0.innerHTML = text;
		
		$( "#"+maxRowNum+"sdate-input" ).datepicker({
			  changeMonth: true,
		      changeYear: true,
		      dateFormat : 'dd-mm-yy',
		      minDate: 0  ,
			  onSelect : function(selected) {
		 	  }
		    });
		
		$( "#"+maxRowNum+"edate-input" ).datepicker({
			  changeMonth: true,
		      changeYear: true,
		      dateFormat : 'dd-mm-yy',
		      minDate: 0  ,
			  onSelect : function(selected) {
		 	  }
		    });
		
		
		var cell1 = row.insertCell(1);
		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/PRICECALCULATION/g,("")); 
		cell1.innerHTML = divTsTd2Str;
		
		var cell1 = row.insertCell(2);
		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd3Str;
		
		var cell1 = row.insertCell(3);
		var divTsTd4Str = document.getElementById("divTaskTd4").innerHTML;
		divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd4Str;
		
		var cell1 = row.insertCell(4);
		var divTsTd5Str = document.getElementById("divTaskTd5").innerHTML;
		divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd5Str;
		
		
		var cell1 = row.insertCell(5);
		var divTsTd6Str = document.getElementById("divTaskTd6").innerHTML;
		divTsTd6Str = divTsTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd6Str;
		
		var cell1 = row.insertCell(6);
		var divTsTd7Str = document.getElementById("divTaskTd7").innerHTML;
		divTsTd7Str = divTsTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd7Str;
		
		getUserList(maxRowNum);
		/* getSow(maxRowNum); */
		
	}
  </script>
  
  
  <script>
  function getUserList(rowId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getAssociatesList?${_csrf.parameterName}=${_csrf.token}",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-Select Associates-</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(opt.lastName != null && opt.lastName != '' && opt.lastName !='undefined'){
		        			lastName = opt.lastName;
		        		}else{
		        			lastName = "";
		        		}
		        		
		        		$('#'+rowId+'candidateId').append('<option value='+opt.id+' >'+opt.firstName+' '+lastName+'</option>');
				}
	        	
	        },
	        
	    });
  
  }
  </script>
  <script>
  function getSow(rowId){
	  var candidateId = $('#'+rowId+'candidateId').val();
	  var orgRelationId = $("#clientOrgRelationId").val();
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getSowcontractListByAssociateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&orgRelationId="+orgRelationId,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-Select sow-</option>"; 
	        	 for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		$('#'+rowId+'sow').append('<option value='+opt.id+' >'+opt.contractTitle+'</option>');
				} 
	        	
	        },
	        
	    });
  }
  
  </script>
  
  
  
  <!-- ********************************************* Add Rows Invoice Table functionality End  ************************************************************ -->
  
  
  <!-- ********************************************* Add Rows resume Table functionality start  ************************************************************ -->
  
  <div id="divInvoiceAttachmentTd1" style="display: none; float: left;">
  			<div class="timesheet_week_comm col-md-12 ">
                <div class="form-group" id="REPLACEROWIDInvoicedocFileuploadFileGroup"> 
               		 <span> 
               		 	<input type="file" name='REPLACEROWIDinvoicedocFile' id='REPLACEROWIDinvoicedocFile' onchange='validateContractsUploadFile(REPLACEROWID)'>
               		    <i class="fa fa-times" onclick="removeInvoiceAttachmentRow(REPLACEROWID);"></i>
               		   </span>
                </div>
             </div>
</div>
  
  
<script>
	function addInvoiceAttachment(){  
				var checkMaxTsRowNum = $("#invoiceAttcahmentMaxRowNumber").val();
				var tbl = document.getElementById("invoiceAttachmentTbl");
				var maxRowNum = parseInt($("input[name = 'invoiceAttcahmentMaxRowNumber']").val());
				$("input[name = 'invoiceAttcahmentMaxRowNumber']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum);
				row.setAttribute("id", "divAttachmentContractsRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divAttachmentTdStr = document.getElementById("divInvoiceAttachmentTd1").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell0.innerHTML = divAttachmentTdStr;
				
	}			
</script>

  <script type="text/javascript">
	function removeInvoiceAttachmentRow(rowId) {
		 document.getElementById("divAttachmentContractsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentContractsRow"+rowId).value= '';
		 var  rId = "divAttachmentContractsRow"+rowId;
		  var r = document.getElementById("invoiceAttachmentTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>
  
  <!-- ********************************************* Add Rows resume Table functionality End  ************************************************************ -->
  
  
	<script>
	  $(function() {
	    $( "#invoiceDate, #dueDate" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	    });
	  });
	  
	  $( "#0sdate-input" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : 'dd-mm-yy',
	      minDate: 0  ,
		  onSelect : function(selected) {
	 	  }
	    });
	
	$( "#0edate-input" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : 'dd-mm-yy',
	      minDate: 0  ,
		  onSelect : function(selected) {
	 	  }
	    });
	  </script> 
	<script type="text/javascript">
	    var config = {
	    '.chosen-select'           : {},
	    '.chosen-select-deselect'  : {allow_single_deselect:true},
	    '.chosen-select-no-single' : {disable_search_threshold:10},
	    '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	    '.chosen-select-width'     : {width:"95%"}
	    }
	    for (var selector in config) {
	    $(selector).chosen(config[selector]);
	    }
	    $('.chosen-select').each(function() {
	    var $this = $(this);
	    $this.next().css({'width': '100%'});
	    })
	    $(document).ready(function(){
	    $("#cd").click(function(){
	    $(".clientDetails").toggleClass('hidden', 500);
	    });
	    });
	</script> 
	<script>
	$("#invoiceTable tbody").sortable({
	    items: "> tr:not(:first)",
	    appendTo: "parent",
	    helper: "clone"
	}).disableSelection();
	    </script>
	    <script>
	    function cloneTax(){
	    }
	    </script>
	    
	    
	    
<!-- added by Rajashekar -->
<script>
function getClientContacts(){
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientContactsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Client Contact</option>";
	for (var i=0; i<json.length; i++)
	{
	var opt = json[i];
	// need to use in edit
	//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
	output = output + "<option value='" +opt.spocUserId+ "'>"+opt.ownerName+"</option>";
	}
	$("#clientContact").empty();
	$("#clientContact").append(output);
	$('#clientContact').trigger("chosen:updated");
	},
	});

	}
</script>



<script>
function getProjectsList(){
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientProjectsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Client Contact</option>";
	for (var i=0; i<json.length; i++)
	{
	var opt = json[i];
	// need to use in edit
	//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
	output = output + "<option value='" +opt.id+ "'>"+opt.projectName+"</option>";
	}
	$("#projectId").empty();
	$("#projectId").append(output);
	$('#projectId').trigger("chosen:updated");
	},
	});

	}
</script>
<script>
function saveInvoice(saveType){
	/* var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };  */
	
	 /*  if ($('#addonboardTask_form').validate(false, validationSettings)){  */
		    var frm = $('#invoice_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var sourceleadUrl ='${sourceleadUrl}';
	        $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	        $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/saveInvoice?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&saveType="+saveType, 
		        success: function(result) {
		     		$("#invoiceLoading").removeAttr("style");
		        },
		    });  
	        /* return true; */
		
	 /*  }else{
		return false;
	  } */
}

/* $('body').on('blur', '#addonboardTask_form', function() {
	$("#addonboardTask_form").showHelpOnFocus();
	$("#addonboardTask_form").validateOnBlur(false, validationSettings);  
}); */
</script>
<!-- End by Rajashekar -->
	    
	    
	    
 <jsp:include page="../masterFooter.jsp" />	