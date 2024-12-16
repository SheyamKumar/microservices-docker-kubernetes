package com.banking.accounts.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.boot.registry.selector.spi.StrategySelector;

@Data
@Entity
public class Customer extends AuditAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    private Long customerId;
    private String name;
    private String email;
    private String mobileNumber;


}
