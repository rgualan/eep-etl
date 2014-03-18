package com.etapa.etl.logic.test;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.etapa.etl.persistence.entity.Archivo;
import com.etapa.etl.persistence.entity.TipoEstacion;
import com.etapa.etl.util.persistencia;
import com.etapa.etl.util.recopilar_datos;



public class App {
	

	public static void main(String[] args) {
	

		System.out.println("Hola mundo!!!");
		recopilar_datos rd = new recopilar_datos();
		rd.leerdatos();
		//recopilar_datos rd = new recopilar_datos();
	//	rd.leerdatos();
	
	/*	String tabla = "Archivo";
		String [] campos = new String [8];
		campos [0]="pruebitaestacion";
		
		/////per.consultas(tabla);
		//per.ingresos();
	//	per.consultas(tabla,"12");
		//per.ingresaTipoEstacion(campos);
	
		campos[1]="pruebitaestacion";
		campos[2]="pruebastd";
		campos[3]="modelo";
		campos[4]="codigo";
		campos[5]="std";
		campos[6]="version";
		campos[7]="num";
		per.ingresaEstacioncamposminimos(campos);
		*/
		/*
		persistencia per = new persistencia();
		per.cargarPersistencia();
		per.consultaEstacionjoin("pruebitaestacion", 1);
		per.cerrarpersistencia();*/
		
	    
		//cargarPersistencia();

		// Probar la persistencia
	}

	
}
