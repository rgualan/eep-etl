package com.etapa.etl.logic.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
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
import com.etapa.etl.util.Format;
import com.etapa.etl.util.Log;

/**
 * 
 * Se encarga de procesar un archivo de datos
 * 
 */
public class FileWorker implements Runnable {

	private Archivo fileEntity;
	private String separador;

	private int HEADER_SIZE = 4;
	private static final int RECORD_BUFFER_SIZE = 1000;

	// Tool
	private SimpleDateFormat formatTime = new SimpleDateFormat(Format.TIME);

	public FileWorker(Archivo fileEntity, String separador) {
		this.fileEntity = fileEntity;
		this.separador = separador;
	}

	public void run() {
		Log.getInstance().info("Procesando archivo: " + fileEntity.getPath());
		Log.getInstance().info(
				"Posicion inicial: " + fileEntity.getLastPosition());

		ExecutorService localExecutor = Executors.newFixedThreadPool(Runtime
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
				int i = 0;
				if (fileEntity.getLastPosition() > 0) {
					archivo.seek(fileEntity.getLastPosition() + 2);
					i = 4;
				}
				String line = "";

				List<Observacion> buffer = new ArrayList<Observacion>(
						RECORD_BUFFER_SIZE);

				while ((line = archivo.readLine()) != null) {

					line = line.replaceAll("\"", "");
					String[] campos = line.split(separador);

					if (i >= HEADER_SIZE) {

						List<Observacion> list = parseRecords(header, campos);

						buffer.addAll(list);
						if (buffer.size() >= RECORD_BUFFER_SIZE) {
							localExecutor.execute(new BatchInserter(buffer));
							buffer = new ArrayList<Observacion>(
									RECORD_BUFFER_SIZE);
						}
					}

					i++;
				}

				if (buffer.size() > 0) {
					localExecutor.execute(new BatchInserter(buffer));
					buffer = null;
				}

				localExecutor.shutdown();
				localExecutor.awaitTermination(LoggerNetScanner.LIMITE,
						TimeUnit.MINUTES);

				Log.getInstance().info(
						"Numero de registros procesados: " + (i - HEADER_SIZE));

				// Guardar posicion final
				// long posfin = archivo.length();
				long posfin = archivo.getFilePointer();
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

	private List<Observacion> parseRecords(FileHeader header, String[] campos)
			throws Exception {
		List<Observacion> list = new ArrayList<Observacion>();

		String dateStr = campos[0];
		Date date = null;
		date = formatTime.parse(dateStr);

		Estacion estacion = header.getStation().parseEstacion();

		for (ColumnHeader column : header.getFenomenonColumns()) {
			// Log.getInstance().debug("COLUMN>>>" + column.getName());

			FenomenoUnidade fenUni = column.parseFenomenoUnidade();

			// Log.getInstance().debug("FENUNI>>>" + fenUni.getId().getFenId());
			// Log.getInstance().debug("FENUNI>>>" + fenUni.getId().getUniId());

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

			// GeneralDao.insert(ob);
			list.add(ob);

			// if (buffer.size() < RECORD_BUFFER_SIZE) {
			// buffer.add(ob);
			// } else {
			// Log.getInstance().debug("Insercion en lote: " +
			// RECORD_BUFFER_SIZE + " registros");
			// GeneralDao.bulkInsert(buffer);
			// buffer = new ArrayList<Observacion>(RECORD_BUFFER_SIZE);
			// }
		}
		// if (buffer.size() > 0) {
		// GeneralDao.bulkInsert(buffer);
		// }

		return list;
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

	/*
	 * private int getFileType(String path) throws IOException { int type=0;
	 * BufferedReader br = null; br = new BufferedReader(new FileReader(path));
	 * String line = br.readLine().replaceAll("\"", ""); String[] campos =
	 * line.split(separador);
	 * 
	 * return type; }
	 */

	private FileHeader getFileHeader(String path) throws Exception {
		FileHeader header = null;
		BufferedReader br = null;
		//int typeOfFile = 0;
		
		try {
			br = new BufferedReader(new FileReader(path));
			String line;
			int i = 0;
			int ncolumns = 0;
			header = new FileHeader();

			while ((line = br.readLine()) != null && i < 4) {
				line = line.replaceAll("\"\"", "NA");
				line = line.replaceAll("\"", "");
				String[] campos = line.split(separador);

				// Station
				if (i == 0) {
					// System.out.println("el separador es "+separador +
					// " y el num de campos es "+ campos.length);
					String toa = "";
					String name = "";
					String datalogger = "";
					String dataloggerCode = "";
					String type = "";
					if (campos.length == 8) {
						toa = campos[0];
						name = campos[1];
						datalogger = campos[2];
						dataloggerCode = campos[3];
						type = campos[7];
					}
					if (campos.length == 3) {
						toa = campos[0];
						String[] aux = campos[1].split("G");
						type = aux[0];
						datalogger = "No especificado";
						dataloggerCode = "No especificado";
						name = "G" + aux[1];
					}
					DictionaryManager dm = new DictionaryManager(); // AQUI
																	// ESTOY
																	// JEJE
					String auxiliar = dm.getSynonym(type, "1");
					if (!auxiliar.equals(""))
						type = auxiliar;
					else
						dm.setDictionary(type, "1");
					auxiliar = dm.getSynonym(name, "2");
					if (!auxiliar.equals(""))
						name = auxiliar;
					else
						dm.setDictionary(name, "2");

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
						ncolumns = ncolumns + 1;
					}

					header.setColumns(columns);
				}
				if (!campos[0].contains(":")) {
					// System.out.println("ENTRA ACA POR Q TIENE :");
					if (i == 2) {
						for (int j = 0; j < campos.length; j++) {
							header.getColumns().get(j).setUnit(campos[j]);
							// System.out.println("ENTRA ACA POR Q TIENE :"+campos[j]);
						}
					} else if (i == 3) {
						for (int j = 0; j < campos.length; j++) {
							header.getColumns().get(j).setStatistic(campos[j]);
						}
					}
				} else {
					if (i > 1) {
						// System.out.println("Entro aca "+header.getStation().getName());
						// Unidade uni;
						// uni = GeneralDao.find(Unidade.class, "NA");
						for (int j = 0; j < ncolumns; j++) {
							header.getColumns().get(j).setUnit("NA");
							header.getColumns().get(j).setStatistic("NA");
						}
						this.HEADER_SIZE = 2;
						i = 3;
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
