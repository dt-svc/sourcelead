package com.bis.sourcelead.dto.helper;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class Paginator {
	private int totalRows;
	private int pageNo;
	private int pageSize = 10;
	private String sortBy;
	private int sortType;
	private int filterType;

	public void applyPaginationData(String query, Session session, List<String> params) {
		String queryString = "select count(*) " + (query.substring(query.toLowerCase().indexOf("from"), query.indexOf("order by")));
		Query queryObj = session.createQuery(queryString);

		if (params != null && !params.isEmpty()) {
			for (String param : params) {
				if (param.split(";")[0].equalsIgnoreCase("String")) {
					queryObj.setString(param.split(";")[1], param.split(";")[2]);
				} else if (param.split(";")[0].equalsIgnoreCase("integer")) {
					queryObj.setInteger(param.split(";")[1], Integer.parseInt(param.split(";")[2]));
				} else if (param.split(";")[0].equalsIgnoreCase("boolean")) {
					queryObj.setBoolean(param.split(";")[1], Boolean.parseBoolean(param.split(";")[2]));
				} else if (param.split(";")[0].equalsIgnoreCase("long")) {
					queryObj.setLong(param.split(";")[1], Long.parseLong(param.split(";")[2]));
				}
			}
		}

		String s = queryObj.list().toString();
		s = s.substring(1, s.length() - 1);
		int no = s.isEmpty() ? 0 : Integer.parseInt(s.contains(",") ? s.split(",").length + "" : s);
		setTotalRows(no);
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public int getSortType() {
		return sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	public int getFilterType() {
		return filterType;
	}

	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}
}
