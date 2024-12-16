package com.banking.accounts.service.impl;

import com.banking.accounts.Mapper.IAccountMapper;
import com.banking.accounts.Mapper.ICustomerMapper;
import com.banking.accounts.constant.AccountConstants;
import com.banking.accounts.dto.AccountDto;
import com.banking.accounts.dto.CustomerDto;
import com.banking.accounts.entity.Account;
import com.banking.accounts.entity.Customer;
import com.banking.accounts.exception.CustomerAlreadyExistException;
import com.banking.accounts.exception.ResourceNotFoundException;
import com.banking.accounts.repo.AccountRepo;
import com.banking.accounts.repo.CustomerRepo;
import com.banking.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepo accountRepo;
    private CustomerRepo customerRepo;
    private ICustomerMapper customerMapper;
    private IAccountMapper accountMapper;


    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = customerMapper.mapToCustomer(customerDto);
        checkIfCustomerAlreadyExist(customer);
        Customer savedCustomer = customerRepo.save(customer);
        accountRepo.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        account.setAccountNumber(1000000000L + new Random().nextInt(1000000000));
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);
        return account;
    }

    private void checkIfCustomerAlreadyExist(Customer customer) {
        Optional<Customer> byMobileNumber = customerRepo.findByMobileNumber(customer.getMobileNumber());
        if(byMobileNumber.isPresent()) {
            throw new CustomerAlreadyExistException("Customer already existed for the given mobile number:"+customer.getMobileNumber());
        }
    }

    @Override
    public CustomerDto getCustomerByMobileNumber(String mobileNumber) {
        Optional<Customer> byMobileNumber = customerRepo.findByMobileNumber(mobileNumber);

        if(byMobileNumber.isPresent()) {
            Customer customer = byMobileNumber.get();
            Account account = accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(() ->
                    new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
           return customerMapper.mapToCustomerDto(customer,account);
        } else {
            throw new ResourceNotFoundException("Customer","mobileNumber",mobileNumber.toString());
        }

    }

    @Override
    public Boolean updateAccount(CustomerDto customerDto) {
        AccountDto accountDto = customerDto.getAccountDto();
        if(Optional.of(accountDto).isPresent()) {
            Account account = accountRepo.findById(accountDto.getAccountNumber()).orElseThrow(()->
                    new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber().toString()));
            Customer customer = customerRepo.findById(account.getCustomerId()).orElseThrow(()->
                    new ResourceNotFoundException("Customer", "accountNumber", accountDto.getAccountNumber().toString()));

            accountMapper.mapToAccount(accountDto, account);
            accountRepo.save(account);
            customerMapper.mapToCustomer(customerDto,customer);
            customerRepo.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteAccountByMobileNumber(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(()->
                new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber.toString()));
        customerRepo.deleteById(customer.getCustomerId());
        accountRepo.deleteByCustomerId(customer.getCustomerId());
        return true;
    }
}
