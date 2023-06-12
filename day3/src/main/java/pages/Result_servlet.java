package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateImpl;

/**
 * Servlet implementation class Result_servlet
 */
@WebServlet("/Result_servlet")
public class Result_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try(PrintWriter pw = response.getWriter()){
			pw.print("<h1>In result Servlet</h1>");
			HttpSession session = request.getSession();
			CandidateImpl canDao = (CandidateImpl) session.getAttribute("candidate_dao");
			Map<String,Integer> allMap = canDao.result();
			pw.print("<table border='1' style=\"background-color: lightgrey; margin: auto\">");
			pw.print("<tr>  <td>Party</td> <td>Votes</td>  "+"</tr>");
			for(Map.Entry<String, Integer> i:allMap.entrySet()) {
				pw.print("<tr> <td>"+i.getKey()+"</td> <td>"+i.getValue()+"</td> </tr>");
			}
			pw.print("</table>");
			
		}catch (Exception e) {
			throw new ServletException("error in doget result servlet");
		}
		
	}

}
