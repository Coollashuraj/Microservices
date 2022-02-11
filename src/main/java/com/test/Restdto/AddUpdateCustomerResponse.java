package com.test.Restdto;

public class AddUpdateCustomerResponse {

	long CustomerId;

	public long getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(long customerId) {
		CustomerId = customerId;
	}

	Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public AddUpdateCustomerResponse(Long CustomerId, Status status) {
		this.CustomerId = CustomerId;
		this.status = status;
	}

}
