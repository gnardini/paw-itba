package ar.edu.itba.it.paw.model;

import javax.persistence.Entity;

@Entity
public class Neighbourhood extends PersistentEntity {

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
}
