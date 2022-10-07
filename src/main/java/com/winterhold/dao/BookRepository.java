package com.winterhold.dao;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,String> {

    @Query("""
            SELECT new com.winterhold.dto.book.BookGridDTO(bo.code,bo.title,cat.name,CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName),bo.isBorrowed,bo.summary,bo.releaseDate,bo.totalPage)
            FROM Book AS bo
                JOIN bo.author AS aut
                JOIN bo.category AS cat
            WHERE bo.authorId = :authorId
            """)
    public List<BookGridDTO> findAllByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.book.BookGridDTO(bo.code,bo.title,cat.name,CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName),bo.isBorrowed,bo.summary,bo.releaseDate,bo.totalPage)
            FROM Book AS bo
                JOIN bo.author AS aut
                JOIN bo.category AS cat
            WHERE bo.authorId = :authorId
            """)
    public List<BookGridDTO> findAllByAuthorId(@Param("authorId") Long authorId);

    @Query("""
            SELECT COUNT(bo.code)
            FROM Book AS bo
                JOIN bo.author AS aut
                JOIN bo.category AS cat
            WHERE bo.authorId = :authorId
            """)
    public Long countByAuthorId(@Param("authorId") Long authorId);

    @Query("""
            SELECT new com.winterhold.dto.book.BookGridDTO(bo.code,bo.title,cat.name,CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName),bo.isBorrowed,bo.summary,bo.releaseDate,bo.totalPage)
            FROM Book AS bo
                JOIN bo.author AS aut
                JOIN bo.category AS cat
            WHERE bo.categoryName = :categoryName AND 
                    bo.title LIKE %:title% AND
                     (aut.firstName LIKE %:authorName% OR aut.lastName LIKE %:authorName%)
            """)
    public List<BookGridDTO> findAllByCategoryName(@Param("categoryName") String categoryName,
                                                   @Param("title") String title,
                                                   @Param("authorName") String authorName,
                                                   Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.book.BookGridDTO(bo.code,bo.title,cat.name,CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName),bo.isBorrowed,bo.summary,bo.releaseDate,bo.totalPage)
            FROM Book AS bo
                JOIN bo.author AS aut
                JOIN bo.category AS cat
            WHERE bo.categoryName = :categoryName
            """)
    public List<BookGridDTO> findAllByCategoryName(@Param("categoryName") String categoryName);

    @Query("""
            SELECT new com.winterhold.dto.book.BookGridDTO(bo.code,bo.title,
                                               cat.name,CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName),
                                               bo.isBorrowed,bo.summary,bo.releaseDate,bo.totalPage,
                                               cat.floor,cat.isle,cat.bay)
             FROM Loan AS lo
                RIGHT JOIN lo.book AS bo
                JOIN bo.author AS aut
                JOIN bo.category AS cat
            WHERE lo.id = :id
            """)
    public BookGridDTO findAllByLoanId(@Param("id") Long id);

    @Query("""
            SELECT COUNT(bo.code)
            FROM Book AS bo
                JOIN bo.author AS aut
                JOIN bo.category AS cat
            WHERE bo.categoryName = :categoryName AND 
                    bo.title LIKE %:title% AND
                     (aut.firstName LIKE %:authorName% OR aut.lastName LIKE %:authorName%)
            """)
    public Long countByCategoryName(@Param("categoryName") String categoryName,
                                    @Param("title") String title,
                                    @Param("authorName") String authorName);


    @Query("""
            SELECT COUNT(bo.code)
            FROM Book AS bo
                JOIN bo.author AS aut
                JOIN bo.category AS cat
            WHERE bo.categoryName = :categoryName
            """)
    public Long countByCategoryName(@Param("categoryName") String categoryName);


    @Query("""
            SELECT DISTINCT new com.winterhold.dto.utility.DropdownDTO(bo.code,bo.title)
            FROM Loan AS lo
                RIGHT JOIN lo.book AS bo
            WHERE bo.isBorrowed = 0 OR lo.id = :id
            ORDER BY bo.title
            """)
    public List<DropdownDTO> findAllAvailableBook(Long id);

    @Query("""
            SELECT DISTINCT new com.winterhold.dto.utility.DropdownDTO(bo.code,bo.title)
            FROM Loan AS lo
                RIGHT JOIN lo.book AS bo
            WHERE bo.isBorrowed = 0
            ORDER BY bo.title
            """)
    public List<DropdownDTO> findAllAvailableBook();

    @Query("""
            SELECT COUNT(bo.code)
            FROM Book AS bo
            WHERE bo.code = :code
            """)
    public Long countBook(@Param("code") String code);
}
