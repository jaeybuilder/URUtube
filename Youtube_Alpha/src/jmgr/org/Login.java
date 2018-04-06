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
import org.json.JSONException;
import org.json.JSONObject;

import jmgr.org.utilities.DB;
import jmgr.org.utilities.MD5;
import jmgr.org.utilities.PropertiesReader;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MD5 md5;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject res = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONArray table = new JSONArray();
		DB db = new DB();
		String query = new PropertiesReader("log.properties").getValue("query");
		
		try {
			md5 = new MD5(reqBody.getString("pass"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = db.executeQuery(query, reqBody.getString("user"), md5.returnEncrypt());
		if (table.length() == 0) {
			res.put("status", 404).put("res", "User or password invalid");
		} else if (session.isNew()) {
			json = table.getJSONObject(0);
			session.setAttribute("id_user", json.get("id_user"));
			res.put("status", 200).put("res", json.get("username"));
		} else {
			res.put("status", 200).put("res", "Session started");
		}
		db.closeCon();
		out.print(res.toString());
	}
	
}
