package com.etapa.etl.persistence.dao;

import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.Sinonimo;
import com.etapa.etl.persistence.manager.JpaManager;

public class SinonimoDao extends GeneralDao {

	public static Sinonimo queryByNombre(String sinNombre, String type) {
		TypedQuery<Sinonimo> query = JpaManager
				.getEntityManager()
				.createQuery(
						"Select s from Sinonimo s where s.id.sinNombre = :sinNombre AND s.id.dicTipo = :dicTipo",
						Sinonimo.class);
		query.setParameter("sinNombre", sinNombre);
		query.setParameter("dicTipo", type);
		Sinonimo Sinonimo = null;
		try {
			Sinonimo = query.getSingleResult();
		} catch (Exception e) {
		}
		return Sinonimo;	//"Select e from Estacion e JOIN TipoEstacion t where e.id = :id"
		//	"Select e from Estacion e JOIN TipoEstacion t where e.id = :id"
	}
	
}
