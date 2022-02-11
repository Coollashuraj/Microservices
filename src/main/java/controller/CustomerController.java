package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.Restdto.AddUpdateCustomerRequest;
import com.test.Restdto.AddUpdateCustomerResponse;
import com.test.Restdto.GetCustomerResponse;
import com.test.Restdto.Error;

@ RestController
@ RequestMapping (value="/CustomerService/", consumes= MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE )
public class CustomerController {
	 private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	 private final CustomerService customerService;

	  @Autowired
	 private CustomerController(final CustomerService customerService)
	 {
		this.customerService = customerService;
		}
	 
		  
	 @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseBody 
	 public ResponseEntity addCustomer(
	      @RequestBody @NonNull AddUpdateCustomerRequest addUpdateCustomerRequest) {

	    AddUpdateCustomerResponse addUpdateCustomerResponse = customerService
	        .addCustomer(addUpdateCustomerRequest);

	    if (addUpdateCustomerResponse == null) {
	      return getGetCustomerResponseEntity("Error adding a new Customer");
	    }

	    log.info("Successfully added Customer={} {} with  status={}",
	        addUpdateCustomerRequest.getFirstName(),
	        addUpdateCustomerRequest.getLastName(),
	        addUpdateCustomerResponse.getStatus().name());
	    return new ResponseEntity(addUpdateCustomerResponse, HttpStatus.CREATED);
	  }
	 
	 

	 @GetMapping(value ="/customer/{customerId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity getCustomerById(@PathVariable @NonNull Long CustomerId) {

	    try {
	      GetCustomerResponse getCustomerResponse = customerService.getCustomerById(CustomerId);
	      if (getCustomerResponse == null) {
	        return getGetCustomerResponseEntity("Error finding Customer Id=" + CustomerId);
	      }
	      log.info("Successfully found Customer={} {} with id={}",
	          getCustomerResponse.getFirstName(), getCustomerResponse.getLastName(), CustomerId);
	      return new ResponseEntity(getCustomerResponse, HttpStatus.OK);
	    } catch (Exception e) {
	      return getGetCustomerResponseEntity(e.getMessage());
	    }
	  }
	  
	  private ResponseEntity getGetCustomerResponseEntity(
		      String errorMessage) {
		    final Error error = new Error(HttpStatus.BAD_REQUEST, errorMessage);
		    log.error(error.getErrorMessage());
		    return new ResponseEntity(error, error.getHttpStatus());
		  }
}
