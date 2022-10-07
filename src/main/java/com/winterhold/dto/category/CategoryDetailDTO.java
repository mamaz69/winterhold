package com.winterhold.dto.category;

import com.winterhold.dto.book.BookGridDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryDetailDTO {
    @Getter @Setter private CategoryGridDTO category;
    @Getter @Setter private List<BookGridDTO> book;

}
