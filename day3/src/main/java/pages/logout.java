package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;

import dao.CandidateImpl;
import dao.UserDaoImp;
import pojo.User;

/**
 * Servlet implementation class logout
 */
@WebServlet("/logout")
public class logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		try(PrintWriter pw = response.getWriter()){
			pw.print("<h4> In logout page </h4>");
			HttpSession session = request.getSession();
			User voter = (User) session.getAttribute("user_details");
			if(voter!=null) {
				if(voter.isVotingStatus()) {
					pw.print("<h3>You alreadyvoted</h3");
				}else {
					int id = Integer.parseInt(request.getParameter("cid"));
					UserDaoImp userDao = (UserDaoImp) session.getAttribute("user_dao");
					CandidateImpl canDao = (CandidateImpl) session.getAttribute("candidate_dao");
					pw.print("<h1> "+userDao.updateVoting(voter.getId())+"</h1>");
					pw.print("<h1>"+canDao.incCandidateVote(id)+"</h1>");
				}
			}else {
				pw.print("<h5>NO session tracking</h5>");
			}
			pw.print("<h4>Logged Out</h4> ");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ServletException("error in do-get"+e);
		}
	}

}
