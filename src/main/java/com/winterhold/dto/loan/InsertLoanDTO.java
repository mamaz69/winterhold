package com.winterhold.dto.loan;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InsertLoanDTO {

    @Getter @Setter private Long id;

    @NotBlank(message = "please choose customer.")
    @Getter @Setter private String customerNumber;

    @NotBlank(message = "please choose book.")
    @Getter @Setter private String bookCode;

    @NotNull(message = "Loan date tidak boleh kosong.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate loanDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate dueDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate returnDate;

    @Size(max = 500, message = "Note can't be more than 500 characters.")
    @Getter @Setter private String note;

}
