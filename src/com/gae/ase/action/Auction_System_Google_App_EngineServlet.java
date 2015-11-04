package com.gae.ase.action;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Auction_System_Google_App_EngineServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
