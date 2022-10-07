package com.winterhold.dto.loan;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LoanDetailDTO {

    @Getter @Setter private BookGridDTO book;
    @Getter @Setter private CustomerGridDTO customer;

}
