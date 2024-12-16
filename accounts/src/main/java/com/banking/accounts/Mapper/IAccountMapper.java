package com.banking.accounts.Mapper;


import com.banking.accounts.dto.AccountDto;
import com.banking.accounts.entity.Account;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Component
@Mapper(componentModel = "spring"
        ,nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAccountMapper  {

    IAccountMapper ACCOUNT_MAPPER = Mappers.getMapper(IAccountMapper.class);

    @Mapping(source = "accountNumber",target = "accountNumber")
    AccountDto mapToAccountDto(Account account);

    Account mapToAccount(AccountDto accountDto);

    void mapToAccount(AccountDto accountDto,@MappingTarget Account account);

}
