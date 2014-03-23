package com.etapa.etl.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the archivo database table.
 * 
 */
@Entity
@NamedQuery(name = "Archivo.findAll", query = "SELECT a FROM Archivo a")
public class Archivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "path")
	private String path;

	@Column(name = "last_position")
	private long lastPosition;

	public Archivo() {
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getLastPosition() {
		return this.lastPosition;
	}

	public void setLastPosition(long lastPosition) {
		this.lastPosition = lastPosition;
	}

}