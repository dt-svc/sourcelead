(function( $ ) {
  'use strict';
 
  $.fn.addRowToJSON = function(fields,maxRowNumber) {
	  var myRows =[];
	  for(var i=1; i<maxRowNumber; i++){
		  var obj = {};
		  $(fields).each(function(j) {
			  var field = fields[j];
			  obj[field] = $('#'+i+field).val();
		  });
		  myRows.push(obj);
	  }
	 
	  return myRows;
  };
})( jQuery );