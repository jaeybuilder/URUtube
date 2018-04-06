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
 * Servlet implementation class Comment
 */
@WebServlet("/Comment")
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() {
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
		JSONObject res = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray table = new JSONArray();
		DB db = new DB();
		PropertiesReader prop = new PropertiesReader("comments.properties");
		String query = prop.getValue("query1");
		
		table = db.executeQuery(query, media_id);
		if (table.length() > 0) {
			for (int i =0; i< table.length(); i++) {
				json = table.getJSONObject(i);
				res.put("comment"+i, json);
			}
		} else {
			res.put("comment", table.length());
		}
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
		String query = new PropertiesReader("comments.properties").getValue("query2");
		
		if (session == null) {
			res.put("res", "Initiate Session");
			out.print(res.toString());
		} else {
			Integer id_user = (Integer) session.getAttribute("id_user");
			System.out.println(reqBody.getInt("media_id")+ id_user+ reqBody.getString("comment_text"));
			db.execute(query, reqBody.getInt("media_id"), id_user, reqBody.getString("comment_text"));
			db.closeCon();
		}
	}

}
