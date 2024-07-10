package com.onlinebanking.hackathon.dto;

import com.onlinebanking.hackathon.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public static   AccountDTO convertToDto(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setCustomerUsername(account.getCustomer().getUsername());
        dto.setCustomerFirstname(account.getCustomer().getFirstname());
        dto.setCustomerLastname(account.getCustomer().getLastname());
        dto.setCustomerEmail(account.getCustomer().getEmail());
        dto.setCustomerPhone(account.getCustomer().getPhone());
        return dto;
    }
}
