package com.etapa.etl.logic.core;

import com.etapa.etl.persistence.dao.DaoUtil;

public class ingresaObservacion implements Runnable{
	String [] datoss = null;
	DaoUtil daoUtil;
	public ingresaObservacion(String [] datoss)
	{
		this.datoss=datoss;
		daoUtil = new DaoUtil();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		 try {
			daoUtil.ingresaObservacion(datoss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
