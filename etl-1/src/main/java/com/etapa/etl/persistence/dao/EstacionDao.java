package com.etapa.etl.persistence.dao;

import javax.persistence.TypedQuery;

import com.etapa.etl.persistence.entity.Estacion;
import com.etapa.etl.persistence.entity.EstacionPK;
import com.etapa.etl.persistence.manager.JpaManager;

public class EstacionDao extends GeneralDao {

	public static Estacion queryjoinById(String estacionId, String tipoestacionId) {
		EstacionPK id = new EstacionPK();
		id.setEstId(estacionId);
		id.setTipId(tipoestacionId);
		TypedQuery<Estacion> query = JpaManager
				.getEntityManager()
				.createQuery(
						"Select e from Estacion e JOIN TipoEstacion t where e.id = :id",
						Estacion.class);
		query.setParameter("id", id);
		Estacion Estacion = null;
		try {
			Estacion = query.getSingleResult();
		} catch (Exception e) {
		}
		return Estacion;
	}

	public static Estacion queryBytipoestacion(int tipoestacionId) {
		TypedQuery<Estacion> query = JpaManager
				.getEntityManager()
				.createQuery(
						"Select e from Estacion e JOIN TipoEstacion t where t.tipId = :tipoestacionId",
						Estacion.class);
		query.setParameter("tipoestacionId", tipoestacionId);
		Estacion Estacion = null;
		try {
			Estacion = query.getSingleResult();
		} catch (Exception e) {
		}
		return Estacion;
	}
}
