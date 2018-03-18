package jqp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import utilities.DB;
import utilities.PropertiesReader;

/**
 * Servlet implementation class FileUp
 */
@WebServlet("/FileUp")
public class FileUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Part file = request.getPart("file");
		InputStream filecontent = file.getInputStream();
		OutputStream os = null;
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		DB db = new DB();
		String query = PropertiesReader.getInstance().getValue("vidQuery");
		String baseDir = "c:/test"; //pasarla al properties
		String url = baseDir+ "/" +this.getFileName(file);
		
		try {
			os = new FileOutputStream(url);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = filecontent.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (filecontent != null) {
				filecontent.close();
			}
			if (os != null) {
				os.close();
			}
		}
		db.execute(query, url, reqBody.getString("name") ,this.getFileName(file), reqBody.get("descrip"));
	}
	
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
