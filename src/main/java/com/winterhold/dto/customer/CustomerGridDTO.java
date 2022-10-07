package com.winterhold.dto.customer;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CustomerGridDTO {

    @Getter @Setter private String membershipNumber;

    @Getter @Setter private String fullName;

    @Getter @Setter private LocalDate birthDate;

    @Getter @Setter private String gender;

    @Getter @Setter private String phone;

    @Getter @Setter private String address;

    @Getter @Setter private LocalDate membershipExpireDate;

}
