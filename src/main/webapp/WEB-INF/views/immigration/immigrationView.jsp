
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Immigrations</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Immigrations List &raquo; Immigration Summery</div>
      
		<div class="clearfix"></div>
		<div class="immigraHead">
			<ul class="immigraTabs">
              <li class="current"><a href="#detailsTab">Details</a></li>
              <li><a href="#passportsTab">Passport</a></li>
              <li><a href="#visasTab">Visa</a></li>
              <li><a href="#formsTab">Forms</a></li>
              <li><a href="#docsTab">Documents</a></li>
              <li><a href="#employersTab">Employers</a></li>
              <li><a href="#residencesTab">Residence</a></li>
              <li><a href="#educationsTab">Educations</a></li>
              <li><a href="#familiesTab">Family</a></li>
              <li><a href="#notesTab">Notes</a></li>
              <li><a href="#activitiesTab">Activities</a></li>
              <div class="clearfix"></div>
            </ul>
		</div>
      
    </section>
    
	<section class="immigraTabsWrap">
  
	  <div id="detailsTab" class="immigraTabs_inn">
		 	<jsp:include page="petitionDetailsTab.jsp" />	  		  		  
		
	  </div>
	  
	  <div id="docsTab" class="immigraTabs_inn">
		<jsp:include page="documentDetailsTab.jsp" />	
		
	  </div>
	  
	  <div id="employersTab" class="immigraTabs_inn">
		<jsp:include page="employerDetailsTab.jsp" />	
	  </div>
	  
	  <div id="residencesTab" class="immigraTabs_inn">
		<jsp:include page="residenceDetailsTab.jsp" />
	  </div>	 
	   
	  <div id="educationsTab" class="immigraTabs_inn">
		<jsp:include page="educationDetailsTab.jsp" />
	  </div>
	  
	  <div id="familiesTab" class="immigraTabs_inn">
		<jsp:include page="familyDetailsTab.jsp" />
	  </div>	
	  
	  <div id="notesTab" class="immigraTabs_inn">
		<jsp:include page="noteDetailsTab.jsp" />
	  </div>  
	  
	  <div id="activitiesTab" class="immigraTabs_inn">
		<jsp:include page="activityDetailsTab.jsp" />
	  </div>  
	  
	  <div id="passportsTab" class="immigraTabs_inn">
		<jsp:include page="passportDetailsTab.jsp" />
	  </div>	  
	  
	  <div id="visasTab" class="immigraTabs_inn">
		<jsp:include page="visaDetailsTab.jsp" />
	  </div>	  
	  
	  <div id="formsTab" class="immigraTabs_inn">
		<jsp:include page="formDetailsTab.jsp" />
		
	  </div>

	</section>
    
    
  </section>
  <!-- Content Area -->
</div>
   
<!-- Footer Area --> 

<!--   Popup Code  -->
<div class="popMask"></div>

<div class="popUpOut" id="docuView">
		<div class="popupHead">
			<h2>Add Document</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Document Type<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Document Type</option>
					  <option>Document-1</option>
					  <option>Document-2</option>
					  <option>Document-3</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Tax Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<input type="text" class="form-control" />
				</div>
			</div>	         
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">Document<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>					          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Description</label>
              	<div class="col-md-8 padding_right">
					<textarea rows="2" class="form-control"></textarea>
				</div>
			</div>		
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>
	
	<div class="popUpOut" id="viewFormpop">
		<div class="popupHead">
			<h2>Add Form</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Form name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<input type="text" class="form-control" />
				</div>
			</div>	         
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn1"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile1" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>					          	
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>
	
	<div class="popUpOut" id="uploadFormPop">
		<div class="popupHead">
			<h2>Upload file</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid popupMidHeight clearfix">
         
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn10"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile10" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>					          	
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut popUpOutBig" id="docuImportView">
		<div class="popupHead">
			<h2>Request Document</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix mt">		
			<table class="table table-responsive table-bordered table-hover noMar">
				<tbody>
					<tr>
						<td width="200px">
							<select class="chosen-select form-control">
							  <option>Select Document Type</option>
							  <option>Document-1</option>
							  <option>Document-2</option>
							  <option>Document-3</option>
							</select>
						</td>
						<td width="auto"><textarea rows="2" placeholder="Description" class="form-control"></textarea></td>
						<td width="120px">
							<label class="switch mt10 ml10 pull-left"><input checked="" type="checkbox"><div class="slider round"></div></label>
							<div class="pull-right"><button type="button" class="btn btm-sm pull-right mt5"><i class="fa fa-plus text-primary"></i></button></div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="emploView">
		<div class="popupHead">
			<h2>Add Employee</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Imployer<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Designation</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right"> 
              		<input type="text" class="form-control calendar" />
				</div>
			</div>				
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>			
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>
	
	<div class="popUpOut" id="popEduView">
		<div class="popupHead">
			<h2>Add Education</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Select Education</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Education</option>
					  <option>Education-1</option>
					  <option>Education-2</option>
					  <option>Education-3</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Specialization</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Score Type</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Score Type</option>
					  <option>Percentage</option>
					  <option>Points</option>
					</select>
				</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Score</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Institute/University</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>											
									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>		
	
	<div class="popUpOut" id="popNoteView">
		<div class="popupHead">
			<h2>Add Note</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">
			<div class="col-sm-12 noPad mt clearfix"><textarea class="form-control" rows="3"></textarea></div>						
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="popPassView">
		<div class="popupHead">
			<h2>Add Passport</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Passport Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Country</option>
					  <option>India</option>
					  <option>USA</option>
					  <option>UK</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid From</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="popResiView">
		<div class="popupHead">
			<h2>Add Residence</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Residence<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Country</option>
					  <option>India</option>
					  <option>USA</option>
					  <option>UK</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="popUpVisa">
		<div class="popupHead">
			<h2>Add Visa</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
	<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Type</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Visa Type</option>
					  <option>H1B</option>
					  <option>H2B</option>
					</select>
				</div>
			</div>
         
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Country</option>
					  <option>India</option>
					  <option>USA</option>
					  <option>UK</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid From</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>		
	
<!--   Popup Code  --> 
<!--   Family Popup's  -->  

	<div class="popUpOut" id="spousePopView">
		<div class="popupHead">
			<h2>Add Spouse</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                           				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="childPopView">
		<div class="popupHead">
			<h2>Add Child</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="fathPopView">
		<div class="popupHead">
			<h2>Add Father</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="mothPopView">
		<div class="popupHead">
			<h2>Add Mother</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script> 
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 
<script src="js/jquery-ui.min.js"></script> 


<script>
$(document).ready(function() {
	$(".popCan").click(function(){
		$(".popMask").fadeOut(200);
		$("#docuView, #emploView, #popUpVisa, #popEduView, #popResiView, #docuImportView, #popNoteView, #popPassView, #viewFormpop, #uploadFormPop").fadeOut(100);
	});
});	
</script>

<!--   Family Popup's  -->
<script>
$(document).ready(function() {
	$("body").click(function(){
		$(".immiSubMenu").hide();
	});
	
	$(".immMenuClick").click(function(){
		$(".immiSubMenu").show();
	});	
	
	$(".immMenuClick, .immiSubMenu").click(function(event) {
	  event.stopImmediatePropagation();
	});	
	
   
	$(".popCan").click(function(){
		$(".popMask").fadeOut(200);
		$("#spousePopView, #childPopView, #fathPopView, #mothPopView").fadeOut(100);
	});	
	
});	
</script>

	<script>
// project side bar tabs
$(document).ready(function() {
	$("#detailsTab").show()
    $(".immigraTabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".immigraTabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
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

    
    <!-- Script for Linked Invoice pop -->
    <script>
        $(".sendReminder").click(function(){
        $('.modal.modal_invoice').fadeIn(200);
        $('.side_mask').fadeIn(200);
        });
        
        $(".close, .cancel").click(function(){
        $('.modal.modal_invoice').fadeOut(200);
        $('.side_mask').fadeOut(200);
        });
    </script>
    <!-- Script for Linked Invoice pop -->
<jsp:include page="../masterFooter.jsp" />
 
