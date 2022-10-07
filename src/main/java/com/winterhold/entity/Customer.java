package com.winterhold.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "MembershipNumber")
    @Getter @Setter
    private String membershipNumber;

    @Column(name = "FirstName")
    @Getter @Setter private String firstName;

    @Column(name = "LastName")
    @Getter @Setter private String lastName;

    @Column(name = "BirthDate")
    @Getter @Setter private LocalDate birthDate;

    @Column(name = "Gender")
    @Getter @Setter private String gender;

    @Column(name = "Phone")
    @Getter @Setter private String phone;

    @Column(name = "Address")
    @Getter @Setter private String address;

    @Column(name = "MembershipExpireDate")
    @Getter @Setter private LocalDate membershipExpireDate;

    @OneToMany(mappedBy = "customer",cascade = {CascadeType.REMOVE})
    @Getter @Setter private List<Loan> loans;

    public Customer(String membershipNumber, String firstName, String lastName, LocalDate birthDate, String gender, String phone, String address, LocalDate membershipExpireDate) {
        this.membershipNumber = membershipNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.membershipExpireDate = membershipExpireDate;
    }
}