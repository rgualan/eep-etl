package com.etapa.etl.persistence.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.etapa.etl.persistence.entity.Archivo;
import com.etapa.etl.persistence.entity.Estacion;
import com.etapa.etl.persistence.entity.EstacionPK;
import com.etapa.etl.persistence.entity.Fenomeno;
import com.etapa.etl.persistence.entity.FenomenoUnidade;
import com.etapa.etl.persistence.entity.FenomenoUnidadePK;
import com.etapa.etl.persistence.entity.Observacion;
import com.etapa.etl.persistence.entity.ObservacionPK;
import com.etapa.etl.persistence.entity.TipoEstacion;
import com.etapa.etl.persistence.entity.Unidade;
import com.etapa.etl.persistence.manager.JpaManager;
import com.etapa.etl.util.Log;

public class DaoUtil {

	public DaoUtil() {
	}

	public Archivo consultaArchivo(String id) throws Exception {
		return JpaManager.find(Archivo.class, id);

	}

	public TipoEstacion consultaTipoEstacion(String id) throws Exception {
		return JpaManager.find(TipoEstacion.class, id);
	}

	public TipoEstacion consultaTipoEstacionporNombre(String nombre) {
		TipoEstacion tmpArc = TipoEstacionDao.queryByNombre(nombre);

		return tmpArc;
	}

	public Estacion consultaEstacion(String estid, String tipoid)
			throws Exception {

		EstacionPK estacionPk = new EstacionPK();
		estacionPk.setEstId(estid);
		estacionPk.setTipId(tipoid);

		return GeneralDao.find(Estacion.class, estacionPk);

	}

	public Fenomeno consultaFenomeno(String id) throws Exception {
		return GeneralDao.find(Fenomeno.class, id);

	}

	public Observacion consultaObservacion(String fecha, String estid,
			String tipid, String uniid, String fenid) throws Exception {

		ObservacionPK pk = new ObservacionPK();
		pk.setObsFecha(new java.util.Date(fecha)); // TODO usar un metodo de
													// parsing de fecha no
													// deprecado
		pk.setEstId(estid);
		pk.setTipId(tipid);
		pk.setUniId(uniid);
		pk.setFenId(fenid);

		Observacion obs = GeneralDao.find(Observacion.class, pk);

		return obs;

	}

	public void ingresaArchivo(String campos[]) throws Exception {
		Archivo newEntity = GeneralDao.find(Archivo.class, campos[0]);
		if (newEntity == null) {
			newEntity = new Archivo();
			newEntity.setPath(campos[0]);
			newEntity.setLastPosition(Long.valueOf(campos[1]));
			GeneralDao.insert(newEntity);
		}
	}

	public void actualizaArchivo(String campos[]) throws Exception {
		Archivo newEntity = GeneralDao.find(Archivo.class, campos[0]);
		if (newEntity != null) {
			newEntity.setLastPosition(Long.valueOf(campos[1]));
			GeneralDao.update(newEntity);
		}

	}

	public TipoEstacion buscaoIngresaTipoEstacion(String campos[])
			throws Exception {
		// Buscar
		TipoEstacion entity = GeneralDao.find(TipoEstacion.class, campos[0]);

		if (entity == null) {
			// Ingresar

			entity = new TipoEstacion();
			entity.setTipId(campos[0]);
			entity.setTipDescripcion(campos[1]);
			GeneralDao.insert(entity);
		}

		return entity;
	}

	public void ingresaEstacioncamposminimos(String campos[], TipoEstacion tip)
			throws Exception
	// campos basicos est_id, tipo_id, toa,modelodatalog,codigotadalog, std,
	// verionprog,num
	{
		// Insertar
		EstacionPK id = new EstacionPK();
		id.setEstId(campos[0]);
		id.setTipId(tip.getTipId());

		Estacion newEntity = GeneralDao.find(Estacion.class, id);
		if (newEntity == null) {
			newEntity = new Estacion();
			newEntity.setTipoEstacion(tip);
			// Log.getInstance().info("ESTE DE AQUI" + id.getTipId());
			newEntity.setId(id);
			newEntity.setEstToa(campos[2]);
			newEntity.setEstModelodatalog(campos[3]);
			newEntity.setEstCodigodatalog(campos[4]);
			newEntity.setEstStd(campos[5]);
			newEntity.setEstVersionprog(campos[6]);
			newEntity.setEstNum(campos[7]);
			GeneralDao.insert(newEntity);
		}

	}

	public Fenomeno ingresaFenomeno(String campos[]) throws Exception {
		Fenomeno entity = JpaManager.find(Fenomeno.class, campos[0]);

		if (entity == null) {
			// Ingresar
			entity = new Fenomeno();
			entity.setFenId(campos[0]);
			entity.setFenNombre(campos[1]);
			entity.setFenDescripcion(campos[2]);
			entity.setFenTipo(campos[3]);

			GeneralDao.insert(entity);
		}

		return entity;
	}

	public Unidade ingresaUnidade(String campos[]) throws Exception {
		Unidade entity = JpaManager.find(Unidade.class, campos[0]);

		if (entity == null) {
			// Ingresar

			entity = new Unidade();
			entity.setUniId(campos[0]);
			entity.setUniNombre(campos[1]);
			entity.setUniDescripcion(campos[2]);
			entity.setUniTipo(campos[3]);

			GeneralDao.insert(entity);
		}

		return entity;
	}

	public void ingresaFenomenoUnidade(String campos[]) throws Exception {
		// List<Fenomeno> fen = this.consultaFenomeno(campos[0]);

		// List<Unidade> uni = this.consultaUnidade(campos[1]);
		Unidade uni = JpaManager.find(Unidade.class, campos[0]);
		Fenomeno fen = JpaManager.find(Fenomeno.class, campos[1]);
		// System.out.println("LLENA FENOMENOOOOOOO"+ fen.getFenId()
		// +" "+uni.getUniId());
		FenomenoUnidadePK id = new FenomenoUnidadePK();
		id.setFenId(fen.getFenId());
		id.setUniId(uni.getUniId());

		FenomenoUnidade newEntity = GeneralDao.find(FenomenoUnidade.class, id);
		if (newEntity == null) {
			newEntity = new FenomenoUnidade();
			newEntity.setFenomeno(fen);
			newEntity.setUnidade(uni);
			newEntity.setId(id);
			if (!campos[2].equals(""))
				newEntity.setFeuMaximo(Double.valueOf(campos[2]));
			if (!campos[3].equals(""))
				newEntity.setFeuMinimo(Double.valueOf(campos[3]));
			if (!campos[4].equals(""))
				newEntity.setFeuAlturasensor(Double.valueOf(campos[4]));

			GeneralDao.insert(newEntity);
		}

	}

	public void ingresaObservacion(String campos[]) throws Exception {

		FenomenoUnidadePK fenUniPk = new FenomenoUnidadePK();
		fenUniPk.setFenId(campos[3]);
		fenUniPk.setUniId(campos[4]);
		FenomenoUnidade fenuni = JpaManager.find(FenomenoUnidade.class,
				fenUniPk);

		EstacionPK estPk = new EstacionPK();
		estPk.setEstId(campos[1]);
		estPk.setTipId(campos[2]);
		Estacion est = JpaManager.find(Estacion.class, estPk);

		ObservacionPK id = new ObservacionPK();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsed = format.parse(campos[0]);
		java.sql.Date sql = new java.sql.Date(parsed.getTime());
		id.setObsFecha(sql);
		id.setEstId(est.getId().getEstId());
		id.setTipId(est.getId().getTipId());
		id.setUniId(fenuni.getId().getFenId());
		id.setFenId(fenuni.getId().getUniId());

		// Observacion newEntity = GeneralDao.find(Observacion.class, id);
		// if (newEntity==null)
		// {
		Observacion newEntity = new Observacion();
		newEntity.setId(id);
		newEntity.setEstacion(est);
		newEntity.setFenomenoUnidade(fenuni);
		newEntity.setObsValor(campos[5]);

		GeneralDao.insert(newEntity);
		// }

	}

	// public void cerrarpersistencia() {
	// Log.getInstance().info("Cerrando la pesistencia...");
	// try {
	// em.close();
	// emf.close();
	// Log.getInstance().info("Persistencia cerrada");
	// } catch (Exception e) {
	// Log.getInstance().error(e);
	// }
	// }
}
