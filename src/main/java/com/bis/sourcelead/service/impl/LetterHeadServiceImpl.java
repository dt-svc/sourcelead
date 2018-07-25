package com.bis.sourcelead.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LetterHeadDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class LetterHeadServiceImpl implements LetterHeadService{

	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	LetterHeadDao letterHeadDao;
	
	@Override
	public LetterHead storeLetterHeadData(LetterHeadDTO letterHeadDTO,OrgLocations orgLocation) throws Exception {
		
		
		
		LetterHead letterHead = null;
		if(StringUtils.isNotBlank(letterHeadDTO.getLetterHeadUUID())){
			letterHead = letterHeadDao.getLetterheadByUUID(letterHeadDTO.getLetterHeadUUID());
		}else{
			letterHead = new LetterHead();
		}
		
		letterHead.setLetterHeadName(letterHeadDTO.getLetterHeadName());
		letterHead.setLetterHeadDescription(letterHeadDTO.getLetterHeadDescription());
		letterHead.setLetterHeadUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		letterHead.setOrgLocationId(orgLocation);
		letterHead.setTemplateBgColor(letterHeadDTO.getTemplateBackgroundColor());
		letterHead.setTemplateType(letterHeadDTO.getTemplateType());
		letterHead.setPaddingLeft(letterHeadDTO.getPaddingLeft());
		letterHead.setPaddingRight(letterHeadDTO.getPaddingRight());
		letterHead.setPaddingTop(letterHeadDTO.getPaddingTop());
		letterHead.setPaddingBottom(letterHeadDTO.getPaddingBottom());
		
		if(letterHeadDTO.getFileInfo() != null) {
			FileInfo fileInfo = letterHeadDTO.getFileInfo().get(0);
			letterHead.setFileName(fileInfo.getFileName());
			letterHead.setFileType(fileInfo.getContentType());
			letterHead.setFileURL(fileInfo.getFileURL());
        	FileUploadUtil.moveFile(staticPath, fileInfo, "LetterHeadDocs");
        }
		
		letterHead.setAlignLogo(letterHeadDTO.getAlignLogo());
		letterHead.setHeaderBgColor(letterHeadDTO.getHeaderBackgroundColor());
		letterHead.setHeaderText(letterHeadDTO.getHeaderText());
		letterHead.setAlignHeaderText(letterHeadDTO.getHeaderTextAlign());
		letterHead.setFooterText(letterHeadDTO.getFooterText());
		letterHead.setAlignFooterText(letterHeadDTO.getAlignFooter());
		letterHead.setFooterBgColor(letterHeadDTO.getFooterBackgroundColor());
		letterHead.setStatus(Constants.MEMBER_ACTIVE);
		
		return letterHeadDao.storeLetterHeadData(letterHead);
	}

	@Override
	public ResponseList<LetterHeadDTO> getAllLetterHeadList(LetterHeadDTO letterHeadDTO,Long orgLocationId) {
		return letterHeadDao.getAllLetterHeadList(letterHeadDTO,orgLocationId);
	}

	@Override
	public List<LetterHead> getLetterheadBylocationId(Long orgLocationId) {
		return letterHeadDao.getLetterheadBylocationId(orgLocationId);
	}

	@Override
	public LetterHead getLetterheadByUUID(String UUID) {
		return letterHeadDao.getLetterheadByUUID(UUID);
	}

	@Override
	public LetterHead getLetterheadById(Long letterHeadId) {
		return letterHeadDao.getLetterheadById(letterHeadId);
	}
	
	@SuppressWarnings("deprecation")
	public void letterHeadData(LetterHead letterHead,HttpServletResponse response) throws Exception {

		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		
		Document document = null;
		if (StringUtils.isNotBlank(letterHead.getTemplateType())) {
			Rectangle pageSize;
			if ("A4".equalsIgnoreCase(letterHead.getTemplateType())) {
				 pageSize = new Rectangle(PageSize.A4);
				 pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
				
			} else if ("Letter".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("A6".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.A6);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("Legal".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LEGAL);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			}
		} else {
			document = new Document();
		}
		
//	    Document document = new Document();// For Template Area
	    									// here we can add page size(a4,a6,letter) and padding left,right as a parameter 
	    									//for ex :Document document = new Document(PageSize.A4,15,15,15,15);
//	    document.setPageSize(letterHead.getFileType());
	    
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
		
		PdfPCell footerCell = new PdfPCell();
		Paragraph p1 = new Paragraph(letterHead.getFooterText(),new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
		if("Left".equalsIgnoreCase(letterHead.getAlignFooterText())) {
	    	p1.setAlignment(Element.ALIGN_LEFT);
			/*footerCell.setPaddingRight(476);
			footerCell.setPaddingLeft(0);*/
	    }else if("Right".equalsIgnoreCase(letterHead.getAlignFooterText())){
	    	p1.setAlignment(Element.ALIGN_RIGHT);
	    	/*footerCell.setPaddingRight(0);
	    	footerCell.setPaddingLeft(476);*/
	    }else {
	    	p1.setAlignment(Element.ALIGN_CENTER);
	    	/*footerCell.setPaddingRight(233);
	    	footerCell.setPaddingLeft(233);*/
	    }
				
		if(letterHead.getFooterBgColor() != null && letterHead.getFooterBgColor() != "") {
	    	BaseColor myColor = WebColors.getRGBColor(letterHead.getFooterBgColor());
	    	footerCell.setBackgroundColor(myColor);
	    }
		footerCell.addElement(p1);
   	footer.addCell(footerCell).setPaddingTop(-4);
		
		
		
		
		
		
		// add copyright
		/*Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
		 PdfContentByte cb = writer.getDirectContent();
		 Phrase footerText = new Phrase(letterHead.getFooterText(), ffont);
//		footer.addCell(new Phrase(letterHead.getFooterText(), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
	     ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,footerText,(document.right() - document.left()) / 2 + document.leftMargin(),document.bottom() - 10, 0);*/
		
		// add current page count
//		footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//		footer.addCell(String.format(writer.getPageNumber()+""));
		
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
		 
		Paragraph p = new Paragraph(letterHead.getHeaderText(),new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
	    p.setAlignment(2);
	    document.add(p);
		
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

	@Override
	public Boolean validateLetterHeadName(String letterHeadName, OrgLocations orgLocation) {
		return letterHeadDao.validateLetterHeadName(letterHeadName,orgLocation);
	}

	@Override
	public LetterHead validateNameInEdit(String letterHeadName, Long letterHeadId, Long orgLocationId) {
		return letterHeadDao.validateNameInEdit(letterHeadName,letterHeadId,orgLocationId);
	}

}
