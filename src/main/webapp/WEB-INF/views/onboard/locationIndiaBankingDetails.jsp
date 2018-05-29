
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<style>
.edit_member_pop {
	top:12% !important; left: 35%; margin: 0px auto; z-index: 15500; width: 520px;
	position: fixed !important; background-color: #f9f9f9; box-shadow: 0px 0px 5px rgba(0,0,0,0.3); padding: 15px;
}
.edit_member_pop .footerBtns input {
    width: 50%; border: none; height: 30px; font-size: 14px; font-weight: 500; color: #FFF; background-color: #ff4b4b;
}
.edit_member_pop .footerBtns input.u_can_color {
    background-color: #375d81;
}
.edit_member_pop h4 {
background-color: #dcdcdc; padding: 10px; margin: 0; margin-bottom: 15px; font-size: 15px;
}
</style>

 <div class="clearfix styleTables mt overflow_Auto">
	<table width="100%">
		<thead>
			<tr>
			  <th>Type</th>           
			  <th>Email</th>           
			  <th>Bank Name</th>         
			  <th>Account Number</th>     
			  <th>Routing Number</th>     
			  <th>First Name</th>        
			  <th>Last Name</th>         
			  <th>Location</th>           
			  <th>Default</th>           
			  <th>Active</th>           
			  <th>Action</th>                  
			</tr>
		</thead>
		<tbody>
		<c:if test="${paymentListSize > 0}">
			<c:forEach var="payment" items="${paymentList}">
				<tr>
				<td><div class="tblminwidth-50">${!empty payment.accountType ? payment.accountType : "--"}</div></td>
			    <td><div class="tbldata-dotdot-100">${!empty payment.email ? payment.email : "--"}</div></td>
			    <td><div class="tbldata-dotdot-100">${!empty payment.bankName ? payment.bankName : "--"}</div></td>
			    <td><div class="tblminwidth-30">${!empty payment.accountNumber ? payment.accountNumber : "--"}</div></td>
			    <td><div class="tblminwidth-30">${!empty payment.routingNumber ? payment.routingNumber : "--"}</div></td>
			    <td><div class="tbldata-dotdot-100">${!empty payment.firstName ? payment.firstName : "--"}</div></td>
				<td><div class="tbldata-dotdot-100">${!empty payment.lastName ? payment.lastName : "--"}</div></td>
				<td><div class="tbldata-dotdot-100">${!empty payment.orgLocationName ? payment.orgLocationName : "--"}</div></td>
				<td><div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="changeDefaultPaymentAccount(this, '${payment.id}')" type="checkbox" ${payment.defaultAccount ?  'checked' : ''}/> <div class="slider round" style="position:absolute !important;"></div> </label> </div></div> </td>
				<td><div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="changeActivePaymentAccount('${payment.id}')" type="checkbox" ${!payment.disabled ?  'checked' : '' }/> <div class="slider round" style="position:absolute !important;"></div> </label> </div></div></td>
				<c:if test="${payment.accountType ne 'WEPAY'}">
				<td><div class="tblminwidth-50"><a href="javascript:void(0)" onclick="editPaymentAccount('${payment.accountType}','${payment.id}','${payment.orgLocationId}','${payment.firstName}','${payment.lastName}','${payment.accountNumber}','${payment.routingNumber}','${payment.bankName}','${payment.bsrNumber}','${payment.micrCode}')">Edit</a></div></td>
				</c:if>
				<c:if test="${payment.accountType eq 'WEPAY'}">
				<td><div class="tblminwidth-50"><a href='javascript:void(0)' onclick='grantWePayAccess("${payment.id}")'>Resend Mail</a></div></td>
				</c:if>
				</tr>
			</c:forEach>
		 </c:if>
		</tbody>
	</table>
	<c:if test="${paymentListSize == 0}">
	 <div class='row'> 
		 <div class='col-lg-12'> 
			 <div class='dataTables_empty'>
			 </div>
		 </div>
	 </div>
	</c:if>
</div>
					
					