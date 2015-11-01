package ar.edu.itba.it.paw.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.util.NumberUtils;

@Component
public class NeighbourhoodConverter implements Converter<String, Neighbourhood> {
	
	private NeighbourhoodRepo neighbourhoodsRepo;
	
	@Autowired
	public NeighbourhoodConverter(NeighbourhoodRepo neighbourhoodsRepo) {
		this.neighbourhoodsRepo = neighbourhoodsRepo;
	}
	
	@Override
	public Neighbourhood convert(String neighbourhoodIdString) {
		if ((neighbourhoodIdString == null || !NumberUtils.isNumber(neighbourhoodIdString))) {
			return null;
		}
		int neighbourhoodId = Integer.valueOf(neighbourhoodIdString);
		return neighbourhoodsRepo.getNeighbourhood(neighbourhoodId);
	}
}
