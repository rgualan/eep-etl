package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fenomeno database table.
 * 
 */
@Entity
@NamedQuery(name="Fenomeno.findAll", query="SELECT f FROM Fenomeno f")
public class Fenomeno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="fen_id")
	private String fenId;

	@Column(name="fen_descripcion")
	private String fenDescripcion;

	@Column(name="fen_nombre")
	private String fenNombre;

	@Column(name="fen_tipo")
	private String fenTipo;

	//bi-directional many-to-one association to FenomenoUnidade
	@OneToMany(mappedBy="fenomeno")
	private List<FenomenoUnidade> fenomenoUnidades;

	public Fenomeno() {
	}

	public String getFenId() {
		return this.fenId;
	}

	public void setFenId(String fenId) {
		this.fenId = fenId;
	}

	public String getFenDescripcion() {
		return this.fenDescripcion;
	}

	public void setFenDescripcion(String fenDescripcion) {
		this.fenDescripcion = fenDescripcion;
	}

	public String getFenNombre() {
		return this.fenNombre;
	}

	public void setFenNombre(String fenNombre) {
		this.fenNombre = fenNombre;
	}

	public String getFenTipo() {
		return this.fenTipo;
	}

	public void setFenTipo(String fenTipo) {
		this.fenTipo = fenTipo;
	}

	public List<FenomenoUnidade> getFenomenoUnidades() {
		return this.fenomenoUnidades;
	}

	public void setFenomenoUnidades(List<FenomenoUnidade> fenomenoUnidades) {
		this.fenomenoUnidades = fenomenoUnidades;
	}

	public FenomenoUnidade addFenomenoUnidade(FenomenoUnidade fenomenoUnidade) {
		getFenomenoUnidades().add(fenomenoUnidade);
		fenomenoUnidade.setFenomeno(this);

		return fenomenoUnidade;
	}

	public FenomenoUnidade removeFenomenoUnidade(FenomenoUnidade fenomenoUnidade) {
		getFenomenoUnidades().remove(fenomenoUnidade);
		fenomenoUnidade.setFenomeno(null);

		return fenomenoUnidade;
	}

}