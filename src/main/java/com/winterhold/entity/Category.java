package com.winterhold.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @Column(name = "Name")
    @Getter @Setter private String name;

    @Column(name = "Floor")
    @Getter @Setter
    private Integer floor;

    @Column(name = "Isle")
    @Getter @Setter private String isle;

    @Column(name = "Bay")
    @Getter @Setter private String bay;
}