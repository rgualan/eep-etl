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
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="arc_path")
	private String arcPath;

	@Column(name="arc_nbytes")
	private Long arcNbytes;

	public Archivo() {
	}

	public String getArcPath() {
		return this.arcPath;
	}

	public void setArcPath(String arcPath) {
		this.arcPath = arcPath;
	}

	public Long getArcNbytes() {
		return this.arcNbytes;
	}

	public void setArcNbytes(Long arcNbytes) {
		this.arcNbytes = arcNbytes;
	}

}