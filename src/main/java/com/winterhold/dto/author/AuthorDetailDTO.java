package com.winterhold.dto.author;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.entity.Author;
import com.winterhold.entity.Book;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthorDetailDTO {

    @Getter @Setter private AuthorGridDTO author;
    @Getter @Setter private List<BookGridDTO> books;
}
