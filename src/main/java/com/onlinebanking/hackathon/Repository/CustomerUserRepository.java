package com.onlinebanking.hackathon.Repository;

import com.onlinebanking.hackathon.Entity.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {


}
