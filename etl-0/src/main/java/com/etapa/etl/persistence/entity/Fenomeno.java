package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fenomeno database table.
 * 
 */
@Entity
@NamedQuery(name="Fenomeno.findAll", query="SELECT f FROM Fenomeno f")
public class Fenomeno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fen_id")
	private String fenId;

	@Column(name="fen_descripcion")
	private String fenDescripcion;

	@Column(name="fen_nombre")
	private String fenNombre;

	@Column(name="fen_tipo")
	private String fenTipo;

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

}