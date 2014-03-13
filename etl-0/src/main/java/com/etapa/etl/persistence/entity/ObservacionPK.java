package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the observacion database table.
 * 
 */
@Embeddable
public class ObservacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name="obs_fecha")
	private java.util.Date obsFecha;

	@Column(name="est_id", insertable=false, updatable=false)
	private String estId;

	@Column(name="tip_id", insertable=false, updatable=false)
	private Integer tipId;

	@Column(name="uni_id", insertable=false, updatable=false)
	private String uniId;

	@Column(name="fen_id", insertable=false, updatable=false)
	private String fenId;

	public ObservacionPK() {
	}
	public java.util.Date getObsFecha() {
		return this.obsFecha;
	}
	public void setObsFecha(java.util.Date obsFecha) {
		this.obsFecha = obsFecha;
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
	public String getUniId() {
		return this.uniId;
	}
	public void setUniId(String uniId) {
		this.uniId = uniId;
	}
	public String getFenId() {
		return this.fenId;
	}
	public void setFenId(String fenId) {
		this.fenId = fenId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ObservacionPK)) {
			return false;
		}
		ObservacionPK castOther = (ObservacionPK)other;
		return 
			this.obsFecha.equals(castOther.obsFecha)
			&& this.estId.equals(castOther.estId)
			&& this.tipId.equals(castOther.tipId)
			&& this.uniId.equals(castOther.uniId)
			&& this.fenId.equals(castOther.fenId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.obsFecha.hashCode();
		hash = hash * prime + this.estId.hashCode();
		hash = hash * prime + this.tipId.hashCode();
		hash = hash * prime + this.uniId.hashCode();
		hash = hash * prime + this.fenId.hashCode();
		
		return hash;
	}
}