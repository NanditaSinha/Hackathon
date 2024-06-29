package com.onlinebanking.hackathon.Repository;

import com.onlinebanking.hackathon.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
