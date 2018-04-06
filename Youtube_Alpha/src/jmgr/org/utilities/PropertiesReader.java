package jmgr.org.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	private Properties prop = new Properties();
	private InputStream input = null;
	
	public PropertiesReader(String file) {
		try {
			String catalina = System.getProperty("catalina.base");
			input = new FileInputStream(catalina + "\\conf\\urutube\\" + file);
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getValue(String key){
		return prop.getProperty(key);
	}
	
}
