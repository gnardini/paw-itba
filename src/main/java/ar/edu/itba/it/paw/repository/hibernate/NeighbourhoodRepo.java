package ar.edu.itba.it.paw.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.model.Neighbourhood;

@Repository
public class NeighbourhoodRepo extends AbstractHibernateRepo {
	
	@Autowired
	public NeighbourhoodRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public List<Neighbourhood> getAllNeighbourhoods() {
		return find("from Neighbourhood");
	}

	public Neighbourhood getNeighbourhood(Integer id) {
		return get(Neighbourhood.class, id);
	}
}
