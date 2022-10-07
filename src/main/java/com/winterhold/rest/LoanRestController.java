package com.winterhold.rest;

import com.winterhold.dto.loan.InsertLoanDTO;
import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.dto.loan.UpdateLoanDTO;
import com.winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/loan")
public class LoanRestController {

    @Autowired
    private LoanService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String bookTitle,
                                      @RequestParam(defaultValue = "") String customerName){
        List<LoanGridDTO> dtos = service.getLoanGrid(page, bookTitle,customerName);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody InsertLoanDTO dto, BindingResult bindingResult){
        Long respondId = service.saveLoan(dto);
        dto.setId(respondId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping
    public ResponseEntity<Object> put(@RequestBody UpdateLoanDTO dto, BindingResult bindingResult) {
        service.returnEditBook(dto.getId());
        service.updateLoan(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PatchMapping
    public ResponseEntity<Object> returnBook(@RequestParam(required = true) Long id){
        service.returnBook(id);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sukses untun return book.");
    }

    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<Object> detail(@RequestParam(required = true) Long id){
        var detail= service.getDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }
}
