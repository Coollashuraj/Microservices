package repository;

import org.springframework.data.repository.CrudRepository;

import controller.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
