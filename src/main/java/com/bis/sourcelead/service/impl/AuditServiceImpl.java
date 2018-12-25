package com.bis.sourcelead.service.impl;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Audit;
import com.bis.sourcelead.dao.AuditDao;
import com.bis.sourcelead.entity.AuditRecord;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AuditDao auditDao;

	@Override
	public void saveAuditReport(String tableName, String oldData, String newData, String actionPerformed, User user) {
		try {
			AuditRecord audit = new AuditRecord();
			audit.setTableName(tableName);
			audit.setOldData(oldData);
			audit.setNewData(newData);
			audit.setActionPerformed(actionPerformed);
			audit.setActionTime(new Date());
			audit.setUserId(user);
			auditDao.saveAuditReport(audit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getData(Object entityObject, List<Object> childClassList) {
		String data = "";
		try {
			for (Field f : entityObject.getClass().getDeclaredFields()) {
				if (f.isAnnotationPresent(Audit.class)) {
					f.setAccessible(true);
					Object value = f.get(entityObject);

					if (f.getAnnotation(Audit.class).isParent()) {
						// getting foreign key ID value
						String parentData = getParentTableId(f, f.getType().getDeclaredFields(), value);
						if (!parentData.isEmpty() && !parentData.equalsIgnoreCase("null")) {
							data += (data.isEmpty() ? "" : ",") + ('"' + f.getName() + '"' + ':' + "{" + parentData + "}");
						}
					} else if (f.getAnnotation(Audit.class).isListRecords()) {
						Set<String> childDataList = new LinkedHashSet<>();
						// getting list of child records
						Iterator<?> items = ((Collection) value).iterator();

						while (items != null && items.hasNext()) {
							Object item = items.next();
							// getting child class object based on index
							
							Iterator<?> valueList = ((Collection) value).iterator();
							while(valueList != null && valueList.hasNext()) {
								Object v = valueList.next();
								childDataList.add(getChildTableId(item, v));
							}
						}
						String childDataListString = "";
						int childObjectIndex = 1;
						for(String str : childDataList) {
							childDataListString += (childDataListString.isEmpty() ? "" : ",") + ('"' + "object" + (childObjectIndex++) + '"' + " : " + "{" + str + "}");
						}
						data += (data.isEmpty() ? "" : ",") + '"' + f.getName() + '"' + ':' + '{' + childDataListString + "}";
					} else {
						// getting value
						data += (data.isEmpty() ? "" : ",") + '"' + f.getName() + '"' + ':' + '"' + value + '"';
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		data = "{" + data + "}";
		return data;
	}
	
	public String getParentTableData(Field parentField, Object object) {
		String data = "";
		try {
			for (Field f1 : object.getClass().getDeclaredFields()) {
				if (f1.isAnnotationPresent(Audit.class)) {
					f1.setAccessible(true);
//					if (f1.getName().equalsIgnoreCase("id")) {
					try {
						data += (data.isEmpty() ? "" : ",") + '"' + f1.getName() + '"' + ':' + '"' + f1.get(object) + '"';
					} catch (Exception e) {
						System.out.println("Exception at getParentTableData() in AuditServiceImpl");
					}
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return '"' + parentField.getName() + '"' + ':' + "{" +data + "}";
	}
	
	public String getParentTableId(Field parentField, Field[] fields, Object targetObject) {
		String data = "";
		try {
			if(targetObject != null) {
				for (Field f1 : fields) {
					if (f1.isAnnotationPresent(Audit.class)) {
						f1.setAccessible(true);
						if (f1.getName().equalsIgnoreCase("id")) {
							if(f1.get(targetObject) != null) {
								data += (data.isEmpty() ? "" : ",") + '"' + f1.getName() + '"' + ':' + '"' + f1.get(targetObject) + '"';
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public String getChildTableId(Object item, Object valueObject) {
		String data = "";
		try {
			for (Field f1 : item.getClass().getDeclaredFields()) {
				if (f1.isAnnotationPresent(Audit.class)) {
					f1.setAccessible(true);
					Object childObject = item.getClass().cast(item);
					if (f1.getAnnotation(Audit.class).isParent()) {
						String parentData = getParentTableId(f1, f1.getType().getDeclaredFields(), f1.get(valueObject));
						if (!parentData.isEmpty() && !parentData.equalsIgnoreCase("null"))
							data += (data.isEmpty() ? "" : ",") + ('"' + f1.getName() + '"' + ':' + "{" + parentData + "}");
					} else {
						data += (data.isEmpty() ? "" : ",") + '"' + f1.getName() + '"' + ":" + '"' + f1.get(childObject) + '"';
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
