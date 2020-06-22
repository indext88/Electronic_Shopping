package com.ruixun.shopcarts.util;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发送邮件的工具类:
 * @author admin
 *
 */
public class MailUtils {
	/**
	 *  发"激活"邮件
	 * @param to  -- 发给新注册的用户邮箱地址
	 * @param code  -- 激活码
	 */
	public static void sendMail(String to,String code){
		
		try {
			// 获得连接:
			Properties props = new Properties();
			Session session = Session.getInstance(props, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("782372248@ruixun.com", "123");
				}
				
			});
			// 构建邮件:
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("782372248@ruixun.com"));
			// 设置收件人:
			// TO:收件人   CC:抄送   BCC:暗送,密送.
			message.addRecipient(RecipientType.TO, new InternetAddress(to));

			// 主题:
			message.setSubject("来自天虎官方商城的激活邮件!");
			// 正文:
			message.setContent("<h1>来自天虎官方商城的激活邮件:请点击下面链接激活!</h1><h3><a href='http://localhost:8080/userServlet?method=active&code="+code+"'>http://localhost:8080/userServlet?method=active&code="+code+"</a></h3>", "text/html;charset=UTF-8");
		
			// 发送邮件:
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MailUtils.sendMail("782372248@ruixun.com", "89fdf0daf808fa09890af8");
		System.out.println("发送邮件成功!");
	}
}
