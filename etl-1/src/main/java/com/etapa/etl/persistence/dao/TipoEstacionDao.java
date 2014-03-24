package com.etapa.etl.persistence.dao;

import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.TipoEstacion;
import com.etapa.etl.persistence.manager.JpaManager;

public class TipoEstacionDao extends GeneralDao {

	public static TipoEstacion queryByNombre(String tipNombre) {
		TypedQuery<TipoEstacion> query = JpaManager
				.getEntityManager()
				.createQuery(
						"Select t from TipoEstacion t where t.tipNombre = :tipNombre",
						TipoEstacion.class);
		query.setParameter("tipNombre", tipNombre);
		TipoEstacion TipoEstacion = null;
		try {
			TipoEstacion = query.getSingleResult();
		} catch (Exception e) {
		}
		return TipoEstacion;
	}
}
