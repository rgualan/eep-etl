package com.etapa.etl.logic.core.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Estructura que representa el encabezado de un archivo de loggernet Se compone
 * basicamente de: Un encabezado de estacion Un conjunto de columnas
 * 
 */
public class FileHeader {
	StationHeader station;
	List<ColumnHeader> columns;

	public StationHeader getStation() {
		return station;
	}

	public void setStation(StationHeader station) {
		this.station = station;
	}

	public List<ColumnHeader> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnHeader> columns) {
		this.columns = columns;
	}

	public List<ColumnHeader> getFenomenonColumns() {
		List<ColumnHeader> fcolumns = new ArrayList<ColumnHeader>();
		for (ColumnHeader columnHeader : columns) {
			if (columnHeader.getName().compareToIgnoreCase("TIMESTAMP") == 0
					|| columnHeader.getName().compareToIgnoreCase("RECORD") == 0
						|| columnHeader.getName().compareToIgnoreCase("TS") == 0
							|| columnHeader.getName().compareToIgnoreCase("RN") == 0
							|| columnHeader.getName().compareToIgnoreCase("TMSTAMP") == 0
							|| columnHeader.getName().compareToIgnoreCase("RECNBR") == 0) {
				
				continue;
			}
			fcolumns.add(columnHeader);
		}
		return fcolumns;
	}

}
