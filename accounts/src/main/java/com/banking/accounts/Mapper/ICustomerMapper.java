package com.banking.accounts.Mapper;

import com.banking.accounts.dto.CustomerDto;
import com.banking.accounts.entity.Account;
import com.banking.accounts.entity.Customer;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring"
        ,nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        ,unmappedTargetPolicy = ReportingPolicy.IGNORE
        ,uses = IAccountMapper.class
, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ICustomerMapper {

    Customer mapToCustomer(CustomerDto customerDto);

    @Mapping(source = "customer.mobileNumber",target = "mobileNumber")
    @Mapping(source = "account",target = "accountDto")
    CustomerDto mapToCustomerDto(Customer customer, Account account);

    void mapToCustomer(CustomerDto customerDto, @MappingTarget Customer customer);
}
