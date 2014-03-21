package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the archivo database table.
 * 
 */
@Entity
@NamedQuery(name="Archivo.findAll", query="SELECT a FROM Archivo a")
public class Archivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="arc_path")
	private String arcPath;

	@Column(name="arc_nbytes")
	private float arcNbytes;

	public Archivo() {
	}

	public String getArcPath() {
		return this.arcPath;
	}

	public void setArcPath(String arcPath) {
		this.arcPath = arcPath;
	}

	public float getArcNbytes() {
		return this.arcNbytes;
	}

	public void setArcNbytes(float arcNbytes) {
		this.arcNbytes = arcNbytes;
	}

}