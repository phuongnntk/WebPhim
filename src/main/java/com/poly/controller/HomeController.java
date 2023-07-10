package com.poly.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poly.entity.Video;
import com.poly.service.VideoService;
import com.poly.service.impl.VideoServiceIpml;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(urlPatterns = "/index")
public class HomeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private VideoService videoService = new VideoServiceIpml();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Video> videos = videoService.findAll();
		request.setAttribute("videos", videos);
		request.getRequestDispatcher("/views/user/index.jsp").forward(request, response);
	}
}
