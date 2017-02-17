package net.slipp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtils {
	private static final String UPLOADPATH = "D:\\workspace-sts\\my-slipp\\src\\main\\resources\\static\\upload\\";

	static Date date = new Date();
	public static String DATEYEAR = new SimpleDateFormat("yyyy").format(date);
	public static String DATEMONTH = new SimpleDateFormat("MM").format(date);
			
	public static String DBPATH = DATEYEAR+"/"+DATEMONTH+"/";
	public static String SAVEPATH=  UPLOADPATH +"\\"+DATEYEAR+"\\"+DATEMONTH;
	
	
	public static void singleFileUpload(MultipartFile file, StringBuffer dbFileName) {
        try {
	        File targetDir = new File(SAVEPATH);  
	        
	        if(!targetDir.exists()) { 
	        	targetDir.mkdirs();
	        }
        	        
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(SAVEPATH +"\\"+ dbFileName);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/*
	 * 2017.02.13
	 * 파일명 중복되지 않도록 난수 생성
	 * author : aron
	 * 
	 */
	public static StringBuffer randomStringFormat(String fileName) {
		Random rand = new Random();
		StringBuffer buf = new StringBuffer();
		 
		for(int i=0; i<20; i++) {
		    if(rand.nextBoolean()) {
		        buf.append((char)((int)(rand.nextInt(26))+97));
		    }else {
		        buf.append((rand.nextInt(10))); 
		    }
		}
		buf.append("_"+fileName);
		
		return buf;
	}
}
