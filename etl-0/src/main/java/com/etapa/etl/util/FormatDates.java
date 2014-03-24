package com.etapa.etl.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Clase que entrega los distintos formatos de fechas.
 * 
 */
public final class FormatDates {

	private static SimpleDateFormat formatDate = new SimpleDateFormat(
			Format.DATE);
	private static SimpleDateFormat formatTime = new SimpleDateFormat(
			Format.TIME);
	private static SimpleDateFormat formatTimestamp = new SimpleDateFormat(
			Format.TIMESTAMP);
	private static SimpleDateFormat formatHour = new SimpleDateFormat(
			Format.HOUR);
	private static SimpleDateFormat formatMinute = new SimpleDateFormat(
			Format.MINUTE);
	private static NumberFormat formatCurrency = new DecimalFormat(
			Format.CURRENCY);
	private static SimpleDateFormat formatFile = new SimpleDateFormat(
			Format.FILE);

	private FormatDates() {
	}

	public static SimpleDateFormat getDateFormat() {
		return formatDate;
	}

	public static synchronized SimpleDateFormat getTimeFormat() {
		return formatTime;
	}

	public static SimpleDateFormat getTimestampFormat() {
		return formatTimestamp;
	}

	public static SimpleDateFormat getHourFormat() {
		return formatHour;
	}

	public static SimpleDateFormat getMinuteFormat() {
		return formatMinute;
	}

	public static NumberFormat getCurrencyFormat() {
		return formatCurrency;
	}

	public static SimpleDateFormat getFileFormat() {
		return formatFile;
	}
}