package com.bis.sourcelead.payroll.symmetry;

public class TestHelloPayrollWorld {
	
	public static void main(String[] args)
	{
		HelloPayrollWorld client = new HelloPayrollWorld();
//		String locationCode = "18-097-452890";
		String locationCode = "04-013-12233";       // Arizona Location Code 
		client.hello(locationCode);
	}
}
