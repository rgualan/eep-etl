package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the unidades database table.
 * 
 */
@Entity
@Table(name="unidades")
@NamedQuery(name="Unidade.findAll", query="SELECT u FROM Unidade u")
public class Unidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="uni_id")
	private String uniId;

	@Column(name="uni_descripcion")
	private String uniDescripcion;

	@Column(name="uni_nombre")
	private String uniNombre;

	@Column(name="uni_tipo")
	private String uniTipo;

	//bi-directional many-to-one association to FenomenoUnidade
	@OneToMany(mappedBy="unidade")
	private List<FenomenoUnidade> fenomenoUnidades;

	public Unidade() {
	}

	public String getUniId() {
		return this.uniId;
	}

	public void setUniId(String uniId) {
		this.uniId = uniId;
	}

	public String getUniDescripcion() {
		return this.uniDescripcion;
	}

	public void setUniDescripcion(String uniDescripcion) {
		this.uniDescripcion = uniDescripcion;
	}

	public String getUniNombre() {
		return this.uniNombre;
	}

	public void setUniNombre(String uniNombre) {
		this.uniNombre = uniNombre;
	}

	public String getUniTipo() {
		return this.uniTipo;
	}

	public void setUniTipo(String uniTipo) {
		this.uniTipo = uniTipo;
	}

	public List<FenomenoUnidade> getFenomenoUnidades() {
		return this.fenomenoUnidades;
	}

	public void setFenomenoUnidades(List<FenomenoUnidade> fenomenoUnidades) {
		this.fenomenoUnidades = fenomenoUnidades;
	}

	public FenomenoUnidade addFenomenoUnidade(FenomenoUnidade fenomenoUnidade) {
		getFenomenoUnidades().add(fenomenoUnidade);
		fenomenoUnidade.setUnidade(this);

		return fenomenoUnidade;
	}

	public FenomenoUnidade removeFenomenoUnidade(FenomenoUnidade fenomenoUnidade) {
		getFenomenoUnidades().remove(fenomenoUnidade);
		fenomenoUnidade.setUnidade(null);

		return fenomenoUnidade;
	}

}