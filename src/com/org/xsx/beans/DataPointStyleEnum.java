package com.org.xsx.beans;

public enum DataPointStyleEnum {
	
	Normol(0), B_Line(1), C_Line(2), T_Line(3);
	
	private final int index;
	
	private DataPointStyleEnum(int num){
		this.index = num;
	}
	
	public int GetIndex(){
		return this.index;
	}
}
