package com.winterhold.dao;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {


    @Query("""
            SELECT new com.winterhold.dto.author.AuthorGridDTO(aut.id,aut.title,aut.firstName,
            aut.lastName,aut.birthDate,aut.deceasedDate,aut.education,aut.summary)
            FROM Author AS aut
            WHERE aut.firstName LIKE %:name% OR aut.lastName LIKE %:name%
            """)
    public List<AuthorGridDTO> findAll(@Param("name") String name, Pageable pageable);

    @Query("""
            SELECT COUNT(aut.id)
            FROM Author AS aut
            WHERE aut.firstName LIKE %:name% OR aut.lastName LIKE %:name%
            """)
    public Long count(@Param("name") String name);


    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(aut.id, CONCAT(aut.firstName,' ',aut.lastName))
            FROM Author AS aut
            ORDER BY aut.firstName
            """)
    public List<DropdownDTO> findAllOrderByName();


}
