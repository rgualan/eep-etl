package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estacion database table.
 * 
 */
@Entity
@NamedQuery(name="Estacion.findAll", query="SELECT e FROM Estacion e")
public class Estacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EstacionPK id;

	@Column(name="est_ciudad")
	private String estCiudad;

	@Column(name="est_codigodatalog")
	private String estCodigodatalog;

	@Column(name="est_descripcion")
	private String estDescripcion;

	@Column(name="est_latitud")
	private double estLatitud;

	@Column(name="est_longitud")
	private double estLongitud;

	@Column(name="est_modelodatalog")
	private String estModelodatalog;

	@Column(name="est_num")
	private String estNum;

	@Column(name="est_parroquia")
	private String estParroquia;

	@Column(name="est_provincia")
	private String estProvincia;

	@Column(name="est_std")
	private String estStd;

	@Column(name="est_toa")
	private String estToa;

	@Column(name="est_versionprog")
	private String estVersionprog;

	//bi-directional many-to-one association to TipoEstacion
	@ManyToOne
	@JoinColumn(name="tip_id")
	private TipoEstacion tipoEstacion;

	//bi-directional many-to-one association to Observacion
	@OneToMany(mappedBy="estacion")
	private List<Observacion> observacions;

	public Estacion() {
	}

	public EstacionPK getId() {
		return this.id;
	}

	public void setId(EstacionPK id) {
		this.id = id;
	}

	public String getEstCiudad() {
		return this.estCiudad;
	}

	public void setEstCiudad(String estCiudad) {
		this.estCiudad = estCiudad;
	}

	public String getEstCodigodatalog() {
		return this.estCodigodatalog;
	}

	public void setEstCodigodatalog(String estCodigodatalog) {
		this.estCodigodatalog = estCodigodatalog;
	}

	public String getEstDescripcion() {
		return this.estDescripcion;
	}

	public void setEstDescripcion(String estDescripcion) {
		this.estDescripcion = estDescripcion;
	}

	public double getEstLatitud() {
		return this.estLatitud;
	}

	public void setEstLatitud(double estLatitud) {
		this.estLatitud = estLatitud;
	}

	public double getEstLongitud() {
		return this.estLongitud;
	}

	public void setEstLongitud(double estLongitud) {
		this.estLongitud = estLongitud;
	}

	public String getEstModelodatalog() {
		return this.estModelodatalog;
	}

	public void setEstModelodatalog(String estModelodatalog) {
		this.estModelodatalog = estModelodatalog;
	}

	public String getEstNum() {
		return this.estNum;
	}

	public void setEstNum(String estNum) {
		this.estNum = estNum;
	}

	public String getEstParroquia() {
		return this.estParroquia;
	}

	public void setEstParroquia(String estParroquia) {
		this.estParroquia = estParroquia;
	}

	public String getEstProvincia() {
		return this.estProvincia;
	}

	public void setEstProvincia(String estProvincia) {
		this.estProvincia = estProvincia;
	}

	public String getEstStd() {
		return this.estStd;
	}

	public void setEstStd(String estStd) {
		this.estStd = estStd;
	}

	public String getEstToa() {
		return this.estToa;
	}

	public void setEstToa(String estToa) {
		this.estToa = estToa;
	}

	public String getEstVersionprog() {
		return this.estVersionprog;
	}

	public void setEstVersionprog(String estVersionprog) {
		this.estVersionprog = estVersionprog;
	}

	public TipoEstacion getTipoEstacion() {
		return this.tipoEstacion;
	}

	public void setTipoEstacion(TipoEstacion tipoEstacion) {
		this.tipoEstacion = tipoEstacion;
	}

	public List<Observacion> getObservacions() {
		return this.observacions;
	}

	public void setObservacions(List<Observacion> observacions) {
		this.observacions = observacions;
	}

	public Observacion addObservacion(Observacion observacion) {
		getObservacions().add(observacion);
		observacion.setEstacion(this);

		return observacion;
	}

	public Observacion removeObservacion(Observacion observacion) {
		getObservacions().remove(observacion);
		observacion.setEstacion(null);

		return observacion;
	}

}