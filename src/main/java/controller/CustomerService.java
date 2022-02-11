package controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.Restdto.AddUpdateCustomerRequest;
import com.test.Restdto.AddUpdateCustomerResponse;
import com.test.Restdto.GetCustomerResponse;
import com.test.Restdto.Status;

import repository.CustomerRepository;

public class CustomerService {

	private final CustomerRepository customerRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	public CustomerService(final CustomerRepository CustomerRepository) {
		this.customerRepository = CustomerRepository;
	}

	public AddUpdateCustomerResponse addCustomer(AddUpdateCustomerRequest addUpdateCustomerRequest) {
		Customer Customer = modelMapper.map(addUpdateCustomerRequest, Customer.class);
		Customer.setStatus(Status.ACTIVE);
		try {
			customerRepository.save(Customer);
		} catch (Exception e) {
			return null;
		}
		return new AddUpdateCustomerResponse(Customer.getCustomerId(), Customer.getStatus());
	}

	public GetCustomerResponse getCustomerById(Long CustomerId) {
		Optional<Customer> Customer = customerRepository.findById(CustomerId);
		return Customer.map(value -> modelMapper.map(value, GetCustomerResponse.class)).orElse(null);
	}

	public AddUpdateCustomerResponse updateCustomerById(AddUpdateCustomerRequest addUpdateCustomerRequest,
			Long CustomerId) {
		Optional<Customer> savedCustomer;
		try {
			savedCustomer = customerRepository.findById(CustomerId);
			if (!savedCustomer.isPresent()) {
				return null;
			}
			mapCustomerRequestToSavedCustomer(addUpdateCustomerRequest, savedCustomer.get());
			customerRepository.save(savedCustomer.get());
		} catch (Exception e) {
			return null;
		}
		return new AddUpdateCustomerResponse(savedCustomer.get().getCustomerId(), savedCustomer.get().getStatus());
	}

	private void mapCustomerRequestToSavedCustomer(AddUpdateCustomerRequest addUpdateStudentRequest,
			Customer savedCustomer) {
		savedCustomer.setFirstName(addUpdateStudentRequest.getFirstName());
		savedCustomer.setLastName(addUpdateStudentRequest.getLastName());
		savedCustomer.setDateOfBirth(addUpdateStudentRequest.getDateOfBirth());

		if (addUpdateStudentRequest.getStatus() != null) {
			savedCustomer.setStatus(addUpdateStudentRequest.getStatus());
		}
	}
}
