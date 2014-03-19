package com.etapa.etl.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.etapa.etl.persistence.entity.TipoEstacion;
import com.etapa.etl.persistence.manager.Persistencia;

public class GestorArchivos implements Runnable {
	Long posini;
	String arch;
	String separador;
	Persistencia per;

	public GestorArchivos(Long posini, String arch, String separador) {
		this.posini = posini;
		this.arch = arch;
		this.separador = separador;
		per = new Persistencia();
	}

	public GestorArchivos() {
		per = new Persistencia();
	}

	RandomAccessFile archivo;
	File dir;

	public String[] leerdirectorio(String path) {
		dir = new File(path);
		String[] archivos = dir.list();
		return archivos;
	}

	public List<String> leerarchivo(Long posinicial, String path) {
		List<String> datos = new ArrayList();

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
				// System.out.println(n); //se muestra en pantalla
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("NO SE ENCONTRO EL ARCHIVO");
			e.printStackTrace();
		}
		try {
			archivo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;

	}

	public void run() {
		// TODO Auto-generated method stub
		try {

			String path = arch;
			System.out.println("INTENTA LLENAR LA BD");
			List<String> lineas = leerarchivo(posini, arch);
			System.out.println("INTENTA LLENAR LA BD2");
			String[] campos;
			Long tam = (long) lineas.size();
			// /****/****/*// guardar esto en la bd
			// guarda la long del archivo
			String est_id = null;
			String tip_id = null;
			String[] fen_id = null;
			String[] uni_id = null;
			System.out.println("ESTE ES EL TAM QUE DEBE LEER + "
					+ lineas.size());
			int i = 0;
			while (i != lineas.size()) {
				campos = lineas.get(i).split(separador);
				if (i == 0) // sacar de la conf el n lineas de la cabecera??
				{

					// tip_nombre = campos[7]; //tip_nombre
					String[] tipo = new String[2];
					tipo[0] = campos[7];
					tipo[1] = campos[7];
					tip_id = campos[7];
					try {
						per.ingresaTipoEstacion(tipo);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// /sacar el id del tipo
					TipoEstacion tipoest = null;
					try {

						tipoest = per.consultaTipoEstacionporNombre(campos[7]);
						System.out.println("ESTA ES EL TIPO = "
								+ tipoest.getTipNombre());

					} catch (Exception e) {
						e.printStackTrace();
					}
					/*
					 * est_id= campos[1]; String tipid =
					 * String.valueOf(tipoest.get(0).getTipId());//lo q saque
					 * //esta va de ley (PK) est_id= campos[1];
					 */
					/*
					 * est_toa= campos[0]; est_id= campos[1]; //este va de ley
					 * (PK) est_modelodatalog = campos[2]; est_codigodatalog =
					 * campos[3]; est_std = campos[4]; est_versionprog=
					 * campos[5]; est_num=campos[6];
					 */
					String[] datoss = new String[8];
					datoss[0] = campos[1];
					datoss[1] = campos[7];
					datoss[2] = campos[0];
					datoss[3] = campos[2];
					datoss[4] = campos[3];
					datoss[5] = campos[4];
					datoss[6] = campos[5];
					datoss[7] = campos[6];
					try {

						per.ingresaEstacioncamposminimos(datoss, tipoest);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if (i == 1) {

					int j = 2;
					fen_id = new String[campos.length];

					while (j != campos.length) {
						fen_id[j] = campos[j]; // este va de ley
						String[] nombre = campos[j].split("_");
						String fennombre = nombre[0];
						String fentipo = "";
						if (nombre.length > 2)
							fentipo = nombre[2];
						else if (nombre.length > 1)
							fentipo = nombre[1];
						String fendescripcion = fennombre;
						String[] datoss = new String[3];
						datoss[0] = fennombre;
						datoss[1] = fennombre;
						datoss[2] = fendescripcion;
						datoss[3] = fentipo;
						try {

							per.ingresaFenomeno(datoss);

						} catch (Exception e) {
							e.printStackTrace();
						}
						j++;
					}

				}
				if (i == 2) {

					uni_id = new String[campos.length];
					String[] datoss = new String[3];
					int j = 2;
					while (j != campos.length) {
						uni_id[j] = campos[j];
						datoss[0] = campos[j];
						datoss[1] = campos[j];
						datoss[2] = campos[j];
						try {

							per.ingresaUnidade(datoss);

						} catch (Exception e) {
							e.printStackTrace();
						}
						datoss = new String[2];
						datoss[0] = uni_id[j];
						datoss[1] = fen_id[j];
						try {

							per.ingresaFenomenoUnidade(datoss);

						} catch (Exception e) {
							e.printStackTrace();
						}
						j++;
					}

				}
				if (i >= 4) {
					int j = 2;
					String fecha = campos[0]; // este va de ley
					String[] datoss = null;
					while (j != campos.length) {
						String obs_valor = campos[j];
						datoss = new String[6];
						datoss[0] = fecha;
						datoss[1] = est_id;
						datoss[2] = tip_id;
						datoss[3] = uni_id[j];
						datoss[4] = fen_id[j];
						datoss[5] = obs_valor;
						// guardaobservacion(est_id,tip_id,uni_id,fen_id,obs_valor,0);
						// //0 indica q es el dato crudo
						try {

							per.ingresaObservacion(datoss);

						} catch (Exception e) {
							e.printStackTrace();
						}
						j++;
					}
				}
				i++;
			}
			try {// //AQUI DEBE SER LO DEL ARCHIVO
					// crearchivo(path,tam); //insert si es la primera vez q lee
					// el
					// archivo
			} catch (Exception e) {
				// guardatamarchivo(path,tam); //alter si ya existe la tabla tam
				// es el tam q se ha leido.

			}

		} finally {
			System.out.println("termino de leer archivo " + arch);
			// per.cerrarpersistencia();
		}
	}
}
