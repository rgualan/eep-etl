package com.etapa.etl.logic.core;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.etapa.etl.persistence.dao.DaoUtil;
import com.etapa.etl.persistence.entity.TipoEstacion;
import com.etapa.etl.util.Log;

public class GestorArchivos implements Runnable {
	Long posini;
	String arch;
	String separador;
	DaoUtil daoUtil;
	private static final long LIMITE = 100000000;
	public GestorArchivos(Long posini, String arch, String separador) {
		this.posini = posini;
		this.arch = arch;
		this.separador = separador;
		daoUtil = new DaoUtil();
	}

	public GestorArchivos() {
		daoUtil = new DaoUtil();
	}

	RandomAccessFile archivo;
	File dir;

	public String[] leerdirectorio(String path) {
		dir = new File(path);
		String[] archivos = dir.list();
		return archivos;
	}

	public List<String> leerArchivo(Long posinicial, String path) {
		List<String> datos = new ArrayList<String>();

		try {
			archivo = new RandomAccessFile(path, "r");
			if (!(archivo.length() == posinicial)) {
				archivo.seek(posinicial); // la posicion del archivo
				String n = "";
				while (n != null) {
					// se lee un entero del fichero
					n = archivo.readLine();
					if (n != null)
						datos.add(n);
				}
				// Log.getInstance().info(n); //se muestra en pantalla
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.getInstance().info("NO SE ENCONTRO EL ARCHIVO");
			Log.getInstance().error(e);
		} finally {
			try {
				archivo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.getInstance().error(e);
			}

		}

		return datos;

	}

	public void run() {

		try {
			long posfin = posini;
			Log.getInstance().info("INTENTA LLENAR LA BD");
			String[] campos;			
			String est_id = null;
			String tip_id = null;
			String[] fen_id = null;
			String[] uni_id = null;			
			int i = 0;			
			//////*******///////			
			try {
				ExecutorService exe = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				archivo = new RandomAccessFile(arch, "r");
				if (!(archivo.length() == posini)) {
					archivo.seek(0); // la posicion del archivo
					String n = "";
					while (n != null) {
						// se lee un entero del fichero
					if (i==4 && posini>0)
							archivo.seek(posini+2);					
					n = archivo.readLine();
					
						if (n != null)
						{

							n=n.replaceAll("\"", "");
							campos = n.split(separador);
							
							if (i == 0) // sacar de la conf el n lineas de la cabecera??
							{

								// tip_nombre = campos[7]; //tip_nombre
								String[] aTipo = new String[2];
								aTipo[0] = campos[7];
								aTipo[1] = campos[7];
								

								TipoEstacion tipoest = daoUtil.buscaoIngresaTipoEstacion(aTipo);
								tip_id = tipoest.getTipId();
								
								String[] datoss = new String[8];
								datoss[0] = campos[1];
								datoss[1] = String.valueOf(tip_id);
								datoss[2] = campos[0];
								datoss[3] = campos[2];
								datoss[4] = campos[3];
								datoss[5] = campos[4];
								datoss[6] = campos[5];
								datoss[7] = campos[6];
								
								daoUtil.ingresaEstacioncamposminimos(datoss, tipoest);
								est_id = campos[1];

							}
							if (i == 1) {

								int j = 2;
								fen_id = new String[campos.length-2];

								while (j != campos.length) {
									fen_id[j-2] = campos[j]; // este va de ley
									String[] nombre = campos[j].split("_");
									String fennombre = nombre[0];
									String fentipo = "";
									if (nombre.length > 2)
										fentipo = nombre[2];
									else if (nombre.length > 1)
										fentipo = nombre[1];
									String fendescripcion = fennombre;
									String[] datoss = new String[4];
									datoss[0] = fen_id[j-2];
									datoss[1] = fennombre;
									datoss[2] = fendescripcion;
									datoss[3] = fentipo;

									daoUtil.ingresaFenomeno(datoss);

									j++;
								}

							}
							if (i == 2) {

								uni_id = new String[campos.length-2];
								String[] datoss = new String[4];
								int j = 2;
								while (j != campos.length) {
									uni_id[j-2] = campos[j];
									datoss[0] = campos[j];
									datoss[1] = campos[j];
									datoss[2] = campos[j];
									datoss[3] = campos[j];
									
									daoUtil.ingresaUnidade(datoss);

									datoss = new String[5];
									datoss[0] = uni_id[j-2];
									datoss[1] = fen_id[j-2];
									datoss[2] =""; 
									datoss[3] = "";
									datoss[4] = "";
									daoUtil.ingresaFenomenoUnidade(datoss);

									j++;
								}

							}
							if (i >= 4 ) {
								int j = 2;
								String fecha = campos[0]; // este va de ley
								String[] datoss = null;
								while (j != campos.length) {
									String obs_valor = campos[j];
									datoss = new String[6];
									datoss[0] = fecha;
									datoss[1] = est_id;
									datoss[2] = String.valueOf(tip_id);
									datoss[3] = fen_id[j-2];
									datoss[4] = uni_id[j-2];						
									datoss[5] = obs_valor;
									// guardaobservacion(est_id,tip_id,uni_id,fen_id,obs_valor,0);
									// //0 indica q es el dato crudo
									
									exe.execute(new ingresaObservacion(datoss));
									//daoUtil.ingresaObservacion(datoss);

									j++;
								}
							}
						
							/* ÇVER SI ACTUALIZA LINEA POR LINEA O TODO DE GOLPE
							 * MAYOR SGURIDAD AQUI 
							 * MAYOR EFICIENCIA ABAJO
							 * posfin = archivo.getFilePointer();
								campos = new String[2];
								campos[0] = arch;
								campos[1]=String.valueOf(posfin);
								daoUtil.actualizaArchivo(campos);
							*/
						}
						i++;
						
					//		
					}
					
					exe.shutdown();
					try {
						exe.awaitTermination(LIMITE, TimeUnit.MINUTES);
					} catch (InterruptedException e) {
						Log.getInstance().error(e);
					}
					posfin = archivo.length();
					campos = new String[2];
					campos[0] = arch;
					campos[1]=String.valueOf(posfin);
					daoUtil.actualizaArchivo(campos);
					System.out.println("LA POSICION FINAL DEL ARCHIVO "+arch +" ES DE "+posfin+" bytes");
					
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.getInstance().info("NO SE ENCONTRO EL ARCHIVO");
				Log.getInstance().error(e);
				e.printStackTrace();
			} finally {
				try {
					archivo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.getInstance().error(e);
				}

			}
						
			Log.getInstance().info("Se terminó de leer el archivo: " + arch);

		} catch (Exception e) {
		e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
