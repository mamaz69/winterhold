package com.winterhold.controller;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.InsertAuthorDTO;
import com.winterhold.dto.author.UpdateAuthorDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.service.AuthorService;
import com.winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String membershipNumber,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        List<CustomerGridDTO> grid = service.getCustomerGrid(page, membershipNumber,name);
        Long totalPages = service.getTotalPages(membershipNumber,name);
        page = (totalPages > 0) ? page : 0;
        model.addAttribute("grid", grid);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("membershipNumber", membershipNumber);
        model.addAttribute("name", name);
        return "/customer/customer-index";
    }

    @GetMapping("/insertForm")
    public String insertForm(@RequestParam(required = false) String membershipNumber, Model model) {
        InsertCustomerDTO dto = new InsertCustomerDTO();
        model.addAttribute("customer", dto);
        model.addAttribute("actionType", "insert");
        return "customer/customer-insert-form";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam(required = true) String membershipNumber, Model model) {
        UpdateCustomerDTO dto = service.getUpdateCustomer(membershipNumber);
        model.addAttribute("membershipExpireDate",dto.getMembershipExpireDate());
        model.addAttribute("customer", dto);
        model.addAttribute("actionType", "update");
        return "customer/customer-update-form";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("customer") InsertCustomerDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            String actionType = "insert";
            model.addAttribute("actionType", actionType);
            return "customer/customer-insert-form";
        } else {
            service.saveCustomer(dto);
            return "redirect:/customer/index";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("customer") UpdateCustomerDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            String actionType = "update";
            model.addAttribute("actionType", actionType);
            return "customer/customer-update-form";
        } else {
            service.updateCustomer(dto);
            return "redirect:/customer/index";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String membershipNumber, Model model) {
         service.deleteCustomer(membershipNumber);
        return "redirect:/customer";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) String membershipNumber,
                         Model model){
        var detail= service.getDetail(membershipNumber);
        model.addAttribute("detail",detail);
        return "customer/customer-detail";
    }

    @GetMapping("/extend")
    public String extend(@RequestParam(required = true) String membershipNumber,
                         Model model){
        service.extendExpireDate(membershipNumber);
        return "redirect:/customer";
    }


}
