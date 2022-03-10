package com.maxtrain.bootcamp.sales.order;

import javax.persistence.*;

import com.maxtrain.bootcamp.sales.customer.Customer;

@Entity


public class Orders {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(columnDefinition="int")
	private int id;
	@Column(length=30, nullable=false)
	private String description;
	@Column(columnDefinition="decimal(9,2) NOT NULL DEFAULT 0.0")
	private double total;
	@Column(length=30, nullable=false)
	private String status;
	
	

	@ManyToOne(optional=false)
	@JoinColumn(name="customerId")
	private Customer customer;
	
	public Orders() {}

		
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
