package com.winterhold.dao;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {

    @Query("""
        SELECT new com.winterhold.dto.category.CategoryGridDTO(cat.name,cat.floor,cat.isle,cat.bay)
            FROM Category AS cat
            WHERE cat.name LIKE %:name%
            """)
    public List<CategoryGridDTO> findAll(@Param("name") String name, Pageable pageable);

    @Query("""
            SELECT COUNT(cat.name)
            FROM Category AS cat
            WHERE cat.name LIKE %:name%
            """)
    public Long count(@Param("name") String name);

    @Query("""
            SELECT COUNT(cat.name)
            FROM Category AS cat
            WHERE cat.name = :name
            """)
    public Long countCategory(@Param("name") String name);
}
