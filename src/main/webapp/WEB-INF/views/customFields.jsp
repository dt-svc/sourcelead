r<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 
 
 
 <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix cusFieldHead">
      <div class="pull-left positionRelative"><h2>Custom Fields</h2>
		  <select class="form-control selectpicker">
			  <option>Add Job</option>
			  <option>Add Candidate</option>
			  <option>Add Vendor</option>
		  </select>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Custom Fields</div>
	<div class="clearfix"></div>
    </section>
    
    
<section class="immigraTabsWrap">
	  
	  <div class="cusFielInn">
	  
		<div class="cusFielInnHead clearfix">
	  		<h2>Job Details</h2>
		  	<a href="javascript:void(0)" id="addField"><i class="glyphicon glyphicon-plus"></i> Add Field</a> 
		</div>
	  	<div class="cusFielTable">
		  <table>
			<tbody>
			  <tr>
				<th>Action</th>
				<th>Feild Name</th>
				<th>Feild Type</th>
				<th>Description</th>
				<th>Visability</th>
				<th>Status</th>
				<th>Info</th>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort hideIcon" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/textfield.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/radio.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/checkbox.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/selectfeild.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/choosen.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>  						
			</tbody>
		  </table>
		</div>
		
	  </div>
	    
	  <div class="cusFielInn">
	  
		<div class="cusFielInnHead clearfix">
	  		<h2>Company Details</h2>
		</div>
	  	<div class="cusFielTable">
		  <table>
			<tbody>
			  <tr>
				<th>Action</th>
				<th>Feild Name</th>
				<th>Feild Type</th>
				<th>Description</th>
				<th>Visability</th>
				<th>Status</th>
				<th>Info</th>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort hideIcon" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/textfield.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/radio.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/checkbox.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/selectfeild.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/choosen.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>  						
			</tbody>
		  </table>
		</div>
		
	  </div>
	    
	</section>    
    
  </section>
  
  
  
  
<!--   Popup Code  -->
	<div class="popMask"></div>
	<div class="cusFielPopup">
		<div class="popupHead">
			<h2>Add New Field</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		<div class="cuspopHead clearfix">
			<div class="col-md-6 padding_left">
				<select class="chosen-select form-control">
				  <option>Select Section</option>
				  <option>Section-1</option>
				  <option>Section-2</option>
				  <option>Section-3</option>
				</select>				
			</div>
			<div class="col-md-6 noPad">
				<select class="chosen-select form-control">
				  <option>Choose Field</option>
				  <option>Textfield</option>
				  <option>Date field</option>
				  <option>Select field</option>
				  <option>Checkbox</option>
				  <option>Radio button</option>
				</select>				
			</div>			
		</div>
		
		<div class="popupMid clearfix">

			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Label Text:</div>
			  <div class="col-md-6"><input class="field-label form-control input-sm" type="text"> </div>
			  <div class="col-md-3 lblHeight">Required?  <input class="toggle-required" type="checkbox">
			  </div>
			</div>
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Option:</div>
			  <div class="col-md-6"><input class="field-label form-control input-sm" type="text"> </div>
			  <div class="col-md-3 lblHeight">Selected?  <input class="toggle-required" type="checkbox">
				  <span class="OpRemove"><i class="fa fa-close"></i></span>
			  </div>
			</div>																								
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Option:</div>
			  <div class="col-md-6"><input class="field-label form-control input-sm" type="text"> </div>
			  <div class="col-md-3 lblHeight">Selected?  <input class="toggle-required" type="checkbox">
				  <span class="OpRemove"><i class="fa fa-close"></i></span>
			  </div>
			</div>				          									
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Title Text:</div>
			  <div class="col-md-6"><input class="field-label form-control input-sm" type="text"> </div>
			</div>
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Placeholder Text:</div>
			  <div class="col-md-6"><input class="field-label form-control input-sm" type="text"> </div>
			</div>
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Alt Text:</div>
			  <div class="col-md-6"><input class="field-label form-control input-sm" type="text"> </div>
			</div>
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Help text:</div>
			  <div class="col-md-9">
			  	<textarea class="form-control input-sm"></textarea>
			  </div>
			</div>					
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Add" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
		
	</div>
<!--   Popup Code  -->  
  
  <script>
// Error pop addSen
	$(document).ready(function() {
		$("#addField").click(function(){
			$(".popMask").fadeIn(200);
			$(".cusFielPopup").fadeIn(100);
		});
		$(".popCan").click(function(){
			$(".popMask").fadeOut(200);
			$(".cusFielPopup").fadeOut(100);
		});
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