package com.poly.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poly.constant.SessionAttr;
import com.poly.entity.User;
import com.poly.service.EmailService;
import com.poly.service.UserService;
import com.poly.service.impl.EmailServiceImpl;
import com.poly.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = { "/login", "/logout", "/register", "/forgotPass" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserServiceImpl();
	private EmailService emailService = new EmailServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getServletPath();
		switch (path) {
		case "/login":
			doGetLogin(request, response);
			break;
		case "/register":
			doGetRegister(request, response);
			break;
		case "/logout":
			doGetLogout(session, request, response);
			break;
		case "/forgotPass":
			doGetForgotPass(request, response);
			break;

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getServletPath();
		switch (path) {
		case "/login":
			doPostLogin(session, request, response);
			break;
		case "/register":
			doPostRegister(session, request, response);
			break;
		case "/forgotPass":
			doPostForgotPass(request, response);
			break;
		case "/changePass":
			doPostChangePass(session, request, response);
			break;
		}
	}

	private void doGetLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/user/login.jsp").forward(request, response);
	}

	private void doGetRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
	}

	private void doGetLogout(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session.removeAttribute(SessionAttr.CURRENT_USER);
		response.sendRedirect("index");
	}

	private void doGetForgotPass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/user/forgot-pass.jsp").forward(request, response);
	}

	protected void doPostLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");// lấy giá trị username bằng Parameter
		String password = request.getParameter("password");

		User user = userService.login(username, password);

		if (user != null) {
			session.setAttribute(SessionAttr.CURRENT_USER, user);// currentUser Người dùng hiện tại
			response.sendRedirect("index");
		} else {
			response.sendRedirect("login");
		}
	}

	protected void doPostRegister(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");// lấy giá trị username bằng Parameter
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		User user = userService.register(username, password, email);

		if (user != null) {
			emailService.sendEmail(getServletContext(), user, "welcome");
			session.setAttribute(SessionAttr.CURRENT_USER, user);// currentUser Người dùng hiện tại
			response.sendRedirect("index");
		} else {
			response.sendRedirect("register");
		}
	}

	private void doPostForgotPass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String email = request.getParameter("email");
		User userWithNewPass = userService.resetPassword(email);
		if (userWithNewPass != null) {
			emailService.sendEmail(getServletContext(), userWithNewPass, "forgot");
			response.setStatus(204);
		} else {
			response.setStatus(400);
		}
	}

	private void doPostChangePass(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		String currentPass = request.getParameter("currentPass");
		String newPass = request.getParameter("newPass");
		
		User currentUser = (User) session.getAttribute(SessionAttr.CURRENT_USER);
		if (currentUser.getPassword().equals(currentPass)) {
			currentUser.setPassword(newPass);
			User updatedUser = userService.update(currentUser);
			if (updatedUser != null) {
				session.setAttribute(SessionAttr.CURRENT_USER, updatedUser);
				response.setStatus(204);
			} else {
				response.setStatus(400);
			}
		} else {
			response.setStatus(400);
		}
	}
}
