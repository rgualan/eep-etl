package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sinonimo database table.
 * 
 */
@Entity
@NamedQuery(name="Sinonimo.findAll", query="SELECT s FROM Sinonimo s")
public class Sinonimo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SinonimoPK id;

	//bi-directional many-to-one association to Diccionario
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="dic_id", referencedColumnName="dic_id"),
		@JoinColumn(name="dic_tipo", referencedColumnName="dic_tipo")
		})
	private Diccionario diccionario;

	public Sinonimo() {
	}

	public SinonimoPK getId() {
		return this.id;
	}

	public void setId(SinonimoPK id) {
		this.id = id;
	}

	public Diccionario getDiccionario() {
		return this.diccionario;
	}

	public void setDiccionario(Diccionario diccionario) {
		this.diccionario = diccionario;
	}

}