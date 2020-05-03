package com.report.miscallaneous;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;
import com.sun.mail.imap.protocol.Item;

public class Path {

	public static String getRootPath() {
		String rootPath = System.getProperty("catalina.base");
		return rootPath;
	}
	public static String getPath() {
		String rootPath = System.getProperty("catalina.base");
		String path=rootPath+"/webapps/ROOT/";
		/*String path="D:\\New Eclipse Workspace\\Report\\src\\main\\webapp\\";*/
        return path;
	}
	

	public static boolean savePic(MultipartFile file,String file_name) {
		boolean status=false;
		try {			 
			String fileToBeSaved=getPath()+"/profile_picture/"+file_name;
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(fileToBeSaved)));
			byte arr[] = file.getBytes();
			bos.write(arr);			
			bos.flush();			
			bos.close();
			status=true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

		public static String randomPassword() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*(){][";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 12) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

		
	public static ChartInfo getChartData(List<Object[]> list) {
		ChartInfo chartInfo=new ChartInfo();
		try {
			for (Object[] objects : list) {
				if(Long.valueOf(objects[0].toString())==1) {
					chartInfo.setCv_sent(Long.valueOf(objects[2].toString()));
				}
				if(Long.valueOf(objects[0].toString())==2) {
					chartInfo.setInterview(Long.valueOf(objects[2].toString()));
				}
				if(Long.valueOf(objects[0].toString())==3) {
					chartInfo.setOffer(Long.valueOf(objects[2].toString()));
				}
				if(Long.valueOf(objects[0].toString())==4) {
					chartInfo.setJoining(Long.valueOf(objects[2].toString()));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return chartInfo;
	}

}
