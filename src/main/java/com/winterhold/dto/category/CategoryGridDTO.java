package com.winterhold.dto.category;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryGridDTO {

    @Getter @Setter private String name;
    @Getter @Setter private Integer floor;
    @Getter @Setter private String isle;
    @Getter @Setter private String bay;
    @Getter @Setter private Long totalBooks;

    public CategoryGridDTO(String name, Integer floor, String isle, String bay) {
        this.name = name;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
    }
}
