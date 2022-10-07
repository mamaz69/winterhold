package com.winterhold.dto.book;

import com.winterhold.entity.Author;
import com.winterhold.entity.Category;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BookGridDTO {

    @Getter
    @Setter
    private String code;

    @Getter @Setter private String title;
    @Getter @Setter private String categoryName;

    @Getter @Setter private String authorName;
    @Getter @Setter private Boolean isBorrowed;
    @Getter @Setter private String borrowed;

    @Getter @Setter private String summary;

    @Getter @Setter private LocalDate releaseDate;
    @Getter @Setter private Integer totalPage;
    @Getter @Setter private Integer floor;
    @Getter @Setter private String isle;
    @Getter @Setter private String bay;

    public BookGridDTO(String code, String title, String categoryName, String authorName, Boolean isBorrowed, String summary, LocalDate releaseDate, Integer totalPage) {
        this.code = code;
        this.title = title;
        this.categoryName = categoryName;
        this.authorName = authorName;
        this.isBorrowed = isBorrowed;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }

    public BookGridDTO(String code, String title, String categoryName, String authorName, Boolean isBorrowed, String summary, LocalDate releaseDate, Integer totalPage, Integer floor, String isle, String bay) {
        this.code = code;
        this.title = title;
        this.categoryName = categoryName;
        this.authorName = authorName;
        this.isBorrowed = isBorrowed;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
    }
}
