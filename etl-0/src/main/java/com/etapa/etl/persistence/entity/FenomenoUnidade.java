package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fenomeno_unidades database table.
 * 
 */
@Entity
@Table(name="fenomeno_unidades")
@NamedQuery(name="FenomenoUnidade.findAll", query="SELECT f FROM FenomenoUnidade f")
public class FenomenoUnidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FenomenoUnidadePK id;

	@Column(name="feu_alturasensor")
	private float feuAlturasensor;

	@Column(name="feu_maximo")
	private float feuMaximo;

	@Column(name="feu_minimo")
	private float feuMinimo;

	public FenomenoUnidade() {
	}

	public FenomenoUnidadePK getId() {
		return this.id;
	}

	public void setId(FenomenoUnidadePK id) {
		this.id = id;
	}

	public float getFeuAlturasensor() {
		return this.feuAlturasensor;
	}

	public void setFeuAlturasensor(float feuAlturasensor) {
		this.feuAlturasensor = feuAlturasensor;
	}

	public float getFeuMaximo() {
		return this.feuMaximo;
	}

	public void setFeuMaximo(float feuMaximo) {
		this.feuMaximo = feuMaximo;
	}

	public float getFeuMinimo() {
		return this.feuMinimo;
	}

	public void setFeuMinimo(float feuMinimo) {
		this.feuMinimo = feuMinimo;
	}

}