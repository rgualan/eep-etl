package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


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
	private int tipId;

	@Column(name="tip_descripcion")
	private String tipDescripcion;

	@Column(name="tip_nombre")
	private String tipNombre;

	public TipoEstacion() {
	}

	public int getTipId() {
		return this.tipId;
	}

	public void setTipId(int tipId) {
		this.tipId = tipId;
	}

	public String getTipDescripcion() {
		return this.tipDescripcion;
	}

	public void setTipDescripcion(String tipDescripcion) {
		this.tipDescripcion = tipDescripcion;
	}

	public String getTipNombre() {
		return this.tipNombre;
	}

	public void setTipNombre(String tipNombre) {
		this.tipNombre = tipNombre;
	}

}