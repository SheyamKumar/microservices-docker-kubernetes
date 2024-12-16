package com.banking.accounts.controller;

import com.banking.accounts.constant.AccountConstants;
import com.banking.accounts.dto.ContactInfoDto;
import com.banking.accounts.dto.CustomerDto;
import com.banking.accounts.dto.ErrorResponseDto;
import com.banking.accounts.dto.ResponseDto;
import com.banking.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/account",produces= MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name="CRUD API to create Account and Customer Information",description = "CRUD API to create Account and Customer Information")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ContactInfoDto contactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Operation(summary = "Create Account",description = "Creates Customer and Account")
    @ApiResponse(responseCode = "200",description = "Created Successfully")
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201,AccountConstants.MESSAGE_201));
    }

    @Operation(summary = "Find customer by mobile number",description = "Find customer by mobile number")
    @ApiResponse(responseCode = "200",description = "Customer found successfully")
    @GetMapping(path="/fetchByMobileNumber")
    public ResponseEntity<CustomerDto> getCustomerByMobileNumber(@RequestParam
                                                                     @Pattern(regexp = "(^[0-9]{10}$)",message = "Mobile Number should be 10 Digit")
                                                                     String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getCustomerByMobileNumber(mobileNumber));
    }

    @Operation(summary = "Updated Account",description = "Update Customer and Account")
    @ApiResponse(responseCode = "200",description = "Updated Successfully")
    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto> updateCustomerInfo(@Valid @RequestBody CustomerDto customerDto) {
        Boolean isUpdated = accountService.updateAccount(customerDto);
        if(isUpdated)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500,AccountConstants.MESSAGE_500));
    }

    @Operation(summary = "Delete Account by mobile number",description = "Delete Account by by mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Account and customer Deleted successfully"),
            @ApiResponse(responseCode = "500",description = "Internal Error",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDto> deleteAccountByMobileNumber(@RequestParam @Pattern(regexp = "(^[0-9]{10}$)",message = "Mobile Number should be 10 Digit") String mobileNumber) {
        Boolean isDeleted = accountService.deleteAccountByMobileNumber(mobileNumber);
        if(isDeleted)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500,AccountConstants.MESSAGE_500));
    }


    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into cards microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get Java version",
            description = "Get Java versions details that is installed into cards microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<ContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactInfoDto);
    }

}
