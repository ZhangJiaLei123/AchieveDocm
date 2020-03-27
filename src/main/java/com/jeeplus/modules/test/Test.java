package com.jeeplus.modules.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Test {
	public static void main(String[] args) throws IOException  {
		try{
		String tableName = readTxtLine("d:/myText.txt",1);
		String file = readTxtLine("d:/myText.txt",2);
		String fileText = readTxtLine("d:/myText.txt",3);
		String cloumType = readTxtLine("d:/myText.txt",4);
		String[] files = file.split(",");
		String[] fileTexts = fileText.split(",");
		String[] cloumTypes =  cloumType.split(",");
		System.out.println("DROP TABLE IF EXISTS "+tableName+";");
		System.out.println("create table "+tableName +"(");
		for (int i = 0; i < files.length; i++) {
			//if((i+1)<files.length){
				if("ID".equals(files[i])){
					System.out.println("    "+files[i]+"   "+ cloumTypes[i]+"  not null COMMENT '"+fileTexts[i]+"',");
				}else{
					System.out.println("    "+files[i]+"   "+ cloumTypes[i]+" COMMENT '"+fileTexts[i]+"',");
				}
			//}else{
			//	System.out.println("    "+files[i]+"   "+ cloumTypes[i]+" '"+fileTexts[i]+"'");
		//	}
		}
		System.out.println(" PRIMARY KEY (ID)");
		System.out.println(");");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static String readTxtLine(String txtPath, int lineNo) { 
        String line = ""; 
        String encoding="GBK"; 
        try { 
            File txtFile = new File(txtPath); 
            InputStream in = new FileInputStream(txtFile); 
            InputStreamReader read = new InputStreamReader(in,encoding); 
            BufferedReader reader = new BufferedReader(read); 
            int i = 0; 
            while (i < lineNo) { 
                line = reader.readLine(); 
                i++; 
            } 
            reader.close(); 
        } catch (Exception e) { 
            // TODO: handle exception 
        } 
        return line; 

    }
}
