package com.winterhold.dto.author;


import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthorGridDTO {


    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String fullName;

    @Getter
    @Setter
    private LocalDate birthDate;

    @Getter
    @Setter
    private Long age;

    @Getter
    @Setter
    private LocalDate deceasedDate;

    @Getter
    @Setter
    private String status; //ini buat status dipakai di service

    @Getter
    @Setter
    private String education;

    @Getter
    @Setter
    private String summary;

    public AuthorGridDTO(Long id, String title, String firstName, String lastName, LocalDate birthDate, LocalDate deceasedDate, String education, String summary) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deceasedDate = deceasedDate;
        this.education = education;
        this.summary = summary;
    }
}
