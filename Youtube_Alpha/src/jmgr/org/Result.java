package jmgr.org;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import jmgr.org.utilities.DB;
import jmgr.org.utilities.PropertiesReader;

/**
 * Servlet implementation class Result
 */
@WebServlet("/Result")
public class Result extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Result() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String param = request.getParameter("search_query");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject res = new JSONObject();
		JSONArray table = new JSONArray();
		DB db = new DB();
		String query = new PropertiesReader("search.properties").getValue("query1");
		
		param = param.replace("?", "%");
		table = db.executeQuery(query, param);
		db.closeCon();
		if (table.length() != 0) {
			for (int i =0; i< table.length(); i++) {
				json = table.getJSONObject(i);
				res.put("res"+i, json);
			}
		} else {
			res.put("res", "null");
		}
		out.print(res.toString());
	}

}
