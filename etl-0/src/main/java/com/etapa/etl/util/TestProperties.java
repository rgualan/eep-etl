package com.etapa.etl.util;

public class TestProperties {

	public static void main(String[] args) {

		try {
//			String path = PropsUtil.load("config.properties").getProperty(
//					"repository.folder");
			String path = ConfigUtil.getConfigProperties().getProperty(
					"repository.folder");
			Log.getInstance().debug(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.getInstance().error(e);
		}
	}

}
