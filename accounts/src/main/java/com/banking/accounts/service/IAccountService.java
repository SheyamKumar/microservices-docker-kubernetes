package com.banking.accounts.service;

import com.banking.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);

    CustomerDto getCustomerByMobileNumber(String mobileNumber);

    Boolean updateAccount(CustomerDto customerDto);

    Boolean deleteAccountByMobileNumber(String mobileNumber);
}
