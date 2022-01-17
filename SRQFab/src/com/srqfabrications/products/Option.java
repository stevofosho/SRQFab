package com.srqfabrications.products;

public class Option {
	
	private String name;
	private double price;
	
	public Option() {
		this.name = "";
		this.price = 0;
	}
	
	public Option(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Option [name=" + name + ", price=" + price + "]";
	}


	
}
