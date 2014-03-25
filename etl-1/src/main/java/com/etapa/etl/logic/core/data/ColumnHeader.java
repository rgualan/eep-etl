package com.etapa.etl.logic.core.data;

import com.etapa.etl.logic.core.DictionaryManager;
import com.etapa.etl.persistence.dao.GeneralDao;
import com.etapa.etl.persistence.entity.Fenomeno;
import com.etapa.etl.persistence.entity.FenomenoUnidade;
import com.etapa.etl.persistence.entity.FenomenoUnidadePK;
import com.etapa.etl.persistence.entity.Unidade;

/**
 * 
 * Representa una columna individual de los ficheros de loggernet
 * 
 */
public class ColumnHeader {
	private int index;
	private String name;
	private String unit;
	private String statistic;

	private final String SEP1 = "_"; // Logger net
	private final String SEP2 = "-"; // ETL app

	public ColumnHeader(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public Fenomeno parseFenomeno() throws Exception {
		Fenomeno fen = null;

		String columnName = getName();
		String[] parts = columnName.split(SEP1);
		DictionaryManager dm = new DictionaryManager(); //AQUI ESTOY JEJE
		String auxiliar = dm.getSynonym(parts[0], "3");
		if (!auxiliar.equals(""))
			parts[0] = auxiliar;
		else
			dm.setDictionary(parts[0], "3");
		if (parts.length == 4) {
			String fenId = parts[0];
			String fenSta = parts[2] + SEP2 + parts[3];
			String fenomenoId = fenId + SEP2 + fenSta;

			fen = new Fenomeno();
			fen.setFenId(fenomenoId);
			fen.setFenNombre(fenomenoId);
			fen.setFenTipo(fenSta);

		} else if (parts.length == 3) {
			String fenId = parts[0];
			String fenSta = parts[2];
			String fenomenoId = fenId + SEP2 + fenSta;

			fen = new Fenomeno();
			fen.setFenId(fenomenoId);
			fen.setFenNombre(fenomenoId);
			fen.setFenTipo(fenSta);
		} else if (parts.length == 2) {
			String fenId = parts[0];
			String fenSta = parts[1];

			String fenomenoId = fenId + SEP2 + fenSta;

			fen = new Fenomeno();
			fen.setFenId(fenomenoId);
			fen.setFenNombre(fenomenoId);
			fen.setFenTipo(fenSta);
		}
		else if (parts.length == 1) {
			String fenId = parts[0];
			String fenSta = parts[0];

			String fenomenoId = fenId;

			fen = new Fenomeno();
			fen.setFenId(fenomenoId);
			fen.setFenNombre(fenomenoId);
			fen.setFenTipo(fenSta);
		}
		return fen;
	}

	public Unidade parseUnidad() throws Exception {
		Unidade uni = null;
		uni = GeneralDao.find(Unidade.class, "NA");
		String columnName = getName();
		//try{
		if (!(getUnit().equals("NA") && getStatistic().equals("NA")))
		{
		String[] parts = columnName.split(SEP1);
		
		if (parts.length >= 3) {
			String uniStr = parts[1];
			DictionaryManager dm = new DictionaryManager(); //AQUI ESTOY JEJE
			String auxiliar = dm.getSynonym(parts[1], "4");
			if (!auxiliar.equals(""))
				parts[1] = auxiliar;
			else
				dm.setDictionary(parts[1], "4");
			uni = new Unidade();
			uni.setUniId(uniStr);
			uni.setUniNombre(getUnit());
			uni.setUniDescripcion(getUnit());
			uni.setUniTipo(getStatistic());
		} else if (parts.length == 2 || parts.length == 1 || parts[0].equals("") || parts[0].equals("NA")) {
			// There is not unit
			uni = GeneralDao.find(Unidade.class, "NA");
		}
		}
		//}
		//catch(Exception e)
		//{e.printStackTrace();}
		return uni;
	}

	public FenomenoUnidade parseFenomenoUnidade() throws Exception {
		FenomenoUnidade fenUni = null;

		Fenomeno fen = parseFenomeno();
		Unidade uni = parseUnidad();
		if (fen != null && uni != null) {
			fenUni = new FenomenoUnidade();
			FenomenoUnidadePK pk = new FenomenoUnidadePK();
			pk.setFenId(fen.getFenId());
			pk.setUniId(uni.getUniId());
			fenUni.setId(pk);
			fenUni.setFenomeno(fen);
			fenUni.setUnidade(uni);
		}

		return fenUni;
	}
}
