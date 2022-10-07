package com.winterhold.service;

import com.winterhold.dao.*;
import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.loan.InsertLoanDTO;
import com.winterhold.dto.loan.LoanDetailDTO;
import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.dto.loan.UpdateLoanDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImplementation implements LoanService{

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final Integer ROWS_IN_PAGE = 10;

    @Override
    public List<LoanGridDTO> getLoanGrid(Integer page, String bookTitle, String customerName) {
        Pageable pagination = PageRequest.of(page - 1, ROWS_IN_PAGE, Sort.by("id"));
        List<LoanGridDTO> grid = loanRepository.findAll(bookTitle, customerName, pagination);
        return grid;
    }

    @Override
    public Long getTotalPages(String bookTitle, String customerName) {
        double totalData = (double) loanRepository.count(bookTitle, customerName);
        long totalPage = (long) (Math.ceil(totalData / (double) ROWS_IN_PAGE));
        return totalPage;
    }

    @Override
    public Long saveLoan(InsertLoanDTO dto) {
        Book book = bookRepository.findById(dto.getBookCode()).get();
        book.setIsBorrowed(true);
        LocalDate dueDate = dto.getLoanDate().plusDays(5);
        Loan entity = new Loan(dto.getId(),
                dto.getCustomerNumber(),
                dto.getBookCode(),
                dto.getLoanDate(),
                dueDate,
                dto.getReturnDate(),
                dto.getNote());
        Loan newLoan = loanRepository.save(entity);
        return newLoan.getId();
    }

    @Override
    public UpdateLoanDTO getUpdateLoan(Long id) {
        Loan entity = loanRepository.findById(id).get();
        UpdateLoanDTO dto = new UpdateLoanDTO(entity.getId(),
                entity.getCustomerNumber(),
                entity.getBookCode(),
                entity.getLoanDate(),
                entity.getDueDate(),
                entity.getReturnDate(),
                entity.getNote());
        return dto;
    }

    @Override
    public void updateLoan(UpdateLoanDTO dto) {
        Loan entity = new Loan(dto.getId(),
                dto.getCustomerNumber(),
                dto.getBookCode(),
                dto.getLoanDate(),
                dto.getDueDate(),
                dto.getReturnDate(),
                dto.getNote());
        Book changeBook = bookRepository.findById(entity.getBookCode()).get();
        changeBook.setIsBorrowed(true);
        loanRepository.save(entity);
    }

    @Override
    public List<DropdownDTO> getCustomerDropdown() {
        return customerRepository.findAllActiveMembership();
    }

    @Override
    public List<DropdownDTO> getBookDropdown(Long id) {
        return bookRepository.findAllAvailableBook(id);
    }

    @Override
    public BookGridDTO getBookDetail(Long id) {
        Loan loan = loanRepository.findById(id).get();
        Book book = bookRepository.findById(loan.getBookCode()).get();
        Long authorId = book.getAuthorId();
        Author author = authorRepository.findById(authorId).get();
        String authorName = String.format("%s %s %s",author.getTitle(),author.getFirstName(),author.getLastName());
        Category category = categoryRepository.findById(book.getCategoryName()).get();
        BookGridDTO dto = new BookGridDTO(book.getCode(),
                book.getTitle(),
                book.getCategoryName(),
                authorName,
                book.getIsBorrowed(),
                book.getSummary(),
                book.getReleaseDate(),
                book.getTotalPage(),
                category.getFloor(),
                category.getIsle(),
                category.getBay());
        return dto;
    }

    @Override
    public CustomerGridDTO getCustomerDetail(Long id) {
        Loan loan = loanRepository.findById(id).get();
        Customer customer = customerRepository.findById(loan.getCustomerNumber()).get();
        String fullName = String.format("%s %s",customer.getFirstName(),customer.getLastName());
        CustomerGridDTO dto = new CustomerGridDTO(customer.getMembershipNumber(),
                fullName,
                customer.getBirthDate(),
                customer.getGender(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getMembershipExpireDate());
        return dto;
    }

    @Override
    public void returnBook(Long id) {
        Loan loan = loanRepository.findById(id).get();
        Book book = bookRepository.findById(loan.getBookCode()).get();
        book.setIsBorrowed(false);
        bookRepository.save(book);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }

    @Override
    public void returnEditBook(Long id) {
        Loan loan = loanRepository.findById(id).get();
        Book book = bookRepository.findById(loan.getBookCode()).get();
        book.setIsBorrowed(false);
        bookRepository.save(book);
    }

    @Override
    public LoanDetailDTO getDetail(Long id) {
        BookGridDTO book = bookRepository.findAllByLoanId(id);
        CustomerGridDTO customer = customerRepository.findAllByLoanId(id);
        LoanDetailDTO detail = new LoanDetailDTO(book,customer);
        return detail;
    }
}
