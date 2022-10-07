package com.winterhold.dto.author;

import com.winterhold.validation.After;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@After(message="Tidak boleh mati sebelum lahir.",
        previousDateField="birthDate", subsequentDateField="deceasedDate")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateAuthorDTO {
    @Getter @Setter private Long id;

    @NotBlank(message = "Title tidak boleh kosong.")
    @Size(max = 10,message = "Title can't be more than 10 characters.")
    @Getter @Setter private String title;

    @NotBlank(message = "Nama Depan tidak boleh kosong.")
    @Size(max = 50,message = "First Name can't be more than 50 characters.")
    @Getter @Setter private String firstName;

    @Size(max = 50,message = "Last Name can't be more than 50 characters.")
    @Getter @Setter private String lastName;

    @NotNull(message = "Tanggal Lahir tidak boleh kosong.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate birthDate;//pakai type date di html

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate deceasedDate;//pakai type date di html

    @Size(max = 50,message = "Title can't be more than 50 characters.")
    @Getter @Setter private String education;

    @Size(max = 500,message = "Title can't be more than 500 characters.")
    @Getter @Setter private String summary;
}
