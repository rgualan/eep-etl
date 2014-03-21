package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_estacion database table.
 * 
 */
@Entity
@Table(name="tipo_estacion")
@NamedQuery(name="TipoEstacion.findAll", query="SELECT t FROM TipoEstacion t")
public class TipoEstacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tip_id")
	private String tipId;

	@Column(name="tip_descripcion")
	private String tipDescripcion;

	//bi-directional many-to-one association to Estacion
	@OneToMany(mappedBy="tipoEstacion")
	private List<Estacion> estacions;

	public TipoEstacion() {
	}

	public String getTipId() {
		return this.tipId;
	}

	public void setTipId(String tipId) {
		this.tipId = tipId;
	}

	public String getTipDescripcion() {
		return this.tipDescripcion;
	}

	public void setTipDescripcion(String tipDescripcion) {
		this.tipDescripcion = tipDescripcion;
	}

	public List<Estacion> getEstacions() {
		return this.estacions;
	}

	public void setEstacions(List<Estacion> estacions) {
		this.estacions = estacions;
	}

	public Estacion addEstacion(Estacion estacion) {
		getEstacions().add(estacion);
		estacion.setTipoEstacion(this);

		return estacion;
	}

	public Estacion removeEstacion(Estacion estacion) {
		getEstacions().remove(estacion);
		estacion.setTipoEstacion(null);

		return estacion;
	}

}