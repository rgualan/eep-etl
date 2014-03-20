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

	//bi-directional many-to-one association to Estacion
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="est_id", referencedColumnName="est_id"),
		@JoinColumn(name="tip_id", referencedColumnName="tip_id")
		})
	private Estacion estacion;

	//bi-directional many-to-one association to FenomenoUnidade
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="fen_id", referencedColumnName="fen_id"),
		@JoinColumn(name="uni_id", referencedColumnName="uni_id")
		})
	private FenomenoUnidade fenomenoUnidade;

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

	public Estacion getEstacion() {
		return this.estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	public FenomenoUnidade getFenomenoUnidade() {
		return this.fenomenoUnidade;
	}

	public void setFenomenoUnidade(FenomenoUnidade fenomenoUnidade) {
		this.fenomenoUnidade = fenomenoUnidade;
	}

}