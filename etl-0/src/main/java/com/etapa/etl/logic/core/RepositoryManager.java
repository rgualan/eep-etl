package com.etapa.etl.logic.core;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class RepositoryManager {

	public static List<String> getListOfFilesToProcess(String path) throws Exception {
		File dir = new File(path);
		
		if ( ! dir.exists() ){
			throw new Exception("Repository not found: " + path);
		} 
		
		String[] archivos = dir.list();
		
		List<String> fileNames = Arrays.asList(archivos);
		
		return fileNames;
	}
}
