package com.etapa.etl.logic.core;

import java.util.ArrayList;
import java.util.List;

import com.etapa.etl.persistence.dao.GeneralDao;
import com.etapa.etl.persistence.entity.Observacion;
import com.etapa.etl.util.Chronometer;

public class BatchInserter implements Runnable {

	private List<Observacion> data;

	public BatchInserter(List<Observacion> data) {
		this.data = new ArrayList<Observacion>(data);
	}

	@Override
	public void run() {
		try {
//			Chronometer.startTimer("" + Thread.currentThread().getName());
			GeneralDao.bulkInsert(data);
//			Chronometer.stopTimer("" + Thread.currentThread().getName(), "Duración insercion en lote: "); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
