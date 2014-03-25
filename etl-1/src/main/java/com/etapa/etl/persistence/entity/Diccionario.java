package com.etapa.etl.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the diccionario database table.
 * 
 */
@Entity
@NamedQuery(name="Diccionario.findAll", query="SELECT d FROM Diccionario d")
public class Diccionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DiccionarioPK id;

	//bi-directional many-to-one association to Sinonimo
	@OneToMany(mappedBy="diccionario")
	private List<Sinonimo> sinonimos;

	public Diccionario() {
	}

	public DiccionarioPK getId() {
		return this.id;
	}

	public void setId(DiccionarioPK id) {
		this.id = id;
	}

	public List<Sinonimo> getSinonimos() {
		return this.sinonimos;
	}

	public void setSinonimos(List<Sinonimo> sinonimos) {
		this.sinonimos = sinonimos;
	}

	public Sinonimo addSinonimo(Sinonimo sinonimo) {
		getSinonimos().add(sinonimo);
		sinonimo.setDiccionario(this);

		return sinonimo;
	}

	public Sinonimo removeSinonimo(Sinonimo sinonimo) {
		getSinonimos().remove(sinonimo);
		sinonimo.setDiccionario(null);

		return sinonimo;
	}

}