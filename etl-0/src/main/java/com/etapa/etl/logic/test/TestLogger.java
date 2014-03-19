package com.etapa.etl.logic.test;

import com.etapa.etl.util.Log;


public class TestLogger {

	public static void main(String[] args) {
		Log.info("Hola mundo!");
		Log.warn("Advertencia!");
		Log.error("Error!");
	}

}
