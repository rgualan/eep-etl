package com.etapa.etl.util;

public final class Format {

	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_PRESENTATION = "dd-MM-yyyy";

	public static final String TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_PRESENTATION = "dd-MM-yyyy HH:mm:ss";

	public static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String TIMESTAMP_PRESENTATION = "dd-MM-yyyy HH:mm:ss.SSS";

	public static final String EXPIRED_TIME = "9999-12-31 00:00:00";

	public static final String HOUR = "HH:mm:ss";
	public static final String MINUTE = "mm:ss.SSS";

	public static final String CURRENCY = "#,##0.00";
	public static final String DECIMAL = "#,###,##0.00";
	public static final String INTEGER = "#";

	public static final String FILE = "yyyyMMdd-HHmmss";

	public static final byte[] GWT_DES_KEY = "[B@10b7f7$6Ya*uJBh15df)d"
			.getBytes();

}