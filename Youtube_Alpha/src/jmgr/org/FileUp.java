package jmgr.org;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import jmgr.org.utilities.DB;
import jmgr.org.utilities.PropertiesReader;

/**
 * Servlet implementation class FileUp
 */
@MultipartConfig
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
		String media_name = request.getParameter("name");
		String media_descrip = request.getParameter("descrip");
		InputStream filecontent = file.getInputStream();
		OutputStream os = null;
		DB db = new DB();
		PropertiesReader prop = new PropertiesReader("fileup.properties");
		String url = prop.getValue("baseDir") + "/" + this.getFileName(file);

		try {
			os = new FileOutputStream(url);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = filecontent.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			db.execute(prop.getValue("query"), url, media_name, getFileName(file), media_descrip);
			db.closeCon();
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
