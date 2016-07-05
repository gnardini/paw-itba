package ar.edu.itba.it.paw.validator;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;

import ar.edu.itba.it.paw.util.DateUtils;

public class DateValidator extends AbstractFormValidator {

	NumberTextField<Integer> day;
	NumberTextField<Integer> month;
	NumberTextField<Integer> year;

	public DateValidator(
			NumberTextField<Integer> day,
			NumberTextField<Integer> month,
			NumberTextField<Integer> year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return new FormComponent<?>[] {day, month, year};
	}
	
	@Override
	public void validate(Form<?> form) {
		Integer dayInt = day.getModelObject();
		Integer monthInt = month.getModelObject();
		Integer yearInt = year.getModelObject();
		if (dayInt == null) {
			error(day);
		} else if (monthInt == null) {
			error(month);
		} else if (yearInt == null) {
			error(year);
		} else if (!DateUtils.isDate(dayInt, monthInt, yearInt)) {
			error(day);
		}
	}
	
}
