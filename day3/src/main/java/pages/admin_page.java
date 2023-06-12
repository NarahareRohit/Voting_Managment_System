package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateImpl;
import pojo.Candidate;
import pojo.User;

/**
 * Servlet implementation class admin_page
 */
@WebServlet("/admin_page")
public class admin_page extends HttpServlet {
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
			pw.print("<h4>In admin page</h4>");
			HttpSession session = request.getSession();
			System.out.println("is now: " + session.isNew());
			System.out.println("session id: " + session.getId());
			User userDetails = (User) session.getAttribute("user_details");

			if (userDetails != null) {
				CandidateImpl canDao = (CandidateImpl) session.getAttribute("candidate_dao");
				ArrayList<User> allData = canDao.displayDataToAdmin();
				pw.print("<table border='1' style=\"background-color: lightgrey; margin: auto\">");
				pw.print("<tr> <td>id</td> <td>first_name</td> <td>last_name</td> <td>email</td> "
						+ "<td>password</td> <td>dob</td> <td>status</td>  <td>role</td> </tr>");
				for (User c : allData) {
					pw.print("<tr> <td>"+c.getId()+"</td>"+
							"<td>"+c.getFirstName()+"</td>"+
							"<td>"+c.getLastName()+"</td>"+
							"<td>"+c.getEmail()+"</td>"+
							"<td>"+c.getPassword()+"</td>"+
							"<td>"+c.getDob()+"</td>"+
							"<td>"+c.isVotingStatus()+"</td>"+
							"<td>"+c.getRole()+"</td>"+
							 "</tr>");
				}

				pw.print("</table>");
				pw.print("<h4> <a href='analysis'> Analysis</a> /"
						+ "<a href='Result_servlet'>Result</a> </h4>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ServletException("erroe in do-get of admin"+e);
		}
	}

}
