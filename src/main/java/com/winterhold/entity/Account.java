package com.winterhold.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @Column(name = "Username")
    @Getter @Setter private String username;

    @Column(name = "Password")
    @Getter @Setter private String password;

}