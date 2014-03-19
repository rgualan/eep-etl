package com.etapa.etl.persistence.dao;

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
		// Log.getInstance().info("Cargando la persistencia...");
		//
		// try {
		// // Cargar persistencia
		// emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		// em = emf.createEntityManager();
		//
		// Log.getInstance().info("Persistencia cargada");
		// } catch (Exception e) {
		// Log.error("Error", e);
		// }
	}

	public Archivo consultaArchivo(String id) throws Exception {
		// List<Archivo> resultList = null;
		// ArchivoDao dao = new ArchivoDao(em);
		// if (id.equals("")) {
		// resultList = dao.queryAll(Archivo.class);
		// } else {
		// Archivo tmpArc = dao.queryById(id);
		// if (tmpArc != null) {
		// resultList = new ArrayList<Archivo>();
		// resultList.add(tmpArc);
		// }
		// }
		// if (resultList != null) {
		// Log.getInstance().info("Resultado: ");
		// for (Archivo archivo : resultList) {
		// Log.getInstance().info(archivo.getArcPath());
		// }
		// }
		// return resultList;

		return JpaManager.find(Archivo.class, id);

	}

	public TipoEstacion consultaTipoEstacion(String id) throws Exception {
		// List<TipoEstacion> resultList = null;
		// TipoEstacionDao dao = new TipoEstacionDao(em);
		// if (id.equals("")) {
		// resultList = dao.queryAll(TipoEstacion.class);
		// } else {
		// TipoEstacion tmpArc = dao.queryById(id);
		// if (tmpArc != null) {
		// resultList = new ArrayList();
		// resultList.add(tmpArc);
		// }
		// }
		// if (resultList != null) {
		// Log.getInstance().info("Resultado: ");
		// for (TipoEstacion tip : resultList) {
		// Log.getInstance().info(tip.getTipNombre());
		// }
		// }
		// return resultList;

		return JpaManager.find(TipoEstacion.class, id);
	}

	public TipoEstacion consultaTipoEstacionporNombre(String nombre) {
		TipoEstacion tmpArc = TipoEstacionDao.queryByNombre(nombre);

		return tmpArc;
	}

	public Estacion consultaEstacion(String estid, int tipoid) throws Exception {
		// List<Estacion> resultList = null;
		// EstacionDao dao = new EstacionDao(em);
		// if (estid.equals("")) {
		// resultList = dao.queryAll(Estacion.class);
		// } else {
		// Estacion tmpArc = dao.queryById(estid, tipoid);
		// if (tmpArc != null) {
		// resultList = new ArrayList();
		// resultList.add(tmpArc);
		// }
		// }
		// if (resultList != null) {
		// Log.getInstance().info("Resultado: ");
		// for (Estacion est : resultList) {
		// Log.getInstance().info(est.getEstToa());
		// }
		// }
		// return resultList;

		EstacionPK estacionPk = new EstacionPK();
		estacionPk.setEstId(estid);
		estacionPk.setTipId(tipoid);

		return GeneralDao.find(Estacion.class, estacionPk);

	}

	public Fenomeno consultaFenomeno(String id) throws Exception {
		// List<Fenomeno> resultList = null;
		// FenomenoDao dao = new FenomenoDao(em);
		// if (id.equals("")) {
		// resultList = dao.queryAll(Fenomeno.class);
		// } else {
		// Fenomeno tmpArc = dao.queryById(id);
		// if (tmpArc != null) {
		// resultList = new ArrayList();
		// resultList.add(tmpArc);
		// }
		// }
		// if (resultList != null) {
		// Log.getInstance().info("Resultado: ");
		// for (Fenomeno fen : resultList) {
		// Log.getInstance().info(fen.getFenId());
		// }
		// }
		// return resultList;

		return GeneralDao.find(Fenomeno.class, id);

	}

	// public List<Unidade> consultaUnidade(String id) {
	// List<Unidade> resultList = null;
	// UnidadeDao dao = new UnidadeDao(em);
	// if (id.equals("")) {
	// resultList = dao.queryAll(Unidade.class);
	// } else {
	// Unidade tmpArc = dao.queryById(id);
	// if (tmpArc != null) {
	// resultList = new ArrayList();
	// resultList.add(tmpArc);
	// }
	// }
	// if (resultList != null) {
	// Log.getInstance().info("Resultado: ");
	// for (Unidade uni : resultList) {
	// Log.getInstance().info(uni.getUniId());
	// }
	// }
	// return resultList;
	//
	// }
	//
	// public List<FenomenoUnidade> consultaFenomenoUnidade(String uniid,
	// String fenid) {
	// List<FenomenoUnidade> resultList = null;
	// FenomenoUnidadeDao dao = new FenomenoUnidadeDao(em);
	// if (fenid.equals("")) {
	// resultList = dao.queryAll(FenomenoUnidade.class);
	// } else {
	// FenomenoUnidade tmpArc = dao.queryById(fenid, uniid);
	// if (tmpArc != null) {
	// resultList = new ArrayList();
	// resultList.add(tmpArc);
	// }
	// }
	// if (resultList != null) {
	// Log.getInstance().info("Resultado: ");
	// for (FenomenoUnidade fen : resultList) {
	// Log.getInstance().info(fen.getFeuAlturasensor());
	// }
	// }
	// return resultList;
	//
	// }

	public Observacion consultaObservacion(String fecha, String estid,
			String tipid, String uniid, String fenid) throws Exception {

		ObservacionPK pk = new ObservacionPK();
		pk.setObsFecha(new java.util.Date(fecha)); // TODO usar un metodo de
													// parsing de fecha no
													// deprecado
		pk.setEstId(estid);
		pk.setTipId(Integer.parseInt(tipid));
		pk.setUniId(uniid);
		pk.setFenId(fenid);

		Observacion obs = GeneralDao.find(Observacion.class, pk);

		return obs;

	}

	public void ingresaArchivo(String campos[]) throws Exception {
		Archivo newEntity = new Archivo();
		newEntity.setArcPath(campos[0]);
		newEntity.setArcNbytes(Long.valueOf(campos[1]));
		GeneralDao.insert(newEntity);
	}

	public TipoEstacion buscaoIngresaTipoEstacion(String campos[]) throws Exception {
		// Buscar
		TipoEstacion entity = JpaManager.find(TipoEstacion.class, campos[0]);

		if (entity == null) {
			// Ingresar

			entity = new TipoEstacion();
			entity.setTipNombre(campos[0]);
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
		Log.getInstance().info("ESTE R =" + campos[1]);

		// Insertar
		Estacion newEntity = new Estacion();
		EstacionPK id = new EstacionPK();
		id.setEstId(campos[0]);
		id.setTipId(tip.getTipId());
		newEntity.setTipoEstacion(tip);
		Log.getInstance().info("ESTE DE AQUI" + id.getTipId());
		newEntity.setId(id);
		newEntity.setEstToa(campos[2]);
		newEntity.setEstModelodatalog(campos[3]);
		newEntity.setEstCodigodatalog(campos[4]);
		newEntity.setEstStd(campos[5]);
		newEntity.setEstVersionprog(campos[6]);
		newEntity.setEstNum(campos[7]);

		GeneralDao.insert(newEntity);

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
		Fenomeno fen = JpaManager.find(Fenomeno.class, campos[0]);

		// List<Unidade> uni = this.consultaUnidade(campos[1]);
		Unidade uni = JpaManager.find(Unidade.class, campos[1]);

		FenomenoUnidade newEntity = new FenomenoUnidade();
		newEntity.setFenomeno(fen);
		newEntity.setUnidade(uni);
		newEntity.setFeuMaximo(Double.valueOf(campos[2]));
		newEntity.setFeuMinimo(Double.valueOf(campos[3]));
		newEntity.setFeuAlturasensor(Double.valueOf(campos[4]));

		GeneralDao.insert(newEntity);

	}

	public void ingresaObservacion(String campos[]) throws Exception {
		FenomenoUnidadePK fenUniPk = new FenomenoUnidadePK();
		fenUniPk.setFenId(campos[3]);
		fenUniPk.setFenId(campos[4]);
		FenomenoUnidade fenuni = JpaManager.find(FenomenoUnidade.class,
				fenUniPk);

		EstacionPK estPk = new EstacionPK();
		estPk.setEstId(campos[1]);
		estPk.setEstId(campos[2]);
		Estacion est = JpaManager.find(Estacion.class, estPk);

		Observacion newEntity = new Observacion();
		ObservacionPK id = new ObservacionPK();
		id.setObsFecha(new java.util.Date(campos[0]));
		newEntity.setId(id);
		newEntity.setEstacion(est);
		newEntity.setFenomenoUnidade(fenuni);
		newEntity.setObsValor(campos[5]);

		GeneralDao.insert(newEntity);

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
