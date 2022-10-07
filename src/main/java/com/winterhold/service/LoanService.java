package com.winterhold.service;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.loan.InsertLoanDTO;
import com.winterhold.dto.loan.LoanDetailDTO;
import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.dto.loan.UpdateLoanDTO;
import com.winterhold.dto.utility.DropdownDTO;

import java.util.List;

public interface LoanService {

    public List<LoanGridDTO> getLoanGrid(Integer page, String bookTitle, String customerName);
    public Long getTotalPages(String bookTitle, String customerName);

    public Long saveLoan(InsertLoanDTO dto);
    public UpdateLoanDTO getUpdateLoan(Long id);

    public void updateLoan(UpdateLoanDTO dto);

    public List<DropdownDTO> getCustomerDropdown();
    public List<DropdownDTO> getBookDropdown(Long id);

    public BookGridDTO getBookDetail(Long id);
    public CustomerGridDTO getCustomerDetail(Long id);
    public void returnBook(Long id);
    public void returnEditBook(Long id);

    public LoanDetailDTO getDetail(Long id);

}
