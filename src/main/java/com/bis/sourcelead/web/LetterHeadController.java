package com.bis.sourcelead.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Controller
@RequestMapping (value="/app")
public class LetterHeadController extends PdfPageEventHelper {
	
	@Value("${static.path}")
	private String staticPath;
	
	private PdfTemplate t;
    private Image total;
    
    private static final String FILE_NAME = "C:/Users/BIS94/Desktop/Rakshit.pdf";

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	LetterHeadService letterHeadService;

	@RequestMapping (value="/letterHead")
	public ModelAndView CustomField() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.setViewName("letterHead/letterHead");
		return model;
	}
	
	@RequestMapping (value="/letterHeadList")
	public ModelAndView letterHeadList() {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.setViewName("letterHead/letterHeadList");
		return model;
	}
	
	
	@RequestMapping(value = "/editLetterhead")
    public ModelAndView editTemplate(@RequestParam(value="letterHeadUUID", required=false) String letterHeadUUID ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		LetterHead letter = letterHeadService.getLetterheadByUUID(letterHeadUUID);
		
		ModelAndView model = new ModelAndView();
		model.addObject("letterHeadUUID", letterHeadUUID);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("letterHead", letter);
		model.setViewName("letterHead/letterHead");
		return model;
	}


    public void openDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }


    private void addHeader(PdfWriter writer,LetterHeadDTO letterHeadDTO) throws Exception{
        PdfPTable header = new PdfPTable(2);
        try {
            // set defaults
            header.setWidths(new int[]{2, 24});
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(40);
//            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
//            header.getDefaultCell().setBackgroundColor(BaseColor.GREEN);

            // add image
            if(letterHeadDTO.getFileInfo() != null) {
            	 Image logo = Image.getInstance(staticPath.replace("/slead-static", "")+letterHeadDTO.getFileInfo().get(0).getFileURL());
//               logo.setBackgroundColor(BaseColor.RED);
//            	 logo.setAlignment(Element.ALIGN_RIGHT);
            	 header.addCell(logo);
            }

//            Paragraph p = new Paragraph(letterHeadDTO.getHeaderText());
//            p.setAlignment(Element.ALIGN_CENTER);
//            header.addCell(p);
//            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph p = new Paragraph(letterHeadDTO.getHeaderText());
            if(letterHeadDTO.getHeaderTextAlign().equalsIgnoreCase("Left")) {
            	header.addCell(getCell(letterHeadDTO.getHeaderText(), PdfPCell.ALIGN_LEFT));
            }else if(letterHeadDTO.getHeaderTextAlign().equalsIgnoreCase("Right")) {
            	header.addCell(getCell(letterHeadDTO.getHeaderText(), PdfPCell.ALIGN_RIGHT));
            }else {
            	header.addCell(getCell(letterHeadDTO.getHeaderText(), PdfPCell.ALIGN_MIDDLE));
            }
            
            header.addCell(p);
            
//            p.add(new Chunk(glue));
//            p.add(letterHeadDTO.getHeaderText());
           /* p.add(Chunk.NEWLINE);
            p.add(new Chunk(glue));
            p.add("Hyderabad");
            p.add(Chunk.NEWLINE);
            p.add(new Chunk(glue));
            p.add("9964639665");*/
            // write content
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } 
    }
    
    public PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBackgroundColor(BaseColor.RED);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private void addFooter(PdfWriter writer, LetterHeadDTO letterHeadDTO){
        PdfPTable footer = new PdfPTable(3);
        try {
            // set defaults
            footer.setWidths(new int[]{24, 2, 1});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
//            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            footer.getDefaultCell().setBackgroundColor(BaseColor.YELLOW);

            // add copyright
            footer.addCell(new Phrase(letterHeadDTO.getFooterText(), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            
//            if(letterHeadDTO.getAlignFooter().equalsIgnoreCase("Left")) {
//            	footer.addCell(getCell(letterHeadDTO.getFooterText(), PdfPCell.ALIGN_LEFT));
//            }else if(letterHeadDTO.getAlignFooter().equalsIgnoreCase("Right")) {
//            	footer.addCell(getCell(letterHeadDTO.getFooterText(), PdfPCell.ALIGN_RIGHT));
//            }else {
//            	footer.addCell(getCell(letterHeadDTO.getFooterText(), PdfPCell.ALIGN_MIDDLE));
//            }

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
            
            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 50, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void closeDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }
    
   /* @RequestMapping (value="/saveLetterhead")
    public Response<String> saveLetterhead(LetterHeadDTO letterHeadDTO) throws Exception {
		
    	return null;
    }*/
}
