package com.report.miscallaneous;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Mail {

	 public static boolean sendResetPasswordmail(String to,String password) {
			boolean status=false;
			Properties props=new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "mail.techwisedigital.com");
			props.put("mail.smtp.port", "587");   
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("saurabh@techwisedigital.com", "XjNETAlNyL$]");
				}
			});
			session.setDebug(true);	
			MimeMessage mm=new MimeMessage(session);
			try {
				mm.setHeader("Content-Type", "text/html");
				mm.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				mm.setFrom(new InternetAddress("saurabh@techwisedigital.com"," Report"));
				mm.setSubject("Reset Password | Report ");			
				mm.setContent(
						 "Your Temporary Password is "+password+" . Kindly update your password after successful login ."
						+ "<img src='https://techwise1.s3.ap-south-1.amazonaws.com/techwise.png' height='200px' width='300px' style='margin-left: 350px;'>"
						 ,"text/html");
				Transport.send(mm);

				status=true;

			} catch (Exception e) {
				e.printStackTrace();
			} 
			return status;
		}
	 
	

}
