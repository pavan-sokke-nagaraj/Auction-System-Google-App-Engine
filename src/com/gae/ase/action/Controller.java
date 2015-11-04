package com.gae.ase.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gae.ase.bean.UserBean;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	private AuctionDAO auction;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		auction = new UserDaoImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			processRequest(request, response);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			processRequest(request, response);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			AddressException, MessagingException {
		String action = request.getParameter("action");
		String forwardPage = "error.jsp";
		
		if (action.equals("login")) {
			String userName = request.getParameter("username");
			String password = request.getParameter("password");
			request.setAttribute("usern", userName);
			forwardPage = processLogin(request, response, userName, password);
		} else if (action.equals("Go Back to Login")) {
			forwardPage = "login.jsp";
		} else if (action.equals("register")) {
			forwardPage = "register1.jsp";
		} else if (action.equals("logout")) {
			forwardPage = "enough.jsp";
		} else if (action.equals("register1")) {
			String userName = request.getParameter("Rusername");
			String password = request.getParameter("Rpassword");
			String email = request.getParameter("Remail");

			forwardPage = processRegister(userName, password, email);
			if (forwardPage.equals("register2.jsp")) {
				request.setAttribute("uName", userName);
				request.setAttribute("eMail", email);
				request.setAttribute("pass", password);
			}
		} else if (action.equals("register2")) {
			String uname = request.getParameter("R2username");
			request.setAttribute("userNN", uname);
			String p = request.getParameter("R2password");
			System.out.println("Password = " + p);
			String emailAdd = request.getParameter("R2email");
			String n = request.getParameter("R2nickname");
			String f = request.getParameter("R2firstname");
			String l = request.getParameter("R2lastname");
			String y = request.getParameter("R2yob");
			String a = request.getParameter("R2address");
			String c = request.getParameter("R2creditCardNumber");
			System.out.println("Mail = " + emailAdd);
			forwardPage = handlePostUser(request, response, emailAdd, uname, p,
					n, f, l, y, a, c);

		} else if (action.equals("registrationCompleted")) {
			forwardPage = "registrationCompleted.jsp";
		

		} else if (action.equals("backToHome")) {
			forwardPage = "/WEB-INF/restricted/adminHome.jsp";
		} else if (action.equals("adminLogin")) {
			String adminName = request.getParameter("adminName");
			String adminPass = request.getParameter("adminPass");

			if (adminName.equals("yunus") && adminPass.equals("pass")) {
				forwardPage = "/WEB-INF/restricted/adminHome.jsp";
			} else {
				forwardPage = "login.jsp";
			}
		}
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/html/" + forwardPage);
		dispatcher.forward(request, response);
	}

	private String processLogin(HttpServletRequest request, HttpServletResponse response, String userName,
			String password) {
		if (userName.equals("") || password.equals("")) {
			return "loginError.jsp";
		} {
			try {
				UserDTO user = auction.getUserDetails(userName);
				if (user != null) {
					if (user.getUsername().equals(userName)
							&& user.getPassword().equals(password)) {
						UserBean userBeann = (UserBean) request.getSession()
								.getAttribute("userBean");
						userBeann.setUsernameBean(user.getUsername());
						userBeann.setPasswordBean(user.getPassword());
						userBeann.setEmailBean(user.getEmailadd());
						userBeann.setNicknameBean(user.getNickname());
						userBeann.setFirstnameBean(user.getFirstname());
						userBeann.setLastnameBean(user.getLastname());
						userBeann.setYobBean(user.getYearofbirth());
						userBeann.setFulladdBean(user.getFulladdress());
						userBeann.setCreditcardnumbBean(user
								.getCreditcardnumber());
						return "home.jsp";
					} else {
						return "loginError.jsp";
					}
				} else {
					return "loginError.jsp";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "loginError.jsp";
			}
		}
	}

	public static boolean isItInt(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}
	
	private String handlePostUser(HttpServletRequest request,
			HttpServletResponse response, String emailAdd, String u, String p,
			String n, String f, String l, String y, String a, String c)
			throws AddressException, UnsupportedEncodingException,
			MessagingException {
		String page = "";
		System.out.println("password in handle post = "
				+ request.getParameter("R2password"));
		try {
			int val = 1;
			System.out.println("val = " + val);
			UserDTO user = new UserDTO(u, p, emailAdd, n, f, l, y, a, c);
			auction.storeUserTemp(user);
			page = "login.jsp";
			System.out.println("Success");
		} catch (Exception e) {
			e.printStackTrace();
			page = "error.jsp";
		}
		return page;
	}

	private String processRegister(String userName, String password,
			String email) {
		if (userName.equals("") || password.equals("") || email.equals("")) {
			return "error.jsp";
		} else if (!email.contains("@")) {
			return "error.jsp";
		} else {
			return "register2.jsp";
		}
	}

}
