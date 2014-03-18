package com.etapa.etl.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.etapa.etl.persistence.dao.ArchivoDao;
import com.etapa.etl.persistence.dao.EstacionDao;
import com.etapa.etl.persistence.dao.FenomenoDao;
import com.etapa.etl.persistence.dao.FenomenoUnidadeDao;
import com.etapa.etl.persistence.dao.ObservacionDao;
import com.etapa.etl.persistence.dao.TipoEstacionDao;
import com.etapa.etl.persistence.dao.UnidadeDao;
import com.etapa.etl.persistence.entity.Archivo;
import com.etapa.etl.persistence.entity.Estacion;
import com.etapa.etl.persistence.entity.EstacionPK;
import com.etapa.etl.persistence.entity.Fenomeno;
import com.etapa.etl.persistence.entity.FenomenoUnidade;
import com.etapa.etl.persistence.entity.Observacion;
import com.etapa.etl.persistence.entity.ObservacionPK;
import com.etapa.etl.persistence.entity.TipoEstacion;
import com.etapa.etl.persistence.entity.Unidade;

public class Persistencia {

	public final String PERSISTENCE_UNIT = "etl";
	private EntityManagerFactory emf = null;
	public EntityManager em = null;

	public void cargarPersistencia() {
		System.out.println("Cargando la persistencia...");

		try {
			// Cargar persistencia
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
			em = emf.createEntityManager();

			System.out.println("Persistencia abierta");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Archivo> consultaArchivo(String id) {
		List<Archivo> resultList = null;
		ArchivoDao dao = new ArchivoDao(em);
		if (id.equals("")) {
			resultList = dao.queryAll(Archivo.class);
		} else {
			Archivo tmpArc = dao.queryById(id);
			if (tmpArc != null) {
				resultList = new ArrayList();
				resultList.add(tmpArc);
			}
		}
		if (resultList != null) {
			System.out.println("Resultado: ");
			for (Archivo archivo : resultList) {
				System.out.println(archivo.getArcPath());
			}
		}
		return resultList;

	}

	public List<TipoEstacion> consultaTipoEstacion(String id) {
		List<TipoEstacion> resultList = null;
		TipoEstacionDao dao = new TipoEstacionDao(em);
		if (id.equals("")) {
			resultList = dao.queryAll(TipoEstacion.class);
		} else {
			TipoEstacion tmpArc = dao.queryById(id);
			if (tmpArc != null) {
				resultList = new ArrayList();
				resultList.add(tmpArc);
			}
		}
		if (resultList != null) {
			System.out.println("Resultado: ");
			for (TipoEstacion tip : resultList) {
				System.out.println(tip.getTipNombre());
			}
		}
		return resultList;

	}

	public TipoEstacion consultaTipoEstacionporNombre(String nombre) {
		TipoEstacion resultList = null;
		TipoEstacionDao dao = new TipoEstacionDao(em);
		TipoEstacion tmpArc = dao.queryByNombre(nombre);

		return tmpArc;

	}

	public List<Estacion> consultaEstacion(String estid, int tipoid) {
		List<Estacion> resultList = null;
		EstacionDao dao = new EstacionDao(em);
		if (estid.equals("")) {
			resultList = dao.queryAll(Estacion.class);
		} else {
			Estacion tmpArc = dao.queryById(estid, tipoid);
			if (tmpArc != null) {
				resultList = new ArrayList();
				resultList.add(tmpArc);
			}
		}
		if (resultList != null) {
			System.out.println("Resultado: ");
			for (Estacion est : resultList) {
				System.out.println(est.getEstToa());
			}
		}
		return resultList;

	}

	public Estacion consultaEstacionjoin(String estid, int tipoid) {
		Estacion resultList = null;
		EstacionDao dao = new EstacionDao(em);
		try {
			Estacion tmpArc = dao.queryjoinById(estid, tipoid);
			resultList = tmpArc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resultList != null) {
			System.out.println("Resultado: ");

			System.out.println(resultList.getEstToa());

		}
		return resultList;

	}

	public List<Fenomeno> consultaFenomeno(String id) {
		List<Fenomeno> resultList = null;
		FenomenoDao dao = new FenomenoDao(em);
		if (id.equals("")) {
			resultList = dao.queryAll(Fenomeno.class);
		} else {
			Fenomeno tmpArc = dao.queryById(id);
			if (tmpArc != null) {
				resultList = new ArrayList();
				resultList.add(tmpArc);
			}
		}
		if (resultList != null) {
			System.out.println("Resultado: ");
			for (Fenomeno fen : resultList) {
				System.out.println(fen.getFenId());
			}
		}
		return resultList;

	}

	public List<Unidade> consultaUnidade(String id) {
		List<Unidade> resultList = null;
		UnidadeDao dao = new UnidadeDao(em);
		if (id.equals("")) {
			resultList = dao.queryAll(Unidade.class);
		} else {
			Unidade tmpArc = dao.queryById(id);
			if (tmpArc != null) {
				resultList = new ArrayList();
				resultList.add(tmpArc);
			}
		}
		if (resultList != null) {
			System.out.println("Resultado: ");
			for (Unidade uni : resultList) {
				System.out.println(uni.getUniId());
			}
		}
		return resultList;

	}

	public List<FenomenoUnidade> consultaFenomenoUnidade(String uniid,
			String fenid) {
		List<FenomenoUnidade> resultList = null;
		FenomenoUnidadeDao dao = new FenomenoUnidadeDao(em);
		if (fenid.equals("")) {
			resultList = dao.queryAll(FenomenoUnidade.class);
		} else {
			FenomenoUnidade tmpArc = dao.queryById(fenid, uniid);
			if (tmpArc != null) {
				resultList = new ArrayList();
				resultList.add(tmpArc);
			}
		}
		if (resultList != null) {
			System.out.println("Resultado: ");
			for (FenomenoUnidade fen : resultList) {
				System.out.println(fen.getFeuAlturasensor());
			}
		}
		return resultList;

	}

	public List<Observacion> consultaObservacion(String fecha, String estid,
			String tipid, String uniid, String fenid) {
		List<Observacion> resultList = null;
		ObservacionDao dao = new ObservacionDao(em);
		if (fenid.equals("")) {
			resultList = dao.queryAll(Observacion.class);
		} else {
			Observacion tmpArc = dao.queryById(fecha, estid, tipid, uniid,
					fenid);
			if (tmpArc != null) {
				resultList = new ArrayList();
				resultList.add(tmpArc);
			}
		}
		if (resultList != null) {
			System.out.println("Resultado: ");
			for (Observacion fen : resultList) {
				System.out.println(fen.getObsValor());
			}
		}
		return resultList;

	}

	public void ingresaArchivo(String campos[]) {
		try {
			em.getTransaction().begin();
			ArchivoDao dao = new ArchivoDao(em);
			Archivo newEntity = new Archivo();
			newEntity.setArcPath(campos[0]);
			newEntity.setArcNbytes(Long.valueOf(campos[1]));
			dao.insert(newEntity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresaTipoEstacion(String campos[]) {
		try {
			em.getTransaction().begin();
			TipoEstacionDao dao = new TipoEstacionDao(em);
			TipoEstacion newEntity = new TipoEstacion();
			newEntity.setTipNombre(campos[0]);
			newEntity.setTipDescripcion(campos[1]);
			dao.insert(newEntity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresaEstacioncamposminimos(String campos[], TipoEstacion tip)
	// campos basicos est_id, tipo_id, toa,modelodatalog,codigotadalog, std,
	// verionprog,num
	{
		try {
			System.out.println("ESTE R =" + campos[1]);
			// cargarPersistencia();
			// TipoEstacion tip=consultaTipoEstacionporNombre(campos[1]);

			EstacionDao dao = new EstacionDao(em);
			Estacion newEntity = new Estacion();
			EstacionPK id = new EstacionPK();
			id.setEstId(campos[0]);
			id.setTipId(tip.getTipId());
			newEntity.setTipoEstacion(tip);
			System.out.println("ESTE DE AQUI" + id.getTipId());
			newEntity.setId(id);
			newEntity.setEstToa(campos[2]);
			newEntity.setEstModelodatalog(campos[3]);
			newEntity.setEstCodigodatalog(campos[4]);
			newEntity.setEstStd(campos[5]);
			newEntity.setEstVersionprog(campos[6]);
			newEntity.setEstNum(campos[7]);
			em.getTransaction().begin();
			dao.insert(newEntity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresaFenomeno(String campos[]) {
		try {
			em.getTransaction().begin();
			FenomenoDao dao = new FenomenoDao(em);
			Fenomeno newEntity = new Fenomeno();
			newEntity.setFenId(campos[0]);
			newEntity.setFenNombre(campos[1]);
			newEntity.setFenDescripcion(campos[2]);
			newEntity.setFenTipo(campos[3]);
			dao.insert(newEntity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresaUnidade(String campos[]) {
		try {
			em.getTransaction().begin();
			UnidadeDao dao = new UnidadeDao(em);
			Unidade newEntity = new Unidade();
			newEntity.setUniId(campos[0]);
			newEntity.setUniNombre(campos[1]);
			newEntity.setUniDescripcion(campos[2]);
			newEntity.setUniTipo(campos[3]);
			dao.insert(newEntity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresaFenomenoUnidade(String campos[]) {
		try {
			List<Fenomeno> fen = this.consultaFenomeno(campos[0]);
			List<Unidade> uni = this.consultaUnidade(campos[1]);
			em.getTransaction().begin();
			FenomenoUnidadeDao dao = new FenomenoUnidadeDao(em);
			FenomenoUnidade newEntity = new FenomenoUnidade();
			newEntity.setFenomeno(fen.get(0));
			newEntity.setUnidade(uni.get(0));
			newEntity.setFeuMaximo(Double.valueOf(campos[2]));
			newEntity.setFeuMinimo(Double.valueOf(campos[3]));
			newEntity.setFeuAlturasensor(Double.valueOf(campos[4]));
			dao.insert(newEntity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresaObservacion(String campos[]) {
		try {
			List<FenomenoUnidade> fen = this.consultaFenomenoUnidade(campos[3],
					campos[4]);
			Estacion est = this.consultaEstacionjoin(campos[1],
					Integer.valueOf(campos[2]));
			em.getTransaction().begin();
			ObservacionDao dao = new ObservacionDao(em);
			Observacion newEntity = new Observacion();
			ObservacionPK id = new ObservacionPK();
			id.setObsFecha(Date.valueOf(campos[0]));
			newEntity.setId(id);
			newEntity.setEstacion(est);
			newEntity.setFenomenoUnidade(fen.get(0));
			newEntity.setObsValor(campos[5]);
			dao.insert(newEntity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cerrarpersistencia() {
		System.out.println("Cerrando la pesistencia...");
		try {
			em.close();
			emf.close();
			System.out.println("Persistencia cerrada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
