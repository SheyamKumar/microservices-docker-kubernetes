package com.banking.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Account extends AuditAttributes {
    @Id
    private Long accountNumber;
    private Long customerId;
    private String accountType;
    private String branchAddress;


}
