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
	private double feuAlturasensor;

	@Column(name="feu_maximo")
	private double feuMaximo;

	@Column(name="feu_minimo")
	private double feuMinimo;

	//uni-directional many-to-one association to Fenomeno
	@ManyToOne
	@JoinColumn(name="fen_id")
	private Fenomeno fenomeno;

	//uni-directional many-to-one association to Unidade
	@ManyToOne
	@JoinColumn(name="uni_id")
	private Unidade unidade;

	public FenomenoUnidade() {
	}

	public FenomenoUnidadePK getId() {
		return this.id;
	}

	public void setId(FenomenoUnidadePK id) {
		this.id = id;
	}

	public double getFeuAlturasensor() {
		return this.feuAlturasensor;
	}

	public void setFeuAlturasensor(double feuAlturasensor) {
		this.feuAlturasensor = feuAlturasensor;
	}

	public double getFeuMaximo() {
		return this.feuMaximo;
	}

	public void setFeuMaximo(double feuMaximo) {
		this.feuMaximo = feuMaximo;
	}

	public double getFeuMinimo() {
		return this.feuMinimo;
	}

	public void setFeuMinimo(double feuMinimo) {
		this.feuMinimo = feuMinimo;
	}

	public Fenomeno getFenomeno() {
		return this.fenomeno;
	}

	public void setFenomeno(Fenomeno fenomeno) {
		this.fenomeno = fenomeno;
	}

	public Unidade getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

}