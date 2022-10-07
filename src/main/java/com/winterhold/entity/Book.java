package com.winterhold.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "Code")
    @Getter @Setter private String code;

    @Column(name = "Title")
    @Getter @Setter private String title;

    @Column(name = "CategoryName")
    @Getter @Setter private String categoryName;

    @ManyToOne
    @JoinColumn(name = "CategoryName", insertable = false, updatable = false)
    @Getter @Setter private Category category;

    @Column(name = "AuthorId")
    @Getter @Setter private Long authorId;

    @ManyToOne
    @JoinColumn(name = "AuthorId", insertable = false, updatable = false)
    @Getter @Setter private Author author;

    @Column(name = "IsBorrowed")
    @Getter @Setter private Boolean isBorrowed = false;

    @Column(name = "Summary")
    @Getter @Setter private String summary;

    @Column(name = "ReleaseDate")
    @Getter @Setter private LocalDate releaseDate;

    @Column(name = "TotalPage")
    @Getter @Setter private Integer totalPage;

    @OneToMany(mappedBy = "book",cascade = {CascadeType.REMOVE})
    @Getter @Setter private List<Loan> loans;

    public Book(String code, String title, String categoryName, Long authorId, Boolean isBorrowed, String summary, LocalDate releaseDate, Integer totalPage) {
        this.code = code;
        this.title = title;
        this.categoryName = categoryName;
        this.authorId = authorId;
        this.isBorrowed = isBorrowed;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}