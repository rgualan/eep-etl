package com.etapa.etl.logic.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.etapa.etl.logic.core.data.ColumnHeader;
import com.etapa.etl.logic.core.data.FileHeader;
import com.etapa.etl.logic.core.data.StationHeader;
import com.etapa.etl.persistence.dao.GeneralDao;
import com.etapa.etl.persistence.entity.Archivo;
import com.etapa.etl.persistence.entity.Estacion;
import com.etapa.etl.persistence.entity.Fenomeno;
import com.etapa.etl.persistence.entity.FenomenoUnidade;
import com.etapa.etl.persistence.entity.Observacion;
import com.etapa.etl.persistence.entity.ObservacionPK;
import com.etapa.etl.persistence.entity.TipoEstacion;
import com.etapa.etl.persistence.entity.Unidade;
import com.etapa.etl.util.Chronometer;
import com.etapa.etl.util.FormatDates;
import com.etapa.etl.util.Log;

public class FileWorker implements Runnable {
	private Archivo fileEntity;
	private String separador;
	
	private static final long LIMITE = 100000000;
	private static final int HEADER_SIZE = 4;

	public FileWorker(Archivo fileEntity, String separador) {
		this.fileEntity = fileEntity;
		this.separador = separador;
	}

	public void run() {
		Log.getInstance().info("Procesando archivo: " + fileEntity.getPath());
		Log.getInstance().info(
				"Posicion inicial: " + fileEntity.getLastPosition());

		ExecutorService executor = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors());

		RandomAccessFile archivo = null;

		try {
			validateFile(fileEntity.getPath());

			Chronometer.startTimer(fileEntity.getPath());

			// Crear el encabezado a partir de las primeras lineas del archivo
			FileHeader header = getFileHeader(fileEntity.getPath());

			// Si es la primera vez que se lee el archivo
			// guardar la informacion de encabezado
			// en las tablas correspondientes
			if (fileEntity.getLastPosition() == 0) {
				saveHeaderInfo(header);
			}

			// Procesar registros de observaciones

			archivo = new RandomAccessFile(fileEntity.getPath(), "r");

			// Hay nuevos cambios que procesar?
			if (archivo.length() == fileEntity.getLastPosition()) {
				Log.getInstance().info(
						"El archivo " + fileEntity.getLastPosition()
								+ "no tiene nuevos registros");
			} else {
				// Guardar nuevos registros

				String line = "";
				int i = 0;

				while ((line = archivo.readLine()) != null) {

					line = line.replaceAll("\"", "");
					String[] campos = line.split(separador);

					if (i >= HEADER_SIZE) {
						saveRecord(header, campos);
					}

					/*
					 * ÇVER SI ACTUALIZA LINEA POR LINEA O TODO DE GOLPE MAYOR
					 * SGURIDAD AQUI MAYOR EFICIENCIA ABAJO posfin =
					 */

					i++;
				}
				
				Log.getInstance().info("Numero de registros procesados: " + (i - HEADER_SIZE));

				executor.shutdown();
				try {
					executor.awaitTermination(LIMITE, TimeUnit.MINUTES);
				} catch (InterruptedException e) {
					Log.getInstance().error(e);
				}

				// Guardar posicion final
				long posfin = archivo.length();
				fileEntity.setLastPosition(posfin);
				GeneralDao.update(fileEntity);
			}

		} catch (FileNotFoundException e) {
			Log.getInstance().error("Archivo no encontrado...", e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			Log.getInstance().error(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				archivo.close();
			} catch (IOException e) {
				Log.getInstance().error(e);
			}

			Chronometer.stopTimer(
					fileEntity.getPath(),
					"Tiempo de procesamiento del archivo "
							+ fileEntity.getPath() + ": ");
		}
	}

	private void saveRecord(FileHeader header, String[] campos)
			throws Exception {
		String dateStr = campos[0];
		Date date = FormatDates.getTimeFormat().parse(dateStr);

		Estacion estacion = header.getStation().parseEstacion();

		for (ColumnHeader column : header.getFenomenonColumns()) {
			//Log.getInstance().debug("COLUMN>>>" + column.getName());
			
			FenomenoUnidade fenUni = column.parseFenomenoUnidade();

			//Log.getInstance().debug("FENUNI>>>" + fenUni.getId().getFenId());
			//Log.getInstance().debug("FENUNI>>>" + fenUni.getId().getUniId());
			
			Observacion ob = new Observacion();
			ObservacionPK pk = new ObservacionPK();
			pk.setObsFecha(date);
			pk.setEstId(estacion.getId().getEstId());
			pk.setTipId(estacion.getId().getTipId());
			pk.setFenId(fenUni.getId().getFenId());
			pk.setUniId(fenUni.getId().getUniId());
			ob.setId(pk);
			ob.setObsValor(campos[column.getIndex()]);

			ob.setEstacion(estacion);
			ob.setFenomenoUnidade(fenUni);

			GeneralDao.insert(ob);
		}
	}

	private void saveHeaderInfo(FileHeader header) throws Exception {
		// Save station type
		TipoEstacion tipoEstacion = header.getStation().parseTipoEstacion();
		GeneralDao.secureInsert(tipoEstacion, tipoEstacion.getTipId());

		// Save station
		Estacion estacion = header.getStation().parseEstacion();
		GeneralDao.secureInsert(estacion, estacion.getId());

		// Save fenomeno - unidad - fenomeno_unidad
		for (ColumnHeader columnHeader : header.getFenomenonColumns()) {
			Fenomeno fen = columnHeader.parseFenomeno();
			if (fen != null) {
				GeneralDao.secureInsert(fen, fen.getFenId());
			}

			Unidade uni = columnHeader.parseUnidad();
			if (uni != null) {
				GeneralDao.secureInsert(uni, uni.getUniId());
			}

			FenomenoUnidade fenUni = columnHeader.parseFenomenoUnidade();
			if (fenUni != null) {
				GeneralDao.secureInsert(fenUni, fenUni.getId());
			}
		}
	}

	private void validateFile(String filePath) throws FileNotFoundException {
		File file = new File(filePath);

		if (!file.exists()) {
			throw new FileNotFoundException(filePath);
		}
	}

	private FileHeader getFileHeader(String path) throws IOException {
		FileHeader header = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));
			String line;
			int i = 0;

			header = new FileHeader();

			while ((line = br.readLine()) != null && i < 4) {
				line = line.replaceAll("\"", "");
				String[] campos = line.split(separador);

				// Station
				if (i == 0) {
					String toa = campos[0];
					String name = campos[1];
					String datalogger = campos[2];
					String dataloggerCode = campos[3];
					String type = campos[7];

					StationHeader stationHeader = new StationHeader();
					stationHeader.setToa(toa);
					stationHeader.setName(name);
					stationHeader.setDataLogger(datalogger);
					stationHeader.setDataLoggerCode(dataloggerCode);
					stationHeader.setType(type);
					header.setStation(stationHeader);
				}
				// Columns
				if (i == 1) {
					List<ColumnHeader> columns = new ArrayList<ColumnHeader>();

					for (int j = 0; j < campos.length; j++) {
						String campo = campos[j];
						ColumnHeader column = new ColumnHeader(campo);
						column.setIndex(j);
						columns.add(column);
					}

					header.setColumns(columns);
				} else if (i == 2) {
					for (int j = 0; j < campos.length; j++) {
						header.getColumns().get(j).setUnit(campos[j]);
					}
				} else if (i == 3) {
					for (int j = 0; j < campos.length; j++) {
						header.getColumns().get(j).setStatistic(campos[j]);
					}
				}

				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return header;
	}

}
