package com.etapa.etl.logic.core;

import com.etapa.etl.persistence.dao.GeneralDao;
import com.etapa.etl.persistence.dao.SinonimoDao;
import com.etapa.etl.persistence.entity.Diccionario;
import com.etapa.etl.persistence.entity.DiccionarioPK;
import com.etapa.etl.persistence.entity.Sinonimo;
import com.etapa.etl.persistence.entity.TipoEstacion;

public class DictionaryManager {
	public void DictionaryManager()
	{}
	
	public String getSynonym(String word, String type) throws Exception
	{
		String Synonym  ="";
		DiccionarioPK dpk;
		Sinonimo syn = SinonimoDao.queryByNombre(word, type);
		if (syn!=null)
		{
			 dpk=syn.getDiccionario().getId();
			 Synonym = dpk.getDicId();
		}
		
		
		return Synonym;
	}
	
	public void setDictionary(String word, String type) throws Exception
	{
		DiccionarioPK dpk = new DiccionarioPK();
		dpk.setDicId(word);
		dpk.setDicTipo(type);
		if (GeneralDao.find(Diccionario.class, dpk)==null){
		Diccionario dic = new Diccionario();		
		dic.setId(dpk);		
			GeneralDao.insert(dic);
		
		}
	}

}
