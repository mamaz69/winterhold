package com.winterhold.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @Getter @Setter private Long id;

    @Column(name = "Title")
    @Getter @Setter private String title;

    @Column(name = "FirstName")
    @Getter @Setter private String firstName;

    @Column(name = "LastName")
    @Getter @Setter private String lastName;

    @Column(name = "BirthDate")
    @Getter @Setter private LocalDate birthDate;

    @Column(name = "DeceasedDate")
    @Getter @Setter private LocalDate deceasedDate;

    @Column(name = "Education")
    @Getter @Setter private String education;

    @Column(name = "Summary")
    @Getter @Setter private String summary;

}