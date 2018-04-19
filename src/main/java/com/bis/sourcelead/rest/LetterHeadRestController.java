package com.bis.sourcelead.rest;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LetterHeadDao;
import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/app")
public class LetterHeadRestController {
	
	
@Value("${static.path}")
private String staticPath;
	
@Autowired
LetterHeadService letterHeadService;

@Autowired
LetterHeadDao letterHeadDao;
	
private static final Logger logger = LoggerFactory.getLogger(LetterHeadRestController.class);
	
@RequestMapping(value= "/saveLetterheadData", method = RequestMethod.POST)
public @ResponseBody String saveLetterheadData(LetterHeadDTO letterHeadDTO,HttpServletRequest request,HttpServletResponse response)
		throws Exception {
	
	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	String status = "failure";
	LetterHead letterHead = letterHeadService.storeLetterHeadData(letterHeadDTO,orgLocation);
	if(letterHead != null) {
		status = "success";
	}
	return status;
}


@RequestMapping(value = "/getAllLetterHeadList", method = RequestMethod.GET)
public ResponseList<LetterHeadDTO> getAllLetterHeadList(LetterHeadDTO letterHeadDTO, HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	
	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	
	String[] tableColumns = {"letterHeadName", "letterHeadDescription", ""};
     int sortColumnIndex = 0; //by default sorting using updated date
	String sortColumnOrder = "desc"; //by default descending order
	if(letterHeadDTO.getOrder() != null && !letterHeadDTO.getOrder().isEmpty()) {
		sortColumnIndex = Integer.parseInt(letterHeadDTO.getOrder().get(0).get("column"));
		sortColumnOrder = letterHeadDTO.getOrder().get(0).get("dir");
	}
	letterHeadDTO.setSortColumn(tableColumns[sortColumnIndex]);
	letterHeadDTO.setSortOrder(sortColumnOrder);
	
	
	return letterHeadService.getAllLetterHeadList(letterHeadDTO,orgLocation.getId());
} 


@RequestMapping(value = "/deleteLetterhead", method = RequestMethod.POST)
public @ResponseBody  void deleteEmployementTemplate(@RequestParam("letterHeadUUID") String letterHeadUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
{
	LetterHead letter = letterHeadService.getLetterheadByUUID(letterHeadUUID);
	letter.setStatus(Constants.MEMBER_IN_ACTIVE);
	letterHeadDao.updateLetterhead(letter);
}


@RequestMapping(value = "/previewLetterHead/{letterHeadUUID}", method = RequestMethod.GET)
public @ResponseBody  void previewLetterHead(@PathVariable("letterHeadUUID") String letterHeadUUID,HttpServletRequest request,HttpServletResponse response) throws Exception
{
	
	try {
		
		LetterHead letterHead = letterHeadService.getLetterheadByUUID(letterHeadUUID);
		
		letterHeadData(letterHead, response);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	
}


private void letterHeadData(LetterHead letterHead,HttpServletResponse response) throws Exception {

	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, byteArray); // Important to display data
    document.open();
    
    PdfPTable header = new PdfPTable(1);
    header.setWidths(new int[]{2});
    header.setTotalWidth(527);
    header.setLockedWidth(true);
    header.getDefaultCell().setFixedHeight(40);
    header.getDefaultCell().setBorder(Rectangle.BOTTOM);
    header.getDefaultCell().setBorderColor(BaseColor.BLUE);
    header.addCell(getCell(letterHead));
    
    PdfPTable footer = new PdfPTable(1);
    // set defaults
	footer.setWidths(new int[]{2});
	footer.setTotalWidth(527);
	footer.setLockedWidth(true);
	footer.getDefaultCell().setFixedHeight(40);
	footer.getDefaultCell().setBorder(Rectangle.TOP);
	footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
	
	// add copyright
	
	footer.addCell(new Phrase(letterHead.getFooterText(), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
	
	// add current page count
//	footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//	footer.addCell(String.format(writer.getPageNumber()+""));
	
	// add placeholder for total page count
	//    PdfPCell totalPageCount = new PdfPCell(total);
	PdfPCell totalPageCount = new PdfPCell();
	totalPageCount.setBorder(Rectangle.TOP);
	totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
	footer.addCell(totalPageCount);
	
	// write page
	PdfContentByte canvas = writer.getDirectContent();
	canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
	footer.writeSelectedRows(0, -1, 34, 50, canvas);
	canvas.endMarkedContentSequence();
	
	document.add(header);
	document.close();
	
	byte[] pdfBytes = byteArray.toByteArray();
	response.setHeader("Content-disposition","inline; filename=Letterhead.pdf");
	response.setContentType("application/pdf");
	response.setContentLength(pdfBytes.length);
  ServletOutputStream sos = response.getOutputStream();
  sos.write(pdfBytes);
  sos.flush();
  sos.close();
	
}


private PdfPCell getCell(LetterHead letterHead) throws Exception
{
	    PdfPCell cell = new PdfPCell();
	    
	    if(letterHead.getHeaderBgColor() != null && letterHead.getHeaderBgColor() != "") {
	    	BaseColor myColor = WebColors.getRGBColor(letterHead.getHeaderBgColor());
	    	cell.setBackgroundColor(myColor);
	    }
	    
	    String removedUrl = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
	    Image img1 = Image.getInstance(removedUrl+letterHead.getFileURL());
	    img1.setAbsolutePosition(50,50);
	    
	    if("Right".equalsIgnoreCase(letterHead.getAlignLogo())) {
	    	cell.setPaddingRight(0);
	    	cell.setPaddingLeft(476);
	    }else if("Left".equalsIgnoreCase(letterHead.getAlignLogo())){
	    	cell.setPaddingRight(476);
	    	cell.setPaddingLeft(0);
	    }else {
	    	cell.setPaddingRight(233);
	    	cell.setPaddingLeft(233);
	    }
	    
    	cell.addElement(img1);
	    return cell;
}
}
