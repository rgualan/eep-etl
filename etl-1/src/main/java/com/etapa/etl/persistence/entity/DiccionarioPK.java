package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the diccionario database table.
 * 
 */
@Embeddable
public class DiccionarioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="dic_id")
	private String dicId;

	@Column(name="dic_tipo")
	private String dicTipo;

	public DiccionarioPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DiccionarioPK)) {
			return false;
		}
		DiccionarioPK castOther = (DiccionarioPK)other;
		return 
			this.dicId.equals(castOther.dicId)
			&& this.dicTipo.equals(castOther.dicTipo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dicId.hashCode();
		hash = hash * prime + this.dicTipo.hashCode();
		
		return hash;
	}
}