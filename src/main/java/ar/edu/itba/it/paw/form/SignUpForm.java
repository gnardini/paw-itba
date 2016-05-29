package ar.edu.itba.it.paw.form;

import ar.edu.itba.it.paw.model.Users;
import ar.edu.itba.it.paw.model.Users.Role;
import ar.edu.itba.it.paw.repository.hibernate.NeighbourhoodRepo;

public class SignUpForm {

	String firstName;
	String lastName;
	String address;
	String email;
	String birthDay;
	String birthMonth;
	String birthYear;
	String password;
	String neighbourhoodId;
	
	public SignUpForm() {
	}
	
	public Users build(NeighbourhoodRepo neighbourhoodRepo) {
		return new Users(firstName, lastName, address, email, Integer.valueOf(birthDay), Integer.valueOf(birthMonth), Integer.valueOf(birthYear), Role.NORMAL, password, neighbourhoodRepo.getNeighbourhood(Integer.valueOf(neighbourhoodId)));
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNeighbourhoodId() {
		return neighbourhoodId;
	}
	
	public void setNeighbourhoodId(String neighbourhoodId) {
		this.neighbourhoodId = neighbourhoodId;
	}
}
