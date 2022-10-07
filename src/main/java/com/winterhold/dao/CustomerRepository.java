package com.winterhold.dao;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    @Query("""
            SELECT new com.winterhold.dto.customer.CustomerGridDTO(cus.membershipNumber,
                    CONCAT(cus.firstName,' ',cus.lastName),cus.birthDate,
                    cus.gender,cus.phone,cus.address,cus.membershipExpireDate)
            FROM Customer AS cus
            WHERE (cus.firstName LIKE %:name% OR cus.lastName LIKE %:name%)
                    AND cus.membershipNumber LIKE %:membershipNumber%
            """)
    public List<CustomerGridDTO> findAll(@Param("membershipNumber") String membershipNumber,@Param("name") String name, Pageable pageable);

    @Query("""
            SELECT COUNT(cus.membershipNumber)
            FROM Customer AS cus
            WHERE (cus.firstName LIKE %:name% OR cus.lastName LIKE %:name%)
                    AND cus.membershipNumber LIKE %:membershipNumber%
            """)
    public Long count(@Param("membershipNumber") String membershipNumber,@Param("name") String name);


    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(cus.membershipNumber,CONCAT(cus.firstName,' ',cus.lastName))
            FROM Customer AS cus
            WHERE cus.membershipExpireDate > GETDATE()
            ORDER BY cus.firstName
            """)
    public List<DropdownDTO> findAllActiveMembership();

    @Query("""
            SELECT COUNT(cus.membershipNumber)
            FROM Customer AS cus
            WHERE cus.membershipNumber = :name
            """)
    public Long countMembership(@Param("name") String name);

    @Query("""
            SELECT new com.winterhold.dto.customer.CustomerGridDTO(cus.membershipNumber,
                    CONCAT(cus.firstName,' ',cus.lastName),cus.birthDate,
                    cus.gender,cus.phone,cus.address,cus.membershipExpireDate)             
            FROM Loan AS lo
                RIGHT JOIN lo.customer AS cus
            WHERE lo.id = :id
            """)
    public CustomerGridDTO findAllByLoanId(@Param("id") Long id);

}
