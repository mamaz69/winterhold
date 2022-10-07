package com.winterhold.rest;

import com.winterhold.dto.author.UpdateAuthorDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.service.CategoryService;
import com.winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String membershipNumber,
                                      @RequestParam(defaultValue = "") String name){
        List<CustomerGridDTO> dtos = service.getCustomerGrid(page, membershipNumber,name);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody InsertCustomerDTO dto, BindingResult bindingResult){
        String respondId = service.saveCustomer(dto);
        dto.setMembershipNumber(respondId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping
    public ResponseEntity<Object> put(@RequestBody UpdateCustomerDTO dto, BindingResult bindingResult) {
        service.updateCustomer(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam(required = true) String membershipNumber){
        service.deleteCustomer(membershipNumber);
        return ResponseEntity.status(HttpStatus.OK).body("Sukses Untuk Delete");
    }

    @GetMapping(value = "/detail/{membershipNumber}")
    public ResponseEntity<Object> detail(@RequestParam(required = true) String membershipNumber){
        var detail= service.getDetail(membershipNumber);
        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }

    @PatchMapping
    public ResponseEntity<Object> extend(@RequestParam(required = true) String membershipNumber){
        service.extendExpireDate(membershipNumber);
        return ResponseEntity.status(HttpStatus.OK).body("Sukses untun extend expire date.");
    }
}
