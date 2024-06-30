package com.onlinebanking.hackathon.repository;

import com.onlinebanking.hackathon.entity.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {


}
