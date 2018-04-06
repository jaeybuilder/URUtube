package jmgr.org;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import jmgr.org.utilities.DB;
import jmgr.org.utilities.PropertiesReader;

/**
 * Servlet implementation class Video
 */
@WebServlet("/Stream")
public class Stream extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Stream() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer media_id = Integer.parseInt(request.getParameter("media_id"));
		ServletOutputStream out = response.getOutputStream();
		JSONObject json = new JSONObject();
		JSONArray table = new JSONArray();
		DB db = new DB();
		String query = new PropertiesReader("video.properties").getValue("query");
		
		table = db.executeQuery(query, media_id);
		db.closeCon();
		json = table.getJSONObject(0);
		
		InputStream in = new FileInputStream (json.getString("media_url"));
		String mimeType = "video/mp4";		
		byte[] bytes = new byte[1024];
		int bytesRead;
		response.setContentType(mimeType);
		
		while ((bytesRead = in.read(bytes)) != -1) {
		    out.write(bytes, 0, bytesRead);
		}
		in.close();
		out.close();
	}

}
