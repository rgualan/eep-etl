package com.etapa.etl.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chronometer {

	private static Map<String, Long> data = new HashMap<String, Long>();

	private Chronometer() {

	}

	public synchronized static void startTimer(String processName) {
		// Save info
		data.put(processName, System.currentTimeMillis());
	}

	public synchronized static void stopTimer(String processName, String message) {
		if ( ! data.containsKey(processName) ){
			Log.getInstance().error("Timer " + processName + " not found!");
			return;
		}
		
		Long end = System.currentTimeMillis();
		Long start = data.get(processName);
		Date deltaDate = new Date(end - start);
		
		Log.getInstance().info(message + FormatDates.getMinuteFormat().format(deltaDate));
	}
}
