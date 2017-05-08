package com.zhaochenxi.bleach.mail;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * @ClassName: MailService
 * @Description: 这个类用于发送邮件
 * @author zhaochenxi
 * @date 2016年12月9日 上午10:52:46
 */
public class MailService {

	private static final String MAIL_STAMP_HOST = "smtp.163.com";
	private static final String IMAP_SMTP_MAIL_PASS = "smtp1234567890";
	private static final String FROM_MAIL = "18285111205@163.com";

	private Session session = null;

	public MailService() {

	}

	public void init() {
		Properties props = new Properties();
		props.put("mail.smtp.host", MAIL_STAMP_HOST);
		props.put("mail.smtp.auth", "true");
		session = Session.getInstance(props);
	}

	public void sendTextMail(Mail mail) {
		init();
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM_MAIL));
			msg.setSubject(mail.getTitle());
			msg.setSentDate(new Date());
			msg.setText(mail.getContext());
			Transport transport = session.getTransport();
			transport.connect(MAIL_STAMP_HOST, FROM_MAIL, IMAP_SMTP_MAIL_PASS);
			transport.sendMessage(msg, new Address[] { new InternetAddress(mail.getAddress()) });
			transport.close();
		} catch (MessagingException mex) {
			System.out.println("send failed, exception: " + mex);
		}
	}

	public void sendHtmlMail(Mail mail) {
		init();
		try {
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			// MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			messageBodyPart.setContent(mail.getContext(), "text/html; charset=utf-8");
			mainPart.addBodyPart(messageBodyPart);

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM_MAIL));
			msg.setSubject(mail.getTitle());
			msg.setSentDate(new Date());
			msg.setContent(mainPart);
			Transport transport = session.getTransport();
			transport.connect(MAIL_STAMP_HOST, FROM_MAIL, IMAP_SMTP_MAIL_PASS);
			transport.sendMessage(msg, new Address[] { new InternetAddress(mail.getAddress()) });
			transport.close();
		} catch (MessagingException mex) {
			System.out.println("send failed, exception: " + mex);
		}
	}
	//A temporary password is generated for root@localhost: t::q?ZUdq8Tu
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")  
        .append("<html>")  
        .append("<head>")  
        .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")  
        .append("<title>测试邮件</title>")  
        .append("<style type=\"text/css\">")  
        .append(".test{font-family:\"Microsoft Yahei\";font-size:15px;color:#000000;}")  
        .append("</style>")  
        .append("</head>")  
        .append("<body>")  
        .append("<span class=\"test\">请点击超链接完成注册</span><span><a href=\"http://www.baidu.com\">www.baidu.com</a></span>")  
        .append("</body>")  
        .append("</html>");  
		
		Mail mail = new Mail();
		mail.setTitle("外星猫身份校验邮件");
		mail.setContext(sb.toString());
		mail.setAddress("704405121@qq.com");
		MailService mailService = new MailService();
		mailService.sendHtmlMail(mail);
	}

}
