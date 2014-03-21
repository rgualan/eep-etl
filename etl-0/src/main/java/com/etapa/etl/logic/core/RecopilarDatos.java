package com.etapa.etl.logic.core;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.etapa.etl.persistence.dao.DaoUtil;
import com.etapa.etl.persistence.entity.Archivo;
import com.etapa.etl.util.ConfigUtil;
import com.etapa.etl.util.Log;

public class RecopilarDatos {
	long time_start;
	long time_end;

	private static final long LIMITE = 100000000;

	public void leerdatos() throws Exception {
		DaoUtil per = new DaoUtil();

		time_start = System.currentTimeMillis();
		GestorArchivos pr = new GestorArchivos();

		// Cargar configuracion
		Properties properties = ConfigUtil.getConfigProperties();
		String path = properties.getProperty("repositorio");
		String extensiones = properties.getProperty("extensiones");
		String separador = properties.getProperty("separador");
		boolean leebackups = Boolean.parseBoolean(properties
				.getProperty("leebackups"));

		// Archivos a procesar
		String[] archivos = pr.leerdirectorio(path);

		// Procesamiento
		if (archivos != null) {
			ExecutorService exe = Executors.newSingleThreadScheduledExecutor();
		//	ExecutorService exe = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

			for (String archivo : archivos) {

				if (archivo.contains(extensiones)
						&& ((leebackups == false && !archivo.contains("backup")) || (leebackups == true))) {
					Log.getInstance().info(archivo);

					// Cargar aqui la posicion del archivo de la bd

					String[] campos = new String[2];
					campos[0] = path + "/" + archivo;
					campos[1] = "0";
					per.ingresaArchivo(campos);

					long pos = 0;
					try {
						Archivo arc = per.consultaArchivo(path + "/" + archivo);
						pos = (long) arc.getArcNbytes();

						// pos=cargarpos(path+"/"+archivo);
					} catch (Exception e) {
						pos = 0;
					}

					Log.getInstance().info("ESTE ES EL TAM DEL ARCHIVO " + pos);
					exe.execute(new GestorArchivos(pos, path + "/" + archivo,
							separador));
				}

			}

			exe.shutdown();
			try {
				exe.awaitTermination(LIMITE, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				Log.getInstance().error(e);
			}
		} else {
			Log.getInstance().info(
					"No hay archivos en el directorio especificado");
		}

		time_end = System.currentTimeMillis();
		Log.getInstance().info(
				"Duracion de la tarea: " + (time_end - time_start)
						+ " milliseconds");

	}
}
