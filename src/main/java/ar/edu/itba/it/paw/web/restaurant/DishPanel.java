package ar.edu.itba.it.paw.web.restaurant;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.model.Dish;
import ar.edu.itba.it.paw.util.NumberUtils;

public class DishPanel extends Panel {

	private Dish dish;
	private IModel<String> dishCountModel;
	private TextField<String> dishCountTextField;
	
	public DishPanel(String id, ListItem<Dish> dishModel) {
		super(id);
		this.dish = dishModel.getModelObject();
		
		addLabel(dishModel, "name");
		addLabel(dishModel, "description");
		addLabel(dishModel, "price");
		
		dishCountModel = new Model<String>();
		dishCountTextField = new TextField<String>("dishCount", dishCountModel); 
		add(dishCountTextField);
	}
	
	public void addLabel(ListItem<Dish> dish, String string) {
		add(new Label(string, new PropertyModel<String>(dish.getModelObject(), string)));
	}
	
	public Dish getDish() {
		return dish;
	}
	
	public int getDishCount() {
		String dishCount = dishCountTextField.getModelObject();
		if (NumberUtils.isNumber(dishCount)) {
			return Integer.valueOf(dishCount);
		}
		return -1;
	}
	
}
