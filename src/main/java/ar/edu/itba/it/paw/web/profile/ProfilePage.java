package ar.edu.itba.it.paw.web.profile;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.model.Neighbourhood;
import ar.edu.itba.it.paw.repository.NeighbourhoodRepo;
import ar.edu.itba.it.paw.repository.UserRepo;
import ar.edu.itba.it.paw.util.Parameter;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ProfilePage extends BasePage {
	
	private transient String email;
	private transient String oldPassword;
	private transient String newPassword;
	private transient String firstName;
	private transient String lastName;
	private transient String address;
	private transient Integer birthDay;
	private transient Integer birthMonth;
	private transient Integer birthYear;
	private transient Neighbourhood neighbourhood;
	
	@SpringBean
	NeighbourhoodRepo neighbourhoodRepo;
	
	@SpringBean
	UserRepo userRepo;
	
	public ProfilePage(){
		add(new Label("pageName", "Perfil"));
		
		Form<ProfilePage> form = new Form<ProfilePage>("editForm",
				new CompoundPropertyModel<ProfilePage>(this)) {

			@Override
			protected void onSubmit() {
				if(!loggedUser.getPassword().equals(oldPassword)){
					showMessage("Contraseña incorrecta", Parameter.ERROR);
					return;
				}
				loggedUser.setEmail(email);
				if(newPassword!=null){
					loggedUser.setPassword(newPassword);
				}
				loggedUser.setFirstName(firstName);
				loggedUser.setLastName(lastName);
				loggedUser.setAddress(address);
				loggedUser.setBirthDay(birthDay);
				loggedUser.setBirthMonth(birthMonth);
				loggedUser.setBirthYear(birthYear);
				loggedUser.setNeighbourhood(neighbourhood);
				userRepo.updateUser(loggedUser);
				showMessage("Cambios Guardados", Parameter.SUCCESS);
			}
		};
		
		DropDownChoice<Neighbourhood> neighbourhoodDropDown = 
	            new DropDownChoice<Neighbourhood>("neighbourhood", 
	                    new PropertyModel<Neighbourhood>(this, "neighbourhood"),
	                    neighbourhoodRepo.getAllNeighbourhoods());
		neighbourhoodDropDown.setRequired(true);
		
		email=loggedUser.getEmail();
		firstName=loggedUser.getFirstName();
		lastName=loggedUser.getLastName();
		address=loggedUser.getAddress();
		neighbourhood=loggedUser.getNeighbourhood();
		birthDay=loggedUser.getBirthDay();
		birthMonth=loggedUser.getBirthMonth();
		birthYear=loggedUser.getBirthYear();
		form.add(new EmailTextField("email").setRequired(true));
		form.add(new PasswordTextField("oldPassword").setRequired(true));
		form.add(new PasswordTextField("newPassword").setRequired(false));
		form.add(new TextField<String>("firstName").setRequired(true));
		form.add(new TextField<String>("lastName").setRequired(true));
		form.add(new TextField<String>("address").setRequired(true));
		form.add(neighbourhoodDropDown);
		
		NumberTextField<Integer> birthDayField = new NumberTextField<Integer>("birthDay");
		NumberTextField<Integer> birthMonthField = new NumberTextField<Integer>("birthMonth");
		NumberTextField<Integer> birthYearField = new NumberTextField<Integer>("birthYear");
		form.add(birthDayField.setRequired(true));
		form.add(birthMonthField.setRequired(true));
		form.add(birthYearField.setRequired(true));
		//form.add(new DateValidator(birthDayField, birthMonthField, birthYearField));
		form.add(new Button("saveChangesButton", new ResourceModel("saveChangesButton")));
		add(form);
	}

}
