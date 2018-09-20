package com.bis.sourcelead;

import java.util.HashMap;
import java.util.Map;

public class Test {
	
	public static void main(String[] args)
	{
		Map<String,String> map = new HashMap<>();
		map.put("Selection 125", "pretax");
		map.put("401(k)", "pretax");
		map.put("403(b)", "pretax");
		map.put("457(b)", "pretax");
		map.put("Roth 401(k)", "posttax");
		map.put("Roth 403(b)", "posttax");
		map.put("Roth 457(b)", "posttax");
		map.put("Simple IRA", "pretax");
		map.put("HSA - Single Limit", "pretax");
		map.put("HSA - Family Limit", "pretax");
		map.put("FSA - medical", "pretax");
		map.put("FSA - Dependent Care", "pretax");

			System.out.println("===="+map.get("Roth 401(k)"));
	}
}
