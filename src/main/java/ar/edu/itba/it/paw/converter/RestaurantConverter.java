package ar.edu.itba.it.paw.converter;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.model.Restaurant;

@Component
public class RestaurantConverter /*implements Converter<String, Restaurant> */{
/*
	private SubjectRepo subjects;

	@Autowired
	public SubjectConverter(SubjectRepo subjects) {
		this.subjects = subjects;
	}

	public Subject convert(String arg0) {
		return subjects.get(Integer.valueOf(arg0));
	}
	*/
}