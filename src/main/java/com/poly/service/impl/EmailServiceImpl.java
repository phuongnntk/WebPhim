package com.poly.service.impl;

import javax.servlet.ServletContext;

import com.poly.entity.User;
import com.poly.service.EmailService;
import com.poly.util.SendEmailUtil;

public class EmailServiceImpl implements EmailService {
	
	private static final String EMAIL_WELCOME_SUBJECT = "Welcome to CGVMovie";
	private static final String EMAIL_FORGOT_PASSWORD = "CGVMovie - New password";

	@Override
	public void sendEmail(ServletContext context, User recipient, String type) {
		String host = context.getInitParameter("host");
		String port = context.getInitParameter("port");
		String user = context.getInitParameter("user");
		String pass = context.getInitParameter("pass");
		try {
			String content = null;
			String subject = null;
			switch (type) {
			case "welcome":
				subject = EMAIL_WELCOME_SUBJECT;
				content = "Dear" + recipient.getUsername() + ", hope you have good time with CGVMovie!!!";
				break;
			case "forgot":
				subject = EMAIL_FORGOT_PASSWORD;
				content = "Dear" + recipient.getUsername() + ", your new password here: " + recipient.getPassword();
				break;
			default:
				subject = "CGVMoive";
				content = "Dear friend, maybe this email is wrong, don't care please!!!";
			}
			SendEmailUtil.sendEmail(host, port, user, pass, recipient.getEmail(), subject, content);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
