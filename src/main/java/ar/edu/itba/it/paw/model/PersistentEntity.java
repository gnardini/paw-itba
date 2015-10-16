package ar.edu.itba.it.paw.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistentEntity {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	public int getId() {
		return id;
	}
	
	public boolean isNew() {
		return id == 0;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof PersistentEntity)) return false;
		System.out.println(getClass() + " " + obj.getClass());
		if (!getClass().equals(obj.getClass())) return false;
		return id == ((PersistentEntity) obj).getId();
	}
}
