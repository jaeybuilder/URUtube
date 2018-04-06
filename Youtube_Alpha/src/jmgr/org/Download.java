package jmgr.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer media_id = Integer.parseInt(request.getParameter("media_id"));
        response.setContentType("file");
        JSONObject json = new JSONObject();
		JSONArray table = new JSONArray();
		DB db = new DB();
		String query = new PropertiesReader("video.properties").getValue("query");
		
		table = db.executeQuery(query, media_id);
		db.closeCon();
		json = table.getJSONObject(0);
		
		String media_name = json.getString("media_name");
        response.setHeader("Content-disposition","attachment; filename="+media_name);
        File my_file = new File("C:\\test\\"+media_name);
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
	}
}
