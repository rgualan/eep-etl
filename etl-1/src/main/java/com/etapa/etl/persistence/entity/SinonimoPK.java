package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the sinonimo database table.
 * 
 */
@Embeddable
public class SinonimoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="dic_id", insertable=false, updatable=false)
	private String dicId;

	@Column(name="dic_tipo", insertable=false, updatable=false)
	private String dicTipo;

	@Column(name="sin_nombre")
	private String sinNombre;

	public SinonimoPK() {
	}
	public String getDicId() {
		return this.dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	public String getDicTipo() {
		return this.dicTipo;
	}
	public void setDicTipo(String dicTipo) {
		this.dicTipo = dicTipo;
	}
	public String getSinNombre() {
		return this.sinNombre;
	}
	public void setSinNombre(String sinNombre) {
		this.sinNombre = sinNombre;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SinonimoPK)) {
			return false;
		}
		SinonimoPK castOther = (SinonimoPK)other;
		return 
			this.dicId.equals(castOther.dicId)
			&& this.dicTipo.equals(castOther.dicTipo)
			&& this.sinNombre.equals(castOther.sinNombre);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dicId.hashCode();
		hash = hash * prime + this.dicTipo.hashCode();
		hash = hash * prime + this.sinNombre.hashCode();
		
		return hash;
	}
}