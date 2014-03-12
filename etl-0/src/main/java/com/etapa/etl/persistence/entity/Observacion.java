package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the observacion database table.
 * 
 */
@Entity
@NamedQuery(name="Observacion.findAll", query="SELECT o FROM Observacion o")
public class Observacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ObservacionPK id;

	@Column(name="obs_valor")
	private String obsValor;

	public Observacion() {
	}

	public ObservacionPK getId() {
		return this.id;
	}

	public void setId(ObservacionPK id) {
		this.id = id;
	}

	public String getObsValor() {
		return this.obsValor;
	}

	public void setObsValor(String obsValor) {
		this.obsValor = obsValor;
	}

}