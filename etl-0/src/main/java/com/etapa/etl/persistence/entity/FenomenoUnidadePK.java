package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fenomeno_unidades database table.
 * 
 */
@Embeddable
public class FenomenoUnidadePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="fen_id")
	private String fenId;

	@Column(name="uni_id")
	private String uniId;

	public FenomenoUnidadePK() {
	}
	public String getFenId() {
		return this.fenId;
	}
	public void setFenId(String fenId) {
		this.fenId = fenId;
	}
	public String getUniId() {
		return this.uniId;
	}
	public void setUniId(String uniId) {
		this.uniId = uniId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FenomenoUnidadePK)) {
			return false;
		}
		FenomenoUnidadePK castOther = (FenomenoUnidadePK)other;
		return 
			this.fenId.equals(castOther.fenId)
			&& this.uniId.equals(castOther.uniId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fenId.hashCode();
		hash = hash * prime + this.uniId.hashCode();
		
		return hash;
	}
}