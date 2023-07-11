package com.poly.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.poly.constant.SessionAttr;
import com.poly.entity.History;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.HistoryService;
import com.poly.service.VideoService;
import com.poly.service.impl.HistoryServiceImpl;
import com.poly.service.impl.VideoServiceIpml;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(urlPatterns = { "/index", "/favorites", "/history" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private VideoService videoService = new VideoServiceIpml();
	private HistoryService historyService = new HistoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getServletPath();
		switch (path) {
		case "/index":
			doGetIndex(request, response);
			break;
		case "/favorites":
			doGetFavorites(session, request, response);
			break;
		case "/history":
			doGetHistory(session, request, response);
			break;
		}
	}

	private void doGetIndex(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		List<Video> videos = videoService.findAll();
		request.setAttribute("videos", videos);
		request.getRequestDispatcher("/views/user/index.jsp").forward(request, response);

	}

	private void doGetFavorites(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) session.getAttribute(SessionAttr.CURRENT_USER);
		List<History> histories = historyService.findByUserAndIsLiked(user.getUsername());
		List<Video> videos = new ArrayList<>();
		histories.forEach(item -> videos.add(item.getVideo()));
		request.setAttribute("videos", videos);
		request.getRequestDispatcher("/views/user/favorites.jsp").forward(request, response);
	}

	private void doGetHistory(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) session.getAttribute(SessionAttr.CURRENT_USER);
		List<History> histories = historyService.findByUser(user.getUsername());
		List<Video> videos = new ArrayList<>();
		histories.forEach(item -> videos.add(item.getVideo()));
		request.setAttribute("videos", videos);
		request.getRequestDispatcher("/views/user/history.jsp").forward(request, response);
	}
}
