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
	private final String UPLOADPATH = "D:\\workspace-sts\\my-slipp\\src\\main\\resources\\static\\upload\\";

	Date date = new Date();
	public String dateYear = new SimpleDateFormat("yyyy").format(date);
	public String dateMonth = new SimpleDateFormat("MM").format(date);
			
	public String dbPath = dateYear+"/"+dateMonth+"/";
	public String savePath = UPLOADPATH+"\\"+dateYear+"\\"+dateMonth+"\\";
	public String saveFolder=  UPLOADPATH +"\\"+dateYear+"\\"+dateMonth;
	
	
	public void singleFileUpload(MultipartFile file, StringBuffer dbFileName) {
        try {
	        File targetDir = new File(saveFolder);  
	        
	        if(!targetDir.exists()) { 
	        	targetDir.mkdirs();
	        }
        	        
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(savePath + dbFileName);
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
	public StringBuffer randomStringFormat(String fileName) {
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
