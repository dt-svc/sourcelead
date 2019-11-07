<div id='changeStatusPopupWithoutComment' class='remove_user_pop remove_user_pop_a'>
	<p id='changeStatusHeading' data-name="Ticket">Change Ticket Status?</p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmStatusWithoutCommentId' type='button' value='Ok' onclick='changeTicketStatusFinal(false)' class="pull-left">
		<input class='u_can_color' type='button' value='Cancel' onClick='closeChangeStatusPopup(true)'>
	</b>
</div>

<div id='changeStatusPopupWithComment' class='remove_user_pop remove_user_pop_a'>
	<p>Comment Required to change Ticket Status</p>
	<div>
		<form id="ticketStatusForm">
			<textarea class="form-control" rows="4" id="statusComment" data-validation="validate_length length1-500" placeholder="Enter Comment"></textarea>
		</form>
	</div>
	<b>
		<input id='confirmStatusWithCommentId' type='button' value='Ok' onclick='changeTicketStatusFinal(true)' class="pull-left">
		<input class='u_can_color' type='button' value='Cancel' onClick='closeChangeStatusPopup(true)'>
	</b>
</div>

<div id='deletePopup' class='remove_user_pop remove_user_pop_a'>
	<p>Delete Ticket?</p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmDeleteId' type='button' value='Ok' onclick='deleteTicket()' class="pull-left">
		<input class='u_can_color' type='button' value='Cancel' onClick='closeDeletePopup()'>
	</b>
</div>

<div id='deleteCommentPopup' class='remove_user_pop remove_user_pop_a'>
	<p>Delete Ticket Comment?</p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmDeleteCommentId' type='button' value='Ok' onclick='deleteTicketComment()' class="pull-left">
		<input class='u_can_color' type='button' value='Cancel' onClick='closeDeleteCommentPopup()'>
	</b>
</div>