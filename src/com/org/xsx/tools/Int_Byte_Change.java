package com.org.xsx.tools;
/**
 *@author: xsx
 *@date:2015��10��29������10:29:34
 *
 *@file name:Int_Byte_Change.java
 */
public class Int_Byte_Change {
	public static int ByteToInt(byte a){
		int b = 256;
		if(a < 0){
			b = b+a;
		}
		else {
			b=a;
		}
		
		return b;
	}
}
