package com.etapa.etl.util;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.etapa.etl.persistence.entity.Archivo;


public class recopilar_datos {
	long time_start;
	long time_end;
	
	public void leerdatos()
	{
		persistencia per = new persistencia();
		
		time_start = System.currentTimeMillis();
 gestor_archivos pr = new gestor_archivos();
 /////****VARIABLES DE LA CONFIGURACION****/////
	String path = "/Users/francisco/datos"; // variable de la conf q define el directorio
	String extensiones= ".dat"; //extension o extensiones definidas en la conf
	String separador =",";	//hacer un primer sondeo la primera lectura del archivo o definirlo en un archivo de configuracion??
	boolean leebackups = false; //indica si leer o no los archivos de backups
	////*********/////
	String [] archivos = pr.leerdirectorio(path);
	if (archivos == null)
		  System.out.println("No hay archivos en el directorio especificado");
		else { 			
			 ExecutorService exe = Executors.newSingleThreadScheduledExecutor();
		  for (int x=0;x<archivos.length;x++)
		  {
			  
			  if (archivos[x].contains(extensiones) && ((leebackups==false && !archivos[x].contains("backup")) || (leebackups==true)))
		   {
				System.out.println(archivos[x]);
		   //********cargar aqui la posicion del archivo de la bd******///
				try
				{
					String [] campos = new String[2];
					campos[0]=path+"/"+archivos[x];
					campos[1]="0";
					per.cargarPersistencia();
					per.ingresaArchivo(campos);
					per.cerrarpersistencia();
				}
				catch(Exception e)
				{
					
				}
				long pos =0;
				try
				{
					per.cargarPersistencia();
					List<Archivo> arc = per.consultaArchivo(path+"/"+archivos[x]);
					 pos=arc.get(0).getArcNbytes();
					 per.cerrarpersistencia();
					//pos=cargarpos(path+"/"+archivos[x]);					
				}
				catch(Exception e)
				{
					pos=0;
				}
				
				System.out.println("ESTE ES EL TAM DEL ARCHIVO "+ pos);
		   exe.execute(new gestor_archivos(pos, path+"/"+archivos[x],separador));
		   }
		    
		  }
		  
			exe.shutdown();
			try {
				exe.awaitTermination(100000000, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		  
		}
	
	 time_end = System.currentTimeMillis();
     System.out.println("the task has taken "+ ( time_end - time_start ) +" milliseconds");
     
	}
}
