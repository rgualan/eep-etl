package com.etapa.etl.util;

import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

/**
 * Manage Log configuration
 */
public final class Log {

	private final String LOG_NAME = "etapa-etl";

	public static Logger INSTANCE = null;

	private Log() {
		try {
			URL url = Loader.getResource("log4j.properties");
			PropertyConfigurator.configure(url);
			INSTANCE = Logger.getLogger(LOG_NAME);
		} catch (Exception e) {
			BasicConfigurator.configure();
			INSTANCE = Logger.getLogger(LOG_NAME);
		}
	}

	public synchronized static Logger getInstance() {
		if (INSTANCE == null) {
			new Log();
		}
		return INSTANCE;
	}
}