package ar.edu.itba.it.paw.model;

public class Restaurant {

	int code;
	String name;
	String address;
	String horario;
	int deliveryCost;
	int minCost;
	String description;
	
	public Restaurant(int code,
					String name,
					String address,
					String horario,
					int deliveryCost,
					int minCost,
					String description) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.horario = horario;
		this.deliveryCost = deliveryCost;
		this.minCost = minCost;
		this.description = description;
	}

	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getHorario() {
		return horario;
	}

	public int getDeliveryCost() {
		return deliveryCost;
	}

	public int getMinCost() {
		return minCost;
	}

	public String getDescription() {
		return description;
	}
}
