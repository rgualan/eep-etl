package com.etapa.etl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ConfigUtil {

	public static String CONFIG_PROPERTIES_FILE_NAME = "config.properties";

	private ConfigUtil() {
	}

	/**
	 * Load a properties file from the classpath
	 * 
	 * @param propsName
	 * @return Properties
	 * @throws Exception
	 */
	public static Properties load(String propsName) throws Exception {

		Properties props = new Properties();

		URL url = ClassLoader.getSystemResource(propsName);

		if (url == null) {
			throw new RuntimeException("Properties resource not found: "
					+ propsName);
		}

		props.load(url.openStream());
		return props;
	}

	/**
	 * Load a Properties File
	 * 
	 * @param propsFile
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties load(File propsFile) throws IOException {
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(propsFile);
		props.load(fis);
		fis.close();
		return props;
	}

	public static Properties getConfigProperties() throws Exception {
		return load(CONFIG_PROPERTIES_FILE_NAME);
	}
	
	public static void main(String [] args){
		try {
			String folder = ConfigUtil.getConfigProperties().getProperty("repository.folder");
			System.out.println(folder);
			System.out.println("Terminado");
		} catch (Exception e) {
			Log.getInstance().error(e);
		}
		
	}
}