package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the estacion database table.
 * 
 */
@Embeddable
public class EstacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="est_id")
	private String estId;

	@Column(name="tip_id", insertable=false, updatable=false)
	private Integer tipId;

	public EstacionPK() {
	}
	public String getEstId() {
		return this.estId;
	}
	public void setEstId(String estId) {
		this.estId = estId;
	}
	public Integer getTipId() {
		return this.tipId;
	}
	public void setTipId(Integer tipId) {
		this.tipId = tipId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EstacionPK)) {
			return false;
		}
		EstacionPK castOther = (EstacionPK)other;
		return 
			this.estId.equals(castOther.estId)
			&& this.tipId.equals(castOther.tipId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.estId.hashCode();
		hash = hash * prime + this.tipId.hashCode();
		
		return hash;
	}
}