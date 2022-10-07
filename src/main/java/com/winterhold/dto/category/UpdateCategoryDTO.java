package com.winterhold.dto.category;

import com.winterhold.validation.UniqueCategoryName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateCategoryDTO {

    @NotBlank(message = "Category Name tidak boleh kosong.")
    @Size(max = 100,message = "Category Name tidak boleh lebih dari 100.")
    @Getter @Setter private String name;

    @NotNull(message = "Floor tidak boleh kosong.")
    @Getter @Setter private Integer floor;

    @NotBlank(message = "Isle tidak boleh kosong.")
    @Size(max = 10,message = "Category Name tidak boleh lebih dari 10.")
    @Getter @Setter private String isle;

    @NotBlank(message = "Bay tidak boleh kosong.")
    @Size(max = 10,message = "Category Name tidak boleh lebih dari 10.")
    @Getter @Setter private String bay;
}
