package ar.edu.itba.it.paw.model;

import java.io.Serializable;
import java.util.Date;

public class OrderDate implements Serializable {

	private int day;
	private int month;
	private int year;
	
	public OrderDate(Date date) {
		this.day = date.getDate();
		this.month = date.getMonth() + 1;
		this.year = date.getYear() + 1900;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDate other = (OrderDate) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return day + "/" + month + "/" + year;
	}
	
}
