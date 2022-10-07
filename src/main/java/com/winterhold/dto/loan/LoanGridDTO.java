package com.winterhold.dto.loan;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LoanGridDTO {

    @Getter @Setter private Long id;
    @Getter @Setter private String bookTitle;
    @Getter @Setter private String customerName;
    @Getter @Setter private LocalDate loanDate;
    @Getter @Setter private LocalDate dueDate;
    @Getter @Setter private LocalDate returnDate;

}
