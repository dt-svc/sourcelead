package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.web.AutoCompleteController;
import com.bis.sourcelead.web.DocumentFonts;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping(value = "/timesheetPdf")
public class TimesheetPdfRestController {
	private static final Logger logger = LoggerFactory.getLogger(TimesheetPdfRestController.class);
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;

	public Phrase getPhrase(String text, Font documentFont) {
		return new Phrase(text, documentFont);
	}

	public static class PdfStyle {
		public static void headerCellStyle(PdfPCell cell) {
			cell.setPaddingBottom(15f);
		}
	}

	@RequestMapping(value = "/downloadPdf", method = RequestMethod.GET)
	public void generateAllTimesheetPdf(HttpServletResponse response) throws Exception {

		Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		try {
			Document document = new Document(PageSize.A4, 36, 36, 50, 36);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, bos);
			document.open();
			document.newPage();
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			URL url = classloader.getResource("borderpdf.pdf");
			File is = new File(url.toURI());
			PdfReader reader = new PdfReader(is.toString());
			PdfImportedPage page = writer.getImportedPage(reader, 1);
			PdfContentByte cb = writer.getDirectContent();
			cb.addTemplate(page, 0, 0);

			Paragraph heading = new Paragraph();
			heading.setAlignment(Element.ALIGN_CENTER);
			heading.add(getPhrase("TIMESHEET", DocumentFonts.UNDERLINE));

			document.add(heading);
			document.add(Chunk.NEWLINE);

			PdfPTable tableHead = new PdfPTable(1);
			tableHead.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableHead.setWidthPercentage(100);
			tableHead.setWidths(new int[] { 2 });

			Font f = new Font();
			f.setColor(255, 143, 122);

			PdfPCell headCell = new PdfPCell(new Phrase("pavan", f));
			PdfStyle.headerCellStyle(headCell);
			headCell.setBorder(Rectangle.NO_BORDER);
			tableHead.addCell(headCell);

			document.add(tableHead);

			PdfPTable table = new PdfPTable(10);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 });

			PdfPCell cell;
			cell = new PdfPCell(getPhrase("Projects", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Tasks", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Sun", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Mon", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Tue", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Wed", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Thu", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Fri", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Sat", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			cell = new PdfPCell(getPhrase("Total Hours", DocumentFonts.BOLD));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);

			for (TimesheetEntries te : timesheetEntriesList) {

				cell = new PdfPCell(
						getPhrase(te.getTimesheetSubmissions().getProjectMembers().getProjects().getProjectName() + "",
								DocumentFonts.NORMAL));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				if (te.getProjectTask() != null) {
					cell = new PdfPCell(getPhrase(te.getProjectTask().toString(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("123", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				if (te.getSunHours() != null) {
					cell = new PdfPCell(getPhrase(te.getSunHours(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("12", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				if (te.getMonHours() != null) {
					cell = new PdfPCell(getPhrase(te.getMonHours(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("23", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				if (te.getTueHours() != null) {
					cell = new PdfPCell(getPhrase(te.getTueHours(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("321", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				if (te.getWedHours() != null) {
					cell = new PdfPCell(getPhrase(te.getWedHours(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("514", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				if (te.getTueHours() != null) {
					cell = new PdfPCell(getPhrase(te.getTueHours(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("564", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				if (te.getFriHours() != null) {
					cell = new PdfPCell(getPhrase(te.getFriHours(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("65564", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				if (te.getSatHours() != null) {
					cell = new PdfPCell(getPhrase(te.getSatHours(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("556", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				if (te.getTotalHours() != null) {
					cell = new PdfPCell(getPhrase(te.getTotalHours().toString(), DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(getPhrase("56", DocumentFonts.NORMAL));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

			}
			document.add(table);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			document.close();

			String mimeType = null;
			if (mimeType == null) {
				mimeType = "application/pdf";
			}
			response.setContentType(mimeType);
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", "Timesheet");
			response.setHeader(headerKey, headerValue);
			InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			} 
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

	}

}