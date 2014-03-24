package com.etapa.etl.logic.core;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.etapa.etl.persistence.dao.GeneralDao;
import com.etapa.etl.persistence.entity.Archivo;
import com.etapa.etl.util.Chronometer;
import com.etapa.etl.util.ConfigUtil;

/**
 * 
 * Engloba el proceso de escaneo de un repositorio de datos de loggernet
 * 
 */
public class LoggerNetScanner {

	private static String path;
	private static String extensiones;
	private static String separador;
	private static boolean leebackups;

	public static final long LIMITE = 1000;

	public static void scan() throws Exception {
		Chronometer.startTimer("dataLoader");

		loadSettings();

		// Archivos a procesar
		List<String> archivos = RepositoryManager.getListOfFilesToProcess(path);

		// Procesamiento
		if (archivos != null && archivos.size() > 0) {
			ExecutorService exe = Executors.newSingleThreadScheduledExecutor();
			// ExecutorService exe = Executors.newFixedThreadPool(Runtime
			// .getRuntime().availableProcessors());

			for (String archivo : archivos) {

				if (archivo.endsWith(extensiones)
						&& ((leebackups == false && !archivo.contains("backup")) || (leebackups == true))) {

					// Cargar aqui la posicion del archivo de la bd

					String filePath = path + "/" + archivo;
					Long position = 0l;
					Archivo fileEntity =GeneralDao.find(Archivo.class, filePath);
					if (fileEntity==null)
					{ fileEntity = new Archivo();
					fileEntity.setPath(filePath);
					fileEntity.setLastPosition(position);
					GeneralDao.secureInsert(fileEntity, filePath);
					}

					exe.execute(new FileWorker(fileEntity, separador));
				}

			}

			exe.shutdown();
			exe.awaitTermination(LIMITE, TimeUnit.MINUTES);
		} else {
			throw new Exception("No hay archivos en el directorio especificado");
		}

		Chronometer.stopTimer("dataLoader",
				"Duracion de la carga de archivos: ");
	}

	private static void loadSettings() throws Exception {
		Properties properties = ConfigUtil.getConfigProperties();

		path = properties.getProperty("repositorio");
		extensiones = properties.getProperty("extensiones");
		separador = properties.getProperty("separador");
		leebackups = Boolean.parseBoolean(properties.getProperty("leebackups"));

	}
}
