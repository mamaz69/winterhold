package com.winterhold.dto.customer;

import com.winterhold.validation.UniqueMembershipNumber;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InsertCustomerDTO {

    @UniqueMembershipNumber(message = "Membership Number sudah ada.")
    @NotBlank(message = "Membership Number tidak boleh kosong.")
    @Size(max = 20, message = "Membership Number can't be more than 20 characters.")
    @Getter @Setter private String membershipNumber;

    @NotBlank(message = "Nama Depan tidak boleh kosong.")
    @Size(max = 50, message = "First Name can't be more than 50 characters.")
    @Getter @Setter private String firstName;

    @Size(max = 50, message = "Last Name can't be more than 50 characters.")
    @Getter @Setter private String lastName;

    @NotNull(message = "Tanggal lahir tidak boleh kosong.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate birthDate;

    @NotBlank(message = "Gender tidak boleh kosong.")
    @Size(max = 10,message = "Gender Name tidak boleh lebih dari 10.")
    @Getter @Setter private String gender;

    @Size(max = 20, message = "Phone can't be more than 20 characters.")
    @Getter @Setter private String phone;

    @Size(max = 500, message = "Address can't be more than 500 characters.")
    @Getter @Setter private String address;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate membershipExpireDate = LocalDate.now().plusYears(2);

}
