package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateImpl;
import dao.UserDaoImp;
import pojo.User;

@WebServlet(value="/login",loadOnStartup = 1)
public class LoginServlet extends  HttpServlet{
	private static final long serialVersionUID = 1L;
	private UserDaoImp userDao;
	private CandidateImpl candidateDao;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		resp.setContentType("text/html");
		try(PrintWriter pw = resp.getWriter()){
			String email = req.getParameter("em");
			String pass = req.getParameter("pass");
			User user = userDao.authentication(email, pass);
			if(user==null) {
				pw.print("<h4>Invalid User</h4>");
			}else {
//				Cookie c = new Cookie("abc",user.toString());
//				resp.addCookie(c);
//				pw.print("<h5>Valid User</h5>");
				HttpSession hs = req.getSession();
				System.out.println("is now: "+hs.isNew());
				System.out.println("session id: "+hs.getId());
				hs.setAttribute("user_details", user);
				hs.setAttribute("user_dao", userDao);
				hs.setAttribute("candidate_dao", candidateDao);
				
				if(user.getRole().equals("admin")) {
					resp.sendRedirect("admin_page");
				}else {
					if(user.isVotingStatus()) {
						resp.sendRedirect("logout");
					}else {
						resp.sendRedirect("Candidate_list");
					}
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ServletException("error in do-post"+getClass(),e);
		}
		
	}

	
	@Override
	public void destroy(){
		try {
			userDao.cleanUp();
			candidateDao.cleanUp();
		} catch (Exception e) {
			System.out.println(("Error in destroyx"+getClass()+""+e));
		}
	}

	
	@Override
	public void init() throws ServletException {
		try {
			userDao = new UserDaoImp();
			candidateDao = new CandidateImpl();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ServletException("Error in init"+getClass(),e);
		}	
	}

	

	
	
	
	
	
}
