package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateImpl;
import pojo.*;

/**
 * Servlet implementation class Candidate_list
 */
@WebServlet("/Candidate_list")
public class Candidate_list extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			pw.print("<h4>In Candidate page</h4>");
			HttpSession session = request.getSession();
			System.out.println("is now: " + session.isNew());
			System.out.println("session id: " + session.getId());
			User userDetails = (User) session.getAttribute("user_details");
			if (userDetails != null) {
				pw.print("<h1>" + "Hello " + userDetails.getFirstName() + "  " + userDetails.getLastName() + "</h1>");
				CandidateImpl candidateDao1 = (CandidateImpl) session.getAttribute("candidate_dao");
				ArrayList<Candidate> allList = candidateDao1.getAllCandidate();
				
				pw.print("<form action='logout'>");
				for (Candidate c : allList) {
					pw.print("<h4> <input type='radio' name='cid' value='" + c.getCandidateId() + "'/>" +c.getName() +"</h4>");
				}
				pw.print("<h4><input type='submit' value='cast to vote'> </h4>");
				pw.print("</from>");
			} else {
				pw.print("<h5> Session Tracking Failed </h5>");
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ServletException("<h5> Session Tracking Failed </h5>");
		}
	}
}
