package com.bis.sourcelead.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
*
* @author  Pradeep Reddy
*          
*          |Ver_Number|  |Modified By|   |Modified On|  |Comments|
*          -----------   -------------   -------------  ----------
* @version    1.0        Pradeep Reddy     New_File       New_File
* 
* @since   2017-06-02
*/

@Controller
@RequestMapping("/app")
public class ExpensesEntryDocmentsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesEntryDocmentsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	/**
	 * checks string is empty or null
	 * @param str
	 * @return
	 */
	 
	 protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
}
