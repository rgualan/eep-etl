package com.etapa.etl.logic.test;

import com.etapa.etl.util.Log;


public class TestLogger {

	public static void main(String[] args) {
		Log.getInstance().info("Hola mundo!");
		Log.getInstance().warn("Advertencia!");
		Log.getInstance().error("Error!");
	}

}
