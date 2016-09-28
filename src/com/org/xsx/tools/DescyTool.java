package com.org.xsx.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DescyTool {
	
	public static String DisDes(String string){
		
		/*∂¡»°key*/
		byte[] key = new byte[54];
		byte[][] word = new byte[94][94];
		
		File keyfile = new File("RES/MyKey.ncd");
		File wordfile = new File("RES/MyWord.ncd");
		InputStream in1 = null;
		InputStream in2 = null;
		StringBuffer result = new StringBuffer();
		
		try {
			in1 = new FileInputStream(keyfile);
			
			int cchar;
			
			for (int i = 0; i < 54; i++) {
				cchar = in1.read();
				key[i] = (byte) (cchar - 127);
			}
			
			in1.close();
			
			in2 = new FileInputStream(wordfile);
			
			int cchar2;
			
			for (int i = 0; i < 94; i++) 
			{
				for (int j = 0; j < 94; j++) 
				{
					cchar2 = in2.read();
					word[i][j] = (byte) (cchar2 - 127);
				}
			}
			in2.close();
			
			for(int i=0; i<string.length(); i++)
			{
				int k = i;
				while(k >= 54)
					k -= 54; 
				for (int j = 0; j < 94; j++) 
				{
					if(word[key[k]-33][j] == string.charAt(i))
					{
						result.append(new Character((char) (j+33)));
						break;
					}
				}
			}

			return result.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static String Des(String string){
		/*∂¡»°key*/
		byte[] key = new byte[54];
		byte[][] word = new byte[94][94];
		
		File keyfile = new File("RES/MyKey.ncd");
		File wordfile = new File("RES/MyWord.ncd");
		InputStream in1 = null;
		InputStream in2 = null;
		
		try {
			in1 = new FileInputStream(keyfile);
			
			int cchar;
			
			for (int i = 0; i < 54; i++) {
				cchar = in1.read();
				key[i] = (byte) (cchar - 127);
			}
			in1.close();
			
			in2 = new FileInputStream(wordfile);
			
			int cchar2;
			
			for (int i = 0; i < 94; i++) 
			{
				for (int j = 0; j < 94; j++) 
				{
					cchar2 = in2.read();
					word[i][j] = (byte) (cchar2 - 127);
				}
			}
			in2.close();
			
			StringBuffer result = new StringBuffer();
	    	
	    	for (int i=0; i<string.length(); i++) {
				char temp1 =  (char) key[i%key.length];
				result.append(new Character((char) word[temp1-33][string.charAt(i)-33]));
			}
			
	    	return result.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
