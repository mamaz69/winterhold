package com.winterhold.dto.book;

import com.winterhold.validation.UniqueCode;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InsertBookDTO {

    //kasih validasi unique code
    @UniqueCode(message = "Code Buku sudah ada.")
    @NotBlank(message = "Code tidak boleh kosong.")
    @Size(max = 20, message = "Code can't be more than 20 characters.")
    @Getter @Setter private String code;

    @NotBlank(message = "Title tidak boleh kosong.")
    @Size(max = 100, message = "Code can't be more than 100 characters.")
    @Getter @Setter private String title;

    @NotBlank(message = "Category Name tidak boleh kosong.")
    @Size(max = 100, message = "Code can't be more than 100 characters.")
    @Getter @Setter private String categoryName;

    @NotNull(message = "author tidak boleh kosong.")
    @Getter @Setter private Long authorId;

    @NotNull
    @Getter @Setter private Boolean isBorrowed = false;

    @Size(max = 500, message = "Code can't be more than 500 characters.")
    @Getter @Setter private String summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter private LocalDate releaseDate;

    @Getter @Setter private Integer totalPage;

    public InsertBookDTO(String code, String title, String categoryName, Long authorId, String summary, LocalDate releaseDate, Integer totalPage) {
        this.code = code;
        this.title = title;
        this.categoryName = categoryName;
        this.authorId = authorId;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}
