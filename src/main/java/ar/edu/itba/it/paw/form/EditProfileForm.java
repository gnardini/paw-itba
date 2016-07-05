package ar.edu.itba.it.paw.form;

import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.repository.hibernate.HibernateNeighbourhoodRepo;

public class EditProfileForm {
	
	Users user;
	String firstName;
	String lastName;
	String address;
	String email;
	String birthDay;
	String birthMonth;
	String birthYear;
	String oldPassword;
	String newPassword;
	String neighbourhoodId;
	
	public EditProfileForm() {
	}
	
	public Users build(HibernateNeighbourhoodRepo neighbourhoodRepo) {
		if (!passwordsMatch()) return null;
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAddress(address);
		user.setEmail(email);
		user.setBirthDay(Integer.parseInt(birthDay));
		user.setBirthMonth(Integer.parseInt(birthMonth));
		user.setBirthYear(Integer.parseInt(birthYear));
		if (!newPassword.equals("")) user.setPassword(newPassword);
		user.setNeighbourhood(neighbourhoodRepo.getNeighbourhood(Integer.valueOf(neighbourhoodId)));
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	public boolean passwordsMatch() {
		return user.getPassword().equals(oldPassword);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getOldPassword() {
		return oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getNeighbourhoodId() {
		return neighbourhoodId;
	}
	
	public void setNeighbourhoodId(String neighbourhoodId) {
		this.neighbourhoodId = neighbourhoodId;
	}
}
