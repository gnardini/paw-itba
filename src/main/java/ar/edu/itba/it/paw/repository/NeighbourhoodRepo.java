package ar.edu.itba.it.paw.repository;

import java.util.List;

import ar.edu.itba.it.paw.model.Neighbourhood;

public interface NeighbourhoodRepo {

	public List<Neighbourhood> getAllNeighbourhoods();

	public Neighbourhood getNeighbourhood(Integer id);
	
}
