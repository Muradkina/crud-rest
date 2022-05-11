package com.mk.muradbank.repository;

import com.mk.muradbank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer,String > {
}
