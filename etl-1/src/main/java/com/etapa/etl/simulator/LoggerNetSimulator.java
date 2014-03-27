package com.etapa.etl.simulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.etapa.etl.logic.core.RepositoryManager;
import com.etapa.etl.util.FormatDates;
import com.etapa.etl.util.Log;

public class LoggerNetSimulator {

	final static int NEW_LINES = 10;

	final static String NEW_LINE = "\r\n";

	public static void main(String[] args) throws Exception {
		simulateDataTransmissionForRepository();
	}

	private static void simulateDataTransmissionForRepository()
			throws Exception {
		// String file1 = "C:/Datos/Chirimachay_Prec.dat";
		// simulateDataTransmission(file1);

		List<String> archivos = RepositoryManager.getDatFiles();

		// Procesamiento
		for (String archivo : archivos) {
			simulateDataTransmission(RepositoryManager.path + "/" + archivo);
		}

	}

	private static void simulateDataTransmission(String filePath) {
		Log.getInstance().info("Simulando transmision de datos para: " + filePath);
		
		File file = new File(filePath);

		String lastLine = tail(file, 1);
		Log.getInstance().info("Last line: \n" + lastLine);

		appendRegisters(file, lastLine);

	}

	private static void appendRegisters(File file, String lastLine) {
		String[] campos = lastLine.split(",");

		StringBuilder newLinesSb = generateNewLines(campos);

		Log.getInstance().info("Generated lines:" + newLinesSb);

		Writer output = null;
		try {
			output = new BufferedWriter(new FileWriter(file, true));
			output.append(newLinesSb);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private static StringBuilder generateNewLines(String[] campos) {
		StringBuilder sb = new StringBuilder();

		sb.append(NEW_LINE);

		for (int i = 0; i < NEW_LINES; i++) {
			for (int j = 0; j < campos.length; j++) {
				String campo = campos[j];

				// Log.getInstance().info("Campo siendo procesado: " + campo);

				// Campo tipo fecha o campo tipo texto
				if (campo.startsWith("\"")) {
					String campo2 = campo.replaceAll("\"", "");
					if (campo2
							.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}")) {
						// Tipo Fecha
						try {
							SimpleDateFormat sdf = FormatDates.getTimeFormat();
							Date date = sdf.parse(campo2);
							Date nexDate = new Date(date.getTime() + 5 * 60000);

							String nuevoCampo = addDelimiters(sdf
									.format(nexDate));
							campos[j] = nuevoCampo;
							sb.append(nuevoCampo);
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						// Tipo texto
						String nuevoCampo = addDelimiters("Cualquier cosa");
						campos[j] = nuevoCampo;
						sb.append(nuevoCampo);
					}
				} else {
					// Campo tipo numerico
					if (campo.indexOf(".") > 0) {
						// Decimal
						try {
							DecimalFormat df = new DecimalFormat("##0.00",
									DecimalFormatSymbols
											.getInstance(Locale.ENGLISH));
							double value;
							value = df.parse(campo).doubleValue();
							double nextValue = value
									+ (new Random()).nextDouble();
							String nuevoCampo = df.format(nextValue);
							campos[j] = nuevoCampo;
							sb.append(nuevoCampo);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						// Entero
						int value = Integer.parseInt(campo);
						int nextValue = value + 1;
						String nuevoCampo = "" + nextValue;
						campos[j] = nuevoCampo;
						sb.append(nuevoCampo);
					}

				}

				if (j < campos.length - 1) {
					sb.append(",");
				} else if (i < NEW_LINES - 1) {
					sb.append(NEW_LINE);
				}
			}
		}

		return sb;
	}

	private static String addDelimiters(String text) {
		return "\"" + text + "\"";
	}

	private static String tail(File file, int lines) {
		java.io.RandomAccessFile fileHandler = null;
		try {
			fileHandler = new java.io.RandomAccessFile(file, "r");
			long fileLength = fileHandler.length() - 2;
			StringBuilder sb = new StringBuilder();
			int line = 0;
			byte[] buffer = new byte[2];

			// System.out.format("%h-%c-%n", buffer[0], (char) buffer[0]);
			// System.out.format("%h-%c-%n", buffer[1], (char) buffer[1]);

			for (long filePointer = fileLength; filePointer != -1; filePointer--) {
				fileHandler.seek(filePointer);
				fileHandler.read(buffer);

				// System.out.format("%h-%c-, %h-%c-%n", buffer[0], buffer[0],
				// buffer[1], buffer[1]);

				if (buffer[0] == 0xD && buffer[1] == 0xA) {
					line = line + 1;
					if (line == lines) {
						if (filePointer == fileLength) {
							continue;
						}
						break;
					}
				}

				sb.append((char) buffer[1]);
			}

			String lastLine = sb.reverse().toString();
			return lastLine;
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fileHandler != null)
				try {
					fileHandler.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

}
