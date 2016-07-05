package ar.edu.itba.it.paw.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Neighbourhood extends PersistentEntity implements Serializable {

	String name;
	
	public Neighbourhood() {
	}
	
	public Neighbourhood(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
