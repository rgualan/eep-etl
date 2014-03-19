package com.etapa.etl.util;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.etapa.etl.persistence.entity.Archivo;
import com.etapa.etl.persistence.manager.Persistencia;

public class RecopilarDatos {
	long time_start;
	long time_end;

	private static final long LIMITE = 100000000;

	public void leerdatos() throws Exception {
		Persistencia per = new Persistencia();

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
			for (String archivo : archivos) {

				if (archivo.contains(extensiones)
						&& ((leebackups == false && !archivo.contains("backup")) || (leebackups == true))) {
					Log.info(archivo);

					// Cargar aqui la posicion del archivo de la bd
					try {
						String[] campos = new String[2];
						campos[0] = path + "/" + archivo;
						campos[1] = "0";
						per.ingresaArchivo(campos);

					} catch (Exception e) {

					}
					long pos = 0;
					try {
						Archivo arc = per.consultaArchivo(path + "/" + archivo);
						pos = arc.getArcNbytes();

						// pos=cargarpos(path+"/"+archivo);
					} catch (Exception e) {
						pos = 0;
					}

					Log.info("ESTE ES EL TAM DEL ARCHIVO " + pos);
					exe.execute(new GestorArchivos(pos, path + "/" + archivo,
							separador));
				}

			}

			exe.shutdown();
			try {
				exe.awaitTermination(LIMITE, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				Log.error("Error...", e);
			}
		} else {
			Log.info("No hay archivos en el directorio especificado");
		}

		time_end = System.currentTimeMillis();
		Log.info("Duracion de la tarea: " + (time_end - time_start)
				+ " milliseconds");

	}
}
