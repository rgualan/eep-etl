package com.etapa.etl.logic.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.etapa.etl.util.ConfigUtil;
import com.etapa.etl.util.Log;

/**
 * 
 * Responsable de las operaciones a nivel de repositorio
 * 
 */
public class RepositoryManager {

	public static String path;
	public static String extensiones;
	public static boolean leebackups;

	static {
		Properties properties;
		try {
			properties = ConfigUtil.getConfigProperties();
			path = properties.getProperty("repositorio");
			extensiones = properties.getProperty("extensiones");
			leebackups = Boolean.parseBoolean(properties
					.getProperty("leebackups"));
		} catch (Exception e) {
			Log.getInstance().error(e);
			e.printStackTrace();
		}
	}

	public static List<String> getListOfFilesToProcess(String path)
			throws Exception {
		File dir = new File(path);

		if (!dir.exists()) {
			throw new Exception("Repository not found: " + path);
		}

		String[] archivos = dir.list();

		List<String> fileNames = Arrays.asList(archivos);

		return fileNames;
	}

	public static List<String> getDatFiles() throws Exception {
		List<String> datFiles = new ArrayList<String>();

		File dir = new File(path);

		if (!dir.exists()) {
			throw new Exception("Repository not found: " + path);
		}

		String[] archivos = dir.list();

		for (String archivo : archivos) {
			if (archivo.endsWith(extensiones)
					&& ((leebackups == false && !archivo.contains("backup")) || (leebackups == true))) {
				datFiles.add(archivo);
			}
		}

		return datFiles;
	}
}
