package jqp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.DB;
import utilities.MD5;
import utilities.PropertiesReader;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MD5 md5;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONObject res = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONArray table = new JSONArray();
		DB db = new DB();
		String selQuery = PropertiesReader.getInstance().getValue("query0");
		String insQuery = PropertiesReader.getInstance().getValue("query1");
				
		table = db.executeQuery(selQuery, reqBody.get("user"), reqBody.get("email"));
		if (table.length() == 0) {
			try {
				md5 = new MD5(reqBody.getString("pass"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.execute(insQuery, reqBody.getString("first"), reqBody.getString("last"), reqBody.getString("user"), md5.returnEncrypt(), reqBody.getString("email"));
			res.put("res", "Register Successful");
		} else {
			res.put("res", "User or email already exist");
		}
		db.closeCon();
		out.print(res.toString());	
	}
}
