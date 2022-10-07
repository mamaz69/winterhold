package com.winterhold.dao;

import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.entity.Loan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {


    @Query("""
        SELECT new com.winterhold.dto.loan.LoanGridDTO(lo.id,bo.title,CONCAT(cus.firstName,' ',cus.lastName),lo.loanDate,lo.dueDate,lo.returnDate)
            FROM Loan AS lo
                JOIN lo.customer AS cus
                JOIN lo.book AS bo
            WHERE (cus.firstName LIKE %:customerName% OR cus.firstName LIKE %:customerName%) AND bo.title LIKE %:bookTitle%
            """)
    public List<LoanGridDTO> findAll(@Param("bookTitle") String bookTitle,
                                     @Param("customerName") String customerName,
                                     Pageable pageable);

    @Query("""
            SELECT COUNT(lo.id)
            FROM Loan AS lo
                JOIN lo.customer AS cus
                JOIN lo.book AS bo
            WHERE (cus.firstName LIKE %:customerName% OR cus.firstName LIKE %:customerName%) AND bo.title LIKE %:bookTitle%
            """)
    public Long count(@Param("bookTitle") String bookTitle,
                      @Param("customerName") String customerName);


}
