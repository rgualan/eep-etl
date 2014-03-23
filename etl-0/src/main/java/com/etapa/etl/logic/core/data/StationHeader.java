package com.etapa.etl.logic.core.data;

import com.etapa.etl.persistence.entity.Estacion;
import com.etapa.etl.persistence.entity.EstacionPK;
import com.etapa.etl.persistence.entity.TipoEstacion;

public class StationHeader {
	String toa;
	String name;
	String dataLogger;
	String dataLoggerCode;
	String type;

	public StationHeader(String toa, String name, String dataLogger, String type) {
		super();
		this.toa = toa;
		this.name = name;
		this.dataLogger = dataLogger;
		this.type = type;
	}

	public StationHeader() {
	}

	public String getToa() {
		return toa;
	}

	public void setToa(String toa) {
		this.toa = toa;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataLogger() {
		return dataLogger;
	}

	public void setDataLogger(String dataLogger) {
		this.dataLogger = dataLogger;
	}

	public String getDataLoggerCode() {
		return dataLoggerCode;
	}

	public void setDataLoggerCode(String dataLoggerCode) {
		this.dataLoggerCode = dataLoggerCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// Parsing / mapping methods

	public TipoEstacion parseTipoEstacion() {
		TipoEstacion tipoEstacion = new TipoEstacion();
		tipoEstacion.setTipId(getType());
		return tipoEstacion;
	}

	public Estacion parseEstacion() {
		TipoEstacion tipoEstacion = parseTipoEstacion();

		Estacion estacion = new Estacion();
		EstacionPK estPk = new EstacionPK();
		estPk.setEstId(getName());
		estPk.setTipId(tipoEstacion.getTipId());
		estacion.setId(estPk);
		estacion.setEstToa(getToa());
		estacion.setEstModelodatalog(getDataLogger());
		estacion.setEstCodigodatalog(getDataLoggerCode());
		estacion.setTipoEstacion(tipoEstacion);

		return estacion;
	}
}
