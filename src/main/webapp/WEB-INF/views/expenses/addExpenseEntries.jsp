<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!-- Modal -->
 <div class="modal fade" id="addTask" role="dialog">
  <div class="modal-dialog modal-lg"> 

	<!-- Modal content-->
	<div class="modal-content">
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h4 class="modal-title">Add Expenses</h4>
	  </div>

	  <div class="modal-body clearfix expPopCon">
		<div class="row">
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">            

		<div class="form-group">
			<label class="col-md-4">Category Type<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-8">
				<select class="chosen-select form-control">
				  <option>Category Type</option>
				  <option>Category-1</option>
				  <option>Category-2</option>
				  <option>Category-3</option>
				  <option>Others</option>
				</select>
			</div>
		  </div>

		<div class="form-group">
		  <label class="col-md-4">Date<span class="text-danger">&nbsp;*</span></label>
		  <div class="col-md-8">
				<input type="text" class="form-control calendar" value="" name="Date" id="" placeholder="Start date">  
		  </div>
		</div>						  

		<div class="form-group">
			<label class="col-md-4">Receipt Issued by<span class="text-danger">&nbsp;*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control" name="First Name" value="">
			  </div>
		  </div>

		<div class="form-group">
			<label class="col-md-4">Payment Mode<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-8">
				<select class="chosen-select form-control">
				  <option>Select Mode</option>
				  <option>Mode-1</option>
				  <option>Mode-2</option>
				  <option>Mode-3</option>
				  <option>Mode</option>
				</select>
			</div>
		  </div>

		<div class="form-group">
			<label class="col-md-4">Amount<span class="text-danger">&nbsp;*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control" name="First Name" value="">
			  </div>
		  </div>

		<div class="form-group">
			<label class="col-md-4">Attachment</label>
			  <div class="col-md-8 clearfix">
				<div class="input-group col-md-10 pull-left">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile" placeholder="Attachment">
				  </div>
				  <div class="input-group col-md-2 pull-right text-right">
				  	<button class="btn btn-danger"><i class="fa fa-remove"></i></button>
				  </div>
			  </div>
		  </div>
		  
		<div class="form-group">
			<label class="col-md-4">Attachment</label>
			  <div class="col-md-8 clearfix">
				<div class="input-group col-md-10 pull-left">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn1"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile1" placeholder="Attachment">
				  </div>
				  <div class="input-group col-md-2 pull-right text-right">
				  	<button class="btn btn-primary"><i class="fa fa-plus"></i></button>
				  </div>
			  </div>
		  </div>		  						  						  

		  <div class="form-group">
		  <label class="col-md-4">Description</label>
		  <div class="col-md-8">
			<textarea rows="2" class="form-control"></textarea>
		  </div>
		</div>

		</div>
		</div>                      
	  </div>

	  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Add</button>
		<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Close</button>
	  </div>
	</div>
  </div>
</div>
<!-- Modal contente Ends--> 


<div class="project_wrap_table expensTable">
	<table>
	<thead>
			<tr>
				<th>Exp Category</th>
				<th>Date</th>
				<th>Payment Mode</th>
				<th>Currency</th>
				<th>Description</th>
				<th>Amount</th>
				<th>Action</th>
			</tr>
		</thead>	
			<tbody>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div class="tbldata-dotdot-me">Description Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr>
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr> 
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr> 
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr> 
			<tr>
				<td><div>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
				<td>
					<div class="tbldata-dotdot_50">
						<i class="fa fa-pencil-square-o"></i>
						 <i class="glyphicon glyphicon-trash"></i>
					</div>
				</td>
			</tr> 									             			

		</tbody>
	</table>
</div>