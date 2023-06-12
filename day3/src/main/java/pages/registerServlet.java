package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoImp;
import pojo.User;

@WebServlet(value = "/register", loadOnStartup = 1)
public class registerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDaoImp userDao;

	public void init(ServletConfig config) throws ServletException {
		try {
			userDao = new UserDaoImp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			userDao.cleanUp();
		} catch (Exception e) {
			System.out.println(("Error in destroyx" + getClass() + "" + e));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try (PrintWriter pw = response.getWriter()) {
			response.setContentType("text/html");
			int id = Integer.parseInt(request.getParameter("id"));
			String fname = request.getParameter("nm");
			String lname = request.getParameter("ln");
			String email = request.getParameter("em");
			String pass = request.getParameter("pw");
			String cpass = request.getParameter("cpw");
			Date dob = Date.valueOf(request.getParameter("dob"));
			Boolean vs = false;
			String role = request.getParameter("ro");
//			if (pass==cpass) {
				User s = new User(id, fname, lname, email, pass, dob, false, role);
				boolean b = userDao.registerUser(s);
				if (b) 
				{
					pw.print("<h1>Done</h1>");
				} else 
				{
					pw.print("<h1>Error in inserting data</h1>");
				}
//			}else {
//				pw.print("<h1>Password is not matching</h1>");
//			}
		} catch (SQLException e)
		{
			throw new ServletException("error in do-post" + getClass(), e);
		}

	}

}
