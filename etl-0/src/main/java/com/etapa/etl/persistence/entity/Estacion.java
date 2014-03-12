package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


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

	@Column(name="est_descripcion")
	private String estDescripcion;

	@Column(name="est_latitud")
	private float estLatitud;

	@Column(name="est_longitud")
	private float estLongitud;

	@Column(name="est_parroquia")
	private String estParroquia;

	@Column(name="est_provincia")
	private String estProvincia;

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

	public String getEstDescripcion() {
		return this.estDescripcion;
	}

	public void setEstDescripcion(String estDescripcion) {
		this.estDescripcion = estDescripcion;
	}

	public float getEstLatitud() {
		return this.estLatitud;
	}

	public void setEstLatitud(float estLatitud) {
		this.estLatitud = estLatitud;
	}

	public float getEstLongitud() {
		return this.estLongitud;
	}

	public void setEstLongitud(float estLongitud) {
		this.estLongitud = estLongitud;
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

}