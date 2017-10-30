package com.bis.sourcelead.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Component
public class Html2PdfUtil {
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private RequirementOffersService requirementOffersService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	public void convertHtml2PdfUsingHtmlWorker(){
		try {
			//TODO replace the string with actual HTML content.
			String k = "<html><body> This is from iText PDF HTML Worker </body></html>";
			OutputStream file = new FileOutputStream(new File("C:\\SourceLead\\Html2PdfUsingHtmlWorker.pdf"));
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			document.open();
			//TODO use the latest XMLWorkerHelper class in below method instead of deprectaed HTMLWorker class
			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(k));
			document.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void convertHtml2PdfUsingXMLWorker(){
		try {
			//TODO replace the string with actual HTML content.
		    String k = "<html><body> This is from iText PDF XML Worker   </body></html>";
		    OutputStream file = new FileOutputStream(new File("C:\\SourceLead\\Html2PdfUsingXMLWorker.pdf"));
			Document document = new Document();
		    PdfWriter writer = PdfWriter.getInstance(document, file);
		    document.open();
		    InputStream is = new ByteArrayInputStream(k.getBytes());
		    XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
		    document.close();
		    file.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	public byte[] getPdfBytes(ContractsTemplate contractsTemplate, Contracts contracts, OrgLocations orgLocation, Org org) throws Exception{
		
		String templateContent = replacePlaceHolders(contractsTemplate.getTemplateContent(),contracts,orgLocation,org);
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
  	    Document document = new Document();
  	    PdfWriter.getInstance(document, byteArray);
  	    document.open();
  	    HTMLWorker htmlWorker = new HTMLWorker(document);
  	    htmlWorker.parse(new StringReader(templateContent));
  	    document.close();
  	    byte[] pdfBytes = byteArray.toByteArray();
		return pdfBytes;
	}
	
	
	 private String replacePlaceHolders(String templateBody, Contracts contracts, OrgLocations orgLocation, Org org) throws Exception {
	        
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 String orgName = "";
		 if (!"Full time offer".equalsIgnoreCase(contracts.getContractType())){
			  orgName = contracts.getContractOrgLocationId().getOrgLocationName();
		 }
	        /*if(contracts.getOrgRelations().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
	            orgName = contracts.getOrgRelations().getLocationId().getOrgLocationName();
	        }
	        else if(contracts.getOrgRelations().getLocationId().getId().longValue() == orgLocation.getId().longValue()){
	            orgName = contracts.getOrgRelations().getResponderLocationId().getOrgLocationName();
	        }*/
	        
	         Pattern logEntry = Pattern.compile("\\[(.*?)\\]");
	            Matcher matchPattern = logEntry.matcher(templateBody);
	            StringBuffer sb = new StringBuffer();
	            while(matchPattern.find()) {
	                if(matchPattern.group().equalsIgnoreCase("[Client Name]") || matchPattern.group().equalsIgnoreCase("[Vendor Name]")){
	                    matchPattern.appendReplacement(sb, orgName);
	                }else if(matchPattern.group().equalsIgnoreCase("[Client location]") || matchPattern.group().equalsIgnoreCase("[Vendor location]")){
	                	StringBuffer location = new StringBuffer();
	                	if(null != contracts.getContractOrgLocationId().getCountry()&& !"".equalsIgnoreCase(contracts.getContractOrgLocationId().getCountry())){
	            			location.append(contracts.getContractOrgLocationId().getCountry());
	            		}else if(null != contracts.getContractOrgLocationId().getState()&& !"".equalsIgnoreCase(contracts.getContractOrgLocationId().getState())){
	            			location.append(contracts.getContractOrgLocationId().getState());
	            		}else if(null != contracts.getContractOrgLocationId().getCity()&& !"".equalsIgnoreCase(contracts.getContractOrgLocationId().getCity())){
	            			location.append(contracts.getContractOrgLocationId().getCity());
	            		}
	                    matchPattern.appendReplacement(sb, location.toString());    
	                }else if(matchPattern.group().equalsIgnoreCase("[Client Contact]") || matchPattern.group().equalsIgnoreCase("[Vendor Contact]")){
	                    matchPattern.appendReplacement(sb, contracts.getContactUser().getFirstName());
	                }else if(matchPattern.group().equalsIgnoreCase("[Company Location]")){
	                	StringBuffer location = new StringBuffer();
	                	if(null != contracts.getOrgLocationId().getCountry()&& !"".equalsIgnoreCase(contracts.getOrgLocationId().getCountry())){
	            			location.append(contracts.getOrgLocationId().getCountry());
	            		}else if(null != contracts.getOrgLocationId().getState()&& !"".equalsIgnoreCase(contracts.getOrgLocationId().getState())){
	            			location.append(contracts.getOrgLocationId().getState());
	            		}else if(null != contracts.getOrgLocationId().getCity()&& !"".equalsIgnoreCase(contracts.getOrgLocationId().getCity())){
	            			location.append(contracts.getOrgLocationId().getCity());
	            		}
	                    matchPattern.appendReplacement(sb, location.toString());    
	                }else if(matchPattern.group().equalsIgnoreCase("[Company Name]")){
	                    matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getOrgLocationName());
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Company Address]")){
	                    if(null != contracts.getOrgLocationId().getOrg().getOrgAddress()){
							matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getOrg().getOrgAddress());	
						}else{
							matchPattern.appendReplacement(sb, "");
						}
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Company City]")){
	                	if(null != contracts.getOrgLocationId().getCity()){
							matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getCity());	
						}else{
							matchPattern.appendReplacement(sb, "");
						}
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Company State]")){
	                	if(null != contracts.getOrgLocationId().getState()){
							matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getState());	
						}else{
							matchPattern.appendReplacement(sb, "");
						}
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Company Country]")){
	                    if(null != contracts.getOrgLocationId().getCountry()){
							matchPattern.appendReplacement(sb, contracts.getOrgLocationId().getCountry());	
						}else{
							matchPattern.appendReplacement(sb, "");
						}
		            }
	                else if(matchPattern.group().equalsIgnoreCase("[Associate Name]") || matchPattern.group().equalsIgnoreCase("[Employee Name]")){
		            	if(null != contracts.getRequirementSubmission().getId()){
			            	RequirementSubmission reqSub = requirementSubmissionService.getRequirementSubmissionById(contracts.getRequirementSubmission().getId());
			            	if(null != reqSub.getCandidate().getLastName()){
								matchPattern.appendReplacement(sb, reqSub.getCandidate().getFirstName()+" "+reqSub.getCandidate().getLastName());	
							}else{
								matchPattern.appendReplacement(sb, reqSub.getCandidate().getFirstName());
							}
		            	}
		            	else{
		            		matchPattern.appendReplacement(sb,"");
		            	}
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Job Title]")){
		            	if(null !=contracts.getRequirementSubmission().getRequirement().getId()){
				            	Requirement req = requirementService.getRequirementByRequirementId(contracts.getRequirementSubmission().getRequirement().getId());
			                    matchPattern.appendReplacement(sb, req.getJobTitle());
		            	}
		            	else{
		            		Contracts con = contractsTemplateService.getContractById(contracts.getParentContractId().getId());
		            		Requirement req = requirementService.getRequirementByRequirementId(con.getRequirementSubmission().getRequirement().getId());
		                    matchPattern.appendReplacement(sb, req.getJobTitle());
		            	}
	                }
	                // need to know about offer rate
		           else if(matchPattern.group().equalsIgnoreCase("[Offer Rate]")){
		        	   if(null !=contracts.getRequirementSubmission().getId()){
		        		   RequirementOffers requirementOffers =  requirementOffersService.getRequirementOfferSubmissionById(contracts.getRequirementSubmission().getId());
		        		   matchPattern.appendReplacement(sb,requirementOffers.getAmount());
		        	   }
		           }
		           else if(matchPattern.group().equalsIgnoreCase("[Rate Per]")){
	                    matchPattern.appendReplacement(sb, contracts.getBillingRatePer());
	                }
		           else if(matchPattern.group().equalsIgnoreCase("[Rate Type]")){
	                   matchPattern.appendReplacement(sb, contracts.getBillingRateType());
	                }
	                
		            else if(matchPattern.group().equalsIgnoreCase("[Billing Rate]")||matchPattern.group().equalsIgnoreCase("[Rate]")){
		            	if(null !=contracts.getBillingAmount()){
		            		matchPattern.appendReplacement(sb, contracts.getBillingAmount());
		            	}
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Overtime]")){
		            	if(null !=contracts.getOverTimeHours()){
		            		matchPattern.appendReplacement(sb, contracts.getOverTimeHours());
		            	}
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[Payment Term]")){
		            	if(null !=contracts.getPaymentTerm()){
		            		matchPattern.appendReplacement(sb, contracts.getPaymentTerm());
		            	}
	                }
	              
		    		 else if(matchPattern.group().equalsIgnoreCase("[Join Date]")){
		            	String currentTimeStr = Constants.TZ_DF.format(contracts.getJoinDate());  
		        		String startTimeUTC= CommonUtil.convertTimeZones("UTC",timezone.getTimeZoneId(), currentTimeStr);
		        		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		        		Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
		                matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),convertedDate));
		            }
		            else if(matchPattern.group().equalsIgnoreCase("[Start Date]")){
		            	String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());  
	            		String startTimeUTC= CommonUtil.convertTimeZones("UTC",timezone.getTimeZoneId(), currentTimeStr);
	            		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	            		Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
	                    matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),convertedDate));
	                }
		            else if(matchPattern.group().equalsIgnoreCase("[End date]")){
		            	if(null != contracts.getEndDate()){
		            		String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());  
		            		String startTimeUTC= CommonUtil.convertTimeZones("UTC",timezone.getTimeZoneId(), currentTimeStr);
		            		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		            		Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
		            		matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),convertedDate));
		            	}
		            	else{
		            		matchPattern.appendReplacement(sb,"");
		            	}
		            }  
		            else if(matchPattern.group().equalsIgnoreCase("[State Of Jurisdiction]")){
		            	if(null != contracts.getStateJurisdiction()){
		            		matchPattern.appendReplacement(sb, contracts.getStateJurisdiction());
		            	}
		            } 
		            else if(matchPattern.group().equalsIgnoreCase("[Work Location]")){
		            	if(null != contracts.getWorkLocation()){
		            		matchPattern.appendReplacement(sb, contracts.getWorkLocation());
		            	}
		            } 
		            else if(matchPattern.group().equalsIgnoreCase("[Billing Cycle]")){
		            	if(null != contracts.getInvoiceFrequency()){
		            		matchPattern.appendReplacement(sb, contracts.getInvoiceFrequency());
		            	}
		            } 
	        		else if(matchPattern.group().equalsIgnoreCase("[Employee Location]")){
		            	if(null != contracts.getRequirementSubmission().getId()){
			            	RequirementSubmission reqSub = requirementSubmissionService.getRequirementSubmissionById(contracts.getRequirementSubmission().getId());
			            	if(null != reqSub.getCandidate().getAddress()){
			            		matchPattern.appendReplacement(sb, reqSub.getCandidate().getAddress());
			            	}
			            	else{
			            		matchPattern.appendReplacement(sb,"");
			            	}
		            	}
		            	else{
		            		matchPattern.appendReplacement(sb,"");
		            	}
	                }
	            }
	            matchPattern.appendTail(sb);
	        return sb.toString();
	    }
	
}
