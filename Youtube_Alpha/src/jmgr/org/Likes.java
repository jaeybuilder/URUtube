package jmgr.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import jmgr.org.utilities.DB;
import jmgr.org.utilities.PropertiesReader;

/**
 * Servlet implementation class Likes
 */
@WebServlet("/Likes")
public class Likes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Likes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer media_id = Integer.parseInt(request.getParameter("media_id"));
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		JSONObject res = new JSONObject();
		JSONArray table = new JSONArray();
		DB db = new DB();
		PropertiesReader prop = new PropertiesReader("likes.properties");
		String query1 = prop.getValue("query1");
		String query2 = prop.getValue("query2");
		
		
		if (session != null) {
			Integer id_user = (Integer) session.getAttribute("id_user");
			table = db.executeQuery(query1, media_id, id_user);
			res.put("res0", table.length());
		}
		table = db.executeQuery(query2, media_id);
		res.put("res1", table.length());
		out.print(res.toString());
		db.closeCon();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		JSONObject res = new JSONObject(); 
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DB db = new DB();
		String query = new PropertiesReader("likes.properties").getValue("query3");
		
		if(session == null) {
			res.put("res", "Initiate Session");
			out.print(res.toString());
		} else {
			Integer id_user = (Integer) session.getAttribute("id_user");
			db.execute(query, id_user, reqBody.getInt("media_id"));
			db.closeCon();
		}
	}

}
