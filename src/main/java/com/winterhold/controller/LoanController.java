package com.winterhold.controller;

import com.winterhold.dto.loan.InsertLoanDTO;
import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.dto.loan.UpdateLoanDTO;
import com.winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String bookTitle,
                        @RequestParam(defaultValue = "") String customerName,
                        Model model) {
        List<LoanGridDTO> grid = service.getLoanGrid(page, bookTitle,customerName);
        Long totalPages = service.getTotalPages(bookTitle,customerName);
        page = (totalPages > 0) ? page : 0;
        model.addAttribute("grid", grid);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("customerName", customerName);
        model.addAttribute("bookTitle", bookTitle);
        return "/loan/loan-index";
    }

    @GetMapping("/insertForm")
    public String insertForm(@RequestParam(required = false) Long id, Model model) {
        var bookDropdown = service.getBookDropdown(id);
        var customerDropdown = service.getCustomerDropdown();
        InsertLoanDTO dto = new InsertLoanDTO();
        model.addAttribute("loan", dto);
        model.addAttribute("bookDropdown", bookDropdown);
        model.addAttribute("customerDropdown", customerDropdown);
        model.addAttribute("actionType", "insert");
        return "loan/loan-insert-form";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam(required = true) Long id, Model model) {
        var bookDropdown = service.getBookDropdown(id);
        var customerDropdown = service.getCustomerDropdown();
        UpdateLoanDTO dto = service.getUpdateLoan(id);
        service.returnEditBook(dto.getId());
        model.addAttribute("loan", dto);
        model.addAttribute("bookDropdown", bookDropdown);
        model.addAttribute("customerDropdown", customerDropdown);
        model.addAttribute("actionType", "update");
        return "loan/loan-update-form";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("loan") InsertLoanDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            var bookDropdown = service.getBookDropdown(dto.getId());
            var customerDropdown = service.getCustomerDropdown();
            String actionType = "insert";
            model.addAttribute("bookDropdown", bookDropdown);
            model.addAttribute("customerDropdown", customerDropdown);
            model.addAttribute("actionType", actionType);
            return "loan/loan-insert-form";
        } else {
            service.saveLoan(dto);
            return "redirect:/loan/index";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("loan") UpdateLoanDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            var bookDropdown = service.getBookDropdown(dto.getId());
            var customerDropdown = service.getCustomerDropdown();
            String actionType = "update";
            model.addAttribute("bookDropdown", bookDropdown);
            model.addAttribute("customerDropdown", customerDropdown);
            model.addAttribute("actionType", actionType);
            return "loan/loan-update-form";
        } else {
            service.updateLoan(dto);
            return "redirect:/loan/index";
        }
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) Long id,
                         Model model){
        var bookDetail= service.getBookDetail(id);
        var customerDetail= service.getCustomerDetail(id);
        model.addAttribute("bookDetail",bookDetail);
        model.addAttribute("customerDetail",customerDetail);
        return "loan/loan-detail";
    }

    @GetMapping("/return")
    public String returnBook(@RequestParam(required = true) Long id,
                         Model model){
        service.returnBook(id);
        return "redirect:/loan";
    }
}
