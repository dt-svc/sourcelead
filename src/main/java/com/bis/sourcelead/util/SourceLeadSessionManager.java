package com.bis.sourcelead.util;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.bis.sourcelead.BeanUtil;
import com.bis.sourcelead.couchbase.ConnectionManager;
import com.bis.sourcelead.couchbase.CouchbaseClient;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.OrgSettings;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class SourceLeadSessionManager {
	
	public static boolean USE_SESSION_STORE = true;
	
	
	private CommonUtil commonUtil = BeanUtil.getBean(CommonUtil.class);
	static CouchbaseClient c = null;
	static{
		c = ConnectionManager.instance().getClient();
	}
	
	public static void setSessionAttribute(HttpServletRequest request, String attribute, Object obj) {
		request.getSession().setAttribute("SourceLeadWeb_" + attribute, obj);
		String id = request.getSession().getId();
		String jsonKey = "" + id + "_SourceLeadWeb_" + attribute;
		String jsonValue = null;
		// If it is a string
		if(obj instanceof String){
			
		} else {
			try {
				if (id != null && attribute != null) {
						if (obj != null) {
							
							jsonValue = SessionTransformUtil.keyValueToSession(attribute, obj);
							
							System.out.println("Value of jsonKey :" + jsonKey);
							System.out.println("Value of jsonValue : " + jsonValue);
	
							c.set(id + "_" + attribute, jsonKey, jsonValue);
							System.out.println("okkk ......... c.set() done");
						} else
							System.out.println("obj is NuLL : " + obj);
				} else
					System.out.println("Id is NuLL : " + id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Object getSessionAttributeProxy(HttpServletRequest request, String attribute) {
		return request.getSession().getAttribute("SourceLeadWeb_"+attribute);
	}
	
	public static Object getSessionAttributeSS(HttpServletRequest request, String attribute) {
		String id = request.getSession().getId();
		Object sv = c.get(id + "_" + attribute);
		SessionTransformUtil.objectValueFromSession(attribute, sv.toString());
		return sv;
	}

	public static Object getSessionAttribute(HttpServletRequest request, String attribute) {
		if(USE_SESSION_STORE){
			return getSessionAttributeSS(request, "SourceLeadWeb_"+attribute);
		} else
		return getSessionAttributeProxy(request, "SourceLeadWeb_"+attribute);
	}
	
	public static void removeSessionAttribute(HttpServletRequest request, String attribute) {
		request.getSession().removeAttribute("SourceLeadWeb_"+attribute);
	}
	
	public static String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	
	public static void main(String[] a){
		
		String tmc = "{\"id\":5,\"masterCandidateId\":{\"handler\":{\"interfaces\":[{}],\"constructed\":true,\"persistentClass\":{},\"overridesEquals\":false,\"entityName\":\"com.bis.sourcelead.entity.MasterCandidate\",\"id\":3,\"initialized\":false,\"readOnly\":false,\"unwrap\":false,\"sessionFactoryUuid\":\"d122e26c-352a-4f18-9731-7583d01d8c62\",\"allowLoadOutsideTransaction\":true}},\"orgLocationId\":{\"handler\":{\"interfaces\":[{}],\"constructed\":true,\"persistentClass\":{},\"overridesEquals\":false,\"entityName\":\"com.bis.sourcelead.entity.OrgLocations\",\"id\":5,\"initialized\":false,\"readOnly\":false,\"unwrap\":false,\"sessionFactoryUuid\":\"d122e26c-352a-4f18-9731-7583d01d8c62\",\"allowLoadOutsideTransaction\":true}},\"consultantUser\":{\"handler\":{\"interfaces\":[{}],\"constructed\":true,\"persistentClass\":{},\"overridesEquals\":false,\"entityName\":\"com.bis.sourcelead.entity.User\",\"id\":5,\"initialized\":false,\"readOnly\":false,\"unwrap\":false,\"sessionFactoryUuid\":\"d122e26c-352a-4f18-9731-7583d01d8c62\",\"allowLoadOutsideTransaction\":true},\"userRole\":[],\"employee\":[],\"assignJobCount\":0,\"userRequirementsCount\":0,\"userApplicantsCount\":0,\"userCandidatesCount\":0,\"userShortlisteApplicantsCount\":0,\"userSubmittedApplicantsCount\":0,\"userInterviewedApplicantsCount\":0,\"userOfferedApplicantsCount\":0,\"userRejectedApplicantsCount\":0,\"userApplicantsBackOutCount\":0},\"candidateType\":\"Candidate\",\"firstName\":\"Credas\",\"lastName\":\"\",\"email\":\"credas@gmail.com\",\"country\":\"India\",\"status\":1,\"createdDate\":\"Sep 18, 2018 2:00:13 PM\",\"createdBy\":\"Ws3Ixrqi\",\"candidateUUID\":\"62ahCMyX\",\"isAssociate\":\"Y\",\"candidateEducation\":[],\"candidateResumes\":[],\"candidateEmployers\":[],\"noOfReq\":0,\"isCandidateAdedd\":false,\"skillPercentage\":0.0,\"expPercentage\":0.0,\"locationPercentage\":0.0,\"ratePercentage\":0.0,\"noticePeriodPercentage\":0.0,\"totalKeyWordPerPercentage\":0.0,\"generatedId\":\"XFHCD1\",\"profilePercentage\":0}";

		String tl = "[{\"id\":11,\"tabName\":\"Settings\",\"status\":1,\"tabIcon\":\"fa fa-cog\"},{\"id\":2,\"tabName\":\"Associates\",\"tabOrder\":2,\"status\":1,\"tabIcon\":\"fa fa-users\"},{\"id\":7,\"tabName\":\"Clients\",\"tabOrder\":3,\"status\":1,\"tabIcon\":\"fa fa-user-plus\"},{\"id\":8,\"tabName\":\"Jobs\",\"tabOrder\":4,\"status\":1,\"tabIcon\":\"fa fa-briefcase\"},{\"id\":9,\"tabName\":\"Vendors\",\"tabOrder\":5,\"status\":1,\"tabIcon\":\"fa fa-suitcase\"},{\"id\":10,\"tabName\":\"Candidates\",\"tabOrder\":6,\"status\":1,\"tabIcon\":\"fa fa-user\"},{\"id\":19,\"tabName\":\"On \\u0026 Off Boarding\",\"tabOrder\":7,\"status\":1,\"tabIcon\":\"fa fa-black-tie\"},{\"id\":3,\"tabName\":\"Projects\",\"tabOrder\":8,\"status\":1,\"tabIcon\":\"fa fa-bar-chart\"},{\"id\":5,\"tabName\":\"My Timesheets\",\"tabs\":{\"id\":4,\"tabName\":\"Timesheets\",\"tabOrder\":9,\"status\":1,\"tabIcon\":\"fa fa-clock-o\"},\"subTabOrder\":1,\"tabOrder\":9,\"status\":1},{\"id\":6,\"tabName\":\"Timesheet Approval\",\"tabs\":{\"id\":4,\"tabName\":\"Timesheets\",\"tabOrder\":9,\"status\":1,\"tabIcon\":\"fa fa-clock-o\"},\"subTabOrder\":2,\"tabOrder\":9,\"status\":1},{\"id\":24,\"tabName\":\"My Expenses\",\"tabs\":{\"id\":23,\"tabName\":\"Expenses\",\"tabOrder\":10,\"status\":1,\"tabIcon\":\"fa fa-calculator\"},\"subTabOrder\":1,\"tabOrder\":10,\"status\":1},{\"id\":25,\"tabName\":\"Expenses Approval\",\"tabs\":{\"id\":23,\"tabName\":\"Expenses\",\"tabOrder\":10,\"status\":1,\"tabIcon\":\"fa fa-calculator\"},\"subTabOrder\":2,\"tabOrder\":10,\"status\":1},{\"id\":14,\"tabName\":\"Asset Management\",\"tabs\":{\"id\":12,\"tabName\":\"Back Office\",\"tabOrder\":11,\"status\":1},\"subTabOrder\":2,\"tabOrder\":11,\"status\":1,\"tabIcon\":\"fa fa-cogs\"},{\"id\":16,\"tabName\":\"Contracts\",\"tabOrder\":13,\"status\":1,\"tabIcon\":\"fa fa-object-ungroup\"},{\"id\":21,\"tabName\":\"Receivable\",\"tabs\":{\"id\":20,\"tabName\":\"Billing\",\"tabOrder\":14,\"status\":1,\"tabIcon\":\"fa fa-money\"},\"subTabOrder\":1,\"tabOrder\":14,\"status\":1},{\"id\":22,\"tabName\":\"Payables\",\"tabs\":{\"id\":20,\"tabName\":\"Billing\",\"tabOrder\":14,\"status\":1,\"tabIcon\":\"fa fa-money\"},\"subTabOrder\":2,\"tabOrder\":14,\"status\":1},{\"id\":30,\"tabName\":\"Campaigns\",\"tabOrder\":17,\"status\":1,\"tabIcon\":\"fa fa-bullhorn\"},{\"id\":31,\"tabName\":\"Leave Management\",\"tabOrder\":18,\"status\":1,\"tabIcon\":\"fa fa-file-text-o\"},{\"id\":37,\"tabName\":\"Run Payroll\",\"tabs\":{\"id\":32,\"tabName\":\"Payroll\",\"tabOrder\":19,\"status\":1,\"tabIcon\":\"fa fa-usd\"},\"subTabOrder\":1,\"tabOrder\":19,\"status\":1},{\"id\":38,\"tabName\":\"My Payroll\",\"tabs\":{\"id\":32,\"tabName\":\"Payroll\",\"tabOrder\":19,\"status\":1,\"tabIcon\":\"fa fa-usd\"},\"subTabOrder\":2,\"tabOrder\":19,\"status\":1},{\"id\":34,\"tabName\":\"Immigrations\",\"tabs\":{\"id\":33,\"tabName\":\"Immigration\",\"tabOrder\":20,\"status\":1,\"tabIcon\":\"fa fa-plane\"},\"subTabOrder\":1,\"tabOrder\":20,\"status\":1},{\"id\":35,\"tabName\":\"My Immigration\",\"tabs\":{\"id\":33,\"tabName\":\"Immigration\",\"tabOrder\":20,\"status\":1,\"tabIcon\":\"fa fa-plane\"},\"subTabOrder\":2,\"tabOrder\":20,\"status\":1},{\"id\":36,\"tabName\":\"Reports\",\"tabOrder\":21,\"status\":1,\"tabIcon\":\"fa fa-pie-chart\"}]";
//		Object o = SessionTransformUtil.objectValueFromSession("tabsList", tl);
		Object o = SessionTransformUtil.objectValueFromSession("candidate", tmc);
		System.out.println(o);
		

	}
	
}
